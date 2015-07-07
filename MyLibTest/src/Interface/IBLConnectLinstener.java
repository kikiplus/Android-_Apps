package Interface;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : IBLConnectLinstener
 * @Description : 블루투스 통신 관련 이벤트 리스너
 * @since 2015-07-07.
 */
public interface IBLConnectLinstener {
    public void onReceiveAction(int what, Object obj);

    /**
     * 결과 메시지 받기
     */
    public final  int RECEIVE_MESSAGE = 0;
    /**
     * 실패
     */
    public final int RECEIVE_ERROR = -1;
}
