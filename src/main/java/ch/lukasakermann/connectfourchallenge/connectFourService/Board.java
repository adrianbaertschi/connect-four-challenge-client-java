package ch.lukasakermann.connectfourchallenge.connectFourService;

import java.util.List;

public class Board {
    private final List<List<String>> cells;

    public Board(List<List<String>> rows) {
        this.cells = rows;
    }

    public List<List<String>> getCells() {
        return cells;
    }

    public String getCell(int col, int row) {
        return cells.get(row).get(col);
    }

    public int getRowCount() {
        return cells.size();
    }

    public int getColumnCount() {
        return cells.get(0).size();
    }


}
