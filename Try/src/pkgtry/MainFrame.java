/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgtry;
import static java.lang.Math.random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class MainFrame extends JFrame {
        private static final long serialVersionUID = 1L;
        private static long score = 0;// 分数
        private static Integer ammoNum = 5;// 子弹数量
        private static JLabel scoreLabel;// 分数
        private BackgroundPanel backgroundPanel;
        private static JLabel ammoLabel;
        private static JPanel infoPane;
        /**
         * 构造方法
         */
        public MainFrame() {
                super();
                setResizable(false);// 进制调整窗体大小
                setTitle("打猎游戏");
                infoPane = (JPanel) getGlassPane();// 获取玻璃面板
                JLabel label = new JLabel("装载子弹……");// 创建提示标签组件
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setFont(new Font("楷体", Font.BOLD, 32));
                label.setForeground(Color.RED);
                infoPane.setLayout(new BorderLayout());
                infoPane.add(label);// 添加提示标签组件到玻璃面板
                setAlwaysOnTop(true);// 是窗体保持在最顶层
                setBounds(100, 100, 573, 411);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                backgroundPanel = new BackgroundPanel();// 创建带背景的面板
                backgroundPanel.setImage(new ImageIcon(getClass().getResource(
                                "background.png")).getImage());// 设置背景图片
                getContentPane().add(backgroundPanel, BorderLayout.CENTER);
                // 添加鼠标事件适配器
                addMouseListener(new FrameMouseListener());
                scoreLabel = new JLabel();// 显示分数的标签组件
                scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
                scoreLabel.setForeground(Color.ORANGE);
                scoreLabel.setText("分数：");
                scoreLabel.setBounds(25, 15, 120, 18);
                backgroundPanel.add(scoreLabel);
                ammoLabel = new JLabel();// 显示自动数量的标签组件
                ammoLabel.setForeground(Color.ORANGE);
                ammoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                ammoLabel.setText("子弹数量：" + ammoNum);
                ammoLabel.setBounds(422, 15, 93, 18);
                backgroundPanel.add(ammoLabel);
        }
        /**
         * 加分方法
         */
        public synchronized static void appScore(int num) {
                score += num;
                scoreLabel.setText("分数：" + score);
        }
        /**
         * 消耗子弹的方法
         */
        public synchronized static void useAmmo() {
                synchronized (ammoNum) {
                        ammoNum--;// 子弹数量递减
                        ammoLabel.setText("子弹数量：" + ammoNum);
                        if (ammoNum <= 0) {// 判断子弹是否小于0
                                new Thread(new Runnable() {
                                        public void run() {
                                                // 显示提示信息面板
                                                infoPane.setVisible(true);
                                                try {
                                                        // 1秒钟装载子弹的时间
                                                        Thread.sleep(1000);
                                                } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                }
                                                ammoNum = 5;// 恢复子弹数量
                                                // 修改子弹数量标签的文本
                                                ammoLabel.setText("子弹数量：" + ammoNum);
                                                infoPane.setVisible(false);// 隐藏提示信息面板
                                        }
                                }).start();
                        }
                }
        }
        /**
         * 判断子弹是否够用
         * 
         */
        public synchronized static boolean readyAmmo() {
                synchronized (ammoNum) {
                        return ammoNum > 0;
                }
        }
        public static void main(String args[]) {
                EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                try {
                                        MainFrame frame = new MainFrame();
                                        frame.setVisible(true);
                                        frame.start();
                                } catch (Exception e) {
                                        e.printStackTrace();
                                }
                        }
                });
        }
        /**
         * 启动游戏的方法
         */
        public void start() {
                new PigThread().start();
                new BirdThread().start();
        }
        /**
         * 窗体的鼠标事件监听器
         * 
         */
        private final class FrameMouseListener extends MouseAdapter {
                public void mousePressed(final MouseEvent e) {
                        Component at = backgroundPanel.getComponentAt(e.getPoint());
                        if (at instanceof BackgroundPanel) {// 如果点到面板也扣除子弹
                                MainFrame.useAmmo();// 消耗子弹
                        }
                        /*
                         * if (at instanceof BirdLabel) {// 如果点到小鸟 MainFrame.appScore(32);//
                         * 加分 } if (at instanceof PigLabel) {// 如果点到野猪
                         * MainFrame.appScore(11);// 加分 }
                         */
                }
        }
        /**
         * 生成猪角色的线程
         * 
         */
        class PigThread extends Thread {
                @Override
                public void run() {
                        while (true) {
                                // 创建代表野猪的标签控件
                                PigLabel pig = new PigLabel();
                                pig.setSize(120, 80);// 设置控件初始大小
                                backgroundPanel.add(pig);// 添加控件到背景面板
                                try {
                                        // 线程随机休眠一段时间
                                        sleep((long) (random() * 3000) + 500);
                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                        }
                }
        }
        /**
         * 生成鸟角色的线程
         * 
         */
        class BirdThread extends Thread {
                @Override
                public void run() {
                        while (true) {
                                // 创建代表小鸟的标签控件
                                BirdLabel bird = new BirdLabel();
                                bird.setSize(50, 50);// 设置控件初始大小
                                backgroundPanel.add(bird);// 添加控件到背景面板
                                try {
                                        // 线程随机休眠一段时间
                                        sleep((long) (Math.random() * 3000) + 500);
                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                        }
                }
        }
}
