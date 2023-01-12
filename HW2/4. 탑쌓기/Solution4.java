import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

class Solution4 {
	static final int max_n = 1000;

	static int n; //블록의 개수
	static int H; //탑의 최대 높이
	static int[] h = new int[max_n]; //각 블록의 높이 (h_i)
	static int[] d = new int[max_n-1]; //각 블록 구멍깊이 (d_i)
	static int Answer;
	static final int MIL = 1000000;

	

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("input4.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output4.txt");

		for (int test_case = 1; test_case <= 10; test_case++) {
			stk = new StringTokenizer(br.readLine());
			n = Integer.parseInt(stk.nextToken());
			H = Integer.parseInt(stk.nextToken());
			stk = new StringTokenizer(br.readLine());

			for (int i = 0; i < n; i++) {
				h[i] = Integer.parseInt(stk.nextToken());
			}
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < n-1; i++) {
				d[i] = Integer.parseInt(stk.nextToken());
			}

			//[0][j]: 현재 블럭이 맨 위에 올라가지 않고 높이 j의 탑을 만드는 경우의 수
			//[1][j]: 현재 블럭이 맨 위에 올라가고 높이 j의 탑을 만드는 경우의 수
			int[][] C = new int[2][H+1];
			int[][] D = new int[2][H+1];

			//가장 첫번째 블럭 놓기.
			C[1][h[0]]++;

			//수행시간은 Big-Theta(nH)
			//n개의 블럭을 다루는데,
			//블럭 하나를 다룰 때마다 H번만큼의 루프를 돌게 된다.
			for (int currBlock = 1; currBlock < n; currBlock++) {
				//현재 블럭만으로 탑을 만드는 케이스
				D[1][h[currBlock]]++;

				for (int height = 1; height <= H; height++) {
					//현재 블럭을 안올리는 케이스.
					D[0][height] = C[0][height] + C[1][height];

					//이전에 만들어둔 탑에다 "연속되지 않게" currBlock을 올리는 케이스
					int heightNotConsecutive = height + h[currBlock];
					if (heightNotConsecutive <= H) {
						D[1][heightNotConsecutive] += C[0][height];
					}
					

					//이전에 만들어둔 탑에다 "연속되게" currBlock을 올리는 케이스
					int heightConsecutive = height + h[currBlock] - d[currBlock-1];
					if (heightConsecutive <= H) {
						D[1][heightConsecutive] += C[1][height];
					}
					
				}

				for (int height = 1; height <= H; height++) {
					D[0][height] %= MIL;
					D[1][height] %= MIL;
				}

				C = D;
				D = new int[2][H+1];
			}

			for (int height = 1; height<=H; height++) {
				Answer += C[0][height] + C[1][height];
				Answer %= MIL;
			}

			pw.println("#" + test_case + " " + Answer);
			pw.flush();
			Answer = 0;
		}

		br.close();
		pw.close();
	}
}
