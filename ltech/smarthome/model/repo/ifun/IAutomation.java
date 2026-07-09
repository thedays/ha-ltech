package com.ltech.smarthome.model.repo.ifun;

import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.net.response.automation.ListAutomationResponse;
import java.util.List;

/* loaded from: classes4.dex */
public interface IAutomation {
    Listing<Automation> getAutomation(LifecycleOwner owner, long automationId);

    Automation getAutomationById(long automationId);

    Listing<Automation> getAutomationList(LifecycleOwner owner, long placeId);

    Listing<Automation> getAutomationList(LifecycleOwner owner, long placeId, int automationType);

    List<Automation> getAutomationListByPlaceId(long placeId);

    List<Automation> getAutomationListFromNet(ListAutomationResponse response);

    boolean isAutomationNameExist(long place, String name);

    void removeAutomation(long automationId);

    void saveAutomation(Automation automation);

    void sortAutomation(List<Automation> automationList);
}