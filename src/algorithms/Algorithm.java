package algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Algorithm <I extends Individual, P extends Problem<I>> {
    protected I globalBest;
    protected boolean stopped;
    protected final int maxIterations;
    protected int t;
    public static Random random;

    public Algorithm(
            int maxIterations,
            Random rand) {
        random = rand;
        this.maxIterations = maxIterations;
    }

    public abstract I run(P problem);

    public I getGlobalBest() {
        return globalBest;
    }

    public void stop() {
        stopped = true;
    }

    //Listeners
    private final transient List<AlgorithmListener> listeners = new ArrayList<>(3);

    public synchronized void removeListener(AlgorithmListener listener) {
        if (listeners != null && listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public synchronized void addListener(AlgorithmListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void fireIterationEnded(AlgorithmEvent e) {
        for (AlgorithmListener listener : listeners) {
            listener.iterationEnded(e);
        }
        if (e.isStopped()) {
            stop();
        }
    }

    public void fireRunEnded(AlgorithmEvent e) {
        for (AlgorithmListener listener : listeners) {
            listener.runEnded(e);
        }
    }

    public int getIteration() {
        return t;
    }
}
