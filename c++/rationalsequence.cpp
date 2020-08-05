#include <iostream>
#include <sstream>

using namespace std;

int main()
{
    int N;
    cin >> N;
    ostringstream op;
    for (int i = 0; i < N; ++i) {
        unsigned long a,b,c;
        char skip;
        cin >> a >> b >> skip >> c;
        
        if (c == 1) {
            c = b+1;
            b = 1;
        } else {
            int levels = b/c;
            b %= c;
            c -= b;
            b += c;
            c += levels*b;
        }
        op << a << " " << b << "/" << c << endl;
    }
    cout << op.str();
    char m;
    cin >> m;
    return 0;
}