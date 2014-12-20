---
layout: page
title: Algorithm of classification of text separators groups
footer: false
---
## Definitions

* [sentence](./main-definitions.html#sentence);
* [text separators](./main-definitions.html#text-separators);
* [text separator groups](./main-definitions.html#text-separators-groups);

## Algorithm

The algorithm needs several groups of text separators and tokens as input parameters. So, to understand this step, [algorithm of dividing text separators into groups](./dividing-text-separators-into-groups.html) should be understood first. This step also uses some definitions from the previous step.

### Step 1

Connections graph like this

`.` connected with:

* I - 0.3
* M - 0.3
* T - 0.3

is built for each unique character that ends a token and is not from any separator groups. For example: 

Input text: "Hello! My name is: Max. I live in Kiev. My age is 18 years. This summer i'll go to Paris! And what about you?" 

The following graphs are built:

`y` connected with:

* n - 0.5
* a - 0.5

`e` connected with:

* i - 1.0

`I` connected with:

* l - 1.0

`n` connected with:

* K - 1.0

and so on.

### Step 2

All these graphs are merged into one graph (one group).

### Step 3

Each separator group graph is compared with the result from step 2. 

### Result

The closest graph to the graph from step 2 represents separator characters from Group 2, other graphs represent separators from Group1.