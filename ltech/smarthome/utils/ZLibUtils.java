package com.ltech.smarthome.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/* loaded from: classes4.dex */
public class ZLibUtils {
    public static byte[] compress(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.reset();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
        try {
            try {
                byte[] bArr = new byte[1024];
                while (!deflater.finished()) {
                    byteArrayOutputStream.write(bArr, 0, deflater.deflate(bArr));
                }
                data = byteArrayOutputStream.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
            deflater.end();
            return data;
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void compress(byte[] data, OutputStream os) {
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(os);
        try {
            deflaterOutputStream.write(data, 0, data.length);
            deflaterOutputStream.finish();
            deflaterOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] decompress(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.reset();
        inflater.setInput(data);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
        try {
            try {
                byte[] bArr = new byte[1024];
                while (!inflater.finished()) {
                    byteArrayOutputStream.write(bArr, 0, inflater.inflate(bArr));
                }
                data = byteArrayOutputStream.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
            inflater.end();
            return data;
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static byte[] decompress(InputStream is) {
        InflaterInputStream inflaterInputStream = new InflaterInputStream(is);
        int i = 1024;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        try {
            byte[] bArr = new byte[1024];
            while (true) {
                i = inflaterInputStream.read(bArr, 0, i);
                if (i <= 0) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
}