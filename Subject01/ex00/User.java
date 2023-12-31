/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   User.java                                          :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: mlongo <mlongo@student.42.fr>              +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2023/07/05 17:29:16 by mlongo            #+#    #+#             */
/*   Updated: 2023/07/06 19:26:41 by mlongo           ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

public class User {
    private int identifier;
    private String name;
    private double balance;

    public User(int identifier, String name, double balance) {
        this.identifier = identifier;
        this.name = name;
        if (balance < 0) {
            System.err.println("wrong amount of balance");
            balance = 0;
        }
        else {
            this.balance = balance;
        }
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

