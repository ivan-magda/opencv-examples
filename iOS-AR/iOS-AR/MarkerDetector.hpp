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

#ifndef iOS_AR_MarkerDetector_hpp
#define iOS_AR_MarkerDetector_hpp

#include <vector>
#include <opencv2/opencv.hpp>

#include "BGRAVideoFrame.h"
#include "CameraCalibration.hpp"

class Marker;

/**
 * A top-level class that encapsulate marker detector algorithm.
 */
class MarkerDetector {
public:
  typedef std::vector<cv::Point> PointsVector;
  typedef std::vector<PointsVector> ContoursVector;
  
  
  /**
   * Initialize a new instance of marker detector object
   * @calibration[in] - Camera calibration (intrinsic and distortion components) necessary for pose estimation.
   */
  MarkerDetector(CameraCalibration calibration);
  
  //! Searches for markes and fills the list of transformation for found markers
  void processFrame(const BGRAVideoFrame& frame);
  
  const std::vector<Transformation>& getTransformations() const;
  
protected:
  
  //! Main marker detection routine
  bool findMarkers(const BGRAVideoFrame& frame, std::vector<Marker>& detectedMarkers);
  
  //! Converts image to grayscale
  void prepareImage(const cv::Mat& bgraMat, cv::Mat& grayscale) const;
  
  //! Performs binary threshold
  void performThreshold(const cv::Mat& grayscale, cv::Mat& thresholdImg) const;
  
  //! Detects appropriate contours
  void findContours(cv::Mat& thresholdImg, ContoursVector& contours, int minContourPointsAllowed) const;
  
  //! Finds marker candidates among all contours
  void findCandidates(const ContoursVector& contours, std::vector<Marker>& detectedMarkers);
  
  //! Tries to recognize markers by detecting marker code
  void recognizeMarkers(const cv::Mat& grayscale, std::vector<Marker>& detectedMarkers);
  
  //! Calculates marker poses in 3D
  void estimatePosition(std::vector<Marker>& detectedMarkers);
  
private:
  float m_minContourLengthAllowed;
  
  cv::Size markerSize;
  cv::Mat camMatrix;
  cv::Mat distCoeff;
  std::vector<Transformation> m_transformations;
  
  cv::Mat m_grayscaleImage;
  cv::Mat m_thresholdImg;
  cv::Mat canonicalMarkerImage;
  
  ContoursVector           m_contours;
  std::vector<cv::Point3f> m_markerCorners3d;
  std::vector<cv::Point2f> m_markerCorners2d;
};

#endif
