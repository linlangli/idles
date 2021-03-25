package io.github.grooters.idles.view.fragment;

import android.content.Context;
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
import io.github.grooters.idles.bean.Works;
import io.github.grooters.idles.presenter.ConfigP;
import io.github.grooters.idles.presenter.WorksP;
import io.github.grooters.idles.presenter.interfac.IWorksP;
import io.github.grooters.idles.utils.Intenter;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.activity.AccountActivity;
import io.github.grooters.idles.view.interfac.IListOfGoodsWorksV;
import io.github.grooters.idles.view.widget.LoadingWidget;

public class ListOfWorksFragment extends BaseFragment implements IListOfGoodsWorksV<Works> {

    @BindView(R.id.worksListFragment_textView_nullData)
    TextView nullDadaTextView;
    @BindView(R.id.worksFragment_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.worksListFragment_textView_loading)
    LoadingWidget loadingWidget;

    private IWorksP iWorksP;
    private List<Works> works;

    @Override
    int getLayout() {
        return R.layout.fragment_works_list;
    }

    @Override
    void initView(View view) {
        iWorksP = new WorksP(getContext(), this);
        iWorksP.getWorksList();
    }

    @Override
    public void initRecyclerView(List<Works> data) {
        nullDadaTextView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyRecyclerViewAdapter(getActivity(), R.layout.item_works, data));
        this.works = data;
    }

    @Override
    public void initNullData() {
        nullDadaTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void loginInvalid() {
        ConfigP.deleteUser(Objects.requireNonNull(getContext()));
        Toaster.shortShow(getContext(), "登录暑校，请重新登录");
        Intenter.jumpActivity(getContext(), AccountActivity.class);
    }

    @Override
    public void unAttach() {
        iWorksP = null;
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
    public void onDestroy() {
        super.onDestroy();
        iWorksP.unAttach();
    }

    class MyRecyclerViewAdapter extends GeneralRecyclerViewAdapter<Works> {
        MyRecyclerViewAdapter(Context context, int layoutId, List<Works> dataList) {
            super(context, layoutId, dataList);
        }
        @Override
        public void handle(GeneralRecyclerViewHolder holder, Works data, final int position) {
            ((TextView)holder.getView(R.id.item_works_textView_description)).setText(data.getDescription());
            ((TextView)holder.getView(R.id.item_works_textView_price_value)).setText(String.valueOf(data.getPrice()));
            ((TextView)holder.getView(R.id.item_works_textView_seller_name)).setText(String.valueOf(data.getSellerName()));
            ((TextView)holder.getView(R.id.item_works_textView_time_min)).setText(String.valueOf(data.getStartTime()));
            ((TextView)holder.getView(R.id.item_works_textView_time_max)).setText(String.valueOf(data.getEndTime()));
            ((TextView)holder.getView(R.id.item_works_textView_location_value)).setText(String.valueOf(data.getLocation()));
            holder.getView(R.id.item_works_button_receipt).setOnClickListener(v -> {
//                    iWorksP.sendTransactionRequest(position, works, user.getNumber());
            });
        }
        @Override
        public SparseArray<Integer> setType(SparseArray<Integer> layoutId_position) {
            return null;
        }
    }
}
