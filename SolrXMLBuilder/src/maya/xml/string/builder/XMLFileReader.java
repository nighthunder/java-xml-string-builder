package maya.xml.string.builder;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class XMLFileReader {

  public static void main(String argv[]) {

    try {

	File fXmlFile = new File("files/FSP940101.xml");
	
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);
			
	//optional, but recommended
	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	doc.getDocumentElement().normalize();

	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
	NodeList nList = doc.getElementsByTagName("DOC");
			
	System.out.println("----------------------------");

	for (int temp = 0; temp < nList.getLength(); temp++) {

		Node nNode = nList.item(temp);
				
		System.out.println("\nCurrent Element :" + nNode.getNodeName());
				
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) nNode;

			/* Print nodes here */
			/*System.out.println("DOCNO : " + eElement.getElementsByTagName("DOCNO").item(0).getTextContent());
			System.out.println("DATE : " + eElement.getElementsByTagName("DATE").item(0).getTextContent());
			System.out.println("TEXT : " + eElement.getElementsByTagName("TEXT").item(0).getTextContent());
			*/
		}
	}
    } catch (Exception e) {
	e.printStackTrace();
    }
  }

}