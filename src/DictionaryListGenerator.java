import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import util.StringGenerator;
import util.StringGenerator.Mode;
public class DictionaryListGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//generate 1000 words
		DictionaryListGenerator.generateFile("assets/data/dictionarylist8-12.txt", 8, 12, 4000);
		DictionaryListGenerator.generateFile("assets/data/dictionarylist13-20.txt", 13, 20, 1000);
	}
	
	public static void generateFile(String filename, int min_characterlen, int max_characterlen, int num_words){
		String content = "";
		try{
			for(int i = 0; i < num_words; i++){
				content += StringGenerator.generateRandomString(min_characterlen, max_characterlen, Mode.ALPHANUMERIC)+"\n";
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
