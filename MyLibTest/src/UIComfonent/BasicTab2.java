package UIComfonent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/***
 * @Class Name : 탭 UI
 * @Description :
 * @since 2015. 6. 17.
 * @version 1.0
 * @author grapegirl
 */
public class BasicTab2 extends Fragment {

    /** 탭 타이틀 */
    private String mTitle = null;

    /**
     * 생성자
     */
    public BasicTab2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(com.test.mihye.R.layout.activity_tab2, null);
        return view;
    }

    @Override
    public View getView() {
        return super.getView();
    }

    /***
     * 타이블 반환 메소드
     */
    public String getTitle() {
        return mTitle;
    }

    /***
     * 타이틀 설정 메소드
     * 
     * @param title
     *            타이틀
     */
    public void setTitle(String title) {
        mTitle = title;

    }
}
