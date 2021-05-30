package ga.geneticoperators;

import ga.GeneticAlgorithm;
import algorithms.IntVectorIndividual;
import algorithms.Problem;

import java.util.Arrays;

public class MutationScramble<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public MutationScramble(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        int[] cuts = ind.getCuts();
        shuffle(ind, cuts[0], cuts[1]+1);
    }

    private void shuffle(I ind, int min, int max) {
        for (int i = min; i < max; i++) {
            int randomIndex = GeneticAlgorithm.random.nextInt(max - min) + min;
            if (i == randomIndex) continue;

            // TODO: fix this repetition, it's everywhere
            int aux = ind.getGene(i);
            ind.setGene(i, ind.getGene(randomIndex));
            ind.setGene(randomIndex, aux);

            aux = ind.getRotation(i);
            ind.setRotation(i, ind.getRotation(randomIndex));
            ind.setRotation(randomIndex, aux);
        }
    }



    @Override
    public String toString() {
        return "Scramble";
    }
}