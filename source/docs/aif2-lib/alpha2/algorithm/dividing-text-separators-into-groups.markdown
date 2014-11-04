---
layout: page
title: Algorithm of dividing text separators into groups
footer: false
---

## Definitions

* [text separators](./main-definitions.html#text-separators)
* [text separators groups](./main-definitions.html#text-separators-groups)

## Preamble

The main goal of this algorithm is to divide all text separators into 2 groups. One of the groups is Group1 and the other is Group2. However, the peculiarity of this algorithm is that it's not known which of the groups is Group1 and which is Group2.

## Algorithm 

The algorithm contains 2 steps:

1. [Create graphs of text separators connections](#create-graph-of-text-separators-connections);
2. [Merge all graphs into 2 graphs](#merging-all-of-graphs-into-2);

### <a id="create-graph-of-text-separators-connections"></a>Create graphs of text separators connections

A graph of connections with other characters is built for each text separator. Weight of connections with other characters equals the times other characters are observed as the first character that starts the token after the token ending with the text splitter character.

This can be illustrated in the example below.

Input text: "Hello! My name is: Max. I live in Kiev. My age is 18 years. This summer i'll go to Paris! And what about you?" 
Input text separators: `!:.?`

Connections that would be extracted from this text:

`!` connected with:

* M - 1
* A - 1

`:` connected with:

* M - 1

`.` connected with:

* I - 1
* M - 1
* T - 1

These graphs should be converted from absolute value to probability:

`!` connected with:

* M - 1/2 = 0.5
* A - 1/2 = 0.5

`:` connected with:

* M - 1/1 = 1.0

`.` connected with:

* I - 1/3 = 0.(3)
* M - 1/3 = 0.(3)
* T - 1/3 = 0.(3)

After creating the graphs for all of the text separators they can be merged into 2 graphs.

### <a id="merging-all-of-graphs-into-2"></a>Merging all of the graphs into 2

To merge graphs algorithm of two graphs comparison should be defined. 

Let's illustrate algorithm of two graphs comparison in the example below. In input there are the following 2 graphs:

`!` connected with:

* M - 0.5
* A - 0.5

`.` connected with:

* I - 0.3
* M - 0.3
* T - 0.3

#### Step 1 creating merged graph 

`!` && `.` connected with:

* M
* A
* I
* T

#### Step 2 calculating connections values for merged graph

connection value should be equal to the module of the difference between the two values that are divided by the maximum value  

`!` && `.` connected with:

* M - |0.3 - 0.5| / 0.5 = 0.2 / 0.5 = 0.4
* A - |0.0 - 0.5| / 0.5 = 0.5 / 0.5 = 1.0
* I - |0.0 - 0.3| / 0.3 = 0.3 / 0.3 = 1.0 
* T - |0.0 - 0.3| / 0.3 = 0.3 / 0.3 = 1.0

#### Step 3 calculating graphs distances with merged graphs values

To calculate graphs distances average value of merged graph connections should be calculated:
(0.4 + 1.0 + 1.0 + 1.0) / 4.0 = 3.4 / 4.0 = 0.85

Graphs distances would be one minus previous value:

1.0 - 0.85 = **0.15**

***

With distances calculation algorithm all graphs can be divided into 2 clusters. To do this 2 graphs that have the shortest distance between each other should be obtained. These two graphs would create 2 base clusters. Each next graph should be compared with each of the two clusters and merged into the closest one.

Let's illustrate this with the previous example:

#### Calculating all distances

dist('.', '!') = 0.15
dist('.', ':') = 1.0 - 0.43 = 0.57
dist('!', ':') = 1.0 - 0.75 = 0.25

#### Creating two base clusters

two base clusters would be: . and ! 
because these two graphs have the shortest distance between each other: **0.15**

#### Choosing cluster for :

dist('.', ':') = 0.57
dist('!', ':') = 0.25

: should be merged with .

#### Result

As a result we have 2 clusters:

* cluster1: .:
* cluster2: !

## Algorithm limitation

As we can see from above, algorithm requires a big text in the output. Otherwise, the quality of the result would be low. Also this algorithm works much better with languages that are case-sensitive. Algorithm shows some quality degradation in case-insensitive texts.