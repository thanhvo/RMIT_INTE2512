package vietnamfc.model;

import vietnamfc.model.Cell;

// The class to store a pair of cells. So we can easily manage the state of the game.
public class Pair {
    private Cell first;
    private Cell second;

    public Pair(Cell first, Cell second) {
        this.first = first;
        this.second = second;
    }
}
