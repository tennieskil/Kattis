import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Sequences {

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        String s = r.readLine();
        long[] dp = new long[s.length()];
        long[] ones = new long[s.length()];
        int index = 0;
        long qm = 1;
        while (index < s.length() && s.charAt(index) == '0') {
            index++;
        }
        if (index < s.length()) {
            if (s.charAt(index) == '?') {
                qm = 2;
            }
            ones[index] = 1;
        }
        long mod = 1000000007;
        for (int i = index+1; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                dp[i] = dp[i-1] + ones[i-1];
                dp[i] %= mod;
                ones[i] = ones[i-1];
            } else if (s.charAt(i) == '1') {
                dp[i] = dp[i-1];
                ones[i] = ones[i-1] + qm;
                ones[i] %= mod;
            } else if (s.charAt(i) == '?') {
                dp[i] = 2*dp[i-1] + ones[i-1];
                dp[i] %= mod;
                ones[i] = 2*ones[i-1] + qm;
                ones[i] %= mod;
                qm *= 2;
                qm %= mod;
            }
        }           
        System.out.println(dp[s.length()-1]);
    }

}