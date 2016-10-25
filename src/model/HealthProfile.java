package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import adapter.DateAdapter;
import utils.RandomNumberHelper;

@XmlRootElement(name="healthprofile")
@XmlType(propOrder= {"lastUpdate", "weight", "height", "BMI"})
@XmlAccessorType(XmlAccessType.FIELD)

public class HealthProfile {
	@XmlElement(name="lastupdate")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date lastUpdate;

	private double weight; 		// in kg
	private double height; 		// in m
	
	public HealthProfile(double weight, double height, String lastUpdate) {
		this.setLastUpdate(lastUpdate);
		this.weight = weight;
		this.height = height;
		
	}

	public HealthProfile() {
		this.weight = 85.5;
		this.height = 1.72;
		setLastUpdate();
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	@XmlElement(name="bmi")
	public double getBMI() {
		double bmi = this.weight/(Math.pow(this.height, 2));
		bmi = Double.parseDouble( String.format("%.2f", bmi).replace(",",".") );
		return bmi;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(){
		setLastUpdate(RandomNumberHelper.getRandomDate());
	}
	
	public void setLastUpdate(String lastUpdate) {
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date date = dateFormat.parse(lastUpdate);
			this.lastUpdate = date;
		}catch(ParseException ex){
			ex.printStackTrace();
		}
	}

	@Override
	public String toString(){
		return "HealthProfile(" + 
					"\tlastupdate: " + this.lastUpdate + 
					"\tweight: " + this.weight + 
					"\theight: " + this.height + 
					"\tbmi: " + this.getBMI() +")";
	}
	}