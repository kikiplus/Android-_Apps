package View.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.test.mihye.R;

/***
 * @Class Name : 메인메뉴 5개 테스트
 * @Description :
 * @since 2015. 6. 17.
 * @version 1.0
 * @author grapegirl
 */
public class MainMenuActivity extends Activity implements android.view.View.OnClickListener {

    private Button[] mButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_layout);

        mButton = new Button[5];
        mButton[0] = (Button) findViewById(R.id.main_tab1);
        mButton[1] = (Button) findViewById(R.id.main_tab2);
        mButton[2] = (Button) findViewById(R.id.main_tab3);
        mButton[3] = (Button) findViewById(R.id.main_tab4);
        mButton[4] = (Button) findViewById(R.id.main_tab5);

        mButton[0].setOnClickListener(this);
        mButton[1].setOnClickListener(this);
        mButton[2].setOnClickListener(this);
        mButton[3].setOnClickListener(this);
        mButton[4].setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.main_tab1:
            Toast.makeText(getApplicationContext(), "메인1", Toast.LENGTH_SHORT).show();
            break;
        case R.id.main_tab2:
            Toast.makeText(getApplicationContext(), "메인2", Toast.LENGTH_SHORT).show();
            break;
        case R.id.main_tab3:
            Toast.makeText(getApplicationContext(), "메인3", Toast.LENGTH_SHORT).show();
            break;
        case R.id.main_tab4:
            Toast.makeText(getApplicationContext(), "메인4", Toast.LENGTH_SHORT).show();
            break;
        case R.id.main_tab5:
            Toast.makeText(getApplicationContext(), "메인5", Toast.LENGTH_SHORT).show();
            break;

        }

    }

}
