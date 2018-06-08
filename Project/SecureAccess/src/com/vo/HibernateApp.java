package com.vo;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class HibernateApp {

	public static void main(String[] args) {
		System.out.println("Maven + Hibernate + MySQL");
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Query query = session.createSQLQuery
				("Select * from userinfo" )
				.addEntity(UserInfo.class);
		
		//query.setParameter("1", new Date(1982,06,12));

		
		List<UserInfo> users=query.list();
		System.out.println(users.size());
		/*
		session.beginTransaction();
		UserInfo userInfo = new UserInfo();
		userInfo.setName("AAA");
		userInfo.setDob(new Date());
		userInfo.setEmail("r.jerome@gmail.com");

		session.save(userInfo);
		session.getTransaction().commit();
		*/
		

	}

}
