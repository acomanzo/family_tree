package com.example.family_tree_temp.Database;

import android.util.Log;

import com.example.family_tree_temp.Models.Address;
import com.example.family_tree_temp.Models.ContactInformation;
import com.example.family_tree_temp.Models.Email;
import com.example.family_tree_temp.Models.FamilyMember;
import com.example.family_tree_temp.Models.MedicalHistory;
import com.example.family_tree_temp.Models.PhoneNumber;
import com.example.family_tree_temp.ViewModels.AncestorDescendantBundle;
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

    private enum Model {
        FAMILY_MEMBER, EMAIL, CONTACT_INFORMATION, PHONE_NUMBER, ADDRESS, MEDICAL_HISTORY
    }

    private String baseUrl;
    
    public FamilyTreeSqlDatabase() {
        //baseUrl = "http://localhost:3000";
        baseUrl = "http://10.0.2.2:3000";
    }

    private String makeHttpUrlRequest(String stubs, String method, CrudMethod crudMethod, Model model) {
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

            parsedResponse = parse(responseContent.toString(), status, crudMethod, model);
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

        String response = makeHttpUrlRequest(stubs, "POST", CrudMethod.CREATE, Model.FAMILY_MEMBER);
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
        String response = makeHttpUrlRequest(stubs, "PATCH", CrudMethod.UPDATE, Model.FAMILY_MEMBER);
        return response;
    }

    public String deleteFamilyMember(FamilyMember familyMember) {
        String stubs = "/familymember/" + familyMember.getServerId();
        String response = makeHttpUrlRequest(stubs, "DELETE", CrudMethod.DELETE, Model.FAMILY_MEMBER);
        return response;
    }

    public static String parse(String responseBody, int status, CrudMethod crudMethod, Model model) {
        switch (model) {
            case FAMILY_MEMBER:
                return parseFamilyMember(responseBody, status, crudMethod);
            case EMAIL:
                return parseEmail(responseBody, status, crudMethod);
            case CONTACT_INFORMATION:
                return parseContactInformation(responseBody, status, crudMethod);
            case PHONE_NUMBER:
                return parsePhoneNumber(responseBody, status, crudMethod);
            case ADDRESS:
                return parseAddress(responseBody, status, crudMethod);
            case MEDICAL_HISTORY:
                return parseMedicalHistory(responseBody, status, crudMethod);
            default:
                return null;
        }
    }

    public static String parseFamilyMember(String responseBody, int status, CrudMethod crudMethod) {
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

    public static String parseEmail(String responseBody, int status, CrudMethod crudMethod) {
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
                        JSONObject email = recordSet.getJSONObject(0);
                        int emailId = email.getInt("EmailId");
                        return String.valueOf(emailId);
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

    public static String parseContactInformation(String responseBody, int status, CrudMethod crudMethod) {
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
                        JSONObject contactInformation = recordSet.getJSONObject(0);
                        int emailId = contactInformation.getInt("ContactInformationId");
                        return String.valueOf(emailId);
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

    public static String parsePhoneNumber(String responseBody, int status, CrudMethod crudMethod) {
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
                        JSONObject phoneNumber = recordSet.getJSONObject(0);
                        int emailId = phoneNumber.getInt("PhoneNumberId");
                        return String.valueOf(emailId);
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

    public static String parseAddress(String responseBody, int status, CrudMethod crudMethod) {
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
                        JSONObject address = recordSet.getJSONObject(0);
                        int emailId = address.getInt("ContactAddressId");
                        return String.valueOf(emailId);
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

    public static String parseMedicalHistory(String responseBody, int status, CrudMethod crudMethod) {
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
                        JSONObject medicalHistory = recordSet.getJSONObject(0);
                        int emailId = medicalHistory.getInt("MedicalHistoryId");
                        return String.valueOf(emailId);
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

    public String insertEmail(Email email) {

        String stubs = "/email" +
                "?email=" + email.getEmail() +
                "&contactInformationId=" + email.getContactInformationServerId();

        String response = makeHttpUrlRequest(stubs, "POST", CrudMethod.CREATE, Model.EMAIL);
        return response;
    }

    public String insertContactInformation(ContactInformation contactInformation) {
        String stubs = "/contactinformation" +
                "?familyMemberId=" + contactInformation.getFamilyMemberId();

        String response = makeHttpUrlRequest(stubs, "POST", CrudMethod.CREATE, Model.CONTACT_INFORMATION);
        return response;
    }

    public String insertPhoneNumber(PhoneNumber phoneNumber) {
        String stubs = "/phonenumber" +
                "?phoneNumber=" + phoneNumber.getPhoneNumber() +
                "&contactInformationId=" + phoneNumber.getContactInformationServerId();

        String response = makeHttpUrlRequest(stubs, "POST", CrudMethod.CREATE, Model.PHONE_NUMBER);
        return response;
    }

    public String insertAddress(Address address) {
        String stubs = "/contactaddress" +
                "?streetName=" + address.getStreetName() +
                "&houseNumber=" + address.getHouseNumber() +
                "&extra=" + "null" +
                "&city=" + address.getCity() +
                "&state=" + address.getState() +
                "&zipcode=" + address.getZipCode() +
                "&contactInformationId=" + address.getContactInformationServerId();

        String response = makeHttpUrlRequest(stubs, "POST", CrudMethod.CREATE, Model.ADDRESS);
        return response;
    }

    public String insertMedicalHistory(MedicalHistory medicalHistory) {
        String stubs = "/medicalhistory" +
                "?dateDiagnosed=" + medicalHistory.getDateDiagnosed() +
                "&note=" + medicalHistory.getNote() +
                "&diagnosis=" + medicalHistory.getDiagnosis() +
                "&familyMemberId=" + medicalHistory.getFamilyMemberServerId();

        String response = makeHttpUrlRequest(stubs, "POST", CrudMethod.CREATE, Model.MEDICAL_HISTORY);
        return response;
    }

//    public String insertAncestor(AncestorDescendantBundle ancestorDescendantBundle) {
//        String stubs = "/ancestordescendant" +
//                "?ancestorId=" + ancestorDescendantBundle.getNewFamilyMember() +
//                "&descendantId=" + ancestorDescendantBundle.getExistingFamilyMemberId() +
//                "&depth=" + ancestorDescendantBundle.getDepth();
//
//        String response = makeHttpUrlRequest(stubs, "POST", CrudMethod.CREATE, Model.ANCESTOR_DESCENDANT);
//        return response;
//    }
}
