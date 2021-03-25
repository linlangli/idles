package io.github.grooters.idles.view.dialog;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import java.util.Objects;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.grooters.idles.R;
import io.github.grooters.idles.bean.Works;
import io.github.grooters.idles.presenter.ConfigP;
import io.github.grooters.idles.presenter.WorksP;
import io.github.grooters.idles.presenter.interfac.IWorksP;
import io.github.grooters.idles.utils.Intenter;
import io.github.grooters.idles.utils.ResourcEr;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.activity.AccountActivity;
import io.github.grooters.idles.view.interfac.IWorksV;

public class WorksDialog extends BaseDialog implements IWorksV {

    @BindView(R.id.dialog_works_editText_description)
    EditText descriptionEditText;
    @BindView(R.id.dialog_works_editText_location)
    EditText locationEditText;
    @BindView(R.id.dialog_works_editText_name)
    EditText nameEditText;
    @BindView(R.id.dialog_works_editText_price)
    EditText priceEditText;
    @BindView(R.id.dialog_works_textView_startTime)
    TextView startTimeTextView;
    @BindView(R.id.dialog_works_textView_endTime)
    TextView endTimeTextView;
    @BindView(R.id.dialog_works_button_push)
    TextView pushButton;
    @BindView(R.id.dialog_works_textView_loading)
    TextView loadingTextView;

    private IWorksP iWorksP;
    private ChooseTimeDialog chooseTimeDialog;

    private TextView timeTextView;

    @OnClick(R.id.dialog_works_button_push)
    void pushButtonOnClick(){
        Works works = new Works();
        if(isEmpty(descriptionEditText, nameEditText, priceEditText)) {
            Toaster.shortShow(getContext(), "信息填写不完整");
            return;
        }

        works.setStartTime(startTimeTextView.getText().toString());
        works.setEndTime(endTimeTextView.getText().toString());
        works.setPrice(Float.valueOf(priceEditText.getText().toString()));
        works.setLocation(locationEditText.getText().toString());
        works.setWorksName(nameEditText.getText().toString());
        works.setSellerName(ConfigP.getUser(getContext()).getName());
        works.setSellerNumber(ConfigP.getUser(getContext()).getUserNumber());
        works.setDescription(descriptionEditText.getText().toString());
        works.setWorksNumber(String.valueOf(System.currentTimeMillis()));
        iWorksP.pushWorks(ConfigP.getUser(getContext()).getTokenNumber(), works);
    }

    @Override
    public void unAttach() {
        iWorksP = null;
    }

    @Override
    public void startLoading() {
        loadingTextView.setVisibility(View.VISIBLE);
        pushButton.setVisibility(View.GONE);
    }

    @Override
    public void stopLoading() {
        pushButton.setVisibility(View.VISIBLE);
        loadingTextView.setVisibility(View.GONE);
        Toaster.shortShow(getContext(), "上传成功");
    }

    @Override
    public void setStartOrEndTime(String time) {
        timeTextView.setText(time);
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d("onPause");
    }

    @Override
    public void loginInvalid() {
        ConfigP.deleteUser(Objects.requireNonNull(getContext()));
        Toaster.shortShow(getContext(), "登录暑校，请重新登录");
        Intenter.jumpActivity(getContext(), AccountActivity.class);
    }

    @Override
    public void showMessage(String message) {
        Toaster.shortShow(getContext(), message);
    }

    @Override
    public int getLayout() {
        return R.layout.dialog_works;
    }

    @Override
    public void initView(View view) {
        Logger.d("initView");
        iWorksP = new WorksP(getContext(), this);
        startTimeTextView.setOnClickListener(v -> {
            timeTextView = startTimeTextView;
            if(chooseTimeDialog == null)
                chooseTimeDialog = new ChooseTimeDialog(this);
            chooseTimeDialog.show(Objects.requireNonNull(getFragmentManager()), "ChooseTimeDialog");
        });
        endTimeTextView.setOnClickListener(v -> {
            timeTextView = endTimeTextView;
            if(chooseTimeDialog == null)
                chooseTimeDialog = new ChooseTimeDialog(this);
            chooseTimeDialog.show(Objects.requireNonNull(getFragmentManager()), "ChooseTimeDialog");
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d("onResume");
        setSize((int) ResourcEr.dp2px(380), (int) ResourcEr.dp2px(360));
    }
}
