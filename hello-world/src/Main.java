import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class Main {

    public static void main(String[] args) {
        // Load the OpenCV Native Library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

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

        System.out.println("Hello from OpenCV !!!");
        System.out.println("mat = " + mat.dump());
    }

}
