
package de.pizzaalex.ejb;

import de.pizzaalex.model.Customer;
import de.pizzaalex.model.MyOrder;
import de.pizzaalex.model.Pizza;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 *
 * @author AWagner
 */
@Stateful(mappedName = "ejb/DataBean")
public class DataBean implements Serializable, DataBeanRemote {
   
    @PersistenceContext
    private EntityManager em;
    
    public DataBean() {

    }
    
    
     
    @Override
    public ArrayList<Customer> getCustomers() {
        Query q = em.createQuery("SELECT * FROM Customer");
        
        return (ArrayList)q.getResultList();
    }

    @Override
    public Customer storeCustomer(Customer cus) {
        em.persist(cus);
        return cus;
    }
    
    
    @Override
    public ArrayList<Pizza> getMenuList() {
        Query q = em.createQuery("SELECT * FROM Pizza");
        return (ArrayList)q.getResultList();
    }

    @Override
    public Pizza storePizza(Pizza p) {
        em.persist(p);
        return p;
    }

    @Override
    public void removePizza(Pizza p) {
        em.remove(p);
    }
    
    @Override
    public void storeOrder(MyOrder ord) {
        em.persist(ord);
    }
}
