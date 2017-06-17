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
#import "ViewController.h"
#import "VideoSource.h"
#import "MarkerDetector.hpp"
#import "SimpleVisualizationController.h"

@interface ViewController() <VideoSourceDelegate>

@property (strong, nonatomic) VideoSource * videoSource;
@property (nonatomic) MarkerDetector * markerDetector;
@property (strong, nonatomic) SimpleVisualizationController * visualizationController;

@end

@implementation ViewController

#pragma mark - View lifecycle

- (void)viewDidLoad {
  [super viewDidLoad];
  
  _videoSource = [VideoSource new];
  _videoSource.delegate = self;
  
  _markerDetector = new MarkerDetector([self.videoSource getCalibration]);
  [self.videoSource startWithDevicePosition:AVCaptureDevicePositionBack];
}

- (void)viewDidUnload {
  [self setGlview:nil];
  [super viewDidUnload];
}

- (void)viewWillAppear:(BOOL)animated {
  [self.glview initContext];
  
  CGSize frameSize = [self.videoSource getFrameSize];
  CameraCalibration camCalib = [self.videoSource getCalibration];
  frameSize = CGSizeMake(640, 480);
  
  self.visualizationController = [[SimpleVisualizationController alloc]
                                  initWithGLView: _glview
                                  calibration: camCalib
                                  frameSize: frameSize];
  
  [super viewWillAppear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
  // Important notice:
  // Since the logical interface orientation differs from coordinate system of video frame
  // We force the interface orientation to landscape right for simplicity.
  // This orientation has one-to-one coordinates mapping from frame buffer to view.
  // So we don't have to worry about inconsistent AR.
  return interfaceOrientation == UIInterfaceOrientationLandscapeRight;
}

- (void)dealloc {
  delete self.markerDetector;
}

#pragma mark - VideoSourceDelegate

-(void)frameReady:(BGRAVideoFrame) frame {
  // Start upload new frame to video memory in main thread
  dispatch_sync(dispatch_get_main_queue(), ^{
    [self.visualizationController updateBackground:frame];
  });
  
  // And perform processing in current thread
  self.markerDetector->processFrame(frame);
  
  // When it's done we query rendering from main thread
  dispatch_async(dispatch_get_main_queue(), ^{
    [self.visualizationController setTransformationList:(self.markerDetector->getTransformations)()];
    [self.visualizationController drawFrame];
  });
}

@end
