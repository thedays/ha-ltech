package com.ltech.smarthome.net.request.automation;

import java.util.List;

/* loaded from: classes4.dex */
public class SortAutomationRequest {
    private List<AutomationId> sorts;

    public SortAutomationRequest(List<AutomationId> ids) {
        this.sorts = ids;
    }

    public static class AutomationId {
        public long automationid;

        public AutomationId(long automationid) {
            this.automationid = automationid;
        }
    }
}