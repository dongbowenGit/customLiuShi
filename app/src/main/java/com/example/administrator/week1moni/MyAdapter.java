package com.example.administrator.week1moni;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2018.05.12.
 */

public class MyAdapter extends BaseAdapter{
    List<String> list;
    Context context;

    public MyAdapter(List<String> list,Context context) {
        this.list = list;
        this.context=context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewhower vh;
        if(convertView==null){
            vh=new viewhower();
            convertView=View.inflate(context,R.layout.item,null);
            vh.tv=convertView.findViewById(R.id.tv);
            convertView.setTag(vh);
        }
        else {
            vh= (viewhower) convertView.getTag();

        }

        vh.tv.setText(list.get(position).toString());
        return convertView;


    }
    class viewhower{
        TextView tv;
    }
}
