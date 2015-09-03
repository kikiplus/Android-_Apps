/**
 *
 */
package View.Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : WiFiAP
 * @Description : 와이파이 AP 정보 클래스
 * @since 2015. 1. 6.
 */
public class WiFiAP implements Parcelable {

    /**
     * AP 맥 주소
     */
    private String mMAC;

    /**
     * AP 맥의 데시벨
     */
    private String mDecibel;

    /**
     * AP 맥의 이름
     */
    private String mSSID;

    /**
     * 보안설정
     */
    private String mCapability;

    /**
     * 생성자
     */
    public WiFiAP() {

    }

    public WiFiAP(Parcel parcel) {
        mMAC = parcel.readString();
        mDecibel = parcel.readString();
        mSSID = parcel.readString();
    }

    /**
     * 생성자
     *
     * @param mac 맥
     * @param deb 데시벨
     */
    public WiFiAP(String ssid, String deb, String mac) {
        mMAC = mac;
        mDecibel = deb;
        mSSID = ssid;
    }

    /**
     * @Description : 맥 주소 반환 메소든
     * @Return 맥주소
     */
    public String getMac() {
        return mMAC;
    }

    /**
     * @Description : 맥 이름 반환 메소든
     * @Return 맥주소
     */
    public String getSSID() {
        return mSSID;
    }

    /**
     * @Description : 맥의 데시벨 값 반환 메소드
     * @Return 데시벨 값
     */
    public String getDecibel() {
        return mDecibel;
    }


    /**
     * @Description : 보안 반환 값 메소드
     * @Return 보안 값
     */
    public String getCapability() {
        return mCapability;
    }

    /**
     * @param mac 맥주소
     * @Description : 맥주소 설정 메소드
     */
    public void setMac(String mac) {
        mMAC = mac;
    }

    /**
     * @param ssid 맥이름
     * @Description : 맥 이름 설정 메소드
     */
    public void setSSID(String ssid) {
        mSSID = ssid;
    }

    /**
     * @param decibel 데시벨
     * @Description : 데시벨 값 설정 메소드
     */
    public void setDecibel(String decibel) {
        mDecibel = decibel;
    }

    /**
     * @param capability 보안
     * @Description : 보안 값 설정 메소드
     */
    public void setCapability(String capability) {
        mCapability = capability;
    }


    @Override
    public String toString() {
        return "ssid: " + mSSID + "\n/deb: " + mDecibel + " /mac: " + mMAC;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSSID);
        dest.writeString(mDecibel);
        dest.writeString(mMAC);
    }

    public static final Parcelable.Creator<WiFiAP> CREATOR = new Creator<WiFiAP>() {

        @Override
        public WiFiAP[] newArray(int size) {
            return new WiFiAP[size];
        }

        @Override
        public WiFiAP createFromParcel(Parcel source) {
            return new WiFiAP(source);
        }
    };
}
