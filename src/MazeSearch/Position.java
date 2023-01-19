package MazeSearch;

public class Position
{
    private int row;
    private int column;
    private char character;
    private Position prevSpace;
    private Position[][] Maze;
    private int startToHereDistance;
    private int manhattanDistance;
    private int cost;

    private boolean visited;


    public Position(int row, int column, char character)
    {
        this.row = row;
        this.column = column;
        this.character = character;
    }

    public Position(int row, int column, char character, Position[][] maze)
    {
        this.row = row;
        this.column = column;
        this.character = character;
        this.Maze = maze;
        this.cost = 9999;
        this.visited = false;
    }

    public void setCharacter(char character) { this.character = character; }
    public void setPrevPosition(Position prevPos) { this.prevSpace = prevPos; }
    public void setStartToHereDistance(Position playerPosition)
    {
        int rowDiff = Math.abs(this.row - playerPosition.getRow());
        int colDiff = Math.abs(this.column - playerPosition.getColumn());
        this.startToHereDistance = rowDiff + colDiff;
    }
    public void setManhattanDistance(Position endPointPosition)
    {
        int rowDiff = Math.abs(this.row - endPointPosition.getRow());
        int colDiff = Math.abs(this.column - endPointPosition.getColumn());
        this.manhattanDistance = rowDiff + colDiff;
    }
    //delete this later
    public void badSetCost(Position playerPosition, Position endPointPosition)
    {

        int rowDiff = Math.abs(this.row - playerPosition.getRow());
        int colDiff = (Integer) ((1/2) ^ Math.abs(this.column - playerPosition.getColumn()));
        this.startToHereDistance = rowDiff + colDiff;
        int rowDiff1 = Math.abs(this.row - endPointPosition.getRow());
        int colDiff1 = (Integer) ((1/2) ^ Math.abs(this.column - endPointPosition.getColumn()));
        this.manhattanDistance = rowDiff1 + colDiff1;
        this.cost = this.manhattanDistance + this.startToHereDistance;
    }
    public void setCost(Position playerPosition, Position endPointPosition)
    {
        int rowDiff = Math.abs(this.row - playerPosition.getRow());
        int colDiff = Math.abs(this.column - playerPosition.getColumn());
        this.startToHereDistance = rowDiff + colDiff;
        int rowDiff1 = Math.abs(this.row - endPointPosition.getRow());
        int colDiff1 = Math.abs(this.column - endPointPosition.getColumn());
        this.manhattanDistance = rowDiff1 + colDiff1;
        this.cost = this.manhattanDistance + this.startToHereDistance;
    }
    public void setCost(int num) { this.cost = 0; }
    public void setVisited(boolean visited){this.visited = true;}

    public int getRow() { return row; }
    public int getColumn() { return column; }
    public char getCharacter() { return character; }
    public Position getPrevSpace() { return prevSpace; }
    public int getStartToHereDistance() { return startToHereDistance; }
    public int getManhattanDistance() { return manhattanDistance; }
    public int getCost() { return this.cost; }
    public boolean getVisited() {return this.visited;}

    public String toString()
    { return "Maze[" + this.row + "]" + "[" + this.column + "]"; }

    boolean compare(Position position)
    {
        if (this.row == position.getRow() && this.column == position.getColumn())
            return true;
        else
            return false;
    }

    public Position getUpPos() { return Maze[this.row - 1][this.column]; }
    public Position getDownPos()
    {
        return Maze[this.row + 1][this.column];
    }
    public Position getLeftPos()
    {
        return Maze[this.row][this.column - 1];
    }
    public Position getRightPos() { return Maze[this.row][this.column + 1]; }


}
