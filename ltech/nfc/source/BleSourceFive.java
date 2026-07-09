package com.ltech.nfc.source;

import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class BleSourceFive extends SourceModel {
    @Override // com.ltech.nfc.source.SourceModel
    public int getBlockNumber() {
        return 15;
    }

    @Override // com.ltech.nfc.source.SourceModel
    public int getCheckBlockNumber() {
        return 14;
    }

    @Override // com.ltech.nfc.source.SourceModel
    public List<Integer> getBlankBlocks() {
        return Arrays.asList(3, 37, 38, 39, 55);
    }
}