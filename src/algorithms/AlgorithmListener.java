package algorithms;

import experiments.ExperimentListener;

public interface AlgorithmListener extends ExperimentListener{
   
    void iterationEnded(AlgorithmEvent e);
    
    void runEnded(AlgorithmEvent e);
}
