
package de.pizzaalex.ejb;

import de.pizzaalex.model.Customer;
import de.pizzaalex.model.MyOrder;
import de.pizzaalex.model.Pizza;
import de.pizzaalex.util.Encoder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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
    public List<Customer> getCustomers() {
        Query q = em.createQuery("SELECT c FROM Customer c");
        Query q2 = em.createNamedQuery("query2");
        return q2.getResultList();
    }

    @Override
    public Customer storeCustomer(Customer cus) {
        String pwRemind = cus.getPassword();
        cus.setPassword(Encoder.hash(cus.getPassword()));
        System.out.println("gehashd: " +cus.getPassword());
        em.persist(cus);
        em.flush();
        em.detach(cus);
        
        cus.setPassword(pwRemind);
        System.out.println("ungehashd: " +cus.getPassword());
        return cus;
    }
    
    
    @Override
    public List<Pizza> getMenuList() {
        Query q = em.createQuery("SELECT p FROM Pizza p");
        return q.getResultList();
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
