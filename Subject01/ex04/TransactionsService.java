/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   TransactionsService.java                           :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: mlongo <mlongo@student.42.fr>              +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2023/07/11 15:02:57 by mlongo            #+#    #+#             */
/*   Updated: 2023/07/12 12:40:29 by mlongo           ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

import java.util.UUID;

public class TransactionsService {

	UsersArrayList UserArray;

	public TransactionsService() {
		UserArray = new UsersArrayList();
	}

	public void addUser(User user) {
		this.UserArray.addUser(user);
	}

	public int RetrieveUserBalance(int id) {
		return (UserArray.getUserById(id).getBalance());
	}

	public void DoTransaction(User recipient, User sender, Transaction.category transferCategory, int transferAmount) {

		Transaction Debit;
		Transaction Credit;

		if (transferCategory == Transaction.category.credit) {
			Debit = new Transaction(recipient, sender, Transaction.category.debit, -transferAmount);
			Credit = new Transaction(recipient, sender, Transaction.category.credit, transferAmount);
			Credit.setID(Debit.getID());
			recipient.setBalance(recipient.getBalance() + transferAmount);
			sender.setBalance(sender.getBalance() - transferAmount);
		}
		else {
			Debit = new Transaction(recipient, sender, Transaction.category.debit, transferAmount);
			Credit = new Transaction(recipient, sender, Transaction.category.credit, -transferAmount);
			Credit.setID(Debit.getID());
			recipient.setBalance(recipient.getBalance() + (-transferAmount));
			sender.setBalance(sender.getBalance() - (-transferAmount));
		}
		this.UserArray.getUserById(recipient.getID()).getTransactions().addTransaction(Credit);
		this.UserArray.getUserById(sender.getID()).getTransactions().addTransaction(Debit);
	}

	public Transaction[] RetrieveUserTransaction(int id) {
		return (UserArray.getUserById(id).getTransactions().toArray());
	}

	public void RemoveUserTransaction(int userID, UUID TransactionID) {
		UserArray.getUserById(userID).getTransactions().removeTransaction(TransactionID);
	}

	public Transaction[] CheckValidity() {

		TransactionsLinkedList UnpairedTrs = new TransactionsLinkedList();
		Transaction[] tmp;

		for (int i = 0; i < UserArray.nUsers(); i++) {
			tmp = UserArray.getUserByIndex(i).getTransactions().toArray();
			for(int j = 0; j < UserArray.getUserByIndex(i).getTransactions().getSize() ; j++) {
				if (!tmp[j].getRecipient().getTransactions().FindTransaction(tmp[j].getID())) {
					UnpairedTrs.addTransaction(tmp[j]);
				}
				if (!tmp[j].getSender().getTransactions().FindTransaction(tmp[j].getID())) {
					UnpairedTrs.addTransaction(tmp[j]);
				}
			}
		}
		return (UnpairedTrs.toArray());
	}
}
