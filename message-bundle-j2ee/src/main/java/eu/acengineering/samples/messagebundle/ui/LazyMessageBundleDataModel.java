package eu.acengineering.samples.messagebundle.ui;

import eu.acengineering.samples.messagebundle.MessageBundleService;
import eu.acengineering.samples.messagebundle.model.MessageBundle;
import eu.acengineering.samples.messagebundle.model.Page;
import eu.acengineering.samples.messagebundle.model.PageRequest;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyMessageBundleDataModel extends LazyDataModel<UIMessageBundle> {

    private final PageRequest pageRequest;

    private final MessageBundleService messageBundleService;

    private final String languageFilter;

    public LazyMessageBundleDataModel(final MessageBundleService someMessageBundleService,
            final String someLanguageFilter) {
        pageRequest = new PageRequest();
        messageBundleService = someMessageBundleService;
        languageFilter = someLanguageFilter;
    }

    @Override
    public final List<UIMessageBundle> load(final int first, final int pageSize, final String sortField, final SortOrder sortOrder, final Map<String, Object> filters) {
        pageRequest.setStart(first);
        pageRequest.setPageSize(pageSize);
        pageRequest.setSortField(sortField);
        if (SortOrder.DESCENDING.equals(sortOrder)) {
            pageRequest.setSortOrder(eu.acengineering.samples.messagebundle.model.SortOrder.DESC);
        } else {
            pageRequest.setSortOrder(eu.acengineering.samples.messagebundle.model.SortOrder.ASC);
        }

        addFilters(pageRequest, filters);

        Page<UIMessageBundle> page = getPage(pageRequest);

        //rowCount
        this.setRowCount(page.getCount());

        return page.getContent();
    }

    @Override
    public UIMessageBundle getRowData(final String rowKey) {
        List<MessageBundle> messageBundles = messageBundleService.findByKey(rowKey);
        UIMessageBundle uiMessageBundle = new UIMessageBundle();
        messageBundles.stream().forEach((messageBundle) -> {
            uiMessageBundle.setKey(messageBundle.getId().getKey());
            uiMessageBundle.addLabel(messageBundle.getId().getLanguage(), messageBundle.getLabel());
        });
        return uiMessageBundle;

    }

    @Override
    public Object getRowKey(final UIMessageBundle someUIMessageBundle) {
        return someUIMessageBundle.getKey();
    }

    private void addFilters(final PageRequest somePageRequest, final Map<String, Object> filters) {
        if (StringUtils.isNotBlank((String) filters.get(MessageBundle.KEY_FIELD))) {
            somePageRequest.addFilter(MessageBundle.KEY_FIELD, (String) filters.get(MessageBundle.KEY_FIELD));
        }
        if (StringUtils.isNotBlank(languageFilter)) {
            somePageRequest.addFilter(MessageBundle.LANGUAGE_FIELD, languageFilter);
        }
        if (StringUtils.isNotBlank((String) filters.get("labels['en']"))) {
            somePageRequest.addFilter(MessageBundle.VALUE_FIELD, (String) filters.get("labels['en']"));
        }

    }

    private Page<UIMessageBundle> getPage(final PageRequest somePageRequest) {
        Page<MessageBundle> page = messageBundleService.findPaginated(somePageRequest);
        return new Page<>(convertToUIMessageBundle(page.getContent()), page.getCount());
    }

    private List<UIMessageBundle> convertToUIMessageBundle(final List<MessageBundle> someMessageBundles) {
        Map<String, UIMessageBundle> map = new LinkedHashMap<>();
        someMessageBundles.stream().forEach((messageBundle) -> {
            UIMessageBundle uiMessageBundle = map.get(messageBundle.getId().getKey());
            if (uiMessageBundle == null) {
                uiMessageBundle = new UIMessageBundle();
                uiMessageBundle.setKey(messageBundle.getId().getKey());
            }
            uiMessageBundle.addLabel(messageBundle.getId().getLanguage(), messageBundle.getLabel());
            map.put(messageBundle.getId().getKey(), uiMessageBundle);
        });
        return new ArrayList<>(map.values());
    }
}
