package kr.co.woobi.imyeon.airvisualapinetwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class FirstSceneActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1000;
    ImageView mImageGood, mImageNormal, mImageBad, mImageVeryBad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_scene);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

     /*   ImageView mImageGood = findViewById(R.id.image_good);
        GlideDrawableImageViewTarget goodgif = new GlideDrawableImageViewTarget(mImageGood);
        Glide.with(this).load(R.drawable.gif1).into(goodgif);*/

        ImageView mImageNormal = findViewById(R.id.image_normal);
        GlideDrawableImageViewTarget normalgif = new GlideDrawableImageViewTarget(mImageNormal);
        Glide.with(this).load(R.drawable.main).into(normalgif);

  /*      ImageView mImageBad = findViewById(R.id.image_bad);
        GlideDrawableImageViewTarget badgif = new GlideDrawableImageViewTarget(mImageBad);
        Glide.with(this).load(R.drawable.gif4).into(badgif);

        ImageView mImageVeryBad = findViewById(R.id.image_verybad);
        GlideDrawableImageViewTarget verybadgif = new GlideDrawableImageViewTarget(mImageVeryBad);
        Glide.with(this).load(R.drawable.gif3).into(verybadgif);*/

       /* Handler hand = new Handler();

        hand.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent i = new Intent(FirstSceneActivity.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        }, 4000);

*/
    }

    public void gotoNextScene(View view) {
        Intent intent=new Intent(this, MainActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }

    }
