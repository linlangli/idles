package io.github.grooters.idles.view.dialog;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.grooters.idles.R;
import io.github.grooters.idles.base.GeneralRecyclerViewAdapter;
import io.github.grooters.idles.base.GeneralRecyclerViewHolder;
import io.github.grooters.idles.entity.Province;
import io.github.grooters.idles.presenter.PersonalP;
import io.github.grooters.idles.presenter.interfac.IPersonalP;
import io.github.grooters.idles.utils.ResourcEr;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.fragment.MyDataFragment;

public class ChooseLocationDialog extends BaseDialog {
    @BindView(R.id.dialog_chooseLocation_recyclerView_province)
    RecyclerView provinceRecyclerView;
    @BindView(R.id.dialog_chooseLocation_recyclerView_city)
    RecyclerView cityRecyclerView;
    private String province, city;
    private IPersonalP iPersonalP;
    private Map<String, Province> provinces;
    
    ChooseLocationDialog(UserDataDialog userDataDialog) {
        iPersonalP = new PersonalP(getContext(), userDataDialog);
    }

    public ChooseLocationDialog(MyDataFragment myDataFragment) {
        iPersonalP = new PersonalP(getContext(), myDataFragment);
    }

    @OnClick(R.id.dialog_chooseLocation_button_ok)
    void okButtonOnClick(){
        if(province.equals("") || city.equals("")){
            Toaster.shortShow(getContext(), "请完整选择地点");
        }else{
            iPersonalP.setLocation(province, city);
            dismiss();
        }
    }

    @Override
    public int getLayout() {
        return R.layout.dialog_choose_location;
    }

    @Override
    public void initView(View view) {
        AssetManager assetManager = Objects.requireNonNull(getContext().getAssets());
        provinces = iPersonalP.getProvinces(assetManager);
        List<String> provinceNames = new ArrayList<>(provinces.keySet());
        provinceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cityRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        provinceRecyclerView.setAdapter(new MyRecyclerViewAdapter(getContext(), "PROVINCE", R.layout.item_location, provinceNames));
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        WindowManager.LayoutParams params = Objects.requireNonNull(window).getAttributes();
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onResume() {
        super.onResume();
        setSize( ResourcEr.getScreenWidth(Objects.requireNonNull(getActivity())), (int) ResourcEr.dp2px(300));
        setTransaction();
    }

    class MyRecyclerViewAdapter extends GeneralRecyclerViewAdapter<String> {
        private String type;
        private TextView temp;
        MyRecyclerViewAdapter(Context context, String type, int layoutId, List<String> dataList) {
            super(context, layoutId, dataList);
            this.type = type;
        }
        @Override
        public void handle(GeneralRecyclerViewHolder holder, String data, final int position) {
                ((TextView) holder.getView(R.id.item_location_textView)).setTextSize(15);
                ((TextView) holder.getView(R.id.item_location_textView)).setText(data);
                (holder.getView(R.id.item_location_textView)).setOnClickListener(v -> {
                    // 恢复上一次操作的item状态
                    if( temp != null ){
                        temp.setTextSize(15);
                        temp.setTypeface(Typeface.DEFAULT);
                    }
                    ((TextView) holder.getView(R.id.item_location_textView)).setTextSize(18);
                    ((TextView) holder.getView(R.id.item_location_textView)).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    // 将该次操作缓存
                    temp = holder.getView(R.id.item_location_textView);
                    switch (type){
                        case "PROVINCE":
                            province =  data;
                            Map<String, List<String>> cities = iPersonalP.getCities(provinces.get(data));
                            // 加载该省份对应的城市
                            cityRecyclerView.setAdapter(new MyRecyclerViewAdapter(getContext(), "CITY", R.layout.item_location, cities.get(data)));
                            break;
                        case "CITY":
                            city = data;
                            break;
                    }
            });
        }
        @Override
        public SparseArray<Integer> setType(SparseArray<Integer> layoutId_position) {
            return null;
        }

    }
}
