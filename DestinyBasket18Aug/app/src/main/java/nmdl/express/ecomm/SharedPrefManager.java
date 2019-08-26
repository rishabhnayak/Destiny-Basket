package nmdl.express.ecomm;

import android.content.Context;
import android.content.SharedPreferences;

import nmdl.express.ecomm.response.User;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "raysbatchsharedpref";


    private static final String KEY_USER_UNAME = "keyusername";
    private static final String KEY_USER_MOBILE = "keyusermobile";
    private static final String KEY_USER_ID = "keyuserid";
    private static final String KEY_USER_ADRESS = "keyuseradress";
    private static final String KEY_USER_EMAIL = "keyuseremail";
    private static final String KEY_USER_CITY = "keyusercity";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(String uname,String mobile,String email,String id,String adress) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_UNAME, uname);
        editor.putString(KEY_USER_MOBILE, mobile);

        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_ID, id);
        editor.putString(KEY_USER_ADRESS, adress);

        editor.apply();
        return true;
    }
    public boolean userCity(String city){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_CITY, city);
        editor.apply();
        return true;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_MOBILE, null) != null)
            return true;
        return false;
    }
    public boolean isCitySet(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_CITY, null) != null)
            return true;
        return false;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_USER_UNAME, null),
                sharedPreferences.getString(KEY_USER_MOBILE, null),
                sharedPreferences.getString(KEY_USER_EMAIL, null),
                sharedPreferences.getString(KEY_USER_ID, null),
                sharedPreferences.getString(KEY_USER_ADRESS, null)
        );
    }
    public String getCity(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_CITY, null);
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.clear();
        editor.remove(KEY_USER_UNAME);
        editor.remove(KEY_USER_MOBILE);
        editor.remove(KEY_USER_EMAIL);
        editor.remove(KEY_USER_ID);
        editor.remove(KEY_USER_ADRESS);
        editor.apply();
        return true;
    }
    public boolean clearCity(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.clear();
        editor.remove(KEY_USER_CITY);

        editor.apply();
        return true;
    }
}
