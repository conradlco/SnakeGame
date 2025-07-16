package org.conradlco.games.snake.ui;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.conradlco.games.snake.model.Direction;
import org.conradlco.games.snake.model.Fruit;
import org.conradlco.games.snake.model.FruitType;

public class SnakePanel extends JPanel implements KeyListener {

  private static final int BORDER_THICKNESS = 20;
  private static final int BOARD_WIDTH = 40;
  private static final int BOARD_HEIGHT = 40;
  private static final int CELL_SIZE = 15;
  private static final int TICKS_PER_FRUIT = 15;

  // Initial length of the snake
  private Direction currentDirection = Direction.RIGHT;
  private Point snakeHeadPosition = new Point(10, 10); // Example starting position
  private LinkedList<Point> snakeBody = new LinkedList<>(); // To store the snake's body segments
  private boolean gameOver = false;
  private int fruitTickCounter = 0; // Counter for fruit spawning
  private final List<Fruit> fruits = new ArrayList<>(); // List to hold fruits
  private int score = 0;

  public SnakePanel() {
    addKeyListener(this);
    this.setFocusable(true);
  }

  public synchronized void tick() {
    if (gameOver) {
      return; // Do not update if the game is over
    }

    // Check if the snake head collides with its body
    for (Point bodySegment : snakeBody) {
      if (snakeHeadPosition.equals(bodySegment)) {
        // Handle collision with itself (e.g., reset game or end game)
        System.out.println("Game Over: Snake collided with itself!");
        JOptionPane.showMessageDialog(
            this, "Game Over: Snake collided with itself!", "Game Over", JOptionPane.ERROR_MESSAGE);
        gameOver = true;
        return;
      }
    }

    // Add the current head position to the body
    snakeBody.addFirst(new Point(snakeHeadPosition));

    // If the snake has grown, remove the last segment
    if (snakeBody.size() > 5) { // Example condition to grow the snake
      snakeBody.removeLast();
    }

    fruitTickCounter++;
    if (fruitTickCounter >= TICKS_PER_FRUIT) {
      // Spawn a new random fruit from FruitType
      int fruitX = (int) (Math.random() * BOARD_WIDTH);
      int fruitY = (int) (Math.random() * BOARD_HEIGHT);
      Fruit newFruit = new Fruit(FruitType.randomFruitType(), new Point(fruitX, fruitY));
      fruits.add(newFruit);

      fruitTickCounter = 0; // Reset the counter after spawning fruit
    }

    // Move snake head
    snakeHeadPosition.x += currentDirection.getDx();
    snakeHeadPosition.y += currentDirection.getDy();

    // If snake head collides with a fruit, remove the fruit and grow the snake
    for (int i = 0; i < fruits.size(); i++) {
      Fruit fruit = fruits.get(i);
      if (snakeHeadPosition.equals(fruit.location())) {
        // Remove the fruit from the list
        fruits.remove(i);
        score += fruit.fruitType().score(); // Update score based on fruit type
        // Grow the snake by not removing the last segment

        snakeBody.addLast(new Point(snakeBody.getLast())); // Add a new segment at the end
        break; // Exit loop after eating one fruit
      }
    }

    if (snakeHeadPosition.x < 0
        || snakeHeadPosition.x >= BOARD_WIDTH
        || snakeHeadPosition.y < 0
        || snakeHeadPosition.y >= BOARD_HEIGHT) {
      // Handle collision with walls (e.g., reset game or end game)
      System.out.println("Game Over: Snake hit the wall!");
      JOptionPane.showMessageDialog(
          this, "Game Over: Snake hit the wall!", "Game Over", JOptionPane.ERROR_MESSAGE);
      gameOver = true;
    } else {
      repaint();
    }
  }

  @Override
  public void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g);

    g.drawRect(
        BORDER_THICKNESS, BORDER_THICKNESS, BOARD_WIDTH * CELL_SIZE, BOARD_HEIGHT * CELL_SIZE);

    // Draw the snake head
    g.fillOval(
        snakeHeadPosition.x * CELL_SIZE + BORDER_THICKNESS,
        snakeHeadPosition.y * CELL_SIZE + BORDER_THICKNESS,
        CELL_SIZE,
        CELL_SIZE);
    // Draw the snake body
    g.setColor(java.awt.Color.GREEN); // Set color for snake body
    for (Point bodySegment : snakeBody) {
      g.fillRect(
          bodySegment.x * CELL_SIZE + BORDER_THICKNESS,
          bodySegment.y * CELL_SIZE + BORDER_THICKNESS,
          CELL_SIZE,
          CELL_SIZE);
    }

    // Draw the fruits on the board
    for (Fruit fruit : fruits) {
      g.setColor(fruit.fruitType().getColor()); // Set color for fruits
      g.fillOval(
          fruit.location().x * CELL_SIZE + BORDER_THICKNESS,
          fruit.location().y * CELL_SIZE + BORDER_THICKNESS,
          CELL_SIZE,
          CELL_SIZE);
    }

    // Draw the score
    g.setColor(java.awt.Color.BLACK);
    g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
    g.drawString("Score: " + score, BOARD_WIDTH * CELL_SIZE + 50, 40);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // Not used
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_UP) {
      currentDirection = Direction.UP;
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      currentDirection = Direction.DOWN;
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      currentDirection = Direction.LEFT;
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      currentDirection = Direction.RIGHT;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {}
}
