package data;
import java.util.*;
public class Tree implements Comparable{
    private Tree parent;
    private int value;
    private int x;
    private int y;
    public Tree(int x1, int y1, int value1){
        x=x1;
        y=y1;
        value=value1;
        parent=this;
    }
    public void change_parent(Tree t){
        parent=t;
    }
    public int get_value(){
        return value;
    }
    @Override
    public int compareTo(Object o) {
        Tree tr=(Tree)o;
        if(this.value< tr.value){
            return 1;
        }else if(this.value>tr.value){
            return -1;
        }
        return 0;
    }    

}


