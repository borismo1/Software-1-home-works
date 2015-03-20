import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class KeyFileEncDecModule implements ByteDecryptor,ByteEncryptor{

	private byte[] secret;
	private int enc_counter =0;
	private int dec_counter =0;
	
	KeyFileEncDecModule (String keyFilename) throws IOException{
		this.secret = Files.readAllBytes(Paths.get(keyFilename));
		
	}
	
	@Override
	public int encryptByte(int b) {
		int temp = b + secret[enc_counter % secret.length] % 256;
		enc_counter++;
		return temp;
	}

	@Override
	public int decryptByte(int b) {
		int temp = b - secret[dec_counter % secret.length] % 256;
		dec_counter++;
		return temp;
	}

}
