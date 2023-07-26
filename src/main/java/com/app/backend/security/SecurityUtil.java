package com.app.backend.security;

import java.io.StringReader;
import java.util.Base64;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;


import jakarta.servlet.http.HttpServletRequest;

public class SecurityUtil {

    public static Integer getIdFromAuthToken(HttpServletRequest request){

        String bearerToken = request.getHeader("Authorization");
        
        bearerToken = bearerToken.substring(7, bearerToken.length());
        String[] chunks = bearerToken.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        Integer id = null;

        try (JsonReader jsonReader = Json.createReader(new StringReader(payload))) {
    
            JsonObject jsonObject = jsonReader.readObject();

            id = jsonObject.getInt("id");
        }
        
        return id;
    }

    public static String getRoleFromAuthToken(HttpServletRequest request){

        String bearerToken = request.getHeader("Authorization");
        
        bearerToken = bearerToken.substring(7, bearerToken.length());
        String[] chunks = bearerToken.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        String role = null;

        try (JsonReader jsonReader = Json.createReader(new StringReader(payload))) {
    
            JsonObject jsonObject = jsonReader.readObject();

            JsonArray rolesArray = jsonObject.getJsonArray("roles");
            
            role = rolesArray.getString(0);
        }
        
        return role;
    }
}
