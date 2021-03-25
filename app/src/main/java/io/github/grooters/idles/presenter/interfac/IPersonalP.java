package io.github.grooters.idles.presenter.interfac;

import android.content.res.AssetManager;
import android.text.TextWatcher;
import java.util.List;
import java.util.Map;
import io.github.grooters.idles.entity.Province;

public interface IPersonalP extends IBaseP{

    void getDataList(String type, boolean isGoods);

    void getUserData(String tokenNumber);

    TextWatcher getTextWatcher();

    void setUserUniversity(String universityName);

    void setResume(String resume);

    Map<String, Province> getProvinces(AssetManager assetManager);

    void setUserData(String userNumber, String number, String name, String gender, String location, String avatar);

    void setLocation(String province, String city);

    Map<String, List<String>> getCities(Province province);
}
