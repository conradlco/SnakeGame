package org.conradlco.games.snake.ui;

import java.awt.BorderLayout;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.swing.JFrame;

public class SnakeWindow extends JFrame {

  private transient ScheduledExecutorService gameTickExecutor;

  private static final long serialVersionUID = 1L;

  private SnakePanel snakePanel;

  public SnakeWindow() {
    setTitle("Snake Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 700);
    setLocationRelativeTo(null);

    snakePanel = new SnakePanel();
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(snakePanel, BorderLayout.CENTER);
  }

  public void startGame() {
    gameTickExecutor = Executors.newSingleThreadScheduledExecutor();
    gameTickExecutor.scheduleAtFixedRate(
        snakePanel::tick, 0, 120, java.util.concurrent.TimeUnit.MILLISECONDS);
  }

  // Additional methods for game logic and UI can be added here

}
