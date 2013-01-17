import java.util.Arrays;

import java.io.*;

public class Main {
	
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader (
								new InputStreamReader(System.in));
		System.out.print("Type your id number : ");
		int idNum = Integer.parseInt(br.readLine())%30+1;
		System.out.print("How many kmap do you want to see : ");
		int numShowedState = Integer.parseInt(br.readLine());
		
		Permutation perm = new Permutation(8,7);
		assert perm.total >= numShowedState
				: "numShowedState exceed perm.total";
		// hareware coursework II ideal state diagram no. 25
		int[][] hwcw2isd = new int[8][2];
		
		for(int i=4;i>=0;i--) {
			if(idNum%2 == 1) {
				hwcw2isd[i][0] = 7;
				hwcw2isd[i][1] = (i==4) ? 6 : i+1;
			} else {
				hwcw2isd[i][0] = (i==4) ? 6 : i+1;
				hwcw2isd[i][1] = 7;
			}
			idNum >>= 1;
		}
		
		/*hwcw2isd25[0][0] = 7; hwcw2isd25[0][1] = 1; // reset
		hwcw2isd25[1][0] = 7; hwcw2isd25[1][1] = 2; // s1
		hwcw2isd25[2][0] = 3; hwcw2isd25[2][1] = 7; // s2 
		hwcw2isd25[3][0] = 4; hwcw2isd25[3][1] = 7; // s3
		hwcw2isd25[4][0] = 7; hwcw2isd25[4][1] = 6; // s4*/
		hwcw2isd[5] = null;                       // unused state
		hwcw2isd[6][0] = 0; hwcw2isd[6][1] = 6; // open
		hwcw2isd[7][0] = 7; hwcw2isd[7][1] = 7; // alarm
		
		int[] assignment = new int[8];
		
		StateToKarnaughMap[] states = new StateToKarnaughMap[perm.total];
		
		for(int i=0;i<states.length;i++) {
			hwcw2UpdateAssignment(assignment,perm);
			states[i] = new StateToKarnaughMap(hwcw2isd,2,3,assignment);
			perm.next();
		}
		
		Arrays.sort(states);
		
		for(int i=0;i<numShowedState;i++)
				hwcw2PrintState(states[i]);
	}
	
	public static void hwcw2UpdateAssignment(int[] assignment,Permutation perm) {
		assignment[0] = perm.pattern[0];
		assignment[1] = perm.pattern[1];
		assignment[2] = perm.pattern[2];
		assignment[3] = perm.pattern[3];
		assignment[4] = perm.pattern[4];
		assignment[5] = -1;
		assignment[6] = perm.pattern[5];
		assignment[7] = perm.pattern[6];
	}
	
	public static void hwcw2PrintState(StateToKarnaughMap state) {
		System.out.println(state.score+"          "+Arrays.toString(state.isMinterm));
		System.out.println(Arrays.toString(state.h));
		Utility.rep4varKmap(state.kmaps[0]);
		Utility.rep4varKmap(state.kmaps[1]);
		Utility.rep4varKmap(state.kmaps[2]);
		Utility.printBoolxtables(4,4,state.kmaps);
		Utility.rep4varKmap(state.kmaps[0]);
		Utility.rep4varKmap(state.kmaps[1]);
		Utility.rep4varKmap(state.kmaps[2]);
		System.out.println();
	}
	
}