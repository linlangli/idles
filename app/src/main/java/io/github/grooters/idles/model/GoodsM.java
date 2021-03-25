package io.github.grooters.idles.model;

import com.orhanobut.logger.Logger;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.CosXmlSimpleService;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.transfer.COSXMLUploadTask;
import com.tencent.cos.xml.transfer.TransferConfig;
import com.tencent.cos.xml.transfer.TransferManager;
import java.util.List;
import io.github.grooters.idles.base.BaseObserver;
import io.github.grooters.idles.bean.Goods;
import io.github.grooters.idles.bean.MultiMedia;
import io.github.grooters.idles.bean.Result;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.model.interfac.IGoodsM;
import io.github.grooters.idles.model.interfac.ModelCallBack;
import io.github.grooters.idles.net.Retrofiter;
import io.github.grooters.idles.net.Server;
import io.github.grooters.idles.net.api.GoodsApi;
import io.github.grooters.idles.utils.Jsoner;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class GoodsM implements IGoodsM {

    @Override
    public void getGoods(String tokenNumber, ModelCallBack<List<Goods>> callBack) {
        Retrofiter.getApi(GoodsApi.class, Server.localUrl).getGoods(tokenNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<List<Goods>>(){
                    @Override
                    public void handleError() {
                        callBack.failure("接口请求错误");
                    }
                });
    }

    @Override
    public void pushGoods(String token, Goods goods, ModelCallBack<Result> callBack) {
        Retrofiter.getApi(GoodsApi.class, Server.localUrl, 120)
                .pushGoods(token, Jsoner.toJson(goods))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success).subscribe(new BaseObserver<Result>(){
                    @Override
                    public void handleError() {
                        callBack.failure("接口请求错误");
                    }
                });
    }

    @Override
    public void collectGoods(String tokenNumber, String goodsNumber, ModelCallBack<Result> callBack) {
        Retrofiter.getApi(GoodsApi.class, Server.localUrl).collectGoods(tokenNumber,goodsNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<Result>(){
                    @Override
                    public void handleError() {
                        callBack.failure("接口请求错误");
                    }
                });
    }

    @Override
    public void getSeller(String tokenNumber,String goodsNumber, ModelCallBack<User> callBack) {
        Retrofiter.getApi(GoodsApi.class, Server.localUrl, 120)
                .getSeller(tokenNumber, goodsNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(callBack::success)
                .subscribe(new BaseObserver<User>(){
                    @Override
                    public void handleError() {
                        Logger.d("getSeller");
                        callBack.failure("接口请求错误");
                    }
                });
    }

    @Override
    public void upload(CosXmlSimpleService cosXmlService, final byte[] titleImage, final byte[] introVideo,
                       List<byte[]> introImageList, String userNumber, final ModelCallBack<MultiMedia> callBack){

        TransferConfig transferConfig;
        /*若有特殊要求，则可以如下进行初始化定制。例如限定当对象 >= 2M 时，启用分块上传，且分块上传的分块大小为1M，当源对象大于5M时启用分块复制，且分块复制的大小为5M。*/
        transferConfig = new TransferConfig.Builder()
                .setDividsionForCopy(5 * 1024 * 1024)  // 是否启用分块复制的最小对象大小
                .setSliceSizeForCopy(5 * 1024 * 1024)  // 分块复制时的分块大小
                .setDivisionForUpload(2 * 1024 * 1024) // 是否启用分块上传的最小对象大小
                .setSliceSizeForUpload(1024 * 1024)    // 分块上传时的分块大小
                .build();

        // 初始化 TransferManager
        TransferManager transferManager = new TransferManager(cosXmlService, transferConfig);
        String bucket = "idles-1252858397";

        // 上传对象
        Logger.d("开始上传标题图片");
        String cosPath = userNumber + "/image/"+System.currentTimeMillis()+".png";
        final String titleImagePath = Server.storeUrl + "/" + cosPath;
        transferManager.upload(bucket, cosPath, titleImage);

        Logger.d("开始上传介绍图片");
        final String[] introImagePath = new String[introImageList.size()];
        for(int i = 0; i < introImageList.size(); i++){
            cosPath = userNumber + "/image/"+System.currentTimeMillis()+".png";
            introImagePath[i] = Server.storeUrl + "/" + cosPath;
            transferManager.upload(bucket, cosPath, introImageList.get(i));
        }

        Logger.d("开始上传介绍视频");
        cosPath = userNumber + "/video/"+System.currentTimeMillis()+".mp4";
        final String introVideoPath = Server.storeUrl + "/" + cosPath;
        COSXMLUploadTask cosxmlUploadTask = transferManager.upload(bucket, cosPath, introVideo);

        //设置视频上传进度回调
        cosxmlUploadTask.setCosXmlProgressListener((complete, target) -> Logger.d("progress = "+ complete));
        //设置返回结果回调
        cosxmlUploadTask.setCosXmlResultListener(new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest request, CosXmlResult result) {
//                COSXMLUploadTask.COSXMLUploadTaskResult cOSXMLUploadTaskResult = (COSXMLUploadTask.COSXMLUploadTaskResult) result;
                MultiMedia multiMedia = new MultiMedia();
                multiMedia.setTitleImage(titleImagePath);
                multiMedia.setIntroImage(introImagePath);
                multiMedia.setIntroVideo(introVideoPath);
                callBack.success(multiMedia);
            }
            @Override
            public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
                callBack.failure("上传失败");
            }
        });
        //设置任务状态回调, 可以查看任务过程
        cosxmlUploadTask.setTransferStateListener(state -> { });

    }
}
