package Bean;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : ViewItem
 * @Description : 리사이클러뷰 아이템
 * @since 2015-07-06.
 */
public class ViewItem {

    /** 글자*/
    private String mText;

    /** 이미지 리소스*/
    private int mRes;

    /**
     * 생성자
     * @param text
     * @param imgRes
     */
    public  ViewItem(String text, int imgRes){
        mText = text;
        mRes = imgRes;
    }

    /**
     * 텍스트 반환 메소드
     * @return 텍스트
     */
    public String getText(){
        return  mText;
    }

    /**
     * 이미지 반환 메소드
     * @return 이미지
     */
    public int getRes(){
        return mRes;
    }

    /***
     * 텍스트 설정 메소드
     * @param text 텍스트
     */
    public void setText(String text){
        mText = text;
    }

    /***
     * 이미지 설정 메소드
     * @param res 이미지
     */
    public void setRes(int res){
        mRes = res;
    }
}
