
public class StateToKarnaughMap implements Comparable<StateToKarnaughMap> {
	// stateDiagram[state][argumentToChangeState]
	public int[][] stateDiagram;
	// assignment that convert idealStateDiagram to stateDiagram
	// if state i is not used then h[i] = -1
	public int[] h; // must be bijective function
	public int score; // final score for sorting
	public boolean[] isMinterm; // each KarnaughMap use minterm or maxterm
	public boolx[][] kmaps; // KarnaughMap of each binary digit of stateDiagram
	
	public int log2(int n) {
		int i;
		for(i=0;(1<<i)<n;i++);
		return i;
	}
	
	public StateToKarnaughMap(int[][] idealStateDiagram,
			                  int argSize,int kmapsSize,int[] h){
		// Pre: argSize and idealStateDiagram.length must be multiple of two
		assert argSize == h.length;
		for(int i=0;i<idealStateDiagram.length;i++)
			assert idealStateDiagram[i].length == argSize;
		
		this.h = h.clone();
		stateDiagram = new int[idealStateDiagram.length][];
		for(int i=0;i<idealStateDiagram.length;i++) {
			if(h[i] == -1)
				continue;
			stateDiagram[h[i]] = new int[idealStateDiagram[i].length];
			for(int j=0;j<idealStateDiagram[i].length;j++)
				stateDiagram[h[i]][j] = h[idealStateDiagram[i][j]];
		}
		score = 0;
		isMinterm = new boolean[kmapsSize];
		kmaps = new boolx[kmapsSize][];
		int numVar = log2(argSize*stateDiagram.length);
		int[][] sd = new int[stateDiagram.length][];
		for(int i=0;i<sd.length;i++)
			if(stateDiagram[i] != null)
				sd[i] = stateDiagram[i].clone();
		for(int k=0;k<kmapsSize;k++) {
			kmaps[k] = new boolx[argSize*sd.length];
			for(int j=0;j<argSize;j++)
				for(int i=0;i<sd.length;i++) {
					if(sd[i] == null) {
						kmaps[k][j*sd.length+i] = boolx.X;
						continue;
					}
					if(sd[i][j]%2 == 1)
						kmaps[k][j*sd.length+i] = boolx.T;
					else
						kmaps[k][j*sd.length+i] = boolx.F;
					sd[i][j] >>= 1;
				}
			int maxterm = KarnaughMapGrader.grade(numVar, kmaps[k], false);
			int minterm = KarnaughMapGrader.grade(numVar, kmaps[k], true);
			isMinterm[k] = minterm <= maxterm;
			if(minterm <= maxterm)
				score += minterm;
			else
				score += maxterm;
		}
	}
	
	public int compareTo(StateToKarnaughMap compState) {
		return this.score - compState.score;
	}
}
