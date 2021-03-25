package io.github.grooters.idles.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Objects;
import butterknife.BindView;
import io.github.grooters.idles.R;
import io.github.grooters.idles.base.GeneralRecyclerViewAdapter;
import io.github.grooters.idles.base.GeneralRecyclerViewHolder;
import io.github.grooters.idles.bean.Goods;
import io.github.grooters.idles.presenter.ConfigP;
import io.github.grooters.idles.presenter.GoodsP;
import io.github.grooters.idles.presenter.interfac.IGoodsP;
import io.github.grooters.idles.utils.Intenter;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.activity.AccountActivity;
import io.github.grooters.idles.view.activity.GoodsDetailActivity;
import io.github.grooters.idles.view.activity.MainActivity;
import io.github.grooters.idles.view.interfac.IListOfGoodsWorksV;
import io.github.grooters.idles.view.widget.LoadingWidget;

public class ListOfGoodsFragment extends BaseFragment implements IListOfGoodsWorksV<Goods> {

    private IGoodsP iGoodsP;

    @BindView(R.id.goodsListFragment_textView_nullData)
    TextView nullDadaTextView;
    @BindView(R.id.goodsListFragment_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.goodsListFragment_textView_loading)
    LoadingWidget loadingWidget;

    private List<Goods> goods;

    @Override
    int getLayout() {
        return R.layout.fragment_goods_list;
    }

    @Override
    void initView(View view) {
        iGoodsP = new GoodsP(getContext(), this);
        iGoodsP.getGoodsList();
    }

    @Override
    public void initRecyclerView(List<Goods> data) {
        nullDadaTextView.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyRecyclerViewAdapter(getActivity(), R.layout.item_goods, data));

        this.goods = data;
    }

    @Override
    public void initNullData() {
        nullDadaTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void loginInvalid() {
        ConfigP.deleteUser(Objects.requireNonNull(getContext()));
        Toaster.shortShow(getContext(), "登录失效，请重新登录");
        Intenter.jumpActivity(getContext(), AccountActivity.class);
    }

    @Override
    public void unAttach() {
        iGoodsP = null;
    }

    @Override
    public void startLoading() {
        loadingWidget.start();
    }

    @Override
    public void stopLoading() {
        loadingWidget.stop();
    }

    @Override
    public void showMessage(String message) {
        Toaster.shortShow(getContext(), message);
    }

    class MyRecyclerViewAdapter extends GeneralRecyclerViewAdapter<Goods> {
        MyRecyclerViewAdapter(Context context, int layoutId, List<Goods> dataList) {
            super(context, layoutId, dataList);
        }
        @Override
        public void handle(GeneralRecyclerViewHolder holder, Goods data, final int position) {
            ((TextView)holder.getView(R.id.item_goods_textView_description)).setText(data.getDescription());
            ((TextView)holder.getView(R.id.item_goods_textView_price)).setText(String.valueOf(data.getPrice()));
            ((TextView)holder.getView(R.id.item_goods_textView_location_value)).setText(String.valueOf(data.getLocation()));
            ((TextView)holder.getView(R.id.item_goods_textView_name)).setText(String.valueOf(data.getGoodsName()));
            ((TextView)holder.getView(R.id.item_goods_textView_need_num)).setText(String.valueOf(data.getFansNumber()));
            ((TextView)holder.getView(R.id.item_goods_textView_time_value)).setText(String.valueOf(data.getTime()));
            ((TextView)holder.getView(R.id.item_goods_textView_seller_name)).setText(String.valueOf(data.getSellerName()));
//            Glide.with(Objects.requireNonNull(getContext())).load(data.getTitleImage()).into(((ImageView)holder.getView(R.id.item_goods_imageView_commodity)));
            holder.getView(R.id.item_goods_frameLayout_background).setOnClickListener(v ->
                    Intenter.jumpActivity(getContext(), GoodsDetailActivity.class, "Goods", goods.get(position)));
        }
        @Override
        public SparseArray<Integer> setType(SparseArray<Integer> layoutId_position) {
            return null;
        }

    }
}
