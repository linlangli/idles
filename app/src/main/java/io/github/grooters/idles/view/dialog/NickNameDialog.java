package io.github.grooters.idles.view.dialog;

import android.view.View;

import io.github.grooters.idles.R;
import io.github.grooters.idles.utils.ResourcEr;

public class NickNameDialog extends BaseDialog {
    @Override
    public int getLayout() {
        return R.layout.dialog_nicak_name;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        setSize((int) ResourcEr.dp2px(380), (int) ResourcEr.dp2px(200));
    }

}
