package users;

public class Student extends User {
    public Student(String userID, String name, String email, String phoneNumber, String address) {
        super(userID, name, email, phoneNumber, address);
    }

    @Override
    public int getMaxBooks() {
        return 5;
    }
}
