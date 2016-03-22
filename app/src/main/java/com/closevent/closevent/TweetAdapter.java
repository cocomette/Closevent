package com.closevent.closevent;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.closevent.closevent.service.Event;
import com.closevent.closevent.service.Tweet;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Côme on 05/03/2016.
 */
public class TweetAdapter extends ArrayAdapter<Tweet> {
    public TweetAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    Bitmap drawable_from_url(String url) throws java.net.MalformedURLException, java.io.IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection)new URL(url) .openConnection();
        connection.setRequestProperty("User-agent","Mozilla/4.0");

        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return x;
    }

    class DownloadPic extends AsyncTask<String, Void, String> {

        public Activity activity;
        Bitmap avatar;
        TweetViewHolder viewHolder;


        public DownloadPic(Activity a, TweetViewHolder viewHolder)
        {
            this.activity = a;
            this.viewHolder = viewHolder;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            try {
                if( url != "" ) {
                    avatar = drawable_from_url(url);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "FAIL";
            }
            return "SUCCESS";
        }

        protected void onProgressUpdate(Void... arg){
        }

        @Override
        protected void onPostExecute(String result) {
            while(viewHolder == null){
                System.out.println("WAIT view");
                try{
                    Thread.sleep(200);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            if( avatar == null ) {
                viewHolder.avatar.setImageDrawable(new ColorDrawable(Color.BLACK));
            } else {
                viewHolder.avatar.setImageBitmap(avatar);
            }
        }

        @Override
        protected void onCancelled() {
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_tweet,parent, false);
        }

        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TweetViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Tweet tweet = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.pseudo.setText(tweet.user.name);
        viewHolder.text.setText(tweet.comment);
        DownloadPic task = new DownloadPic((Activity)getContext(), viewHolder);
        task.execute(tweet.user.picture_url);
        return convertView;
    }

    public void updateOrgTweets( Event e) {
        this.clear();
        for( Tweet t:e.org_thread) {
            this.add(t);
        }
    }

    public void updateMainTweets( Event e) {
        this.clear();
        for( Tweet t:e.main_thread) {
            this.add(t);
        }
    }
    private class TweetViewHolder{
        public TextView pseudo;
        public TextView text;
        public ImageView avatar;
    }
}

