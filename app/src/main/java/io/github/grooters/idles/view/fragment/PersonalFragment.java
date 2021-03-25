package io.github.grooters.idles.view.fragment;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.util.Objects;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.grooters.idles.R;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.presenter.ConfigP;
import io.github.grooters.idles.presenter.PersonalP;
import io.github.grooters.idles.presenter.interfac.IPersonalP;
import io.github.grooters.idles.utils.Intenter;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.activity.PersonalItemActivity;
import io.github.grooters.idles.view.interfac.IPersonalV;
import io.github.grooters.idles.view.widget.LoadingWidget;

public class PersonalFragment extends BaseFragment implements IPersonalV {

    public static final String MY_ORDER = "MY_ORDER";
    public static final String MY_PUSH = "MY_PUSH";
    public static final String MY_COLLECTION = "MY_COLLECTION";
    public static final String MY_INFO= "MY_INFO";
    public static final String MY_DATA= "MY_DATA";
    @BindView(R.id.fragment_personal_linearLayout_myOrder)
    LinearLayout myOrderLinearLayout;
    @BindView(R.id.fragment_personal_linearLayout_myPush)
    LinearLayout myPushLinearLayout;
    @BindView(R.id.fragment_personal_LinearLayout_myCollection)
    LinearLayout myCollectionLinearLayout;
    @BindView(R.id.fragment_personal_textView_loading)
    LoadingWidget loadingWidget;
    @BindView(R.id.fragment_personal_imageView_avatar)
    ImageView avatarImageView;
    @BindView(R.id.fragment_personal_textView_gradeValue)
    TextView gradValueTextView;
    @BindView(R.id.fragment_personal_textView_timeValue)
    TextView timeValueTextView;
    @BindView(R.id.fragment_personal_textView_orderNumber)
    TextView orderNumberTextView;
    @BindView(R.id.fragment_personal_textView_pushNumber)
    TextView pushNumberTextView;
    @BindView(R.id.fragment_personal_textView_level)
    TextView levelTextView;

    private IPersonalP iPersonalP;
    private String type;
    private SparseArray<String> level = new SparseArray<>();

    @OnClick(R.id.fragment_personal_linearLayout_myOrder)
    void myOrderLinearLayoutOnClick(){
        type = MY_ORDER;
        Intenter.jumpActivity(getContext(), PersonalItemActivity.class, "type", type);
    }

    @OnClick(R.id.fragment_personal_linearLayout_myPush)
    void myPushLinearLayoutOnClick(){
        type = MY_PUSH;
        Intenter.jumpActivity(getContext(), PersonalItemActivity.class, "type", type);
    }

    @OnClick(R.id.fragment_personal_LinearLayout_myCollection)
    void myCollectionLinearLayoutOnClick(){
        type = MY_COLLECTION;
        Intenter.jumpActivity(getContext(), PersonalItemActivity.class, "type", type);
    }

    @OnClick(R.id.fragment_personal_LinearLayout_myData)
    void myDataLinearLayoutOnClick(){
        type = MY_DATA;
        Intenter.jumpActivity(getContext(), PersonalItemActivity.class, "type", type);
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
    public void setResume(String resume) { }

    @Override
    public void showMessage(String message) {
        Toaster.shortShow(getContext(), message);
    }

    @Override
    public void loginInvalid() {
        invalid();
    }

    @Override
    int getLayout() {
        return R.layout.fragment_personal;
    }

    @Override
    void initView(View view) {
        iPersonalP = new PersonalP(getContext(), this);
        iPersonalP.getUserData(ConfigP.getUser(getContext()).getTokenNumber());
        level.put(0, "初出茅庐");
        level.put(1, "崭露头角");
        level.put(2, "购物狂人");
    }

    @Override
    public void initData(User user) {
        String[] myGoodsNumber = user.getMyGoodsPushNumber();
        String[] myWorksNumber = user.getMyWorksPushNumber();
        int goodsCount = myGoodsNumber == null ? 0 : myGoodsNumber.length;
        int worksCount = myWorksNumber == null ? 0 : myWorksNumber.length;
        pushNumberTextView.setText(String.valueOf(goodsCount + worksCount));
        myGoodsNumber = user.getMyGoodsOrderNumber();
        myWorksNumber = user.getMyWorksOrderNumber();
        goodsCount = myGoodsNumber == null ? 0 : myGoodsNumber.length;
        worksCount = myWorksNumber == null ? 0 : myWorksNumber.length;
        orderNumberTextView.setText(String.valueOf(goodsCount + worksCount));
        gradValueTextView.setText(user.getGrade());
        String levelText = user.getLevel() + " " + level.get(user.getLevel());
        levelTextView.setText(levelText);
        timeValueTextView.setText(String.valueOf(user.getTime()));
    }

    @Override
    public void setAvatar(String url) {
        Glide.with(Objects.requireNonNull(getContext())).load(url).into(avatarImageView);
    }

    @Override
    public void setDefaultAvatar() {
        Glide.with(Objects.requireNonNull(getContext())).load(R.mipmap.ic_avatar).into(avatarImageView);
    }

    @Override
    public void provideLocation(String location) { }
}
