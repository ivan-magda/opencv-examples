//
//  MarkerDetectorUtils.h
//  iOS-AR
//
//  Created by Ivan Magda on 20/06/2017.
//

#import <Foundation/Foundation.h>
#include "Marker.hpp"

@interface MarkerDetectorUtils : NSObject

+ (CGRect)makeRectFromMarker:(const Marker &) marker;
  
@end
