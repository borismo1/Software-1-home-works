
public class DummyEncDecModule implements ByteEncryptor , ByteDecryptor {

	public DummyEncDecModule(){
	}
	
	@Override
	public int encryptByte(int b) {
		return b;
	}

	@Override
	public int decryptByte(int b) {
		return b;
	}

}
