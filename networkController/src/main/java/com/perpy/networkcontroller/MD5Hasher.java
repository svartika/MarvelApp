package com.perpy.networkcontroller;

public class MD5Hasher implements Hasher {
    @Override
    public String hashcode(String value) {
        /*byte[] messageDigest = getMD5Digest(value);
        String md5 = new BigInteger(1, messageDigest).toString(16);*/
        return "";
        //return "0" * (32 - md5.length()) + md5;
    }

    /*byte[] getMD5Digest(String str) {
        try {
            MessageDigest.getInstance("MD5").digest(str.getBytes());
        } catch(NoSuchAlgorithmException e) {
            Log.e("VartikaRx", e.getMessage());
        }
    }*/
}
