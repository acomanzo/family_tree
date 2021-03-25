package com.example.family_tree_temp.Database;

import android.content.Context;
import android.util.Log;

import com.example.family_tree_temp.DatabaseAccessObjects.AddressDao;
import com.example.family_tree_temp.DatabaseAccessObjects.ContactInformationDao;
import com.example.family_tree_temp.DatabaseAccessObjects.EmailDao;
import com.example.family_tree_temp.DatabaseAccessObjects.FamilyMemberDao;
import com.example.family_tree_temp.DatabaseAccessObjects.MedicalHistoryNoteDao;
import com.example.family_tree_temp.DatabaseAccessObjects.PhoneNumberDao;
import com.example.family_tree_temp.Models.Address;
import com.example.family_tree_temp.Models.ContactInformation;
import com.example.family_tree_temp.Models.Email;
import com.example.family_tree_temp.Models.FamilyMember;
import com.example.family_tree_temp.Models.MedicalHistory;
import com.example.family_tree_temp.Models.PhoneNumber;
import com.example.family_tree_temp.Models.City;
import com.example.family_tree_temp.Models.State;
import com.example.family_tree_temp.Models.Zipcode;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {
        FamilyMember.class,
        MedicalHistory.class,
        ContactInformation.class,
        PhoneNumber.class,
        Email.class,
        City.class,
        State.class,
        Zipcode.class,
        Address.class
}, version = 1, exportSchema = false)
public abstract class FamilyTreeRoomDatabase extends RoomDatabase {

    public abstract FamilyMemberDao familyMemberDao();
    public abstract MedicalHistoryNoteDao medicalHistoryNoteDao();
    public abstract ContactInformationDao contactInformationDao();
    public abstract PhoneNumberDao phoneNumberDao();
    public abstract EmailDao emailDao();
    public abstract AddressDao addressDao();

    private static FamilyTreeRoomDatabase INSTANCE;

    static FamilyTreeRoomDatabase getDatabase(final Context context) {
        // to re build database without doing migration, just do this
        context.deleteDatabase("FamilyTreeDatabase");
        if (INSTANCE == null) {
            synchronized (FamilyTreeRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FamilyTreeRoomDatabase.class, "FamilyTreeDatabase")
                            .fallbackToDestructiveMigration()
                            .build();
                    Log.i("FamilyTreeRoomDatabase", "Created DB");
                }
            }
        }
        return INSTANCE;
    }
}
