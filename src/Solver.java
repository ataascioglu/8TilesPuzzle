import java.util.*;

// A class that implements the A* search algorithm to solve the 8 puzzle problem.
public class Solver {
    // A* search algorithm
    public static void solve(Board initialBoard) {
        PriorityQueue<State> pq = new PriorityQueue<>(); // Priority queue to store states
        Set<Board> visited = new HashSet<>(); // Set to keep track of visited boards
        Map<Board, State> cameFrom = new HashMap<>(); // Map to store the parent state for each board

        pq.add(new State(initialBoard, 0)); // Add initial state to the priority queue
        cameFrom.put(initialBoard, null); // Mark the initial board's parent as null

        while (!pq.isEmpty()) { // Continue until priority queue is empty
            State current = pq.poll(); // Retrieve and remove the state with the lowest priority
            Board currentBoard = current.board; // Retrieve the board from the current state

            if (currentBoard.isGoal()) { // Check if the current board is the goal state
                printSolution(current, cameFrom); // Print the solution
                return; // Exit the method
            }

            visited.add(currentBoard); // Mark the current board as visited

            // Generate possible moves from the current board
            for (Board neighbor : currentBoard.neighbors()) {
                if (!visited.contains(neighbor)) { // Check if the neighbor has not been visited
                    pq.add(new State(neighbor, current.moves + 1)); // Add the neighbor to the priority queue with an updated move count
                    cameFrom.put(neighbor, current); // Store the parent state for the neighbor
                }
            }
        }

        System.out.println("No solution found."); // Print a message if no solution is found
    }

    // Method to print the solution
    private static void printSolution(State current, Map<Board, State> cameFrom) {
        List<State> solution = new ArrayList<>(); // List to store the sequence of moves
        while (current != null) {
            solution.add(current); // Add the current state to the solution list
            current = cameFrom.get(current.board); // Move to the parent state
        }

        // Print the solution in reverse order
        System.out.println("Solution found in " + (solution.size() - 1) + " moves"); // -1 because initial state is not a move
        for (int i = solution.size() - 1; i >= 0; i--) {
            System.out.println("Move " + (solution.size() - i - 1) + ":");
            solution.get(i).board.draw(); // Draw the board for each step
            System.out.println(); // Add a newline for readability
        }
    }

    // Main method
    public static void main(String[] args) {
        // StdDraw setup and board creation
        StdDraw.setCanvasSize(500, 500); // Set canvas size for drawing
        StdDraw.setScale(0.5, 3.5); // Set scale for drawing
        StdDraw.enableDoubleBuffering(); // Enable double buffering for smoother drawing
        Board initialBoard = new Board(); // Create initial board

        // Solve the puzzle
        solve(initialBoard); // Call the solve method to find the solution

        StdDraw.show(); // Display the final solution
    }
}
