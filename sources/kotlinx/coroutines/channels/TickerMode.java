package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lkotlinx/coroutines/channels/TickerMode;", "", "<init>", "(Ljava/lang/String;I)V", "FIXED_PERIOD", "FIXED_DELAY", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: TickerChannels.kt */
public enum TickerMode {
    FIXED_PERIOD,
    FIXED_DELAY;

    static {
        $ENTRIES = EnumEntriesKt.enumEntries((E[]) (Enum[]) $VALUES);
    }

    public static EnumEntries<TickerMode> getEntries() {
        return $ENTRIES;
    }
}
