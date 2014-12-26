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
        experimentWith_splitter_characters_grouper_search_step();
    }
    
    private static void experimentWith_splitter_characters_grouper_search_step() throws Exception {
        Logger logger = Logger.getRootLogger();
        logger.setLevel(Level.OFF);
        final PropertyBasedSettings propertyBasedSettings = (PropertyBasedSettings) ISettings.SETTINGS;

        System.out.println("splitter_characters_grouper_search_step: [");
        for (Double splitter_characters_grouper_search_step = 0.00005; splitter_characters_grouper_search_step < 1.; splitter_characters_grouper_search_step += 0.0005) {
            propertyBasedSettings.properties.setProperty("splitter_characters_grouper_search_step", String.valueOf(splitter_characters_grouper_search_step));
            final Map<String, List<String>> testResult = SimpleSentenceSplitterCharactersExtractorQualityTest.executeTest();
            System.out.println(String.format("{value: %f, errors: %d},", splitter_characters_grouper_search_step, testResult.keySet().stream().mapToInt(key -> testResult.get(key).size()).sum()));
//            System.out.println(ISettings.SETTINGS.splitterCharactersGrouperSearchStep());
//            System.gc();
        }
        System.out.println("]");
        
    }

}