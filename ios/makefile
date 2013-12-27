.SUFFIXES: .java .m

BUILD_DIR = KTUtils
GEN_DIR = gen/java
SOURCE_DIR = ../src/main/java/

# Change to where distribution was unzipped.
J2OBJC_DISTRIBUTION = /usr/share/j2objc
J2OBJC = $(J2OBJC_DISTRIBUTION)/j2objc
J2OBJCC = $(J2OBJC_DISTRIBUTION)/j2objcc

OBJECTS = \
  $(BUILD_DIR)/java/net/URLDecoder.o \
  $(BUILD_DIR)/com/kloudtek/util/StringUtils.o \
  $(BUILD_DIR)/com/kloudtek/util/BaseNCodec.o \
  $(BUILD_DIR)/com/kloudtek/util/Base32.o \
  $(BUILD_DIR)/com/kloudtek/util/UnexpectedException.o \
  $(BUILD_DIR)/com/kloudtek/util/Base64.o
OBJC_SOURCES = $(OBJECTS:.o=.m)
RESULT = build/idvkey-lib

default: translate $(OBJECTS)
		mkdir -p build
		$(J2OBJCC) -o $(RESULT) $(OBJECTS)

translate: $(BUILD_DIR) $(OBJC_SOURCES)

clean:
		@rm -rf $(RESULT) $(BUILD_DIR) ${GEN_DIR}

$(BUILD_DIR)/%.m $(BUILD_DIR)/%.h: $(GEN_DIR)/%.java
		$(J2OBJC) -use-arc --prefix com.kloudtek.util=KTUtils -sourcepath $(GEN_DIR) -d $(BUILD_DIR) $?

$(BUILD_DIR)/%.o: $(BUILD_DIR)/%.m
		$(J2OBJCC) -I$(BUILD_DIR) -c $? -o $@

${GEN_DIR}/%.java:
		mkdir -p ${GEN_DIR}
		rsync -a java/ ${GEN_DIR}/
		rsync -a ${SOURCE_DIR}/ ${GEN_DIR}/

$(BUILD_DIR):
		@mkdir -p $(BUILD_DIR)