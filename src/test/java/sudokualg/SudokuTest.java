package sudokualg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bartoszsredzinski.sudokuapp.sudokualg.BacktrackingAlgorithm;
import pl.bartoszsredzinski.sudokuapp.sudokualg.Sudoku;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Class description
 *
 * @author Bartosz Średziński
 * created on 21.02.2022
 */
class SudokuTest{

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp(){
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void should_find_solution_sudoku(){
        int[][] sudoku = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        Sudoku s = new Sudoku(sudoku);
        s.solve();
        assertEquals("This sudoku have solution.", outputStreamCaptor.toString().trim());
    }

    @Test
    public void no_solution_for_sudoku(){
        int[][] sudoku = {
                {1, 1, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 4, 5, 1, 6, 6, 7, 8},
                {0, 2, 4, 0, 3, 2, 1, 7, 2},
                {1, 3, 3, 0, 0, 0, 0, 6, 1},
                {1, 4, 2, 8, 0, 0, 0, 5, 2},
                {0, 0, 1, 0, 0, 0, 0, 4, 3},
                {0, 6, 2, 0, 0, 0, 0, 3, 4},
                {0, 6, 3, 4, 0, 0, 0, 2, 5},
                {0, 7, 4, 0, 8, 0, 0, 1, 6}
        };
        Sudoku s = new Sudoku(sudoku);
        s.solve();
        assertEquals("No solution for this sudoku.", outputStreamCaptor.toString().trim());
    }

    @Test
    public void should_print_sudoku(){
        int[][] sudoku = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        Sudoku s = new Sudoku(sudoku);
        s.print();
        assertEquals(
                "5 3 0 | 0 7 0 | 0 0 0 \r\n"
                        + "6 0 0 | 1 9 5 | 0 0 0 \r\n"
                        + "0 9 8 | 0 0 0 | 0 6 0 \r\n"
                        + "---------------------\r\n"
                        + "8 0 0 | 0 6 0 | 0 0 3 \r\n"
                        + "4 0 0 | 8 0 3 | 0 0 1 \r\n"
                        + "7 0 0 | 0 2 0 | 0 0 6 \r\n"
                        + "---------------------\r\n"
                        + "0 6 0 | 0 0 0 | 2 8 0 \r\n"
                        + "0 0 0 | 4 1 9 | 0 0 5 \r\n"
                        + "0 0 0 | 0 8 0 | 0 7 9",
                outputStreamCaptor.toString().trim());
    }
}