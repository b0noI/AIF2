---
layout: post
title: "First IPython Usage for analyzing quality "
date: 2014-12-27 13:14:33 +0000
comments: true
categories: 
---
Today is the first day of work with Alpha4. One of the main improvements that we’ve done is the way how we tune AIF settings. AIF has a lot of different variables in settings and we need to prove which value is best for quality. 

_note_: in this post we left measuring quality process out of our scope. Quality measurement is the question of other article.

Here is config example of current revision:

``` basic properties https://github.com/b0noI/AIF2/blob/master/src/main/resources/io/aif/common/settings/main.properties
version=1.1.0-alpha3
 minimum_tokens_input_count=600
 use_is_alphabetic_method=true
 threshold_p_for_second_filter_separator_character=.65
 minimal_valuable_token_size_during_sentence_splitting=3
 minimum_character_obervations_count_for_make_charatcer_valuable_during_sentence_splitting=10
 threshold_p_for_first_filter_separator_character=0.05
 splitter_characters_grouper_search_step=0.0005
 splitter_characters_grouper_init_search_P_value=0.65
 word_set_dict_comparator_treshold=0.75
```

each of these variables can impact quality. So we need to have a way of building function that correlates each of these variables with output quality. In the perfect world with pony we should have the one big function that correlates all variables from settings with the quality. Unfortunately building the one global function is hard task. So we decided to build function for each variable independly to see how this particular variable impacts the quality.

Today we will show the small step that was done with the "splitter_characters_grouper_search_step" variable. 

Our experiment is very simple:

* execute a lot of quality tests with different "splitter_characters_grouper_search_step" values;
* plot the results;
* build the function that based on the data from first step;
* find global extremum of the function and set configuration to that extremum.

So, basically, we need to find value of "splitter_characters_grouper_search_step" that gives the best quality.

## Executing a lot of quality tests

Done with the simple implementation (may be refactored in future):
``` java experimentWith_splitter_characters_grouper_search_step https://github.com/b0noI/AIF2/blob/c093104ecbc069016d1d187e6779efd3593d6a57/src/test/integration/java/io/aif/language/common/settings/PropertyBasedSettingsTest.java
private static void experimentWith_splitter_characters_grouper_search_step() throws Exception {

        Logger logger = Logger.getRootLogger();

        logger.setLevel(Level.OFF);

        final PropertyBasedSettings propertyBasedSettings = (PropertyBasedSettings) ISettings.SETTINGS;




        System.out.println("splitter_characters_grouper_search_step: [");

        for (Double splitter_characters_grouper_search_step = 0.00005; splitter_characters_grouper_search_step < 0.3; splitter_characters_grouper_search_step += 0.0005) {

            propertyBasedSettings.properties.setProperty("splitter_characters_grouper_search_step", String.valueOf(splitter_characters_grouper_search_step));

            final Map<String, List<String>> testResult = SimpleSentenceSplitterCharactersExtractorQualityTest.executeTest();

            System.out.println(String.format("{value: %f, errors: %d},", splitter_characters_grouper_search_step, testResult.keySet().stream().mapToInt(key -> testResult.get(key).size()).sum()));

        }

        System.out.println("]");

        

    }

```

This code will generate Python array with data. Using this we can use Python for visualizing data to see the shape of the function.

_note_: this variable was chosen as the first to be analyzed because we expecting it to has simple function without local extremums at all with 1 global extremum that we are searching for.

## Visualizing data with Python

After we have all the data we can plot it to see how quality chart is looks like. Here is Python code that is doing exactly this:

``` python splitter_characters_grouper_search_step.py https://github.com/b0noI/AIF2/blob/c093104ecbc069016d1d187e6779efd3593d6a57/src/test/integration/python/splitter_characters_grouper_search_step.py
# data collected by PropertyBasedSettingsTest.experimentWith_splitter_characters_grouper_search_step

data = [
{"value": 0.000050, "errors": 53},
{"value": 0.000550, "errors": 53},
# ... lines removed
{"value": 0.144550, "errors": 65},
{"value": 0.145050, "errors": 65},
]

x = []
y = []

for value in data:
    x.append(value["value"])
    y.append(value["errors"])
    
from pandas import *

d = {"x": x, "y": y}
df = DataFrame(d)
    
import matplotlib.pyplot as plt
from pandas.tools.rplot import *

plt.plot(x, y, 'ro')
plt.ylabel('errors')
plt.xlabel('splitter_characters_grouper_search_step')
plt.title('splitter_characters_grouper_search_step vs errors count')
```

result of execution  is:

![img](https://s3.amazonaws.com/aif2/screenshots/Screen+Shot+2014-12-27+at+12.42.51.png)

## Next steps

Now we can try to build function that represents the plot. It will give us the answers, can we improve quality by reducing value even more or we already reach the limit of this function. For this we will have to find lim(f(x)) of our function.

We will create a new blog entry when we have new data to show!