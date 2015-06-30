package com.mckuai.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mckuai.bean.Map;
import com.mckuai.imc.MCkuai;
import com.mckuai.imc.R;
import com.mckuai.widget.MasterLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;

import cn.aigestudio.downloader.bizs.DLManager;
import cn.aigestudio.downloader.interfaces.DLTaskListener;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Zzz on 2015/6/24.
 */
public class MapAdapter extends BaseAdapter {

    private Map maps;
    private Context mContext;
    private View view;
    private LayoutInflater mInflater;
    private ArrayList<Map> mMapBeans = new ArrayList<Map>();
    private ImageLoader mLoader;


    private DLManager manager;

    public MapAdapter(Context context, ArrayList<Map> mapBeans) {
        mMapBeans = mapBeans;
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return mMapBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mMapBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Map map = (Map) getItem(position);
        if (null == map) {
            return null;
        }
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.item_map, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_category = (TextView) convertView.findViewById(R.id.tv_category);
            holder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.MasterLayout01 = (MasterLayout) convertView.findViewById(R.id.MasterLayout01);
            final MasterLayout btn = holder.MasterLayout01;
            btn.setOnClickListener(new ClickLinstener(map, btn));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        mLoader.displayImage(map.getIcon(), holder.image);
        holder.tv_time.setText(map.getInsertTime());
        holder.tv_name.setText(map.getViewName());
        holder.tv_size.setText(map.getResSize());
        holder.tv_category.setText(map.getResCategroyTwo());
        holder.MasterLayout01.setTag(map);
        return convertView;
    }

    class ViewHolder {
        public ImageView image;
        public TextView tv_name;
        public TextView tv_category;
        public TextView tv_time;
        public TextView tv_size;
        public MasterLayout MasterLayout01;
    }

    class ClickLinstener implements View.OnClickListener {
        private Map map;
        private MasterLayout MasterLayout01;

        public ClickLinstener(Map map, MasterLayout MasterLayout01) {
            this.map = map;
            this.MasterLayout01 = MasterLayout01;
        }

        @Override
        public void onClick(View v) {
            final MasterLayout btn = MasterLayout01;
            btn.animation();
            Map clickedMap = (Map) v.getTag();
            if (mapManager == null) {
                mapManager = new MCMapManager();
            }

            if (null == clickedMap) {
//                Toast.makeText(mContext, "点击出错", LENGTH_SHORT).show();
//                return;
            }
            switch (btn.getFlg_frmwrk_mode()) {
                case 1:
                    if (null == manager) {
                        manager = DLManager.getInstance(mContext);
                    }
                    String downloadDir = MCkuai.getInstance().getMapDownloadDir();
                    String url = "http://"+clickedMap.getSavePath();
                    Log.e(TAG,"downloaddir:"+downloadDir);
                    Log.e(TAG,"url:"+url);
                    manager.dlStart(url,downloadDir, new McDLTaskListener(clickedMap, btn) {


                    });
                    break;
                //Download stopped
                case 2:

                    break;
                //Download complete
                case 3:

                    break;
            }
        }
    }

    class McDLTaskListener extends DLTaskListener {
        private Map clickedMap;
        private MasterLayout MasterLayout01;

        public McDLTaskListener(Map clickedMap, MasterLayout MasterLayout01) {
            super();
            this.clickedMap = clickedMap;
            this.MasterLayout01 = MasterLayout01;

        }

//        final MasterLayout btn = MasterLayout01;

        @Override
        public void onStart(String fileName, String url) {
            super.onStart(fileName, url);
            Log.e("111111", "onStart");
            //Toast.makeText(mContext, "Start", LENGTH_SHORT).show();
        }

        @Override
        public boolean onConnect(int type, String msg) {

            //Toast.makeText(mContext, "Connect", LENGTH_SHORT).show();
            Log.e("111111", "onConnect");
            return super.onConnect(type, msg);
        }

        @Override
        public void onProgress(int progress) {
            super.onProgress(progress);
            MasterLayout01.cusview.setupprogress(progress);
        }

        @Override
        public void onFinish(File file) {
            Log.e("111", "" + file.getPath() + file.getName());
            super.onFinish(file);
            mapManager.addDownloadMap(clickedMap);
            mapManager.closeDB();
        }

        @Override
        public void onError(String error) {
            Log.e("1111112222222222222222222222222222222222222222222222222222222222222", "onError");
            super.onError(error);
            //Toast.makeText(mContext, "Error", LENGTH_SHORT).show();
        }
    }
}
