package progettoSudoku;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NRegine extends Backtracking<Integer,Integer> {

    private final boolean [][]board;
    private final int n;
    private int nrSol=0;
    private int regineAssegnate=0;

    public NRegine(int n) {
        if( n<=3 ) throw new IllegalArgumentException();
        board=new boolean[n][n];
        this.n=n;
    }

    @Override
    protected Collection<Integer> scelte(Integer p ) {
        List<Integer> s=new ArrayList<>();
        for( int i=0; i<n; ++i )
            s.add(i);
        return s;
    }

    @Override
    protected Integer primoPuntoDiScelta() {
        return 0;
    }

    @Override
    protected boolean esisteSoluzione() {
        return regineAssegnate==n;
    }//esisteSoluzione

    @Override
    protected boolean assegnabile( Integer r, Integer c ) {
        //è assegnabile una regina sulla colonna c della riga r?
        //verifica i vincoli di essere sotto attacco
        //verifica attacco a NORD
        for( int i=r-1; i>=0; --i )
            if( board[i][c] ) return false;
        //verifica attacco a NORD-OVEST
        for( int i=r-1,j=c-1; i>=0 && j>=0; --i,--j )
            if( board[i][j] ) return false;
        //verifica attacco a NORD-EST
        for( int i=r-1,j=c+1; i>=0 && j<=n-1; --i,++j )
            if( board[i][j] ) return false;
        return true;
    }//assegnabile

    @Override
    protected void assegna( Integer r, Integer c ) {
        board[r][c]=true;
        regineAssegnate++;
    }//assegna

    @Override
    protected void deassegna( Integer r, Integer c ) {
        board[r][c]=false;
        regineAssegnate--;
    }//deassegna

    @Override
    protected void scriviSoluzione() {
        nrSol++;
        System.out.print(nrSol+": ");
        for( int i=0; i<n; ++i ) {
            for( int j=0; j<n; ++j )
                if( board[i][j] ) {
                    System.out.print("<"+i+","+j+">");
                    break; //interrompe il ciclo di for interno
                }
        }
        System.out.println();
    }//scriviSoluzione

    @Override
    protected Integer prossimoPuntoDiScelta(Integer p) {
        if (p>=n-1)
            throw new IllegalStateException();
        return ++p;
    }

    @Override
    protected boolean esisteProssimoPuntoDiScelta(Integer p) {
        return p<n-1;
    }

    public static void main( String[] args ) {
        new NRegine(8).risolvi();
    }//main

}
