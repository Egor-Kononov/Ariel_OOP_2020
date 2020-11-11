package ex1;

import Ex0.NodeData;
import Ex0.node_data;

import java.io.Serializable;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms, Serializable {
    private weighted_graph graph;

    public class FuelPriority implements Comparator<node_info>{

        @Override
        public int compare(node_info o1, node_info o2) {
            return  Double.compare(o1.getTag(), o2.getTag());
        }
    }

    public  WGraph_Algo(){
        this.graph = new WGraph_DS();
    }
    @Override
    public void init(weighted_graph g) {
        this.graph = g;
    }

    @Override
    public weighted_graph getGraph() {
        return this.graph;
    }

    @Override
    public weighted_graph copy() {
        WGraph_DS g = new WGraph_DS();
        for(node_info run : graph.getV()){
             g.addNode(run);
        }
        for(node_info run : graph.getV()){
            for(node_info run2 : graph.getV(run.getKey())){
                g.connect(run.getKey(), run2.getKey(),graph.getEdge(run.getKey(),run2.getKey()));
            }
        }
        return g;
    }

    @Override
    public boolean isConnected() {
        if(graph.nodeSize()==0)
            return true;
        if(graph.nodeSize()==1)
            return true;
        int count = 0;
        node_info start = null;
        for (node_info run : this.graph.getV()){ //forEach i used to get any node which is located in
            start = run;
            break;
        }
        bfs(start);
        for(node_info run : this.graph.getV()){ //here i am counting the number of vertices in which it was changed Tag
            if(run.getTag()!=-1)
                count++;
        }

        nullify();
        return this.graph.nodeSize() == count ;
    }
    public void bfs(node_info start){
        if(start == null)
            return;
        Queue<node_info> st1 = new ArrayDeque<node_info>();
        start.setTag(0);
        st1.add(start);
        while (!st1.isEmpty()){
            node_info p = st1.poll();
            for(node_info run : graph.getV(p.getKey())){
                if(run.getTag()== Integer.MAX_VALUE) {
                    run.setTag(p.getTag() + 1);
                    st1.add(run);
                }
            }
        }
    }

    public void nullify(){
        for(node_info run : graph.getV()){
            run.setTag(Integer.MAX_VALUE);
            run.setInfo("white");
        }
    }

    public HashMap<node_info,Integer> dijkatra(int src, int dest){
        FuelPriority strategy = new FuelPriority();
        PriorityQueue<node_info> pq = new PriorityQueue<>(strategy);
        HashMap<node_info,Integer> parent = new HashMap();
        node_info p = this.graph.getNode(src);
        p.setTag(0);
        pq.add(p);
        while(!pq.isEmpty()){
            node_info temp = pq.poll();
            if(temp.getInfo() == "white"){
                temp.setInfo("blue");
                if(temp.getKey() == dest){
                    return parent;
                }
                for(node_info run : this.graph.getV(temp.getKey())){
                    if(run.getInfo() != "blue" ){
                        double dist = temp.getTag() + graph.getEdge(temp.getKey(),run.getKey());
                        if(dist < run.getTag()){
                            run.setTag(dist);
                            pq.add(run);
                            if(parent.containsKey(run)){
                                parent.remove(run);
                            }
                            parent.put(run,temp.getKey());
                        }
                    }
                }
            }
        }



        return null;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if(src == dest)
            return 0;
        if(graph.getV().contains(graph.getNode(src))&&graph.getV().contains(graph.getNode(dest))) {
            dijkatra(src, dest);
            double dist = this.graph.getNode(dest).getTag();
            if(dist == Integer.MAX_VALUE)
                return -1;
            nullify();
            return dist;
        }
        return -1;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        LinkedList<node_info> path = new LinkedList<node_info>();
        if(src == dest) {
            node_info s = graph.getNode(dest);
            path.addFirst(s);
            return path;
        }
        if(graph.getV().contains(graph.getNode(src))&&graph.getV().contains(graph.getNode(dest))) {
             path = new LinkedList<node_info>();
            HashMap<node_info, Integer> h = dijkatra(src, dest);
            node_info s = graph.getNode(dest);
            if(s.getTag()==Integer.MAX_VALUE )
                return null;
            path.addFirst(s);
            int r = h.get(s);
            while (r != src) {
                r = h.get(s);
                s = graph.getNode(r);
                path.addFirst(s);
            }
            return path;
        }
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }


}
