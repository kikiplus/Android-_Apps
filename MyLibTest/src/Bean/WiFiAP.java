/**
 * 
 */
package Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Class Name : WiFiAP
 * @Description : 와이파이 AP 정보 클래스
 * @since 2015. 1. 6.
 * @author grapegirl
 * @version 1.0
 */
public class WiFiAP implements Parcelable {

	/** AP 맥 주소 */
	private String	m_Mac;

	/** AP 맥의 데시벨 */
	private String	m_Decibel;

	/** AP 맥의 이름 */
	private String	m_SSID;

	/**
	 * 생성자
	 */
	public WiFiAP() {

	}

	public WiFiAP(Parcel parcel) {
		m_Mac = parcel.readString();
		m_Decibel = parcel.readString();
		m_SSID = parcel.readString();
	}

	/**
	 * 생성자
	 * 
	 * @param mac
	 *            맥
	 * @param deb
	 *            데시벨
	 */
	public WiFiAP(String ssid, String deb, String mac) {
		m_Mac = mac;
		m_Decibel = deb;
		m_SSID = ssid;
	}

	/**
	 * @Description : 맥 주소 반환 메소든
	 * @Return 맥주소
	 */
	public String getMac() {
		return m_Mac;
	}

	/**
	 * @Description : 맥 이름 반환 메소든
	 * @Return 맥주소
	 */
	public String getSSID() {
		return m_SSID;
	}

	/**
	 * @Description : 맥의 데시벨 값 반환 메소드
	 * @Return 데시벨 값
	 */
	public String getDecibel() {
		return m_Decibel;
	}

	/**
	 * @Description : 맥주소 설정 메소드
	 * @param mac
	 *            맥주소
	 */
	public void setMac(String mac) {
		m_Mac = mac;
	}

	/**
	 * @Description : 맥 이름 설정 메소드
	 * @param ssid
	 *            맥이름
	 */
	public void setSSID(String ssid) {
		m_SSID = ssid;
	}

	/**
	 * @Description : 데시벨 값 설정 메소드
	 * @param decibel
	 *            데시벨
	 */
	public void setDecibel(String decibel) {
		m_Decibel = decibel;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString( m_SSID );
		dest.writeString( m_Decibel );
		dest.writeString( m_Mac );
	}

	public static final Parcelable.Creator<WiFiAP>	CREATOR	= new Creator<WiFiAP>() {

																@Override
																public WiFiAP[] newArray(int size) {
																	return new WiFiAP[size];
																}

																@Override
																public WiFiAP createFromParcel(Parcel source) {
																	return new WiFiAP( source );
																}
															};
}
