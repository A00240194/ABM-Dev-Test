package question1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LOCparser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String text = "UNA:+.? '\r\n" + 
				"UNB+UNOC:3+2021000969+4441963198+180525:1225+3VAL2MJV6EH9IX+KMSV7HMD+CUSDECUIE++1++1'\r\n" + 
				"UNH+EDIFACT+CUSDEC:D:96B:UN:145050'\r\n" + 
				"BGM+ZEM:::EX+09SEE7JPUV5HC06IC6+Z'\r\n" + 
				"LOC+17+IT044100'\r\n" + 
				"LOC+18+SOL'\r\n" + 
				"LOC+35+SE'\r\n" + 
				"LOC+36+TZ'\r\n" + 
				"LOC+116+SE003033'\r\n" + 
				"DTM+9:20090527:102'\r\n" + 
				"DTM+268:20090626:102'\r\n" + 
				"DTM+182:20090527:102";
		
		List<String> strResults = new ArrayList<>();
		String[] messages = text.split("\\r?\\n"); //Array of type string for the segments we want
		
		for (String i : messages) { //here we loop through every element of the splitted array
		    if (i.startsWith("LOC")) {
		        strResults.add(i); //and we add the elements starting with LOC to our arrayList
		    }
		}
		String[] LOCs = strResults.toArray(new String[strResults.size()]); //convert this arrayList to an array for the next step
		
		strResults.clear(); //empty this arrayList as we can reuse it later
		for(String i:LOCs) //now loop through all elements (starting with LOC)
		{
			String[] elements = i.split("\\+"); //split up each line by the +, and form an array out of each line
			
			String firstElement = elements[elements.length-2]; //get the first element we went after the LOC string
			
			String secondElement = elements[elements.length-1]; //get the second element
			String secNoAp = secondElement.replaceAll("'", ""); //remove the apostrophe

			String firstAndSecond = firstElement+ " & " +secNoAp; 
			strResults.add(firstAndSecond); //add both of these strings to the one string and add them to our arrayList
		}
		String[] FirstAndSecondElements = strResults.toArray(new String[strResults.size()]); //put the array of all first & second elements into an array as requested
		System.out.println(Arrays.toString((FirstAndSecondElements)));
	}

}
