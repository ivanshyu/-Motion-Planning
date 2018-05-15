package data;
import java.util.*;
public class Tree implements Comparable{
    private Tree parent;
    private int total_value;
    private int idv_value;
    private int x;
    private int y;

    private boolean visit;
    public Tree(int x1, int y1, int value0 ,int value1){
        x=x1;
        y=y1;
        idv_value=value0;
        total_value=value1;
        parent=this;
        visit=false;
    }
    public void set_visit(){
        visit=true;
    }
    public void set_parent(Tree t){
        parent=t;
    }
    public int get_total_value(){
        return total_value;
    }
    public int get_idv_value(){
        return idv_value;
    }
    public int get_x(){
        return x;
    }
    public int get_y(){
        return y;
    }
    @Override
    public int compareTo(Object o) {
        Tree tr=(Tree)o;
        if(this.total_value > tr.total_value){
            return 1;
        }else if(this.total_value < tr.total_value){
            return -1;
        }
        return 0;
    }    

}


