package eu.acengineering.samples.messagebundle.model;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {

    /**
     * The content of the page.
     */
    private List<T> content;

    /**
     * The total amount of entities.
     */
    private int count;

    /**
     * Default constructor.
     */
    public Page() {
        super();
    }

    /**
     * Create an instance of Page given the content and the total number of entities.
     * @param someContent The content.
     * @param someCount The total number of entities.
     */
    public Page(final List<T> someContent, final int someCount) {
        this();
        content = someContent;
        count = someCount;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(final List<T> someContent) {
        content = someContent;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int someCount) {
        count = someCount;
    }
}
