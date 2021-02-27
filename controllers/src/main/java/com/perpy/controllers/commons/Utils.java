package com.perpy.controllers.commons;

import java.util.List;

public class Utils {
    public static boolean compareLists(List<? extends ProcessedMarvelItemBase> first, List<? extends  ProcessedMarvelItemBase> second) {
        if(first==null && second==null) return true;
        else if(first==null || second==null) return false;
        else if(first.size()!=second.size()) return false;
        else {
            boolean ret = true;
            for(int i = 0; i < first.size(); i++) {
                if(first.get(i).id!=second.get(i).id) {
                    ret = false;
                    break;
                }
            }

            return ret;
        }
    }
}
