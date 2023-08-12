package edu.Roma42.numbers;


class IllegalNumberException extends RuntimeException {

	public IllegalNumberException(String message) {
		super(message);
	}
}

public class NumberWorker {

	public boolean isPrime(int n) {

	int i = 2;
	
	if (n < 2) {
		throw new IllegalNumberException("IllegalNumber");
	}
	else if (n == 2 || n == 3) {
		return(true);
	}
	else
		while (i < n / 2 + 1) {
			if (n % i++ == 0) {
				return(false);
			}
		}
	return(true);
	}

	public int digitsSum(int number) {
		int	sum;

		sum = 0;
		if (number < 0)
			number = -number;
		while (number / 10 != 0) {
			sum += number % 10;
			number /= 10;
		}
		sum += number;
		return (sum);
	}
}
