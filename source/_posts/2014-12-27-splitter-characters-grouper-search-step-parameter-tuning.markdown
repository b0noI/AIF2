---
layout: post
title: "splitter_<wbr>characters_<wbr>grouper_<wbr>search_<wbr>step parameter tuning"
date: 2014-12-27 16:08:46 +0000
comments: true
categories: 
---
In last [article](http://aif.io/blog/2014/12/27/first-ipython-usage-for-quality-analyze/) we discussed our plan about searching optimal values for splitter_characters_grouper_search_step. This time we will show how we were able to find optimal value and prove that this value is optimal.

## Some theory about splitter_characters_grouper_search_step value

This value is used by Sentence splitting module. At some point our algorithm doing gradient search of the sentence splitters (we are not going to dive to the algorithm right now). Point is that this value is compromise between accuracy and speed. We can have as accurate result as small value we can set, on the other side of coin, this value have dramatic impact on the algorithm speed. As low we set this value as much slow we will make our algorithm.

When choosing value for this parameter, you always need to ask:

* is there are highest level of accuracy that you can get with this algorithm? - if this limit exists you can't get more accurate result even by making value splitter_characters_grouper_search_step lower;
* if accuracy limit exists, what maximum value of splitter_characters_grouper_search_step we can set to achieve this level of accuracy? Knowing this information will give us compromise between best accuracy and algorithm speed;

In [prev](http://aif.io/blog/2014/12/27/first-ipython-usage-for-quality-analyze/) article we already collected enough experiments results for visualizing and building function:

![img](https://s3.amazonaws.com/aif2/screenshots/Screen+Shot+2014-12-27+at+12.42.51.png)

now we can proceed with function building

## Building function for splitter_characters_grouper_search_step vs quality

We decided to use least squares method for building polynomial. By quick first look on plot you may think that we should use polynomial with max power 2 (3 elements polynomial) like this one:

![img](https://s3.amazonaws.com/aif2/screenshots/Screen+Shot+2014-12-27+at+17.54.28.png)

Here is the plot of the final function:

![img](https://s3.amazonaws.com/aif2/screenshots/Screen+Shot+2014-12-27+at+16.01.47.png)

As you can see from this plot count of errors start decreasing after some point. This could not be correct. But this chart could be pretty close to the target one. Also here is target limit for this function:

![img](https://s3.amazonaws.com/aif2/screenshots/Screen+Shot+2014-12-27+at+18.00.21.png)

so the top quality that we can get is 54 errors. That is not true, because we have our experiment that shows 53 errors. Let's increase size of the polynomial to 5 (size 4 is similar to 3). 

Resulted chart:

![img](https://s3.amazonaws.com/aif2/screenshots/Screen+Shot+2014-12-27+at+16.02.08.png)

and new limit:

![img](https://s3.amazonaws.com/aif2/screenshots/Screen+Shot+2014-12-27+at+18.04.46.png)

this result is much more accurate and consistent with real life. 

## Conclusion

As you can see, 53 errors is the best value of quality that can be achieved by setting different values to the variable. This means that we need to find biggest value of this variable that still gives us 53 error. And the value is: 

{"value": 0.0031, "errors": 53},

this is 6x times bigger than previous value (0.0005), so we improve algorithm speed in 6x time without losing accuracy!
