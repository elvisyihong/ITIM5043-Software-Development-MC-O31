import java.util.Scanner;


public class BattleshipGame {
    // Board sizes
    private static final int BOARD_SIZE = 10;

    // Symbols used in the board
    private static final char EMPTY = ' ';
    private static final char SHIP = '@';
    private static final char HIT = 'X';
    private static final char MISS = '-';
    private static final char SUNK = '!';

    // Number of ships
    private static final int SHIP_COUNT = 5;

    // Boards for player and computer
    private static char[][] playerBoard;
    private static char[][] computerBoard;

    // Remaining ships
    private static int playerShipsRemaining;
    private static int computerShipsRemaining;

    // Scanner for user input
    private static Scanner input;


    // Constructor to initialize the game
    public BattleshipGame() {
        playerBoard = new char[BOARD_SIZE][BOARD_SIZE];
        computerBoard = new char[BOARD_SIZE][BOARD_SIZE];
        playerShipsRemaining = SHIP_COUNT;
        computerShipsRemaining = SHIP_COUNT;

        input = new Scanner(System.in);

        // Initialize boards
        initializeBoard(playerBoard);
        initializeBoard(computerBoard);
    }

    private static void clearConsole() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }

    // Initialize the game board with empty spaces
    private static void initializeBoard(char[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    private static void debugBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " | ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (computerBoard[i][j] == SHIP) {
                    System.out.print(computerBoard[i][j] + " ");}
                else {
                    System.out.print("  ");
                }
            }
            System.out.println("| " + i);
        }
    }

    // Display the board with the specified layout
    private static void displayBoard() {
        System.out.println();
        // Print column headers
        System.out.print("    ");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Print the board with row headers and vertical bars
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " | ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (playerBoard[i][j] == HIT || playerBoard[i][j] == SHIP) {
                    System.out.print(playerBoard[i][j] + " ");}
                else if (computerBoard[i][j] == SUNK || computerBoard[i][j] == MISS) {
                    System.out.print(computerBoard[i][j] + " ");}
                 else {
                    System.out.print("  ");
                }
            }
            System.out.println("| " + i);
        }

        // Print column headers again
        System.out.print("    ");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println();
    }

    // Place ships on the board
    private static void placeShips(char[][] board, String user) {
        for (int i = 0; i < SHIP_COUNT; i++) {
            int x, y;
            boolean validPlacement = false;

            do {
                // Prompt the user for coordinates
                if (user.equals("Player")) {
                    System.out.println("Deploy your ships: ");
                    System.out.print("Enter X coordinate for your " + (i + 1) + ". ship: ");
                    x = input.nextInt();
                    System.out.print("Enter Y coordinate for your " + (i + 1) + ". ship: ");
                    y = input.nextInt();
                } else {
                    // Randomly generate coordinates for computer
                    x = (int) (Math.random() * BOARD_SIZE);
                    y = (int) (Math.random() * BOARD_SIZE);
                }

                // Validate coordinates
                if (!isValidCoordinate(x, y)) {
                    if (user.equals("Player")) {
                        System.out.println("Error: Coordinates out of range. Please enter valid " +
                                                   "coordinates.");
                    }
                } else if (board[x][y] == SHIP) {
                    if (user.equals("Player")) {
                        System.out.println("Error: There is already a ship at these coordinates. " +
                                                   "Please enter different coordinates.");
                    }
                } else if (user.equals("Computer") && playerBoard[x][y] == SHIP) {
                    // Prevent the computer from placing a ship where the player has already placed one
                    // Do nothing, loop will continue to generate new coordinates
                } else {
                    validPlacement = true;
                }
            } while (!validPlacement);

            // Place the ship on the board
            board[x][y] = SHIP;

            // Display the player's board after placing each ship
            if (user.equals("Player")) {
                clearConsole();
                displayBoard();
            } else {
                System.out.println((i + 1) + ". ship DEPLOYED");
            }
        }
        System.out.println("---------------------------------------------------------");
    }

    // Validate coordinates
    private static boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
    }

    // Player's turn to guess
    private static void playerTurn() {
        System.out.println("Player's turn to guess...");
        int x, y;
        boolean validGuess = false;

        do {
            System.out.print("Enter X coordinate to attack: ");
            x = input.nextInt();
            System.out.print("Enter Y coordinate to attack: ");
            y = input.nextInt();

            if (!isValidCoordinate(x, y)) {
                System.out.println("Error: Coordinates out of range. Please enter valid coordinates.");
            } else if (computerBoard[x][y] == HIT || computerBoard[x][y] == MISS || computerBoard[x][y] == SUNK) {
                System.out.println("Error: You have already guessed this coordinate. Please try again.");
            } else {
                validGuess = true;
            }
        } while (!validGuess);

        clearConsole();

        if (computerBoard[x][y] == SHIP) {
            System.out.println("Boom! You sunk the ship");
            computerBoard[x][y] = SUNK;
            computerShipsRemaining--;
        } else if (playerBoard[x][y] == SHIP) {
            System.out.println("Oh no, you sunk your own ship :(");
            playerBoard[x][y] = HIT;
            computerBoard[x][y] = MISS;
            playerShipsRemaining--;
        } else {
            System.out.println("Miss!");
            computerBoard[x][y] = MISS;
        }
        System.out.println("-----------------------------------");
    }

    // Computer's turn to guess
    private static void computerTurn() {
        System.out.println("Computer's turn to guess...");
        int x, y;
        do {
            x = (int) (Math.random() * BOARD_SIZE);
            y = (int) (Math.random() * BOARD_SIZE);
        } while (!isValidCoordinate(x, y) || playerBoard[x][y] == HIT ||
                playerBoard[x][y] == MISS || playerBoard[x][y] == SUNK);

        if (playerBoard[x][y] == SHIP) {
            System.out.println("The Computer sunk one of your ships!");
            playerBoard[x][y] = HIT;
            playerShipsRemaining--;
        } else if (computerBoard[x][y] == SHIP) {
            System.out.println("The Computer sunk one of its own ships");
            computerBoard[x][y] = SUNK;
            playerBoard[x][y] = MISS;
            computerShipsRemaining--;
        } else {
            System.out.println("Computer missed at (" + x + "," + y + ")!");
            playerBoard[x][y] = MISS;
        }
        System.out.println("-----------------------------------");
    }


    // Check if all ships are hit
    private static boolean checkWin(char[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == SHIP) {
                    return false;
                }
            }
        }
        return true;
    }

    // Display current status
    private static void displayStatus() {
        System.out.println("Your ships: " + playerShipsRemaining + " | Computer ships: " + computerShipsRemaining);
        displayBoard();
    }

    // Start the game
    public static void startGame() {
        clearConsole();
        // Welcome message
        System.out.println("**** Welcome to Battle Ships game ****");
        System.out.println();
        System.out.println("Right now, the sea is empty");

        // Display the empty player board
        displayBoard();

        // Place ships for player
        placeShips(playerBoard, "Player");

        clearConsole();

        // Inform that computer is deploying ships
        System.out.println("Computer is deploying ships");
        System.out.println("---------------------------------------------------------");

        // Place ships for computer
        placeShips(computerBoard, "Computer");

        // To Display Computer Coordinates for testing
        debugBoard();

        // Display player's board with ships
        System.out.println("Player's board with deployed ships:");
        displayBoard();
        System.out.println();

        while (true) {
            playerTurn();

            if (checkWin(computerBoard)) {
                displayStatus();
                System.out.println("Hooray! You win the battle :)");
                break;
            } else if (checkWin(playerBoard)) {
                displayStatus();
                System.out.println("Computer wins!");
                break;
            }

            computerTurn();
            displayStatus();

            if (checkWin(playerBoard)) {
                System.out.println("Computer wins!");
                break;
            } else if (checkWin(computerBoard)) {
                System.out.println("Hooray! You win the battle :)");
                break;
            }
        }
    }

    public static void main(String[] args) {
        BattleshipGame game = new BattleshipGame();

        // Start the game
        game.startGame();
    }
}
