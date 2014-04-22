#!/bin/bash

rm -f KTUtils/*.m KTUtils/*.h
mkdir -p _java
rsync -a java/ _java
rsync -a ../src/main/java/ _java
KU=com/kloudtek/util

j2objc -use-arc --no-package-directories --prefix com.kloudtek.util=KTUtil --prefix com.kloudtek.util.io=KTUtil \
    -sourcepath _java -d KTUtils java/net/URLDecoder.java ${KU}/StringUtils.java ${KU}/BaseNCodec.java ${KU}/Base64.java ${KU}/Base32.java \
    ${KU}/UnexpectedException.java ${KU}/io/IOUtils.java ${KU}/io/DataInputStream.java ${KU}/io/DataOutputStream.java  \
    ${KU}/io/ByteArrayDataInputStream.java ${KU}/io/ByteArrayDataOutputStream.java ${KU}/io/IOUtils.java  ${KU}/crypto/CryptoEngine.java

find KTUtils -type f -name '*' -exec sed -i '' s/java\\/net\\/URLDecoder/URLDecoder/g {} +
find KTUtils -type f -name '*' -exec sed -i '' s/java\\/security\\/DataInputStream/DataInputStream/g {} +
find KTUtils -type f -name '*' -exec sed -i '' s/java\\/security\\/GeneralSecurityException/GeneralSecurityException/g {} +
find KTUtils -type f -name '*' -exec sed -i '' s/java\\/security\\/InvalidKeyException/InvalidKeyException/g {} +
find KTUtils -type f -name '*' -exec sed -i '' s/java\\/security\\/Key/Key/g {} +
find KTUtils -type f -name '*' -exec sed -i '' s/java\\/security\\/KeyException/KeyException/g {} +
find KTUtils -type f -name '*' -exec sed -i '' s/java\\/security\\/KeyPair/KeyPair/g {} +
find KTUtils -type f -name '*' -exec sed -i '' s/java\\/security\\/PrivateKey/PrivateKey/g {} +
find KTUtils -type f -name '*' -exec sed -i '' s/java\\/security\\/PublicKey/PublicKey/g {} +
find KTUtils -type f -name '*' -exec sed -i '' s/java\\/security\\/SignatureException/SignatureException/g {} +
find KTUtils -type f -name '*' -exec sed -i '' s/java\\/security\\/spec\\/InvalidKeySpecException/InvalidKeySpecException/g {} +
find KTUtils -type f -name '*' -exec sed -i '' s/java\\/security\\/interfaces\\/RSAPublicKey/RSAPublicKey/g {} +
find KTUtils -type f -name '*' -exec sed -i '' s/java\\/security\\/interfaces\\/RSAKey/RSAKey/g {} +
find KTUtils -type f -name '*' -exec sed -i '' s/javax\\/crypto\\/BadPaddingException/BadPaddingException/g {} +
find KTUtils -type f -name '*' -exec sed -i '' s/javax\\/crypto\\/IllegalBlockSizeException/IllegalBlockSizeException/g {} +
find KTUtils -type f -name '*' -exec sed -i '' s/javax\\/crypto\\/SecretKey/SecretKey/g {} +

rm -rf _java
