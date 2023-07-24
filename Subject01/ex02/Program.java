public class Program {

    public static void main(String[] args) {

        User            user1 = new User();
        User            user2 = new User();
        User            user3 = new User();
        UsersArrayList  list = new UsersArrayList();

        list.addUser(user1);
        list.addUser(user2);
        list.addUser(user3);
        System.out.println(list.getUserById(1).getId());
        System.out.println(list.getUserByIndex(1).getId());
        System.out.println(list.getUserById(2).getId());
        System.out.println(list.getUserByIndex(2).getId());
    }
}
