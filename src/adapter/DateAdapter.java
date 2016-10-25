package adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String,Date>{

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	@Override
	public String marshal(Date v) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("---> marshall");
		return dateFormat.format(v);
	}
	@Override
	public Date unmarshal(String v) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("---> unmarshall");
		return dateFormat.parse(v);
	}

	
}
