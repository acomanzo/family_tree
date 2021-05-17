package com.example.family_tree_temp.Database;

import android.util.Log;

import com.example.family_tree_temp.Models.Address;
import com.example.family_tree_temp.Models.ContactInformation;
import com.example.family_tree_temp.Models.Email;
import com.example.family_tree_temp.Models.FamilyMember;
import com.example.family_tree_temp.Models.FamilyTree;
import com.example.family_tree_temp.Models.MedicalHistory;
import com.example.family_tree_temp.Models.PhoneNumber;
import com.example.family_tree_temp.Models.AncestorDescendantBundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class FamilyTreeSqlDatabase {

    private enum CrudMethod {
        CREATE, READ, UPDATE, DELETE, LOGIN
    }

    private enum Model {
        FAMILY_MEMBER,
        EMAIL,
        CONTACT_INFORMATION,
        PHONE_NUMBER,
        ADDRESS,
        MEDICAL_HISTORY,
        ANCESTOR_DESCENDANT,
        APP_USER,
        FAMILY_TREE,
        SHARE
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
                "&gender=" + familyMember.getGender() +
                "&familyTreeId=" + familyMember.getFamilyTreeId();

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
                "&gender=" + familyMember.getGender() +
                "&familyTreeId=" + familyMember.getFamilyTreeId();
        String response = makeHttpUrlRequest(stubs, "PATCH", CrudMethod.UPDATE, Model.FAMILY_MEMBER);
        return response;
    }

    public String deleteFamilyMember(FamilyMember familyMember) {
        String stubs = "/familymember/" + familyMember.getServerId();
        String response = makeHttpUrlRequest(stubs, "DELETE", CrudMethod.DELETE, Model.FAMILY_MEMBER);
        return response;
    }

    public String parse(String responseBody, int status, CrudMethod crudMethod, Model model) {
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
            case ANCESTOR_DESCENDANT:
                return parseAncestorDescendant(responseBody, status, crudMethod);
            case APP_USER:
                return parseAppUser(responseBody, status, crudMethod);
            case FAMILY_TREE:
                return parseFamilyTree(responseBody, status, crudMethod);
            case SHARE:
                return parseShare(responseBody, status, crudMethod);
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
                JSONArray recordSet = response.getJSONArray("recordset");
                switch (crudMethod) {
                    case CREATE:
                    case UPDATE:
                    case DELETE:
                        JSONObject familyMember = recordSet.getJSONObject(0);
                        return String.valueOf(familyMember);
                    case READ:
                        return recordSet.toString();
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
//                        int emailId = email.getInt("EmailId");
                        return String.valueOf(email);
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
//                        int contactInformationId = contactInformation.getInt("ContactInformationId");
                        return String.valueOf(contactInformation);
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
//                        int phoneNumberId = phoneNumber.getInt("PhoneNumberId");
                        return String.valueOf(phoneNumber);
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
//                        int addressId = address.getInt("ContactAddressId");
                        return String.valueOf(address);
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
//                        int medicalHistoryId = medicalHistory.getInt("MedicalHistoryId");
                        return String.valueOf(medicalHistory);
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

    public static String parseAncestorDescendant(String responseBody, int status, CrudMethod crudMethod) {
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
                        JSONObject ancestorDescendant = recordSet.getJSONObject(0);
//                        int ancestorDescendantId = ancestorDescendant.getInt("AncestorDescendantId");
                        return String.valueOf(ancestorDescendant);
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

    public static String parseAppUser(String responseBody, int status, CrudMethod crudMethod) {
        try {
            JSONObject response = new JSONObject(responseBody);
            if (status > 299) {
                String message = response.getString("message");
                Log.i("familyTreeSqlDatabase", message);
            }
            else {
                JSONArray recordSet = response.getJSONArray("recordset");
                if (recordSet.length() > 0) {
                    JSONObject appUser = recordSet.getJSONObject(0);
                    switch (crudMethod) {
                        case LOGIN:
                        case CREATE:
//                            int appUserId = appUser.getInt("AppUserId");
                            return String.valueOf(appUser);
                        case UPDATE:
                        case DELETE:
                            break;
                    }
                }
            }
        } catch (JSONException e) {
            Log.e("familyTreeSqlDatabase", e.getLocalizedMessage());
        }
        return null;
    }

    public String parseFamilyTree(String responseBody, int status, CrudMethod crudMethod) {
        try {
            JSONObject response = new JSONObject(responseBody);
            if (status > 299) {
                String message = response.getString("message");
                Log.i("familyTreeSqlDatabase", message);
            }
            else {
                JSONArray recordSet = response.getJSONArray("recordset");
                if (recordSet.length() > 0) {
                    JSONObject familyTree = recordSet.getJSONObject(0);
                    switch (crudMethod) {
                        case READ:
                            return recordSet.toString();
                        case CREATE:
//                            int familyTreeId = familyTree.getInt("FamilyTreeId");
                            return String.valueOf(familyTree);
                        case UPDATE:
                        case DELETE:
                            break;
                    }
                }
            }
        } catch (JSONException e) {
            Log.e("familyTreeSqlDatabase", e.getLocalizedMessage());
        }
        return null;
    }

    public String parseShare(String responseBody, int status, CrudMethod crudMethod) {
        try {
            JSONObject response = new JSONObject(responseBody);
            if (status > 299) {
                String message = response.getString("message");
                Log.i("familyTreeSqlDatabase", message);
            }
            else {
                JSONArray recordSet = response.getJSONArray("recordset");
                if (recordSet.length() > 0) {
                    JSONObject share = recordSet.getJSONObject(0);
                    switch (crudMethod) {
                        case READ:
                            return recordSet.toString();
                        case CREATE:
                            int shareId = share.getInt("ShareId");
                            return String.valueOf(shareId);
                        case UPDATE:
                        case DELETE:
                            break;
                    }
                }
            }
        } catch (JSONException e) {
            Log.e("familyTreeSqlDatabase", e.getLocalizedMessage());
        }
        return null;
    }

    public Email insertEmail(Email email) {

        String stubs = "/email" +
                "?email=" + email.getEmail() +
                "&contactInformationId=" + email.getContactInformationServerId();

        String response = makeHttpUrlRequest(stubs, "POST", CrudMethod.CREATE, Model.EMAIL);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int serverId = jsonObject.getInt("EmailId");
            String createdAt = jsonObject.getString("CreatedAt");
            String updatedAt = jsonObject.getString("UpdatedAt");
            email.setServerId(serverId);
            email.setCreatedAt(createdAt);
            email.setUpdatedAt(updatedAt);
            return email;
        } catch (JSONException e) {
            return null;
        }
    }

    public ContactInformation insertContactInformation(ContactInformation contactInformation) {
        String stubs = "/contactinformation" +
                "?familyMemberId=" + contactInformation.getFamilyMemberId();

        String response = makeHttpUrlRequest(stubs, "POST", CrudMethod.CREATE, Model.CONTACT_INFORMATION);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int serverId = jsonObject.getInt("ContactInformationId");
            String createdAt = jsonObject.getString("CreatedAt");
            String updatedAt = jsonObject.getString("UpdatedAt");
            contactInformation.setServerId(serverId);
            contactInformation.setCreatedAt(createdAt);
            contactInformation.setUpdatedAt(updatedAt);
            return contactInformation;
        } catch (JSONException e) {
            return null;
        }
    }

    public PhoneNumber insertPhoneNumber(PhoneNumber phoneNumber) {
        String stubs = "/phonenumber" +
                "?phoneNumber=" + phoneNumber.getPhoneNumber() +
                "&contactInformationId=" + phoneNumber.getContactInformationServerId();

        String response = makeHttpUrlRequest(stubs, "POST", CrudMethod.CREATE, Model.PHONE_NUMBER);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int serverId = jsonObject.getInt("PhoneNumberId");
            String createdAt = jsonObject.getString("CreatedAt");
            String updatedAt = jsonObject.getString("UpdatedAt");
            phoneNumber.setServerId(serverId);
            phoneNumber.setCreatedAt(createdAt);
            phoneNumber.setUpdatedAt(updatedAt);
            return phoneNumber;
        } catch (JSONException e) {
            return null;
        }
    }

    public Address insertAddress(Address address) {
        String stubs = "/contactaddress" +
                "?streetName=" + address.getStreetName() +
                "&houseNumber=" + address.getHouseNumber() +
                "&extra=" + "null" +
                "&city=" + address.getCity() +
                "&state=" + address.getState() +
                "&zipcode=" + address.getZipCode() +
                "&contactInformationId=" + address.getContactInformationServerId();

        String response = makeHttpUrlRequest(stubs, "POST", CrudMethod.CREATE, Model.ADDRESS);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int serverId = jsonObject.getInt("ContactAddressId");
            String createdAt = jsonObject.getString("CreatedAt");
            String updatedAt = jsonObject.getString("UpdatedAt");
            address.setServerId(serverId);
            address.setCreatedAt(createdAt);
            address.setUpdatedAt(updatedAt);
            return address;
        } catch (JSONException e) {
            return null;
        }
    }

    public MedicalHistory insertMedicalHistory(MedicalHistory medicalHistory) {
        String stubs = "/medicalhistory" +
                "?dateDiagnosed=" + medicalHistory.getDateDiagnosed() +
                "&note=" + medicalHistory.getNote() +
                "&diagnosis=" + medicalHistory.getDiagnosis() +
                "&familyMemberId=" + medicalHistory.getFamilyMemberServerId();

        String response = makeHttpUrlRequest(stubs, "POST", CrudMethod.CREATE, Model.MEDICAL_HISTORY);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int serverId = jsonObject.getInt("MedicalHistoryId");
            String createdAt = jsonObject.getString("CreatedAt");
            String updatedAt = jsonObject.getString("UpdatedAt");
            medicalHistory.setServerId(serverId);
            medicalHistory.setCreatedAt(createdAt);
            medicalHistory.setUpdatedAt(updatedAt);
            return medicalHistory;
        } catch (JSONException e) {
            return null;
        }
    }

    public AncestorDescendantBundle insertAncestor(AncestorDescendantBundle ancestorDescendantBundle) {
        String stubs = "/ancestordescendant" +
                "?ancestorId=" + ancestorDescendantBundle.getNewFamilyMember().getServerId() +
                "&descendantId=" + ancestorDescendantBundle.getExistingFamilyMember().getServerId() +
                "&depth=" + ancestorDescendantBundle.getDepth();

        String response = makeHttpUrlRequest(stubs, "POST", CrudMethod.CREATE, Model.ANCESTOR_DESCENDANT);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int serverId = jsonObject.getInt("AncestorDescendantId");
            String createdAt = jsonObject.getString("CreatedAt");
            String updatedAt = jsonObject.getString("UpdatedAt");
            ancestorDescendantBundle.setServerId(serverId);
            ancestorDescendantBundle.setCreatedAt(createdAt);
            ancestorDescendantBundle.setUpdatedAt(updatedAt);
            return ancestorDescendantBundle;
        } catch (JSONException e) {
            return null;
        }
    }

    public AncestorDescendantBundle insertDescendant(AncestorDescendantBundle ancestorDescendantBundle) {
        String stubs = "/ancestordescendant" +
                "?ancestorId=" + ancestorDescendantBundle.getExistingFamilyMember().getServerId() +
                "&descendantId=" + ancestorDescendantBundle.getNewFamilyMember().getServerId() +
                "&depth=" + ancestorDescendantBundle.getDepth();

        String response = makeHttpUrlRequest(stubs, "POST", CrudMethod.CREATE, Model.ANCESTOR_DESCENDANT);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int serverId = jsonObject.getInt("AncestorDescendantId");
            String createdAt = jsonObject.getString("CreatedAt");
            String updatedAt = jsonObject.getString("UpdatedAt");
            ancestorDescendantBundle.setServerId(serverId);
            ancestorDescendantBundle.setCreatedAt(createdAt);
            ancestorDescendantBundle.setUpdatedAt(updatedAt);
            return ancestorDescendantBundle;
        } catch (JSONException e) {
            return null;
        }
    }

    public String login(String email, String password) {
        String stubs = "/appuser/login" +
                "?email=" + email +
                "&userPassword=" + password;

        String response = makeHttpUrlRequest(stubs, "POST", CrudMethod.LOGIN, Model.APP_USER);
        return response;
    }

    public String insertAppUser(String email, String password) {
        String stubs = "/appuser" +
                "?email=" + email +
                "&userPassword=" + password;

        String response = makeHttpUrlRequest(stubs, "POST", CrudMethod.CREATE, Model.APP_USER);
        return response;
    }

    public JSONArray selectAllFamilyTrees() throws JSONException {
        String stubs = "/familytree";

        JSONArray response = new JSONArray(makeHttpUrlRequest(stubs, "GET", CrudMethod.READ, Model.FAMILY_TREE));
        return response;
    }

    public JSONArray selectFamilyTreesByAppUserId(String appUserId) {
        String stubs = "/familytree?appUserId=" + appUserId;

        try {
            String response = makeHttpUrlRequest(stubs, "GET", CrudMethod.READ, Model.FAMILY_TREE);
            JSONArray jsonArray = new JSONArray(response);
            return jsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public FamilyTree insertFamilyTree(FamilyTree familyTree) {
        String stubs = "/familytree" +
                "?appUserId=" + familyTree.getAppUserId() +
                "&treeName=" + familyTree.getTreeName();

        String response = makeHttpUrlRequest(stubs, "POST", CrudMethod.CREATE, Model.FAMILY_TREE);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int familyTreeId = jsonObject.getInt("FamilyTreeId");
            String createdAt = jsonObject.getString("CreatedAt");
            String updatedAt = jsonObject.getString("UpdatedAt");
            familyTree.setFamilyTreeId(familyTreeId);
            familyTree.setCreatedAt(createdAt);
            familyTree.setUpdatedAt(updatedAt);
            return familyTree;
        } catch (JSONException e) {
            return null;
        }
    }

    public List<FamilyMember> getFamilyMembersBy(int familyTreeId) {
        String stubs = "/familymember/" + familyTreeId;
        String response = makeHttpUrlRequest(stubs, "GET", CrudMethod.READ, Model.FAMILY_MEMBER);
        try {
            JSONArray jsonArray = new JSONArray(response);
            ArrayList<FamilyMember> familyMembers = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int serverId = jsonObject.getInt("FamilyMemberId");
                String firstName = jsonObject.getString("FirstName");
                String lastName = jsonObject.getString("LastName");
                String birthDate = jsonObject.getString("BirthDate");
                String gender = jsonObject.getString("Gender");
                int treeId = jsonObject.getInt("FamilyTreeId");
                String createdAt = jsonObject.getString("CreatedAt");
                String updatedAt = jsonObject.getString("UpdatedAt");
                FamilyMember familyMember = new FamilyMember(firstName, lastName, birthDate, gender, treeId, createdAt, updatedAt);
                familyMember.setServerId(serverId);
                familyMembers.add(familyMember);
            }
            return familyMembers;
        } catch (JSONException e) {
            return null;
        }
    }

    public String shareFamilyTree(int appUserId, int familyTreeId, String email) {
        String stubs = "/familytree/share?" +
                "appUserId=" + appUserId +
                "&familyTreeId=" + familyTreeId +
                "&email=" + email;
        String response = makeHttpUrlRequest(stubs, "POST", CrudMethod.CREATE, Model.SHARE);
        return response;
    }

}
