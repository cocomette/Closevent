package com.closevent.closevent;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.closevent.closevent.service.Post;

public class PostActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editPost;
    private TextView editPic;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        editPost = (EditText) findViewById(R.id.editPost);
        editPost.requestFocus();
        editPic = (TextView) findViewById(R.id.editPic);

        editPic.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_validate:
                // Perform action on click
                //fillForm();
                boolean completed = true;

                if (editPost.getText().length() <= 0) {
                    editPost.setError("You must fill this field");
                    completed = false;
                } else {
                    editPost.setError(null);
                }

                if (completed) {
                    String admin = "False";
                    if( LoginActivity.fbToken.getUserId() == TweetFragment.event.user_id ) {
                        admin = "True";
                    }
                    Post post = new Post(editPost.getText().toString(), LoginActivity.fbToken.getUserId(), admin);
                    post.save(TweetFragment.event.id);
                    PostActivity.this.finish();
                }
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    @Override
    public void onClick(View view) {
        if(view == editPic) {
            System.out.println("here");
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.showPic);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
}
