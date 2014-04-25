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

public class HackUserAccount {
	static JFrame frame, mframe;
	static ArrayList<JTextField> jtextf = new ArrayList<JTextField>();
	static JButton blogin;
	static JLabel jl_status, jl_password_check;
	static ArrayList<Component> comps;
	static String[] args;
	static JTextField jt_username;
	
	public static void main(String[] args){
		mframe = new JFrame("Hacking Login");
		HackUserAccount.args = args;
		show(hackView());
	}
	

	public static void show(JPanel panel){
		mframe.getContentPane().add(panel);
		mframe.setVisible(true);
		mframe.pack();
		mframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mframe.setBackground(Color.gray);
		mframe.setSize(400, 500);
	}
	
	public static JPanel hackView(){
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
		
		JButton bn_hack = new JButton("Hack");
		cons.gridx = 0;
		cons.gridy = 4;
		cons.gridwidth = 3;
		panel.add(bn_hack, cons);
		
		jl_password_check = new JLabel("");
		cons.gridx = 0;
		cons.gridy = 6;
		cons.gridwidth = 6;
		panel.add(jl_password_check, cons);
		
		bn_hack.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method study
				UserAccountForm.main(args);
				Component[] comps = Frame.getFrames();
				for(Component comp : comps){
					if(((JFrame) comp).getTitle().equals("User login")){
						frame = (JFrame) comp;
						break;
					}					
				}
				getFields();
				runHack();
			}
		} );
		return panel;
	}
	
	
	public static void runHack(){
		String user = jt_username.getText().toString();
		jtextf.get(0).setText(user);
		String filepre = "dictionarylist";
		try{
			File file = new File("assets/data/"+filepre+"8-12.txt");
			Scanner scan = new Scanner(file);
			int counter = 0;
			while(scan.hasNextLine()){
				String password = scan.nextLine();
				jtextf.get(0).setText(password);
				counter++;
				System.out.println("Hack number: "+counter+" password: "+password);
				jl_password_check.setText("password: "+password);
				blogin.doClick();
				ArrayList<Component> c = getAllComponents(frame);
				if(c.size() != comps.size()){		
					System.out.println("Not the same screen");		
					break;
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void getFields(){
		comps = getAllComponents(frame);
		System.out.println(comps.size());
		for(Component comp : comps){
			if(comp instanceof JTextField){
				jtextf.add(((JTextField) comp));
			}else if(comp instanceof JButton){
				if(((JButton) comp).getText().equals("Login"))
					blogin = (JButton) comp;
			}else if(comp instanceof JLabel){
				if(!((JLabel) comp).getText().equals("Username") || !((JLabel) comp).getText().equals("Password"))
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
