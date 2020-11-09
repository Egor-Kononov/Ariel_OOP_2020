package ex1;

import Ex0.NodeData;
import Ex0.node_data;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class WGraph_Algo implements weighted_graph_algorithms{
    private weighted_graph graph;
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
                if(run.getTag()==-1) {
                    run.setTag(p.getTag() + 1);
                    st1.add(run);
                }
            }
        }
    }

    public void nullify(){
        for(node_info run : graph.getV()){
            run.setTag(-1);
            run.setInfo(" ");
        }
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
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
