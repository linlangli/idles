package io.github.grooters.idles.presenter.interfac;

import android.text.TextWatcher;
import android.view.KeyEvent;

public interface IAccountP extends IBaseP{
    void showOriHidePassword(boolean b);

    void rememberAccount(boolean b, String number, String password);

    TextWatcher getTextWatcher();

    void login(String number, String password);

    void loginAsVisitor();

    void judgeAccount();

    boolean getIsRemember();

    boolean endActivity(int keyCode, KeyEvent event);

    void config();

    void initServerUrl();

    void getVerification(String email, String emailServer, String emailServerKey);

    void verify(String email,  String verificationNumber);

    void register(int type, String email, String password, String passwordSend);

}
