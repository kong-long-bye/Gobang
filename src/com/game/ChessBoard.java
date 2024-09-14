package com.game;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChessBoard extends JFrame {
    private double chessSize;    // 棋盘每个格子的尺寸
    private int boardSize;    // 棋盘大小，定义行列数，例如 13、15、17
    private int marginX;      // 棋盘起始X位置
    private int marginY;      // 棋盘起始Y位置
    private int[][] chessMap; // 用来表示棋盘的二维数组，0 表示空，1 表示黑棋，-1 表示白棋

    // 棋子的图片
    private Image blackChessImage;     // 黑棋图片
    private Image whiteChessImage;     // 白棋图片
    private Image boardImage;          // 棋盘图片

    //黑棋还是白棋
    private int playerKind = 1; // 1 表示黑棋，-1 表示白棋
    //棋子当前的坐标
    private int currentX;
    private int currentY;

    private boolean moveValid = true; // 用于判断是否可以落子
    //初始化棋盘
    public void init() {
        //设置游戏窗口
        this.setTitle("五子棋");
        //设置窗口大小
        this.setSize(897, 895);
        //设置窗口关闭方式
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口居中
        this.setLocationRelativeTo(null);
        //设置窗口大小不可变
        //this.setResizable(True);
        //设置窗口可见
        this.setVisible(true);
        //棋盘清零，初始化
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                chessMap[i][j] = 0;
            }
        }
        //加载棋盘图片
        try {
            // 加载图片
            blackChessImage = ImageIO.read(new File("D:/code/Java/Game/src/com/image/black.png"));
            whiteChessImage = ImageIO.read(new File("D:/code/Java/Game/src/com/image/white.png"));
            boardImage = ImageIO.read(new File("D:/code/Java/Game/src/com/image/ChessBoard.jpg"));  // 用棋盘图片代替线条
        } catch (IOException e) {
            e.printStackTrace();
        }
        //重绘棋盘
        paint(this.getGraphics());
    }
    //重写paint方法
    public void paint(Graphics g) {
        // 绘制棋盘
        g.drawImage(boardImage, 0, 10, null);
        // 绘制棋子
        int x = marginX-41;
        int y = marginY-41;

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (chessMap[i][j] == 1) {
                    g.drawImage(blackChessImage, i * (int)chessSize, j * (int)chessSize, null);
                } else if (chessMap[i][j] == -1) {
                    g.drawImage(whiteChessImage, i * (int)chessSize,  j * (int)chessSize, null);
                }
            }
        }

    }
    //构造方法，初始化棋盘
    public ChessBoard(int boardSize, int chessSize, int marginX, int marginY) {
        this.boardSize = boardSize;       // 行列数，例如13、15、17等
        this.chessSize = chessSize;       // 每个格子的尺寸
        this.marginX = marginX;           // X轴边距
        this.marginY = marginY;           // Y轴边距
        this.chessMap = new int[boardSize][boardSize];  // 初始化棋盘二维数组
        this.currentX = 0;  // 初始化当前棋子的坐标
        this.currentY = 0;  // 初始化当前棋子的坐标

        // 将棋盘传递给鼠标监听器
//        ChessMouseListener mouseListener = new ChessMouseListener(this);
//        addMouseListener(mouseListener);  // 添加鼠标监听器
    }
    //判断是否胜利
    public boolean checkWin(){
        int x = currentX;
        int y = currentY;//当前棋子的坐标
        //水平方向
        for(int i = 0; i < 5; i++){
            if(y - i >= 0 && y + 4 - i < boardSize &&
                    chessMap[x][y - i] == chessMap[x][y + 1 - i] &&
                    chessMap[x][y - i] == chessMap[x][y + 2 - i] &&
                    chessMap[x][y - i] == chessMap[x][y + 3 - i] &&
                    chessMap[x][y - i] == chessMap[x][y + 4 - i]){
                return true;
            }
        }
        //垂直方向
        for (int i = 0; i < 5; i++){
            if(x - i >= 0 && x + 4 - i < boardSize &&
                    chessMap[x - i][y] == chessMap[x + 1 - i][y] &&
                    chessMap[x - i][y] == chessMap[x + 2 - i][y] &&
                    chessMap[x - i][y] == chessMap[x + 3 - i][y] &&
                    chessMap[x - i][y] == chessMap[x + 4 - i][y]){
                return true;
            }
        }
        //左上到右下
        for(int i =0; i < 5; i++){
            if(x - i >= 0 && x + 4 - i < boardSize &&
                    y - i >= 0 && y + 4 - i < boardSize &&
                    chessMap[x - i][y - i] == chessMap[x + 1 - i][y + 1 - i] &&
                    chessMap[x - i][y - i] == chessMap[x + 2 - i][y + 2 - i] &&
                    chessMap[x - i][y - i] == chessMap[x + 3 - i][y + 3 - i] &&
                    chessMap[x - i][y - i] == chessMap[x + 4 - i][y + 4 - i]){
                return true;
            }
        }
        //右上到左下
        for(int i = 0; i < 5; i++){
            if(x + i < boardSize && x - 4 + i >= 0 &&
                    y - i >= 0 && y + 4 - i < boardSize &&
                    chessMap[x + i][y - i] == chessMap[x - 1 + i][y + 1 - i] &&
                    chessMap[x + i][y - i] == chessMap[x - 2 + i][y + 2 - i] &&
                    chessMap[x + i][y - i] == chessMap[x - 3 + i][y + 3 - i] &&
                    chessMap[x + i][y - i] == chessMap[x - 4 + i][y + 4 - i]){
                return true;
            }
        }
        return false;
    }
    // Getter方法，供ChessMouseListener使用
    public double getChessSize() {
        return chessSize;
    }
    public int getPlayerKind() {
        return playerKind;
    }
    public void setPlayerKind(int playerKind) {
        this.playerKind = playerKind;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getMarginX() {
        return marginX;
    }

    public int getMarginY() {
        return marginY;
    }

    public int[][] getChessMap() {
        return chessMap;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public int getCurrentX() {
        return currentX;
    }
    public int getCurrentY() {
        return currentY;
    }

    // 下棋
    public void placePiece(int kind) {
        int row = getCurrentX();
        int col = getCurrentY();
        if (kind == 1) {
            chessMap[row][col] = 1; // 黑棋
        } else if (kind == -1) {
            chessMap[row][col] = -1; // 白棋
        }
    }
    //指定位置下棋，ai
    public void placePiece(int kind, int row, int col) {
        if (kind == 1) {
            chessMap[row][col] = 1; // 黑棋
        } else if (kind == -1) {
            chessMap[row][col] = -1; // 白棋
        }
    }
}

//class ChessMouseListener implements MouseListener {
//    private ChessBoard chessBoard; // 引用棋盘对象，用于更新棋盘状态
//    private int[] lastClickedPosition = new int[2]; // 存储最后点击的棋盘格子坐标
//
//    public ChessMouseListener(ChessBoard chessBoard) {
//        this.chessBoard = chessBoard;
//    }
//    // 鼠标点击事件 下棋
//    @Override
//    public void mouseClicked(MouseEvent e) {
//        // 获取鼠标点击的坐标
//        int x = e.getX();
//        int y = e.getY();
//        // 输出坐标
//        System.out.println("x:" + x + " y:" + y);
//        // 计算点击的棋盘格子坐标 增加判断，是点击在棋盘内
//        int row = (int) ((x - chessBoard.getMarginX() + chessBoard.getChessSize() / 2) / chessBoard.getChessSize());
//        int col = (int) ((y - chessBoard.getMarginY() + chessBoard.getChessSize() / 2) / chessBoard.getChessSize());
//
//        // 确保点击在棋盘范围内
//        if (row >= 0 && row < chessBoard.getBoardSize() && col >= 0 && col < chessBoard.getBoardSize()) {
//            if (chessBoard.getChessMap()[row][col] == 0) {
//                // 更新当前棋子的坐标
//                chessBoard.setCurrentX(row);
//                chessBoard.setCurrentY(col);
//                // 存储最后点击的棋盘格子坐标
//                lastClickedPosition[0] = row;
//                lastClickedPosition[1] = col;
//                // 在空白位置落子，1 表示黑棋，-1 表示白棋
//                // 如果是黑棋
//                 if (chessBoard.getPlayerKind() == 1) {
//                     chessBoard.getChessMap()[row][col] = 1; // 可以改成玩家或 AI 选择的值
//                     chessBoard.setPlayerKind(-1); // 切换到白棋
//                     chessBoard.repaint();  // 重新绘制棋盘
//                 } else if (chessBoard.getPlayerKind() == -1) {
//                     chessBoard.getChessMap()[row][col] = -1; // 可以改成玩家或 AI 选择的值
//                     chessBoard.setPlayerKind(1); // 切换到黑棋
//                     chessBoard.repaint();  // 重新绘制棋盘
//                 }
//                 //判断游戏是否结束 下面之所以不同，是因为上面已经切换了playerKind，所以这里判断的是上一步的playerKind
//                 if (chessBoard.checkWin()) {
//                     if (chessBoard.getPlayerKind() == -1) {
//                         JOptionPane.showMessageDialog(chessBoard, "黑棋胜利");
//                     } else if (chessBoard.getPlayerKind() == 1) {
//                         JOptionPane.showMessageDialog(chessBoard, "白棋胜利");
//                     }
//                 }
//            } else {
//                JOptionPane.showMessageDialog(chessBoard, "此处已有棋子，请重新落子");
//            }
//        }
//    }
//
//    // 获取最后点击的棋盘格子坐标
//    public int[] getLastClickedPosition() {
//        return lastClickedPosition;
//    }
//    //判断游戏是否结束
//
//    @Override
//    public void mousePressed(MouseEvent e) {}
//
//    @Override
//    public void mouseReleased(MouseEvent e) {}
//
//    @Override
//    public void mouseEntered(MouseEvent e) {}
//
//    @Override
//    public void mouseExited(MouseEvent e) {}
//}
