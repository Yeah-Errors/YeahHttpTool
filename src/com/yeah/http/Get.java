package com.yeah.http;



import com.sun.istack.internal.NotNull;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class Get {
    String url;
    HashMap<String,String> header;
    String body;
    {
        header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");
    }
    public Get(String url){
        this.url=url;
    }
    public Get(String url,String... postParams){
        this.url=url;
        StringBuilder postParam = new StringBuilder();
        for (int i = 0; i < postParams.length; i+=2) {
            postParam.append("&").append(postParams[i]).append("=").append(postParams[i + 1]);
        }
        body = postParam.substring(1);
    }
    public void putHeaderParam(String key,String value){
        header.put(key, value);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String doPost(){
        StringBuilder response = new StringBuilder();
        try {
            URL postUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) postUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            header.forEach(httpURLConnection::setRequestProperty);
            httpURLConnection.setDoOutput(true);
            DataOutputStream writeBody = (DataOutputStream)httpURLConnection.getOutputStream();
            writeBody.writeBytes(body);
            writeBody.flush();
            writeBody.close();
            response.append("Response_Code: "+httpURLConnection.getResponseCode());
            if(httpURLConnection.getResponseCode()==200){
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.toString();
    }
    @NotNull
    public void setUserAgent(String userAgent){
        header.put("User-Agent",userAgent);
    }

    public HashMap<String, String> getHeaderHashMap() {
        return header;
    }
    public String getHeader(String key){
        return header.get("key");
    }

    public String getBody() {
        return body;
    }

    public String getUrl() {
        return url;
    }
}
