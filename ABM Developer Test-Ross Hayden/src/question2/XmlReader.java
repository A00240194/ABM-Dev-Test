package question2;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.xpath.XPathExpression;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class XmlReader {
	
	//we are using a private method to return a document object from
	//a given file
	private static Document getDocument(String XMLfile) throws Exception
    {
		//Declare instance of DocumentBuilderFactory which allows applications to
		//obtain a parser that produces DOM object trees from XML documents
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        
        //Documentbuilder is then instantiated to allow the program to obtain a
        //Document from the xml file
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(XMLfile); //Parse the content of the given input source
        return doc;
    }
	
	//This method will go through the XML paths and locate the nodes we want
	private static List<String> evaluateXMLPath(Document document, String xpathExpression) throws Exception 
    {
        // Create XPathFactory object so we can create XPath objects
        XPathFactory xpathFactory = XPathFactory.newInstance();
         
        // Create XPath object so we can provide syntax to define a certain part of an XML document.
        XPath xpath = xpathFactory.newXPath();
 
        List<String> values = new ArrayList<>();
        try
        {
            // Create XPathExpression object to give us access to the compiled XPath expression
            javax.xml.xpath.XPathExpression expr = xpath.compile(xpathExpression);
             
            // Evaluate expression result on XML document
            NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
             
            //loop through the nodes and add them to our values list
            for (int i = 0; i < nodes.getLength(); i++) {
                values.add(nodes.item(i).getNodeValue());
            }
                 
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return values;
    }

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		//Get DOM Node for XML
        String file= "src/question2/InputDocument.xml";
        Document document = getDocument(file); //making a document object to be equal to the output of Calling getDocument() on our file
         
        String xpathExpression = ""; //Initialize string xpathExpression, this will be the path to the element we are going to in the file 
        
        //Get RefText where RefCode = MWB or = TRV, or = CAR
        xpathExpression = "InputDocument/DeclarationList/Declaration/DeclarationHeader/Reference[contains(@RefCode,'MWB') or"
        		+ " contains(@RefCode,'TRV') or"
        		+ " contains(@RefCode,'CAR')]/RefText/text()";
        System.out.println(evaluateXMLPath(document, xpathExpression) );
	}

}
