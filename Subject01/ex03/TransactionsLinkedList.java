import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {

	private Transaction	node;
	private int			size;

	public TransactionsLinkedList() {

		this.node = null;
		this.size = 0;
	}

	@Override
	public void addTransaction(Transaction transaction) {

		if (this.node != null) {
			transaction.setNext(this.node);
		}
		this.node = transaction;
		this.size++;
	}

	@Override
	public void removeTransaction(UUID id) throws TransactionNotFoundException {

		Transaction currTransaction = this.node;

		if (currTransaction.getID() == id) {
			this.node = this.node.getNext();
			this.size--;
		}

		while (currTransaction.getNext() != null) {
			if (currTransaction.getNext().getID() == id) {
				currTransaction.setNext(currTransaction.getNext().getNext());
				this.size--;
				return ;
			}
			currTransaction = currTransaction.getNext();
		}
		throw new TransactionNotFoundException("Transaction not found");
	}

	@Override
	public Transaction[] toArray() {

		int				i = 0;
		Transaction		tmp = this.node;
		Transaction[]	arrayList = new Transaction[this.size];

		while (i < this.size) {
			arrayList[i] = tmp;
			i++;
			tmp = tmp.getNext();
		}
		return (arrayList);
	}
}
