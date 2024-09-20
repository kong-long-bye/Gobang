package com.game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HumanPlayer extends Player implements MouseListener {

    private boolean moveValid = true;
    // 构造函数
    public HumanPlayer(ChessBoard chessBoard) {
        super(chessBoard); // 人类默认黑棋

        //this.chessBoard.addMouseListener(this); // 为棋盘添加鼠标监听器
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // 获取鼠标点击的坐标
        int x = e.getX();
        int y = e.getY();

        // 计算点击的棋盘格子坐标
        int row = (int) ((x - chessBoard.getMarginX() + chessBoard.getChessSize() / 2) / chessBoard.getChessSize());
        int col = (int) ((y - chessBoard.getMarginY() + chessBoard.getChessSize() / 2) / chessBoard.getChessSize());

        // 确保点击在棋盘范围内并且该位置没有其他棋子
        if (row >= 0 && row < chessBoard.getBoardSize() && col >= 0 && col < chessBoard.getBoardSize()) {
            if (chessBoard.getChessMap()[row][col] == 0) {
                // 更新当前棋子的坐标
                chessBoard.setCurrentX(row);
                chessBoard.setCurrentY(col);

                // 玩家落子，1 表示黑棋，-1 表示白棋，根据当前玩家种类落子
                chessBoard.placePiece(chessBoard.getPlayerKind());

                // 切换到对方棋子
                chessBoard.setPlayerKind(-chessBoard.getPlayerKind());

                // 重绘棋盘
                chessBoard.repaint();

                // 检查是否胜利
                if (chessBoard.checkWin()) {
                    System.out.println("玩家胜利！");
                }
                else {
                    moveValid = true; // 如果没胜利，恢复点击权限
                }
            } else {
                //输出到窗口
                javax.swing.JOptionPane.showMessageDialog(null, "该位置已有棋子，请选择其他位置！");
                System.out.println("该位置已有棋子，请选择其他位置！");
            }
        }
    }

    // 其他接口方法的空实现
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
