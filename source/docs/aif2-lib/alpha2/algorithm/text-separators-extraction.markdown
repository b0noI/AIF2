---
layout: page
title: Algorithm of text separators extraction
footer: false
---

## Definitions

* [Text separators](./main-definitions.html#text-separators)

### Functions definitions 

To explain the algorithm some functions have to be defined. The implementation of the functions is out of this scope; the given article only defines the functions used.

**P1(ch: Character, tokens: List\<String\>): Double** - Function that returns probability that character _ch_ occurs in the end (or beginning) of the tokens. 

For example, for the tokens input "hello, my name is" P1 is going to look like this:

* P1(',', "hello, my name is") == 1.0
* P1('e', "hello, my name is") == 0.5
* P1('l', "hello, my name is") == 0.0
* P1('s', "hello, my name is") == 1.0
* P1('a', "hello, my name is") == 0.0
* P1('m', "hello, my name is") == 0.5
* P1('o', "hello, my name is") == 0.0
* P1('h', "hello, my name is") == 0.0

**P2(ch: Character, tokens: List\<String\>): Double** - Function that returns average P1 probability of all characters from the tokens that occur before character _ch_.

For example, for the tokens input "hello, my name is" P2 is going to look like this (P1 values can be seen in example for P1):

* P2(',', "hello, my name is") == (P1('o', "...") * 1.0) / 1.0 == 0.0 / 1.0 == 0.0
* P2('y', "hello, my name is") == (P1('m', "...") * 1.0) / 1.0 == 0.5 / 1.0 == 0.5
* P2('e', "hello, my name is") == (P1('m', "...") * 1.0 + P1('h', "...") * 1.0) / 2.0 == (0.5 + 0.0) / 2.0 == 0.25

## Algorithm

The algorithm contains 2 steps:

1. Initial search
2. Search result filter.

The first step finds all text separators plus some "noise" (non-separators/alphabetic characters). The second step filters out all non-separators found in the first step.

### Initial search

The probability of being a separator must be calculated for each unique character in tokens. This can be done according to the following formula:

    P(ch: Character, tokens: List<String>): Double = P1(ch, tokens) * (P2(ch, tokens) ^ 2)

The result should contain 30% of characters that have the highest probability (calculated by formula P above). 

### Search result filter

Search result may contain characters that are not separators. They must be filtered out. Here is a filter that can be applied for each character in search result:

    P1(ch, tokens) > 0.65

## Research problems for the given steps

* How to determine the number of characters that should be used as a result during initial search (why is 30% is chosen and how to choose this value?);
* What value should be used as probability threshold in search result filter (why 0.65?).
