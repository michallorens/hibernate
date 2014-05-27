package pl.edu.agh;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import pl.edu.agh.util.HibernateUtil;

public class App6 {
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Query query = session.createQuery("from Orders as o "
				+ "left join fetch o.customers "
				+ "where o.orderid=1");
		
		List<?> result = query.list();
	}
}
