#!/bin/bash

rm -f KTUtils/*.m KTUtils/*.h
mkdir -p _java
rsync -a java/ _java
rsync -a ../src/main/java/ _java
KU=com/kloudtek/util

j2objc -use-arc --no-package-directories --prefix com.kloudtek.util=KTUtils -sourcepath _java -d KTUtils \
    java/net/URLDecoder.java ${KU}/StringUtils.java ${KU}/BaseNCodec.java ${KU}/Base64.java ${KU}/Base32.java \
    ${KU}/UnexpectedException.java

find KTUtils -type f -name '*' -exec sed -i '' s/java\\/net\\/URLDecoder/URLDecoder/g {} +

rm -rf _java