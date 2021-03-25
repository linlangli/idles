package io.github.grooters.idles.presenter;

import android.content.Context;

import com.orhanobut.logger.Logger;

import java.util.List;
import io.github.grooters.idles.bean.Result;
import io.github.grooters.idles.bean.Works;
import io.github.grooters.idles.model.WorksM;
import io.github.grooters.idles.model.interfac.IWorksM;
import io.github.grooters.idles.model.interfac.ModelCallBack;
import io.github.grooters.idles.net.Code;
import io.github.grooters.idles.presenter.interfac.IWorksP;
import io.github.grooters.idles.utils.Intenter;
import io.github.grooters.idles.view.activity.AccountActivity;
import io.github.grooters.idles.view.dialog.WorksDialog;
import io.github.grooters.idles.view.fragment.ListOfWorksFragment;
import io.github.grooters.idles.view.interfac.IListOfGoodsWorksV;
import io.github.grooters.idles.view.interfac.IWorksV;

public class WorksP implements IWorksP {

    private IListOfGoodsWorksV<Works> iListOfGoodsWorksV;
    private IWorksM iWorksM;
    private Context context;
    private IWorksV iWorksV;

    public WorksP(Context context, ListOfWorksFragment listOfWorksFragment){
        iListOfGoodsWorksV = listOfWorksFragment;
        iWorksM = new WorksM();
        this.context = context;
    }

    public WorksP(Context context, WorksDialog worksDialog){
        iWorksV = worksDialog;
        iWorksM = new WorksM();
        this.context = context;
    }

    @Override
    public void getWorksList() {
        iListOfGoodsWorksV.startLoading();
        Logger.d("tokenNumber:" +ConfigP.getUser(context).getTokenNumber() );
        iWorksM.getWorks(ConfigP.getUser(context).getTokenNumber(), new ModelCallBack<List<Works>>() {
            @Override
            public void success(List<Works> data) {
                iListOfGoodsWorksV.stopLoading();
                if(data.get(0).getCode() == Code.WORKS_SUCCESS_GET_ALL){
                    iListOfGoodsWorksV.initRecyclerView(data);
                }else if (data.get(0).getCode() == Code.LOGIN_INVALID){
                    iListOfGoodsWorksV.showMessage(data.get(0).getMessage());
                    ConfigP.deleteUser(context);
                    iListOfGoodsWorksV.unAttach();
                    Intenter.jumpActivity(context, AccountActivity.class);
                }else if( data.get(0).getCode() == Code.WORKS_SUCCESS_EMPTY){
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
    public void pushWorks(String tokenNumber, Works works) {
        iWorksV.startLoading();
        iWorksM.pushWorks(tokenNumber, works, new ModelCallBack<Result>() {
            @Override
            public void success(Result data) {
                if(data.getCode() == Code.LOGIN_INVALID){
                    iWorksV.showMessage(data.getMessage());
                    ConfigP.deleteUser(context);
                    iWorksV.unAttach();
                    Intenter.jumpActivity(context, AccountActivity.class);
                }
                iWorksV.stopLoading();
            }
            @Override
            public void failure(String message) {
                iWorksV.showMessage(message);
            }
        });
    }

    @Override
    public void provideStartOrEndTime(String time) {
        iWorksV.setStartOrEndTime(time);
    }

    @Override
    public void unAttach() {
        iWorksM = null;
        iListOfGoodsWorksV = null;
        iWorksV = null;
        context = null;
    }
}
