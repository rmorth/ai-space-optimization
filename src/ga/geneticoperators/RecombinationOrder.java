package ga.geneticoperators;

import algorithms.IntVectorIndividual;
import algorithms.Problem;
import ga.GeneticAlgorithm;

import java.util.ArrayList;
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

        int lim2, lim1 = GeneticAlgorithm.random.nextInt(numGenes);
        do {
            lim2 = GeneticAlgorithm.random.nextInt(numGenes);
        }while(lim1 == lim2);

        if (lim1 > lim2) {
            int aux = lim1;
            lim1 = lim2;
            lim2 = aux;
        }

        child1.addAll(ind1.subList(lim1, lim2 + 1));
        child2.addAll(ind2.subList(lim1, lim2 + 1));

        int currentItemIndex = 0;
        int currentItemInInd1 = 0;
        int currentItemInInd2 = 0;

        for (int i = 0; i < numGenes; i++) {
            currentItemIndex = (lim2 + i) % numGenes;

            currentItemInInd1 = ind1.getGene(currentItemIndex);
            currentItemInInd2 = ind2.getGene(currentItemIndex);

            if (!child1.contains(currentItemInInd2)) {
                child1.add(currentItemInInd2);
            }

            if (!child2.contains(currentItemInInd1)) {
                child2.add(currentItemInInd1);
            }
        }

        Collections.rotate(child1, lim1);
        Collections.rotate(child2, lim1);
        replace(ind1, child1, numGenes);
        replace(ind2, child2, numGenes);
    }

    private void replace(I parent, ArrayList<Integer> child, int size) {
        for (int i = 0; i < size; i++) {
            parent.setGene(i, (Integer) child.get(i));
        }
    }

    @Override
    public String toString() {
        return "Order";
    }
}