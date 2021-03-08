package sample;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.DecimalFormat;

public class TestFile {
	SimpleStringProperty filename;
	SimpleDoubleProperty spamProbability;
	SimpleStringProperty actualClass;
	
	public TestFile(String filename, double spamProbability, String actualClass) {
		this.filename = new SimpleStringProperty(filename);
		this.spamProbability = new SimpleDoubleProperty(spamProbability);
		this.actualClass = new SimpleStringProperty(actualClass);
	}
	public String getFilename(){
		return filename.get();
	}
	public double getSpamProbability(){ 
		return spamProbability.get();
	}
	public String getSpamProbRounded(){
		DecimalFormat df = new DecimalFormat("0.00000");
		return df.format(this.spamProbability);
	}
	public String getActualClass(){
		return actualClass.get();
	} 
	public void setFilename(String value) {
		filename.set(value);
	}
	public void setSpamProbability(double val) {
		spamProbability.set(val);
	}
	public void setActualClass(String value) { 
		actualClass.set(value);
	}
}