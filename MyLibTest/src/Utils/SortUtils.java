package Utils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;

import Bean.PostData;

/***
 * @Class Name : SortUtils
 * @Description : ���� ���� Ŭ����
 * @since 2015. 6. 23.
 * @version 1.0
 */
public class SortUtils {

    private final static  Collator mCollator = Collator.getInstance();

    public final static Comparator<PostData> comparetor = new Comparator<PostData>() {

        @Override
        public int compare(PostData left, PostData right) {
            //���� ����
            return left.getmNo() < right.getmNo() ? 1 : -1;
        }
    };


}
