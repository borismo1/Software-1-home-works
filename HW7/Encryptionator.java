import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Encryptionator {

	public static void main (String[] args) {

	try {
			System.out.println ("Encryptionator initiating...");			
			DummyEncDecModule dummyEncDec = new DummyEncDecModule();
			ShiftByOneEncDecModule shiftByOneEncDec = new ShiftByOneEncDecModule();
			ShiftByXEncDecModule shiftByXEncDec = new ShiftByXEncDecModule(77);
			KeyFileEncDecModule keyFileEncDec = new KeyFileEncDecModule("keyFile1.dat");

			ByteEncryptor[] subEncryptors = {shiftByOneEncDec, shiftByXEncDec, keyFileEncDec};
			ByteDecryptor[] subDecryptors = {shiftByOneEncDec, shiftByXEncDec, keyFileEncDec};		
			ComplexEncDecModule complexEncDec = new ComplexEncDecModule(subEncryptors, subDecryptors);

			System.out.println ("Encrypting files...");
			encryptFile ("in1.txt", "in1_dummy.xxx",dummyEncDec );
			encryptFile ("in1.txt", "in1_shiftByOne.xxx",shiftByOneEncDec );
			encryptFile ("in2.txt", "in2_shiftByX.xxx",shiftByXEncDec );
			encryptFile ("in2.txt", "in2_keyFile.xxx",keyFileEncDec );
			encryptFile ("in2.txt", "in2_complex.xxx",complexEncDec );

			System.out.println ("Decrypting files...");
			decryptFile ("in1_dummy.xxx","in1_dummy.txt", dummyEncDec );
			decryptFile ("in1_shiftByOne.xxx","in1_shiftByOne.txt",shiftByOneEncDec );
			decryptFile ("in2_shiftByX.xxx","in2_shiftByX.txt",shiftByXEncDec );
			decryptFile ("in2_keyFile.xxx","in2_keyFile.txt",keyFileEncDec );
			decryptFile ("in2_complex.xxx","in2_complex.txt",complexEncDec );

			System.out.println ("Done !");

		} catch (IOException e) {
			System.out.println ("IO error encountered while processing files.");
			e.printStackTrace();
		}
	}


	public static void encryptFile (String inputFilename, String outputFilename, ByteEncryptor encryptor) throws IOException  {
		File fromfile = new File(inputFilename);
		File tofile = new File(outputFilename);
		FileInputStream fis = new FileInputStream(fromfile);
		FileOutputStream fos = new FileOutputStream(tofile);
		BufferedInputStream data_source = new BufferedInputStream(fis);
		BufferedOutputStream data_dump = new BufferedOutputStream(fos);
		int temp;
		while((temp = data_source.read()) != -1){
			temp = encryptor.encryptByte(temp);
			data_dump.write(temp);
		}
		data_source.close();
		data_dump.close();
		
	}

	public static void decryptFile (String inputFilename, String outputFilename, ByteDecryptor decryptor) throws IOException {
		File fromfile = new File(inputFilename);
		File tofile = new File(outputFilename);
		FileInputStream fis = new FileInputStream(fromfile);
		FileOutputStream fos = new FileOutputStream(tofile);
		BufferedInputStream data_source = new BufferedInputStream(fis);
		BufferedOutputStream data_dump = new BufferedOutputStream(fos);
		int temp;
		while((temp = data_source.read()) !=-1){
			temp = decryptor.decryptByte(temp);
			data_dump.write(temp);
		}
		data_source.close();
		data_dump.close();
	}

}
