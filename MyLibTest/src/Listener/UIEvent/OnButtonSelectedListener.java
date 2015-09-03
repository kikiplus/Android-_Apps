package Listener.UIEvent;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : OnButtonSelectedListener
 * @Description : 버튼 선택시 호출 메소드
 * @since 2015-07-09.
 */
public interface OnButtonSelectedListener {
    public void onButtonItemSelected(int what, Object item);

    /** 확인버튼*/
    public final int BUTTON_OK = 1;
    /** 취소버튼*/
    public final int BUTTON_CANCLE = 2;
}
