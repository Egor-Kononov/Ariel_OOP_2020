import org.w3c.dom.Node;

import java.util.*;

public class NodeData implements node_data {
    private int _key;
    private HashSet<node_data> Neighbor;
    private static int count = 1 ;
    private String info;
    private int t;



    public NodeData(){
        _key = count;
        info = null;
        Neighbor = new HashSet<>();
        t = -1;
        count++;
    }
    public NodeData(node_data other){  //copy constractor
        _key = other.getKey();
        info = other.getInfo();
        Neighbor = new HashSet<>();
        t = -1;
    }


    @Override
    public int getKey(){
        return this._key;
    }

    @Override
    public Collection<node_data> getNi() {
        if(this.Neighbor == null)
            return null;
        return this.Neighbor;
    }

    @Override
    public boolean hasNi(int key) {
        for(node_data p : this.Neighbor){
            if(p.getKey()== key)
                return true;
        }
        return false;
    }

    @Override
    public void addNi(node_data t) {
        this.Neighbor.add(t);
    }

    @Override
    public void removeNode(node_data node) {
        Neighbor.remove(node);

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
    public int getTag() {
        return this.t;
    }

    @Override
    public void setTag(int t) {
        this.t = t;
    }
}
