package stockingproblem;

import algorithms.IntVectorIndividual;
import ga.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StockingProblemIndividual extends IntVectorIndividual<StockingProblem, StockingProblemIndividual> {
    // https://duckduckgo.com/?q=.ppt+crossover+operators+for+permutation+representation&t=newext&atb=v270-2&ia=web
    // testar cruzamento, mut: 0
    // implementar rotação: (array separada com a mesma dimensão que a solution, 1:1)
    private List<Integer>[] solution;
    private int cuts;
    private int waste;

    private int auxMaxColumns;

    private final static double WEIGHT_PENALTY_CUTS = 0.2;
    private final static double WEIGHT_PENALTY_WIDTH = 0.1;
    private final static double WEIGHT_PENALTY_WASTE = 0.7;

    public StockingProblemIndividual(StockingProblem problem, int size) {
        super(problem, size);
        for (int i = 0; i < genome.length; i++) {
            genome[i] = i;
        }
        shuffleGenome(size);
    }

    private void shuffleGenome(int size) {
        for (int i = 0; i < genome.length; i++) {
            int randomIndex = GeneticAlgorithm.random.nextInt(size);
            if (i == randomIndex) continue;
            int aux = genome[i];
            genome[i] = genome[randomIndex];
            genome[randomIndex] = aux;
        }
    }

    public StockingProblemIndividual(StockingProblemIndividual original) {
        super(original);
        this.solution = original.solution;
        this.cuts = original.cuts;
        this.waste = original.waste;
        this.auxMaxColumns = original.auxMaxColumns;
    }

    @Override
    public double computeFitness() {
        fitness = 0;
        waste = 0;
        auxMaxColumns = 0;

        solution = new ArrayList[problem.getMaterialHeight()];

        for (int i = 0; i < problem.getMaterialHeight(); i++) {
            solution[i] = new ArrayList<>();
        }

        for (int k : genome) {
            boolean placed = false;
            for (int j = 0; j < problem.getMaterialWidth(); j++) {
                for (int i = 0; i < problem.getMaterialHeight(); i++) {
                    var item = problem.getItems().get(k);
                    if (checkValidPlacement(item, i, j)) {
                        place(item, i, j);
                        placed = true;
                        break;
                    }
                }
                if (placed) break;
            }
        }

        System.out.println(Arrays.toString(genome) + ":");
        cuts = calculateCuts();
        waste += calculateNonzeroWaste();
        //System.out.println("[C:"+cuts+"|W:"+waste+"|MC:"+auxMaxColumns+"]");
        //System.out.println(solutionRepresentation(true));

        fitness = cuts * WEIGHT_PENALTY_CUTS + waste * WEIGHT_PENALTY_WASTE + auxMaxColumns * WEIGHT_PENALTY_WIDTH;
        return fitness;
    }


    private int calculateCuts() {
        int vcuts = 0; // cortes verticais (não na vertical)
        int hcuts = 0; // cortes horizontais (não na horizontal)
        // TODO: OPTIMISE (probably can be done as we place..?)
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < auxMaxColumns; j++) {

                if (solutionTileFilled(i, j) && solution[i].get(j) == 0) continue;

                if (j < solution[i].size()) {
                    // Cortes verticais
                    if (j + 1 >= solution[i].size() || solution[i].get(j) != solution[i].get(j + 1)) vcuts++;
                    if ((j - 1 >= 0) && solution[i].get(j) != solution[i].get(j - 1) && solution[i].get(j - 1) == 0) {
                        vcuts++;
                    }
                    // Cortes horizontais
                    if (i+1 < problem.getMaterialHeight()) {
                        if (j < solution[i+1].size()) {
                            if (solution[i].get(j) != solution[i + 1].get(j)) hcuts++;
                        } else hcuts++; //  j >= solution[i+1].size()
                    }

                    if (i - 1 >= 0 && j < solution[i - 1].size() && solution[i - 1].get(j) == 0) {
                        if (solution[i].get(j) != solution[i - 1].get(j))hcuts++;
                    }
                } else if (i+1 < problem.getMaterialHeight() && j >= solution[i].size() && j < solution[i+1].size()) hcuts++;
            }
        }
        return hcuts + vcuts;
    }

    // Non-zero waste (not all waste is represented by 0) wasn't being calculated, this calculates it
    private int calculateNonzeroWaste() {
        int w = 0;
        for (int i = 0; i < problem.getMaterialHeight(); i++) {
            w += auxMaxColumns - solution[i].size();
        }
        return w;
    }

    // Função que adiciona um item.getRepresentation à solução
    private void addToSolution(int lineIndex, int columnIndex, Integer value) {
        while(this.solution[lineIndex].size() <= columnIndex) { // cInd: 1
            if (this.solution[lineIndex].size() < columnIndex) {
                this.solution[lineIndex].add(0);
                waste++;
            } else {
                this.solution[lineIndex].add(value);
                if (columnIndex > auxMaxColumns-1) auxMaxColumns = columnIndex+1;
            }
        }
    }

    // Função auxiliar
    private boolean solutionTileFilled(int lineIndex, int columnIndex) {
        try {
            solution[lineIndex].get(columnIndex);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    private void place(Item item, int lineIndex, int columnIndex) {
        int[][] itemMatrix = item.getMatrix();
        for (int i = 0; i < itemMatrix.length; i++) {
            for (int j = 0; j < itemMatrix[i].length; j++) {
                if (itemMatrix[i][j] != 0) {
                    addToSolution(lineIndex + i, columnIndex + j, (int) item.getRepresentation());
                }
            }
        }
    }

    private boolean checkValidPlacement(Item item, int lineIndex, int columnIndex) {
        int[][] itemMatrix = item.getMatrix();
        for (int i = 0; i < itemMatrix.length; i++) {
            for (int j = 0; j < itemMatrix[i].length; j++) {
                if (itemMatrix[i][j] != 0) {
                    if (lineIndex + i >= problem.getMaterialHeight() || (solutionTileFilled(lineIndex+i, columnIndex+j) && this.solution[lineIndex + i].get(columnIndex + j) != 0)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private String solutionRepresentation(boolean convertToSpaces) {
        StringBuilder sb = new StringBuilder();
        String newLine = "\n";
        for (int i = 0; i < problem.getMaterialHeight(); i++) {
            for (int j = 0; j < solution[i].size(); j++) {
                if (solution[i].get(j) == 0)
                    if (convertToSpaces)
                        sb.append("  ");
                    else
                        sb.append("0 ");
                else
                    sb.append((char) solution[i].get(j).intValue()).append(" ");
            }
            sb.append(newLine);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        String newLine = "\n";
        StringBuilder sb = new StringBuilder();
        sb.append("fitness: ");
        sb.append(fitness);
        sb.append(newLine);
        sb.append("cuts: ").append(cuts);
        sb.append(newLine);
        sb.append("waste: ").append(waste);
        sb.append(newLine);
        sb.append("max columns used: ").append(auxMaxColumns);
        sb.append(newLine).append(newLine);

        sb.append(solutionRepresentation(true));


        return sb.toString();
    }

    /**
     * @param i
     * @return 1 if this object is BETTER than i, -1 if it is WORST than I and
     * 0, otherwise.
     */
    @Override
    public int compareTo(StockingProblemIndividual i) {
        return (this.fitness == i.getFitness()) ? 0 : (this.fitness < i.getFitness()) ? 1 : -1;
    }

    @Override
    public StockingProblemIndividual clone() {
        return new StockingProblemIndividual(this);

    }
}