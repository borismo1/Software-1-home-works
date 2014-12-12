package il.ac.tau.cs.sw1.bioinformatics;

import java.awt.image.SampleModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import org.apache.commons.math3.stat.inference.TestUtils;

/**
 * 
 * Gene Expression Analyzer
 * 
 * Command line arguments: 
 * args[0] - GeoDatasetName: Gene expression dataset name (expects a corresponding input file in SOFT format to exist in the local directory). 
 * args[1] - LabelType: Specifies type of sample labels. Only labels of this type will be parsed from the input file.
 * args[2] - Label1: Label of the first sample subset.
 * args[3] - Label2: Label of the second sample subset 
 * args[4] - Alpha: T-test confidence level : only genes with pValue below this threshold will be printed to output
 * file
 * 
 * Execution example: GeneExpressionAnalyzer GDS4085 "estrogen receptor-negative" "estrogen receptor-positive" 0.01
 * 
 * @author software1-2014
 *
 */
public class GeneExpressionAnalyzer {

	public static void main(String args[]) throws IOException {

		// Reads the dataset from a SOFT input file
		String inputSoftFileName = args[0] + ".soft"; 
		String labelType = args[1];
		GeneExpressionDataset geneExpressionDataset = parseGeneExpressionFile(inputSoftFileName, labelType);
		System.out.printf("Gene expression dataset loaded from file %s. %n", inputSoftFileName);
		System.out.printf("Dataset contains %d samples and %d gene probes.%n", geneExpressionDataset.samplesNumber, geneExpressionDataset.genesNumber);
		System.out.printf("Dataset contains %d label subsets of type [%s]: %s%n%n", geneExpressionDataset.labelSubsetNumber, labelType, new HashSet<String>(Arrays.asList(geneExpressionDataset.labels)));

		// Removes low variance genes which are usually not informative. Keeping only the top 50% variable genes. 
		float varianceFractionThreshold = 0.5f;
		int numberOfGenesToKeep = (int) Math.floor(geneExpressionDataset.genesNumber * varianceFractionThreshold);
		float varianceThreshold = applyVarianceFilterOnDataset(geneExpressionDataset, numberOfGenesToKeep);
		System.out.printf("Applying variance filter on dataset: Kept %d top variable genes having variance of at least %.2f.%n", geneExpressionDataset.genesNumber, varianceThreshold);

		// Identifies differentially expressed genes between two sample groups
		// and writes the results to a text file
		String label1 = args[2];
		String label2 = args[3];
		double alpha = Double.parseDouble(args[4]);
		String diffGenesFileName = args[0] + "-DiffGenes.txt";
		int numOfDiffGenes = writeTopDifferentiallyExpressedGenesToFile(diffGenesFileName, geneExpressionDataset, alpha, label1, label2);
		System.out.printf("%d differentially expressed genes identified using alpha of %.5f when comparing the two sample groups [%s] and [%s].%n",
				numOfDiffGenes, alpha, label1, label2);
		System.out.printf("Results saved to file %s.%n", diffGenesFileName);
	}

	/**
	 * 
	 * parseGeneExpressionFile - parses the given SOFT file
	 * 
	 * @param filename
	 *            A gene expression file in SOFT format
	 *  @param labelType
	 *             Only labels of this type will be included in the analysis
	 * @return a GeneExpressionDataset object storing all data parsed from the
	 *         input file
	 * @throws IOException
	 */
	public static GeneExpressionDataset parseGeneExpressionFile(String filename, String labelType) throws IOException {
		GeneExpressionDataset dataset = new GeneExpressionDataset();
		String chunk = file_to_string(filename);
		dataset.datasetTitle = extract_title(chunk);
		dataset.samplesNumber = extract_samples_number(chunk);
		dataset.genesNumber = extract_genes_number(chunk);
		dataset.sampleIds = extract_sampleid(chunk);
		String[][] geneinfo = extract_gene_info(chunk);
		dataset.geneIds =geneinfo[0];
		dataset.geneSymbols = geneinfo[1];
		dataset.dataMatrix = extract_data_matrix(chunk);
		dataset.labelSubsetNumber = extract_lable_subset_number(chunk,labelType);
		dataset.labels = extract_lables(extract_lable_names(chunk,labelType), extract_subset_IDlists(chunk,labelType), dataset.sampleIds);
		return dataset;
	}
	
	// reads the file and returns a huge string 
	
	public static String file_to_string(String filename) throws IOException{
		byte[] allbytes = Files.readAllBytes(Paths.get(filename));
		String main_chunk = new String(allbytes);
		return main_chunk;
	}
	
	//extracts dataset title from String
	
	public static String extract_title(String chunk){
		String title = chunk.split("!dataset_title")[1].split("\\n")[0].split("=")[1];
		return title.substring(1);
	}
	
	//extract sample number from string
	
	public static int extract_samples_number(String chunk){
		String sample_count = chunk.split("!dataset_sample_count")[1].split("\\n")[0].split("=")[1];
		return Integer.parseInt(sample_count.substring(1));
	}
	
	//extracts genes number from string
	
	public static int extract_genes_number(String chunk){
		String sample_count = chunk.split("!dataset_feature_count")[1].split("\\n")[0].split("=")[1];
		return Integer.parseInt(sample_count.substring(1));
	}
	
	//extracts sampleID from file
	
	public static String[] extract_sampleid(String chunk){
		String[] sampleid = chunk.split("!dataset_table_begin")[1].split("\\n")[1].split("\\t");
		return Arrays.copyOfRange(sampleid, 2, sampleid.length);
	}
	
	//extracts geneIDs and geneSymbols from string
	
	public static String[][] extract_gene_info(String chunk){
		String data_matrix = chunk.split("!dataset_table_begin")[1].split("!dataset_table_end")[0];
		String[] matrix_lines = data_matrix.split("\\n");
		String[][] gene_info = new String[2][extract_genes_number(chunk)];
		for(int i = 2;i < matrix_lines.length;i++){
			String[] temp = matrix_lines[i].split("\\t");
			gene_info[0][i-2] = temp[0];
			gene_info[1][i-2] = temp[1];
		}
		return gene_info;
	}
	
	//extracts the float matrix values from string
	
	public static float[][] extract_data_matrix(String chunk){
		String data_matrix = chunk.split("!dataset_table_begin")[1].split("!dataset_table_end")[0];
		String[] matrix_lines = data_matrix.split("\\n");
		float[][] data_frame = new float[extract_genes_number(chunk)][extract_samples_number(chunk)];
		for(int i = 2; i < data_frame.length + 2;i++){
			String[] temp = matrix_lines[i].split("\\t");
			for(int j = 2; j < data_frame[1].length + 2;j++){
				data_frame[i-2][j-2] = Float.parseFloat(temp[j]);
			}
		}
		return data_frame;
	}
	
	//extracts labels subset number from string
	
	public static int extract_lable_subset_number(String chunk,String tag){
		String[] temp = chunk.split(tag);
		return temp.length - 1;
	}
	
	//extracts labels names
	
	public static String[] extract_lable_names(String chunk,String tag){
		String[] lable_names = new String[extract_lable_subset_number(chunk,tag)];
		String[] sub_chunk = chunk.split(tag);
		for(int i = 0; i < lable_names.length ; i++){
			String[] temp = sub_chunk[i].split("\n");
			lable_names[i] = temp[temp.length - 3].split("=")[1];
		}
		return lable_names;
	}

	//extracts the sample Id associated with the given lable
	
	public static String[][] extract_subset_IDlists(String chunk,String tag){
		String[][] idarrays = new String[extract_lable_subset_number(chunk,tag)][];
		String[] sub_chunk = chunk.split("!subset_sample_id");
		for(int i=0 ;i < idarrays.length;i++){
			idarrays[i] = sub_chunk[i+1].split("\\n")[0].split(" ")[2].split(",");
		}
		return idarrays;
	}
	
	
	//extracts label array with the help of extract_subset_IDlists
	
	public static String[] extract_lables(String[] lablenames,String[][] idlists,String[] samplids){
		String[] lablelist = new String[samplids.length];
		for(int i=0;i< lablenames.length;i++){
			for(int j=0;j< idlists[i].length ; j++){
				for(int k=0;k< samplids.length;k++){
					if(idlists[i][j].equals(samplids[k])){
						lablelist[k] = lablenames[i].substring(1);
					}
				}
			}
		}
		return lablelist;
	}
	
	
	/**
	 * applyVarianceFilterOnDataset - Filters out low variability genes from the given dataset object
	 * 
	 * 
	 * @param geneExpressionDataset - The dataset object to be processed.
	 * @param numberOfGenesToKeep - number of genes to keep in the dataset object based on variance filtering
	 * @return varianceThreshold - a float indicating the variance threshold value. Only genes having this variance value or higher are kept in the filtered dataset.
	 */
	public static float applyVarianceFilterOnDataset(GeneExpressionDataset geneExpressionDataset, int numberOfGenesToKeep) {
		float[][] submatrix = new float[numberOfGenesToKeep][geneExpressionDataset.samplesNumber];
		float[] variance_array = variance(geneExpressionDataset);
		float thold =threshold(variance_array, numberOfGenesToKeep);
		String[] geneId = new String[numberOfGenesToKeep];
		String[] geneSymbol = new String[numberOfGenesToKeep];
		int counter = 0;
		for(int i=0;i < geneExpressionDataset.genesNumber;i++){
			if(variance_array[i] > thold){
				geneId[counter] = geneExpressionDataset.geneIds[i];
				geneSymbol[counter] = geneExpressionDataset.geneSymbols[i];
				submatrix[counter] = geneExpressionDataset.dataMatrix[i];
				counter++;
			}
		}
		geneExpressionDataset.dataMatrix = submatrix;
		geneExpressionDataset.geneIds =geneId;
		geneExpressionDataset.geneSymbols =geneSymbol;
		geneExpressionDataset.genesNumber = numberOfGenesToKeep;
		return thold;
	}

	 
	//computes the means vector of the given dataset
	
	public static float[] mean(GeneExpressionDataset dataset){
		float[] means_array = new float[dataset.genesNumber];
		float sum;
		for(int i =0; i < dataset.genesNumber;i++){
			sum =0;
			for(int k =0;k < dataset.samplesNumber;k++){
				sum = sum + dataset.dataMatrix[i][k];
			}
			means_array[i] = sum / dataset.samplesNumber;
		}
		return means_array;
	}
	
	//computes the variance vector of the given dataset
	
	public static float[] variance(GeneExpressionDataset dataset){
		float[] variance_array = new float[dataset.genesNumber];
		float[] mean_array = mean(dataset);
		float sum;
		for(int i =0;i < dataset.genesNumber;i++){
			sum =0;
			for(int k=0;k < dataset.samplesNumber;k++){
				sum = (float) (sum + Math.pow((dataset.dataMatrix[i][k] - mean_array[i]),2));
			}
			variance_array[i] = sum / dataset.samplesNumber;
		}
		return variance_array;
	}
	
	//computes the threshold of the variance 
	
	public static float threshold(float[] variance,int num){
		float[] temp = new float[variance.length];
		temp = variance.clone();
		Arrays.sort(temp);
		return temp[variance.length - num];
	}
	
	/**
	 * 
	 * getDataEntriesForLabel
	 * 
	 * Returns the entries in the 'data' array for which the corresponding
	 * entries in the 'labels' array equals 'label'
	 * 
	 * @param data
	 * @param labels
	 * @param label
	 * @return
	 */
	public static double[] getDataEntriesForLabel(float[] data,String[] labels, String label) {
		int counter =0;
		for(int i = 0 ; i < labels.length;i++){
			if (labels[i].equals(label)){
				counter++;
			}
		}
		double[] subarray = new double[counter];
		counter =0;
		for(int i =0 ; i < labels.length;i++){
			if(labels[i].equals(label)){
				subarray[counter] =(Math.floor(data[i]*100000)/1000000);
				counter++;
			}
		}
		return subarray;
	}

	/**
	 * 
	 * writeTopDifferentiallyExpressedGenesToFile
	 * Writes a sorted list of differentially expressed probes to a file.
	 * 
	 * @param outputFilename
	 * @param geneExpressionDataset
	 * @param alpha
	 * @param label1
	 * @param label2
	 * @return numOfDiffGenes The number of differentially expressed genes detected, having p-value lower than alpha
	 * @throws IOException
	 */
	public static int writeTopDifferentiallyExpressedGenesToFile(String outputFilename, GeneExpressionDataset geneExpressionDataset,double alpha, String label1, String label2) throws IOException {
		int numOfDiffGenes = number_low_pvalues(pvalue_array(geneExpressionDataset, label1, label2), alpha);
		float[] pvaluearrey = pvalue_array(geneExpressionDataset, label1, label2);
		int counter =0;
		float[] final_p =new float[numOfDiffGenes];
		String[] g_id = new String[numOfDiffGenes];
		String[] g_sym =new String[numOfDiffGenes];
		for(int i=0;i < geneExpressionDataset.genesNumber;i++){
			if(pvaluearrey[i] < alpha){
				final_p[counter ] =pvaluearrey[i];
				g_id[counter ] = geneExpressionDataset.geneIds[i];
				g_sym[counter ] = geneExpressionDataset.geneSymbols[i];
				counter++;
			}
		}
		File tofile = new File(outputFilename);
		FileWriter fwriter =new FileWriter(tofile);
		Arrays.sort(pvaluearrey);
		pvaluearrey = Arrays.copyOfRange(pvaluearrey, 0, numOfDiffGenes);
		for(int i=0;i< numOfDiffGenes;i++){
			for(int j = 0;j < numOfDiffGenes;j++){
				if(pvaluearrey[i] == final_p[j]){
					fwriter.write((i+1) + "\t" + final_p[j] + "\t" + g_id[j] + "\t" + g_sym[j] + "\n");
				}
			}
		}
		fwriter.close();
		return numOfDiffGenes;
	}
	
	//computes the p-value vector associated with the given dataset 
	
	public static float[]  pvalue_array(GeneExpressionDataset dataset,String label1,String label2){
		float[] output = new float[dataset.genesNumber];
		for(int i=0 ;i< dataset.genesNumber;i++){
			output[i] = (float) calcTtest(dataset, i, label1, label2);
		}
		return output;
	}
	
	//computes the number of p_values that passed the hypothesis test with the given alpha
	
	public static int number_low_pvalues(float[] pvalarray,double alpha){
		int counter =0;
		for(int i=0;i < pvalarray.length;i++){
			if(pvalarray[i] < alpha){
				counter++;
			}
		}
		return counter;
	}

	/**
	 * calcTtest - returns a pValue for the t-Test
	 * 
	 * Returns the p-value, associated with a two-sample, two-tailed t-test
	 * comparing the means of the input arrays
	 * 
	 * //http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/stat/inference/TTest.html#tTest(double[], double[])
	 * 
	 * @param geneExpressionDataset
	 * @param geneIndex
	 * @param label1
	 * @param label2
	 * @return
	 */
	public static double calcTtest(GeneExpressionDataset geneExpressionDataset, int geneIndex,String label1, String label2) {
		double[] sample1 = getDataEntriesForLabel(
				geneExpressionDataset.dataMatrix[geneIndex],
				geneExpressionDataset.labels, label1);
		double[] sample2 = getDataEntriesForLabel(
				geneExpressionDataset.dataMatrix[geneIndex],
				geneExpressionDataset.labels, label2);
		return TestUtils.tTest(sample1, sample2);
	}
}
