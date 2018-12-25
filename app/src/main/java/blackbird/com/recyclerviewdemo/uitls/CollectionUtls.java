package blackbird.com.recyclerviewdemo.uitls;

import java.util.Collection;

public class CollectionUtls {

    public static boolean notNull(Collection c){
        if(c==null||c.isEmpty())
            return false;
        else {
            return true;
        }
    }
}
