package progettoSudoku;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

public class Sudoku extends Backtracking<Integer, Integer> {

    private final int[] griglia;
    private static final int SIZE = 9;

    public Sudoku(String input) {
        this.griglia = new int[SIZE*SIZE];

        StringTokenizer st = new StringTokenizer(input, "\n ",false);
        for(int i=0; i<SIZE*SIZE; i++) {
            griglia[i] = Integer.parseInt(st.nextToken());
        }
    }

    public Sudoku(int[][] board) {
        griglia = new int[SIZE*SIZE];
        int pos = 0;
        for(int i=0; i<SIZE; i++) {
            for(int j=0; j<SIZE; j++) {
                griglia[pos++] = board[i][j];
            }
        }
    }

    @Override
    protected boolean assegnabile(Integer pos, Integer scelta) {
        if(griglia[pos] != 0)
            return false;

        //controllo orizzontale
        int start = pos - (pos%SIZE);
        int a;
        for(int i=0; i<SIZE; i++) {
            a = start + i;
            if(griglia[a] == scelta)
                return false;
        }

        //controllo verticale

        start = pos - (SIZE * (pos/SIZE));
        for(int i=0; i<SIZE; i++) {
            a = start + (SIZE*i);
            if(griglia[a] == scelta)
                return false;
        }

        //controllo quadrato

        int boxRow = pos/SIZE - (pos/SIZE) % (int) (Math.sqrt(SIZE));
        int boxCol = pos%SIZE - (pos%SIZE) % (int) Math.sqrt(SIZE);

        int tmp;

        for(int r = boxRow; r<boxRow+Math.sqrt(SIZE); r++) {
            for(int d = boxCol; d<boxCol+Math.sqrt(SIZE); d++) {
                tmp = SIZE*r + d;
                if(griglia[tmp] == scelta)
                    return false;
            }
        }
        return true;
    }

    private boolean isFull() {
        for(int i=0; i<SIZE*SIZE; i++)
            if(griglia[i] == 0)
                return false;
        return true;
    }
    @Override
    protected void assegna(Integer pos, Integer scelta) {
        griglia[pos] = scelta;
    }

    @Override
    protected void deassegna(Integer pos, Integer integer) {
        griglia[pos] = 0;
    }

    @Override
    protected void scriviSoluzione() {
        for(int i=0; i<SIZE; i++) {
            for(int j=0; j<SIZE; j++) {
                System.out.print(griglia[SIZE*i + j]);
                if(j != 8)
                    System.out.print(" ");
            }
            System.out.println();
        }
    }

    @Override
    protected boolean esisteSoluzione() {
        return isFull();
    }

    @Override
    protected Integer prossimoPuntoDiScelta(Integer pos) {
        if(pos >= SIZE*SIZE-1)
            throw new IllegalStateException();
        for(;;pos++) {
            if(griglia[pos] == 0)
                return pos;
        }
    }

    @Override
    protected boolean esisteProssimoPuntoDiScelta(Integer pos) {
        for(;pos<SIZE*SIZE; pos++) {
            if(griglia[pos] == 0)
                return true;
        }
        return false;
    }

    @Override
    protected Collection<Integer> scelte(Integer integer) {
        List<Integer> lista = new ArrayList<>();
        for(int i=1; i<=SIZE; i++) {
            lista.add(i);
        }
        return lista;
    }

    protected Integer primoPuntoDiScelta() {
        for(int pos=0; pos < SIZE*SIZE-1; pos++) {
            if(griglia[pos] == 0)
                return pos;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[][] board = new int[][] {
                { 9, 0, 6, 0, 7, 0, 4, 0, 3 },
                { 0, 0, 0, 4, 0, 0, 2, 0, 0 },
                { 0, 7, 0, 0, 2, 3, 0, 1, 0 },
                { 5, 0, 0, 0, 0, 0, 1, 0, 0 },
                { 0, 4, 0, 2, 0, 8, 0, 6, 0 },
                { 0, 0, 3, 0, 9, 0, 0, 0, 5 },
                { 0, 3, 0, 7, 0, 0, 0, 5, 0 },
                { 0, 0, 7, 0, 0, 5, 0, 0, 0 },
                { 4, 0, 5, 0, 1, 0, 7, 0, 8 }
        };

        String griglia = """
                0 0 6 0 3 0 9 0 0
                0 8 0 0 0 5 0 4 0
                0 0 0 9 0 1 0 0 0
                0 4 8 0 7 0 1 0 0
                2 0 0 0 0 0 0 0 5
                0 0 3 0 6 0 4 8 0
                0 0 0 4 0 8 0 0 0
                0 7 0 2 0 0 0 3 0
                0 0 9 0 1 0 6 0 0
                """;

        Sudoku s = new Sudoku(board);
        s.risolvi();
    }
}