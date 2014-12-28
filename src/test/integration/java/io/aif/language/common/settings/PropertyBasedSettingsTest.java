package io.aif.language.common.settings;

import io.aif.language.sentence.SimpleSentenceSplitterCharactersExtractorQualityTest;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class PropertyBasedSettingsTest {

    public static void main(String[] args) throws Exception {
        experimentWith_threshold_p_for_first_filter_separator_character();
    }
    
    private static void experimentWith_splitter_characters_grouper_search_step() throws Exception {
        experiment("splitter_characters_grouper_search_step", 0.00005, 0.16, 0.00005);
        
    }

    private static void experimentWith_splitter_characters_grouper_init_search_P_value() throws Exception {
        experiment("splitter_characters_grouper_init_search_P_value", 0., 1., 0.005);

    }

    private static void experimentWith_threshold_p_for_first_filter_separator_character() throws Exception {
        experiment("threshold_p_for_first_filter_separator_character", 0.3, 0.7, 0.0005);

    }

    private static void experiment(final String keyName, final double startValue, final double endValue, final double delta) throws Exception {
        Logger logger = Logger.getRootLogger();
        logger.setLevel(Level.OFF);
        final PropertyBasedSettings propertyBasedSettings = (PropertyBasedSettings) ISettings.SETTINGS;

        System.out.println(keyName + ": [");
        for (Double splitter_characters_grouper_search_step = startValue; splitter_characters_grouper_search_step < endValue; splitter_characters_grouper_search_step += delta) {
            propertyBasedSettings.properties.setProperty(keyName, String.valueOf(splitter_characters_grouper_search_step));
            final Map<String, List<String>> testResult = SimpleSentenceSplitterCharactersExtractorQualityTest.executeTest();
            System.out.println(String.format("{\"value\": %f, \"errors\": %d},", splitter_characters_grouper_search_step, testResult.keySet().stream().mapToInt(key -> testResult.get(key).size()).sum()));
        }
        System.out.println("]");

    }
    
    // threshold_p_for_first_filter_separator_character 0.05

}