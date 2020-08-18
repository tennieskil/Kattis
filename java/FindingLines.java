import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Random;

public class FindingLines {

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        String s = r.readLine();
        int n = Integer.parseInt(s);
        s = r.readLine();
        int p = Integer.parseInt(s);
        int[][] points = new int[n][2];
        for (int i = 0; i < n; i++) {
            s = r.readLine();
            String[] parts = s.split(" ");
            points[i][0] = Integer.parseInt(parts[0]);
            points[i][1] = Integer.parseInt(parts[1]);
        }
        if (n < 3) { System.out.println("possible"); System.exit(0); }
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < 5000; i++) {
            int a = rand.nextInt(n);
            int b = rand.nextInt(n);
            if (a == b) { continue; }
            long xa = points[a][0], ya = points[a][1];
            long xb = points[b][0], yb = points[b][1];
            if (xa == xb) {
                int count = 0;
                for (int j = 0; j < n; j++) {
                    long x = points[j][0];
                    if (x == xa) {
                        count++;
                    }
                }
                if (n*p <= count*100) {
                    System.out.println("possible");
                    System.exit(0);
                }
            }
            long kt = ya - yb, kn = xa - xb;
            long m = ya*kn - kt*xa;
            int count = 0;
            for (int j = 0; j < n; j++) {
                long x = points[j][0], y = points[j][1];
                if (y*kn == kt*x + m) {
                    count++;
                }
            }
            if (n*p <= count*100) {
                System.out.println("possible");
                System.exit(0);
            }
        }
        System.out.println("impossible");
    }

}