package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManagement {

    public static SharedPreferences usersSesion;
    public static SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN="IsLoggedIn";

    public static final String KEY_FULLNAME="fullName";
    public static final String KEY_USERNAME="username";
    public static final String KEY_EMAIL="email";
    public static final String KEY_PASSWORD="password";

    public SessionManagement(Context _context)
    {   context=_context;
        usersSesion= _context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        editor=usersSesion.edit();

    }

    public void createLoginSession(String fullname, String username, String email, String password)
    {
        editor.putBoolean("IS_LOGIN",true);
        editor.putString(KEY_FULLNAME,fullname);
        editor.putString(KEY_USERNAME,username);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PASSWORD,password);

        editor.commit();
    }

    public HashMap<String, String> getUserDetailFromSession()
    {
        HashMap<String,String > userData= new HashMap<String ,String>();
        userData.put(KEY_FULLNAME, usersSesion.getString(KEY_FULLNAME,null));
        userData.put(KEY_USERNAME, usersSesion.getString(KEY_USERNAME,null));
        userData.put(KEY_EMAIL, usersSesion.getString(KEY_EMAIL,null));
        userData.put(KEY_PASSWORD, usersSesion.getString(KEY_PASSWORD,null));

        return userData;

    }

    public static boolean checkLogIn()
    {
        if(usersSesion.getBoolean(IS_LOGIN,false))
        {
            return true;
        }
        else
            return false;
    }

    public static void logOutFromSession()
    {
        editor.clear();
        editor.commit();
    }
















    /*String SHARED_PREF_NAME="session";
    public static String SESSION_KEY="session user";
    public SessionManagement(Context context)
    {
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();

    }

    public void saveSession(UserHelper user)
    {
       String username= user.getUsername();
       editor.putString(SESSION_KEY,username).commit();
    }

    public  String getSession()
    {
        return sharedPreferences.getString(SESSION_KEY, null);

    }*/

}
