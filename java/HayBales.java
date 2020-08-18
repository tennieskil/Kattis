import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HayBales {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        String sb = r.readLine();
        StringBuilder s = new StringBuilder(sb);        
        int count = -1;
        while (true) {
            count++;
            int i = s.lastIndexOf("PPC");
            if (i != -1) {
                s.setCharAt(i, 'C');
                s.setCharAt(i+2, 'P');
                continue;
            }
            i = s.lastIndexOf("PCC");
            if (i != -1) {
                s.setCharAt(i, 'C');
                s.setCharAt(i+1, 'C');
                s.setCharAt(i+2, 'P');
                continue;
            }
            i = s.lastIndexOf("CPC");
            if (i != -1) {
                s.setCharAt(i+1, 'C');
                s.setCharAt(i+2, 'P');
                continue;
            }
            i = s.lastIndexOf("PPC");
            if (i != -1) {
                s.setCharAt(i, 'C');
                s.setCharAt(i+2, 'P');
                continue;
            }
            break;
        }
        System.out.println(count);
    }

}