import	java.io.*;
import	java.util.*;

public class signatures {
	private boolean load;
	private int maxsize;
	private HashMap<int[], int[]> map;
	ArrayList <char[]> result;

	public signatures() {
		load = false;
		maxsize = 0;
		map = new HashMap<int[], int[]>();
		result = new ArrayList<char[]>();
	}

	public void loadSignatures() {
		try (FileInputStream sig = new FileInputStream("signatures.txt")) {
			int read = 0;
			int keysize = 0;
			int valuesize = 0;

			while (true) {
				ArrayList<Integer> value = new ArrayList<Integer>();
				ArrayList<Integer> key = new ArrayList<Integer>();
				valuesize = 0;
				keysize = 0;

				while ((read = sig.read()) != ',') {
					if (read == -1) {
						break ;
					}
					value.add(read);
					valuesize++;
				}

				if (read == -1) {
					break ;
				}

				while ((read = sig.read()) != '\n') {
					if (read != ' ') {
						read = (read <= '9' ? read - '0' : read - 55) * 16;
						int read2 = sig.read();
						read2 = (read2 <= '9' ? read2 - '0' : read2 - 55);
						key.add(read + read2);
						keysize++;
					}
				}
				int[] keyarray = new int[keysize];
				int[] valuearray = new int[valuesize];

				for (int i = 0; i < keysize; i++) {
					keyarray[i] = key.get(i);
				}

				for (int i = 0; i < valuesize; i++) {
					valuearray[i] = value.get(i);
				}

				if (this.maxsize < keysize) {
					maxsize = keysize;
				}

				map.put(keyarray, valuearray);
			}
			this.load = true;
		}
		catch (FileNotFoundException e) {
				System.out.println("errore apertura file");
		}
		catch (IOException e) {
				System.out.println("IOexception");
		}
	}

	public ArrayList<char[]> compareFile(String filename) {

		try (FileInputStream file = new FileInputStream(filename)) {
		int[] read = new int[this.maxsize];
			for (int i = 0; i < this.maxsize && (read[i] = file.read()) != -1; i++) {
			}
			for (int	key[] : this.map.keySet()) {
				int[] type = this.map.get(key);
				int i = 0;
				for (; i < key.length; i++) {
					// System.out.println(key[i] )
					if (key[i] != read[i]) {
						break ;
					}
				}
				if (i == key.length) {
					System.out.println("PROCESSED");
					char[] str = new char[type.length];
					for (int j = 0; j < type.length; j++) {
						str[j] = (char)type[j];
					}
					this.result.add(str);
					return (this.result);
				}
			}
		}
		catch (FileNotFoundException e) {
				System.out.println("errore apertura file");
		}
		catch (IOException e) {
				System.out.println("IOexception");
		}
		System.out.println("UNDEFINED");
		return (this.result);
	}

	public void		printMap() {
		int	type[];

		if (!this.load) {
			return ;
		}
		for (int	key[] : this.map.keySet()) {
			for (int i = 0; i < key.length; i++) {
				System.out.printf(key[i] + " ");
			}
			System.out.print(": ");
			type = this.map.get(key);
			for (int i = 0; i < type.length; i++) {
				System.out.print((char) type[i]);
			}
			System.out.println();
		}
	}

}
