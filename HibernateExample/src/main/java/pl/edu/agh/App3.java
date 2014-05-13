package pl.edu.agh;

import java.math.BigDecimal;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import pl.edu.agh.northwind.Categories;

public class App3 {

	public static void main(String [] args){
        
        SessionFactory sessionFactory = new Configuration()
                                .configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        
        session.beginTransaction();
        Categories category = (Categories) session.get(Categories.class, new BigDecimal(1));
        session.delete(category);
        session.getTransaction().commit();
        session.close();
	}
}
