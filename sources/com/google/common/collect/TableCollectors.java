package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.firebase.firestore.model.Values;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

@ElementTypesAreNonnullByDefault
final class TableCollectors {
    static <T, R, C, V> Collector<T, ?, ImmutableTable<R, C, V>> toImmutableTable(Function<? super T, ? extends R> rowFunction, Function<? super T, ? extends C> columnFunction, Function<? super T, ? extends V> valueFunction) {
        Preconditions.checkNotNull(rowFunction, "rowFunction");
        Preconditions.checkNotNull(columnFunction, "columnFunction");
        Preconditions.checkNotNull(valueFunction, "valueFunction");
        return Collector.of(new TableCollectors$$ExternalSyntheticLambda8(), new TableCollectors$$ExternalSyntheticLambda9(rowFunction, columnFunction, valueFunction), new TableCollectors$$ExternalSyntheticLambda10(), new TableCollectors$$ExternalSyntheticLambda1(), new Collector.Characteristics[0]);
    }

    static <T, R, C, V> Collector<T, ?, ImmutableTable<R, C, V>> toImmutableTable(Function<? super T, ? extends R> rowFunction, Function<? super T, ? extends C> columnFunction, Function<? super T, ? extends V> valueFunction, BinaryOperator<V> mergeFunction) {
        Preconditions.checkNotNull(rowFunction, "rowFunction");
        Preconditions.checkNotNull(columnFunction, "columnFunction");
        Preconditions.checkNotNull(valueFunction, "valueFunction");
        Preconditions.checkNotNull(mergeFunction, "mergeFunction");
        return Collector.of(new TableCollectors$$ExternalSyntheticLambda4(), new TableCollectors$$ExternalSyntheticLambda5(rowFunction, columnFunction, valueFunction, mergeFunction), new TableCollectors$$ExternalSyntheticLambda6(mergeFunction), new TableCollectors$$ExternalSyntheticLambda7(), new Collector.Characteristics[0]);
    }

    static /* synthetic */ ImmutableTableCollectorState lambda$toImmutableTable$1() {
        return new ImmutableTableCollectorState();
    }

    static <T, R, C, V, I extends Table<R, C, V>> Collector<T, ?, I> toTable(Function<? super T, ? extends R> rowFunction, Function<? super T, ? extends C> columnFunction, Function<? super T, ? extends V> valueFunction, Supplier<I> tableSupplier) {
        return toTable(rowFunction, columnFunction, valueFunction, new TableCollectors$$ExternalSyntheticLambda0(), tableSupplier);
    }

    static /* synthetic */ Object lambda$toTable$5(Object v1, Object v2) {
        throw new IllegalStateException("Conflicting values " + v1 + " and " + v2);
    }

    static <T, R, C, V, I extends Table<R, C, V>> Collector<T, ?, I> toTable(Function<? super T, ? extends R> rowFunction, Function<? super T, ? extends C> columnFunction, Function<? super T, ? extends V> valueFunction, BinaryOperator<V> mergeFunction, Supplier<I> tableSupplier) {
        Preconditions.checkNotNull(rowFunction);
        Preconditions.checkNotNull(columnFunction);
        Preconditions.checkNotNull(valueFunction);
        Preconditions.checkNotNull(mergeFunction);
        Preconditions.checkNotNull(tableSupplier);
        return Collector.of(tableSupplier, new TableCollectors$$ExternalSyntheticLambda2(rowFunction, columnFunction, valueFunction, mergeFunction), new TableCollectors$$ExternalSyntheticLambda3(mergeFunction), new Collector.Characteristics[0]);
    }

    static /* synthetic */ Table lambda$toTable$7(BinaryOperator mergeFunction, Table table1, Table table2) {
        for (Table.Cell<R, C, V> cell2 : table2.cellSet()) {
            mergeTables(table1, cell2.getRowKey(), cell2.getColumnKey(), cell2.getValue(), mergeFunction);
        }
        return table1;
    }

    private static final class ImmutableTableCollectorState<R, C, V> {
        final List<MutableCell<R, C, V>> insertionOrder;
        final Table<R, C, MutableCell<R, C, V>> table;

        private ImmutableTableCollectorState() {
            this.insertionOrder = new ArrayList();
            this.table = HashBasedTable.create();
        }

        /* access modifiers changed from: package-private */
        public void put(R row, C column, V value, BinaryOperator<V> merger) {
            MutableCell<R, C, V> oldCell = this.table.get(row, column);
            if (oldCell == null) {
                MutableCell<R, C, V> cell = new MutableCell<>(row, column, value);
                this.insertionOrder.add(cell);
                this.table.put(row, column, cell);
                return;
            }
            oldCell.merge(value, merger);
        }

        /* access modifiers changed from: package-private */
        public ImmutableTableCollectorState<R, C, V> combine(ImmutableTableCollectorState<R, C, V> other, BinaryOperator<V> merger) {
            for (MutableCell<R, C, V> cell : other.insertionOrder) {
                put(cell.getRowKey(), cell.getColumnKey(), cell.getValue(), merger);
            }
            return this;
        }

        /* access modifiers changed from: package-private */
        public ImmutableTable<R, C, V> toTable() {
            return ImmutableTable.copyOf(this.insertionOrder);
        }
    }

    private static final class MutableCell<R, C, V> extends Tables.AbstractCell<R, C, V> {
        private final C column;
        private final R row;
        private V value;

        MutableCell(R row2, C column2, V value2) {
            this.row = Preconditions.checkNotNull(row2, "row");
            this.column = Preconditions.checkNotNull(column2, "column");
            this.value = Preconditions.checkNotNull(value2, Values.VECTOR_MAP_VECTORS_KEY);
        }

        public R getRowKey() {
            return this.row;
        }

        public C getColumnKey() {
            return this.column;
        }

        public V getValue() {
            return this.value;
        }

        /* access modifiers changed from: package-private */
        public void merge(V value2, BinaryOperator<V> mergeFunction) {
            Preconditions.checkNotNull(value2, Values.VECTOR_MAP_VECTORS_KEY);
            this.value = Preconditions.checkNotNull(mergeFunction.apply(this.value, value2), "mergeFunction.apply");
        }
    }

    /* access modifiers changed from: private */
    public static <R, C, V> void mergeTables(Table<R, C, V> table, @ParametricNullness R row, @ParametricNullness C column, @ParametricNullness V value, BinaryOperator<V> mergeFunction) {
        Preconditions.checkNotNull(value);
        V oldValue = table.get(row, column);
        if (oldValue == null) {
            table.put(row, column, value);
            return;
        }
        V newValue = mergeFunction.apply(oldValue, value);
        if (newValue == null) {
            table.remove(row, column);
        } else {
            table.put(row, column, newValue);
        }
    }

    private TableCollectors() {
    }
}
