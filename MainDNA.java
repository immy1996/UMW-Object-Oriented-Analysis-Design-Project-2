
import java.io.*;
import java.util.*;

/*
 *This project is to to assist a molecular biologist in
 *reconstructing a DNA molecule from multiple strands of 
 *characters representing nucleotide bases. It contains 
 *methods such as sorting, containment, overlap etc.
 * @author Imran
 */
public class MainDNA {

    public static int threshold; // # of threshold

    public static int nSeq; // nSeq = # of items in each of the following data structures

    public static SeqNode[] seqArray; // array of DNA sequences

    public static ArrayList<SeqNode> seqList = new ArrayList<SeqNode>(); // created a seq list to stores elements from SeqNode within it

    public static void printSeqArray(String title) {
       // testing purposes to see where it is a containment for the containment method in the list

        System.out.println();
        System.out.println(title);
        System.out.println();

        for (int i = 0; i < nSeq; i++) {

            if (seqArray[i] == null) {

                System.out.println("seqArray[" + i + "] is null");
            } else {

                System.out.println(seqArray[i].name); // sequence name 
                System.out.println(seqArray[i].seq);  // sequence    
            }
        }
    }

    public static void printSeqList(ArrayList<SeqNode> seqList, String title) {
       // created a list to store and print out the new update name and sequences combined

        System.out.println();
        System.out.println(title);
        System.out.println();

        for (int i = 0; i < seqList.size(); i++) {

            System.out.println(seqList.get(i).name); // sequence name
            System.out.println(seqList.get(i).seq); // sequence
        }
    }

    public static void copyArraytoList() {

        // copy seqArray (skip null elements) to ArrayList<SeqNode> seqList
        for (int i = 0; i < nSeq; i++) {

            if (seqArray[i] != null) {
                seqList.add(seqArray[i]);
            }
        }
    }

    public static void sortSeqArray() {

    	// using bubble sort, sorts sequence array by sequence length
        for (int i = 0; i < nSeq; i++) {

            for (int j = 0; j < nSeq - 1 - i; j++) {

                if (seqArray[j].length > seqArray[j + 1].length) {

                     // after completion of i=0,1,2,3... of the j-for-statement, the i+1 largest elements are in place
                     // example: 5 3 7 4
                    // example: 3 5 7 4
                    // example: 3 5 4 7
                     // example: 3 4 5 7
                     // swap j and j+1 elements
                    SeqNode seqObj = seqArray[j];
                    seqArray[j] = seqArray[j + 1];
                    seqArray[j + 1] = seqObj;
                }
            }
        }
    }

    public static void containmentCheck() {

        // checks for containment relations among the sequences
        for (int i = 0; i < nSeq; i++) {

            for (int j = i + 1; j < nSeq; j++) {

                // seq0, seq1, seq2, ..., seq(N-1)
                //StringBuffer iSeq = new StringBuffer( seqArray[i].seq );
                if (seqArray[j].seq.contains(seqArray[i].seq)) {

                    seqArray[i] = null;
                    break;
                }
            }
        }
    }

    public static boolean contains(ArrayList<SeqNode> seqList, SeqNode seqNode) {
        // checks to see if one seq is equal to another  
        for (int i = 0; i < seqList.size(); i++) {

            if (seqList.get(i).seq.equals(seqNode.seq)) {

                return true;
            }
        }

        return false;
    }

    public static void remove(ArrayList<SeqNode> seqList, SeqNode seqNode) {
        // remove from seqList the unneccesary sequences that weren't combined   
        for (int i = 0; i < seqList.size(); i++) {

            if (seqList.get(i).seq.equals(seqNode.seq)) {

                seqList.remove(seqList.get(i));
            }
        }
    }

    public static boolean overlap() {
        // checks to see if there's an overlap between two strings at a time
        boolean keepWorking = false;

        ArrayList<SeqNode> deleteList = new ArrayList<SeqNode>();
        // seq0, seq1, seq2, seq3, ...

        for (int i = 0; i < seqList.size() - 1; i++) {

            for (int j = i + 1; j < seqList.size(); j++) {

                // check for overlaps
                for (int k = threshold; k < Math.min(seqList.get(i).seq.length(), seqList.get(j).seq.length()); k++) {

                    String LHOfSeqI = seqList.get(i).seq.substring(0, k);

                    String RHOfSeqI = seqList.get(i).seq.substring(seqList.get(i).length - k, seqList.get(i).length);

                    if (seqList.get(j).seq.endsWith(LHOfSeqI)) {

                        // RH of seq j is merged with LH of seq i
                        String mergedName;
                        String RHOfNameJ = seqList.get(j).name.substring(seqList.get(j).name.length() - 2, seqList.get(j).name.length());
                        String LHOfNameI = seqList.get(i).name.substring(0, 2);

                        if (RHOfNameJ.equals(LHOfNameI)) {

                            mergedName = seqList.get(j).name + seqList.get(i).name.substring(2);
                        } else {

                            mergedName = seqList.get(j).name + seqList.get(i).name;
                        }

                        String mergedSeq = seqList.get(j).seq + seqList.get(i).seq.substring(k, seqList.get(i).length);

                        SeqNode mergedSeqNode = new SeqNode(mergedName, mergedSeq);

                        if (!contains(seqList, mergedSeqNode)) {
                            seqList.add(mergedSeqNode);
                        }

                        System.out.println("Success!");
                        System.out.println(mergedName + ": " + mergedSeq);

                        if (!contains(deleteList, seqList.get(i))) {
                            deleteList.add(seqList.get(i));
                        }
                        if (!contains(deleteList, seqList.get(j))) {
                            deleteList.add(seqList.get(j));
                        }
                    }

                    if (seqList.get(j).seq.startsWith(RHOfSeqI)) {

                        // LH of seq j is merged with RH of seq i
                        String mergedName;
                        String RHOfNameI = seqList.get(i).name.substring(seqList.get(i).name.length() - 2, seqList.get(i).name.length());
                        String LHOfNameJ = seqList.get(j).name.substring(0, 2);

                        if (RHOfNameI.equals(LHOfNameJ)) {

                            mergedName = seqList.get(i).name + seqList.get(j).name.substring(2);
                        } else {

                            mergedName = seqList.get(i).name + seqList.get(j).name;
                        }

                        String mergedSeq = seqList.get(i).seq + seqList.get(j).seq.substring(k, seqList.get(j).length);

                        SeqNode mergedSeqNode = new SeqNode(mergedName, mergedSeq);

                        if (!contains(seqList, mergedSeqNode)) {
                            seqList.add(mergedSeqNode);
                        }

                        System.out.println("Success!");
                        System.out.println(mergedName + ": " + mergedSeq);
                        System.out.println();

                        if (!contains(deleteList, seqList.get(i))) {
                            deleteList.add(seqList.get(i));
                        }
                        if (!contains(deleteList, seqList.get(j))) {
                            deleteList.add(seqList.get(j));
                        }
                    }
                }
            }
        }

        for (int i = 0; i < deleteList.size(); i++) {

            if (contains(seqList, deleteList.get(i))) {

                remove(seqList, deleteList.get(i));
            }
        }

        if (deleteList.size() > 0) {
            keepWorking = true; // if an overlap occurred, keep processing
        }
        return keepWorking;
    }

    /**
     * @param FileReader
     * @param SeqNode
     * @param printSeqList
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {

       // read user values from stdin
        Scanner stdin = new Scanner(System.in);

        System.out.print("Please enter threshold: ");

        threshold = stdin.nextInt(); //stores the number of threshold into threshold

        System.out.print("Please enter input filename: ");

        String filename = stdin.next();

       // read DNA sequence data from file
        Scanner inputFile = new Scanner(new FileReader(filename));

        nSeq = inputFile.nextInt(); //reads first line of text file and store it into nSeq

        seqArray = new SeqNode[nSeq]; //store nSeq into my seqArray

        for (int i = 0; i < nSeq; i++) { //in for loop, it stores the dna label and sequence to seqArray

            String name = inputFile.next(); // read next token, use next() to exclude newline
            String seq = inputFile.next();

            seqArray[i] = new SeqNode(name, seq);
        }

        sortSeqArray(); //sort sequence array by length

        containmentCheck(); //check for containment of all strings in text file to see if it is a substring within a string

        copyArraytoList(); //put all of my elements into my arraylist

        while (overlap()) { // while an overlap occurs, keep processing

            printSeqList(seqList, "Sequence list in while-loop:");
            System.out.println();
        }

        if (seqList.size() == 1) {

           // success
            System.out.println();
            System.out.println("Final success: Final sequence list has just one sequence!");

            printSeqList(seqList, "Final sequence list:");
            System.out.println();
        } else {

           // failure
            System.out.println();
            System.out.println("Final failure: Final sequence list has more than one sequence");

            printSeqList(seqList, "Final sequence list:");
        }

        inputFile.close(); // done
    }
}
