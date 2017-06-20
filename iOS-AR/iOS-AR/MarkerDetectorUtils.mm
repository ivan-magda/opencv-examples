//
//  MarkerDetectorUtils.m
//  iOS-AR
//
//  Created by Ivan Magda on 20/06/2017.
//

#import "MarkerDetectorUtils.h"

#include <vector>
#include <opencv2/opencv.hpp>

@implementation MarkerDetectorUtils

+ (CGRect)makeRectFromMarker:(const Marker &) marker {
  std::vector<cv::Point2f> points = marker.points;
  
  if (points.size() == 0) {
    return CGRectZero;
  }
  
  if (points.size() != 4) {
    NSLog(@"Invalid count of points. Must be 4, but recived %lu", points.size());
    return CGRectZero;
  }
  
  cv::Point2f topLeft = points[0];
  cv::Point2f topRight = points[1];
  cv::Point2f bottomRight = points[2];
  cv::Point2f bottomLeft = points[3];
  
  CGFloat x = floor(fmax(topLeft.x, bottomLeft.x) * 0.85);
  CGFloat y = floor(fmax(topLeft.y, topRight.y) * 0.6);
  CGFloat width = floor(fmax(topRight.x, bottomRight.x) - fmax(topLeft.x, bottomLeft.x)) * 0.89;
  CGFloat height = floor(fmax(bottomLeft.y, bottomRight.y) - fmax(topLeft.y, topRight.y)) * 0.75;
  
  return CGRectMake(x, y, width, height);
}

@end
