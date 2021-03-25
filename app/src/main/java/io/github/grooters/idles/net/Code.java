package io.github.grooters.idles.net;

public class Code {
    // 登录功能： 1   -  10
    // 注册功能： 11  -  20
    // 销售功能： 21  -  30
    // 购买功能： 31  -  40
    // 账户功能： 41  -  50

    public static final int UNKNOWN = 0;

    public static final int LOGIN_SUCCESS = 1;

    public static final int LOGIN_FAILURE_PASSWORD = 2;

    public static final int LOGIN_FAILURE_NUMBER = 3;

    public static final int LOGIN_INVALID = 4;

    public static final int LOGIN_FAILURE_VERIFICATION = 5;

    public static final int LOGIN_SUCCESS_TOKEN = 6;

    public static final int REGISTER_SUCCESS_VERIFICATION = 11;

    public static final int REGISTER_FAILURE_VERIFICATION = 12;

    public static final int REGISTER_FAILURE_EMAIL = 13;

    public static final int REGISTER_SUCCESS_VERIFY = 14;

    public static final int REGISTER_FAILURE_VERIFY = 15;

    public static final int REGISTER_SUCCESS = 16;

    public static final int REGISTER_FAILURE = 17;

    public static final int GOODS_SUCCESS_GET_ALL = 21;

    public static final int GOODS_SUCCESS_EMPTY = 22;

    public static final int GOODS_FAILURE_GET_ALL = 23;

    public static final int GOODS_FAILURE_PUSH = 24;

    public static final int GOODS_SUCCESS_PUSH = 25;

    public static final int GOODS_SUCCESS_COLLECT = 26;

    public static final int GOODS_SUCCESS_CANCEL_COLLECT = 27;

    public static final int WORKS_SUCCESS_GET_ALL = 31;

    public static final int WORKS_SUCCESS_EMPTY = 32;

    public static final int WORKS_FAILURE_GET_ALL = 33;

    public static final int WORKS_FAILURE_PUSH = 34;

    public static final int WORKS_SUCCESS_PUSH = 35;

    public static final int PERSONAL_SUCCESS_COLLECTION = 41;

    public static final int PERSONAL_SUCCESS_HISTORY = 42;

    public static final int PERSONAL_SUCCESS_SELL = 43;

    public static final int PERSONAL_SUCCESS_BUY = 44;

    public static final int PERSONAL_SUCCESS_SETTING= 45;
}
