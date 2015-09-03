package Listener;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : IBLConnectLinstener
 * @Description : 블루투스 통신 이벤트 리스너
 * @since 2015-07-07.
 */
public interface IBLConnectLinstener {
    public void onReceiveAction(int what, Object obj);

    /**
     * 실패
     */
    public final int RECEIVE_ERROR = -1;

    /**
     * 리시브 성공
     */
    public final int RECEIVE_OK = 0;

    /**
     *  전송 성공
     */
    public final int SEND_OK = 1;

    /**
     * 에러
     */
    public final int MESSAGE_ERROR = 2;

}