package Utils;

import android.content.Context;
import android.os.Debug;
import android.util.Log;

import com.test.mihye.R;

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
        Log.d(context.getString(R.string.main_log), "@@ ==============================================");
        Log.d(context.getString(R.string.main_log), "@@ 힙사이즈 : " + Debug.getNativeHeapSize());
        Log.d(context.getString(R.string.main_log), "@@ 힙 Free 사이즈: " + Debug.getNativeHeapFreeSize());
        Log.d(context.getString(R.string.main_log), "@@ 힙에 할당된 사이즈 : " + Debug.getNativeHeapAllocatedSize());
        Log.d(context.getString(R.string.main_log), "@@ ==============================================");
    }
}
