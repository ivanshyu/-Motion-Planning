package data;
import data.*;
import java.util.*;
public class Planning{
    private int [][]total_field;
    public void addField(Map m){    
        total_field = new int [128][128];
        for(int i=0;i<128;i++){
            for(int j=0;j<128;j++){
                for(int k=0;k<m.field[0].length;k++)
                    total_field[i][j]+=m.field[0][k][i][j];
                //System.out.print(total_field[i][j]+" ");
            }
            //System.out.println();
        }
    }
    public void start(Robot r, Map m){
        Queue<Tree> []p = new PriorityQueue[256];
        p[0]=new PriorityQueue();
        double []holdx0=new double [r.crtl_x(0).length];
        double []holdy0=new double [r.crtl_y(0).length];
        double []holdx1=new double [r.crtl_x(1).length];
        double []holdy1=new double [r.crtl_y(1).length];
        holdx0=r.crtl_x(0); 
        holdy0=r.crtl_y(0); 
        holdx1=r.crtl_x(1); 
        holdy1=r.crtl_y(1); 
        //System.out.println(m.field[0].length);
        for(int i=0;i<holdx0.length;i++){
            Tree a=new Tree((int)holdx0[i],(int)holdy0[i],m.field[0][i][(int)holdx0[i]][(int)holdy0[i]],total_field[(int)holdx0[i]][(int)holdy0[i]]);   //(x position,y position, individual value, total value)
            p[0].offer(a); 
        }
        /*Tree a=new Tree(0,0,0,0);
        p[0].offer(a);
        Tree b=new Tree(1,1,1,1);
        p[0].offer(b);
        Tree c=new Tree(2,2,2,2);
        p[0].offer(c);*/
        System.out.println(((Tree)p[0].peek()).get_total_value());
        
    }
}
