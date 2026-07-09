package com.ltech.smarthome.ui.device.super_panel;

import com.github.angads25.filepicker.BuildConfig;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;

/* loaded from: classes4.dex */
public class SuperPanelVersionHelper {
    public static boolean canAddRs8(Device device) {
        return true;
    }

    public static boolean isSupportReplace(Device device) {
        if (device.getMcuversion() != null && !device.getBleversion().isEmpty() && device.getBleversion().compareTo("3.9.4") >= 0) {
            String productId = device.getProductId();
            productId.hashCode();
            switch (productId) {
                case "123050811340901":
                case "122080911090801":
                case "121052512023201":
                case "120010615085201":
                    if (device.getMcuversion().compareTo("2.3.8") >= 0) {
                        return true;
                    }
                    break;
                case "123050811353501":
                    return device.getMcuversion().compareTo("1.0.8") >= 0;
                case "3683388245101248":
                    return device.getMcuversion().compareTo(BuildConfig.VERSION_NAME) >= 0;
                case "122042815485901":
                    return device.getMcuversion().compareTo("1.2.4") >= 0;
                default:
                    return false;
            }
        }
        return false;
    }

    public static boolean canAddW5b(Device device) {
        if (device.getMcuversion() == null) {
            return false;
        }
        String productId = device.getProductId();
        productId.hashCode();
        switch (productId) {
            case "123050811340901":
            case "122080911090801":
            case "121052512023201":
            case "120010615085201":
                if (device.getMcuversion().compareTo("2.3.8") > 0) {
                }
                break;
            case "123050811353501":
                if (device.getMcuversion().compareTo("1.0.8") > 0) {
                }
                break;
            case "3683388245101248":
                if (device.getMcuversion().compareTo("1.0.1") > 0) {
                }
                break;
            case "122042815485901":
                if (device.getMcuversion().compareTo("1.2.4") > 0) {
                }
                break;
        }
        return false;
    }

    public static boolean canAddG4(Device device) {
        if (device.getMcuversion() == null) {
            return false;
        }
        String productId = device.getProductId();
        productId.hashCode();
        switch (productId) {
            case "123050811340901":
            case "122080911090801":
            case "121052512023201":
            case "120010615085201":
                if (device.getMcuversion().compareTo("2.3.9") > 0) {
                }
                break;
            case "123050811353501":
                if (device.getMcuversion().compareTo("1.0.9") > 0) {
                }
                break;
            case "122042815485901":
                if (device.getMcuversion().compareTo("1.2.5") > 0) {
                }
                break;
        }
        return false;
    }

    public static boolean canAdd485(Device device) {
        if (device.getMcuversion() == null) {
            return false;
        }
        String productId = device.getProductId();
        productId.hashCode();
        return productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S) && device.getFirmwareversion().compareTo("20251020") >= 0;
    }

    public static boolean supportDoorSensor(Device device) {
        return canAddW5b(device);
    }

    public static boolean canAddSonos(Device device) {
        String productId = device.getProductId();
        productId.hashCode();
        return productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S) || productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S);
    }

    public static boolean supportIrControlCmd(Device device) {
        if (device.getMcuversion() == null) {
            return false;
        }
        String productId = device.getProductId();
        productId.hashCode();
        switch (productId) {
            case "123050811340901":
            case "122080911090801":
            case "121052512023201":
            case "120010615085201":
                if (device.getMcuversion().compareTo("2.4.9") > 0) {
                }
                break;
            case "123050811353501":
                if (device.getMcuversion().compareTo("1.1.5") > 0) {
                }
                break;
            case "3683388245101248":
                if (device.getMcuversion().compareTo("1.0.7") > 0) {
                }
                break;
            case "122042815485901":
                if (device.getMcuversion().compareTo("1.3.1") > 0) {
                }
                break;
        }
        return false;
    }
}