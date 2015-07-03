package Utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : ByteUtils
 * @Description : ����Ʈ ��ȯ ��ƿ Ŭ����
 * @since 2015-07-03.
 */
public class ByteUtils {

    /**
     *  ��Ʈ���� ����Ʈ �迭�� ��ȯ�ϴ� �޼ҵ�
     * @param map ��Ʈ��
     * @return ����Ʈ �迭
     */
    public static byte[] getByteArrayFromBitmap(Bitmap map) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        map.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

}
