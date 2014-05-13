package pl.edu.agh;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import pl.edu.agh.northwind.Customers;
import pl.edu.agh.util.HibernateUtil;

public class Hello {
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Customers customer = new Customers("Hello Hibernate!");
		
		session.beginTransaction();
		session.save(customer);
		session.getTransaction().commit();
		
		Query query = session.createQuery("from Customers where CompanyName = :name");
		query.setParameter("name", "Hello Hibernate!");
		
		List<?> result = query.list();
		System.out.println(((Customers) result.get(0)).getCompanyname());
	}
}
