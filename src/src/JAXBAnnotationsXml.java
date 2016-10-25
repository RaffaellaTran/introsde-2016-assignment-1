package src;

import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import dao.PeopleStore;
import model.HealthProfile;
import model.Person;
import utils.RandomNumberHelper;

public class JAXBAnnotationsXml {

	private static String xmlDocument = "peopleNew.xml";
	private static PeopleStore people = new PeopleStore();

	private static List<Person> listPerson;
	private static String[] listFirstname = {"Maria", "Marco", "Giovanni", "Laura", "Anna", "Nicola", "Giulio","Valeria","Chiara","Andrea"};
	private static String[] listLastname = {"Rossi", "Cosa", "Bianchi", "Valentini", "Pucci", "Dondo", "Simba","Marini","Verno","Pello"};
	

	public static void inizializedDB() {

		listPerson = new ArrayList<Person>();
		for (int i = 0; i < listFirstname.length && i < listLastname.length; i++) {
			Person person = new Person();
			person.setIdPerson();
			person.setFirstname(listFirstname[i]);
			person.setLastname(listLastname[i]);
			person.setBirthdate();
			HealthProfile hprofile = createNewHealthProfile();
			person.sethProfile(hprofile);
			listPerson.add(person);
			people.getData().add(person);
		}
	}

	public static HealthProfile createNewHealthProfile() {
		HealthProfile hprofile = new HealthProfile();
		hprofile.setLastUpdate();
		hprofile.setWeight(getRandomWeight());
		hprofile.setHeight(getRandomHeight());
		hprofile.getBMI();
		return hprofile;
	}

	public static double getRandomWeight() {
		double result = RandomNumberHelper.getRandomDoubleNumber(30.5, 150);
		return result;
	}

	public static double getRandomHeight() {
		double result = RandomNumberHelper.getRandomDoubleNumber(1.45, 2.90);
		return result;
	}

	public static void main(String args[]) throws Exception {
		
		inizializedDB();
		
		JAXBContext jaxbContext = JAXBContext.newInstance(PeopleStore.class);

		/*
		 * ========================================================================================== 
		 * 			runs instruction 2 based on Lab 4 marshalling using JAXB and classes model 
		 * 	==========================================================================================
		 */
		System.out.println();
		System.out.println("=============== Marshalling to XML ----------------");
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty("jaxb.formatted.output", new Boolean(true));
		System.out.println("File " + xmlDocument + " created in the home folder.....");
		marshaller.marshal(people, new File(xmlDocument)); // marshalling into a file
		System.out.println("Output: ");
		marshaller.marshal(people, System.out); // marshalling into the system default output

		
		
		/*
		 *  ========================================================================================== 
		 * 		runs instruction 2 based on Lab 4 un-marshalling using JAXB and classes model 
		 * 	==========================================================================================
		 */
		System.out.println();
		System.out.println("------------------ Un-Marshalling from XML ----------------");
		System.out.println("Output from XML File: ");
		Unmarshaller um = jaxbContext.createUnmarshaller();
		PeopleStore people = (PeopleStore) um.unmarshal(new FileReader(xmlDocument));
		List<Person> list = people.getData();
	
		for (Person person : list) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String formatBirthdate = dateFormat.format(person.getBirthdate());
			String formatLastUpdate = dateFormat.format(person.gethProfile().getLastUpdate());
			System.out.println("Person with id "+ person.getIdPerson() + "is " + person.getFirstname() +" "+ person.getLastname()+ " born on the "
					+ formatBirthdate + ". The last update was on the "+formatLastUpdate+" ,"+person.getFirstname()+" has an weight of " + person.gethProfile().getWeight() 
					+ " and height of " + person.gethProfile().getWeight() +" and the bmi is:"+person.gethProfile().getBMI()+ ".");
		}

	}

}