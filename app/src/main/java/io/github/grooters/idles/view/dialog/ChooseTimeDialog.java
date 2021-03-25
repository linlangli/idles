package io.github.grooters.idles.view.dialog;

import android.content.Context;
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
import java.util.Objects;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.grooters.idles.R;
import io.github.grooters.idles.base.GeneralRecyclerViewAdapter;
import io.github.grooters.idles.base.GeneralRecyclerViewHolder;
import io.github.grooters.idles.presenter.WorksP;
import io.github.grooters.idles.presenter.interfac.IWorksP;
import io.github.grooters.idles.utils.ResourcEr;
import io.github.grooters.idles.utils.Toaster;

public class ChooseTimeDialog extends BaseDialog {

    @BindView(R.id.dialog_chooseTime_recyclerView_year)
    RecyclerView yearRecyclerView;
    @BindView(R.id.dialog_chooseTime_recyclerView_month)
    RecyclerView monthRecyclerView;
    @BindView(R.id.dialog_chooseTime_recyclerView_day)
    RecyclerView dayRecyclerView;

    private String year, month, day;

    private WorksDialog worksDialog;
    private IWorksP iWorksP;

    ChooseTimeDialog(WorksDialog worksDialog){
        this.worksDialog = worksDialog;
    }

    @OnClick(R.id.dialog_chooseTime_button_ok)
    void okButtonOnClick(){
        if(year.equals("") || month.equals("") || day.equals("")){
            Toaster.shortShow(getContext(), "请完整选择时间");
        }else{
            iWorksP.provideStartOrEndTime(year+"年 "+month+"月 "+day+"日");
            dismiss();
        }
    }

    @Override
    public int getLayout() {
        return R.layout.dialog_choose_time;
    }

    @Override
    public void initView(View view) {
        iWorksP = new WorksP(getContext(), worksDialog);
        List<String> years = new ArrayList<>();
        List<String> months = new ArrayList<>();
        List<String> days = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            years.add(String.valueOf(2020 + i));
        }
        for(int i = 0; i < 12; i++){
            months.add(String.valueOf(1 + i));
        }
        for(int i = 0; i < 31; i++){
            days.add(String.valueOf(1 + i));
        }
        yearRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        monthRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dayRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        yearRecyclerView.setAdapter(new MyRecyclerViewAdapter(getContext(), "YEAR", R.layout.item_time, years));
        monthRecyclerView.setAdapter(new MyRecyclerViewAdapter(getContext(), "MONTH", R.layout.item_time, months));
        dayRecyclerView.setAdapter(new MyRecyclerViewAdapter(getContext(), "DAY", R.layout.item_time, days));
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
        setSize( ResourcEr.getScreenWidth(Objects.requireNonNull(getActivity())), (int) ResourcEr.dp2px(280));
        setTransaction();
    }

    class MyRecyclerViewAdapter extends GeneralRecyclerViewAdapter<String> {
        private String type;
        MyRecyclerViewAdapter(Context context, String type, int layoutId, List<String> dataList) {
            super(context, layoutId, dataList);
            this.type = type;
        }
        @Override
        public void handle(GeneralRecyclerViewHolder holder, String data, final int position) {
                ((TextView) holder.getView(R.id.item_time_textView)).setTextSize(15);
                ((TextView) holder.getView(R.id.item_time_textView)).setText(data);
                (holder.getView(R.id.item_time_textView)).setOnClickListener(v -> {
                ((TextView) holder.getView(R.id.item_time_textView)).setTextSize(18);
                ((TextView) holder.getView(R.id.item_time_textView)).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                switch (type){
                    case "YEAR":
                        year =  ((TextView) holder.getView(R.id.item_time_textView)).getText().toString();
                        break;
                    case "MONTH":
                        month =  ((TextView) holder.getView(R.id.item_time_textView)).getText().toString();
                        break;
                    case "DAY":
                        day =  ((TextView) holder.getView(R.id.item_time_textView)).getText().toString();
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
