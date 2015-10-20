package com.kiki.View.ViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.kiki.View.R;

import java.util.ArrayList;

/**
 * 
 * @Class Name : SpinnerAdapter.java
 * @Description : 팝업 아이템 어뎁터 클래스 
 *
 * @author grapegirl
 * @since 2014.08.01
 * @version 1.0
 */
public class SpinnerAdapter extends BaseAdapter {
	
	/** Context */
	private Context m_Context = null;
	
	/** 레이아웃 리소스 ID */
	private int m_Res = 0;
	
	/** 팝업  ID List */
	private int[] m_idList = { R.id.spiner_item_checkbox, R.id.spiner_item_textview };
	
	/** 팝업  목록 데이타 */
	private ArrayList<String> m_sDataList = null;
	
	/** 라디오 버튼 상태값  */
	private boolean[] m_radioBtn;
	
	/** 팝업 아이템 선택 index */
	private int m_selIndex = 0;

	public SpinnerAdapter(Context context, int res, ArrayList<String> m_ArrList) {
		m_Context = context;
		m_Res = res;
		m_sDataList = m_ArrList;
		m_radioBtn = new boolean[m_sDataList.size()];
	}

	@Override
	public int getCount() {
		return m_sDataList.size();
	}

	@Override
	public String getItem(int position) {
		return m_sDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) m_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(m_Res, null);
		}
		((TextView) view.findViewById(m_idList[0])).setText(m_sDataList.get(position));
		
		if(position==m_selIndex){
			((RadioButton) view.findViewById(m_idList[1])).setChecked(true);	
		}else{
			((RadioButton) view.findViewById(m_idList[1])).setChecked(false);
		}
		return view;
	}

	/***
	 * 선택한 item 인덱스 설정하는 메소드
	 * @param index
	 */
	public void setSelectIndex(int index) {
		m_selIndex = index;
	}
}
