/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   Transaction.java                                   :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: mlongo <mlongo@student.42.fr>              +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2023/07/05 17:32:12 by mlongo            #+#    #+#             */
/*   Updated: 2023/07/06 18:43:18 by mlongo           ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

import java.util.UUID;

public class Transaction {
    private UUID identifier;
    private User recipient;
    private User sender;
    private String transferCategory;
    private double transferAmount;

    public Transaction(User recipient, User sender, String transferCategory, double transferAmount) {
        this.identifier = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.transferCategory = transferCategory;
        if ((transferAmount < 0 && transferCategory.equals("credit")) || (transferAmount > 0 && transferCategory.equals("debit"))) {
            System.err.println("Invalid transer amount");
            transferAmount = 0;
        }
        else {
            this.transferAmount = transferAmount;
        }
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public String getTransferCategory() {
        return transferCategory;
    }

    public double getTransferAmount() {
        return transferAmount;
    }
}


