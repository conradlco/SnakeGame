package org.conradlco.games.snake;

import javax.swing.SwingUtilities;
import org.conradlco.games.snake.ui.SnakeWindow;

public class Application {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(Application::createAndShowGUI);
  }

  private static void createAndShowGUI() {
    SnakeWindow snakeWindow = new SnakeWindow();
    snakeWindow.setVisible(true);
    snakeWindow.startGame();
  }
}
