package perceptron;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class UserPanel extends JFrame{

    private JButton checkNumber = new JButton();
    private JTextField  resultWin = new JTextField (10);
    private Panel upPanel  = new Panel();
    private Panel leftPanel  = new Panel();
    private Panel drawPanel  = new Panel();
    private Panel rightPanel = new Panel();
    private Panel downPanel   = new Panel();
    private JButton [] pixel = new JButton[35];
    private int [] pixelVal = new int[35];
    private Perceptron[] perceptron;
    private Random rand = new Random();
    private boolean draw = false;

    public UserPanel(Perceptron[] perceptron)
    {
        this.perceptron=perceptron;
        this.setPreferredSize(new Dimension(800,900));

        drawPanel.setLayout(new GridLayout(7, 5));

        for(int i=0; i<pixel.length;i++)
        {
            final JButton button = new JButton();
            button.setBackground(Color.WHITE);

            button.addMouseListener(new MouseListener()
            {
                @Override
                public void mouseClicked(MouseEvent event) {}

                @Override
                public void mouseEntered(MouseEvent event)
                {
                    if(draw)
                    {
                        if(SwingUtilities.isLeftMouseButton(event)) button.setBackground(Color.BLACK);
                        if(SwingUtilities.isRightMouseButton(event)) button.setBackground(Color.WHITE);

                        changValue();
                    }
                }

                @Override
                public void mouseExited(MouseEvent event)
                {
                    if(draw)
                    {
                        if(SwingUtilities.isLeftMouseButton(event)) button.setBackground(Color.BLACK);
                        if(SwingUtilities.isRightMouseButton(event)) button.setBackground(Color.WHITE);

                        changValue();
                    }
                }

                @Override
                public void mousePressed(MouseEvent event)
                {
                    draw = true;

                    if(draw)
                    {
                        if(SwingUtilities.isLeftMouseButton(event)) button.setBackground(Color.BLACK);
                        if(SwingUtilities.isRightMouseButton(event)) button.setBackground(Color.WHITE);

                        changValue();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent event)
                {
                    draw = false;
                }
            });

            pixel[i]=button;
            pixel[i].setPreferredSize(new Dimension(50,50));
            pixel[i].setBackground(Color.WHITE);
            drawPanel.add(pixel[i]);
        }

        checkNumber.setPreferredSize(new Dimension(150,30));
        checkNumber.setText("Rozpoznaj");
        checkNumber.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getNumber();
            }
        });

        resultWin.setPreferredSize(new Dimension(300,30));
        rightPanel.add(resultWin);
        rightPanel.add(checkNumber);
        upPanel.setPreferredSize(new Dimension(800,100));
        leftPanel.setPreferredSize(new Dimension(150,900));
        drawPanel.setPreferredSize(new Dimension(400,650));
        downPanel.setPreferredSize(new Dimension(800,150));
        rightPanel.setPreferredSize(new Dimension(250,900));
        this.setLayout(new BorderLayout());
        this.add(upPanel,BorderLayout.NORTH);
        this.add(drawPanel,BorderLayout.CENTER);
        this.add(leftPanel,BorderLayout.WEST);
        this.add(downPanel,BorderLayout.SOUTH);
        this.add(rightPanel,BorderLayout.EAST);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void changValue()
    {
        for(int i=0 ; i<35 ;i++)
        {
            if( pixel[i].getBackground()==Color.black) pixelVal[i]=1;
            else pixelVal[i]=-1;
        }
    }

    public void getNumber()
    {
        resultWin.setText("  ");

        for(int i=0; i<10; i++)
        {
            int w =  perceptron[i].getResult(pixelVal,i);

            if(w>0)
            {
                String s = resultWin.getText();
                resultWin.setText(s+i+", ");
                //System.out.println("i "+i);
            }
        }
        //System.out.println();
    }


    public void loadExamples() throws FileNotFoundException
    {
        int [] [] exampleS = loadExamplesFromFile("examples.txt");
        int result;
        int r;

        for(int k=0; k < 10; k++)
        {
            for(int j=0; j<10000; j++)
            {
                r = rand.nextInt(100);
                int [] example = new int[35];
                result = exampleS[r][35];

                for(int i=0; i<35; i++)
                {
                    example[i] = exampleS [r][i];
                }

                perceptron[k].learnPerceptron(example,result,k);

            }
        }
    }

    public int[][] loadExamplesFromFile(String name)
    {
        int i=0;

        File file = new File(name);
        try
        {
            Scanner intput = new Scanner(file);

            while(intput.hasNextLine())
            {
                intput.nextLine();
                i++;
            }

            intput.close();
            Scanner intput2 = new Scanner(file);
            int [][] examples = new int[i][36];
            int row = 0;

            while(intput2.hasNextLine())
            {
                String line = intput2.nextLine();
                String[] s = line.split(" ");
                for(int j=0 ; j<36 ; j++)
                {
                    examples[row][j] = Integer.parseInt(s[j]);
                }

                row++;

            }

            intput2.close();

            return examples;

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
