package io.github.grooters.idles.view.fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.orhanobut.logger.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.grooters.idles.R;
import io.github.grooters.idles.base.GeneralRecyclerViewAdapter;
import io.github.grooters.idles.base.GeneralRecyclerViewHolder;
import io.github.grooters.idles.entity.Place;
import io.github.grooters.idles.presenter.PersonalP;
import io.github.grooters.idles.presenter.interfac.IPersonalP;
import io.github.grooters.idles.utils.Jsoner;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.activity.PersonalItemActivity;
import io.github.grooters.idles.view.interfac.IChooseUniversityV;
import io.github.grooters.idles.view.widget.LoadingWidget;

public class ChooseUniversityFragment extends BaseFragment implements IChooseUniversityV {

    @BindView(R.id.fragment_choose_college_loadingWidget)
    LoadingWidget loadingWidget;
    @BindView(R.id.fragment_choose_college_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_choose_college_editText)
    EditText editText;

    private IPersonalP iPersonalP;

    @OnClick(R.id.fragment_choose_college_imageButton_return)
    void returnImageButton(){
        ((PersonalItemActivity) Objects.requireNonNull(getActivity())).replaceFragment(R.id.activity_personal_item_frameLayout, new MyDataFragment(), "MyDataFragment");
    }

    @Override
    int getLayout() {
        return R.layout.fragment_choose_college;
    }

    @Override
    void initView(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        iPersonalP = new PersonalP(getContext(), this);
        editText.addTextChangedListener(iPersonalP.getTextWatcher());
        AssetManager assetManager = Objects.requireNonNull(getContext()).getAssets();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open("university.js")));
            String str;
            StringBuilder stringBuilder = new StringBuilder();
            while ((str = bufferedReader.readLine())!= null){
                stringBuilder.append(str);
            }
            Place place = Jsoner.toObj(stringBuilder.toString(), Place.class);
            List<String> universities = new ArrayList<>();
            for(int i = 0; i < place.getProvinces().size(); i++){
                for(int j = 0; j < place.getProvinces().get(i).getCities().size(); j++){
                    universities.addAll(place.getProvinces().get(i).getCities().get(j).getUniversities());
                }
            }
            Logger.d("大学数量：" + universities.size());
            recyclerView.setAdapter(new MyRecyclerViewAdapter(getContext(),R.layout.item_college, universities));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeUniversityInfo(String name) {
        MyDataFragment myDataFragment = new MyDataFragment();
        ((PersonalItemActivity) Objects.requireNonNull(getActivity()))
                .replaceFragment(R.id.activity_personal_item_frameLayout, myDataFragment, "MyDataFragment");
    }

    @Override
    public void updateUniversity(List<String> universities) {
        recyclerView.setAdapter(new MyRecyclerViewAdapter(getContext(),R.layout.item_college, universities));
    }

    @Override
    public void unAttach() {
        iPersonalP = null;
    }

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
        Toaster.shortShow(getContext(),message);
    }

    @Override
    public void loginInvalid() {
        invalid();
    }

    class MyRecyclerViewAdapter extends GeneralRecyclerViewAdapter<String>{
        MyRecyclerViewAdapter(Context context, int layoutId, List<String> dataList) {
            super(context, layoutId, dataList);
        }
        @Override
        public void handle(GeneralRecyclerViewHolder holder, String data, int position) {
            ((TextView)holder.getView(R.id.item_college_textView)).setText(data);
            holder.getView(R.id.item_college_constraintLayout).setOnClickListener(v -> iPersonalP.setUserUniversity(data));
        }
        @Override
        public SparseArray<Integer> setType(SparseArray<Integer> layoutId_position) {
            return null;
        }
    }
}
