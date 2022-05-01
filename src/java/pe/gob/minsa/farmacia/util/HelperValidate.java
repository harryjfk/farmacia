package pe.gob.minsa.farmacia.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperValidate {

    public static boolean IsRucValidate(String ruc) {
        Pattern pattern;
        Matcher matcher;

        String RUC_PATTERN = "^\\d{11}$";

        pattern = Pattern.compile(RUC_PATTERN);
        matcher = pattern.matcher(ruc);

        return matcher.matches();
    }
}
