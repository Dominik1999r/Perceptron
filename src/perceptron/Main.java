package perceptron;

import java.io.FileNotFoundException;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        double constLearn =0.001;
        Random rand = new Random();
        Perceptron [] perceptron = new Perceptron [10];

        for(int i=0 ; i<10 ; i++)
        {
            double[] w = new double[35];
            for(int j=0 ; j<35 ; j++)
            {
                w[j] = 0.1 + (1 - 0.1) *rand.nextDouble()/1000;
            }

            double theta = 0.1 + (1 - 0.1) *rand.nextDouble()/1000;
            perceptron[i] = new Perceptron(w, theta , constLearn);
        }

        UserPanel userPanel = new UserPanel(perceptron);
        userPanel.loadExamples();
    }
}
