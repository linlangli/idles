package io.github.grooters.idles.presenter.interfac;

import android.view.KeyEvent;

import io.github.grooters.idles.view.fragment.BaseFragment;

public interface IMainP {

    boolean endActivity(int keyCode, KeyEvent event);

    void selectPage(String pageId);

    BaseFragment createPageFragment(String pageId);

}
