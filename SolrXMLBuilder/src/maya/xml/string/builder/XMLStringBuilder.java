package maya.xml.string.builder;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLStringBuilder {

	public static void main(String argv[]) {

		try {

			File fXmlFile = new File("files/FSP940101.xml");
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
				
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			
			DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder icBuilder;
	        
	        // cria??o da string de sa?da
	        icBuilder = icFactory.newDocumentBuilder();
	        Document doc1 = icBuilder.newDocument();
	        Element mainRootElement = doc1.createElement("add");
	        doc1.appendChild(mainRootElement);
	           
	        NodeList nList = doc.getElementsByTagName("DOC");     
	
			for (int temp = 0; temp < nList.getLength(); temp++) {
	
				Node nNode = nList.item(temp);
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	
					Element eElement = (Element) nNode;
					
					String docid = eElement.getElementsByTagName("DOCNO").item(0).getTextContent();
					String date = eElement.getElementsByTagName("DATE").item(0).getTextContent();
					String text = eElement.getElementsByTagName("TEXT").item(0).getTextContent();
					
					System.out.println(docid);
					 mainRootElement.appendChild(getDoc(doc1,docid,date,text));
				}
			}
			
			// output DOM XML to console 
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            DOMSource source = new DOMSource(doc1);
            //StreamResult console = new StreamResult(System.out);
            StreamResult archive = new StreamResult(new File("files/FSP940101Solr.xml"));
            //transformer.transform(source, console);
            transformer.transform(source, archive);
 
            System.out.println("\nXML DOM Created Successfully..");
			
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	private static Node getDoc(Document doc, String id, String date, String text) {
    	Element docElem = doc.createElement("doc");
        docElem.appendChild(getFieldElement(doc, "id", id));
        docElem.appendChild(getFieldElement(doc, "date", date));
        docElem.appendChild(getFieldElement(doc, "text", text));
        return docElem;
    }
 
    // utility method to create text node
    private static Node getFieldElement(Document doc, String atribute, String value) {
        Element field = doc.createElement("text_pt");
        field.setAttribute("name", atribute);
        field.setTextContent(value);
        return field;
    }
}