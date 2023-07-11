import java.util.ArrayList;

public class UsersArrayList implements UsersList {

    private User[] arrayList;
    private int size;
    private int users;
    
    public UsersArrayList() {

        this.arrayList = new User[10];
        this.size = 10;
        this.users = 0;
    }

    @Override
    public void addUser(User user) {

        if (this.size < this.users) {
            this.arrayList[this.users++] = user;
        }
        else {
            this.size += this.size / 2;
            User[] tmp = new User[this.size];
            for (int i = 0; i < this.arrayList.length; i++) {
                tmp[i] = this.arrayList[i];
            }
            this.arrayList[this.users++] = user;
        }
    }

    @Override
    public User getUserByIndex(int index) throws UserNotFoundException {

        if (index > this.users) {
            throw new UserNotFoundException("User index not found");
        }
        return (this.arrayList[index]);
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException {

        if (id > this.users) {
            throw new UserNotFoundException("User id not found");
        }
        return (this.arrayList[id]);
    }

    @Override
    public int nUsers() {

        return (this.users);
    }
}
