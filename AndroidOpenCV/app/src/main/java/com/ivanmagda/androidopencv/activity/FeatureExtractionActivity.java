package com.ivanmagda.androidopencv.activity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.ivanmagda.androidopencv.R;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class FeatureExtractionActivity extends AppCompatActivity
        implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "FeatureExtractionActivity";
    private static final String SOURCE_IMAGE_NAME = "dollar.jpeg";

    private int mWidth, mHeight;
    private CameraBridgeViewBase mOpenCvCameraView;

    Scalar RED = new Scalar(255, 0, 0);
    Scalar GREEN = new Scalar(0, 255, 0);

    // Use for detect features.
    private FeatureDetector mDetector;

    // Computes the descriptors.
    private DescriptorExtractor mDescriptor;

    // Match the descriptors.
    private DescriptorMatcher mMatcher;

    private Mat mSourceDescriptors, mTargetDescriptors;
    private Mat mSourceImage;
    private MatOfKeyPoint mSourceKeypoints, mTargetKeypoints;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    try {
                        initializeOpenCVDependencies();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                default: {
                    super.onManagerConnected(status);
                    break;
                }
            }
        }
    };

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

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_feature_extraction);

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.java_surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView();
        }
    }

    // Private

    private void initializeOpenCVDependencies() throws IOException {
        mOpenCvCameraView.enableView();

        mDetector = FeatureDetector.create(FeatureDetector.ORB);
        mDescriptor = DescriptorExtractor.create(DescriptorExtractor.ORB);
        mMatcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);

        mSourceImage = new Mat();

        AssetManager assetManager = getAssets();
        InputStream inputStream = assetManager.open(SOURCE_IMAGE_NAME);

        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        Utils.bitmapToMat(bitmap, mSourceImage);
        Imgproc.cvtColor(mSourceImage, mSourceImage, Imgproc.COLOR_RGB2GRAY);

        // converting the image to match with the type of the cameras image
        mSourceImage.convertTo(mSourceImage, 0);

        mSourceDescriptors = new Mat();
        mSourceKeypoints = new MatOfKeyPoint();

        mDetector.detect(mSourceImage, mSourceKeypoints);
        mDescriptor.compute(mSourceImage, mSourceKeypoints, mSourceDescriptors);
    }

    // CameraBridgeViewBase.CvCameraViewListener2

    @Override
    public void onCameraViewStarted(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    @Override
    public void onCameraViewStopped() {
    }

    /**
     * Receive the video as frames and do all the image processing inside this method
     * and return a Mat with that image.
     *
     * @param inputFrame video frame.
     * @return Mat with that image.
     */
    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        return recognize(inputFrame.rgba());
    }

    // Recognition

    private Mat recognize(Mat aInputFrame) {
        Imgproc.cvtColor(aInputFrame, aInputFrame, Imgproc.COLOR_RGB2GRAY);

        mTargetDescriptors = new Mat();
        mTargetKeypoints = new MatOfKeyPoint();
        mDetector.detect(aInputFrame, mTargetKeypoints);
        mDescriptor.compute(aInputFrame, mTargetKeypoints, mTargetDescriptors);

        // Matching
        MatOfDMatch matches = new MatOfDMatch();

        if (mSourceImage.type() == aInputFrame.type()) {
            try {
                mMatcher.match(mSourceDescriptors, mTargetDescriptors, matches);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return aInputFrame;
        }

        List<DMatch> matchesList = matches.toList();

        Double max_dist = 0.0;
        Double min_dist = 100.0;

        for (int i = 0; i < matchesList.size(); i++) {
            Double dist = (double) matchesList.get(i).distance;
            if (dist < min_dist) min_dist = dist;
            if (dist > max_dist) max_dist = dist;
        }

        LinkedList<DMatch> good_matches = new LinkedList<>();

        for (int i = 0; i < matchesList.size(); i++) {
            if (matchesList.get(i).distance <= (1.5 * min_dist)) {
                good_matches.addLast(matchesList.get(i));
            }
        }

        MatOfDMatch goodMatches = new MatOfDMatch();
        goodMatches.fromList(good_matches);

        Mat outputImg = new Mat();
        MatOfByte drawnMatches = new MatOfByte();

        if (aInputFrame.empty() || aInputFrame.cols() < 1 || aInputFrame.rows() < 1) {
            return aInputFrame;
        }

        Features2d.drawMatches(
                mSourceImage, mSourceKeypoints, aInputFrame, mTargetKeypoints, goodMatches, outputImg,
                GREEN, RED, drawnMatches, Features2d.NOT_DRAW_SINGLE_POINTS
        );

        Imgproc.resize(outputImg, outputImg, aInputFrame.size());

        return outputImg;
    }
}
