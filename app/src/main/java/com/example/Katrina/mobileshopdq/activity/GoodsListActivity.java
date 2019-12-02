package com.example.Katrina.mobileshopdq.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.Katrina.mobileshopdq.R;
import com.example.Katrina.mobileshopdq.adapter.GoodsListAdapter;
import com.example.Katrina.mobileshopdq.commom.BaseActivity;
import com.example.Katrina.mobileshopdq.http.entity.GoodsEntity;
import com.example.Katrina.mobileshopdq.http.presenter.GoodsPresenter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class GoodsListActivity extends BaseActivity {
    private int cat_id;
    SwipeRefreshLayout goodsListSwipeRefresh;
    RecyclerView goodsListRecyclerview;
    TextView goodsListNoData;

    private List<GoodsEntity> listData;
    private GoodsListAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_goodslist;
    }

    void close(){
        finish();
    }

    @Override
    protected void initView() {
        super.initView();

        goodsListSwipeRefresh=(SwipeRefreshLayout)findViewById(R.id.goodslist_swipe_refresh);
        goodsListRecyclerview=(RecyclerView)findViewById(R.id.goodslist_recyclerview);
        goodsListNoData=(TextView)findViewById(R.id.goodslist_nodata);

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });

        goodsListNoData.setVisibility(View.GONE);

        cat_id = getIntent().getIntExtra("cat_id",0);

        goodsListSwipeRefresh.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        goodsListSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        goodsListRecyclerview.setLayoutManager(layoutManager);

        listData = new ArrayList<GoodsEntity>();
        adapter = new GoodsListAdapter(this,listData);
        adapter.setOnItemClickListener(new GoodsListAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view,int position,GoodsEntity entity){
                Intent intent = new Intent(GoodsListActivity.this,GoodsDetailActivity.class);
                intent.putExtra("goods_id",entity.getGoods_id());
                intent.putExtra("goods_name",entity.getName());
                startActivity(intent);
            }
        });
        goodsListRecyclerview.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        loadData();
    }

    private void loadData(){
        GoodsPresenter.list(new Subscriber<List<GoodsEntity>>() {
            @Override
            public void onCompleted() { goodsListSwipeRefresh.setRefreshing(false); }

            @Override
            public void onError(Throwable e) { goodsListSwipeRefresh.setRefreshing(false); }

            @Override
            public void onNext(List<GoodsEntity> CategoryEntities) {
                listData.clear();
                listData.addAll(CategoryEntities);
                adapter.notifyDataSetChanged();

                if (listData == null || listData.size() == 0){
                    toastLong("没有该列表的商品数据");
                    goodsListNoData.setVisibility(View.VISIBLE);
                    goodsListRecyclerview.setVisibility(View.GONE);
                }else {
                    goodsListNoData.setVisibility(View.GONE);
                    goodsListRecyclerview.setVisibility(View.VISIBLE);
                }
            }
        },cat_id);
    }
}

