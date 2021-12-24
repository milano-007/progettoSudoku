package progettoSudoku;

import java.util.Collection;

public abstract class Backtracking<P,S> {

    protected abstract boolean assegnabile( P p, S s );
    protected abstract void assegna( P ps, S s );
    protected abstract void deassegna( P ps, S s );

    protected abstract void scriviSoluzione();
    protected abstract boolean esisteSoluzione();

    protected abstract P prossimoPuntoDiScelta( P p );
    protected abstract boolean esisteProssimoPuntoDiScelta( P p );


    protected abstract Collection<S> scelte( P p );

    protected boolean ultimaSoluzione() {
        return false; //cerca tutte le possibili soluzioni
    }//ultimaSoluzione

    protected abstract P primoPuntoDiScelta();

    protected final void tentativo( P p ) {
        Collection<S> sa=scelte(p); //scelte ammissibili per p
        for( S s: sa) {
            if( assegnabile(p,s) ) {
                assegna(p,s);
                if( esisteSoluzione() ) {
                    scriviSoluzione();
                    System.out.println();
                }
                if( ultimaSoluzione() )
                    break;//in questo modo blocco l'intero backtracking
                if (esisteProssimoPuntoDiScelta(p)) {
                    P prossimo = prossimoPuntoDiScelta(p);
                    tentativo( prossimo );
                }
                deassegna(p,s);
            }
        }
    }//tentativo

    public final void risolvi(P p) {
        tentativo(p);
    }//risolvi

    public final void risolvi() {
        tentativo(primoPuntoDiScelta());
    }

}
