package me.changjie.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ChangJie on 2019-03-07.
 */
public class UserInfoConstant {

    public static Map<String, Integer> nameIdMap = new HashMap<>();
    public static Map<Integer, String> idNameMap = new HashMap<>();

    static {
        nameIdMap.put("常杰", 1);
        nameIdMap.put("陈志", 2);
        nameIdMap.put("刘爽", 3);

        Iterator<Map.Entry<String, Integer>> iterator = nameIdMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Integer> enrty = iterator.next();
            idNameMap.put(enrty.getValue(), enrty.getKey());
        }
    }


}
