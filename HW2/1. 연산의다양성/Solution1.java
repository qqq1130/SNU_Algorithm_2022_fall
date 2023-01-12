import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;


class Solution1 {
	static final int a = 0;
	static final int b = 1;
	static final int c = 2;
	static final int[][] operationBoard = {
			{b, b, a},
			{c, b, a},
			{a, c, c}
	};

    static int n;                           // 문자열 길이
    static String s;                        // 문자열
	static long[] Answer = new long[3];     // 정답

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("input1.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output1.txt");

		for (int test_case = 1; test_case <= 10; test_case++) {
			stk = new StringTokenizer(br.readLine());
			n = Integer.parseInt(stk.nextToken());
			s = br.readLine();

			long[][][] M = new long[n][n][3];

			//크기 1짜리 문제들을 먼저 푼다.
			for (int i =0; i < n; i++) {	//이 부분 시간복잡도는 Big-Theta(n)
				char currentChar = s.charAt(i);
				if (currentChar == 'a') {
					M[i][i][a] = 1;
				} else if (currentChar == 'b') {
					M[i][i][b] = 1;
				} else {
					M[i][i][c] = 1;
				}
			}

			//문제의 크기를 늘려가며 계산한다.
			//이 반복문은 n에 비례하는 횟수로 실행된다.
			for (int size = 2; size < n; size++) {
				//이 반복문은 "n - size" 에 비례하여 실행된다.
				for (int j = 0; j<=n-size; j++) {
					// M[j][j+size-1]에 접근해서 이 값이 a,b,c중 무엇을 가질 수 있는지 판단.
					int targetRow = j;
					int targetCol = j + size - 1;
					M[targetRow][targetCol] = new long[] {0, 0, 0};

					//가능한 모든 연산의 조합을 따지며 현재 관심을 갖는 M[][]이 a,b,c 중 어떤 경우를 가질 가능성이 있는지 찾는다.
					//이 반복문은 size에 비례하는 시간을 소모한다. (내부 2중 for문은 상수시간)
					for (int k = 1; k<= size-1; k++) {
						long[] possibleOp1Arr = M[targetRow][targetCol-k];
						long[] possibleOp2Arr = M[targetRow+size-k][targetCol];
						for (int op1 = a; op1 <= c; op1++) {
							for (int op2 = a; op2 <= c; op2++) {
								long numOfOp1 = possibleOp1Arr[op1];
								long numOfOp2 = possibleOp2Arr[op2];
								if (numOfOp1 != 0 && numOfOp2 != 0) {
									int result = operationBoard[op1][op2];
									M[targetRow][targetCol][result] += numOfOp1 * numOfOp2;
								}
							}
						}
					}
				}
			}
			//여기까지, 만약 size를 k라고 둔다면, sigma{(n-k+1)*(k-1)} (k=2,3,...,n-1) 만큼 루프가 실행된다.
			//즉, 실행횟수를 기준으로 하면 Big-Theta(n^3)

			//이제 마지막으로 size n짜리 문제, 즉 M[0][n-1]만 풀면 된다.
			//이 반복문의 시간복잡도 Big-Theta(n)
			for (int k = 1; k <= n-1; k++) {
				long[] possibleOp1Arr = M[0][n - 1 - k];
				long[] possibleOp2Arr = M[n - k][n - 1];
				for (int op1 = 0; op1 < 3; op1++) {
					for (int op2 = 0; op2 < 3; op2++) {
						long numOfOp1 = possibleOp1Arr[op1];
						long numOfOp2 = possibleOp2Arr[op2];
						if (numOfOp1 != 0 && numOfOp2 != 0) {
							int result = operationBoard[op1][op2];
							Answer[result] += numOfOp1 * numOfOp2;
						}
					}
				}
			}
			//결과적으로, Big-Theta(n^3) 만큼 점근적 시간복잡도를 갖는다.

			pw.println("#" + test_case + " " + Answer[0] + " " + Answer[1] + " " + Answer[2]);

			Answer[0] = 0;
			Answer[1] = 0;
			Answer[2] = 0;

			pw.flush();
		}

		br.close();
		pw.close();
	}
}

