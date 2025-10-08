package androidx.appcompat.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

class ActivityChooserModel extends DataSetObservable {
    static final String ATTRIBUTE_ACTIVITY = "activity";
    static final String ATTRIBUTE_TIME = "time";
    static final String ATTRIBUTE_WEIGHT = "weight";
    static final boolean DEBUG = false;
    private static final int DEFAULT_ACTIVITY_INFLATION = 5;
    private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0f;
    public static final String DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml";
    public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
    private static final String HISTORY_FILE_EXTENSION = ".xml";
    private static final int INVALID_INDEX = -1;
    static final String LOG_TAG = ActivityChooserModel.class.getSimpleName();
    static final String TAG_HISTORICAL_RECORD = "historical-record";
    static final String TAG_HISTORICAL_RECORDS = "historical-records";
    private static final Map<String, ActivityChooserModel> sDataModelRegistry = new HashMap();
    private static final Object sRegistryLock = new Object();
    private final List<ActivityResolveInfo> mActivities = new ArrayList();
    private OnChooseActivityListener mActivityChoserModelPolicy;
    private ActivitySorter mActivitySorter = new DefaultSorter();
    boolean mCanReadHistoricalData = true;
    final Context mContext;
    private final List<HistoricalRecord> mHistoricalRecords = new ArrayList();
    private boolean mHistoricalRecordsChanged = true;
    final String mHistoryFileName;
    private int mHistoryMaxSize = 50;
    private final Object mInstanceLock = new Object();
    private Intent mIntent;
    private boolean mReadShareHistoryCalled = false;
    private boolean mReloadActivities = false;

    public interface ActivityChooserModelClient {
        void setActivityChooserModel(ActivityChooserModel activityChooserModel);
    }

    public interface ActivitySorter {
        void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2);
    }

    public interface OnChooseActivityListener {
        boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent);
    }

    public static ActivityChooserModel get(Context context, String historyFileName) {
        ActivityChooserModel dataModel;
        synchronized (sRegistryLock) {
            dataModel = sDataModelRegistry.get(historyFileName);
            if (dataModel == null) {
                dataModel = new ActivityChooserModel(context, historyFileName);
                sDataModelRegistry.put(historyFileName, dataModel);
            }
        }
        return dataModel;
    }

    private ActivityChooserModel(Context context, String historyFileName) {
        this.mContext = context.getApplicationContext();
        if (TextUtils.isEmpty(historyFileName) || historyFileName.endsWith(HISTORY_FILE_EXTENSION)) {
            this.mHistoryFileName = historyFileName;
        } else {
            this.mHistoryFileName = historyFileName + HISTORY_FILE_EXTENSION;
        }
    }

    public void setIntent(Intent intent) {
        synchronized (this.mInstanceLock) {
            if (this.mIntent != intent) {
                this.mIntent = intent;
                this.mReloadActivities = true;
                ensureConsistentState();
            }
        }
    }

    public Intent getIntent() {
        Intent intent;
        synchronized (this.mInstanceLock) {
            intent = this.mIntent;
        }
        return intent;
    }

    public int getActivityCount() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mActivities.size();
        }
        return size;
    }

    public ResolveInfo getActivity(int index) {
        ResolveInfo resolveInfo;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            resolveInfo = this.mActivities.get(index).resolveInfo;
        }
        return resolveInfo;
    }

    public int getActivityIndex(ResolveInfo activity) {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            List<ActivityResolveInfo> activities = this.mActivities;
            int activityCount = activities.size();
            for (int i = 0; i < activityCount; i++) {
                if (activities.get(i).resolveInfo == activity) {
                    return i;
                }
            }
            return -1;
        }
    }

    public Intent chooseActivity(int index) {
        synchronized (this.mInstanceLock) {
            if (this.mIntent == null) {
                return null;
            }
            ensureConsistentState();
            ActivityResolveInfo chosenActivity = this.mActivities.get(index);
            ComponentName chosenName = new ComponentName(chosenActivity.resolveInfo.activityInfo.packageName, chosenActivity.resolveInfo.activityInfo.name);
            Intent choiceIntent = new Intent(this.mIntent);
            choiceIntent.setComponent(chosenName);
            if (this.mActivityChoserModelPolicy != null) {
                if (this.mActivityChoserModelPolicy.onChooseActivity(this, new Intent(choiceIntent))) {
                    return null;
                }
            }
            addHistoricalRecord(new HistoricalRecord(chosenName, System.currentTimeMillis(), 1.0f));
            return choiceIntent;
        }
    }

    public void setOnChooseActivityListener(OnChooseActivityListener listener) {
        synchronized (this.mInstanceLock) {
            this.mActivityChoserModelPolicy = listener;
        }
    }

    public ResolveInfo getDefaultActivity() {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            if (this.mActivities.isEmpty()) {
                return null;
            }
            ResolveInfo resolveInfo = this.mActivities.get(0).resolveInfo;
            return resolveInfo;
        }
    }

    public void setDefaultActivity(int index) {
        float weight;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            ActivityResolveInfo newDefaultActivity = this.mActivities.get(index);
            ActivityResolveInfo oldDefaultActivity = this.mActivities.get(0);
            if (oldDefaultActivity != null) {
                weight = (oldDefaultActivity.weight - newDefaultActivity.weight) + 5.0f;
            } else {
                weight = 1.0f;
            }
            addHistoricalRecord(new HistoricalRecord(new ComponentName(newDefaultActivity.resolveInfo.activityInfo.packageName, newDefaultActivity.resolveInfo.activityInfo.name), System.currentTimeMillis(), weight));
        }
    }

    private void persistHistoricalDataIfNeeded() {
        if (!this.mReadShareHistoryCalled) {
            throw new IllegalStateException("No preceding call to #readHistoricalData");
        } else if (this.mHistoricalRecordsChanged) {
            this.mHistoricalRecordsChanged = false;
            if (!TextUtils.isEmpty(this.mHistoryFileName)) {
                new PersistHistoryAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[]{new ArrayList(this.mHistoricalRecords), this.mHistoryFileName});
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0015, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setActivitySorter(androidx.appcompat.widget.ActivityChooserModel.ActivitySorter r3) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.mInstanceLock
            monitor-enter(r0)
            androidx.appcompat.widget.ActivityChooserModel$ActivitySorter r1 = r2.mActivitySorter     // Catch:{ all -> 0x0016 }
            if (r1 != r3) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0016 }
            return
        L_0x0009:
            r2.mActivitySorter = r3     // Catch:{ all -> 0x0016 }
            boolean r1 = r2.sortActivitiesIfNeeded()     // Catch:{ all -> 0x0016 }
            if (r1 == 0) goto L_0x0014
            r2.notifyChanged()     // Catch:{ all -> 0x0016 }
        L_0x0014:
            monitor-exit(r0)     // Catch:{ all -> 0x0016 }
            return
        L_0x0016:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0016 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActivityChooserModel.setActivitySorter(androidx.appcompat.widget.ActivityChooserModel$ActivitySorter):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0018, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setHistoryMaxSize(int r3) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.mInstanceLock
            monitor-enter(r0)
            int r1 = r2.mHistoryMaxSize     // Catch:{ all -> 0x0019 }
            if (r1 != r3) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0019 }
            return
        L_0x0009:
            r2.mHistoryMaxSize = r3     // Catch:{ all -> 0x0019 }
            r2.pruneExcessiveHistoricalRecordsIfNeeded()     // Catch:{ all -> 0x0019 }
            boolean r1 = r2.sortActivitiesIfNeeded()     // Catch:{ all -> 0x0019 }
            if (r1 == 0) goto L_0x0017
            r2.notifyChanged()     // Catch:{ all -> 0x0019 }
        L_0x0017:
            monitor-exit(r0)     // Catch:{ all -> 0x0019 }
            return
        L_0x0019:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0019 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActivityChooserModel.setHistoryMaxSize(int):void");
    }

    public int getHistoryMaxSize() {
        int i;
        synchronized (this.mInstanceLock) {
            i = this.mHistoryMaxSize;
        }
        return i;
    }

    public int getHistorySize() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mHistoricalRecords.size();
        }
        return size;
    }

    private void ensureConsistentState() {
        boolean stateChanged = loadActivitiesIfNeeded() | readHistoricalDataIfNeeded();
        pruneExcessiveHistoricalRecordsIfNeeded();
        if (stateChanged) {
            sortActivitiesIfNeeded();
            notifyChanged();
        }
    }

    private boolean sortActivitiesIfNeeded() {
        if (this.mActivitySorter == null || this.mIntent == null || this.mActivities.isEmpty() || this.mHistoricalRecords.isEmpty()) {
            return false;
        }
        this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
        return true;
    }

    private boolean loadActivitiesIfNeeded() {
        if (!this.mReloadActivities || this.mIntent == null) {
            return false;
        }
        this.mReloadActivities = false;
        this.mActivities.clear();
        List<ResolveInfo> resolveInfos = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
        int resolveInfoCount = resolveInfos.size();
        for (int i = 0; i < resolveInfoCount; i++) {
            this.mActivities.add(new ActivityResolveInfo(resolveInfos.get(i)));
        }
        return true;
    }

    private boolean readHistoricalDataIfNeeded() {
        if (!this.mCanReadHistoricalData || !this.mHistoricalRecordsChanged || TextUtils.isEmpty(this.mHistoryFileName)) {
            return false;
        }
        this.mCanReadHistoricalData = false;
        this.mReadShareHistoryCalled = true;
        readHistoricalDataImpl();
        return true;
    }

    private boolean addHistoricalRecord(HistoricalRecord historicalRecord) {
        boolean added = this.mHistoricalRecords.add(historicalRecord);
        if (added) {
            this.mHistoricalRecordsChanged = true;
            pruneExcessiveHistoricalRecordsIfNeeded();
            persistHistoricalDataIfNeeded();
            sortActivitiesIfNeeded();
            notifyChanged();
        }
        return added;
    }

    private void pruneExcessiveHistoricalRecordsIfNeeded() {
        int pruneCount = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
        if (pruneCount > 0) {
            this.mHistoricalRecordsChanged = true;
            for (int i = 0; i < pruneCount; i++) {
                HistoricalRecord remove = this.mHistoricalRecords.remove(0);
            }
        }
    }

    public static final class HistoricalRecord {
        public final ComponentName activity;
        public final long time;
        public final float weight;

        public HistoricalRecord(String activityName, long time2, float weight2) {
            this(ComponentName.unflattenFromString(activityName), time2, weight2);
        }

        public HistoricalRecord(ComponentName activityName, long time2, float weight2) {
            this.activity = activityName;
            this.time = time2;
            this.weight = weight2;
        }

        public int hashCode() {
            return (((((1 * 31) + (this.activity == null ? 0 : this.activity.hashCode())) * 31) + ((int) (this.time ^ (this.time >>> 32)))) * 31) + Float.floatToIntBits(this.weight);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            HistoricalRecord other = (HistoricalRecord) obj;
            if (this.activity == null) {
                if (other.activity != null) {
                    return false;
                }
            } else if (!this.activity.equals(other.activity)) {
                return false;
            }
            if (this.time == other.time && Float.floatToIntBits(this.weight) == Float.floatToIntBits(other.weight)) {
                return true;
            }
            return false;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("[");
            builder.append("; activity:").append(this.activity);
            builder.append("; time:").append(this.time);
            builder.append("; weight:").append(new BigDecimal((double) this.weight));
            builder.append("]");
            return builder.toString();
        }
    }

    public static final class ActivityResolveInfo implements Comparable<ActivityResolveInfo> {
        public final ResolveInfo resolveInfo;
        public float weight;

        public ActivityResolveInfo(ResolveInfo resolveInfo2) {
            this.resolveInfo = resolveInfo2;
        }

        public int hashCode() {
            return Float.floatToIntBits(this.weight) + 31;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass() && Float.floatToIntBits(this.weight) == Float.floatToIntBits(((ActivityResolveInfo) obj).weight)) {
                return true;
            }
            return false;
        }

        public int compareTo(ActivityResolveInfo another) {
            return Float.floatToIntBits(another.weight) - Float.floatToIntBits(this.weight);
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("[");
            builder.append("resolveInfo:").append(this.resolveInfo.toString());
            builder.append("; weight:").append(new BigDecimal((double) this.weight));
            builder.append("]");
            return builder.toString();
        }
    }

    private static final class DefaultSorter implements ActivitySorter {
        private static final float WEIGHT_DECAY_COEFFICIENT = 0.95f;
        private final Map<ComponentName, ActivityResolveInfo> mPackageNameToActivityMap = new HashMap();

        DefaultSorter() {
        }

        public void sort(Intent intent, List<ActivityResolveInfo> activities, List<HistoricalRecord> historicalRecords) {
            Map<ComponentName, ActivityResolveInfo> componentNameToActivityMap = this.mPackageNameToActivityMap;
            componentNameToActivityMap.clear();
            int activityCount = activities.size();
            for (int i = 0; i < activityCount; i++) {
                ActivityResolveInfo activity = activities.get(i);
                activity.weight = 0.0f;
                componentNameToActivityMap.put(new ComponentName(activity.resolveInfo.activityInfo.packageName, activity.resolveInfo.activityInfo.name), activity);
            }
            float nextRecordWeight = 1.0f;
            for (int i2 = historicalRecords.size() - 1; i2 >= 0; i2--) {
                HistoricalRecord historicalRecord = historicalRecords.get(i2);
                ActivityResolveInfo activity2 = componentNameToActivityMap.get(historicalRecord.activity);
                if (activity2 != null) {
                    activity2.weight += historicalRecord.weight * nextRecordWeight;
                    nextRecordWeight *= WEIGHT_DECAY_COEFFICIENT;
                }
            }
            Collections.sort(activities);
        }
    }

    private void readHistoricalDataImpl() {
        try {
            FileInputStream fis = this.mContext.openFileInput(this.mHistoryFileName);
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(fis, "UTF-8");
                int type = 0;
                while (type != 1 && type != 2) {
                    type = parser.next();
                }
                if (TAG_HISTORICAL_RECORDS.equals(parser.getName())) {
                    List<HistoricalRecord> historicalRecords = this.mHistoricalRecords;
                    historicalRecords.clear();
                    while (true) {
                        int type2 = parser.next();
                        if (type2 == 1) {
                            if (fis == null) {
                                return;
                            }
                        } else if (!(type2 == 3 || type2 == 4)) {
                            if (TAG_HISTORICAL_RECORD.equals(parser.getName())) {
                                historicalRecords.add(new HistoricalRecord(parser.getAttributeValue((String) null, ATTRIBUTE_ACTIVITY), Long.parseLong(parser.getAttributeValue((String) null, ATTRIBUTE_TIME)), Float.parseFloat(parser.getAttributeValue((String) null, ATTRIBUTE_WEIGHT))));
                            } else {
                                throw new XmlPullParserException("Share records file not well-formed.");
                            }
                        }
                    }
                    try {
                        fis.close();
                    } catch (IOException e) {
                    }
                } else {
                    throw new XmlPullParserException("Share records file does not start with historical-records tag.");
                }
            } catch (XmlPullParserException xppe) {
                Log.e(LOG_TAG, "Error reading historical recrod file: " + this.mHistoryFileName, xppe);
                if (fis == null) {
                }
            } catch (IOException ioe) {
                Log.e(LOG_TAG, "Error reading historical recrod file: " + this.mHistoryFileName, ioe);
                if (fis == null) {
                }
            } catch (Throwable th) {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e2) {
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException e3) {
        }
    }

    private final class PersistHistoryAsyncTask extends AsyncTask<Object, Void, Void> {
        PersistHistoryAsyncTask() {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0079, code lost:
            if (r10 != null) goto L_0x00ad;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x00ab, code lost:
            if (r10 != null) goto L_0x00ad;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
            r10.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x00d3, code lost:
            if (r10 != null) goto L_0x00ad;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x00f8, code lost:
            if (r10 != null) goto L_0x00ad;
         */
        /* JADX WARNING: Removed duplicated region for block: B:45:0x0107 A[SYNTHETIC, Splitter:B:45:0x0107] */
        /* JADX WARNING: Unknown top exception splitter block from list: {B:29:0x00b4=Splitter:B:29:0x00b4, B:35:0x00d9=Splitter:B:35:0x00d9, B:21:0x008c=Splitter:B:21:0x008c} */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Void doInBackground(java.lang.Object... r18) {
            /*
                r17 = this;
                r1 = r17
                java.lang.String r0 = "historical-record"
                java.lang.String r2 = "historical-records"
                java.lang.String r3 = "Error writing historical record file: "
                r4 = 0
                r5 = r18[r4]
                java.util.List r5 = (java.util.List) r5
                r6 = 1
                r7 = r18[r6]
                java.lang.String r7 = (java.lang.String) r7
                r8 = 0
                r9 = 0
                androidx.appcompat.widget.ActivityChooserModel r10 = androidx.appcompat.widget.ActivityChooserModel.this     // Catch:{ FileNotFoundException -> 0x010d }
                android.content.Context r10 = r10.mContext     // Catch:{ FileNotFoundException -> 0x010d }
                java.io.FileOutputStream r10 = r10.openFileOutput(r7, r4)     // Catch:{ FileNotFoundException -> 0x010d }
                org.xmlpull.v1.XmlSerializer r8 = android.util.Xml.newSerializer()
                r8.setOutput(r10, r9)     // Catch:{ IllegalArgumentException -> 0x00d6, IllegalStateException -> 0x00b1, IOException -> 0x0089, all -> 0x0083 }
                java.lang.String r11 = "UTF-8"
                java.lang.Boolean r12 = java.lang.Boolean.valueOf(r6)     // Catch:{ IllegalArgumentException -> 0x00d6, IllegalStateException -> 0x00b1, IOException -> 0x0089, all -> 0x0083 }
                r8.startDocument(r11, r12)     // Catch:{ IllegalArgumentException -> 0x00d6, IllegalStateException -> 0x00b1, IOException -> 0x0089, all -> 0x0083 }
                r8.startTag(r9, r2)     // Catch:{ IllegalArgumentException -> 0x00d6, IllegalStateException -> 0x00b1, IOException -> 0x0089, all -> 0x0083 }
                int r11 = r5.size()     // Catch:{ IllegalArgumentException -> 0x00d6, IllegalStateException -> 0x00b1, IOException -> 0x0089, all -> 0x0083 }
                r12 = 0
            L_0x0035:
                if (r12 >= r11) goto L_0x006d
                java.lang.Object r13 = r5.remove(r4)     // Catch:{ IllegalArgumentException -> 0x00d6, IllegalStateException -> 0x00b1, IOException -> 0x0089, all -> 0x0083 }
                androidx.appcompat.widget.ActivityChooserModel$HistoricalRecord r13 = (androidx.appcompat.widget.ActivityChooserModel.HistoricalRecord) r13     // Catch:{ IllegalArgumentException -> 0x00d6, IllegalStateException -> 0x00b1, IOException -> 0x0089, all -> 0x0083 }
                r8.startTag(r9, r0)     // Catch:{ IllegalArgumentException -> 0x00d6, IllegalStateException -> 0x00b1, IOException -> 0x0089, all -> 0x0083 }
                java.lang.String r14 = "activity"
                android.content.ComponentName r15 = r13.activity     // Catch:{ IllegalArgumentException -> 0x00d6, IllegalStateException -> 0x00b1, IOException -> 0x0089, all -> 0x0083 }
                java.lang.String r15 = r15.flattenToString()     // Catch:{ IllegalArgumentException -> 0x00d6, IllegalStateException -> 0x00b1, IOException -> 0x0089, all -> 0x0083 }
                r8.attribute(r9, r14, r15)     // Catch:{ IllegalArgumentException -> 0x00d6, IllegalStateException -> 0x00b1, IOException -> 0x0089, all -> 0x0083 }
                java.lang.String r14 = "time"
                r16 = r5
                long r4 = r13.time     // Catch:{ IllegalArgumentException -> 0x0081, IllegalStateException -> 0x007f, IOException -> 0x007d }
                java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ IllegalArgumentException -> 0x0081, IllegalStateException -> 0x007f, IOException -> 0x007d }
                r8.attribute(r9, r14, r4)     // Catch:{ IllegalArgumentException -> 0x0081, IllegalStateException -> 0x007f, IOException -> 0x007d }
                java.lang.String r4 = "weight"
                float r5 = r13.weight     // Catch:{ IllegalArgumentException -> 0x0081, IllegalStateException -> 0x007f, IOException -> 0x007d }
                java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ IllegalArgumentException -> 0x0081, IllegalStateException -> 0x007f, IOException -> 0x007d }
                r8.attribute(r9, r4, r5)     // Catch:{ IllegalArgumentException -> 0x0081, IllegalStateException -> 0x007f, IOException -> 0x007d }
                r8.endTag(r9, r0)     // Catch:{ IllegalArgumentException -> 0x0081, IllegalStateException -> 0x007f, IOException -> 0x007d }
                int r12 = r12 + 1
                r5 = r16
                r4 = 0
                goto L_0x0035
            L_0x006d:
                r16 = r5
                r8.endTag(r9, r2)     // Catch:{ IllegalArgumentException -> 0x0081, IllegalStateException -> 0x007f, IOException -> 0x007d }
                r8.endDocument()     // Catch:{ IllegalArgumentException -> 0x0081, IllegalStateException -> 0x007f, IOException -> 0x007d }
                androidx.appcompat.widget.ActivityChooserModel r0 = androidx.appcompat.widget.ActivityChooserModel.this
                r0.mCanReadHistoricalData = r6
                if (r10 == 0) goto L_0x00fe
            L_0x007b:
                goto L_0x00fa
            L_0x007d:
                r0 = move-exception
                goto L_0x008c
            L_0x007f:
                r0 = move-exception
                goto L_0x00b4
            L_0x0081:
                r0 = move-exception
                goto L_0x00d9
            L_0x0083:
                r0 = move-exception
                r16 = r5
                r2 = r0
                goto L_0x0101
            L_0x0089:
                r0 = move-exception
                r16 = r5
            L_0x008c:
                java.lang.String r2 = androidx.appcompat.widget.ActivityChooserModel.LOG_TAG     // Catch:{ all -> 0x00ff }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ff }
                r4.<init>()     // Catch:{ all -> 0x00ff }
                java.lang.StringBuilder r3 = r4.append(r3)     // Catch:{ all -> 0x00ff }
                androidx.appcompat.widget.ActivityChooserModel r4 = androidx.appcompat.widget.ActivityChooserModel.this     // Catch:{ all -> 0x00ff }
                java.lang.String r4 = r4.mHistoryFileName     // Catch:{ all -> 0x00ff }
                java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x00ff }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00ff }
                android.util.Log.e(r2, r3, r0)     // Catch:{ all -> 0x00ff }
                androidx.appcompat.widget.ActivityChooserModel r0 = androidx.appcompat.widget.ActivityChooserModel.this
                r0.mCanReadHistoricalData = r6
                if (r10 == 0) goto L_0x00fe
            L_0x00ad:
                r10.close()     // Catch:{ IOException -> 0x00fc }
                goto L_0x00fb
            L_0x00b1:
                r0 = move-exception
                r16 = r5
            L_0x00b4:
                java.lang.String r2 = androidx.appcompat.widget.ActivityChooserModel.LOG_TAG     // Catch:{ all -> 0x00ff }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ff }
                r4.<init>()     // Catch:{ all -> 0x00ff }
                java.lang.StringBuilder r3 = r4.append(r3)     // Catch:{ all -> 0x00ff }
                androidx.appcompat.widget.ActivityChooserModel r4 = androidx.appcompat.widget.ActivityChooserModel.this     // Catch:{ all -> 0x00ff }
                java.lang.String r4 = r4.mHistoryFileName     // Catch:{ all -> 0x00ff }
                java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x00ff }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00ff }
                android.util.Log.e(r2, r3, r0)     // Catch:{ all -> 0x00ff }
                androidx.appcompat.widget.ActivityChooserModel r0 = androidx.appcompat.widget.ActivityChooserModel.this
                r0.mCanReadHistoricalData = r6
                if (r10 == 0) goto L_0x00fe
                goto L_0x007b
            L_0x00d6:
                r0 = move-exception
                r16 = r5
            L_0x00d9:
                java.lang.String r2 = androidx.appcompat.widget.ActivityChooserModel.LOG_TAG     // Catch:{ all -> 0x00ff }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ff }
                r4.<init>()     // Catch:{ all -> 0x00ff }
                java.lang.StringBuilder r3 = r4.append(r3)     // Catch:{ all -> 0x00ff }
                androidx.appcompat.widget.ActivityChooserModel r4 = androidx.appcompat.widget.ActivityChooserModel.this     // Catch:{ all -> 0x00ff }
                java.lang.String r4 = r4.mHistoryFileName     // Catch:{ all -> 0x00ff }
                java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x00ff }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00ff }
                android.util.Log.e(r2, r3, r0)     // Catch:{ all -> 0x00ff }
                androidx.appcompat.widget.ActivityChooserModel r0 = androidx.appcompat.widget.ActivityChooserModel.this
                r0.mCanReadHistoricalData = r6
                if (r10 == 0) goto L_0x00fe
            L_0x00fa:
                goto L_0x00ad
            L_0x00fb:
                goto L_0x00fe
            L_0x00fc:
                r0 = move-exception
                goto L_0x00fb
            L_0x00fe:
                return r9
            L_0x00ff:
                r0 = move-exception
                r2 = r0
            L_0x0101:
                androidx.appcompat.widget.ActivityChooserModel r0 = androidx.appcompat.widget.ActivityChooserModel.this
                r0.mCanReadHistoricalData = r6
                if (r10 == 0) goto L_0x010c
                r10.close()     // Catch:{ IOException -> 0x010b }
                goto L_0x010c
            L_0x010b:
                r0 = move-exception
            L_0x010c:
                throw r2
            L_0x010d:
                r0 = move-exception
                r16 = r5
                java.lang.String r2 = androidx.appcompat.widget.ActivityChooserModel.LOG_TAG
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.StringBuilder r3 = r4.append(r3)
                java.lang.StringBuilder r3 = r3.append(r7)
                java.lang.String r3 = r3.toString()
                android.util.Log.e(r2, r3, r0)
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActivityChooserModel.PersistHistoryAsyncTask.doInBackground(java.lang.Object[]):java.lang.Void");
        }
    }
}
