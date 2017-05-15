package user;

import data.Data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    protected String userName;
    protected String passHash;

    public void changePass(String newPass) {
        passHash = hashPass(newPass);
    }

    public String getPassHash() {
        return passHash;
    }

    public String getUserName() {
        return userName;
    }

    public void changeUserName(String userName) {
        this.userName = userName;
    }

    public static User getUserByUserName(String userName) {
        for (User user: Data.passengers) {
            if (user.userName.equals(userName)) {
                return user;
            }
        }
        for (User user: Data.admins) {
            if (user.userName.equals(userName)) {
                return user;
            }
        }
        return null;
    }

    public static String hashPass(String password) {
        if (password  ==  null || password.length() == 0){
            return null;
        }
        try {
            MessageDigest m = MessageDigest.getInstance("SHA-1");
            m.update(password.getBytes());
            byte messageDigest[] = m.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) { /* impossible! */ }
        return null;
    }
}