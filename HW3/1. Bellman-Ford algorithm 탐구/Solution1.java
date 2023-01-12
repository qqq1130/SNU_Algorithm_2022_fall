import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

class Solution1 {

    static final int MAX_N = 1000;
	static final int MAX_E = 100000;
	static final int Div = 100_000_000;  // 1억
	static int N, E; // N: Vertex 수 / E: Edge 수
	static int[] U = new int[MAX_E], V = new int[MAX_E], W = new int[MAX_E];
	static int[] Answer1 = new int[MAX_N+1];
	static int[] Answer2 = new int[MAX_N+1];
    static double start1, start2;
    static double time1, time2;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("input1.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output1.txt");

		for (int test_case = 1; test_case <= 10; test_case++) {
			/*
			   각 테스트 케이스를 표준 입력에서 읽어옵니다.
			   먼저 정점의 개수와 간선의 개수를 각각 N, E에 읽어들입니다.
			   그리고 각 i번째 간선의 시작점의 번호를 U[i], 끝점의 번호를 V[i]에, 간선의 가중치를 W[i]에 읽어들입니다.
			   (0 ≤ i ≤ E-1, 1 ≤ U[i] ≤ N, 1 ≤ V[i] ≤ N)
			 */
			stk = new StringTokenizer(br.readLine());
			N = Integer.parseInt(stk.nextToken()); E = Integer.parseInt(stk.nextToken());
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < E; i++) {
				U[i] = Integer.parseInt(stk.nextToken());
				V[i] = Integer.parseInt(stk.nextToken());
				W[i] = Integer.parseInt(stk.nextToken());
			}

            /* Problem 1-1 */
            start1 = System.currentTimeMillis();

			Answer1 = BellmanFord1(1);

            time1 = (System.currentTimeMillis() - start1);
			/* Problem 1-1 END */

            /* Problem 1-2 */
            start2 = System.currentTimeMillis();

			Answer2 = BellmanFord2(1);

            time2 = (System.currentTimeMillis() - start2);
			/* Problem 1-2 END*/

			// output1.txt로 답안을 출력합니다.
			pw.println("#" + test_case);
            for (int i = 1; i <= N; i++) {
                pw.print(Answer1[i]);
                if (i != N)
                    pw.print(" ");
                else
                    pw.print("\n");
            }
            pw.println(time1);

            for (int i = 1; i <= N; i++) {
                pw.print(Answer2[i]);
                if (i != N)
                    pw.print(" ");
                else
                    pw.print("\n");
            }
            pw.println(time2);
			pw.flush();
		}

		br.close();
		pw.close();
	}

	static int[] BellmanFord1(int root) {
		int[] distance = new int[N+1];
		distance[root] = 0;
		for (int i = 2; i<=N; i++) {
			distance[i] = Integer.MAX_VALUE;
		}
		for (int i = 0; i<N-1; i++) {
			for (int j = 0; j<E; j++) {
				int sourceVertexNum = U[j];
				int destVertexNum = V[j];
				int weight = W[j];
				if (distance[sourceVertexNum] == Integer.MAX_VALUE) {
					continue;
				}
				int potentialRelaxedWeight = distance[sourceVertexNum] + weight;
				if (potentialRelaxedWeight < distance[destVertexNum]) {
					distance[destVertexNum] = potentialRelaxedWeight;
				}
			}
		}
		//문제의 조건 하에서 Path의 길이가 1억 이상이 될 수 없음.
		// for (int i =1; i < N+1; i++) {
		// 	distance[i] %= Div;
		// }
		return distance;
	}

	static int[] BellmanFord2(int root) {
		int[] distance = new int[N+1];

		for (int i = 1; i<=N; i++) {
			distance[i] = Integer.MAX_VALUE;
		}
		distance[root] = 0;

		int[][] graph = new int[N+1][N+1];

		//Big-Theta(E) time
		for (int i = 1; i<=N; i++) {
			for (int j = 1; j<=N; j++) {
				graph[i][j] = Integer.MIN_VALUE;
			}
		}

		for (int i = 0; i<E; i++) {
			graph[U[i]][V[i]] = W[i];
		}

		MyQueue q = new MyQueue(N);
		q.add(root);

		while(!q.isEmpty()) {
			int source = q.poll();
			for (int dest = 1; dest <= N; dest++) {
				int weight = graph[source][dest];
				if (weight != Integer.MIN_VALUE) {
					if (distance[dest] > distance[source] + weight) {
						distance[dest] = distance[source] + weight; //relaxation
						q.add(dest);
					}
				}
			}
		}
		//문제의 조건 하에서 Path의 길이가 1억 이상이 될 수 없음.
		// for (int i =1; i < N+1; i++) {
		// 	distance[i] %= Div;
		// }
		return distance;
	}
}



class MyQueue {
	private int[] data;
	private int head;
	private int tail;
	private int size = 0;

	public MyQueue(int n) {
		data = new int[n*n]; //N*N 개면 Queue의 크기 충분.
		head = 0;
		tail = data.length-1;
	}

	public boolean add(int input) {
		if (isFull()) {
			return false;
		}
		else {
			tail = (tail + 1) % data.length;
			data[tail] = input;
			size++;
			return true;
		}
	}

	public Integer poll() {
		if (isEmpty()) {
			return null;
		} else {
			int queueTop = data[head];
			head = (head + 1) % data.length;
			size--;
			return queueTop;
		}
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return (size == 0);
	}

	private boolean isFull() {
		return size == data.length;
	}
}












