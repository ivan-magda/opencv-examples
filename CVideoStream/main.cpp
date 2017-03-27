#include <iostream>
#include <opencv2/opencv.hpp>

using namespace std;
using namespace cv;

int main() {
    Mat mat = Mat::eye(3, 3, CV_8UC1);

    cout << "Hello from OpenCV !" << endl;
    cout << "Mat = " << mat << endl;

    return 0;
}