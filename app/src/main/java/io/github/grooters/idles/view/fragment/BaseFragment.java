package io.github.grooters.idles.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.grooters.idles.presenter.ConfigP;
import io.github.grooters.idles.utils.Intenter;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.activity.AccountActivity;

/**
 * Create by 李林浪 in 2019/6/12
 * Elegant Code...
 */
public abstract class BaseFragment extends Fragment {

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

    abstract int getLayout();

    abstract void initView(View view);

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
