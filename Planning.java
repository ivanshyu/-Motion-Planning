package data;
import data.*;
import java.util.*;
public class Planning{
    private int [][]totol_field;
    public void addField(Map m){
        int [][]totol_field = new int [128][128];
        for(int i=0;i<128;i++){
            for(int j=0;j<128;j++){
                for(int k=0;k<m.field[0].length;k++)
                    totol_field[i][j]+=m.field[0][k][i][j];
            }
        }
    }
    public void start(){
        PriorityQueue<Tree> []p = new PriorityQueue[256];
        p[0]=new PriorityQueue();
        Tree a=new Tree(0,0,0);
        p[0].offer(a);
        Tree b=new Tree(1,1,1);
        p[0].offer(b);
        Tree c=new Tree(2,2,2);
        p[0].offer(c);
        System.out.println(((Tree)p[0].peek()).get_value());
    }
}
