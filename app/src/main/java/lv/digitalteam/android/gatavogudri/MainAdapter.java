package lv.digitalteam.android.gatavogudri;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainAdapter extends BaseAdapter {

    int toolImages[];
    String toolNames[];
    Context context;

    public MainAdapter(Context context, int toolImages[], String toolNames[]) {

        this.context=context;
        this.toolImages=toolImages;
        this.toolNames=toolNames;

    }

    @Override
    public int getCount() {
        return toolNames.length;
    }


    @Override
    public Object getItem(int position) {
        return toolNames[position];
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {

            LayoutInflater lInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = lInflater.inflate(R.layout.adapter_main, null);

        }

        ImageView icon = view.findViewById(R.id.toolPicture);
        TextView name = view.findViewById(R.id.toolName);

        icon.setImageResource(toolImages[position]);
        name.setText(toolNames[position]);

        return view;
    }

}
