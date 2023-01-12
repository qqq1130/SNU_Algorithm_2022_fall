import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

class Solution2 {
	static final int max_n = 100000;

	static int n; //입력 문자열 길이
	static String s; //입력 문자열
	static int Answer;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("input2.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output2.txt");

		for (int test_case = 1; test_case <= 10; test_case++) {
			stk = new StringTokenizer(br.readLine());
			n = Integer.parseInt(stk.nextToken());
			s = br.readLine();
			int P[][] = new int[n][n];

			//일단, 길이가 1인 substring은 모두 회문이다.
			//여기에 들어가는 시간복잡도 Big-Theta(n)
			for (int i = 0; i<n; i++) {
				P[i][i] = 1;
			}

			//길이가 2인 substring은 두 알파벳이 같으면 길이 2짜리 회문, 다르면 길이 1짜리 회문.
			//여기에 들어가는 시간복잡도 Big-Theta(n)
			for (int i = 0; i<n-1; i++) {
				if (s.charAt(i) == s.charAt(i+1)) {
					P[i][i+1] = 2;
				} else {
					P[i][i+1] = 1;
				}
			}

			//이제 문제의 크기를 키워가며 푼다.
			//문제의 크기를 k라고 할 때, sigma(n+1-k) (k=3,4,...,n) 번 루프가 수행된다. 즉 Big-Theta(n^2)
			for (int size = 3; size <= n; size++) {
				for (int start = 0; start < n-size+1; start++) {
					if (s.charAt(start)==s.charAt(start+size-1)) {
						P[start][start+size-1] = P[start+1][start+size-2] + 2;
					} else {
						P[start][start+size-1] = Math.max(P[start][start+size-2], P[start+1][start+size-1]);
					}
				}
			}

			Answer = P[0][n-1];

			pw.println("#" + test_case + " " + Answer);
			Answer = 0;

			pw.flush();

		}

		br.close();
		pw.close();
	}
}

