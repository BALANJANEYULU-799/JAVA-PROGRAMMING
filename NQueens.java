import java.util.Arrays;

public class NQueens {
    private int N; 
    private int[] board; 

    public NQueens(int n) {
        this.N = n;
        this.board = new int[n];
        Arrays.fill(board, -1); 

    
    public void solve() {
        if (placeQueens(0)) {
            printSolution();
        } else {
            System.out.println("No solution exists.");
        }
    }

   
    private boolean placeQueens(int row) {
        if (row == N) {
            return true; 
        }

        for (int col = 0; col < N; col++) {
            if (isSafe(row, col)) {
                board[row] = col; 
                if (placeQueens(row + 1)) { 
                    return true;
                }
                board[row] = -1; 
            }
        }
        return false; 
    }

  
    private boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
           
            if (board[i] == col || 
                board[i] - i == col - row || 
                board[i] + i == col + row) {
                return false;
            }
        }
        return true; 
    }

    
    private void printSolution() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i] == j) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int N = 8; 
        NQueens nQueens = new NQueens(N);
        nQueens.solve();
    }
}
