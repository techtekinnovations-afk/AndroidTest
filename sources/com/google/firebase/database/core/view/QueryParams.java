package com.google.firebase.database.core.view;

import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.view.filter.IndexedFilter;
import com.google.firebase.database.core.view.filter.LimitedFilter;
import com.google.firebase.database.core.view.filter.NodeFilter;
import com.google.firebase.database.core.view.filter.RangedFilter;
import com.google.firebase.database.snapshot.BooleanNode;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.DoubleNode;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.LongNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.snapshot.PriorityIndex;
import com.google.firebase.database.snapshot.PriorityUtilities;
import com.google.firebase.database.snapshot.StringNode;
import com.google.firebase.database.util.JsonMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class QueryParams {
    public static final QueryParams DEFAULT_PARAMS = new QueryParams();
    private static final String INDEX = "i";
    private static final String INDEX_END_NAME = "en";
    private static final String INDEX_END_VALUE = "ep";
    private static final String INDEX_START_NAME = "sn";
    private static final String INDEX_START_VALUE = "sp";
    private static final String LIMIT = "l";
    private static final String VIEW_FROM = "vf";
    private Index index = PriorityIndex.getInstance();
    private ChildKey indexEndName = null;
    private Node indexEndValue = null;
    private ChildKey indexStartName = null;
    private Node indexStartValue = null;
    private String jsonSerialization = null;
    private Integer limit;
    private ViewFrom viewFrom;

    private enum ViewFrom {
        LEFT,
        RIGHT
    }

    public boolean hasStart() {
        return this.indexStartValue != null;
    }

    public Node getIndexStartValue() {
        if (hasStart()) {
            return this.indexStartValue;
        }
        throw new IllegalArgumentException("Cannot get index start value if start has not been set");
    }

    public ChildKey getIndexStartName() {
        if (!hasStart()) {
            throw new IllegalArgumentException("Cannot get index start name if start has not been set");
        } else if (this.indexStartName != null) {
            return this.indexStartName;
        } else {
            return ChildKey.getMinName();
        }
    }

    public boolean hasEnd() {
        return this.indexEndValue != null;
    }

    public Node getIndexEndValue() {
        if (hasEnd()) {
            return this.indexEndValue;
        }
        throw new IllegalArgumentException("Cannot get index end value if start has not been set");
    }

    public ChildKey getIndexEndName() {
        if (!hasEnd()) {
            throw new IllegalArgumentException("Cannot get index end name if start has not been set");
        } else if (this.indexEndName != null) {
            return this.indexEndName;
        } else {
            return ChildKey.getMaxName();
        }
    }

    public boolean hasLimit() {
        return this.limit != null;
    }

    public boolean hasAnchoredLimit() {
        return hasLimit() && this.viewFrom != null;
    }

    public int getLimit() {
        if (hasLimit()) {
            return this.limit.intValue();
        }
        throw new IllegalArgumentException("Cannot get limit if limit has not been set");
    }

    public Index getIndex() {
        return this.index;
    }

    private QueryParams copy() {
        QueryParams params = new QueryParams();
        params.limit = this.limit;
        params.indexStartValue = this.indexStartValue;
        params.indexStartName = this.indexStartName;
        params.indexEndValue = this.indexEndValue;
        params.indexEndName = this.indexEndName;
        params.viewFrom = this.viewFrom;
        params.index = this.index;
        return params;
    }

    public QueryParams limitToFirst(int limit2) {
        QueryParams copy = copy();
        copy.limit = Integer.valueOf(limit2);
        copy.viewFrom = ViewFrom.LEFT;
        return copy;
    }

    public QueryParams limitToLast(int limit2) {
        QueryParams copy = copy();
        copy.limit = Integer.valueOf(limit2);
        copy.viewFrom = ViewFrom.RIGHT;
        return copy;
    }

    public QueryParams startAt(Node indexStartValue2, ChildKey indexStartName2) {
        Utilities.hardAssert(indexStartValue2.isLeafNode() || indexStartValue2.isEmpty());
        Utilities.hardAssert(!(indexStartValue2 instanceof LongNode));
        QueryParams copy = copy();
        copy.indexStartValue = indexStartValue2;
        copy.indexStartName = indexStartName2;
        return copy;
    }

    public QueryParams endAt(Node indexEndValue2, ChildKey indexEndName2) {
        Utilities.hardAssert(indexEndValue2.isLeafNode() || indexEndValue2.isEmpty());
        Utilities.hardAssert(!(indexEndValue2 instanceof LongNode));
        QueryParams copy = copy();
        copy.indexEndValue = indexEndValue2;
        copy.indexEndName = indexEndName2;
        return copy;
    }

    public QueryParams orderBy(Index index2) {
        QueryParams copy = copy();
        copy.index = index2;
        return copy;
    }

    public boolean isViewFromLeft() {
        if (this.viewFrom != null) {
            return this.viewFrom == ViewFrom.LEFT;
        }
        return hasStart();
    }

    public Map<String, Object> getWireProtocolParams() {
        Map<String, Object> queryObject = new HashMap<>();
        if (hasStart()) {
            queryObject.put(INDEX_START_VALUE, this.indexStartValue.getValue());
            if (this.indexStartName != null) {
                queryObject.put(INDEX_START_NAME, this.indexStartName.asString());
            }
        }
        if (hasEnd()) {
            queryObject.put(INDEX_END_VALUE, this.indexEndValue.getValue());
            if (this.indexEndName != null) {
                queryObject.put(INDEX_END_NAME, this.indexEndName.asString());
            }
        }
        if (this.limit != null) {
            queryObject.put(LIMIT, this.limit);
            ViewFrom viewFromToAdd = this.viewFrom;
            if (viewFromToAdd == null) {
                if (hasStart()) {
                    viewFromToAdd = ViewFrom.LEFT;
                } else {
                    viewFromToAdd = ViewFrom.RIGHT;
                }
            }
            switch (viewFromToAdd) {
                case LEFT:
                    queryObject.put(VIEW_FROM, LIMIT);
                    break;
                case RIGHT:
                    queryObject.put(VIEW_FROM, "r");
                    break;
            }
        }
        if (!this.index.equals(PriorityIndex.getInstance())) {
            queryObject.put(INDEX, this.index.getQueryDefinition());
        }
        return queryObject;
    }

    public boolean loadsAllData() {
        return !hasStart() && !hasEnd() && !hasLimit();
    }

    public boolean isDefault() {
        return loadsAllData() && this.index.equals(PriorityIndex.getInstance());
    }

    public boolean isValid() {
        return !hasStart() || !hasEnd() || !hasLimit() || hasAnchoredLimit();
    }

    public String toJSON() {
        if (this.jsonSerialization == null) {
            try {
                this.jsonSerialization = JsonMapper.serializeJson(getWireProtocolParams());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return this.jsonSerialization;
    }

    public static QueryParams fromQueryObject(Map<String, Object> map) {
        QueryParams params = new QueryParams();
        params.limit = (Integer) map.get(LIMIT);
        if (map.containsKey(INDEX_START_VALUE)) {
            params.indexStartValue = normalizeValue(NodeUtilities.NodeFromJSON(map.get(INDEX_START_VALUE)));
            String indexStartName2 = (String) map.get(INDEX_START_NAME);
            if (indexStartName2 != null) {
                params.indexStartName = ChildKey.fromString(indexStartName2);
            }
        }
        if (map.containsKey(INDEX_END_VALUE)) {
            params.indexEndValue = normalizeValue(NodeUtilities.NodeFromJSON(map.get(INDEX_END_VALUE)));
            String indexEndName2 = (String) map.get(INDEX_END_NAME);
            if (indexEndName2 != null) {
                params.indexEndName = ChildKey.fromString(indexEndName2);
            }
        }
        String viewFrom2 = (String) map.get(VIEW_FROM);
        if (viewFrom2 != null) {
            params.viewFrom = viewFrom2.equals(LIMIT) ? ViewFrom.LEFT : ViewFrom.RIGHT;
        }
        String indexStr = (String) map.get(INDEX);
        if (indexStr != null) {
            params.index = Index.fromQueryDefinition(indexStr);
        }
        return params;
    }

    public NodeFilter getNodeFilter() {
        if (loadsAllData()) {
            return new IndexedFilter(getIndex());
        }
        if (hasLimit()) {
            return new LimitedFilter(this);
        }
        return new RangedFilter(this);
    }

    public String toString() {
        return getWireProtocolParams().toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QueryParams that = (QueryParams) o;
        if (this.limit == null ? that.limit != null : !this.limit.equals(that.limit)) {
            return false;
        }
        if (this.index == null ? that.index != null : !this.index.equals(that.index)) {
            return false;
        }
        if (this.indexEndName == null ? that.indexEndName != null : !this.indexEndName.equals(that.indexEndName)) {
            return false;
        }
        if (this.indexEndValue == null ? that.indexEndValue != null : !this.indexEndValue.equals(that.indexEndValue)) {
            return false;
        }
        if (this.indexStartName == null ? that.indexStartName != null : !this.indexStartName.equals(that.indexStartName)) {
            return false;
        }
        if (this.indexStartValue == null ? that.indexStartValue != null : !this.indexStartValue.equals(that.indexStartValue)) {
            return false;
        }
        if (isViewFromLeft() != that.isViewFromLeft()) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i = 0;
        int result = (((((((((((this.limit != null ? this.limit.intValue() : 0) * 31) + (isViewFromLeft() ? 1231 : 1237)) * 31) + (this.indexStartValue != null ? this.indexStartValue.hashCode() : 0)) * 31) + (this.indexStartName != null ? this.indexStartName.hashCode() : 0)) * 31) + (this.indexEndValue != null ? this.indexEndValue.hashCode() : 0)) * 31) + (this.indexEndName != null ? this.indexEndName.hashCode() : 0)) * 31;
        if (this.index != null) {
            i = this.index.hashCode();
        }
        return result + i;
    }

    private static Node normalizeValue(Node value) {
        if ((value instanceof StringNode) || (value instanceof BooleanNode) || (value instanceof DoubleNode) || (value instanceof EmptyNode)) {
            return value;
        }
        if (value instanceof LongNode) {
            return new DoubleNode(Double.valueOf(((Long) value.getValue()).doubleValue()), PriorityUtilities.NullPriority());
        }
        throw new IllegalStateException("Unexpected value passed to normalizeValue: " + value.getValue());
    }
}
