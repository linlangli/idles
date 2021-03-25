package io.github.grooters.idles.view.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.orhanobut.logger.Logger;
import io.github.grooters.idles.R;
import io.github.grooters.idles.bean.Goods;
import io.github.grooters.idles.view.fragment.GoodsItemFragment;

public class GoodsDetailActivity extends BaseActivity {

    private GoodsItemFragment goodsItemFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Goods goods = getIntent().getParcelableExtra("Goods");
        if(goods == null){
            Logger.d("goods == null");
        }
        goodsItemFragment = new GoodsItemFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Goods", goods);
        goodsItemFragment.setArguments(bundle);

        super.onCreate(savedInstanceState);
}

    @Override
    public Fragment createFragment() {
        return goodsItemFragment;
    }

    @Override
    public int containerId() {
        return R.id.activity_goods_Detail_frameLayout;
    }

    @Override
    public Fragment delayJump() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_goods_detail;
    }
}
