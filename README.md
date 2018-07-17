# Introduction to Service Design and Engineering (fall 2016) - Assignment 1

In the folder there are 6 packages where you can find theirs classes:
	
- adapter: DateAdapter class;
- dao: PeopleStore class;
- model: HealthProfile and Person classes;
- people: JAXBMarshallerXML and JAXBUnMarshallerXML classes;
- people.generated: the classes generated with JAXB XJC. At the beginning we don't have, it will be generated, for that reason when you start you see some missing imports;
- src: 
 - HealthProfileReader: will run the requests based on Laboratory 3; 
 - JAXBAnnotationsXml: will marshall (to XML) and unmarshall (from XML).This class will create a file named peopleNew.xml with 10 people in the home folder. Then you can read people_new.xml file and print one by one.
  - JAXBMarshallerJson: will marshall (to JSON). It will generate and print a peopleNew.json in the home folder.
- utils: - RandomNumberHelper class has the methods to create random interger / double numbers in the range (min,max]
 - CustomValidationEventHandler class and it use to validate event in JAXBUnMarshallerXML class.

There also are 4 files:
	
- people.xml: is the database.
- people.xsd: is the XML Schema for the XML file of people.
- build.xml: is the ANT build script that compiling source code, running tests, generating documentation directly from the command line. In the ivy folder the compile target will generate and download ivy.jar. Libraries, specified in ivy.xml, will be downloaded and save into lib folder. The generate target will also created the folder people.generated that, using JAXB XJC, there will generate the classes we already explain.
- ivy.xml: is used to specify the dependencies as JAXB API and XJC compiler.

## Request based on Lab 3

## Request 1: Use xpath to implement methods like getWeight and getHeight.

This functions will be used for print the details of all the people or by given the personID it will show his person's healthprofile.
The functions are:
	
	- getWeight: returns weight of a given personID.
	- getHeight: returns height of a given personID.

## Request 2: Make a function that prints all people in the list with details. (args[0]="printAllPeople")

The interateNode function is used to print the personal details.
	
	public void iterateNode(Node node, Long personID) throws XPathExpressionException {
	
		NodeList nodeList = node.getChildNodes();
		for (int k = 0; k < nodeList.getLength(); k++) {
			Node currentNode = nodeList.item(k);
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				if (currentNode.getNodeName().equals("healthprofile")) {
					// ---> this NOT will print healthprofile tag
				} else if (currentNode.getNodeName().equals("weight")) {
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

## Request 3: A function that accepts id as parameter and prints the HealthProfile of the person with id=5

By given personID as a parameter (args[0]= "getHProfileByIDPerson" ,args[1]=5) the getHProfileByIDPerson function will print the HealthProfile detail of that personID.

This is the output of the function:

    /people/person[@id=5]/healthprofile
    
    Person's healthprofile with id 5
    lastupdate: 2016-01-20T18:00:00.000+02:00
    weight: 30
    height: 1.10
    bmi: 24.79
	
## Request 4: A function which accepts a weight>90 and an operator > as parameters and prints people that fulfill that condition

Using the condition weight>90 (args[0]="printPeopleByCondition" ,args[1]=90, args[2]= >) in the printPeopleByCondition function, it will print the people's details that satisfy the condition.

    /people/person[healthprofile/weight>90]
    
    People that fulfill that condition (weight > 90.0)

    Person id n°: 0001
    firstname: George R. R.
    lastname: Martin
    birthdate: 1984-09-20T18:00:00.000+02:00
    lastupdate: 2014-09-20T18:00:00.000+02:00
    weight: 100
    height: 1.70
    bmi: 31.14

    Person id n°: 0004
    firstname: Luigi
    lastname: Doe
    birthdate: 1982-09-29T18:00:00.000+02:00
    lastupdate: 2014-01-20T13:00:00.000+02:00
    weight: 93
    height: 1.76
    bmi: 22.985397512168742

    Person id n°: 0020
    firstname: Anna
    lastname: Camacho
    birthdate: 1987-03-21T18:00:00.000+02:00
    lastupdate: 2014-09-10T18:00:00.000+02:00
    weight: 103
    height: 1.79
    bmi: 32.985397512168742

## Request based on Lab 4

## Request 1: Create the XML schema XSD file for the XML document of people

<?xml version="1.0" encoding="UTF-8"?>

	<xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema">

	<!-- definition of root element -->
	<xsd:element name="people" type="peopleType" />


	<!-- definition of complex elements -->
	<xsd:complexType name="peopleType">
		<xsd:sequence>
			<xsd:element name="person" type="personType" maxOccurs="unbounded" />
		</xsd:sequence>
	</xsd:complexType>

	<xsd:complexType name="personType">
		<xsd:sequence>
			<xsd:element name="firstname" type="stringType" />
			<xsd:element name="lastname" type="stringType" />
			<xsd:element name="birthdate" type="dateType" />
			<xsd:element name="healthprofile" type="healthprofileType" />
		</xsd:sequence>
		<xsd:attribute name="id" type="xsd:long" use="required" />
	</xsd:complexType>

	<xsd:complexType name="healthprofileType">
		<xsd:sequence>
			<xsd:element name="lastupdate" type="dateType" />
			<xsd:element name="weight" type="doubleType" />
			<xsd:element name="height" type="doubleType" />
			<xsd:element name="bmi" type="doubleType" />
		</xsd:sequence>
	</xsd:complexType>

	<xsd:simpleType name="stringType">
		<xsd:restriction base="xsd:string" />
	</xsd:simpleType>

	<xsd:simpleType name="dateType">
		<xsd:restriction base="xsd:dateTime" />
	</xsd:simpleType>

	<xsd:simpleType name="doubleType">
		<xsd:restriction base="xsd:double" />
	</xsd:simpleType>

</xsd:schema>

## Request 2: Write a java application that does the marshalling and un-marshalling using classes generated with JAXB XJC

Using java invocating inizializedDB function i created 10 people. The JABXAnnotationsXML class will do marshall java objects to XML and will produce a peopleNew.xml in home folder. This class also will read the xml file created and print one at a time.

**Marshalling to XML using model classes**

File peopleNew.xml created in the home folder.....
Output: 
	
	
	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	
	<people>
    <person id="4227">
        <firstname>Maria</firstname>
        <lastname>Rossi</lastname>
        <birthdate>19-09-1971</birthdate>
        <healthprofile>
            <lastupdate>27-05-1966</lastupdate>
            <weight>93.62</weight>
            <height>1.88</height>
            <bmi>26.49</bmi>
        </healthprofile>
    </person>
    <person id="6183">
        <firstname>Marco</firstname>
        <lastname>Cosa</lastname>
        <birthdate>02-10-2015</birthdate>
        <healthprofile>
            <lastupdate>04-10-1963</lastupdate>
            <weight>116.66</weight>
            <height>1.95</height>
            <bmi>30.68</bmi>
        </healthprofile>
    </person>
    <person id="9483">
        <firstname>Giovanni</firstname>
        <lastname>Bianchi</lastname>
        <birthdate>20-06-2010</birthdate>
        <healthprofile>
            <lastupdate>26-10-1954</lastupdate>
            <weight>71.29</weight>
            <height>1.58</height>
            <bmi>28.56</bmi>
        </healthprofile>
    </person>
    <person id="5063">
        <firstname>Laura</firstname>
        <lastname>Valentini</lastname>
        <birthdate>27-02-1951</birthdate>
        <healthprofile>
            <lastupdate>06-11-2014</lastupdate>
            <weight>108.99</weight>
            <height>1.66</height>
            <bmi>39.55</bmi>
        </healthprofile>
    </person>
    <person id="6791">
        <firstname>Anna</firstname>
        <lastname>Pucci</lastname>
        <birthdate>18-09-2015</birthdate>
        <healthprofile>
            <lastupdate>13-04-1973</lastupdate>
            <weight>52.41</weight>
            <height>2.38</height>
            <bmi>9.25</bmi>
        </healthprofile>
    </person>
    <person id="3873">
        <firstname>Nicola</firstname>
        <lastname>Dondo</lastname>
        <birthdate>03-02-1964</birthdate>
        <healthprofile>
            <lastupdate>28-10-1966</lastupdate>
            <weight>87.53</weight>
            <height>2.62</height>
            <bmi>12.75</bmi>
        </healthprofile>
    </person>
    <person id="7951">
        <firstname>Giulio</firstname>
        <lastname>Simba</lastname>
        <birthdate>10-11-1969</birthdate>
        <healthprofile>
            <lastupdate>10-07-2008</lastupdate>
            <weight>55.94</weight>
            <height>2.52</height>
            <bmi>8.81</bmi>
        </healthprofile>
    </person>
    <person id="2883">
        <firstname>Valeria</firstname>
        <lastname>Marini</lastname>
        <birthdate>11-08-2011</birthdate>
        <healthprofile>
            <lastupdate>10-09-1985</lastupdate>
            <weight>51.82</weight>
            <height>2.24</height>
            <bmi>10.33</bmi>
        </healthprofile>
    </person>
    <person id="8586">
        <firstname>Chiara</firstname>
        <lastname>Verno</lastname>
        <birthdate>04-09-1987</birthdate>
        <healthprofile>
            <lastupdate>09-01-1952</lastupdate>
            <weight>133.31</weight>
            <height>2.4</height>
            <bmi>23.14</bmi>
        </healthprofile>
    </person>
    <person id="2394">
        <firstname>Andrea</firstname>
        <lastname>Pello</lastname>
        <birthdate>09-06-2004</birthdate>
        <healthprofile>
            <lastupdate>03-04-2012</lastupdate>
            <weight>76.07</weight>
            <height>2.24</height>
            <bmi>15.16</bmi>
        </healthprofile>
    </person>
</people>

**Un-Marshalling from XML using model classes**
Output from XML File: 

 Person with id 4227is Maria Rossi born on the 19-09-1971. The last update was on the 27-05-1966 ,Maria has an weight of 93.62 and height of 93.62 and the bmi is:26.49.

Person with id 6183is Marco Cosa born on the 02-10-2015. The last update was on the 04-10-1963 ,Marco has an weight of 116.66 and height of 116.66 and the bmi is:30.68.

Person with id 9483is Giovanni Bianchi born on the 20-06-2010. The last update was on the 26-10-1954 ,Giovanni has an weight of 71.29 and height of 71.29 and the bmi is:28.56.

Person with id 5063is Laura Valentini born on the 27-02-1951. The last update was on the 06-11-2014 ,Laura has an weight of 108.99 and height of 108.99 and the bmi is:39.55.

Person with id 6791is Anna Pucci born on the 18-09-2015. The last update was on the 13-04-1973 ,Anna has an weight of 52.41 and height of 52.41 and the bmi is:9.25.

Person with id 3873is Nicola Dondo born on the 03-02-1964. The last update was on the 28-10-1966 ,Nicola has an weight of 87.53 and height of 87.53 and the bmi is:12.75.

Person with id 7951is Giulio Simba born on the 10-11-1969. The last update was on the 10-07-2008 ,Giulio has an weight of 55.94 and height of 55.94 and the bmi is:8.81.

Person with id 2883is Valeria Marini born on the 11-08-2011. The last update was on the 10-09-1985 ,Valeria has an weight of 51.82 and height of 51.82 and the bmi is:10.33.

Person with id 8586is Chiara Verno born on the 04-09-1987. The last update was on the 09-01-1952 ,Chiara has an weight of 133.31 and height of 133.31 and the bmi is:23.14.

Person with id 2394is Andrea Pello born on the 09-06-2004. The last update was on the 03-04-2012 ,Andrea has an weight of 76.07 and height of 76.07 and the bmi is:15.16.

Another way to do marshall java objects to XM is using JAXB and generated class with JAXB XJC. This class will produce a people_new_xjc.xml in home folder.

**Marshalling to XML using generated class with JAXB XJC**

	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	<people>
    <person id="1">
        <firstname>Maria</firstname>
        <lastname>Rossi</lastname>
        <birthdate>2009-01-09T16:46:51.009+01:00</birthdate>
        <healthprofile>
            <lastupdate>1978-01-16T21:24:14.022+01:00</lastupdate>
            <weight>129.16</weight>
            <height>2.78</height>
            <bmi>16.71</bmi>
        </healthprofile>
    </person>
    <person id="2">
        <firstname>Marco</firstname>
        <lastname>Cosa</lastname>
        <birthdate>1962-01-17T11:50:15.026+01:00</birthdate>
        <healthprofile>
            <lastupdate>2005-01-22T15:18:13.027+01:00</lastupdate>
            <weight>146.86</weight>
            <height>1.47</height>
            <bmi>67.96</bmi>
        </healthprofile>
    </person>
    <person id="3">
        <firstname>Giovanni</firstname>
        <lastname>Bianchi</lastname>
        <birthdate>1947-01-23T03:52:32.027+01:00</birthdate>
        <healthprofile>
            <lastupdate>1995-01-09T16:17:58.028+01:00</lastupdate>
            <weight>52.71</weight>
            <height>2.83</height>
            <bmi>6.58</bmi>
        </healthprofile>
    </person>
    <person id="4">
        <firstname>Laura</firstname>
        <lastname>Valentini</lastname>
        <birthdate>2004-01-26T20:34:20.028+01:00</birthdate>
        <healthprofile>
            <lastupdate>1988-01-10T19:28:40.028+01:00</lastupdate>
            <weight>107.94</weight>
            <height>1.5</height>
            <bmi>47.97</bmi>
        </healthprofile>
    </person>
    <person id="5">
        <firstname>Anna</firstname>
        <lastname>Pucci</lastname>
        <birthdate>1931-01-24T01:13:47.029+01:00</birthdate>
        <healthprofile>
            <lastupdate>2012-01-17T04:00:55.029+01:00</lastupdate>
            <weight>132.28</weight>
            <height>1.94</height>
            <bmi>35.15</bmi>
        </healthprofile>
    </person>
    <person id="6">
        <firstname>Nicola</firstname>
        <lastname>Dondo</lastname>
        <birthdate>1933-01-20T18:01:05.030+01:00</birthdate>
        <healthprofile>
            <lastupdate>1925-01-22T13:31:16.030+01:00</lastupdate>
            <weight>75.65</weight>
            <height>2.82</height>
            <bmi>9.51</bmi>
        </healthprofile>
    </person>
    <person id="7">
        <firstname>Giulio</firstname>
        <lastname>Simba</lastname>
        <birthdate>1975-01-06T13:29:19.031+01:00</birthdate>
        <healthprofile>
            <lastupdate>1943-01-27T01:46:00.035+01:00</lastupdate>
            <weight>49.53</weight>
            <height>2.55</height>
            <bmi>7.62</bmi>
        </healthprofile>
    </person>
    <person id="8">
        <firstname>Valeria</firstname>
        <lastname>Marini</lastname>
        <birthdate>1985-01-11T07:17:26.036+01:00</birthdate>
        <healthprofile>
            <lastupdate>1982-01-05T09:59:31.036+01:00</lastupdate>
            <weight>70.29</weight>
            <height>2.77</height>
            <bmi>9.16</bmi>
        </healthprofile>
    </person>
    <person id="9">
        <firstname>Chiara</firstname>
        <lastname>Verno</lastname>
        <birthdate>1948-01-19T12:09:49.037+01:00</birthdate>
        <healthprofile>
            <lastupdate>1957-01-19T14:58:15.037+01:00</lastupdate>
            <weight>112.91</weight>
            <height>1.8</height>
            <bmi>34.85</bmi>
        </healthprofile>
    </person>
    <person id="10">
        <firstname>Andrea</firstname>
        <lastname>Pello</lastname>
        <birthdate>1920-01-24T05:32:11.037+01:00</birthdate>
        <healthprofile>
            <lastupdate>1931-01-15T10:11:05.037+01:00</lastupdate>
            <weight>109.9</weight>
            <height>2.86</height>
            <bmi>13.44</bmi>
        </healthprofile>
    </person>
</people>

**Un-Marshalling to XML using generated class with JAXB XJC**
Output from XML File: 

Person with id 1: Rossi, Maria born 29-01-1909. Has an weight of 107.18 kg, a height of 2.82 , a BMI of 13.48 and the last update was 11-01-1916.

Person with id 2: Cosa, Marco born 30-01-1972. Has an weight of 40.78 kg, a height of 1.94 , a BMI of 10.84 and the last update was 26-01-1969.

Person with id 3: Bianchi, Giovanni born 04-01-1984. Has an weight of 74.54 kg, a height of 2.25 , a BMI of 14.72 and the last update was 09-01-1959.

Person with id 4: Valentini, Laura born 15-01-1977. Has an weight of 69.08 kg, a height of 2.79 , a BMI of 8.87 and the last update was 09-01-1987.

Person with id 5: Pucci, Anna born 17-01-1905. Has an weight of 139.45 kg, a height of 2.83 , a BMI of 17.41 and the last update was 04-01-2007.

Person with id 6: Dondo, Nicola born 13-01-1932. Has an weight of 137.45 kg, a height of 2.45 , a BMI of 22.9 and the last update was 21-01-1920.

Person with id 7: Simba, Giulio born 19-01-1989. Has an weight of 98.43 kg, a height of 2.37 , a BMI of 17.52 and the last update was 12-01-1994.

Person with id 8: Marini, Valeria born 23-01-1970. Has an weight of 117.08 kg, a height of 1.68 , a BMI of 41.48 and the last update was 27-01-1976.

Person with id 9: Verno, Chiara born 11-01-1988. Has an weight of 144.39 kg, a height of 2.84 , a BMI of 17.9 and the last update was 26-01-1905.

Person with id 10: Pello, Andrea born 13-01-1917. Has an weight of 144.49 kg, a height of 1.53 , a BMI of 61.72 and the last update was 20-01-1966.

## Request 3: Make your application to convert also JSON

I created 10 people using java and marshall them to JSON. This function will print the content and save to people_new.json in home folder.

**Marshalling to JSON using model classes**

------------------ Marshalling to JSON ----------------

 File peopleNew.json created in the home folder.....
Output: 

	{
  	"person" : [ {
    "firstname" : "Maria",
    "lastname" : "Rossi",
    "birthdate" : "17-05-1966",
    "healthprofile" : {
      "lastupdate" : "10-04-1987",
      "weight" : 145.93,
      "height" : 2.72,
      "bmi" : 19.72
    },
    "id" : 5551
  	}, {
    "firstname" : "Marco",
    "lastname" : "Cosa",
    "birthdate" : "18-02-1953",
    "healthprofile" : {
      "lastupdate" : "09-11-2011",
      "weight" : 104.07,
      "height" : 1.98,
      "bmi" : 26.55
    },
    "id" : 8372
  	}, {
    "firstname" : "Giovanni",
    "lastname" : "Bianchi",
    "birthdate" : "18-11-2014",
    "healthprofile" : {
      "lastupdate" : "25-06-1966",
      "weight" : 107.63,
      "height" : 2.89,
      "bmi" : 12.89
    },
    "id" : 8685
  	}, {
    "firstname" : "Laura",
    "lastname" : "Valentini",
    "birthdate" : "06-09-1961",
    "healthprofile" : {
      "lastupdate" : "14-02-1991",
      "weight" : 34.48,
      "height" : 2.0,
      "bmi" : 8.62
    },
    "id" : 8112
  	}, {
    "firstname" : "Anna",
    "lastname" : "Pucci",
    "birthdate" : "25-03-1988",
    "healthprofile" : {
      "lastupdate" : "21-03-1978",
      "weight" : 81.96,
      "height" : 2.25,
      "bmi" : 16.19
    },
    "id" : 3076
 	 }, {
    "firstname" : "Nicola",
    "lastname" : "Dondo",
    "birthdate" : "24-04-1975",
    "healthprofile" : {
      "lastupdate" : "25-10-1973",
      "weight" : 74.19,
      "height" : 1.63,
      "bmi" : 27.92
    },
    "id" : 2568
  	}, {
    "firstname" : "Giulio",
    "lastname" : "Simba",
    "birthdate" : "09-07-1983",
    "healthprofile" : {
      "lastupdate" : "11-06-2006",
      "weight" : 95.8,
      "height" : 2.57,
      "bmi" : 14.5
    },
    "id" : 1492
  	}, {
    "firstname" : "Valeria",
    "lastname" : "Marini",
    "birthdate" : "22-02-1989",
    "healthprofile" : {
      "lastupdate" : "11-01-2002",
      "weight" : 139.71,
      "height" : 1.47,
      "bmi" : 64.65
    },
    "id" : 9675
  	}, {
    "firstname" : "Chiara",
    "lastname" : "Verno",
    "birthdate" : "04-11-1961",
    "healthprofile" : {
      "lastupdate" : "02-08-2014",
      "weight" : 51.98,
      "height" : 2.06,
      "bmi" : 12.25
    },
    "id" : 5679
 	 }, {
    "firstname" : "Andrea",
    "lastname" : "Pello",
    "birthdate" : "11-10-1964",
    "healthprofile" : {
      "lastupdate" : "13-03-1982",
      "weight" : 117.88,
      "height" : 2.35,
      "bmi" : 21.35
    },
    "id" : 9802
 	 } ]
	}

**How to run it**

First of all, take a look to the build.xml of the project and from the command line run this request that executed the targets "compile" and "generate":

[ant execute.evaluation]

From here, you can run this others requests:

- [ant compile] : execute the targets "init" and "generate";
- [ant execute.printAllPeople] : run instruction 2 based on Lab 3;
- [ant execute.HProfileByIDPerson] : run instruction 3 based on Lab 3;
- [ant execute.printPeopleByCondition] : run instruction 4 based on Lab 3;
- [ant execute.MarshallerAndUnmarshallerXML] : run instruction 2 based on Lab 4;
- [ant execute.MarshallerJSON] : run instruction 3 based on Lab 4;

If you want marshall and unmarshall using classes generated with JAXB XJC. You wil execute the commands:

- [ant execute.JAXBMarshaller] : run instruction 2 based on Lab 4;
- [ant execute.JAXBUnMarshaller] : run instruction 2 based on Lab 4.





