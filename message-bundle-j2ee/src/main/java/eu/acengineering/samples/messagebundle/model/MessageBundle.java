package eu.acengineering.samples.messagebundle.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "message_bundle")
public class MessageBundle implements Serializable {

    public static final String KEY_FIELD = "key";
    public static final String LANGUAGE_FIELD = "language";
    public static final String VALUE_FIELD = "label";

    @EmbeddedId
    private MessageBundleKey id;

    @Column(name = "label")
    private String label;

    public MessageBundleKey getId() {
        return id;
    }

    public void setId(final MessageBundleKey someId) {
        id = someId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String someLabel) {
        label = someLabel;
    }


}
