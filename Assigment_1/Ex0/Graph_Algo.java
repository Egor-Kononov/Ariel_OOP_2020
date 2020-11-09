package Ex0;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class Graph_Algo implements graph_algorithms {
    private graph g;

    public Graph_Algo(){
        this.g = g;
    }

    @Override
    public void init(graph g) {
        this.g = g;
    }

    @Override
    public graph copy() {
        graph gr = new Graph_DS();
        for(node_data run : g.getV()){
            gr.addNode(new NodeData(run));
        }
        for(node_data run : g.getV()){
            for(node_data run2 : run.getNi()){
                gr.connect(run.getKey(), run2.getKey());
            }
        }
        return gr;
    }

    @Override
    public boolean isConnected() {
        if(g.nodeSize()==0)
            return true;
        if(g.nodeSize()==1)
            return true;
        int count = 0;
        node_data start = null;
        for (node_data run : this.g.getV()){ //forEach i used to get any node which is located in
            start = run;
            break;
        }
        bfs(start);
        for(node_data run : this.g.getV()){ //here i am counting the number of vertices in which it was changed Tag
            if(run.getTag()!=-1)
                count++;
        }

        nullify();
        return this.g.nodeSize() == count ;
    }

    /**
     * Function bfs works on the basis of an algorithm Breadth-first search, I was use
     * data structure Queue, marked the level of neighbors using Tag.
     * @param start
     */


    public void bfs(node_data start){
        if(start == null)
            return;
        Queue<node_data> st1 = new ArrayDeque<node_data>();
        start.setTag(0);
        st1.add(start);
        while (!st1.isEmpty()){
            node_data p = st1.poll();
            for(node_data run : p.getNi()){
                if(run.getTag()==-1) {
                    run.setTag(p.getTag() + 1);
                    st1.add(run);
                }
            }
        }
    }

    @Override
    public int shortestPathDist(int src, int dest) {
        int dist = -1;
        node_data n = this.g.getNode(src);
        bfs(n);
        dist = g.getNode(dest).getTag();
        nullify();
        return dist ;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        LinkedList<node_data> shortestPath = new LinkedList();
        node_data start = this.g.getNode(src);
        bfs(start);
        node_data finish = this.g.getNode(dest);
        if(finish.getTag()!=-1){  //i check if exist path
            while (finish.getTag()!=0){
                shortestPath.addFirst(finish);
                for(node_data run : finish.getNi()){
                    if(run.getTag() == (finish.getTag()-1)){
                        finish = run;
                        break;
                    }
                }
            }
            shortestPath.addFirst(finish);
        }
        nullify();

        return shortestPath;
    }

    /**
     * function nullify intendend to zero out Tag
     */
    public void nullify(){
        for(node_data run : g.getV()){
            run.setTag(-1);
        }
    }
}
