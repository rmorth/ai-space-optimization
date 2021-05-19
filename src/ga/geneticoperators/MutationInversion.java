package ga.geneticoperators;

import algorithms.IntVectorIndividual;
import algorithms.Problem;
import ga.GeneticAlgorithm;

public class MutationInversion<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public MutationInversion(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        // Inversion Mutation: (according to slides)
        // Pick two alleles at random and then invert the sub-string between them
        int[] cuts = ind.getCuts();

        int j = cuts[1];
        int last = cuts[0];

        for (int i = cuts[0]; i <= cuts[1]; i++) {
            if (last == j) break;
            int aux = ind.getGene(i);
            ind.setGene(i, ind.getGene(j));
            ind.setGene(j, aux);
            j--;
            last = i;
        }
    }

    @Override
    public String toString(){
        return "Inversion";
    }
}