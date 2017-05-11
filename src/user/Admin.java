package user;

public class Admin extends User {
    public Admin(String userName, String password) {
        this.userName = userName;
        passHash = hashPass(password);
    }
}