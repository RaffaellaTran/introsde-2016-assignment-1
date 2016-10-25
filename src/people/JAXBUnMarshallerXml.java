package people;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import people.generated.PeopleType;
import people.generated.PersonType;
import utils.CustomValidationEventHandler;


public class JAXBUnMarshallerXml {

	private String context = "people.generated";
	private String schemaLanguage = "http://www.w3.org/2001/XMLSchema";
	private String schemaName ="people.xsd";
	private Map<Long,Object> personList;
	
	
	public void unMarshall(File xmlDocument){
		
		try {
			
			JAXBContext jaxbContext = JAXBContext.newInstance(context);
			
			/*
			 * ====================================================================================================
			 * 	runs instruction 2 based on Lab 4 un-marshalling using JAXB and generated classes with JAXB XJC 
			 * ====================================================================================================
			 */
			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			SchemaFactory schemaFactory = SchemaFactory.newInstance(schemaLanguage);
			Schema schema = schemaFactory.newSchema(new File(schemaName));
			unMarshaller.setSchema(schema);
			
			CustomValidationEventHandler validationEventHandler = new CustomValidationEventHandler();
			unMarshaller.setEventHandler(validationEventHandler);
			
			@SuppressWarnings("unchecked")
			JAXBElement<PeopleType> peopleElement = (JAXBElement<PeopleType>) unMarshaller.unmarshal(xmlDocument); 
			
			PeopleType people = peopleElement.getValue();
			
			personList = new HashMap<Long,Object>();
			for(int i=0; i<people.getPerson().size(); i++){
				PersonType person = people.getPerson().get(i); //tag person
				String birthdateFormat = xmlGregorianCalendartoString(person.getBirthdate());
				String lastupdateFormat = xmlGregorianCalendartoString(person.getHealthprofile().getLastupdate());
				
				personList.put(person.getId(), 
							   person.getLastname() + ", " + person.getFirstname() + 
							   " born "+ birthdateFormat + 
							   ". Has an weight of " + person.getHealthprofile().getWeight() + 
							   " kg, a height of " + person.getHealthprofile().getHeight()+" , a BMI of "+person.getHealthprofile().getBmi()+
							   " and the last update was "+lastupdateFormat+".");
				
			}
			
			System.out.println();
			System.out.println("------------------ Un-Marshalling from XML ----------------");
			System.out.println("Output from XML File: ");
			printingXml(personList);
			System.out.println();
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void printingXml(Map personList) {
		//Get a set of the entries
		Set set = personList.entrySet();
		//Get an interator
		Iterator iterator = set.iterator();
	    while (iterator.hasNext()) {
	        Map.Entry pair = (Map.Entry)iterator.next();
	        System.out.println("Person with id " + pair.getKey() + ": " + pair.getValue());
	    }
	}
	
	public String xmlGregorianCalendartoString(XMLGregorianCalendar xmlBirthdate){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		GregorianCalendar gc = xmlBirthdate.toGregorianCalendar();
		String stringBirthdate = sdf.format(gc.getTime());
		return stringBirthdate;
	}
	
	public static void main(String args[]){
		String xmlDocument = "peopleNew_xjc.xml";
		JAXBUnMarshallerXml jaxbUnmarshaller = new JAXBUnMarshallerXml();
		
		jaxbUnmarshaller.unMarshall(new File(xmlDocument));
	}
	
}