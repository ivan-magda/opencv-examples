//
//  ARKeyboard.m
//  iOS-AR
//
//  Created by Ivan Magda on 20/06/2017.
//

#import "ARKeyboard.h"

@interface ARKeyboard ()

@end

@implementation ARKeyboard

- (instancetype)initWithFrame:(CGRect)frame {
  self = [super initWithFrame:frame];
  if (self) {
    UIView *view = [[NSBundle mainBundle] loadNibNamed:@"Keyboard" owner:self options:nil].firstObject;
    CGRect rect = CGRectMake(0, 0, frame.size.width, frame.size.height);
    view.frame = rect;
    
    view.layer.shadowColor = [UIColor blackColor].CGColor;
    view.layer.shadowOffset = CGSizeMake(5, 5);
    view.layer.shadowOpacity = 1;
    view.layer.shadowRadius = 8.0;
    
    [self setup];
    [self addSubview:view];
  }
  return self;
}

- (void)setup {
  [self setSHadows:@[_oneLabel, _twoLabel, _threeLabel, _fourLabel, _fiveLabel, _sixLabel,
                     _sevenLabel, _eightLabel, _nineLabel]
   ];
  
  [self applyZAngleRotateByAngle:20 toView:_zeroLabel];
  [self applyZAngleRotateByAngle:-30 toView:_oneLabel];
  [self applyZAngleRotateByAngle:-30 toView:_twoLabel];
  [self applyZAngleRotateByAngle:-30 toView:_threeLabel];
  [self applyZAngleRotateByAngle:20 toView:_fourLabel];
  [self applyZAngleRotateByAngle:20 toView:_fiveLabel];
  [self applyZAngleRotateByAngle:20 toView:_sixLabel];
  [self applyZAngleRotateByAngle:-10 toView:_sevenLabel];
  [self applyZAngleRotateByAngle:-10 toView:_eightLabel];
  [self applyZAngleRotateByAngle:-10 toView:_nineLabel];
}

- (void)applyZAngleRotateByAngle:(CGFloat)angle toView:(UIView *)view {
  CATransform3D transform = CATransform3DIdentity;
  transform.m34 = 1.0 / 500.0;
  transform = CATransform3DRotate(transform, angle * M_PI / 180, 1, 0, 0);
  
  view.layer.transform = transform;
}

- (void)setSHadows:(NSArray *)views {
  [views enumerateObjectsUsingBlock:^(UIView  * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
    [self setShadowToView:obj];
  }];
}

- (void)setShadowToView:(UIView *)view {
  view.layer.shadowColor = [UIColor blackColor].CGColor;
  view.layer.shadowOffset = CGSizeMake(5, 5);
  view.layer.shadowOpacity = 1;
  view.layer.shadowRadius = 3.0;
}

@end
