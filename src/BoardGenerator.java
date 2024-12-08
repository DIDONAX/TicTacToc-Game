import java.util.Scanner;

public class BoardGenerator {
    private int scale;
    private String[] board;
    private Player currentPlayer;
    public BoardGenerator(int scale){
        if (scale < 3) {
            System.out.println("Error: scale must be at least 3");
        } else {
            //this.currentPlayer = Player.X;
            this.scale = scale;
            board = new String[this.scale * this.scale];
            for (int i = 0; i < scale*scale; i++) {
                board[i] = Integer.toString(i+1);
            }
        }
        createBoard();
    }

    public void createBoard() {
        int index = 0;
        for (int i = 0; i < scale; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < scale; j++) {
                row.append(addSpace(board[index++]) +"|");
            }
            System.out.println("|"+ row);
        }
    }

    public void setPlayer(int position, Player player) {
        board[position-1] = player.toString();
    }
    public String getSquare(int position) {
        return board[position-1];
    }
    public int getScale() {return scale;}
    public boolean isOccupied(int position) {
        if (isValid(position)) {
            return board[position-1].equals("O") || board[position-1].equals("X");
        } else {
            return false;
        }
    }
    public boolean isValid(int position) {return 0 < position && position <= board.length;}
    public void playTurn(Player player) {
        System.out.println("Player " + player + " turn, select a position");
        Scanner input = new Scanner(System.in);
        int position = input.nextInt();
        while (isOccupied(position) || !isValid(position)) {
            if (!isValid(position)) {
                System.out.println("Invalid position, try again");
            }
            else if (isValid(position) && isOccupied(position)) {
                System.out.println("Position is already occupied, try again");
            }
            position = input.nextInt();
        }
        setPlayer(position, player);
    }
    public void loop() {
        boolean nextPlayerO = true;
        while (!gameOver()) {
            if (nextPlayerO) {
                currentPlayer = Player.X;
                playTurn(currentPlayer);
            } else {
                currentPlayer = Player.O;
                playTurn(currentPlayer);
            }
            nextPlayerO = !nextPlayerO;
            createBoard();
        }
    }
    public boolean boardFull() {
        for (String square : board) {
            if (!square.equals("X") && !square.equals("O")) {
                return false;
            }
        }
        if (!winVertical() && !winHorizontal() && !winDiagonal()) {
            System.out.println("it's a draw");
            return true;
        } else {
            return winVertical() || winHorizontal() || winDiagonal();
        }
    }
    public boolean gameOver() {
        return winVertical() || winHorizontal() || winDiagonal() || boardFull();
    }
    /**public boolean playerWon() {

    }
     */
    public boolean winVertical() {
        for (int i = 0; i < scale; i++) {
            String initialSquare = board[i];
            int counter = 0;
            for (int j = i; j < i+1+scale*(scale-1); j+=scale) {
                if (initialSquare.equals(board[j])) {
                    counter++;
                    if (counter == scale) {
                        System.out.println(initialSquare + " wins");
                        return true;
                    }
                } else {
                    break;
                }
            }
        }
        return false;
    }
    public boolean winHorizontal() {
        //change the coeff to make it more genertic for other board scales (2)
        for (int i = 0; i < 1+(scale-1)*scale; i+=scale) {
            String initialSquare = board[i];
            int counter = 0;
            for (int j = i; j < i+scale; j++) {
                if (initialSquare.equals(board[j])) {
                    counter++;
                    if (counter == scale) {
                        System.out.println(initialSquare + " wins");
                        return true;
                    }
                } else {
                    break;
                }
            }
        }
        return false;
    }
    public boolean winDiagonal() {
        int counter = 0;
        for (int i = 0; i < 1+(scale-1)*(scale+1); i+=scale+1) {
            if (board[i].equals(board[0])) {
                counter++;
                if (counter == scale) {
                    System.out.println(board[0] + " wins");
                    return true;
                }
            } else {
                counter = 0;
                break;
            }
        }
        //it was (scale-1)+(scale-1)*(scale-1), but I simplified it
        for (int i = scale-1; i < 1+(scale-1)*scale; i+=scale-1) {
            if (board[i].equals(board[scale-1])) {
                counter++;
                if (counter == scale) {
                    System.out.println(board[scale-1] + " wins");
                    return true;
                }
            } else {
                break;
            }
        }
        return false;
    }

    public String addSpace(String s) {
        String s1 = Integer.toString(scale*scale);
        while (s.length() != s1.length() ) {
            s = " " + s;
        }
        return s;
    }
}
