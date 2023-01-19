package MazeSearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Solution
{
    public static void main(String[] args) throws Exception
    {
        File smallMaze = new File("layouts/smallMaze.lay");
        File mediumMaze = new File("layouts/mediumMaze.lay");
        File bigMaze = new File("layouts/bigMaze.lay");
        File openMaze = new File("layouts/openMaze.lay");
        File trickySearch = new File("layouts/trickySearch.lay");

        boolean showAllSteps = false; // Change this to true if you want to see every step made by the searcher.
        //breadthFirstSearch(smallMaze, showAllSteps);
        //AStarSearch(smallMaze, showAllSteps);
        //breadthFirstSearch(mediumMaze, showAllSteps);
        //AStarSearch(mediumMaze, showAllSteps);
        //breadthFirstSearch(bigMaze, showAllSteps);
        //AStarSearch(bigMaze, showAllSteps);
        //breadthFirstSearch(openMaze);
        //AStarSearch(openMaze, showAllSteps);
        //badAStarSearch(mediumMaze, showAllSteps);// You can change the cost values with the badSetCost function in Postition Class!

        AStarSearch(trickySearch, showAllSteps);

    }

    public static void breadthFirstSearch(File mazeFile, boolean showAllSteps) throws Exception
    {
        System.out.print("BREADTH FIRST SEARCH - ");
        System.out.println(mazeFile.toString().substring(8,mazeFile.toString().length()));
        Position[][] mediumMaze = readMaze(mazeFile);
        System.out.printf("\nMaze[%d][%d]\n", mediumMaze[0].length, mediumMaze.length);
        printMaze(mediumMaze);
        Player bfsPacman = new Player('P', mediumMaze);
        Player bfsSearcher = new Player(bfsPacman.position, mediumMaze);
        Endpoint bfsEndpoint = new Endpoint('.', mediumMaze);

        Queue<Position> bfsQueue = new LinkedList<>();
        bfsQueue.add(bfsSearcher.getPosition());

        int i = 0;
        while (!(bfsQueue.isEmpty()) && !(bfsSearcher.comparePosition(bfsEndpoint.getPosition())))
        {
            bfsQueue.remove();
            checkMoves(mediumMaze, bfsSearcher, bfsQueue);
            if (mediumMaze[bfsSearcher.getRow()][bfsSearcher.getColumn()].getCharacter() != 'P')
                mediumMaze[bfsSearcher.getRow()][bfsSearcher.getColumn()].setCharacter('#');

            bfsSearcher.setPosition(bfsQueue.peek());

            mediumMaze[bfsSearcher.getRow()][bfsSearcher.getColumn()].setCharacter('@');
            if (showAllSteps == true)
            {
                System.out.println("Player position is " + bfsSearcher.PositionToString());
                printMaze(mediumMaze);
            }
            i++;
        }
        printMaze(mediumMaze);
        System.out.println("End goal found in " + i + " moves.\n");

        int j = 0;
        while (!(bfsSearcher.comparePosition(bfsPacman.position)))
        {
            mediumMaze[bfsSearcher.getRow()][bfsSearcher.getColumn()].setCharacter('+');
            bfsSearcher.setPosition(bfsSearcher.getPrevPosition());
            j++;
        }
        mediumMaze[bfsEndpoint.position.getRow()][bfsEndpoint.position.getColumn()].setCharacter('.');
        printCleanMaze(mediumMaze);
        System.out.println("\nShortest path found in " + j + " moves using BFS!\n\n\n\n\n");
    }

    public static void AStarSearch(File mazeFile, boolean showAllSteps) throws Exception
    {
        System.out.print("A* SEARCH - ");
        System.out.println(mazeFile.toString().substring(8,mazeFile.toString().length()));
        Position[][] bigMaze = readMaze(mazeFile);
        System.out.printf("\nMaze[%d][%d]\n", bigMaze[0].length, bigMaze.length);
        printMaze(bigMaze);
        Player AStarPacman = new Player('P', bigMaze);
        Player AStarSearcher = new Player(AStarPacman.position, bigMaze);
        Queue<Position> EndpointsToVisit = new LinkedList<>();
        Endpoint AStarEndpoint = new Endpoint('.', bigMaze);

        ArrayList<Position> positionList = new ArrayList<>();
        AStarSearcher.position.setCost(0);
        positionList.add(AStarSearcher.position);

        int l=0;
        while (!(positionList.isEmpty()) && !(AStarSearcher.comparePosition(AStarEndpoint.getPosition())))
        {
            int index = 0;
            if (positionList.size() > 1)
            {
                for (int k = 0; k < positionList.size() - 1; k++)
                {
                    if (positionList.get(k).getCost() > positionList.get(k+1).getCost())
                        index = k+1;
                }
            }

            if (bigMaze[AStarSearcher.getRow()][AStarSearcher.getColumn()].getCharacter() != 'P')
                bigMaze[AStarSearcher.getRow()][AStarSearcher.getColumn()].setCharacter('#');

            AStarSearcher.setPosition(positionList.get(index));
            positionList.get(index).setVisited(true);

            if (bigMaze[AStarSearcher.getRow()][AStarSearcher.getColumn()].getCharacter() != 'P')
                bigMaze[AStarSearcher.getRow()][AStarSearcher.getColumn()].setCharacter('@');

            positionList.remove(index);
            checkMovesAStar(bigMaze, AStarSearcher, positionList, AStarEndpoint);
            l++;
            AStarEndpoint.checkForExtraEndpoints(AStarSearcher);
            if (showAllSteps == true)
            {
                System.out.println("Player position is " + AStarSearcher.PositionToString());
                printMaze(bigMaze);
            }
        }
        printMaze(bigMaze);
        System.out.println("End goal found in " + l + " moves.\n");

        int m = 0;
        while (!(AStarSearcher.comparePosition(AStarPacman.position)))
        {
            bigMaze[AStarSearcher.getRow()][AStarSearcher.getColumn()].setCharacter('+');
            AStarSearcher.setPosition(AStarSearcher.getPrevPosition());
            m++;
        }
        bigMaze[AStarEndpoint.position.getRow()][AStarEndpoint.position.getColumn()].setCharacter('.');
        printCleanMaze(bigMaze);
        System.out.println("Shortest path found in " + m + " moves using A star search!!\n\n\n\n\n");
    }


    public static void badAStarSearch(File mazeFile, boolean showAllSteps) throws Exception
    {
        System.out.print("A* SEARCH - ");
        System.out.println(mazeFile.toString().substring(8,mazeFile.toString().length()));
        Position[][] bigMaze = readMaze(mazeFile);
        System.out.printf("\nMaze[%d][%d]\n", bigMaze[0].length, bigMaze.length);
        printMaze(bigMaze);
        Player AStarPacman = new Player('P', bigMaze);
        Player AStarSearcher = new Player(AStarPacman.position, bigMaze);
        Endpoint AStarEndpoint = new Endpoint('.', bigMaze);

        ArrayList<Position> positionList = new ArrayList<>();
        AStarSearcher.position.setCost(0);
        positionList.add(AStarSearcher.position);

        int l=0;
        while (!(positionList.isEmpty()) && !(AStarSearcher.comparePosition(AStarEndpoint.getPosition())))
        {
            int index = 0;
            if (positionList.size() > 1)
            {
                for (int k = 0; k < positionList.size() - 1; k++)
                {
                    if (positionList.get(k).getCost() > positionList.get(k+1).getCost())
                        index = k+1;
                }
            }

            if (bigMaze[AStarSearcher.getRow()][AStarSearcher.getColumn()].getCharacter() != 'P')
                bigMaze[AStarSearcher.getRow()][AStarSearcher.getColumn()].setCharacter('#');

            AStarSearcher.setPosition(positionList.get(index));
            positionList.get(index).setVisited(true);

            if (bigMaze[AStarSearcher.getRow()][AStarSearcher.getColumn()].getCharacter() != 'P')
                bigMaze[AStarSearcher.getRow()][AStarSearcher.getColumn()].setCharacter('@');

            positionList.remove(index);
            badCheckMovesAStar(bigMaze, AStarSearcher, positionList, AStarEndpoint);
            l++;
            AStarEndpoint.checkForExtraEndpoints(AStarSearcher);
        }
        printMaze(bigMaze);
        System.out.println("End goal found in " + l + " moves.\n");

        int m = 0;
        while (!(AStarSearcher.comparePosition(AStarPacman.position)))
        {
            bigMaze[AStarSearcher.getRow()][AStarSearcher.getColumn()].setCharacter('+');
            AStarSearcher.setPosition(AStarSearcher.getPrevPosition());
            m++;
        }
        bigMaze[AStarEndpoint.position.getRow()][AStarEndpoint.position.getColumn()].setCharacter('.');
        printCleanMaze(bigMaze);
        System.out.println("Shortest path found in " + m + " moves using A star search!!\n\n\n\n\n");
    }

    public static Position[][] readMaze(File maze) throws Exception
    {
        Scanner mazeLineCounter = new Scanner(maze);
        int mazeLineAmount = 0;
        while (mazeLineCounter.hasNextLine())
        {
            mazeLineAmount++;
            mazeLineCounter.nextLine();
        }
        BufferedReader mazeCharCounter = new BufferedReader(new FileReader(maze));
        int mazeCharAmount = mazeCharCounter.readLine().toCharArray().length;

        char[][] mazeCharArray = new char[mazeLineAmount][mazeCharAmount];
        BufferedReader mazeReader = new BufferedReader(new FileReader(maze));

        for (int i = 0; i < mazeLineAmount; i++)
        {
            mazeCharArray[i] = mazeReader.readLine().toCharArray();
        }

        Position[][] mazeArray = new Position[mazeLineAmount][mazeCharAmount];

        for (int i = 0; i < mazeLineAmount; i++)
        {
            for (int j = 0; j < mazeCharAmount; j++)
            {
                mazeArray[i][j] = new Position(i,j,mazeCharArray[i][j]);
            }
        }
        for (int i = 0; i < mazeLineAmount; i++)
        {
            for (int j = 0; j < mazeCharAmount; j++)
            {
                mazeArray[i][j] = new Position(i,j,mazeCharArray[i][j], mazeArray);
            }
        }
        return mazeArray;
    }

    public static void printMaze(Position[][] maze)
    {
        System.out.println();
        for (int i = 0; i < maze.length; i++)
        {
            for (int j = 0; j < maze[0].length; j++)
            {
                System.out.print(maze[i][j].getCharacter());
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void checkMoves(Position[][] maze, Player searcher, Queue<Position> bfsQueue)
    {
        // Up
        if (maze[searcher.getRow() - 1][searcher.getColumn()].getCharacter() == ' ' || maze[searcher.getRow() - 1][searcher.getColumn()].getCharacter() == '.')
        {
            searcher.getUpPos().setPrevPosition(searcher.getPosition());
            bfsQueue.add(searcher.getUpPos());
            //System.out.println("Player can move up.");
        }
        // Down
        if (maze[searcher.getRow() + 1][searcher.getColumn()].getCharacter() == ' ' || maze[searcher.getRow() + 1][searcher.getColumn()].getCharacter() == '.')
        {
            searcher.getDownPos().setPrevPosition(searcher.getPosition());
            bfsQueue.add(searcher.getDownPos());
            //System.out.println("Player can move down.");
        }
        // Left
        if (maze[searcher.getRow()][searcher.getColumn() - 1].getCharacter() == ' ' || maze[searcher.getRow()][searcher.getColumn() - 1].getCharacter() == '.')
        {
            searcher.getLeftPos().setPrevPosition(searcher.getPosition());
            bfsQueue.add(searcher.getLeftPos());
            //System.out.println("Player can move left.");
        }
        // Right
        if (maze[searcher.getRow()][searcher.getColumn() + 1].getCharacter() == ' ' || maze[searcher.getRow()][searcher.getColumn() + 1].getCharacter() == '.')
        {
            searcher.getRightPos().setPrevPosition(searcher.getPosition());
            bfsQueue.add(searcher.getRightPos());
            //System.out.println("Player can move right.");
        }
    }

    public static void checkMovesAStar(Position[][] maze, Player searcher, ArrayList<Position> openList, Endpoint endpoint)
    {
        // Up
        if (maze[searcher.getRow() - 1][searcher.getColumn()].getCharacter() != '%' && !(searcher.getUpPos().getVisited()))
        {
            searcher.getUpPos().setPrevPosition(searcher.getPosition());
            searcher.getUpPos().setCost(searcher.position, endpoint.position);
            openList.add(searcher.getUpPos());
            //System.out.println("Player can move up.");
        }
        // Down
        if (maze[searcher.getRow() + 1][searcher.getColumn()].getCharacter() != '%' && !(searcher.getDownPos().getVisited()))
        {
            searcher.getDownPos().setPrevPosition(searcher.getPosition());
            searcher.getDownPos().setCost(searcher.position, endpoint.position);
            openList.add(searcher.getDownPos());
            //System.out.println("Player can move down.");
        }
        // Left
        if (maze[searcher.getRow()][searcher.getColumn() - 1].getCharacter() != '%' && !(searcher.getLeftPos().getVisited()))
        {
            searcher.getLeftPos().setPrevPosition(searcher.getPosition());
            searcher.getLeftPos().setCost(searcher.position, endpoint.position);
            openList.add(searcher.getLeftPos());
            //System.out.println("Player can move left.");
        }
        // Right
        if (maze[searcher.getRow()][searcher.getColumn() + 1].getCharacter() != '%' && !(searcher.getRightPos().getVisited()))
        {
            searcher.getRightPos().setPrevPosition(searcher.getPosition());
            searcher.getRightPos().setCost(searcher.position, endpoint.position);
            openList.add(searcher.getRightPos());
            //System.out.println("Player can move right.");
        }
    }
    // delete later
    public static void badCheckMovesAStar(Position[][] maze, Player searcher, ArrayList<Position> openList, Endpoint endpoint)
    {
        // Up
        if (maze[searcher.getRow() - 1][searcher.getColumn()].getCharacter() != '%' && !(searcher.getUpPos().getVisited()))
        {
            searcher.getUpPos().setPrevPosition(searcher.getPosition());
            searcher.getUpPos().badSetCost(searcher.position, endpoint.position);
            openList.add(searcher.getUpPos());
            //System.out.println("Player can move up.");
        }
        // Down
        if (maze[searcher.getRow() + 1][searcher.getColumn()].getCharacter() != '%' && !(searcher.getDownPos().getVisited()))
        {
            searcher.getDownPos().setPrevPosition(searcher.getPosition());
            searcher.getDownPos().badSetCost(searcher.position, endpoint.position);
            openList.add(searcher.getDownPos());
            //System.out.println("Player can move down.");
        }
        // Left
        if (maze[searcher.getRow()][searcher.getColumn() - 1].getCharacter() != '%' && !(searcher.getLeftPos().getVisited()))
        {
            searcher.getLeftPos().setPrevPosition(searcher.getPosition());
            searcher.getLeftPos().badSetCost(searcher.position, endpoint.position);
            openList.add(searcher.getLeftPos());
            //System.out.println("Player can move left.");
        }
        // Right
        if (maze[searcher.getRow()][searcher.getColumn() + 1].getCharacter() != '%' && !(searcher.getRightPos().getVisited()))
        {
            searcher.getRightPos().setPrevPosition(searcher.getPosition());
            searcher.getRightPos().badSetCost(searcher.position, endpoint.position);
            openList.add(searcher.getRightPos());
            //System.out.println("Player can move right.");
        }
    }

    public static void printCleanMaze(Position[][] maze)
    {
        System.out.println();
        for (int i = 0; i < maze.length; i++)
        {
            for (int j = 0; j < maze[0].length; j++)
            {
                if (maze[i][j].getCharacter() == '#')
                    System.out.print(' ');
                else
                    System.out.print(maze[i][j].getCharacter());
            }
            System.out.println();
        }
    }


}