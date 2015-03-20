
public class ShiftByXEncDecModule implements ByteDecryptor,ByteEncryptor {
	
	private int secret;
	
	ShiftByXEncDecModule(int x){
		this.secret = x;
	}

	@Override
	public int encryptByte(int b) {
		int temp = ((b + this.secret) % 256) ;
		return temp; 
	}

	@Override
	public int decryptByte(int b) {
		int temp = ((b - this.secret) % 256);
		return temp;
	}
}
