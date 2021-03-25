package io.github.grooters.idles.view.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.github.grooters.idles.R;
import io.github.grooters.idles.presenter.AccountP;
import io.github.grooters.idles.presenter.interfac.IAccountP;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.fragment.LoginFragment;
import io.github.grooters.idles.view.interfac.IAccountV;

public class AccountActivity extends BaseActivity implements IAccountV {

    private IAccountP iAccountP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iAccountP = new AccountP(this);
    }

    @Override
    public void destroy() {
        finish();
    }

    @Override
    public void unAttach() {
        iAccountP = null;
    }

    @Override
    public void startLoading() { }

    @Override
    public void stopLoading() { }

    @Override
    public void showMessage(String message) {
        Toaster.shortShow(this, message);
    }

    @Override
    public Fragment createFragment() {
        return new LoginFragment();
    }

    @Override
    public int containerId() {
        return R.id.login_frame_fragment_container;
    }

    @Override
    public Fragment delayJump() {
        return null;
    }

    @Override
    public void loginInvalid() { }

    @Override
    public int getLayout() {
        return R.layout.activity_account;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return  iAccountP.endActivity(keyCode, event);
    }

}
