package java;
import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public class Test extends WordCounter{

	

	public void parseFile(File file) throws IOException{
		System.out.println("Starting parsing the file:" + file.getAbsolutePath());
		
		if(file.isDirectory()){
			//parse each file inside the directory
			File[] content = file.listFiles();
			for(File current: content){
				parseFile(current);
			}
		}
		else if(file.getParent().equals("assignment1_data\\data\\test\\spam")){
			Scanner scanner = new Scanner(file);
			// scanning token by token
			List<String> wordsInFile = new ArrayList<String>();
			while (scanner.hasNext()){
				String token = scanner.next().toLowerCase();
				//String token2 = token.toLowerCase();
				if(isValidWord(token)){
					if(!wordsInFile.contains(token)){
						wordsInFile.add(token);
					}
				}
			}
			String temp = "Spam";
			spamFileProb(wordsInFile, file, temp);
			wordsInFile.clear();
			scanner.close();
		}
		else{
			Scanner scanner = new Scanner(file);
			// scanning token by token
			List<String> wordsInFile = new ArrayList<String>();
			while (scanner.hasNext()){
				String token = scanner.next().toLowerCase();
			
				if(isValidWord(token)){
					if(!wordsInFile.contains(token)){
						wordsInFile.add(token);
					}
				}
			}
			String temp = "Ham";
			spamFileProb(wordsInFile, file, temp);
			wordsInFile.clear();
		}
	}

	public void spamFileProb(List<String> wordsList, File file, String t){
		Double n = 0.0;
		for(String word: wordsList){
			if(probabiltyFinalSpam.containsKey(word)){
				double val = probabiltyFinalSpam.get(word);
				n += (Math.log(1-val) - Math.log(val));	
			}
			else{
				n += 0;
			}
		}
		double probFile = 1/(Math.pow(Math.E, n));
		list.Add(new TestFile(file.getName(), probFile, t));
	}
}
    

