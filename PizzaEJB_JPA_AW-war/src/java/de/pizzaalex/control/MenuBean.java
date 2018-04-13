
package de.pizzaalex.control;


import de.pizzaalex.ejb.DataBeanRemote;
import de.pizzaalex.model.Pizza;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;


import javax.inject.Named;


/**
 *
 * @author AWagner
 */

@Named
@ApplicationScoped
public class MenuBean extends LookUpData implements Serializable{
    private ArrayList<Pizza> menuList;
    private DataBeanRemote dbr = lookupDataBeanRemote();
    
    
    public MenuBean() {
        
    }
    
    @PostConstruct
    public void fillMenu(){
        menuList = dbr.getMenuList();
    }
    
    public ArrayList<Pizza> getMenuList() {
        return menuList;
    }
    
   
    
    
}
