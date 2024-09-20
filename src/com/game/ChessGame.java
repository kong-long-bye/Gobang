package com.game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessGame extends JFrame {
    private ChessBoard chessBoard;
    private Player player1;  // 玩家 1
    private Player player2;  // 玩家 2
    private int currentPlayer; // 当前玩家，1 代表玩家1，-1 代表玩家2
    private AIPlayer aiPlayer; // AI 玩家

    public ChessGame() {
        // 设置窗口标题
        this.setTitle("五子棋");
        this.setSize(300, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // 创建按钮
        JButton realVsRealButton = new JButton("真人对战");
        JButton realVsAIButton = new JButton("人机对战");

        // 设置按钮点击事件
        realVsRealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startRealVsReal(); // 真人对战模式
            }
        });

        realVsAIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startRealVsAI(); // 人机对战模式
            }
        });

        // 将按钮添加到窗口
        JPanel panel = new JPanel();
        panel.add(realVsRealButton);
        panel.add(realVsAIButton);
        this.add(panel);

        this.setVisible(true);
    }

    // 开始真人对战模式
    public void startRealVsReal() {
        // 初始化棋盘和两个玩家
        chessBoard = new ChessBoard(13, 69, 45, 55);
        //player1 = new HumanPlayer(chessBoard);  // 玩家1
        //player2 = new HumanPlayer(chessBoard);  // 玩家2
        currentPlayer = 1;  // 玩家 1 先开始

        chessBoard.init(); // 初始化棋盘

        chessBoard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 获取点击的坐标
                int x = e.getX();
                int y = e.getY();
                int row = (int) ((x - chessBoard.getMarginX() + chessBoard.getChessSize() / 2) / chessBoard.getChessSize());
                int col = (int) ((y - chessBoard.getMarginY() + chessBoard.getChessSize() / 2) / chessBoard.getChessSize());

                // 检查是否在棋盘有效区域
                if (row < 0 || row >= chessBoard.getBoardSize() || col < 0 || col >= chessBoard.getBoardSize()) {
                    return;  // 点击位置无效，直接返回
                }

                // 检查当前位置是否已经有棋子
                if (chessBoard.getChessMap()[row][col] != 0) {
                    javax.swing.JOptionPane.showMessageDialog(null, "该位置已有棋子，请选择其他位置！");
                    System.out.println("该位置已有棋子，请选择其他位置！");
                    return;
                }

                // 根据当前玩家落子
                if (currentPlayer == 1 && chessBoard.getPlayerKind() == 1) {
                    // 更新当前棋子的坐标
                    chessBoard.setCurrentX(row);
                    chessBoard.setCurrentY(col);
                    System.out.println("玩家1的回合");
                    chessBoard.placePiece(1);  // 玩家1（黑棋）落子
                    currentPlayer = -1;  // 切换到玩家2
                    chessBoard.setPlayerKind(-1);  // 更新当前棋子种类

                } else if (currentPlayer == -1 && chessBoard.getPlayerKind() == -1) {
                    // 更新当前棋子的坐标
                    chessBoard.setCurrentX(row);
                    chessBoard.setCurrentY(col);
                    System.out.println("玩家2的回合");
                    chessBoard.placePiece(-1);  // 玩家2（白棋）落子
                    currentPlayer = 1;  // 切换到玩家1
                    chessBoard.setPlayerKind(1);  // 更新当前棋子种类
                }

                // 重绘棋盘，显示最新的棋子状态
                chessBoard.repaint();

                // 检查游戏是否结束
                if (chessBoard.checkWin()) {
                    String winner = (currentPlayer == -1) ? "玩家1（黑棋）胜利！" : "玩家2（白棋）胜利！";
                    JOptionPane.showMessageDialog(chessBoard, winner);
                    chessBoard.init();  // 重新初始化棋盘
                    currentPlayer = 1;  // 玩家1重新开始
                }
            }
        });
    }


    // 开始人机对战模式
    // 开始人机对战模式
    public void startRealVsAI() {
        // 初始化棋盘和玩家1（人类）以及AI玩家
        chessBoard = new ChessBoard(13, 69, 45, 55);
        aiPlayer = new AIPlayer(chessBoard);  // AI玩家
        currentPlayer = 1;  // 玩家 1 先开始

        chessBoard.init(); // 初始化棋盘

        // 添加鼠标点击事件监听器
        chessBoard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 获取点击的坐标
                int x = e.getX();
                int y = e.getY();
                int row = (int) ((x - chessBoard.getMarginX() + chessBoard.getChessSize() / 2) / chessBoard.getChessSize());
                int col = (int) ((y - chessBoard.getMarginY() + chessBoard.getChessSize() / 2) / chessBoard.getChessSize());

                // 检查是否在棋盘有效区域
                if (row < 0 || row >= chessBoard.getBoardSize() || col < 0 || col >= chessBoard.getBoardSize()) {
                    return;  // 点击位置无效，直接返回
                }

                // 检查当前位置是否已经有棋子
                if (chessBoard.getChessMap()[row][col] != 0) {
                    javax.swing.JOptionPane.showMessageDialog(null, "该位置已有棋子，请选择其他位置！");
                    System.out.println("该位置已有棋子，请选择其他位置！");
                    return;
                }

                // 玩家1（人类）的回合落子
                if (currentPlayer == 1) {
                    // 更新当前棋子的坐标
                    chessBoard.setCurrentX(row);
                    chessBoard.setCurrentY(col);
                    System.out.println("玩家1的回合");
                    chessBoard.placePiece(1);  // 玩家1落子（黑棋）
                    currentPlayer = -1;  // 切换到AI
                    chessBoard.repaint();  // 更新棋盘显示

                    // 检查玩家1是否获胜
                    if (chessBoard.checkWin()) {
                        JOptionPane.showMessageDialog(chessBoard, "玩家1（黑棋）胜利！");
                        chessBoard.init();  // 重新初始化棋盘
                        currentPlayer = 1;  // 玩家1重新开始
                        return;
                    }

                    // AI回合，使用多线程来处理AI的动作
                    new Thread(() -> {
                        try {
                            Thread.sleep(1000);  // 延迟1秒
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                        SwingUtilities.invokeLater(() -> {
                            aiPlayer.go(-1);  // 调用AI下棋方法
                            currentPlayer = 1;  // 切换回玩家1
                            chessBoard.setPlayerKind(1);  // 更新当前棋子种类
                            chessBoard.repaint();  // 更新棋盘显示
                        });
                    }).start();

                }
            }
        });
    }

    // AI的下棋方法


    public static void main(String[] args) {
        new ChessGame();
    }
}
