import java.util.*;
class Main {

  public static void printBoard(char[] board) {
    int index = 0;
    for(int i = 0; i < 3; i++) {
      for(int j = 0; j < 3; j++) {
        System.out.print(" " + board[index] + " ");
        if(j != 2) {
          System.out.print('|');
        }
        index++;
      }
      System.out.println();
      if(i != 2) {
        System.out.println("-----------");
      }
    }
  }

  public static boolean winner(char symbol, char[] board) {
    if (board[0] == symbol && board[1] == symbol && board[2] == symbol) {
      return true;
    } else if (board[3] == symbol && board[4] == symbol && board[5] == symbol) {
      return true;
    } else if (board[6] == symbol && board[7] == symbol && board[8] == symbol) {
      return true;
    } else if (board[0] == symbol && board[3] == symbol && board[6] == symbol) {
      return true;
    } else if (board[1] == symbol && board[4] == symbol && board[7] == symbol) {
      return true;
    } else if (board[2] == symbol && board[5] == symbol && board[8] == symbol) {
      return true;
    } else if (board[0] == symbol && board[4] == symbol && board[8] == symbol) {
      return true;
    } else if (board[6] == symbol && board[4] == symbol && board[2] == symbol) {
      return true;
    } else {
      return false;
    }
  }

  public static int getScore(int depth) {
    int score = 1;
    for (int i = 10 - depth; i > 0; i--) {
      score *= i;
    }
    return score;
  }

  public static int minmax(char[] board, int alpha, int beta, char symbol, int initial, int depth) {
    if(symbol == 'O') {
      symbol = 'X';
    }
    else {
      symbol = 'O';
    }

    depth++;
    char[] next = new char[9];
    int score;
    int maxScore = -5000000;
    int minScore = 5000000;
    int idealPath = 0;

    if (winner('X', board)) {
      return getScore(depth - 1);
    } else if (winner('O', board)) {
      return -1 * getScore(depth - 1);
    } else if(depth == 10) {
      return 0;
    }
    else {
      if(symbol == 'X') {
        for(int j = 0; j < 9; j++ ){
          if(board[j] == ' ') {
            next = board.clone();
            next[j] = symbol;
            score = minmax(next, alpha, beta, symbol, initial, depth);
            if(depth == initial + 1) {
              if(score > maxScore) {
                idealPath = j;
              }
            }
            maxScore = Math.max(score, maxScore);
            alpha = Math.max(alpha, score);
            if (beta <= alpha) {
              break;
            }
          }
        }
        if(depth == initial + 1) {
          return idealPath;
        } else {
          return maxScore;
        }
      }
      else {
        for(int j = 0; j < 9; j++ ){
          if(board[j] == ' ') {
            next = board.clone();
            next[j] = symbol;
            score = minmax(next, alpha, beta, symbol,initial, depth);
            minScore = Math.min(score, minScore);
            beta = Math.min(beta, score);
            if (beta <= alpha) {
              break;
            }
          }
        }
        return minScore;
      }
    }
  }

  public static void playerMove(Scanner scan, char[] board) {
    boolean valid = false;
    int move;
    printBoard(board);
    do {
      System.out.print("Where would you like to place an O (1-9): ");
      move = scan.nextInt();
      if (move < 1 || move > 9) {
        System.out.println("That is an invalid move");
      } else if (board[move - 1] != ' ') {
        System.out.println("There is already an item in that spot");
      }
      else {
        valid = true;
      }
    } while (!valid);
    board[move - 1] = 'O';
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    Random rand = new Random();
    char[] board = {' ', ' ',' ', ' ',' ', ' ',' ', ' ',' '};
    playerMove(scan, board);
    int compMove;
    int alpha = -5000000;
    int beta = 50000000;
    int turn = 1;
    do {
        compMove = minmax(board, alpha, beta, 'O', turn, turn);
        //System.out.println(compMove);
        board[compMove] = 'X';
        turn++;
        if (!winner('X', board)) {
          System.out.println();
            playerMove(scan, board);
            turn++;
        }
    }while(!winner('X', board) && !winner('O', board) && turn < 9);
    System.out.println();
    printBoard(board);
    if(winner('X', board)) {
        System.out.println("The computer wins");
    }
    else if (winner('O', board)){
        System.out.println("You win!!");
    }
    else {
        System.out.println("Tie");
    }
  }
}
