package com.team06.roadangel.model;

/**
 * User: David.Pechacek
 * Date: 8/25/12
 * Time: 11:38 AM
 */
public class User {
    private int userId;
    private String userToken;
//    private String userName;
//    private String userPw;
//    private int remember;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getUserPw() {
//        return userPw;
//    }
//
//    public void setUserPw(String userPw) {
//        this.userPw = userPw;
//    }
//
//    public int getRemember() {
//        return remember;
//    }
//
//    public void setRemember(int remember) {
//        this.remember = remember;
//    }
}
