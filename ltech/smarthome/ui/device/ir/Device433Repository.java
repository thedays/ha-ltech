package com.ltech.smarthome.ui.device.ir;

import android.content.Context;
import com.airoha.libfota.constant.ErrorCodeManager;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.SignedBytes;
import com.ltech.smarthome.R;
import com.smart.message.utils.StringUtils;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/* loaded from: classes4.dex */
public class Device433Repository {
    private static int DUYA_CODE_NUM = 2;
    public static final String IR_AIRER_KEY_NAME_DISINFECT = "IR_AIRER_KEY_NAME_DISINFECT";
    public static final String IR_AIRER_KEY_NAME_DOWN = "IR_AIRER_KEY_NAME_DOWN";
    public static final String IR_AIRER_KEY_NAME_FIRE_DRY = "IR_AIRER_KEY_NAME_FIRE_DRY";
    public static final String IR_AIRER_KEY_NAME_LET_GO = "IR_AIRER_KEY_NAME_LET_GO";
    public static final String IR_AIRER_KEY_NAME_LIGHTING = "IR_AIRER_KEY_NAME_LIGHTING";
    public static final String IR_AIRER_KEY_NAME_MUSIC = "IR_AIRER_KEY_NAME_MUSIC";
    public static final String IR_AIRER_KEY_NAME_STOP = "IR_AIRER_KEY_NAME_STOP";
    public static final String IR_AIRER_KEY_NAME_UP = "IR_AIRER_KEY_NAME_UP";
    public static final String IR_AIRER_KEY_NAME_WAKE_UP = "IR_AIRER_KEY_NAME_WAKE_UP";
    public static final String IR_AIRER_KEY_NAME_WIND_DRY = "IR_AIRER_KEY_NAME_WIND_DRY";
    public static final String IR_MOTOR_KEY_NAME_CONFIG = "IR_MOTOR_KEY_NAME_CONFIG";
    public static final String IR_MOTOR_KEY_NAME_DOWN = "IR_MOTOR_KEY_NAME_DOWN";
    public static final String IR_MOTOR_KEY_NAME_STOP = "IR_MOTOR_KEY_NAME_STOP";
    public static final String IR_MOTOR_KEY_NAME_UP = "IR_MOTOR_KEY_NAME_UP";
    private int duyaCodeIndex = 0;
    private String idString;

    public boolean isChangeLast() {
        return this.duyaCodeIndex >= DUYA_CODE_NUM;
    }

    public MotorCodeLib getCodeLib(Context context, String brandName, boolean isBle) {
        if (brandName.equals(context.getString(R.string.motor_duya))) {
            return isBle ? createDuYaCodeLibBle() : createDuYaCodeLib();
        }
        if (brandName.equals(context.getString(R.string.motor_baizhen))) {
            return isBle ? createBaiZhenCodeLibBle() : createDuYaCodeLib();
        }
        if (brandName.equals(context.getString(R.string.motor_sien))) {
            return isBle ? createSiEnCodeLibBle() : createDuYaCodeLib();
        }
        if (brandName.equals(context.getString(R.string.motor_lansen))) {
            return isBle ? createLanSenCodeLibBle() : createLanSenCodeLib();
        }
        if (brandName.equals(context.getString(R.string.motor_aoke))) {
            return isBle ? createAoKeCodeLibBle() : createAoKeCodeLib();
        }
        if (brandName.equals(context.getString(R.string.motor_idemo))) {
            return isBle ? createIDemoEnCodeLibBle() : createAoKeCodeLib();
        }
        if (brandName.equalsIgnoreCase(context.getString(R.string.hanger_lbest))) {
            return isBle ? createLBestCodeLibBle() : createLBestCodeLib();
        }
        if (brandName.equalsIgnoreCase(context.getString(R.string.motor_chuangming))) {
            return isBle ? createChMingCodeLibBle() : createChMingCodeLib();
        }
        return null;
    }

    private MotorCodeLib createDuYaCodeLibBle() {
        if (this.duyaCodeIndex >= DUYA_CODE_NUM) {
            this.duyaCodeIndex = 0;
        }
        byte[] createRandomId = createRandomId(3);
        String[] strArr = {IR_MOTOR_KEY_NAME_CONFIG, IR_MOTOR_KEY_NAME_UP, IR_MOTOR_KEY_NAME_STOP, IR_MOTOR_KEY_NAME_DOWN};
        byte[] bArr = {-52, 1, 0, 2};
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < 4; i++) {
            arrayList2.clear();
            arrayList2.addAll(Bytes.asList((byte) (this.duyaCodeIndex == 0 ? 3 : 4)));
            arrayList2.addAll(Bytes.asList(createRandomId));
            arrayList2.add(Byte.valueOf(bArr[i]));
            SingleKey singleKey = new SingleKey();
            singleKey.setName(strArr[i]);
            singleKey.setCode(Bytes.toArray(arrayList2));
            arrayList.add(singleKey);
        }
        this.duyaCodeIndex++;
        return new MotorCodeLib(arrayList);
    }

    private MotorCodeLib createDuYaCodeLib() {
        byte[] bArr = {1, 1, 2, 55, 10, 70, BinaryMemcacheOpcodes.GATK, 5};
        byte[] bArr2 = {1, 1, 2, 48, 14, 70, BinaryMemcacheOpcodes.GATK, 5};
        byte[] bArr3 = {0, 100};
        byte[] bArr4 = {-52, 17, ErrorCodeManager.RES_DEVICE_NO_RESET_AFTER_OTA, 51};
        byte[] createRandomId = createRandomId(4);
        if (this.duyaCodeIndex >= DUYA_CODE_NUM) {
            this.duyaCodeIndex = 0;
        }
        String[] strArr = {IR_MOTOR_KEY_NAME_CONFIG, IR_MOTOR_KEY_NAME_UP, IR_MOTOR_KEY_NAME_STOP, IR_MOTOR_KEY_NAME_DOWN};
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < 4; i++) {
            arrayList2.clear();
            arrayList2.addAll(Bytes.asList(this.duyaCodeIndex == 0 ? bArr : bArr2));
            arrayList2.addAll(Bytes.asList(createRandomId));
            arrayList2.add(Byte.valueOf(bArr4[i]));
            arrayList2.addAll(Bytes.asList(bArr3));
            SingleKey singleKey = new SingleKey();
            singleKey.setName(strArr[i]);
            singleKey.setCode(Bytes.toArray(arrayList2));
            arrayList.add(singleKey);
        }
        this.duyaCodeIndex++;
        return new MotorCodeLib(arrayList);
    }

    private MotorCodeLib createLanSenCodeLibBle() {
        byte[] createRandomId = createRandomId(3);
        String[] strArr = {IR_MOTOR_KEY_NAME_CONFIG, IR_MOTOR_KEY_NAME_UP, IR_MOTOR_KEY_NAME_STOP, IR_MOTOR_KEY_NAME_DOWN};
        byte[] bArr = {-52, 1, 0, 2};
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < 4; i++) {
            arrayList2.clear();
            arrayList2.addAll(Bytes.asList(5));
            arrayList2.addAll(Bytes.asList(createRandomId));
            arrayList2.add(Byte.valueOf(bArr[i]));
            SingleKey singleKey = new SingleKey();
            singleKey.setName(strArr[i]);
            singleKey.setCode(Bytes.toArray(arrayList2));
            arrayList.add(singleKey);
        }
        return new MotorCodeLib(arrayList);
    }

    private MotorCodeLib createSiEnCodeLibBle() {
        byte[] createRandomId = createRandomId(3);
        String[] strArr = {IR_MOTOR_KEY_NAME_CONFIG, IR_MOTOR_KEY_NAME_UP, IR_MOTOR_KEY_NAME_STOP, IR_MOTOR_KEY_NAME_DOWN};
        byte[] bArr = {-52, 1, 0, 2};
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < 4; i++) {
            arrayList2.clear();
            arrayList2.addAll(Bytes.asList(7));
            arrayList2.addAll(Bytes.asList(createRandomId));
            arrayList2.add(Byte.valueOf(bArr[i]));
            SingleKey singleKey = new SingleKey();
            singleKey.setName(strArr[i]);
            singleKey.setCode(Bytes.toArray(arrayList2));
            arrayList.add(singleKey);
        }
        return new MotorCodeLib(arrayList);
    }

    private MotorCodeLib createIDemoEnCodeLibBle() {
        byte[] createRandomId = createRandomId(3);
        String[] strArr = {IR_MOTOR_KEY_NAME_CONFIG, IR_MOTOR_KEY_NAME_UP, IR_MOTOR_KEY_NAME_STOP, IR_MOTOR_KEY_NAME_DOWN};
        byte[] bArr = {-52, 1, 0, 2};
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < 4; i++) {
            arrayList2.clear();
            arrayList2.addAll(Bytes.asList(6));
            arrayList2.addAll(Bytes.asList(createRandomId));
            arrayList2.add(Byte.valueOf(bArr[i]));
            SingleKey singleKey = new SingleKey();
            singleKey.setName(strArr[i]);
            singleKey.setCode(Bytes.toArray(arrayList2));
            arrayList.add(singleKey);
        }
        return new MotorCodeLib(arrayList);
    }

    private MotorCodeLib createBaiZhenCodeLibBle() {
        byte[] createRandomId = createRandomId(3);
        String[] strArr = {IR_MOTOR_KEY_NAME_CONFIG, IR_MOTOR_KEY_NAME_UP, IR_MOTOR_KEY_NAME_STOP, IR_MOTOR_KEY_NAME_DOWN};
        byte[] bArr = {-52, 1, 0, 2};
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < 4; i++) {
            arrayList2.clear();
            arrayList2.addAll(Bytes.asList(2));
            arrayList2.addAll(Bytes.asList(createRandomId));
            arrayList2.add(Byte.valueOf(bArr[i]));
            SingleKey singleKey = new SingleKey();
            singleKey.setName(strArr[i]);
            singleKey.setCode(Bytes.toArray(arrayList2));
            arrayList.add(singleKey);
        }
        return new MotorCodeLib(arrayList);
    }

    private MotorCodeLib createLanSenCodeLib() {
        byte[] bArr = {2, 1, 65, 65, 65, 95, 4};
        byte[] bArr2 = {1, 2, 6, 60};
        byte[] bArr3 = {81, BinaryMemcacheOpcodes.SASL_AUTH, 1, 17};
        byte[] createRandomId = createRandomId(2);
        String[] strArr = {IR_MOTOR_KEY_NAME_CONFIG, IR_MOTOR_KEY_NAME_UP, IR_MOTOR_KEY_NAME_STOP, IR_MOTOR_KEY_NAME_DOWN};
        byte[] bArr4 = new byte[createRandomId.length + 1];
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < 4; i++) {
            Arrays.fill(bArr4, (byte) 0);
            System.arraycopy(createRandomId, 0, bArr4, 0, createRandomId.length);
            System.arraycopy(bArr3, i, bArr4, createRandomId.length, 1);
            arrayList2.clear();
            arrayList2.addAll(Bytes.asList(bArr));
            arrayList2.addAll(Bytes.asList(bArr4));
            arrayList2.add(Byte.valueOf(checkSum(bArr4)));
            arrayList2.addAll(Bytes.asList(bArr2));
            SingleKey singleKey = new SingleKey();
            singleKey.setName(strArr[i]);
            singleKey.setCode(Bytes.toArray(arrayList2));
            arrayList.add(singleKey);
        }
        return new MotorCodeLib(arrayList);
    }

    private MotorCodeLib createAoKeCodeLibBle() {
        byte[] createRandomId = createRandomId(3);
        String[] strArr = {IR_MOTOR_KEY_NAME_CONFIG, IR_MOTOR_KEY_NAME_UP, IR_MOTOR_KEY_NAME_STOP, IR_MOTOR_KEY_NAME_DOWN};
        byte[] bArr = {-52, 1, 0, 2};
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < 4; i++) {
            arrayList2.clear();
            arrayList2.addAll(Bytes.asList(1));
            arrayList2.addAll(Bytes.asList(createRandomId));
            arrayList2.add(Byte.valueOf(bArr[i]));
            SingleKey singleKey = new SingleKey();
            singleKey.setName(strArr[i]);
            singleKey.setCode(Bytes.toArray(arrayList2));
            arrayList.add(singleKey);
        }
        return new MotorCodeLib(arrayList);
    }

    private MotorCodeLib createAoKeCodeLib() {
        byte[] bArr = {1, 1, 2, 50, 6, 60, 30, 8};
        byte[] bArr2 = {0, 0};
        byte[] bArr3 = {11, 11, BinaryMemcacheOpcodes.GATK, 67};
        byte[] createRandomId = createRandomId(3);
        String[] strArr = {IR_MOTOR_KEY_NAME_CONFIG, IR_MOTOR_KEY_NAME_UP, IR_MOTOR_KEY_NAME_STOP, IR_MOTOR_KEY_NAME_DOWN};
        byte[] bArr4 = new byte[createRandomId.length + 3];
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < 4; i++) {
            Arrays.fill(bArr4, (byte) 0);
            System.arraycopy(createRandomId, 0, bArr4, 0, createRandomId.length);
            bArr4[createRandomId.length] = 1;
            bArr4[createRandomId.length + 1] = 0;
            System.arraycopy(bArr3, i, bArr4, createRandomId.length + 2, 1);
            arrayList2.clear();
            arrayList2.addAll(Bytes.asList(bArr));
            arrayList2.add((byte) -93);
            arrayList2.addAll(Bytes.asList(bArr4));
            arrayList2.add(Byte.valueOf(checkSum(bArr4)));
            arrayList2.addAll(Bytes.asList(bArr2));
            SingleKey singleKey = new SingleKey();
            singleKey.setName(strArr[i]);
            singleKey.setCode(Bytes.toArray(arrayList2));
            arrayList.add(singleKey);
        }
        return new MotorCodeLib(arrayList);
    }

    private MotorCodeLib createLBestCodeLibBle() {
        byte[] createRandomId = createRandomId(3);
        String[] strArr = {IR_AIRER_KEY_NAME_UP, IR_AIRER_KEY_NAME_DOWN, IR_AIRER_KEY_NAME_STOP, IR_AIRER_KEY_NAME_WAKE_UP, IR_AIRER_KEY_NAME_LIGHTING, IR_AIRER_KEY_NAME_DISINFECT, IR_AIRER_KEY_NAME_WIND_DRY, IR_AIRER_KEY_NAME_FIRE_DRY, IR_AIRER_KEY_NAME_MUSIC, IR_AIRER_KEY_NAME_LET_GO};
        byte[] bArr = {1, 3, 2, 4, 5, 6, 7, 8, 9, 10};
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < 10; i++) {
            arrayList2.clear();
            arrayList2.addAll(Bytes.asList(9));
            arrayList2.addAll(Bytes.asList(createRandomId));
            arrayList2.add(Byte.valueOf(bArr[i]));
            SingleKey singleKey = new SingleKey();
            singleKey.setName(strArr[i]);
            singleKey.setCode(Bytes.toArray(arrayList2));
            arrayList.add(singleKey);
        }
        return new MotorCodeLib(arrayList);
    }

    private MotorCodeLib createLBestCodeLib() {
        byte[] bArr = {6, 1, 2, 50, 6, 60, 20, 8};
        byte[] bArr2 = {0, 0};
        byte[] createRandomId = createRandomId(3);
        byte[][] bArr3 = {new byte[]{2, 0, 11}, new byte[]{2, 0, 67}, new byte[]{2, 0, BinaryMemcacheOpcodes.GATK}, new byte[]{2, 0, 80}, new byte[]{1, 0, 11}, new byte[]{1, 0, BinaryMemcacheOpcodes.GATK}, new byte[]{1, 0, 67}, new byte[]{1, 0, 83}, new byte[]{1, 0, 96}, new byte[]{1, 0, BinaryMemcacheOpcodes.GATKQ}};
        String[] strArr = {IR_AIRER_KEY_NAME_UP, IR_AIRER_KEY_NAME_DOWN, IR_AIRER_KEY_NAME_STOP, IR_AIRER_KEY_NAME_WAKE_UP, IR_AIRER_KEY_NAME_LIGHTING, IR_AIRER_KEY_NAME_DISINFECT, IR_AIRER_KEY_NAME_WIND_DRY, IR_AIRER_KEY_NAME_FIRE_DRY, IR_AIRER_KEY_NAME_MUSIC, IR_AIRER_KEY_NAME_LET_GO};
        byte[] bArr4 = new byte[createRandomId.length + bArr3[0].length];
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < 10; i++) {
            Arrays.fill(bArr4, (byte) 0);
            System.arraycopy(createRandomId, 0, bArr4, 0, createRandomId.length);
            byte[] bArr5 = bArr3[i];
            System.arraycopy(bArr5, 0, bArr4, createRandomId.length, bArr5.length);
            arrayList2.clear();
            arrayList2.addAll(Bytes.asList(bArr));
            arrayList2.add((byte) -93);
            arrayList2.addAll(Bytes.asList(bArr4));
            arrayList2.add(Byte.valueOf(checkSum(bArr4)));
            arrayList2.addAll(Bytes.asList(bArr2));
            SingleKey singleKey = new SingleKey();
            singleKey.setName(strArr[i]);
            singleKey.setCode(Bytes.toArray(arrayList2));
            arrayList.add(singleKey);
        }
        return new MotorCodeLib(arrayList);
    }

    private MotorCodeLib createChMingCodeLibBle() {
        byte[] createRandomId = createRandomId(3);
        String[] strArr = {IR_MOTOR_KEY_NAME_CONFIG, IR_MOTOR_KEY_NAME_UP, IR_MOTOR_KEY_NAME_STOP, IR_MOTOR_KEY_NAME_DOWN};
        byte[] bArr = {-52, 1, 0, 2};
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < 4; i++) {
            arrayList2.clear();
            arrayList2.addAll(Bytes.asList(8));
            arrayList2.addAll(Bytes.asList(createRandomId));
            arrayList2.add(Byte.valueOf(bArr[i]));
            SingleKey singleKey = new SingleKey();
            singleKey.setName(strArr[i]);
            singleKey.setCode(Bytes.toArray(arrayList2));
            arrayList.add(singleKey);
        }
        return new MotorCodeLib(arrayList);
    }

    private MotorCodeLib createChMingCodeLib() {
        byte[] bArr = {3, 1, 2, 25, 25, SignedBytes.MAX_POWER_OF_TWO, SignedBytes.MAX_POWER_OF_TWO, 4};
        byte[] bArr2 = {0, 100};
        byte[] bArr3 = {Byte.MIN_VALUE, 1, 2, 3};
        byte[] createRandomId = createRandomId(3);
        String[] strArr = {IR_MOTOR_KEY_NAME_CONFIG, IR_MOTOR_KEY_NAME_UP, IR_MOTOR_KEY_NAME_STOP, IR_MOTOR_KEY_NAME_DOWN};
        byte[] bArr4 = new byte[createRandomId.length + 1];
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < 4; i++) {
            Arrays.fill(bArr4, (byte) 0);
            System.arraycopy(createRandomId, 0, bArr4, 0, createRandomId.length);
            bArr4[createRandomId.length] = bArr3[i];
            arrayList2.clear();
            arrayList2.addAll(Bytes.asList(bArr));
            arrayList2.addAll(Bytes.asList(bArr4));
            arrayList2.add(Byte.valueOf(checkSum(bArr4)));
            arrayList2.addAll(Bytes.asList(bArr2));
            SingleKey singleKey = new SingleKey();
            singleKey.setName(strArr[i]);
            singleKey.setCode(Bytes.toArray(arrayList2));
            arrayList.add(singleKey);
        }
        return new MotorCodeLib(arrayList);
    }

    private byte checkSum(byte... codes) {
        int i = 0;
        for (int i2 : codes) {
            if (i2 < 0) {
                i2 &= 255;
            }
            i += i2;
        }
        return (byte) i;
    }

    private byte[] createRandomId(int length) {
        byte[] bArr = new byte[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            bArr[i] = (byte) random.nextInt(255);
        }
        this.idString = StringUtils.byte2Str(bArr);
        return bArr;
    }

    public String getIdString() {
        return this.idString;
    }

    public static final class SingleKey {
        private byte[] code;
        private String name;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public byte[] getCode() {
            return this.code;
        }

        public void setCode(byte[] code) {
            this.code = code;
        }
    }

    public static final class MotorCodeLib {
        private List<SingleKey> singleKeyList;

        public MotorCodeLib(List<SingleKey> singleKeyList) {
            this.singleKeyList = singleKeyList;
        }

        public byte[] getCodeByKey(String key) {
            for (SingleKey singleKey : this.singleKeyList) {
                if (singleKey.getName().equals(key)) {
                    return singleKey.getCode();
                }
            }
            return new byte[0];
        }
    }

    public static MotorKeyItem getMotorKeyItem(String key) {
        key.hashCode();
        switch (key) {
            case "IR_AIRER_KEY_NAME_WIND_DRY":
                return new MotorKeyItem(R.mipmap.icon_ir_wind_type, R.string.hanger_wind, IR_AIRER_KEY_NAME_WIND_DRY);
            case "IR_AIRER_KEY_NAME_FIRE_DRY":
                return new MotorKeyItem(R.mipmap.icon_airdrying, R.string.hanger_dry, IR_AIRER_KEY_NAME_FIRE_DRY);
            case "IR_AIRER_KEY_NAME_UP":
                return new MotorKeyItem(R.mipmap.icon_rise, R.string.hanger_rise, IR_AIRER_KEY_NAME_UP);
            case "IR_MOTOR_KEY_NAME_DOWN":
                return new MotorKeyItem(R.mipmap.icon_close, R.string.close, IR_MOTOR_KEY_NAME_DOWN);
            case "IR_MOTOR_KEY_NAME_STOP":
                return new MotorKeyItem(R.mipmap.icon_stop, R.string.pause_curtain, IR_MOTOR_KEY_NAME_STOP);
            case "IR_AIRER_KEY_NAME_LIGHTING":
                return new MotorKeyItem(R.mipmap.icon_lighting, R.string.hanger_light, IR_AIRER_KEY_NAME_LIGHTING);
            case "IR_AIRER_KEY_NAME_DOWN":
                return new MotorKeyItem(R.mipmap.icon_decline, R.string.hanger_down, IR_AIRER_KEY_NAME_DOWN);
            case "IR_AIRER_KEY_NAME_STOP":
                return new MotorKeyItem(R.mipmap.icon_stop, R.string.hanger_pause, IR_AIRER_KEY_NAME_STOP);
            case "IR_MOTOR_KEY_NAME_UP":
                return new MotorKeyItem(R.mipmap.icon_open, R.string.open_curtain, IR_MOTOR_KEY_NAME_UP);
            case "IR_AIRER_KEY_NAME_DISINFECT":
                return new MotorKeyItem(R.mipmap.icon_disinfect, R.string.hanger_disnfect, IR_AIRER_KEY_NAME_DISINFECT);
            default:
                return null;
        }
    }

    public static Object[][] getCurtainLibKey() {
        return new Object[][]{new Object[]{"开", IR_MOTOR_KEY_NAME_UP}, new Object[]{"关", IR_MOTOR_KEY_NAME_DOWN}, new Object[]{"停止", IR_MOTOR_KEY_NAME_STOP}};
    }
}