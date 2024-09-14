package com.game;

class Player {
    protected ChessBoard chessBoard; // 持有棋盘的引用

    //构造函数
    public Player(ChessBoard chessBoard){
        this.chessBoard = chessBoard;
    }

    //下棋
    public void takeTurn() {
        // 由子类实现
    }


}
