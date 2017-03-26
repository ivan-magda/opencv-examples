package com.ivanmagda.androidopencv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String OPENCV_NAME = "opencv_java3";

    static {
        System.loadLibrary(OPENCV_NAME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testOpenCV();
    }

    private void testOpenCV() {
        /**
         * Define a new Mat.
         *
         * The class Mat represents an n-dimensional dense numerical single-channel or multi-channel array.
         * It can be used to store real or complex-valued vectors and matrices, grayscale or color images,
         * voxel volumes, vector fields, point clouds, tensors, histograms.
         *
         * For more details check out the OpenCV page http://docs.opencv.org/3.0.0/dc/d84/group__core__basic.html.
         */
        Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
        Log.d(TAG, "Hey from OpenCV !!!");
        Log.d(TAG, "mat = " + mat.dump());
    }
}
