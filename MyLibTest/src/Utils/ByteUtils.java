package Utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : ByteUtils
 * @Description : 바이트 변환 유틸 클래스
 * @since 2015-07-03.
 */
public class ByteUtils {

    /**
     *  비트맵을 바이트 배열로 변환하는 메소드
     * @param map 비트맵
     * @return 바이트 배열
     */
    public static byte[] getByteArrayFromBitmap(Bitmap map) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        map.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

}
