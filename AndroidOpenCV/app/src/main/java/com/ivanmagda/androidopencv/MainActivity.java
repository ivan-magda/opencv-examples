package com.ivanmagda.androidopencv;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ivanmagda.androidopencv.utils.Constants;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    static {
        System.loadLibrary(Constants.NDK_MODULE_NAME);

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
        configure();
    }

    private void configure() {
        findViewById(R.id.btn_camera_output).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDemoActivityWithClass(CameraOutput.class);
            }
        });

        findViewById(R.id.btn_feature_extraction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDemoActivityWithClass(FeatureExtraction.class);
            }
        });

        TextView jniTextView = (TextView) findViewById(R.id.tv_jni_msg);
        jniTextView.setText(getMsgFromJni());
    }

    private void startDemoActivityWithClass(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }

    public native String getMsgFromJni();

}
