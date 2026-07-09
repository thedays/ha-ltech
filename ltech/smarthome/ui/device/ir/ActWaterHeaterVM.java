package com.ltech.smarthome.ui.device.ir;

import java.util.List;

/* loaded from: classes4.dex */
public class ActWaterHeaterVM extends BaseIrVM {
    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVM
    protected void addKeyItem(List<IrKeyItem> keyItemList) {
        super.addKeyItem(keyItemList);
        keyItemList.add(IrKeyRepository.getIrKeyItem(1));
        keyItemList.add(IrKeyRepository.getIrKeyItem(3));
        keyItemList.add(IrKeyRepository.getIrKeyItem(4));
        keyItemList.add(IrKeyRepository.getIrKeyItem(42));
        keyItemList.add(IrKeyRepository.getIrKeyItem(2));
        keyItemList.add(IrKeyRepository.getIrKeyItem(IrKeyRepository.ID_MEDIUM_TEMPERATURE_INSULATION));
        keyItemList.add(IrKeyRepository.getIrKeyItem(1299));
        if (this.irData.keys.size() > keyItemList.size() + 1) {
            keyItemList.add(IrKeyRepository.getIrKeyItem(-1));
        } else {
            keyItemList.add(IrKeyRepository.getIrKeyItem(IrKeyRepository.ID_SET));
        }
    }
}