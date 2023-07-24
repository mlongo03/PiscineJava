/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   Program.java                                       :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: mlongo <mlongo@student.42.fr>              +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2023/07/05 16:27:30 by mlongo            #+#    #+#             */
/*   Updated: 2023/07/24 13:11:46 by mlongo           ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */


public class Program {
    public static void main(String[] args) {
        User user1 = new User(1, "John", 100.0);
        User user2 = new User(2, "Jane", 50.0);

        Transaction transaction = new Transaction(user1, user2, "debit", 20.0);

        System.out.println("User 1:");
        System.out.println("Identifier: " + user1.getIdentifier());
        System.out.println("Name: " + user1.getName());
        System.out.println("Balance: " + user1.getBalance());

        System.out.println();

        System.out.println("User 2:");
        System.out.println("Identifier: " + user2.getIdentifier());
        System.out.println("Name: " + user2.getName());
        System.out.println("Balance: " + user2.getBalance());

        System.out.println();

        System.out.println("Transaction:");
        System.out.println("Identifier: " + transaction.getIdentifier());
        System.out.println("Recipient: " + transaction.getRecipient().getName());
        System.out.println("Sender: " + transaction.getSender().getName());
        System.out.println("Transfer Category: " + transaction.getTransferCategory());
        System.out.println("Transfer Amount: " + transaction.getTransferAmount());
    }
}

