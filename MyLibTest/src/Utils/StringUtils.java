/**
 * 
 */
package Utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @Class Name : 스트링 유틸
 * @Description :
 * @since 2015. 1. 8.
 * @version 1.0
 */
public class StringUtils {

	/****
	 * @Description : HTML 태그 변환된거 다시 변환하는 메소드
	 * @Return 변환된 스트링 값
	 */
	public static String convertString(String str) {
		if (str.contains("&amp;")) {
			str = str.replace("&amp;", "&");
		}
		if (str.contains("&apos;")) {
			str = str.replace("&apos;", "'");
		}
		if (str.contains("&quot;")) {
			str = str.replace("&quot;", "\"");
		}
		if (str.contains("\\")) {
			str = str.replace("\\", "\\");
		}
		if (str.contains("&lt;")) {
			str = str.replace("&lt;", "<");
		}
		if (str.contains("&gt;")) {
			str = str.replace("&gt;", ">");
		}
		return str;
	}


	/**
	 * 포스트 방식으로 데이타 전송시 인자 설정 메소드
	 * @param sendData
	 * @return 포스트 방식 전송 데이타
	 */
	public static String getHTTPPostSendData(HashMap<String,Object> sendData){
		StringBuilder sb = new StringBuilder();

		//키값과 값을 추가함.
		 Set<String> key = sendData.keySet();
		for(Iterator iterator = key.iterator(); iterator.hasNext();){
			String keyName = (String)iterator.next();
			Object value = (Object)sendData.get(keyName);

			if(iterator.hasNext())
				sb.append(keyName).append("=").append(value).append("&");
			else
				sb.append(keyName).append("=").append(value);
		}
		System.out.println("@@ getHTTPPostSendData :  " + sb.toString());
		return sb.toString();
	}
}
