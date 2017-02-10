/**
 *
 */
package com.kikiplus.View.ViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kikiplus.View.R;

import java.util.ArrayList;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : SpinnerListAdapter.java
 * @Description : 팝업 목록 어뎁터 클래스 (ListView)
 * @since 2014.08.01
 */
public class SpinnerListAdapter extends BaseAdapter implements OnClickListener {

    /**
     * Context
     */
    private Context m_Context = null;

    /**
     * 레이아웃 리소스 ID
     */
    private int m_Res = 0;

    /**
     * 팝업  목록 데이타
     */
    private ArrayList<String> m_sDataList = null;

    /**
     * 코드 데이타
     */
    private ArrayList<String> m_sCodeList = null;

    /**
     * 팝업 아이템 선택 index
     */
    private int m_selectIndex = -1;

    /**
     * 팝업 아이템 선택 수
     */
    private int m_selectCnt = 0;

    /**
     * 팝업 아이템 최대 선택 수
     */
    private static final int MAX_SELECTED_CNT = 2;

    /**
     * 라디오 버튼 상태값
     */
    private boolean[] m_radioBtn = null;

    public SpinnerListAdapter(Context context, ArrayList<String> data, ArrayList<String> listCodeData, int res) {
        m_Context = context;
        m_Res = res;
        m_sDataList = data;
        m_sCodeList = listCodeData;

        m_radioBtn = new boolean[m_sDataList.size()];
    }

    @Override
    public int getCount() {
        return m_sDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return m_sDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) m_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(m_Res, null);
        }
        ((TextView) view.findViewById(R.id.spiner_item_textview)).setText(m_sDataList.get(position));
        CheckBox radio = (CheckBox) view.findViewById(R.id.spiner_item_checkbox);
        radio.setChecked(m_radioBtn[position]);
        radio.setTag(String.valueOf(position));
        radio.setOnClickListener((OnClickListener) this);
        view.setTag(String.valueOf(position));
        view.setOnClickListener((OnClickListener) this);

        return view;
    }

    /***
     * default로 선택되어야 할 인덱스 설정 메소드
     *
     * @param index 선택한 인덱스
     */
    public void setDefaultChecked(int index) {
        if (m_selectIndex > 0) {
            m_selectIndex = index;
            m_radioBtn[m_selectIndex] = true;
        }
    }

    /**
     * 선택한 아이템 반환하는 메소드
     *
     * @return 선택한 내용
     */
    public String getSelectedItem() {
        return m_sDataList.get(m_selectIndex);
    }

    /**
     * 선택한 아이템의 index 반환하는 메소드
     *
     * @return 선택한 인덱스
     */
    public int getSelectedIndex() {
        return m_selectIndex;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spiner_item_checkbox://라디오 버튼 선택
            case R.id.spiner_list_panel://글자 선택
                m_selectIndex = Integer.parseInt((String) v.getTag());
                if (checkStatusCnt() < MAX_SELECTED_CNT) {
                    m_radioBtn[m_selectIndex] = !m_radioBtn[m_selectIndex];
                } else {
                    if (m_radioBtn[m_selectIndex] == true) {
                        m_radioBtn[m_selectIndex] = false;
                    }
                }
                notifyDataSetChanged();
                break;
        }
    }

    private int checkStatusCnt() {
        int count = 0;
        for (int i = 0; i < m_radioBtn.length; i++) {
            if (m_radioBtn[i] == true) {
                count++;
            }
        }
        return count;
    }

    /**
     * 선택한 item 반환 메소드
     *
     * @return 선택한 아이템 반환
     */
    public String getSelectedItemList() {
        String data = "";
        for (int i = 0; i < m_radioBtn.length; i++) {
            if (m_radioBtn[i] == true)
                data += m_sDataList.get(i) + ",";
        }
        return data;
    }

    public String getSelectedCodeList() {
        String data = "";
        for (int i = 0; i < m_radioBtn.length; i++) {
            if (m_radioBtn[i] == true)
                data += m_sCodeList.get(i) + ",";
        }
        return data;
    }

}
