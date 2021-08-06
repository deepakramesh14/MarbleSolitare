package cs3500.marblesolitaire.model.hw02;

import java.util.StringJoiner;

/**
 * This is the Class that implements that the Model. It implements the MarbleSolitaireModel
 * interface.
 */

public class MarbleSolitaireModelImpl implements MarbleSolitaireModel {

  private final int arm;
  private final int lengthOfBoard;
  private final OPTIONS[][] board;
  private final int sRow;
  private final int sCol;
  // I'm calculating the score on the fly and hence cannot make it final
  private int score;

  /**
   * In this constructor we have the user has the liberty to set all the parameters of the board
   * within the game rules.
   *
   * @param arm  the arm length of the board .
   * @param sRow the row number where the slot is empty is empty.
   * @param sCol the column number where the marble is empty.
   */
  public MarbleSolitaireModelImpl(int arm, int sRow, int sCol) {

    // If the arm size is negative ,less than 3 and even it should throw an exception.
    if (arm < 3 || arm % 2 == 0) {
      throw new IllegalArgumentException("Arm size invalid");
    } else {
      // Initializing the arm.
      this.arm = arm;
    }


    // Initializing the length of the board.
    this.lengthOfBoard = (this.arm * 3) - 2;

    // Initializing the board.
    this.board = new OPTIONS[lengthOfBoard][lengthOfBoard];

    // Initializing the score
    this.score = 0;


    this.sRow = sRow;
    this.sCol = sCol;

    // Initializing the board and the score.
    construct();


  }

  /**
   * The constructor that takes in the empty slot in a 3 size board.
   *
   * @param sRow The Row number of the empty spot.
   * @param sCol The Column number of the empty spot.
   */


  public MarbleSolitaireModelImpl(int sRow, int sCol) {
    this(3, sRow, sCol);
  }

  /**
   * Initializes the board with the given arm size with the empty slot being in the middle.
   *
   * @param arm the arm length of the board .
   */


  public MarbleSolitaireModelImpl(int arm) {
    this(arm, (((arm * 3) - 2) - 1) / 2, (((arm * 3) - 2) - 1) / 2);
  }

  /**
   * A constructor that initializes the board with size and the empty slot in the middle.
   */


  public MarbleSolitaireModelImpl() {
    this(3, 3, 3);
  }

  /**
   * Move a single marble from a given position to another given position. A move is valid only if
   * the from and to positions are valid. Specific implementations may place additional constraints
   * on the validity of a move.
   *
   * @param fromRow the row number of the position to be moved from (starts at 0)
   * @param fromCol the column number of the position to be moved from (starts at 0)
   * @param toRow   the row number of the position to be moved to (starts at 0)
   * @param toCol   the column number of the position to be moved to (starts at 0)
   * @throws IllegalArgumentException if the move is not possible
   */
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {

    if (!(isValidMove(fromRow, fromCol, toRow, toCol))) {
      throw new IllegalArgumentException("This move is not possible");
    }

    board[fromRow][fromCol] = OPTIONS.EMPTY;
    board[toRow][toCol] = OPTIONS.MARBLE;

    int middleRow = (fromRow + toRow) / 2;
    int middleCol = (fromCol + toCol) / 2;

    board[middleRow][middleCol] = OPTIONS.EMPTY;
    score--;


  }

  /**
   * Determine and return if the game is over or not. A game is over if no more moves can be made.
   *
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {

    for (int row = 0; row < lengthOfBoard; row++) {
      for (int col = 0; col < lengthOfBoard; col++) {
        if (isValidMove(row, col, row, col - 2) || isValidMove(row, col, row, col + 2)
                || isValidMove(row, col, row + 2, col) || isValidMove(row, col,
                row - 2, col)) {
          return false;
        }

      }
    }
    return true;
  }

  /**
   * Return a string that represents the current state of the board. The string should have one line
   * per row of the game board. Each slot on the game board is a single character (O, X or space for
   * a marble, empty and invalid position respectively). Slots in a row should be separated by a
   * space. Each row has no space before the first slot and after the last slot.
   *
   * @return the game state as a string
   */
  @Override
  public String getGameState() {


    StringJoiner colOutput = new StringJoiner("\n");


    for (int row = 0; row < lengthOfBoard; row++) {
      StringJoiner rowOutput = new StringJoiner(" ");
      for (int col = 0; col < lengthOfBoard; col++) {
        rowOutput.add(board[row][col].toString());
      }
      colOutput.add(rowOutput.toString().replaceAll("\\s+$", ""));
    }


    return colOutput.toString();

  }

  /**
   * Return the number of marbles currently on the board.
   *
   * @return the number of marbles currently on the board
   */
  @Override
  public int getScore() {
    return score;
  }

  /**
   * Validates whether the move is valid or not. These are the conditions that are need to make the
   * move:
   * <ol>
   * <li>Both the to & from  cells should be within the board </li>
   * <li> The from cell should have a marble and the to cell should be empty</li>
   * <li> The two cell should be two cells away in either either of four directions
   * but not diagonal</li>
   * <li>There should be a marble in the cell between to and from cell. </li>
   * </ol>
   *
   * @return whether its a valid move or not
   * @throws IllegalArgumentException if the above in the paragraph conditions are not met.
   */
  private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {

    // Checking is it within in the board.
    if (!(isWithinBoard(fromRow, fromCol) && isWithinBoard(toRow, toCol))) {
      return false;
    }

    // Checking whether for the second condition.
    if (!(board[fromRow][fromCol] == OPTIONS.MARBLE && board[toRow][toCol] == OPTIONS.EMPTY)) {
      return false;
    }

    boolean leftSide = (toRow == fromRow) && (toCol == fromCol - 2);
    boolean rightSide = (toRow == fromRow) && (toCol == fromCol + 2);
    boolean upSide = (toRow == fromRow - 2) && (toCol == fromCol);
    boolean downSide = (toRow == fromRow + 2) && (toCol == fromCol);


    if (!(leftSide || rightSide || upSide || downSide)) {
      return false;
    }


    int middleRow = (fromRow + toRow) / 2;
    int middleCol = (fromCol + toCol) / 2;


    return board[middleRow][middleCol] == OPTIONS.MARBLE;

  }

  /**
   * Verifies whether the given coordinates are within the boundaries of the board.
   *
   * @param row the row number
   * @param col the column number
   */
  private boolean isWithinBoard(int row, int col) {

    if (row < 0 || col < 0 || row >= lengthOfBoard || col >= lengthOfBoard) {
      return false;
    }

    if (row < (arm - 1) || row > (lengthOfBoard - arm)) {
      if (col < (arm - 1) || col > (lengthOfBoard - arm)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Constructing the board and adding the score.
   */
  private void construct() {

    for (int row = 0; row < lengthOfBoard; row++) {
      for (int col = 0; col < lengthOfBoard; col++) {
        if (isWithinBoard(row, col)) {
          board[row][col] = OPTIONS.MARBLE;
          score++;
        } else {
          board[row][col] = OPTIONS.INVALID;
        }
      }
    }


    if (!isWithinBoard(sRow, sCol)) {
      throw new IllegalArgumentException(String.format("Invalid empty cell position "
              + "(%s,%s)"
              + "", sRow, sCol));
    }

    // Assigning the empty slot in the board.
    board[sRow][sCol] = OPTIONS.EMPTY;
    // Reducing the score by 1.
    score--;

  }


}

