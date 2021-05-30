package stockingproblem;

import java.util.Arrays;

public class Item {

    private int id;
    private char representation;
    private int lines;
    private int columns;
    private int[][] matrix; // 0 rotations

    private int[][] matrix1R; // 1 rotation
    private int[][] matrix2R; // 2 rotations
    private int[][] matrix3R; // 3 rotations

    public Item(int id, int[][] matrix) {
        this.id = id;
        this.representation = (id < 26) ? (char) ('A' + id) : (char) ('A' + id + 6);
        this.lines = matrix.length;
        this.columns = matrix[0].length;
        this.matrix = matrix;
        this.matrix1R = null;
        this.matrix2R = null;
        this.matrix3R = null;
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

    public int[][] getMatrix(int rotation) {
        switch (rotation) {
            case 0: return matrix;
            case 1:
                if (matrix1R == null) return matrix1R = rotateMatrix(matrix, 1);
                return matrix1R;
            case 2:
                if (matrix1R == null && matrix2R == null) return matrix2R = rotateMatrix(matrix, 2);
                if (matrix2R == null) return matrix2R = rotateMatrix(matrix1R, 1);
                return matrix2R;
            case 3:
                if (matrix1R == null && matrix2R == null && matrix3R == null) return matrix3R = rotateMatrix(matrix, 3); // if matrix hasn't been rotated, rotate 3 times
                if (matrix2R == null && matrix3R == null) return matrix3R = rotateMatrix(matrix1R, 2); // if matrix has been rotated once, rotate twice
                if (matrix3R == null) return matrix3R = rotateMatrix(matrix2R, 1); // if matrix has been rotated 2 times, rotate 1 time
                return matrix3R; // if matrix has been rotated 3 times, good
            default:
                throw new IndexOutOfBoundsException("Something hasn't gone right, it shouldn't be here. (Rotation times)");
        }
    }

    private int[][] rotateMatrix(int[][] input, int times) {
        if (times <= 0) return input;
        int[][] output = null;
        int count = 0;

        do {
            count++;

            int n = input.length;
            int m = input[0].length;
            int[][] matrixToRotate = new int[m][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    matrixToRotate[j][n - 1 - i] = input[i][j];
                }
            }

            if (count == times) {
                output = matrixToRotate;
            } else {
                input = matrixToRotate;
            }
        } while(count < times);

        return output;
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
