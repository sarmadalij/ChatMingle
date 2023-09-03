package com.sarmadali.chatmingle.Models;

public class Users {

    String profilePic, userName, eMail, passWord, userId, lastMessage;

    //constructor
    public Users(String profilePic, String userName, String eMail,
                 String passWord, String userId, String lastMessage) {

        this.profilePic = profilePic;
        this.userName = userName;
        this.eMail = eMail;
        this.passWord = passWord;
        this.userId = userId;
        this.lastMessage = lastMessage;
    }

    //empty constructor for firebase
    public Users(){}

    //signup constructor
    public Users(String userName, String eMail, String passWord) {

        this.userName = userName;
        this.eMail = eMail;
        this.passWord = passWord;

    }
    //getter and setter
    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
