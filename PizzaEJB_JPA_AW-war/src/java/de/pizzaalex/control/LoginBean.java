/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pizzaalex.control;

import de.pizzaalex.model.User;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author AWagner
 */
@SessionScoped
@Named
public class LoginBean implements Serializable{
    private User user;
    private Boolean loggedIn;
    HttpServletRequest req;
    
    @Inject
    CustomerBean cb;
    
    public LoginBean() {
        user = new User();
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

       
    public void settingRole() {
        if (req.isUserInRole("customerRole")){
            user.setUserRole("customer");
        } else if (req.isUserInRole("cookRole")) {
            user.setUserRole("cook");
        } else if (req.isUserInRole("managerRole")) {
            user.setUserRole("manager");
        } else {
            user.setUserRole(null); 
        }
    }
    
    
    public String login(){
        req =(HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
        try {
            
            req.login(user.getUsername().toLowerCase(), user.getPassword());
            loggedIn=true;
            settingRole();
            if (user.getUserRole().equals("customer")) {
                cb.setSelectedCustomer(cb.getCustByUsername(user.getUsername()));
            }
            System.out.println("User eingeloggt: " + user.getUsername() + " als " + user.getUserRole());
            
            // each user role will navigate to dedicated web page (navigation rule at faces config)
            return user.getUserRole();
            
        } catch (ServletException ex) {
            loggedIn=false;
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            return "error";
        }
        
    }
    
    public void logout() {
        req=(HttpServletRequest) FacesContext.getCurrentInstance()
               .getExternalContext().getRequest();
        try {
            req.logout();
            loggedIn = false;
           // cb.setSelectedCustomer(selectedCustomer);
            user = new User();
        } catch (ServletException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
