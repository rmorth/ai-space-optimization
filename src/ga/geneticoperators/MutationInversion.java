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

        int rInd2, rInd = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        do {
            rInd2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        }while(rInd == rInd2);

        if (rInd > rInd2) {
            int aux = rInd;
            rInd = rInd2;
            rInd2 = aux;
        }

        int j = rInd2;
        int last = rInd;

        for (int i = rInd; i <= rInd2; i++) {
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