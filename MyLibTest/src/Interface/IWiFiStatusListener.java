package Interface;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : IWiFiStatusListener
 * @Description : 와이파이 상태 변경 리스너
 * @since 2015-07-09.
 */
public interface IWiFiStatusListener {


    public void OnChangedStatus(int what, Object obj);

    /**
     * 연결
     */
    public final int NETWORK_STATE_CONNECTED = 1;

    /**
     * 연결 안됨
     */
    public final int NETWORK_STATE_DISCONNECTED = 2;


    /**
     * 연결 시도
     */
    public final int NETWORK_STATE_CONNECTING = 3;


    /**
     * 연결 시도 안되는중
     */
    public final int NETWORK_STATE_DISCONNECTING = 4;

}
