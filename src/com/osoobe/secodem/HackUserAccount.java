package com.osoobe.secodem;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.osoobe.secodem.util.DictionaryList;

public class HackUserAccount {
	private static JFrame frame, mframe;
	private static ArrayList<JTextField> jtextf = new ArrayList<JTextField>();
	private static JButton blogin;
	private static JLabel jl_status, jl_password_check;
	private static ArrayList<Component> comps;
	private static String[] args;
	private static JTextField jt_username, jt_minchar, jt_maxchar, jt_attempts;
	private static int password_attempts = 0, minchar = 0, maxchar = 25,
			max_attempts = 2000;
	private static boolean active = true, login_running = false;
	private static Thread hack_thread, login_thread;
	private static long startTime, endTime;
	private static JLabel jl_att;

	public static void main(String[] args) {
		mframe = new JFrame("Hacking Login");
		HackUserAccount.args = args;
		show(hackView());
	}

	public static void show(JPanel panel) {
		mframe.getContentPane().add(panel);
		mframe.setVisible(true);
		mframe.pack();
		mframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mframe.setBackground(Color.gray);
		mframe.setSize(600, 500);
	}

	public static JPanel hackView() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		panel.setBackground(Color.gray);
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(5, 5, 5, 5);

		JLabel jl_username = new JLabel("Username");
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridwidth = 3;
		panel.add(jl_username, cons);

		jt_username = new JTextField(25);
		cons.gridx = 3;
		cons.gridy = 0;
		cons.gridwidth = 3;
		panel.add(jt_username, cons);

		JLabel jl_minchar = new JLabel("Minimum Password Characters");
		cons.gridx = 0;
		cons.gridy = 2;
		panel.add(jl_minchar, cons);

		jt_minchar = new JTextField(4);
		cons.gridx = 3;
		cons.gridy = 2;
		cons.gridwidth = 3;
		panel.add(jt_minchar, cons);

		JLabel jl_maxchar = new JLabel("Maximum Passowrd Characters");
		cons.gridx = 0;
		cons.gridy = 4;
		cons.gridwidth = 3;
		panel.add(jl_maxchar, cons);

		jt_maxchar = new JTextField(4);
		cons.gridx = 3;
		cons.gridy = 4;
		cons.gridwidth = 3;
		panel.add(jt_maxchar, cons);

		JLabel jl_attempts = new JLabel("Numbers of attempts");
		cons.gridx = 0;
		cons.gridy = 6;
		cons.gridwidth = 3;
		panel.add(jl_attempts, cons);

		jt_attempts = new JTextField(10);
		cons.gridx = 3;
		cons.gridy = 6;
		cons.gridwidth = 3;
		panel.add(jt_attempts, cons);

		JButton bn_hack = new JButton("Hack");
		cons.gridx = 0;
		cons.gridy = 8;
		cons.gridwidth = 3;
		panel.add(bn_hack, cons);		

		JButton bn_stop = new JButton("Stop");
		cons.gridx = 3;
		cons.gridy = 8;
		cons.gridwidth = 3;
		panel.add(bn_stop, cons);
		bn_stop.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				active = false;
				try {
					hack_thread.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		jl_att = new JLabel("");
		cons.gridx = 0;
		cons.gridy = 12;
		cons.gridwidth = 3;
		panel.add(jl_att, cons);

		jl_password_check = new JLabel("");
		cons.gridx = 0;
		cons.gridy = 10;
		cons.gridwidth = 6;
		panel.add(jl_password_check, cons);
		UserAccountForm.main(args);
		bn_hack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method study
				setUserLoginFrame();	
				active = true;
				password_attempts = 0;
				try {
					minchar = Integer.parseInt(jt_minchar.getText().toString());
					System.out
							.println("minimum character length is " + minchar);
				} catch (NumberFormatException e1) {
					System.out.println("minimum character length is 8");
				}

				try {
					max_attempts = Integer.parseInt(jt_attempts.getText()
							.toString());
					System.out.println("maximum password attempts is "
							+ max_attempts);
				} catch (NumberFormatException e1) {
					System.out.println("maximum password attempts is 2000");
				}

				try {
					maxchar = Integer.parseInt(jt_maxchar.getText().toString());
					System.out
							.println("maximum character length is " + maxchar);
				} catch (NumberFormatException e1) {
					System.out.println("maximum character length is 16");
				}
				getFields();
				runHack();
			}
		});
		return panel;
	}

		
	public static void setUserLoginFrame() {	
		Component[] comps = Frame.getFrames();
		for (Component comp : comps) {
			if (((JFrame) comp).getTitle().equals("User login")) {
				frame = (JFrame) comp;
				break;
			}
		}
	}

	public static void runHack() {
		hack_thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				File file = new File(getClass().getResource("/passwordlist.txt").getFile());
				HackUserAccount.dictionaryAttack(file);
			}
		});
		hack_thread.start();
	}
	
	public static boolean displayPassword(String password, String status){
		System.out.println("Not the same screen");
		endTime = System.currentTimeMillis();
		jl_password_check.setText(status+" password: " + password);
		return true;
	}
	
	public static boolean dictionaryAttack(File file) {
		String password = "N/A";
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(active){
					endTime = System.currentTimeMillis();
					jl_att.setText("password attempt: "
							+ password_attempts + "\n\n time: "
							+ ((endTime - startTime) / 1000) + " seconds");
				}				
			}
		});
		thread.start();
		try {
			Scanner scan = new Scanner(file);
			startTime = endTime = System.currentTimeMillis();
			String user;
			int count = 0;
			while (scan.hasNextLine()) {
				if(!active){
					break;
				}
				if(count < max_attempts){
					password = scan.nextLine();
					displayPassword(password, "wrong");
					if(password.length() >= minchar  && password.length() <= maxchar){
						user = jt_username.getText().toString();
						jtextf.get(0).setText(user);
						jtextf.get(1).setText(password);
						System.out.println(jtextf.get(0).getText().toString()
								+ " Hack number: " + password_attempts + " password: "
								+ password);
						blogin.doClick();
						jtextf.get(0).setText(user);
						jtextf.get(1).setText(password);
						setUserLoginFrame();
						getFields();
						ArrayList<Component> c = getAllComponents(frame);
						if (c.size() != comps.size()) {
							System.out.println("hacked");
							active = false;
							try {
								thread.join();
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							return displayPassword(password, "correct");
						}
						password_attempts++;
					}
					count++;
				}else{
					active = false;
					try {
						thread.join();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					
					return displayPassword(password, "wrong");
				}
			}
			active = false;
		} catch (IOException e) {
			e.printStackTrace();
			active = false;
		} catch (Exception e){
			active = false;
		}finally{
			try {
				thread.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return false;
	}

	public static void getFields() {
		jtextf.clear();
		comps = getAllComponents(frame);
		System.out.println(comps.size());
		for (Component comp : comps) {
			if (comp instanceof JTextField) {
				jtextf.add(((JTextField) comp));
			} else if (comp instanceof JButton) {
				if (((JButton) comp).getText().equals("Login"))
					blogin = (JButton) comp;
			} else if (comp instanceof JLabel) {
				if (!((JLabel) comp).getText().equals("Username")
						|| !((JLabel) comp).getText().equals("Password"))
					jl_status = (JLabel) comp;
			}
		}
	}

	public static ArrayList<Component> getAllComponents(final Container c) {
		Component[] comps = c.getComponents();
		ArrayList<Component> compList = new ArrayList<Component>();
		for (Component comp : comps) {
			compList.add(comp);
			if (comp instanceof Container)
				compList.addAll(getAllComponents((Container) comp));
		}
		return compList;
	}

}
