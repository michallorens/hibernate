package pl.edu.agh;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import pl.edu.agh.northwind.Customers;
import pl.edu.agh.util.HibernateUtil;

public class App7 {
	private static final int ENDING_INDEX = 680000;
	private static final int INITIAL_INDEX = 600000;
	private static final String TABLE = "CUSTOMERS";
	private static final String TABLE_ID = "CUSTOMERID";
	private static final String TABLE_COMPANY_NAME = "COMPANYNAME";
	private static final String TABLE_CONTACT_NAME = "CONTACTNAME";
	private static final String TABLE_CONTACT_TITLE = "CONTACTNAME";
	private static final String TABLE_ADRESS = "ADDRESS";
	private static final String TABLE_CITY = "CITY";
	private static final String TABLE_REGION = "REGION";
	private static final String TABLE_POSTALCODE = "POSTALCODE";
	private static final String TABLE_COUNTRY = "COUNTRY";
	private static final String TABLE_PHONE = "PHONE";
	private static final String TABLE_FAX = "FAX";
	private static final String COMPANY_NAME = "Firma super";
	private static final String COMPANY_SEARCHED_NAME = "Szukana firma";

	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		/* nalezy wykomentowac nietestowana metode */
		try {
			testJDBC();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		testHibernate();
		
		String result = String.valueOf((System.currentTimeMillis() - start));		
		System.out.println("Czas wykonania: " + ((result.length() > 3) ?
					result.substring(0, result.length() - 3) + "," + result.substring(result.length() - 3)
					: "0," + result) + " sekund");
	
	}
	
	/* ------------------------ JDBC ------------------------ */
	private static void testJDBC() throws SQLException {
		String username = "user";
		String password = "password";
        String url = "jdbc:oracle:thin:@127.0.0.1:1521/XE";
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        
        /* wybieramy testowana metode */
        
        insertJDBC(username, statement);
        
        readJDBC(username, statement);
        
        searchJDBC(username, statement);
        
        deleteJDBC(username, statement);
        
        
        connection.close();
	}
	
	
	private static void insertJDBC(String username, Statement statement) throws SQLException {
		String query;
        
        for (int i = INITIAL_INDEX; i <= ENDING_INDEX; i++) {
            query = "insert into " + username + "." + TABLE + " (" + TABLE_ID + ", " 
            		+ TABLE_COMPANY_NAME + ") values ("+ i +", '"+ COMPANY_NAME +"')";
            statement.execute(query);
        }
	}
	
	private static void readJDBC(String username, Statement statement) throws SQLException {
		String query;
		
		query = "select * from " + username + "." + TABLE;
        ResultSet result = statement.executeQuery(query);
        LinkedList<Customers> list = new LinkedList<Customers>();
        while (result.next()) {
        	Customers customer = new Customers();
        	customer.setCustomerid(result.getBigDecimal(TABLE_ID));
        	customer.setCompanyname(result.getString(TABLE_COMPANY_NAME));
        	customer.setContactname(result.getString(TABLE_CONTACT_NAME));
        	customer.setContacttitle(result.getString(TABLE_CONTACT_TITLE));
        	customer.setAddress(result.getString(TABLE_ADRESS));
        	customer.setCity(result.getString(TABLE_CITY));
        	customer.setRegion(result.getString(TABLE_REGION));
        	customer.setPostalcode(result.getString(TABLE_POSTALCODE));
        	customer.setCountry(result.getString(TABLE_COUNTRY));
        	customer.setPhone(result.getString(TABLE_PHONE));
        	customer.setFax(result.getString(TABLE_FAX));
        	list.add(customer);
        }
	}
	
	private static void searchJDBC(String username, Statement statement) throws SQLException {
		String query;
		
		query = "select * from " + username + "." + TABLE + " where " + TABLE_COMPANY_NAME + " like '" + COMPANY_SEARCHED_NAME + "'";
        ResultSet result = statement.executeQuery(query); 
        if (result.next())
        	System.out.println(result.getString(TABLE_COMPANY_NAME));
	}
	
	private static void deleteJDBC(String username, Statement statement) throws SQLException {
		String query;
		
		query = "delete from " + username + "." + TABLE + " where " + TABLE_COMPANY_NAME + " like '" + COMPANY_SEARCHED_NAME + "'";
        statement.execute(query);
	}
	
	
	
	
	/* ------------------------ HIBERNATE ------------------------ */
	private static void testHibernate() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		
		/* wybieramy testowana metode */
		
		insertHibernateSingleTrans(session);
		
		insertHibernateMultipleTrans(session);
		
		readHibernateCriteria(session);
		
		readHibernateHQL(session);
		
		searchHibernateCriteria(session);
		
		searchHibernateHQL(session);
		
		deleteHibernateCriteria(session);
		
		deleteHibernateHQL(session);
		
		
		
		session.close();
	}
	
	private static void insertHibernateSingleTrans(Session session) {
		session.beginTransaction();
		for (int i = INITIAL_INDEX; i <= ENDING_INDEX; i++) {
			Customers customer = new Customers(COMPANY_NAME);
            session.save(customer);
        }
		session.getTransaction().commit();
	}
	
	private static void insertHibernateMultipleTrans(Session session) {
		for (int i = INITIAL_INDEX; i <= ENDING_INDEX; i++) {
			Customers customer = new Customers(COMPANY_NAME);
			session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();
        }
	}
	
	private static void readHibernateCriteria(Session session) {
		List list = session.createCriteria(Customers.class).list();
	}
	
	private static void readHibernateHQL(Session session) {
		String query = "from Customers";
		List queryReslt = session.createQuery(query).list();
	}
	
	private static void searchHibernateCriteria(Session session) {
		List list = session.createCriteria(Customers.class).add(Restrictions.like("companyname", COMPANY_SEARCHED_NAME)).list();
	}
	
	private static void searchHibernateHQL(Session session) {
		Query query = session.createQuery("from Customers where companyname like :name");
		query.setParameter("name", COMPANY_SEARCHED_NAME);
	}
	
	private static void deleteHibernateCriteria(Session session) {
		session.beginTransaction();
		Customers customer = (Customers) session.createCriteria(Customers.class)
                .add(Restrictions.like("companyname", COMPANY_SEARCHED_NAME)).uniqueResult();
		session.delete(customer);
		session.getTransaction().commit();
	}
	
	private static void deleteHibernateHQL(Session session) {
		session.beginTransaction();
		Query query = session.createQuery("delete from Customers where companyname like :name");
		query.setParameter("name", COMPANY_SEARCHED_NAME);
		query.executeUpdate();
		session.getTransaction().commit();
	}
	
	
	private static void cleanDB() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		String query = "delete from Customers where customerid >= " + INITIAL_INDEX;
		System.out.println("Usunieto:" + session.createQuery(query).executeUpdate());
		
		session.close();
	}
}
