package io.github.grooters.idles.view.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.util.Objects;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.grooters.idles.R;
import io.github.grooters.idles.bean.Goods;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.presenter.ConfigP;
import io.github.grooters.idles.presenter.GoodsP;
import io.github.grooters.idles.presenter.interfac.IGoodsP;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.interfac.IGoodsItemV;
import io.github.grooters.idles.view.widget.LoadingWidget;

public class GoodsItemFragment extends BaseFragment implements IGoodsItemV {
    private IGoodsP iGoodsP;

    private User user;
    private Goods goods;

    @BindView(R.id.fragment_goodsItem_loadingWidget)
    LoadingWidget loadingWidget;
    @BindView(R.id.fragment_goodsItem_textView_comeLatelyTime)
    TextView comeOverTimeTextView;
    @BindView(R.id.fragment_goodsItem_imageView_sellerAvatar)
    ImageView sellerAvatarImageView;
    @BindView(R.id.fragment_goodsItem_TextView_sellerName)
    TextView sellerNameTextView;
    @BindView(R.id.fragment_goodsItem_TextView_princeValue)
    TextView priceTextView;
    @BindView(R.id.fragment_goodsItem_TextView_locationValue)
    TextView locationTextView;

    @BindView(R.id.fragment_goodsItem_imageView_introImage1)
    ImageView introImage1ImageView;
    @BindView(R.id.fragment_goodsItem_imageView_introImage2)
    ImageView introImage2ImageView;
    @BindView(R.id.fragment_goodsItem_imageView_introImage3)
    ImageView introImage3ImageView;
    @BindView(R.id.fragment_goodsItem_imageView_introImage4)
    ImageView introImage4ImageView;
    @BindView(R.id.fragment_goodsItem_imageView_introImage5)
    ImageView introImage5ImageView;
    @BindView(R.id.fragment_goodsItem_textView_descriptionValue)
    TextView descriptionTextView;
    @BindView(R.id.fragment_goodsItem_imageButton_collect)
    ImageButton collectImageButton;
    @BindView(R.id.fragment_goodsItem_button_need)
    Button needButton;
    @BindView(R.id.fragment_goodsItem_video)
    VideoView videoView;

    @OnClick(R.id.fragment_goodsItem_button_need)
    void needButtonOnClick(){
        needButton.setClickable(false);
    }

    @OnClick(R.id.fragment_goodsItem_imageButton_collect)
    void collectImageButtonOnClick(){
        collectImageButton.setClickable(false);
        iGoodsP.collectGoods(user.getTokenNumber(), goods.getGoodsNumber());
    }

    @Override
    public void initSellerGoodsInfo(User seller) {

        sellerNameTextView.setText(goods.getSellerName());
        comeOverTimeTextView.setText(goods.getComeLatelyTime());
        priceTextView.setText(String.valueOf(goods.getPrice()));
        locationTextView.setText(goods.getLocation());
        descriptionTextView.setText(goods.getDescription());
        if(goods.getIntroVideo() != null && !goods.getIntroVideo().equals("")){
            videoView.setVisibility(View.VISIBLE);
            videoView.setMediaController(new MediaController(getContext()));
            videoView.seekTo(1);
            videoView.setVideoPath(goods.getIntroVideo());
        }
        switch (goods.getIntroImage().length){
            case 1:
                Glide.with(this).load(goods.getIntroImage()[0]).centerInside().into(introImage1ImageView);
                introImage1ImageView.setVisibility(View.VISIBLE);
                break;
            case 2:
                Glide.with(this).load(goods.getIntroImage()[0]).centerInside().into(introImage1ImageView);
                Glide.with(this).load(goods.getIntroImage()[1]).centerInside().into(introImage2ImageView);
                introImage1ImageView.setVisibility(View.VISIBLE);
                introImage2ImageView.setVisibility(View.VISIBLE);
                break;
            case 3:
                Glide.with(this).load(goods.getIntroImage()[0]).centerInside().into(introImage1ImageView);
                Glide.with(this).load(goods.getIntroImage()[1]).centerInside().into(introImage2ImageView);
                Glide.with(this).load(goods.getIntroImage()[2]).centerInside().into(introImage3ImageView);
                introImage1ImageView.setVisibility(View.VISIBLE);
                introImage2ImageView.setVisibility(View.VISIBLE);
                introImage3ImageView.setVisibility(View.VISIBLE);
                break;
            case 4:
                Glide.with(this).load(goods.getIntroImage()[0]).centerInside().into(introImage1ImageView);
                Glide.with(this).load(goods.getIntroImage()[1]).centerInside().into(introImage2ImageView);
                Glide.with(this).load(goods.getIntroImage()[2]).centerInside().into(introImage3ImageView);
                Glide.with(this).load(goods.getIntroImage()[3]).centerInside().into(introImage4ImageView);
                introImage1ImageView.setVisibility(View.VISIBLE);
                introImage2ImageView.setVisibility(View.VISIBLE);
                introImage3ImageView.setVisibility(View.VISIBLE);
                introImage4ImageView.setVisibility(View.VISIBLE);
                break;
            case 5:
                Glide.with(this).load(goods.getIntroImage()[0]).centerInside().into(introImage1ImageView);
                Glide.with(this).load(goods.getIntroImage()[1]).centerInside().into(introImage2ImageView);
                Glide.with(this).load(goods.getIntroImage()[2]).centerInside().into(introImage3ImageView);
                Glide.with(this).load(goods.getIntroImage()[3]).centerInside().into(introImage4ImageView);
                Glide.with(this).load(goods.getIntroImage()[4]).centerInside().into(introImage5ImageView);
                introImage1ImageView.setVisibility(View.VISIBLE);
                introImage2ImageView.setVisibility(View.VISIBLE);
                introImage3ImageView.setVisibility(View.VISIBLE);
                introImage4ImageView.setVisibility(View.VISIBLE);
                introImage5ImageView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void setSellerAvatar(String url) {
        Glide.with(this).load(url).into(sellerAvatarImageView);
    }

    @Override
    public void setDefaultAvatar() {
        Glide.with(this).load(R.mipmap.ic_avatar).into(sellerAvatarImageView);
    }

    @Override
    public void beCollected() {
        collectImageButton.setImageDrawable(Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_collect_press));
        collectImageButton.setClickable(true);
    }

    @Override
    public void notBeCollected() {
        collectImageButton.setImageDrawable(Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_collect));
        collectImageButton.setClickable(true);
    }

    @Override
    int getLayout() {
        return R.layout.fragment_goods_item;
    }

    @Override
    void initView(View view) {
        user = ConfigP.getUser(getContext());
        iGoodsP = new GoodsP(getContext(), this);
        goods = Objects.requireNonNull(getArguments()).getParcelable("Goods");
        iGoodsP.getSellerGoodsData(ConfigP.getUser(getContext()).getTokenNumber(), goods.getGoodsNumber());
    }

    @Override
    public void unAttach() { iGoodsP = null; }

    @Override
    public void startLoading() {
        loadingWidget.start();
    }

    @Override
    public void stopLoading() {
        loadingWidget.stop();
    }

    @Override
    public void showMessage(String message) {
        Toaster.shortShow(getContext(), message);
    }

    @Override
    public void loginInvalid() { invalid(); }
}
