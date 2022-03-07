package sudokualg;

import org.junit.jupiter.api.Test;
import pl.bartoszsredzinski.sudokuapp.sudokualg.BacktrackingAlgorithm;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class description
 *
 * @author Bartosz Średziński
 * created on 21.02.2022
 */
class BacktrackingAlgorithmTest{

   @Test
   public void should_solve_sudoku(){
       BacktrackingAlgorithm alg = new BacktrackingAlgorithm();
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

       int[][] solved = {
               {5,3,4,6,7,8,9,1,2},
               {6,7,2,1,9,5,3,4,8},
               {1,9,8,3,4,2,5,6,7},
               {8,5,9,7,6,1,4,2,3},
               {4,2,6,8,5,3,7,9,1},
               {7,1,3,9,2,4,8,5,6},
               {9,6,1,5,3,7,2,8,4},
               {2,8,7,4,1,9,6,3,5},
               {3,4,5,2,8,6,1,7,9}
       };
       assertTrue(Arrays.deepEquals(solved, alg.solve(sudoku)));
   }

   @Test
   public void result_should_be_null(){
       BacktrackingAlgorithm alg = new BacktrackingAlgorithm();

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

       assertNull(alg.solve(sudoku));
   }

}