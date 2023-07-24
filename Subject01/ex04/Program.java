
class	Program {
	static public void	main(String args[]) {
		TransactionsService	ts = new TransactionsService();
		Transaction			trs[];
		User				Lorenzo = new User("Lorenzo", 1000);
		User				Flaviano = new User("Flaviano", 2000);
		User				Francesco = new User("Francesco", 3000);
		User				Manuele = new User("Manuele", 4000);

		ts.addUser(Lorenzo);
		ts.addUser(Flaviano);
		ts.addUser(Francesco);
		ts.addUser(Manuele);
		System.out.println("Before: ");
		System.out.println("Flaviano's balance: " + ts.RetrieveUserBalance(Flaviano.getID()));
		System.out.println("Lorenzo's balance: " + ts.RetrieveUserBalance(Lorenzo.getID()));
		System.out.println("Francesco's balance: " + ts.RetrieveUserBalance(Francesco.getID()));
		System.out.println("Manuele's balance: " + ts.RetrieveUserBalance(Manuele.getID()));
		ts.DoTransaction(Lorenzo, Flaviano, Transaction.category.credit, 1000);
		ts.DoTransaction(Manuele, Francesco, Transaction.category.credit, 1000);
		ts.DoTransaction(Flaviano, Manuele, Transaction.category.credit, 2000);
		System.out.println("------------------------------------------------");
		System.out.println("After: ");
		System.out.println("Flaviano's balance: " + ts.RetrieveUserBalance(Flaviano.getID()));
		System.out.println("Lorenzo's balance: " + ts.RetrieveUserBalance(Lorenzo.getID()));
		System.out.println("Francesco's balance: " + ts.RetrieveUserBalance(Francesco.getID()));
		System.out.println("Manuele's balance: " + ts.RetrieveUserBalance(Manuele.getID()));
		System.out.println("------------------------------------------------");
		trs = ts.RetrieveUserTransaction(Flaviano.getID());
		System.out.println("Flaviano's transactions: ");
		for (Transaction tr : trs) {
			System.out.println(tr);
		}
		trs = ts.RetrieveUserTransaction(Francesco.getID());
		System.out.println("Francesco's transactions: ");
		for (Transaction tr : trs) {
			System.out.println(tr);
		}
		trs = ts.RetrieveUserTransaction(Lorenzo.getID());
		System.out.println("Lorenzo's transactions: ");
		for (Transaction tr : trs) {
			System.out.println(tr);
		}
		trs = ts.RetrieveUserTransaction(Manuele.getID());
		System.out.println("Manuele's transactions: ");
		for (Transaction tr : trs) {
			System.out.println(tr);
		}
		System.out.println("------------------------------------------------");
		System.out.println("check validity: ");
		trs = ts.CheckValidity();
		if (trs == null) {
			System.out.println("Nothing to report");
		} else {
			for (Transaction tr : trs) {
				System.out.println(tr);
			}
		}
		System.out.println("------------------------------------------------");
		trs = ts.RetrieveUserTransaction(Flaviano.getID());
		ts.RemoveUserTransaction(Flaviano.getID(), trs[1].getID());
		trs = ts.RetrieveUserTransaction(Flaviano.getID());
		System.out.println("Flaviano's transactions after remove: ");
		for (Transaction tr : trs) {
			System.out.println(tr);
		}
		System.out.println("------------------------------------------------");
		System.out.println("check validity: ");
		trs = ts.CheckValidity();
		for (Transaction tr : trs) {
			System.out.println(tr);
		}
		System.out.println("------------------------------------------------");
	}
}
