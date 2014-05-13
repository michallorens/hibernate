package pl.edu.agh;

import java.math.BigDecimal;

import org.hibernate.Session;

import pl.edu.agh.northwind.Customers;
import pl.edu.agh.northwind.Employees;
import pl.edu.agh.northwind.OrderDetails;
import pl.edu.agh.northwind.OrderDetailsId;
import pl.edu.agh.northwind.Orders;
import pl.edu.agh.northwind.Products;
import pl.edu.agh.util.HibernateUtil;

public class App5 {
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		Employees employee = new Employees("Krzysztof", "Nawrot");
		Customers customer = new Customers("Ocado Software");
		Orders order = new Orders(employee, customer, 
				null, null, null, null, null, null, 
				null, null, null, null, null, null);
		Products product = new Products("Kupa", new BigDecimal(0));
		OrderDetails od = new OrderDetails(new OrderDetailsId(order, product), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0));

		session.beginTransaction();
		session.save(product);
		session.save(order);
		session.save(od);		
		session.getTransaction().commit();
		
		session.close();
	}

}
