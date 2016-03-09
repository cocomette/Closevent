package com.closevent.closevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.closevent.closevent.service.CloseventAPI;
import com.closevent.closevent.service.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import retrofit2.Retrofit;

/**
 * Created by Côme on 05/03/2016.
 */
public class LoginActivity extends AppCompatActivity{
    private CallbackManager callbackManager;
    public CloseventAPI api;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait_for_log);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rocky-mesa-88769.herokuapp.com/")
                .build();
        api = retrofit.create(CloseventAPI.class);
        try{
            Thread.sleep(200);
        }
        catch (Exception e){
        }
        if(isLoggedIn()==true){
            System.out.println("OOOOOOKKKKKKKKKKK");
            Intent intent = new Intent().setClass(this, MainActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.login_activity);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.log_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String id = loginResult.getAccessToken().getToken();
                String name = "Henri HANNETEL";
                String picture = "";
                User user = new User(id, name, picture);
                api.createUser(user);

                // Launch main activity
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }


        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

}