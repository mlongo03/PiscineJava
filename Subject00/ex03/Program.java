/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   Program.java                                       :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: mlongo <mlongo@student.42.fr>              +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2023/07/03 16:54:25 by mlongo            #+#    #+#             */
/*   Updated: 2023/07/03 17:52:17 by mlongo           ###   ########.fr       */
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
	String week = "";
	Scanner getline = new Scanner(System.in);
	String  line = getline.nextLine();

	week = week.concat("week " + nweek);
	if (!week.equals(line)) {
		System.err.println("wrong week order");
		System.exit(-1);
	}
	while (i < 5) {
		n = getline.nextInt();
		if (n == 42)
			break ;
		if (min < 0)
			min = n;
		else if (n < min)
			min = n;
		i++;
	}
	grades = grades * 10 + min;


	System.out.println(grades);
	if (n < 0) {
		System.err.println("negative vote");
		System.exit(-1);
	}
   }
}
