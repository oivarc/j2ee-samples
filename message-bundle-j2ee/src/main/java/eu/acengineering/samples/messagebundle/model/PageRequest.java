package eu.acengineering.samples.messagebundle.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PageRequest implements Serializable {

    /**
     * The index of the first element to be retrieved.
     */
    private int start;

    /**
     * The size of the page to be retrieved.
     * Represents the number of elements returned by the query.
     */
    private int pageSize;

    /**
     * The sort order <code>ASC</code> or <code>DESC</code> to be used.
     */
    private SortOrder sortOrder;

    /**
     * The sorting field used together with the sortOrder.
     */
    private String sortField;

    /**
     * A map of filters to be applied to the query.
     * The content is defined by the service.
     */
    private Map<String, String> filters = new HashMap<>();

    /**
     * Default constructor.
     */
    public PageRequest() {
        super();
    }

    /**
     * Creates an instance of PageRequest given some parameters.
     * @param someStart The index of the first element to be retrieved.
     * @param somePageSize The size of the page to be retrieved.
     * @param someSortOrder The sort order <code>ASC</code> or <code>DESC</code> to be used.
     * @param someSortField The sorting field used together with the sortOrder.
     * @param someFilters A map of filters to be applied to the query.
     */
    public PageRequest(final int someStart, final int somePageSize, final SortOrder someSortOrder,
                       final String someSortField, final Map<String, String> someFilters) {
        this();
        start = someStart;
        pageSize = somePageSize;
        sortOrder = someSortOrder;
        sortField = someSortField;
        filters = someFilters;
    }

    public int getStart() {
        return start;
    }

    public void setStart(final int someStart) {
        start = someStart;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(final int somePageSize) {
        pageSize = somePageSize;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(final SortOrder someSortOrder) {
        sortOrder = someSortOrder;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(final String someSortField) {
        sortField = someSortField;
    }

    public Map<String, String> getFilters() {
        return filters;
    }

    /**
     * Adds a filter to the page request.
     * @param filter The filter name.
     * @param value The filter value.
     */
    public void addFilter(final String filter, final String value) {
        filters.put(filter,  value);
    }
}
