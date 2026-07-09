package com.ltech.smarthome.net.request.device;

import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.CgdProLightExtParam;
import com.ltech.smarthome.utils.CodeLibraryUtil;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class UpdateDaliDeviceRequest {
    private List<DaliSubDevice> devices = new ArrayList();
    private long floorid;
    private long macdeviceid;
    private long placeid;
    private long roomid;

    public UpdateDaliDeviceRequest(long roomid, long floorid, long placeid, long macdeviceid, List<Device> deviceList) {
        this.roomid = roomid;
        this.floorid = floorid;
        this.placeid = placeid;
        this.macdeviceid = macdeviceid;
        for (Device device : deviceList) {
            DaliSubDevice daliSubDevice = new DaliSubDevice();
            daliSubDevice.setProductid(device.getProductId());
            daliSubDevice.setMac(device.getWifiMac());
            daliSubDevice.setDevicename(device.getDeviceName());
            daliSubDevice.setMacfalg(device.getMacfalg());
            daliSubDevice.setDevicesn(device.getDevicesn());
            daliSubDevice.setMaccode(device.getMaccode());
            daliSubDevice.setImgindex(device.getImageIndex() + "");
            daliSubDevice.setParam(device.getParam());
            daliSubDevice.setParamext(device.getExtParam());
            daliSubDevice.setMaxkelvin(device.getMaxkelvin());
            daliSubDevice.setMinkelvin(device.getMinkelvin());
            CgdProLightExtParam cgdProLightExtParam = (CgdProLightExtParam) device.getExtParam(CgdProLightExtParam.class);
            daliSubDevice.setCodeLibrary(CodeLibraryUtil.generateLightcodeLibraryData(cgdProLightExtParam.getDaliAddr(), cgdProLightExtParam.getDaliType()));
            this.devices.add(daliSubDevice);
        }
    }

    public static final class DaliSubDevice {
        private String CodeLibrary;
        private String devicename;
        private String devicesn;
        private String img;
        private String imgindex;
        private String mac;
        private String maccode;
        private int macfalg;
        private int maxkelvin;
        private int minkelvin;
        private String param;
        private String paramext;
        private String productid;

        public String getProductid() {
            return this.productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getMac() {
            return this.mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getDevicename() {
            return this.devicename;
        }

        public void setDevicename(String devicename) {
            this.devicename = devicename;
        }

        public int getMacfalg() {
            return this.macfalg;
        }

        public void setMacfalg(int macfalg) {
            this.macfalg = macfalg;
        }

        public String getDevicesn() {
            return this.devicesn;
        }

        public void setDevicesn(String devicesn) {
            this.devicesn = devicesn;
        }

        public String getMaccode() {
            return this.maccode;
        }

        public void setMaccode(String maccode) {
            this.maccode = maccode;
        }

        public String getImgindex() {
            return this.imgindex;
        }

        public void setImgindex(String imgindex) {
            this.imgindex = imgindex;
        }

        public String getImg() {
            return this.img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getParam() {
            return this.param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public String getParamext() {
            return this.paramext;
        }

        public void setParamext(String paramext) {
            this.paramext = paramext;
        }

        public String getCodeLibrary() {
            return this.CodeLibrary;
        }

        public void setCodeLibrary(String codeLibrary) {
            this.CodeLibrary = codeLibrary;
        }

        public int getMinkelvin() {
            return this.minkelvin;
        }

        public void setMinkelvin(int minkelvin) {
            this.minkelvin = minkelvin;
        }

        public int getMaxkelvin() {
            return this.maxkelvin;
        }

        public void setMaxkelvin(int maxkelvin) {
            this.maxkelvin = maxkelvin;
        }
    }
}