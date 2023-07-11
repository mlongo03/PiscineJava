/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   Program.java                                       :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: mlongo <mlongo@student.42.fr>              +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2023/07/06 19:03:27 by mlongo            #+#    #+#             */
/*   Updated: 2023/07/10 14:31:47 by mlongo           ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

public class Program {
    public static void main(String[] args) {
        User user1 = new User("John", 100.0);
        User user2 = new User("Jane", 50.0);

        System.out.println("User 1:");
        System.out.println("ID: " + user1.getId());
        System.out.println("Name: " + user1.getName());
        System.out.println("Balance: " + user1.getBalance());

        System.out.println();

        System.out.println("User 2:");
        System.out.println("ID: " + user2.getId());
        System.out.println("Name: " + user2.getName());
        System.out.println("Balance: " + user2.getBalance());
    }
}

