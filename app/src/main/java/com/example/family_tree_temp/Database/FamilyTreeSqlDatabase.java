package com.example.family_tree_temp.Database;

import android.util.Log;

import com.example.family_tree_temp.Models.FamilyMember;
import com.google.gson.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;


public class FamilyTreeSqlDatabase {

    private enum CrudMethod {
        CREATE, UPDATE, DELETE
    }

    private String baseUrl;
    
    public FamilyTreeSqlDatabase() {
        //baseUrl = "http://localhost:3000";
        baseUrl = "http://10.0.2.2:3000";
    }

    private String makeHttpUrlRequest(String stubs, String method, CrudMethod crudMethod) {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        String parsedResponse = null;

        HttpURLConnection connection = null;
        try {
            String insertUrl = baseUrl + stubs;

            URL url = new URL(insertUrl);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod(method);

            int status = connection.getResponseCode();

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();

            parsedResponse = parse(responseContent.toString(), status, crudMethod);
        } catch (MalformedURLException e) {
            Log.e("familyTreeSqlDatabase", e.getLocalizedMessage());
        } catch (IOException e) {
            Log.e("familyTreeSqlDatabase", e.getLocalizedMessage());
        } finally {
            connection.disconnect();
            return parsedResponse;
        }
    }

    public String insertFamilyMember(FamilyMember familyMember) {

//        String stubs = "/familymember" +
//                "?firstName=" + familyMember.getFirstName() +
//                "&lastName=" + familyMember.getLastName() +
//                "&age=" + familyMember.getAge() +
//                "&genderId=" + familyMember.getGenderId();

        String stubs = "/familymember" +
                "?firstName=" + familyMember.getFirstName() +
                "&lastName=" + familyMember.getLastName() +
                "&birthDate=" + familyMember.getBirthDate() +
                "&gender=" + familyMember.getGender();

        String response = makeHttpUrlRequest(stubs, "POST", CrudMethod.CREATE);
        return response;
    }

    public String updateFamilyMember(FamilyMember familyMember) {

//        String stubs = "/familymember/" + familyMember.getServerId() +
//                "?firstName=" + familyMember.getFirstName() +
//                "&lastName=" + familyMember.getLastName() +
//                "&age=" + familyMember.getAge() +
//                "&genderId=" + familyMember.getGenderId();
        String stubs = "/familymember/" + familyMember.getServerId() +
                "?firstName=" + familyMember.getFirstName() +
                "&lastName=" + familyMember.getLastName() +
                "&birthDate=" + familyMember.getBirthDate() +
                "&gender=" + familyMember.getGender();
        String response = makeHttpUrlRequest(stubs, "PATCH", CrudMethod.UPDATE);
        return response;
    }

    public String deleteFamilyMember(FamilyMember familyMember) {
        String stubs = "/familymember/" + familyMember.getServerId();
        String response = makeHttpUrlRequest(stubs, "DELETE", CrudMethod.DELETE);
        return response;
    }

    public static String parse(String responseBody, int status, CrudMethod crudMethod) {
        try {
            JSONObject response = new JSONObject(responseBody);
            if (status > 299) {
                String message = response.getString("message");
                Log.i("familyTreeSqlDatabase", message);
            }
            else {
                switch (crudMethod) {
                    case CREATE:
                        JSONArray recordSet = response.getJSONArray("recordset");
                        JSONObject familyMember = recordSet.getJSONObject(0);
                        int familyMemberId = familyMember.getInt("FamilyMemberId");
                        return String.valueOf(familyMemberId);
                    case UPDATE:
                    case DELETE:
                        break;
                }
            }
        } catch (JSONException e) {
            Log.e("familyTreeSqlDatabase", e.getLocalizedMessage());
        }
        return null;
    }
}
