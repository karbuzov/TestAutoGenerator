package com.test.utils;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassUtils {
    public static boolean isPrimitive(Class cls) {
        if (cls.isPrimitive()) {
            return true;
        }
        if (isValidPrimitiveType(cls)) {
            return true;
        }
        return false;
    }

    public static boolean isCollectionOrMap(Class<?> cls){
        if (Set.class.isAssignableFrom(cls)){
            return true;
        }
        if (List.class.isAssignableFrom(cls)){
            return true;
        }
        if (Array.class.isAssignableFrom(cls)){
            return true;
        }
        if (Map.class.isAssignableFrom(cls)) {
            return true;
        }
        return false;
    }

    public static boolean isMap(Class<?> cls) {
        if (Map.class.isAssignableFrom(cls)) {
            return true;
        }
        return false;
    }

    public static boolean isValidPrimitiveType(Class<?> retType)
    {
        if (retType.isPrimitive() && retType != void.class) return true;
        if (Number.class.isAssignableFrom(retType)) return true;
//        if (AbstractCode.class.isAssignableFrom(retType)) return true;
        if (Boolean.class == retType) return true;
        if (Character.class == retType) return true;
        if (String.class == retType) return true;
//        if (Date.class.isAssignableFrom(retType)) return true;
        if (byte[].class.isAssignableFrom(retType)) return true;
        if (Enum.class.isAssignableFrom(retType)) return true;
        return false;
    }
}
