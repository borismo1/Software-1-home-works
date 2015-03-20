
public class ComplexEncDecModule implements ByteDecryptor,ByteEncryptor {
	private ByteDecryptor[] dec;
	private int dec_count =0;
	private ByteEncryptor[] enc;
	private int enc_count =0;

	ComplexEncDecModule(ByteEncryptor[] byteEncryptors, ByteDecryptor[] byteDecryptors){
		this.dec = byteDecryptors;
		this.enc = byteEncryptors;
	}

	@Override
	public int encryptByte(int b) {
		int temp = enc[enc_count % enc.length].encryptByte(b) % 256;
		enc_count++;
		return temp;
	}

	@Override
	public int decryptByte(int b) {
		int temp = dec[dec_count % dec.length].decryptByte(b) % 256;
		dec_count++;
		return temp;
	}

}
