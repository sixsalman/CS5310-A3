import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This program converts one string into another by editing it. It calculates and prints the cost matrix as well as
 * an optimal decision sequence to perform the conversion.
 * It reads from 5 test files, each of which contains 2 lines. The first one is the string to convert and the second
 * one is the string to achieve. Matrices are displayed only when length of both strings is <= 100 characters
 *
 * @author M. Salman Khan
 */
public class AssignmentThree {

    public static void main(String[] args) {

        System.out.println("Test your programs using the example in the notes with costs 1, 1 and 2 for I, D and C, " +
                "respectively:\n");

        String from = "aabab";
        String to = "babb";

        double insertCost = 1;
        double deleteCost = 1;
        double changeCost = 2;

        System.out.println("Version 1 Output: ");
        calcAndPrint(from, to, insertCost, deleteCost, changeCost, true, false);

        System.out.println("\nVersion 2 Output: ");
        calcAndPrint(from, to, insertCost, deleteCost, changeCost, true, true);

        System.out.println("\n\nRun with costs 0.1, 0.2 and 0.3 for the test files:");

        insertCost = 0.1;
        deleteCost = 0.2;
        changeCost = 0.3;

        for (int i = 0; i < 5; i++) {
            try {
                Scanner inFile = new Scanner(new File(String.format("testFile%d.txt", i + 1)));

                from = inFile.nextLine();
                to = inFile.nextLine();
            } catch (FileNotFoundException e) {
                System.out.printf("\nError: A required file (testFile%d.txt) was not found at expected location.\n",
                        i + 1);
                continue;
            }


            boolean printMatrix = from.length() <= 100 && to.length() <= 100;

            System.out.printf("\nTest File %d:\n\n", i + 1);

            System.out.println("Version 1 Output: ");
            calcAndPrint(from, to, insertCost, deleteCost, changeCost, printMatrix, false);

            System.out.println("\nVersion 2 Output: ");
            calcAndPrint(from, to, insertCost, deleteCost, changeCost, printMatrix, printMatrix);
        }

    }

    /**
     * This method calculates cost matrix to convert 'from' string to 'to' string using the received costs.
     *
     * @param from the string to convert
     * @param to the string to achieve after editing
     * @param insertCost cost to insert a character
     * @param deleteCost cost to delete a character
     * @param changeCost cost to change a character
     * @param printDecisionSeq if true, operations are printed alongside costs and decision sequence is shown
     * @param printMatrix if true, prints cost matrix; otherwise only prints final cost
     */
    private static void calcAndPrint(String from, String to, double insertCost, double deleteCost, double changeCost,
                                     boolean printMatrix, boolean printDecisionSeq) {
        double[][] costMatrix = new double[from.length() + 1][to.length() + 1];
        char[][] operationMatrix = new char[from.length() + 1][to.length() + 1];

        if (printMatrix) System.out.println("Cost Table:");

        if (printMatrix) {
            if (printDecisionSeq) {
                System.out.printf("%6c\t%6c\t", ' ', ' ');
                for (int i = 0; i < to.length(); i++) System.out.printf("%6c\t", to.charAt(i));
            } else {
                System.out.printf("%4c\t%4c\t", ' ', ' ');
                for (int i = 0; i < to.length(); i++) System.out.printf("%4c\t", to.charAt(i));
            }
            System.out.print("\n");
        }

        for (int i = 0; i < costMatrix.length; i++) {
            if (printMatrix) {
                if (printDecisionSeq) {
                    if (i == 0) {
                        System.out.printf("%6c\t", ' ');
                    } else {
                        System.out.printf("%6c\t", from.charAt(i - 1));
                    }
                } else {
                    if (i == 0) {
                        System.out.printf("%4c\t", ' ');
                    } else {
                        System.out.printf("%4c\t", from.charAt(i - 1));
                    }
                }
            }

            for (int j = 0; j < costMatrix[i].length; j++) {
                if (i == 0 && j == 0) {
                    costMatrix[i][j] = 0;
                    operationMatrix[i][j] = '-';
                } else if (j == 0) {
                    costMatrix[i][j] = costMatrix[i - 1][0] + deleteCost;
                    operationMatrix[i][j] = 'D';
                } else if (i == 0) {
                    costMatrix[i][j] = costMatrix[0][j - 1] + insertCost;
                    operationMatrix[i][j] = 'I';
                } else {
                    double[] values = new double[3];

                    values[0] = costMatrix[i - 1][j] + deleteCost;

                    values[1] = from.charAt(i - 1) == to.charAt(j - 1) ? costMatrix[i - 1][j - 1] :
                            costMatrix[i - 1][j - 1] + changeCost;

                    values[2] = costMatrix[i][j - 1] + insertCost;

                    if (values[0] <= values[1] && values[0] <= values[2]) {
                        costMatrix[i][j] = values[0];
                        operationMatrix[i][j] = 'D';
                    } else if (values[1] <= values[0] && values[1] <= values[2]) {
                        costMatrix[i][j] = values[1];
                        operationMatrix[i][j] = 'C';
                    } else {
                        costMatrix[i][j] = values[2];
                        operationMatrix[i][j] = 'I';
                    }
                }

                if (printMatrix) {
                    if (printDecisionSeq) {
                        System.out.printf("%4.1f/%c\t", costMatrix[i][j], operationMatrix[i][j]);
                    } else {
                        System.out.printf("%4.1f\t", costMatrix[i][j]);
                    }
                }
            }

            if (printMatrix) System.out.print("\n");
        }

        if (printMatrix) System.out.print("\n");

        System.out.printf("Final Cost: %.1f\n", costMatrix[costMatrix.length - 1][costMatrix[0].length - 1]);

        if (printDecisionSeq) {
            System.out.println("\nDecision Sequence:");

            int indOne = costMatrix.length - 1;
            int indTwo = costMatrix[0].length - 1;
            ArrayList<String> revDecisionSeq = new ArrayList<>();

            while (true) {
                revDecisionSeq.add(String.format("%c\t%.1f", operationMatrix[indOne][indTwo],
                        costMatrix[indOne][indTwo]));

                if (operationMatrix[indOne][indTwo] == 'I') {
                    if (indTwo != 0) indTwo--;
                } else if (operationMatrix[indOne][indTwo] == 'D') {
                    if (indOne != 0) indOne--;
                } else if (operationMatrix[indOne][indTwo] == 'C') {
                    if (indOne != 0) indOne--;
                    if (indTwo != 0) indTwo--;
                } else {
                    break;
                }
            }

            for (int i = revDecisionSeq.size() - 1; i >= 0; i--) System.out.println(revDecisionSeq.get(i));
        }
    }

}