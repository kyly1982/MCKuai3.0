package com.mckuai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mckuai.bean.Map;
import com.mckuai.imc.R;
import com.mckuai.widget.MasterLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;

import cn.aigestudio.downloader.bizs.DLManager;
import cn.aigestudio.downloader.interfaces.DLTaskListener;

/**
 * Created by Zzz on 2015/6/24.
 */
public class MapAdapter extends BaseAdapter {


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
        if (null != map) {
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
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn.animation();
                    switch (btn.getFlg_frmwrk_mode()) {
                        case 1:
                            if (null == manager) {
                                manager = DLManager.getInstance(mContext);
                            }
                            manager.dlStart("http://softdown.mckuai.com:8081/mckuai.apk", "/mnt/sdcard/Download/", new DLTaskListener() {
                                @Override
                                public void onStart(String fileName, String url) {
                                    super.onStart(fileName, url);
                                }

                                @Override
                                public boolean onConnect(int type, String msg) {
                                    return super.onConnect(type, msg);
                                }

                                @Override
                                public void onProgress(int progress) {
                                    super.onProgress(progress);
                                    btn.cusview.setupprogress(progress);
                                }

                                @Override
                                public void onFinish(File file) {
                                    super.onFinish(file);
                                }

                                @Override
                                public void onError(String error) {
                                    super.onError(error);
                                }
                            });
                            break;
                        case 2:

                            break;
                        case 3:

                            break;
                    }
                }
            });
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
}
