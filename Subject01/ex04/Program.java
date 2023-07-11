
class	Program {
	static public void	main(String args[]) {
		TransactionsService	ts = new TransactionsService();
		Transaction			trs[];
		User				giovanni = new User("Giovanni", 1000);
		User				marco = new User("Marco", 2000);
		User				adistef = new User("Adistef", 3000);
		User				afraccal = new User("Afraccal", 4000);

		ts.addUser(giovanni);
		ts.addUser(marco);
		ts.addUser(adistef);
		ts.addUser(afraccal);
		System.out.println("Before: ");
		System.out.println("Marco's balance: " + ts.RetrieveUserBalance(marco.getID()));
		System.out.println("Giovanni's balance: " + ts.RetrieveUserBalance(giovanni.getID()));
		System.out.println("Adistef's balance: " + ts.RetrieveUserBalance(adistef.getID()));
		System.out.println("Afraccal's balance: " + ts.RetrieveUserBalance(afraccal.getID()));
		ts.DoTransaction(marco, giovanni, Transaction.category.credit, 1000);
		ts.DoTransaction(adistef, afraccal, Transaction.category.credit, 1000);
		ts.DoTransaction(afraccal, marco, Transaction.category.credit, 2000);
		System.out.println("------------------------------------------------");
		System.out.println("After: ");
		System.out.println("Marco's balance: " + ts.RetrieveUserBalance(marco.getID()));
		System.out.println("Giovanni's balance: " + ts.RetrieveUserBalance(giovanni.getID()));
		System.out.println("Adistef's balance: " + ts.RetrieveUserBalance(adistef.getID()));
		System.out.println("Afraccal's balance: " + ts.RetrieveUserBalance(afraccal.getID()));
		System.out.println("------------------------------------------------");
		trs = ts.RetrieveUserTransaction(marco.getID());
		System.out.println("Marco's transactions: ");
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
		trs = ts.RetrieveUserTransaction(marco.getID());
		ts.RetrieveUserTransaction(marco.getID());
		trs = ts.RetrieveUserTransaction(marco.getID());
		System.out.println("Marco's transactions after remove: ");
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
