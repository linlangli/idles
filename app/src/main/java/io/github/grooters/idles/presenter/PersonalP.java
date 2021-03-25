package io.github.grooters.idles.presenter;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.github.grooters.idles.bean.Goods;
import io.github.grooters.idles.bean.Result;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.bean.Works;
import io.github.grooters.idles.entity.Cities;
import io.github.grooters.idles.entity.Place;
import io.github.grooters.idles.entity.Province;
import io.github.grooters.idles.entity.Universities;
import io.github.grooters.idles.model.PersonalM;
import io.github.grooters.idles.model.interfac.IPersonalM;
import io.github.grooters.idles.model.interfac.ModelCallBack;
import io.github.grooters.idles.net.Code;
import io.github.grooters.idles.presenter.interfac.IPersonalP;
import io.github.grooters.idles.utils.Jsoner;
import io.github.grooters.idles.view.dialog.UserDataDialog;
import io.github.grooters.idles.view.fragment.ChooseUniversityFragment;
import io.github.grooters.idles.view.fragment.GoodsWorksFragment;
import io.github.grooters.idles.view.fragment.MyDataFragment;
import io.github.grooters.idles.view.fragment.PersonalFragment;
import io.github.grooters.idles.view.interfac.IUserDataV;
import io.github.grooters.idles.view.interfac.IChooseUniversityV;
import io.github.grooters.idles.view.interfac.IGoodsWorksV;
import io.github.grooters.idles.view.interfac.IPersonalV;

public class PersonalP implements IPersonalP {

    private IPersonalV iPersonalV;
    private IPersonalM iPersonalM;
    private IGoodsWorksV iGoodsWorksV;
    private IChooseUniversityV iChooseUniversityV;
    private IUserDataV iUserDataV;
    private Context context;

    public PersonalP(Context context, PersonalFragment personalFragment){
        iPersonalV = personalFragment;
        iPersonalM = new PersonalM();
        this.context = context;
    }

    public PersonalP(Context context, GoodsWorksFragment goodsWorksFragment){
        iGoodsWorksV = goodsWorksFragment;
        iPersonalM = new PersonalM();
        this.context = context;
    }

    public PersonalP(Context context, MyDataFragment myDataFragment){
        iPersonalV = myDataFragment;
        iPersonalM = new PersonalM();
        this.context = context;
    }

    public PersonalP(Context context, ChooseUniversityFragment chooseCollegeFragment){
        iChooseUniversityV = chooseCollegeFragment;
        iPersonalM = new PersonalM();
        this.context = context;
    }

    public PersonalP(Context context, UserDataDialog userDataDialog){
        iUserDataV = userDataDialog;
        iPersonalM = new PersonalM();
        this.context = context;
    }

    @Override
    public void getDataList(String type, boolean isGoods) {
        String tokenNumber = ConfigP.getUser(context).getTokenNumber();
        switch (type){
            case PersonalFragment.MY_ORDER:
                if(isGoods){
                    iPersonalM.getMyOrderGoods(tokenNumber, new ModelCallBack<List<Goods>>() {
                        @Override
                        public void success(List<Goods> data) {
                            if(data.get(0).getCode() == Code.LOGIN_INVALID){
                                iPersonalV.loginInvalid();
                                return;
                            }
                            if(data.get(0).getCode() == Code.GOODS_SUCCESS_EMPTY){
                                iGoodsWorksV.showEmptyTextView();
                            }else{
                                iGoodsWorksV.hideEmptyTextView();
                                iGoodsWorksV.initRecyclerViewWithGoods(data);
                            }
                        }
                        @Override
                        public void failure(String message) { }
                    });
                } else {
                    iPersonalM.getMyOrderWorks(tokenNumber, new ModelCallBack<List<Works>>() {
                        @Override
                        public void success(List<Works> data) {
                            if(data.get(0).getCode() == Code.LOGIN_INVALID){
                                iPersonalV.loginInvalid();
                                return;
                            }
                            if(data.get(0).getCode() == Code.WORKS_SUCCESS_EMPTY){
                                iGoodsWorksV.showEmptyTextView();
                            }else{
                                iGoodsWorksV.hideEmptyTextView();
                                iGoodsWorksV.initRecyclerViewWithWorks(data);
                            }
                        }
                        @Override
                        public void failure(String message) { }
                    });
                }
                break;
            case PersonalFragment.MY_PUSH:
                if(isGoods){
                    iPersonalM.getMyPushGoods(tokenNumber, new ModelCallBack<List<Goods>>() {
                        @Override
                        public void success(List<Goods> data) {
                            if(data.get(0).getCode() == Code.LOGIN_INVALID){
                                iPersonalV.loginInvalid();
                                return;
                            }
                            if(data.get(0).getCode() == Code.GOODS_SUCCESS_EMPTY){
                                iGoodsWorksV.showEmptyTextView();
                                iGoodsWorksV.initRecyclerViewWithGoods(new ArrayList<>());
                            }else{
                                iGoodsWorksV.hideEmptyTextView();
                                iGoodsWorksV.initRecyclerViewWithGoods(data);
                            }
                        }
                        @Override
                        public void failure(String message) { }
                    });
                }else{
                    iPersonalM.getMyPushWorks(tokenNumber, new ModelCallBack<List<Works>>() {
                        @Override
                        public void success(List<Works> data) {
                            if(data.get(0).getCode() == Code.LOGIN_INVALID){
                                iPersonalV.loginInvalid();
                                return;
                            }
                            if(data.get(0).getCode() == Code.WORKS_SUCCESS_EMPTY){
                                iGoodsWorksV.showEmptyTextView();
                                iGoodsWorksV.initRecyclerViewWithWorks(new ArrayList<>());
                            }else{
                                iGoodsWorksV.hideEmptyTextView();
                                iGoodsWorksV.initRecyclerViewWithWorks(data);
                            }
                        }
                        @Override
                        public void failure(String message) { }
                    });
                }
                break;
            case PersonalFragment.MY_COLLECTION:
                if (isGoods){
                    iPersonalM.getMyCollectionGoods(tokenNumber, new ModelCallBack<List<Goods>>() {
                        @Override
                        public void success(List<Goods> data) {
                            if(data.get(0).getCode() == Code.LOGIN_INVALID){
                                iPersonalV.loginInvalid();
                                return;
                            }
                            if(data.get(0).getCode() == Code.GOODS_SUCCESS_EMPTY){
                                iGoodsWorksV.showEmptyTextView();
                                iGoodsWorksV.initRecyclerViewWithGoods(new ArrayList<>());
                            }else{
                                iGoodsWorksV.hideEmptyTextView();
                                iGoodsWorksV.initRecyclerViewWithGoods(data);
                            }
                        }
                        @Override
                        public void failure(String message) { }
                    });
                }else{
                    iPersonalM.getMyCollectionWorks(tokenNumber, new ModelCallBack<List<Works>>() {
                        @Override
                        public void success(List<Works> data) {
                            if(data.get(0).getCode() == Code.LOGIN_INVALID){
                                iPersonalV.loginInvalid();
                                return;
                            }
                            if(data.get(0).getCode() == Code.WORKS_SUCCESS_EMPTY){
                                iGoodsWorksV.showEmptyTextView();
                                iGoodsWorksV.initRecyclerViewWithWorks(new ArrayList<>());
                            }else{
                                iGoodsWorksV.hideEmptyTextView();
                                iGoodsWorksV.initRecyclerViewWithWorks(data);
                            }
                        }
                        @Override
                        public void failure(String message) { }
                    });
                }
                break;
        }
    }

    @Override
    public void getUserData(String tokenNumber) {
        iPersonalM.getUser(tokenNumber, new ModelCallBack<User>() {
            @Override
            public void success(User data) {
                if(data.getCode() == Code.LOGIN_INVALID){
                    iPersonalV.loginInvalid();
                    return;
                }
                if(data.getAvatarUrl() == null || data.getAvatarUrl().equals("")){
                    iPersonalV.setDefaultAvatar();
                }else {
                    iPersonalV.setAvatar(data.getAvatarUrl());
                }
                iPersonalV.initData(data);
            }
            @Override
            public void failure(String message) {
                iPersonalV.showMessage(message);
            }
        });
    }

    @Override
    public void unAttach() {
        iPersonalV = null;
        iGoodsWorksV = null;
    }

    @Override
    public TextWatcher getTextWatcher() {
        return new IsEmpty();
    }

    // 个人资料修改大学信息选中学校
    @Override
    public void setUserUniversity(String universityName) {
        iPersonalM.setUniversityName(ConfigP.getUser(context).getTokenNumber(), universityName,
                new ModelCallBack<io.github.grooters.idles.bean.Result>() {
            @Override
            public void success(io.github.grooters.idles.bean.Result data) {
                if(data.getCode() == Code.LOGIN_INVALID){
                    iChooseUniversityV.loginInvalid();
                }else{
                    iChooseUniversityV.changeUniversityInfo(universityName);
                    iChooseUniversityV.showMessage(data.getMessage());
                }
            }
            @Override
            public void failure(String message) {
                iChooseUniversityV.showMessage("请求接口失败");
            }
        });
    }

    @Override
    public Map<String, Province> getProvinces(AssetManager assetManager) {
        BufferedReader bufferedReader;
        Map<String, Province> provinces = new HashMap<>();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open("university.js")));
            String str;
            StringBuilder stringBuilder = new StringBuilder();
            while ((str = bufferedReader.readLine())!= null){
                stringBuilder.append(str);
            }
            Place place = Jsoner.toObj(stringBuilder.toString(), Place.class);
            for(Province province: place.getProvinces()){
                provinces.put(province.getProvince_name(), province);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return provinces;
    }

    @Override
    public Map<String, List<String>> getCities(Province province) {
        List<String> cities = new ArrayList<>();
        Map<String, List<String>> result = new HashMap<>();
        for(Cities city :province.getCities()){
            cities.add(city.getCity_name());
        }
        result.put(province.getProvince_name(), cities);
        return result;
    }

    // 个人资料的个人简介信息设置
    @Override
    public void setResume(String resume) {
        iPersonalM.setResume(ConfigP.getUser(context).getTokenNumber(),
                resume, new ModelCallBack<io.github.grooters.idles.bean.Result>() {
            @Override
            public void success(io.github.grooters.idles.bean.Result data) {
                if(data.getCode() == Code.LOGIN_INVALID){
                    iPersonalV.loginInvalid();
                }else{
                    iPersonalV.setResume(resume);
                    iPersonalV.showMessage(data.getMessage());
                }
            }
            @Override
            public void failure(String message) {
                iPersonalV.showMessage("请求接口失败");
            }
        });
    }

    // 为注册账号后的账号信息补充提供地址与学校信息
    @Override
    public void setLocation(String province, String city) {
        if(iUserDataV != null)
            iUserDataV.provideLocation(province+city);
        else{
            iPersonalM.setLocation(ConfigP.getUser(context).getTokenNumber(),
                    province + city, new ModelCallBack<Result>() {
                @Override
                public void success(Result data) {
                    if(data.getCode() == Code.LOGIN_INVALID){
                        iPersonalV.loginInvalid();
                    }else{
                        iPersonalV.provideLocation(province+city);
                    }
                }
                @Override
                public void failure(String message) {
                    iPersonalV.showMessage(message);
                }
            });
        }
    }

    class IsEmpty implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if( s.toString().length() > 1){
                iChooseUniversityV.startLoading();
                iPersonalM.searchUniversity(s.toString(), new ModelCallBack<Universities>() {
                    @Override
                    public void success(Universities data) {
                        iChooseUniversityV.updateUniversity(data.getName());
                        iChooseUniversityV.stopLoading();
                    }
                    @Override
                    public void failure(String message) {
                        iChooseUniversityV.showMessage("请求接口失败");
                    }
                });
            }
        }
        @Override
        public void afterTextChanged(Editable s) { }
    }

    @Override
    public void setUserData(String userNumber, String newUserNumber,
                            String name, String gender, String home, String avatar) {
        iPersonalM.setUserData(userNumber, newUserNumber, name,
                gender, home, avatar, new ModelCallBack<Result>() {
            @Override
            public void success(Result data) {

            }
            @Override
            public void failure(String message) {

            }
        });
    }
}
