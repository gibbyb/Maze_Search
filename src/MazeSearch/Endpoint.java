package MazeSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Queue;
import java.util.Stack;

public class Endpoint
{
    public Position position;
    private Position[][] maze;

    private char symbol;

    public Endpoint(char symbol, Position[][] maze) throws IOException
    {
        this.maze = maze;
        this.symbol = symbol;
        boolean foundEndGoal = false;
        for (int i = 0; i < maze.length; i++)
        {
            for (int j = 0; j < maze[0].length; j++)
            {
                if (maze[i][j].getCharacter() == this.symbol)
                {
                    this.position = maze[i][j];
                    foundEndGoal = true;
                    System.out.printf("End goal position found at maze[%d][%d]\n", position.getRow(), position.getColumn());
                }
            }
        }
        if (foundEndGoal == false)
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Error! No Player was found!");
            reader.readLine();
        }

    }

    public Endpoint(char symbol, Position[][] maze, Queue<Position> endpointsToVisit) throws IOException
    {
        this.maze = maze;
        this.symbol = symbol;
        boolean foundEndGoal = false;
        for (int i = 0; i < maze.length; i++)
        {
            for (int j = 0; j < maze[0].length; j++)
            {
                if (maze[i][j].getCharacter() == this.symbol)
                {
                    this.position = maze[i][j];
                    foundEndGoal = true;
                    System.out.printf("End goal position found at maze[%d][%d]\n", position.getRow(), position.getColumn());
                    endpointsToVisit.add(maze[i][j]);
                }
            }
        }
        if (foundEndGoal == false)
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Error! No Player was found!");
            reader.readLine();
        }

    }

    public void checkForExtraEndpoints(Player searcher)
    {
        if (searcher.comparePosition(this.position) == true)
        {
            for (int i = 0; i < maze.length; i++)
            {
                for (int j = 0; j < maze[0].length; j++)
                {
                    if (maze[i][j].getCharacter() == this.symbol)
                    {
                        this.position = maze[i][j];
                        //System.out.printf("\nEnd goal position found at maze[%d][%d]\n\n", position.getRow(), position.getColumn());
                    }
                }
            }
        }
    }

    public Position getPosition() { return position; }
}
