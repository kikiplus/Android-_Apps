package com.kiki.android.Utils;

import android.content.Context;
import android.os.Debug;
import android.util.Log;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name :MemoryUtils
 * @Description : 메모리 관련 유틸
 * @since 2015-06-30.
 */
public class MemoryUtils {

    /***
     * 힙 메모리 출력 메소드
     * @param context 컨텍스트
     */
    public static void printMemory(Context context){
        Log.d(com.kiki.android.Utils.conf.Log.LOG_NAME, context.getClass()+ "@@ ==============================================");
        Log.d(com.kiki.android.Utils.conf.Log.LOG_NAME, context.getClass()+ "@@ 힙사이즈 : " + Debug.getNativeHeapSize());
        Log.d(com.kiki.android.Utils.conf.Log.LOG_NAME, context.getClass()+ "@@ 힙 Free 사이즈: " + Debug.getNativeHeapFreeSize());
        Log.d(com.kiki.android.Utils.conf.Log.LOG_NAME, context.getClass()+ "@@ 힙에 할당된 사이즈 : " + Debug.getNativeHeapAllocatedSize());
        Log.d(com.kiki.android.Utils.conf.Log.LOG_NAME, context.getClass()+ "@@ ==============================================");
    }
}
