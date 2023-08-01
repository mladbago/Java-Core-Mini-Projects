package uj.wmii.pwj.collections;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


public class ListMerger {

    public static List<Object> mergeLists(List<?> l1, List<?> l2) {
        List<Object>mergedList = new ArrayList<>();

        int i = 0;
        int j = 0;
        if (l1 == null)
            return mergedList;
        if (l2 == null)
            return mergedList;
        while (i < l1.size() && j < l2.size()) {
            mergedList.add(l1.get(i));
            mergedList.add(l2.get(j));
            i ++;
            j ++;
        }
        while (i < l1.size()) {
            mergedList.add(l1.get(i));
            i ++;
        }
        while (j < l2.size()) {
            mergedList.add(l2.get(j));
            j ++;
        }

        return Collections.unmodifiableList(mergedList);
    }

}
