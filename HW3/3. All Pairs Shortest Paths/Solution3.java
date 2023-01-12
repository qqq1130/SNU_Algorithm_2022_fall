import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution3 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
       javac Solution3.java -encoding UTF8

   2. 컴파일 후 아래와 같은 명령어를 입력했을 때 여러분의 프로그램이 정상적으로 출력파일 output3.txt 를 생성시켜야 채점이 이루어집니다.
       java Solution3

   - 제출하시는 소스코드의 인코딩이 UTF8 이어야 함에 유의 바랍니다.
   - 수행시간 측정을 위해 다음과 같이 time 명령어를 사용할 수 있습니다.
       time java Solution3
   - 일정 시간 초과시 프로그램을 강제 종료 시키기 위해 다음과 같이 timeout 명령어를 사용할 수 있습니다.
       timeout 0.5 java Solution3   // 0.5초 수행
       timeout 1 java Solution3     // 1초 수행
 */

class Solution3 {
	static final int MAX_N = 200;
	static final int MAX_E = 10000;

	static int N, E;
	static int[] U = new int[MAX_E], V = new int[MAX_E], W = new int[MAX_E];
	static int Answer;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("input3.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output3.txt");

		for (int test_case = 1; test_case <= 10; test_case++) {
			stk = new StringTokenizer(br.readLine());
			N = Integer.parseInt(stk.nextToken()); E = Integer.parseInt(stk.nextToken());
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < E; i++) {
				U[i] = Integer.parseInt(stk.nextToken());
				V[i] = Integer.parseInt(stk.nextToken());
				W[i] = Integer.parseInt(stk.nextToken());
			}

			int[][] graph = new int[N+1][N+1];

			for (int i = 1; i <= N; i++) {
				for (int j = 1; j<=N; j++) {
					graph[i][j] = Integer.MAX_VALUE;
				}
			}
			for (int i = 0; i<E; i++) {
				graph[U[i]][V[i]] = W[i];
			}
			for (int k = 1; k<=N; k++) { //Floyd-Warshall Algorithm
				for (int source = 1; source <= N; source++) {
					for (int dest = 1; dest <= N; dest++) {
						if (source == dest) {
							continue;
						}

						if (k==N) {
							int minValue = getMinValue(graph, source, dest ,k);
							if (minValue != Integer.MAX_VALUE) {
								Answer += minValue;
							}
						} else {
							graph[source][dest] = getMinValue(graph, source, dest, k);
						}
					}
				}
			}

			pw.println("#" + test_case + " " + Answer);
			pw.flush();

			Answer = 0;
		}

		br.close();
		pw.close();
	}

	static int getMinValue(int[][] graph, int source, int dest, int k) {
		if (source == k || dest ==k) {
			return graph[source][dest];
		} else if (graph[source][dest] == Integer.MAX_VALUE) {
			if (graph[source][k] != Integer.MAX_VALUE && graph[k][dest] != Integer.MAX_VALUE) {
				return graph[source][k] + graph[k][dest];
			} else {
				return Integer.MAX_VALUE;
			}
		} else {
			if (graph[source][k] != Integer.MAX_VALUE && graph[k][dest] != Integer.MAX_VALUE) {
				return Math.min(graph[source][dest], graph[source][k] + graph[k][dest]);
			} else {
				return graph[source][dest];
			}
		}
	}
}
