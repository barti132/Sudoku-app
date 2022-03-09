package pl.bartoszsredzinski.sudokuapp.sudokualg;

/**
 * Implementation of backtracking algorithm
 *
 * @author Bartosz Średziński
 * created on 20.02.2022
 */
public class BacktrackingAlgorithm{

    public int[][] solve(int[][] board){

        int row = 0;
        int column = 0;
        boolean empty = false;

        //check if empty
        for(row = 0; row < 9; row++){
            for(column = 0; column < 9; column++){
                if(board[row][column] == 0){
                    empty = true;
                    break;
                }
            }
            if(empty){
                break;
            }
        }

        //no more empty spaces, sudoku is solved
        if(!empty){
            return board;
        }

        for(int i = 1; i <= 9; i++){
            if(isValid(board, row, column, i)){
                board[row][column] = i;

                if(solve(board) != null){
                    return board;
                }

                //wrong number, backtrack to empty number
                board[row][column] = 0;
            }
        }

        return null;
    }

    public boolean isValid(int[][] board, int row, int column, int value){
        return (rowValid(board, row, value) && columnValid(board, column, value) && boxValid(board, row, column, value));
    }

    private boolean rowValid(int[][] board, int row, int value){
        for(int col = 0; col < 9; col++){
            if(board[row][col] == value){
                return false;
            }
        }
        return true;
    }

    private boolean columnValid(int[][] board, int column, int value){
        for(int row = 0; row < 9; row++){
            if(board[row][column] == value){
                return false;
            }
        }
        return true;
    }

    private boolean boxValid(int[][] board, int row, int col, int value){
        int sqrt = (int) Math.sqrt(board.length);
        int boxRowStart = row - row % sqrt;
        int boxColStart = col - col % sqrt;

        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 3; c++){
                if(board[r + boxRowStart][c + boxColStart] == value){
                    return false;
                }
            }
        }

        return true;
    }
}