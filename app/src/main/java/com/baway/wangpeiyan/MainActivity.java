package com.baway.wangpeiyan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.baway.wangpeiyan.adapter.Adapter;
import com.baway.wangpeiyan.bean.ImageBean;
import com.baway.wangpeiyan.bean.ReBean;
import com.baway.wangpeiyan.utils.GlideImageLoader;
import com.baway.wangpeiyan.utils.MyDecoration;
import com.baway.wangpeiyan.utils.OkHttpUtils;
import com.google.gson.Gson;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 *   主页
 */
public class MainActivity extends AppCompatActivity {
    private String ImagePath = "http://i.dxy.cn/snsapi/event/count/list/all";
    private String DataPath = "http://i.dxy.cn/snsapi/home/feeds/list/all?sid=4df0360f-2a20-4198-beb8-4dc5660c4f08&u=zhetianyishou&s=10&mc=0000000049029dcaffffffff99d603a9&token=TGT-13165-buaw5fHpqLlefw9bSOB0oF41fobaV4rMZmK-50&hardName=iToolsAVM_T0008098S&ac=4124c5f1-2029-4fda-b06f-a87ac5ad8d11&bv=2013&vc=6.0.6&tid=c25e673d-e82a-4e46-bd4e-c1e86d497126&vs=4.4.4&ref_tid=54720e1a-7eed-4993-9f51-3d760f3d0b2e";
    private Banner banner;
    private List<String> listImage = new ArrayList<>();
    private RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiView();
        ReData();
        getImageData();

    }
    private void intiView(){
        banner = (Banner) findViewById(R.id.banner);
        recycle = (RecyclerView) findViewById(R.id.recycle);
    }
    private void ReData(){
        OkHttpUtils httpUtils = new OkHttpUtils();
        httpUtils.getJson(DataPath,new getData_num());
    }
    //轮播数据
    private void getImageData(){
        OkHttpUtils httpUtils = new OkHttpUtils();
        httpUtils.getJson(ImagePath,new getData());
    }
    /**
     *  网络获取
     */
    class getData extends OkHttpUtils.HttpCallBack {

        @Override
        public void onSusscess(String data) {
            //拿到数据
            Gson gson = new Gson();
            ImageBean imageBean = gson.fromJson(data, ImageBean.class);
            List<ImageBean.ItemsBeanHeadline> items = imageBean.getItems();
            Log.i("TAG", "onSusscess: ppp"+items.size());
            for (int i = 0; i < items.size(); i++) {
                listImage.add(items.get(i).getPath());
            }
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            Log.i("TAG", "onSusscess: ppdddddp"+listImage.size());
            banner.setImages(listImage);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
        }
    }
    class getData_num extends OkHttpUtils.HttpCallBack {

        @Override
        public void onSusscess(String data) {
            Gson gson =  new Gson();
            ReBean reBean = gson.fromJson(data, ReBean.class);
            List<ReBean.ItemsBeanHeadline> items = reBean.getItems();
            RecyclerView.LayoutManager layoutManager  = new LinearLayoutManager(MainActivity.this);
            recycle.setLayoutManager(layoutManager);
            MyDecoration myDecoration = new MyDecoration(MainActivity.this, LinearLayoutManager.VERTICAL);
            recycle.addItemDecoration(myDecoration);
            Adapter adapter = new Adapter(MainActivity.this,items);
            recycle.setAdapter(adapter);
        }
    }
}
