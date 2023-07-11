import java.util.UUID;

public class Program {

	public static void main(String[] args) {

		User        user1 = new User("mario", 1000);
		User        user2 = new User("marina", 500);
		Transaction trans1 = new Transaction(user1, user2, Transaction.category.credit, 100);
		Transaction trans2 = new Transaction(user1, user2, Transaction.category.credit, 300);

		user1.getTransactions().addTransaction(trans1);
		user1.getTransactions().addTransaction(trans2);

		System.out.println(user1.getTransactions().toArray()[0].toString());
		System.out.println(user1.getTransactions().toArray()[1].toString());

		user1.getTransactions().removeTransaction(trans2.getID());

		System.out.println(user1.getTransactions().toArray()[1].toString());
	}
}
