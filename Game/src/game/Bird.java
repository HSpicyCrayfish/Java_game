/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Container;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author admin
 */
public class Bird extends JLabel implements Runnable {
    
    private static final long serialVersionUID = 1L;
    private static int y =400;
    //小鸟死亡时在x轴的位置
    private  int i=0;
    public boolean havedied=false;
    private Thread thread;// 将线程作为成员变量
    private Container father;
    private int m=0;
    private int sleepTime=5;
    private int width=0;
    private int few;
    private int s;
        
    public Bird(int m,int few,int s) {
                super();
                // 创建小鸟图标对象
                ImageIcon icon = new ImageIcon(getClass().getResource("bird"+s+".gif"));
                setIcon(icon);// 设置控件图标
                // 添加控件事件监听器
                addComponentListener(new ComponentAction());
                this.m=m;
                this.few=few;
                this.s=s;
                thread = new Thread(this);// 创建线程对象
        }

    private void destory() {
        if (father == null)
                        return;
        father.remove(this);// 从父容器中移除本逐渐
        father.repaint();
        father = null; // 通过该语句终止线程循环
    }
    
    
    private class ComponentAction extends ComponentAdapter {
                public void componentResized(final ComponentEvent e) {
                        thread.start();// 线程启动
                }
        }
    @Override
    public void run() {
        int width = 0;
        father = null;
                try {
                    //随机产生高度
                    int j=(int)(Math.random() * 400);
                    while (width <= 0 || father == null) {
                                if (father == null) {
                                        father = getParent();// 获取父容器
                                } else {
                                        width = father.getWidth();// 获取父容器的宽度
                                        y=father.getHeight();
                                }
                        }
                    //从左向右移动
                    //分数越高速度越快
                    for (; i <width&& father != null; i += m) {
                                setLocation(i, j);// 从右向左移动本组件位置
                                thread.sleep(sleepTime);// 休眠片刻
                        }
                    Game.removeBird(few);
                    destory();
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
    }
    //小鸟被打中后
    public void Die(int j){
        havedied=true;
        ImageIcon icon = new ImageIcon(getClass().getResource("bird"+s+"die.png"));
        setIcon(icon);// 设置控件图标
        Game.addScore(s);
        DieThread diethread=new DieThread(j);
        diethread.start();
    }
    public class DieThread extends Thread{
        private int j;
        public DieThread(int j){
            this.j=j;
        }
        public void run(){
        try {
            thread.stop();
            while (width <= 0 || father == null) {
                                if (father == null) {
                                        father = getParent();// 获取父容器
                                } else {
                                        width = father.getWidth();// 获取父容器的宽度
                                        y=father.getHeight();
                                }
                        }
                //从左向右移动
                //分数越高速度越快
                //这边暂时不执行
                float time=0;
                for (; (i <width)&&(j<y-200)&& father != null; i += m) {
                            j=j+(int)(3*time*time/200000);
                            setLocation(i, j);// 从右向左移动本组件位置
                            thread.sleep(sleepTime);// 休眠片刻
                            time+=sleepTime;
                    }
                ImageIcon icon = new ImageIcon(getClass().getResource("bird"+s+"die.gif"));
                setIcon(icon);// 设置控件图标
                setLocation(i,j+50);
                sleep(2500);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                Game.removeBird(few);
                destory();// 移动完毕，销毁本组件
        }
    }
    
}
