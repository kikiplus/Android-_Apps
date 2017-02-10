package com.kikiplus.android.Listener;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : IHttpReceive
 * @Description : HTTP 통신 결과
 * @since 2015-06-30.
 */
public interface IHttpReceive {

    /**
     * 성공
     */
    public static final int HTTP_OK = 0;

    /**
     * 실패
     */
    public static final int HTTP_FAIL = -1;
    /***
     * 리비스 콜백 메소드
     */
    public void onHttpReceive(int type, Object obj);
}
