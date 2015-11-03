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
        KLog.d(context.getClass().getSimpleName(), context.getClass()+ "@@ ==============================================");
        KLog.d(context.getClass().getSimpleName(),context.getClass()+ "@@ 힙사이즈 : " + Debug.getNativeHeapSize());
        KLog.d(context.getClass().getSimpleName(), context.getClass()+ "@@ 힙 Free 사이즈: " + Debug.getNativeHeapFreeSize());
        KLog.d(context.getClass().getSimpleName(), context.getClass()+ "@@ 힙에 할당된 사이즈 : " + Debug.getNativeHeapAllocatedSize());
        KLog.d(context.getClass().getSimpleName(),context.getClass()+ "@@ ==============================================");
    }
}
