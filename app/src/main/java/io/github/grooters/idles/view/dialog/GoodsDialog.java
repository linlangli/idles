package io.github.grooters.idles.view.dialog;

import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.constant.Type;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.grooters.idles.R;
import io.github.grooters.idles.presenter.ConfigP;
import io.github.grooters.idles.presenter.GoodsP;
import io.github.grooters.idles.presenter.interfac.IGoodsP;
import io.github.grooters.idles.utils.GlideEngine;
import io.github.grooters.idles.utils.Intenter;
import io.github.grooters.idles.utils.ResourcEr;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.activity.AccountActivity;
import io.github.grooters.idles.view.interfac.IGoodsV;

public class GoodsDialog extends BaseDialog implements IGoodsV {

    private IGoodsP iGoodsP;

    @BindView(R.id.dialog_goods_editText_description)
    EditText descriptionEditText;
    @BindView(R.id.dialog_goods_editText_location)
    EditText locationEditText;
    @BindView(R.id.dialog_goods_editText_name)
    EditText nameEditText;
    @BindView(R.id.dialog_goods_editText_price)
    EditText priceEditText;
    @BindView(R.id.dialog_goods_editText_time)
    EditText timeEditText;
    @BindView(R.id.dialog_goods_textView_loading)
    TextView loadingTextView;
    @BindView(R.id.dialog_goods_button_titleImage)
    Button titleImageButton;
    @BindView(R.id.dialog_goods_button_introImage)
    Button introImageButton;
    @BindView(R.id.dialog_goods_button_introVideo)
    Button introVideoButton;
    @BindView(R.id.dialog_goods_imageButton_push)
    ImageButton pushImageButton;

    private Uri titleImageUri;
    private List<Uri> introImageUri;
    private String videoPath;
    private Uri videoUri;

    @OnClick(R.id.dialog_goods_button_titleImage)
    void titleImageButtonOnClick(){
        EasyPhotos.createAlbum(this, false, GlideEngine.getGlideEngine()).start(new SelectCallback() {
            @Override
            public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                titleImageUri = photos.get(0).uri;
            }
        });
    }

    @OnClick(R.id.dialog_goods_button_introImage)
    void introImageButtonOnClick(){
        EasyPhotos.createAlbum(this, false, GlideEngine.getGlideEngine())
                .setPictureCount(5)
                .setGif(true)
                .setVideo(false)
                .start(new SelectCallback() {
                    @Override
                    public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                        Logger.d("photos.size = " + photos.size());
                        for(Photo photo : photos){
                            introImageUri.add(photo.uri);
                            Logger.d("photos.path = " + photo.path);
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        setSize((int) ResourcEr.dp2px(380), (int) ResourcEr.dp2px(400));
    }

    @OnClick(R.id.dialog_goods_button_introVideo)
    void introVideoButtonOnClick(){
        EasyPhotos.createAlbum(this, false, GlideEngine.getGlideEngine())
                .filter(Type.VIDEO)
                .setVideo(true)
                .start(new SelectCallback() {
                    @Override
                    public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                        videoPath = photos.get(0).path;
                        videoUri = photos.get(0).uri;
                        Logger.d("photos.get(0).path = " + photos.get(0).path);
                    }
                });
    }

    @OnClick(R.id.dialog_goods_imageButton_push)
    void pushImageButtonOnClick(){
        titleImageButton.setVisibility(View.GONE);
        introImageButton.setVisibility(View.GONE);
        introVideoButton.setVisibility(View.GONE);
        loadingTextView.setVisibility(View.VISIBLE);
        isEmpty(descriptionEditText, locationEditText, nameEditText, priceEditText, timeEditText);
        new Thread(() -> iGoodsP.handleGoodsData(
                getContext(),
                titleImageUri,
                introImageUri,
                videoPath,
                videoUri,
                nameEditText.getText().toString(),
                Float.valueOf(priceEditText.getText().toString()),
                timeEditText.getText().toString(),
                locationEditText.getText().toString(),
                descriptionEditText.getText().toString())).start();
    }

    @Override
    public void loginInvalid() {
        ConfigP.deleteUser(Objects.requireNonNull(getContext()));
        Toaster.shortShow(getContext(), "登录暑校，请重新登录");
        Intenter.jumpActivity(getContext(), AccountActivity.class);
    }

    @Override
    public int getLayout() {
        return R.layout.dialog_goods;
    }

    @Override
    public void initView(View view) {
        iGoodsP = new GoodsP(getContext(), this);
        introImageUri = new ArrayList<>();
    }

    @Override
    public void unAttach() {
        iGoodsP = null;
    }

    @Override
    public void startLoading() {
        loadingTextView.setVisibility(View.VISIBLE);
        pushImageButton.setVisibility(View.GONE);
    }

    @Override
    public void stopLoading() {
        pushImageButton.setVisibility(View.VISIBLE);
        loadingTextView.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toaster.shortShow(getContext(), message);
    }
}
