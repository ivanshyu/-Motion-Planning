package data;
import data.*;
import java.util.*;
public class Planning{
    private int [][]total_field;
    private Robot r;
    private Map m;
    public Planning(Robot r1 , Map m1){
        r=r1;
        m=m1;
    }
    public void addField(){    
        total_field = new int [128][128];
        for(int i=0;i<128;i++){
            for(int j=0;j<128;j++){
                for(int k=0;k<m.field[0].length;k++){
                    total_field[i][j]+=m.field[0][k][i][j];
                   // System.out.print(m.field[0][k][i][j]);
                }
                //System.out.print(total_field[i][j]+" ");
            }
        }

        start();
    }
    public void start(){
        Queue<Tree> []p = new PriorityQueue[256];
        p[0]=new PriorityQueue();
        double []init=r.get_config(0);
        double []holdx=r.crtl_x(0);
        double []holdy=r.crtl_y(0);
        int total_value=0;
        for(int i=0;i<holdx.length;i++){
            total_value+=total_field[(int)holdx[i]][(int)holdy[i]];
        }
        System.out.println("total_value is "+ total_value);
        Tree a=new Tree((int)init[0],(int)init[1], (int)r.get_angle(), total_value);   //(x position,y position, individual value, total value)
        //    p[0].offer(a); 
        //}
        Tree d=new Tree(0,0,0,0);
        p[0].offer(d);
        Tree b=new Tree(1,1,1,1);
        p[0].offer(b);
        Tree c=new Tree(2,2,2,2);
        p[0].offer(c);
        System.out.println(((Tree)p[0].peek()).get_value());
        
    }
}
