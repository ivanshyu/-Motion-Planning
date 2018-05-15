package data;
import java.util.*;
public class Tree implements Comparable{
    private Tree parent;
    private int value;
    private int angle;
    private int x;
    private int y;

    private boolean visit;
    public Tree(int x1, int y1, int angle1, int value1){
        x=x1;
        y=y1;
        angle=angle1;
        value=value1;
        parent=this;
        visit=false;
    }
    public void set_visit(){
        visit=true;
    }
    public void set_parent(Tree t){
        parent=t;
    }
    public int get_value(){
        return value;
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
        if(this.value > tr.value){
            return 1;
        }else if(this.value < tr.value){
            return -1;
        }
        return 0;
    }    

}


