package UIFrame;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.test.mihye.R;

import Bean.Book;

/**
 * @author grapegirl
 * @version 1.0
 * @Class Name : TitleFragement
 * @Description : 타이틀 플래그먼트
 * @since 2015. 6. 28.
 */
public class TitleFragement extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, Book.TITLE));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        DetailFragement detailFragement = (DetailFragement) getFragmentManager().findFragmentById(R.id.transversemode_details);

        if (detailFragement == null || detailFragement.getPosition() != position) {
            detailFragement = DetailFragement.newInstance(position);

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.transversemode_details, detailFragement);
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
    }
}
