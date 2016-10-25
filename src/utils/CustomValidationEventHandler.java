package utils;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

public class CustomValidationEventHandler implements ValidationEventHandler {
	
	public boolean handleEvent(ValidationEvent event) {
		
		System.out.println("---> Event: ");
		
		if (event.getSeverity() == ValidationEvent.WARNING) {
			return true;
		}
		if ((event.getSeverity() == ValidationEvent.ERROR)
				|| (event.getSeverity() == ValidationEvent.FATAL_ERROR)) {

			System.out.println("Severity:" + event.getSeverity());
			System.out.println("Validation Error:" + event.getMessage());
			
			System.out.println("Locator: ");
			ValidationEventLocator locator = event.getLocator();
			System.out.println("\tAt line number:" + locator.getLineNumber());
			System.out.println("\tAt column number:" + locator.getColumnNumber());
			
			System.out.println("Unmarshalling Terminated");
			return false;
		}
		return true;
	}

}