package com.example.lakhs.movieapplication.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lakhs.movieapplication.db.MovieSQLiteOpenHelper;
import com.example.lakhs.movieapplication.R;
import com.squareup.picasso.Picasso;

/**
 * Created by lakhs on 3/22/2016.
 */
public class SingleMovieActivity extends AppCompatActivity {
    TextView title,year,desc,favourite;
    ImageView imageView;
    RatingBar rateBar;
    String mTitle,mYear,mRate,mDesc,mId,s;
    MovieSQLiteOpenHelper sql;
    int id;
    int fav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sql=new MovieSQLiteOpenHelper(this);
        title = (TextView) findViewById(R.id.tv_title);
        year = (TextView) findViewById(R.id.tv_year);
        favourite = (TextView) findViewById(R.id.b_delete);
        desc = (TextView) findViewById(R.id.tv_desc);
        rateBar= (RatingBar) findViewById(R.id.ratingBar);
        imageView = (ImageView) findViewById(R.id.iv_movie);

        mTitle  =  getIntent().getStringExtra("title");
        mYear  =  getIntent().getStringExtra("year");
        mRate  =  getIntent().getStringExtra("rate");
        mDesc  =  getIntent().getStringExtra("desc");
        mId  =  getIntent().getStringExtra("id");
        float f = (Float.parseFloat(getIntent().getStringExtra("rate")));
        rateBar.setRating((float) (f/2.0));

        desc.setText("Description: " +mDesc);
        title.setText("Title : " + mTitle);
        year.setText("Date : " + mYear);

        if(fav==0){
            favourite.setText("Add");
        }
        else{
            favourite.setText("remove");
        }

        s = getIntent().getStringExtra("rsc");
        id = Integer.parseInt(mId);
        Picasso.with(this)
                .load(s)
                .resize(175,250)
                .centerCrop()
                .into(imageView);

    }

    public void onClickBack(View v) {
        super.onBackPressed();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void deleteFavorite(View v){
        if (fav==0) {
            Integer res = sql.deleteFav(mId);
            if (res == 1)
                Toast.makeText(this, "Removed", Toast.LENGTH_LONG).show();
            fav=1;
            favourite.setText("Add");
            favourite.setBackgroundColor(getColor(R.color.add));
        }else {
            boolean b = sql.insertData(id, mTitle, mYear, mRate, mDesc, s);
            if (b == true)
                Toast.makeText(this, "Successfully Added", Toast.LENGTH_LONG).show();
            favourite.setText("remove");
            fav=0;
            favourite.setBackgroundColor(getColor(R.color.remove));

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

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
        if (id == R.id.action_fav) {
            Intent intent =new Intent(this,FavoriteActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_top10) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}


