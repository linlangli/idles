package io.github.grooters.idles.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.grooters.idles.net.Retrofiter;
import io.github.grooters.idles.net.Server;
import io.github.grooters.idles.net.api.AccountApi;
import io.github.grooters.idles.view.fragment.BaseFragment;

/**
 * Create by 李林浪 in 2019/6/12
 * Elegant Code...
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Handler timerHandler;
    private Runnable runnable;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        unbinder = ButterKnife.bind(this);

        Fragment fragment = createFragment();
        if( fragment != null ){
            getSupportFragmentManager().beginTransaction()
                    .replace(containerId(), fragment).commit();
        }

        if( delayJump() != null ){
            timerHandler = new Handler();
            runnable = () -> getSupportFragmentManager().beginTransaction()
                    .replace(containerId(), delayJump())
                    .commit();
            timerHandler.postDelayed(runnable,3000);
        }
    }


    public abstract Fragment createFragment();

    public abstract int containerId();


    public void replaceFragment(int containerId, BaseFragment fragment, String name) {
        getSupportFragmentManager().beginTransaction().replace(containerId, fragment, name).commit();
    }

    public abstract Fragment delayJump();

    public void setFullScreen(boolean isFull){
        if( isFull ){
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        }else{
            getWindow().clearFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN );
        }
    }

    public void setWindowStatusBarColor(int colorResId) {
        try {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(colorResId, null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStateBarTransaction(){
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    public void changeStateBarColor(int color){
        Window window = getWindow();
        //设置修改状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏的颜色，和你的app主题或者标题栏颜色设置一致就ok了
        window.setStatusBarColor(getResources().getColor(color, null));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if( timerHandler !=null ){
            timerHandler.removeCallbacks(runnable);
        }
        unbinder.unbind();
    }

    public abstract int getLayout();
}
