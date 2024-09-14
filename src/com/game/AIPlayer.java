package com.game;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class AIPlayer extends Player {

    class Pair<T, U> {
        T first;
        U second;
        Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }
    }

    //一个得分表的属性
    private int[][] scoreMap;
    public AIPlayer(ChessBoard chessBoard) {
        super(chessBoard);
        scoreMap = new int[chessBoard.getBoardSize()][chessBoard.getBoardSize()];//初始化得分表
    }
    public void go(int kind) {
        //sleep(1000)
       
        //获取row和col
        Pair<Integer, Integer> pair = FindMaxScore();
        int row = pair.first;
        int col = pair.second;
        this.chessBoard.setCurrentX(row);
        this.chessBoard.setCurrentY(col);
        // AI 下棋
        chessBoard.placePiece(kind,row,col);
        // 画图
        chessBoard.repaint();
        // 检查是否获胜
        if (chessBoard.checkWin()) {
            JOptionPane.showMessageDialog(chessBoard, "AI 胜利！");
            chessBoard.init();  // 重新初始化棋盘

        }
    }
    //寻找最大值的位置
    public Pair<Integer, Integer> FindMaxScore() {
        CalculateScore();//计算每个位置的得分
        int maxScore = 0;//最大得分，有可能又多个
        //创建一个列表，存储最大得分的位置，可能有多个
        List<Pair<Integer, Integer>> maxSocoreMap = new ArrayList<>();
        int size = chessBoard.getBoardSize();//获取棋盘大小
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if(chessBoard.getChessMap()[row][col] == 0 && scoreMap[row][col] >= maxScore){
                    if(scoreMap[row][col] > maxScore){
                        //记录最大值
                        maxScore = scoreMap[row][col];
                        //发现最大值清零 重新添加
                        maxSocoreMap.clear();
                        //添加最大值的位置
                        maxSocoreMap.add(new Pair(row,col));

                    }
                    else if(scoreMap[row][col] == maxScore){
                        maxSocoreMap.add(new Pair(row, col));
                    }

                }
            }
        }
        //随机选择一个最大值
        Random random = new Random();
        int randomIndex = random.nextInt(maxSocoreMap.size());
        return maxSocoreMap.get(randomIndex);
    }

    // 计算每个位置的得分
    public void CalculateScore() {
        int personNum = 0; // 黑棋连在一起的数量
        int aiNum = 0; // 白棋（AI）连在一起的数量
        int emptyNum = 0; // 空白位的数量

        // 清空得分表
        for (int row = 0; row < scoreMap.length; row++) {
            for (int col = 0; col < scoreMap[row].length; col++) {
                scoreMap[row][col] = 0;
            }
        }

        int size = chessBoard.getBoardSize();

        // 遍历棋盘上的每个位置
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // 如果当前位置已经有棋子，跳过
                if (chessBoard.getChessMap()[row][col] != 0) {
                    continue;
                }

                // 遍历四个方向（上、右、上右、下右）
                for (int y = -1; y <= 1; y++) {
                    for (int x = -1; x <= 1; x++) {
                        if (x == 0 && y == 0) continue; // 跳过当前位置
                        if (y == 0 && x != 1) continue; // 只计算四个方向

                        // 初始化变量
                        personNum = 0;
                        aiNum = 0;
                        emptyNum = 0;

                        // 黑棋（玩家）的正向计算
                        for (int i = 1; i <= 4; i++) {
                            int curRow = row + i * y;
                            int curCol = col + i * x;
                            if (curRow >= 0 && curRow < size && curCol >= 0 && curCol < size &&
                                    chessBoard.getChessMap()[curRow][curCol]  == 1) {
                                personNum++;
                            } else if (curRow >= 0 && curRow < size && curCol >= 0 && curCol < size &&
                                    chessBoard.getChessMap()[curRow][curCol]  == 0) {
                                emptyNum++;
                                break;
                            } else {
                                break;
                            }
                        }

                        // 黑棋反向计算
                        for (int i = 1; i <= 4; i++) {
                            int curRow = row - i * y;
                            int curCol = col - i * x;
                            if (curRow >= 0 && curRow < size && curCol >= 0 && curCol < size &&
                                    chessBoard.getChessMap()[curRow][curCol]  == 1) {
                                personNum++;
                            } else if (curRow >= 0 && curRow < size && curCol >= 0 && curCol < size &&
                                    chessBoard.getChessMap()[curRow][curCol]  == 0) {
                                emptyNum++;
                                break;
                            } else {
                                break;
                            }
                        }

                        // 根据玩家连子数评分
                        if (personNum == 1) {
                            scoreMap[row][col] += 10;
                        } else if (personNum == 2) {
                            if (emptyNum == 1) scoreMap[row][col] += 30;
                            if (emptyNum == 2) scoreMap[row][col] += 40;
                        } else if (personNum == 3) {
                            if (emptyNum == 1) scoreMap[row][col] += 60;
                            if (emptyNum == 2) scoreMap[row][col] += 200;
                        } else if (personNum == 4) {
                            scoreMap[row][col] += 20000;
                        }

                        // AI（白棋）的正向计算
                        emptyNum = 0; // 重置空白位计数
                        for (int i = 1; i <= 4; i++) {
                            int curRow = row + i * y;
                            int curCol = col + i * x;
                            if (curRow >= 0 && curRow < size && curCol >= 0 && curCol < size &&
                                    chessBoard.getChessMap()[curRow][curCol]  == -1) {
                                aiNum++;
                            } else if (curRow >= 0 && curRow < size && curCol >= 0 && curCol < size &&
                                    chessBoard.getChessMap()[curRow][curCol]  == 0) {
                                emptyNum++;
                                break;
                            } else {
                                break;
                            }
                        }

                        // AI的反向计算
                        for (int i = 1; i <= 4; i++) {
                            int curRow = row - i * y;
                            int curCol = col - i * x;
                            if (curRow >= 0 && curRow < size && curCol >= 0 && curCol < size &&
                                    chessBoard.getChessMap()[curRow][curCol]  == -1) {
                                aiNum++;
                            } else if (curRow >= 0 && curRow < size && curCol >= 0 && curCol < size &&
                                    chessBoard.getChessMap()[curRow][curCol]  == 0) {
                                emptyNum++;
                                break;
                            } else {
                                break;
                            }
                        }

                        // 根据AI连子数评分
                        if (aiNum == 0) {
                            scoreMap[row][col] += 5;
                        } else if (aiNum == 1) {
                            scoreMap[row][col] += 10;
                        } else if (aiNum == 2) {
                            if (emptyNum == 1) scoreMap[row][col] += 25;
                            if (emptyNum == 2) scoreMap[row][col] += 50;
                        } else if (aiNum == 3) {
                            if (emptyNum == 1) scoreMap[row][col] += 55;
                            if (emptyNum == 2) scoreMap[row][col] += 10000;
                        } else if (aiNum >= 4) {
                            scoreMap[row][col] += 30000;
                        }
                    }
                }
            }
        }
    }
}


