package security;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import security.Encryption.TYPE;
import util.StringGenerator;
import util.StringGenerator.Mode;
public class User {
	String userid, password, status;
	static String current_user;
	static File file = new File("assets/data/users.ser");
	
	public User(String userid){
		this.status = "unlocked";
		this.userid = userid;
		password = generatePassword();
		System.out.println(this.userid+" "+" "+password+" "+status);
		password = Encryption.encrypt(TYPE.SHA, this.password);
		System.out.println(this.userid+" "+" "+password+" "+status);
	}
	
	public User(String userid, String password){
		this.userid = userid;
		this.password = password;
	}
		
	public String getUserId(){
		return userid;
	}
	
	private String generatePassword(){
		try {
			return StringGenerator.generateRandomString(8, 16, Mode.ALPHANUMERIC);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void saveUser(){	
		System.out.println("Saving user");
		if(readUser(this.userid) == null){
			System.out.println("Saving user");
			try { 
				if(!file.exists()){
					file.createNewFile();
				}
				FileOutputStream fout = new FileOutputStream(file, true);
				String data = this.userid+","+this.password+","+this.status+"\n";
				System.out.println("Saving user");
				System.out.println(data);
				fout.write(data.getBytes());
				fout.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
	
	public static String readUser(String userinfo){
		try {
			if(!file.exists())
				return null;
			Scanner scan = new Scanner(file);
			String userdata = null, data;
			while(scan.hasNextLine()){
				data = scan.nextLine();
				String[] ud = data.split(userinfo);
				if(ud[0].equals(userinfo)){
					userdata = data;
					break;
				}
			}
			scan.close();
			System.out.println(userdata);
			return userdata;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean checkStatus(String status){
		return this.status == status;
	}
	
	public void lockAccount(){
		this.status = "locked";
	}
	
	public void unlockAccount(){
		this.status = "unlocked";
	}
	
	public void setUserId(String userid){
		this.userid = userid;
	}
	
	public static boolean setUserAccountStatus(String userid, String status){
		try {
			Scanner scan = new Scanner(file);
			String usersdata = "";
			String line = "";
			String[] data;
			while(scan.hasNext()){
				line = scan.next();
				data = line.split(",");
				if(data[0].equals(userid)){
					String[] userdata = data;
					userdata[2] = status;
					line = userdata[0]+","+userdata[1]+","+userdata[2];
				}
				usersdata += line+"\n";
			}
			System.out.println(usersdata);
			scan.close();
			FileOutputStream fout = new FileOutputStream(file);			
			fout.write(usersdata.getBytes());
			fout.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static String[] verifyUser(String userid, String password){
		try{
			String ent_password = Encryption.encrypt(TYPE.SHA, password);
			String userdata = readUser(userid);
			System.out.println(ent_password);
			System.out.println(userdata);
			if(userdata != null){
				if(userdata.contains("locekd")){
					return new String[]{"false", userid+" has been locked"};
				}else if(userdata.contains(ent_password)){
					return new String[]{"true", userid+" user has been verified"};
				}
				return new String[]{"false", "incorrect password or username"};
			}else{
				return new String[]{"false", "no user exists with userid, "+userid};
			}
		}catch(Exception e){
			e.printStackTrace();
			return new String[]{"false", "something went wrong"};
		}
	}
	

	public static String getCurrentUser() {
		// TODO Auto-generated method stub
		return current_user;
	}

	public static void setCurrentUser(String userid) {
		// TODO Auto-generated method stub
		current_user = userid;
	}
}
