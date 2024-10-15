package users;

public class PublicMember extends User {
    public PublicMember(String userID, String name, String email, String phoneNumber, String address) {
        super(userID, name, email, phoneNumber, address, "PublicMember");
    }

    @Override
    public int getMaxBooks() {
        return 3;
    }
}
