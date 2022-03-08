package pl.bartoszsredzinski.sudokuapp.sudokualg;

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
    private BacktrackingAlgorithm solver;
    private final Random random;

    public Sudoku(){
        board = new int[BOARD_SIZE][BOARD_SIZE];
        solver = new BacktrackingAlgorithm();
        random = new Random();
    }

    public void setBoard(int[][] board){
        this.board = board;
    }

    public int[][] getBoard(){
        return board;
    }

    public void print(){
        for(int i = 0; i < 9; i++){
            if(i % 3 == 0 && i != 0){
                System.out.println("---------------------");
            }

            for(int j = 0; j < 9; j++){
                if(j % 3 == 0 && j != 0){
                    System.out.print("| ");
                }
                System.out.print(board[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public void generate(int level){
        setEmpty();

        int [][] tmp;
        do{
            setSeedForBoard();
            tmp = solve();
        }while(tmp == null);

        board = tmp;

        deleteFields(level * 10);
        System.out.println("generate sudoku level: " + level);
    }

    private void deleteFields(int limit){
        for(int i = 0; i < limit;){
            int x = random.nextInt(BOARD_SIZE);
            int y = random.nextInt(BOARD_SIZE);
            int val = board[x][y];
            board[x][y] = 0;

            if(solve() == null){
                this.board[x][y] = val;
            }
            else {
                i++;
            }
        }
    }

    private void setSeedForBoard(){
        for(int i = 0; i < 10; i++){
            int x = random.nextInt(BOARD_SIZE);
            int y = random.nextInt(BOARD_SIZE);
            int val = random.nextInt(BOARD_SIZE) + 1;
            if(SudokuValidation.isValid(board, x, y, val)){
                board[x][y] = val;
            }
        }
    }

    public int[][] solve(){
        int[][] copyBoard = new int[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++)
            copyBoard[i] = board[i].clone();
        return solver.solve(copyBoard);
    }

    public void setEmpty(){
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                board[i][j] = 0;
            }
        }
    }

}
