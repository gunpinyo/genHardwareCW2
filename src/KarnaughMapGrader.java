
public class KarnaughMapGrader {
	protected static int totalVar; // number of total variable in KarnaughMap
	protected static boolx[] kmap; // KarnaughMap
	protected static boolean isMinterm; // true - minterm // false - maxterm
	protected static int[] numLayer; // number of layers that map to current cell
	
	public static int powInt(int base,int expo) {
		assert base > 0 && expo >= 0;
		int result = 1;
		for(;expo>0;expo--)
			result *= base;
		return result;
	}
	
	public static int reverseDigit(int size,int base,int number) {
		assert number <= powInt(base,size);
		int result = 0;
		for(;size>0;size--) {
			result = result*base + number%base;
			number /= base;
		}
		return result;
	}
	
	public static int numVarInPattern(int pattern) {
		int n = 0;
		for(;pattern!=0;pattern/=3)
			if(pattern%3 != 2)
				n++;
		return n;
	}
	
	protected static void updateLabel(int numVar,int pattern,int prefix,int update) {
		if(numVar == 0) {
			numLayer[reverseDigit(totalVar,2,prefix)] += update;
			return;
		}
		if(pattern%3 == 0 || pattern%3 == 2)
			updateLabel(numVar-1,pattern/3,prefix*2+0,update);
		if(pattern%3 == 1 || pattern%3 == 2)
			updateLabel(numVar-1,pattern/3,prefix*2+1,update);
	}
	
	protected static boolean groupable(int numVar,int pattern,int prefix) {
		if(numVar == 0) {
			switch(kmap[reverseDigit(totalVar,2,prefix)]) {
				case F: return !isMinterm;
				case T: return  isMinterm;
				case X: return true;
			}
		}
		switch(pattern%3) {
			case 0: return groupable(numVar-1,pattern/3,prefix*2+0);
			case 1: return groupable(numVar-1,pattern/3,prefix*2+1);
			default:if(!groupable(numVar-1,pattern/3,prefix*2+0))
						return false;
					return groupable(numVar-1,pattern/3,prefix*2+1);
		}
	}
	
	protected static boolean deletable(int numVar,int pattern,int prefix) {
		if(numVar == 0)
			return numLayer[reverseDigit(totalVar,2,prefix)] > 1;
		switch(pattern%3) {
			case 0: return deletable(numVar-1,pattern/3,prefix*2+0);
			case 1: return deletable(numVar-1,pattern/3,prefix*2+1);
			default:if(!deletable(numVar-1,pattern/3,prefix*2+0))
						return false;
					return deletable(numVar-1,pattern/3,prefix*2+1);
		}
	}
	
	// main method
	// pattern is encode by base3 which 0 = ~var, 1 = var, 2 = don't care 
	public static int grade(int totalVar,boolx[] kmap,boolean isMinterm) {
		assert kmap.length == powInt(2,totalVar);
		int cost = 0;
		boolean[] groupAbility = new boolean[powInt(3,totalVar)];
		KarnaughMapGrader.totalVar = totalVar;
		KarnaughMapGrader.kmap = kmap;
		KarnaughMapGrader.isMinterm = isMinterm;
		numLayer = new int[kmap.length];
		for(int pattern=0;pattern<powInt(3,totalVar);pattern++)
			if(groupAbility[pattern] = groupable(totalVar,pattern,0))
				updateLabel(totalVar,pattern,0,1);
		for(int numVar=totalVar;numVar>=0;numVar--)
			for(int pattern=0;pattern<powInt(3,totalVar);pattern++) {
				if(!groupAbility[pattern]);
				else if(numVarInPattern(pattern) != numVar);
				else if(deletable(totalVar,pattern,0))
					updateLabel(totalVar,pattern,0,-1);
				else
					cost += numVar;
			}
		return cost;
	}
}
