package MazeSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class Player
{
    public Position position;
    private Position[][] maze;

    public Player(char symbol, Position[][] maze) throws IOException
    {
        this.maze = maze;
        boolean foundPlayer = false;
        for (int i = 0; i < maze.length; i++)
        {
            for (int j = 0; j < maze[0].length; j++)
            {
                if (maze[i][j].getCharacter() == symbol)
                {
                    this.position = maze[i][j];
                    foundPlayer = true;
                    System.out.printf("Player position found at maze[%d][%d]\n", position.getRow(), position.getColumn());
                }
            }
        }
        if (foundPlayer == false)
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Error! No Player was found!");
            reader.readLine();
        }

    }
    public Player(Position pos, Position[][] maze)
    {
        this.maze = maze;
        this.position = pos;
    }

    public void setPosition(Position pos)
    {
        this.position = maze[pos.getRow()][pos.getColumn()];
    }
    public void setRow(int row)
    {
        this.position = maze[row][position.getColumn()];
    }
    public void setColumn(int column)
    {
        this.position = maze[position.getRow()][column];
    }
    public void setCharacter(char ch)
    {
        maze[position.getRow()][position.getColumn()].setCharacter(ch);
    }

    public Position getPosition() { return maze[position.getRow()][position.getColumn()]; }
    public int getRow() { return position.getRow(); }
    public int getColumn() { return position.getColumn();}
    public Position getPrevPosition() { return maze[position.getPrevSpace().getRow()][position.getPrevSpace().getColumn()]; }


    public boolean comparePosition(Position pos)
    {
        return position.compare(pos);
    }

    public String PositionToString()
    {
        return position.toString();
    }

    public Position getUpPos() { return position.getUpPos(); }
    public Position getDownPos() { return position.getDownPos(); }
    public Position getLeftPos() { return position.getLeftPos(); }
    public Position getRightPos() { return position.getRightPos(); }



}
