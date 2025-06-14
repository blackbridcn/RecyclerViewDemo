package blackbird.com.recyclerviewdemo.uitls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionUtls {

    public static boolean notNull(Collection c) {
        if (c == null || c.isEmpty())
            return false;
        else {
            return true;
        }
    }

    public static <T> List<T> createList(T... t) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < t.length; i++) {
            list.add(t[i]);
        }
        return list;
    }
}
