package random;

import algorithms.Algorithm;
import algorithms.AlgorithmEvent;
import algorithms.Individual;
import algorithms.Problem;

import java.util.Random;

public class RandomAlgorithm<I extends Individual, P extends Problem<I>> extends Algorithm<I, P> {
    //TODO this class might require the definition of additional methods and/or attributes

    public RandomAlgorithm(int maxIterations, Random rand) {
        super(maxIterations, rand);
    }

    @Override
    public I run(P problem) { // See GeneticAlgorithm and adapt
        this.t = 0;
        I individual = problem.getNewIndividual();
        individual.computeFitness();
        this.globalBest = individual;
        fireIterationEnded(new AlgorithmEvent(this));

        while (t < maxIterations && !stopped) {
            individual = problem.getNewIndividual();
            individual.computeFitness();
            if (individual.compareTo(this.globalBest) > 0) {
                this.globalBest = (I) individual.clone();
            }
            this.t++;
            fireIterationEnded(new AlgorithmEvent(this));
        }

        fireRunEnded(new AlgorithmEvent(this));
        return this.globalBest;
    }
}
