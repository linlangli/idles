package io.github.grooters.idles.view.activity;

import androidx.fragment.app.Fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.orhanobut.logger.Logger;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.grooters.idles.R;
import io.github.grooters.idles.presenter.ConfigP;
import io.github.grooters.idles.presenter.MainP;
import io.github.grooters.idles.presenter.interfac.IMainP;
import io.github.grooters.idles.utils.Intenter;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.dialog.PushDialog;
import io.github.grooters.idles.view.fragment.BaseFragment;
import io.github.grooters.idles.view.fragment.ListOfGoodsFragment;
import io.github.grooters.idles.view.fragment.ListOfWorksFragment;
import io.github.grooters.idles.view.fragment.MessageFragment;
import io.github.grooters.idles.view.fragment.PersonalFragment;
import io.github.grooters.idles.view.interfac.IMainV;

public class MainActivity extends BaseActivity implements IMainV {

    private IMainP iMainP;

    private Resources resources;
    private int beSelectedDrawableID, notBeSelectedDrawableId;
    private PushDialog pushDialog;
    private ImageButton beSelectedButton;
    private BaseFragment targetFragment;

    public static final String listOfGoods = "LIST_OF_GOODS";
    public static final String listOfWorks = "LIST_OF_WORKS";
    public static final String message = "MESSAGE";
    public static final String personal = "PERSONAL";

    @BindView(R.id.activity_main_imageButton_goods)
    ImageButton goodsImageButton;
    @BindView(R.id.activity_main_imageButton_message)
    ImageButton messageImageButton;
    @BindView(R.id.activity_main_imageButton_works)
    ImageButton worksImageButton;
    @BindView(R.id.activity_main_imageButton_personal)
    ImageButton personalImageButton;
    @BindView(R.id.toolbar)
    View toolBar;

    private String pageId;

    @OnClick(R.id.activity_main_imageButton_goods)
    void goodsImageButtonOnClick(){
        changeStateBarColor(R.color.colorPrimary);
        beSelectedButton.setImageDrawable(resources.getDrawable(notBeSelectedDrawableId,null));
        beSelectedButton = goodsImageButton;
        beSelectedDrawableID  = R.drawable.ic_goods_press;
        notBeSelectedDrawableId = R.drawable.ic_goods;
        toolBar.setVisibility(View.VISIBLE);
        targetFragment = new ListOfGoodsFragment();
        beSelectedButton.setImageDrawable(resources.getDrawable(beSelectedDrawableID, null));
        replaceFragment(R.id.main_frame_fragment_container, targetFragment, "ListOfGoodsFragment");
    }

    @OnClick(R.id.activity_main_imageButton_works)
    void worksImageButtonOnClick(){
        changeStateBarColor(R.color.colorPrimary);
        beSelectedButton.setImageDrawable(resources.getDrawable(notBeSelectedDrawableId,null));
        beSelectedButton = worksImageButton;
        beSelectedDrawableID = R.drawable.ic_works_press;
        notBeSelectedDrawableId = R.drawable.ic_works;
        toolBar.setVisibility(View.VISIBLE);
        targetFragment = new ListOfWorksFragment();
        beSelectedButton.setImageDrawable(resources.getDrawable(beSelectedDrawableID, null));
        replaceFragment(R.id.main_frame_fragment_container, targetFragment, "ListOfWorksFragment");
    }

    @OnClick(R.id.activity_main_imageButton_message)
    void messageImageButtonOnClick(){
        changeStateBarColor(R.color.colorPrimary);
        beSelectedButton.setImageDrawable(resources.getDrawable(notBeSelectedDrawableId,null));
        beSelectedButton = messageImageButton;
        beSelectedDrawableID = R.drawable.ic_message_press;
        notBeSelectedDrawableId = R.drawable.ic_message;
        toolBar.setVisibility(View.VISIBLE);
        targetFragment = new MessageFragment();
        beSelectedButton.setImageDrawable(resources.getDrawable(beSelectedDrawableID, null));
        replaceFragment(R.id.main_frame_fragment_container, targetFragment, "MessageFragment");
    }

    @OnClick(R.id.activity_main_imageButton_personal)
    public void personalImageButtonOnClick(){
        beSelectedButton.setImageDrawable(resources.getDrawable(notBeSelectedDrawableId,null));
        beSelectedButton = personalImageButton;
        beSelectedDrawableID = R.drawable.ic_personal_press;
        notBeSelectedDrawableId = R.drawable.ic_personal;
        toolBar.setVisibility(View.GONE);
        changeStateBarColor(R.color.fragment_personal_frameLayout);
        targetFragment = new PersonalFragment();
        beSelectedButton.setImageDrawable(resources.getDrawable(beSelectedDrawableID, null));
        replaceFragment(R.id.main_frame_fragment_container, targetFragment, "PersonalFragment");
    }

    @OnClick(R.id.activity_main_imageButton_push)
    public void pushImageButton(){
        if(pushDialog == null)
            pushDialog = new PushDialog();
        pushDialog.show(getSupportFragmentManager(),"PushDialog");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        iMainP = new MainP(this);
        pageId = getIntent().getStringExtra("pageId");

        super.onCreate(savedInstanceState);

        resources = getResources();
        iMainP.selectPage(pageId);
    }

    @Override
    public void selectListOfGoods() {
//        beSelectedButton.setImageDrawable(resources.getDrawable(R.drawable.ic_goods,null));
        beSelectedDrawableID = R.drawable.ic_goods_press;
        beSelectedButton = goodsImageButton;
        notBeSelectedDrawableId = R.drawable.ic_goods;
        beSelectedButton.setImageDrawable(resources.getDrawable(beSelectedDrawableID,null));
    }

    @Override
    public void selectListOfWorks() {
        goodsImageButton.setImageDrawable(resources.getDrawable(R.drawable.ic_goods,null));
        beSelectedDrawableID = R.drawable.ic_works_press;
        beSelectedButton = worksImageButton;
        notBeSelectedDrawableId = R.drawable.ic_works;
        beSelectedButton.setImageDrawable(resources.getDrawable(beSelectedDrawableID,null));
    }

    @Override
    public void selectMessage() {
        goodsImageButton.setImageDrawable(resources.getDrawable(R.drawable.ic_goods,null));
        beSelectedDrawableID = R.drawable.ic_message_press;
        beSelectedButton = messageImageButton;
        notBeSelectedDrawableId = R.drawable.ic_message;
        beSelectedButton.setImageDrawable(resources.getDrawable(beSelectedDrawableID,null));
    }

    @Override
    public void selectPersonal() {
        goodsImageButton.setImageDrawable(resources.getDrawable(R.drawable.ic_goods,null));
        toolBar.setVisibility(View.GONE);
        changeStateBarColor(R.color.fragment_personal_frameLayout);
        beSelectedDrawableID = R.drawable.ic_personal_press;
        beSelectedButton = personalImageButton;
        notBeSelectedDrawableId = R.drawable.ic_personal;
        beSelectedButton.setImageDrawable(resources.getDrawable(beSelectedDrawableID,null));
    }

    @Override
    public void loginInvalid() {
        ConfigP.deleteUser(Objects.requireNonNull(this));
        Toaster.shortShow(this, "登录暑校，请重新登录");
        Intenter.jumpActivity(this, AccountActivity.class);
    }

    @Override
    public Fragment createFragment() {

        return iMainP.createPageFragment(pageId);
    }

    @Override
    public int containerId() {
        return  R.id.main_frame_fragment_container;
    }

    @Override
    public Fragment delayJump() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void destroy() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return  iMainP.endActivity(keyCode, event);
    }

    @Override
    public void unAttach() {
        iMainP = null;
    }

    @Override
    public void startLoading() { }

    @Override
    public void stopLoading() { }

    @Override
    public void showMessage(String message) { Toaster.shortShow(this, message); }
}
