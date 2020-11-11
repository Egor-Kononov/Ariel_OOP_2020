package ex1;

import Ex0.node_data;
import jdk.jshell.spi.ExecutionControl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;


public class WGraph_DS  implements weighted_graph,Serializable {
    private HashMap<Integer, node_info> graph;
    private int numberOfNode;
    private int numberOfEdge;
    private int mc ;

    public class NodeData implements node_info, Serializable {
        private HashMap<Integer,node_info> neighbor;
        private HashMap<Integer,Double> edge;
        private int _key;
        private String info;
        private double t;


        public NodeData(int key){
            _key = key;
            neighbor = new HashMap<>();
            edge = new HashMap<>();
            info = "white";
            t = Integer.MAX_VALUE;
        }

        public NodeData(node_info other){  //copy constructor
            _key = other.getKey();
            info = other.getInfo();
            neighbor = new HashMap<>();
            edge = new HashMap<>();
            t = -1;
        }
        public  Collection<node_info> getNi() {
            if(this.neighbor == null)
                return null;
            return this.neighbor.values();
        }
        public void addNi(node_info p, double weight) {
            this.neighbor.put(p.getKey(),p);
            this.edge.put(p.getKey(), weight);
        }
        public boolean hasNi(int key) {
            for(node_info p : this.neighbor.values()){
                if(p.getKey()== key)
                    return true;
            }
            return false;
        }

        public void removeNode(node_info node) {
            neighbor.remove(node.getKey());
            edge.remove(node.getKey());
        }
        public double getWeightOfEdge(int key){
            return  this.edge.get(key);
        }

        @Override
        public int getKey() {
            return this._key;
        }

        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        @Override
        public double getTag() {
            return this.t;
        }

        @Override
        public void setTag(double t) {
            this.t = t;
        }
    }

    public WGraph_DS() {
        graph = new HashMap();
        numberOfNode = 0;
        numberOfEdge = 0;
        mc = 0;
    }



    @Override
    public node_info getNode(int key) {
        return this.graph.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        NodeData p1 = (NodeData)getNode(node1);
        NodeData p2 = (NodeData)getNode(node2);
        return p1.getNi().contains(p2);
    }

    @Override
    public double getEdge(int node1, int node2) {
        if(hasEdge(node1,node2)){
            NodeData p = (NodeData)getNode(node1);
            return p.getWeightOfEdge(node2);
        }
        return -1;
    }

    @Override
    public void addNode(int key) {
        if(getNode(key)!=null) {
            throw new RuntimeException("such a Key already exists");
        }else {
            node_info p = new NodeData(key);
            graph.put(p.getKey(), p);
            numberOfNode ++;
            mc++;
        }
    }
    public void addNode(node_info n) {  // assistant for copy constructor
        if(!this.graph.containsKey(n.getKey())) {
            node_info p = new NodeData(n);
            this.graph.put(p.getKey(), p);
            numberOfNode++;
            mc++;
        }
    }

    @Override
    public void connect(int node1, int node2, double w) {
        if(getNode(node1)==null || getNode(node2)==null ) {
            throw new RuntimeException("vertex "+node1+"or vertex "+node2+"does not exist");
        }else {
            NodeData p1 = (NodeData) getNode(node1);
            NodeData p2 = (NodeData) getNode(node2);
            if(!p1.getNi().contains(p2)&& node1 != node2) {
                p1.addNi(p2, w);
                p2.addNi(p1, w);
                numberOfEdge++;
                mc++;
            }
        }
    }

    @Override
    public Collection<node_info> getV() {
        return this.graph.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        NodeData p = (NodeData)getNode(node_id);
        return p.getNi();
    }

    @Override
    public node_info removeNode(int key) {
        if(!this.graph.containsKey(key))
            return null;

        node_info p = getNode(key);
        for(node_info i : getV(key)){
            NodeData s = (NodeData)i;
            s.removeNode(p);
            numberOfEdge --;
        }
        graph.remove(key);
        numberOfNode --;
        mc ++;
        return p;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        NodeData p1 = (NodeData)getNode(node1);
        NodeData p2 = (NodeData)getNode(node2);
        if(getV(node1).contains(p2)) {
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
