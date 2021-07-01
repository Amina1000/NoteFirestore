package com.example.lesson10;

/**
 * homework com.example.lesson10
 *
 * @author Amina
 * 24.06.2021
 */
public class User {

    public static  String nameUser;
    private static User userData = new User();
    public User() {

    }
      public static User getUserData(String name) {
        nameUser = name;
        return userData;
    }

}
