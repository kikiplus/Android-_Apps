package UIFrame;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.mihye.R;

import org.w3c.dom.Text;

import Bean.Book;

/**
 * Created by mihye on 2015-06-28.
 */
public class DetailFragement extends Fragment {
    /**
     * 인스턴스 생성 후 반환 메소드
     *
     * @return 인스턴스
     */
    public static DetailFragement newInstance(int position) {
        DetailFragement mInstance = new DetailFragement();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        mInstance.setArguments(bundle);
        return mInstance;
    }

    /**
     * 인덱스 반환 메소드
     *
     * @return 인덱스
     */
    public int getPosition() {
        return getArguments().getInt("position", 0);
    }

    @Nullable
    @Override
    /**
     * 뷰 생성하여 반환하는 메소드
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transversemode_detail_layout, container, false);
        TextView textView = (TextView) view.findViewById(R.id.transversemode_details_textview);
        textView.setText(Book.DETAILS[getPosition()]);
        return view;
    }
}
