package algorithms;

public abstract class Individual<P extends Problem, I extends Individual> implements Comparable<I>{

    protected double fitness;
    protected P problem;

    public Individual(P problem) {
        this.problem = problem;
    }

    public Individual(Individual<P, I> original) {
        this.problem = original.problem;
        this.fitness = original.fitness;
    }

    public abstract double computeFitness();

    public abstract int getNumGenes();

    public abstract void swapGenes(I other, int index);

    public double getFitness() {
        return fitness;
    }

    @Override
    public abstract I clone();
}
