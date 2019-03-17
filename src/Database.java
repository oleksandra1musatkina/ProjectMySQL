import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Database {

    private final String username = "oleksandra";
    private final String password = "sasa";
    private final String url = "jdbc:mysql://localhost:3308/db1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private Connection getConnection() {
        Connection connection;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void insertNewPerson(Person person) {
        Connection conn = getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO person (name,surn,dob,rodnc) values(?,?,?,?)");
            stmt.setString(1, person.getName());
            stmt.setString(2, person.getSurn());
            stmt.setDate(3, new Date(person.getDob().getTime()));
            stmt.setString(4, person.getRc());

            int result = stmt.executeUpdate();

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Person selectPersonbyLastName(String lastname) throws SQLException {
        Connection conn = getConnection();
        String name = "";
        String surn = lastname;
        Date date = new Date(1998 - 10 - 10);
        String rodnc = "";
        try {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * from person where person.surn like ?")) {
                stmt.setString(1, surn);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    name = rs.getString("name");
                    surn = rs.getString("surn");
                    date = rs.getDate("dob");
                    rodnc = rs.getString("rodnc");
                    conn.close();
                    Person person = new Person(name, surn, date, rodnc);
                    return person;
                } else {
                    conn.close();
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Person selectPersonbyRC(String rodnc) throws SQLException {
        Connection conn = getConnection();
        String name = "";
        String surn = "";
        Date date = new Date(1998 - 10 - 10);
        try {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * from person where person.rodnc like ?")) {
                stmt.setString(1, rodnc);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    name = rs.getString("name");
                    surn = rs.getString("surn");
                    date = rs.getDate("dob");
                    rodnc = rs.getString("rodnc");
                    conn.close();
                    Person person = new Person(name, surn, date, rodnc);
                    return person;
                } else {
                    conn.close();
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getNumberOfWomen() throws SQLException {
        Connection conn = getConnection();
        try {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT count(*) as pocet from person where rodnc like '__5%' or rodnc like '__6%'")) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
//                    conn.close();
                    return rs.getInt("pocet");
                } else {
                    conn.close();
                    return 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Person> getAllMen() {
        Connection conn = getConnection();
        String query = "Select * from person where rodnc like '__0%' or rodnc like '__1%'";
        List<Person> men = new ArrayList<>();
        ResultSet rs;
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            rs = stmnt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String surn = rs.getString("surn");
                Date date = rs.getDate("dob");
                String rodnc = rs.getString("rodnc");
                Person p = new Person(name, surn, date, rodnc);
                men.add(p);
            }
            closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return men;
    }

    public List<Person> getAdults() {
        Connection conn = getConnection();

        LocalDate localdate = LocalDate.now();

        String daaaatum = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localdate);
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();

        String query = "SELECT * from person where dob <= ? - interval 18 year";
        List<Person> adults = new ArrayList<>();
        ResultSet rs;
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, daaaatum);
            System.out.println(daaaatum);
            rs = stmnt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String surn = rs.getString("surn");
                Date date = rs.getDate("dob");
                String rodnc = rs.getString("rodnc");
                Person p = new Person(name, surn, date, rodnc);
                adults.add(p);
            }
            closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adults;
    }

    public Set<String> getFirstNames() {
        Connection conn = getConnection();
        String query = "select name from person";
        Set<String> firstnames = new HashSet<String>();
        ResultSet rs;

        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            rs = stmnt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                firstnames.add(name);
            }
            closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return firstnames;

    }

    public List<Person> getAllPeople() {
        Connection conn = getConnection();
        String query = "SELECT * from person";
        List<Person> adults = new ArrayList<>();
        ResultSet rs;
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            rs = stmnt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String surn = rs.getString("surn");
                Date date = rs.getDate("dob");
                String rodnc = rs.getString("rodnc");
                Person p = new Person(name, surn, date, rodnc);
                adults.add(p);
            }
            closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adults;
    }


    private void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
