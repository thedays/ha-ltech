package com.ltech.nfc.utils;

import android.nfc.tech.NfcV;
import android.util.Log;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class NfcVUtil {
    private String AFI;
    private String DSFID;
    private byte[] ID;
    private String UID;
    private int blockNumber = 1024;
    private byte[] infoRmation;
    private NfcV mNfcV;
    private int oneBlockSize;

    public NfcVUtil(NfcV nfcV) throws IOException {
        this.mNfcV = nfcV;
        byte[] id = nfcV.getTag().getId();
        this.ID = id;
        byte[] bArr = new byte[id.length];
        int i = 0;
        for (int length = id.length - 1; length >= 0; length--) {
            bArr[i] = this.ID[length];
            i++;
        }
        this.UID = printHexString(bArr);
        getInfoRmation();
    }

    public String getUID() {
        return this.UID;
    }

    private byte[] getInfoRmation() throws IOException {
        byte[] bArr = new byte[10];
        bArr[0] = 34;
        bArr[1] = 43;
        byte[] bArr2 = this.ID;
        System.arraycopy(bArr2, 0, bArr, 2, bArr2.length);
        byte[] transceive = this.mNfcV.transceive(bArr);
        this.infoRmation = transceive;
        if (transceive.length > 13) {
            this.blockNumber = transceive[12];
            this.oneBlockSize = transceive[13];
        }
        this.AFI = printHexString(new byte[]{transceive[11]});
        this.DSFID = printHexString(new byte[]{this.infoRmation[10]});
        return this.infoRmation;
    }

    public String getDSFID() {
        return this.DSFID;
    }

    public String getAFI() {
        return this.AFI;
    }

    public int getBlockNumber() {
        return this.blockNumber + 1;
    }

    public int getOneBlockSize() {
        return this.oneBlockSize + 1;
    }

    public String readOneBlock(int i) throws IOException {
        byte[] bArr = new byte[11];
        bArr[0] = 34;
        bArr[1] = 32;
        byte[] bArr2 = this.ID;
        System.arraycopy(bArr2, 0, bArr, 2, bArr2.length);
        bArr[10] = (byte) i;
        byte[] transceive = this.mNfcV.transceive(bArr);
        if (transceive[0] != 0) {
            return null;
        }
        byte[] bArr3 = new byte[transceive.length - 1];
        System.arraycopy(transceive, 1, bArr3, 0, transceive.length - 1);
        return printHexString(bArr3);
    }

    public String readMultiBlock(int i, int i2) throws IOException {
        byte[] bArr = new byte[12];
        bArr[0] = 34;
        bArr[1] = BinaryMemcacheOpcodes.GATK;
        byte[] bArr2 = this.ID;
        System.arraycopy(bArr2, 0, bArr, 2, bArr2.length);
        bArr[10] = (byte) i;
        bArr[11] = (byte) i2;
        byte[] transceive = this.mNfcV.transceive(bArr);
        if (transceive[0] != 0) {
            return null;
        }
        byte[] bArr3 = new byte[transceive.length - 1];
        System.arraycopy(transceive, 1, bArr3, 0, transceive.length - 1);
        return printHexString(bArr3);
    }

    public String readBlocks(int i, int i2) throws IOException {
        int i3 = i + i2;
        int i4 = this.blockNumber;
        if (i3 > i4) {
            i2 = i4 - i;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i5 = i; i5 < i2 + i; i5 += 20) {
            stringBuffer.append(readMultiBlock(i5, 19));
        }
        return stringBuffer.toString();
    }

    private String printHexString(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            stringBuffer.append(hexString);
        }
        return stringBuffer.toString();
    }

    public boolean writeOneBlock(int i, byte[] bArr) throws IOException {
        byte[] bArr2 = new byte[15];
        bArr2[0] = 34;
        bArr2[1] = BinaryMemcacheOpcodes.SASL_AUTH;
        byte[] bArr3 = this.ID;
        System.arraycopy(bArr3, 0, bArr2, 2, bArr3.length);
        bArr2[10] = (byte) i;
        System.arraycopy(bArr, 0, bArr2, 11, bArr.length);
        return this.mNfcV.transceive(bArr2)[0] == 0;
    }

    public boolean writeExtendedMultiLtBlock(int i, int i2, byte[] bArr) throws IOException {
        byte[] bArr2 = new byte[bArr.length + 14];
        bArr2[0] = 34;
        bArr2[1] = 52;
        byte[] bArr3 = this.ID;
        System.arraycopy(bArr3, 0, bArr2, 2, bArr3.length);
        bArr2[10] = (byte) (i & 255);
        bArr2[11] = (byte) (i >> 8);
        bArr2[12] = (byte) (i2 & 255);
        bArr2[13] = (byte) (i2 >> 8);
        System.arraycopy(bArr, 0, bArr2, 14, bArr.length);
        return this.mNfcV.transceive(bArr2)[0] == 0;
    }

    public boolean writeBlocks(int i, int i2, byte[] bArr) throws IOException {
        int i3 = i2 % 4;
        int i4 = i2 / 4;
        if (i3 != 0) {
            i4++;
        }
        for (int i5 = 0; i5 < i4; i5++) {
            int i6 = i5 * 16;
            byte[] copyOfRange = Arrays.copyOfRange(bArr, i6, Math.min(bArr.length, i6 + 16));
            if (copyOfRange.length < 16) {
                byte[] bArr2 = new byte[16];
                System.arraycopy(copyOfRange, 0, bArr2, 0, copyOfRange.length);
                copyOfRange = bArr2;
            }
            Log.e(getClass().getSimpleName(), "NFC WRITE 11:" + DataUtil.formatHexString(copyOfRange));
            for (int i7 = 0; i7 < copyOfRange.length; i7++) {
                if (i7 % 2 == 0) {
                    copyOfRange[i7] = (byte) (copyOfRange[i7] ^ (-91));
                } else {
                    copyOfRange[i7] = (byte) (copyOfRange[i7] ^ (-74));
                }
            }
            if (!writeExtendedMultiLtBlock((i5 * 4) + i, 3, copyOfRange)) {
                return false;
            }
        }
        return true;
    }

    public String readLtOneBlock(int i) throws IOException {
        byte[] bArr = new byte[11];
        bArr[0] = 34;
        bArr[1] = 32;
        byte[] bArr2 = this.ID;
        System.arraycopy(bArr2, 0, bArr, 2, bArr2.length);
        bArr[10] = (byte) i;
        byte[] transceive = this.mNfcV.transceive(bArr);
        if (transceive[0] != 0) {
            return null;
        }
        int length = transceive.length - 1;
        byte[] bArr3 = new byte[length];
        System.arraycopy(transceive, 1, bArr3, 0, transceive.length - 1);
        for (int i2 = 0; i2 < length; i2++) {
            if (i2 % 2 == 0) {
                bArr3[i2] = (byte) (bArr3[i2] ^ (-91));
            } else {
                bArr3[i2] = (byte) (bArr3[i2] ^ (-74));
            }
        }
        return printHexString(bArr3);
    }

    public boolean writeLtOneBlock(int i, byte[] bArr) throws IOException {
        byte[] bArr2 = new byte[15];
        bArr2[0] = 34;
        bArr2[1] = BinaryMemcacheOpcodes.SASL_AUTH;
        byte[] bArr3 = this.ID;
        System.arraycopy(bArr3, 0, bArr2, 2, bArr3.length);
        bArr2[10] = (byte) i;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if (i2 % 2 == 0) {
                bArr[i2] = (byte) (bArr[i2] ^ (-91));
            } else {
                bArr[i2] = (byte) (bArr[i2] ^ (-74));
            }
        }
        System.arraycopy(bArr, 0, bArr2, 11, bArr.length);
        return this.mNfcV.transceive(bArr2)[0] == 0;
    }

    public byte[] getPassword(int i) {
        byte[] bArr = this.ID;
        byte[] bArr2 = new byte[bArr.length];
        if (i == 1) {
            int i2 = 0;
            for (int length = bArr.length - 1; length >= 0; length--) {
                bArr2[i2] = (byte) (this.ID[length] ^ NfcHelper.PWD_BASE[i2]);
                i2++;
            }
            return bArr2;
        }
        return NfcHelper.PWD_BASE;
    }

    public boolean checkPassword(int i) throws IOException {
        byte[] bArr = new byte[20];
        bArr[0] = 34;
        bArr[1] = -77;
        bArr[2] = 2;
        byte[] bArr2 = this.ID;
        System.arraycopy(bArr2, 0, bArr, 3, bArr2.length);
        bArr[11] = (byte) i;
        byte[] password = getPassword(i);
        System.arraycopy(password, 0, bArr, 12, password.length);
        if (this.mNfcV.transceive(bArr)[0] == 0) {
            return true;
        }
        if (i == 1) {
            return checkPassword(2);
        }
        return false;
    }
}