package com.aif.language.sentance;

import java.util.List;

public interface ISentanceSplit {

    public List<List<String>> parseSentances(List<String> tokens);

}
