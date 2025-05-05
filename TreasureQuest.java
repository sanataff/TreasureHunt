import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class TreasureQuest {
   

    public static int[] findSpecialTiles(char[][] myMap) {
        int[] specialTiles = new int[2]; // 0 for S, 1 for E
        int cols = myMap[0].length;
        boolean foundS = false, foundE = false;
        
        for (int i = 0; i < myMap.length; i++) {
            for (int j = 0; j < myMap[i].length; j++) {
                if (myMap[i][j] == 'S') {
                    specialTiles[0] = node(i, j, cols);
                    foundS = true;
                } else if (myMap[i][j] == 'E') {
                    specialTiles[1] = node(i, j, cols);
                    foundE = true;
                }
            }
            if (foundS && foundE) break;
        }
        return specialTiles;
    }

    private static boolean isInBounds(int x, int y, int rows, int cols) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    private static int node(int i, int j, int cols) {
        return (i * cols) + j;
    }

    public static Graph buildGraphFromMap(boolean[][] SafeMatrix) {
        int rows = SafeMatrix.length;
        int cols = SafeMatrix[0].length;
        Graph graph = new Graph(rows * cols);

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // up, down, left, right

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (SafeMatrix[i][j]) {
                    for (int[] dir : directions) {
                        int newX = i + dir[0];
                        int newY = j + dir[1];
                        if (isInBounds(newX, newY, rows, cols) && SafeMatrix[newX][newY]) {
                            graph.addEdge(node(i, j, cols), node(newX, newY, cols), 1);
                        }
                    }
                }
            }
        }
        return graph;
    }

    public static char[][] readMap(int n1, int n2) {
        Scanner scanner = new Scanner(System.in);
        char[][] mapMatrix = new char[n1][n2];
        for (int i = 0; i < n1; i++) {
            String line = scanner.nextLine().trim();
            mapMatrix[i] = line.toCharArray();
        }
        return mapMatrix;
    }

    public static int[][] readMonsterPositions(int numOfMonsters,int n, int m) {
        Scanner scanner = new Scanner(System.in);
        int[][] monsterPositions = new int[numOfMonsters][3];
        for (int i = 0; i < numOfMonsters; i++) {
            monsterPositions[i][0] = scanner.nextInt() - 1; // convert to 0-based index
            monsterPositions[i][1] = scanner.nextInt() - 1;
            monsterPositions[i][2] = scanner.nextInt();
            if(monsterPositions[i][0] < 0 || monsterPositions[i][1] < 0 || monsterPositions[i][0]>n || monsterPositions[i][1]>m){
                System.out.println("Invalid monster position: " + (monsterPositions[i][0]+1) + " " + (monsterPositions[i][1]+1));
            }
            
        }
        
        return monsterPositions;
    }
    public static void shotest(int start, int end, Graph graph){
        for(int i = 0; i < graph.adjacencylist.length; i++){
            if(i== start){

            }
        }
    }
    public static boolean[][] markMonitoredTiles(char[][] mapMatrix, int n1, int n2, int[][] monsterPositions) {
        boolean[][] safe = new boolean[n1][n2];
        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < n2; j++) {
                safe[i][j] = (mapMatrix[i][j] != '#');
            }
        }
    
      
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    
        for (int[] monster : monsterPositions) {
            int x = monster[0];
            int y = monster[1];
            int range = monster[2];
            
            
            safe[x][y] = false;
            
           
            for (int[] dir : directions) {
                int dx = dir[0];
                int dy = dir[1];
                
                for (int r = 1; r <= range; r++) {
                    int newX = x + dx * r;
                    int newY = y + dy * r;
                    
                    
                    if (!isInBounds(newX, newY, n1, n2)){
                        break;

                    } 
                    if (mapMatrix[newX][newY] == '#'){break;} 
                    
                    safe[newX][newY] = false;
                }
            }
        }
        
        return safe;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int k = scanner.nextInt();
        scanner.nextLine(); 
        // Read map
        char[][] map = readMap(n, m);
        
        // Find S and E positions
        int[] startEnd = findSpecialTiles(map);
        int start = startEnd[0];
        int end = startEnd[1];
        System.out.println("Start: " + start + ", End: " + end);
        int[][] monsters = readMonsterPositions(k, n ,m);
        
    
        boolean[][] safeMap = markMonitoredTiles(map, n, m, monsters);

        int startX = start / m;
        int startY = start % m;
        int endX = end / m;
        int endY = end % m;
        
        if (!safeMap[startX][startY] || !safeMap[endX][endY]) {
            System.out.println("IMPOSSIBLE");
            return;
        }
        
       
        Graph graph = buildGraphFromMap(safeMap);
        
      
        graph.dijkstra_GetMinDistances(start,end);

        
       
        
       
    }
}