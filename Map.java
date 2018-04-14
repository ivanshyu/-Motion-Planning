package data;
import data.Obstacle;
import java.io.*;
import java.lang.*;
public class Map{
    private int [][]map;
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
        System.out.println(max+"!!!!!!!!!!!!"+min);
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
            //h=hold[0]-hold[1];
            //System.out.print(i+" "+hold[1]+" ");
            //System.out.println(hold[0]);
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
