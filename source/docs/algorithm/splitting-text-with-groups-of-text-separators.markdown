---
layout: page
title: Algorithm of splitting text with groups of text separators
footer: false
---

## Definitions

* [sentence](./main-definitions.html#sentence);
* [text separators](./main-definitions.html#text-separators);
* [text separators groups](./main-definitions.html#text-separators-groups);

## Algorithm

Connections graphs for characters from Group1 and characters from Group2 should be provided for the given algorithm. The way for obtaining this graph is described here:

* [Algorithm of text separators extraction](./text-separators-extraction.html)
* [Algorithm of dividing text separators into groups](./dividing-text-separators-into-groups.html);
* [Algorithm of classification of text separators groups](./classification-of-text-separators-groups.html);

### Step 1 : Calculate average sentence size (in tokens)

Average sentence size can be calculated by counting distances between characters from Group1. For example:

Input tokens: 

    Hello! My name is: Max. I live in Kiev. My age is 18 years. This summer i'll go to Paris! And what about you?
 
Group1 characters: `.!?`
Distances would be:

* 1 - **Hello!** My name is: Max. I live in Kiev. My age is 18 years. This summer i'll go to Paris! And what about you?
* 4 - Hello! **My name is: Max.** I live in Kiev. My age is 18 years. This summer i'll go to Paris! And what about you?
* 4 - Hello! My name is: Max. **I live in Kiev.** My age is 18 years. This summer i'll go to Paris! And what about you?
* 5 - Hello! My name is: Max. I live in Kiev. **My age is 18 years.** This summer i'll go to Paris! And what about you?
* 6 - Hello! My name is: Max. I live in Kiev. My age is 18 years. **This summer i'll go to Paris!** And what about you?
* 4 - Hello! My name is: Max. I live in Kiev. My age is 18 years. This summer i'll go to Paris! **And what about you?**

average size in this case would be: (1 + 4 + 4 + 5 + 6 + 4) / 6 = 4

### Step 2 : Splitting sentences

Rules to be applied in the text for each splitter from Group 1:

1. If the size of previous or next Group 1 character is bigger by more than 20% than the average sentence size, this splitter character is used for splitting sentences; otherwise, go to the next check.
2. Calculating distance to connections graphs:
    1. Creating one-character connection graph consisting of the splitter character and the first character of the next token; 
    2. Calculating distances between one-character connection graph and Group1/2 connections graphs;
    3. If one-character graph is closer to Group 1 characters connections graph, this character is used for sentence splitting; 
    4. If one-character graph is closer to Group 2 characters connections graph, this character is NOT used for sentence splitting.

---

By applying the two steps above to each Group 1 character in the text, the text can be split into sentences. Applying the algorithm to the next example input:

    ... He was born in the U.S.S.R. during the second world war...

will allow to split this sentence correctly. 

Here are two examples from real books where Group 1 character usage is recognized as usage for non- sentence splitting purpose:

* ... by __U.S.__ federal laws and ...
* ... the __U.S.__ unless a copyright ...

## Research problems for this step

* Magic number - 20%?
* There has to be one formula that represents the probability that this particular Group 1 character is used for sentence splitting. If such a formula is created, the last question will be to find the correct threshold.