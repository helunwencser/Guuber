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

    private String userName;
    private transient String passWord;
    private String userType;
    private String emailAddress;
    private String gender;
    private String carId;

    public User(
            String userName,
            String passWord,
            String userType,
            String emailAddress,
            String gender,
            String carId
    ) {
        this.userName = userName;
        this.passWord = passWord;
        this.userType = userType;
        this.emailAddress = emailAddress;
        this.gender = gender;
        this.carId = carId;
    }

<<<<<<< HEAD

=======
>>>>>>> f4be038ac09c7f4171862690675e10ccda0c8435
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

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
}
