//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: com/kloudtek/util/io/ByteArrayDataOutputStream.java
//
//  Created by yannick on 03/02/14.
//

#include "ByteArrayDataOutputStream.h"
#include "IOSByteArray.h"
#include "java/io/ByteArrayOutputStream.h"

@implementation KTUtilByteArrayDataOutputStream

- (id)init {
  return [super initWithJavaIoOutputStream:[[JavaIoByteArrayOutputStream alloc] init]];
}

- (id)initWithInt:(int)size {
  return [super initWithJavaIoOutputStream:[[JavaIoByteArrayOutputStream alloc] initWithInt:size]];
}

- (IOSByteArray *)toByteArray {
  return [((JavaIoByteArrayOutputStream *) check_class_cast(out_, [JavaIoByteArrayOutputStream class])) toByteArray];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "toByteArray", NULL, "LIOSByteArray", 0x1, NULL },
  };
  static J2ObjcClassInfo _KTUtilByteArrayDataOutputStream = { "ByteArrayDataOutputStream", "com.kloudtek.util.io", NULL, 0x1, 1, methods, 0, NULL, 0, NULL};
  return &_KTUtilByteArrayDataOutputStream;
}

@end