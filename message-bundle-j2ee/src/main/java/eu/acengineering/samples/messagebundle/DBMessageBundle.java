package eu.acengineering.samples.messagebundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

@Named(value = "msg")
public class DBMessageBundle extends ResourceBundle {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBMessageBundle.class);

    @Inject
    private MessageBundleService messageBundleService; 

    public DBMessageBundle() {
        super();
    }

    @Override
    protected Object handleGetObject(final String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        Locale locale = context.getViewRoot().getLocale();
        LOGGER.debug("Getting message {} for language {} from DB", key, locale.getLanguage());
        return messageBundleService.findByKeyAndLanguage(key, locale.getLanguage()).getLabel();
    }

    @Override
    public Enumeration<String> getKeys() {
        return Collections.enumeration(messageBundleService.findAllKey());
    }

}
