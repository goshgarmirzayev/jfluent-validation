#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
BUILD_DIR="$ROOT_DIR/build"
MAIN_OUT="$BUILD_DIR/main-classes"
TEST_OUT="$BUILD_DIR/test-classes"
SUPPORT_OUT="$BUILD_DIR/test-support"

rm -rf "$BUILD_DIR"
mkdir -p "$MAIN_OUT" "$TEST_OUT" "$SUPPORT_OUT"

if [ -d "$ROOT_DIR/testing-support" ]; then
  find "$ROOT_DIR/testing-support" -name "*.java" > "$BUILD_DIR/support-sources.txt"
  if [ -s "$BUILD_DIR/support-sources.txt" ]; then
    javac -d "$SUPPORT_OUT" @"$BUILD_DIR/support-sources.txt"
  fi
fi

find "$ROOT_DIR/src/main/java" -name "*.java" > "$BUILD_DIR/main-sources.txt"
javac -d "$MAIN_OUT" @"$BUILD_DIR/main-sources.txt"

find "$ROOT_DIR/src/test/java" -name "*.java" > "$BUILD_DIR/test-sources.txt"
javac -cp "$MAIN_OUT:$SUPPORT_OUT" -d "$TEST_OUT" @"$BUILD_DIR/test-sources.txt"

java -cp "$MAIN_OUT:$TEST_OUT:$SUPPORT_OUT" com.fluentrules.TestRunner
