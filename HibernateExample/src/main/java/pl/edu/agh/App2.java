package pl.edu.agh;

import java.math.BigDecimal;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import pl.edu.agh.northwind.Products;

public class App2 {
	public static void main(String [] args){
        SessionFactory sessionFactory = new Configuration()
                                .configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        
        session.beginTransaction();
        Products product = (Products) session.get(Products.class, new BigDecimal(5));
        session.getTransaction().commit();
        
        if (product != null) {
                System.out.println("Nazwa produktu: " + product.getProductname());
                System.out.println("Czy w pelni zainicjalizowano obiekt? " + Hibernate
                                                .isInitialized(product.getCategories()));
                System.out.println("Zczytywanie kategorii...");
                System.out.println("Nazwa: " + product
                                                .getCategories().getCategoryname());
        } else {
                System.err.println("product == null");
        }
        session.close();
	}
}
