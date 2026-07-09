package com.ltech.nfc.source;

import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public class HB4Model extends SourceModel {
    @Override // com.ltech.nfc.source.SourceModel
    public int getBlockNumber() {
        return 16;
    }

    @Override // com.ltech.nfc.source.SourceModel
    public int getCheckBlockNumber() {
        return 15;
    }

    @Override // com.ltech.nfc.source.SourceModel
    public List<Integer> getBlankBlocks() {
        return Collections.EMPTY_LIST;
    }
}