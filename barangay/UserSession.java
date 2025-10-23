/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package barangay;

public class UserSession {
    private static int patientid;
    private static String firstname;
    private static String lastname;
    private static String middlename;
    private static String gmail;
    private static String gender;
    private static String number;
    private static int age;

    // ✅ Correct and complete method
    public static void setUser(int patientId, String fname, String lname, String mname, String mail , String Gender, String num, int Age) {
        patientid = patientId;
        firstname = fname;
        lastname = lname;
        middlename = mname;
        gmail = mail;
        gender = Gender;
        number = num;
        age = Age;
    } // ← this closing brace was missing before!

    public static int getUserId() {
        return patientid;
    }

    public static String getFirstname() {
        return firstname;
    }

    public static String getLastname() {
        return lastname;
    }

    public static String getMiddlename() {
        return middlename;
    }

    public static String getGmail() {
        return gmail;
    }
    public static String getGender() {
        return gender;
    }

    public static String getNumber() {
        return number;
    }

    public static int getAge() {
        return age;
    }

    public static void clearSession() {
        patientid = 0;
        firstname = null;
        lastname = null;
        middlename = null;
        gmail = null;
        gender=null;
        number = null;
        age = 0;
    }
  
}
