package arithmetic.backtrack;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Backtrack2 {

    static List<List<String>> res;

    /**
     * 输入棋盘边长 n，返回所有合法的放置
     * @param n
     * @return
     */
    static List<List<String>> solveNQueens(int n) {
       if (n < 0) return null;
       res = new LinkedList<>();
       // 初始化棋盘
        char[][] board = new char[n][n];
        for (char[] chars : board) Arrays.fill(chars, '.');
        backtrack(board, 0);
        return res;
    }

    /**
     * 路径：board 中小于 row 的那些行都已经成功放置了皇后
     * 选择列表：第 row 行的所有列都是放置皇后(Q)的选择
     * 结束条件：row 超过 board 的最后一行
     * @param board
     * @param row
     */
    static void backtrack(char[][] board, int row) {
        if (row == board.length) {
            res.add(charToString(board));
            return;
        }
        int n = board[row].length;
        for (int col = 0; col < n; col++) {
            if (!isValid(board, row, col)) continue;
            board[row][col] = 'Q';
            backtrack(board, row+1);
            board[row][col] = '.';
        }
    }

    private static boolean isValid(char[][] board, int row, int col) {
        int rows = board.length;
        // check is valid is col
        for (char[] chars : board) if (chars[col]=='Q') return false;
        for (int i = row - 1, j = col + 1; i >= 0 && j < rows; i--, j++) {
            if (board[i][j] == 'Q') return false;
        }
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if(board[i][j]=='Q') return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(solveNQueens(8));
    }

    private static List<String> charToString(char[][] array){
        List<String> result = new LinkedList<>();
        for (char[] chars : array) {
            result.add(String.valueOf(chars));
        }
        return result;
    }
}
