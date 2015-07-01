package com.mckuai.imc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mckuai.adapter.ExportAdapter;
import com.mckuai.adapter.MymapAdapter;
import com.mckuai.bean.Map;
import com.mckuai.until.MCMapManager;

import java.util.ArrayList;

/**
 * Created by Zzz on 2015/6/24.
 */
public class MymapActivity extends BaseActivity implements OnClickListener {
    private String searchContext;//输入内容
    private Context mContent;
    private ListView map_mymap_lv;
    private ImageView btn_left;
    private ImageButton btn_right;
    private TextView tv_title;
    private EditText map_ed;
    private Button go_map, leave_map, delete_map;
    private MCMapManager mapManager;
    private MymapAdapter adapter;
    ArrayList<Map> downloadMap;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_mymap);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null == mapManager) {
            mapManager = new MCMapManager();
            initView();
        }

//        ArrayList<String> curmap = mapManager.getCurrentMaps();

        showData();
    }

    protected void showData() {
        downloadMap = mapManager.getDownloadMaps();
        if (downloadMap == null) {
            showNotification(1, "请下载地图", R.id.maproot);
        } else {
            adapter = new MymapAdapter(this, downloadMap);
            map_mymap_lv.setAdapter(adapter);
        }
    }

    private void initView() {
        btn_left = (ImageView) findViewById(R.id.btn_left);
        btn_right = (ImageButton) findViewById(R.id.btn_right);
        map_ed = (EditText) findViewById(R.id.map_ed);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我的地图");
        map_mymap_lv = (ListView) findViewById(R.id.map_mymap_lv);
        go_map = (Button) findViewById(R.id.go_map);
        leave_map = (Button) findViewById(R.id.leave_map);
        delete_map = (Button) findViewById(R.id.delete_map);
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        go_map.setOnClickListener(this);
        leave_map.setOnClickListener(this);
        delete_map.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_left:
                finish();
                break;
            //根据输入名搜索地图
            case R.id.btn_right:
                map_ed.setVisibility(View.VISIBLE);
                if (0 < map_ed.getText().length()) {
                    searchContext = map_ed.getText().toString().trim();//trim() 表示空格
                    search();
                } else {
                    Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.go_map:
                intent = new Intent(this, MapimportActivity.class);
                startActivity(intent);
                break;
            case R.id.leave_map:
                intent = new Intent(this, Export_mapActivity.class);
                startActivity(intent);
                break;
            case R.id.delete_map:
                intent = new Intent(this, MapdeleteActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void search() {

    }
}
