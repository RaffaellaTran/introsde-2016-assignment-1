package people;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import people.generated.HealthprofileType;
import people.generated.PeopleType;
import people.generated.PersonType;
import utils.RandomNumberHelper;

public class JAXBMarshallerXml {

	private String context = "people.generated";
	private people.generated.ObjectFactory factory;

	private static String[] listFirstname = {"Maria", "Marco", "Giovanni", "Laura", "Anna", "Nicola", "Giulio","Valeria","Chiara","Andrea"};
	private static String[] listLastname = {"Rossi", "Cosa", "Bianchi", "Valentini", "Pucci", "Dondo", "Simba","Marini","Verno","Pello"};
	
	public void marshall(File xmlDocument){
		
		try{
			
			/* In the file newPeople.xml there will be create a person's list*/
			factory = new people.generated.ObjectFactory();
			
			PeopleType peopletype = factory.createPeopleType();
			List<PersonType> personList = peopletype.getPerson();
			
			List<PersonType> list = inizializedDB();
			for(int i=0; i<list.size();i++){
				personList.add(list.get(i));
			}
			
			JAXBElement<PeopleType> peopleElement = factory.createPeople(peopletype);
			
			/*
			 runs instruction 2 based on Lab 4 marshalling using JAXB and generated classes with JAXB XJC 
			  */
			JAXBContext jaxbContext = JAXBContext.newInstance(context);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", new Boolean(true));
			System.out.println();
			System.out.println("------------------ Marshalling from XML ----------------");
			System.out.println("File " + xmlDocument + " created in the home folder.....");
			marshaller.marshal(peopleElement, new FileOutputStream(xmlDocument));	// marshalling into a file
			System.out.println();
			System.out.println("Output: ");
			marshaller.marshal(peopleElement, System.out);							// marshalling into the system default output 
			
		}catch(JAXBException ex){
			System.out.println(ex.getMessage());
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public List<PersonType> inizializedDB(){
		
		List<PersonType> listPerson = new ArrayList<PersonType>();
		PersonType person;
		HealthprofileType hprofile;
		long index=1;
		for(int i=0;i<listFirstname.length && i<listLastname.length;i++){
			person = factory.createPersonType();
			person.setId(index);
			person.setFirstname(listFirstname[i]);
			person.setLastname(listLastname[i]);
			person.setBirthdate(randomDateXMLGregorianCalendar());
			hprofile = createHealthProfileDetails();
			person.setHealthprofile(hprofile);
			index++;
			listPerson.add(person);
		}
		return listPerson;
	}
	
	
	public HealthprofileType createHealthProfileDetails(){
		HealthprofileType hprofile = factory.createHealthprofileType();
		hprofile.setLastupdate(randomDateXMLGregorianCalendar());
		hprofile.setWeight(getRandomWeight());
		hprofile.setHeight(getRandomHeight());
		hprofile.setBmi(getBMI(hprofile));
		return hprofile;
	}
	
	
	public double getBMI(HealthprofileType hprofile){
		double bmi = hprofile.getWeight()/(Math.pow(hprofile.getHeight(), 2));
		bmi = Double.parseDouble( String.format("%.2f", bmi).replace(",",".") );
		return bmi;
	}
	
	
	public double getRandomWeight(){
		double result = RandomNumberHelper.getRandomDoubleNumber(30.5, 150);
		return result;
	}
	
	
	public double getRandomHeight(){
		double result = RandomNumberHelper.getRandomDoubleNumber(1.45, 2.90);
		return result;
	}
	
	
	public XMLGregorianCalendar randomDateXMLGregorianCalendar(){
		
		XMLGregorianCalendar xmlcalendar = null;
		GregorianCalendar gcalendar = new GregorianCalendar();
		
		try{
			int year = RandomNumberHelper.getRandomNumber(1900, 2015);
			gcalendar.set(Calendar.YEAR, year);
			int month = RandomNumberHelper.getRandomNumber(1, 12);
			gcalendar.set(Calendar.MONTH, month);
			int day = RandomNumberHelper.getRandomNumber(1, 31);
			gcalendar.set(Calendar.DAY_OF_YEAR, day);
			int hour = RandomNumberHelper.getRandomNumber(0, 23);
			gcalendar.set(Calendar.HOUR_OF_DAY, hour);
			int minute = RandomNumberHelper.getRandomNumber(0, 59);
			gcalendar.set(Calendar.MINUTE, minute);
			int second = RandomNumberHelper.getRandomNumber(0, 59);
			gcalendar.set(Calendar.SECOND, second);
			int millisecond = gcalendar.get(Calendar.MILLISECOND);
			int timezone = gcalendar.get(Calendar.ZONE_OFFSET) / (60 * 60 * 1000);
			
			DatatypeFactory dataTypes = DatatypeFactory.newInstance();
			xmlcalendar = dataTypes.newXMLGregorianCalendar(gcalendar);
			
		}catch(DatatypeConfigurationException ex){
			ex.printStackTrace();
		}
		return xmlcalendar;
	}
	

	public static void main(String[] args) throws DatatypeConfigurationException {
		String xmlDocument = "peopleNew_xjc.xml";
		JAXBMarshallerXml jaxbMarshaller = new JAXBMarshallerXml();
		jaxbMarshaller.marshall(new File(xmlDocument));
	}

}