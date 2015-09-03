package Listener;


/**
 * @Class Name : ServerResponse
 * @Description : 서버로부터 응답을 전달하는 인터페이스
 * @author grapegirl
 * @since 2015. 1. 6.
 * @version 1.0
 */
public interface IServerResponse {
  
  /**
   * @Method : getServerResponse 
   * @Description :서버의 응답을 반환하는 메소드
   * @param str 서버의 응답 결과
   */
	public void getServerResponse(String str);
}
