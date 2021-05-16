package stockingproblem;

public class Item {

    private int id;
    private char representation;
    private int lines;
    private int columns;
    private int[][] matrix;

    public Item(int id, int[][] matrix) {
        this.id = id;
        this.representation = (id < 26) ? (char) ('A' + id) : (char) ('A' + id + 6);
        this.lines = matrix.length;
        this.columns = matrix[0].length;
        this.matrix = matrix;
    }

    public int getId() {
        return id;
    }

    public char getRepresentation() {
        return representation;
    }

    public int getLines() {
        return lines;
    }

    public int getColumns() {
        return columns;
    }


    public int[][] getMatrix() {
        return matrix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n");
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                sb.append(matrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
