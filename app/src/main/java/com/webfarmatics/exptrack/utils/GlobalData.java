package com.webfarmatics.exptrack.utils;

import android.content.Context;
import android.widget.Toast;

public class GlobalData {

    public static final String ADD_ID = "ca-app-pub-3417932029149162~3544947789";

    public static final String APP_LOGIN_STATUS = "APP_LOGIN_STATUS";
    public static final String LOGED_IN = "LOGED_IN";
    public static final String LOGED_OUT = "LOGED_OUT";

    public static final String APP_SIGNUP_STATUS = "APP_SIGNUP_STATUS";
    public static final String SIGNUP_COMPLETE = "SIGNUP_COMPLETE";
    public static final String SIGNUP_NOT = "SIGNUP_NOT";

    public static final String USER_NAME = "PET_NAME";
    public static final String PASSWORD = "PET_NAME";
    public static final String FIRST_TIME = "FIRST_TIME";
    public static final String FIRST_COMPLETE = "FIRST_COMPLETE";
    public static final String NICK_NAME = "NICK_NAME";

    public static final String REMEMBER_ME = "REMEMBER_ME";
    public static final String YES_REMBER = "YES_REMBER";
    public static final String NO_REMBER = "NO_REMBER";

    public static final String READ_COPYRIGHTS = "READ_COPYRIGHTS";
    public static final String YES_READ = "YES_READ";
    public static final String NO_READ = "NO_READ";


    public static final String LANGUAGE_SELECTED = "LANGUAGE_SELECTED";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String HINDI_LANGUAGE = "HINDI";
    public static final String ENGLISH_LANGUAGE = "ENGLISH";
    public static final String YES = "YES";
    public static final String NO = "NO";

    public static final String LAST_CLEAR_DATE = "LAST_CLEAR_DATE";


    public static final String PURCHASE_STATUS = "PURCHASE_STATUS";
    public static final String COMPLETE = "COMPLETE";


    public static void toastMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


}
