package main.services;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

//import org.bouncycastle.jce.provider.BouncyCastleProvider;
public class RSA {
    private KeyPair keyPair;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    public static RSA  instance;
    public static RSA getInstance() {
        if (instance == null) {
            instance = new RSA();
        }
        return instance;
    }
    public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // Độ dài key (2048 bits là một giá trị thông dụng)
        return keyPairGenerator.generateKeyPair();
    }
    public String encodePublicKey(PublicKey publicKey) {
        byte[] publicKeyBytes = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(publicKeyBytes);
    }
    // Hàm mã hóa Private Key sang chuỗi Base64
    public String encodePrivateKey(PrivateKey privateKey) {
        byte[] privateKeyBytes = privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(privateKeyBytes);
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
    public PublicKey importPublicKey(String publicKeyString) throws Exception {
        try {
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return publicKey = keyFactory.generatePublic(keySpec);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Chuỗi khóa công khai không phải là Base64 hợp lệ: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("Thuật toán RSA không được hỗ trợ: " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            throw new InvalidKeySpecException("Định dạng khóa công khai không hợp lệ: " + e.getMessage());
        }
    }

    // Nhập khóa riêng tư từ chuỗi
    public PrivateKey importPrivateKey(String privateKeyString) throws Exception {
        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return privateKey = keyFactory.generatePrivate(keySpec);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Chuỗi khóa riêng tư không phải là Base64 hợp lệ: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("Thuật toán RSA không được hỗ trợ: " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            throw new InvalidKeySpecException("Định dạng khóa riêng tư không hợp lệ: " + e.getMessage());
        }
    }
    public String decrypt(String ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE,publicKey);
        byte[] output = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(output, StandardCharsets.UTF_8);
    }

    // Tạo chữ ký (sign) thủ công: dùng Private Key để mã hóa hash của dữ liệu
    public String signData(String  data) throws Exception {
        byte[] dataBytes = data.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        System.out.println(" Key sign" + Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] signatureBytes = cipher.doFinal(dataBytes);
        System.out.println("Hassh thành công");
        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    // Xác minh chữ ký (verify) thủ công: dùng Public Key để giải mã chữ ký và so sánh với hash của dữ liệu
    public boolean verifySignature(String dataHashSignature, String encryptHashDataSignature) throws Exception {
        byte[] dataBytes = dataHashSignature.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedSignature = cipher.doFinal(Base64.getDecoder().decode(encryptHashDataSignature));

        //So sánh hash của dữ liệu với chữ ký đã giải mã
        return MessageDigest.isEqual(dataBytes, decryptedSignature);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyPair kp =   RSA.getInstance().generateKeyPair();
        // PublicKey
        PublicKey publicKey = kp.getPublic();
        System.out.println(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        // PrivateKey
        PrivateKey privateKey = kp.getPrivate();
        System.out.println(Base64.getEncoder().encodeToString(privateKey.getEncoded()));

    }
}