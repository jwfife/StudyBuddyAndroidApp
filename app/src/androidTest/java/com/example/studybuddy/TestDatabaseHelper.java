package com.example.studybuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.core.app.ApplicationProvider;

import com.example.studybuddy.db.DatabaseHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.SQLException;

public class TestDatabaseHelper {

    private DatabaseHelper DB;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        DB = DatabaseHelper.getInstance(context);

        DB.onUpgrade(DB.getWritableDatabase(), 1, 1);
        DB.onCreate(DB.getWritableDatabase());
    }

    @Before
    public void setUpEnrollment(){
        String email = "john@example.com";
        String courseID = "CS101";

        DB.insertEnrollment(email, courseID);
    }

    @After
    public void tearDown() {
        DB.close();
    }

    @Test
    public void testInsertRegistration() throws SQLException {
        boolean inserted = DB.insertUserData(
                "john@example.com",
                "Password123",
                "John",
                "Doe"
        );
        assertTrue(inserted);

        assertTrue(DB.checkEmail("john@example.com"));
        assertTrue(DB.checkEmailPassword("john@example.com", "Password123"));
        assertEquals("John", DB.getFirstName("john@example.com"));
        assertEquals("Doe", DB.getLastName("john@example.com"));
    }

    @Test
    public void testInsertEnrollment() {
        //insertion already done in @Before method setUpEnrollment
        assertTrue(DB.checkEnrollment("john@example.com", "CS101"));
    }

    @Test
    public void testRemoveEnrollment() {
        DB.removeEnrollment("john@example.com", "CS101");
        assertFalse(DB.checkEnrollment("john@example.com", "CS101"));
    }
}
