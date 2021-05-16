package statistics;

import experiments.Experiment;
import experiments.ExperimentEvent;
import algorithms.AlgorithmEvent;
import algorithms.AlgorithmListener;
import ga.GeneticAlgorithm;
import algorithms.Individual;
import algorithms.Problem;
import utils.Maths;

import java.io.File;

public class StatisticBestAverage<E extends Individual, P extends Problem<E>> implements AlgorithmListener {

    private final double[] values;
    private int run;

    public StatisticBestAverage(int numRuns, String experimentHeader) {
        values = new double[numRuns];
        File file = new File("statistic_average_fitness.xls");
        if(!file.exists()){
            utils.FileOperations.appendToTextFile("statistic_average_fitness.xls", experimentHeader + "\t" + "Average:" + "\t" + "StdDev:" + "\r\n");
        }
    }

    @Override
    public void iterationEnded(AlgorithmEvent e) {
    }

    @Override
    public void runEnded(AlgorithmEvent e) {
        GeneticAlgorithm<E, P> ga = (GeneticAlgorithm) e.getSource();
        values[run++] = ga.getGlobalBest().getFitness();
    }

    @Override
    public void experimentEnded(ExperimentEvent e) {

        double average = Maths.average(values);
        double sd = Maths.standardDeviation(values, average);

        String experimentConfigurationValues = ((Experiment) e.getSource()).getExperimentValues();

        utils.FileOperations.appendToTextFile("statistic_average_fitness.xls", experimentConfigurationValues + "\t" + average + "\t" + sd + "\r\n");
    }
}
