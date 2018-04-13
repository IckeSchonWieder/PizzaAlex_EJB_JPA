
package de.pizzaalex.control;

import de.pizzaalex.ejb.DataBeanRemote;
import de.pizzaalex.model.Order;
import de.pizzaalex.model.OrderedItem;
import de.pizzaalex.model.Pizza;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author AWagner
 */

@Named
@SessionScoped
public class OrderBean extends LookUpData {
    private DataBeanRemote dbr;
    private Order order;
    

    public OrderBean() {
        order = new Order();
    }
    
    
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    
    
    public void setIpAndSession() {
        HttpServletRequest req=(HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
        order.setIpAddr(req.getRemoteAddr());
        order.setSessId(req.getSession().getId());
    }
    
     
    /**
     * Adds the specified pizza onto the cart. count will be raised by one, if 
     * Pizza already on cart. Otherwise, a new OrderedItem is added to cart.
     * @param pizza Specifies the pizza which shall be added
    
     */
    public void addPizza(Pizza pizza) {
        OrderedItem item = order.getItemByID(pizza.getId());
        
        if (item == null) {
            order.getItems().add(new OrderedItem(pizza, 1)); 
        } else {
            item.setCount(item.getCount() + 1);
        }
        
    }
    
    
     /**
     * Removes the specified pizza onto the cart. OrderedItem will be removed 
     * from cart, if count = 1. Otherwise, count will be decreased by one
     * @param pizza Specifies the pizza which shall be added
    
     */
    public void removePizza(Pizza pizza) {
        OrderedItem item = order.getItemByID(pizza.getId());
                
        try {
            if (item.getCount() == 1) {
                order.getItems().remove(item); 
            } else {
                item.setCount(item.getCount() - 1);
            }
        } catch (NullPointerException e) {
            System.out.println("No Pizza found to remove!");
        }
    }
    

    public String finalizeOrder() {
        setIpAndSession();
        dbr = lookupDataBeanRemote();
        dbr.storeOrder(order);
        return "finalize";
    }
    
   
}
