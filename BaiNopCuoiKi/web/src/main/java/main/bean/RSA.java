package main.bean;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA {
    private KeyPair keyPair;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    public static RSA instance;

    public static RSA getInstance() {
        if (instance == null) {
            instance = new RSA();
        }
        return instance;
    }

    // Sinh khóa RSA
    public void genKey(int sizeKey) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(sizeKey);
        keyPair = keyPairGenerator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    // Xuất khóa công khai
    public String exportPublicKey() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    // Xuất khóa riêng tư
    public String exportPrivateKey() {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    // Nhập khóa công khai từ chuỗi
    public PublicKey importPublicKey(String publicKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    // Nhập khóa riêng tư từ chuỗi
    public PrivateKey importPrivateKey(String privateKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
    // Tạo chữ ký (sign) thủ công: dùng Private Key để mã hóa hash của dữ liệu
    public String signData(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] signatureBytes = cipher.doFinal(data);
        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    // Xác minh chữ ký (verify) thủ công: dùng Public Key để giải mã chữ ký và so sánh với hash của dữ liệu
    public boolean verifySignature(byte[] data, String signature) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedSignature = cipher.doFinal(Base64.getDecoder().decode(signature));

        //So sánh hash của dữ liệu với chữ ký đã giải mã
        return MessageDigest.isEqual(data, decryptedSignature);
    }
}
