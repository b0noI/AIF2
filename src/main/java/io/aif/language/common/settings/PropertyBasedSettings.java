package io.aif.language.common.settings;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

class PropertyBasedSettings implements ISettings {

    private static final String        VERSION_PROPERTIES_KEY
            = "version";

    private static final String        PROPERTIES_FILE_NAME
            = "main.properties";

    private static final String        MINIMUM_TOKENS_INPUT_COUNT_KEY
            = "minimum_tokens_input_count";

    private static final String        USE_IS_ALPHABETIC_METHOD_KEY
            = "use_is_alphabetic_method";

    private static final String         THRESHOLD_P_FOR_SECOND_FILTER_SEPARATOR_CHARACTER_KEY
            = "threshold_p_for_second_filter_separator_character";

    private static final String         MINIMAL_VALUABLE_TOKEN_SIZE_DURING_SENTENCE_SPLITTING_KEY
            = "minimal_valuable_token_size_during_sentence_splitting";

    private static final String         MINIMUM_CHARACTER_OBERVATIONS_COUNT_FOR_MAKE_CHARATCER_VALUABLE_DURING_SENTENCE_SPLITTING_KEY
            = "minimum_character_observations_count_for_make_character_valuable_during_sentence_splitting";

    private static final String         THRESHOLD_P_FOR_FIRST_FILTER_SEPARATOR_CHARACTER_KEY
            = "threshold_p_for_first_filter_separator_character";

    private static final String         SPLITTER_CHARACTERS_GROUPER_SEARCH_STEP_KEY
            = "splitter_characters_grouper_search_step";

    private static final String         SPLITTER_CHARACTERS_GROUPER_INIT_SEARCH_P_VALUE_KEY
            = "splitter_characters_grouper_init_search_P_value";

    private static final String         WORD_SET_DICT_COMPARATOR_THRESHOLD
            = "word_set_dict_comparator_threshold";
    
    private static final String         RECURSIVE_SUBSTRING_COMPARATOR_WEIGHT_KEY
            = "recursive_substring_comparator_weight";
    
    private static final String         SIMPLE_TOKEN_COMPARATOR_WEIGHT_KEY 
            = "simple_token_comparator_weight";

                   final Properties     properties
            = new Properties();

    public static PropertyBasedSettings createInstance() {
        
        final Optional<PropertyBasedSettings> userSettings = checkUserSettings();
        
        if (userSettings.isPresent()) return userSettings.get();
        
        try (final InputStream is
                     = ISettings.class
                        .getClassLoader()
                        .getResourceAsStream(PROPERTIES_FILE_NAME)) {
            return new PropertyBasedSettings(is);
        } catch (IOException e) {
            throw new ValueException(e.getMessage());
        }
    }
    
    private static Optional<PropertyBasedSettings> checkUserSettings() {
        try (final InputStream is
                     = ISettings.class
                .getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE_NAME)) {
            if (is != null) return Optional.of(new PropertyBasedSettings(is));
        } catch (IOException e) {
            throw new ValueException(e.getMessage());
        }     
        return Optional.empty();
    }

    private PropertyBasedSettings(final InputStream is) throws IOException {
        properties.load(is);
    }

    @Override
    public String getVersion() {
        return properties.getProperty(VERSION_PROPERTIES_KEY);
    }

    @Override
    public int recommendedMinimumTokensInputCount() {
        return Integer.valueOf(properties.getProperty(MINIMUM_TOKENS_INPUT_COUNT_KEY));
    }

    @Override
    public boolean useIsAlphabeticMethod() {
        return Boolean.valueOf(properties.getProperty(USE_IS_ALPHABETIC_METHOD_KEY));
    }

    @Override
    public double thresholdPForSeparatorCharacterInSecondFilter() {
        return Double.valueOf(properties.getProperty(THRESHOLD_P_FOR_SECOND_FILTER_SEPARATOR_CHARACTER_KEY));
    }

    @Override
    public int minimalValuableTokenSizeForSentenceSplit() {
        return Integer.valueOf(properties.getProperty(MINIMAL_VALUABLE_TOKEN_SIZE_DURING_SENTENCE_SPLITTING_KEY));
    }

    @Override
    public int minimumCharacterObervationsCountForMakingCharatcerValuableDuringSentenceSplitting() {
        return Integer.valueOf(properties.getProperty(MINIMUM_CHARACTER_OBERVATIONS_COUNT_FOR_MAKE_CHARATCER_VALUABLE_DURING_SENTENCE_SPLITTING_KEY));
    }

    @Override
    public double thresholdPFirstFilterForSeparatorCharacter() {
        return Double.valueOf(properties.getProperty(THRESHOLD_P_FOR_FIRST_FILTER_SEPARATOR_CHARACTER_KEY));
    }

    @Override
    public double splitterCharactersGrouperSearchStep() {
        return Double.valueOf(properties.getProperty(SPLITTER_CHARACTERS_GROUPER_SEARCH_STEP_KEY));
    }

    @Override
    public double splitterCharactersGrouperInitSearchPValue() {
        return Double.valueOf(properties.getProperty(SPLITTER_CHARACTERS_GROUPER_INIT_SEARCH_P_VALUE_KEY));
    }

    @Override
    public double wordSetDictComparatorThreshold() {
        return Double.valueOf(properties.getProperty(WORD_SET_DICT_COMPARATOR_THRESHOLD));
    }

    @Override
    public double recursiveSubstringComparatorWeight() {
        return Double.valueOf(properties.getProperty(RECURSIVE_SUBSTRING_COMPARATOR_WEIGHT_KEY));
    }

    @Override
    public double simpleTokenComparatorWeight() {
        return Double.valueOf(properties.getProperty(SIMPLE_TOKEN_COMPARATOR_WEIGHT_KEY));
    }

}
