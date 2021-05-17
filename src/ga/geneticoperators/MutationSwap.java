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

        int rInd2, rInd = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        do {
            rInd2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        }while(rInd == rInd2);

        int aux = ind.getGene(rInd);
        ind.setGene(rInd, ind.getGene(rInd2));
        ind.setGene(rInd2, aux);
    }

    @Override
    public String toString(){
        return "Swap";
    }
}