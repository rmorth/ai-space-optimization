package ga.geneticoperators;

import ga.GeneticAlgorithm;
import algorithms.IntVectorIndividual;
import algorithms.Problem;

public class MutationInsert<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public MutationInsert(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        int[] cuts = ind.getCuts();

        for(int i = cuts[1]-1; i > cuts[0] ; i--) {
            int aux = ind.getGene(i + 1);
            ind.setGene(i + 1, ind.getGene(i));
            ind.setGene(i, aux);
        }

    }


    @Override
    public String toString() {
        return "Insert";
    }
}