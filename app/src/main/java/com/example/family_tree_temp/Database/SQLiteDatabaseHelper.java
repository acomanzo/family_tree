//package com.example.family_tree.Database;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteException;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//
//public class SQLiteDatabaseHelper extends SQLiteOpenHelper {
//    private static final String DATABASE_NAME = "family_tree_database.db";
//
//    private static final String TAG = "SQLiteDatabaseHelper";
//
//    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
//
//    // Table names
//    private static final String FAMILY_MEMBER = "FamilyMember";
//    private static final String MEDICAL_HISTORY = "MedicalHistoryNote";      // (many to one with family member)
//    private static final String CONTACT_INFORMATION = "ContactInformation";  // (one to one with family member)
//    private static final String PHONE_NUMBER = "PhoneNumber";                // (many to one with contact information)
//    private static final String EMAIL = "Email";                             // (many to one with contact information)
//    private static final String ADDRESS = "Address";                         // (many to one with family member)
//
//    // Columns FamilyMember
//    private static final String FAMILY_MEMBER_COLUMN_ID = "FamilyMemberId";
//    private static final String FAMILY_MEMBER_COLUMN_FIRST_NAME = "FirstName";
//    private static final String FAMILY_MEMBER_COLUMN_LAST_NAME = "LastName";
//    private static final String FAMILY_MEMBER_COLUMN_GENDER = "Gender";
//
//    // Create FamilyMember
//    private static final String CREATE_FAMILY_MEMBER_TABLE =
//            "CREATE TABLE " + FAMILY_MEMBER + "(" +
//                    FAMILY_MEMBER_COLUMN_ID + " INTEGER PRIMARY KEY," +
//                    FAMILY_MEMBER_COLUMN_FIRST_NAME + " TEXT NOT NULL," +
//                    FAMILY_MEMBER_COLUMN_LAST_NAME + " TEXT NOT NULL," +
//                    FAMILY_MEMBER_COLUMN_GENDER + " TEXT NOT NULL" +
//                    ");";
//
//    // Columns MedicalHistoryNote
//    private static final String MEDICAL_HISTORY_NOTE_COLUMN_ID = "MedicalHistoryNoteId";
//    private static final String MEDICAL_HISTORY_NOTE_COLUMN_DATE = "Date";
//    private static final String MEDICAL_HISTORY_NOTE_COLUMN_DIAGNOSIS = "Diagnosis";
//    private static final String MEDICAL_HISTORY_NOTE_COLUMN_FAMILY_MEMBER_ID = "FamilyMemberId";
//
//    // Create MedicalHistoryNote
//    private static final String CREATE_MEDICAL_HISTORY_TABLE =
//            "CREATE TABLE " + MEDICAL_HISTORY + "(" +
//                    MEDICAL_HISTORY_NOTE_COLUMN_ID + " INTEGER PRIMARY KEY," +
//                    MEDICAL_HISTORY_NOTE_COLUMN_DATE + " INTEGER NOT NULL," +
//                    MEDICAL_HISTORY_NOTE_COLUMN_DIAGNOSIS + " TEXT NOT NULL," +
//                    MEDICAL_HISTORY_NOTE_COLUMN_FAMILY_MEMBER_ID + " INTEGER NOT NULL," +
//                    "FOREIGN KEY (" + MEDICAL_HISTORY_NOTE_COLUMN_FAMILY_MEMBER_ID + ") REFERENCES " + FAMILY_MEMBER + "(" + FAMILY_MEMBER_COLUMN_ID + ")" +
//                    ");";
//
//    // Columns ContactInformation
//    private static final String CONTACT_INFORMATION_COLUMN_ID = "ContactInformationId";
//
//    // Create ContactInformation
//    private static final String CREATE_CONTACT_INFORMATION_TABLE =
//            "CREATE TABLE " + CONTACT_INFORMATION + "(" +
//                    CONTACT_INFORMATION_COLUMN_ID + " INTEGER PRIMARY KEY" +
//                    ");";
//
//    // Columns PhoneNumber
//    private static final String PHONE_NUMBER_COLUMN_ID = "PhoneNumberId";
//    private static final String PHONE_NUMBER_COLUMN_CONTACT_INFORMATION_ID = "ContactInformationId";
//    private static final String PHONE_NUMBER_COLUMN_PHONE_NUMBER = "PhoneNumber";
//
//    // Create PhoneNumber
//    private static final String CREATE_PHONE_NUMBER_TABLE =
//            "CREATE TABLE " + PHONE_NUMBER + "(" +
//                    PHONE_NUMBER_COLUMN_ID + " INTEGER PRIMARY KEY," +
//                    PHONE_NUMBER_COLUMN_CONTACT_INFORMATION_ID + " INTEGER NOT NULL," +
//                    PHONE_NUMBER_COLUMN_PHONE_NUMBER + " TEXT NOT NULL," +
//                    "FOREIGN KEY (" + PHONE_NUMBER_COLUMN_CONTACT_INFORMATION_ID + ") REFERENCES " + CONTACT_INFORMATION + "(" + CONTACT_INFORMATION_COLUMN_ID + ")" +
//                    ");";
//
//    // Columns Email
//    private static final String EMAIL_COLUMN_ID = "EmailId";
//    private static final String EMAIL_COLUMN_CONTACT_INFORMATION_ID = "ContactInformationId";
//    private static final String EMAIL_COLUMN_PHONE_NUMBER = "Email";
//
//    // Create Email
//    private static final String CREATE_EMAIL_TABLE =
//            "CREATE TABLE " + EMAIL + "(" +
//                    EMAIL_COLUMN_ID + " INTEGER PRIMARY KEY," +
//                    EMAIL_COLUMN_CONTACT_INFORMATION_ID + " INTEGER NOT NULL," +
//                    EMAIL_COLUMN_PHONE_NUMBER + " TEXT NOT NULL," +
//                    "FOREIGN KEY (" + EMAIL_COLUMN_CONTACT_INFORMATION_ID + ") REFERENCES " + CONTACT_INFORMATION + "(" + CONTACT_INFORMATION_COLUMN_ID + ")" +
//                    ");";
//
//    // Columns Address
//    private static final String ADDRESS_COLUMN_ID = "AddressId";
//    private static final String ADDRESS_COLUMN_CONTACT_INFORMATION_ID = "ContactInformationId";
//    private static final String ADDRESS_COLUMN_STREET = "Street";
//    private static final String ADDRESS_COLUMN_HOUSE_NUMBER = "HouseNumber";
//    private static final String ADDRESS_COLUMN_EXTRA = "Extra";
//    private static final String ADDRESS_COLUMN_CITY = "City";
//    private static final String ADDRESS_COLUMN_STATE = "State";
//    private static final String ADDRESS_COLUMN_ZIP_CODE = "ZipCode";
//
//    // Create Address
//    private static final String CREATE_ADDRESS_TABLE =
//            "CREATE TABLE " + ADDRESS + "(" +
//                    ADDRESS_COLUMN_ID + " INTEGER PRIMARY KEY," +
//                    ADDRESS_COLUMN_CONTACT_INFORMATION_ID + " INTEGER NOT NULL," +
//                    ADDRESS_COLUMN_STREET + " TEXT NOT NULL," +
//                    ADDRESS_COLUMN_HOUSE_NUMBER + " INTEGER NOT NULL," +
//                    ADDRESS_COLUMN_EXTRA + " TEXT," +
//                    ADDRESS_COLUMN_CITY + " TEXT NOT NULL," +
//                    ADDRESS_COLUMN_STATE + " TEXT NOT NULL," +
//                    ADDRESS_COLUMN_ZIP_CODE + " INT NOT NULL," +
//                    "FOREIGN KEY (" + ADDRESS_COLUMN_CONTACT_INFORMATION_ID + ") REFERENCES " + CONTACT_INFORMATION + "(" + CONTACT_INFORMATION_COLUMN_ID + ")" +
//                    ");";
//
//    public SQLiteDatabaseHelper(Context context) {
//        this(context, DATABASE_NAME, null, 1);
//    }
//
//    public SQLiteDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        try {
//            // Enable the foreign keys in SQLite
//            db.execSQL("PRAGMA foreign_keys = ON");
//
//            // create tables
//            db.execSQL(CREATE_FAMILY_MEMBER_TABLE);
//            db.execSQL(CREATE_MEDICAL_HISTORY_TABLE);
//            db.execSQL(CREATE_CONTACT_INFORMATION_TABLE);
//            db.execSQL(CREATE_PHONE_NUMBER_TABLE);
//            db.execSQL(CREATE_EMAIL_TABLE);
//            db.execSQL(CREATE_ADDRESS_TABLE);
//            Log.i(TAG, "Created DB");
//        } catch (SQLiteException e) {
//            Log.e(TAG, e.toString());
//        }
//    }
//
//    @Override
//    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
//        try {
//            db.execSQL(DROP_TABLE + FAMILY_MEMBER);
//            db.execSQL(DROP_TABLE + MEDICAL_HISTORY);
//            db.execSQL(DROP_TABLE + CONTACT_INFORMATION);
//            db.execSQL(DROP_TABLE + PHONE_NUMBER);
//            db.execSQL(DROP_TABLE + EMAIL);
//            db.execSQL(DROP_TABLE + ADDRESS);
//            onCreate(db);
//            Log.i(TAG, "Upgraded DB");
//        } catch (SQLiteException e) {
//            Log.e(TAG, e.toString());
//        }
//    }
//
//    public boolean insertFamilyMember(String firstName, String lastName, String gender) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(FAMILY_MEMBER_COLUMN_FIRST_NAME, firstName);
//        contentValues.put(FAMILY_MEMBER_COLUMN_LAST_NAME, lastName);
//        contentValues.put(FAMILY_MEMBER_COLUMN_GENDER, gender);
//        long result = db.insert(FAMILY_MEMBER, null, contentValues);
//        if (result == -1) {
//            return false;
//        }
//        else {
//            return true;
//        }
//    }
//
//    public Cursor getAllFamilyMembers() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        final String QUERY = "SELECT * FROM " + FAMILY_MEMBER + "";
//        Cursor res = db.rawQuery(QUERY, null);
//
//        /*if (res.getCount() == 0) {
//            Log.e(TAG, "error querying family members");
//            return null;
//        }
//        while (res.moveToNext()) {
//            res.getInt(0);
//            res.getString(1);
//            res.getString(2);
//            res.getString(3);
//        }*/
//
//        return res;
//    }
//
//    public boolean updateFamilyMember(String id, String firstName, String lastName, String gender) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(FAMILY_MEMBER_COLUMN_ID, id);
//        values.put(FAMILY_MEMBER_COLUMN_FIRST_NAME, firstName);
//        values.put(FAMILY_MEMBER_COLUMN_LAST_NAME, lastName);
//        values.put(FAMILY_MEMBER_COLUMN_GENDER, gender);
//        db.update(FAMILY_MEMBER, values, "ID = ?", new String[] { id });
//        return true;
//    }
//
//    public int deleteFamilyMember(String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(FAMILY_MEMBER, "ID = ?", new String[] { id });
//    }
//}
