package io.aif.language.common.settings;

import io.aif.language.sentence.SimpleSentenceSplitterCharactersExtractorQualityTest;
import io.aif.language.word.dict.DictBuilderIntegTest;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class PropertyBasedSettingsTest {

    public static void main(String[] args) throws Exception {
        experimentWithDictBuilding();
    }
    
    private static void experimentWith_splitter_characters_grouper_search_step() throws Exception {
        experimentWithSentenceSplitting("splitter_characters_grouper_search_step", 0.00005, 0.16, 0.00005, false);
        
    }

    private static void experimentWith_splitter_characters_grouper_init_search_P_value() throws Exception {
        experimentWithSentenceSplitting("splitter_characters_grouper_init_search_P_value", 0., 1., 0.005, false);

    }

    private static void experimentWith_threshold_p_for_first_filter_separator_character() throws Exception {
        experimentWithSentenceSplitting("threshold_p_for_first_filter_separator_character", 0.3, 0.7, 0.0005, false);

    }

    private static void experimentWith_minimum_character_obervations_count_for_make_charatcer_valuable_during_sentence_splitting() throws Exception {
        experimentWithSentenceSplitting("minimum_character_observations_count_for_make_character_valuable_during_sentence_splitting", 96, 1000, 1, true);

    }

    private static void experimentWith_threshold_p_for_second_filter_separator_character() throws Exception {
        experimentWithSentenceSplitting("threshold_p_for_second_filter_separator_character", 0., 0.9, 0.005, false);

    }

    private static void experimentWithSentenceSplitting(final String keyName, final double startValue, final double endValue, final double delta, final boolean toInt) throws Exception {
        Logger logger = Logger.getRootLogger();
        logger.setLevel(Level.OFF);
        final PropertyBasedSettings propertyBasedSettings = (PropertyBasedSettings) ISettings.SETTINGS;

        System.out.println(keyName + ": [");
        for (Double splitter_characters_grouper_search_step = startValue; splitter_characters_grouper_search_step < endValue; splitter_characters_grouper_search_step += delta) {
            final String value = ((Object)(toInt ? splitter_characters_grouper_search_step.intValue() : splitter_characters_grouper_search_step)).toString();
            if (toInt && value.contains(".")) {
                propertyBasedSettings.properties.setProperty(keyName, value.split("\\.")[0]);
            } else {
                propertyBasedSettings.properties.setProperty(keyName, value);
            }
            
            final Map<String, List<String>> testResult = SimpleSentenceSplitterCharactersExtractorQualityTest.executeTest();
            System.out.println(String.format("{\"value\": %f, \"errors\": %d},", splitter_characters_grouper_search_step, testResult.keySet().stream().mapToInt(key -> testResult.get(key).size()).sum()));
        }
        System.out.println("]");

    }

    private static void experimentWithDictBuilding() throws Exception {
        Logger logger = Logger.getRootLogger();
        logger.setLevel(Level.OFF);
        final PropertyBasedSettings propertyBasedSettings = (PropertyBasedSettings) ISettings.SETTINGS;

        final String keyName = "word_set_dict_comparator_threshold";
        System.out.println(keyName + ": [");
        for (Double word_set_dict_comparator_threshold = 0.; word_set_dict_comparator_threshold < 1.; word_set_dict_comparator_threshold += .0005) {

            propertyBasedSettings.properties.setProperty(keyName, String.valueOf(word_set_dict_comparator_threshold));

            System.out.println(String.format("{\"value\": %f, \"errors\": %d},", word_set_dict_comparator_threshold, DictBuilderIntegTest.runExperiment().totalErrorsCount()));
        }
        System.out.println("]");

    }

    private static void experimentWithDictBuilding3D() throws Exception {
        Logger logger = Logger.getRootLogger();
        logger.setLevel(Level.OFF);
        final PropertyBasedSettings propertyBasedSettings = (PropertyBasedSettings) ISettings.SETTINGS;

        System.out.println("data: [");
        for (Double recursive_substring_comparator_weight = 0.; recursive_substring_comparator_weight < 1.; recursive_substring_comparator_weight += 0.005) {
            for (Double simple_token_comparator_weight = 0.; simple_token_comparator_weight < 1.; simple_token_comparator_weight += 0.005) {
                propertyBasedSettings.properties.setProperty("recursive_substring_comparator_weight", String.valueOf(recursive_substring_comparator_weight));
                propertyBasedSettings.properties.setProperty("simple_token_comparator_weight", String.valueOf(simple_token_comparator_weight));
                final DictBuilderIntegTest.ExperimentResult experimentResult = DictBuilderIntegTest.runExperiment();
                System.out.println(String.format("{\"quality\": %d, \"rec\": %f, \"simple\": %f},", 
                                                experimentResult.totalErrorsCount(),
                                                recursive_substring_comparator_weight,
                                                simple_token_comparator_weight));
            }
        }
        System.out.println("]");

    }
    
    // threshold_p_for_second_filter_separator_character 0.65

}