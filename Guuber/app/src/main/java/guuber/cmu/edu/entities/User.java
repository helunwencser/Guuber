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

    private String username;
    private transient String passWord;
    private String userType;
    private String email;
    private String gender;
    private String carId;

    public User(
            String username,
            String passWord,
            String userType,
            String email,
            String gender,
            String carId
    ) {
        this.username = username;
        this.passWord = passWord;
        this.userType = userType;
        this.email = email;
        this.gender = gender;
        this.carId = carId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        return email;
    }

    public void setEmailAddress(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
}
