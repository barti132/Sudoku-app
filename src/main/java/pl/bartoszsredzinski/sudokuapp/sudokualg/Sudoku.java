package pl.bartoszsredzinski.sudokuapp.sudokualg;

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

    public Sudoku(){
        solver = new BacktrackingAlgorithm();
    }

    public Sudoku(int[][] board){
        if(checkInputBoard(board)){
            this.board = board;
            solver = new BacktrackingAlgorithm();
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    private boolean checkInputBoard(int[][] board){
        for(int[] row : board){
            if(row.length != BOARD_SIZE){
                return false;
            }
            for(int field : row){
                if(field > 9){
                    return false;
                }
            }
        }
        return true;
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
        System.out.println("generate sudoku level: " + level);
    }

    public void solve(){
        int[][] solvedBoard = solver.solve(board);
        if(solvedBoard != null){
            this.board = solvedBoard;
            System.out.println("This sudoku have solution.");
        }
        else{
            System.out.println("No solution for this sudoku.");
        }
    }

}
