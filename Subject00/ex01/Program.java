/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   Program.java                                       :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: mlongo <mlongo@student.42.fr>              +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2023/07/03 14:52:07 by mlongo            #+#    #+#             */
/*   Updated: 2023/07/24 12:37:06 by mlongo           ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

import java.util.Scanner;

public class  Program {
   public static void main(String[] args) {
	int n;
	int i = 2;
	Scanner getnum = new Scanner(System.in);

	n = getnum.nextInt();
	if (n == 2 || n == 3) {
		System.out.println("true 1");
		getnum.close();
		System.exit(0);
	}

	if (n < 2) {
		System.err.println("IllegalArgument");
		getnum.close();
		System.exit(-1);
	}

	else
		while (i < n / 2 + 1) {
			if (n % i++ == 0) {
				System.out.println("false " + (i - 2));
				getnum.close();
				System.exit(0);
			}
		}
	System.out.println("true " + (i - 2));
	getnum.close();
	System.exit(0);
   }
}
