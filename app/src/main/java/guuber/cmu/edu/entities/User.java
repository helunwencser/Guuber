package guuber.cmu.edu.entities;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * Created by wangziming on 4/9/16.
 */
public class User implements Serializable{

    private static final long serialVersionUID = 2016010101L;//cant find how to generate it in android studio



    private static KeyGenerator keyGen;
    private static SecretKey desKey;
    private static Cipher cipher;



    private int userID;
    private String userName;
    private transient String passWord;
    private byte[] cipherByte;
    private String userType;
    private String emailAddress;
    private String gender;
    private int carId;



    static {
        desKey =  keyGen.generateKey();
    }


    static {
        try {
            keyGen = KeyGenerator.getInstance("DESede");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
             cipher = Cipher.getInstance("DESede");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    public User(int userID, String userName, String passWord, String userType, String emailAddress, String gender, int carId) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        this.userID = userID;
        this.userName = userName;
        this.passWord = passWord;
        this.userType = userType;
        this.emailAddress = emailAddress;
        this.gender = gender;
        this.carId = carId;
        this.cipherByte = encrytor(passWord);
    }



    private byte[] encrytor(String str) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {

        cipher.init(Cipher.ENCRYPT_MODE, desKey);
        byte[] src = str.getBytes();
        cipherByte = cipher.doFinal(src);
        return cipherByte;
    }


    private byte[] decryptor(byte[] buff) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {

        cipher.init(Cipher.DECRYPT_MODE, desKey);
        cipherByte = cipher.doFinal(buff);
        return cipherByte;
    }





    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }
}
