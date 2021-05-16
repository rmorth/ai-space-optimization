package ga;


import algorithms.*;
import ga.geneticoperators.Mutation;
import ga.geneticoperators.Recombination;
import ga.selectionmethods.SelectionMethod;

import java.util.Random;

public class GeneticAlgorithm<I extends Individual, P extends Problem<I>> extends Algorithm<I, P> {

    private final int populationSize;
    private Population<I, P> population;
    private final SelectionMethod<I, P> selection;
    private final Recombination<I, P> recombination;
    private final Mutation<I, P> mutation;

    public GeneticAlgorithm(
            int populationSize,
            int maxGenerations,
            SelectionMethod<I, P> selection,
            Recombination<I, P> recombination,
            Mutation<I, P> mutation,
            Random rand) {
        super(maxGenerations, rand);

        this.populationSize = populationSize;
        this.selection = selection;
        this.recombination = recombination;
        this.mutation = mutation;
    }

    public I run(P problem) {
        t = 0;
        population = new Population<>(populationSize, problem);
        globalBest = population.evaluate();
        fireIterationEnded(new AlgorithmEvent(this));

        while (t < maxIterations && !stopped) {
            Population<I, P> populationAux = selection.run(population);
            recombination.run(populationAux);
            mutation.run(populationAux);
            population = populationAux;
            I bestInGen = population.evaluate();
            computeBestInRun(bestInGen);
            t++;
            fireIterationEnded(new AlgorithmEvent(this));
        }
        fireRunEnded(new AlgorithmEvent(this));
        return globalBest;
    }

    private void computeBestInRun(I bestInGen) {
        if (bestInGen.compareTo(globalBest) > 0) {
            globalBest = (I) bestInGen.clone();
        }
    }

    public double getAverageFitness() {
        return population.getAverageFitness();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Population size:" + populationSize + "\n");
        sb.append("Max generations:" + maxIterations + "\n");
        sb.append("Selection:" + selection + "\n");
        sb.append("Recombination:" + recombination + "\n");
        sb.append("Mutation:" + mutation + "\n");
        return sb.toString();
    }

}
