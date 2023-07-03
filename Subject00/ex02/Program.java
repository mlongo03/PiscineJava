/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   Program.java                                       :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: mlongo <mlongo@student.42.fr>              +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2023/07/03 15:12:25 by mlongo            #+#    #+#             */
/*   Updated: 2023/07/03 18:59:29 by mlongo           ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

import java.util.Scanner;

public class Program {
   public static void main(String[] args) {
	int n;
	int i = 2;
	int sum = 0;
	int request = 0;
	Scanner getnum = new Scanner(System.in);

	n = getnum.nextInt();
	while (n != 42 && n > 2) {
		while (n > 10) {
			sum = sum + (n % 10);
			n /= 10;
		}
		sum = sum + (n % 10);
		while (i < sum) {
			if (sum % i++ == 0)
				break;
		}
		if (i == sum)
			request++;
		getnum = new Scanner(System.in);
		n = getnum.nextInt();
		sum = 0;
		i = 2;
	}
	if (n < 2) {
		System.err.println("IllegalArgument");
		System.exit(-1);
	}
	System.out.println("Count of coffee - request - " + request);
	System.exit(0);
   }
}
