
package de.pizzaalex.control;

import de.pizzaalex.ejb.DataBeanRemote;
import de.pizzaalex.model.Customer;
import de.pizzaalex.model.Order;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author AWagner
 */

@SessionScoped
@Named
public class CustomerBean extends LookUpData {
    private Customer selectedCustomer;
    private boolean hasAcc;
    private DataBeanRemote dbr;
    private ArrayList<Customer> customers;
    
    @Inject
    LoginBean lb;
    
    public CustomerBean() {
        hasAcc = false;
    }
   
    
    @PostConstruct
    public void fillList() {
        dbr = lookupDataBeanRemote();
        customers = dbr.getCustomers();
    }
    
    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }
       
    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    
    public ArrayList<Customer> getCustomers() {
        return customers;
    }
    
//    public void setCustomers(ArrayList<Customer> customers) {
//        this.customers = customers;
//    }
    
    public boolean isHasAcc() {
        return hasAcc;
    }

    public void setHasAcc(boolean hasAcc) {
        this.hasAcc = hasAcc;
    }

  
    
    public Customer getCustById(int id){
        Customer found = null;
        
        for (Customer cus:customers) {
            if (cus.getId() == id) {
                found = cus;
            } 
        }
        return found;
    } 
    
    
    public Customer getCustByUsername(String username){
        Customer found = null;
        for (Customer cus:customers) {
            if (cus.getUsername().equals(username.toLowerCase())) {
                found = cus;
            } 
        }
        return found;
    }
    
    
    public String newCust(){
        //selectedCustomer = new Customer();
        selectedCustomer = new Customer("Vorname", "Nachname", "Hier", "12345", "Landshut", "customer23");
        hasAcc = false;
        return "newCustomer";
    }
     
    
    public String storeCustomer() {
        selectedCustomer = dbr.storeCustomer(selectedCustomer);
        customers.add(selectedCustomer);
        lb.setUser(selectedCustomer);
        return lb.login();
        
    }
    
    
    public String checkOrder(Order order){
        order.setCus(selectedCustomer);
        return "check";
    }
    
    public String backToStart() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance()
               .getExternalContext().getRequest();
        lb.logout();
        
        req.getSession().invalidate();
        
        return "start";
    }
    
    
}
