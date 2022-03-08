package pl.bartoszsredzinski.sudokuapp.sudokualg;

/**
 * Class description
 *
 * @author Bartosz Średziński
 * created on 08.03.2022
 */
public class SudokuValidation{
    public static boolean isValid(int[][] board, int row, int column, int value){
        return (rowValid(board, row, value) && columnValid(board, column, value) && boxValid(board, row, column, value));
    }

    private static boolean rowValid(int[][] board, int row, int value){
        for(int col = 0; col < 9; col++){
            if(board[row][col] == value){
                return false;
            }
        }
        return true;
    }

    private static boolean columnValid(int[][] board, int column, int value){
        for(int row = 0; row < 9; row++){
            if(board[row][column] == value){
                return false;
            }
        }
        return true;
    }

    private static boolean boxValid(int[][] board, int row, int col, int value){
        int sqrt = (int)Math.sqrt(board.length);
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
