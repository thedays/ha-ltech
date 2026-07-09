package com.ltech.nfc.source;

import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class BleSource extends SourceModel {
    @Override // com.ltech.nfc.source.SourceModel
    public int getBlockNumber() {
        return 12;
    }

    @Override // com.ltech.nfc.source.SourceModel
    public int getCheckBlockNumber() {
        return 7;
    }

    @Override // com.ltech.nfc.source.SourceModel
    public List<Integer> getBlankBlocks() {
        return Arrays.asList(3, 14, 15, 26, 27);
    }

    public static List<Integer> getExtendDataList() {
        return Arrays.asList(46, 47);
    }
}