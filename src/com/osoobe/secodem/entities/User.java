package com.osoobe.secodem.entities;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.annotation.Resource;

import com.osoobe.secodem.util.Encryption;
import com.osoobe.secodem.util.StringGenerator;
import com.osoobe.secodem.util.Encryption.TYPE;
import com.osoobe.secodem.util.StringGenerator.Mode;
public class User {
	private String userid, password, status;
	static String current_user;
	
	public User(String userid){
		this.status = "unlocked";
		this.userid = userid;
		this.password = generatePassword();
		this.password = Encryption.encrypt(TYPE.SHA, this.password);
	}
	
	public User(String userid, String password){
		this.status = "unlocked";
		this.userid = userid;
		this.password = password;
		this.password = Encryption.encrypt(TYPE.SHA, this.password);
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
				File file = new File((User.class).getResource("/users.ser").getFile());
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
	
	public static String[] readUser(String userinfo){
		try {
			File file = new File((User.class).getResource("/users.ser").getFile());
			if(!file.exists())
				return null;
			Scanner scan = new Scanner(file);
			String[] userdata = null;
			String line;
			while(scan.hasNextLine()){
				line = scan.nextLine();
				String[] ud = line.split(",");
				if(ud[0].equals(userinfo)){
					userdata = ud;
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
			File file = new File((User.class).getResource("/users.ser").getFile());
			Scanner scan = new Scanner(file);
			String usersdata = "";
			String line;
			String[] data;
			while(scan.hasNextLine()){
				line = scan.nextLine();
				data = line.split(",");
				if(data[0].equals(userid)){
					System.out.println("changing user status");
					System.out.println(line);
					data[2] = status;
					line = data[0]+","+data[1]+","+data[2];
				}
				usersdata += line+"\n";
			}
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
			password.trim();
			String ent_password = Encryption.encrypt(TYPE.SHA, password);
			String[] userdata = readUser(userid);
			System.out.printf("user %s, password %s, %s\n", userid, ent_password, userdata[2]);
			if(userdata != null){
				if((userdata[2].equals("locked")) && (userdata[1].equals(ent_password))){
					return new String[]{"false", userid+" has been locked"};
				}else if(userdata[1].equals(ent_password)){
					return new String[]{"true", userid+" user has been verified"};
				}
				return new String[]{"false", "incorrect password or username"};
			}else{
				return new String[]{"false", "no user exists with userid, "+userid};
			}
		}catch(Exception e){
			//e.printStackTrace();
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
