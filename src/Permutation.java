
class Permutation {
	protected int[] pattern; // pattern of current permutation
	protected int total; // size of total pattern possible
	protected int n, r; // n and r of permutation
	protected boolean[] free; // which number is free

	protected int numberOfPermutation(int n, int r) {
		int result = 1;
		for (int i = 0; i < r; i++)
			result *= n - i;
		return result;
	}

	public Permutation(int n, int r) {
		assert 0 < r && r <= n : "cannot find nPr.";
		this.n = n;
		this.r = r;
		this.total = numberOfPermutation(n, r);
		this.pattern = new int[r];
		free = new boolean[n];
		reset();
	}

	/*public String toString() {
		return Arrays.toString(pattern) + " " + Arrays.toString(free);
	}*/

	protected void reset() {
		for (int i = 0; i < r; i++)
			pattern[i] = i;
		for (int i = 0; i < n; i++)
			free[i] = i >= r;
	}

	protected void next() {
		int p = r - 1; // pointer to current the current index of patten.
		free[pattern[p]] = true;
		int i = pattern[p] + 1;
		while (p < r) {
			for (; i < n; i++)
				if (free[i])
					break;
			if (i < n) {
				pattern[p] = i;
				free[i] = false;
				p++;
				i = 0;
			} else {
				free[pattern[p]] = true;
				p--;
				if (p == -1) {
					reset();
					return;
				}
				free[pattern[p]] = true;
				i = pattern[p] + 1;
			}
		}
	}
}