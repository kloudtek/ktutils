//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: com/kloudtek/util/Base64.java
//
//  Created by yannick on 03/02/14.
//

#ifndef _KTUtilBase64_H_
#define _KTUtilBase64_H_

@class IOSByteArray;
@class JavaMathBigInteger;
@class KTUtilBaseNCodec_Context;

#import "JreEmulation.h"
#include "BaseNCodec.h"

#define KTUtilBase64_BITS_PER_ENCODED_BYTE 6
#define KTUtilBase64_BYTES_PER_ENCODED_BLOCK 4
#define KTUtilBase64_BYTES_PER_UNENCODED_BLOCK 3
#define KTUtilBase64_MASK_6BITS 63

@interface KTUtilBase64 : KTUtilBaseNCodec {
 @public
  IOSByteArray *encodeTable_;
  IOSByteArray *decodeTable_;
  IOSByteArray *lineSeparator_;
  int decodeSize_;
  int encodeSize_;
}

+ (IOSByteArray *)CHUNK_SEPARATOR;
+ (IOSByteArray *)STANDARD_ENCODE_TABLE;
+ (IOSByteArray *)URL_SAFE_ENCODE_TABLE;
+ (IOSByteArray *)DECODE_TABLE;
- (id)init;
- (id)initWithBoolean:(BOOL)urlSafe;
- (id)initWithInt:(int)lineLength;
- (id)initWithInt:(int)lineLength
    withByteArray:(IOSByteArray *)lineSeparator;
- (id)initWithInt:(int)lineLength
    withByteArray:(IOSByteArray *)lineSeparator
      withBoolean:(BOOL)urlSafe;
- (BOOL)isUrlSafe;
- (void)encodeWithByteArray:(IOSByteArray *)inArg
                    withInt:(int)inPos
                    withInt:(int)inAvail
withKTUtilBaseNCodec_Context:(KTUtilBaseNCodec_Context *)context;
- (void)decodeWithByteArray:(IOSByteArray *)inArg
                    withInt:(int)inPos
                    withInt:(int)inAvail
withKTUtilBaseNCodec_Context:(KTUtilBaseNCodec_Context *)context;
+ (BOOL)isArrayByteBase64WithByteArray:(IOSByteArray *)arrayOctet;
+ (BOOL)isBase64WithByte:(char)octet;
+ (BOOL)isBase64WithNSString:(NSString *)base64;
+ (BOOL)isBase64WithByteArray:(IOSByteArray *)arrayOctet;
+ (IOSByteArray *)encodeBase64WithByteArray:(IOSByteArray *)binaryData;
+ (NSString *)encodeBase64StringWithByteArray:(IOSByteArray *)binaryData;
+ (IOSByteArray *)encodeBase64URLSafeWithByteArray:(IOSByteArray *)binaryData;
+ (NSString *)encodeBase64URLSafeStringWithByteArray:(IOSByteArray *)binaryData;
+ (IOSByteArray *)encodeBase64ChunkedWithByteArray:(IOSByteArray *)binaryData;
+ (IOSByteArray *)encodeBase64WithByteArray:(IOSByteArray *)binaryData
                                withBoolean:(BOOL)isChunked;
+ (IOSByteArray *)encodeBase64WithByteArray:(IOSByteArray *)binaryData
                                withBoolean:(BOOL)isChunked
                                withBoolean:(BOOL)urlSafe;
+ (IOSByteArray *)encodeBase64WithByteArray:(IOSByteArray *)binaryData
                                withBoolean:(BOOL)isChunked
                                withBoolean:(BOOL)urlSafe
                                    withInt:(int)maxResultSize;
+ (IOSByteArray *)decodeBase64WithNSString:(NSString *)base64String;
+ (IOSByteArray *)decodeBase64WithByteArray:(IOSByteArray *)base64Data;
+ (JavaMathBigInteger *)decodeIntegerWithByteArray:(IOSByteArray *)pArray;
+ (IOSByteArray *)encodeIntegerWithJavaMathBigInteger:(JavaMathBigInteger *)bigInt;
+ (IOSByteArray *)toIntegerBytesWithJavaMathBigInteger:(JavaMathBigInteger *)bigInt;
- (BOOL)isInAlphabetWithByte:(char)octet;
- (void)copyAllFieldsTo:(KTUtilBase64 *)other;
@end

J2OBJC_FIELD_SETTER(KTUtilBase64, encodeTable_, IOSByteArray *)
J2OBJC_FIELD_SETTER(KTUtilBase64, decodeTable_, IOSByteArray *)
J2OBJC_FIELD_SETTER(KTUtilBase64, lineSeparator_, IOSByteArray *)

typedef KTUtilBase64 ComKloudtekUtilBase64;

#endif // _KTUtilBase64_H_