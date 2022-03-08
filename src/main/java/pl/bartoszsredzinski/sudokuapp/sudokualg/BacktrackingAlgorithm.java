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
            if(SudokuValidation.isValid(board, row, column, i)){
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


}
