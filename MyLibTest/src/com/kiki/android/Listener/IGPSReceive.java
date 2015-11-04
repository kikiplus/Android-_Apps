package com.kiki.android.Listener;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : IGPSReceive
 * @Description : GPS 정보 받기
 * @since 2015-10-29.
 */
public interface IGPSReceive {

    /**
     * 성공
     */
    public static final int RECEIVE_OK = 0;

    /**
     * 실패
     */
    public static final int RECEIVE_FAIL = -1;

    /**
     * 위치 변경
     */
    public static final int RECEIVE_UPDATE = 1;

    /***
     * 리비스 콜백 메소드
     */
    public void onGpsReceive(int type, Object obj);
}
