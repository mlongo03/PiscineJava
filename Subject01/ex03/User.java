public class User {

	public class UserNotFoundException extends RuntimeException {
		public UserNotFoundException(String message) {
			super(message);
		}
	}

	private int						id;
	private String  				name;
	private int						balance;
	private TransactionsLinkedList	transactions;

	public User (String name, int balance) {

        this.id = UserIdsGenerator.getInstance().generateId();
		this.name = name;
		if (balance < 0) {
			System.out.println("invalid balance");
			this.balance = 0;
		}
		else {
			this.balance = balance;
		}
		this.transactions = new TransactionsLinkedList();
	}

	@Override
    public String toString() {
        return ("User [id=" + id + ", name=" + name + ", balance=" + balance + "]");
	}

	public int getid() {
		return (id);
	}

	public String getName() {
		return (name);
	}

	public int getBalance() {
		return (balance);
	}

	public void setBalance(int balance) {
        if (balance < 0) {
			System.out.println("invalid balance");
		}
		else {
			this.balance = balance;
		}
    }

	public TransactionsLinkedList getTransactions() {
		return (this.transactions);
	}
}
