package il.ac.tau.cs.sw1.bioinformatics;

/**
 * GeneExpressionDataset
 * A class representing a gene expression dataset
 * 
 * @author software1-2014
 *
 */
public class GeneExpressionDataset {

	public String datasetTitle = "NA";

	public int samplesNumber; // number of dataset samples
	public int genesNumber; // number of dataset gene probes

	public String[] sampleIds; // sample ids
	public String[] geneIds; // gene probe ids
	public String[] geneSymbols; // gene symbols
	public float[][] dataMatrix; // expression data matrix

	public int labelSubsetNumber; // number of subsets which is the number of unique values in the labels field
	public String[] labels; // sample labels
}
