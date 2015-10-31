package com.synsoft.categorywisead;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by controller on 10/26/15.
 */
public class ListAdapterCustom extends BaseAdapter {


    private Context context;
    private List<String> data;

    public ListAdapterCustom(Context context,List<String> data)
    {
        this.context=context;
        this.data=data;
    }

    @Override
    public int getCount() {
        if(data==null)
            return 0;
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.list_adapter_view, parent, false);

        ((TextView)convertView.findViewById(R.id.text)).setText(data.get(position));

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
}
