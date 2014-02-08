//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: com/kloudtek/util/io/IOUtils.java
//
//  Created by yannick on 03/02/14.
//

#include "ByteArrayDataOutputStream.h"
#include "IOSByteArray.h"
#include "IOSCharArray.h"
#include "IOSClass.h"
#include "IOUtils.h"
#include "java/io/File.h"
#include "java/io/FileReader.h"
#include "java/io/IOException.h"
#include "java/io/InputStream.h"
#include "java/io/OutputStream.h"
#include "java/io/Reader.h"
#include "java/io/StringWriter.h"
#include "java/io/Writer.h"
#include "java/lang/IllegalArgumentException.h"

@implementation KTUtilIOUtils

+ (IOSByteArray *)toByteArrayWithJavaIoInputStream:(JavaIoInputStream *)inputStream {
  KTUtilByteArrayDataOutputStream *buffer = [[KTUtilByteArrayDataOutputStream alloc] init];
  [KTUtilIOUtils copy__WithJavaIoInputStream:inputStream withJavaIoOutputStream:buffer];
  [buffer close];
  return [buffer toByteArray];
}

+ (long long int)copy__WithJavaIoInputStream:(JavaIoInputStream *)inputStream
                      withJavaIoOutputStream:(JavaIoOutputStream *)outputStream {
  return [KTUtilIOUtils copy__WithJavaIoInputStream:inputStream withJavaIoOutputStream:outputStream withInt:KTUtilIOUtils_DEF_BUFF_SIZE];
}

+ (long long int)copy__WithJavaIoInputStream:(JavaIoInputStream *)inputStream
                      withJavaIoOutputStream:(JavaIoOutputStream *)outputStream
                                     withInt:(int)bufSize {
  IOSByteArray *buffer = [IOSByteArray arrayWithLength:bufSize];
  long long int count = 0;
  while (YES) {
    int read = [((JavaIoInputStream *) nil_chk(inputStream)) readWithByteArray:buffer];
    if (read > 0) {
      [((JavaIoOutputStream *) nil_chk(outputStream)) writeWithByteArray:buffer withInt:0 withInt:read];
      count += read;
    }
    else {
      return count;
    }
  }
}

+ (long long int)copy__WithJavaIoReader:(JavaIoReader *)reader
                       withJavaIoWriter:(JavaIoWriter *)writer {
  return [KTUtilIOUtils copy__WithJavaIoReader:reader withJavaIoWriter:writer withInt:KTUtilIOUtils_DEF_CHAR_BUFF_SIZE];
}

+ (long long int)copy__WithJavaIoReader:(JavaIoReader *)reader
                       withJavaIoWriter:(JavaIoWriter *)writer
                                withInt:(int)bufSize {
  IOSCharArray *buffer = [IOSCharArray arrayWithLength:bufSize];
  long long int count = 0;
  while (YES) {
    int read = [((JavaIoReader *) nil_chk(reader)) readWithCharArray:buffer];
    if (read > 0) {
      [((JavaIoWriter *) nil_chk(writer)) writeWithCharArray:buffer withInt:0 withInt:read];
      count += read;
    }
    else {
      return count;
    }
  }
}

+ (long long int)byteArrayToLongWithByteArray:(IOSByteArray *)data {
  if (data == nil || (int) [data count] != 8) {
    @throw [[JavaLangIllegalArgumentException alloc] init];
  }
  return (((long long) (((uint64_t) (long long int) IOSByteArray_Get(nil_chk(data), 0)) << 56)) + ((long long) (((uint64_t) (long long int) (IOSByteArray_Get(data, 1) & 255)) << 48)) + ((long long) (((uint64_t) (long long int) (IOSByteArray_Get(data, 2) & 255)) << 40)) + ((long long) (((uint64_t) (long long int) (IOSByteArray_Get(data, 3) & 255)) << 32)) + ((long long) (((uint64_t) (long long int) (IOSByteArray_Get(data, 4) & 255)) << 24)) + ((IOSByteArray_Get(data, 5) & 255) << 16) + ((IOSByteArray_Get(data, 6) & 255) << 8) + ((IOSByteArray_Get(data, 7) & 255) << 0));
}

+ (IOSByteArray *)longToByteArrayWithLong:(long long int)value {
  IOSByteArray *data = [IOSByteArray arrayWithLength:8];
  (*IOSByteArray_GetRef(data, 0)) = (char) ((long long) (((unsigned long long) value) >> 56));
  (*IOSByteArray_GetRef(data, 1)) = (char) ((long long) (((unsigned long long) value) >> 48));
  (*IOSByteArray_GetRef(data, 2)) = (char) ((long long) (((unsigned long long) value) >> 40));
  (*IOSByteArray_GetRef(data, 3)) = (char) ((long long) (((unsigned long long) value) >> 32));
  (*IOSByteArray_GetRef(data, 4)) = (char) ((long long) (((unsigned long long) value) >> 24));
  (*IOSByteArray_GetRef(data, 5)) = (char) ((long long) (((unsigned long long) value) >> 16));
  (*IOSByteArray_GetRef(data, 6)) = (char) ((long long) (((unsigned long long) value) >> 8));
  (*IOSByteArray_GetRef(data, 7)) = (char) ((long long) (((unsigned long long) value) >> 0));
  return data;
}

+ (NSString *)toStringWithJavaIoFile:(JavaIoFile *)file {
  JavaIoStringWriter *buffer = [[JavaIoStringWriter alloc] init];
  JavaIoFileReader *fileReader = [[JavaIoFileReader alloc] initWithJavaIoFile:file];
  [KTUtilIOUtils copy__WithJavaIoReader:fileReader withJavaIoWriter:buffer];
  return [buffer description];
}

- (id)init {
  return [super init];
}

+ (J2ObjcClassInfo *)__metadata {
  static J2ObjcMethodInfo methods[] = {
    { "toByteArrayWithJavaIoInputStream:", NULL, "LIOSByteArray", 0x9, "JavaIoIOException" },
    { "copy__WithJavaIoInputStream:withJavaIoOutputStream:", NULL, "J", 0x9, "JavaIoIOException" },
    { "copy__WithJavaIoInputStream:withJavaIoOutputStream:withInt:", NULL, "J", 0xa, "JavaIoIOException" },
    { "copy__WithJavaIoReader:withJavaIoWriter:", NULL, "J", 0x9, "JavaIoIOException" },
    { "copy__WithJavaIoReader:withJavaIoWriter:withInt:", NULL, "J", 0xa, "JavaIoIOException" },
    { "longToByteArrayWithLong:", NULL, "LIOSByteArray", 0x9, NULL },
    { "toStringWithJavaIoFile:", NULL, "LNSString", 0x9, "JavaIoIOException" },
  };
  static J2ObjcFieldInfo fields[] = {
    { "DEF_BUFF_SIZE_", NULL, 0x1a, "I" },
    { "DEF_CHAR_BUFF_SIZE_", NULL, 0x1a, "I" },
  };
  static J2ObjcClassInfo _KTUtilIOUtils = { "IOUtils", "com.kloudtek.util.io", NULL, 0x1, 7, methods, 2, fields, 0, NULL};
  return &_KTUtilIOUtils;
}

@end
