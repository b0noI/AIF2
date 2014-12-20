---
layout: page
title: Tests
footer: false
---
AIF uses TestNG

## Tests groups

**BASIC:** (conditions of running)

1. _"unit-tests"_ - method level tests without any additional logic checks. Every class method should be covered by at least one positive unit-test and one negative unit test. Must be run at every method change.
2. _«integration-tests»_ - test for checking communication between different parts of the system. Must be run at every change of the module(class) logic.
3. _«acceptance-tests»_ - tests which test general quality and compare with set acceptance level. Must be run before every version release.

**DETAILED:** (what they test)

1. _«functional-fast»_ - functional test with runtime not longer than predefined time duration
2. _«functional-slow»_ - functional test with runtime longer than predefined time duration
3. _«quality-fast»_ - quality test with runtime not longer than predefined time duration
4. _«quality-slow»_ - quality test with runtime longer than predefined time duration

**OPTIONAL:**

1. _«experimental»_ - special test without any predefined test target. Just one experiment on the system
2. _«help-test»_ - not a test in general, but a test method used to generate or save test data etc. Must be @Ignore by default - start it manually.

## Test sources

Test sources are divided into 2 parts:

- functional/integration/quality tests 
- unit tests

functional/integration/quality tests can be found here:  src/test/unit/java
unit tests can be found here: src/test/integration/java

## Test resources

### Text corpuses

All RAW texts can be found at:
src/test/resources/texts/<language>

### NLP models for 3d part engines

src/test/resources/models/<engine_name>