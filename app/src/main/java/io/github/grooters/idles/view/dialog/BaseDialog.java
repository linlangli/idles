package io.github.grooters.idles.view.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.util.Objects;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.grooters.idles.presenter.ConfigP;
import io.github.grooters.idles.utils.Intenter;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.activity.AccountActivity;

public abstract class BaseDialog extends DialogFragment {

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(getLayout(),null);

        unbinder = ButterKnife.bind(this, layout);

        initView(layout);

        return layout;
    }

    void invalid(){
        ConfigP.deleteUser(Objects.requireNonNull(getContext()));
        Toaster.shortShow(getContext(), "登录失效，请重新登录");
        Intenter.jumpActivity(getContext(), AccountActivity.class);
    }

    void setTransaction(){

        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow())
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    void setSize(int white, int height){

        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setLayout(white, height);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    boolean isEmpty(EditText...data){
        for (EditText editText : data){
            if( editText.getText().toString().equals("") ){
                return true;
            }
        }
        return false;
    }

    public abstract int getLayout();

    public abstract void initView(View view);
}
