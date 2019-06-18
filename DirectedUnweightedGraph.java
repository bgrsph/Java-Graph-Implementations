package code;

import java.util.ArrayList;
import java.util.LinkedList;

public class DirectedUnweightedGraph<V> extends BaseGraph<V> {
	
	//Adjacency List Implementation:
	ArrayList<Vertex<V>> vertexList;
	ArrayList<Edge> edgeList;

	
	public DirectedUnweightedGraph()
	{
		//System.out.println("System called the constructor for : " + this.toString());
		
		vertexList = new ArrayList<Vertex<V>>();
		edgeList = new ArrayList<Edge>();
		
	}
	

  @Override
  public String toString() {
    String tmp = "Directed Unweighted Graph";
    return tmp;
  }


public class Vertex<V>
  {
	  V element;
	  LinkedList<Vertex<V>> outGoings;
	  boolean isVisited = false;
	  public Vertex(V element)
	  {
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
	public String toString()
	{
		String res = "";
		res += "|(" + element;
		for(int i = 0; i < outGoings.size(); i++)
		{
			res += ","+outGoings.get(i);
		}
		res+= ")|";
		return res;
	}
	  
  }
  
  public class Edge
  {
	  private ArrayList<Vertex<V>> ends;
	  
	  public Edge(Vertex<V> source, Vertex<V> target)
	  {
		 ends = new ArrayList<Vertex<V>>();
		 ends.add(source);
		 ends.add(target);
	  }
	  public String toString()
	  {
		  return ends.get(0).element + "--->" + ends.get(1).element;
	  }
	 
  }
  
  public boolean hasVertex(V v)
  {
	  boolean alreadyExist = false;
		for(int i = 0; i < vertexList.size(); i++)
		{
			if(vertexList.get(i).element.equals(v)) //or ==
			{
				alreadyExist = true;
				break;
			}
		}
		
		return alreadyExist;
  }
  
  
  
  public Vertex<V> getVertex(V v)
  {
	  
	  Vertex<V> vertex = null;
		for(int i = 0; i < vertexList.size(); i++)
		{
			if(vertexList.get(i).element.equals(v)) 
			{
				vertex = vertexList.get(i); 
				break;
			}
		}
		
		return vertex;
  }
  

@Override
  public void insertVertex(V v) {
	//System.out.println("\nSystem called insertVertex:");
	if(!hasVertex(v))
	{
		Vertex<V> newVertex = new Vertex<V>(v);
		vertexList.add(newVertex);  
		//System.out.println("Vertex added: " + newVertex.toString() + "\n");
	}
	else
	{
		//System.out.println("Vertex already exists: " + v);
	}
	
  }

  @Override
  public V removeVertex(V v) {
	  
	  if(!hasVertex(v))
	  {
		  //  System.out.println("Vertex is not in graph: " + v);
		  //  System.out.println("Vertex list: " + vertexList);
		  return null;
	  }
	  //  System.out.println("System called removeVertex() : " + v);
	  for(int i = 0; i < vertexList.size(); i++)
	  {
		  if(vertexList.get(i).element.equals(v))
		  {
			  vertexList.remove(i);
		  }
	  }
	  
	  for(int i = 0; i < vertexList.size(); i++)
	  {
		  for(int j = 0; j < vertexList.get(i).outGoings.size(); j++)
		  {
			  if(vertexList.get(i).outGoings.get(j).element.equals(v))
			  {
				  vertexList.get(i).outGoings.remove(j);
			  }
		  }
	  }
	
	  for(int i = edgeList.size() - 1; i >= 0; i--)
	  {
		 if(edgeList.get(i).ends.get(0).element == v || edgeList.get(i).ends.get(1).element == v  )
		 {
			 edgeList.remove(i);
		 }
	  }
	  
	
	  return v;
  }

  @Override
  public boolean areAdjacent(V v1, V v2) {
	  //  System.out.println("System called areAdjacent()");
	  boolean b = false;
	  for(int i = 0; i < vertexList.size(); i++)
	  {
		  if(vertexList.get(i).element == v1)
		  {
			  for(int j = 0; j < vertexList.get(i).outGoings.size(); j++)
			  {
				  if(vertexList.get(i).outGoings.get(j).element == v2)
				  {
					  b = true;
					  break;
				  }
			  }
			  
		  }
		  
		  if(b)
		  {
			  break;
		  }
	  }
	  return b;
  }

  @Override
  public void insertEdge(V source, V target) {
	  //  System.out.println("\nSystem called insertEdge():");
	  Vertex<V> v1 = getVertex(source);
	  Vertex<V> v2 = getVertex(target);
	  
	  if(v1 != null && v2 != null)
	  {
		  // System.out.println("Case 1: Both vertices exist in graph: " + v1.element + "-->" + v2.element);
		  if(isThereAnyEdge(v1,v2))
		  {
			  //	  System.out.println("Edge already existed.");
			  
			  return;
		  }
		  else
		  {
			  Edge newEdge = new Edge(v1,v2);
			  edgeList.add(newEdge);
			  //	  System.out.println("Edge has been added : " + newEdge);
			  v1.outGoings.add(v2);
			  //  System.out.println("Vertex List: " + vertexList);
			  //  System.out.println("Edge list: " + edgeList);

			  return;
		  }
		  
	  }
	  
	  else if(v1 == null && v2 != null)
	  {
		  //  System.out.println("Case 2: One of the vertices exist in graph: " + source + "-->" + target);
		  insertVertex(source);
		  v1 = getVertex(source);
		  v1.outGoings.add(v2);
		  Edge newEdge = new Edge(v1,v2);
		  edgeList.add(newEdge);	
		  //  System.out.println("Vertex List: " + vertexList);
		  //  System.out.println("Edge list: " + edgeList);
		  return;
	  }
	  
	  else if(v1 != null && v2 == null)
	  {
		  //	System.out.println("Case 3: One of the vertices exist in graph: " + source + "-->" + target);
		insertVertex(target);
		v2 = getVertex(target);
		v1.outGoings.add(v2);
		Edge newEdge = new Edge(v1,v2);
		edgeList.add(newEdge);	
		//System.out.println("Vertex List: " + vertexList);
		// System.out.println("Edge list: " + edgeList);
		return;
		}
	  else if(v1==null && v2 == null)
	  {
		  //  System.out.println("Case 4: None of the vertices exist in graph: " + source + "-->" + target);
		  insertVertex(source);
		  insertVertex(target);
		  v1 = getVertex(source);
		  v2 = getVertex(target);
		  Edge newEdge = new Edge(v1,v2);
		  edgeList.add(newEdge);	
		  v1.outGoings.add(v2);
		  //  System.out.println("Vertex List: " + vertexList);
		  //  System.out.println("Edge list: " + edgeList);
		  return;
	  }
	 
  }

  private boolean isThereAnyEdge(Vertex<V> v1, Vertex<V> v2) {
	  boolean b = false;
	for(int i = 0; i < edgeList.size(); i++)
	{
		if(edgeList.get(i).ends.get(0).element == v1.element && edgeList.get(i).ends.get(1).element == v2.element)
		{
			b = true;
			break;
		}
	}
	return b;
}


@Override
  public void insertEdge(V source, V target, float weight) {
	insertEdge(source,target);
    
  }

  @Override
  public boolean removeEdge(V source, V target) {
	  //   System.out.println("System called removeEdge():");
    Vertex<V> v1 = getVertex(source);
	Vertex<V> v2 = getVertex(target);
	if(v1 != null && v2 != null)
	{
		if(!isThereAnyEdge(v1,v2))
		{
			//	System.out.println("Edge does not exits : " + source + "-->" + target);
			return false;
		} else {
			//		System.out.println("Case 1: Both edges exits and there is a edge : " + source + "-->" + target);
			edgeList.remove(getEdge(v1,v2));
			v1.outGoings.remove(v2);
			//	 System.out.println("Vertex List: " + vertexList);
			//	 System.out.println("Edge list: " + edgeList);
			
			return true;
		}
	}
	else if(v1 != null && v2 == null)
    {
		//System.out.println("One of the edges does not exits : " + target);
    	insertVertex(target);
    	// System.out.println("Vertex List: " + vertexList);
    	// System.out.println("Edge list: " + edgeList);
    	return true;
    }
	else if(v1 == null && v2 != null)
    {
		//System.out.println("One of the edges does not exits : " + source);
    	insertVertex(source);
    	// System.out.println("Vertex List: " + vertexList);
    	// System.out.println("Edge list: " + edgeList);
    	return true;
    }
	else if(v1 == null && v2 == null)
    {
		//System.out.println("None of the edges exist: " + source + "-->"+ target);
    	insertVertex(source);
    	insertVertex(target);
    	// System.out.println("Vertex List: " + vertexList);
    	// System.out.println("Edge list: " + edgeList);
    	return true;
    }
    
return false;
  }

  private Edge getEdge(Vertex<V> v1, Vertex<V> v2) {
	  int i = 0;
	  for(i = 0; i < edgeList.size(); i++)
		{
			if(edgeList.get(i).ends.get(0).element == v1.element && edgeList.get(i).ends.get(1).element == v2.element)
			{
				break;
				
			}
		}
	return edgeList.get(i);
}

  @Override
  public float getEdgeWeight(V source, V target) {
	  //  System.out.println("System called getEdgeWeight(): " + source + "-->" + target);
	  Vertex<V> v1 = getVertex(source);
	  Vertex<V> v2 = getVertex(target);
	  
	  if(isThereAnyEdge(v1,v2))
	  {
		  //  System.out.println("There is an edge in list : " + edgeList);
		  return 1;
	  }
	  else
	  {
		  // System.out.println("No such edge in list: " + edgeList);
		  return 0;
	  }
  }

  @Override
  public int numVertices() {
	 
   
    return vertexList.size();
  }


@Override
  public Iterable<V> vertices() {
	//System.out.println("\nSystem called the vertices method");
	  ArrayList<V> iter = new ArrayList<V>();
	  for(int i = 0; i < vertexList.size(); i++)
	  {
		  iter.add(vertexList.get(i).element);
	  }
	 
	  // System.out.println("Vertices method outputs: " + iter);
    return iter;
  }

  @Override
  public int numEdges() {
	  //  System.out.println("\nSystem called numEdges()");
	  //  System.out.println("Edge list: " + edgeList);
	  //  System.out.println("Size of edge list: " + edgeList.size());
    return edgeList.size();
  }

  @Override
  public boolean isDirected() {
    
    return true;
  }

  @Override
  public boolean isWeighted() {
    
    return false;
  }

  @Override
  public int outDegree(V v) {
	  Vertex<V> v1 = getVertex(v);
	  if(v1 == null)
	  {
		  return -1;
	  }
	  else
	  {
		  return v1.outGoings.size();
	  }
   
  }

  @Override
  public int inDegree(V v) {
	  int count = 0;
   if(v == null || !hasVertex(v))
   {
	   return -1;
   } 
   else 
   {
	   int position = 0;
	   for(int i = 0; i < vertexList.size(); i++)
	   {
		   if(vertexList.get(i).element.equals(v))
		   {
			   position = i;
			   break;
		   }
	   }
	   
	   for(int i = 0; i < position; i++)
	   {
		   for(int j = 0; j < vertexList.get(i).outGoings.size(); j++)
		   {
			   if( vertexList.get(i).outGoings.get(j).element.equals(v))
			   {
				   count++;
			   }
		   }
	   }
	   
	   for(int i = position + 1; i < vertexList.size(); i++)
	   {
		   for(int j = 0; j < vertexList.get(i).outGoings.size(); j++)
		   {
			   if( vertexList.get(i).outGoings.get(j).element.equals(v))
			   {
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
	  if(v1==null)
	  {
		  return null;
	  } else {
		 for(int i = 0; i < v1.outGoings.size(); i++)
		 {
			 iter.add(v1.outGoings.get(i).element);
		 }
	  }
    return iter;
  }

  @Override
  public Iterable<V> incomingNeighbors(V v) {
	  ArrayList<V> incomes = new ArrayList<V>();
	  if(v == null || !hasVertex(v))
	  {
		return null;  
	  }
	  else
	  {
		  for(int i = 0; i < vertexList.size(); i++)
		  {
			  if(vertexList.get(i).element != v)
			  {
				  for(int j = 0; j < vertexList.get(i).outGoings.size(); j++)
				  {
					  if(vertexList.get(i).outGoings.get(j).element.equals(v)) 
					  {
						  incomes.add(vertexList.get(i).element);
					  }
				  }
			  }
			 
			  
		  }
		  return incomes;
	  }
	  
	 
  }
}

























