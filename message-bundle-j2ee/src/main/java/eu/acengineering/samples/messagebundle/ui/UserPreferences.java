package eu.acengineering.samples.messagebundle.ui;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class UserPreferences implements Serializable {
    
    private String locale = "en";

    public String  getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
    
}
