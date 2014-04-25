import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filepre = "dictionarylist";
		try{
			File file = new File("assets/data/"+filepre+"8-12.txt");
			Scanner scan = new Scanner(file);
			//int counter = 0;
			while(scan.hasNextLine()){
				System.out.println(scan.nextLine());
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
