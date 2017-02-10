package com.kikiplus.View.ViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kikiplus.View.Bean.PostData;
import com.kikiplus.View.R;
import com.kikiplus.common.utils.SortUtils;

import java.util.ArrayList;
import java.util.Collections;

/***
 * @Class Name : ReverseListAdpater
 * @Description : 역순 리스트 목록 어뎁터
 * @since 2015. 6. 23.
 * @author grapegirl
 * @version 1.0
 */
public class ReverseListAdpater extends BaseAdapter {

	/** 컨텍스트 */
	private Context				m_Context	= null;

	/** 리소스 아이디 */
	private int					m_Res		= -1;

	/** 리스트 아이템 */
	private ArrayList<PostData> m_ListItem	= null;

	/**
	 * 생성자
	 */
	public ReverseListAdpater(Context context, int res, ArrayList<PostData> list) {
		m_Context = context;
		m_Res = res;
		m_ListItem = list;
		//역순 정렬
		Collections.sort(m_ListItem, SortUtils.comparetor);
	}

	@Override
	public int getCount() {
		if ( m_ListItem != null ) {
			return m_ListItem.size();
		}
		return -1;
	}

	@Override
	public Object getItem(int position) {
		if ( m_ListItem != null ) {
			return m_ListItem.get( position );
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		if ( m_ListItem != null ) {
			return position;
		}
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if ( view == null ) {
			LayoutInflater inflater = (LayoutInflater) m_Context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
			view = inflater.inflate( m_Res, null );
		}
		((TextView) view.findViewById(R.id.listview_line_Textview)).setText(m_ListItem.get(position).getContents());
		return view;
	}

	
	/**
	 * 데이타 리스트 변경 메소드
	 */
	public void setDataList(ArrayList<PostData> list){
		m_ListItem = list;
		//역순 정렬
		Collections.sort(m_ListItem, SortUtils.comparetor);
		notifyDataSetChanged();
	}
}
