package smspack;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *
 * @author selvagates, copyrights www.selromsoft.com, +91 99427 32229,
 * mysoft.java@gmail.com
 */
public class Encoding {

    public String encodeURL(String url) {
        try {

            String encodedUrl = URLEncoder.encode(url, "UTF-8");

            return encodedUrl;

        } catch (UnsupportedEncodingException e) {

            System.err.println(e);

        }

        return "";
    }
}
