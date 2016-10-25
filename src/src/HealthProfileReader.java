package src;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class HealthProfileReader {

	Document doc;
	XPath xpath;

	/*
	 * creating a Java DOM XML Parser and parsing XML with a Java DOM Parser
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void loadXML() throws ParserConfigurationException, SAXException,
			IOException {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory
				.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		doc = builder.parse("people.xml");
		getXPathObj();
	}

	
	/*
	 * creating an XPath Object
	 * 
	 * @return
	 */
	public XPath getXPathObj() {
		XPathFactory factory = XPathFactory.newInstance();
		xpath = factory.newXPath();
		return xpath;
	}

	
	/*
	 * given person-->return weight
	 * 
	 * @param firstname
	 * @param lastname
	 * @return
	 * @throws XPathExpressionException
	 */
	public String getWeight(Long personID) throws XPathExpressionException {

		String id_string = String.format("%04d",personID);
		XPathExpression expr = xpath.compile("/people/person[@id='" + id_string
				+ "']/healthprofile/weight/text()");
		String weight = (String) expr.evaluate(doc, XPathConstants.STRING);
		return weight;
	}

	
	/*
	 * given person-->return height 
	 * 
	 * @param firstname
	 * @param lastname
	 * @return
	 * @throws XPathExpressionException
	 */
	public String getHeight(Long personID) throws XPathExpressionException {
		
		String id_string = String.format("%04d", personID);
		XPathExpression expr = xpath.compile("/people/person[@id='" + id_string
				+ "']/healthprofile/height/text()");
		String height = (String) expr.evaluate(doc, XPathConstants.STRING);
		return height;
	}
	
	
	/*
	 * print all people with all detail
	 * 
	 * @param weight
	 * @param condition
	 * @throws XPathExpressionException
	 */
	public void printAllPeople() throws XPathExpressionException {

		XPathExpression expr = xpath.compile("/people/person");
		NodeList nodeListPerson = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		for (int i = 0; i < nodeListPerson.getLength(); i++) {
			Node person = nodeListPerson.item(i);
			Long personID = Long.valueOf(person.getAttributes().item(0).getNodeValue());
			System.out.println("Person id n°: " + String.format("%04d",personID));
			iterateNode(person, personID);
			System.out.println();
		}
	}

	
	public void iterateNode(Node node, Long personID) throws XPathExpressionException {
	
		NodeList nodeList = node.getChildNodes();
		for (int k = 0; k < nodeList.getLength(); k++) {
			Node currentNode = nodeList.item(k);
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				if (currentNode.getNodeName().equals("healthprofile")) {
					// ---> this NOT will print healthprofile tag
				}else if (currentNode.getNodeName().equals("weight")) {
					System.out.println(currentNode.getNodeName() + ": " + getWeight(personID));
				} else if (currentNode.getNodeName().equals("height")) {
					System.out.println(currentNode.getNodeName() + ": " + getHeight(personID));
				} else {
					System.out.println(currentNode.getNodeName() + ": "
							+ currentNode.getFirstChild().getNodeValue());
				}
				iterateNode(currentNode,personID);
			}
		}
	}
	

	/*
	 * given id-> print healthprofile
	 * 
	 * @param idPerson
	 * @return
	 * @throws XPathExpressionException
	 */
	public Node getHProfileByIDPerson(Long idPerson)
			throws XPathExpressionException {

		String id_string = String.format("%04d", idPerson);
		XPathExpression expr = xpath.compile("/people/person[@id='" + id_string
				+ "']/healthprofile");
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		System.out.println("Person's " + node.getNodeName()
				+ " with id " + idPerson);
		iterateNode(node, idPerson);
		return node;
	}

	
	/*
	 * print people that satisfy a condition
	 * 
	 * @param weight
	 * @param condition
	 * @throws XPathExpressionException
	 */
	public void printPeopleByCondition(Double weight, String condition)
			throws XPathExpressionException {

		XPathExpression expr = xpath
				.compile("/people/person[healthprofile/weight " + condition
						+ "'" + weight + "']");
		NodeList nodeListPerson = (NodeList) expr.evaluate(doc,
				XPathConstants.NODESET);
		System.out.println("People that fulfill that condition (weight " + condition + " " + weight + ")");
		for (int i = 0; i < nodeListPerson.getLength(); i++) {
			Node person = nodeListPerson.item(i);
			Long personID = Long.valueOf(person.getAttributes().item(0).getNodeValue());
			System.out.println("Person id n°: " + String.format("%04d",personID));
			iterateNode(person,personID);
			System.out.println();
		}
	}
	
	
	public static void main(String args[]) throws ParserConfigurationException,
			SAXException, IOException, XPathExpressionException, DOMException,
			ParseException {

		HealthProfileReader hpReader = new HealthProfileReader();
		hpReader.loadXML();

		int argCount = args.length;
		
		if (argCount == 0) {
			System.out.println("I cannot find a person inside the people.xml file.");
		} else {
			String method = args[0];
			if (method.equals("printAllPeople")) {
				/*
				 			runs instruction 2 based on Lab 3 prints all people in the list with details 
				 */
				System.out.println();
				System.out.println("List of all the person with details: ");
				hpReader.printAllPeople();
			} else if (method.equals("getHProfileByIDPerson")) {
				/*
				 * 	runs instruction 3 based on Lab 3 with id=5
				 */
				System.out.println();
				Long personId=0L;
				try{
					personId = Long.parseLong(args[1]);
					hpReader.getHProfileByIDPerson(personId);
				}catch(Exception ex){
					System.out.println("Person with " + personId + " doesn't exist into xml file. You will try to use 1 as personID default.");
					hpReader.getHProfileByIDPerson(new Long(1));
				}				
			}  else if (method.equals("printPeopleByCondition")) {
				/*
				 *	runs instruction 4 based on Lab 3 with weight is 90 and condition is > 
				 */
				System.out.println();
				Double weight=0.0;
				String condition="";
				try{
					weight = Double.parseDouble(args[1]);
					condition = args[2];
					hpReader.printPeopleByCondition(weight, condition);
				}catch(Exception ex){
					System.out.println("Person that has an weight of " + weight + " kg. doesn't exist into xml file. You will find the people have an weight more than 60.");
					hpReader.printPeopleByCondition(new Double(60), condition);
				}
				
			} else {
				System.out.println("The system did not find the method '" + method + "'");
			}
		}
	}
}