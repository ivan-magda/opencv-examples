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

#ifndef iOS_AR_GeometryTypes_hpp
#define iOS_AR_GeometryTypes_hpp

struct Matrix44 {
  union {
    float data[16];
    float mat[4][4];
  };
  
  Matrix44 getTransposed() const;
  Matrix44 getInvertedRT() const;
  static Matrix44 identity();
};

struct Matrix33 {
  union {
    float data[9];
    float mat[3][3];
  };
  
  static Matrix33 identity();
  Matrix33 getTransposed() const;
};

struct Vector4 {
  float data[4];
};

struct Vector3 {
  float data[3];
  
  static Vector3 zero();
  Vector3 operator-() const;
};

struct Transformation {
  Transformation();
  Transformation(const Matrix33& r, const Vector3& t);
  
  Matrix33& r();
  Vector3&  t();
  
  const Matrix33& r() const;
  const Vector3&  t() const;
  
  Matrix44 getMat44() const;
  
  Transformation getInverted() const;
private:
  Matrix33 m_rotation;
  Vector3  m_translation;
};

#endif
