package eu.acengineering.samples.messagebundle.ui;

import eu.acengineering.samples.messagebundle.MessageBundleService;
import eu.acengineering.samples.messagebundle.model.MessageBundle;
import eu.acengineering.samples.messagebundle.model.MessageBundleKey;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import static javax.faces.context.FacesContext.getCurrentInstance;
import javax.faces.event.ActionEvent;
import org.apache.commons.lang.StringUtils;

import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean
public class MessageBundleListBean implements Serializable {

    private static final long serialVersionUID = -6251300594266251350L;

    private LazyDataModel<UIMessageBundle> lazyModel;

    @Inject
    private MessageBundleService messageBundleService;

    private List<SelectItem> languages;

    private String languageFilter;

    private Locale locale = Locale.ENGLISH;

    private String key;

    private String value;

    @PostConstruct
    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            initLocalesList();
            loadBundles();
        }
    }

    public void filterOnLabel() {
        System.out.println(languageFilter);
        loadBundles();
    }

    public LazyDataModel<UIMessageBundle> getLazyModel() {
        return lazyModel;
    }

    public List<SelectItem> getLanguages() {
        return languages;
    }

    public String getLanguageFilter() {
        return languageFilter;
    }

    public void setLanguageFilter(final String someLanguageFilter) {
        System.out.println("Set languageFilter: " + someLanguageFilter);
        languageFilter = someLanguageFilter;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void add(ActionEvent actionEvent) {
        MessageBundle bundle = new MessageBundle();
        bundle.setId(new MessageBundleKey(key, languageFilter));
        bundle.setLabel(value);
        messageBundleService.saveOrUpdate(bundle);
        key = "";
        value = "";
    }

    public void onEdit(final RowEditEvent event) {
        UIMessageBundle row = (UIMessageBundle) event.getObject();
        row.getLabels().entrySet().stream().forEach((label) -> {
            MessageBundle bundle = new MessageBundle();
            bundle.setId(new MessageBundleKey(row.getKey(), label.getKey()));
            bundle.setLabel(label.getValue());
            if (StringUtils.isNotEmpty(label.getValue())) {
                messageBundleService.saveOrUpdate(bundle);
            } else {
                messageBundleService.delete(bundle);
            }
        });
    }

    private void loadBundles() {
        lazyModel = new LazyMessageBundleDataModel(messageBundleService, languageFilter);
    }

    private void initLocalesList() {
        Iterator<Locale> iterator = getCurrentInstance().getApplication().getSupportedLocales();
        languages = new ArrayList<>();
        while (iterator.hasNext()) {
            Locale elementLocale = iterator.next();
            SelectItem localeSelectItem = new SelectItem(
                    elementLocale.getLanguage(),
                    elementLocale.getDisplayName(locale)
            );
            languages.add(localeSelectItem);
        }
        languageFilter = (String) languages.get(0).getValue();
    }

}
