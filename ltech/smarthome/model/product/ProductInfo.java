package com.ltech.smarthome.model.product;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public class ProductInfo {
    private static final int PRODUCT_BLE = 2;
    private static final int PRODUCT_IR = 4;
    private static final int PRODUCT_WIFI = 1;
    private static final int PRODUCT_WIFI_BLE = 3;
    private int addNameRes;
    private int agreementId;
    private int controlType;
    private int curtainType;
    private int defaultIconRes;
    private int defaultNameRes;
    private String defaultNameStr;
    private String groupKey;
    private String hardwareId;
    private int macFlag;
    private String productId;
    private String productKey;
    private int productType;
    private String subProductKey;

    private ProductInfo() {
    }

    public int getAgreementId() {
        return this.agreementId;
    }

    public String getProductKey() {
        return this.productKey;
    }

    public String getProductId() {
        return this.productId;
    }

    public String getHardwareId() {
        return this.hardwareId;
    }

    public String getDefaultName(Context context) {
        int i = this.defaultNameRes;
        if (i != 0) {
            return context.getString(i);
        }
        return this.defaultNameStr;
    }

    public int getDefaultIconRes() {
        return this.defaultIconRes;
    }

    public boolean isBleProduct() {
        return 2 == this.productType;
    }

    public boolean isIrProduct() {
        return 4 == this.productType;
    }

    public boolean isWifiProduct() {
        return this.productType == 1;
    }

    public boolean isWifiBleProduct() {
        return this.productType == 3;
    }

    public int getMacFlag() {
        return this.macFlag;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public int getControlType() {
        return this.controlType;
    }

    public int getAddNameRes() {
        return this.addNameRes;
    }

    public String getAddName(Context context) {
        return context.getString(this.addNameRes);
    }

    public String toString() {
        return "ProductInfo{productId='" + this.productId + "', hardwareId='" + this.hardwareId + "', agreementId=" + this.agreementId + ", productKey='" + this.productKey + "', subProductKey='" + this.subProductKey + "', defaultNameRes=" + this.defaultNameRes + ", defaultIconRes=" + this.defaultIconRes + ", productType=" + this.productType + ", macFlag=" + this.macFlag + ", groupKey='" + this.groupKey + "', controlType=" + this.controlType + '}';
    }

    public String getSubProductKey() {
        return this.subProductKey;
    }

    public int getCurtainType() {
        return this.curtainType;
    }

    public static final class Builder {
        private int addNameRes;
        private int agreementId;
        private int controlType;
        private int curtainType;
        private int defaultIconRes;
        private int defaultNameRes;
        private String defaultNameStr;
        private String groupKey;
        private String hardwareId = "0";
        private int macFlag;
        private String productId;
        private String productKey;
        private int productType;
        private String subProductKey;

        private Builder(int productType) {
            this.productType = productType;
        }

        public static Builder irProduct() {
            return new Builder(4);
        }

        public static Builder wifiProduct() {
            return new Builder(1);
        }

        public static Builder bleProduct() {
            return new Builder(2);
        }

        public static Builder wifiBleProduct() {
            return new Builder(3);
        }

        public Builder productId(String productId) {
            this.productId = productId;
            return this;
        }

        public Builder hardwareId(String hardwareId) {
            this.hardwareId = hardwareId;
            return this;
        }

        public Builder agreementId(int agreementId) {
            this.agreementId = agreementId;
            return this;
        }

        public Builder productKey(String productKey) {
            this.productKey = productKey;
            return this;
        }

        public Builder subProductKey(String subProductKey) {
            this.subProductKey = subProductKey;
            return this;
        }

        public Builder defaultNameRes(int defaultNameRes) {
            this.defaultNameRes = defaultNameRes;
            return this;
        }

        public Builder defaultIconRes(int defaultIconRes) {
            this.defaultIconRes = defaultIconRes;
            return this;
        }

        public Builder macFlag(int macFlag) {
            this.macFlag = macFlag;
            return this;
        }

        public Builder groupKey(String groupKey) {
            this.groupKey = groupKey;
            return this;
        }

        public Builder controlType(int controlType) {
            this.controlType = controlType;
            return this;
        }

        public Builder curtainType(int curtainType) {
            this.curtainType = curtainType;
            return this;
        }

        public Builder defaultNameStr(String defaultNameStr) {
            this.defaultNameStr = defaultNameStr;
            return this;
        }

        public Builder addNameRes(int addNameRes) {
            this.addNameRes = addNameRes;
            return this;
        }

        public ProductInfo build() {
            ProductInfo productInfo = new ProductInfo();
            productInfo.agreementId = this.agreementId;
            productInfo.productKey = TextUtils.isEmpty(this.productKey) ? "" : this.productKey;
            productInfo.subProductKey = TextUtils.isEmpty(this.subProductKey) ? "" : this.subProductKey;
            productInfo.defaultNameRes = this.defaultNameRes;
            productInfo.defaultNameStr = this.defaultNameStr;
            productInfo.defaultIconRes = this.defaultIconRes;
            productInfo.productType = this.productType;
            productInfo.hardwareId = this.hardwareId;
            productInfo.productId = this.productId;
            productInfo.macFlag = this.macFlag;
            productInfo.groupKey = TextUtils.isEmpty(this.groupKey) ? "" : this.groupKey;
            productInfo.controlType = this.controlType;
            productInfo.curtainType = this.curtainType;
            int i = this.addNameRes;
            if (i == 0) {
                i = this.defaultNameRes;
            }
            productInfo.addNameRes = i;
            return productInfo;
        }
    }
}