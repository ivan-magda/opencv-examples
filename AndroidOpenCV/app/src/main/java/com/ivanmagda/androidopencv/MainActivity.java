package com.ivanmagda.androidopencv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    static {
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Unable to load OpenCV");
        } else {
            Log.d(TAG, "OpenCV loaded");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_feature_extraction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FeatureExtraction.class);
                startActivity(intent);
            }
        });
    }

}
