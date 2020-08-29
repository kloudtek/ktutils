package com.aeontronix.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class JsonUtils {
    private static final HashSet<Class<?>> OBJ2JSON_PASSTHROUGH = new HashSet<>(Arrays.asList(String.class,
            Number.class, Boolean.class, Date.class));
    private static final HashSet<String> OBJ2JSON_METHODBLACKLIST = new HashSet<>(Arrays.asList("getClass"));

    public static Object toJsonObject(Object object) {
        return toJsonObject(object, false);
    }

    /**
     * <p>Convert a java object into a json-friendly java object structure.</p>
     *
     * @param object          Object
     * @param parseJsonString If true, this will convert any string that is known to be a json string into a java json structure (only works with mulesoft's TypedValue currently)
     * @return json-friendly java object
     */
    public static Object toJsonObject(Object object, boolean parseJsonString) {
        LinkedList<Object> processed = new LinkedList<>();
        return toJsonObject(object, parseJsonString, processed);
    }

    @SuppressWarnings("unchecked")
    private static Object toJsonObject(Object object, boolean parseJsonString, LinkedList<Object> processed) {
        object = deOptionalize(object);
        if (object == null) {
            return null;
        }
        processed.add(object);
        if (isPassthrough(object)) {
            return object;
        }
        Class<?> objCl = object.getClass();
        if (objCl.isArray()) {
            if( objCl.getName().equals("[B") ) {
                return StringUtils.base64Encode((byte[]) object);
            }
            ArrayList<Object> newArr = new ArrayList<>();
            for (Object o : (Object[]) object) {
                if (isNotProcessed(processed, o)) {
                    newArr.add(toJsonObject(o,parseJsonString,processed));
                }
            }
            return newArr;
        }
        if( object instanceof Map ) {
            HashMap<String, Object> newMap = new HashMap<>();
            Set<Map.Entry<Object,Object>> set = ((Map) object).entrySet();
            for (Map.Entry<Object, Object> entry : set) {
                String key = entry.getKey().toString();
                Object v = deOptionalize(entry.getValue());
                if( v == null ) {
                    newMap.put(key,null);
                } else if (isNotProcessed(processed, v)) {
                    newMap.put(key,toJsonObject(v,parseJsonString,processed));
                }
            }
            return newMap;
        }
        HashMap<String, Object> objMap = new HashMap<>();
        for (Method method : objCl.getMethods()) {
            final String methodName = method.getName();
            String key = null;
            if (!OBJ2JSON_METHODBLACKLIST.contains(methodName) && method.getParameters().length == 0 && !method.getReturnType().getName().equals("void")) {
                if (methodName.startsWith("get") && methodName.length() > 3) {
                    key = getJBFieldName(methodName, 3);
                } else if (methodName.startsWith("is") && methodName.length() > 3) {
                    key = getJBFieldName(methodName, 2);
                }
                if (key != null) {
                    try {
                        Object value = method.invoke(object);
                        value = deOptionalize(value);
                        if (isNotProcessed(processed, value)) {
                            objMap.put(key, toJsonObject(value, parseJsonString, processed));
                        }
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        //
                    }
                }
            }
        }
        return objMap;
    }

    private static boolean isPassthrough(Object obj) {
        Class<?> cl = obj.getClass();
        if (cl.isPrimitive() || cl.isEnum()) {
            return true;
        }
        for (Class<?> pcl : OBJ2JSON_PASSTHROUGH) {
            if (pcl.isAssignableFrom(cl)) {
                return true;
            }
        }
        return false;
    }

    private static Object deOptionalize(Object object) {
        if (object instanceof Optional<?>) {
            object = ((Optional) object).isPresent() ? ((Optional) object).get() : null;
        }
        return object;
    }

    private static String getJBFieldName(String methodName, int index) {
        char c = Character.toLowerCase(methodName.charAt(index));
        if (methodName.length() > (index + 1)) {
            return c + methodName.substring(index + 1);
        } else {
            return new String(new char[]{c});
        }
    }

    private static boolean isNotProcessed(LinkedList<Object> processed, Object value) {
        for (Object p : processed) {
            if (p == value) {
                return false;
            }
        }
        return true;
    }
//    /**
//     * Convert an object into a structure designed to serializable to json
//     * @param object object
//     * @return Json object
//     * @throws InvocationTargetException
//     * @throws IllegalAccessException
//     */
//    public static Object objectToJson(Object object) throws InvocationTargetException, IllegalAccessException {
//        LinkedList<Object> processed = new LinkedList<>();
//        return objectToMapInternal(object,processed);
//    }
//
//    private static Map<String, Object> objectToMapInternal(Object object, LinkedList<Object> processed) throws IllegalAccessException, InvocationTargetException {
//        if (object == null) {
//            return null;
//        }
//        processed.add(object);
//        Map<String, Object> map = new HashMap<>();
//        for (Method method : object.getClass().getMethods()) {
//            final String methodName = method.getName();
//            String key = null;
//            if (! OBJ2MAP_METHODBLACKLIST.contains(methodName) && method.getParameters().length == 0 && !method.getReturnType().getName().equals("void")) {
//                if (methodName.startsWith("get") && methodName.length() > 3) {
//                    key = getJBFieldName(methodName, 3);
//                } else if (methodName.startsWith("is") && methodName.length() > 3) {
//                    key = getJBFieldName(methodName, 2);
//                }
//                if (key != null) {
//                    Object value = method.invoke(object);
//                    if( value instanceof Optional ) {
//                        if( ((Optional) value).isPresent()) {
//                            value = ((Optional) value).get();
//                        } else {
//                            value = null;
//                        }
//                    }
//                    if( value != null ) {
//                        ObjectToMapObjectType type = objectToMapConvertObjectGetType(value, processed);
//                        if( type != null ) {
//                            switch (type) {
//                                case PASSTHROUGH:
//                                    map.put(key, value);
//                                    break;
//                                case OBJECT:
//                                    map.put(key,objectToMapInternal(value, processed));
//                                    break;
//                            }
//                        } else {
//                            map.put(key, null);
//                        }
//                    } else {
//                        map.put(key, null);
//                    }
//                }
//            }
//        }
//        return map;
//    }
//
//
//    private static ObjectToMapObjectType objectToMapConvertObjectGetType(Object val, LinkedList<Object> processed) {
//        if( val == null ) {
//            return null;
//        }
//        Class<?> cl = val.getClass();
//        for (Class<?> pt : OBJ2MAP_PASSTHROUGH) {
//            if( pt.isAssignableFrom(cl)) {
//                return ObjectToMapObjectType.PASSTHROUGH;
//            }
//        }
//        if (cl.isPrimitive() || cl.isEnum() || cl.isArray() ) {
//            return ObjectToMapObjectType.PASSTHROUGH;
//        } else {
//            if( ! isSame(processed, val) ) {
//                return ObjectToMapObjectType.OBJECT;
//            } else {
//                return ObjectToMapObjectType.PROCESSED;
//            }
//        }
//    }


}
