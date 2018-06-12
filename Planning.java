package data;
import data.*;
import java.util.*;
import java.awt.Polygon;
public class Planning{
    private int [][]total_field;
    private Robot r;
    private Map m;
    private Polygon[] path;
    private int path_length=0;
    public Planning(Robot r1 , Map m1){
        path = new Polygon[10000];
        r=r1;
        m=m1;
    }
    public Polygon paint(int i){
        return path[i];
    }
    public int length_of_path(){
        return path_length;
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
        PriorityQueue<Tree> []p = new PriorityQueue[256];
        for(int i=0;i<256;i++)
            p[i]=new PriorityQueue();
        double []init=r.get_config(0);
        double []goal=r.get_config(1);
        double []init_crtl_x=r.crtl_x(0);
        double []init_crtl_y=r.crtl_y(0);
        double []goal_crtl_x=r.crtl_x(1);
        double []goal_crtl_y=r.crtl_y(1);
        double []double_x=r.poly_x(0);
        double []double_y=r.poly_y(0);
        boolean [][]visit=new boolean[128][128];
        int []path_x=new int[r.poly_x(0).length];
        int []path_y=new int[r.poly_y(0).length];
        int total_value=0;
        int goal_value=get_total_value((int)goal[0], (int)goal[1], 0);
        total_value = get_total_value((int)init[0], (int)init[1], 0);
        System.out.println("total_value is "+ total_value);
        Tree a=new Tree((int)init[0],(int)init[1], (int)r.get_angle(), total_value);   //(x position,y position, value, shift)
        p[0].offer(a); 
        for(int i=0;i<path_x.length;i++){
            path_x[i]=(int)double_x[i];
            path_y[i]=(int)double_y[i];
        }
        path[0] =new Polygon(path_x, path_y, path_x.length);
        for(int i=0;i<128;i++){
            for(int j=0;j<128;j++)
                visit[i][j]=false;
        }
        //}
        /*double temp_x[]=new double[init_crtl_x.length];
          double temp_y[]=new double[init_crtl_y.length];
          for(int j=0; j< init_crtl_x.length; j++){
          temp_x[j] = init_crtl_x[j];
          temp_y[j] = init_crtl_y[j];
          }*/
        boolean success=false;
        path_length=0;
        while(success==false){
            for(int i=1;i<256;i++){
                if(p[i-1].peek()==null) break;
                while(p[i-1].peek().get_visit()==true)
                    p[i-1].poll();
                Tree temp = new Tree();
                if(p[i-1].peek()==null) break;
                temp = p[i-1].peek();

                //System.out.println("the value is "+ temp.get_value());
                temp.set_visit();
                visit[temp.get_x()][temp.get_y()]=true;
                if(temp.get_value()==goal_value){
                    System.out.println("find in "+temp.get_x()+" "+temp.get_y());
                    success=true;
                    break;
                }
                double shift_x = temp.get_x() - temp.get_parent().get_x();      
                double shift_y = temp.get_y() - temp.get_parent().get_y();
                for(int j=0; j< path_x.length;j++){
                    path_x[j] += 5*shift_x;
                    path_y[j] -= 5*shift_y;

                    //System.out.println(path_x[j]/5+" "+(128-path_y[j]/5));
                }
                path[path_length]=new Polygon(path_x, path_y, path_x.length);
                path_length++;
                boolean collision=false;
                for(int j=0;j<m.all_record.length;j++){
                    for(int k=0;k<m.all_record[j].length;k++){
                        for(int l=0;l<m.all_record[j][k].length;l++){
                            if(path[path_length-1].contains(m.all_record[j][k][l][0], m.all_record[j][k][l][1])){
                                collision=true;
                                System.out.println("oopsssss");
                                i--;
                                path_length--;
                            }
                        }
                    }
                }
                if(collision){
                    for(int j=0; j< path_x.length;j++){
                        path_x[j] -= 5*shift_x;
                        path_y[j] += 5*shift_y;
                    }
                    continue;
                }
                /*for(int j=0; j< init_crtl_x.length; j++){
                  temp_x[j] = temp_x[j] + shift_x;
                  temp_y[j] = temp_y[j] + shift_y;
                  }*/
                if(temp.get_x()<127){
                    Tree b = new Tree(temp.get_x()+1, temp.get_y(), temp.get_angle(), get_total_value(temp.get_x(), temp.get_y(),1));
                    b.set_parent(temp);
                    p[i].offer(b);
                }
                if(temp.get_y()<127){
                    Tree c = new Tree(temp.get_x(), temp.get_y()+1, temp.get_angle(), get_total_value(temp.get_x(), temp.get_y(),2));
                    c.set_parent(temp);
                    p[i].offer(c);
                }
                if(temp.get_x()>1){
                    Tree d = new Tree(temp.get_x()-1, temp.get_y(), temp.get_angle(), get_total_value(temp.get_x(), temp.get_y(),3));
                    d.set_parent(temp);
                    p[i].offer(d);
                }
                if(temp.get_y()>1){
                    Tree e = new Tree(temp.get_x(), temp.get_y()-1, temp.get_angle(), get_total_value(temp.get_x(), temp.get_y(),4));
                    e.set_parent(temp);
                    p[i].offer(e);
                }
            }
        }
        /* for(int i=0;i<path_length;i++){
           System.out.println(path[i].contains(-1, -1));
           }*/
        //System.out.println(((Tree)p[0].peek()).get_value());*/
}
public int get_total_value(int x, int y, int shift){
    int total_value = 0;
    if(shift==0){
        total_value += total_field[x][y];
    }
    if(shift==1){
        if(x+1>128){
            total_value = 1020;
        }
        total_value += total_field[x+1][y];
    }
    if(shift==2){
        if(y+1>128){
            total_value = 1020;
        }
        total_value += total_field[x][y+1];
    }
    if(shift==3){
        if(x-1<0){
            total_value = 1020;
        }
        total_value += total_field[x-1][y];
    }
    if(shift==4){
        if(y-1<0){
            total_value = 1020;
        }
        total_value += total_field[x][y-1];
    }
    return total_value;
}
}
