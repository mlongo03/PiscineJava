/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   Program.java                                       :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: mlongo <mlongo@student.42.fr>              +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2023/07/03 14:06:08 by mlongo            #+#    #+#             */
/*   Updated: 2023/07/03 14:25:26 by mlongo           ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

public class Program {
   public static void main(String[] args) {
	int number = 479598;
	int sum = 0;

	while (number > 10) {
		sum = sum + (number % 10);
		number /= 10;
	}
	sum = sum + (number % 10);
	System.out.println(sum);
   }
}
