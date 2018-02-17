package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPGetter {

    private URL url;

    public HTTPGetter(String url){
        try {
            this.url = new URL(url);
        } catch (java.net.MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getHTML() {
        HttpURLConnection con;
        StringBuffer content = new StringBuffer();

        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setRequestProperty("Content-Type", "application/json");
            String contentType = con.getHeaderField("Content-Type");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }
}
