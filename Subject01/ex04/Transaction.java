import java.util.UUID;

public class Transaction {

	enum category {

		credit,
		debit
	}

	private UUID		id;
	private User		recipient;
	private User		sender;
	private category	transferCategory;
	private int			transferAmount;

	public Transaction (User recipient, User sender, category transferCategory, int transferAmount) {
		this.id = UUID.randomUUID();
		this.recipient = recipient;
		this.sender = sender;
		if (transferCategory == category.credit && transferAmount <= 0 || transferCategory == category.debit && transferAmount >= 0) {
			System.out.println("transaction rejected");
			this.transferCategory = null;
		}
		else {
			this.transferCategory = transferCategory;
			System.out.println("Transfer amount processed");
		}
		if (transferAmount > sender.getBalance()) {
			System.out.println("transaction rejected");
			this.transferAmount = 0;
		}
		else {
			this.transferAmount = transferAmount;
		}
	}

	@Override
    public String toString() {
        return ("Transaction [id=" + this.id + ", recipient=" + recipient.getName() + ", sender=" + sender.getName() + ", transferCategory=" + transferCategory + ", transferAmount=" + transferAmount + "]");
	}

	public UUID	getID() {
		return (this.id);
	}

	public void	setID(UUID newID) {
		this.id = newID;
	}

	public User getRecipient() {
		return (this.recipient);
	}

	public User getSender() {
		return (this.sender);
	}

	public category getTransferCategory() {
		return (this.transferCategory);
	}

	public int getTransferAmount() {
		return (this.transferAmount);
	}
}
