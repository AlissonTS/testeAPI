package api.util;

public class ManipulateString {
    public String replaceEscapeString(String stringManipulate) {
        if (stringManipulate != null) {
            stringManipulate = stringManipulate.replaceAll("\\n", "");
            stringManipulate = stringManipulate.replaceAll("\\t", "");
            stringManipulate = stringManipulate.replaceAll("\\r", "");
            stringManipulate = stringManipulate.replaceAll("\\\\", "");
        }
        return stringManipulate;
    }
}