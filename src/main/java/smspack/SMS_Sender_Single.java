package smspack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SMS_Sender_Single {

    public String getData(String user1, String pass1, String sender1, String mobile1, String message1) {
        try {

            String user = "username=" + "" + user1;
            String hash = "&hash=" + "" + pass1;
            String sender = "&sender=" + "" + sender1;

            HttpURLConnection conn = (HttpURLConnection) new URL("http://newsms.selromsoft.in/api2/send/?").openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            String numbers = "&numbers=" + "91" + mobile1;
            String message = "&message=" + "" + message1;
            String data = user + hash + numbers + message + sender;
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));

            try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = rd.readLine()) != null) {
                    System.out.println("Message Status: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error SMS " + e);
        }
        return null;
    }
}
