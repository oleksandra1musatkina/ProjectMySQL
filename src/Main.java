import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {

        File file = new File("dokument.txt");
        Scanner sc = new Scanner(file);

        Database database = new Database();
        int index = 0;


        while (sc.hasNextLine()) {
            String meno = "";
            String priezvisko = "";
            String rc = "";
            String datumNar = "";
            if (sc.hasNext())
                meno = sc.next();

            if (sc.hasNext())
                priezvisko = sc.next();

            if (sc.hasNext())
                rc = sc.next();

            datumNar = sc.next();
            SimpleDateFormat dateformat1 = new SimpleDateFormat("dd.MM.yyyy");

            Date date1 = null;
            try {
                date1 = dateformat1.parse(datumNar);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String str = meno + " " + priezvisko + " " + rc + " " + datumNar;
            Validations clovek = new Validations(meno, priezvisko, rc, datumNar);
            System.out.println("test::::::::::::::::::::::::::::::::::");
            System.out.println(clovek.isValidRC(rc));
            System.out.println(clovek.isValidDN(datumNar));
            System.out.println(clovek.canByRcDivided(rc));
            System.out.println(clovek.doesRcAndDateMatch(rc, datumNar));
            if (clovek.isValidRC(rc) && clovek.isValidDN(datumNar) && clovek.canByRcDivided(rc) && clovek.doesRcAndDateMatch(rc, datumNar)) {
                Person osoba = new Person(meno, priezvisko, date1, rc);
                database.insertNewPerson(osoba);
                System.out.println("insertttttttttttttttttttttttttttttttttttttttttttttt");
            }
            System.out.println(clovek.isValidRC(rc) + " " + clovek.isValidDN(datumNar) + " " + clovek.canByRcDivided(rc) + " " + clovek.doesRcAndDateMatch(rc, datumNar));
            System.out.println("new: ---------------------" + meno + " " + priezvisko + rc + datumNar + " " + index);
            index++;

        }

        Person person = database.selectPersonbyLastName("Kriva");
        System.out.println("person by last name: " + person.getName() + " " + person.getSurn() + " " + person.getDob() + " " + person.getRc());

        Person person1 = database.selectPersonbyRC("841018/5454");
        System.out.println("person by rc: " + person1.getName() + " " + person1.getSurn() + " " + person1.getDob() + " " + person1.getRc());


        List<Person> allMen = database.getAllMen();
        for (
                Person p : allMen) {
            System.out.println("men: " + p.getName() + p.getSurn() + p.getDob() + p.getRc());
        }
        System.out.println("nember of womans: " + database.getNumberOfWomen());

        List<Person> adults = database.getAdults();
        for (
                Person p : adults) {
            System.out.println("adults: " + p.getName() + p.getSurn() + p.getDob() + p.getRc());
        }

        Set<String> firstNames = database.getFirstNames();
        for (
                String s : firstNames) {
            System.out.println("name: " + s);
        }

        List<Person> allPeople = database.getAllPeople();
        for (
                Person p : allPeople) {
            System.out.println("all: " + p.getName() + " " + p.getSurn() + " " + p.getDob() + " " + p.getRc());
        }

        XML xml = new XML();
        try {
            xml.createXML();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
