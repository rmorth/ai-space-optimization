package random;

import algorithms.Algorithm;
import algorithms.Individual;
import algorithms.Problem;

import java.util.Random;

public class RandomAlgorithm<I extends Individual, P extends Problem<I>> extends Algorithm<I, P> {
    //TODO this class might require the definition of additional methods and/or attributes

    public RandomAlgorithm(int maxIterations, Random rand) {
        super(maxIterations, rand);
    }

    @Override
    public I run(P problem) {
        //TODO
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
