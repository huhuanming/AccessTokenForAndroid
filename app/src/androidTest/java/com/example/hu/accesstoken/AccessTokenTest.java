package com.example.hu.accesstoken;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.mode.access_token.AccessToken;

import java.security.MessageDigest;
import java.util.HashMap;

/**
 * Created by hu on 14/10/28.
 */
public class AccessTokenTest extends ApplicationTestCase<Application>  {

    public AccessTokenTest() {
        super(Application.class);
    }

    @SmallTest
    public void testIsHasTokenAndKey() {
        assertFalse(AccessToken.getInstance().isHasTokenAndKey());

        AccessToken.getInstance().setTokenAndKey("ddc7844f-99f0-4b38-9f58-0d7e510930db", "pWGzxjnU9tMZRckzV3AEFQ");

        assertTrue(AccessToken.getInstance().isHasTokenAndKey());

        AccessToken.getInstance().clearTokenAndKey();

        assertFalse(AccessToken.getInstance().isHasTokenAndKey());
    }

    @SmallTest
    public void testVersion() {
        assertEquals(2, AccessToken.version());
    }

    @SmallTest
    public void testAuthorized() {
        AccessToken.getInstance().setTokenAndKey("ddc7844f-99f0-4b38-9f58-0d7e510930db", "pWGzxjnU9tMZRckzV3AEFQ");

        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("b","++fda_fafa_");
        params.put("a",2);
        params.put("c","\"\"...+++");

        String authorization  = AccessToken.getInstance().authorized(params);
        String[] splits = authorization.split("_");

        assertEquals("ddc7844f-99f0-4b38-9f58-0d7e510930db", splits[0]);
        assertEquals(md5("a=2b=++fda_fafa_c=\"\"...+++"+"pWGzxjnU9tMZRckzV3AEFQ"+splits[2]), splits[1]);
        assertEquals(10, splits[2].length());

        AccessToken.getInstance().clearTokenAndKey();
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
