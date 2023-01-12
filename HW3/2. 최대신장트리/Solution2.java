import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution2 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
       javac Solution2.java -encoding UTF8

   2. 컴파일 후 아래와 같은 명령어를 입력했을 때 여러분의 프로그램이 정상적으로 출력파일 output2.txt 를 생성시켜야 채점이 이루어집니다.
       java Solution2

   - 제출하시는 소스코드의 인코딩이 UTF8 이어야 함에 유의 바랍니다.
   - 수행시간 측정을 위해 다음과 같이 time 명령어를 사용할 수 있습니다.
       time java Solution2
   - 일정 시간 초과시 프로그램을 강제 종료 시키기 위해 다음과 같이 timeout 명령어를 사용할 수 있습니다.
       timeout 0.5 java Solution2   // 0.5초 수행
       timeout 1 java Solution2     // 1초 수행
 */

class Solution2 {
	static final int MAX_N = 20000;
	static final int MAX_E = 80000;

	static int N;
	static int E;
	static int[] U = new int[MAX_E];
	static int[] V = new int[MAX_E];
	static int[] W = new int[MAX_E];
	static int Answer;

	public static void main(String[] args) throws Exception {
		/*
		   동일 폴더 내의 input2.txt 로부터 데이터를 읽어옵니다.
		   또한 동일 폴더 내의 output2.txt 로 정답을 출력합니다.
		 */
		BufferedReader br = new BufferedReader(new FileReader("input2.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output2.txt");

		for (int test_case = 1; test_case <= 10; test_case++) {
			/*
			   각 테스트 케이스를 표준 입력에서 읽어옵니다.
			   먼저 정점의 개수와 간선의 개수를 각각 N, E에 읽어들입니다.
			   그리고 각 i번째 간선의 양 끝점의 번호를 U[i], V[i]에 읽어들이고, i번째 간선의 가중치를 W[i]에 읽어들입니다. (0 ≤ i ≤ E-1, 1 ≤ U[i] ≤ N, 1 ≤ V[i] ≤ N)
			 */
			stk = new StringTokenizer(br.readLine());
			N = Integer.parseInt(stk.nextToken()); E = Integer.parseInt(stk.nextToken());
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < E; i++) {
				U[i] = Integer.parseInt(stk.nextToken());
				V[i] = Integer.parseInt(stk.nextToken());
				W[i] = Integer.parseInt(stk.nextToken());
			}

			Answer = primMst();
		
			pw.println("#" + test_case + " " + Answer);
			pw.flush();
			Answer = 0;
		}

		br.close();
		pw.close();
	}

	private static int primMst() {
		ArrayList<ArrayList<Pair>> graph = new ArrayList<>(N+1);
		for (int i = 0; i < N+1; i++) {
			graph.add(new ArrayList<>());
		}
		for (int i = 0; i < E; i++) {
			graph.get(U[i]).add(new Pair(V[i], W[i]));
			graph.get(V[i]).add(new Pair(U[i], W[i]));
		}

		boolean[] isInSet = new boolean[N+1];
		PriorityQueue<Vertex> heap = new PriorityQueue<>();
		int root = 1;
		isInSet[root] = true;
		int nodeInTreeNum = 1;
		int treeSize = 0;

		Iterator iterator = graph.get(root).iterator();
		while(iterator.hasNext()) {
			Pair pair = (Pair)iterator.next();
			heap.add(new Vertex(pair.vertexId, pair.weight));
		}

		while(nodeInTreeNum != N) {
			Vertex maxVertex = heap.poll();
			int maxNodeId = maxVertex.getId();
			if (!isInSet[maxNodeId]) {
				isInSet[maxNodeId] = true;
				nodeInTreeNum++;
				treeSize += maxVertex.getDistance();
				Iterator iterator2 = graph.get(maxNodeId).iterator();
				while(iterator2.hasNext()) {
					Pair pair = (Pair)iterator2.next();
					if (!isInSet[pair.vertexId]) {
						heap.add(new Vertex(pair.vertexId, pair.weight));
					}
				}
			}
		}

		return treeSize;
	}
}

class Pair {
	int vertexId;
	int weight;
	public Pair(int vertexId, int weight) {
		this.vertexId = vertexId;
		this.weight = weight;
	}
}

class Vertex implements Comparable<Vertex> {
	private int id;
	private int distance;
	public Vertex(int id, int distance) {
		this.id = id;
		this.distance = distance;
	}
	public int getId() {
		return id;
	}
	public int getDistance() {
		return distance;
	}
	@Override
	public int compareTo(Vertex o) {
		return Integer.compare(o.distance, this.distance);
	}
}