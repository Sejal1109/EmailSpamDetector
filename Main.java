package java;
import java.io.*;
import java.util.*;

public class Main{

public static void main(String[] args) {
	if(args.length < 2){
		System.err.println("Usage: java WordCounter <inputDir>");
		System.exit(0);
	}
	File dataDir = new File(args[0]);
	File dataDir2 = new File(args[1]);
	WordCounter wordCounter = new WordCounter();
	Test test = new Test();
	
	try{
		wordCounter.parseFile(dataDir);
		wordCounter.outputHamWordCount(new File("ham.txt"));
		wordCounter.outputSpamWordCount(new File("spam.txt"));
		wordCounter.calculateProb();
		test.parseFile(dataDir2);
		System.out.println(test.list);

		//wordCounter.printProb();

	}catch(FileNotFoundException e){
		System.err.println("Invalid input dir: " + dataDir.getAbsolutePath());
		e.printStackTrace();
	}catch(IOException e){
		e.printStackTrace();
	}
	
}
}