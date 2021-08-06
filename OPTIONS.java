package cs3500.marblesolitaire.model.hw02;

/**
 * Here are the three types of OPTIONS that can be encountered on the board. MARBLE - It represents
 * the marble EMPTY - It represents an empty slot on the valid positions of the board. INVALID - It
 * represents an invalid position i.e. not on the board.
 */
enum OPTIONS {
  MARBLE("O"), EMPTY("_"), INVALID(" ");

  private final String value;

  OPTIONS(String value) {
    this.value = value;
  }

  public String toString() {
    return this.value;
  }
}