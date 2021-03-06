package pl.bartoszsredzinski.sudokuapp.sudokualg;

import java.util.Arrays;
import java.util.Random;

/**
 * Sudoku class
 *
 * @author Bartosz Średziński
 * created on 20.02.2022
 */
public class Sudoku{

    private static final int BOARD_SIZE = 9;
    private int[][] board;
    private int[][] solvedBoard;
    private final BacktrackingAlgorithm solver;
    private final Random random;

    public Sudoku(){
        board = new int[BOARD_SIZE][BOARD_SIZE];
        solver = new BacktrackingAlgorithm();
        random = new Random();
    }

    public void generate(int level){
        if(level > 6){//81 fields, to solve sudoku minimal count of values is 16
            level = 6;
        }
        board = new int[BOARD_SIZE][BOARD_SIZE];
        int[][] tmp;
        do{
            setSeedForBoard();//set random values in some places
            tmp = solve();//check if random values gives a proper sudoku
        }while(tmp == null);

        //set sudoku boards
        solvedBoard = new int[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++){
            solvedBoard[i] = tmp[i].clone();
        }
        board = tmp;

        deleteFields(level * 10);//deletes random fields from board
    }

    public int[][] solve(){
        int[][] copyBoard = new int[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++){
            copyBoard[i] = board[i].clone();
        }
        return solver.solve(copyBoard);
    }

    public void setField(int val, int col, int row){
        board[col][row] = val;
    }

    public void setBoard(int[][] board){
        this.board = board;
    }

    public int[][] getBoard(){
        return board;
    }

    private void deleteFields(int limit){
        for(int i = 0; i < limit; ){
            int x = random.nextInt(BOARD_SIZE);
            int y = random.nextInt(BOARD_SIZE);
            int val = board[x][y];
            if(val != 0){
                board[x][y] = 0;

                if(solve() == null){
                    board[x][y] = val;
                }
                else{
                    i++;
                }
            }
        }
    }

    private void setSeedForBoard(){
        for(int i = 0; i < 10; i++){
            int x = random.nextInt(BOARD_SIZE);
            int y = random.nextInt(BOARD_SIZE);
            int val = random.nextInt(BOARD_SIZE) + 1;
            if(solver.isValid(board, x, y, val)){
                board[x][y] = val;
            }
        }
    }

    public int getAdvice(int x, int y){
        return solvedBoard[x][y];
    }

    public boolean isCorrect(int parseInt, int x, int y){
        return solvedBoard[x][y] == parseInt;
    }

    public boolean isWin(){
        for(int i = 0; i < BOARD_SIZE; i++){
            if(!Arrays.equals(board[i], solvedBoard[i])){
                return false;
            }
        }
        return true;
    }

    public int[][] getSolvedBoard(){
        return solvedBoard;
    }
}
