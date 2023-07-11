/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   Program.java                                       :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: mlongo <mlongo@student.42.fr>              +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2023/07/04 10:41:44 by mlongo            #+#    #+#             */
/*   Updated: 2023/07/06 18:48:44 by mlongo           ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

import java.util.Scanner;

public class Program {
   public static void main(String[] args) {
	Scanner getline = new Scanner(System.in);
	String line = getline.nextLine();
    char[] charArray = line.toCharArray();
	int lenght = line.length();
	int[][] lettersData = new int[lenght][2];
	int nletters = 0;

	for (char c : charArray) {
  		nletters = addToArray(c, lettersData, nletters);
	}
	if (Check(lettersData, nletters) == 1) {
		System.err.println("IllegalArgument");
		System.exit(-1);
	}
	BubbleSort(lettersData, nletters);
	int n = 0;
	int k = 0;
	int limitletters = 0;
	if (nletters < 10) {
		limitletters = nletters;
	}
	else {
		limitletters = 10;
	}
	for (int i = 10 + 2; i >= 0; i--) {
		k = 0;
		for (int j = nletters - 1; k < limitletters; j--) {
			n = map(lettersData[j][1], 0, lettersData[nletters - 1][1], 0, 10);
			if (i == n + 1) {
				if (lettersData[j][1] < 10) {
					System.out.print("  ");
				}
				else if (lettersData[j][1] < 100) {
					System.out.print(" ");
				}
				System.out.print(lettersData[j][1]);
			}
			else if (i <= n && i > 0) {
				System.out.print("  #");
			}
			else if (i == 0) {
				System.out.print("  " + (char)lettersData[j][0]);
			}
			k++;
		}
		System.out.println();
}
	getline.close();
	System.exit(0);
}

public static int Check(int[][] Array, int nletters) {
	int i = 0;
	while (i < nletters) {
		if (Array[i][1] > 999 || Array[i][0] > 65535) {
			System.out.println("IllegalArgument");
			return (1);
		}
		i++;
	}
	return (0);
}

public static void BubbleSort(int[][] array, int nletters) {
    for (int i = 0; i < nletters - 1; i++) {
        for (int j = 0; j < nletters - i - 1; j++) {
            if (array[j][1] > array[j + 1][1]) {
                int temp = array[j][0];
                int temp1 = array[j][1];
                array[j][1] = array[j + 1][1];
                array[j][0] = array[j + 1][0];
                array[j + 1][1] = temp1;
                array[j + 1][0] = temp;
            }
        }
    }
}

	public static int addToArray(char c, int[][] lettersData, int nletters) {
		int i = 0;
		for (; i < nletters; i++) {
			if (lettersData[i][0] == c) {
				lettersData[i][1] += 1;
				break ;
			}
		}

		if (i == nletters) {
			lettersData[i][0] = c;
			lettersData[i][1] += 1;
			nletters++;
		}
		return (nletters);
	}

	public static int	map(int x, int in_min, int in_max, int out_min, int out_max) {
		return ((x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min);
	}
}
