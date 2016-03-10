package com.closevent.closevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.closevent.closevent.service.CloseventAPI;
import com.closevent.closevent.service.ResUser;
import com.closevent.closevent.service.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.HEAD;

/**
 * Created by Côme on 05/03/2016.
 */
public class LoginActivity extends AppCompatActivity{
    private CallbackManager callbackManager;
    public CloseventAPI api;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rocky-mesa-88769.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
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
            this.finish();
        }
        setContentView(R.layout.login_activity);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.log_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String id = loginResult.getAccessToken().getUserId();
                String name = "Henri HANNETEL";
                String picture = "";
                User user = new User(id, name, picture);
                Call<ResUser> req = api.createUser(user);
                req.enqueue(new Callback<ResUser>() {
                                @Override
                                public void onResponse(Call<ResUser> req, Response<ResUser> response) {
                                    try {
                                        System.out.println("User ID: " + response.body().user_id);
                                    } catch (Exception e) {
                                        try {
                                            System.out.println("ERROR: " + response.errorBody().string());
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResUser> req, Throwable t) {
                                    System.out.println(t);
                                }
                            });

                // Launch main activity
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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
    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

}