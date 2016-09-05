package io.aif.language.sentence;

import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import io.aif.language.common.ISplitter;

class OpenNLPSentenceSplitter implements ISplitter<String, String> {
  private final SentenceDetector detector;

  public OpenNLPSentenceSplitter(InputStream modelIn) throws IOException {

    final SentenceModel model = new SentenceModel(modelIn);

    detector = new SentenceDetectorME(model);
  }

  @Override
  public List<String> split(String target) {
    return Arrays.asList(detector.sentDetect(target));
  }
}
