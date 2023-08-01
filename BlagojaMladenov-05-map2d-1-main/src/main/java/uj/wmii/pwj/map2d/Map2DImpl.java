package uj.wmii.pwj.map2d;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Map2DImpl<R, C, V> implements Map2D<R, C, V> {

    private Map<R,Map<C,V>> helpingMap;

    public Map2DImpl() {
        helpingMap = new HashMap<>();
    }
    @Override
    public V put(R rowKey, C columnKey, V value) {
        if (rowKey == null || columnKey == null)
            throw new NullPointerException();
        Map<C, V> map;
        if (helpingMap.containsKey(rowKey)) {
            map = helpingMap.get(rowKey);
        } else {
            map = new HashMap<C, V>();
            helpingMap.put(rowKey, map);
        }
        return map.put(columnKey, value);
    }

    @Override
    public V get(R rowKey, C columnKey) {
        if (helpingMap.get(rowKey) != null && helpingMap.get(rowKey).containsKey(columnKey)) {
            return helpingMap.get(rowKey).get(columnKey);
        }
        return null;
    }

    @Override
    public V getOrDefault(R rowKey, C columnKey, V defaultValue) {
        if (helpingMap.get(rowKey) != null && helpingMap.get(rowKey).containsKey(columnKey)) {
            return helpingMap.get(rowKey).get(columnKey);
        }
        return defaultValue;
    }

    @Override
    public V remove(R rowKey, C columnKey) {
        if (helpingMap.get(rowKey) != null) {
            return helpingMap.get(rowKey).remove(columnKey);
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return helpingMap.isEmpty();
    }

    @Override
    public boolean nonEmpty() {
        return !isEmpty();
    }

    @Override
    public int size() {
       int counter = 0;
       for (R rows: helpingMap.keySet()) {
           counter += helpingMap.get(rows).size();
       }
       return counter;
    }

    @Override
    public void clear() {
        helpingMap.clear();
    }

    @Override
    public Map<C, V> rowView(R rowKey) {
        if (helpingMap.get(rowKey) == null) {
            return new HashMap<>();
        }
        return Collections.unmodifiableMap(helpingMap.get(rowKey));
    }

    @Override
    public Map<R, V> columnView(C columnKey) {
        Map <R,V> toReturn = new HashMap<>();
        for (Map.Entry<R,Map<C, V>> iter : helpingMap.entrySet()) {
            R row = iter.getKey();
            Map<C,V> copyingMap = iter.getValue();

            if (row == null)
                return Collections.unmodifiableMap(new HashMap<>());
            if (helpingMap.get(row) == copyingMap)
                toReturn.put(row, copyingMap.get(columnKey));
        }
        return Collections.unmodifiableMap(toReturn);
    }

    @Override
    public boolean hasValue(V value) {
        for (R rows: helpingMap.keySet()) {
            if (helpingMap.get(rows) != null && helpingMap.get(rows).containsValue(value))
                return true;
        }
        return false;
    }

    @Override
    public boolean hasKey(R rowKey, C columnKey) {
        if (helpingMap.get(rowKey) == null)
            return false;
        return helpingMap.get(rowKey).containsKey(columnKey);
    }

    @Override
    public boolean hasRow(R rowKey) {
        if (helpingMap == null)
            return false;
        return helpingMap.containsKey(rowKey);
    }

    @Override
    public boolean hasColumn(C columnKey) {
        for (R rows: helpingMap.keySet()) {
            if (helpingMap.get(rows).containsKey(columnKey))
                return true;
        }
        return false;
    }

    @Override
    public Map<R, Map<C, V>> rowMapView() {
        Map <R,Map<C,V>> toReturn = new HashMap<>();
        for (Map.Entry<R,Map<C, V>> iter : helpingMap.entrySet()) {
            if (helpingMap.get(iter.getKey()) == null)
                toReturn.put(iter.getKey(), new HashMap<>());
            toReturn.put(iter.getKey(), Map.copyOf(helpingMap.get(iter.getKey())));
        }

        return Collections.unmodifiableMap(toReturn);
    }

    @Override
    public Map<C, Map<R, V>> columnMapView() {
        Map <C,Map<R,V>> toReturn = new HashMap<>();
        for (Map.Entry<R,Map<C, V>> iter : helpingMap.entrySet()) {
            Map<C,V> myMap = helpingMap.get(iter.getKey());
            for (Map.Entry<C,V> secondIter : myMap.entrySet()) {
                toReturn.put(secondIter.getKey(), columnView(secondIter.getKey()));
            }
        }

        return Collections.unmodifiableMap(toReturn);
    }

    @Override
    public Map2D<R, C, V> fillMapFromRow(Map<? super C, ? super V> target, R rowKey) {
        target.putAll(rowView(rowKey));
        return this;
    }

    @Override
    public Map2D<R, C, V> fillMapFromColumn(Map<? super R, ? super V> target, C columnKey) {
        target.putAll(columnView(columnKey));
        return this;
    }

    @Override
    public Map2D<R, C, V> putAll(Map2D<? extends R, ? extends C, ? extends V> source) {
        Map<? extends R, ? extends Map<? extends C, ? extends V>> help = source.rowMapView();
        for (R rows : help.keySet()) {
            putAllToRow(help.get(rows), rows);
        }
        return this;
    }

    @Override
    public Map2D<R, C, V> putAllToRow(Map<? extends C, ? extends V> source, R rowKey) {
        for (Map.Entry<? extends C,? extends V> iter : source.entrySet()) {
            C myKey = iter.getKey();
            V myValue = iter.getValue();
            this.put(rowKey, myKey, myValue);
        }
        return this;
    }

    @Override
    public Map2D<R, C, V> putAllToColumn(Map<? extends R, ? extends V> source, C columnKey) {
        for (Map.Entry<? extends R,? extends V> iter : source.entrySet()) {
            R myKey = iter.getKey();
            V myValue = iter.getValue();
            this.put(myKey, columnKey, myValue);
        }
        return this;
    }

    @Override
    public <R2, C2, V2> Map2D<R2, C2, V2> copyWithConversion(Function<? super R, ? extends R2> rowFunction,
                                                             Function<? super C, ? extends C2> columnFunction,
                                                             Function<? super V, ? extends V2> valueFunction) {
        Map2D toReturn = new Map2DImpl<R2, C2, V2>();
        for (Map.Entry<R,Map<C,V>> iter : helpingMap.entrySet()) {
            Map<C,V> castingMap;
            castingMap = iter.getValue();
            for (Map.Entry<C,V> secondIter : castingMap.entrySet()) {
                toReturn.put(rowFunction.apply(iter.getKey()),
                        columnFunction.apply(secondIter.getKey()),
                        valueFunction.apply(secondIter.getValue()));
            }
        }
        return toReturn;
    }
}
