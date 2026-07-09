package com.ltech.smarthome.utils;

import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class InOrOutManager {
    private static InOrOutManager instance = new InOrOutManager();
    private ArrayList<FtDeviceGroupVM.InOrOutGroupItem> taskList = new ArrayList<>();
    private ArrayList<FtDeviceGroupVM.InOrOutGroupItem> errorList = new ArrayList<>();

    private InOrOutManager() {
    }

    public static InOrOutManager getInstance() {
        return instance;
    }

    public FtDeviceGroupVM.InOrOutGroupItem addTask(Device device, int position, boolean alreadyJoin) {
        FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem = new FtDeviceGroupVM.InOrOutGroupItem();
        inOrOutGroupItem.setDevice(device);
        inOrOutGroupItem.setId(position);
        this.taskList.add(inOrOutGroupItem);
        return inOrOutGroupItem;
    }

    public void addErrorList(FtDeviceGroupVM.InOrOutGroupItem item) {
        this.errorList.add(item);
    }

    public ArrayList<FtDeviceGroupVM.InOrOutGroupItem> getErrorList() {
        return this.errorList;
    }

    public ArrayList<FtDeviceGroupVM.InOrOutGroupItem> getTaskList() {
        return this.taskList;
    }

    public FtDeviceGroupVM.InOrOutGroupItem getItemById(int id) {
        Iterator<FtDeviceGroupVM.InOrOutGroupItem> it = this.taskList.iterator();
        while (it.hasNext()) {
            FtDeviceGroupVM.InOrOutGroupItem next = it.next();
            if (next.getId() == id) {
                return next;
            }
        }
        return null;
    }

    public boolean isSmartPanelDevice() {
        Iterator<FtDeviceGroupVM.InOrOutGroupItem> it = this.taskList.iterator();
        while (it.hasNext()) {
            FtDeviceGroupVM.InOrOutGroupItem next = it.next();
            if ((next.getDevice() != null && next.getDevice().getProductId().equals(ProductId.ID_SMART_SWITCH_S1C)) || next.getDevice().getProductId().equals(ProductId.ID_SMART_SWITCH_S2C) || next.getDevice().getProductId().equals(ProductId.ID_SMART_SWITCH_S3C) || next.getDevice().getProductId().equals(ProductId.ID_SMART_SWITCH_S4) || next.getDevice().getProductId().equals(ProductId.ID_SWITCH_PANEL_S4M) || next.getDevice().getProductId().equals(ProductId.ID_SMART_SWITCH_S1_PRO) || next.getDevice().getProductId().equals(ProductId.ID_SMART_SWITCH_S2_PRO) || next.getDevice().getProductId().equals(ProductId.ID_SMART_SWITCH_S3_PRO) || next.getDevice().getProductId().equals(ProductId.ID_SMART_PANEL_G4) || next.getDevice().getProductId().equals(ProductId.ID_SMART_PANEL_G4_PRO) || next.getDevice().getProductId().equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                return true;
            }
        }
        return false;
    }

    public boolean isWaveSensor() {
        Iterator<FtDeviceGroupVM.InOrOutGroupItem> it = this.taskList.iterator();
        while (it.hasNext()) {
            FtDeviceGroupVM.InOrOutGroupItem next = it.next();
            if (next.getDevice().getProductId().equals(ProductId.ID_SENSOR_MR03) || next.getDevice().getProductId().equals(ProductId.ID_SENSOR_MR04) || next.getDevice().getProductId().equals(ProductId.ID_SENSOR_MS03)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEurPanel() {
        Iterator<FtDeviceGroupVM.InOrOutGroupItem> it = this.taskList.iterator();
        if (it.hasNext()) {
            return ProductRepository.isEurPanel(it.next().getDevice().getProductId());
        }
        return false;
    }

    public boolean isAsPanel() {
        Iterator<FtDeviceGroupVM.InOrOutGroupItem> it = this.taskList.iterator();
        if (it.hasNext()) {
            return ProductRepository.isAsPanel(it.next().getDevice().getProductId());
        }
        return false;
    }

    public boolean isEurPanelError() {
        Iterator<FtDeviceGroupVM.InOrOutGroupItem> it = this.errorList.iterator();
        if (it.hasNext()) {
            return ProductRepository.isEurPanel(it.next().getDevice().getProductId());
        }
        return false;
    }

    public boolean isAsPanelError() {
        Iterator<FtDeviceGroupVM.InOrOutGroupItem> it = this.errorList.iterator();
        while (it.hasNext()) {
            if (ProductRepository.isAsPanel(it.next().getDevice().getProductId())) {
                return !AsHelper.isNewUb(r1.getDevice());
            }
        }
        return false;
    }

    public void clearAllList() {
        this.errorList.clear();
        this.taskList.clear();
    }

    public HashMap<Long, FtDeviceGroupVM.InOrOutGroupItem> getTaskMap() {
        HashMap<Long, FtDeviceGroupVM.InOrOutGroupItem> hashMap = new HashMap<>();
        Iterator<FtDeviceGroupVM.InOrOutGroupItem> it = this.taskList.iterator();
        while (it.hasNext()) {
            FtDeviceGroupVM.InOrOutGroupItem next = it.next();
            hashMap.put(Long.valueOf(next.getDevice().getId()), next);
        }
        return hashMap;
    }
}