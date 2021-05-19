package ga.geneticoperators;

import algorithms.IntVectorIndividual;
import algorithms.Problem;
import ga.GeneticAlgorithm;

public class MutationSwap<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public MutationSwap(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        // Swap Mutation: (according to powerpoint)
        // Pick 2 random alleles, swap their positions
        int[] cuts = ind.getCuts(); // cut1 will always be < cut2, shouldn't be a problem

        int aux = ind.getGene(cuts[0]);
        ind.setGene(cuts[0], ind.getGene(cuts[1]));
        ind.setGene(cuts[1], aux);
    }

    @Override
    public String toString(){
        return "Swap";
    }
}