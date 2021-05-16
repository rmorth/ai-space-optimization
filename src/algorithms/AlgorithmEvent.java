package algorithms;

import ga.GeneticAlgorithm;

public class AlgorithmEvent<A extends Algorithm> {

    private A source;
    private boolean stopped;
    
    public AlgorithmEvent(A source) {
        this.source = source;
    }
    
    public boolean isStopped(){
        return stopped;
    }
    
    public void setStopped(boolean stopped){
        this.stopped = stopped;
    }
    
    public A getSource(){
        return source;
    }
}
