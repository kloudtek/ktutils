//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: _java/com/kloudtek/util/ArrayUtils.java
//
//  Created by yinkaf on 6/14/14.
//

#include "ArrayUtils.h"
#include "CryptoUtils.h"
#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "IOSPrimitiveArray.h"
#include "java/lang/System.h"
#include "java/lang/reflect/Array.h"
#include "java/nio/ByteBuffer.h"
#include "java/nio/CharBuffer.h"
#include "java/nio/charset/Charset.h"
#include "java/util/Arrays.h"

@implementation KTUtilArrayUtils

+ (IOSObjectArray *)concatWithNSObjectArray:(IOSObjectArray *)array1
                          withNSObjectArray:(IOSObjectArray *)array2 {
  IOSObjectArray *result = (IOSObjectArray *) check_class_cast([JavaLangReflectArray newInstanceWithIOSClass:[[((IOSObjectArray *) nil_chk(array1)) getClass] getComponentType] withInt:(int) [array1 count] + (int) [((IOSObjectArray *) nil_chk(array2)) count]], [IOSObjectArray class]);
  if ((int) [array1 count] > 0) {
    [JavaLangSystem arraycopyWithId:array1 withInt:0 withId:result withInt:0 withInt:(int) [array1 count]];
  }
  if ((int) [array2 count] > 0) {
    [JavaLangSystem arraycopyWithId:array2 withInt:0 withId:result withInt:(int) [array1 count] withInt:(int) [array2 count]];
  }
  return result;
}

+ (IOSBooleanArray *)concatWithBooleanArray:(IOSBooleanArray *)array1
                           withBooleanArray:(IOSBooleanArray *)array2 {
  IOSBooleanArray *result = [IOSBooleanArray arrayWithLength:(int) [((IOSBooleanArray *) nil_chk(array1)) count] + (int) [((IOSBooleanArray *) nil_chk(array2)) count]];
  if ((int) [array1 count] > 0) {
    [JavaLangSystem arraycopyWithId:array1 withInt:0 withId:result withInt:0 withInt:(int) [array1 count]];
  }
  if ((int) [array2 count] > 0) {
    [JavaLangSystem arraycopyWithId:array2 withInt:0 withId:result withInt:(int) [array1 count] withInt:(int) [array2 count]];
  }
  return result;
}

+ (IOSByteArray *)concatWithByteArray:(IOSByteArray *)array1
                        withByteArray:(IOSByteArray *)array2 {
  IOSByteArray *result = [IOSByteArray arrayWithLength:(int) [((IOSByteArray *) nil_chk(array1)) count] + (int) [((IOSByteArray *) nil_chk(array2)) count]];
  if ((int) [array1 count] > 0) {
    [JavaLangSystem arraycopyWithId:array1 withInt:0 withId:result withInt:0 withInt:(int) [array1 count]];
  }
  if ((int) [array2 count] > 0) {
    [JavaLangSystem arraycopyWithId:array2 withInt:0 withId:result withInt:(int) [array1 count] withInt:(int) [array2 count]];
  }
  return result;
}

+ (IOSShortArray *)concatWithShortArray:(IOSShortArray *)array1
                         withShortArray:(IOSShortArray *)array2 {
  IOSShortArray *result = [IOSShortArray arrayWithLength:(int) [((IOSShortArray *) nil_chk(array1)) count] + (int) [((IOSShortArray *) nil_chk(array2)) count]];
  if ((int) [array1 count] > 0) {
    [JavaLangSystem arraycopyWithId:array1 withInt:0 withId:result withInt:0 withInt:(int) [array1 count]];
  }
  if ((int) [array2 count] > 0) {
    [JavaLangSystem arraycopyWithId:array2 withInt:0 withId:result withInt:(int) [array1 count] withInt:(int) [array2 count]];
  }
  return result;
}

+ (IOSIntArray *)concatWithIntArray:(IOSIntArray *)array1
                       withIntArray:(IOSIntArray *)array2 {
  IOSIntArray *result = [IOSIntArray arrayWithLength:(int) [((IOSIntArray *) nil_chk(array1)) count] + (int) [((IOSIntArray *) nil_chk(array2)) count]];
  if ((int) [array1 count] > 0) {
    [JavaLangSystem arraycopyWithId:array1 withInt:0 withId:result withInt:0 withInt:(int) [array1 count]];
  }
  if ((int) [array2 count] > 0) {
    [JavaLangSystem arraycopyWithId:array2 withInt:0 withId:result withInt:(int) [array1 count] withInt:(int) [array2 count]];
  }
  return result;
}

+ (IOSLongArray *)concatWithLongArray:(IOSLongArray *)array1
                        withLongArray:(IOSLongArray *)array2 {
  IOSLongArray *result = [IOSLongArray arrayWithLength:(int) [((IOSLongArray *) nil_chk(array1)) count] + (int) [((IOSLongArray *) nil_chk(array2)) count]];
  if ((int) [array1 count] > 0) {
    [JavaLangSystem arraycopyWithId:array1 withInt:0 withId:result withInt:0 withInt:(int) [array1 count]];
  }
  if ((int) [array2 count] > 0) {
    [JavaLangSystem arraycopyWithId:array2 withInt:0 withId:result withInt:(int) [array1 count] withInt:(int) [array2 count]];
  }
  return result;
}

+ (IOSFloatArray *)concatWithFloatArray:(IOSFloatArray *)array1
                         withFloatArray:(IOSFloatArray *)array2 {
  IOSFloatArray *result = [IOSFloatArray arrayWithLength:(int) [((IOSFloatArray *) nil_chk(array1)) count] + (int) [((IOSFloatArray *) nil_chk(array2)) count]];
  if ((int) [array1 count] > 0) {
    [JavaLangSystem arraycopyWithId:array1 withInt:0 withId:result withInt:0 withInt:(int) [array1 count]];
  }
  if ((int) [array2 count] > 0) {
    [JavaLangSystem arraycopyWithId:array2 withInt:0 withId:result withInt:(int) [array1 count] withInt:(int) [array2 count]];
  }
  return result;
}

+ (IOSDoubleArray *)concatWithDoubleArray:(IOSDoubleArray *)array1
                          withDoubleArray:(IOSDoubleArray *)array2 {
  IOSDoubleArray *result = [IOSDoubleArray arrayWithLength:(int) [((IOSDoubleArray *) nil_chk(array1)) count] + (int) [((IOSDoubleArray *) nil_chk(array2)) count]];
  if ((int) [array1 count] > 0) {
    [JavaLangSystem arraycopyWithId:array1 withInt:0 withId:result withInt:0 withInt:(int) [array1 count]];
  }
  if ((int) [array1 count] > 0) {
    [JavaLangSystem arraycopyWithId:array2 withInt:0 withId:result withInt:(int) [array1 count] withInt:(int) [array2 count]];
  }
  return result;
}

+ (IOSCharArray *)concatWithCharArray:(IOSCharArray *)array1
                        withCharArray:(IOSCharArray *)array2 {
  IOSCharArray *result = [IOSCharArray arrayWithLength:(int) [((IOSCharArray *) nil_chk(array1)) count] + (int) [((IOSCharArray *) nil_chk(array2)) count]];
  if ((int) [array1 count] > 0) {
    [JavaLangSystem arraycopyWithId:array1 withInt:0 withId:result withInt:0 withInt:(int) [array1 count]];
  }
  if ((int) [array2 count] > 0) {
    [JavaLangSystem arraycopyWithId:array2 withInt:0 withId:result withInt:(int) [array1 count] withInt:(int) [array2 count]];
  }
  return result;
}

+ (IOSCharArray *)concatWithCharArray2:(IOSObjectArray *)arrays {
  int len = 0;
  {
    IOSObjectArray *a__ = arrays;
    IOSCharArray * const *b__ = ((IOSObjectArray *) nil_chk(a__))->buffer_;
    IOSCharArray * const *e__ = b__ + a__->size_;
    while (b__ < e__) {
      IOSCharArray *array = (*b__++);
      if (array != nil) {
        len += (int) [array count];
      }
    }
  }
  IOSCharArray *result = [IOSCharArray arrayWithLength:len];
  int pos = 0;
  {
    IOSObjectArray *a__ = arrays;
    IOSCharArray * const *b__ = ((IOSObjectArray *) nil_chk(a__))->buffer_;
    IOSCharArray * const *e__ = b__ + a__->size_;
    while (b__ < e__) {
      IOSCharArray *array = (*b__++);
      if (array != nil) {
        [JavaLangSystem arraycopyWithId:array withInt:0 withId:result withInt:pos withInt:(int) [array count]];
        pos += (int) [array count];
      }
    }
  }
  return result;
}

+ (IOSByteArray *)toBytesWithCharArray:(IOSCharArray *)chars {
  JavaNioCharBuffer *charBuffer = [JavaNioCharBuffer wrapWithCharArray:chars];
  JavaNioByteBuffer *byteBuffer = [((JavaNioCharsetCharset *) nil_chk([JavaNioCharsetCharset forNameWithNSString:@"UTF-8"])) encodeWithJavaNioCharBuffer:charBuffer];
  IOSByteArray *bytes = [JavaUtilArrays copyOfRangeWithByteArray:[((JavaNioByteBuffer *) nil_chk(byteBuffer)) array] withInt:[byteBuffer position] withInt:[byteBuffer limit]];
  [ComKloudtekUtilCryptoCryptoUtils zeroWithJavaNioByteBuffer:byteBuffer];
  return bytes;
}

+ (IOSCharArray *)toCharsWithByteArray:(IOSByteArray *)data {
  JavaNioByteBuffer *byteBuffer = [JavaNioByteBuffer wrapWithByteArray:data];
  JavaNioCharBuffer *charBuffer = [((JavaNioCharsetCharset *) nil_chk([JavaNioCharsetCharset forNameWithNSString:@"UTF-8"])) decodeWithJavaNioByteBuffer:byteBuffer];
  IOSCharArray *chars = [JavaUtilArrays copyOfRangeWithCharArray:[((JavaNioCharBuffer *) nil_chk(charBuffer)) array] withInt:[charBuffer position] withInt:[charBuffer limit]];
  [ComKloudtekUtilCryptoCryptoUtils zeroWithJavaNioCharBuffer:charBuffer];
  return chars;
}

+ (IOSByteArray *)xor__WithByteArray:(IOSByteArray *)b1
                       withByteArray:(IOSByteArray *)b2 {
  BOOL b1Smallest = (int) [((IOSByteArray *) nil_chk(b1)) count] < (int) [((IOSByteArray *) nil_chk(b2)) count];
  int smallest = b1Smallest ? (int) [b1 count] : (int) [b2 count];
  IOSByteArray *result = [IOSByteArray arrayWithLength:b1Smallest ? (int) [b2 count] : (int) [b1 count]];
  for (int i = 0; i < smallest; i++) {
    (*IOSByteArray_GetRef(result, i)) = (char) (IOSByteArray_Get(b1, i) ^ IOSByteArray_Get(b2, i));
  }
  return result;
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "concatWithNSObjectArray:withNSObjectArray:", "concat", "[Ljava.lang.Object;", 0x89, NULL },
    { "concatWithBooleanArray:withBooleanArray:", "concat", "[Z", 0x89, NULL },
    { "concatWithByteArray:withByteArray:", "concat", "[B", 0x89, NULL },
    { "concatWithShortArray:withShortArray:", "concat", "[S", 0x89, NULL },
    { "concatWithIntArray:withIntArray:", "concat", "[I", 0x89, NULL },
    { "concatWithLongArray:withLongArray:", "concat", "[J", 0x89, NULL },
    { "concatWithFloatArray:withFloatArray:", "concat", "[F", 0x89, NULL },
    { "concatWithDoubleArray:withDoubleArray:", "concat", "[D", 0x89, NULL },
    { "concatWithCharArray:withCharArray:", "concat", "[C", 0x89, NULL },
    { "concatWithCharArray2:", "concat", "[C", 0x89, NULL },
    { "toBytesWithCharArray:", "toBytes", "[B", 0x9, NULL },
    { "toCharsWithByteArray:", "toChars", "[C", 0x9, NULL },
    { "xor__WithByteArray:withByteArray:", "xor", "[B", 0x9, NULL },
    { "init", NULL, NULL, 0x1, NULL },
  };
  static J2ObjcClassInfo _KTUtilArrayUtils = { "ArrayUtils", "com.kloudtek.util", NULL, 0x1, 14, methods, 0, NULL, 0, NULL};
  return &_KTUtilArrayUtils;
}

@end
