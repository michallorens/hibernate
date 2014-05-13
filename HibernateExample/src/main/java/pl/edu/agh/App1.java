package pl.edu.agh;

import java.math.BigDecimal;

import org.hibernate.Session;

import pl.edu.agh.northwind.Categories;
import pl.edu.agh.northwind.Products;
import pl.edu.agh.util.HibernateUtil;

public class App1 {
	public static void main(String[] args) {
		System.out.println("Maven + Hibernate + Oracle");
		Session session = HibernateUtil.getSessionFactory().openSession();
 
		Categories category = new Categories("Owoce");
		Products product = new Products("Banan", new BigDecimal(0));
		product.setCategories(category);
		Products product2 = new Products("Jablko", new BigDecimal(0));
		product2.setCategories(category);

		session.beginTransaction();
		session.save(product);
		session.save(product2);
		session.getTransaction().commit();
		
		category.setCategoryname("Swieze owoce");

		session.beginTransaction();
		session.saveOrUpdate(category);
		session.getTransaction().commit();
		
		session.close();
	}
}
