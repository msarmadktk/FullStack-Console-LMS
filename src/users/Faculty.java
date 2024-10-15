package users;

public class Faculty extends User {
    public Faculty(String userID, String name, String email, String phoneNumber, String address) {
        super(userID, name, email, phoneNumber, address, "Faculty");
    }

    @Override
    public int getMaxBooks() {
        return 10;
    }
}
