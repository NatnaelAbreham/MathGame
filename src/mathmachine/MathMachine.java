
package mathmachine;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
 
public class MathMachine extends JFrame {

    JButton[][] button = new JButton[9][9];
    JPanel p1, p2, p3, p4;
    JLabel numlabel, timelabel;
    int rand = 0;
    int[] row = new int[40];
    int[] colomn = new int[40];
    int sum = 0, counter = 0, score = 0, innerscore = 0;

    JProgressBar progressbar, pbar;

    // setting initially 0 and final 60 for progressbar
    int max = 60;
    int min = 0;
    int second = 60;

    Timer timer;

    MathMachine() {

        JOptionPane.showMessageDialog(null, "Select Board numbers such that thier sum must be\nthe "
                + "number of the upper box\n\nIf you can get it by clicking on 2 numbers give you less point.\n"
                + "if you increase number of click, your score also will inclease\n"
                + "\nIf you click sequentially your score increase 3 times more than the above\n\n"
                + "You have 60 seconds to solve the Greater number of sum, but be Careful because if the sum of numbers is incorrect \nyour Seconds will decrease by 10"
                + "\n\nGOOD LUCK!!!", "How to Play the Game\n\n", JOptionPane.DEFAULT_OPTION);
        rand = randomNumberQ();

        p2 = new JPanel();
        p2.setBorder(new TitledBorder("Number"));

        numlabel = new JLabel();
        numlabel.setText("" + rand);
        numlabel.setFont(new Font("Serif", 3, 44));

        p2.add(numlabel);

        p1 = new JPanel(new GridLayout(9, 9));
        p1.setBorder(new LineBorder(Color.GRAY, 2));
        p1.setBackground(Color.WHITE);

        p3 = new JPanel(new BorderLayout());
        p3.setBackground(Color.BLUE);

        pbar = new JProgressBar();
        pbar.setMinimum(min);
        pbar.setMaximum(max);
        pbar.setForeground(Color.GREEN);
        pbar.setBackground(Color.WHITE);

        timelabel = new DisplayTime();
        timelabel.setFont(new Font("serif", 3, 24));
        timelabel.setForeground(Color.WHITE);

        p3.add(timelabel, BorderLayout.WEST);
        p3.add(pbar, BorderLayout.CENTER);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                button[i][j] = new JButton("" + randomNumber());
                button[i][j].setBackground(Color.BLACK);
                button[i][j].setForeground(Color.WHITE);
                p1.add(button[i][j]);

            }
        }

        p4 = new JPanel(new BorderLayout());
        p4.add(p3, BorderLayout.NORTH);
        p4.add(p2, BorderLayout.CENTER);

        add(p4, BorderLayout.NORTH);
        add(p1, BorderLayout.CENTER);

        ButtonListener listener = new ButtonListener();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                button[i][j].addActionListener(listener);

            }
        }
    }

    class DisplayTime extends JLabel {

        DisplayTime() {
            timer = new Timer(1000, new TimerListener());
            second = 60;
            timer.start();

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            FontMetrics fontmetrics = g.getFontMetrics();

            int stringwidth = fontmetrics.stringWidth(String.valueOf(second));
            int stringascent = fontmetrics.getAscent();

            int xcoordinate = getWidth() / 2 - stringwidth / 2;
            int ycoordinate = getHeight() / 2 + stringascent / 2;

            g.drawString(String.valueOf(second), xcoordinate, ycoordinate);

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(40, 40);
        }

        class TimerListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                second--;

                if (second >= 0) {
                    pbar.setValue(second);
                    repaint();
                } else {
                     
                    timer.stop();
                    int prescore = getHighScore(score);
                    if (prescore == score) {
                        JOptionPane.showMessageDialog(null, "Game Over!\nHigh score " + prescore + "\ncurrent score " + score + " points\nnew high score record !!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Game Over!\nHigh score " + prescore + "\ncurrent score " + score + " points");
                    }
                    int yes = JOptionPane.showConfirmDialog(null, "Do you went to Continue");

                    switch (yes) {
                        case JOptionPane.YES_OPTION:
                            resetGame();
                            break;
                        case JOptionPane.NO_OPTION:
                            timer.stop();
                            System.exit(0);
                            break;
                        case JOptionPane.CANCEL_OPTION:
                            timer.stop();
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    //generate random numbers for the button
    public static int randomNumber() {
        int number = (int) (Math.random() * 10);
        while (number == 0) {
            number = (int) (Math.random() * 10);
        }

        return number;
    }

    //generate random number for question
    public static int randomNumberQ() {
        int number = (int) (Math.random() * 50);
        while (number < 17) {
            number = (int) (Math.random() * 50);
        }

        return number;
    }

    //reset the game for playing again
    public void resetGame() {
        score = 0;
        second = 61;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                button[i][j].setText("" + randomNumber());
                button[i][j].setBackground(Color.BLACK);
                button[i][j].setVisible(true);
            }
        }
        sum = 0;
        rand = randomNumberQ();
        numlabel.setText(rand + "");
        timer.start();
    }

    public static void main(String[] args) {
        JFrame frame = new MathMachine();

        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static int getHighScore(int currentscore) {
        int prescore = 0;
        try {
            File file = new File("score.txt");

            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                prescore = scanner.nextInt();
                scanner.close();
                if (prescore < currentscore) {
                    PrintWriter writer = new PrintWriter(file);
                    writer.print(currentscore);
                    writer.close();
                    prescore = currentscore;
                }

            } else {
                PrintWriter writer = new PrintWriter(file);
                writer.print(currentscore);
                writer.close();
                prescore = currentscore;
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return prescore;
    }

    public class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {

                    if (e.getSource() == button[i][j]) {
                        //if the button previously clicked. It out from the loop without computation
                        for (int y = 0; y < counter; y++) {
                            if (button[row[y]][colomn[y]] == button[i][j]) {
                                return;
                            }
                        }
                        button[i][j].setBackground(Color.MAGENTA);
                        row[counter] = i;
                        colomn[counter] = j;
                        //check for if the sum of the numbers greater or equal to the number given for calculation
                        sum += Integer.parseInt(button[i][j].getText());
                        counter++;

                        if (sum == rand) {
                            boolean tv = false;//check if the player click sequentially
                            for (int y = 0; y < counter - 1; y++) {
                                if (row[y] == row[y + 1]) {
                                    tv = true;
                                } else {
                                    tv = false;
                                    break;
                                }
                            }
                            if (!tv) {
                                for (int y = 0; y < counter - 1; y++) {
                                    if (colomn[y] == colomn[y + 1]) {
                                        tv = true;
                                    } else {
                                        tv = false;
                                        break;
                                    }
                                }
                            }
                            //clearing the  clicked button after sum equal to geven number
                            for (int y = 0; y < counter; y++) {
                                button[row[y]][colomn[y]].setVisible(false);
                            }

                            if (tv) {
                                for (int y = 0; y < counter; y++) {
                                    innerscore += 5;
                                }
                            } else {
                                for (int y = 0; y < counter; y++) {
                                    innerscore += 2;
                                }
                            }
                            score += innerscore;
                            innerscore = 0;
                            counter = 0;
                            sum = 0;
                            rand = randomNumberQ();//generating another random number 
                            numlabel.setText(rand + "");

                        } else if (sum > rand) {
                            for (int y = 0; y < counter; y++) {
                                button[row[y]][colomn[y]].setBackground(Color.BLACK);//if the sum not equal with a geven number clear a selected number back to black
                            }
                            second = second - 10 > 0 ? second -= 10 : second;//decreasing the second by 10;
                            counter = 0;
                            sum = 0;
                            innerscore = 0;
                            rand = randomNumberQ();
                            numlabel.setText(rand + "");
                        }
                    }

                }
            }
        }
    }

}
