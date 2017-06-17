/**
 * Copyright (c) 2017 Ivan Magda
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

#ifndef iOS_AR_Marker_hpp
#define iOS_AR_Marker_hpp

#include <vector>
#include <iostream>
#include <opencv2/opencv.hpp>

#include "GeometryTypes.hpp"

/**
 * This class represents a marker
 */
class Marker {
public:
  Marker();
  
  friend bool operator<(const Marker &M1, const Marker&M2);
  friend std::ostream & operator<<(std::ostream &str, const Marker &M);

  static cv::Mat rotate(cv::Mat in);
  static int hammDistMarker(cv::Mat bits);
  static int mat2id(const cv::Mat &bits);
  static int getMarkerId(cv::Mat &in, int &nRotations);
  
public:
  
  // Id of  the marker
  int id;
  
  // Marker transformation with regards to the camera
  Transformation transformation;
  
  std::vector<cv::Point2f> points;

  // Helper function to draw the marker contour over the image
  void drawContour(cv::Mat& image, cv::Scalar color = CV_RGB(0,250,0)) const;
};

#endif
