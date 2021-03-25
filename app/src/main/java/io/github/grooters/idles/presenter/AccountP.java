package io.github.grooters.idles.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import androidx.annotation.NonNull;
import com.orhanobut.logger.Logger;
import java.util.Objects;
import io.github.grooters.idles.bean.Result;
import io.github.grooters.idles.bean.Token;
import io.github.grooters.idles.bean.User;
import io.github.grooters.idles.model.AccountM;
import io.github.grooters.idles.model.interfac.IAccountM;
import io.github.grooters.idles.model.interfac.ModelCallBack;
import io.github.grooters.idles.net.Code;
import io.github.grooters.idles.net.Server;
import io.github.grooters.idles.presenter.interfac.IAccountP;
import io.github.grooters.idles.utils.Constant;
import io.github.grooters.idles.utils.Encrypter;
import io.github.grooters.idles.utils.FileIOer;
import io.github.grooters.idles.utils.Jsoner;
import io.github.grooters.idles.utils.SharePrefer;
import io.github.grooters.idles.utils.Toaster;
import io.github.grooters.idles.view.activity.AccountActivity;
import io.github.grooters.idles.view.fragment.LoginFragment;
import io.github.grooters.idles.view.fragment.RegisterFragment;
import io.github.grooters.idles.view.interfac.IAccountV;
import io.github.grooters.idles.view.interfac.ILoginV;
import io.github.grooters.idles.view.interfac.IRegisterV;

public class AccountP implements IAccountP {
    private Context context;
    private ILoginV iLoginV;
    private IRegisterV iRegisterV;
    private IAccountM iAccountM;
    private IAccountV iAccountV;
    private User user;
    private int backCount = 0;
    private int configCount = 0;
    private boolean isRememberAccount;
    private int verifyTime = 0;

    private boolean end;

    public AccountP(Context context, LoginFragment loginFragment){
        iLoginV = loginFragment;
        this.context = context;
        iAccountM = new AccountM();
    }

    public AccountP(Context context, RegisterFragment registerFragment){
        iRegisterV = registerFragment;
        this.context = context;
        iAccountM = new AccountM();
        end = false;
    }

    public AccountP(AccountActivity accountActivity){
        iAccountV = accountActivity;
        this.context = accountActivity;
        iAccountM = new AccountM();
    }

    @Override
    public void getVerification(String email, String emailServer, String emailServerKey) {
        if(email.equals("") ){
            iRegisterV.showMessage("邮箱地址不能为空");
            return;
        }
        if(verifyTime != 0){
            iRegisterV.showMessage("需要等待"+verifyTime+"秒后才能再次获得验证码");
            return;
        }
        iAccountM.getVerification(email, emailServer, emailServerKey, new ModelCallBack<Result>() {
            @Override
            public void success(Result data) {
                if(data.getCode() == Code.REGISTER_SUCCESS_VERIFICATION){
                    new VerificationThread().start();
                    iRegisterV.showVerificationSuccess(data.getMessage());
                }
                iRegisterV.showMessage(data.getMessage());
            }
            @Override
            public void failure(String message) {
                // 接口调用问题
                iRegisterV.showMessage(message);
            }
        });
    }
    @Override
    public void verify(String email, String verificationNumber) {
        iAccountM.verify(email, verificationNumber, new ModelCallBack<Result>() {
            @Override
            public void success(Result data) {
                if(data.getCode() == Code.REGISTER_SUCCESS_VERIFY){
                    iRegisterV.verifySuccess();
                }else{
                    iRegisterV.verifyFailure();
                }
                iRegisterV.showMessage(data.getMessage());
            }
            @Override
            public void failure(String message) {
                iRegisterV.showMessage(message);
            }
        });
    }

    @Override
    public void register(int type, String email, String password, String passwordSend) {
        if(!password.equals(passwordSend)){
            iRegisterV.showMessage("两次密码输入不一致");
            return;
        }
        if(type == LoginFragment.REGISTER_ACCOUNT_FRAGMENT) {
            iRegisterV.setRegisterButtonText("注册");
            // 注册账号
            Logger.d("注册邮箱：" + email + "注册密码：" +password);
            iAccountM.register(email, password, new ModelCallBack<User>() {
                @Override
                public void success(User data) {
                    if(data.getCode() == Code.REGISTER_SUCCESS){
                        end = true;
                        iRegisterV.showRegisterSuccess(data);
                        return;
                    }else if(data.getCode() == Code.REGISTER_FAILURE_VERIFICATION){
                        iRegisterV.verifyFailure();
                    }
                    iRegisterV.showMessage(data.getMessage());
                }
                @Override
                public void failure(String message) {
                    iRegisterV.showMessage("接口请求错误");
                }
            });
        }
        else if (type == LoginFragment.FIND_PASSWORD_FRAGMENT){
            iRegisterV.setRegisterButtonText("找回");
            // 找回密码
            iAccountM.register(email, password, new ModelCallBack<User>() {
                @Override
                public void success(User data) {
                    iRegisterV.showMessage("新密码设置成功");
                }
                @Override
                public void failure(String message) {
                    Toaster.shortShow(context, "接口请求出错");
                }
            });
        }
    }

    @Override
    public boolean endActivity(int keyCode, KeyEvent event) {
        Logger.d(++backCount);
        new EndThread().start();
        if(keyCode == KeyEvent.KEYCODE_BACK && backCount == 1){
            Toaster.shortShow(context, "再按一次退出程序");
            ++backCount;
        }else if(keyCode == KeyEvent.KEYCODE_BACK){
            iAccountV.destroy();
        }
        return false;
    }

    @Override
    public void config() {
        new ConfigThread().start();
        ++configCount;
        if(configCount == 5){
            iLoginV.showConfigDialog();
            configCount = 0;
        }
    }

    @Override
    public void initServerUrl() {
        String url = Encrypter.fromBase64((String) Objects.requireNonNull(
                SharePrefer.get(context, ConfigP.SERVER_CONFIG, "url", String.class)));

        if(url.equals("")){
            return;
        }

        String[] urls = url.split("\\|");
        if(urls.length == 2){
            Server.localUrl = url.split("\\|")[0].split("-")[1];
            Server.netLocal = url.split("\\|")[1].split("-")[1];
        }else{
            Server.localUrl = url.split("\\|")[0].split("-")[1];
        }
    }

    @Override
    public void showOriHidePassword(boolean b) {
        if(b){
            iLoginV.showPassword();
        }else{
            iLoginV.hidePassword();
        }
    }

    @Override
    public void rememberAccount(boolean b, String number, String password) {
        if(!b){
            iLoginV.changeRememberIcon();
        }else{
            Logger.d("取消记住账户");
            iLoginV.forgetAccount();
            SharePrefer.remove(context, Constant.ACCOUNT_REMEMBER, "number");
            SharePrefer.remove(context, Constant.ACCOUNT_REMEMBER, "password");
        }
        isRememberAccount = !b;
    }

    @Override
    public TextWatcher getTextWatcher() {
        return new IsEmpty();
    }

    @Override
    public void login(String number, String password) {
        if(number.equals("") || password.equals("")){
            Toaster.shortShow(context, "账户或密码不能为空");
            return;
        }
        iLoginV.startLoading();
        iAccountM.login(number, password, new ModelCallBack<User>() {
            @Override
            public void success(User data) {
                user = data;
                if(data.getCode() == Code.LOGIN_SUCCESS){
                    if( isRememberAccount){
                        Logger.d("需要记住账户");
                        SharePrefer.set(context,  Constant.ACCOUNT_REMEMBER, "number", Encrypter.toBase64(user.getUserNumber()));
                        SharePrefer.set(context, Constant.ACCOUNT_REMEMBER, "password", Encrypter.toBase64(user.getPassword()));
                    }else{
                        SharePrefer.remove(context,  Constant.ACCOUNT_REMEMBER, "number");
                        SharePrefer.remove(context, Constant.ACCOUNT_REMEMBER, "password");
                    }
                    ConfigP.setUser(context, user);
                    iLoginV.stopLoading();
                    iLoginV.showMessage(user.getMessage());
                    iLoginV.jumpToMainActivity();
                    return;
                }
                iLoginV.stopLoading();
                iLoginV.showMessage(user.getMessage());
            }
            @Override
            public void failure(String message) {
                iLoginV.stopLoading();
                iLoginV.showMessage(message);
            }
        });
    }

    @Override
    public void loginAsVisitor() {
        iLoginV.startLoading();
        // 拿到临时访问令牌
        iAccountM.getToken(new ModelCallBack<Token>() {
            @Override
            public void success(Token token) {
                if(token.getCode() == Code.LOGIN_SUCCESS_TOKEN){
                    FileIOer.writeString(context.getFilesDir().getPath(), Constant.ACCOUNT_VISITOR, Encrypter.toBase64(Jsoner.toJson(token)));
                    iLoginV.stopLoading();
                    iLoginV.jumpToMainActivity();
                    return;
                }
                iLoginV.showMessage(token.getMessage());
            }
            @Override
            public void failure(String message) {
                iLoginV.stopLoading();
                iLoginV.showMessage(message);
            }
        });
    }

    @Override
    public void judgeAccount() {
        String number = (String) SharePrefer.get(context, Constant.ACCOUNT_REMEMBER, "number", String.class);
        String password = (String) SharePrefer.get(context, Constant.ACCOUNT_REMEMBER, "password", String.class);

        if(number == null || number.equals("") || password == null || password.equals("")){
            Logger.d("没有记住账户");
            return;
        }
        Logger.d("已经记住账户" + "- number：" + number);
        isRememberAccount = true;
        iLoginV.changeRememberIcon();
        iLoginV.initAccount(Encrypter.fromBase64(number), Encrypter.fromBase64(password));
    }

    @Override
    public boolean getIsRemember() {
        return isRememberAccount;
    }

    @Override
    public void unAttach() {
        iAccountM = null;
        iRegisterV = null;
        iLoginV = null;
        context = null;
    }

    class ConfigThread extends Thread {
        int time = 0;
        @Override
        public void run() {
            super.run();
            while(time++ < 2){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            configCount = 0;
        }
    }

    class IsEmpty implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if( s.toString().equals("") ){
                iLoginV.hideClearButton();
            }else {
                iLoginV.showClearButton();
            }
        }
        @Override
        public void afterTextChanged(Editable s) { }
    }

    // 倒计时再次获取验证码
    class VerificationThread extends Thread{
        @Override
        public void run() {
            super.run();
            verifyTime = 60;
            while(--verifyTime > 0){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(end)
                return;
            HandleUi handleUi = new HandleUi(Looper.getMainLooper());
            Message message = Message.obtain();
            message.obj = iRegisterV;
            handleUi.sendMessage(message);
        }
    }

    static class HandleUi extends Handler {

        HandleUi(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {

            IRegisterV iRegisterV = (IRegisterV) msg.obj;
            super.handleMessage(msg);

            if(iRegisterV != null)
                iRegisterV.initVerificationTextView();
        }
    }

    // 倒计时按两次返回退出程序
    class EndThread extends Thread {
        int time = 0;
        @Override
        public void run() {
            super.run();
            while(time++ < 2){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            backCount = 0;
        }
    }
}
