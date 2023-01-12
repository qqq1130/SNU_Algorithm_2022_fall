import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

class Solution3 {
	static final int max_n = 100000;

	static int n;
	static int[][] A = new int[3][max_n]; //주어진 놀이판
	static int Answer;
	// k열에 p 패턴이 올 때, k-1 열에 몇번 패턴이 올 수 있는가?를 compatiblePatterns[p]에 저장.
	//T 는 Triangle의 "T" 이다. 즉 삼각형을 의미.
	//위에서부터 순서대로 표시한다.
	//pattern 0: O - X - T
	//pattern 1: O - T - X
	//pattern 2: T - O - X
	//pattern 3: T - X - O
	//pattern 4: X - O - T
	//pattern 5: X - T - O
	static int[][] compatiblePatterns = {
			{2, 5},
			{3, 4},
			{0, 5},
			{1, 4},
			{1, 3},
			{0, 2}
	};

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("input3.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output3.txt");

		for (int test_case = 1; test_case <= 10; test_case++) {
			stk = new StringTokenizer(br.readLine());
			n = Integer.parseInt(stk.nextToken());
			for (int i = 0; i < 3; i++) {
				stk = new StringTokenizer(br.readLine());
				for (int j = 0; j < n; j++) {
					A[i][j] = Integer.parseInt(stk.nextToken());
				}
			}

			int[][] C = new int[6][n]; //subproblem들의 결과를 기록할 배열

			//일단 size 1짜리 subproblem 해결해서 기록.
			//상수시간이므로 이 부분은 무시할 수 있다.
			for (int pattern = 0; pattern < 6; pattern++) {
				C[pattern][0] = getScore(0, pattern);
			}

			//size를 n까지 키워가며 subproblem들을 푼다.
			//Big-Theta(n)
			for (int col = 1; col < n; col++) {
				for (int pattern = 0; pattern < 6; pattern++ ) {
					C[pattern][col] = getScore(col, pattern) + getLargestCompatiblePattern(C, col, pattern);
				}
			}

			//이제 n번째 column에서 최댓값을 골라내면 된다.
			//이 부분도 상수시간을 소모한다.
			int max = Integer.MIN_VALUE;
			for (int pattern = 0; pattern < 6; pattern++) {
				if (max < C[pattern][n-1]) {
					max = C[pattern][n-1];
				}
			}
			Answer = max;

			pw.println("#" + test_case + " " + Answer);
			pw.flush();
			Answer = 0;
		}
		//결과적으로 Big-Theta(n)에 원하는 정답을 찾아낼 수 있다.

		br.close();
		pw.close();
	}

	private static int getScore(int colIndex, int pattern) {
		//특정 열과 패턴이 주어졌을 때 그 패턴에 따라 점수를 계산해서 리턴하는 함수.
		//상수시간을 소모하는 함수.
		switch(pattern){
			case 0:
				return A[0][colIndex] - A[1][colIndex];
			case 1:
				return A[0][colIndex] - A[2][colIndex];
			case 2:
				return A[1][colIndex] - A[2][colIndex];
			case 3:
				return -A[1][colIndex] + A[2][colIndex];
			case 4:
				return -A[0][colIndex] + A[1][colIndex];
			case 5:
				return -A[0][colIndex] + A[2][colIndex];
			default:
				return 0;
		}
	}

	private static int getLargestCompatiblePattern(int[][] C, int colIndex, int pattern) {
		//특정 열과 패턴이 주어지고, 그 직전 열에 주어진 패턴과 compatible한 패턴들을 두는 점수 중 최댓값을 리턴하는 함수. 
		//상수시간을 소모하는 함수.
		return Math.max(C[compatiblePatterns[pattern][0]][colIndex-1], C[compatiblePatterns[pattern][1]][colIndex-1]);
	}
}

