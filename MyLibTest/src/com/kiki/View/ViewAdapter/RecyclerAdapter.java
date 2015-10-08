package com.kiki.View.ViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kiki.View.Bean.ViewItem;
import com.kiki.View.R;

import java.util.List;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name :
 * @Description :
 * @since 2015-07-06.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHoler> {

    /** 태그명*/
    private static final String TAG = "RecyclerAdapter";
    /** 컨텍스트*/
    private Context mContext;
    /** 리스트*/
    private List<ViewItem> mListItems;
    /** 레이아웃*/
    private int mItemLayout;

    /**
     * 생성자
     * @param context 컨텍스트
     * @param items 리스트
     * @param layout 레이아웃
     */
    public RecyclerAdapter(Context context, List<ViewItem> items, int layout) {
        mContext = context;
        mListItems = items;
        mItemLayout = layout;
    }


    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemLayout, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(ViewHoler viewHolder, int position) {
        viewHolder.textView.setText(mListItems.get(position).getText());
        viewHolder.imgageView.setImageResource(mListItems.get(position).getRes());
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    /***
     * 뷰홀더 클래스
     */
    public static class ViewHoler extends RecyclerView.ViewHolder {
        /** 이미지뷰*/
        public ImageView imgageView;
        /** 텍스트뷰*/
        public TextView textView;

        /***
         * 생성자
         * @param itemView
         */
        public ViewHoler(View itemView) {
            super(itemView);
//            imgageView = (ImageView)itemView.findViewById(R.id.recycler_item_imageview);
//            textView = (TextView)itemView.findViewById(R.id.recycler_item_textview);
        }
    }


}