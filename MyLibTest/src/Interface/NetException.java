package Interface;

/**
 * @Class Name : NetException
 * @Description : 네트워크 예외 인터페이스 정의 
 * @author grapegirl
 * @since 2015. 1. 6.
 * @version 1.0
 */
public interface NetException {
  
  /**
   * @Method : getExceptionString
   * @Description : 예외문구 반환 메소드
   * @param str 예외문구
   */
	public void getExceptionString(String str);
}
