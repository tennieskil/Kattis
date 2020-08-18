import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class GolfBot {
    
    
    static class Complex {
        double real;
        double imag;
        public Complex(double r, double i) {
            this.real = r;
            this.imag = i;
        }
        
        public String toString() {
            return real + " + " + imag + "i";
        }
        
        void mult(Complex b) {
            double r = real*b.real - imag*b.imag;
            double im = real*b.imag + imag*b.real;
            real = r; imag = im;
        }
    }
    
    static Complex add(Complex a, Complex b) {
        double real = a.real + b.real;
        double imag = a.imag + b.imag;
        return new Complex(real, imag);
    }
    
    static Complex multiply(Complex a, Complex b) {
        double real = a.real*b.real - a.imag*b.imag;
        double imag = a.real*b.imag + a.imag*b.real;
        return new Complex(real, imag);
    }
    
    static Complex negative(Complex a) {
        return new Complex(-a.real, -a.imag);
    }
    
    
    public static Complex[] fft(Complex[] a, Complex w) {
        return fft(a, w, 0, 1);
    }
    
    public static Complex[] fft(Complex[] a, Complex w, int s, int t) {
        int m = (a.length - s - 1)/t + 1;
        if (m == 1) return new Complex[] { a[s] };
        Complex[] F_even = fft(a, multiply(w, w), s, 2*t);
        Complex[] F_odd = fft(a, multiply(w, w), s+t, 2*t);
        Complex[] F = new Complex[m];
        Complex x = new Complex(1, 0); //w^0
        for (int i = 0; i < m/2; i++) {
            F[i] = add(F_even[i], multiply(x, F_odd[i]));
            F[i+m/2] = add(F_even[i], negative(multiply(x, F_odd[i])));
            x.mult(w);
        }       
        return F;
    }
    
    
    
    public static void main(String[] args) throws IOException {
        int N = 524288;
        int[] a = new int[N];
        a[0] = 1;
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        String s = r.readLine();
        int n = Integer.parseInt(s);
        for (int i = 0; i < n; i++) {
            //int di = scan.nextInt();
            s = r.readLine();
            int di = Integer.parseInt(s);
            a[di] = 1;
        }
        Complex[] x = new Complex[N];
        for (int i = 0; i < N; i++) {
            x[i] = new Complex(a[i], 0);
        }
        double y = 2*Math.PI/N;
        Complex w = new Complex(Math.cos(y), Math.sin(y));
        Complex[] Fx = fft(x, w);
        Complex[] res = new Complex[N];
        for (int i = 0; i < N; i++) {
            res[i] = multiply(Fx[i], Fx[i]);
        }
        Complex invW = new Complex(w.real, -w.imag);
        Complex[] coeffs = fft(res, invW);
        int[] cs = new int[N];
        for (int i = 0; i < N; i++) {
            cs[i] = (int) Math.round(coeffs[i].real/N);
            //System.out.print(i + ":" + cs[i] + " ");
        }
        s = r.readLine();
        int m = Integer.parseInt(s);
        int count = 0;
        for (int i = 0; i < m; i++) {
            s = r.readLine();
            int di = Integer.parseInt(s);
            if (cs[di] > 1) count++;
            else if (cs[di] == 1) {
                if (di % 2 == 1) count++;
                else {
                    int v = (di + N)/2;
                    count += 1 - a[v];
                }
            }
        }
        System.out.println(count);
    }

}