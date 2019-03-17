import java.util.Date;

public class Person {

    private String name;
    private String surn;
    private Date dob;
    private String rc;

    public Person(String name, String surn, Date dob, String rc) {
        this.name = name;
        this.surn = surn;
        this.dob = dob;
        this.rc = rc;
    }

    public String getName() {
        return name;
    }

    public String getSurn() {
        return surn;
    }

    public Date getDob() {
        return dob;
    }

    public String getRc() {
        return rc;
    }
}
