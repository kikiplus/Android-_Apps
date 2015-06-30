package main;


import java.util.ArrayList;

import Bean.PostData;
import ViewAdapter.ListAdpater;
import ViewAdapter.ReverseListAdpater;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.test.mihye.R;

/***
 * @Class Name :ListTestActivity 
 * @Description : 리스트 어뎁터 메인
 * @since 2015. 6. 16.
 * @version 1.0
 * @author grapegirl
 */
public class ListActivity extends Activity implements OnClickListener {

	/** 리스트 데이타*/
	private ArrayList<PostData> mDataList = null;
	/** 리스트 어뎁타*/
	private ReverseListAdpater mAdapter = null;
	/** 리스트뷰*/
	private ListView mListView = null;
	/** 버튼*/
	private Button mButton = null;
	/** 리스트 추가 변수 */
	private int mCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_layout);

		mDataList = new ArrayList<PostData>();
		for (int i = 0; i < mCount + 10; i++) {
			String temp = "Data " + i + "";
			PostData data = new PostData("",temp,"2015-06-23",i);
			mDataList.add(data);
		}
		mListView = (ListView) findViewById(R.id.listView);
		mListView.setDividerHeight(1);
		mButton = (Button) findViewById(R.id.button);
		mButton.setOnClickListener(this);
		mAdapter = new ReverseListAdpater(getApplicationContext(),
				R.layout.listview_line, mDataList);

		mListView.setAdapter(mAdapter);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button:
			mCount += 10;
			for (int i = mCount; i < mCount + 10; i++) {
				String temp = "Data " + i + "";
				PostData data = new PostData("",temp,"2015-06-23",i);
				mDataList.add(data);
			}
			mAdapter.setDataList(mDataList);
			break;
		}
	}

}
