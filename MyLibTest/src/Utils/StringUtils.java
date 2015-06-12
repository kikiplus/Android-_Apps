/**
 * 
 */
package Utils;

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
}
