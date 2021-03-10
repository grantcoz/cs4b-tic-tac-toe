package models;

import java.util.ArrayList;
import java.util.UUID;
import javax.swing.tree.DefaultMutableTreeNode;
import javafx.scene.paint.Color;
import org.javatuples.Pair;
import org.javatuples.Triplet;

public class Ai extends Player{

    /*==========================================================================================================
     * CLASS VARIABLES
     *==========================================================================================================*/

     /** 
     * Constructs a new Ai object by using the super class constructor.
     * @param color The color of the Ai's marker.
     * @param id    The Ai's uuid.
     * @param name  The name of the Ai.
     * @param shape The shape of the Ai's marker.
     */
    public Ai(Color color, String name, MarkerShape shape) {
        super(color, UUID.randomUUID(), name, shape);
        this.isAi = true;
    }

    public Ai(Player player){
        this(player.getColor(), player.getName(), player.getShape());
    }

    public Player toPlayer(){
        return new Player(getColor(), UUID.randomUUID(), getName(), getShape());
    }

    /*==========================================================================================================
     * ACCESSORS & MUTATORS
     *==========================================================================================================*/

    /**
     * This method will determine the best play that the AI can make with the current state of the board.
     * Returns the best pair of x and y values for the AI player to play.
     * @param gameState The current state  of the game.
     * @return the best pair of x and y values for the AI player to play.
     */
    public Pair<Integer, Integer> generateMove(GameState gameState){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new Triplet<Integer, Integer, Integer>(null, null, null));

        ArrayList<Pair<Integer, Integer>> emptyCells = new ArrayList<Pair<Integer, Integer>>();
        for(int i = 0; i < gameState.getGridSize(); i++){
            for(int j = 0; j < gameState.getGridSize(); j++){
                if(gameState.getCell(i, j) == null){
                    emptyCells.add(new Pair<Integer, Integer>(Integer.valueOf(i), Integer.valueOf(j)));
                }
            }
        }

        Triplet<Integer, Integer, Integer> bestMove = findBestMove(root, emptyCells, gameState.getVictoryArr(), gameState.getGridSize());

        return bestMove.removeFrom2();
    }
    
    @SuppressWarnings("unchecked")
    private Triplet<Integer, Integer, Integer> findBestMove(DefaultMutableTreeNode node, ArrayList<Pair<Integer, Integer>> emptyCells, ArrayList<Integer> victoryArr, int gridSize){
        boolean isMax = (node.getPath().length % 2) == 0;
        Triplet<Integer, Integer, Integer> bestMove = new Triplet<Integer, Integer, Integer>(null, null, isMax ? -100 : 100);

        int cellIndex = 0;
        boolean shortCircuit = false;

        while(cellIndex < emptyCells.size() && !shortCircuit){
            Pair<Integer, Integer> cell = emptyCells.get(cellIndex);
            
            ArrayList<Integer> vArrClone = new ArrayList<Integer>();
            for(Integer i : victoryArr){
                vArrClone.add(Integer.valueOf(i.intValue()));
            }

            boolean isVictory = GameState.checkVictoryArr(vArrClone, cell, isMax);

            DefaultMutableTreeNode child = new DefaultMutableTreeNode();
            node.add(child);

            if(isVictory){
                int weight = evaluate(vArrClone, cell, gridSize);
                child.setUserObject(cell.add(Integer.valueOf(weight)));
                shortCircuit = true;
            } else if(emptyCells.size() == 1){
                child.setUserObject(cell.add(Integer.valueOf(0)));
            } else{
                ArrayList<Pair<Integer, Integer>> emptyCellsClone = new ArrayList<Pair<Integer, Integer>>();
                
                for(Pair<Integer, Integer> emptyCell : emptyCells){
                    if(emptyCell != cell){
                        emptyCellsClone.add(emptyCell);
                    }
                }
                child.setUserObject(cell.add((Integer)null));

                findBestMove(child, emptyCellsClone, vArrClone, gridSize);
            }

            Triplet<Integer, Integer, Integer> childData = (Triplet<Integer, Integer, Integer>)child.getUserObject();
            if((isMax && childData.getValue2() > bestMove.getValue2()) || (!isMax && childData.getValue2() < bestMove.getValue2())){
                bestMove = childData;
            }

            cellIndex++;
        }

        Triplet<Integer, Integer, Integer> currentData = (Triplet<Integer, Integer, Integer>)node.getUserObject();
        node.setUserObject(currentData.setAt2(bestMove.getValue2()));
        return bestMove;
    }

    private int evaluate(ArrayList<Integer> victoryArr, Pair<Integer, Integer> move, int gridSize){
        int i = 0;
        boolean keepSearching = true;

        while(keepSearching && i < victoryArr.size()){
            if(Math.abs(victoryArr.get(i)) >= gridSize){
                keepSearching = false;
            } else{
                i++;
            }
        }
        
        if(i == victoryArr.size()){
            return 0;
        } else if(victoryArr.get(i) < 0){
            return -10;
        } else{
            return 10;
        }
    }
}
