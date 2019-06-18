package code;

import java.util.ArrayList;

public class UndirectedUnweightedGraph<V> extends BaseGraph<V> {
	ArrayList<Vertex<V>> vertexList;
	public ArrayList<Edge> edgeList;

	public UndirectedUnweightedGraph() {
		// System.out.println("System Called the Constructor of " + this.toString());
		vertexList = new ArrayList<Vertex<V>>();
		edgeList = new ArrayList<Edge>();

	}
	///////////////// NESTED CLASSES START////////////////////

	private class Vertex<V> {
		V element;
		ArrayList<Vertex<V>> connecteds;
		boolean isVisited = false;

		public Vertex(V element) {
			setElement(element);
			connecteds = new ArrayList<Vertex<V>>();
		}

		public void setElement(V element) {
			this.element = element;
		}

		@Override
		public String toString() {
			return "(" + element + ")";
		}
	}

	private class Edge {
		Vertex<V> end1;
		Vertex<V> end2;

		public Edge(Vertex<V> end1, Vertex<V> end2) {

			this.end1 = end1;
			this.end2 = end2;
		}

		public String toString() {
			return "(" + end1.element + "---" + end2.element + ")";
		}
	}
	/////////////////////// NESTED CLASSES END////////////////////////

	//////////////////////////// HELPER METHODS START ////////////////////////////

	private boolean hasVertex(V v) {

		if (v == null) {
			return false;
		}
		boolean b = false;
		for (int i = 0; i < vertexList.size(); i++) {
			if (vertexList.get(i).element.equals(v)) {
				b = true;
				break;
			}
		}
		return b;
	}

	private Vertex<V> getVertex(V v) {
		if (v == null) {
			return null;
		}
		Vertex<V> vertex = null;
		for (int i = 0; i < vertexList.size(); i++) {
			if (vertexList.get(i).element.equals(v)) {
				vertex = vertexList.get(i);
			}
		}
		return vertex;
	}

	private boolean isThereAnyEdge(Vertex<V> v1, Vertex<V> v2) {

		boolean b = false;
		for (int i = 0; i < edgeList.size(); i++) {
			if (edgeList.get(i).end1.element == v1.element && edgeList.get(i).end2.element == v2.element) {
				b = true;
				break;
			} else if (edgeList.get(i).end1.element == v2.element && edgeList.get(i).end2.element == v1.element) {
				b = true;
				break;
			}
		}
		return b;
	}

	private Edge getEdge(Vertex<V> v1, Vertex<V> v2) {

		for (int i = 0; i < edgeList.size(); i++) {
			if (edgeList.get(i).end1.element == v1.element && edgeList.get(i).end2.element == v2.element) {
				return edgeList.get(i);

			} else if (edgeList.get(i).end1.element == v2.element && edgeList.get(i).end2.element == v1.element) {
				return edgeList.get(i);
			}
		}

		return null;
	}

	//////////////////////////// HELPER METHODS END///////////////////////////////

	@Override
	public String toString() {
		String tmp = "Undirected Unweighted Graph";
		return tmp;
	}

	@Override
	public void insertVertex(V v) {
	//	System.out.println("System called insertVertex : " + v);
		if (!hasVertex(v)) {
			//	System.out.println("Adding new Vertex :" + v);
			Vertex<V> vertex = new Vertex<V>(v);
			vertexList.add(vertex);
			//	System.out.println("Vertex List: " + vertexList);
		} else {
			//	System.out.println("Vertex already exist :" + v);
			//	System.out.println("Vertex List: " + vertexList);
		}

	}

	@Override
	public V removeVertex(V v) {
		//	System.out.println("System called removeVertex : " + v);
		if (!hasVertex(v)) {
			//	System.out.println("Vertex does not exist : " + v);
			//System.out.println("Vertex List: " + vertexList);
			return null;
		} else {
			//	System.out.println("Vertex exist : " + v);
			//	System.out.println("Vertex List :" + vertexList);
			//	System.out.println("Removing edges connected...");
			vertexList.remove(getVertex(v));
			for (int i = edgeList.size() - 1; i >= 0; i--) {
				//	System.out.println("Looking for " + edgeList.get(i));
				if (edgeList.get(i).end1.element == v || edgeList.get(i).end2.element == v) {

					//		System.out.println("Connected edge found: " + edgeList.get(i));
					edgeList.remove(i);
					//	System.out.println("Edge Removed");
					//		System.out.println("Edge List: " + edgeList);
				}
			}

			//	System.out.println("Removing connected vertecies...");
			for (int i = 0; i < vertexList.size(); i++) {
				for (int j = 0; j < vertexList.get(i).connecteds.size(); j++) {
					if (vertexList.get(i).connecteds.get(j).element == v) {
						//			System.out.println("Connected vertex found: " + v);
						//				System.out.println("Connecteds list: " + vertexList.get(i).connecteds);
						vertexList.get(i).connecteds.remove(j);
					}
				}
			}

			return v;
		}
	}

	@Override
	public boolean areAdjacent(V v1, V v2) {

		for (int i = 0; i < edgeList.size(); i++) {
			if (edgeList.get(i).end1.element == v1 && edgeList.get(i).end2.element == v2) {
				return true;
			} else if (edgeList.get(i).end1.element == v2 && edgeList.get(i).end2.element == v1) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void insertEdge(V source, V target) {
		//	System.out.println("System called insertEdge : " + source + "---" + target);
		Vertex<V> v1 = getVertex(source);
		Vertex<V> v2 = getVertex(target);

		if (v1 != null && v2 != null) {
			//	System.out.println("Both vertex exist");
			//		System.out.println("Vertex List: " + vertexList);
			if (!isThereAnyEdge(v1, v2)) {
				Edge newEdge = new Edge(v1, v2);
				//		System.out.println("No edge so adding new edge: " + newEdge);
				edgeList.add(newEdge);
				//		System.out.println("Edge List: " + edgeList);
				v1.connecteds.add(v2);
				v2.connecteds.add(v1);
				return;
			}
		}

		else if (v1 != null && v2 == null) {
			//		System.out.println("One of the vertex does not exist: " + target);
			insertVertex(target);
			v2 = getVertex(target);
			Edge newEdge = new Edge(v1, v2);
			//		System.out.println("No edge so adding new edge: " + newEdge);
			edgeList.add(newEdge);
			//	System.out.println("Edge List: " + edgeList);
			v1.connecteds.add(v2);
			v2.connecteds.add(v1);
			return;
		} else if (v1 == null && v2 != null) {
			//	System.out.println("One of the vertex does not exist: " + source);
			insertVertex(source);
			v1 = getVertex(source);
			Edge newEdge = new Edge(v1, v2);
			//		System.out.println("No edge so adding new edge: " + newEdge);
			edgeList.add(newEdge);
			//		System.out.println("Edge List: " + edgeList);
			v1.connecteds.add(v2);
			v2.connecteds.add(v1);
			return;
		}

		else if (v1 == null && v2 == null) {
			insertVertex(source);
			insertVertex(target);
			v1 = getVertex(source);
			v2 = getVertex(target);
			Edge newEdge = new Edge(v1, v2);
			//	System.out.println("No edge so adding new edge: " + newEdge);
			edgeList.add(newEdge);
			//	System.out.println("Edge List: " + edgeList);
			v1.connecteds.add(v2);
			v2.connecteds.add(v1);
			return;
		}

	}

	@Override
	public void insertEdge(V source, V target, float weight) {
		insertEdge(source, target);

	}

	@Override
	public boolean removeEdge(V source, V target) {
		//	System.out.println("System called removeEdge: " + source + "---" + target);
		Vertex<V> v1 = getVertex(source);
		Vertex<V> v2 = getVertex(target);

		if (v1 != null && v2 != null) {
			//	System.out.println("Both vertex exist");
			//	System.out.println("Edge List: " + edgeList);
			if (!isThereAnyEdge(v1, v2)) {
				//		//		System.out.println("No edge to remove: " + source + "---" + target);
				//		System.out.println("Edge List: " + edgeList);
				return false;
			} else {
				//		System.out.println("Edge exists");
				//		System.out.println("Edge List: " + edgeList);

				for (int i = 0; i < edgeList.size(); i++) {
					if (edgeList.get(i).end1.element == v1.element && edgeList.get(i).end2.element == v2.element) {
						edgeList.remove(i);
						break;
					} else if (edgeList.get(i).end1.element == v2.element
							&& edgeList.get(i).end2.element == v1.element) {
						edgeList.remove(i);
						break;
					}
				}
				//		System.out.println("Edge Removed ");
				//		System.out.println("Edge List: " + edgeList);
				//		System.out.println("Removing the vertexes from their connected lists");
				//		System.out.println("Connecteds of " + source + ": " + v1.connecteds);
				for (int i = 0; i < v1.connecteds.size(); i++) {
					//		System.out.println("Looking at: " + v1.connecteds.get(i));
					if (v1.connecteds.get(i).element == v2.element) {
						//			System.out.println(source + " has " + target + " in its connecteds list: " + v1.connecteds);
						v1.connecteds.remove(i);
					}
				}

				//	System.out.println("Connecteds of " + target + ": " + v2.connecteds);
				for (int i = 0; i < v2.connecteds.size(); i++) {
					//		System.out.println("Looking at: " + v2.connecteds.get(i));
					if (v2.connecteds.get(i).element == v1.element) {
						//			System.out.println(source + " has " + target + " in its connecteds list: " + v1.connecteds);
						v2.connecteds.remove(i);
					}
				}
				return true;
			}
		}

		else if (v1 != null && v2 == null) {
			//		System.out.println("One of the vertex does not exist :" + target);
			//		System.out.println("Vertex List:" + vertexList);
			insertVertex(target);
			return false;
		}

		else if (v1 == null && v2 != null) {
			//		System.out.println("One of the vertex does not exist :" + source);
			//		System.out.println("Vertex List:" + vertexList);
			insertVertex(source);
			return false;
		}

		else if (v1 == null && v2 == null) {
			//	System.out.println("Both of the vertex do not exist :" + source + "," + target);
			//	System.out.println("Vertex List:" + vertexList);
			insertVertex(source);
			insertVertex(target);
			return false;
		}

		return false;
	}

	@Override
	public float getEdgeWeight(V source, V target) {

		Vertex<V> v1 = getVertex(source);
		Vertex<V> v2 = getVertex(target);

		if (isThereAnyEdge(v1, v2)) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int numVertices() {

		return vertexList.size();
	}

	@Override
	public Iterable<V> vertices() {
		ArrayList<V> iter = new ArrayList<V>();
		for (Vertex<V> v : vertexList) {
			iter.add(v.element);
		}
		return iter;
	}

	@Override
	public int numEdges() {

		return edgeList.size();
	}

	@Override
	public boolean isDirected() {

		return false;
	}

	@Override
	public boolean isWeighted() {

		return false;
	}

	@Override
	public int outDegree(V v) {
	//	System.out.println("System called outDegree: " + v);
		Vertex<V> v1 = getVertex(v);
		if (v1 == null) {
	//		System.out.println("Vertex is null or does not exist: " + v);
	//		System.out.println("Vertex List: " + vertexList);
			return -1;
		} else {
		//	System.out.println("Vertex exist");
	//		System.out.println("Connecteds: " + v1.connecteds);
		//	System.out.println("Edge List: " + edgeList);
			return v1.connecteds.size();
		}
	}

	@Override
	public int inDegree(V v) {

		return outDegree(v);
	}

	@Override
	public Iterable<V> outgoingNeighbors(V v) {
		//	System.out.println("System called outgoingNeighbors: " + v);
		Vertex<V> v1 = getVertex(v);
		if (v1 == null) {
			//		System.out.println("Vertex is null or does not exist: " + v);
			//		System.out.println("Vertex List: " + vertexList);
			return null;
		} else {
			//		System.out.println("Vertex exist");
			//		//		System.out.println("Connecteds: " + v1.connecteds);
			//		System.out.println("Edge List: " + edgeList);
			ArrayList<V> iter = new ArrayList<V>();

			for (Vertex<V> vertex : v1.connecteds) {
				iter.add(vertex.element);
			}
			//		System.out.println("Returning : " + iter);
			return iter;
		}

	}

	@Override
	public Iterable<V> incomingNeighbors(V v) {

		return outgoingNeighbors(v);
	}

}
