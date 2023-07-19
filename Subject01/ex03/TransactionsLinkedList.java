import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {

	private class Node {

		private Transaction	Node;
		private Node Next;
	}

	public class TransactionNotFoundException extends RuntimeException {
		public TransactionNotFoundException(String message) {
			super(message);
		}
	}
	
	private Node head;
	private int size;

	public TransactionsLinkedList() {
		this.size = 0;
	}

	@Override
	public void addTransaction(Transaction transaction) {

		if (this.head == null) {
			this.head = new Node();
		}
		else {
			this.head.Next = new Node();
			this.head.Next.Node = this.head.Node;
		}
		this.head.Node = transaction;
		this.size++;
	}

	@Override
	public void removeTransaction(UUID id) throws TransactionNotFoundException {

		if (this.head.Node.getID() == id) {
			this.head.Node = this.head.Next.Node;
			this.size--;
		}

		while (this.head.Next != null) {
			if (this.head.Next.Node.getID() == id) {
				this.head.Next.Node = this.head.Next.Next.Node;
				this.size--;
				return ;
			}
			this.head = this.head.Next;
		}
		throw new TransactionNotFoundException("Transaction not found");
	}

	@Override
	public Transaction[] toArray() {

		int				i = 0;
		Node			tmpNode = this.head;
		Transaction[]	arrayList = new Transaction[this.size];

		while (i < this.size) {
			arrayList[i] = tmpNode.Node;
			i++;
			tmpNode = tmpNode.Next;
		}
		return (arrayList);
	}
}
