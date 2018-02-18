package com.hophacks2018.bonappetit.bonappetit.vector.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPGetter {

    private URL url;
    private int status;
    private String html;

    public HTTPGetter(String url){
        try {
            this.url = new URL(url);

            HttpURLConnection con;
            StringBuffer content = new StringBuffer();

            try {
                con = (HttpURLConnection) this.url.openConnection();
                con.setRequestMethod("GET");

                con.setRequestProperty("Content-Type", "application/json");
                String contentType = con.getHeaderField("Content-Type");
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);

                this.status = con.getResponseCode();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                this.html = content.toString();
                con.disconnect();

            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        } catch (java.net.MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getHTML() {

        return this.getHtml();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
