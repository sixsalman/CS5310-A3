Find a minimum cost edit sequence to transform string X = x1 x2 ... xn into Y = y1 y2 ... ym
using edit operations of I (Insert), D (Delete) and C (Change).

Compute the matrix and cost for the strings X = aabaababaa and Y = babaabab.
Note: Use the costs of 0.1, 0.2 and 0.3 for I (Insert), D (Delete) and C (Change), respectively.

The algorithm should compute the cost table, and list the final cost(n,m),
and a version of it should also generate the decision sequence.
In the algorithm, C(xi,yj) is applied with cost C if xi is not equal to yj;
if xi equals yj then the cost C(xi,yj) = 0.

Test your programs using the example in the notes with costs 1, 1 and 2 for I, D and C,
respectively, and run with costs 0.1, 0.2 and 0.3 for the test files.
Read in your test data from a file.

Show final cost(n,m) for all tests; show input sequences, matrix and
decision sequence only when the input sequences are of length <= 100.
