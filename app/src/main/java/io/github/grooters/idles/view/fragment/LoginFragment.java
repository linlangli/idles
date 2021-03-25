package io.github.grooters.idles.view.fragment;

import android.app.Application;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

import java.util.Objects;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.grooters.idles.R;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.presenter.AccountP;
import io.github.grooters.idles.presenter.interfac.IAccountP;
import io.github.grooters.idles.utils.Constant;
import io.github.grooters.idles.utils.Encrypter;
import io.github.grooters.idles.utils.FileIOer;
import io.github.grooters.idles.utils.Intenter;
import io.github.grooters.idles.utils.Jsoner;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.activity.AccountActivity;
import io.github.grooters.idles.view.activity.MainActivity;
import io.github.grooters.idles.view.dialog.ConfigDialog;
import io.github.grooters.idles.view.dialog.UserDataDialog;
import io.github.grooters.idles.view.interfac.ILoginV;
import io.github.grooters.idles.view.widget.LoadingWidget;

public class LoginFragment extends BaseFragment implements ILoginV {

    @BindView(R.id.fragment_login_editText_number)
    public EditText numberEditText;
    @BindView(R.id.fragment_login_editText_password)
    public EditText passwordEditText;
    @BindView(R.id.fragment_login_textView_loading)
    public LoadingWidget loadingText;
    @BindView(R.id.fragment_login_imageView_showOrHide)
    public ImageView showIrHideImageView;
    @BindView(R.id.fragment_login_imageView_remember)
    public ImageView rememberImageView;
    @BindView(R.id.fragment_login_imageView_clear)
    public ImageView clearImageView;

    private IAccountP iAccountP;

    private boolean isShowPassword;

    private ConfigDialog configDialog;

    public static final int FIND_PASSWORD_FRAGMENT = 2;

    public static final int REGISTER_ACCOUNT_FRAGMENT = 1;

    @Override
    void initView(View view) {
        iAccountP = new AccountP(getContext(), this);
        iAccountP.judgeAccount();
        iAccountP.initServerUrl();
        numberEditText.addTextChangedListener(iAccountP.getTextWatcher());
    }

    @OnClick(R.id.fragment_login_imageView_clear)
    void clearOnClick(){
        numberEditText.setText(null);
    }

    @OnClick(R.id.fragment_login_imageView_showOrHide)
    void showOrHideOnClick(){
        isShowPassword = !isShowPassword;
        iAccountP.showOriHidePassword(isShowPassword);
    }

    @OnClick(R.id.fragment_login_imageView_remember)
    void rememberOnClick(){
        iAccountP.rememberAccount(iAccountP.getIsRemember(), numberEditText.getText().toString(), passwordEditText.getText().toString());
    }

    @OnClick(R.id.fragment_login_button_login)
    void loginButtonOnClick(){
        iAccountP.login(numberEditText.getText().toString(), passwordEditText.getText().toString());
    }

    @OnClick(R.id.fragment_login_textView_visitor)
    void visitorTextViewOnClick(){
        iAccountP.loginAsVisitor();
    }

    @OnClick(R.id.fragment_login_imageView_logo)
    void logonImageView(){
        iAccountP.config();
    }

    @OnClick(R.id.fragment_login_textView_register)
    void registerTextViewOnClick(){
        ((AccountActivity) Objects.requireNonNull(getActivity()))
                .replaceFragment(R.id.login_frame_fragment_container, new RegisterFragment(REGISTER_ACCOUNT_FRAGMENT), "RegisterFragment");
//        User user = new User();
//        UserDataDialog userDataDialog = new UserDataDialog(user);
//        userDataDialog.show(Objects.requireNonNull(getFragmentManager()), "UserDataDialog");
    }

    @Override
    public void showPassword() {
        showIrHideImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_password_press, null));
        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    @Override
    public void hidePassword() {
        showIrHideImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_password, null));
        passwordEditText.setInputType(129);
    }

    @Override
    public void changeRememberIcon() {
        rememberImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_account_press, null));
    }

    @Override
    public void forgetAccount() {
        rememberImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_account, null));
    }

    @Override
    public void showClearButton() {
        clearImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideClearButton() {
        clearImageView.setVisibility(View.GONE);
    }

    @Override
    public void jumpToMainActivity() {
        Intenter.jumpActivity(getActivity(), MainActivity.class);
        iAccountP.unAttach();
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void loginInvalid() { }

    @Override
    public void initAccount(String number, String password) {
        numberEditText.setText(number);
        passwordEditText.setText(password);
    }

    @Override
    public void showConfigDialog() {
        if(configDialog == null)
            configDialog = new ConfigDialog();
        configDialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "configDialog");
    }

    @Override
    public void startLoading() {
        loadingText.start();
    }

    @Override
    public void stopLoading() {
        loadingText.stop();
    }

    @Override
    public void showMessage(String message) {
        Toaster.shortShow(getContext(), message);
    }

    @Override
    public void unAttach() {
        iAccountP = null;
    }

    @Override
    int getLayout() {
        return R.layout.fragment_login;
    }
}
