package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public class WordCounter {

	private Map<String, Integer> trainHamFreq;
	private Map<String, Integer> trainSpamFreq;
	private int spamFileNum = 0, hamFileNum = 0;
	private Map<String, Double> probabiltySpam;
	private Map<String, Double> probabiltyHam;
	public static Map<String, Double> probabiltyFinalSpam;
	public static ObservableList<TestFile> probs = FXCollections.observableArrayList();

	private List<String> totWords = new ArrayList<String>();

	public WordCounter() {
		trainHamFreq = new TreeMap<>();
		trainSpamFreq = new TreeMap<>();
		probabiltySpam = new TreeMap<>();
		probabiltyHam = new TreeMap<>();
		probabiltyFinalSpam = new TreeMap<>();
	}

	public void parseFile(File file) throws IOException {
		System.out.println("Starting parsing the file:" + file.getAbsolutePath());

		if (file.isDirectory()) {
			//parse each file inside the directory
			File[] content = file.listFiles();
			for (File current : content) {
				parseFile(current);
			}
		} else if (file.getParent().equals("C:\\csci2020u_assignment1\\assignment1_data\\data\\train\\spam")) {
			spamFileNum++;
			Scanner scanner = new Scanner(file);
			List<String> wordList = new ArrayList<String>();
			// scanning token by token
			while (scanner.hasNext()) {
				String token = scanner.next().toLowerCase();
				//String token2 = token.toLowerCase();
				if (isValidWord(token)) {
					if (!wordList.contains(token)) {
						if (!totWords.contains(token))
							totWords.add(token);
						wordList.add(token);
						countSpamWord(token);
					}
				}
			}
			scanner.close();
		} else {
			hamFileNum++;
			Scanner scanner = new Scanner(file);
			List<String> wordList = new ArrayList<String>();
			// scanning token by token
			while (scanner.hasNext()) {
				String token = scanner.next().toLowerCase();
				//String token2 = token.toLowerCase();
				if (isValidWord(token)) {
					if (!wordList.contains(token)) {
						if (!totWords.contains(token))
							totWords.add(token);
						wordList.add(token);
						countHamWord(token);
					}
				}
			}
		}
	}

	public void calculateProb() {
		for (Map.Entry<String, Integer> x : trainSpamFreq.entrySet()) {
			String word = x.getKey();
			Integer value = x.getValue();
			Double p = (double) value / spamFileNum;
			probabiltySpam.put(word, p);
		}
		for (Map.Entry<String, Integer> x : trainHamFreq.entrySet()) {
			String word = x.getKey();
			Integer value = x.getValue();
			Double p = (double) value / hamFileNum;
			probabiltyHam.put(word, p);
		}
		for (String word : totWords) {
			double hamVal;
			if (probabiltyHam.containsKey(word)) {
				hamVal = probabiltyHam.get(word);
			} else {
				hamVal = 0;
			}
			double spamVal;
			if (probabiltySpam.containsKey(word)) {
				spamVal = probabiltySpam.get(word);
			} else {
				spamVal = 0;
			}

			double prob = spamVal / (spamVal + hamVal);
			probabiltyFinalSpam.put(word, prob);
		}
	}

	protected boolean isValidWord(String word) {
		String allLetters = "^[a-zA-Z]+$";
		// returns true if the word is composed by only letters otherwise returns false;
		return word.matches(allLetters);
	}

	private void countHamWord(String word) {
		if (trainHamFreq.containsKey(word)) {
			int previous = trainHamFreq.get(word);
			trainHamFreq.put(word, previous + 1);
		} else {
			trainHamFreq.put(word, 1);
		}
	}

	private void countSpamWord(String word) {
		if (trainSpamFreq.containsKey(word)) {
			int previous = trainSpamFreq.get(word);
			trainSpamFreq.put(word, previous + 1);
		} else {
			trainSpamFreq.put(word, 1);
		}
	}

	// public void printProb(){
	// 	for(Map.Entry<String, Double> x: probabiltyFinalSpam.entrySet()){
	// 		String word = x.getKey();
	// 		Double val = x.getValue();
	// 		System.out.println(word + ":" +val);
	// 	}
	// }

	public void outputHamWordCount(File output) throws IOException {
		System.out.println("Saving word counts to file:" + output.getAbsolutePath());
		System.out.println("Total words:" + trainHamFreq.keySet().size());
		int minCount = 2;
		if (!output.exists()) {
			output.createNewFile();
			if (output.canWrite()) {
				PrintWriter fileOutput = new PrintWriter(output);

				Set<String> keys = trainHamFreq.keySet();
				Iterator<String> keyIterator = keys.iterator();

				while (keyIterator.hasNext()) {
					String key = keyIterator.next();
					int count = trainHamFreq.get(key);
					// testing minimum number of occurances
					if (count >= minCount) {
						fileOutput.println(key + ": " + count);
					}
				}

				fileOutput.close();
			}
		} else {
			System.out.println("Error: the output file already exists: " + output.getAbsolutePath());
		}

	}

	public void outputSpamWordCount(File output) throws IOException {
		System.out.println("Saving word counts to file:" + output.getAbsolutePath());
		System.out.println("Total words:" + trainSpamFreq.keySet().size());
		int minCount = 2;
		if (!output.exists()) {
			output.createNewFile();
			if (output.canWrite()) {
				PrintWriter fileOutput = new PrintWriter(output);

				Set<String> keys = trainSpamFreq.keySet();
				Iterator<String> keyIterator = keys.iterator();

				while (keyIterator.hasNext()) {
					String key = keyIterator.next();
					int count = trainSpamFreq.get(key);
					// testing minimum number of occurances
					if (count >= minCount) {
						fileOutput.println(key + ": " + count);
					}
				}

				fileOutput.close();
			}
		} else {
			System.out.println("Error: the output file already exists: " + output.getAbsolutePath());
		}

	}


	//Working on Test directory
	public void parseFile2(File file) throws IOException {
		System.out.println("Starting parsing the file:" + file.getAbsolutePath());

		if (file.isDirectory()) {
			//parse each file inside the directory
			File[] content = file.listFiles();
			for (File current : content) {
				parseFile(current);
			}
		} else if (file.getParent().equals("test\\spam")) {
			Scanner scanner = new Scanner(file);
			// scanning token by token
			List<String> wordsInFile = new ArrayList<String>();
			while (scanner.hasNext()) {
				String token = scanner.next().toLowerCase();
				//String token2 = token.toLowerCase();
				if (isValidWord(token)) {
					if (!wordsInFile.contains(token)) {
						wordsInFile.add(token);
					}
				}
			}
			String temp = "Spam";
			spamFileProb(wordsInFile, file, temp);
			wordsInFile.clear();
			scanner.close();
		} else {
			Scanner scanner = new Scanner(file);
			// scanning token by token
			List<String> wordsInFile = new ArrayList<String>();
			while (scanner.hasNext()) {
				String token = scanner.next().toLowerCase();

				if (isValidWord(token)) {
					if (!wordsInFile.contains(token)) {
						wordsInFile.add(token);
					}
				}
			}
			String temp = "Ham";
			spamFileProb(wordsInFile, file, temp);
			wordsInFile.clear();
		}
	}

	public static void spamFileProb(List<String> wordsList, File file, String t) {
		Double n = 0.0;
		for (String word : wordsList) {
			if (probabiltyFinalSpam.containsKey(word)) {
				double val = probabiltyFinalSpam.get(word);
				n += (Math.log(1 - val) - Math.log(val));
			} else {
				n += 0;
			}
		}
		double probFile = 1 / (Math.pow(Math.E, n));
		addProbability(file.getName(), probFile, t);
	}

	public static void addProbability(String x, double y, String z) {
		probs.add(new TestFile(x, y, z));
	}

	public static ObservableList<TestFile> getAllProb() {
		return probs;
	}
}
