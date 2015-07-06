package main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.test.mihye.R;

import java.util.ArrayList;
import java.util.List;

import Bean.ViewItem;
import ViewAdapter.RecyclerAdapter;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : RecyclerViewActivity
 * @Description : 리사이클러 뷰
 * @since 2015-07-06.
 */
public class RecyclerViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_main_layout);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_main_recyclerview);

        RecyclerAdapter adapter = new RecyclerAdapter(this,createListItem(), R.layout.recycer_item_layout);
        recyclerView.setAdapter(adapter);

        //리니어 레이아웃
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //그리드 레이아웃
        //GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        //recyclerView.setLayoutManager(layoutManager);

        //지그재그 그리드 레이아웃
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        //recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private List<ViewItem> createListItem() {
        List<ViewItem> items = new ArrayList<ViewItem>();
        for (int i = 0; i < 10; i++) {
            items.add(new ViewItem("아이템 " + i, R.mipmap.ic_launcher));
        }
        return items;
    }
}
