import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;


public class XML {

    public void createXML() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElement("people");
        doc.appendChild(rootElement);

        Database database = new Database();
        List<Person> allPeople = database.getAllPeople();

        for (Person p : allPeople) {
            Element person = doc.createElement("person");
            rootElement.appendChild(person);

            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(p.getName()));
            person.appendChild(name);

            Element surname = doc.createElement("surname");
            surname.appendChild(doc.createTextNode(p.getSurn()));
            person.appendChild(surname);

            Element dob = doc.createElement("dateOfBirth");
            dob.appendChild(doc.createTextNode(String.valueOf(p.getDob())));
            person.appendChild(dob);

            Element pin = doc.createElement("rc");
            pin.appendChild(doc.createTextNode(p.getRc()));
            person.appendChild(pin);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("people.xml"));
        transformer.transform(source, result);

        StreamResult consoleResult = new StreamResult(System.out);
        transformer.transform(source, consoleResult);
    }
}
