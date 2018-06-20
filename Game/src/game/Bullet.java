/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class Bullet extends JLabel implements Runnable {
    
    private static final long serialVersionUID = 1L;
    private int x=0;
    private int y=0;
    private int x0=0;
    private int y0=0;
    private float k=0;
    private float b=0;
    private float m=0;
    private float l=0;
    private Thread thread;// 将线程作为成员变量
    private Container father;
    public int sleepTime=1;
    public Boolean end=false;
        
    public Bullet( int x0,int y0,int m0) {
                super();
                // 创建炮弹图标对象
                ImageIcon icon = new ImageIcon(getClass().getResource("bullet.png"));
                setIcon(icon);// 设置控件图标
                // 添加控件事件监听器
                addComponentListener(new ComponentAction());
                this.x0=x0;
                this.y0=y0;
                m=m0;
                thread = new Thread(this);// 创建线程对象
        }

    public void destory() {
        if (father == null)
                        return;
        father.remove(this);// 从父容器中移除本逐渐
        father.repaint();
        end=true;
        father = null; // 通过该语句终止线程循环
    }
    
    private final class ComponentAction extends ComponentAdapter {
                public void componentResized(final ComponentEvent e) {
                        thread.start();// 线程启动
                }
        }
    @Override
    public void run() {
        float i=0;
        float j=0;
                try {
                    while (x== 0 || father == null) {
                                if (father == null) {
                                        father = getParent();// 获取父容器
                                } else {
                                 
                                        x = father.getWidth()/2;// 获取父容器的宽度
                                        y=father.getHeight();
                                }
                        }
                k=(float)(y0-y)/(this.x0-x);
                b=(y-k*x);
                m=  (float) (m*(Math.sqrt(1/(k*k+1))));
                    if(x0>=x){
                        for (i=x,j=y;(i<2*x&&i>0)&&(j>0)&&father != null;i+=m) {
                                j=(int)(k*i+b);
                                setLocation((int)i, (int)j);// 从右向左移动本组件位置
                                thread.sleep(sleepTime);// 休眠片刻
                        }
                    }else{
                        for (i=x,j=y;(i<2*x&&i>0)&&(j>0)&&father != null;i-=m) {
                                j=(int)(k*i+b);
                                setLocation((int)i, (int)j);// 从右向左移动本组件位置
                                thread.sleep(sleepTime);// 休眠片刻
                        }
                    }
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                destory();// 移动完毕，销毁本组件
    }
}
