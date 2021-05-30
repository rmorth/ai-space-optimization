package ga.geneticoperators;

import algorithms.IntVectorIndividual;
import algorithms.Problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RecombinationOrder<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {

    public RecombinationOrder(double probability) {
        super(probability);
    }

    @Override
    public void recombine(I ind1, I ind2) {
        // Order Crossover: (according to slides)
        // https://stackoverflow.com/questions/11782881/how-to-implement-ordered-crossover
        final int numGenes = ind1.getNumGenes();
        final ArrayList<Integer> child1 = new ArrayList<>(numGenes);
        final ArrayList<Integer> child2 = new ArrayList<>(numGenes);

        int[] cuts = ind1.getCuts();

        child1.addAll(ind1.subGenomeList(cuts[0], cuts[1] + 1));
        child2.addAll(ind2.subGenomeList(cuts[0], cuts[1] + 1));

        int currentItemIndex = 0;
        int currentItemInInd1 = 0;
        int currentItemInInd2 = 0;

        for (int i = 0; i < numGenes; i++) {
            currentItemIndex = (cuts[1] + i) % numGenes;

            currentItemInInd1 = ind1.getGene(currentItemIndex);
            currentItemInInd2 = ind2.getGene(currentItemIndex);

            if (!child1.contains(currentItemInInd2)) {
                child1.add(currentItemInInd2);
            }

            if (!child2.contains(currentItemInInd1)) {
                child2.add(currentItemInInd1);
            }
        }


        Collections.rotate(child1, cuts[0]);
        Collections.rotate(child2, cuts[0]);
        replaceGenome(ind1, child1, numGenes);
        replaceGenome(ind2, child2, numGenes);
    }

    private void replaceGenome(I parent, ArrayList<Integer> child, int size) {
        for (int i = 0; i < size; i++) {
            parent.setGene(i, (Integer) child.get(i));
        }
    }

    @Override
    public String toString() {
        return "Order";
    }
}