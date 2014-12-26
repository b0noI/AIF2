package io.aif.common;

import java.io.InputStream;

public interface ICorporaParser {

    public String toPlainText(InputStream inputXML);

    public int getSentenceCount();
}
