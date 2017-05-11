package user;

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