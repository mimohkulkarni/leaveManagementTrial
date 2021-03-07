package com.mimoh.leavemanagementtrial;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class SecurePreferences {

    public static class SecurePreferencesException extends RuntimeException {

        SecurePreferencesException(Throwable e) {
            super(e);
        }
    }

    private final Cipher writer;
    private final Cipher reader;
    private final Cipher keyWriter;
    private final SharedPreferences preferences;

    /**
     * This will initialize an instance of the SecurePreferences class
     * @param context your current context.
//     * @param preferenceName name of preferences file (preferenceName.xml)
//     * @param secureKey the key used for encryption, finding a good key scheme is hard.
     * Hardcoding your key in the application is bad, but better than plaintext preferences. Having the user enter the key upon application launch is a safe(r) alternative, but annoying to the user.
//     * @param encryptKeys settings this to false will only encrypt the values,
     * true will encrypt both values and keys. Keys can contain a lot of information about
     * the plaintext value of the value which can be used to decipher the value.
//     * @throws SecurePreferencesException
     */
    @SuppressLint("GetInstance")
    public SecurePreferences(Context context) throws SecurePreferencesException {
        try {
            this.writer = Cipher.getInstance(Constant.TRANSFORMATION);
            this.reader = Cipher.getInstance(Constant.TRANSFORMATION);
            this.keyWriter = Cipher.getInstance(Constant.KEY_TRANSFORMATION);

            initCiphers();

            this.preferences = context.getSharedPreferences("perinfo", Context.MODE_PRIVATE);
        }
        catch (Exception e) {
            throw new SecurePreferencesException(e);
        }
    }

    private void initCiphers() throws Exception {
        IvParameterSpec ivSpec = getIv();
        SecretKeySpec secretKey = getSecretKey();

        writer.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        reader.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        keyWriter.init(Cipher.ENCRYPT_MODE, secretKey);
    }

    private IvParameterSpec getIv() {
        byte[] iv = new byte[writer.getBlockSize()];
        System.arraycopy("fldsjfodasjifudslfjdsaofshaufihadsf".getBytes(), 0, iv, 0, writer.getBlockSize());
        return new IvParameterSpec(iv);
    }

    private SecretKeySpec getSecretKey() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] keyBytes = createKeyBytes();
        return new SecretKeySpec(keyBytes, Constant.TRANSFORMATION);
    }

    private byte[] createKeyBytes() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(Constant.SECRET_KEY_HASH_TRANSFORMATION);
        md.reset();
        return md.digest(Constant.SECURE_KEY.getBytes(Constant.CHARSET));
    }

    public void put(String key, String value) {
        if (value == null) {
            preferences.edit().remove(toKey(key)).apply();
        }
        else {
            putValue(toKey(key), value);
        }
    }

    public String getString(String key, String defValue) throws SecurePreferencesException {
        if (preferences.contains(toKey(key))) {
            String securedEncodedValue = preferences.getString(toKey(key), defValue);
            return decrypt(securedEncodedValue);
        }
        return defValue;
    }

    public void clear() {
        preferences.edit().clear().apply();
    }

    private String toKey(String key) {
        return encrypt(key, keyWriter);
    }

    private void putValue(String key, String value) throws SecurePreferencesException {
        String secureValueEncoded = encrypt(value, writer);

        preferences.edit().putString(key, secureValueEncoded).apply();
    }

    private String encrypt(String value, Cipher writer) throws SecurePreferencesException {
        byte[] secureValue;
        try {
            secureValue = convert(writer, value.getBytes(Constant.CHARSET));
        }
        catch (UnsupportedEncodingException e) {
            throw new SecurePreferencesException(e);
        }
        return Base64.encodeToString(secureValue, Base64.NO_WRAP);
    }

    private String decrypt(String securedEncodedValue) {
        byte[] securedValue = Base64.decode(securedEncodedValue, Base64.NO_WRAP);
        byte[] value = convert(reader, securedValue);
        try {
            return new String(value, Constant.CHARSET);
        }
        catch (UnsupportedEncodingException e) {
            throw new SecurePreferencesException(e);
        }
    }

    private static byte[] convert(Cipher cipher, byte[] bs) throws SecurePreferencesException {
        try {
            return cipher.doFinal(bs);
        }
        catch (Exception e) {
            throw new SecurePreferencesException(e);
        }
    }

    public static boolean isNetworkConnected(Application application) {
        final ConnectivityManager cm = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            if (Build.VERSION.SDK_INT < 23) {
                final NetworkInfo ni = cm.getActiveNetworkInfo();

                if (ni != null) {
                    return (ni.isConnected() && (ni.getType() == ConnectivityManager.TYPE_WIFI || ni.getType() == ConnectivityManager.TYPE_MOBILE));
                }
            } else {
                final Network n = cm.getActiveNetwork();

                if (n != null) {
                    final NetworkCapabilities nc = cm.getNetworkCapabilities(n);

                    return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
                }
            }
        }

        return false;
    }
}