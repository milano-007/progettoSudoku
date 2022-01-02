package progettoSudoku;

public class Prova implements Runnable {
    @Override
    public void run() {
        new NRegine(8).risolvi();
    }
}
