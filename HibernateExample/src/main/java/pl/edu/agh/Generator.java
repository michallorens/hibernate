package pl.edu.agh;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;

import pl.edu.agh.northwind.Categories;
import pl.edu.agh.northwind.Customers;
import pl.edu.agh.northwind.Employees;
import pl.edu.agh.northwind.OrderDetails;
import pl.edu.agh.northwind.OrderDetailsId;
import pl.edu.agh.northwind.Orders;
import pl.edu.agh.northwind.Products;
import pl.edu.agh.northwind.Shippers;
import pl.edu.agh.northwind.Suppliers;
import pl.edu.agh.util.HibernateUtil;

public class Generator {
	private final static int DATA_N = 100;
	
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Random rand = new Random();
		
		String categoryNames[] = { "Warzywa", "Owoce", "Nabial", "Alkohol", "Mieso", "Przyprawy", "Napoje", "Slodycze", "Tekstylia", "Odziez" };
		String names[] = { "Adam", "Ada", "Igor", "Ewa", "Piotr", "Alicja", "Wojciech", "Ewelina", "Lech", "Bogumila" };
		String surnames[] = { "Nowak", "Wojcik", "Wozniak", "Kaczmarek", "Mazur", "Krawczyk", "Adamczyk", "Dudek", "Zajsc", "Krol" };
		String companies[] = { "Spolem", "Zabka", "Biedronka", "Lewiatan", "Lidl", "Netto", "Carrefour", "Auchan", "Makro", "Avitex" };
		String productNames[] = { "Snickers", "Coca-Cola", "Maka", "Ryz", "Bluza", "Koszula", "Wodka Stock", "Ogorki kiszone", "Kucharek", "Spaghetti" };
		//String titles[] = { "mgr", "in¿.", "mgr in¿.", "dr", "dr hab.", "lic.", "prof. dr hab.", "dr in¿.", "dr hab. in¿", "prof. dr hab. in¿." };
		
		List<Employees> employees = new ArrayList<Employees>();
		List<Customers> customers = new ArrayList<Customers>();
		List<Suppliers> suppliers = new ArrayList<Suppliers>();
		List<Categories> categories = new ArrayList<Categories>();
		List<Orders> orders = new ArrayList<Orders>();
		List<Products> products = new ArrayList<Products>();
		
		session.beginTransaction();
		for(int i = 0; i < DATA_N; i++) {
			Employees employee = new Employees(names[rand.nextInt(names.length)], surnames[rand.nextInt(surnames.length)]);
			Categories category = new Categories(categoryNames[rand.nextInt(categoryNames.length)]);
			Customers customer = new Customers(companies[rand.nextInt(companies.length)]);
			Shippers shipper = new Shippers(companies[rand.nextInt(companies.length)]);
			Suppliers supplier = new Suppliers(companies[rand.nextInt(companies.length)]);
			
			employees.add(employee);
			customers.add(customer);
			suppliers.add(supplier);
			categories.add(category);
			
			session.save(supplier);
			session.save(category);
			session.save(employee);
			session.save(shipper);
			session.save(customer);
		}
		session.getTransaction().commit();
		
		session.beginTransaction();
		for(int i = 0; i < DATA_N*DATA_N; i++) {
			Orders order = new Orders(employees.get(rand.nextInt(DATA_N)), 
					customers.get(rand.nextInt(DATA_N)), null, null, null, 
					null, null, null, null, null, null, null, null, null);
			orders.add(order);
			session.save(order);
		}
		session.getTransaction().commit();
		
		session.beginTransaction();
		for(int i = 0; i < DATA_N*DATA_N; i++) {
			Products product = new Products(productNames[rand.nextInt(productNames.length)], 
					new BigDecimal(0));
			products.add(product);
			session.save(product);
		}
		session.getTransaction().commit();
		
		session.beginTransaction();
		int limit = 100000;
		for(Orders order : orders) {
			for(Products product : products) {
				OrderDetailsId id = new OrderDetailsId(order, product);
				OrderDetails orderDetail = new OrderDetails(id, new BigDecimal(5), new BigDecimal(1), new BigDecimal(0));
				session.save(orderDetail);
				limit--;
				if(limit == 0)
					break;
			}
			if(limit == 0)
				break;
		}
		session.getTransaction().commit();
		
		session.close();
	}
}
