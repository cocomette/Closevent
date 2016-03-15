package com.closevent.closevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;

import com.closevent.closevent.service.CloseventApi;
import com.closevent.closevent.service.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Côme on 05/03/2016.
 */
public class LoginActivity extends AppCompatActivity{
    private CallbackManager callbackManager;
    public static CloseventApi api;
    public static AccessToken fbToken;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(getApplicationContext());
        FacebookSdk.sdkInitialize(getApplicationContext());
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rocky-mesa-88769.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api = retrofit.create(CloseventApi.class);
        try{
            Thread.sleep(200);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        fbToken = AccessToken.getCurrentAccessToken();
        if( fbToken != null ){
            Intent intent = new Intent().setClass(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
        setContentView(R.layout.login_activity);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.log_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                fbToken = loginResult.getAccessToken();
                String id = fbToken.getUserId();
                String name = "Henri HANNETEL";
                String picture = "";
                User user = new User(id, name, picture);
                user.register();


                // Launch main activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {
                System.out.println("on error");
            }


        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        this.finish();
    }

}