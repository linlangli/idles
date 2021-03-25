package io.github.grooters.idles.view.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Objects;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.grooters.idles.R;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.net.Server;
import io.github.grooters.idles.presenter.AccountP;
import io.github.grooters.idles.presenter.interfac.IAccountP;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.activity.AccountActivity;
import io.github.grooters.idles.view.dialog.UserDataDialog;
import io.github.grooters.idles.view.interfac.IRegisterV;
import io.github.grooters.idles.view.widget.LoadingWidget;

public class RegisterFragment extends BaseFragment implements IRegisterV {

    private IAccountP iAccountP;

    @BindView(R.id.fragment_register_editText_password)
    public EditText passwordEditText;
    @BindView(R.id.fragment_register_editText_secondPassword)
    public EditText passwordSecondEditText;
    @BindView(R.id.fragment_register_editText_email)
    public EditText emailEditText;
    @BindView(R.id.fragment_register_button_register)
    public Button registerButton;
    @BindView(R.id.fragment_register_textView_verification)
    public TextView verificationTextView;
    @BindView(R.id.fragment_register_widget_loading)
    public LoadingWidget loadingWidget;
    @BindView(R.id.fragment_register_editText_verification)
    public EditText verificationEditText;

    private int type;

    RegisterFragment(int type){
        this.type = type;
    }

    @OnClick(R.id.fragment_register_textView_verification)
    void verificationTextViewOnClick(){
        iAccountP.getVerification(emailEditText.getText().toString(), Server.email, Server.emailKey);
    }

    @OnClick(R.id.fragment_register_button_register)
     void registerButtonOnClick(){
        iAccountP.register(type, emailEditText.getText().toString(),
                passwordEditText.getText().toString(), passwordSecondEditText.getText().toString());
    }

    @OnClick(R.id.fragment_register_textView_backLogin)
    void backLoginTextViewOnClick(){
        ((AccountActivity) Objects.requireNonNull(getActivity()))
                .replaceFragment(R.id.login_frame_fragment_container, new LoginFragment(), "LoginFragment");
    }

    @OnClick(R.id.fragment_register_textView_verify)
    void verifyTextView(){
        iAccountP.verify(emailEditText.getText().toString(), verificationEditText.getText().toString());
    }

    @Override
    public void verifySuccess() {
        passwordEditText.setVisibility(View.VISIBLE);
        passwordSecondEditText.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void verifyFailure() {
        passwordEditText.setVisibility(View.GONE);
        passwordSecondEditText.setVisibility(View.GONE);
        passwordEditText.setText("");
        passwordSecondEditText.setText(" ");
    }

    @Override
    public void showVerificationSuccess(String message) {
        verificationTextView.setTextColor(getResources().getColor(R.color.fragment_register_textView_press,null));
    }

    @Override
    public void setRegisterButtonText(String text) {
        registerButton.setText(text);
    }

    @Override
    public void initVerificationTextView() {
        verificationTextView.setTextColor(getResources().getColor(R.color.fragment_register_textView_verification,null));
    }

    @Override
    public void showRegisterSuccess(User data) {
        iAccountP.unAttach();
        UserDataDialog userDataDialog = new UserDataDialog(data);
        userDataDialog.show(getFragmentManager(), "UserDataDialog");
    }

    @Override
    public void loginInvalid() { }

    @Override
    int getLayout() {
        return R.layout.fragment_register;
    }

    @Override
    void initView(View view) {
        iAccountP = new AccountP(getContext(), this);
    }

    @Override
    public void unAttach() {
        iAccountP = null;
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
        iAccountP.unAttach();
    }
}
