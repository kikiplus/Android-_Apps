package com.kikiplus.common.utils;

import com.kikiplus.View.Bean.PostData;

import java.text.Collator;
import java.util.Comparator;
import java.util.Map;


/***
 * @version 1.0
 * @Class Name : SortUtils
 * @Description : 정렬 관련 클래스
 * @since 2015. 6. 23.
 */
public class SortUtils {

    private final static Collator mCollator = Collator.getInstance();

    public final static Comparator<PostData> comparetor = new Comparator<PostData>() {

        @Override
        public int compare(PostData left, PostData right) {
            //역순 정렬
            return left.getmNo() < right.getmNo() ? 1 : -1;
        }
    };

    /**
     * 해쉬 맵 정렬할 변수명
     */
    public static String mSortPivot = "";

    /**
     * 해쉬 맵 정렬 compator
     */
    public final static Comparator<Map<String, String>> comparetorHashMap = new Comparator<Map<String, String>>() {

        @Override
        public int compare(Map<String, String> left, Map<String, String> right) {
            //역순 정렬
            int lhsValue = Integer.parseInt(left.get(mSortPivot));
            int rhsValue = Integer.parseInt(right.get(mSortPivot));
            return lhsValue > rhsValue ? 1 : -1;
        }
    };

}
