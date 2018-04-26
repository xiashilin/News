package com.kaku.colorfulnews.mvp.ui.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import com.avos.avoscloud.AVObject;
import com.kaku.colorfulnews.R;
import com.kaku.colorfulnews.mvp.entity.CollectBean;
import com.kaku.colorfulnews.mvp.ui.activities.base.BaseActivity;
import com.kaku.colorfulnews.mvp.ui.adapter.CollectAdapter;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Email:1479714932@qq.com
 *
 * @author:xsl Date:2018/4/25,Time:12:03
 * Description:
 */

public class HistoryActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @BindView(R.id.history_rv)
    RecyclerView mNewsRV;
    private List<CollectBean> mItemList;
    private CollectAdapter mCollectAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_history);
        ButterKnife.bind(this);
        setStatusBarTranslucent();
        initToolBar();
        initViews();
    }

    public void initViews() {
        mNewsRV.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mItemList = new ArrayList<>();
        mItemList = queryAllCollectBean();
        mCollectAdapter = new CollectAdapter(this, mItemList);
        mNewsRV.setAdapter(mCollectAdapter);
        mCollectAdapter.notifyDataSetChanged();
    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.icon_back);
        mToolbar.setTitle("我的收藏");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public List<CollectBean> queryAllCollectBean() {
        Realm mRealm = Realm.getDefaultInstance();

        RealmResults<CollectBean> collectBeans = mRealm.where(CollectBean.class).findAll();

        return mRealm.copyFromRealm(collectBeans);
    }

    // TODO:适配4.4
    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void setStatusBarTranslucent() {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimary);
        }
    }
}
