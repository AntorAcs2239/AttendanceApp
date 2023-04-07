package com.example.yessir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class openscreen extends AppCompatActivity {
    Animation topanimation,bottomanimation;
    ImageView imageView;
    TextView textView,fri;
    private static int t=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_openscreen);
        topanimation= AnimationUtils.loadAnimation(this,R.anim.topanim);
        bottomanimation= AnimationUtils.loadAnimation(this,R.anim.bottomanim);
        imageView=findViewById(R.id.imageView);
        textView=findViewById(R.id.textView);
        imageView.setAnimation(topanimation);
        textView.setAnimation(bottomanimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(openscreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },t);
    }
}