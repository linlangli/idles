package io.github.grooters.idles.view.dialog;

import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.grooters.idles.R;
import io.github.grooters.idles.presenter.ConfigP;
import io.github.grooters.idles.presenter.interfac.IConfigP;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.interfac.IConfigV;

public class ConfigDialog extends BaseDialog implements IConfigV {

    private IConfigP iConfigP;

    @BindView(R.id.dialog_config_editText_localUrl)
    public EditText localUrlEditText;

    @BindView(R.id.dialog_config_editText_netUrl)
    public EditText netUrlEditText;

    @BindView(R.id.dialog_config_editText_email)
    public EditText emailEditText;

    @BindView(R.id.dialog_config_editText_emailKey)
    public EditText emailKeyEditText;

    @BindView(R.id.dialog_config_editText_key)
    public EditText authorizeChangeUrlEditText;

    @Override
    public void initView(View view) {
        iConfigP = new ConfigP(getContext(), this);
        setTransaction();
    }

    @OnClick(R.id.dialog_config_button_config)
    void configOnClick(){
        iConfigP.setUrl(
                localUrlEditText.getText().toString(),
                netUrlEditText.getText().toString(),
                emailEditText.getText().toString(),
                emailKeyEditText.getText().toString(),
                authorizeChangeUrlEditText.getText().toString());
    }

    @Override
    public void loginInvalid() { }

    @Override
    public void showMessage(String message) {
        Toaster.shortShow(getContext(), message);
    }

    @Override
    public int getLayout() {
        return R.layout.dialog_config;
    }

    @Override
    public void unAttach() {
        iConfigP = null;
    }

    @Override
    public void startLoading() {}

    @Override
    public void stopLoading() {}
}
