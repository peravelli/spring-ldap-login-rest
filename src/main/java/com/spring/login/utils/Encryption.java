package com.spring.login.utils;

import com.netflix.config.DynamicPropertyFactory;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.properties.PropertyValueEncryptionUtils;

public class Encryption {

    public static String getProperty(String propertyName, String defaultValue) {
        String propertyValue = DynamicPropertyFactory.getInstance().getStringProperty(propertyName, defaultValue).get();
        if (PropertyValueEncryptionUtils.isEncryptedValue(propertyValue)) {
            propertyValue = jasyptDecrypt(propertyValue);
        }
        return propertyValue;
    }

    private static String jasyptDecrypt(String encryptedValue) {
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm(Constants.ENCRYPT_ALGORITHM);
        config.setPassword(getProperty(Constants.APP_SEC_PSSWD, null));
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setConfig(config);
        return PropertyValueEncryptionUtils.decrypt(encryptedValue, encryptor);
    }

    public static String encryptValue(String valueToEncrypt) {
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm(Constants.ENCRYPT_ALGORITHM);
        config.setPassword(getProperty(Constants.APP_SEC_PSSWD, null));
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setConfig(config);
        return PropertyValueEncryptionUtils.encrypt(valueToEncrypt, encryptor);
    }


}
