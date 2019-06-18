package code;

import java.util.ArrayList;
import java.util.LinkedList;

import code.DirectedUnweightedGraph.Edge;
import code.DirectedUnweightedGraph.Vertex;

public class DirectedWeightedGraph<V> extends BaseGraph<V> {

	private ArrayList<Vertex<V>> vertexList;
	private ArrayList<Edge> edgeList;
	final float DEFAULT_WEIGHT = Float.MIN_VALUE;

	public DirectedWeightedGraph() {
		vertexList = new ArrayList<Vertex<V>>();
		edgeList = new ArrayList<Edge>();
	}

	public class Vertex<V> {
		V element;
		LinkedList<Vertex<V>> outGoings;
		boolean isVisited = false;

		public Vertex(V element) {
			setElement(element);
			outGoings = new LinkedList<Vertex<V>>();
		}

		public V getElement() {
			return element;
		}

		public void setElement(V element) {
			this.element = element;
		}

		public LinkedList<Vertex<V>> getoutGoings() {
			return outGoings;
		}

		@Override
		public String toString() {
			String res = "";
			res += "|(" + element;
			for (int i = 0; i < outGoings.size(); i++) {
				res += "," + outGoings.get(i);
			}
			res += ")|";
			return res;
		}

	}

	public class Edge {
		private ArrayList<Vertex<V>> ends;
		float weight;

		public Edge(Vertex<V> source, Vertex<V> target, float weight) {
			ends = new ArrayList<Vertex<V>>();
			ends.add(source);
			ends.add(target);
			setWeight(weight);
		}

		public ArrayList<Vertex<V>> getEnds() {
			return ends;
		}

		public float getWeight() {
			return weight;
		}

		public void setWeight(float weight) {
			this.weight = weight;
		}

		public String toString() {
			return "(" + ends.get(0).element + "-->" + ends.get(1).element + ", " + weight + ")";
		}

	}

	public boolean isThereAnyEdge(Vertex<V> v1, Vertex<V> v2) {
		boolean b = false;
		for (int i = 0; i < edgeList.size(); i++) {
			if (edgeList.get(i).ends.get(0) == v1 && edgeList.get(i).ends.get(1) == v2) {
				b = true;
				break;
			}
		}
		return b;
	}

	public Edge getEdge(Vertex<V> v1, Vertex<V> v2) {
		int i = 0;
		for (i = 0; i < edgeList.size(); i++) {
			if (edgeList.get(i).ends.get(0) == v1 && edgeList.get(i).ends.get(1) == v2) {
				break;

			}
		}
		return edgeList.get(i);
	}


	public boolean hasVertex(V v) {
		boolean alreadyExist = false;
		for (int i = 0; i < vertexList.size(); i++) {
			if (vertexList.get(i).element.equals(v)) {
				alreadyExist = true;
				break;
			}
		}

		return alreadyExist;
	}

	public Vertex<V> getVertex(V v) {

		Vertex<V> vertex = null;
		for (int i = 0; i < vertexList.size(); i++) {
			if (vertexList.get(i).element.equals(v)) {
				vertex = vertexList.get(i);
				break;
			}
		}

		return vertex;
	}

	@Override
	public String toString() {
		String tmp = "Directed Weighted Graph";
		return tmp;
	}

	@Override
	public void insertVertex(V v) {

		if (!hasVertex(v)) {
			Vertex<V> newVertex = new Vertex<V>(v);
			vertexList.add(newVertex);

		} else {

		}

	}

	@Override
	public V removeVertex(V v) {

		if (!hasVertex(v)) {

			return null;
		}

		for (int i = 0; i < vertexList.size(); i++) {
			if (vertexList.get(i).element.equals(v)) {
				vertexList.remove(i);
			}
		}

		for (int i = 0; i < vertexList.size(); i++) {
			for (int j = 0; j < vertexList.get(i).outGoings.size(); j++) {
				if (vertexList.get(i).outGoings.get(j).element.equals(v)) {
					vertexList.get(i).outGoings.remove(j);
				}
			}
		}

		for (int i = edgeList.size() - 1; i >= 0; i--) {
			if (edgeList.get(i).ends.get(0).element == v || edgeList.get(i).ends.get(1).element == v) {
				edgeList.remove(i);
			}
		}

		return v;
	}

	@Override
	public boolean areAdjacent(V v1, V v2) {
		boolean b = false;
		for (int i = 0; i < edgeList.size(); i++) {
			if ((edgeList.get(i).ends.get(0).element == v1 && edgeList.get(i).ends.get(1).element == v2)
					|| (edgeList.get(i).ends.get(0).element == v2 && edgeList.get(i).ends.get(1).element == v1)) {
				b = true;
				break;
			}
		}
		return b;
	}

	@Override
	public void insertEdge(V source, V target) {
		Vertex<V> v1 = getVertex(source);
		Vertex<V> v2 = getVertex(target);

		if (v1 != null && v2 != null) {
			if (isThereAnyEdge(v1, v2)) {
				return;
			} else {
				Edge newEdge = new Edge(v1, v2, DEFAULT_WEIGHT);
				edgeList.add(newEdge);
				v1.outGoings.add(v2);
				return;
			}
		} else if (v1 == null && v2 != null) {
			insertVertex(source);
			v1 = getVertex(source);
			v1.outGoings.add(v2);
			Edge newEdge = new Edge(v1, v2, DEFAULT_WEIGHT);
			edgeList.add(newEdge);
			return;
		}

		else if (v1 != null && v2 == null) {
			insertVertex(target);
			v2 = getVertex(target);
			v1.outGoings.add(v2);
			Edge newEdge = new Edge(v1, v2, DEFAULT_WEIGHT);
			edgeList.add(newEdge);
			return;
		} else if (v1 == null && v2 == null) {
			insertVertex(source);
			insertVertex(target);
			v1 = getVertex(source);
			v2 = getVertex(target);
			Edge newEdge = new Edge(v1, v2, DEFAULT_WEIGHT);
			edgeList.add(newEdge);
			v1.outGoings.add(v2);
			return;
		}

	}

	/*
	 * Creates an edge between vertices source and target with the given weight and
	 * returns it. CAREFUL: Adds the vertices if they are not already in the graph!
	 * This is different than most graph ADTs CAREFUL: If an edge already exists
	 * between the vertices: - Updates the weight of the edge for weighted graphs
	 * and returns the update edge - Does nothing for unweighted graphs
	 * 
	 * 
	 * Equivalent to insertEdge(V target, V source, weight) for undirected graphs
	 * Safely ignore the weight parameters if the graph is unweighted i.e. make it
	 * equivalent to insertEdge(source, target)
	 * 
	 */

	@Override
	public void insertEdge(V source, V target, float weight) {
		Vertex<V> v1 = getVertex(source);
		Vertex<V> v2 = getVertex(target);

		if (v1 != null && v2 != null) {
			if (isThereAnyEdge(v1, v2)) {
				getEdge(v1, v2).setWeight(weight);
			} else {
				Edge newEdge = new Edge(v1, v2, weight);
				edgeList.add(newEdge);
				v1.outGoings.add(v2);
				return;
			}
		}

		else if (v1 == null && v2 != null) {
			insertVertex(source);
			v1 = getVertex(source);
			v1.outGoings.add(v2);
			Edge newEdge = new Edge(v1, v2, weight);
			edgeList.add(newEdge);
			return;
		}

		else if (v1 != null && v2 == null) {
			insertVertex(target);
			v2 = getVertex(target);
			v1.outGoings.add(v2);
			Edge newEdge = new Edge(v1, v2, weight);
			edgeList.add(newEdge);
			return;
		} else if (v1 == null && v2 == null) {
			insertVertex(source);
			insertVertex(target);
			v1 = getVertex(source);
			v2 = getVertex(target);
			Edge newEdge = new Edge(v1, v2, weight);
			edgeList.add(newEdge);
			v1.outGoings.add(v2);
			return;
		}

	}

	@Override
	public boolean removeEdge(V source, V target) {

		Vertex<V> v1 = getVertex(source);
		Vertex<V> v2 = getVertex(target);
		if (v1 != null && v2 != null) {
			if (!isThereAnyEdge(v1, v2)) {

				return false;
			} else {

				edgeList.remove(getEdge(v1, v2));
				v1.outGoings.remove(v2);

				return true;
			}
		} else if (v1 != null && v2 == null) {
			insertVertex(target);
			return true;
		} else if (v1 == null && v2 != null) {

			insertVertex(source);

			return true;
		} else if (v1 == null && v2 == null) {

			insertVertex(source);
			insertVertex(target);

			return true;
		}

		return false;
	}

	/*
	 * Returns the weight of the edge between the given vertices: - given weight for
	 * the weighted graphs - 1 for unweighted graphs If there is no edge or if one
	 * or both vertices do not exist: - Return Float.MAX_VALUE for weighted graphs -
	 * Return 0 for unweighted graphs
	 * 
	 * Equivalent to getEdgeWeight(target, source) for undirected graphs
	 */
	@Override
	public float getEdgeWeight(V source, V target) {
		Vertex<V> v1 = getVertex(source);
		Vertex<V> v2 = getVertex(target);
		
		if(v1 == null || v2 == null || !isThereAnyEdge(v1,v2))
		{
			return Float.MAX_VALUE;
		}
		else
		{
			return getEdge(v1,v2).weight;
		}
		
	}

	@Override
	public int numVertices() {

		return vertexList.size();
	}

	@Override
	public Iterable<V> vertices() {

		ArrayList<V> iter = new ArrayList<V>();
		for (int i = 0; i < vertexList.size(); i++) {
			iter.add(vertexList.get(i).element);
		}

		return iter;
	}

	@Override
	public int numEdges() {

		return edgeList.size();
	}

	@Override
	public boolean isDirected() {

		return true;
	}

	@Override
	public boolean isWeighted() {

		return true;
	}

	@Override
	public int outDegree(V v) {
		Vertex<V> v1 = getVertex(v);
		if (v1 == null) {
			return -1;
		} else {
			return v1.outGoings.size();
		}

	}

	@Override
	public int inDegree(V v) {
		int count = 0;
		if (v == null || !hasVertex(v)) {
			return -1;
		} else {
			int position = 0;
			for (int i = 0; i < vertexList.size(); i++) {
				if (vertexList.get(i).element.equals(v)) {
					position = i;
					break;
				}
			}

			for (int i = 0; i < position; i++) {
				for (int j = 0; j < vertexList.get(i).outGoings.size(); j++) {
					if (vertexList.get(i).outGoings.get(j).element.equals(v)) {
						count++;
					}
				}
			}

			for (int i = position + 1; i < vertexList.size(); i++) {
				for (int j = 0; j < vertexList.get(i).outGoings.size(); j++) {
					if (vertexList.get(i).outGoings.get(j).element.equals(v)) {
						count++;
					}
				}
			}
			return count;
		}
	}

	@Override
	public Iterable<V> outgoingNeighbors(V v) {
		Vertex<V> v1 = getVertex(v);
		ArrayList<V> iter = new ArrayList<V>();
		if (v1 == null) {
			return null;
		} else {
			for (int i = 0; i < v1.outGoings.size(); i++) {
				iter.add(v1.outGoings.get(i).element);
			}
		}
		return iter;
	}

	@Override
	public Iterable<V> incomingNeighbors(V v) {
		ArrayList<V> incomes = new ArrayList<V>();
		if (v == null || !hasVertex(v)) {
			return null;
		} else {
			for (int i = 0; i < vertexList.size(); i++) {
				if (vertexList.get(i).element != v) {
					for (int j = 0; j < vertexList.get(i).outGoings.size(); j++) {
						if (vertexList.get(i).outGoings.get(j).element.equals(v)) {
							incomes.add(vertexList.get(i).element);
						}
					}
				}

			}
			return incomes;
		}

	}

}
