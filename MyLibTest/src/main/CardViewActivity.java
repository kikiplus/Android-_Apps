package main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.test.mihye.R;

import UIComfonent.KDialog;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name :
 * @Description :
 * @since 2015-07-03.
 */
public class CardViewActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardview_main_layout);

        Button button = (Button) findViewById(R.id.cardview_main_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardview_main_button:
                Intent intent = new Intent(this, CardSubViewActivity.class);
                startActivity(intent);
//                KDialog kDialog = new KDialog(this, "테스트", R.layout.cardview_sub_layout) {
//                    @Override
//                    public void onClick(View v) {
//                        System.out.println("@@ ONCLICK");
//                    }
//
//                    @Override
//                    public void initDialog() {
//                        mDialogView.findViewById(R.id.cardview_sub_button).setOnClickListener(this);
//                    }
//
//                    @Override
//                    public void destroyDialog() {
//
//                    }
//                };
//                kDialog.showDialog();
                break;

        }
    }

}
