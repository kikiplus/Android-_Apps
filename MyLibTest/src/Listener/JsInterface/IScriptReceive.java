package Listener.JsInterface;

/**
 * @author grape girl
 * @version 1.0
 * @Class Name : IScriptReceive
 * @Description : 자바스크립트로 부터 메소드 호출
 * @since 2015-08-03.
 */
public interface IScriptReceive {

    /** 페이지 이동*/
    public static final int LOAD_PAGE_URL = 0;

    /** 파일 첨부*/
    public static final int SEND_FILE_ATTACKED = 1;

    /** 설정 화면으롱 이동*/
    public static final int LOCATION_CONF_DISPLAY = 2;

    /** URL 페이지 시작*/
    public static final int ON_PAGE_STARTED = 3;

    /** URL 페이지 종료*/
    public static final int ON_PAGE_FINISHED = 4;

    /*** 타이틀 메뉴 값 설정 */
    public static final int SET_TITLE = 5;

    /*** 타이틀 메뉴 값 설정 */
    public static final int SET_TITLES = 6;

    /***
     * 리비스 콜백 메소드
     */
    public void onScriptReceive(int what, Object obj);
}
