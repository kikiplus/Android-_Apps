package Event;

import java.util.Objects;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : BluetoothEventListener
 * @Description : 블루투스 이벤트 리스너
 * @since 2015-06-25.
 */
public interface OnBluetoothEventListener {
    /**
     * 이벤트 메시지 처리 메소드
     */
    public void onAction(int what, Object obj);

    /**
     * 등록된 기기 보기
     */
    public static final int SET_ADDED_IST = 100;
    /**
     * 주변 기기 보기
     */
    public static final int SEARCH_DEVICES_LIST = 101;

    /**
     * 주변 기기 검색 완료
     */
    public static final int COMPLE_SEARCH_DEVICES = 102;

    /**
     * 블루투스 상태 꺼짐
     */
    public static final int BLUETOOTH_OFF = 103;

    /**
     * 블루투스 상태 켜짐
     */
    public static final int BLUETOOTH_ON = 104;
}
