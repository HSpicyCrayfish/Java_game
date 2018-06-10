/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgtry;

import java.awt.Container;
import java.awt.event.*;
import javax.swing.*;
public class PigLabel extends JLabel implements Runnable {
        private static final long serialVersionUID = 1L;
        // 随机生成休眠时间，即野猪移动速度
        private int sleepTime = (int) (Math.random() * 300) + 30;
        private int y = 260;// 控件的垂直坐标
        private int score = 10;// 该角色对应的分数
        private Thread thread;// 内置线程对象
        private Container parent;// 控件的父容器对象
        /**
         * 构造方法
         */
        public PigLabel() {
                super();
                ImageIcon icon = new ImageIcon(getClass().getResource("pig.gif"));// 加载野猪图片
                setIcon(icon);// 设置本组件的图标
                // 添加鼠标事件适配器
                addMouseListener(new MouseAdapter() {
                        // 按下鼠标按键的处理方法
                        public void mousePressed(final MouseEvent e) {
                                if (!MainFrame.readyAmmo())
                                        return;
                                MainFrame.useAmmo();// 消耗子弹
                                appScore();// 给游戏加分
                                destory();// 销毁本组件
                        }
                });
                // 添加组件事件适配器
                addComponentListener(new ComponentAdapter() {
                        // 调整组件大小时
                        public void componentResized(final ComponentEvent e) {
                                thread.start();// 启动线程
                        }
                });
                // 初始化线程对象
                thread = new Thread(this);
        }
        public void run() {
                parent = null;
                int width = 0;
                while (width <= 0 || parent == null) {// 获取父容器宽度
                        if (parent == null)
                                parent = getParent();
                        else
                                width = parent.getWidth();
                }
                // 从左向右移动本组件
                for (int i = 0; i < width && parent != null; i += 8) {
                        setLocation(i, y);
                        try {
                                Thread.sleep(sleepTime);// 休眠片刻
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }
                }
                if (parent != null) {
                        MainFrame.appScore(-score * 10); // 自然销毁将扣分
                }
                destory();
        }
        /**
         * 从容器移除本组件的方法
         */
        public void destory() {
                if (parent == null)
                        return;
                parent.remove(this);
                parent.repaint();
                parent = null; // 通过该语句终止线程循环
        }
        /**
         * 加分的方法
         */
        private void appScore() {
                System.out.println("野猪被击中");
                MainFrame.appScore(10);
        }
}