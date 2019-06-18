package code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;

/*
 * The class that will hold your graph algorithm implementations
 * Implement:
 * - Depth first search
 * - Breadth first search
 * - Dijkstra's single-source all-destinations shortest path algorithm
 * 
 * Feel free to add any addition methods and fields as you like
 */
public class GraphAlgorithms<V extends Comparable<V>> {

	ArrayList<V> visiteds;
	public static boolean usageCheck = false;

	/*
	 * WARNING: MUST USE THIS FUNCTION TO SORT THE NEIGHBORS (the adjacent call in
	 * the pseudocodes) FOR DFS AND BFS
	 * 
	 * THIS IS DONE TO MAKE AUTOGRADING EASIER
	 */
	public Iterable<V> iterableToSortedIterable(Iterable<V> inIterable) {
		usageCheck = true;
		List<V> sorted = new ArrayList<>();
		for (V i : inIterable) {
			sorted.add(i);
		}
		Collections.sort(sorted);
		return sorted;
	}

	/*
	 * Runs depth first search on the given graph G and returns a list of vertices
	 * in the visited order, starting from the startvertex.
	 * 
	 */
	public List<V> DFS(BaseGraph<V> G, V startVertex) {
		usageCheck = false;

		Stack<V> stack = new Stack<V>();
		visiteds = new ArrayList<V>();
		stack.push(startVertex);
		while (!stack.isEmpty()) {
			V u = stack.pop();
			if (!visiteds.contains(u)) {
				visiteds.add(u);
				for (V w : iterableToSortedIterable(G.outgoingNeighbors(u))) {
					if (!visiteds.contains(w)) {
						stack.push(w);
					}
				}
			}
		}
		return visiteds;
	}

	/*
	 * Runs breadth first search on the given graph G and returns a list of vertices
	 * in the visited order, starting from the startvertex.
	 * 
	 */
	public List<V> BFS(BaseGraph<V> G, V startVertex) {
		usageCheck = false;
		ArrayList<V> queue = new ArrayList<V>();
		visiteds = new ArrayList<V>();
		queue.add(startVertex);
		while (!queue.isEmpty()) {
			V u = queue.remove(0);
			if (!visiteds.contains(u)) {
				visiteds.add(u);
				for (V w : iterableToSortedIterable(G.outgoingNeighbors(u))) {
					if (!visiteds.contains(w)) {
						queue.add(w);
					}
				}
			}

		}

		return visiteds;
	}

	/////////////////////////////////////DIJKSTRAS START/////////////////////////////////////////
	/*
	 * Runs Dijkstras single source all-destinations shortest path algorithm on the
	 * given graph G and returns a map of vertices and their associated minimum
	 * costs, starting from the startvertex.
	 * 
	 */

	
	public HashMap<V, Float> Dijkstras(BaseGraph<V> G, V startVertex) {
		usageCheck = false;
		HashMap<V, Float> CostedVertices = new HashMap<V, Float>();
		HashMap<V, Float> pq = new HashMap<V, Float>();
		ArrayList<V> visiteds = new ArrayList<V>();
		Stack<V> reversePath = new Stack<V>();
		
		reversePath.push(startVertex);
		putAllVerticesIntoCostedList((ArrayList<V>)G.vertices() , CostedVertices);
		setCost(startVertex,0f,CostedVertices);
		push(startVertex,pq,CostedVertices); //just adds the CostedVertex object into indicated priority queue
		while(!pq.isEmpty())
		{
			V u = pop(pq); //returns the vertex of the object that has minimum cost
			if(!visiteds.contains(u))
			{
				visiteds.add(u); //visit
				for (V w : iterableToSortedIterable(G.outgoingNeighbors(u))) 
				{
					if(!visiteds.contains(w) && cost(w,CostedVertices) > cost(u,CostedVertices) + G.getEdgeWeight(u, w))
					{
						setCost(w,cost(u,CostedVertices) + G.getEdgeWeight(u, w) , CostedVertices);
						push(w,pq,CostedVertices);
						setParent(w,u,reversePath); // u is the parent of w 
					}
					
				}
			}
		}
		
		return turnPathIntoHashMap(reversePath,CostedVertices);
	}
	


	
	private HashMap<V, Float> turnPathIntoHashMap(Stack<V> reversePath, HashMap<V, Float> costedVertices) {
		HashMap<V,Float> path = new HashMap<V,Float>();
		
		while(!reversePath.isEmpty())
		{
			V u = reversePath.pop();
			float cost = costedVertices.get(u);
			path.put(u, cost);
			
		}
		return path;
	}

	private void setParent(V w, V u, Stack<V> reversePath) {
		reversePath.push(w);
		
	}

	private float cost(V u, HashMap<V, Float> costedVertices) {
		
		return costedVertices.get(u);
	}

	private V pop(HashMap<V, Float> pq) {
		float currentMin = Float.MAX_VALUE;
		V v = null;
		for(Float cost : pq.values())
		{
			if(cost < currentMin)
			{
				currentMin = cost;
			}
		}
		
		for(Entry<V,Float> entry : pq.entrySet())
		{
			if(entry.getValue() == currentMin)
			{
				v = entry.getKey();
			}
		}
		pq.remove(v);
		return v;
	}

	private void push(V startVertex, HashMap<V, Float> pq, HashMap<V, Float> costedVertices) {
		float cost = costedVertices.get(startVertex);
		pq.put(startVertex, cost);
	}

	private void setCost(V startVertex, float cost, HashMap<V, Float> costedVertices) {
		costedVertices.put(startVertex, cost);
		
	}

	private void putAllVerticesIntoCostedList(ArrayList<V> vertices, HashMap<V, Float> costedVertices) {
		for(V w : vertices)
		{
			costedVertices.put(w, Float.MAX_VALUE);
		}
		
	}
	
	
/////////////////////////////////////DIJKSTRAS END/////////////////////////////////////////
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	/*
	 * Returns true if the given graph is cyclic, false otherwise
	 */
	public boolean isCyclic(BaseGraph<V> G) {
		boolean isCyclic = false;
		if(G.numVertices() == 0)
		{
			isCyclic = false;
			
			return isCyclic;
		}
		
		
		
		else
		{
			
			
			if(G.isDirected())
			{
				if(isTree(G))
				{
					return false;
				}
				Stack<V> s = new Stack<V>();
				HashMap<V,String> marks = new HashMap<V,String>();
				ArrayList<V> vertices = (ArrayList<V>) G.vertices();
				
				//Initially put all the vertices into map as "unmarked"
				for(V v : vertices)
				{
					marks.put(v, "unmarked");
				}
				//Initiate the algorithm by selecting a random start point
				marks.put(vertices.get(0), "marked");
				s.push(vertices.get(0));
				while(!s.isEmpty())
				{
					V u = s.peek();
					if(isCyclic)
					{
						break;
					}
					if(G.outgoingNeighbors(u).iterator().hasNext())
					{
						//Look at all the neighbors; if there is a marked one, it means that there is a cycle
						for(V w : G.outgoingNeighbors(u))
						{
							if(marks.get(w).equals("marked"))
							{
								isCyclic = true;
								break;
								
							}
							
							else if(marks.get(w).equals("unmarked"))
							{
								marks.put(w, "marked");
							}
							else
							{
								marks.put(u, "antimarked");
								s.pop();
							}
						}
					}
					else //no children exist so antimarked it
					{
						marks.put(u, "antimarked");
						s.pop();
					}
					
				} // exit while loop

			}
			
			else
			{
				/*
				if(isTree(G))
				{
					return false;
				}
				*/
				
				ArrayList<V> vertices = (ArrayList<V>) G.vertices();
				ArrayList<V> visiteds = new ArrayList<V>();
				Stack<V> stack = new Stack<V>();
				
				stack.push(vertices.get(0));
				
				while (!stack.isEmpty()) {
					
					V u = stack.pop();
				
					
					if (!visiteds.contains(u)) {
						
						visiteds.add(u);
					
						for (V w : G.outgoingNeighbors(u)) {
							if (!visiteds.contains(w)) {
								
								stack.push(w);
							} 
							else
							{
			
								isCyclic = true;
								break;
								
							}
						}
					}
					if(isCyclic)
						break;
						
				}
			}
			
			
			return isCyclic;
		}
	

	}
	
	
	public boolean isTree(BaseGraph<V> G)
	{
		boolean b = false;
		ArrayList<V> vertices1 = (ArrayList<V>) G.vertices();
		for(V w : vertices1)
		{
			ArrayList<V> outs = (ArrayList<V>) G.outgoingNeighbors(w);
			ArrayList<V> ins = (ArrayList<V>) G.incomingNeighbors(w);
			if(outs.size() == 0 && ins.size() == 0)
			{
				b = true;
				break;
			}
		}
		return b;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
