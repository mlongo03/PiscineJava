/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   Program.java                                       :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: mlongo <mlongo@student.42.fr>              +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2023/07/03 16:54:25 by mlongo            #+#    #+#             */
/*   Updated: 2023/07/05 16:03:46 by mlongo           ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

import java.util.Scanner;

public class Program {
   public static void main(String[] args) {
	int i = 0;
	int min = -1;
	int n = 0;
	int nweek = 1;
	long grades = 0;
	Scanner getline = new Scanner(System.in);
	String  line = getline.nextLine();

	while (n != 42 && nweek != 18) {
		if (!line.equals("Week " + nweek)) {
			if (line.equals("42")) {
				break ;
			}
			System.err.println("IllegalArgument");
			System.exit(-1);
		}
		nweek++;
		i = 0;
		min = -1;
		while (i < 5) {
			n = getline.nextInt();
			if (n == 42) {
				break ;
			}
			if (min < 0) {
				min = n;
			}
			else if (n < min) {
				min = n;
			}
			i++;
		}
		if (n != 42 && nweek != 18) {
			grades = grades + PowerTen(min, nweek - 1);
			line = getline.nextLine();
			line = getline.nextLine();
		}
	}
	i = 1;
	while (grades > 0) {
		System.out.print("Week " + i);
		for (long j = grades % 10; j > 0; j--) {
			System.out.print("=");
		}
		System.out.println(">");
		grades = grades / 10;
		i++;
	}
	getline.close();
   }
	private static long PowerTen(long num, int k) {
		long powten = 1;
		for (int i = 1; i < k; i++){
			powten = powten * 10;
		}
		num = num * powten;
		return (num);
	}
}
