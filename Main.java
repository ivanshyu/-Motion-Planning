import data.Robot;
import data.Obstacle;
import data.Map;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
public class Main extends JComponent implements MouseListener{
    static Robot []r;
    static Obstacle []o;
    static Map m=new Map();
    static int rnum,onum;
    static int rpnum,opnum;
    int robot_or_obstacle,order1,order2;
    int press_x,press_y;
    boolean find=false;

    public Main(){
        //setTitle("Motion Plannig GUI");
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = 600-e.getY();
        System.out.println("Mouse Clicked at X: " + x + " - Y: " + y);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        //System.out.println("Mouse Entered frame at X: " + x + " - Y: " + y);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        //System.out.println("Mouse Exited frame at X: " + x + " - Y: " + y);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println("Mouse Pressed at X: " + x + " - Y: " + y);
        for(int i=0;i<r.length;i++){
            for(int k=0;k<r[i].pnumber();k++){
                int []px=r[i].poly_x(k);
                int []py=r[i].poly_y(k);
                int max_x=0,min_x=640,max_y=0,min_y=640;
                for(int j=0;j<px.length;j++){
                    if(px[j]>max_x)
                        max_x=px[j];
                    if(px[j]<min_x)
                        min_x=px[j];
                    if(py[j]>max_y)
                        max_y=py[j];
                    if(py[j]<min_y)
                        min_y=py[j];
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
            if(find==true) break;
        }
        if(find==false){
            for(int i=0;i<o.length;i++){
                for(int k=0;k<o[i].pnumber();k++){
                    int []px=o[i].poly_x(k);
                    int []py=o[i].poly_y(k);
                    int max_x=0,min_x=640,max_y=0,min_y=640;
                    for(int j=0;j<px.length;j++){
                        if(px[j]>max_x)
                            max_x=px[j];
                        if(px[j]<min_x)
                            min_x=px[j];
                        if(py[j]>max_y)
                            max_y=py[j];
                        if(py[j]<min_y)
                            min_y=py[j];
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
                if(find==true) break;
            }
        }


    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println("Mouse Released at X: " + x + " - Y: " + y);
        if(find==true){
            if(e.getModifiers()==16){
                if(robot_or_obstacle==0)
                    r[order1].reset_poly(order2,(x-press_x),(press_y-y));
                else{
                    o[order1].reset_poly(order2,(x-press_x),(press_y-y));
                    m.setObstacle(o);
                }
            }
            else if(e.getModifiers()==4){
                double z1=Math.sqrt(press_x*press_x+press_y*press_y);
                double angle1=(Math.asin(press_y/z1)/Math.PI*180);
                double z2=Math.sqrt(x*x+y*y);
                double angle2=(Math.asin(y/z2)/Math.PI*180);
                System.out.println(angle2-angle1);
                if(robot_or_obstacle==0)
                    r[order1].angle_poly(order2,(angle2-angle1)/75);
                else{
                    o[order1].angle_poly(order2,(angle2-angle1)/75);
                    m.setObstacle(o);
                }

            }
            find=false;
        }
        repaint();
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

       
        m.setObstacle(o);


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
                g.setColor(Color.BLUE);
                g.fillPolygon(r[i].poly_x(j), r[i].poly_y(j),r[i].vnumber(j));
            }
        }
        for(int i=0;i<onum;i++){
            for(int j=0;j<o[i].pnumber();j++){
                g.setColor(Color.RED);
                g.fillPolygon(o[i].poly_x(j), o[i].poly_y(j),o[i].vnumber(j));
            }
        }
    }
}
//g.drawLine(-100,-100,0,0);
