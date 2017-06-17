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

#include "CameraCalibration.hpp"


CameraCalibration::CameraCalibration() {}

CameraCalibration::CameraCalibration(float fx, float fy, float cx, float cy) {
  for(int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
      m_intrinsic.mat[i][j] = 0;
    }
  }
  
  m_intrinsic.mat[0][0] = fx;
  m_intrinsic.mat[1][1] = fy;
  m_intrinsic.mat[0][2] = cx;
  m_intrinsic.mat[1][2] = cy;
  
  for(int i = 0; i < 4; i++) {
    m_distorsion.data[i] = 0;
  }
}

CameraCalibration::CameraCalibration(float fx, float fy, float cx, float cy, float distorsionCoeff[4]) {
  for(int i = 0; i < 3; i++) {
    for(int j = 0; j < 3; j++) {
      m_intrinsic.mat[i][j] = 0;
    }
  }
  
  m_intrinsic.mat[0][0] = fx;
  m_intrinsic.mat[1][1] = fy;
  m_intrinsic.mat[0][2] = cx;
  m_intrinsic.mat[1][2] = cy;
  
  for(int i = 0; i < 4; i++) {
    m_distorsion.data[i] = distorsionCoeff[i];
  }
}

void CameraCalibration::getMatrix34(float cparam[3][4]) const {
  for(int j = 0; j < 3; j++) {
    for(int i = 0; i < 3; i++) {
      cparam[i][j] = m_intrinsic.mat[i][j];
    }
  }
  
  for(int i = 0; i < 4; i++) {
    cparam[3][i] = m_distorsion.data[i];
  }
}

const Matrix33& CameraCalibration::getIntrinsic() const { return m_intrinsic; }

const Vector4&  CameraCalibration::getDistorsion() const { return m_distorsion; }
