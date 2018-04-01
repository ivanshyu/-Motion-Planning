package data;
import java.io.*;
import java.lang.*;
public class Robot {
    private double [][][]poly;   //before convert
    private int [][][]poly2;     //after convert
    private double [][]config;
    private int polygon_num=0;
    private int robot_num=0;
    private int vertice_num;
    private int crtl_num=0;
    private double [][]crtl;
    private int [][]poly_x;      //after convert, x
    private int [][]poly_y;      //after convert, y
    public Robot[] pre_data() throws IOException{
        String line;
        int i = 0;
        FileReader fr = new FileReader("Robot.dat");
        BufferedReader br = new BufferedReader(fr);
        FileWriter fw = new FileWriter("Robot_fixed.dat");
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
    public int rnumber(){
        System.out.println(robot_num);
        return robot_num;
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
    public void move_object(Robot []r){
        for(int i=0;i<robot_num;i++){
            r[i].poly2 = new int[r[i].polygon_num][20][2]; //dest point   note!!
            r[i].poly_x= new int [r[i].polygon_num][];
            r[i].poly_y= new int [r[i].polygon_num][];
            for(int j=0;j<r[i].polygon_num;j++){
                r[i].poly_x[j]=new int[r[i].poly[j].length];
                r[i].poly_y[j]=new int[r[i].poly[j].length];
                //System.out.println(r[i].polygon_num);
                for(int k=0;k<r[i].poly[j].length;k++){
                    //System.out.println(r[i].poly[j].length);
                    double hold=Math.cos(r[i].config[0][2])*r[i].poly[j][k][0]-Math.sin(r[i].config[0][2])*r[i].poly[j][k][1]+r[i].config[0][0];   //x'=cosx - sinx + dx
                    r[i].poly2[j][k][0]=(int)hold;
                    double hold2=Math.sin(r[i].config[0][2])*r[i].poly[j][k][0]+Math.cos(r[i].config[0][2])*r[i].poly[j][k][1]+r[i].config[0][1];   //y'=sinx + cosy + dy
                    r[i].poly2[j][k][1]=(int)hold2;
                    //System.out.println(r[i].poly[j].length);
                    r[i].poly_x[j][k]=(int)hold*5;
                    r[i].poly_y[j][k]=640-(int)hold2*5;
                    //System.out.println(r[i].poly_x[j][k]+", "+r[i].poly_y[j][k]);
                    System.out.println(r[i].poly2[j][k][0]+", "+r[i].poly2[j][k][1]);
                }
            }
        }
    }
    public Robot[] start() throws IOException{
        String line; 
        int hold=0;
        FileReader fr = new FileReader("Robot_fixed.dat");
        BufferedReader br = new BufferedReader(fr);
        line = br.readLine(); //number of robots
        try{  //string to int
            hold = Integer.parseInt(line,10);  
        }catch(NumberFormatException e){}
        robot_num=hold;
        Robot[] r=new Robot[robot_num];        
        for(int i=0;i<robot_num;i++){//           i=2
            r[i]=new Robot();
            //r[i].robot_num=robot_num;
            line = br.readLine();  //number of polygons
            try{  //string to int
                hold = Integer.parseInt(line,10);  
            }catch(NumberFormatException e){}
            polygon_num=hold;
            r[i].polygon_num=hold;
            for(int j=0;j<polygon_num;j++){    //  j=2
                line = br.readLine();  //number of vertices
                try{  //string to int
                    hold = Integer.parseInt(line,10);  
                }catch(NumberFormatException e){}
                vertice_num = hold;
                if (j==0)
                    r[i].poly=new double [polygon_num][vertice_num][2];
                r[i].vertice_num=vertice_num;
                for(int l=0;l<vertice_num;l++){    //l=4
                    line = br.readLine();  //vertices
                    String[] token = line.split(" ");
                    for (int k = 0; k < token.length; k++){   
                        r[i].poly[j][l][k]=Double.parseDouble(token[k]); 
                        //System.out.println(r[i].poly[j][l][k]);
                    }
            

                }
            }
                        r[i].config = new double[2][3];
            for(int j=0;j<2;j++){  //init & goal's config
                line = br.readLine();
                String[] token = line.split(" ");
                for (int k = 0; k < token.length; k++){   
                    if(k<2){
                        r[i].config[j][k]=Double.parseDouble(token[k]); 
                    }
                    else{
                        double rad=Double.parseDouble(token[k]);
                        r[i].config[j][2] = Math.toRadians(rad);
                    }
        //            System.out.println(r[i].config[j][k]);
                }

            }
            line = br.readLine();
            String[] tokens = line.split(" ");
            for (int k = 0; k < tokens.length; k++){   
                //System.out.println(poly[j][l][k]);
                hold = Integer.parseInt(tokens[k],10);  
            }

            crtl_num=hold;
            r[i].crtl = new double[crtl_num][2];
            for(int j=0;j<crtl_num;j++){  //controll point
                line = br.readLine();
                String[] token = line.split(" ");
                for (int k = 0; k < token.length; k++){   
                    r[i].crtl[j][k]=Double.parseDouble(token[k]); 
                //    System.out.println(r[i].crtl[j][k]);
                }
            }
           // System.out.println("end"); 

        }
        //System.out.println("======================");
        return r;
    }


}
