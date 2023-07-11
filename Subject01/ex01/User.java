/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   User.java                                          :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: mlongo <mlongo@student.42.fr>              +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2023/07/06 19:03:37 by mlongo            #+#    #+#             */
/*   Updated: 2023/07/10 14:32:06 by mlongo           ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

public class User {
    private final int id;
    private String name;
    private double balance;

    public User(String name, double balance) {
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        this.balance = balance;
    }

    // Getters for name, balance
    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    // Getters for id (read-only)
    public int getId() {
        return id;
    }
}

