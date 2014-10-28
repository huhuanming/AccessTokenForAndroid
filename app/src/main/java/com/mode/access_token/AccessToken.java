package com.mode.access_token;

import android.content.SharedPreferences;

import com.example.hu.accesstoken.ApplicationRunTime;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by hu on 14/10/27.
 */

public class AccessToken {
    static final String kToken = "tokenForAccessToken";
    static final String kKey = "keyForAccessToken";
    static final String kIsHasTokenAndKey = "isHasTokenAndKeyForAccessToken";
    private boolean isHasTokenAndKey = false;

    public static AccessToken getInstance(){
        return AccessTokenHolder.accessToken;
    }

    public boolean isHasTokenAndKey(){
        return this.isHasTokenAndKey;
    }

    public void setTokenAndKey(String token, String key){
        SharedPreferences mshared = ApplicationRunTime.getAppContext().getSharedPreferences("access_token", 0);
        SharedPreferences.Editor editor = mshared.edit();
        editor.putString(kToken,token);
        editor.putString(kKey,key);
        editor.putBoolean(kIsHasTokenAndKey, true);
        this.isHasTokenAndKey = true;
        editor.commit();
    }

    public String authorized(HashMap<String, Object> params){
        return authorized(params, 60);
    }

    public String authorized(HashMap<String, Object> params, long ttl){
        ArrayList<String> keys =new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        StringBuilder paramsStringBuilder = new StringBuilder();
        for (String key : keys){
            paramsStringBuilder.append(key).append("=").append(params.get(key));
        }
        long theTTL = ttl;
        if(ttl > 120 || ttl < 0){
            ttl = 60;
        }
        long expires = System.currentTimeMillis()/1000 + theTTL;
        paramsStringBuilder.append(getKey());
        paramsStringBuilder.append(expires);
        return new StringBuilder(getToken()).append("_").append(md5(paramsStringBuilder.toString())).append("_").append(expires).toString();
    }

    public void clearTokenAndKey(){
        SharedPreferences shared = ApplicationRunTime.getAppContext().getSharedPreferences("access_token", 0);
        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.commit();
        this.isHasTokenAndKey = false;
    }

    public static int version(){
        return 2;
    }

    private static class AccessTokenHolder{
        public static AccessToken accessToken = init();

        private static AccessToken init(){
            AccessToken theAccessToken = new AccessToken();
            SharedPreferences shared = ApplicationRunTime.getAppContext().getSharedPreferences("access_token", 0);
            theAccessToken.isHasTokenAndKey = shared.getBoolean(kIsHasTokenAndKey, false);
            return theAccessToken;
        }
    }

    private String getToken(){
        SharedPreferences shared = ApplicationRunTime.getAppContext().getSharedPreferences("access_token", 0);
        return shared.getString(kToken, "token");
    }

    private String getKey(){
        SharedPreferences shared = ApplicationRunTime.getAppContext().getSharedPreferences("access_token", 0);
        return shared.getString(kKey, "key");
    }

    private String md5(String str)
    {
        MessageDigest md5 = null;
        try
        {
            md5 = MessageDigest.getInstance("MD5");
        }catch(Exception e)
        {
            e.printStackTrace();
            return "";
        }

        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for(int i = 0; i < charArray.length; i++)
        {
            byteArray[i] = (byte)charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();
        for( int i = 0; i < md5Bytes.length; i++)
        {
            int val = ((int)md5Bytes[i])&0xff;
            if(val < 16)
            {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
