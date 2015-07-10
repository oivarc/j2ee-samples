package eu.acengineering.samples.messagebundle.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MessageBundleKey implements Serializable {

    private static final long serialVersionUID = -5157045022420438166L;

    @Column(name = "bundle_key")
    private String key;

    @Column(name = "language")
    private String language;

    /**
     * Default constructor.
     */
    public MessageBundleKey() {
    }

    /**
     * Constructor given the bundle key and language.
     * @param someKey The bundle key.
     * @param someLanguage The language.
     */
    public MessageBundleKey(final String someKey, final String someLanguage) {
        key = someKey;
        language = someLanguage;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String someKey) {
        key = someKey;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String someLanguage) {
        language = someLanguage;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MessageBundleKey that = (MessageBundleKey) o;

        if (!key.equals(that.key)) {
            return false;
        }
        if (!language.equals(that.language)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + language.hashCode();
        return result;
    }
}
