import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Artwork {
    
    static class Square {
        int x;
        int y;
        
        public Square(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public static class UnionFind {
        int[] nodes;
        int[] size;
        int count;
        
        public UnionFind(int size) {
            nodes = new int[size];
            this.size = new int[size];
            for (int i = 0; i < size; i++) {
                nodes[i] = i;
                this.size[i] = 1;
            }
            count = 0;
        }
        
        public boolean check(int e1, int e2) {
            return find(e1) == find(e2);
        }
        
        public void union(int e1, int e2) {
            int root1 = find(e1);
            int root2 = find(e2);
            if (root1 == root2) { return; }
            if (size[root1] > size[root2]) {
                nodes[root2] = root1;
                size[root1] += size[root2];
            } else {
                nodes[root1] = root2;
                size[root2] += size[root1];
            }
            count++;
        }
        
        private int find(int e) {
            int curr = nodes[e];
            while (curr != nodes[curr]) {
                curr = nodes[curr];
            }
            return curr;
        }
        
    }


    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        String s = r.readLine();
        String[] parts = s.split(" ");
        int n = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        int q = Integer.parseInt(parts[2]);
        ArrayList<Square>[] addedSquares = new ArrayList[q];
        int[][] field = new int[n][m];
        for (int i = 0; i < q; i++) {
            addedSquares[i] = new ArrayList<>();
            s = r.readLine();
            parts = s.split(" ");
            int x1 = Integer.parseInt(parts[0]) - 1, y1 = Integer.parseInt(parts[1]) - 1;
            int x2 = Integer.parseInt(parts[2]) - 1, y2 = Integer.parseInt(parts[3]) - 1;
            if (x1 == x2) {
                if (y1 > y2) {
                    int temp = y1;
                    y1 = y2;
                    y2 = temp;
                }
                for (int j = y1; j <= y2; j++) {
                    if (field[x1][j] != -1) {
                        field[x1][j] = -1;
                        addedSquares[i].add(new Square(x1, j));
                    }
                }
            } else {
                if (x1 > x2) {
                    int temp = x1;
                    x1 = x2;
                    x2 = temp;
                }
                for (int j = x1; j <= x2; j++) {
                    if (field[j][y1] != -1) {
                        field[j][y1] = -1;
                        addedSquares[i].add(new Square(j, y1));
                    }
                }
            }
        }
        int next = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (field[i][j] == 0) {
                    Queue<Square> squares = new LinkedList<>();
                    field[i][j] = next;
                    squares.add(new Square(i, j));
                    while (!squares.isEmpty()) {
                        Square sq = squares.poll();
                        int x = sq.x;
                        int y = sq.y;
                        if (x != 0) {
                            if (field[x-1][y] == 0) {
                                field[x-1][y] = next;
                                squares.add(new Square(x-1, y));
                            }
                        }
                        if (y != 0) {
                            if (field[x][y-1] == 0) {
                                field[x][y-1] = next;
                                squares.add(new Square(x, y-1));
                            }
                        }
                        if (x != n-1) {
                            if (field[x+1][y] == 0) {
                                field[x+1][y] = next;
                                squares.add(new Square(x+1, y));
                            }
                        }
                        if (y != m-1) {
                            if (field[x][y+1] == 0) {
                                field[x][y+1] = next;
                                squares.add(new Square(x, y+1));
                            }
                        }
                    }
                    next++;
                }
            }
        }
        int[] regions = new int[q];
        regions[q-1] = next-1;
        UnionFind uf = new UnionFind(1000000);
        for (int i = q-1; i > 0; i--) {
            for (Square sq : addedSquares[i]) {             
                int x = sq.x, y = sq.y;
                int[] a = new int[4];
                if (x != 0) {
                    a[0] = field[x-1][y];
                }
                if (y != 0) {
                    a[1] = field[x][y-1];   
                }
                if (x != n-1) {
                    a[2] = field[x+1][y];
                }
                if (y != m-1) {
                    a[3] = field[x][y+1];
                }
                int ind = 4;
                for (int j = 0; j < 4; j++) {
                    if (a[j] > 0) {
                        ind = j;
                        break;
                    }
                }
                for (int j = ind + 1; j < 4; j++) {
                    if (a[j] > 0) {
                        uf.union(a[ind], a[j]);
                    }
                }
                if (ind == 4) {
                    field[x][y] = next;
                    next++;
                } else {
                    field[x][y] = uf.find(a[ind]);
                }               
            }
            regions[i-1] = next - uf.count - 1;
        }
        for (int i = 0; i < q; i++) {
            System.out.println(regions[i]);
        }
    }

}
