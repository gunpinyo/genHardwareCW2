
public class Utility {

	public static <T> void arraySwap(T[] array, int a, int b) {
		T tmp = array[a];
		array[a] = array[b];
		array[b] = tmp;
	}

	public static <T> void rep4varKmap(T[] kmap) {
		arraySwap(kmap, 2, 3);
		arraySwap(kmap, 6, 7);
		arraySwap(kmap, 8, 12);
		arraySwap(kmap, 9, 13);
		arraySwap(kmap, 10, 15);
		arraySwap(kmap, 11, 14);
	}

	public static Integer[] intsToIntegers(int[] ints) {
		Integer[] integers = new Integer[ints.length];
		for (int i = 0; i < ints.length; i++)
			integers[i] = new Integer(ints[i]);
		return integers;
	}

	public static void printBoolxtables(int row, int col, boolx[][] kmaps) {
		for (int i = 0; i < row; i++, System.out.println())
			for (int k = 0; k < kmaps.length; k++, System.out.print("    "))
				for (int j = 0; j < col; j++)
					switch (kmaps[k][i * row + j]) {
						case F: System.out.print("0 "); break;
						case T: System.out.print("1 "); break;
						case X: System.out.print("X "); break;
					}
	}

}
