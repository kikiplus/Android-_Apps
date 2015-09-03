package Utils;

import java.text.Collator;
import java.util.Comparator;

import View.Bean.PostData;

/***
 * @Class Name : SortUtils
 * @Description : 정렬 관련 클래스
 * @since 2015. 6. 23.
 * @version 1.0
 */
public class SortUtils {

    private final static  Collator mCollator = Collator.getInstance();

    public final static Comparator<PostData> comparetor = new Comparator<PostData>() {

        @Override
        public int compare(PostData left, PostData right) {
            //역순 정렬
            return left.getmNo() < right.getmNo() ? 1 : -1;
        }
    };

}
