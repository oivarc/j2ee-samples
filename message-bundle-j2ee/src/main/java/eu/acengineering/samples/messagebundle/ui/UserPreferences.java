/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.acengineering.samples.messagebundle.ui;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Alessandro
 */
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
