package com.osoobe.secodem.util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.osoobe.secodem.util.StringGenerator.Mode;
public class DictionaryList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//generate 1000 words
		DictionaryList.generateFile(8, 12, 4000);
		DictionaryList.generateFile(13, 20, 1000);
	}
	
	public static File getFile(int minchar, int maxchar, int num_words){
		String filename = "assets/data/dictionarylist"+minchar+"-"+maxchar+"-"+num_words+".txt";
		System.out.println("Getting dictionary list "+filename);
		File file = new File(filename);
		if(file.exists())
			return file;
		generateFile(minchar, maxchar, 2000);
		return getFile(minchar, maxchar, 2000);
	}
	
	public static File getFile(String filename){
		File file = new File(filename);
		if(file.exists())
			return file;
		return null;
	}
	
	public static void generateFile(int minchar, int maxchar, int num_words){
		String content = "", filename = "assets/data/dictionarylist"+minchar+"-"+maxchar+"-"+num_words+".txt";
		System.out.println("Generating dictionary list "+filename);
		try{
			for(int i = 0; i < num_words; i++){
				content += StringGenerator.generateRandomString(minchar, maxchar, Mode.ALPHANUMERIC)+"\n";
			}
			File file = new File(filename);
			if(!file.exists())
				file.createNewFile();
			else{
				file.delete();
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			System.out.println("Finsih create file "+filename);
		}catch(IOException e){
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
