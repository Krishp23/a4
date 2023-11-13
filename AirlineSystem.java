import java.util.*;
import java.io.*;

final public class AirlineSystem implements AirlineInterface {
    private ArrayList<String> cityNames = null;                      
    private Digraph G = null;  
    private ArrayList<Route> paths = new ArrayList<Route>();

    public boolean loadRoutes(String fileName) {
        try { 
            Scanner fileScan = new Scanner(new FileInputStream(fileName));
        
        int v = fileScan.nextInt();                         
        G = new Digraph(v);                                 

        cityNames = new ArrayList<String>(v);                          
        String st = fileScan.nextLine();
        for(int i = 0; i<v; i++)                            
        {
            cityNames.add(fileScan.nextLine());
        }

        while(fileScan.hasNext())                           
        {                                                  
            int from = fileScan.nextInt();
            int to = fileScan.nextInt();
            int distance = fileScan.nextInt();
            double cost = fileScan.nextDouble();
            G.addEdge(new DirectedEdge(from-1, to-1, cost, distance));
            G.addEdge(new DirectedEdge(to-1, from-1, cost, distance));
            Route newRoute = new Route(cityNames.get(from - 1), cityNames.get(to -1), distance, cost);
            Route newRouteFlip = new Route(cityNames.get(to -1), cityNames.get(from - 1), distance, cost);

            paths.add(newRoute);
            paths.add(newRouteFlip);
        }
        fileScan.close();
        }
        catch (FileNotFoundException e) {
        }
        return true;
    }

  /**
  * writes the city names and the routes into a file
  * @param fileName the String file name
  * @return true if routes saved successfully and false otherwise
  */
    public boolean saveRoutes(String fileName) 
    {
        /*
        Scanner fileScan = new Scanner(new FileInputStream(fileName));
        for(int i = 0; i<G.v; i++)                          
        {
            System.out.println(cityNames.get(i) + " to: ");     
            for(DirectedEdge e : G.adj(i))
                System.out.println("    " + cityNames.get(e.to()) + " (" + e.distance() + " miles) $" + e.cost());
            System.out.println();
        }
        return true;
        */
        return true;
    }
    


    public Set<String> retrieveCityNames() {
        HashSet<String> citySet = new HashSet<String>();
        Scanner scan = new Scanner(System.in);
	
        
	    System.out.println(G.v);
        for (int i = 0; i < G.v; i++) 
        {
            System.out.println(cityNames.get(i) + ": ");
		    citySet.add(cityNames.get(i));
	    }
	    for (int i = 0; i < G.v; i++)
        {
            for (DirectedEdge e : G.adj[i]) 
                System.out.println((e.v + 1 ) + " " + (e.w + 1) + " " + " " + e.distance+ " " + " " + e.cost + " ");
        }
        
	    return citySet;  
    }

  /**
  * returns the set of direct routes out of a given city
  * @param city the String city name
  * @return a (possibly empty) Set<Route> of Route objects representing the
  * direct routes out of city
  * @throws CityNotFoundException if the city is not found in the Airline
  * system
  */
    public Set<Route> retrieveDirectRoutesFrom(String city) throws CityNotFoundException {
        HashSet<Route> routeSet = new HashSet<Route>();
        boolean found = false;
        int index = 0;

        for (int i = 0; i < G.v; i++) 
        {
            index = i;
            if (cityNames.get(i).equals(city))
            {
                found = true;
                break;
            }
        }

        if (!found)
            return routeSet;

        for (DirectedEdge e : G.adj[index])
        {
            String source = cityNames.get(e.from());
		    String destination = cityNames.get(e.to());
		    Route temp = new Route(source, destination, e.distance, e.cost );
		    routeSet.add(temp);
        }
        return routeSet;
    }

  public Set<ArrayList<Route>> cheapestItinerary(String source,
    String destination) throws CityNotFoundException{
    return new HashSet<ArrayList<Route>>();
  }


  public Set<ArrayList<Route>> cheapestItinerary(String source,String transit, String destination) throws CityNotFoundException{
      
      
      
      return new HashSet<ArrayList<Route>>();

    }

  /**
   * finds one Minimum Spanning Tree (MST) for each connected component of
   * the graph
   * @return a (possibly empty) Set<Set<Route>> of MSTs. Each MST is a Set<Route>
   * of Route objects representing the MST edges.
   */
  public Set<Set<Route>> getMSTs(){
    return new HashSet<Set<Route>>();
  }

  public Set<ArrayList<Route>> tripsWithin(String city, double budget)
    throws CityNotFoundException {
      return new HashSet<ArrayList<Route>>();
  }


  public Set<ArrayList<Route>> tripsWithin(double budget){
    return new HashSet<ArrayList<Route>>();
  }


    public boolean deleteRoute(String source, String destination)throws CityNotFoundException{
        int start = cityNames.indexOf(source);
        int end = cityNames.indexOf(destination);
        int i;
        //int x;
        

        //unneeded flag?
        for (i = 0; i < G.v; i++)
            for (DirectedEdge e : G.adj(i))
                if (Objects.equals(e.from(),start) && Objects.equals(e.to(),end))
                {

                    G.removeEdge(e);
                }


            
        return true;
    }


    public void deleteCity(String city) throws CityNotFoundException{
        try {
            for (int i = 0; i < G.v; i++)
            {
                if (cityNames.get(i).equals(city))
                {
                    cityNames.remove(city);
                    G.v--;
                    G.adj[i] = G.adj[i+1];
                }
            } 
        }
        catch (Exception e){ 
            return;
        }
  }



    private class Digraph      {                            
        private int v;        
        private int e;            
        private LinkedList<DirectedEdge>[] adj;
        private boolean[] marked;
        private int[] edgeTo;       
        private int[] distTo;       

        
        public Digraph(int v)
        {
            if(v<0) throw new RuntimeException("Number of vertices must be nonnegative");
            this.v=v;
            this.e=0;
            @SuppressWarnings("unchecked")
            LinkedList<DirectedEdge>[] temp = (LinkedList<DirectedEdge>[]) new LinkedList[v];
            adj = temp;
            for(int i = 0; i<v; i++)
            {
                adj[i] = new LinkedList<DirectedEdge>();
            }
        }

        public void decreaseNumEdges(){
            e--;
        }

        public void removeEdge( DirectedEdge edge){
            adj[edge.from()].remove(edge);
            
        }

        
        public void addEdge(DirectedEdge edge)
        {
            int from = edge.from();
            adj[from].add(edge);
            e++;
        }

        public int v()
        {
            return this.v;
        }

      
         
        public Iterable<DirectedEdge> adj(int v)
        {
            return adj[v];
        }

        public Iterable<DirectedEdge> edges() {
            ArrayList<DirectedEdge> list = new ArrayList<DirectedEdge>();
            for (int v = 0; v < v; v++) {
                int selfLoops = 0;
                for (DirectedEdge e : adj(v)) {
                    if (e.other(v) > v) {
                        list.add(e);
                    }
                   
                    else if (e.other(v) == v) {
                        if (selfLoops % 2 == 0)
                            list.add(e);
                        selfLoops++;
                    }
                }
            }
            return list;
        }

        public void bfs(int source)
        {
            marked = new boolean[this.v];
            distTo = new int[this.v];
            edgeTo = new int[this.v];

            Queue<Integer> q = new LinkedList<Integer>();
            for(int i = 0; i<v; i++)
            {
                distTo[i] = Integer.MAX_VALUE;
                marked[i] = false;
            }
            distTo[source] = 0;
            marked[source] = true;
            q.add(source);

            while(!q.isEmpty())
            {
                int v = q.remove();
                for(DirectedEdge w : adj(v))
                {
                    if(!marked[w.to()])
                    {
                        edgeTo[w.to()] = v;
                        distTo[w.to()] = distTo[v]+1;
                        marked[w.to()] = true;
                        q.add(w.to());
                    }
                }
            }
        }

        public void digkstras(int source, int destination, boolean costNotDistance)
        {
            marked = new boolean[this.v];
            distTo = new int[this.v];
            edgeTo = new int[this.v];

            for(int i = 0; i<v; i++)
            {
                distTo[i] = Integer.MAX_VALUE;
                marked[i] = false;
            }
            distTo[source] = 0;
            marked[source] = true;
            int nMarked = 1;

            int current = source;
            while(nMarked<this.v)
            {
                for(DirectedEdge w : adj(current))
                {
                    if(costNotDistance)             
                    {
                        if(distTo[current] + w.cost() < distTo[w.to()])
                        {
                            edgeTo[w.to()] = current;
                            distTo[w.to()] = distTo[current] + (int)w.cost;
                        }
                    }
                    else                            
                    {
                        if(distTo[current] + w.distance() < distTo[w.to()])
                        {
                            edgeTo[w.to()] = current;
                            distTo[w.to()] = distTo[current] + w.distance;
                        }
                    }
                }

            
                int min = Integer.MAX_VALUE;
                current = -1;

                for(int i = 0; i<distTo.length; i++)
                {
                    if(marked[i]) continue;
                    if(distTo[i] < min)
                    {
                        min = distTo[i];
                        current = i;
                    }
                }

                if(current>= 0)
                {
                    marked[current] = true;
                    nMarked++;
                }
                else break;
            }

        }
    }

    private class DirectedEdge                             
    {
        private int v;
        private int w;
        private double cost;
        private int distance;

        public DirectedEdge(int v, int w)
        {
            this.v = v;
            this.w = w;
            this.cost = 0;
            this.distance = 0;
        }
        public DirectedEdge(int v, int w, double cost, int distance)
        {
            this.v=v;
            this.w=w;
            this.setCost(cost);;
            this.setDistance(distance);
        }

        public int from()
        {
            return v;
        }

        public int to()
        {
            return w;
        }

        public void setCost(double cost) 
        {
            this.cost = cost;
        }

        public void setDistance(int distance) 
        {
            this.distance = distance;
        }

        public int distance()
        {
            return distance;
        }

        public double cost()
        {
            return cost;
        }

        public int either()
        {
            return v;
        }

        public int other(int vertex)
        {
            if (vertex == v)
                return w;
            else if (vertex == w)
                return v;
            else
                throw new RuntimeException("Illegal endpoint");
        }
    }

	@Override
	public Set<ArrayList<String>> fewestStopsItinerary(String source, String destination) throws CityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ArrayList<Route>> shortestDistanceItinerary(String source, String destination)
			throws CityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}