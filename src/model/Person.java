package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import utils.RandomNumberHelper;
import adapter.DateAdapter;

@XmlRootElement(name="person")
@XmlType(propOrder={"firstname", "lastname", "birthdate", "hProfile"})
@XmlAccessorType(XmlAccessType.FIELD)

public class Person {
	
	@XmlAttribute(name="id")
	private Long idPerson;
	
	private String firstname;		
	private String lastname;
	
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date birthdate;
	
	@XmlElement(name="healthprofile")
	private HealthProfile hProfile;
	
				
	
	/*
	 * constructor creates a Person object with a particular idPerson, firstname, lastname, hProfile and birthdate
	 * @param idPerson
	 * @param firstname
	 * @param lastname
	 * @param birthdate
	 * @param hProfile
	 */
	public Person(Long idPerson, String firstname, String lastname, String birthdate, HealthProfile hProfile){
		this.setIdPerson(idPerson);
		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setBirthdate(birthdate);
		this.sethProfile(hProfile);
	}
	
	/*
	 * constructor creates a Person object with a particular firstname, lastname and hProfile
	 * @param firstname
	 * @param lastname
	 * @param hProfile
	 */
	public Person(String firstname, String lastname, HealthProfile hProfile) {
		this.setIdPerson();
		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setBirthdate();
		this.sethProfile(hProfile);
	}
	
	/*
	 * constructor creates a Person object with a particular firstname, lastname, birthdate and hProfile
	 * @param firstname
	 * @param lastname
	 * @param birthdate
	 * @param hProfile
	 */
	public Person(String firstname, String lastname, String birthdate, HealthProfile hProfile) {
		this.setIdPerson();
		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setBirthdate(birthdate);
		this.sethProfile(hProfile);
	}
	
	/*
	 * constructor creates a Person object with a particular firstname and lastname
	 * @param firstname
	 * @param lastname
	 */
	public Person(String firstname, String lastname) {
		this.setIdPerson();
		this.firstname = firstname;
		this.lastname = lastname;
		this.setBirthdate();
		this.hProfile=new HealthProfile();
	}
	
	public Person() {
		this.setIdPerson();
		this.firstname="Nuovo";
		this.lastname="Utente";
		this.setBirthdate();
		this.hProfile=new HealthProfile();
	}

	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public HealthProfile gethProfile() {
		return hProfile;
	}
	
	public void sethProfile(HealthProfile hProfile) {
		this.hProfile = hProfile;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(){
		setBirthdate(RandomNumberHelper.getRandomDate());
	}
	
	public void setBirthdate(String birthdateString) {
		try{
			
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date date = dateFormat.parse(birthdateString);
			this.birthdate = date;
			
		}catch(ParseException ex){
			ex.printStackTrace();
		}
	}
	
	public Long getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(){
		this.idPerson = new Long(RandomNumberHelper.getRandomNumber(1, 9999));
	}
	
	public void setIdPerson(Long idPerson) {
		this.idPerson = idPerson;
	}

	@Override
	public String toString(){
		return "Person(id:" + this.idPerson +
					"\tfirstname: " + this.firstname + 
					"\tlastname: " + this.lastname + 
					"\tbirthdate: " + this.birthdate + ")";

	}
}