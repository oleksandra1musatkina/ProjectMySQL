public class Validations {

    String name;
    String surn;
    String rc;
    String dn;


    public Validations(String name, String surn, String rc, String dn) {
        this.name = name;
        this.surn = surn;
        this.rc = rc;
        this.dn = dn;
    }

    public boolean isValidRC(String rc) {
        return rc.matches("[0-9]{2}[0156][0-9]{3}/[0-9]{3,4}");
    }

    public boolean isValidDN(String dn) {

        return dn.matches("(0?[1-9]|1[0-9]|2[0-9]|3[01]).(0?[1-9]|1[012]).(19[0-9]{2}|20[01][0-9])");

    }

    public boolean canByRcDivided(String rc) {
        long rcLong = Long.parseLong(rc.replaceAll("/", ""));
        return (rcLong % 11 == 0);
    }

    public boolean doesRcAndDateMatch(String rc, String dn) {
        if (rc.charAt(0) == dn.charAt(dn.length()-2) &&
                rc.charAt(1) == dn.charAt(dn.length()-1) &&
                (rc.charAt(2) == '0' ||
                        rc.charAt(2) == '1' ||
                        rc.charAt(2) == '5' ||
                        rc.charAt(2) == '6')&&
                rc.charAt(3) == dn.charAt(4) &&
                rc.charAt(4) == dn.charAt(0) &&
                rc.charAt(5) == dn.charAt(1)
        )
            return true;
        else
            return false;
    }


}
