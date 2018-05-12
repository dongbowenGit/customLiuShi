package com.example.administrator.week1moni;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> list;
    private EditText et;
    private ListView lv;
    private MyAdapter myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();

        et = (EditText) findViewById(R.id.et);
        lv = (ListView) findViewById(R.id.lv);

    }
    public void suosou(View view) {
        myadapter = new MyAdapter(list, this);

        String s = et.getText().toString();
        list.add(s);
        et.getText().clear();
        lv.setAdapter(myadapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setMessage("确定删除");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(list.get(position));
                        myadapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                return true;
            }
        });

        myadapter.notifyDataSetChanged();

    }

    public void sc(View view) {
        list.clear();
        myadapter.notifyDataSetChanged();
    }
}
