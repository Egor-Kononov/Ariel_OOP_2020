package Ex0;

import java.util.*;

public class Graph_DS implements graph {
    private HashMap<Integer,node_data> graph;
    private int numberOfNode;
    private int numberOfEdge;
    private int mc ;


    public Graph_DS() {
        graph = new HashMap();
        numberOfNode = 0;
        numberOfEdge = 0;
        mc = 0;
    }


    @Override
    public node_data getNode(int key) {
        return graph.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        node_data p1 = getNode(node1);
        node_data p2 = getNode(node2);
        return p1.getNi().contains(p2);
    }

    @Override
    public void addNode(node_data n) {
        if(!this.graph.containsKey(n.getKey())) {
            this.graph.put(n.getKey(), n);
            numberOfNode++;
            mc++;
        }
    }

    @Override
    public void connect(int node1, int node2) {
        node_data p1 = getNode(node1);
        node_data p2 = getNode(node2);
        if(!p1.getNi().contains(p2)&& node1 != node2) {
            p1.addNi(p2);
            p2.addNi(p1);
            numberOfEdge++;
            mc++;
        }
    }

    @Override
    public Collection<node_data> getV() {
        return this.graph.values();
    }

    @Override
    public Collection<node_data> getV(int node_id) {
        return this.graph.get(node_id).getNi();
    }

    @Override
    public node_data removeNode(int key) {
        if(!this.graph.containsKey(key))
            return null;
        node_data p = getNode(key);
        for (node_data run : p.getNi()) {
            run.removeNode(p);
            numberOfEdge--;
        }
        graph.remove(key);
        numberOfNode --;
        mc++;
        return p;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        node_data p1 = getNode(node1);
        node_data p2 = getNode(node2);
        if(p1.getNi().contains(p2)) {
            p1.removeNode(p2);
            p2.removeNode(p1);
            numberOfEdge--;
            mc++;
        }
    }

    @Override
    public int nodeSize() {
        return this.numberOfNode;
    }

    @Override
    public int edgeSize() {
        return this.numberOfEdge;
    }

    @Override
    public int getMC() {
        return this.mc;
    }
}
