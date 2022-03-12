package sudokualg;

import org.junit.jupiter.api.Test;
import pl.bartoszsredzinski.sudokuapp.sudokualg.Sudoku;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Bartosz Średziński
 * created on 21.02.2022
 */
class SudokuTest{

    @Test
    public void solve_should_find_solution(){
        int[][] sudoku = {{5, 3, 0, 0, 7, 0, 0, 0, 0}, {6, 0, 0, 1, 9, 5, 0, 0, 0}, {0, 9, 8, 0, 0, 0, 0, 6, 0}, {8, 0, 0, 0, 6, 0, 0, 0, 3}, {4, 0, 0, 8, 0, 3, 0, 0, 1}, {7, 0, 0, 0, 2, 0, 0, 0, 6}, {0, 6, 0, 0, 0, 0, 2, 8, 0}, {0, 0, 0, 4, 1, 9, 0, 0, 5}, {0, 0, 0, 0, 8, 0, 0, 7, 9}};
        Sudoku s = new Sudoku();
        s.setBoard(sudoku);

        assertNotNull(s.solve());
    }

    @Test
    public void solve_should_not_find_solution(){
        int[][] sudoku = {{1, 1, 1, 0, 1, 0, 1, 0, 1}, {1, 0, 4, 5, 1, 6, 6, 7, 8}, {0, 2, 4, 0, 3, 2, 1, 7, 2}, {1, 3, 3, 0, 0, 0, 0, 6, 1}, {1, 4, 2, 8, 0, 0, 0, 5, 2}, {0, 0, 1, 0, 0, 0, 0, 4, 3}, {0, 6, 2, 0, 0, 0, 0, 3, 4}, {0, 6, 3, 4, 0, 0, 0, 2, 5}, {0, 7, 4, 0, 8, 0, 0, 1, 6}};
        Sudoku s = new Sudoku();
        s.setBoard(sudoku);

        assertNull(s.solve());
    }

    @Test
    public void generate_should_return_sudoku_with_empty_fields(){
        Sudoku s = new Sudoku();
        s.generate(1);

        assertNotNull(s.getBoard());
        int counter = 0;
        int[][] board = s.getBoard();
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(board[i][j] == 0){
                    counter++;
                }
            }
        }

        assertEquals(10, counter);
    }

    @Test
    public void setField_should_set_value_in_board(){
        Sudoku s = new Sudoku();
        s.setField(2, 0, 0);
        assertEquals(2, s.getBoard()[0][0]);
    }

    @Test
    public void setBoard_should_change_board(){
        int[][] sudoku = {{5, 3, 0, 0, 7, 0, 0, 0, 0}, {6, 0, 0, 1, 9, 5, 0, 0, 0}, {0, 9, 8, 0, 0, 0, 0, 6, 0}, {8, 0, 0, 0, 6, 0, 0, 0, 3}, {4, 0, 0, 8, 0, 3, 0, 0, 1}, {7, 0, 0, 0, 2, 0, 0, 0, 6}, {0, 6, 0, 0, 0, 0, 2, 8, 0}, {0, 0, 0, 4, 1, 9, 0, 0, 5}, {0, 0, 0, 0, 8, 0, 0, 7, 9}};
        Sudoku s = new Sudoku();
        s.setBoard(sudoku);
        assertEquals(sudoku, s.getBoard());
    }

    @Test
    public void isWin_should_return_false_for_not_solved_sudoku(){
        Sudoku s = new Sudoku();
        s.generate(1);
        assertFalse(s.isWin());
    }

    @Test
    public void isWin_should_return_true_for_solved_sudoku(){
        Sudoku s = new Sudoku();
        s.generate(1);
        s.setBoard(s.getSolvedBoard());
        assertTrue(s.isWin());
    }

}