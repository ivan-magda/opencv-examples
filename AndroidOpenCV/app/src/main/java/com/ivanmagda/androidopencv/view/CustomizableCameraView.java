package com.ivanmagda.androidopencv.view;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;

import org.opencv.android.JavaCameraView;

import java.util.List;

import static android.hardware.Camera.Parameters.PREVIEW_FPS_MAX_INDEX;
import static android.hardware.Camera.Parameters.PREVIEW_FPS_MIN_INDEX;

public final class CustomizableCameraView extends JavaCameraView {

    private static final String TAG = "CustomizableCameraView";

    public CustomizableCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setupFPS() {
        Camera.Parameters params = mCamera.getParameters();
        setupCaptureParams(params);

        mCamera.setParameters(params);
    }

    private void setupCaptureParams(Camera.Parameters parameters) {
        List<int[]> frameRates = parameters.getSupportedPreviewFpsRange();
        int last = frameRates.size() - 1;
        int minFps = (frameRates.get(last))[PREVIEW_FPS_MIN_INDEX];
        int maxFps = (frameRates.get(last))[PREVIEW_FPS_MAX_INDEX];
        parameters.setPreviewFpsRange(minFps, maxFps);
        Log.d(TAG, "preview fps: " + minFps + ", " + maxFps);
    }

}
