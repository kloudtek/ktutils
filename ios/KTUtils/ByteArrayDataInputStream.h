//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: com/kloudtek/util/io/ByteArrayDataInputStream.java
//
//  Created by yannick on 08/02/14.
//

#ifndef _KTUtilByteArrayDataInputStream_H_
#define _KTUtilByteArrayDataInputStream_H_

@class IOSByteArray;

#import "JreEmulation.h"
#include "DataInputStream.h"

@interface KTUtilByteArrayDataInputStream : KTUtilDataInputStream {
}

- (id)initWithByteArray:(IOSByteArray *)buf;
- (id)initWithByteArray:(IOSByteArray *)buf
                withInt:(int)offset
                withInt:(int)length;
@end

typedef KTUtilByteArrayDataInputStream ComKloudtekUtilIoByteArrayDataInputStream;

#endif // _KTUtilByteArrayDataInputStream_H_
