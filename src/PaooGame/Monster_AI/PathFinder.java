package PaooGame.Monster_AI;

import PaooGame.Entity.Character;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

import java.util.ArrayList;

public class PathFinder {
    private RefLinks refLinks;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0, maxCol, maxRow;

    public PathFinder(RefLinks reflinks, int maxCol, int maxRow) {
        this.maxCol = maxCol;
        this.maxRow = maxRow;
        this.refLinks = reflinks;
        instantiateNodes();
    }

    public void instantiateNodes() {
        node = new Node[maxCol][maxRow];
        int col = 0, row = 0;

        while (col < maxCol && row < maxRow) {
            node[col][row] = new Node(col, row);

            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }
    }

    public void resetNodes() {
        int col = 0, row = 0;

        while (col < maxCol && row < maxRow) {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Character entity) {
        resetNodes();
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];

//        System.out.println("goal node: " + "col = " + goalNode.col + "row = " + goalNode.row);
        openList.add(currentNode);

        int col = 0, row = 0;

        while (col < maxCol && row < maxRow) {
            Tile tile = refLinks.GetMap().GetCollisionTile(col, row);
            if (tile.IsSolid()) {
                node[col][row].solid = true;
            }
            getCost(node[col][row]);

            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }

    }

    public void getCost(Node node) {
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        node.fCost = node.gCost + node.hCost;
    }

    public boolean search() {
        while (goalReached == false && step < 500) {
            int col = currentNode.col, row = currentNode.row;
            currentNode.checked = true;
            openList.remove(currentNode);

            // Open the Up node
            if (row - 1 >= 0 ) {
                openNode(node[col][row - 1]);
            }
            // Open the Left node
            if (col - 1 >= 0) {
                openNode(node[col - 1][row]);
            }

            // Open the down node
            if (row + 1 < maxRow) {
                openNode(node[col][row + 1]);
            }

            // Open the Right Node
            if (col + 1 < maxCol) {
                openNode(node[col + 1][row]);
            }
            // Find the best node
            int bestNodeIndex = 0, bestNodefCost = 999;

            for (int i = 0; i < openList.size(); ++i) {
                // Check if this node's f cost is better
                if (openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                // if f cost is equal, check g cost
                else if (openList.get(i).fCost == bestNodefCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }
            // if there is no node in the openList, end the loop
            if (openList.size() == 0) break;

            // After the loop, openList[bestNodeIndex] is the next step
            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }
            step++;
        }

        return goalReached;
    }
    public void openNode(Node node) {
        if (node.open == false && node.checked == false && node.solid == false) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackThePath() {
        Node current = goalNode;
        while (current != startNode) {
            pathList.add(0, current);
            current = current.parent;
        }
    }
}
