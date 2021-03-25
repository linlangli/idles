package io.github.grooters.idles.view.interfac;

public interface ILoginV extends IBaseV{
    void showPassword();

    void hidePassword();

    void changeRememberIcon();

    void forgetAccount();

    void showClearButton();

    void hideClearButton();

    void jumpToMainActivity();

    void initAccount(String number, String password);

    void showConfigDialog();
}
