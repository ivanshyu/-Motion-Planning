import data.Robot;
import data.Obstacle;
import data.Map;
import data.Planning;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseMotionListener;
import javax.swing.*;
import javax.swing.event.*;
public class Main extends JComponent implements MouseMotionListener{
    static Robot []r;
    static Obstacle []o;
    static Map m=new Map();
    static Planning p;
    static int rnum,onum;
    static int rpnum,opnum;
    int robot_or_obstacle,order1,order2;
    int press_x,press_y;
    int count;
    boolean find=false;

    public Main(){
        //setTitle("Motion Plannig GUI");
        addMouseMotionListener(this);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = 640-e.getY();
        find=false;
        count=0;
        press_x=0;
        press_y=0;
        //System.out.println("Mouse Clicked at X: " + x + " - Y: " + y);
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        //count++;    
        System.out.println("Mouse Pressed at X: " + x + " - Y: " + y);
        //if(count%2!=0){
        if(find==false){    
            for(int i=0;i<r.length;i++){
                for(int k=0;k<r[i].pnumber();k++){
                    double []px=r[i].poly_x(k);
                    double []py=r[i].poly_y(k);
                    int max_x=0,min_x=640,max_y=0,min_y=640;
                    for(int j=0;j<px.length;j++){
                        if(px[j]>max_x)
                            max_x=(int)px[j];
                        if(px[j]<min_x)
                            min_x=(int)px[j];
                        if(py[j]>max_y)
                            max_y=(int)py[j];
                        if(py[j]<min_y)
                            min_y=(int)py[j];
                    }

                    if(x>min_x && x< max_x && y>min_y && y<max_y){
                        System.out.println("in the polygon");
                        robot_or_obstacle=0;
                        order1=i;
                        order2=k;
                        press_x=x;
                        press_y=y;
                        find=true;
                        break;
                    }
                }
                if(find==true){
                    System.out.println("find!!!!!!!!");
                    break;
                }
            }
        }
        if(find==false){
            for(int i=0;i<o.length;i++){
                for(int k=0;k<o[i].pnumber();k++){
                    double []px=o[i].poly_x(k);
                    double []py=o[i].poly_y(k);
                    int max_x=0,min_x=640,max_y=0,min_y=640;
                    for(int j=0;j<px.length;j++){
                        if(px[j]>max_x)
                            max_x=(int)px[j];
                        if(px[j]<min_x)
                            min_x=(int)px[j];
                        if(py[j]>max_y)
                            max_y=(int)py[j];
                        if(py[j]<min_y)
                            min_y=(int)py[j];
                    }

                    if(x>min_x && x< max_x && y>min_y && y<max_y){
                        System.out.println("in the polygon");
                        robot_or_obstacle=1;
                        order1=i;
                        order2=k;
                        press_x=x;
                        press_y=y;
                        find=true;
                        break;
                    }
                }
                if(find==true){
                    System.out.println("find!!!!!!!!!");
                    break;
                }
            }
            //    }
        }
        if(find==true){
            x = e.getX();
            y = e.getY();
            System.out.println("Mouse Released at X: " + x + " - Y: " + y);
            if(e.getModifiers()==16){
                if(robot_or_obstacle==0)
                    r[order1].reset_poly(order2,(x-press_x),(press_y-y));
                else{
                    o[order1].reset_poly(order2,(x-press_x),(press_y-y));
                    m.setObstacle(o);
                }
                m.init_field(r,o,p);
            }
            else if(e.getModifiers()==4){
                double z1=Math.sqrt(press_x*press_x+press_y*press_y);
                double angle1=(Math.asin(press_y/z1));
                double z2=Math.sqrt(x*x+y*y);
                double angle2=(Math.asin(y/z2));
                //r[order1].set_angle(Math.toDegrees(angle2)-Math.toDegrees(angle1)+r[order1].get_angle());
                //System.out.println(r[order1].get_angle());
                if(robot_or_obstacle==0)
                    r[order1].angle_poly(order2,(angle2-angle1));
                else{
                    o[order1].angle_poly(order2,(angle2-angle1));
                    m.setObstacle(o);
                }
                m.init_field(r,o,p);
            }
        //find=false; 
            repaint();
            press_x=x;
            press_y=y;
        }
    }
    private static void createAndShowGUI() {
        JFrame f = new JFrame();
        f.getContentPane().add(new Main());
        f.setBounds(0,0,640,640);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

    }

    public static void main(String args[]) throws IOException{
        Robot robot = new Robot();
        r=robot.pre_data();
        rnum=robot.rnumber();
        robot.move_object(r);

        Obstacle obs = new Obstacle();
        o=obs.pre_data();
        onum=obs.onumber();
        obs.move_object(o);


        p=new Planning(r[0],m);
        m.setObstacle(o);
        m.init_field(r,o,p);
        
 //     p.addField(m);
   //   p.start(r[0],m);

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                createAndShowGUI();
            }
        });

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i=0;i<rnum;i++){
            for(int j=0;j<r[i].pnumber();j++){
                if(j<r[i].pnumber()/2)
                    g.setColor(Color.BLUE);
                else
                    g.setColor(Color.GRAY);
                int[] polyx= new int[r[i].poly_x(j).length];
                for (int k=0; k<polyx.length; ++k)
                    polyx[k] = (int) r[i].poly_x(j)[k];
                int[] polyy= new int[r[i].poly_y(j).length];
                for (int k=0; k<polyy.length; ++k)
                    polyy[k] = (int) r[i].poly_y(j)[k];
                g.fillPolygon(polyx,polyy,r[i].vnumber(j));
            }
        }
        for(int i=0;i<onum;i++){
            for(int j=0;j<o[i].pnumber();j++){
                g.setColor(Color.RED);
                int[] polyx= new int[o[i].poly_x(j).length];
                for (int k=0; k<polyx.length; ++k)
                    polyx[k] = (int) o[i].poly_x(j)[k];
                int[] polyy= new int[o[i].poly_y(j).length];
                for (int k=0; k<polyy.length; ++k)
                    polyy[k] = (int) o[i].poly_y(j)[k];
                g.fillPolygon(polyx,polyy,o[i].vnumber(j));
            }
        }
        //m.printField();
    }
}
//g.drawLine(-100,-100,0,0);

