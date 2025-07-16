package org.conradlco.games.snake.model;

public enum FruitType {
  APPLE,
  ORANGE,
  BANANA,
  GRAPE,
  STRAWBERRY;

  public java.awt.Color getColor() {
    return switch (this) {
      case APPLE -> java.awt.Color.RED;
      case ORANGE -> java.awt.Color.ORANGE;
      case BANANA -> java.awt.Color.YELLOW.darker();
      case GRAPE -> new java.awt.Color(128, 0, 128); // Purple
      case STRAWBERRY -> new java.awt.Color(220, 20, 60); // Crimson
    };
  }

  public int score() {
    return switch (this) {
      case APPLE -> 10;
      case ORANGE -> 20;
      case BANANA -> 30;
      case GRAPE -> 40;
      case STRAWBERRY -> 50;
    };
  }

  public static FruitType randomFruitType() {
    FruitType[] values = values();
    int randomIndex = (int) (Math.random() * values.length);
    return values[randomIndex];
  }
}
