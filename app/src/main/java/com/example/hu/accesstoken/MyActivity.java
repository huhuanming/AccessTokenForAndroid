package com.example.hu.accesstoken;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mode.access_token.AccessToken;

import java.util.HashMap;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        AccessToken.getInstance().setTokenAndKey("ddc7844f-99f0-4b38-9f58-0d7e510930db","pWGzxjnU9tMZRckzV3AEFQ");
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("b", "想当年，我和xx谈笑风生");
        params.put("a", 1);
        params.put("c", "aaaaaa");
        Log.e("authorization", AccessToken.getInstance().authorized(params));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
