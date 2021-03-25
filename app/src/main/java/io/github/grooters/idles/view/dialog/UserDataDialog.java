package io.github.grooters.idles.view.dialog;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.orhanobut.logger.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.grooters.idles.R;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.presenter.PersonalP;
import io.github.grooters.idles.presenter.interfac.IPersonalP;
import io.github.grooters.idles.utils.ResourcEr;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.activity.AccountActivity;
import io.github.grooters.idles.view.fragment.LoginFragment;
import io.github.grooters.idles.view.interfac.IUserDataV;

public class UserDataDialog extends BaseDialog implements IUserDataV {

    @BindView(R.id.dialog_user_data_imageButton_home)
    ImageButton homeImageButton;
    @BindView(R.id.dialog_user_data_imageButton_avatar)
    ImageButton avatarImageButton;
    @BindView(R.id.dialog_user_data_imageButton_male)
    ImageButton maleImageButton;
    @BindView(R.id.dialog_user_data_imageButton_feMale)
    ImageButton feMaleImageButton;
    @BindView(R.id.dialog_user_data_editText_number)
    EditText numberEditText;
    @BindView(R.id.dialog_user_data_editText_name)
    EditText nameEditText;
    @BindView(R.id.dialog_user_data_editText_resume)
    EditText resumeEditText;

    // 通过Map容器来确定按钮状态（点击与被点击）
    private Map<Boolean, Integer> male;
    private Map<Boolean, Integer> feMale;
    private Map<Boolean, Integer> home;
    private Map<Boolean, Integer> university;
    private boolean maleIsChoose;
    private boolean feMaleIsChoose;
    private String avatarUrl, locationName;
    private IPersonalP iPersonalP;

    private User data;

    public UserDataDialog(User data){
        this.data = data;
    }

    @OnClick(R.id.dialog_user_data_imageButton_home)
    void locationImageButtonOnClick(){
        // 如果locationName != null说明已经选择了地点，再次点击则取消选择结果
        if(locationName != null){
            homeImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_home, null));
            locationName = null;
            return;
        }
        ChooseLocationDialog chooseLocationDialog = new ChooseLocationDialog(this);
        chooseLocationDialog.show(Objects.requireNonNull(getFragmentManager()), "chooseLocationDialog");
    }

    @OnClick(R.id.dialog_user_data_imageButton_avatar)
    void avatarImageButtonOnClick(){
        if(avatarUrl == null){
            avatarImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_avatar, null));
            return;
            // todo
        }
    }

    @OnClick(R.id.dialog_user_data_imageButton_male)
    void maleImageButtonOnClick(){
        maleImageButton.setImageDrawable(getResources().getDrawable(male.get(!maleIsChoose), null));
        if(!(!maleIsChoose && !feMaleIsChoose)){
            feMaleImageButton.setImageDrawable(getResources().getDrawable(feMale.get(!feMaleIsChoose), null));
            feMaleIsChoose = !feMaleIsChoose;
        }
        maleIsChoose = !maleIsChoose;
    }

    @OnClick(R.id.dialog_user_data_imageButton_feMale)
    void feMaleImageButton(){
        feMaleImageButton.setImageDrawable(getResources().getDrawable(feMale.get(!feMaleIsChoose), null));
        if(!(!maleIsChoose && !feMaleIsChoose)){
            maleImageButton.setImageDrawable(getResources().getDrawable(male.get(!maleIsChoose), null));
            maleIsChoose = !maleIsChoose;
        }
        feMaleIsChoose = !feMaleIsChoose;
    }

    @OnClick(R.id.dialog_user_data_button_cancel)
    void cancelButtonOnClick(){
        dismiss();
    }

    @OnClick(R.id.dialog_user_data_button_ok)
    void okButtonOnClick(){
        String gender = male.get(!maleIsChoose) == R.drawable.ic_male ? "男" : "女";
        if(gender == null){
            gender = "test";
            Logger.d("姓别信息为null");
        }else if(locationName == null){
            locationName = "test";
            Logger.d("地址信息为null");
        }else if(avatarUrl == null){
            locationName = "test";
            Logger.d("头像信息为null");
        }
        // 将补充的信息上传到云端
        iPersonalP.setUserData(data.getUserNumber(),
                numberEditText.getText().toString(), nameEditText.getText().toString(), gender, locationName, avatarUrl);
        // 返回到登录界面
        ((AccountActivity) Objects.requireNonNull(getActivity()))
                .replaceFragment(R.id.login_frame_fragment_container, new LoginFragment(), "LoginFragment");
    }

    // 完成大学选择操作返回选择结果
    @Override
    public void provideAvatar(String avatar) {
        avatarImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_avatar_press, null));
        this.avatarUrl = avatar;
    }

    @Override
    public void provideLocation(String locationName) {
        homeImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_press, null));
        this.locationName = locationName;
    }

    @Override
    public void onResume() {
        super.onResume();
        setSize( ResourcEr.getScreenWidth(Objects.requireNonNull(getActivity())), (int) ResourcEr.dp2px(400));
//        setTransaction();
    }

    @Override
    public void unAttach() {
        iPersonalP = null;
        male = null;
        feMale = null;
        home = null;
        university = null;
    }

    @Override
    public void showMessage(String message) {
        Toaster.shortShow(getContext(), message);
    }

    @Override
    public int getLayout() {
        return R.layout.dialog_user_data;
    }

    @Override
    public void initView(View view) {
        iPersonalP = new PersonalP(getContext(), this);
        male = new HashMap<>();
        male.put(true, R.drawable.ic_male_press);
        male.put(false, R.drawable.ic_male);
        feMale = new HashMap<>();
        feMale.put(true, R.drawable.ic_female_press);
        feMale.put(false, R.drawable.ic_female);
        home = new HashMap<>();
        home.put(true, R.drawable.ic_home_press);
        home.put(false, R.drawable.ic_home);
        university = new HashMap<>();
        university.put(true, R.drawable.ic_university_press);
        university.put(false, R.drawable.ic_university);
        maleIsChoose = false;
        feMaleIsChoose = false;
    }

    @Override
    public void startLoading() { }

    @Override
    public void stopLoading() { }

    @Override
    public void loginInvalid() { }
}
