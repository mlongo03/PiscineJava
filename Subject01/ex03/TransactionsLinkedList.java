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

		Node tmpNode = new Node();

		tmpNode.Node = transaction;
		tmpNode.Next = this.head;
		this.head = tmpNode;
		this.size++;
	}

	@Override
	public void removeTransaction(UUID id) throws TransactionNotFoundException {

		Node tmp = this.head;

		if (tmp.Node.getID() == id) {
			tmp = tmp.Next;
			this.size--;
			return ;
		}

		while (tmp.Next != null) {
			if (tmp.Next.Node.getID() == id) {
				tmp.Next = tmp.Next.Next;
				this.size--;
				return ;
			}
			tmp = tmp.Next;
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
