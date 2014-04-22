
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import security.User;
public class UserAccountForm {
	static JFrame frame;
	public static void main(String[] args){
		frame = new JFrame("User login");		
		UserLoginPanel userlogin = new UserLoginPanel();
		show(userlogin);
	}
	
	public static void show(JPanel panel){
		frame.getContentPane().add(panel);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.gray);
		frame.setSize(400, 500);
	}
	
	public static class UserLoginPanel extends JPanel implements ActionListener{
		static final long serialVersionUID = 1L;
		JButton bn_login, bn_register;
		JTextField jt_username, jt_password;
		JLabel jl_error;
		GridBagConstraints cons;
		String page, status_message;
		static ArrayList<String> login_attempts = new ArrayList<String>();
		
		public UserLoginPanel(){
			super(new GridBagLayout());
			status_message = "";
			loginView();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource().equals(bn_login)){
				status_message = "";
				//if the user is on the login page and click the login button, then check user credentials else pass show the register page on click
				if(page.equals("login")){
					String username = jt_username.getText().toLowerCase();
					String password = jt_password.getText().toString();
					int num_attempts = 0;
					if(login_attempts.contains(username)){
						for(String attempts : login_attempts){
							if(attempts.equals(username)){
								num_attempts++;
							}
						}
					}
					if(num_attempts < 3){
						String[] result = User.verifyUser(username, password);
						if(result[0].equals("true")){
							User.setCurrentUser(username);
							welcomeView();
						}else{
							login_attempts.add(username);
							displayStatusMessage(result[1]);
						}
					}else{
						System.out.println("going to be locked");
						User.setUserAccountStatus(username, "locked");
						displayStatusMessage(username+" has been locked because\nof numerous login attempt");
					}
				}else{
					loginView();
				}
			}else if(e.getSource().equals(bn_register)){
				status_message = "";
				if(page == "register"){		
					String username = jt_username.getText().toLowerCase();	
					User oshane = new User(username);
					oshane.saveUser();
				}else{
					registerView();
				}
			}
		}
		
		private void displayStatusMessage(String message) {
			// TODO Auto-generated method stub
			this.status_message = message;
			if(page.equals("login")){
				loginView();
			}else if(page.equals("register")){
				registerView();
			}
		}
		


		public void loginView(){
			removeAll();
			cons = new GridBagConstraints();
			setBackground(Color.gray);
			page = "login";
			cons.fill = GridBagConstraints.HORIZONTAL;
			cons.insets = new Insets(5, 5, 5, 5);	
			
			JLabel jl_username = new JLabel("Username");
			cons.gridx = 0;
			cons.gridy = 0;
			cons.gridwidth = 3;
			add(jl_username, cons);	
			
			jt_username = new JTextField(25);
			cons.gridx = 3;
			cons.gridy = 0;
			cons.gridwidth = 3;
			add(jt_username, cons);
			
			JLabel jl_password = new JLabel("Password");
			cons.gridx = 0;
			cons.gridy = 2;
			cons.gridwidth = 3;
			add(jl_password, cons);	
			
			jt_password = new JTextField(25);
			cons.gridx = 3;
			cons.gridy = 2;
			cons.gridwidth = 3;
			add(jt_password, cons);		
			
			bn_login = new JButton("Login");
			cons.gridx = 3;
			cons.gridy = 4;
			cons.gridwidth = 3;
			add(bn_login, cons);
			
			bn_register = new JButton("Register");
			cons.gridx = 0;
			cons.gridy = 4;
			cons.gridwidth = 3;
			add(bn_register, cons);
			
			jl_error = new JLabel(status_message);
			cons.gridx = 0;
			cons.gridy = 6;
			cons.gridwidth = 6;
			add(jl_error, cons);
			
			bn_login.addActionListener(this);
			bn_register.addActionListener(this);
			revalidate();
			repaint();
		}

		public void registerView(){
			removeAll();
			cons = new GridBagConstraints();
			setBackground(Color.gray);
			page = "register";
			cons.fill = GridBagConstraints.HORIZONTAL;
			cons.insets = new Insets(5, 5, 5, 5);	
			
			JLabel jl_username = new JLabel("Username");
			cons.gridx = 0;
			cons.gridy = 0;
			cons.gridwidth = 3;
			add(jl_username, cons);	
			
			jt_username = new JTextField(25);
			cons.gridx = 3;
			cons.gridy = 0;
			cons.gridwidth = 3;
			add(jt_username, cons);
			
			bn_login = new JButton("Login");
			cons.gridx = 0;
			cons.gridy = 4;
			cons.gridwidth = 3;
			add(bn_login, cons);
			
			bn_register = new JButton("Register");
			cons.gridx = 3;
			cons.gridy = 4;
			cons.gridwidth = 3;
			add(bn_register, cons);
			
			jl_error = new JLabel(status_message);
			cons.gridx = 0;
			cons.gridy = 6;
			cons.gridwidth = 6;
			add(jl_error, cons);
			
			bn_login.addActionListener(this);
			bn_register.addActionListener(this);
			revalidate();
			repaint();
		}
		
		public void welcomeView(){
			removeAll();
			cons = new GridBagConstraints();
			setBackground(Color.gray);
			page = "login";
			cons.fill = GridBagConstraints.HORIZONTAL;
			cons.insets = new Insets(5, 5, 5, 5);	
			
			JLabel jl_user = new JLabel("<html><p>Welcome, "+User.getCurrentUser()+"</p></html>");
			cons.gridx = 3;
			cons.gridy = 0;
			cons.gridwidth = 3;
			add(jl_user, cons);	
			
			revalidate();
			repaint();
		}
	}
}
