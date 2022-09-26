package perceptron;

public class Perceptron {

    public double [] weigths ;
    public double theta;
    public double error;
    public double constLearning;
    public int lifeTime;
    public int max;
    public double[]  pocket = new double[35];

    public Perceptron(double[] weigths, double theta, double constLearning)
    {
        this.weigths = weigths;
        this.theta = theta;
        this.constLearning=constLearning;
        pocket = weigths;
        lifeTime = 0;
        max = 0;
    }

    public int getResult(int [] v, int j)
    {
        int result = 0;
        double tmp=0.0;

        for(int i = 0; i <35; i++)
        {
            tmp+=v[i]*pocket[i];
        }

        tmp -= theta;

        //System.out.println("Wynik "+ j + ": " +tmp);

        if (tmp>0) result = 1;
        else result =-1;

        return result;
    }

    public void learnPerceptron(int []example , int result, int j)
    {
        int o=0;
        double tmp=0;

        if(j == result) result = 1;
        else result = -1;

        for (int i=0; i<35; i++)
        {
            tmp = tmp + (example[i]*weigths[i]);
        }

        tmp -= theta;

        if(tmp > 0) o=1;
        else o=-1;

        error = result - o;

        if (error !=0)
        {
            lifeTime = 0;

            for(int i = 0 ; i< 35 ; i++)
            {
                double w =  weigths[i] + constLearning * error * example[i];
                weigths[i]= w;
            }

            theta = theta - (constLearning * error);
        } else {

            lifeTime++;

            if (lifeTime > max)
            {
                for (int i=0 ; i<35 ; i++)
                {
                    pocket[i] = weigths[i];
                    max = lifeTime;
                }
            }
        }
    }

}
