import java.awt.Color;

import javax.swing.*; //javax.swing is a package in the Java programming language that provides 
                      //a set of 'lightweight' (all-Java) GUI (Graphical User Interface) components. 
                      //Swing is part of the Java Foundation Classes (JFC), which is a group of features 
                      //for building graphical user interfaces and adding rich graphics functionality and 
                      //interactivity to Java applications.

public class Snake {
    public static void main(String[] args) throws Exception {
        int boardWidth = 600;
        int boardHeight = 600;

        JFrame frame = new JFrame("Snake Game"); // JFrame is a class in the javax.swing package that represents a top-level
                                     // window with a title and a border

        frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game game = new Game(boardWidth, boardHeight);
        frame.add(game);
        frame.pack();
        game.requestFocus();
        
    }
}