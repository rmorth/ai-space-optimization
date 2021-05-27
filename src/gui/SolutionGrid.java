package gui;

import ga.GeneticAlgorithm;
import stockingproblem.Item;
import stockingproblem.StockingProblem;
import stockingproblem.StockingProblemIndividual;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class SolutionGrid extends JPanel {
    private static final double BRIGHTNESS_THRESHOLD = 210;
    private static final int SQUARE_WIDTH = 16;
    private static final int SQUARE_HEIGHT = 16;
    private final static Color BACKGROUND_COLOR = new Color(150, 150, 150);
    private static final Color WASTE_COLOR = new Color(100, 100, 100);

    private StockingProblemIndividual individual;
    private StockingProblem problem;
    private List<Integer>[] solution;
    private ArrayList<Color> colors;
    private HashMap<Integer, Color> colorsMappedByItem;

    public SolutionGrid() {
        super();
        this.setVisible(true);
        this.colors = new ArrayList<>();
        this.addColors(); // ~25 colors, big datasets will have repeated colors
    }

    public SolutionGrid(StockingProblemIndividual individual, StockingProblem problem) {
        super();
        this.individual = individual;
        this.solution = individual.getSolution();
        this.problem = problem;
        initialiseColors(problem.getItems());
        this.setVisible(true);
    }

    private void initialiseColors(ArrayList<Item> items) {
        colorsMappedByItem = new HashMap<>();
        int iterator = 0;
        for (Item i: items) {
            colorsMappedByItem.put((int) i.getRepresentation(), colors.get(iterator));
            iterator++;
            if (iterator == colors.size()) iterator = 0;
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(BACKGROUND_COLOR);
        g.setFont(new Font("Roboto", Font.BOLD, 12));

        if (this.problem == null) return;
        if (this.individual == null) return;
        if (this.solution == null) return;
        int x = 0, y = 0;
        for (int i = 0; i < this.solution.length; i++) {
            x=0;
            for (int j = 0; j < this.solution[i].size(); j++) {
                if (this.solution[i].get(j) != 0) {
                    Color c = colorsMappedByItem.get(this.solution[i].get(j));
                    g.setColor(c);
                    g.fillRect(x, y, SQUARE_WIDTH, SQUARE_HEIGHT);
                    g.setColor(getContrastColor(c));
                    char a = (char) (int) this.solution[i].get(j);
                    g.drawString(String.valueOf(a), x+(SQUARE_WIDTH/4), y+(SQUARE_HEIGHT-4));
                } else {
                    g.setColor(WASTE_COLOR);
                    g.fillRect(x, y, SQUARE_WIDTH, SQUARE_HEIGHT);
                }
                x+=SQUARE_WIDTH+1;
            }
            y+=SQUARE_HEIGHT+1;
        }
    }

    public static Color getContrastColor(Color color) {
        double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
        return y >= BRIGHTNESS_THRESHOLD ? Color.black : Color.white;
    }

    public StockingProblemIndividual getIndividual() {
        return individual;
    }

    public void setIndividual(StockingProblemIndividual individual) {
        this.individual = individual;
        this.solution = individual.getSolution();
    }

    public StockingProblem getProblem() {
        return problem;
    }

    public void setProblem(StockingProblem problem) {
        this.problem = problem;
        initialiseColors(problem.getItems());
    }

    private void addColors() {
        colors.add(new Color(255,0,0));
        colors.add(new Color(0,255,0));
        colors.add(new Color(0,0,255));
        colors.add(new Color(255,255,0));
        colors.add(new Color(0,255,255));
        colors.add(new Color(255,0,255));

        colors.add(new Color(255, 200, 200));
        colors.add(new Color(200, 255, 200));
        colors.add(new Color(200, 200, 255));
        colors.add(new Color(255, 255, 200));
        colors.add(new Color(200, 255, 255));
        colors.add(new Color(255, 200, 255));

        colors.add(new Color(125,0,0));
        colors.add(new Color(0, 125,0));
        colors.add(new Color(0, 0, 125));
        colors.add(new Color(125, 125, 0));
        colors.add(new Color(0, 125, 125));
        colors.add(new Color(125, 0, 125));

        colors.add(new Color(255, 150, 75));
        colors.add(new Color(75, 255, 130));
        colors.add(new Color(75, 130, 255));
        colors.add(new Color(255, 200, 100));
        colors.add(new Color(75, 200, 255));
        colors.add(new Color(200, 150, 255));
        colors.add(new Color(100, 175, 100));
    }
}
