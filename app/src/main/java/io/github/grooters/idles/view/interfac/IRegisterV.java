package io.github.grooters.idles.view.interfac;

import io.github.grooters.idles.bean.User;

public interface IRegisterV extends IBaseV{

    void verifySuccess();

    void verifyFailure();

    void showVerificationSuccess(String message);

    void setRegisterButtonText(String text);

    void initVerificationTextView();

    void showRegisterSuccess(User data);
}
