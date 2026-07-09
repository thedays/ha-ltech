package com.ltech.smarthome.ui.device.ir;

import java.util.List;

/* loaded from: classes4.dex */
public class ActFanVM extends BaseIrVM {
    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVM
    protected void addKeyItem(List<IrKeyItem> keyItemList) {
        super.addKeyItem(keyItemList);
        keyItemList.add(IrKeyRepository.getIrKeyItem(1));
        keyItemList.add(IrKeyRepository.getIrKeyItem(IrKeyRepository.ID_FAN_SPEED));
        keyItemList.add(IrKeyRepository.getIrKeyItem(IrKeyRepository.ID_SWING_MODE));
        keyItemList.add(IrKeyRepository.getIrKeyItem(IrKeyRepository.ID_SWING));
        keyItemList.add(IrKeyRepository.getIrKeyItem(23));
        keyItemList.add(IrKeyRepository.getIrKeyItem(31));
        keyItemList.add(IrKeyRepository.getIrKeyItem(1762));
        if (this.irData.keys.size() > keyItemList.size() + 1) {
            keyItemList.add(IrKeyRepository.getIrKeyItem(-1));
        } else {
            keyItemList.add(IrKeyRepository.getIrKeyItem(106));
        }
    }
}