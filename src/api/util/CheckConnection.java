package api.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class CheckConnection {

    public boolean tryConnect() {
        String address = "http://www.google.com.br";
        try {
            URL url = new URL(address);
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
