package io.github.grooters.idles.view.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
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
import io.github.grooters.idles.view.activity.MainActivity;
import io.github.grooters.idles.view.activity.PersonalItemActivity;
import io.github.grooters.idles.view.dialog.ChooseLocationDialog;
import io.github.grooters.idles.view.dialog.NickNameDialog;
import io.github.grooters.idles.view.dialog.ResumeDialog;
import io.github.grooters.idles.view.interfac.IPersonalV;
import io.github.grooters.idles.view.interfac.IUserDataV;
import io.github.grooters.idles.view.widget.LoadingWidget;

public class MyDataFragment extends BaseFragment implements IPersonalV {

    @BindView(R.id.fragment_myData_imageView_avatar)
    ImageView avatarImageView;
    @BindView(R.id.fragment_myData_textView_basic_genderValue)
    TextView genderValueTextView;
    @BindView(R.id.fragment_myData_textView_basic_locationValue)
    TextView locationValueTextView;
    @BindView(R.id.fragment_myData_textView_basic_resumeValue)
    TextView resumeValueTextView;
    @BindView(R.id.fragment_myData_textView_basic_nickNameValue)
    TextView nickNameValueTextView;
    @BindView(R.id.fragment_myData_textView_basic_numberValue)
    TextView numberValueTextView;
    @BindView(R.id.fragment_myData_textView_basic__collegeValue)
    TextView universityTextView;
    @BindView(R.id.fragment_myData_loadingWidget)
    LoadingWidget loadingWidget;
    @BindView(R.id.fragment_myData_textView_integrity)
    TextView integrityTextView;

    private IPersonalP iPersonalP;

    @OnClick(R.id.fragment_myData_constrainLayout_basic_nickName)
    void nickNameBasicConstrainLayoutOnClick(){
        NickNameDialog nickNameDialog = new NickNameDialog();
        nickNameDialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "NickNameDialog");
    }

    @OnClick(R.id.fragment_myData_constrainLayout_basic_resume)
    void resumeBasicConstrainLayoutOnClick(){
        ResumeDialog resumeDialog = new ResumeDialog(this);
        resumeDialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "ResumeDialog");
    }

    @OnClick(R.id.fragment_myData_constrainLayout_basic_location)
    void  locationBasicConstrainLayoutOnClick(){
        ChooseLocationDialog chooseLocationDialog =  new ChooseLocationDialog(this);
        chooseLocationDialog.show(getFragmentManager(), "ChooseLocationDialog");
    }

    @OnClick(R.id.fragment_myData_imageButton_return)
    void returnImageButtonOnClick(){
        Intenter.jumpActivity(getContext(), MainActivity.class, "pageId", MainActivity.personal);
        Objects.requireNonNull(getActivity()).finish();
    }

    @OnClick(R.id.fragment_myData_constrainLayout_basic_college)
    void collectConstrainLayout(){
        ((PersonalItemActivity) Objects.requireNonNull(getActivity()))
                .replaceFragment(R.id.activity_personal_item_frameLayout, new ChooseUniversityFragment(), "ChooseUniversityFragment");
    }

    @Override
    public void provideLocation(String location) {
        locationValueTextView.setText(location);
    }

    @Override
    int getLayout() {
        return R.layout.fragment_my_data;
    }

    @Override
    void initView(View view) {
        iPersonalP = new PersonalP(getContext(), this);
        iPersonalP.getUserData(ConfigP.getUser(getContext()).getTokenNumber());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData(User user) {
        List<String> infoIntegrity = new ArrayList<>();
        if(user.getAvatarUrl() != null)
            infoIntegrity.add(user.getAvatarUrl());
        if(user.getGender() != null)
            infoIntegrity.add(user.getGender());
        if(user.getLocation() != null)
            infoIntegrity.add(user.getLocation());
        if(user.getResume() != null)
            infoIntegrity.add(user.getResume());
        if(user.getName() != null)
            infoIntegrity.add(user.getName());
        if(user.getUserNumber() != null)
            infoIntegrity.add(user.getUserNumber());
        if(user.getUniversity() != null)
            infoIntegrity.add(user.getUniversity());
        switch (infoIntegrity.size()){
            case 0:
                integrityTextView.setText("完整度0%");
                break;
            case 1:
                integrityTextView.setText("完整度14%");
                break;
            case 2:
                integrityTextView.setText("完整度29%");
                break;
            case 3:
                integrityTextView.setText("完整度43%");
                break;
            case 4:
                integrityTextView.setText("完整度57%");
                break;
            case 5:
                integrityTextView.setText("完整度71%");
                break;
            case 6:
                integrityTextView.setText("完整度86%");
                break;
            case 7:
                integrityTextView.setText("完整度100%");
                break;
        }
        genderValueTextView.setText(user.getGender());
        locationValueTextView.setText(user.getLocation());
        resumeValueTextView.setText(user.getResume());
        nickNameValueTextView.setText(user.getName());
        numberValueTextView.setText(user.getUserNumber());
        universityTextView.setText(user.getUniversity());
    }

    @Override
    public void setResume(String resume) {
        resumeValueTextView.setText(resume);
    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void unAttach() {
        iPersonalP = null;
    }

    @Override
    public void startLoading() {loadingWidget.start(); }

    @Override
    public void stopLoading() { loadingWidget.stop();}

    @Override
    public void showMessage(String message) {
        Toaster.shortShow(getContext(), message);
    }

    @Override
    public void loginInvalid() {
        invalid();
    }
}
