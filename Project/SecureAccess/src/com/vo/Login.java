package com.vo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.hibernate.Query;
import org.hibernate.Session;

public class Login extends JFrame implements ActionListener {
	JButton SUBMIT;
	JPanel panel;
	JLabel label1, label2;
	final JTextField text1, text2;

	Login() {
		label1 = new JLabel();
		label1.setText("Username:");
		text1 = new JTextField(15);

		label2 = new JLabel();
		label2.setText("Password:");
		text2 = new JPasswordField(15);

		SUBMIT = new JButton("SUBMIT");

		panel = new JPanel(new GridLayout(3, 1));
		panel.add(label1);
		panel.add(text1);
		panel.add(label2);
		panel.add(text2);
		panel.add(new JLabel());
		panel.add(SUBMIT);
		add(panel, BorderLayout.CENTER);
		SUBMIT.addActionListener(this);
		setTitle("LOGIN FORM");

		setLocation(200, 200);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent ae) {
		String value1 = text1.getText();
		String value2 = text2.getText();

		Session session = HibernateUtil.getSessionFactory().openSession();

		Object user =session.get(User.class, value1);
		
		if(user==null){
			JOptionPane.showMessageDialog(this, "Incorrect UserID", "Error", JOptionPane.ERROR_MESSAGE);
		}else{
			User dbUser=(User) user;
			if(dbUser.getPassword().equals(value2)){
				Home page = new Home();
				page.setUserId(value1);
				Query query=session.createSQLQuery("select service from userservice where userid=:1");
				query.setParameter("1", value1);
				List<Integer> services=query.list();
				page.setServices(services);
				page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setVisible(false);
				page.setVisible(true);
			}else {
				JOptionPane.showMessageDialog(this, "Incorrect password", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		
	}
}