package data;
import data.Obstacle;
import java.io.*;
import java.lang.*;
import java.util.LinkedList;
import java.util.Queue;
public class Map{
    private int [][]map;
    private int [][][][]field;
    public void init_field(Robot []r,Obstacle []o){
        field=new int [r.length][][][];
        for(int i=0;i<r.length;i++){
            field[i]=new int [r[i].crtl_x(0).length][128][128];  //i for every robot
            for(int j=0;j<r[i].crtl_x(0).length;j++){   //j for every control point
                for(int k=0;k<128;k++){
                    for(int l=0;l<128;l++){
                        field[i][j][k][l]=map[k][l];
                    }
                }
            }
        }
        for(int i=0;i<r.length;i++) 
            build_field(i,r[i],o);
    }
    public void build_field(int num, Robot r,Obstacle []o){
        double []holdx=new double[r.crtl_x(1).length];
        double []holdy=new double[r.crtl_y(1).length];
        System.arraycopy( r.crtl_x(1), 0, holdx, 0, holdx.length ); 
        System.arraycopy( r.crtl_y(1), 0, holdy, 0, holdy.length );
        for(int i=0;i<holdx.length;i++){
            Queue<Integer> []x_queue = new Queue[256];    
            Queue<Integer> []y_queue = new Queue[256];
            field[num][i][(int)holdx[i]][(int)holdy[i]]=0;
            for(int j=0;j<256;j++){
                x_queue[j] = new LinkedList<Integer>();
                y_queue[j] = new LinkedList<Integer>();
            }
            x_queue[0].offer((int)holdx[i]);
            y_queue[0].offer((int)holdy[i]);
            for(int value=0;value<253;value++){         //potential field value
                for(;x_queue[value].peek()!=null;){  //queue[i]!=null
                    int x = x_queue[value].poll();   //x & y are position
                    int y = y_queue[value].poll();
                    //System.out.print(x+" "+y);
                    if(x!=0 && field[num][i][x-1][y]==254){
                        field[num][i][x-1][y]=value+1;
                        x_queue[value+1].offer(x-1);
                        y_queue[value+1].offer(y);
                    }
                    if(y!=127 && field[num][i][x][y+1]==254){
                        field[num][i][x][y+1]=value+1;
                        x_queue[value+1].offer(x);
                        y_queue[value+1].offer(y+1);
                    }
                    if(x!=127 && field[num][i][x+1][y]==254){
                        field[num][i][x+1][y]=value+1;
                        x_queue[value+1].offer(x+1);
                        y_queue[value+1].offer(y);
                    }
                    if(y!=0 && field[num][i][x][y-1]==254){
                        field[num][i][x][y-1]=value+1;
                        x_queue[value+1].offer(x);
                        y_queue[value+1].offer(y-1);
                    }
                    //System.out.println(" "+value);
                }
                    
            }

        }
    }
    public void setObstacle(Obstacle []o){
        map=new int [128][128];
        for(int i=0;i<128;i++){
            for(int j=0;j<128;j++)
                map[i][j]=254;
        }

        for(int i=0;i<o.length;i++){
            for(int j=0;j<o[i].pnumber();j++){
                draw(o[i].poly_x(j),o[i].poly_y(j));
            }
        }
    }
    public void draw(double []x , double []y){
        int []org_x=new int[x.length];
        int []org_y=new int[y.length];
        int [][]record=new int[1000][2];
        int max=0,min=128,count=0;
        for(int i=0;i<x.length;i++){
            org_x[i]=(int)x[i]/5;
            org_y[i]=(int)(640-y[i])/5;
            if(org_x[i]>max)
                max=org_x[i];
            if(org_x[i]<min)
                min=org_x[i];
        }
        //System.out.println(max+"!!!!!!!!!!!!"+min);
        for(int i=0;i<x.length;i++){
            double d;
            double dx,dy;
            if(i!=x.length-1){
                d = Math.max(Math.abs(org_x[i+1]-org_x[i]),Math.abs(org_y[i+1]-org_y[i]));
                dx = (org_x[i+1]-org_x[i])/d;
                dy = (org_y[i+1]-org_y[i])/d;
            }
            else{
                d = Math.max(Math.abs(org_x[0]-org_x[i]),Math.abs(org_y[0]-org_y[i]));
                dx = (org_x[0]-org_x[i])/d;
                dy = (org_y[0]-org_y[i])/d;
            }
            for(int j=0;j<d;j++){
                record[count][0]=(int)(org_x[i]+j*dx);
                record[count][1]=(int)(org_y[i]+j*dy);
                //System.out.println(record[count][0]+","+record[count][1]);
                count++;
            }
            //System.out.println(d);
        }
        for(int i=min;i<max;i++){
            int []hold = {0 , 128};
            for(int j=0;j<record.length;j++){
                if(record[j][0]==i && record[j][1]>hold[0])
                    hold[0]=record[j][1];
                if(record[j][0]==i && record[j][1]<hold[1])
                    hold[1]=record[j][1];
                //System.out.println(record[j][0]+" "+i);
            }
            for(int j=hold[1];j<hold[0];j++)
                map[i][j]=255;
        }
        /*for(int j=0;j<128;j++){
            for(int i=0;i<128;i++){
                if(map[i][j]==255){
                    System.out.print('*');
                }
                else
                    System.out.print(' ');
            }
            System.out.println();
        }*/
        
    }


}
