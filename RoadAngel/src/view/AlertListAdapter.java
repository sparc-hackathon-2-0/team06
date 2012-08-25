package view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.team06.roadangel.R;

import java.util.ArrayList;
import java.util.List;

/**
 * User: David.Pechacek
 * Date: 8/25/12
 * Time: 5:11 PM
 */
public class AlertListAdapter extends ArrayAdapter<String> {
    private LayoutInflater mInflater;

    private List<String> mItems;

    private int mViewResourceId;
    private Context mContext;

    public AlertListAdapter(Context context, int viewResourceId, List<String> items) {

        super(context, viewResourceId, items);

        mInflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        mItems = new ArrayList<String>();

        for(String item : items) {
            mItems.add(item);
        }

        mContext = context;
        mViewResourceId = viewResourceId;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public String getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);
        String item = mItems.get(position);

        TextView tv = (TextView)convertView.findViewById(R.id.alert_text);
        tv.setText(item);

        return convertView;
    }

    @Override
    public void remove(String item) {
        if (mItems != null) {
            mItems.remove(item);
        }

        notifyDataSetChanged();
    }
}
