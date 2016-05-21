package entity;

import android.text.TextUtils;

import com.handmark.pulltorefresh.library.CArrayList;
import com.handmark.pulltorefresh.library.StringUtil;
import com.raoleqing.yangmatou.view.ValueEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by ybin on 2016/5/11.
 */
public class EntityUtil {
    public static Object instanceEntity(String className, JSONObject json) {
        Object entity = null;
        try {
            Class clazz = Class.forName(className);
            entity = clazz.newInstance();//通过反射创建对象
            Field[] fields = entity.getClass().getFields();
            String k;
            for (Field f : fields) {
                f.setAccessible(true);
                k = f.getName();
                if (!Character.isLowerCase(k.toCharArray()[0])) continue;
                if (json.has(k)) {
                    if (f.getType().equals(String.class)) {
                        f.set(entity, json.isNull(k) ? "" : json.optString(k));
                    } else if (f.getType().equals(int.class) || f.getType().equals(Integer.class)) {
                        f.set(entity, json.optInt(k));
                    } else if (f.getType().equals(long.class) || f.getType().equals(Long.class)) {
                        f.set(entity, json.optLong(k));
                    } else if (f.getType().equals(float.class) || f.getType().equals(Float.class)) {
                        f.set(entity, StringUtil.stringToFloat(json.getString(k)));
                    } else if (f.getType().equals(double.class) || f.getType().equals(Double.class)) {
                        f.set(entity, json.optDouble(k));
                    } else if (f.getType().equals(boolean.class) || f.getType().equals(Boolean.class)) {
                        f.set(entity, json.optBoolean(k));
                    } else if (List.class.isAssignableFrom(f.getType()) && !json.isNull(k)) {
                        String strClass = f.getGenericType().toString().split("<")[1];
                        Class t = Class.forName(strClass.substring(0, strClass.length() - 1));
                        f.set(entity, EntityUtil.createEntityList(json.getJSONArray(k), t));
                    }  else if (CBaseEntity.class.isAssignableFrom(f.getType())) {
                        f.set(entity, EntityUtil.createEntity(json.getJSONObject(k), f.getType()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    public static <T> T createEntity(JSONObject jsonObject, Class<T> clazz) {
        return (T) EntityUtil.instanceEntity(clazz.getName(), jsonObject);
    }

    public static <T> T createEntity(String jsonString, Class<T> clazz) {
        try {
            if (TextUtils.isEmpty(jsonString)) return null;
            JSONObject json = new JSONObject(jsonString);
            return createEntity(json, clazz);
        } catch (Exception e) {
            try {
                return clazz.newInstance();
            } catch (Exception e1) {
                return null;
            }
        }
    }

    public static <T> T createEntity(String json, T obj, Class<T> clazz) {
        if (obj != null) return obj;
        obj = EntityUtil.createEntity(json, clazz);
        if (obj == null) {
            try {
                obj = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public static <T> CArrayList<T> createEntityList(String json, Class<T> clazz) {
        return createEntityList(null, clazz, json);
    }


    public static <T> CArrayList<T> createEntityList(CArrayList<T> list, Class<T> clazz, String json) {
        if (list != null) return list;
        CArrayList<T> tempList = new CArrayList<>();
        try {
            tempList = createEntityList(new JSONArray(json), clazz);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tempList;
    }

    public static <T> CArrayList<T> createEntityList(JSONArray array, Class<T> clazz) {
        try {
            CArrayList<T> list = new CArrayList<>();
            for (int i = 0, length = array.length(); i < length; i++) {
                list.add(createEntity(array.getJSONObject(i), clazz));
            }
            return list;
        } catch (JSONException e) {
            return new CArrayList<>();
        }
    }


    public static <T> List<T> createKeyValueEntityList(JSONObject json, JSONArray array, Class clazz) {
        try {
            ValueEntity entity;
            List<T> list = new ArrayList<>();
            for (int i = 0, length = array.length(); i < length; i++) {
                entity = (ValueEntity) clazz.newInstance();
                String key = array.getString(i);
                String value = json.getString(key);
                entity.setKey(key);
                entity.setValue(value);
                list.add((T) entity);
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> List<T> createKeyValueEntityList(String jsonStr, Class clazz, List<T> dataArr) {
        if (dataArr != null) return dataArr;
        List<T> newDataArr = new ArrayList<>();
        if (TextUtils.isEmpty(jsonStr)) return newDataArr;
        try {
            JSONObject json = new JSONObject(jsonStr);
            JSONArray array = json.names();
            newDataArr = createKeyValueEntityList(json, array, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDataArr;
    }

    public static <T> List<T> createKeyValueEntityList(String jsonStr, Class clazz) {
        return createKeyValueEntityList(jsonStr, clazz, null);
    }

    public static <T> List<T> hashMapToList(HashMap<Long, T> hashMap) {
        List<T> list = new ArrayList<>();
        Set<Long> keySet = hashMap.keySet();
        for (long k : keySet) {
            list.add(hashMap.get(k));
        }
        return list;
    }

    public static Object copyEntity(Object obj, Object newObj) {
        return copyEntity(obj, newObj, true);
    }

    private static Object copyEntity(Object obj, Object newObj, boolean backup) {
        Object backupObj = null;
        try {
            if (backup) {
                Class clazz = Class.forName(obj.getClass().getName());
                backupObj = clazz.newInstance();
                backupObj = copyEntity(backupObj, obj, false);
                if (backupObj == null) return obj;
            }
            Field[] fields = obj.getClass().getFields();
            for (Field f : fields) {
                f.setAccessible(true);
                f.set(obj, newObj.getClass().getField(f.getName()).get(newObj));
            }
            return obj;
        } catch (Exception e) {
            if (backup)
                return backupObj;
            else
                return null;
        }
    }
}
