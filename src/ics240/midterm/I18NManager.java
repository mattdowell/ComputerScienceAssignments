//*******1*********2*********3*********4*********5*********6*********7*********8
package ics240.midterm;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class manages the instantiation of the resource bundle in the current
 * language and/or country
 * 
 * @author matt.dowell
 *
 */
public class I18NManager {

    private static final I18NManager INSTANCE = new I18NManager();
    
    private ResourceBundle messages = null;
    
    private I18NManager() {
        
        Locale currentLocale = Locale.getDefault();
        messages = ResourceBundle.getBundle("ics240.midterm.MessagesBundle", currentLocale);  
    }
    
    public ResourceBundle getMessages() {
        return messages;
    }
    
    public static I18NManager getInstance() {
        return INSTANCE;
    }

}
