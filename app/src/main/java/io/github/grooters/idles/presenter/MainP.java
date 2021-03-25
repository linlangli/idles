package io.github.grooters.idles.presenter;

import android.content.Context;
import android.view.KeyEvent;

import com.orhanobut.logger.Logger;

import io.github.grooters.idles.bean.Result;
import io.github.grooters.idles.model.AccountM;
import io.github.grooters.idles.model.interfac.IAccountM;
import io.github.grooters.idles.model.interfac.ModelCallBack;
import io.github.grooters.idles.net.Retrofiter;
import io.github.grooters.idles.net.Server;
import io.github.grooters.idles.net.api.AccountApi;
import io.github.grooters.idles.presenter.interfac.IMainP;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.activity.MainActivity;
import io.github.grooters.idles.view.fragment.BaseFragment;
import io.github.grooters.idles.view.fragment.ListOfGoodsFragment;
import io.github.grooters.idles.view.fragment.ListOfWorksFragment;
import io.github.grooters.idles.view.fragment.MessageFragment;
import io.github.grooters.idles.view.fragment.PersonalFragment;
import io.github.grooters.idles.view.interfac.IMainV;

public class MainP implements IMainP {

    private Context context;
    private IMainV iMainV;

    IAccountM iAccountM;

    private int backCount = 1;

    public MainP(MainActivity mainActivity){
        iMainV = mainActivity;
        iAccountM = new AccountM();
        this.context = mainActivity;
    }

    @Override
    public boolean endActivity(int keyCode, KeyEvent event) {
        new EndThread().start();
        if(keyCode == KeyEvent.KEYCODE_BACK && backCount == 1){
            iMainV.showMessage("再按一次退出应用");
            ++backCount;
        }else if(keyCode == KeyEvent.KEYCODE_BACK){
            iAccountM.setTime(ConfigP.getUser(context).getTokenNumber(),new ModelCallBack<Result>() {
                @Override
                public void success(Result data) {
                    iMainV.destroy();
                }
                @Override
                public void failure(String message) { }
            });
        }
        return false;
    }

    class EndThread extends Thread {
        int time = 0;
        @Override
        public void run() {
            super.run();
            while(time++ < 2){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            backCount = 0;
        }
    }

    @Override
    public void selectPage(String pageId) {
        if (pageId == null){
            iMainV.selectListOfGoods();
            return;
        }
        switch (pageId){
            case MainActivity.listOfGoods:
                iMainV.selectListOfGoods();
                return;
            case MainActivity.listOfWorks:
                iMainV.selectListOfWorks();
                return;
            case MainActivity.message:
                iMainV.selectMessage();
                return;
            case MainActivity.personal:
                Logger.d("case MainActivity.personal:");
                iMainV.selectPersonal();
                return;
        }
        iMainV.selectListOfGoods();
    }

    @Override
    public BaseFragment createPageFragment(String pageId) {
        if(pageId == null){
            return new ListOfGoodsFragment();
        }
        switch (pageId){
            case MainActivity.listOfGoods:
                return new ListOfGoodsFragment();
            case MainActivity.listOfWorks:
                return new ListOfWorksFragment();
            case MainActivity.message:
                return new MessageFragment();
            case MainActivity.personal:
                Logger.d("case MainActivity.personal");
                return new PersonalFragment();
        }
        return new ListOfGoodsFragment();
    }
}
