package View.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.test.mihye.R;

/***
 * *
 * @Class Name : GridLayoutTestActivity
 * @Description : 그리드 레이아웃 테스트 클래스
 * @since 2015. 6. 19.
 * @version 1.0
 * @author grapegirl
 */
public class GridLayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);
    }
}
