package question3;

import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Got it!";
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String addMessage(String responsive) throws ParserConfigurationException, IOException {
		
		//String responsive is just what the Inputed XML is called
		boolean isValid; //A boolean containing if the XML is structured correctly or not
		DefaultHandler handler = new SAXHandler(); //Instantiate our SAXHandler

		try {
			SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser(); //Instantiate the XML SAXParser
			InputStream stream = new ByteArrayInputStream(responsive.getBytes("UTF-8")); //Translate the XML to UTF-8
			saxParser.parse(stream, handler); //Parse the stream XML with our handler
			isValid = true; //If it parses successfully, and is therefore valid, then true
		} catch (SAXException e) {
			// not valid XML String
			isValid = false;
		}
		String SiteID = SAXHandler.SiteID;
		String Command = SAXHandler.Command;

		//These are the conditions specified in the question. As for the status code, I could only print them as
		//as strings as I am unsure rather the question wanted me to create my own HTTP response codes
		//or output the response as a string. To output an official HTTP response code, the return type of
		//this method would change from String to Response.
		if (isValid && SiteID.equals("DUB") && Command.equals("DEFAULT")) {
			return "Status Code: 1";
		} else if (!SiteID.equals("DUB")){
			return "Status Code: -2";
		} else if (!Command.equals("DEFAULT")){
			return "Status Code: -1";
		} else {
			return "Status Code: 0";
		}
	}

	public static class SAXHandler extends DefaultHandler {	
		   // Local variables to store data that found in the XML document
		   public static String SiteID = "";
		   public static String Command = "";
		   // Buffer to collect data from // the "characters" SAX event.
		   private CharArrayWriter contents = new CharArrayWriter();
		   // Override the methods of the DefaultHandler class to get notification of SAX Events.

		   public void startElement( String namespaceURI,
		              String localName,
		              String qName,
		              Attributes attr ) throws SAXException {
			   
				int attributeLength = attr.getLength();
				if ("Declaration".equals(qName)) {
					Command=attr.getValue("Command");
				}
		      contents.reset();
		   }
		   public void endElement( String namespaceURI,
		              String localName,
		              String qName ) throws SAXException {
		      if ( localName.equals("SiteID") ) {
		    	  SiteID = contents.toString();
		      }
		      if ( qName.equals("SiteID") ) {
		    	  SiteID = contents.toString();
		      }
		   }
		   public void characters( char[] ch, int start, int length )
		                  throws SAXException {
		      contents.write( ch, start, length );
		   }

	}
}
