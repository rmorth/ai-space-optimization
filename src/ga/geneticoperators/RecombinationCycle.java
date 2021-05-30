package ga.geneticoperators;

import algorithms.IntVectorIndividual;
import algorithms.Problem;
import ga.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class RecombinationCycle<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {

    public RecombinationCycle(double probability) {
        super(probability);
    }

    @Override
    public void recombine(I ind1, I ind2) {
        // Code is messy and needs refactoring, which would lead to slightly better performance
        // https://github.com/jfinkels/jmona/blob/master/jmona-examples/src/main/java/jmona/example/tsp/crossover/CycleCrossoverFunction.java
        final int size = ind1.getNumGenes();

        final ArrayList<Integer> cycleIndices = new ArrayList<>();
        final int[] child1 = new int[size];
        final int[] child2 = new int[size];
        final int[] child1Rotations = new int[size];
        final int[] child2Rotations = new int[size];

        int indexParent1 = GeneticAlgorithm.random.nextInt(size-1); // guarantee change

        cycleIndices.add(indexParent1); // beginning of the cycle
        int itemParent2 = ind2.getGene(indexParent1);
        indexParent1 = ind1.getIndexof(itemParent2);


        // Until cycle is complete
        while (indexParent1 != cycleIndices.get(0)) {
            cycleIndices.add(indexParent1);
            itemParent2 = ind2.getGene(indexParent1);
            indexParent1 = ind1.getIndexof(itemParent2);
        }

        for (int i = 0; i < size; i++) {
            if (cycleIndices.contains(i)) {
                child1[i] = ind2.getGene(i);
                child2[i] = ind1.getGene(i);

                child1Rotations[i] = ind2.getRotation(i);
                child2Rotations[i] = ind1.getRotation(i);
            } else {
                child1[i] = ind1.getGene(i);
                child2[i] = ind2.getGene(i);

                child1Rotations[i] = ind1.getRotation(i);
                child2Rotations[i] = ind2.getRotation(i);
            }
        }

        replace(ind1, child1, child1Rotations, size);
        replace(ind2, child2, child2Rotations, size);
    }

    private void replace(I parent, int[] child, int[] childRotations, int size) {
        for (int i = 0; i < size; i++) {
            parent.setGene(i, child[i]);;
            parent.setRotation(i, childRotations[i]);
        }
    }

    @Override
    public String toString(){
        return "Cycle";
    }    
}