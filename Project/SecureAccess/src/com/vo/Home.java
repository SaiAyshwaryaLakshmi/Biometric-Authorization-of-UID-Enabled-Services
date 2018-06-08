package com.vo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.hibernate.Query;
import org.hibernate.Session;

import SecuGen.FDxSDKPro.jni.JSGFPLib;
import SecuGen.FDxSDKPro.jni.SGDeviceInfoParam;
import SecuGen.FDxSDKPro.jni.SGFDxDeviceName;
import SecuGen.FDxSDKPro.jni.SGFDxSecurityLevel;
import SecuGen.FDxSDKPro.jni.SGFingerInfo;
import SecuGen.FDxSDKPro.jni.SGFingerPosition;
import SecuGen.FDxSDKPro.jni.SGImpressionType;
import SecuGen.FDxSDKPro.jni.SGPPPortAddr;

public class Home extends JFrame {
	
	private String userId;
	
	private List<Integer> services;

	public void setServices(List<Integer> services) {
		this.services = services;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	JTabbedPane tabbedPane = new JTabbedPane();
	JPanel registerPanel = new JPanel();
	JPanel verifyPanel = new JPanel();
	JPanel aboutPanel = new JPanel();

	JTextField txtName = new JTextField(15);
	JTextField txtDob = new JTextField(15);
	JTextField txtEmail = new JTextField(15);
	JTextField txtPhone = new JTextField(15);
	JTextField txtPan = new JTextField(15);
	JTextField txtAathar = new JTextField(15);
	JTextField txtPassport = new JTextField(15);

	JSGFPLib jsgfpLib = new JSGFPLib();
	SGDeviceInfoParam deviceInfo = new SGDeviceInfoParam();

	public static void showError(Long error) {
		switch (error.intValue()) {
		case 6:
			System.out.println("Error Loading Device Driver");
			break;
		case 0:
			System.out.println("Success..");
			break;
		}
	}

	public Home() {
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Home");

		/** Detect the device */
		long error = jsgfpLib.Init(SGFDxDeviceName.SG_DEV_AUTO);
		showError(error);

		/** Open the device */
		error = jsgfpLib.OpenDevice(SGPPPortAddr.USB_AUTO_DETECT);
		showError(error);

		/** Get Image size of device */

		error = jsgfpLib.GetDeviceInfo(deviceInfo);
		showError(error);

		initTabs();
		initVerify();
		initRegister();
		initDevice();
		initAbout();
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		setResizable(false);
		setSize(400, 600);
		setLocation(200, 150);
	}

	private void initDevice() {
		// TODO Auto-generated method stub

	}

	private void initAbout() {
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		JPanel leftTopPanel = new JPanel();
		JPanel rightTopPanel = new JPanel();

		bottomPanel.setLayout(new GridLayout(8, 3, 5, 3));

		JLabel lblName = new JLabel("Name:", JLabel.RIGHT);
		JLabel lblEmail = new JLabel("Email:", JLabel.RIGHT);
		JLabel lblDob = new JLabel("DOB:", JLabel.RIGHT);
		JLabel lblPhone = new JLabel("Phone:", JLabel.RIGHT);
		JLabel lblPan = new JLabel("Pan No:", JLabel.RIGHT);
		JLabel lblAathar = new JLabel("Aathar No:", JLabel.RIGHT);
		JLabel lblPassport = new JLabel("Passport No:", JLabel.RIGHT);
		JLabel lblPurpose = new JLabel("Purpose:", JLabel.RIGHT);

		JLabel lblNameVerify = new JLabel();
		JLabel lblDobVerify = new JLabel();
		JLabel lblEmailVerify = new JLabel();
		JLabel lblPhoneVerify = new JLabel();
		JLabel lblPanVerify = new JLabel();
		JLabel lblAatharVerify = new JLabel();
		JLabel lblPassportVerify = new JLabel();
		JLabel lblPurposeVerify = new JLabel();

		bottomPanel.add(lblName);
		bottomPanel.add(lblNameVerify);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblDob);
		bottomPanel.add(lblDobVerify);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblEmail);
		bottomPanel.add(lblEmailVerify);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblPhone);
		bottomPanel.add(lblPhoneVerify);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblPan);
		bottomPanel.add(lblPanVerify);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblAathar);
		bottomPanel.add(lblAatharVerify);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblPassport);
		bottomPanel.add(lblPassportVerify);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblPurpose);
		bottomPanel.add(lblPurposeVerify);
		bottomPanel.add(new JLabel());

		topPanel.setLayout(new GridLayout(1, 2));
		topPanel.add(leftTopPanel);
		topPanel.add(rightTopPanel);

		aboutPanel.add(topPanel, BorderLayout.EAST);
		aboutPanel.add(bottomPanel, BorderLayout.CENTER);

	}

	private void initRegister() {
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		JPanel leftTopPanel = new JPanel();
		JPanel rightTopPanel = new JPanel();

		leftTopPanel.setPreferredSize(new Dimension(180, 230));
		rightTopPanel.setPreferredSize(new Dimension(180, 230));

		// leftTopPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
		// rightTopPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN,
		// 1));

		JButton btnCapture = new JButton("Capture");
		JButton btnReCapture = new JButton("Re-Capture");

		BufferedImage img = new BufferedImage(deviceInfo.imageWidth,
				deviceInfo.imageHeight, BufferedImage.TYPE_BYTE_GRAY);

		BufferedImage img2 = new BufferedImage(deviceInfo.imageWidth,
				deviceInfo.imageHeight, BufferedImage.TYPE_BYTE_GRAY);

		JLabel leftFingImage = new JLabel();
		JLabel rightFingImage = new JLabel();

		byte[] firstTemplate = new byte[400];
		byte[] secondTemplate = new byte[400];

		btnCapture.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				byte[] capturedData = ((java.awt.image.DataBufferByte) img
						.getRaster().getDataBuffer()).getData();
				long timeout = 10000;
				long quality = 50;

				jsgfpLib.GetImageEx(capturedData, timeout, 0, quality);
				leftFingImage.setIcon(new ImageIcon(img.getScaledInstance(130,
						150, Image.SCALE_DEFAULT)));

				int[] createdquality = new int[1];

				jsgfpLib.GetImageQuality(deviceInfo.imageWidth,
						deviceInfo.imageHeight, capturedData, createdquality);

				SGFingerInfo fingerInfo = new SGFingerInfo();
				fingerInfo.FingerNumber = SGFingerPosition.SG_FINGPOS_LI;
				fingerInfo.ImageQuality = createdquality[0];
				fingerInfo.ImpressionType = SGImpressionType.SG_IMPTYPE_LP;
				fingerInfo.ViewNumber = 1;

				// Create image template to verify

				jsgfpLib.CreateTemplate(fingerInfo, capturedData, firstTemplate);

			}
		});

		btnReCapture.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				byte[] capturedData = ((java.awt.image.DataBufferByte) img2
						.getRaster().getDataBuffer()).getData();
				long timeout = 10000;
				long quality = 50;

				jsgfpLib.GetImageEx(capturedData, timeout, 0, quality);
				rightFingImage.setIcon(new ImageIcon(img2.getScaledInstance(
						130, 150, Image.SCALE_DEFAULT)));

				int[] createdquality = new int[1];

				jsgfpLib.GetImageQuality(deviceInfo.imageWidth,
						deviceInfo.imageHeight, capturedData, createdquality);

				SGFingerInfo fingerInfo = new SGFingerInfo();
				fingerInfo.FingerNumber = SGFingerPosition.SG_FINGPOS_LI;
				fingerInfo.ImageQuality = createdquality[0];
				fingerInfo.ImpressionType = SGImpressionType.SG_IMPTYPE_LP;
				fingerInfo.ViewNumber = 1;

				jsgfpLib.CreateTemplate(fingerInfo, capturedData,
						secondTemplate);

			}
		});

		leftFingImage.setPreferredSize(new Dimension(120, 180));
		leftFingImage.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		leftTopPanel.add(leftFingImage);
		leftTopPanel.add(btnCapture);

		rightFingImage.setPreferredSize(new Dimension(120, 180));
		rightFingImage
				.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		rightTopPanel.add(rightFingImage);
		rightTopPanel.add(btnReCapture);

		bottomPanel.setLayout(new GridLayout(7, 3, 5, 3));

		JLabel lblName = new JLabel("Name:", JLabel.RIGHT);
		JLabel lblDob = new JLabel("DOB:", JLabel.RIGHT);
		JLabel lblEmail = new JLabel("Email:", JLabel.RIGHT);
		JLabel lblPhone = new JLabel("Phone:", JLabel.RIGHT);
		JLabel lblPan = new JLabel("Pan No:", JLabel.RIGHT);
		JLabel lblAathar = new JLabel("Aathar No:", JLabel.RIGHT);
		JLabel lblPassport = new JLabel("Passport No:", JLabel.RIGHT);

		bottomPanel.add(lblName);
		bottomPanel.add(txtName);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblDob);
		bottomPanel.add(txtDob);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblEmail);
		bottomPanel.add(txtEmail);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblPhone);
		bottomPanel.add(txtPhone);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblPan);
		bottomPanel.add(txtPan);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblAathar);
		bottomPanel.add(txtAathar);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblPassport);
		bottomPanel.add(txtPassport);
		bottomPanel.add(new JLabel());

		topPanel.setLayout(new GridLayout(1, 2));
		topPanel.add(leftTopPanel);
		topPanel.add(rightTopPanel);

		JButton registerBtn = new JButton("Register");
		registerBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean flag = true;
				UserInfo userInfo = new UserInfo();
				if (txtName.getText() == null || txtName.getText() == "") {
					JOptionPane.showMessageDialog(null, "Enter Valid Name",
							"Error", JOptionPane.ERROR_MESSAGE);
					flag = false;
				} else {
					userInfo.setName(txtName.getText());
				}

				if (txtDob.getText() == null || txtDob.getText() == "") {
					JOptionPane.showMessageDialog(null,
							"Enter Valid Date of Birth", "Error",
							JOptionPane.ERROR_MESSAGE);
					flag = false;
				} else {
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					try {
						Date date = format.parse(txtDob.getText());
						userInfo.setDob(txtDob.getText());
					} catch (ParseException e) {
						JOptionPane.showMessageDialog(null,
								"Date of Birth should be of format DD/MM/YYYY",
								"Error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
						flag = false;
					}
				}
				userInfo.setEmail(txtEmail.getText());
				userInfo.setPanno(txtPan.getText());
				userInfo.setPassportno(txtPassport.getText());
				userInfo.setPhone(txtPhone.getText());
				userInfo.setAatharno(txtAathar.getText());

				long secuLevel = SGFDxSecurityLevel.SL_NORMAL;
				boolean[] matched = new boolean[1];
				matched[0] = false;

				if (jsgfpLib.MatchTemplate(firstTemplate, secondTemplate,
						secuLevel, matched) == 0) {
					if (matched[0] == false) {
						JOptionPane.showMessageDialog(null,
								"Fingure Print is Not Matching", "Error",
								JOptionPane.ERROR_MESSAGE);
						flag = false;
					} else {
						userInfo.setFingureprint(firstTemplate);
					}
				}

				if (flag) {
					Session session = HibernateUtil.getSessionFactory()
							.openSession();

					session.beginTransaction();
					session.save(userInfo);
					session.getTransaction().commit();
					JOptionPane.showMessageDialog(null,
							"Date Stored Successfully", "Success",
							JOptionPane.INFORMATION_MESSAGE);
					txtName.setText("");
					txtDob.setText("");
					txtEmail.setText("");
					txtPan.setText("");
					txtPassport.setText("");
					txtPhone.setText("");
					txtAathar.setText("");

				}
			}
		});

		registerPanel.add(topPanel, BorderLayout.EAST);
		registerPanel.add(bottomPanel, BorderLayout.CENTER);
		registerPanel.add(registerBtn, BorderLayout.WEST);

	}

	private void initVerify() {
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		JPanel leftTopPanel = new JPanel();
		JPanel rightTopPanel = new JPanel();

		leftTopPanel.setPreferredSize(new Dimension(180, 230));
		rightTopPanel.setPreferredSize(new Dimension(180, 230));

		JButton btnCapture = new JButton("Capture");
		JButton btnVerify = new JButton("Verify");
		JButton btnClear = new JButton("Print");

		JLabel leftFingImage = new JLabel();

		BufferedImage verifyimg = new BufferedImage(deviceInfo.imageWidth,
				deviceInfo.imageHeight, BufferedImage.TYPE_BYTE_GRAY);

		byte[] verifyTemplate = new byte[400];

		btnCapture.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] capturedData = ((java.awt.image.DataBufferByte) verifyimg
						.getRaster().getDataBuffer()).getData();
				long timeout = 10000;
				long quality = 50;

				jsgfpLib.GetImageEx(capturedData, timeout, 0, quality);
				leftFingImage.setIcon(new ImageIcon(verifyimg
						.getScaledInstance(130, 150, Image.SCALE_DEFAULT)));

				int[] createdquality = new int[1];

				jsgfpLib.GetImageQuality(deviceInfo.imageWidth,
						deviceInfo.imageHeight, capturedData, createdquality);

				SGFingerInfo fingerInfo = new SGFingerInfo();
				fingerInfo.FingerNumber = SGFingerPosition.SG_FINGPOS_LI;
				fingerInfo.ImageQuality = createdquality[0];
				fingerInfo.ImpressionType = SGImpressionType.SG_IMPTYPE_LP;
				fingerInfo.ViewNumber = 1;

				jsgfpLib.CreateTemplate(fingerInfo, capturedData,
						verifyTemplate);

			}
		});

		JTextField dobfield = new JTextField(15);

		JLabel lblNameVerify = new JLabel();
		JLabel lblDobVerify = new JLabel();
		JLabel lblEmailVerify = new JLabel();
		JLabel lblPhoneVerify = new JLabel();
		JLabel lblPanVerify = new JLabel();
		JLabel lblAatharVerify = new JLabel();
		JLabel lblPassportVerify = new JLabel();
		JLabel lblPurposeVerify = new JLabel();

		btnVerify.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat format = new SimpleDateFormat("DD/mm/yyyy");
				boolean flag = true;
				Date date = null;
				if (dobfield.getText() == null || dobfield.getText() == null) {
					JOptionPane.showMessageDialog(null,
							"Date of Birth is Mandatory", "Error",
							JOptionPane.ERROR_MESSAGE);
					flag = false;
				} else {

					try {
						date = format.parse(dobfield.getText());
					} catch (ParseException e1) {
						JOptionPane.showMessageDialog(null,
								"Date of Birth should be of format DD/MM/YYYY",
								"Error", JOptionPane.ERROR_MESSAGE);
						flag = false;
					}
				}

				if (flag) {
					Session session = HibernateUtil.getSessionFactory()
							.openSession();

					Query query = session.createSQLQuery(
							"Select * from userinfo where dob=:1").addEntity(
							UserInfo.class);
					query.setParameter("1", dobfield.getText());
					List<UserInfo> users = query.list();
					UserInfo userInfoOut = null;
					long secuLevel = SGFDxSecurityLevel.SL_NORMAL;
					boolean[] matched = new boolean[1];
					matched[0] = false;

					for (UserInfo userInfo : users) {

						if (jsgfpLib.MatchTemplate(userInfo.getFingureprint(),
								verifyTemplate, secuLevel, matched) == 0) {
							if (matched[0]) {
								userInfoOut = userInfo;
							}
						}

					}
					if (userInfoOut != null) {
						lblNameVerify.setText(userInfoOut.getName());
						lblDobVerify.setText(userInfoOut.getDob().toString());
						lblEmailVerify.setText(userInfoOut.getEmail());
						lblPhoneVerify.setText(userInfoOut.getPhone());
						lblPanVerify.setText(userInfoOut.getPanno());
						lblAatharVerify.setText(userInfoOut.getAatharno());
						lblPassportVerify.setText(userInfoOut.getPassportno());
						Query query2=session.createSQLQuery("select * from services").addEntity(Services.class);
						List<Services> serviceList=query2.list();
						String data="";
						for(Services s:serviceList){
							if(services.contains(s.getId())){
								if(data.length()==0){
									data=s.getName();
								}else{
									data=data+", ";
									data=data+s.getName();
								}
							}
						}
						lblPurposeVerify.setText(data);
					} else {
						JOptionPane.showMessageDialog(null,
								"No Records Available", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});

		btnClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Configure Printer");

			}
		});

		leftFingImage.setPreferredSize(new Dimension(120, 180));
		leftFingImage.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		leftTopPanel.add(leftFingImage);

		rightTopPanel.setLayout(new GridLayout(5, 1, 5, 5));
		rightTopPanel.add(new JLabel("Date Of Birth"));
		rightTopPanel.add(dobfield);
		rightTopPanel.add(btnCapture);
		rightTopPanel.add(btnVerify);
		rightTopPanel.add(btnClear);

		bottomPanel.setLayout(new GridLayout(8, 3, 5, 3));

		JLabel lblName = new JLabel("Name:", JLabel.RIGHT);
		JLabel lblDob = new JLabel("DOB:", JLabel.RIGHT);
		JLabel lblEmail = new JLabel("Email:", JLabel.RIGHT);
		JLabel lblPhone = new JLabel("Phone:", JLabel.RIGHT);
		JLabel lblPan = new JLabel("Pan No:", JLabel.RIGHT);
		JLabel lblAathar = new JLabel("Aathar No:", JLabel.RIGHT);
		JLabel lblPassport = new JLabel("Passport No:", JLabel.RIGHT);
		JLabel lblPurpose = new JLabel("Purpose:", JLabel.RIGHT);

		bottomPanel.add(lblName);
		bottomPanel.add(lblNameVerify);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblDob);
		bottomPanel.add(lblDobVerify);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblEmail);
		bottomPanel.add(lblEmailVerify);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblPhone);
		bottomPanel.add(lblPhoneVerify);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblPan);
		bottomPanel.add(lblPanVerify);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblAathar);
		bottomPanel.add(lblAatharVerify);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblPassport);
		bottomPanel.add(lblPassportVerify);
		bottomPanel.add(new JLabel());
		bottomPanel.add(lblPurpose);
		bottomPanel.add(lblPurposeVerify);
		bottomPanel.add(new JLabel());

		topPanel.setLayout(new GridLayout(1, 2));
		topPanel.add(leftTopPanel);
		topPanel.add(rightTopPanel);

		verifyPanel.add(topPanel, BorderLayout.EAST);
		verifyPanel.add(bottomPanel, BorderLayout.CENTER);
	}

	private void initTabs() {
		tabbedPane.addTab("Verify", null, verifyPanel, "Verify");
		tabbedPane.addTab("Register", null, registerPanel, "Register");
		tabbedPane.addTab("About", null, aboutPanel, "About");

	}
}