package Listener.UIEvent;

/***
 * @Class Name : PopupListener
 * @Description : 팝업 관련 리스너
 * @since 2015. 5. 18.
 * @version 1.0
 * @author grapegirl
 */
public interface OnPopupEventListener {

	/**
	 * 팝업 관련 동작 메소드
	 * 
	 * @param what
	 * @param obj
	 */
	public void onPopupAction(int what, Object obj);

	/** 팝업 확인 버튼 선택 */
	public static final int	POPUP_BTN_OK		= 0;
	/** 팝업 취소 버튼 선택 */
	public static final int	POPUP_BTN_CLOSEE	= 1;
	/** 팝업 백키 */
	public static final int	POPUP_BACK			= 2;
	/** 팝업 해제 */
	public static final int	POPUP_DISPOSE		= 3;
}
