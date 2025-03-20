// A Java program to find biconnected components in a given
// undirected graph
import java.io.*;
import java.util.*;


class Graph {
	private int V, E; 
	private LinkedList<Integer> adj[]; 
	
	static int count = 0, time = 0;

	class Edge {
		int u;
		int v;
		Edge(int u, int v)
		{
			this.u = u;
			this.v = v;
		}
	};

	Graph(int v)
	{
		V = v;
		E = 0;
		adj = new LinkedList[v];
		for (int i = 0; i < v; ++i)
			adj[i] = new LinkedList();
	}

	void addEdge(int v, int w)
	{
		adj[v].add(w);
		E++;
	}

	void BCCUtil(int u, int disc[], int low[], LinkedList<Edge> st,
				int parent[])
	{

		
		disc[u] = low[u] = ++time;
		int children = 0;

		
		Iterator<Integer> it = adj[u].iterator();
		while (it.hasNext()) {
			int v = it.next(); 
			
			if (disc[v] == -1) {
				children++;
				parent[v] = u;

				
				st.add(new Edge(u, v));
				BCCUtil(v, disc, low, st, parent);

				
				if (low[u] > low[v])
					low[u] = low[v];

				
				if ((disc[u] == 1 && children > 1) || (disc[u] > 1 && low[v] >= disc[u])) {
					while (st.getLast().u != u || st.getLast().v != v) {
						System.out.print(st.getLast().u + "--" + st.getLast().v + " ");
						st.removeLast();
					}
					System.out.println(st.getLast().u + "--" + st.getLast().v + " ");
					st.removeLast();

					count++;
				}
			}

			
			else if (v != parent[u] && disc[v] < disc[u] ) {
				if (low[u] > disc[v])
					low[u] = disc[v];

				st.add(new Edge(u, v));
			}
		}
	}

	
	void BCC()
	{
		int disc[] = new int[V];
		int low[] = new int[V];
		int parent[] = new int[V];
		LinkedList<Edge> st = new LinkedList<Edge>();

		
		for (int i = 0; i < V; i++) {
			disc[i] = -1;
			low[i] = -1;
			parent[i] = -1;
		}

		for (int i = 0; i < V; i++) {
			if (disc[i] == -1)
				BCCUtil(i, disc, low, st, parent);

			int j = 0;

			
			while (st.size() > 0) {
				j = 1;
				System.out.print(st.getLast().u + "--" + st.getLast().v + " ");
				st.removeLast();
			}
			if (j == 1) {
				System.out.println();
				count++;
			}
		}
	}

	public static void main(String args[])
	{
		Graph g = new Graph(12);
		g.addEdge(0, 1);
		g.addEdge(1, 0);
		g.addEdge(1, 2);
		g.addEdge(2, 1);
		g.addEdge(1, 3);
		g.addEdge(3, 1);
		g.addEdge(2, 3);
		g.addEdge(3, 2);
		g.addEdge(2, 4);
		g.addEdge(4, 2);
		g.addEdge(3, 4);
		g.addEdge(4, 3);
		g.addEdge(1, 5);
		g.addEdge(5, 1);
		g.addEdge(0, 6);
		g.addEdge(6, 0);
		g.addEdge(5, 6);
		g.addEdge(6, 5);
		g.addEdge(5, 7);
		g.addEdge(7, 5);
		g.addEdge(5, 8);
		g.addEdge(8, 5);
		g.addEdge(7, 8);
		g.addEdge(8, 7);
		g.addEdge(8, 9);
		g.addEdge(9, 8);
		g.addEdge(10, 11);
		g.addEdge(11, 10);

		g.BCC();

		System.out.println("Above are " + g.count + " biconnected components in graph");
	}
}

