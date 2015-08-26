package Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : SharedPreferenceUtils.java
 * @Description :
 * @Description : �����۷��� ��ƿ Ŭ����
 * @since 2014.08.01
 */
public class SharedPreferenceUtils {

    /**
     * �����۷��� integer Ÿ��
     */
    public static final int SHARED_PREF_VALUE_INTEGER = 0;

    /**
     * �����۷��� float Ÿ��
     */
    public static final int SHARED_PREF_VALUE_FLOAT = 1;

    /**
     * �����۷��� long Ÿ��
     */
    public static final int SHARED_PREF_VALUE_LONG = 2;

    /**
     * �����۷��� string Ÿ��
     */
    public static final int SHARED_PREF_VALUE_STRING = 3;

    /**
     * �����۷��� boolean Ÿ��
     */
    public static final int SHARED_PREF_VALUE_BOOLEAN = 4;


    /***
     * �����۷��� ���� �޼ҵ�
     *
     * @param context ���ؽ�Ʈ
     * @param key     ������ Ű��
     * @param value   ������ ��
     */
    public static void write(Context context, String key, Object value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ContextUtils.KEY_PREFER_NAME, Context.MODE_PRIVATE).edit();
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value == null) {
            editor.putString(key, null);
        } else {
            editor.putBoolean(key, (Boolean) value);
        }
        editor.commit();
    }

    /**
     * �����۷��� �б� �޼ҵ�
     *
     * @param context ���ؽ�Ʈ
     * @param key     �о�� Ű��
     * @param type    �о�� ���� Ÿ��
     * @return �����۷��� ������ ��
     */
    public static Object read(Context context, String key, int type) {
        SharedPreferences sharedPref = context.getSharedPreferences(ContextUtils.KEY_PREFER_NAME, Context.MODE_PRIVATE);
        switch (type) {
            case SHARED_PREF_VALUE_INTEGER:
                return sharedPref.getInt(key, -1);
            case SHARED_PREF_VALUE_FLOAT:
                return sharedPref.getFloat(key, -1);
            case SHARED_PREF_VALUE_LONG:
                return sharedPref.getLong(key, -1);
            case SHARED_PREF_VALUE_STRING:
                return sharedPref.getString(key, null);
            case SHARED_PREF_VALUE_BOOLEAN:
                return sharedPref.getBoolean(key, false);
        }
        return null;
    }
}