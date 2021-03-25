package io.github.grooters.idles.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import io.github.grooters.idles.R;
import io.github.grooters.idles.view.fragment.GoodsWorksFragment;
import io.github.grooters.idles.view.fragment.MyDataFragment;
import io.github.grooters.idles.view.fragment.PersonalDetailFragment;
import io.github.grooters.idles.view.fragment.PersonalFragment;

public class PersonalItemActivity extends BaseActivity{

    private String type;

    @BindView(R.id.toolbar)
    View toolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        super.onCreate(savedInstanceState);
    }

    @Override
    public Fragment createFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        if(type.equals(PersonalFragment.MY_INFO)){
            PersonalDetailFragment personalDetailFragment = new PersonalDetailFragment();
            personalDetailFragment.setArguments(bundle);
            return personalDetailFragment;
        } else if(type.equals(PersonalFragment.MY_DATA)){
            MyDataFragment myDataFragment = new MyDataFragment();
            myDataFragment.setArguments(bundle);
            toolBar.setVisibility(View.GONE);
            return myDataFragment;
        }else {
            Logger.d("GoodsWorksFragment");
            GoodsWorksFragment goodsWorksFragment = new GoodsWorksFragment();
            goodsWorksFragment.setArguments(bundle);
            return goodsWorksFragment;
        }
    }

    @Override
    public int containerId() { return R.id.activity_personal_item_frameLayout; }

    @Override
    public Fragment delayJump() { return null; }

    @Override
    public int getLayout() { return R.layout.activity_personal_item; }
}
