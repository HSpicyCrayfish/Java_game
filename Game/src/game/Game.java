/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author admin
 */
public class Game extends JFrame {
    private static final long serialVersionUID = 1L;
    private static BackgroundPanel backgroundpanel;
    private int bx;
    private int by;
    //显示剩余子弹数的窗口
    private static JLabel ammoLabel;
    //显示当前得分的窗口
    private static JLabel scoreLabel;
    private JLabel birdLabel;
    //生成鸟的间隔
    private int sleepTime=2000;
    //剩余子弹数目
    private static int ammoNum=100;
    //得分数
    private static int score=0;
    //剩余鸟的个数
    private static int birdNum=50;
    //三个不同的难易程度的按钮
    private static JButton button;
    private static JButton button1;
    private static JButton button2;
    //子弹移动速度
    private int speed=2;
    //当前界面上有几只鸟
    private static Bird[] bird;
    //生成普通鸟还是黄金鸟
    private int s;
    /**
     * @param args the command line arguments
     */
    
    public Game(){
        backgroundpanel= new BackgroundPanel();
        backgroundpanel.setImage(new ImageIcon(getClass().getResource("logo.png")).getImage());// 设置背景图片
        getContentPane().add(backgroundpanel, BorderLayout.CENTER);
        setBounds(100, 100, 1600, 900);
        //添加鼠标点击事件
        addMouseListener(new FrameMouseListener());
        button=new JButton("简单");
        button.setFont(new Font("宋体", Font.PLAIN, 32));
        button.setForeground(Color.BLUE);
        button.setName("start");
        button.setVisible(true);
        button.setSize(200,60);
        button.setLocation(690, 600);
        button.addMouseListener(new MenuMouseListener1());
        backgroundpanel.add(button);
        button1=new JButton("中等");
        button1.setFont(new Font("宋体", Font.PLAIN, 32));
        button1.setForeground(Color.BLUE);
        button1.setName("中等");
        button1.setVisible(true);
        button1.setSize(200,60);
        button1.setLocation(690, 680);
        button1.addMouseListener(new MenuMouseListener2());
        backgroundpanel.add(button1);
        button2=new JButton("困难");
        button2.setFont(new Font("宋体", Font.PLAIN, 32));
        button2.setForeground(Color.BLUE);
        button2.setName("start");
        button2.setVisible(true);
        button2.setSize(200,60);
        button2.setLocation(690, 760);
        button2.addMouseListener(new MenuMouseListener3());
        backgroundpanel.add(button2);
        bird=new Bird[8];
    }
    public static void main(String[] args) {
        Game game=new Game();
        game.setVisible(true);
    }

    private  class MenuMouseListener1 extends MouseAdapter {
        public void mousePressed(final MouseEvent e) {
                    if(e.getButton()==e.BUTTON1){
                        sleepTime=2500;
                        GameThread gamethread=new GameThread();
                        gamethread.start();
                    }
                }
    }
    private class MenuMouseListener2 extends MouseAdapter {
        public void mousePressed(final MouseEvent e) {
                    if(e.getButton()==e.BUTTON1){
                        sleepTime=1500;
                        GameThread gamethread=new GameThread();
                        gamethread.start();
                    }
                }
    }
    private class MenuMouseListener3 extends MouseAdapter {
        public void mousePressed(final MouseEvent e) {
                    if(e.getButton()==e.BUTTON1){
                        sleepTime=800;
                        GameThread gamethread=new GameThread();
                        gamethread.start();
                    }
                }
    }
    private void gameOver() {
            backgroundpanel.setImage(new ImageIcon(getClass().getResource("gameover.png")).getImage());
            //重新绘制backgroundpanel
            backgroundpanel.repaint();
            scoreLabel.setFont(new Font("宋体", Font.PLAIN, 48));
            scoreLabel.setText("你的得分："+score);
            scoreLabel.setBounds(550, 550, 500, 50);
            ammoLabel.setVisible(false);
            birdLabel.setVisible(false);
    }
    //为窗体添加鼠标左击事件
    private final class FrameMouseListener extends MouseAdapter {
        public void mousePressed(final MouseEvent e) {
            if(e.getButton()==e.BUTTON1){
                Bullet bullet=new Bullet(e.getX(),e.getY(),speed);
                bullet.setSize(50,50);
                ammoNum--;
                ammoLabel.setText("子弹数量："+ammoNum);
                backgroundpanel.add(bullet);
                new Music("src/game/bullet.wav");
                BirdbeDied birdbedied=new BirdbeDied(bullet);
                birdbedied.start();
            }
        }
    }
    //击中得分
    public static void addScore(int s){
    score+=s;
    scoreLabel.setText("当前得分："+score);
    }

    private  class GameThread extends Thread{
        public void run(){
            button.setVisible(false);
            button1.setVisible(false);
            button2.setVisible(false);
            // 更换背景图片
            backgroundpanel.setImage(new ImageIcon(getClass().getResource("background0.png")).getImage());
            //重新绘制backgroundpanel
            backgroundpanel.repaint();
            scoreLabel = new JLabel();// 显示分数的标签组件
            scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
            scoreLabel.setForeground(Color.blue);
            scoreLabel.setText("当前得分：");
            scoreLabel.setFont(new Font("宋体", Font.PLAIN, 24));
            scoreLabel.setBounds(20, 15, 240, 24);
            backgroundpanel.add(scoreLabel);
            ammoLabel = new JLabel();// 显示自动数量的标签组件
            ammoLabel.setForeground(Color.blue);
            ammoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            ammoLabel.setText("子弹数量：" + ammoNum);
            ammoLabel.setFont(new Font("宋体", Font.PLAIN, 24));
            ammoLabel.setBounds(400, 15, 240, 24);
            backgroundpanel.add(ammoLabel);
            birdLabel=new JLabel();
            birdLabel = new JLabel();// 显示分数的标签组件
            birdLabel.setHorizontalAlignment(SwingConstants.CENTER);
            birdLabel.setForeground(Color.blue);
            birdLabel.setText("剩余鸟数："+birdNum);
            birdLabel.setFont(new Font("宋体", Font.PLAIN, 24));
            birdLabel.setBounds(780, 15, 240, 24);
            backgroundpanel.add(birdLabel);
                while(birdNum>0&&ammoNum>0){
                    for(int i=0;i<8;i++){
                        if(bird[i]==null){
                            if((int)(Math.random()*10)>1){
                                s=1;
                            }else{
                                s=2;
                            }
                            birdNum--;
                            birdLabel.setText("剩余鸟数："+birdNum);
                            bird[i]=new Bird(4-(sleepTime/1000)+(int)(Math.random()*3),i,s);
                            bird[i].setSize(200, 200);// 设置控件初始大小
                            backgroundpanel.add(bird[i]);
                            break;
                        }
                    }
                    try {
                            sleep(sleepTime+(int)Math.random()*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } 
            gameOver();
        }
    }
    //该线程用于监听是否有鸟被子弹打中
    private class BirdbeDied extends Thread{
        private Bullet bullet;
        private int i;
        private int j;
        public BirdbeDied(Bullet bullet){
            this.bullet=bullet;
            i=bullet.getX();
            j=bullet.getY();
        }
        public void run(){
            Boolean flag;
            while(!bullet.end){
                for(int a=0;a<8;a++){
                    if(bird[a]!=null&&!bird[a].havedied){
                        i=bullet.getX();
                        j=bullet.getY();
                        flag=(i>=bird[a].getX()&&i<bird[a].getX()+200)&&(j>=bird[a].getY()&&j<=bird[a].getY()+100);
                        if(flag){
                            bird[a].Die(bird[a].getY()-10);
                            bullet.destory();
                            new Music("src/game/bird.wav");
                        }
                    }
                }
            }
        }
    }
    public class Music extends JApplet { 
        private static final long serialVersionUID = 1L;
        private URL url;
        public Music(String str){
           try {
                url = new URL("file:"+str);
                AudioClip aau;
                aau = Applet.newAudioClip(url);
                aau.play();
            }catch (Exception e){ 
                e.printStackTrace();
            }
        }
    }
    public static void removeBird(int i){
        bird[i]=null;
    }
}
