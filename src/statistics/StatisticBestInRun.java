package statistics;

import experiments.Experiment;
import experiments.ExperimentEvent;
import algorithms.AlgorithmEvent;
import algorithms.AlgorithmListener;
import ga.GeneticAlgorithm;
import algorithms.Individual;
import algorithms.Problem;

import java.io.File;

public class StatisticBestInRun<I extends Individual, P extends Problem<I>> implements AlgorithmListener {

    private I bestInExperiment;
    private int run, bestRun;

    public StatisticBestInRun(String experimentHeader) {
        File file = new File("statistic_best_per_experiment_fitness.xls");
        if(!file.exists()){
            utils.FileOperations.appendToTextFile("statistic_best_per_experiment_fitness.xls", experimentHeader + "\t" + "Fitness:"  + "\t" + "Seed:" + "\r\n");
        }
    }

    @Override
    public void iterationEnded(AlgorithmEvent e) {
    }

    @Override
    public void runEnded(AlgorithmEvent e) {
        GeneticAlgorithm<I, P> ga = (GeneticAlgorithm) e.getSource();
        run++;
        if (bestInExperiment == null || ga.getGlobalBest().compareTo(bestInExperiment) > 0) {
            bestInExperiment = (I) ga.getGlobalBest().clone();
            bestRun= run;
        }
    }

    @Override
    public void experimentEnded(ExperimentEvent e) {

        String experimentTextualRepresentation = ((Experiment) e.getSource()).getExperimentTextualRepresentation();
        String experimentHeader = ((Experiment) e.getSource()).getExperimentHeader();
        String experimentConfigurationValues = ((Experiment) e.getSource()).getExperimentValues();

        utils.FileOperations.appendToTextFile("statistic_best_per_experiment_fitness.xls", experimentConfigurationValues + "\t" + bestInExperiment.getFitness() + "\t" + bestRun + "\r\n");
        utils.FileOperations.appendToTextFile("statistic_best_per_experiment.txt", "\r\n\r\n" + experimentTextualRepresentation + "\r\n" + bestInExperiment);
    }
}
