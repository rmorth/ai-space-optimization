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

    private final static double WEIGHT_PENALTY_CUTS = 0.2;
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
    }

    @Override
    public double computeFitness() {
        fitness = 0;
        waste = 0;

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
        System.out.println(solutionRepresentation(false));
        cuts = calculateCuts();
        waste = calculateWaste();

        fitness = cuts * WEIGHT_PENALTY_CUTS + waste * WEIGHT_PENALTY_WASTE;
        return fitness;
    }


    private int calculateCuts() {
        int vcuts = 0;
        int hcuts = 0;
        // TODO: OPTIMISE (probably can be done as we place..?)
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[i].size(); j++) {
                if (solution[i].get(j) == 0) continue;
                if (j+1 >= solution[i].size() || (solutionTileFilled(i, j+1)) && solution[i].get(j) != solution[i].get(j+1)) vcuts++;
                if (i+1 < problem.getMaterialHeight() && solution[i].get(j) != solution[i+1].get(j)) hcuts++;

                // since we're skipping 0s, we need to check above and to the left and count it as a cut (if it's a 0 in that position)
                if (i-1 >= 0 && solution[i].get(j) != solution[i-1].get(j) && solution[i-1].get(j) == 0) hcuts++;
                if ((j-1 > 0) && solution[i].get(j) != solution[i].get(j-1) && solution[i].get(j-1) == 0) vcuts++;
            }
        }
        return hcuts + vcuts;
    }

    private int calculateWaste() {
        int w = 0;

        for (List<Integer> ints : solution) {
            for (Integer anInt : ints) {
                if (anInt == 0) w++;
            }
        }
        return w;
    }

    // Função que adiciona um item.getRepresentation à solução
    private void addToSolution(int lineIndex, int columnIndex, Integer value) {
        while (!solutionTileFilled(lineIndex, columnIndex)) {
            solution[lineIndex].add(0);

            for (int i = 1; i < problem.getMaterialHeight(); i++) {
                if (lineIndex-i >= 0 && !solutionTileFilled(lineIndex-i, columnIndex)) {
                    solution[lineIndex-i].add(0);

                }
                if (lineIndex+i < problem.getMaterialHeight() && !solutionTileFilled(lineIndex+i, columnIndex)) {
                    solution[lineIndex+i].add(0);
                }
            }
        }

        this.solution[lineIndex].set(columnIndex, value);

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
                } else {
                    addToSolution(lineIndex + i, columnIndex + j, 0);
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