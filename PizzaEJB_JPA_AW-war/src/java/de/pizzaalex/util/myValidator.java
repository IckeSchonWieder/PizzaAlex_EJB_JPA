/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pizzaalex.util;

import de.pizzaalex.control.CustomerBean;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author AWagner
 */
@FacesValidator("myValidator")
public class myValidator implements Validator {

    @Inject
    CustomerBean cb;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        HttpServletRequest request=(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        if (component.getClientId().contains("inFirst") ||
                component.getClientId().contains("inLast") ||
                component.getClientId().contains("inCity") ) {
            
            Pattern p=Pattern.compile("^[A-Za-zäöüÄÖÜß -]*$");
            Matcher m =p.matcher(value.toString());
            if(!m.matches()){
                throw new ValidatorException(getMessage("nameFailSign", context));
            }
            // 3 Leerzeichen immernoch möcglich ...
            if (value.toString().length() < 3) {
                throw new ValidatorException(getMessage("nameFailLength", context));
            }
        }
        
       
        if (component.getClientId().contains("inStreet")){
             
// 3 Leerzeichen immernoch möcglich ...
            if (value.toString().length() < 3) {
                throw new ValidatorException(getMessage("nameFailLength", context));
            }
        }
        
        if (component.getClientId().contains("inUsername")){
             
            if (value.toString().length() < 3) {
                throw new ValidatorException(getMessage("nameFailLength", context));
            }
            
            
            if (cb.getCustByUsername(value.toString()) != null ){
                throw new ValidatorException(getMessage("usernameExists", context));
            }
        }
             
        
    }
    
    
    private FacesMessage getMessage(String key,FacesContext context){
        ResourceBundle bundle = ResourceBundle.getBundle("labels", context.getViewRoot().getLocale());
        FacesMessage fmsg=new FacesMessage(bundle.getString(key));
        
        
        return fmsg;
    } 
    
}
