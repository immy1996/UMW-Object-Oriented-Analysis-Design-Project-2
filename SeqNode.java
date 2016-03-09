/*
*This class helps to gain the 
*dna name,sequence, and length
*of sequence.
 */

/**
 *
 * @author Imran
 */
public class SeqNode extends MainDNA {
	
    public String name;
    public String seq;
    public int length;

    public SeqNode( String name, String seq ) {
    	
        this.name = name;
        this.seq = seq;
        
        length = this.seq.length();
    }
}
