package eu.acengineering.samples.messagebundle.ui;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UIMessageBundle implements Serializable {

    private static final long serialVersionUID = -9157672117015248863L;

    private String key;

    private final Map<String, String> labels = new HashMap<>();

    public String getKey() {
        return key;
    }

    public void setKey(final String someKey) {
        key = someKey;
    }

    public String getLabel(final String someLanguage) {
        return labels.get(someLanguage);
    }

    public void addLabel(final String someLanguage, final String someLabel) {
        labels.put(someLanguage, someLabel);
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UIMessageBundle that = (UIMessageBundle) o;

        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
