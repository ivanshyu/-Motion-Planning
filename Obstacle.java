package data;
import java.io.*;
import java.lang.*;
public class Obstacle {
    private double [][][]poly;
    private int [][][]poly2;
    private int polygon_num=0;
    private int obs_num=0;
    private int vertice_num=0;
    private int crtl_num=0;
    private int [][]poly_x;      //after convert, x
    private int [][]poly_y;      //after convert, y
    private double []config;
    public Obstacle[] pre_data() throws IOException{
        String line;
        int i = 0;
        FileReader fr = new FileReader("obstacle.dat");
        BufferedReader br = new BufferedReader(fr);
        FileWriter fw = new FileWriter("Obstacle_fixed.dat");
        while((line = br.readLine())!=null){
            if(line.startsWith("#"))
                continue;
            fw.write(line+"\n");
            fw.flush();
        }
        fw.close();
        fr.close();
        return start();
    }
    public void angle_poly(int poly,double angle){
        for(int j=0;j<pnumber();j++){
            for(int i=0;i<vnumber(j);i++){
                double hold=Math.cos(angle)*poly_x[j][i]-Math.sin(angle)*poly_y[j][i];   //x'=cosx - sinx + dx
                double hold2=Math.sin(angle)*poly_x[j][i]+Math.cos(angle)*poly_y[j][i];   //x'=cosx - sinx + dx
                System.out.println(poly_x[j][i]/5+","+ (600-poly_y[j][i])/5);
                System.out.println(hold/5+","+(600-hold2)/5);
                poly_x[j][i]=(int)hold;
                poly_y[j][i]=(int)hold2;
            }
        }
    }    
    public void reset_poly(int poly,int x,int y){
        for(int j=0;j<pnumber();j++){
            for(int i=0;i<vnumber(j);i++){
                System.out.println(poly_x[j][i]/5+","+(600-poly_y[j][i])/5);
                poly_x[j][i]+=x;
                poly_y[j][i]-=y;
                System.out.println(poly_x[j][i]/5+","+(600-poly_y[j][i])/5);
            }
        }
    }
    
        public int onumber(){
        //System.out.println(obs_num);
        return obs_num;
    }
    public int pnumber(){
        return polygon_num;
    }
    public int vnumber(int poly){
        return poly_x[poly].length;
    }
    public int[] poly_x(int poly){
        return poly_x[poly];
    }
    public int[] poly_y(int poly){
        return poly_y[poly];
    }
    public void move_object(Obstacle []o){
        for(int i=0;i<obs_num;i++){
            o[i].poly_x= new int [o[i].polygon_num][];
            o[i].poly_y= new int [o[i].polygon_num][];
            o[i].poly2 = new int [o[i].polygon_num][20][2]; //dest point   note!!
            for(int j=0;j<o[i].polygon_num;j++){
                o[i].poly_x[j]=new int[o[i].poly[j].length];
                o[i].poly_y[j]=new int[o[i].poly[j].length];
                //System.out.println(o[i].polygon_num);
                for(int k=0;k<o[i].poly[j].length;k++){
                    //System.out.println(o[i].poly[j].length);
                    double hold=Math.cos(o[i].config[2])*o[i].poly[j][k][0]-Math.sin(o[i].config[2])*o[i].poly[j][k][1]+o[i].config[0];   //x'=cosx - sinx + dx
                    o[i].poly2[j][k][0]=(int)hold;
                    double hold2=Math.sin(o[i].config[2])*o[i].poly[j][k][0]+Math.cos(o[i].config[2])*o[i].poly[j][k][1]+o[i].config[1];   //y'=sinx + cosy + dy 
                    o[i].poly2[j][k][1]=(int)hold2;
                    o[i].poly_x[j][k]=(int)hold*5;
                    o[i].poly_y[j][k]=640-(int)hold2*5;
                    //System.out.println(o[i].poly_x[j][k]+", "+o[i].poly_y[j][k]);
                    System.out.println(o[i].poly2[j][k][0]+", "+o[i].poly2[j][k][1]);
                }
            }
        }
    }

    public Obstacle[] start() throws IOException{

        String line;
        int hold=0;
        FileReader fr = new FileReader("Obstacle_fixed.dat");
        BufferedReader br = new BufferedReader(fr);
        line = br.readLine(); //number of obstacles
        try{  //string to int
            hold = Integer.parseInt(line,10);
        }catch(NumberFormatException e){}
        obs_num=hold;
        Obstacle[] o=new Obstacle[obs_num];
        for(int i=0;i<obs_num;i++){//           i=2
            o[i]=new Obstacle();    
            o[i].obs_num=obs_num;
            line = br.readLine();  //number of polygons
            try{  //string to int
                hold = Integer.parseInt(line,10);
            }catch(NumberFormatException e){}
            polygon_num=hold;
            o[i].polygon_num=polygon_num;
            for(int j=0;j<polygon_num;j++){    //  j=2
                line = br.readLine();  //number of vertices
                try{  //string to int
                    hold = Integer.parseInt(line,10);
                }catch(NumberFormatException e){}
                vertice_num = hold;
                if(j==0)
                    o[i].poly=new double [polygon_num][vertice_num][2];
                for(int l=0;l<vertice_num;l++){    //l=4
                    line = br.readLine();  //vertices
                    String[] token = line.split(" ");
                    for (int k = 0; k < token.length; k++){
                        o[i].poly[j][l][k]=Double.parseDouble(token[k]);
                        //System.out.println(o[i].poly[j][l][k]);
                    }
                }
            }
            line=br.readLine();
            o[i].config = new double[3];
            String[] token = line.split(" ");
            for (int k = 0; k < token.length; k++){
                if(k<2){
                    o[i].config[k]=Double.parseDouble(token[k]);
                }
                else{
                    double rad=Double.parseDouble(token[k]);
                    o[i].config[2] = Math.toRadians(rad);
                }
                //System.out.println(o[i].config[k]);
            }

        }
        //System.out.println("end");
        return o;
    }
}




