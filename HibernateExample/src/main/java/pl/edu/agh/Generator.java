package pl.edu.agh;

import java.util.Random;

import org.hibernate.Session;

import pl.edu.agh.northwind.Categories;
import pl.edu.agh.northwind.Customers;
import pl.edu.agh.northwind.Employees;
import pl.edu.agh.util.HibernateUtil;

public class Generator {
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
 
		Random rand = new Random();
		
		String categoryNames[] = { "Warzywa", "Owoce", "Nabia³", "Alkohol", "Miêso", "Przyprawy", "Napoje", "S³odycze", "Tekstylia", "Odzie¿" };

		session.beginTransaction();
		for(int i = 0; i < 1000; i++) {
			Categories category = new Categories(categoryNames[rand.nextInt(10)]);
			session.save(category);
		}
		session.getTransaction().commit();
		
		String names[] = { "Adam", "Ada", "Igor", "Ewa", "Piotr", "Alicja", "Wojciech", "Ewelina", "Lech", "Bogumi³a" };
		String surnames[] = { "Nowak", "Wójcik", "WoŸniak", "Kaczmarek", "Mazur", "Krawczyk", "Adamczyk", "Dudek", "Zaj¹c", "Król" };
		//String titles[] = { "mgr", "in¿.", "mgr in¿.", "dr", "dr hab.", "lic.", "prof. dr hab.", "dr in¿.", "dr hab. in¿", "prof. dr hab. in¿." };
		
		session.beginTransaction();
		for(int i = 0; i < 1000; i++) {
			Employees employee = new Employees(names[rand.nextInt(10)], surnames[rand.nextInt(10)]);
			session.save(employee);
		}
		session.getTransaction().commit();
		
		String companies[] = { "Spo³em", "¯abka", "Biedronka", "Lewiatan", "Lidl", "Netto", "Carrefour", "Auchan", "Makro", "Avitex" };
		
		session.beginTransaction();
		for(int i = 0; i < 1000; i++) {
			Customers customer = new Customers(companies[rand.nextInt(10)]);
			session.save(customer);
		}
		session.getTransaction().commit();
		
		
		
		session.close();
	}
}
