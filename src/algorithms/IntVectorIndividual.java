package algorithms;

import ga.GeneticAlgorithm;

import java.util.ArrayList;

public abstract class IntVectorIndividual<P extends Problem, I extends IntVectorIndividual> extends Individual<P, I> {
    //TODO this class might require the definition of additional methods and/or attributes

    protected int[] genome;
    protected int[] rotations;

    public IntVectorIndividual(P problem, int size) {
        super(problem);
        genome = new int[size];
        rotations = new int[size];
      }

    public IntVectorIndividual(IntVectorIndividual<P, I> original) {
        super(original);
        this.genome = new int[original.genome.length];
        System.arraycopy(original.genome, 0, genome, 0, genome.length);
        this.rotations = new int[original.rotations.length];
        System.arraycopy(original.rotations, 0, rotations, 0, rotations.length);
    }

    @Override
    public int getNumGenes() {
        return genome.length;
    }

    public int getIndexof(int value){
        for (int i = 0; i < genome.length; i++) {
            if (genome[i] == value)
                return i;
        }
        return -1;
    }

    // For genetic operators
    public int[] getCuts() {
        int[] pair = new int[2];
        pair[0] = GeneticAlgorithm.random.nextInt(getNumGenes());
        do {
            pair[1] = GeneticAlgorithm.random.nextInt(getNumGenes());
        }while (pair[0]==pair[1]);
        if (pair[0] > pair[1]) {
            int aux = pair[0];
            pair[0] = pair[1];
            pair[1] = aux;
        }
        return pair;
    }

    public ArrayList<Integer> subGenomeList(int cut1, int cut2) {
        ArrayList<Integer> values = new ArrayList<>();
        for (int i = cut1; i < cut2; i++) {
            values.add(genome[i]);
        }
        return values;
    }

    public ArrayList<Integer> subRotationList(int cut1, int cut2) {
        ArrayList<Integer> values = new ArrayList<>();
        for (int i = cut1; i < cut2; i++) {
            values.add(rotations[i]);
        }
        return values;
    }

    public int getGene(int index) {
        return genome[index];
    }

    public void setGene(int index, int newValue) {
        genome[index] = newValue;
    }

    public int getRotation(int index) {
        return rotations[index];
    }

    public void setRotation(int index, int newValue) {
        rotations[index] = newValue;
    }

    @Override
    public void swapGenes(IntVectorIndividual other, int index) {
        int aux = genome[index];
        genome[index] = other.genome[index];
        other.genome[index] = aux;
    }

    public int[] getGenome() {
        return genome;
    }

    public int[] getRotations() {
        return rotations;
    }
}
