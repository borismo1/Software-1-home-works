
public class ShiftByOneEncDecModule implements ByteDecryptor,ByteEncryptor {

	ShiftByOneEncDecModule(){
		
	}


	@Override
	public int encryptByte(int b) {
		return ((b+1) % 256);
	}

	@Override
	public int decryptByte(int b) {
		return ((b-1) % 256);
	}
	
}
