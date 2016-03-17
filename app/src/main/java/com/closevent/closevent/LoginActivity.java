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
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rocky-mesa-88769.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
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
                GraphRequest request = GraphRequest.newMeRequest(
                        fbToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted( JSONObject object, GraphResponse response) {
                                System.out.println(object.toString());
                                String name = "";
                                try {
                                    name = (String) object.get("name");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                String picture_url = "";
                                try {
                                    JSONObject picture = (JSONObject) ((JSONObject)object.get("picture")).get("data");
                                    picture_url = (String) picture.get("url");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                User user = new User(fbToken.getUserId(), name, picture_url);
                                user.register();
                            }
                        }
                );
                Bundle parameters = new Bundle();
                parameters.putString("fields", "name,picture");
                request.setParameters(parameters);
                request.executeAsync();


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