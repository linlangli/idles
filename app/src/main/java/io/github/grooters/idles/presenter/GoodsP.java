package io.github.grooters.idles.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.orhanobut.logger.Logger;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.CosXmlSimpleService;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.auth.ShortTimeCredentialProvider;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import io.github.grooters.idles.bean.Goods;
import io.github.grooters.idles.bean.MultiMedia;
import io.github.grooters.idles.bean.Result;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.model.GoodsM;
import io.github.grooters.idles.model.interfac.IGoodsM;
import io.github.grooters.idles.model.interfac.ModelCallBack;
import io.github.grooters.idles.net.Code;
import io.github.grooters.idles.net.Server;
import io.github.grooters.idles.presenter.interfac.IGoodsP;
import io.github.grooters.idles.utils.Jsoner;
import io.github.grooters.idles.view.dialog.GoodsDialog;
import io.github.grooters.idles.view.fragment.GoodsItemFragment;
import io.github.grooters.idles.view.fragment.ListOfGoodsFragment;
import io.github.grooters.idles.view.interfac.IGoodsItemV;
import io.github.grooters.idles.view.interfac.IGoodsV;
import io.github.grooters.idles.view.interfac.IListOfGoodsWorksV;

public class GoodsP implements IGoodsP {
    private IListOfGoodsWorksV<Goods> iListOfGoodsWorksV;
    private IGoodsItemV iGoodsItemV;
    private IGoodsV iGoodsV;
    private IGoodsM iGoodsM;
    private Context context;
    private List<byte[]> introImageList;
    private byte[] titleImage;
    private byte[] introVideo;
    private List<Bitmap[]> bitmapsList;

    public GoodsP(Context context, ListOfGoodsFragment listOfGoodsFragment){
        this.iListOfGoodsWorksV = listOfGoodsFragment;
        iGoodsM = new GoodsM();
        this.context = context;
    }

    public GoodsP(Context context, GoodsDialog goodsDialog){
        iGoodsV = goodsDialog;
        introImageList = new ArrayList<>();
        iGoodsM = new GoodsM();
        this.context = context;
    }

    public GoodsP(Context context, GoodsItemFragment goodsItemFragment){
        iGoodsItemV = goodsItemFragment;
        introImageList = new ArrayList<>();
        iGoodsM = new GoodsM();
        this.context = context;
    }

    @Override
    public void getSellerGoodsData(String tokenNumber, String goodsNumber) {
        iGoodsM.getSeller(tokenNumber, goodsNumber, new ModelCallBack<User>() {
            @Override
            public void success(User data) {
                iGoodsItemV.initSellerGoodsInfo(data);
                if(data.getAvatarUrl() != null && !data.getAvatarUrl().equals("")){
                    iGoodsItemV.setSellerAvatar(data.getAvatarUrl());
                }else{
                    iGoodsItemV.setDefaultAvatar();
                }
                if(data.getMyGoodsCollectionNumber() != null){
                    String[] myGoodsCollectionNumbers = data.getMyGoodsCollectionNumber();
                    for(String myGoodsCollectionNumber : myGoodsCollectionNumbers){
                        if(myGoodsCollectionNumber.equals(goodsNumber)){
                            iGoodsItemV.beCollected();
                            return;
                        }
                    }
                }
                iGoodsItemV.notBeCollected();
            }
            @Override
            public void failure(String message) {
                iGoodsItemV.showMessage(message);
            }
        });
    }

    @Override
    public void collectGoods(String tokenNumber, String goodsNumber) {
        iGoodsM.collectGoods(tokenNumber, goodsNumber, new ModelCallBack<Result>() {
            @Override
            public void success(Result data) {
                if(data.getCode() == Code.LOGIN_INVALID){
                    iGoodsItemV.loginInvalid();
                }else if (data.getCode() == Code.GOODS_SUCCESS_COLLECT){
                    iGoodsItemV.beCollected();
                }else if(data.getCode() == Code.GOODS_SUCCESS_CANCEL_COLLECT){
                    iGoodsItemV.notBeCollected();
                }
                iGoodsItemV.showMessage(data.getMessage());
            }
            @Override
            public void failure(String message) {
                iGoodsItemV.showMessage(message);
            }
        });
    }

    @Override
    public void getGoodsList() {
        iListOfGoodsWorksV.startLoading();
        iGoodsM.getGoods(ConfigP.getUser(context).getTokenNumber(), new ModelCallBack<List<Goods>>() {
            @Override
            public void success(List<Goods> data) {
                iListOfGoodsWorksV.stopLoading();
                if(data.get(0).getCode() == Code.GOODS_SUCCESS_GET_ALL){
                    iListOfGoodsWorksV.initRecyclerView(data);
                }else if (data.get(0).getCode() == Code.LOGIN_INVALID){
                    Logger.d("登录失效：" + ConfigP.getUser(context).getTokenNumber());
                    iListOfGoodsWorksV.loginInvalid();
                }else if( data.get(0).getCode() == Code.GOODS_SUCCESS_EMPTY){
                    iListOfGoodsWorksV.initNullData();
                }
            }
            @Override
            public void failure(String message) {
                iListOfGoodsWorksV.stopLoading();
                iListOfGoodsWorksV.showMessage(message);
            }
        });
    }

    @Override
    public void handleGoodsData(Context context, Uri titleImageUri, List<Uri> introImageUri, String videoPath,
                                Uri videoUri, String name, float price, String time, String location, String intro) {
        iGoodsV.startLoading();

        final Goods goods = new Goods();
        goods.setGoodsNumber(String.valueOf(System.currentTimeMillis()));
        goods.setTime(time);
        goods.setLocation(location);
        goods.setPrice(price);
        goods.setGoodsName(name);
        String resume = intro.length() > 50 ? intro.substring(0, 30) : intro;
        goods.setDescription(resume);
        goods.setSellerNumber(ConfigP.getUser(context).getUserNumber());
        goods.setSellerName(ConfigP.getUser(context).getName());
        goods.setDescription(intro);

        try {
            // 压缩图片
//            titleImage = FileIOer.compressImage(BitmapFactory.decodeStream(context.getContentResolver().openInputStream(titleImageUri)), getPushedImagePath());
            // 不压缩图片
            InputStream inputStream = context.getContentResolver().openInputStream(titleImageUri);
            titleImage = new byte[Objects.requireNonNull(inputStream).available()];
            inputStream.read(titleImage);
//            pushVideo = FileIOer.inputStreamToFile(context, introVideoUri, introVideoPath);
//            introVideo = new File(videoPath);
            inputStream = context.getContentResolver().openInputStream(videoUri);
            introVideo = new byte[Objects.requireNonNull(inputStream).available()];
            inputStream.read(introVideo);
            for(Uri uri : introImageUri){
//                introImageList.add(FileIOer.compressImage(BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri)), getPushedImagePath()));
                inputStream = context.getContentResolver().openInputStream(uri);
                byte[] introImage = new byte[Objects.requireNonNull(inputStream).available()];
                inputStream.read(introImage);
                introImageList.add(introImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        QCloudCredentialProvider credentialProvider = new ShortTimeCredentialProvider(Server.secretId, Server.secretKey, 300);
        // 创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
        CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
                .setRegion(Server.region)
                .isHttps(true)
                .builder();
        iGoodsM.upload(new CosXmlSimpleService(Objects.requireNonNull(context), serviceConfig, credentialProvider),
                titleImage, introVideo, introImageList, ConfigP.getUser(context).getUserNumber(), new ModelCallBack<MultiMedia>() {
            @Override
            public void success(MultiMedia data) {
                goods.setTitleImage(data.getTitleImage());
                goods.setIntroImage(data.getIntroImage());
                goods.setIntroVideo(data.getIntroVideo());
                iGoodsM.pushGoods(ConfigP.getUser(context).getTokenNumber(), goods, new ModelCallBack<Result>() {
                    @Override
                    public void success(Result data) {
                        if(data.getCode() == Code.LOGIN_INVALID){
                            // 登录失效
                            iListOfGoodsWorksV.loginInvalid();
                        }
                        iGoodsV.showMessage(data.getMessage());
                        iGoodsV.stopLoading();
                    }
                    @Override
                    public void failure(String message) {
                        iGoodsV.showMessage(message);
                    }
                });
            }
            @Override
            public void failure(String message) {
                iGoodsV.showMessage(message);
            }
        });
    }

    @Override
    public void unAttach() {
        iListOfGoodsWorksV = null;
    }
}
