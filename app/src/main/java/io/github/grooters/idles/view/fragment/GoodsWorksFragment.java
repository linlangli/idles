package io.github.grooters.idles.view.fragment;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Objects;
import butterknife.BindView;
import io.github.grooters.idles.R;
import io.github.grooters.idles.base.GeneralRecyclerViewAdapter;
import io.github.grooters.idles.base.GeneralRecyclerViewHolder;
import io.github.grooters.idles.bean.Goods;
import io.github.grooters.idles.bean.Works;
import io.github.grooters.idles.presenter.PersonalP;
import io.github.grooters.idles.presenter.interfac.IPersonalP;
import io.github.grooters.idles.utils.Intenter;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.activity.GoodsDetailActivity;
import io.github.grooters.idles.view.interfac.IGoodsWorksV;
import io.github.grooters.idles.view.widget.LoadingWidget;

public class GoodsWorksFragment extends BaseFragment implements View.OnClickListener, IGoodsWorksV {

    @BindView(R.id.fragment_goodsWorks_imageButton_goods)
    ImageButton goodsImageButton;
    @BindView(R.id.fragment_goodsWorks_imageButton_works)
    ImageButton worksImageButton;
    @BindView(R.id.fragment_goodsWorks_textView_empty)
    TextView emptyTextView;
    @BindView(R.id.fragment_goodsWorks_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.goodsWorksFragment_textView_loading)
    LoadingWidget loadingWidget;

    private boolean isGoods;
    private String type;
    private IPersonalP iPersonalP;

    @Override
    int getLayout() {
        return R.layout.fragment_goods_works;
    }

    @Override
    void initView(View view) {
        isGoods = true;
        goodsImageButton.setImageDrawable(Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_goods_press));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        iPersonalP = new PersonalP(getContext(), this);
        type = Objects.requireNonNull(getArguments()).getString("type");
        goodsImageButton.setOnClickListener(this);
        worksImageButton.setOnClickListener(this);
        iPersonalP.getDataList(type, isGoods);
    }

    @Override
    public void onClick(View v) {
        if (isGoods && v.getId() == R.id.fragment_goodsWorks_imageButton_works){
            goodsImageButton.setImageDrawable(Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_goods));
            worksImageButton.setImageDrawable(getContext().getDrawable(R.drawable.ic_works_press));
            isGoods = !isGoods;
        }else if(!isGoods && v.getId() == R.id.fragment_goodsWorks_imageButton_goods){
            worksImageButton.setImageDrawable(Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_works));
            goodsImageButton.setImageDrawable(getContext().getDrawable(R.drawable.ic_goods_press));
            isGoods = !isGoods;
        }
        iPersonalP.getDataList(type, isGoods);
    }

    @Override
    public void initRecyclerViewWithGoods(List<Goods> goods) {
        if(goods == null){
            Logger.d("initRecyclerViewWithGoods_goods == null");
        }
        recyclerView.setAdapter(new MyGoodsAdapter(getContext(), R.layout.item_goods, goods));
    }

    @Override
    public void initRecyclerViewWithWorks(List<Works> works) {
        recyclerView.setAdapter(new MyWorksAdapter(getContext(), R.layout.item_works, works));
    }

    @Override
    public void showEmptyTextView() {
        emptyTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyTextView() {
        emptyTextView.setVisibility(View.GONE);
    }

    @Override
    public void unAttach() {
        iPersonalP = null;
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

    @Override
    public void loginInvalid() {
        invalid();
    }

    class MyGoodsAdapter extends GeneralRecyclerViewAdapter<Goods> {
        private List<Goods> goods;
        MyGoodsAdapter(Context context, int layoutId, List<Goods> goods) {
            super(context, layoutId, goods);
            this.goods = goods;
        }
        @Override
        public void handle(GeneralRecyclerViewHolder holder, final Goods data, final int position) {
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

    class MyWorksAdapter extends GeneralRecyclerViewAdapter<Works> {
        MyWorksAdapter(Context context, int layoutId, List<Works> dataList) {
            super(context, layoutId, dataList);
        }
        @Override
        public void handle(GeneralRecyclerViewHolder holder, Works data, int position) {
            ((TextView)holder.getView(R.id.item_works_textView_description)).setText(data.getDescription());
            ((TextView)holder.getView(R.id.item_works_textView_price)).setText(String.valueOf(data.getPrice()));
            ((TextView)holder.getView(R.id.item_works_textView_seller_name)).setText(String.valueOf(data.getSellerName()));
            ((TextView)holder.getView(R.id.item_works_textView_time_min)).setText(String.valueOf(data.getStartTime()));
            ((TextView)holder.getView(R.id.item_works_textView_time_max)).setText(String.valueOf(data.getEndTime()));
        }
        @Override
        public SparseArray<Integer> setType(SparseArray<Integer> layoutId_position) {
            return null;
        }
    }
}
