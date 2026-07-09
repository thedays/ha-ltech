package com.ltech.smarthome;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aliyun.linksdk.alcs.AlcsConstant;
import com.justalk.cloud.lemon.MtcConf2Constants;
import com.justalk.cloud.lemon.MtcConfConstants;
import com.ltech.smarthome.databinding.ActAboutBindingImpl;
import com.ltech.smarthome.databinding.ActAcBgBindingImpl;
import com.ltech.smarthome.databinding.ActAcBindingImpl;
import com.ltech.smarthome.databinding.ActAcCentralBindingImpl;
import com.ltech.smarthome.databinding.ActAccountAndSecurityBindingImpl;
import com.ltech.smarthome.databinding.ActAddAutomationBindingImpl;
import com.ltech.smarthome.databinding.ActAddCloudSceneBindingImpl;
import com.ltech.smarthome.databinding.ActAddDeviceBindingImpl;
import com.ltech.smarthome.databinding.ActAddDuvBindingImpl;
import com.ltech.smarthome.databinding.ActAddFloorBindingImpl;
import com.ltech.smarthome.databinding.ActAddInstructBindingImpl;
import com.ltech.smarthome.databinding.ActAddIrDeviceBindingImpl;
import com.ltech.smarthome.databinding.ActAddIrKeyBindingImpl;
import com.ltech.smarthome.databinding.ActAddLocalSceneBindingImpl;
import com.ltech.smarthome.databinding.ActAddModeColorBindingImpl;
import com.ltech.smarthome.databinding.ActAddModeCtDimBindingImpl;
import com.ltech.smarthome.databinding.ActAddMusicBindingImpl;
import com.ltech.smarthome.databinding.ActAddPlaceUserBindingImpl;
import com.ltech.smarthome.databinding.ActAddVirtualDeviceBindingImpl;
import com.ltech.smarthome.databinding.ActAsPanelBindingImpl;
import com.ltech.smarthome.databinding.ActAsPanelSettingBindingImpl;
import com.ltech.smarthome.databinding.ActAutoNetTimeSettingBindingImpl;
import com.ltech.smarthome.databinding.ActBatteryGuideBindingImpl;
import com.ltech.smarthome.databinding.ActBleCurtainMotorBindingImpl;
import com.ltech.smarthome.databinding.ActBleCurtainMotorMoreSettingBindingImpl;
import com.ltech.smarthome.databinding.ActBleCurtainMotorTypeSettingBindingImpl;
import com.ltech.smarthome.databinding.ActBleHamSettingDefaultBindingImpl;
import com.ltech.smarthome.databinding.ActBleMotorModeSetBindingImpl;
import com.ltech.smarthome.databinding.ActBleMotorSettingBindingImpl;
import com.ltech.smarthome.databinding.ActBleMusicPlayerBindingImpl;
import com.ltech.smarthome.databinding.ActBleMusicPlayerSettingBindingImpl;
import com.ltech.smarthome.databinding.ActBleTrigCurtainSettingBindingImpl;
import com.ltech.smarthome.databinding.ActBleTrigSceneSettingBindingImpl;
import com.ltech.smarthome.databinding.ActBrtButtonSettingBindingImpl;
import com.ltech.smarthome.databinding.ActBtOtaBindingImpl;
import com.ltech.smarthome.databinding.ActBtOtaLowPowerBindingImpl;
import com.ltech.smarthome.databinding.ActBtOtaSingleBindingImpl;
import com.ltech.smarthome.databinding.ActCameraInfoBindingImpl;
import com.ltech.smarthome.databinding.ActCameraPlayBackBindingImpl;
import com.ltech.smarthome.databinding.ActCameraPlayBindingImpl;
import com.ltech.smarthome.databinding.ActCameraSettingBindingImpl;
import com.ltech.smarthome.databinding.ActCentralAirGatewayBindingImpl;
import com.ltech.smarthome.databinding.ActCentralAirMeshGatewaySettingBindingImpl;
import com.ltech.smarthome.databinding.ActCentralAirProGatewayBindingImpl;
import com.ltech.smarthome.databinding.ActCg485BindingImpl;
import com.ltech.smarthome.databinding.ActCg485DeviceBindingImpl;
import com.ltech.smarthome.databinding.ActCg485OldBindingImpl;
import com.ltech.smarthome.databinding.ActCg485SettingBindingImpl;
import com.ltech.smarthome.databinding.ActCgdProLightBindingImpl;
import com.ltech.smarthome.databinding.ActCgdProLightSettingBindingImpl;
import com.ltech.smarthome.databinding.ActChangeEmailBindingImpl;
import com.ltech.smarthome.databinding.ActChangePhoneBindingImpl;
import com.ltech.smarthome.databinding.ActChangePwdBindingImpl;
import com.ltech.smarthome.databinding.ActChannel512BindingImpl;
import com.ltech.smarthome.databinding.ActChildMcuUpgradeBindingImpl;
import com.ltech.smarthome.databinding.ActChoiceLightTypeBindingImpl;
import com.ltech.smarthome.databinding.ActColorLightBindingImpl;
import com.ltech.smarthome.databinding.ActColorLightCcBindingImpl;
import com.ltech.smarthome.databinding.ActCommandCategoryBindingImpl;
import com.ltech.smarthome.databinding.ActConfigSuccessBindingImpl;
import com.ltech.smarthome.databinding.ActCreateHomeBindingImpl;
import com.ltech.smarthome.databinding.ActCtEditBindingImpl;
import com.ltech.smarthome.databinding.ActCtLightBindingImpl;
import com.ltech.smarthome.databinding.ActCtSelectColorBindingImpl;
import com.ltech.smarthome.databinding.ActCurrentSetBindingImpl;
import com.ltech.smarthome.databinding.ActCurrentSetFiveBindingImpl;
import com.ltech.smarthome.databinding.ActDaliBatchModifyParamBindingImpl;
import com.ltech.smarthome.databinding.ActDaliLightBindingImpl;
import com.ltech.smarthome.databinding.ActDaliLightGroupSettingBindingImpl;
import com.ltech.smarthome.databinding.ActDaliLightSettingBindingImpl;
import com.ltech.smarthome.databinding.ActDaliSceneSettingBindingImpl;
import com.ltech.smarthome.databinding.ActDaliSelectBindingImpl;
import com.ltech.smarthome.databinding.ActDcaMusicDetailBindingImpl;
import com.ltech.smarthome.databinding.ActDcaMusicHomeBindingImpl;
import com.ltech.smarthome.databinding.ActDcaMusicListBindingImpl;
import com.ltech.smarthome.databinding.ActDcaWebViewBindingImpl;
import com.ltech.smarthome.databinding.ActDeviceConfigFailBindingImpl;
import com.ltech.smarthome.databinding.ActDeviceConnectAndroid10BindingImpl;
import com.ltech.smarthome.databinding.ActDeviceConnectBindingImpl;
import com.ltech.smarthome.databinding.ActDeviceGroupManageBindingImpl;
import com.ltech.smarthome.databinding.ActDeviceLogBindingImpl;
import com.ltech.smarthome.databinding.ActDeviceManageBindingImpl;
import com.ltech.smarthome.databinding.ActDeviceReplaceBindingImpl;
import com.ltech.smarthome.databinding.ActDeviceSettingBatteryBindingImpl;
import com.ltech.smarthome.databinding.ActDeviceSettingDefaultBindingImpl;
import com.ltech.smarthome.databinding.ActDimLightBindingImpl;
import com.ltech.smarthome.databinding.ActDimSelectColorBindingImpl;
import com.ltech.smarthome.databinding.ActDiyIrSettingBindingImpl;
import com.ltech.smarthome.databinding.ActDiyLightNameBindingImpl;
import com.ltech.smarthome.databinding.ActDiyRoomBindingImpl;
import com.ltech.smarthome.databinding.ActDmx512SettingBindingImpl;
import com.ltech.smarthome.databinding.ActDmxChannelSelectBindingImpl;
import com.ltech.smarthome.databinding.ActDoorSensorBindingImpl;
import com.ltech.smarthome.databinding.ActDuvListBindingImpl;
import com.ltech.smarthome.databinding.ActE6PanelBindingImpl;
import com.ltech.smarthome.databinding.ActE6PanelSettingBindingImpl;
import com.ltech.smarthome.databinding.ActEditAdvancedModeBindingImpl;
import com.ltech.smarthome.databinding.ActEditColorDiyModeBindingImpl;
import com.ltech.smarthome.databinding.ActEditGeneralModeBindingImpl;
import com.ltech.smarthome.databinding.ActEditInstructCmdBindingImpl;
import com.ltech.smarthome.databinding.ActEditKeyNameBindingImpl;
import com.ltech.smarthome.databinding.ActEditNameBindingImpl;
import com.ltech.smarthome.databinding.ActEditNumberBindingImpl;
import com.ltech.smarthome.databinding.ActEngineeringModeBindingImpl;
import com.ltech.smarthome.databinding.ActEngineeringModeOnOffBindingImpl;
import com.ltech.smarthome.databinding.ActEnvironmentLogBindingImpl;
import com.ltech.smarthome.databinding.ActEurPanelBindingImpl;
import com.ltech.smarthome.databinding.ActEurPanelEb6BindingImpl;
import com.ltech.smarthome.databinding.ActEurPanelGroupSettingBindingImpl;
import com.ltech.smarthome.databinding.ActEurPanelSettingBindingImpl;
import com.ltech.smarthome.databinding.ActFanBindingImpl;
import com.ltech.smarthome.databinding.ActFeedBackBindingImpl;
import com.ltech.smarthome.databinding.ActFloorHeatBindingImpl;
import com.ltech.smarthome.databinding.ActFloorManageBindingImpl;
import com.ltech.smarthome.databinding.ActFreshAirBindingImpl;
import com.ltech.smarthome.databinding.ActG4MaxKeySetBindingImpl;
import com.ltech.smarthome.databinding.ActGetPermissionBindingImpl;
import com.ltech.smarthome.databinding.ActGqProBindingImpl;
import com.ltech.smarthome.databinding.ActGqProThemeBindingImpl;
import com.ltech.smarthome.databinding.ActGqxBindingImpl;
import com.ltech.smarthome.databinding.ActGqxSettingBindingImpl;
import com.ltech.smarthome.databinding.ActGroupCurtainSettingBindingImpl;
import com.ltech.smarthome.databinding.ActGroupManageBindingImpl;
import com.ltech.smarthome.databinding.ActGroupSettingBindingImpl;
import com.ltech.smarthome.databinding.ActHomeBindingImpl;
import com.ltech.smarthome.databinding.ActHomeKitBindingImpl;
import com.ltech.smarthome.databinding.ActHomeKitOldBindingImpl;
import com.ltech.smarthome.databinding.ActHomeKitSettingBindingImpl;
import com.ltech.smarthome.databinding.ActHomeManageBindingImpl;
import com.ltech.smarthome.databinding.ActHomePositionBindingImpl;
import com.ltech.smarthome.databinding.ActHomeQrCodeBindingImpl;
import com.ltech.smarthome.databinding.ActHomeSettingBindingImpl;
import com.ltech.smarthome.databinding.ActHomeTransferSettingBindingImpl;
import com.ltech.smarthome.databinding.ActHsdSensorBindingImpl;
import com.ltech.smarthome.databinding.ActHsdSensorSettingBindingImpl;
import com.ltech.smarthome.databinding.ActImportSceneAllBindingImpl;
import com.ltech.smarthome.databinding.ActInstructSettingBindingImpl;
import com.ltech.smarthome.databinding.ActIntelligenceBindingImpl;
import com.ltech.smarthome.databinding.ActIntercomBindingImpl;
import com.ltech.smarthome.databinding.ActIntercomLoginBindingImpl;
import com.ltech.smarthome.databinding.ActIntercomRecordBindingImpl;
import com.ltech.smarthome.databinding.ActIntercomSelectCommunityBindingImpl;
import com.ltech.smarthome.databinding.ActIntercomSetFaceBindingImpl;
import com.ltech.smarthome.databinding.ActIntercomSetOpenKeyBindingImpl;
import com.ltech.smarthome.databinding.ActIntercomSettingBindingImpl;
import com.ltech.smarthome.databinding.ActIntercomTempKeyBindingImpl;
import com.ltech.smarthome.databinding.ActIntercomTipsBindingImpl;
import com.ltech.smarthome.databinding.ActIrDiyBindingImpl;
import com.ltech.smarthome.databinding.ActIrSettingBindingImpl;
import com.ltech.smarthome.databinding.ActKbsBindingImpl;
import com.ltech.smarthome.databinding.ActKbsGroupSettingBindingImpl;
import com.ltech.smarthome.databinding.ActKbsSettingBindingImpl;
import com.ltech.smarthome.databinding.ActKeySwitchBindingImpl;
import com.ltech.smarthome.databinding.ActKnobPanelBindingImpl;
import com.ltech.smarthome.databinding.ActKnobPanelSettingBindingImpl;
import com.ltech.smarthome.databinding.ActKnobScreenPanelBindingImpl;
import com.ltech.smarthome.databinding.ActLightGroupSubItemControlBindingImpl;
import com.ltech.smarthome.databinding.ActLightOnOffTimeBindingImpl;
import com.ltech.smarthome.databinding.ActLightPlanBatchSetBindingImpl;
import com.ltech.smarthome.databinding.ActLightPlanDetailBindingImpl;
import com.ltech.smarthome.databinding.ActLightPlanListBindingImpl;
import com.ltech.smarthome.databinding.ActLightSettingBindingImpl;
import com.ltech.smarthome.databinding.ActLightSettingNewBindingImpl;
import com.ltech.smarthome.databinding.ActLocalDeviceLogBindingImpl;
import com.ltech.smarthome.databinding.ActLocationPermissionDescriptionBindingImpl;
import com.ltech.smarthome.databinding.ActLoginBindingImpl;
import com.ltech.smarthome.databinding.ActMapBindingImpl;
import com.ltech.smarthome.databinding.ActMatterListBindingImpl;
import com.ltech.smarthome.databinding.ActMatterPlatformListBindingImpl;
import com.ltech.smarthome.databinding.ActMatterSubListBindingImpl;
import com.ltech.smarthome.databinding.ActMeshGatewayBindingImpl;
import com.ltech.smarthome.databinding.ActMeshGatewayLightSettingBindingImpl;
import com.ltech.smarthome.databinding.ActMeshGatewaySettingBindingImpl;
import com.ltech.smarthome.databinding.ActMeshNearDeviceBindingImpl;
import com.ltech.smarthome.databinding.ActMeshScan1BindingImpl;
import com.ltech.smarthome.databinding.ActMeshScan2BindingImpl;
import com.ltech.smarthome.databinding.ActMeshScanBindingImpl;
import com.ltech.smarthome.databinding.ActMeshScanProxyBindingImpl;
import com.ltech.smarthome.databinding.ActMessageCenterBindingImpl;
import com.ltech.smarthome.databinding.ActModeBindingImpl;
import com.ltech.smarthome.databinding.ActModuleSwitchBindingImpl;
import com.ltech.smarthome.databinding.ActMonitorBindingImpl;
import com.ltech.smarthome.databinding.ActMotorBindingImpl;
import com.ltech.smarthome.databinding.ActMotorPairBindingImpl;
import com.ltech.smarthome.databinding.ActMusicBindingImpl;
import com.ltech.smarthome.databinding.ActMusicListBindingImpl;
import com.ltech.smarthome.databinding.ActNetConfigBindingImpl;
import com.ltech.smarthome.databinding.ActNetConnectBindingImpl;
import com.ltech.smarthome.databinding.ActNewAsPanelBindingImpl;
import com.ltech.smarthome.databinding.ActNewScreenPanelBindingImpl;
import com.ltech.smarthome.databinding.ActNewSmartPanelBindingImpl;
import com.ltech.smarthome.databinding.ActNfcRestoreBindingImpl;
import com.ltech.smarthome.databinding.ActOpenDoorLogDetailBindingImpl;
import com.ltech.smarthome.databinding.ActPageScreenPanelBindingImpl;
import com.ltech.smarthome.databinding.ActPanelColorSetBindingImpl;
import com.ltech.smarthome.databinding.ActPanelGroupSettingBindingImpl;
import com.ltech.smarthome.databinding.ActPanelNightGetupBindingImpl;
import com.ltech.smarthome.databinding.ActPanelSwitchPositionSetBindingImpl;
import com.ltech.smarthome.databinding.ActPlaceUserSettingBindingImpl;
import com.ltech.smarthome.databinding.ActPlaylistManageBindingImpl;
import com.ltech.smarthome.databinding.ActProductIntroduction1BindingImpl;
import com.ltech.smarthome.databinding.ActProductIntroductionBindingImpl;
import com.ltech.smarthome.databinding.ActPubListBindingImpl;
import com.ltech.smarthome.databinding.ActQrCodeScanBindingImpl;
import com.ltech.smarthome.databinding.ActQrCodeScanResultBindingImpl;
import com.ltech.smarthome.databinding.ActR8SettingBindingImpl;
import com.ltech.smarthome.databinding.ActRc4sBindingImpl;
import com.ltech.smarthome.databinding.ActRecordListBindingImpl;
import com.ltech.smarthome.databinding.ActRegisterBindingImpl;
import com.ltech.smarthome.databinding.ActRemoteBatteryBindingImpl;
import com.ltech.smarthome.databinding.ActReplaceBindingImpl;
import com.ltech.smarthome.databinding.ActRgbwafSelectBindingImpl;
import com.ltech.smarthome.databinding.ActRoomManageBindingImpl;
import com.ltech.smarthome.databinding.ActRs8AddSubDeviceBindingImpl;
import com.ltech.smarthome.databinding.ActRs8AddressWriteBindingImpl;
import com.ltech.smarthome.databinding.ActRs8BindingImpl;
import com.ltech.smarthome.databinding.ActRs8CurtainBindingImpl;
import com.ltech.smarthome.databinding.ActRs8SubDeviceSettingBindingImpl;
import com.ltech.smarthome.databinding.ActSaveQrCodeBindingImpl;
import com.ltech.smarthome.databinding.ActScenePanelBindingImpl;
import com.ltech.smarthome.databinding.ActScreenPanelBindingImpl;
import com.ltech.smarthome.databinding.ActScreenPanelElderlyModeBindingImpl;
import com.ltech.smarthome.databinding.ActSearchAutomationBindingImpl;
import com.ltech.smarthome.databinding.ActSearchDeviceBindingImpl;
import com.ltech.smarthome.databinding.ActSearchGooglePositionBindingImpl;
import com.ltech.smarthome.databinding.ActSearchMusicBindingImpl;
import com.ltech.smarthome.databinding.ActSearchPositionBindingImpl;
import com.ltech.smarthome.databinding.ActSearchSceneBindingImpl;
import com.ltech.smarthome.databinding.ActSelect2BindingImpl;
import com.ltech.smarthome.databinding.ActSelect3BindingImpl;
import com.ltech.smarthome.databinding.ActSelect4BindingImpl;
import com.ltech.smarthome.databinding.ActSelectBindingImpl;
import com.ltech.smarthome.databinding.ActSelectBleCurtainActionBindingImpl;
import com.ltech.smarthome.databinding.ActSelectBrandBindingImpl;
import com.ltech.smarthome.databinding.ActSelectColorBindingImpl;
import com.ltech.smarthome.databinding.ActSelectColorCcBindingImpl;
import com.ltech.smarthome.databinding.ActSelectConditionDeviceBindingImpl;
import com.ltech.smarthome.databinding.ActSelectCountryBindingImpl;
import com.ltech.smarthome.databinding.ActSelectDaliAddBindingImpl;
import com.ltech.smarthome.databinding.ActSelectDaliColorBindingImpl;
import com.ltech.smarthome.databinding.ActSelectDivideBindingImpl;
import com.ltech.smarthome.databinding.ActSelectEffectPeriodBindingImpl;
import com.ltech.smarthome.databinding.ActSelectHomeMemberBindingImpl;
import com.ltech.smarthome.databinding.ActSelectLightBindingImpl;
import com.ltech.smarthome.databinding.ActSelectListBindingImpl;
import com.ltech.smarthome.databinding.ActSelectMultiTypeBindingImpl;
import com.ltech.smarthome.databinding.ActSelectOperatorsBindingImpl;
import com.ltech.smarthome.databinding.ActSelectSceneAllBindingImpl;
import com.ltech.smarthome.databinding.ActSelectSceneBindingImpl;
import com.ltech.smarthome.databinding.ActSelectSonosActionBindingImpl;
import com.ltech.smarthome.databinding.ActSelectSpiForActionBindingImpl;
import com.ltech.smarthome.databinding.ActSelectSuperPanelMusicBindingImpl;
import com.ltech.smarthome.databinding.ActSelectTemperatureWeatherBindingImpl;
import com.ltech.smarthome.databinding.ActSelectThemeModeBindingImpl;
import com.ltech.smarthome.databinding.ActSelectTimeBindingImpl;
import com.ltech.smarthome.databinding.ActSelectVoiceSpeakBindingImpl;
import com.ltech.smarthome.databinding.ActSelectWeatherBindingImpl;
import com.ltech.smarthome.databinding.ActSenseSettingBindingImpl;
import com.ltech.smarthome.databinding.ActSensorNobobyTestBindingImpl;
import com.ltech.smarthome.databinding.ActSensorSettingBindingImpl;
import com.ltech.smarthome.databinding.ActSerialSettingBindingImpl;
import com.ltech.smarthome.databinding.ActSetLightChannelBindingImpl;
import com.ltech.smarthome.databinding.ActSetScreenDisplayBindingImpl;
import com.ltech.smarthome.databinding.ActShareDuvListBindingImpl;
import com.ltech.smarthome.databinding.ActSmartPanelBindingImpl;
import com.ltech.smarthome.databinding.ActSmartPanelChildSettingBindingImpl;
import com.ltech.smarthome.databinding.ActSmartPanelGroupChildSettingBindingImpl;
import com.ltech.smarthome.databinding.ActSmartPanelKeySetBindingImpl;
import com.ltech.smarthome.databinding.ActSmartPanelSettingBindingImpl;
import com.ltech.smarthome.databinding.ActSmartPanelSwitchSettingBindingImpl;
import com.ltech.smarthome.databinding.ActSmartPanelThemeBindingImpl;
import com.ltech.smarthome.databinding.ActSmartSpeakerBindingImpl;
import com.ltech.smarthome.databinding.ActSmartSpeakerDetailBindingImpl;
import com.ltech.smarthome.databinding.ActSongsBindingImpl;
import com.ltech.smarthome.databinding.ActSonosMusicDetailBindingImpl;
import com.ltech.smarthome.databinding.ActSonosSettingDefaultBindingImpl;
import com.ltech.smarthome.databinding.ActSonosWebViewBindingImpl;
import com.ltech.smarthome.databinding.ActSortBindingImpl;
import com.ltech.smarthome.databinding.ActSortLocalMusicBindingImpl;
import com.ltech.smarthome.databinding.ActSp485ListBindingImpl;
import com.ltech.smarthome.databinding.ActSpiControllerBindingImpl;
import com.ltech.smarthome.databinding.ActSpiControllerSettingBindingImpl;
import com.ltech.smarthome.databinding.ActSpiEditPlayListBindingImpl;
import com.ltech.smarthome.databinding.ActSpiLightSettingBindingImpl;
import com.ltech.smarthome.databinding.ActSplashBindingImpl;
import com.ltech.smarthome.databinding.ActStepsIntroductionBindingImpl;
import com.ltech.smarthome.databinding.ActSubDeviceSettingDefaultBindingImpl;
import com.ltech.smarthome.databinding.ActSuperPanelAlbumBindingImpl;
import com.ltech.smarthome.databinding.ActSuperPanelBindingImpl;
import com.ltech.smarthome.databinding.ActSuperPanelClipPhotoBindingImpl;
import com.ltech.smarthome.databinding.ActSuperPanelContinousTalkBindingImpl;
import com.ltech.smarthome.databinding.ActSuperPanelIrRemoteControlBindingImpl;
import com.ltech.smarthome.databinding.ActSuperPanelKeySet6sBindingImpl;
import com.ltech.smarthome.databinding.ActSuperPanelKeySetBindingImpl;
import com.ltech.smarthome.databinding.ActSuperPanelPreviewPhotoBindingImpl;
import com.ltech.smarthome.databinding.ActSuperPanelSelectPhotoBindingImpl;
import com.ltech.smarthome.databinding.ActSuperPanelSettingBindingImpl;
import com.ltech.smarthome.databinding.ActSuperPanelVoiceControlRangeBindingImpl;
import com.ltech.smarthome.databinding.ActSuperPanelVoiceControlRangeRoleBindingImpl;
import com.ltech.smarthome.databinding.ActSuperPanelVoiceTalkBindingImpl;
import com.ltech.smarthome.databinding.ActTePanelBindingImpl;
import com.ltech.smarthome.databinding.ActTestModeMainBindingImpl;
import com.ltech.smarthome.databinding.ActTestPrepareBindingImpl;
import com.ltech.smarthome.databinding.ActTestStepBindingImpl;
import com.ltech.smarthome.databinding.ActThemeDownloadBindingImpl;
import com.ltech.smarthome.databinding.ActTransferMusicBindingImpl;
import com.ltech.smarthome.databinding.ActTrigBindingImpl;
import com.ltech.smarthome.databinding.ActTrigCurtainBindingImpl;
import com.ltech.smarthome.databinding.ActTrigCurtainChannelSetBindingImpl;
import com.ltech.smarthome.databinding.ActTrigCurtainGroupSettingBindingImpl;
import com.ltech.smarthome.databinding.ActTrigCurtainOpenDirSetBindingImpl;
import com.ltech.smarthome.databinding.ActTrigToBleBindingImpl;
import com.ltech.smarthome.databinding.ActTvBindingImpl;
import com.ltech.smarthome.databinding.ActUpgradeBindingImpl;
import com.ltech.smarthome.databinding.ActUserInfoBindingImpl;
import com.ltech.smarthome.databinding.ActVoiceCallBindBindingImpl;
import com.ltech.smarthome.databinding.ActVoiceCallGropListBindingImpl;
import com.ltech.smarthome.databinding.ActVoiceCallGroupAddBindingImpl;
import com.ltech.smarthome.databinding.ActVoiceCallSettingBindingImpl;
import com.ltech.smarthome.databinding.ActVoiceCallWhitelistAddBindingImpl;
import com.ltech.smarthome.databinding.ActWaveSensorBindingImpl;
import com.ltech.smarthome.databinding.ActWaveSensorEffectPeriodBindingImpl;
import com.ltech.smarthome.databinding.ActWaveSensorGroupSettingBindingImpl;
import com.ltech.smarthome.databinding.ActWaveSensorProBindingImpl;
import com.ltech.smarthome.databinding.ActWaveSensorSettingBindingImpl;
import com.ltech.smarthome.databinding.ActWebViewBindingImpl;
import com.ltech.smarthome.databinding.ActWelcomeBindingImpl;
import com.ltech.smarthome.databinding.ActXySelectBindingImpl;
import com.ltech.smarthome.databinding.ActivityActMeshAddAllDeviceBindingImpl;
import com.ltech.smarthome.databinding.Dialog512ChannelSettingBindingImpl;
import com.ltech.smarthome.databinding.DialogAcQuickBindingImpl;
import com.ltech.smarthome.databinding.DialogAddCg485CategoryBindingImpl;
import com.ltech.smarthome.databinding.DialogAddCg485DeviceBindingImpl;
import com.ltech.smarthome.databinding.DialogAddColorPointBindingImpl;
import com.ltech.smarthome.databinding.DialogAddGroupBindingImpl;
import com.ltech.smarthome.databinding.DialogAddSceneBindingImpl;
import com.ltech.smarthome.databinding.DialogButtonTipBindingImpl;
import com.ltech.smarthome.databinding.DialogCalendarBindingImpl;
import com.ltech.smarthome.databinding.DialogCallInviteBindingImpl;
import com.ltech.smarthome.databinding.DialogCenterDaliModifyParamBindingImpl;
import com.ltech.smarthome.databinding.DialogCenterSelectListBindingImpl;
import com.ltech.smarthome.databinding.DialogCenterTipBindingImpl;
import com.ltech.smarthome.databinding.DialogCityPickerBindingImpl;
import com.ltech.smarthome.databinding.DialogColorBrtControlBindingImpl;
import com.ltech.smarthome.databinding.DialogDaliDetectBindingImpl;
import com.ltech.smarthome.databinding.DialogDaliDetectTipBindingImpl;
import com.ltech.smarthome.databinding.DialogDaliLoadDataBindingImpl;
import com.ltech.smarthome.databinding.DialogDelFailBindingImpl;
import com.ltech.smarthome.databinding.DialogDeviceIconSelectorBindingImpl;
import com.ltech.smarthome.databinding.DialogDimDepthSelectorBindingImpl;
import com.ltech.smarthome.databinding.DialogE6TipBindingImpl;
import com.ltech.smarthome.databinding.DialogEditBindingImpl;
import com.ltech.smarthome.databinding.DialogEditCopyBindingImpl;
import com.ltech.smarthome.databinding.DialogEditDeviceBindingImpl;
import com.ltech.smarthome.databinding.DialogEurFunctionAndRgbBindingImpl;
import com.ltech.smarthome.databinding.DialogEurFunctionBindingImpl;
import com.ltech.smarthome.databinding.DialogExecuteDelayTimeSelectorBindingImpl;
import com.ltech.smarthome.databinding.DialogExecuteTimeSelectorBindingImpl;
import com.ltech.smarthome.databinding.DialogFloorHeatQuickBindingImpl;
import com.ltech.smarthome.databinding.DialogFrequencyIntervalBindingImpl;
import com.ltech.smarthome.databinding.DialogFreshAirQuickBindingImpl;
import com.ltech.smarthome.databinding.DialogGradualTimeBindingImpl;
import com.ltech.smarthome.databinding.DialogImageTipBindingImpl;
import com.ltech.smarthome.databinding.DialogIntercomTimePickerBindingImpl;
import com.ltech.smarthome.databinding.DialogIrFunBindingImpl;
import com.ltech.smarthome.databinding.DialogIrLearnBindingImpl;
import com.ltech.smarthome.databinding.DialogIrQuickBindingImpl;
import com.ltech.smarthome.databinding.DialogLearnInstructBindingImpl;
import com.ltech.smarthome.databinding.DialogLeftTitleSelectListBindingImpl;
import com.ltech.smarthome.databinding.DialogLightQuickBindingImpl;
import com.ltech.smarthome.databinding.DialogListBindingImpl;
import com.ltech.smarthome.databinding.DialogLoadingBindingImpl;
import com.ltech.smarthome.databinding.DialogLoadingProgressBindingImpl;
import com.ltech.smarthome.databinding.DialogLoadingSuccessBindingImpl;
import com.ltech.smarthome.databinding.DialogLogCalendarBindingImpl;
import com.ltech.smarthome.databinding.DialogMatterQrcodeBindingImpl;
import com.ltech.smarthome.databinding.DialogMsTimeSelectorBindingImpl;
import com.ltech.smarthome.databinding.DialogMusicListBindingImpl;
import com.ltech.smarthome.databinding.DialogMusicTimeBindingImpl;
import com.ltech.smarthome.databinding.DialogMusicVipTipBindingImpl;
import com.ltech.smarthome.databinding.DialogMusicVolumeBindingImpl;
import com.ltech.smarthome.databinding.DialogNumberEditBindingImpl;
import com.ltech.smarthome.databinding.DialogPairBindingImpl;
import com.ltech.smarthome.databinding.DialogPanelBrtBindingImpl;
import com.ltech.smarthome.databinding.DialogPowerStateBatchBindingImpl;
import com.ltech.smarthome.databinding.DialogPowerStateSelectorBindingImpl;
import com.ltech.smarthome.databinding.DialogProgressBindingImpl;
import com.ltech.smarthome.databinding.DialogRc4sTipBindingImpl;
import com.ltech.smarthome.databinding.DialogRegModeBindingImpl;
import com.ltech.smarthome.databinding.DialogResultBindingImpl;
import com.ltech.smarthome.databinding.DialogRgbFunctionTipBindingImpl;
import com.ltech.smarthome.databinding.DialogRoomPickerBindingImpl;
import com.ltech.smarthome.databinding.DialogRoomSelectorBindingImpl;
import com.ltech.smarthome.databinding.DialogScanNfcBindingImpl;
import com.ltech.smarthome.databinding.DialogSelectBrtBindingImpl;
import com.ltech.smarthome.databinding.DialogSelectCgdActionBindingImpl;
import com.ltech.smarthome.databinding.DialogSelectColorBindingImpl;
import com.ltech.smarthome.databinding.DialogSelectCtBindingImpl;
import com.ltech.smarthome.databinding.DialogSelectDimCurveBindingImpl;
import com.ltech.smarthome.databinding.DialogSelectDimFadeTimeBindingImpl;
import com.ltech.smarthome.databinding.DialogSelectDimRangeBindingImpl;
import com.ltech.smarthome.databinding.DialogSelectGqThemeBindingImpl;
import com.ltech.smarthome.databinding.DialogSelectItemBindingImpl;
import com.ltech.smarthome.databinding.DialogSelectKDuvBindingImpl;
import com.ltech.smarthome.databinding.DialogSelectListAndPicBindingImpl;
import com.ltech.smarthome.databinding.DialogSelectListBindingImpl;
import com.ltech.smarthome.databinding.DialogSelectLuxBindingImpl;
import com.ltech.smarthome.databinding.DialogSelectSceneBindingImpl;
import com.ltech.smarthome.databinding.DialogSelectVolumeBindingImpl;
import com.ltech.smarthome.databinding.DialogSensingDistanceSettingBindingImpl;
import com.ltech.smarthome.databinding.DialogSetBleTypeBindingImpl;
import com.ltech.smarthome.databinding.DialogSetScreenDisplayBindingImpl;
import com.ltech.smarthome.databinding.DialogSinglePickerBindingImpl;
import com.ltech.smarthome.databinding.DialogSpQuickBindingImpl;
import com.ltech.smarthome.databinding.DialogSwitchPatternSelectorBindingImpl;
import com.ltech.smarthome.databinding.DialogTePanelBindingImpl;
import com.ltech.smarthome.databinding.DialogTimeIntervalSelectorBindingImpl;
import com.ltech.smarthome.databinding.DialogTimePickerBindingImpl;
import com.ltech.smarthome.databinding.DialogTimeSelectorBindingImpl;
import com.ltech.smarthome.databinding.DialogTimeSelectorWithLimitBindingImpl;
import com.ltech.smarthome.databinding.DialogTimeSelectorWithMsBindingImpl;
import com.ltech.smarthome.databinding.DialogTimingSetBindingImpl;
import com.ltech.smarthome.databinding.DialogTipBindingImpl;
import com.ltech.smarthome.databinding.DialogWheelSelectDoubleListBindingImpl;
import com.ltech.smarthome.databinding.DialogWheelSelectListBindingImpl;
import com.ltech.smarthome.databinding.DialogWhiteBalanceBindingImpl;
import com.ltech.smarthome.databinding.DialogWyBindingImpl;
import com.ltech.smarthome.databinding.FooterAddBindingImpl;
import com.ltech.smarthome.databinding.FooterSuperPanelKeySetBindingImpl;
import com.ltech.smarthome.databinding.FtAcBindingImpl;
import com.ltech.smarthome.databinding.FtAcDialogBindingImpl;
import com.ltech.smarthome.databinding.FtAccessControlBindingImpl;
import com.ltech.smarthome.databinding.FtAdvancedModeBindingImpl;
import com.ltech.smarthome.databinding.FtAirBindingImpl;
import com.ltech.smarthome.databinding.FtAirDialogBindingImpl;
import com.ltech.smarthome.databinding.FtAutomationBindingImpl;
import com.ltech.smarthome.databinding.FtBindMailBindingImpl;
import com.ltech.smarthome.databinding.FtBindPhoneBindingImpl;
import com.ltech.smarthome.databinding.FtCameraPtzBindingImpl;
import com.ltech.smarthome.databinding.FtCameraRecordBindingImpl;
import com.ltech.smarthome.databinding.FtCameraTalkBindingImpl;
import com.ltech.smarthome.databinding.FtClipPhotoBindingImpl;
import com.ltech.smarthome.databinding.FtCloudSceneBindingImpl;
import com.ltech.smarthome.databinding.FtColorCctBindingImpl;
import com.ltech.smarthome.databinding.FtColorCircleBindingImpl;
import com.ltech.smarthome.databinding.FtColorHslBindingImpl;
import com.ltech.smarthome.databinding.FtColorPushrodBindingImpl;
import com.ltech.smarthome.databinding.FtColorXyyBindingImpl;
import com.ltech.smarthome.databinding.FtCtLightBindingImpl;
import com.ltech.smarthome.databinding.FtDaliAddBindingImpl;
import com.ltech.smarthome.databinding.FtDaliPushrodBindingImpl;
import com.ltech.smarthome.databinding.FtDevice2BindingImpl;
import com.ltech.smarthome.databinding.FtDeviceAndGroupBindingImpl;
import com.ltech.smarthome.databinding.FtDimLightBindingImpl;
import com.ltech.smarthome.databinding.FtFindMailPwdBindingImpl;
import com.ltech.smarthome.databinding.FtFindPhonePwdBindingImpl;
import com.ltech.smarthome.databinding.FtGeneralModeBindingImpl;
import com.ltech.smarthome.databinding.FtGqxActionBindingImpl;
import com.ltech.smarthome.databinding.FtIntelligenceBindingImpl;
import com.ltech.smarthome.databinding.FtListBindingImpl;
import com.ltech.smarthome.databinding.FtLocalSceneBindingImpl;
import com.ltech.smarthome.databinding.FtLogBindingImpl;
import com.ltech.smarthome.databinding.FtMailRegBindingImpl;
import com.ltech.smarthome.databinding.FtMessageHomeBindingImpl;
import com.ltech.smarthome.databinding.FtMessageNoticeBindingImpl;
import com.ltech.smarthome.databinding.FtMicBindingImpl;
import com.ltech.smarthome.databinding.FtMusicBindingImpl;
import com.ltech.smarthome.databinding.FtMyBindingImpl;
import com.ltech.smarthome.databinding.FtPageScreenPanelDetailBindingImpl;
import com.ltech.smarthome.databinding.FtPageScreenPanelSwitchBindingImpl;
import com.ltech.smarthome.databinding.FtPhoneRegBindingImpl;
import com.ltech.smarthome.databinding.FtRgbLightBindingImpl;
import com.ltech.smarthome.databinding.FtRoomBindingImpl;
import com.ltech.smarthome.databinding.FtRoomDaliAddBindingImpl;
import com.ltech.smarthome.databinding.FtSceneBindingImpl;
import com.ltech.smarthome.databinding.FtSelectIconsBindingImpl;
import com.ltech.smarthome.databinding.FtSelectProductBindingImpl;
import com.ltech.smarthome.databinding.FtSelectThemeBindingImpl;
import com.ltech.smarthome.databinding.FtSensorTestStepBindingImpl;
import com.ltech.smarthome.databinding.FtSongPlaylistBindingImpl;
import com.ltech.smarthome.databinding.FtSongsBindingImpl;
import com.ltech.smarthome.databinding.FtSpiColorBindingImpl;
import com.ltech.smarthome.databinding.FtSpiModeBindingImpl;
import com.ltech.smarthome.databinding.FtSpiPlayListBindingImpl;
import com.ltech.smarthome.databinding.FtStepsIntroductionBindingImpl;
import com.ltech.smarthome.databinding.FtSubDeviceBindingImpl;
import com.ltech.smarthome.databinding.FtTvExtFunBindingImpl;
import com.ltech.smarthome.databinding.FtTvFunBindingImpl;
import com.ltech.smarthome.databinding.FtTvNumBindingImpl;
import com.ltech.smarthome.databinding.HeadG4MaxKeySetBindingImpl;
import com.ltech.smarthome.databinding.HeadSuperPanelKeySetBindingImpl;
import com.ltech.smarthome.databinding.Item485ToBleBindingImpl;
import com.ltech.smarthome.databinding.ItemAddGroupBindingImpl;
import com.ltech.smarthome.databinding.ItemAddInstructContentBindingImpl;
import com.ltech.smarthome.databinding.ItemAddInstructTitleBindingImpl;
import com.ltech.smarthome.databinding.ItemAdvancedModeAddBindingImpl;
import com.ltech.smarthome.databinding.ItemAdvancedModeColorTimeBindingImpl;
import com.ltech.smarthome.databinding.ItemAsPanelRelatedInfoBindingImpl;
import com.ltech.smarthome.databinding.ItemAutoConditionBindingImpl;
import com.ltech.smarthome.databinding.ItemAutomationBindingImpl;
import com.ltech.smarthome.databinding.ItemBatchSettingBindingImpl;
import com.ltech.smarthome.databinding.ItemBigContentLeftBindingImpl;
import com.ltech.smarthome.databinding.ItemBigContentRightBindingImpl;
import com.ltech.smarthome.databinding.ItemBindEurSceneBindingImpl;
import com.ltech.smarthome.databinding.ItemBindZoneBindingImpl;
import com.ltech.smarthome.databinding.ItemBleTo485BindingImpl;
import com.ltech.smarthome.databinding.ItemBtOtaBindingImpl;
import com.ltech.smarthome.databinding.ItemCategoryBindingImpl;
import com.ltech.smarthome.databinding.ItemCentralAirSubDeviceBindingImpl;
import com.ltech.smarthome.databinding.ItemCg485ColorBindingImpl;
import com.ltech.smarthome.databinding.ItemCgdActionBindingImpl;
import com.ltech.smarthome.databinding.ItemCgdBindingImpl;
import com.ltech.smarthome.databinding.ItemCloudSceneBindingImpl;
import com.ltech.smarthome.databinding.ItemColorBindingImpl;
import com.ltech.smarthome.databinding.ItemColorPointBindingImpl;
import com.ltech.smarthome.databinding.ItemCommandBindingImpl;
import com.ltech.smarthome.databinding.ItemContentNormalBindingImpl;
import com.ltech.smarthome.databinding.ItemContentSmallBindingImpl;
import com.ltech.smarthome.databinding.ItemCtLightGroupControlSubDeviceBindingImpl;
import com.ltech.smarthome.databinding.ItemCurtainCurrainmotortypeSelectBindingImpl;
import com.ltech.smarthome.databinding.ItemCurtainOpenDirSelectBindingImpl;
import com.ltech.smarthome.databinding.ItemDaliBatchModifyParamBindingImpl;
import com.ltech.smarthome.databinding.ItemDaliLightManageBindingImpl;
import com.ltech.smarthome.databinding.ItemDaliManageBindingImpl;
import com.ltech.smarthome.databinding.ItemDefaultBindingImpl;
import com.ltech.smarthome.databinding.ItemDefaultModeBindingImpl;
import com.ltech.smarthome.databinding.ItemDefaultModeSelectBindingImpl;
import com.ltech.smarthome.databinding.ItemDeviceAsPanelBindingImpl;
import com.ltech.smarthome.databinding.ItemDeviceBleBindingImpl;
import com.ltech.smarthome.databinding.ItemDeviceCameraBindingImpl;
import com.ltech.smarthome.databinding.ItemDeviceCentralAirGateBindingImpl;
import com.ltech.smarthome.databinding.ItemDeviceGroupManageBindingImpl;
import com.ltech.smarthome.databinding.ItemDeviceIrBindingImpl;
import com.ltech.smarthome.databinding.ItemDeviceKeySwitchBindingImpl;
import com.ltech.smarthome.databinding.ItemDeviceLightBindingImpl;
import com.ltech.smarthome.databinding.ItemDeviceLogBindingImpl;
import com.ltech.smarthome.databinding.ItemDeviceManageBindingImpl;
import com.ltech.smarthome.databinding.ItemDeviceManageNewBindingImpl;
import com.ltech.smarthome.databinding.ItemDeviceMeshGatewayBindingImpl;
import com.ltech.smarthome.databinding.ItemDeviceMusicPlayerBindingImpl;
import com.ltech.smarthome.databinding.ItemDeviceSceneBindingImpl;
import com.ltech.smarthome.databinding.ItemDeviceSelectBindingImpl;
import com.ltech.smarthome.databinding.ItemDeviceSensorBindingImpl;
import com.ltech.smarthome.databinding.ItemDeviceSortBindingImpl;
import com.ltech.smarthome.databinding.ItemDeviceSuperPanelBindingImpl;
import com.ltech.smarthome.databinding.ItemDiyModeBindingImpl;
import com.ltech.smarthome.databinding.ItemDoubleTextBindingImpl;
import com.ltech.smarthome.databinding.ItemE6PanelKeyBindingImpl;
import com.ltech.smarthome.databinding.ItemE6dActionBindingImpl;
import com.ltech.smarthome.databinding.ItemE6dAddressBindingImpl;
import com.ltech.smarthome.databinding.ItemE6dDefaultBindingImpl;
import com.ltech.smarthome.databinding.ItemEditDiyKeyNameBindingImpl;
import com.ltech.smarthome.databinding.ItemEmptyFoot15BindingImpl;
import com.ltech.smarthome.databinding.ItemEmptyFootBindingImpl;
import com.ltech.smarthome.databinding.ItemEnvStatusDetailConditionBindingImpl;
import com.ltech.smarthome.databinding.ItemFloorManageBindingImpl;
import com.ltech.smarthome.databinding.ItemFreshAirInfoBindingImpl;
import com.ltech.smarthome.databinding.ItemGeneralModeBindingImpl;
import com.ltech.smarthome.databinding.ItemGo1BindingImpl;
import com.ltech.smarthome.databinding.ItemGo2BindingImpl;
import com.ltech.smarthome.databinding.ItemGoBindingImpl;
import com.ltech.smarthome.databinding.ItemGroupDeviceSortBindingImpl;
import com.ltech.smarthome.databinding.ItemGroupLightBindingImpl;
import com.ltech.smarthome.databinding.ItemGroupManageBindingImpl;
import com.ltech.smarthome.databinding.ItemGroupSelectBindingImpl;
import com.ltech.smarthome.databinding.ItemGroupSmartPanelBindingImpl;
import com.ltech.smarthome.databinding.ItemGroupSwitchPatternBindingImpl;
import com.ltech.smarthome.databinding.ItemGroupWaveSensorBindingImpl;
import com.ltech.smarthome.databinding.ItemHomeManageBindingImpl;
import com.ltech.smarthome.databinding.ItemImgBindingImpl;
import com.ltech.smarthome.databinding.ItemImgPreviewBindingImpl;
import com.ltech.smarthome.databinding.ItemIntercomDoorBindingImpl;
import com.ltech.smarthome.databinding.ItemIntercomLogCallBindingImpl;
import com.ltech.smarthome.databinding.ItemIntercomLogOpenDoorBindingImpl;
import com.ltech.smarthome.databinding.ItemIntercomLogTitleBindingImpl;
import com.ltech.smarthome.databinding.ItemIrDiyFunBindingImpl;
import com.ltech.smarthome.databinding.ItemIrDiyKeyBindingImpl;
import com.ltech.smarthome.databinding.ItemIrFunBindingImpl;
import com.ltech.smarthome.databinding.ItemIrKeyBindingImpl;
import com.ltech.smarthome.databinding.ItemIrKeyTeBindingImpl;
import com.ltech.smarthome.databinding.ItemKAndDuvBindingImpl;
import com.ltech.smarthome.databinding.ItemLightActionBindingImpl;
import com.ltech.smarthome.databinding.ItemLightGroupControlBindingImpl;
import com.ltech.smarthome.databinding.ItemLightGroupControlSubDeviceBindingImpl;
import com.ltech.smarthome.databinding.ItemLightOrderBindingImpl;
import com.ltech.smarthome.databinding.ItemMeshScanAllDeviceBindingImpl;
import com.ltech.smarthome.databinding.ItemMeshScanBindingImpl;
import com.ltech.smarthome.databinding.ItemMeshScanReplaceBindingImpl;
import com.ltech.smarthome.databinding.ItemMeshSearchScanBindingImpl;
import com.ltech.smarthome.databinding.ItemMessageDataFooterBindingImpl;
import com.ltech.smarthome.databinding.ItemMessageNoticeBindingImpl;
import com.ltech.smarthome.databinding.ItemMessagePlaceBindingImpl;
import com.ltech.smarthome.databinding.ItemModeBindingImpl;
import com.ltech.smarthome.databinding.ItemMrParamBindingImpl;
import com.ltech.smarthome.databinding.ItemMusicBindingImpl;
import com.ltech.smarthome.databinding.ItemMusicDialogListBindingImpl;
import com.ltech.smarthome.databinding.ItemMusicListBindingImpl;
import com.ltech.smarthome.databinding.ItemMusicSearchBindingImpl;
import com.ltech.smarthome.databinding.ItemNameValueBindingImpl;
import com.ltech.smarthome.databinding.ItemNumPanelSwitchPositionSetBindingImpl;
import com.ltech.smarthome.databinding.ItemPadContentBindingImpl;
import com.ltech.smarthome.databinding.ItemPageScreenPanelBindingImpl;
import com.ltech.smarthome.databinding.ItemPageSmartPanelKeySetBindingImpl;
import com.ltech.smarthome.databinding.ItemPanelBindBindingImpl;
import com.ltech.smarthome.databinding.ItemPanelSwitchPositionSetBindingImpl;
import com.ltech.smarthome.databinding.ItemPicBindingImpl;
import com.ltech.smarthome.databinding.ItemPlaceUserBindingImpl;
import com.ltech.smarthome.databinding.ItemPlaceUserTransformPlaceBindingImpl;
import com.ltech.smarthome.databinding.ItemPlaylistManagerBindingImpl;
import com.ltech.smarthome.databinding.ItemRelateInfoBindingImpl;
import com.ltech.smarthome.databinding.ItemRgbcwDuvBindingImpl;
import com.ltech.smarthome.databinding.ItemRoomManageBindingImpl;
import com.ltech.smarthome.databinding.ItemRoomNameBindingImpl;
import com.ltech.smarthome.databinding.ItemRs8KeyBindingImpl;
import com.ltech.smarthome.databinding.ItemRs8SubDeviceBindingImpl;
import com.ltech.smarthome.databinding.ItemSceneActionBindingImpl;
import com.ltech.smarthome.databinding.ItemSceneEurBindingImpl;
import com.ltech.smarthome.databinding.ItemSceneNewBindingImpl;
import com.ltech.smarthome.databinding.ItemScenePanelKeyBindingImpl;
import com.ltech.smarthome.databinding.ItemScreenDisplayBindingImpl;
import com.ltech.smarthome.databinding.ItemScreenInfoInputBindingImpl;
import com.ltech.smarthome.databinding.ItemScreenPanelBindingImpl;
import com.ltech.smarthome.databinding.ItemScreenPanelMultLineBindingImpl;
import com.ltech.smarthome.databinding.ItemScreenSpBtnKeyBindingImpl;
import com.ltech.smarthome.databinding.ItemSearchBarBindingImpl;
import com.ltech.smarthome.databinding.ItemSearchBarNoEditBindingImpl;
import com.ltech.smarthome.databinding.ItemSearchBarNoEditMusicBindingImpl;
import com.ltech.smarthome.databinding.ItemSelect2BindingImpl;
import com.ltech.smarthome.databinding.ItemSelectAutomationBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectCloudSceneBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectCoverBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectDaliWithPlaceBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectDeviceIconBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectDeviceIconHorizontalBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectDeviceImportModeBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectDeviceInGroupBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectDeviceManageBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectDeviceWithPlaceBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectDeviceWithSyncBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectDiyBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectGatewayBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectHomeMemberBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectIconBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectKValueBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectListBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectListWhiteBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectListWithIconBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectLoopValueBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectLuxCtBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectModeCoverBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectMusicPlayerVolumeBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectOnOffTimeDiyBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectOnOffTimeNormalBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectPlaceBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectProductBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectSceneBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectSubBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectSubtextVerBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectSubtextVerOnoffDiyAllBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectSubtextVerOnoffDiyBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectSubtextVerOnoffDiyDaliBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectWithIconBindingImpl;
import com.ltech.smarthome.databinding.ItemSelectWithPlaceCg485BindingImpl;
import com.ltech.smarthome.databinding.ItemSelectWithSubBindingImpl;
import com.ltech.smarthome.databinding.ItemSensorSubDeviceBindingImpl;
import com.ltech.smarthome.databinding.ItemSettingBindingImpl;
import com.ltech.smarthome.databinding.ItemShareDataBindingImpl;
import com.ltech.smarthome.databinding.ItemSimpleSelectBindingImpl;
import com.ltech.smarthome.databinding.ItemSmartPanelBindingImpl;
import com.ltech.smarthome.databinding.ItemSmartPanelKeyBindingImpl;
import com.ltech.smarthome.databinding.ItemSmartPanelKeyEb6BindingImpl;
import com.ltech.smarthome.databinding.ItemSmartPanelKeySetBindingImpl;
import com.ltech.smarthome.databinding.ItemSmartSpeakerBindingImpl;
import com.ltech.smarthome.databinding.ItemSongInfoBindingImpl;
import com.ltech.smarthome.databinding.ItemSongPlaylistBindingImpl;
import com.ltech.smarthome.databinding.ItemSongPlaylistFootBindingImpl;
import com.ltech.smarthome.databinding.ItemSortBindingImpl;
import com.ltech.smarthome.databinding.ItemSpBtnKeyBindingImpl;
import com.ltech.smarthome.databinding.ItemSpiEditPlayListBindingImpl;
import com.ltech.smarthome.databinding.ItemSpiModeBindingImpl;
import com.ltech.smarthome.databinding.ItemSpiPlayListBindingImpl;
import com.ltech.smarthome.databinding.ItemSqPanelActionBindingImpl;
import com.ltech.smarthome.databinding.ItemSuperPanelFunBindingImpl;
import com.ltech.smarthome.databinding.ItemSuperPanelKeySetBindingImpl;
import com.ltech.smarthome.databinding.ItemSuperPanelSwitchBindingImpl;
import com.ltech.smarthome.databinding.ItemSwitchBindingImpl;
import com.ltech.smarthome.databinding.ItemSwitchButtonBindingImpl;
import com.ltech.smarthome.databinding.ItemTailBindingImpl;
import com.ltech.smarthome.databinding.ItemTempBindingImpl;
import com.ltech.smarthome.databinding.ItemText36BindingImpl;
import com.ltech.smarthome.databinding.ItemTextAddBindingImpl;
import com.ltech.smarthome.databinding.ItemTextBindingImpl;
import com.ltech.smarthome.databinding.ItemTextHeadBindingImpl;
import com.ltech.smarthome.databinding.ItemTextMiddleBindingImpl;
import com.ltech.smarthome.databinding.ItemTextWrapBindingImpl;
import com.ltech.smarthome.databinding.ItemTitleBindingImpl;
import com.ltech.smarthome.databinding.ItemTrigKeyBindingImpl;
import com.ltech.smarthome.databinding.ItemTrigSceneBindingImpl;
import com.ltech.smarthome.databinding.LayoutEmptyBindingImpl;
import com.ltech.smarthome.databinding.LayoutError2BindingImpl;
import com.ltech.smarthome.databinding.LayoutErrorBindingImpl;
import com.ltech.smarthome.databinding.LayoutExtBindingImpl;
import com.ltech.smarthome.databinding.LayoutLoadingBindingImpl;
import com.ltech.smarthome.databinding.LayoutProtocolDefaultBindingImpl;
import com.ltech.smarthome.databinding.LayoutRadioImageTextViewBindingImpl;
import com.ltech.smarthome.databinding.LayoutTabViewBindingImpl;
import com.ltech.smarthome.databinding.LayoutTitleDefaultBindingImpl;
import com.ltech.smarthome.databinding.LayoutTitleIrBindingImpl;
import com.ltech.smarthome.databinding.LayoutTitleTranBindingImpl;
import com.ltech.smarthome.databinding.LayoutTitleTransparentBindingImpl;
import com.ltech.smarthome.databinding.TabText2BindingImpl;
import com.ltech.smarthome.databinding.TabTextBindingImpl;
import com.ltech.smarthome.databinding.ViewBrtSettingBindingImpl;
import com.ltech.smarthome.databinding.ViewCgdActionBindingImpl;
import com.ltech.smarthome.databinding.ViewCgdLightTitleDaliBindingImpl;
import com.ltech.smarthome.databinding.ViewCgdLightTitleManageBindingImpl;
import com.ltech.smarthome.databinding.ViewCgdLightTitleSelectBindingImpl;
import com.ltech.smarthome.databinding.ViewCgdLightTitleSelectGroupBindingImpl;
import com.ltech.smarthome.databinding.ViewDaliManageBottomBindingImpl;
import com.ltech.smarthome.databinding.ViewDaliTextSeekbarBindingImpl;
import com.ltech.smarthome.databinding.ViewDaliTextSeekbarNewBindingImpl;
import com.ltech.smarthome.databinding.ViewDeviceManageBottomBindingImpl;
import com.ltech.smarthome.databinding.ViewEditTextSeekbarBindingImpl;
import com.ltech.smarthome.databinding.ViewEurTipFunctionBindingImpl;
import com.ltech.smarthome.databinding.ViewEurTipSceneBindingImpl;
import com.ltech.smarthome.databinding.ViewEurTipZoneBindingImpl;
import com.ltech.smarthome.databinding.ViewFiveCurrentItemBindingImpl;
import com.ltech.smarthome.databinding.ViewImageTextBindingImpl;
import com.ltech.smarthome.databinding.ViewLocationTipBindingImpl;
import com.ltech.smarthome.databinding.ViewNumberSettingBindingImpl;
import com.ltech.smarthome.databinding.ViewPowerStateBleAllBindingImpl;
import com.ltech.smarthome.databinding.ViewRemoteTipBindingImpl;
import com.ltech.smarthome.databinding.ViewS1ProGuideBindingImpl;
import com.ltech.smarthome.databinding.ViewS1ProGuideEnBindingImpl;
import com.ltech.smarthome.databinding.ViewS2ProGuideBindingImpl;
import com.ltech.smarthome.databinding.ViewS2ProGuideEnBindingImpl;
import com.ltech.smarthome.databinding.ViewS3ProGuideBindingImpl;
import com.ltech.smarthome.databinding.ViewS3ProGuideEnBindingImpl;
import com.ltech.smarthome.databinding.ViewSqProGuideBindingImpl;
import com.ltech.smarthome.databinding.ViewSqProGuideEnBindingImpl;
import com.ltech.smarthome.databinding.ViewSwitchSeekbarBindingImpl;
import com.ltech.smarthome.databinding.ViewSwitchTwoSeekbarBindingImpl;
import com.ltech.smarthome.databinding.ViewTextSeekbarBindingImpl;
import com.ltech.smarthome.databinding.ViewTextWithButtonBindingImpl;
import com.ltech.smarthome.utils.Constants;
import com.videogo.openapi.model.req.GetSmsCodeResetReq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes3.dex */
public class DataBinderMapperImpl extends DataBinderMapper {
    private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(745);
    private static final int LAYOUT_ACTABOUT = 1;
    private static final int LAYOUT_ACTAC = 2;
    private static final int LAYOUT_ACTACBG = 3;
    private static final int LAYOUT_ACTACCENTRAL = 4;
    private static final int LAYOUT_ACTACCOUNTANDSECURITY = 5;
    private static final int LAYOUT_ACTADDAUTOMATION = 6;
    private static final int LAYOUT_ACTADDCLOUDSCENE = 7;
    private static final int LAYOUT_ACTADDDEVICE = 8;
    private static final int LAYOUT_ACTADDDUV = 9;
    private static final int LAYOUT_ACTADDFLOOR = 10;
    private static final int LAYOUT_ACTADDINSTRUCT = 11;
    private static final int LAYOUT_ACTADDIRDEVICE = 12;
    private static final int LAYOUT_ACTADDIRKEY = 13;
    private static final int LAYOUT_ACTADDLOCALSCENE = 14;
    private static final int LAYOUT_ACTADDMODECOLOR = 15;
    private static final int LAYOUT_ACTADDMODECTDIM = 16;
    private static final int LAYOUT_ACTADDMUSIC = 17;
    private static final int LAYOUT_ACTADDPLACEUSER = 18;
    private static final int LAYOUT_ACTADDVIRTUALDEVICE = 19;
    private static final int LAYOUT_ACTASPANEL = 20;
    private static final int LAYOUT_ACTASPANELSETTING = 21;
    private static final int LAYOUT_ACTAUTONETTIMESETTING = 22;
    private static final int LAYOUT_ACTBATTERYGUIDE = 23;
    private static final int LAYOUT_ACTBLECURTAINMOTOR = 24;
    private static final int LAYOUT_ACTBLECURTAINMOTORMORESETTING = 25;
    private static final int LAYOUT_ACTBLECURTAINMOTORTYPESETTING = 26;
    private static final int LAYOUT_ACTBLEHAMSETTINGDEFAULT = 27;
    private static final int LAYOUT_ACTBLEMOTORMODESET = 28;
    private static final int LAYOUT_ACTBLEMOTORSETTING = 29;
    private static final int LAYOUT_ACTBLEMUSICPLAYER = 30;
    private static final int LAYOUT_ACTBLEMUSICPLAYERSETTING = 31;
    private static final int LAYOUT_ACTBLETRIGCURTAINSETTING = 32;
    private static final int LAYOUT_ACTBLETRIGSCENESETTING = 33;
    private static final int LAYOUT_ACTBRTBUTTONSETTING = 34;
    private static final int LAYOUT_ACTBTOTA = 35;
    private static final int LAYOUT_ACTBTOTALOWPOWER = 36;
    private static final int LAYOUT_ACTBTOTASINGLE = 37;
    private static final int LAYOUT_ACTCAMERAINFO = 38;
    private static final int LAYOUT_ACTCAMERAPLAY = 39;
    private static final int LAYOUT_ACTCAMERAPLAYBACK = 40;
    private static final int LAYOUT_ACTCAMERASETTING = 41;
    private static final int LAYOUT_ACTCENTRALAIRGATEWAY = 42;
    private static final int LAYOUT_ACTCENTRALAIRMESHGATEWAYSETTING = 43;
    private static final int LAYOUT_ACTCENTRALAIRPROGATEWAY = 44;
    private static final int LAYOUT_ACTCG485 = 45;
    private static final int LAYOUT_ACTCG485DEVICE = 46;
    private static final int LAYOUT_ACTCG485OLD = 47;
    private static final int LAYOUT_ACTCG485SETTING = 48;
    private static final int LAYOUT_ACTCGDPROLIGHT = 49;
    private static final int LAYOUT_ACTCGDPROLIGHTSETTING = 50;
    private static final int LAYOUT_ACTCHANGEEMAIL = 51;
    private static final int LAYOUT_ACTCHANGEPHONE = 52;
    private static final int LAYOUT_ACTCHANGEPWD = 53;
    private static final int LAYOUT_ACTCHANNEL512 = 54;
    private static final int LAYOUT_ACTCHILDMCUUPGRADE = 55;
    private static final int LAYOUT_ACTCHOICELIGHTTYPE = 56;
    private static final int LAYOUT_ACTCOLORLIGHT = 57;
    private static final int LAYOUT_ACTCOLORLIGHTCC = 58;
    private static final int LAYOUT_ACTCOMMANDCATEGORY = 59;
    private static final int LAYOUT_ACTCONFIGSUCCESS = 60;
    private static final int LAYOUT_ACTCREATEHOME = 61;
    private static final int LAYOUT_ACTCTEDIT = 62;
    private static final int LAYOUT_ACTCTLIGHT = 63;
    private static final int LAYOUT_ACTCTSELECTCOLOR = 64;
    private static final int LAYOUT_ACTCURRENTSET = 65;
    private static final int LAYOUT_ACTCURRENTSETFIVE = 66;
    private static final int LAYOUT_ACTDALIBATCHMODIFYPARAM = 67;
    private static final int LAYOUT_ACTDALILIGHT = 68;
    private static final int LAYOUT_ACTDALILIGHTGROUPSETTING = 69;
    private static final int LAYOUT_ACTDALILIGHTSETTING = 70;
    private static final int LAYOUT_ACTDALISCENESETTING = 71;
    private static final int LAYOUT_ACTDALISELECT = 72;
    private static final int LAYOUT_ACTDCAMUSICDETAIL = 73;
    private static final int LAYOUT_ACTDCAMUSICHOME = 74;
    private static final int LAYOUT_ACTDCAMUSICLIST = 75;
    private static final int LAYOUT_ACTDCAWEBVIEW = 76;
    private static final int LAYOUT_ACTDEVICECONFIGFAIL = 77;
    private static final int LAYOUT_ACTDEVICECONNECT = 78;
    private static final int LAYOUT_ACTDEVICECONNECTANDROID10 = 79;
    private static final int LAYOUT_ACTDEVICEGROUPMANAGE = 80;
    private static final int LAYOUT_ACTDEVICELOG = 81;
    private static final int LAYOUT_ACTDEVICEMANAGE = 82;
    private static final int LAYOUT_ACTDEVICEREPLACE = 83;
    private static final int LAYOUT_ACTDEVICESETTINGBATTERY = 84;
    private static final int LAYOUT_ACTDEVICESETTINGDEFAULT = 85;
    private static final int LAYOUT_ACTDIMLIGHT = 86;
    private static final int LAYOUT_ACTDIMSELECTCOLOR = 87;
    private static final int LAYOUT_ACTDIYIRSETTING = 88;
    private static final int LAYOUT_ACTDIYLIGHTNAME = 89;
    private static final int LAYOUT_ACTDIYROOM = 90;
    private static final int LAYOUT_ACTDMX512SETTING = 91;
    private static final int LAYOUT_ACTDMXCHANNELSELECT = 92;
    private static final int LAYOUT_ACTDOORSENSOR = 93;
    private static final int LAYOUT_ACTDUVLIST = 94;
    private static final int LAYOUT_ACTE6PANEL = 95;
    private static final int LAYOUT_ACTE6PANELSETTING = 96;
    private static final int LAYOUT_ACTEDITADVANCEDMODE = 97;
    private static final int LAYOUT_ACTEDITCOLORDIYMODE = 98;
    private static final int LAYOUT_ACTEDITGENERALMODE = 99;
    private static final int LAYOUT_ACTEDITINSTRUCTCMD = 100;
    private static final int LAYOUT_ACTEDITKEYNAME = 101;
    private static final int LAYOUT_ACTEDITNAME = 102;
    private static final int LAYOUT_ACTEDITNUMBER = 103;
    private static final int LAYOUT_ACTENGINEERINGMODE = 104;
    private static final int LAYOUT_ACTENGINEERINGMODEONOFF = 105;
    private static final int LAYOUT_ACTENVIRONMENTLOG = 106;
    private static final int LAYOUT_ACTEURPANEL = 107;
    private static final int LAYOUT_ACTEURPANELEB6 = 108;
    private static final int LAYOUT_ACTEURPANELGROUPSETTING = 109;
    private static final int LAYOUT_ACTEURPANELSETTING = 110;
    private static final int LAYOUT_ACTFAN = 111;
    private static final int LAYOUT_ACTFEEDBACK = 112;
    private static final int LAYOUT_ACTFLOORHEAT = 113;
    private static final int LAYOUT_ACTFLOORMANAGE = 114;
    private static final int LAYOUT_ACTFRESHAIR = 115;
    private static final int LAYOUT_ACTG4MAXKEYSET = 116;
    private static final int LAYOUT_ACTGETPERMISSION = 117;
    private static final int LAYOUT_ACTGQPRO = 118;
    private static final int LAYOUT_ACTGQPROTHEME = 119;
    private static final int LAYOUT_ACTGQX = 120;
    private static final int LAYOUT_ACTGQXSETTING = 121;
    private static final int LAYOUT_ACTGROUPCURTAINSETTING = 122;
    private static final int LAYOUT_ACTGROUPMANAGE = 123;
    private static final int LAYOUT_ACTGROUPSETTING = 124;
    private static final int LAYOUT_ACTHOME = 125;
    private static final int LAYOUT_ACTHOMEKIT = 126;
    private static final int LAYOUT_ACTHOMEKITOLD = 127;
    private static final int LAYOUT_ACTHOMEKITSETTING = 128;
    private static final int LAYOUT_ACTHOMEMANAGE = 129;
    private static final int LAYOUT_ACTHOMEPOSITION = 130;
    private static final int LAYOUT_ACTHOMEQRCODE = 131;
    private static final int LAYOUT_ACTHOMESETTING = 132;
    private static final int LAYOUT_ACTHOMETRANSFERSETTING = 133;
    private static final int LAYOUT_ACTHSDSENSOR = 134;
    private static final int LAYOUT_ACTHSDSENSORSETTING = 135;
    private static final int LAYOUT_ACTIMPORTSCENEALL = 136;
    private static final int LAYOUT_ACTINSTRUCTSETTING = 137;
    private static final int LAYOUT_ACTINTELLIGENCE = 138;
    private static final int LAYOUT_ACTINTERCOM = 139;
    private static final int LAYOUT_ACTINTERCOMLOGIN = 140;
    private static final int LAYOUT_ACTINTERCOMRECORD = 141;
    private static final int LAYOUT_ACTINTERCOMSELECTCOMMUNITY = 142;
    private static final int LAYOUT_ACTINTERCOMSETFACE = 143;
    private static final int LAYOUT_ACTINTERCOMSETOPENKEY = 144;
    private static final int LAYOUT_ACTINTERCOMSETTING = 145;
    private static final int LAYOUT_ACTINTERCOMTEMPKEY = 146;
    private static final int LAYOUT_ACTINTERCOMTIPS = 147;
    private static final int LAYOUT_ACTIRDIY = 148;
    private static final int LAYOUT_ACTIRSETTING = 149;
    private static final int LAYOUT_ACTIVITYACTMESHADDALLDEVICE = 329;
    private static final int LAYOUT_ACTKBS = 150;
    private static final int LAYOUT_ACTKBSGROUPSETTING = 151;
    private static final int LAYOUT_ACTKBSSETTING = 152;
    private static final int LAYOUT_ACTKEYSWITCH = 153;
    private static final int LAYOUT_ACTKNOBPANEL = 154;
    private static final int LAYOUT_ACTKNOBPANELSETTING = 155;
    private static final int LAYOUT_ACTKNOBSCREENPANEL = 156;
    private static final int LAYOUT_ACTLIGHTGROUPSUBITEMCONTROL = 157;
    private static final int LAYOUT_ACTLIGHTONOFFTIME = 158;
    private static final int LAYOUT_ACTLIGHTPLANBATCHSET = 159;
    private static final int LAYOUT_ACTLIGHTPLANDETAIL = 160;
    private static final int LAYOUT_ACTLIGHTPLANLIST = 161;
    private static final int LAYOUT_ACTLIGHTSETTING = 162;
    private static final int LAYOUT_ACTLIGHTSETTINGNEW = 163;
    private static final int LAYOUT_ACTLOCALDEVICELOG = 164;
    private static final int LAYOUT_ACTLOCATIONPERMISSIONDESCRIPTION = 165;
    private static final int LAYOUT_ACTLOGIN = 166;
    private static final int LAYOUT_ACTMAP = 167;
    private static final int LAYOUT_ACTMATTERLIST = 168;
    private static final int LAYOUT_ACTMATTERPLATFORMLIST = 169;
    private static final int LAYOUT_ACTMATTERSUBLIST = 170;
    private static final int LAYOUT_ACTMESHGATEWAY = 171;
    private static final int LAYOUT_ACTMESHGATEWAYLIGHTSETTING = 172;
    private static final int LAYOUT_ACTMESHGATEWAYSETTING = 173;
    private static final int LAYOUT_ACTMESHNEARDEVICE = 174;
    private static final int LAYOUT_ACTMESHSCAN = 175;
    private static final int LAYOUT_ACTMESHSCAN1 = 177;
    private static final int LAYOUT_ACTMESHSCAN2 = 176;
    private static final int LAYOUT_ACTMESHSCANPROXY = 178;
    private static final int LAYOUT_ACTMESSAGECENTER = 179;
    private static final int LAYOUT_ACTMODE = 180;
    private static final int LAYOUT_ACTMODULESWITCH = 181;
    private static final int LAYOUT_ACTMONITOR = 182;
    private static final int LAYOUT_ACTMOTOR = 183;
    private static final int LAYOUT_ACTMOTORPAIR = 184;
    private static final int LAYOUT_ACTMUSIC = 185;
    private static final int LAYOUT_ACTMUSICLIST = 186;
    private static final int LAYOUT_ACTNETCONFIG = 187;
    private static final int LAYOUT_ACTNETCONNECT = 188;
    private static final int LAYOUT_ACTNEWASPANEL = 189;
    private static final int LAYOUT_ACTNEWSCREENPANEL = 190;
    private static final int LAYOUT_ACTNEWSMARTPANEL = 191;
    private static final int LAYOUT_ACTNFCRESTORE = 192;
    private static final int LAYOUT_ACTOPENDOORLOGDETAIL = 193;
    private static final int LAYOUT_ACTPAGESCREENPANEL = 194;
    private static final int LAYOUT_ACTPANELCOLORSET = 195;
    private static final int LAYOUT_ACTPANELGROUPSETTING = 196;
    private static final int LAYOUT_ACTPANELNIGHTGETUP = 197;
    private static final int LAYOUT_ACTPANELSWITCHPOSITIONSET = 198;
    private static final int LAYOUT_ACTPLACEUSERSETTING = 199;
    private static final int LAYOUT_ACTPLAYLISTMANAGE = 200;
    private static final int LAYOUT_ACTPRODUCTINTRODUCTION = 201;
    private static final int LAYOUT_ACTPRODUCTINTRODUCTION1 = 202;
    private static final int LAYOUT_ACTPUBLIST = 203;
    private static final int LAYOUT_ACTQRCODESCAN = 204;
    private static final int LAYOUT_ACTQRCODESCANRESULT = 205;
    private static final int LAYOUT_ACTR8SETTING = 206;
    private static final int LAYOUT_ACTRC4S = 207;
    private static final int LAYOUT_ACTRECORDLIST = 208;
    private static final int LAYOUT_ACTREGISTER = 209;
    private static final int LAYOUT_ACTREMOTEBATTERY = 210;
    private static final int LAYOUT_ACTREPLACE = 211;
    private static final int LAYOUT_ACTRGBWAFSELECT = 212;
    private static final int LAYOUT_ACTROOMMANAGE = 213;
    private static final int LAYOUT_ACTRS8 = 214;
    private static final int LAYOUT_ACTRS8ADDRESSWRITE = 216;
    private static final int LAYOUT_ACTRS8ADDSUBDEVICE = 215;
    private static final int LAYOUT_ACTRS8CURTAIN = 217;
    private static final int LAYOUT_ACTRS8SUBDEVICESETTING = 218;
    private static final int LAYOUT_ACTSAVEQRCODE = 219;
    private static final int LAYOUT_ACTSCENEPANEL = 220;
    private static final int LAYOUT_ACTSCREENPANEL = 221;
    private static final int LAYOUT_ACTSCREENPANELELDERLYMODE = 222;
    private static final int LAYOUT_ACTSEARCHAUTOMATION = 223;
    private static final int LAYOUT_ACTSEARCHDEVICE = 224;
    private static final int LAYOUT_ACTSEARCHGOOGLEPOSITION = 225;
    private static final int LAYOUT_ACTSEARCHMUSIC = 226;
    private static final int LAYOUT_ACTSEARCHPOSITION = 227;
    private static final int LAYOUT_ACTSEARCHSCENE = 228;
    private static final int LAYOUT_ACTSELECT = 229;
    private static final int LAYOUT_ACTSELECT2 = 230;
    private static final int LAYOUT_ACTSELECT3 = 231;
    private static final int LAYOUT_ACTSELECT4 = 232;
    private static final int LAYOUT_ACTSELECTBLECURTAINACTION = 233;
    private static final int LAYOUT_ACTSELECTBRAND = 234;
    private static final int LAYOUT_ACTSELECTCOLOR = 235;
    private static final int LAYOUT_ACTSELECTCOLORCC = 236;
    private static final int LAYOUT_ACTSELECTCONDITIONDEVICE = 237;
    private static final int LAYOUT_ACTSELECTCOUNTRY = 238;
    private static final int LAYOUT_ACTSELECTDALIADD = 239;
    private static final int LAYOUT_ACTSELECTDALICOLOR = 240;
    private static final int LAYOUT_ACTSELECTDIVIDE = 241;
    private static final int LAYOUT_ACTSELECTEFFECTPERIOD = 242;
    private static final int LAYOUT_ACTSELECTHOMEMEMBER = 243;
    private static final int LAYOUT_ACTSELECTLIGHT = 244;
    private static final int LAYOUT_ACTSELECTLIST = 245;
    private static final int LAYOUT_ACTSELECTMULTITYPE = 246;
    private static final int LAYOUT_ACTSELECTOPERATORS = 247;
    private static final int LAYOUT_ACTSELECTSCENE = 248;
    private static final int LAYOUT_ACTSELECTSCENEALL = 249;
    private static final int LAYOUT_ACTSELECTSONOSACTION = 250;
    private static final int LAYOUT_ACTSELECTSPIFORACTION = 251;
    private static final int LAYOUT_ACTSELECTSUPERPANELMUSIC = 252;
    private static final int LAYOUT_ACTSELECTTEMPERATUREWEATHER = 253;
    private static final int LAYOUT_ACTSELECTTHEMEMODE = 254;
    private static final int LAYOUT_ACTSELECTTIME = 255;
    private static final int LAYOUT_ACTSELECTVOICESPEAK = 256;
    private static final int LAYOUT_ACTSELECTWEATHER = 257;
    private static final int LAYOUT_ACTSENSESETTING = 258;
    private static final int LAYOUT_ACTSENSORNOBOBYTEST = 259;
    private static final int LAYOUT_ACTSENSORSETTING = 260;
    private static final int LAYOUT_ACTSERIALSETTING = 261;
    private static final int LAYOUT_ACTSETLIGHTCHANNEL = 262;
    private static final int LAYOUT_ACTSETSCREENDISPLAY = 263;
    private static final int LAYOUT_ACTSHAREDUVLIST = 264;
    private static final int LAYOUT_ACTSMARTPANEL = 265;
    private static final int LAYOUT_ACTSMARTPANELCHILDSETTING = 266;
    private static final int LAYOUT_ACTSMARTPANELGROUPCHILDSETTING = 267;
    private static final int LAYOUT_ACTSMARTPANELKEYSET = 268;
    private static final int LAYOUT_ACTSMARTPANELSETTING = 269;
    private static final int LAYOUT_ACTSMARTPANELSWITCHSETTING = 270;
    private static final int LAYOUT_ACTSMARTPANELTHEME = 271;
    private static final int LAYOUT_ACTSMARTSPEAKER = 272;
    private static final int LAYOUT_ACTSMARTSPEAKERDETAIL = 273;
    private static final int LAYOUT_ACTSONGS = 274;
    private static final int LAYOUT_ACTSONOSMUSICDETAIL = 275;
    private static final int LAYOUT_ACTSONOSSETTINGDEFAULT = 276;
    private static final int LAYOUT_ACTSONOSWEBVIEW = 277;
    private static final int LAYOUT_ACTSORT = 278;
    private static final int LAYOUT_ACTSORTLOCALMUSIC = 279;
    private static final int LAYOUT_ACTSP485LIST = 280;
    private static final int LAYOUT_ACTSPICONTROLLER = 281;
    private static final int LAYOUT_ACTSPICONTROLLERSETTING = 282;
    private static final int LAYOUT_ACTSPIEDITPLAYLIST = 283;
    private static final int LAYOUT_ACTSPILIGHTSETTING = 284;
    private static final int LAYOUT_ACTSPLASH = 285;
    private static final int LAYOUT_ACTSTEPSINTRODUCTION = 286;
    private static final int LAYOUT_ACTSUBDEVICESETTINGDEFAULT = 287;
    private static final int LAYOUT_ACTSUPERPANEL = 288;
    private static final int LAYOUT_ACTSUPERPANELALBUM = 289;
    private static final int LAYOUT_ACTSUPERPANELCLIPPHOTO = 290;
    private static final int LAYOUT_ACTSUPERPANELCONTINOUSTALK = 291;
    private static final int LAYOUT_ACTSUPERPANELIRREMOTECONTROL = 292;
    private static final int LAYOUT_ACTSUPERPANELKEYSET = 293;
    private static final int LAYOUT_ACTSUPERPANELKEYSET6S = 294;
    private static final int LAYOUT_ACTSUPERPANELPREVIEWPHOTO = 295;
    private static final int LAYOUT_ACTSUPERPANELSELECTPHOTO = 296;
    private static final int LAYOUT_ACTSUPERPANELSETTING = 297;
    private static final int LAYOUT_ACTSUPERPANELVOICECONTROLRANGE = 298;
    private static final int LAYOUT_ACTSUPERPANELVOICECONTROLRANGEROLE = 299;
    private static final int LAYOUT_ACTSUPERPANELVOICETALK = 300;
    private static final int LAYOUT_ACTTEPANEL = 301;
    private static final int LAYOUT_ACTTESTMODEMAIN = 302;
    private static final int LAYOUT_ACTTESTPREPARE = 303;
    private static final int LAYOUT_ACTTESTSTEP = 304;
    private static final int LAYOUT_ACTTHEMEDOWNLOAD = 305;
    private static final int LAYOUT_ACTTRANSFERMUSIC = 306;
    private static final int LAYOUT_ACTTRIG = 307;
    private static final int LAYOUT_ACTTRIGCURTAIN = 308;
    private static final int LAYOUT_ACTTRIGCURTAINCHANNELSET = 309;
    private static final int LAYOUT_ACTTRIGCURTAINGROUPSETTING = 310;
    private static final int LAYOUT_ACTTRIGCURTAINOPENDIRSET = 311;
    private static final int LAYOUT_ACTTRIGTOBLE = 312;
    private static final int LAYOUT_ACTTV = 313;
    private static final int LAYOUT_ACTUPGRADE = 314;
    private static final int LAYOUT_ACTUSERINFO = 315;
    private static final int LAYOUT_ACTVOICECALLBIND = 316;
    private static final int LAYOUT_ACTVOICECALLGROPLIST = 317;
    private static final int LAYOUT_ACTVOICECALLGROUPADD = 318;
    private static final int LAYOUT_ACTVOICECALLSETTING = 319;
    private static final int LAYOUT_ACTVOICECALLWHITELISTADD = 320;
    private static final int LAYOUT_ACTWAVESENSOR = 321;
    private static final int LAYOUT_ACTWAVESENSOREFFECTPERIOD = 322;
    private static final int LAYOUT_ACTWAVESENSORGROUPSETTING = 323;
    private static final int LAYOUT_ACTWAVESENSORPRO = 324;
    private static final int LAYOUT_ACTWAVESENSORSETTING = 325;
    private static final int LAYOUT_ACTWEBVIEW = 326;
    private static final int LAYOUT_ACTWELCOME = 327;
    private static final int LAYOUT_ACTXYSELECT = 328;
    private static final int LAYOUT_DIALOG512CHANNELSETTING = 330;
    private static final int LAYOUT_DIALOGACQUICK = 331;
    private static final int LAYOUT_DIALOGADDCG485CATEGORY = 332;
    private static final int LAYOUT_DIALOGADDCG485DEVICE = 333;
    private static final int LAYOUT_DIALOGADDCOLORPOINT = 334;
    private static final int LAYOUT_DIALOGADDGROUP = 335;
    private static final int LAYOUT_DIALOGADDSCENE = 336;
    private static final int LAYOUT_DIALOGBUTTONTIP = 337;
    private static final int LAYOUT_DIALOGCALENDAR = 338;
    private static final int LAYOUT_DIALOGCALLINVITE = 339;
    private static final int LAYOUT_DIALOGCENTERDALIMODIFYPARAM = 340;
    private static final int LAYOUT_DIALOGCENTERSELECTLIST = 341;
    private static final int LAYOUT_DIALOGCENTERTIP = 342;
    private static final int LAYOUT_DIALOGCITYPICKER = 343;
    private static final int LAYOUT_DIALOGCOLORBRTCONTROL = 344;
    private static final int LAYOUT_DIALOGDALIDETECT = 345;
    private static final int LAYOUT_DIALOGDALIDETECTTIP = 346;
    private static final int LAYOUT_DIALOGDALILOADDATA = 347;
    private static final int LAYOUT_DIALOGDELFAIL = 348;
    private static final int LAYOUT_DIALOGDEVICEICONSELECTOR = 349;
    private static final int LAYOUT_DIALOGDIMDEPTHSELECTOR = 350;
    private static final int LAYOUT_DIALOGE6TIP = 351;
    private static final int LAYOUT_DIALOGEDIT = 352;
    private static final int LAYOUT_DIALOGEDITCOPY = 353;
    private static final int LAYOUT_DIALOGEDITDEVICE = 354;
    private static final int LAYOUT_DIALOGEURFUNCTION = 355;
    private static final int LAYOUT_DIALOGEURFUNCTIONANDRGB = 356;
    private static final int LAYOUT_DIALOGEXECUTEDELAYTIMESELECTOR = 357;
    private static final int LAYOUT_DIALOGEXECUTETIMESELECTOR = 358;
    private static final int LAYOUT_DIALOGFLOORHEATQUICK = 359;
    private static final int LAYOUT_DIALOGFREQUENCYINTERVAL = 360;
    private static final int LAYOUT_DIALOGFRESHAIRQUICK = 361;
    private static final int LAYOUT_DIALOGGRADUALTIME = 362;
    private static final int LAYOUT_DIALOGIMAGETIP = 363;
    private static final int LAYOUT_DIALOGINTERCOMTIMEPICKER = 364;
    private static final int LAYOUT_DIALOGIRFUN = 365;
    private static final int LAYOUT_DIALOGIRLEARN = 366;
    private static final int LAYOUT_DIALOGIRQUICK = 367;
    private static final int LAYOUT_DIALOGLEARNINSTRUCT = 368;
    private static final int LAYOUT_DIALOGLEFTTITLESELECTLIST = 369;
    private static final int LAYOUT_DIALOGLIGHTQUICK = 370;
    private static final int LAYOUT_DIALOGLIST = 371;
    private static final int LAYOUT_DIALOGLOADING = 372;
    private static final int LAYOUT_DIALOGLOADINGPROGRESS = 373;
    private static final int LAYOUT_DIALOGLOADINGSUCCESS = 374;
    private static final int LAYOUT_DIALOGLOGCALENDAR = 375;
    private static final int LAYOUT_DIALOGMATTERQRCODE = 376;
    private static final int LAYOUT_DIALOGMSTIMESELECTOR = 377;
    private static final int LAYOUT_DIALOGMUSICLIST = 378;
    private static final int LAYOUT_DIALOGMUSICTIME = 379;
    private static final int LAYOUT_DIALOGMUSICVIPTIP = 380;
    private static final int LAYOUT_DIALOGMUSICVOLUME = 381;
    private static final int LAYOUT_DIALOGNUMBEREDIT = 382;
    private static final int LAYOUT_DIALOGPAIR = 383;
    private static final int LAYOUT_DIALOGPANELBRT = 384;
    private static final int LAYOUT_DIALOGPOWERSTATEBATCH = 385;
    private static final int LAYOUT_DIALOGPOWERSTATESELECTOR = 386;
    private static final int LAYOUT_DIALOGPROGRESS = 387;
    private static final int LAYOUT_DIALOGRC4STIP = 388;
    private static final int LAYOUT_DIALOGREGMODE = 389;
    private static final int LAYOUT_DIALOGRESULT = 390;
    private static final int LAYOUT_DIALOGRGBFUNCTIONTIP = 391;
    private static final int LAYOUT_DIALOGROOMPICKER = 392;
    private static final int LAYOUT_DIALOGROOMSELECTOR = 393;
    private static final int LAYOUT_DIALOGSCANNFC = 394;
    private static final int LAYOUT_DIALOGSELECTBRT = 395;
    private static final int LAYOUT_DIALOGSELECTCGDACTION = 396;
    private static final int LAYOUT_DIALOGSELECTCOLOR = 397;
    private static final int LAYOUT_DIALOGSELECTCT = 398;
    private static final int LAYOUT_DIALOGSELECTDIMCURVE = 399;
    private static final int LAYOUT_DIALOGSELECTDIMFADETIME = 400;
    private static final int LAYOUT_DIALOGSELECTDIMRANGE = 401;
    private static final int LAYOUT_DIALOGSELECTGQTHEME = 402;
    private static final int LAYOUT_DIALOGSELECTITEM = 403;
    private static final int LAYOUT_DIALOGSELECTKDUV = 404;
    private static final int LAYOUT_DIALOGSELECTLIST = 405;
    private static final int LAYOUT_DIALOGSELECTLISTANDPIC = 406;
    private static final int LAYOUT_DIALOGSELECTLUX = 407;
    private static final int LAYOUT_DIALOGSELECTSCENE = 408;
    private static final int LAYOUT_DIALOGSELECTVOLUME = 409;
    private static final int LAYOUT_DIALOGSENSINGDISTANCESETTING = 410;
    private static final int LAYOUT_DIALOGSETBLETYPE = 411;
    private static final int LAYOUT_DIALOGSETSCREENDISPLAY = 412;
    private static final int LAYOUT_DIALOGSINGLEPICKER = 413;
    private static final int LAYOUT_DIALOGSPQUICK = 414;
    private static final int LAYOUT_DIALOGSWITCHPATTERNSELECTOR = 415;
    private static final int LAYOUT_DIALOGTEPANEL = 416;
    private static final int LAYOUT_DIALOGTIMEINTERVALSELECTOR = 417;
    private static final int LAYOUT_DIALOGTIMEPICKER = 418;
    private static final int LAYOUT_DIALOGTIMESELECTOR = 419;
    private static final int LAYOUT_DIALOGTIMESELECTORWITHLIMIT = 420;
    private static final int LAYOUT_DIALOGTIMESELECTORWITHMS = 421;
    private static final int LAYOUT_DIALOGTIMINGSET = 422;
    private static final int LAYOUT_DIALOGTIP = 423;
    private static final int LAYOUT_DIALOGWHEELSELECTDOUBLELIST = 424;
    private static final int LAYOUT_DIALOGWHEELSELECTLIST = 425;
    private static final int LAYOUT_DIALOGWHITEBALANCE = 426;
    private static final int LAYOUT_DIALOGWY = 427;
    private static final int LAYOUT_FOOTERADD = 428;
    private static final int LAYOUT_FOOTERSUPERPANELKEYSET = 429;
    private static final int LAYOUT_FTAC = 430;
    private static final int LAYOUT_FTACCESSCONTROL = 432;
    private static final int LAYOUT_FTACDIALOG = 431;
    private static final int LAYOUT_FTADVANCEDMODE = 433;
    private static final int LAYOUT_FTAIR = 434;
    private static final int LAYOUT_FTAIRDIALOG = 435;
    private static final int LAYOUT_FTAUTOMATION = 436;
    private static final int LAYOUT_FTBINDMAIL = 437;
    private static final int LAYOUT_FTBINDPHONE = 438;
    private static final int LAYOUT_FTCAMERAPTZ = 439;
    private static final int LAYOUT_FTCAMERARECORD = 440;
    private static final int LAYOUT_FTCAMERATALK = 441;
    private static final int LAYOUT_FTCLIPPHOTO = 442;
    private static final int LAYOUT_FTCLOUDSCENE = 443;
    private static final int LAYOUT_FTCOLORCCT = 444;
    private static final int LAYOUT_FTCOLORCIRCLE = 445;
    private static final int LAYOUT_FTCOLORHSL = 446;
    private static final int LAYOUT_FTCOLORPUSHROD = 447;
    private static final int LAYOUT_FTCOLORXYY = 448;
    private static final int LAYOUT_FTCTLIGHT = 449;
    private static final int LAYOUT_FTDALIADD = 450;
    private static final int LAYOUT_FTDALIPUSHROD = 451;
    private static final int LAYOUT_FTDEVICE2 = 452;
    private static final int LAYOUT_FTDEVICEANDGROUP = 453;
    private static final int LAYOUT_FTDIMLIGHT = 454;
    private static final int LAYOUT_FTFINDMAILPWD = 455;
    private static final int LAYOUT_FTFINDPHONEPWD = 456;
    private static final int LAYOUT_FTGENERALMODE = 457;
    private static final int LAYOUT_FTGQXACTION = 458;
    private static final int LAYOUT_FTINTELLIGENCE = 459;
    private static final int LAYOUT_FTLIST = 460;
    private static final int LAYOUT_FTLOCALSCENE = 461;
    private static final int LAYOUT_FTLOG = 462;
    private static final int LAYOUT_FTMAILREG = 463;
    private static final int LAYOUT_FTMESSAGEHOME = 464;
    private static final int LAYOUT_FTMESSAGENOTICE = 465;
    private static final int LAYOUT_FTMIC = 466;
    private static final int LAYOUT_FTMUSIC = 467;
    private static final int LAYOUT_FTMY = 468;
    private static final int LAYOUT_FTPAGESCREENPANELDETAIL = 469;
    private static final int LAYOUT_FTPAGESCREENPANELSWITCH = 470;
    private static final int LAYOUT_FTPHONEREG = 471;
    private static final int LAYOUT_FTRGBLIGHT = 472;
    private static final int LAYOUT_FTROOM = 473;
    private static final int LAYOUT_FTROOMDALIADD = 474;
    private static final int LAYOUT_FTSCENE = 475;
    private static final int LAYOUT_FTSELECTICONS = 476;
    private static final int LAYOUT_FTSELECTPRODUCT = 477;
    private static final int LAYOUT_FTSELECTTHEME = 478;
    private static final int LAYOUT_FTSENSORTESTSTEP = 479;
    private static final int LAYOUT_FTSONGPLAYLIST = 480;
    private static final int LAYOUT_FTSONGS = 481;
    private static final int LAYOUT_FTSPICOLOR = 482;
    private static final int LAYOUT_FTSPIMODE = 483;
    private static final int LAYOUT_FTSPIPLAYLIST = 484;
    private static final int LAYOUT_FTSTEPSINTRODUCTION = 485;
    private static final int LAYOUT_FTSUBDEVICE = 486;
    private static final int LAYOUT_FTTVEXTFUN = 487;
    private static final int LAYOUT_FTTVFUN = 488;
    private static final int LAYOUT_FTTVNUM = 489;
    private static final int LAYOUT_HEADG4MAXKEYSET = 490;
    private static final int LAYOUT_HEADSUPERPANELKEYSET = 491;
    private static final int LAYOUT_ITEM485TOBLE = 492;
    private static final int LAYOUT_ITEMADDGROUP = 493;
    private static final int LAYOUT_ITEMADDINSTRUCTCONTENT = 494;
    private static final int LAYOUT_ITEMADDINSTRUCTTITLE = 495;
    private static final int LAYOUT_ITEMADVANCEDMODEADD = 496;
    private static final int LAYOUT_ITEMADVANCEDMODECOLORTIME = 497;
    private static final int LAYOUT_ITEMASPANELRELATEDINFO = 498;
    private static final int LAYOUT_ITEMAUTOCONDITION = 499;
    private static final int LAYOUT_ITEMAUTOMATION = 500;
    private static final int LAYOUT_ITEMBATCHSETTING = 501;
    private static final int LAYOUT_ITEMBIGCONTENTLEFT = 502;
    private static final int LAYOUT_ITEMBIGCONTENTRIGHT = 503;
    private static final int LAYOUT_ITEMBINDEURSCENE = 504;
    private static final int LAYOUT_ITEMBINDZONE = 505;
    private static final int LAYOUT_ITEMBLETO485 = 506;
    private static final int LAYOUT_ITEMBTOTA = 507;
    private static final int LAYOUT_ITEMCATEGORY = 508;
    private static final int LAYOUT_ITEMCENTRALAIRSUBDEVICE = 509;
    private static final int LAYOUT_ITEMCG485COLOR = 510;
    private static final int LAYOUT_ITEMCGD = 511;
    private static final int LAYOUT_ITEMCGDACTION = 512;
    private static final int LAYOUT_ITEMCLOUDSCENE = 513;
    private static final int LAYOUT_ITEMCOLOR = 514;
    private static final int LAYOUT_ITEMCOLORPOINT = 515;
    private static final int LAYOUT_ITEMCOMMAND = 516;
    private static final int LAYOUT_ITEMCONTENTNORMAL = 517;
    private static final int LAYOUT_ITEMCONTENTSMALL = 518;
    private static final int LAYOUT_ITEMCTLIGHTGROUPCONTROLSUBDEVICE = 519;
    private static final int LAYOUT_ITEMCURTAINCURRAINMOTORTYPESELECT = 520;
    private static final int LAYOUT_ITEMCURTAINOPENDIRSELECT = 521;
    private static final int LAYOUT_ITEMDALIBATCHMODIFYPARAM = 522;
    private static final int LAYOUT_ITEMDALILIGHTMANAGE = 523;
    private static final int LAYOUT_ITEMDALIMANAGE = 524;
    private static final int LAYOUT_ITEMDEFAULT = 525;
    private static final int LAYOUT_ITEMDEFAULTMODE = 526;
    private static final int LAYOUT_ITEMDEFAULTMODESELECT = 527;
    private static final int LAYOUT_ITEMDEVICEASPANEL = 528;
    private static final int LAYOUT_ITEMDEVICEBLE = 529;
    private static final int LAYOUT_ITEMDEVICECAMERA = 530;
    private static final int LAYOUT_ITEMDEVICECENTRALAIRGATE = 531;
    private static final int LAYOUT_ITEMDEVICEGROUPMANAGE = 532;
    private static final int LAYOUT_ITEMDEVICEIR = 533;
    private static final int LAYOUT_ITEMDEVICEKEYSWITCH = 534;
    private static final int LAYOUT_ITEMDEVICELIGHT = 535;
    private static final int LAYOUT_ITEMDEVICELOG = 536;
    private static final int LAYOUT_ITEMDEVICEMANAGE = 537;
    private static final int LAYOUT_ITEMDEVICEMANAGENEW = 538;
    private static final int LAYOUT_ITEMDEVICEMESHGATEWAY = 539;
    private static final int LAYOUT_ITEMDEVICEMUSICPLAYER = 540;
    private static final int LAYOUT_ITEMDEVICESCENE = 541;
    private static final int LAYOUT_ITEMDEVICESELECT = 542;
    private static final int LAYOUT_ITEMDEVICESENSOR = 543;
    private static final int LAYOUT_ITEMDEVICESORT = 544;
    private static final int LAYOUT_ITEMDEVICESUPERPANEL = 545;
    private static final int LAYOUT_ITEMDIYMODE = 546;
    private static final int LAYOUT_ITEMDOUBLETEXT = 547;
    private static final int LAYOUT_ITEME6DACTION = 549;
    private static final int LAYOUT_ITEME6DADDRESS = 550;
    private static final int LAYOUT_ITEME6DDEFAULT = 551;
    private static final int LAYOUT_ITEME6PANELKEY = 548;
    private static final int LAYOUT_ITEMEDITDIYKEYNAME = 552;
    private static final int LAYOUT_ITEMEMPTYFOOT = 553;
    private static final int LAYOUT_ITEMEMPTYFOOT15 = 554;
    private static final int LAYOUT_ITEMENVSTATUSDETAILCONDITION = 555;
    private static final int LAYOUT_ITEMFLOORMANAGE = 556;
    private static final int LAYOUT_ITEMFRESHAIRINFO = 557;
    private static final int LAYOUT_ITEMGENERALMODE = 558;
    private static final int LAYOUT_ITEMGO = 559;
    private static final int LAYOUT_ITEMGO1 = 560;
    private static final int LAYOUT_ITEMGO2 = 561;
    private static final int LAYOUT_ITEMGROUPDEVICESORT = 562;
    private static final int LAYOUT_ITEMGROUPLIGHT = 563;
    private static final int LAYOUT_ITEMGROUPMANAGE = 564;
    private static final int LAYOUT_ITEMGROUPSELECT = 565;
    private static final int LAYOUT_ITEMGROUPSMARTPANEL = 566;
    private static final int LAYOUT_ITEMGROUPSWITCHPATTERN = 567;
    private static final int LAYOUT_ITEMGROUPWAVESENSOR = 568;
    private static final int LAYOUT_ITEMHOMEMANAGE = 569;
    private static final int LAYOUT_ITEMIMG = 570;
    private static final int LAYOUT_ITEMIMGPREVIEW = 571;
    private static final int LAYOUT_ITEMINTERCOMDOOR = 572;
    private static final int LAYOUT_ITEMINTERCOMLOGCALL = 573;
    private static final int LAYOUT_ITEMINTERCOMLOGOPENDOOR = 574;
    private static final int LAYOUT_ITEMINTERCOMLOGTITLE = 575;
    private static final int LAYOUT_ITEMIRDIYFUN = 576;
    private static final int LAYOUT_ITEMIRDIYKEY = 577;
    private static final int LAYOUT_ITEMIRFUN = 578;
    private static final int LAYOUT_ITEMIRKEY = 579;
    private static final int LAYOUT_ITEMIRKEYTE = 580;
    private static final int LAYOUT_ITEMKANDDUV = 581;
    private static final int LAYOUT_ITEMLIGHTACTION = 582;
    private static final int LAYOUT_ITEMLIGHTGROUPCONTROL = 583;
    private static final int LAYOUT_ITEMLIGHTGROUPCONTROLSUBDEVICE = 584;
    private static final int LAYOUT_ITEMLIGHTORDER = 585;
    private static final int LAYOUT_ITEMMESHSCAN = 586;
    private static final int LAYOUT_ITEMMESHSCANALLDEVICE = 587;
    private static final int LAYOUT_ITEMMESHSCANREPLACE = 588;
    private static final int LAYOUT_ITEMMESHSEARCHSCAN = 589;
    private static final int LAYOUT_ITEMMESSAGEDATAFOOTER = 590;
    private static final int LAYOUT_ITEMMESSAGENOTICE = 591;
    private static final int LAYOUT_ITEMMESSAGEPLACE = 592;
    private static final int LAYOUT_ITEMMODE = 593;
    private static final int LAYOUT_ITEMMRPARAM = 594;
    private static final int LAYOUT_ITEMMUSIC = 595;
    private static final int LAYOUT_ITEMMUSICDIALOGLIST = 596;
    private static final int LAYOUT_ITEMMUSICLIST = 597;
    private static final int LAYOUT_ITEMMUSICSEARCH = 598;
    private static final int LAYOUT_ITEMNAMEVALUE = 599;
    private static final int LAYOUT_ITEMNUMPANELSWITCHPOSITIONSET = 600;
    private static final int LAYOUT_ITEMPADCONTENT = 601;
    private static final int LAYOUT_ITEMPAGESCREENPANEL = 602;
    private static final int LAYOUT_ITEMPAGESMARTPANELKEYSET = 603;
    private static final int LAYOUT_ITEMPANELBIND = 604;
    private static final int LAYOUT_ITEMPANELSWITCHPOSITIONSET = 605;
    private static final int LAYOUT_ITEMPIC = 606;
    private static final int LAYOUT_ITEMPLACEUSER = 607;
    private static final int LAYOUT_ITEMPLACEUSERTRANSFORMPLACE = 608;
    private static final int LAYOUT_ITEMPLAYLISTMANAGER = 609;
    private static final int LAYOUT_ITEMRELATEINFO = 610;
    private static final int LAYOUT_ITEMRGBCWDUV = 611;
    private static final int LAYOUT_ITEMROOMMANAGE = 612;
    private static final int LAYOUT_ITEMROOMNAME = 613;
    private static final int LAYOUT_ITEMRS8KEY = 614;
    private static final int LAYOUT_ITEMRS8SUBDEVICE = 615;
    private static final int LAYOUT_ITEMSCENEACTION = 616;
    private static final int LAYOUT_ITEMSCENEEUR = 617;
    private static final int LAYOUT_ITEMSCENENEW = 618;
    private static final int LAYOUT_ITEMSCENEPANELKEY = 619;
    private static final int LAYOUT_ITEMSCREENDISPLAY = 620;
    private static final int LAYOUT_ITEMSCREENINFOINPUT = 621;
    private static final int LAYOUT_ITEMSCREENPANEL = 622;
    private static final int LAYOUT_ITEMSCREENPANELMULTLINE = 623;
    private static final int LAYOUT_ITEMSCREENSPBTNKEY = 624;
    private static final int LAYOUT_ITEMSEARCHBAR = 625;
    private static final int LAYOUT_ITEMSEARCHBARNOEDIT = 626;
    private static final int LAYOUT_ITEMSEARCHBARNOEDITMUSIC = 627;
    private static final int LAYOUT_ITEMSELECT = 628;
    private static final int LAYOUT_ITEMSELECT2 = 629;
    private static final int LAYOUT_ITEMSELECTAUTOMATION = 630;
    private static final int LAYOUT_ITEMSELECTCLOUDSCENE = 631;
    private static final int LAYOUT_ITEMSELECTCOVER = 632;
    private static final int LAYOUT_ITEMSELECTDALIWITHPLACE = 633;
    private static final int LAYOUT_ITEMSELECTDEVICEICON = 634;
    private static final int LAYOUT_ITEMSELECTDEVICEICONHORIZONTAL = 635;
    private static final int LAYOUT_ITEMSELECTDEVICEIMPORTMODE = 636;
    private static final int LAYOUT_ITEMSELECTDEVICEINGROUP = 637;
    private static final int LAYOUT_ITEMSELECTDEVICEMANAGE = 638;
    private static final int LAYOUT_ITEMSELECTDEVICEWITHPLACE = 639;
    private static final int LAYOUT_ITEMSELECTDEVICEWITHSYNC = 640;
    private static final int LAYOUT_ITEMSELECTDIY = 641;
    private static final int LAYOUT_ITEMSELECTGATEWAY = 642;
    private static final int LAYOUT_ITEMSELECTHOMEMEMBER = 643;
    private static final int LAYOUT_ITEMSELECTICON = 644;
    private static final int LAYOUT_ITEMSELECTKVALUE = 645;
    private static final int LAYOUT_ITEMSELECTLIST = 646;
    private static final int LAYOUT_ITEMSELECTLISTWHITE = 647;
    private static final int LAYOUT_ITEMSELECTLISTWITHICON = 648;
    private static final int LAYOUT_ITEMSELECTLOOPVALUE = 649;
    private static final int LAYOUT_ITEMSELECTLUXCT = 650;
    private static final int LAYOUT_ITEMSELECTMODECOVER = 651;
    private static final int LAYOUT_ITEMSELECTMUSICPLAYERVOLUME = 652;
    private static final int LAYOUT_ITEMSELECTONOFFTIMEDIY = 653;
    private static final int LAYOUT_ITEMSELECTONOFFTIMENORMAL = 654;
    private static final int LAYOUT_ITEMSELECTPLACE = 655;
    private static final int LAYOUT_ITEMSELECTPRODUCT = 656;
    private static final int LAYOUT_ITEMSELECTSCENE = 657;
    private static final int LAYOUT_ITEMSELECTSUB = 658;
    private static final int LAYOUT_ITEMSELECTSUBTEXTVER = 659;
    private static final int LAYOUT_ITEMSELECTSUBTEXTVERONOFFDIY = 660;
    private static final int LAYOUT_ITEMSELECTSUBTEXTVERONOFFDIYALL = 661;
    private static final int LAYOUT_ITEMSELECTSUBTEXTVERONOFFDIYDALI = 662;
    private static final int LAYOUT_ITEMSELECTWITHICON = 663;
    private static final int LAYOUT_ITEMSELECTWITHPLACECG485 = 664;
    private static final int LAYOUT_ITEMSELECTWITHSUB = 665;
    private static final int LAYOUT_ITEMSENSORSUBDEVICE = 666;
    private static final int LAYOUT_ITEMSETTING = 667;
    private static final int LAYOUT_ITEMSHAREDATA = 668;
    private static final int LAYOUT_ITEMSIMPLESELECT = 669;
    private static final int LAYOUT_ITEMSMARTPANEL = 670;
    private static final int LAYOUT_ITEMSMARTPANELKEY = 671;
    private static final int LAYOUT_ITEMSMARTPANELKEYEB6 = 672;
    private static final int LAYOUT_ITEMSMARTPANELKEYSET = 673;
    private static final int LAYOUT_ITEMSMARTSPEAKER = 674;
    private static final int LAYOUT_ITEMSONGINFO = 675;
    private static final int LAYOUT_ITEMSONGPLAYLIST = 676;
    private static final int LAYOUT_ITEMSONGPLAYLISTFOOT = 677;
    private static final int LAYOUT_ITEMSORT = 678;
    private static final int LAYOUT_ITEMSPBTNKEY = 679;
    private static final int LAYOUT_ITEMSPIEDITPLAYLIST = 680;
    private static final int LAYOUT_ITEMSPIMODE = 681;
    private static final int LAYOUT_ITEMSPIPLAYLIST = 682;
    private static final int LAYOUT_ITEMSQPANELACTION = 683;
    private static final int LAYOUT_ITEMSUPERPANELFUN = 684;
    private static final int LAYOUT_ITEMSUPERPANELKEYSET = 685;
    private static final int LAYOUT_ITEMSUPERPANELSWITCH = 686;
    private static final int LAYOUT_ITEMSWITCH = 687;
    private static final int LAYOUT_ITEMSWITCHBUTTON = 688;
    private static final int LAYOUT_ITEMTAIL = 689;
    private static final int LAYOUT_ITEMTEMP = 690;
    private static final int LAYOUT_ITEMTEXT = 691;
    private static final int LAYOUT_ITEMTEXT36 = 692;
    private static final int LAYOUT_ITEMTEXTADD = 693;
    private static final int LAYOUT_ITEMTEXTHEAD = 694;
    private static final int LAYOUT_ITEMTEXTMIDDLE = 695;
    private static final int LAYOUT_ITEMTEXTWRAP = 696;
    private static final int LAYOUT_ITEMTITLE = 697;
    private static final int LAYOUT_ITEMTRIGKEY = 698;
    private static final int LAYOUT_ITEMTRIGSCENE = 699;
    private static final int LAYOUT_LAYOUTEMPTY = 700;
    private static final int LAYOUT_LAYOUTERROR = 701;
    private static final int LAYOUT_LAYOUTERROR2 = 702;
    private static final int LAYOUT_LAYOUTEXT = 703;
    private static final int LAYOUT_LAYOUTLOADING = 704;
    private static final int LAYOUT_LAYOUTPROTOCOLDEFAULT = 705;
    private static final int LAYOUT_LAYOUTRADIOIMAGETEXTVIEW = 706;
    private static final int LAYOUT_LAYOUTTABVIEW = 707;
    private static final int LAYOUT_LAYOUTTITLEDEFAULT = 708;
    private static final int LAYOUT_LAYOUTTITLEIR = 709;
    private static final int LAYOUT_LAYOUTTITLETRAN = 710;
    private static final int LAYOUT_LAYOUTTITLETRANSPARENT = 711;
    private static final int LAYOUT_TABTEXT = 712;
    private static final int LAYOUT_TABTEXT2 = 713;
    private static final int LAYOUT_VIEWBRTSETTING = 714;
    private static final int LAYOUT_VIEWCGDACTION = 715;
    private static final int LAYOUT_VIEWCGDLIGHTTITLEDALI = 716;
    private static final int LAYOUT_VIEWCGDLIGHTTITLEMANAGE = 717;
    private static final int LAYOUT_VIEWCGDLIGHTTITLESELECT = 718;
    private static final int LAYOUT_VIEWCGDLIGHTTITLESELECTGROUP = 719;
    private static final int LAYOUT_VIEWDALIMANAGEBOTTOM = 720;
    private static final int LAYOUT_VIEWDALITEXTSEEKBAR = 721;
    private static final int LAYOUT_VIEWDALITEXTSEEKBARNEW = 722;
    private static final int LAYOUT_VIEWDEVICEMANAGEBOTTOM = 723;
    private static final int LAYOUT_VIEWEDITTEXTSEEKBAR = 724;
    private static final int LAYOUT_VIEWEURTIPFUNCTION = 725;
    private static final int LAYOUT_VIEWEURTIPSCENE = 726;
    private static final int LAYOUT_VIEWEURTIPZONE = 727;
    private static final int LAYOUT_VIEWFIVECURRENTITEM = 728;
    private static final int LAYOUT_VIEWIMAGETEXT = 729;
    private static final int LAYOUT_VIEWLOCATIONTIP = 730;
    private static final int LAYOUT_VIEWNUMBERSETTING = 731;
    private static final int LAYOUT_VIEWPOWERSTATEBLEALL = 732;
    private static final int LAYOUT_VIEWREMOTETIP = 733;
    private static final int LAYOUT_VIEWS1PROGUIDE = 734;
    private static final int LAYOUT_VIEWS1PROGUIDEEN = 735;
    private static final int LAYOUT_VIEWS2PROGUIDE = 736;
    private static final int LAYOUT_VIEWS2PROGUIDEEN = 737;
    private static final int LAYOUT_VIEWS3PROGUIDE = 738;
    private static final int LAYOUT_VIEWS3PROGUIDEEN = 739;
    private static final int LAYOUT_VIEWSQPROGUIDE = 740;
    private static final int LAYOUT_VIEWSQPROGUIDEEN = 741;
    private static final int LAYOUT_VIEWSWITCHSEEKBAR = 742;
    private static final int LAYOUT_VIEWSWITCHTWOSEEKBAR = 743;
    private static final int LAYOUT_VIEWTEXTSEEKBAR = 744;
    private static final int LAYOUT_VIEWTEXTWITHBUTTON = 745;

    static {
        internalPopulateLayoutIdLookup0();
        internalPopulateLayoutIdLookup1();
    }

    private final ViewDataBinding internalGetViewDataBinding0(DataBindingComponent component, View view, int internalId, Object tag) {
        switch (internalId) {
            case 1:
                if ("layout/act_about_0".equals(tag)) {
                    return new ActAboutBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_about is invalid. Received: " + tag);
            case 2:
                if ("layout/act_ac_0".equals(tag)) {
                    return new ActAcBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_ac is invalid. Received: " + tag);
            case 3:
                if ("layout/act_ac_bg_0".equals(tag)) {
                    return new ActAcBgBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_ac_bg is invalid. Received: " + tag);
            case 4:
                if ("layout/act_ac_central_0".equals(tag)) {
                    return new ActAcCentralBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_ac_central is invalid. Received: " + tag);
            case 5:
                if ("layout/act_account_and_security_0".equals(tag)) {
                    return new ActAccountAndSecurityBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_account_and_security is invalid. Received: " + tag);
            case 6:
                if ("layout/act_add_automation_0".equals(tag)) {
                    return new ActAddAutomationBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_add_automation is invalid. Received: " + tag);
            case 7:
                if ("layout/act_add_cloud_scene_0".equals(tag)) {
                    return new ActAddCloudSceneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_add_cloud_scene is invalid. Received: " + tag);
            case 8:
                if ("layout/act_add_device_0".equals(tag)) {
                    return new ActAddDeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_add_device is invalid. Received: " + tag);
            case 9:
                if ("layout/act_add_duv_0".equals(tag)) {
                    return new ActAddDuvBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_add_duv is invalid. Received: " + tag);
            case 10:
                if ("layout/act_add_floor_0".equals(tag)) {
                    return new ActAddFloorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_add_floor is invalid. Received: " + tag);
            case 11:
                if ("layout/act_add_instruct_0".equals(tag)) {
                    return new ActAddInstructBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_add_instruct is invalid. Received: " + tag);
            case 12:
                if ("layout/act_add_ir_device_0".equals(tag)) {
                    return new ActAddIrDeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_add_ir_device is invalid. Received: " + tag);
            case 13:
                if ("layout/act_add_ir_key_0".equals(tag)) {
                    return new ActAddIrKeyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_add_ir_key is invalid. Received: " + tag);
            case 14:
                if ("layout/act_add_local_scene_0".equals(tag)) {
                    return new ActAddLocalSceneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_add_local_scene is invalid. Received: " + tag);
            case 15:
                if ("layout/act_add_mode_color_0".equals(tag)) {
                    return new ActAddModeColorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_add_mode_color is invalid. Received: " + tag);
            case 16:
                if ("layout/act_add_mode_ct_dim_0".equals(tag)) {
                    return new ActAddModeCtDimBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_add_mode_ct_dim is invalid. Received: " + tag);
            case 17:
                if ("layout/act_add_music_0".equals(tag)) {
                    return new ActAddMusicBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_add_music is invalid. Received: " + tag);
            case 18:
                if ("layout/act_add_place_user_0".equals(tag)) {
                    return new ActAddPlaceUserBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_add_place_user is invalid. Received: " + tag);
            case 19:
                if ("layout/act_add_virtual_device_0".equals(tag)) {
                    return new ActAddVirtualDeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_add_virtual_device is invalid. Received: " + tag);
            case 20:
                if ("layout/act_as_panel_0".equals(tag)) {
                    return new ActAsPanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_as_panel is invalid. Received: " + tag);
            case 21:
                if ("layout/act_as_panel_setting_0".equals(tag)) {
                    return new ActAsPanelSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_as_panel_setting is invalid. Received: " + tag);
            case 22:
                if ("layout/act_auto_net_time_setting_0".equals(tag)) {
                    return new ActAutoNetTimeSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_auto_net_time_setting is invalid. Received: " + tag);
            case 23:
                if ("layout/act_battery_guide_0".equals(tag)) {
                    return new ActBatteryGuideBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_battery_guide is invalid. Received: " + tag);
            case 24:
                if ("layout/act_ble_curtain_motor_0".equals(tag)) {
                    return new ActBleCurtainMotorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_ble_curtain_motor is invalid. Received: " + tag);
            case 25:
                if ("layout/act_ble_curtain_motor_more_setting_0".equals(tag)) {
                    return new ActBleCurtainMotorMoreSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_ble_curtain_motor_more_setting is invalid. Received: " + tag);
            case 26:
                if ("layout/act_ble_curtain_motor_type_setting_0".equals(tag)) {
                    return new ActBleCurtainMotorTypeSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_ble_curtain_motor_type_setting is invalid. Received: " + tag);
            case 27:
                if ("layout/act_ble_ham_setting_default_0".equals(tag)) {
                    return new ActBleHamSettingDefaultBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_ble_ham_setting_default is invalid. Received: " + tag);
            case 28:
                if ("layout/act_ble_motor_mode_set_0".equals(tag)) {
                    return new ActBleMotorModeSetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_ble_motor_mode_set is invalid. Received: " + tag);
            case 29:
                if ("layout/act_ble_motor_setting_0".equals(tag)) {
                    return new ActBleMotorSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_ble_motor_setting is invalid. Received: " + tag);
            case 30:
                if ("layout/act_ble_music_player_0".equals(tag)) {
                    return new ActBleMusicPlayerBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_ble_music_player is invalid. Received: " + tag);
            case 31:
                if ("layout/act_ble_music_player_setting_0".equals(tag)) {
                    return new ActBleMusicPlayerSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_ble_music_player_setting is invalid. Received: " + tag);
            case 32:
                if ("layout/act_ble_trig_curtain_setting_0".equals(tag)) {
                    return new ActBleTrigCurtainSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_ble_trig_curtain_setting is invalid. Received: " + tag);
            case 33:
                if ("layout/act_ble_trig_scene_setting_0".equals(tag)) {
                    return new ActBleTrigSceneSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_ble_trig_scene_setting is invalid. Received: " + tag);
            case 34:
                if ("layout/act_brt_button_setting_0".equals(tag)) {
                    return new ActBrtButtonSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_brt_button_setting is invalid. Received: " + tag);
            case 35:
                if ("layout/act_bt_ota_0".equals(tag)) {
                    return new ActBtOtaBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_bt_ota is invalid. Received: " + tag);
            case 36:
                if ("layout/act_bt_ota_low_power_0".equals(tag)) {
                    return new ActBtOtaLowPowerBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_bt_ota_low_power is invalid. Received: " + tag);
            case 37:
                if ("layout/act_bt_ota_single_0".equals(tag)) {
                    return new ActBtOtaSingleBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_bt_ota_single is invalid. Received: " + tag);
            case 38:
                if ("layout/act_camera_info_0".equals(tag)) {
                    return new ActCameraInfoBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_camera_info is invalid. Received: " + tag);
            case 39:
                if ("layout/act_camera_play_0".equals(tag)) {
                    return new ActCameraPlayBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_camera_play is invalid. Received: " + tag);
            case 40:
                if ("layout/act_camera_play_back_0".equals(tag)) {
                    return new ActCameraPlayBackBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_camera_play_back is invalid. Received: " + tag);
            case 41:
                if ("layout/act_camera_setting_0".equals(tag)) {
                    return new ActCameraSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_camera_setting is invalid. Received: " + tag);
            case 42:
                if ("layout/act_central_air_gateway_0".equals(tag)) {
                    return new ActCentralAirGatewayBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_central_air_gateway is invalid. Received: " + tag);
            case 43:
                if ("layout/act_central_air_mesh_gateway_setting_0".equals(tag)) {
                    return new ActCentralAirMeshGatewaySettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_central_air_mesh_gateway_setting is invalid. Received: " + tag);
            case 44:
                if ("layout/act_central_air_pro_gateway_0".equals(tag)) {
                    return new ActCentralAirProGatewayBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_central_air_pro_gateway is invalid. Received: " + tag);
            case 45:
                if ("layout/act_cg_485_0".equals(tag)) {
                    return new ActCg485BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_cg_485 is invalid. Received: " + tag);
            case 46:
                if ("layout/act_cg_485_device_0".equals(tag)) {
                    return new ActCg485DeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_cg_485_device is invalid. Received: " + tag);
            case 47:
                if ("layout/act_cg_485_old_0".equals(tag)) {
                    return new ActCg485OldBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_cg_485_old is invalid. Received: " + tag);
            case 48:
                if ("layout/act_cg_485_setting_0".equals(tag)) {
                    return new ActCg485SettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_cg_485_setting is invalid. Received: " + tag);
            case 49:
                if ("layout/act_cgd_pro_light_0".equals(tag)) {
                    return new ActCgdProLightBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_cgd_pro_light is invalid. Received: " + tag);
            case 50:
                if ("layout/act_cgd_pro_light_setting_0".equals(tag)) {
                    return new ActCgdProLightSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_cgd_pro_light_setting is invalid. Received: " + tag);
            default:
                return null;
        }
    }

    private final ViewDataBinding internalGetViewDataBinding1(DataBindingComponent component, View view, int internalId, Object tag) {
        switch (internalId) {
            case 51:
                if ("layout/act_change_email_0".equals(tag)) {
                    return new ActChangeEmailBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_change_email is invalid. Received: " + tag);
            case 52:
                if ("layout/act_change_phone_0".equals(tag)) {
                    return new ActChangePhoneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_change_phone is invalid. Received: " + tag);
            case 53:
                if ("layout/act_change_pwd_0".equals(tag)) {
                    return new ActChangePwdBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_change_pwd is invalid. Received: " + tag);
            case 54:
                if ("layout/act_channel_512_0".equals(tag)) {
                    return new ActChannel512BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_channel_512 is invalid. Received: " + tag);
            case 55:
                if ("layout/act_child_mcu_upgrade_0".equals(tag)) {
                    return new ActChildMcuUpgradeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_child_mcu_upgrade is invalid. Received: " + tag);
            case 56:
                if ("layout/act_choice_light_type_0".equals(tag)) {
                    return new ActChoiceLightTypeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_choice_light_type is invalid. Received: " + tag);
            case 57:
                if ("layout/act_color_light_0".equals(tag)) {
                    return new ActColorLightBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_color_light is invalid. Received: " + tag);
            case 58:
                if ("layout/act_color_light_cc_0".equals(tag)) {
                    return new ActColorLightCcBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_color_light_cc is invalid. Received: " + tag);
            case 59:
                if ("layout/act_command_category_0".equals(tag)) {
                    return new ActCommandCategoryBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_command_category is invalid. Received: " + tag);
            case 60:
                if ("layout/act_config_success_0".equals(tag)) {
                    return new ActConfigSuccessBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_config_success is invalid. Received: " + tag);
            case 61:
                if ("layout/act_create_home_0".equals(tag)) {
                    return new ActCreateHomeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_create_home is invalid. Received: " + tag);
            case 62:
                if ("layout/act_ct_edit_0".equals(tag)) {
                    return new ActCtEditBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_ct_edit is invalid. Received: " + tag);
            case 63:
                if ("layout/act_ct_light_0".equals(tag)) {
                    return new ActCtLightBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_ct_light is invalid. Received: " + tag);
            case 64:
                if ("layout/act_ct_select_color_0".equals(tag)) {
                    return new ActCtSelectColorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_ct_select_color is invalid. Received: " + tag);
            case 65:
                if ("layout/act_current_set_0".equals(tag)) {
                    return new ActCurrentSetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_current_set is invalid. Received: " + tag);
            case 66:
                if ("layout/act_current_set_five_0".equals(tag)) {
                    return new ActCurrentSetFiveBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_current_set_five is invalid. Received: " + tag);
            case 67:
                if ("layout/act_dali_batch_modify_param_0".equals(tag)) {
                    return new ActDaliBatchModifyParamBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_dali_batch_modify_param is invalid. Received: " + tag);
            case 68:
                if ("layout/act_dali_light_0".equals(tag)) {
                    return new ActDaliLightBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_dali_light is invalid. Received: " + tag);
            case 69:
                if ("layout/act_dali_light_group_setting_0".equals(tag)) {
                    return new ActDaliLightGroupSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_dali_light_group_setting is invalid. Received: " + tag);
            case 70:
                if ("layout/act_dali_light_setting_0".equals(tag)) {
                    return new ActDaliLightSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_dali_light_setting is invalid. Received: " + tag);
            case 71:
                if ("layout/act_dali_scene_setting_0".equals(tag)) {
                    return new ActDaliSceneSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_dali_scene_setting is invalid. Received: " + tag);
            case 72:
                if ("layout/act_dali_select_0".equals(tag)) {
                    return new ActDaliSelectBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_dali_select is invalid. Received: " + tag);
            case 73:
                if ("layout/act_dca_music_detail_0".equals(tag)) {
                    return new ActDcaMusicDetailBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_dca_music_detail is invalid. Received: " + tag);
            case 74:
                if ("layout/act_dca_music_home_0".equals(tag)) {
                    return new ActDcaMusicHomeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_dca_music_home is invalid. Received: " + tag);
            case 75:
                if ("layout/act_dca_music_list_0".equals(tag)) {
                    return new ActDcaMusicListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_dca_music_list is invalid. Received: " + tag);
            case 76:
                if ("layout/act_dca_web_view_0".equals(tag)) {
                    return new ActDcaWebViewBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_dca_web_view is invalid. Received: " + tag);
            case 77:
                if ("layout/act_device_config_fail_0".equals(tag)) {
                    return new ActDeviceConfigFailBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_device_config_fail is invalid. Received: " + tag);
            case 78:
                if ("layout/act_device_connect_0".equals(tag)) {
                    return new ActDeviceConnectBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_device_connect is invalid. Received: " + tag);
            case 79:
                if ("layout/act_device_connect_android10_0".equals(tag)) {
                    return new ActDeviceConnectAndroid10BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_device_connect_android10 is invalid. Received: " + tag);
            case 80:
                if ("layout/act_device_group_manage_0".equals(tag)) {
                    return new ActDeviceGroupManageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_device_group_manage is invalid. Received: " + tag);
            case 81:
                if ("layout/act_device_log_0".equals(tag)) {
                    return new ActDeviceLogBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_device_log is invalid. Received: " + tag);
            case 82:
                if ("layout/act_device_manage_0".equals(tag)) {
                    return new ActDeviceManageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_device_manage is invalid. Received: " + tag);
            case 83:
                if ("layout/act_device_replace_0".equals(tag)) {
                    return new ActDeviceReplaceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_device_replace is invalid. Received: " + tag);
            case 84:
                if ("layout/act_device_setting_battery_0".equals(tag)) {
                    return new ActDeviceSettingBatteryBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_device_setting_battery is invalid. Received: " + tag);
            case 85:
                if ("layout/act_device_setting_default_0".equals(tag)) {
                    return new ActDeviceSettingDefaultBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_device_setting_default is invalid. Received: " + tag);
            case 86:
                if ("layout/act_dim_light_0".equals(tag)) {
                    return new ActDimLightBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_dim_light is invalid. Received: " + tag);
            case 87:
                if ("layout/act_dim_select_color_0".equals(tag)) {
                    return new ActDimSelectColorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_dim_select_color is invalid. Received: " + tag);
            case 88:
                if ("layout/act_diy_ir_setting_0".equals(tag)) {
                    return new ActDiyIrSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_diy_ir_setting is invalid. Received: " + tag);
            case 89:
                if ("layout/act_diy_light_name_0".equals(tag)) {
                    return new ActDiyLightNameBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_diy_light_name is invalid. Received: " + tag);
            case 90:
                if ("layout/act_diy_room_0".equals(tag)) {
                    return new ActDiyRoomBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_diy_room is invalid. Received: " + tag);
            case 91:
                if ("layout/act_dmx_512_setting_0".equals(tag)) {
                    return new ActDmx512SettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_dmx_512_setting is invalid. Received: " + tag);
            case 92:
                if ("layout/act_dmx_channel_select_0".equals(tag)) {
                    return new ActDmxChannelSelectBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_dmx_channel_select is invalid. Received: " + tag);
            case 93:
                if ("layout/act_door_sensor_0".equals(tag)) {
                    return new ActDoorSensorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_door_sensor is invalid. Received: " + tag);
            case 94:
                if ("layout/act_duv_list_0".equals(tag)) {
                    return new ActDuvListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_duv_list is invalid. Received: " + tag);
            case 95:
                if ("layout/act_e6_panel_0".equals(tag)) {
                    return new ActE6PanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_e6_panel is invalid. Received: " + tag);
            case 96:
                if ("layout/act_e6_panel_setting_0".equals(tag)) {
                    return new ActE6PanelSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_e6_panel_setting is invalid. Received: " + tag);
            case 97:
                if ("layout/act_edit_advanced_mode_0".equals(tag)) {
                    return new ActEditAdvancedModeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_edit_advanced_mode is invalid. Received: " + tag);
            case 98:
                if ("layout/act_edit_color_diy_mode_0".equals(tag)) {
                    return new ActEditColorDiyModeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_edit_color_diy_mode is invalid. Received: " + tag);
            case 99:
                if ("layout/act_edit_general_mode_0".equals(tag)) {
                    return new ActEditGeneralModeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_edit_general_mode is invalid. Received: " + tag);
            case 100:
                if ("layout/act_edit_instruct_cmd_0".equals(tag)) {
                    return new ActEditInstructCmdBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_edit_instruct_cmd is invalid. Received: " + tag);
            default:
                return null;
        }
    }

    private final ViewDataBinding internalGetViewDataBinding2(DataBindingComponent component, View view, int internalId, Object tag) {
        switch (internalId) {
            case 101:
                if ("layout/act_edit_key_name_0".equals(tag)) {
                    return new ActEditKeyNameBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_edit_key_name is invalid. Received: " + tag);
            case 102:
                if ("layout/act_edit_name_0".equals(tag)) {
                    return new ActEditNameBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_edit_name is invalid. Received: " + tag);
            case 103:
                if ("layout/act_edit_number_0".equals(tag)) {
                    return new ActEditNumberBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_edit_number is invalid. Received: " + tag);
            case 104:
                if ("layout/act_engineering_mode_0".equals(tag)) {
                    return new ActEngineeringModeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_engineering_mode is invalid. Received: " + tag);
            case 105:
                if ("layout/act_engineering_mode_on_off_0".equals(tag)) {
                    return new ActEngineeringModeOnOffBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_engineering_mode_on_off is invalid. Received: " + tag);
            case 106:
                if ("layout/act_environment_log_0".equals(tag)) {
                    return new ActEnvironmentLogBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_environment_log is invalid. Received: " + tag);
            case 107:
                if ("layout/act_eur_panel_0".equals(tag)) {
                    return new ActEurPanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_eur_panel is invalid. Received: " + tag);
            case 108:
                if ("layout/act_eur_panel_eb6_0".equals(tag)) {
                    return new ActEurPanelEb6BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_eur_panel_eb6 is invalid. Received: " + tag);
            case 109:
                if ("layout/act_eur_panel_group_setting_0".equals(tag)) {
                    return new ActEurPanelGroupSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_eur_panel_group_setting is invalid. Received: " + tag);
            case 110:
                if ("layout/act_eur_panel_setting_0".equals(tag)) {
                    return new ActEurPanelSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_eur_panel_setting is invalid. Received: " + tag);
            case 111:
                if ("layout/act_fan_0".equals(tag)) {
                    return new ActFanBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_fan is invalid. Received: " + tag);
            case 112:
                if ("layout/act_feed_back_0".equals(tag)) {
                    return new ActFeedBackBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_feed_back is invalid. Received: " + tag);
            case 113:
                if ("layout/act_floor_heat_0".equals(tag)) {
                    return new ActFloorHeatBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_floor_heat is invalid. Received: " + tag);
            case 114:
                if ("layout/act_floor_manage_0".equals(tag)) {
                    return new ActFloorManageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_floor_manage is invalid. Received: " + tag);
            case 115:
                if ("layout/act_fresh_air_0".equals(tag)) {
                    return new ActFreshAirBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_fresh_air is invalid. Received: " + tag);
            case 116:
                if ("layout/act_g4_max_key_set_0".equals(tag)) {
                    return new ActG4MaxKeySetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_g4_max_key_set is invalid. Received: " + tag);
            case 117:
                if ("layout/act_get_permission_0".equals(tag)) {
                    return new ActGetPermissionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_get_permission is invalid. Received: " + tag);
            case 118:
                if ("layout/act_gq_pro_0".equals(tag)) {
                    return new ActGqProBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_gq_pro is invalid. Received: " + tag);
            case 119:
                if ("layout/act_gq_pro_theme_0".equals(tag)) {
                    return new ActGqProThemeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_gq_pro_theme is invalid. Received: " + tag);
            case 120:
                if ("layout/act_gqx_0".equals(tag)) {
                    return new ActGqxBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_gqx is invalid. Received: " + tag);
            case 121:
                if ("layout/act_gqx_setting_0".equals(tag)) {
                    return new ActGqxSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_gqx_setting is invalid. Received: " + tag);
            case 122:
                if ("layout/act_group_curtain_setting_0".equals(tag)) {
                    return new ActGroupCurtainSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_group_curtain_setting is invalid. Received: " + tag);
            case 123:
                if ("layout/act_group_manage_0".equals(tag)) {
                    return new ActGroupManageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_group_manage is invalid. Received: " + tag);
            case 124:
                if ("layout/act_group_setting_0".equals(tag)) {
                    return new ActGroupSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_group_setting is invalid. Received: " + tag);
            case 125:
                if ("layout/act_home_0".equals(tag)) {
                    return new ActHomeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_home is invalid. Received: " + tag);
            case 126:
                if ("layout/act_home_kit_0".equals(tag)) {
                    return new ActHomeKitBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_home_kit is invalid. Received: " + tag);
            case 127:
                if ("layout/act_home_kit_old_0".equals(tag)) {
                    return new ActHomeKitOldBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_home_kit_old is invalid. Received: " + tag);
            case 128:
                if ("layout/act_home_kit_setting_0".equals(tag)) {
                    return new ActHomeKitSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_home_kit_setting is invalid. Received: " + tag);
            case 129:
                if ("layout/act_home_manage_0".equals(tag)) {
                    return new ActHomeManageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_home_manage is invalid. Received: " + tag);
            case 130:
                if ("layout/act_home_position_0".equals(tag)) {
                    return new ActHomePositionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_home_position is invalid. Received: " + tag);
            case 131:
                if ("layout/act_home_qr_code_0".equals(tag)) {
                    return new ActHomeQrCodeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_home_qr_code is invalid. Received: " + tag);
            case 132:
                if ("layout/act_home_setting_0".equals(tag)) {
                    return new ActHomeSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_home_setting is invalid. Received: " + tag);
            case 133:
                if ("layout/act_home_transfer_setting_0".equals(tag)) {
                    return new ActHomeTransferSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_home_transfer_setting is invalid. Received: " + tag);
            case 134:
                if ("layout/act_hsd_sensor_0".equals(tag)) {
                    return new ActHsdSensorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_hsd_sensor is invalid. Received: " + tag);
            case 135:
                if ("layout/act_hsd_sensor_setting_0".equals(tag)) {
                    return new ActHsdSensorSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_hsd_sensor_setting is invalid. Received: " + tag);
            case 136:
                if ("layout/act_import_scene_all_0".equals(tag)) {
                    return new ActImportSceneAllBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_import_scene_all is invalid. Received: " + tag);
            case 137:
                if ("layout/act_instruct_setting_0".equals(tag)) {
                    return new ActInstructSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_instruct_setting is invalid. Received: " + tag);
            case 138:
                if ("layout/act_intelligence_0".equals(tag)) {
                    return new ActIntelligenceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_intelligence is invalid. Received: " + tag);
            case 139:
                if ("layout/act_intercom_0".equals(tag)) {
                    return new ActIntercomBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_intercom is invalid. Received: " + tag);
            case 140:
                if ("layout/act_intercom_login_0".equals(tag)) {
                    return new ActIntercomLoginBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_intercom_login is invalid. Received: " + tag);
            case 141:
                if ("layout/act_intercom_record_0".equals(tag)) {
                    return new ActIntercomRecordBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_intercom_record is invalid. Received: " + tag);
            case 142:
                if ("layout/act_intercom_select_community_0".equals(tag)) {
                    return new ActIntercomSelectCommunityBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_intercom_select_community is invalid. Received: " + tag);
            case 143:
                if ("layout/act_intercom_set_face_0".equals(tag)) {
                    return new ActIntercomSetFaceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_intercom_set_face is invalid. Received: " + tag);
            case 144:
                if ("layout/act_intercom_set_open_key_0".equals(tag)) {
                    return new ActIntercomSetOpenKeyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_intercom_set_open_key is invalid. Received: " + tag);
            case 145:
                if ("layout/act_intercom_setting_0".equals(tag)) {
                    return new ActIntercomSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_intercom_setting is invalid. Received: " + tag);
            case 146:
                if ("layout/act_intercom_temp_key_0".equals(tag)) {
                    return new ActIntercomTempKeyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_intercom_temp_key is invalid. Received: " + tag);
            case 147:
                if ("layout/act_intercom_tips_0".equals(tag)) {
                    return new ActIntercomTipsBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_intercom_tips is invalid. Received: " + tag);
            case 148:
                if ("layout/act_ir_diy_0".equals(tag)) {
                    return new ActIrDiyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_ir_diy is invalid. Received: " + tag);
            case 149:
                if ("layout/act_ir_setting_0".equals(tag)) {
                    return new ActIrSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_ir_setting is invalid. Received: " + tag);
            case 150:
                if ("layout/act_kbs_0".equals(tag)) {
                    return new ActKbsBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_kbs is invalid. Received: " + tag);
            default:
                return null;
        }
    }

    private final ViewDataBinding internalGetViewDataBinding3(DataBindingComponent component, View view, int internalId, Object tag) {
        switch (internalId) {
            case 151:
                if ("layout/act_kbs_group_setting_0".equals(tag)) {
                    return new ActKbsGroupSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_kbs_group_setting is invalid. Received: " + tag);
            case 152:
                if ("layout/act_kbs_setting_0".equals(tag)) {
                    return new ActKbsSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_kbs_setting is invalid. Received: " + tag);
            case 153:
                if ("layout/act_key_switch_0".equals(tag)) {
                    return new ActKeySwitchBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_key_switch is invalid. Received: " + tag);
            case 154:
                if ("layout/act_knob_panel_0".equals(tag)) {
                    return new ActKnobPanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_knob_panel is invalid. Received: " + tag);
            case 155:
                if ("layout/act_knob_panel_setting_0".equals(tag)) {
                    return new ActKnobPanelSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_knob_panel_setting is invalid. Received: " + tag);
            case 156:
                if ("layout/act_knob_screen_panel_0".equals(tag)) {
                    return new ActKnobScreenPanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_knob_screen_panel is invalid. Received: " + tag);
            case 157:
                if ("layout/act_light_group_sub_item_control_0".equals(tag)) {
                    return new ActLightGroupSubItemControlBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_light_group_sub_item_control is invalid. Received: " + tag);
            case 158:
                if ("layout/act_light_on_off_time_0".equals(tag)) {
                    return new ActLightOnOffTimeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_light_on_off_time is invalid. Received: " + tag);
            case 159:
                if ("layout/act_light_plan_batch_set_0".equals(tag)) {
                    return new ActLightPlanBatchSetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_light_plan_batch_set is invalid. Received: " + tag);
            case 160:
                if ("layout/act_light_plan_detail_0".equals(tag)) {
                    return new ActLightPlanDetailBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_light_plan_detail is invalid. Received: " + tag);
            case 161:
                if ("layout/act_light_plan_list_0".equals(tag)) {
                    return new ActLightPlanListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_light_plan_list is invalid. Received: " + tag);
            case 162:
                if ("layout/act_light_setting_0".equals(tag)) {
                    return new ActLightSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_light_setting is invalid. Received: " + tag);
            case 163:
                if ("layout/act_light_setting_new_0".equals(tag)) {
                    return new ActLightSettingNewBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_light_setting_new is invalid. Received: " + tag);
            case 164:
                if ("layout/act_local_device_log_0".equals(tag)) {
                    return new ActLocalDeviceLogBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_local_device_log is invalid. Received: " + tag);
            case 165:
                if ("layout/act_location_permission_description_0".equals(tag)) {
                    return new ActLocationPermissionDescriptionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_location_permission_description is invalid. Received: " + tag);
            case 166:
                if ("layout/act_login_0".equals(tag)) {
                    return new ActLoginBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_login is invalid. Received: " + tag);
            case 167:
                if ("layout/act_map_0".equals(tag)) {
                    return new ActMapBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_map is invalid. Received: " + tag);
            case 168:
                if ("layout/act_matter_list_0".equals(tag)) {
                    return new ActMatterListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_matter_list is invalid. Received: " + tag);
            case 169:
                if ("layout/act_matter_platform_list_0".equals(tag)) {
                    return new ActMatterPlatformListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_matter_platform_list is invalid. Received: " + tag);
            case 170:
                if ("layout/act_matter_sub_list_0".equals(tag)) {
                    return new ActMatterSubListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_matter_sub_list is invalid. Received: " + tag);
            case 171:
                if ("layout/act_mesh_gateway_0".equals(tag)) {
                    return new ActMeshGatewayBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_mesh_gateway is invalid. Received: " + tag);
            case 172:
                if ("layout/act_mesh_gateway_light_setting_0".equals(tag)) {
                    return new ActMeshGatewayLightSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_mesh_gateway_light_setting is invalid. Received: " + tag);
            case 173:
                if ("layout/act_mesh_gateway_setting_0".equals(tag)) {
                    return new ActMeshGatewaySettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_mesh_gateway_setting is invalid. Received: " + tag);
            case 174:
                if ("layout/act_mesh_near_device_0".equals(tag)) {
                    return new ActMeshNearDeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_mesh_near_device is invalid. Received: " + tag);
            case 175:
                if ("layout/act_mesh_scan_0".equals(tag)) {
                    return new ActMeshScanBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_mesh_scan is invalid. Received: " + tag);
            case 176:
                if ("layout/act_mesh_scan2_0".equals(tag)) {
                    return new ActMeshScan2BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_mesh_scan2 is invalid. Received: " + tag);
            case 177:
                if ("layout/act_mesh_scan_1_0".equals(tag)) {
                    return new ActMeshScan1BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_mesh_scan_1 is invalid. Received: " + tag);
            case 178:
                if ("layout/act_mesh_scan_proxy_0".equals(tag)) {
                    return new ActMeshScanProxyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_mesh_scan_proxy is invalid. Received: " + tag);
            case 179:
                if ("layout/act_message_center_0".equals(tag)) {
                    return new ActMessageCenterBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_message_center is invalid. Received: " + tag);
            case 180:
                if ("layout/act_mode_0".equals(tag)) {
                    return new ActModeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_mode is invalid. Received: " + tag);
            case 181:
                if ("layout/act_module_switch_0".equals(tag)) {
                    return new ActModuleSwitchBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_module_switch is invalid. Received: " + tag);
            case 182:
                if ("layout/act_monitor_0".equals(tag)) {
                    return new ActMonitorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_monitor is invalid. Received: " + tag);
            case 183:
                if ("layout/act_motor_0".equals(tag)) {
                    return new ActMotorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_motor is invalid. Received: " + tag);
            case 184:
                if ("layout/act_motor_pair_0".equals(tag)) {
                    return new ActMotorPairBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_motor_pair is invalid. Received: " + tag);
            case 185:
                if ("layout/act_music_0".equals(tag)) {
                    return new ActMusicBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_music is invalid. Received: " + tag);
            case 186:
                if ("layout/act_music_list_0".equals(tag)) {
                    return new ActMusicListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_music_list is invalid. Received: " + tag);
            case 187:
                if ("layout/act_net_config_0".equals(tag)) {
                    return new ActNetConfigBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_net_config is invalid. Received: " + tag);
            case 188:
                if ("layout/act_net_connect_0".equals(tag)) {
                    return new ActNetConnectBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_net_connect is invalid. Received: " + tag);
            case 189:
                if ("layout/act_new_as_panel_0".equals(tag)) {
                    return new ActNewAsPanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_new_as_panel is invalid. Received: " + tag);
            case 190:
                if ("layout/act_new_screen_panel_0".equals(tag)) {
                    return new ActNewScreenPanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_new_screen_panel is invalid. Received: " + tag);
            case 191:
                if ("layout/act_new_smart_panel_0".equals(tag)) {
                    return new ActNewSmartPanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_new_smart_panel is invalid. Received: " + tag);
            case 192:
                if ("layout/act_nfc_restore_0".equals(tag)) {
                    return new ActNfcRestoreBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_nfc_restore is invalid. Received: " + tag);
            case 193:
                if ("layout/act_open_door_log_detail_0".equals(tag)) {
                    return new ActOpenDoorLogDetailBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_open_door_log_detail is invalid. Received: " + tag);
            case 194:
                if ("layout/act_page_screen_panel_0".equals(tag)) {
                    return new ActPageScreenPanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_page_screen_panel is invalid. Received: " + tag);
            case 195:
                if ("layout/act_panel_color_set_0".equals(tag)) {
                    return new ActPanelColorSetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_panel_color_set is invalid. Received: " + tag);
            case 196:
                if ("layout/act_panel_group_setting_0".equals(tag)) {
                    return new ActPanelGroupSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_panel_group_setting is invalid. Received: " + tag);
            case 197:
                if ("layout/act_panel_night_getup_0".equals(tag)) {
                    return new ActPanelNightGetupBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_panel_night_getup is invalid. Received: " + tag);
            case 198:
                if ("layout/act_panel_switch_position_set_0".equals(tag)) {
                    return new ActPanelSwitchPositionSetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_panel_switch_position_set is invalid. Received: " + tag);
            case 199:
                if ("layout/act_place_user_setting_0".equals(tag)) {
                    return new ActPlaceUserSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_place_user_setting is invalid. Received: " + tag);
            case 200:
                if ("layout/act_playlist_manage_0".equals(tag)) {
                    return new ActPlaylistManageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_playlist_manage is invalid. Received: " + tag);
            default:
                return null;
        }
    }

    private final ViewDataBinding internalGetViewDataBinding4(DataBindingComponent component, View view, int internalId, Object tag) {
        switch (internalId) {
            case 201:
                if ("layout/act_product_introduction_0".equals(tag)) {
                    return new ActProductIntroductionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_product_introduction is invalid. Received: " + tag);
            case 202:
                if ("layout/act_product_introduction_1_0".equals(tag)) {
                    return new ActProductIntroduction1BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_product_introduction_1 is invalid. Received: " + tag);
            case 203:
                if ("layout/act_pub_list_0".equals(tag)) {
                    return new ActPubListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_pub_list is invalid. Received: " + tag);
            case 204:
                if ("layout/act_qr_code_scan_0".equals(tag)) {
                    return new ActQrCodeScanBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_qr_code_scan is invalid. Received: " + tag);
            case 205:
                if ("layout/act_qr_code_scan_result_0".equals(tag)) {
                    return new ActQrCodeScanResultBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_qr_code_scan_result is invalid. Received: " + tag);
            case 206:
                if ("layout/act_r8_setting_0".equals(tag)) {
                    return new ActR8SettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_r8_setting is invalid. Received: " + tag);
            case 207:
                if ("layout/act_rc4s_0".equals(tag)) {
                    return new ActRc4sBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_rc4s is invalid. Received: " + tag);
            case 208:
                if ("layout/act_record_list_0".equals(tag)) {
                    return new ActRecordListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_record_list is invalid. Received: " + tag);
            case 209:
                if ("layout/act_register_0".equals(tag)) {
                    return new ActRegisterBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_register is invalid. Received: " + tag);
            case 210:
                if ("layout/act_remote_battery_0".equals(tag)) {
                    return new ActRemoteBatteryBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_remote_battery is invalid. Received: " + tag);
            case 211:
                if ("layout/act_replace_0".equals(tag)) {
                    return new ActReplaceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_replace is invalid. Received: " + tag);
            case 212:
                if ("layout/act_rgbwaf_select_0".equals(tag)) {
                    return new ActRgbwafSelectBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_rgbwaf_select is invalid. Received: " + tag);
            case 213:
                if ("layout/act_room_manage_0".equals(tag)) {
                    return new ActRoomManageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_room_manage is invalid. Received: " + tag);
            case 214:
                if ("layout/act_rs8_0".equals(tag)) {
                    return new ActRs8BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_rs8 is invalid. Received: " + tag);
            case 215:
                if ("layout/act_rs8_add_sub_device_0".equals(tag)) {
                    return new ActRs8AddSubDeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_rs8_add_sub_device is invalid. Received: " + tag);
            case 216:
                if ("layout/act_rs8_address_write_0".equals(tag)) {
                    return new ActRs8AddressWriteBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_rs8_address_write is invalid. Received: " + tag);
            case 217:
                if ("layout/act_rs8_curtain_0".equals(tag)) {
                    return new ActRs8CurtainBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_rs8_curtain is invalid. Received: " + tag);
            case 218:
                if ("layout/act_rs8_sub_device_setting_0".equals(tag)) {
                    return new ActRs8SubDeviceSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_rs8_sub_device_setting is invalid. Received: " + tag);
            case 219:
                if ("layout/act_save_qr_code_0".equals(tag)) {
                    return new ActSaveQrCodeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_save_qr_code is invalid. Received: " + tag);
            case 220:
                if ("layout/act_scene_panel_0".equals(tag)) {
                    return new ActScenePanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_scene_panel is invalid. Received: " + tag);
            case 221:
                if ("layout/act_screen_panel_0".equals(tag)) {
                    return new ActScreenPanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_screen_panel is invalid. Received: " + tag);
            case 222:
                if ("layout/act_screen_panel_elderly_mode_0".equals(tag)) {
                    return new ActScreenPanelElderlyModeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_screen_panel_elderly_mode is invalid. Received: " + tag);
            case 223:
                if ("layout/act_search_automation_0".equals(tag)) {
                    return new ActSearchAutomationBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_search_automation is invalid. Received: " + tag);
            case 224:
                if ("layout/act_search_device_0".equals(tag)) {
                    return new ActSearchDeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_search_device is invalid. Received: " + tag);
            case 225:
                if ("layout/act_search_google_position_0".equals(tag)) {
                    return new ActSearchGooglePositionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_search_google_position is invalid. Received: " + tag);
            case 226:
                if ("layout/act_search_music_0".equals(tag)) {
                    return new ActSearchMusicBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_search_music is invalid. Received: " + tag);
            case 227:
                if ("layout/act_search_position_0".equals(tag)) {
                    return new ActSearchPositionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_search_position is invalid. Received: " + tag);
            case LAYOUT_ACTSEARCHSCENE /* 228 */:
                if ("layout/act_search_scene_0".equals(tag)) {
                    return new ActSearchSceneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_search_scene is invalid. Received: " + tag);
            case 229:
                if ("layout/act_select_0".equals(tag)) {
                    return new ActSelectBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select is invalid. Received: " + tag);
            case 230:
                if ("layout/act_select2_0".equals(tag)) {
                    return new ActSelect2BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select2 is invalid. Received: " + tag);
            case 231:
                if ("layout/act_select3_0".equals(tag)) {
                    return new ActSelect3BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select3 is invalid. Received: " + tag);
            case 232:
                if ("layout/act_select4_0".equals(tag)) {
                    return new ActSelect4BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select4 is invalid. Received: " + tag);
            case 233:
                if ("layout/act_select_ble_curtain_action_0".equals(tag)) {
                    return new ActSelectBleCurtainActionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_ble_curtain_action is invalid. Received: " + tag);
            case 234:
                if ("layout/act_select_brand_0".equals(tag)) {
                    return new ActSelectBrandBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_brand is invalid. Received: " + tag);
            case LAYOUT_ACTSELECTCOLOR /* 235 */:
                if ("layout/act_select_color_0".equals(tag)) {
                    return new ActSelectColorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_color is invalid. Received: " + tag);
            case LAYOUT_ACTSELECTCOLORCC /* 236 */:
                if ("layout/act_select_color_cc_0".equals(tag)) {
                    return new ActSelectColorCcBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_color_cc is invalid. Received: " + tag);
            case LAYOUT_ACTSELECTCONDITIONDEVICE /* 237 */:
                if ("layout/act_select_condition_device_0".equals(tag)) {
                    return new ActSelectConditionDeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_condition_device is invalid. Received: " + tag);
            case LAYOUT_ACTSELECTCOUNTRY /* 238 */:
                if ("layout/act_select_country_0".equals(tag)) {
                    return new ActSelectCountryBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_country is invalid. Received: " + tag);
            case LAYOUT_ACTSELECTDALIADD /* 239 */:
                if ("layout/act_select_dali_add_0".equals(tag)) {
                    return new ActSelectDaliAddBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_dali_add is invalid. Received: " + tag);
            case 240:
                if ("layout/act_select_dali_color_0".equals(tag)) {
                    return new ActSelectDaliColorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_dali_color is invalid. Received: " + tag);
            case LAYOUT_ACTSELECTDIVIDE /* 241 */:
                if ("layout/act_select_divide_0".equals(tag)) {
                    return new ActSelectDivideBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_divide is invalid. Received: " + tag);
            case LAYOUT_ACTSELECTEFFECTPERIOD /* 242 */:
                if ("layout/act_select_effect_period_0".equals(tag)) {
                    return new ActSelectEffectPeriodBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_effect_period is invalid. Received: " + tag);
            case LAYOUT_ACTSELECTHOMEMEMBER /* 243 */:
                if ("layout/act_select_home_member_0".equals(tag)) {
                    return new ActSelectHomeMemberBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_home_member is invalid. Received: " + tag);
            case LAYOUT_ACTSELECTLIGHT /* 244 */:
                if ("layout/act_select_light_0".equals(tag)) {
                    return new ActSelectLightBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_light is invalid. Received: " + tag);
            case LAYOUT_ACTSELECTLIST /* 245 */:
                if ("layout/act_select_list_0".equals(tag)) {
                    return new ActSelectListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_list is invalid. Received: " + tag);
            case LAYOUT_ACTSELECTMULTITYPE /* 246 */:
                if ("layout/act_select_multi_type_0".equals(tag)) {
                    return new ActSelectMultiTypeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_multi_type is invalid. Received: " + tag);
            case LAYOUT_ACTSELECTOPERATORS /* 247 */:
                if ("layout/act_select_operators_0".equals(tag)) {
                    return new ActSelectOperatorsBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_operators is invalid. Received: " + tag);
            case 248:
                if ("layout/act_select_scene_0".equals(tag)) {
                    return new ActSelectSceneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_scene is invalid. Received: " + tag);
            case LAYOUT_ACTSELECTSCENEALL /* 249 */:
                if ("layout/act_select_scene_all_0".equals(tag)) {
                    return new ActSelectSceneAllBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_scene_all is invalid. Received: " + tag);
            case 250:
                if ("layout/act_select_sonos_action_0".equals(tag)) {
                    return new ActSelectSonosActionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_sonos_action is invalid. Received: " + tag);
            default:
                return null;
        }
    }

    private final ViewDataBinding internalGetViewDataBinding5(DataBindingComponent component, View view, int internalId, Object tag) {
        switch (internalId) {
            case 251:
                if ("layout/act_select_spi_for_action_0".equals(tag)) {
                    return new ActSelectSpiForActionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_spi_for_action is invalid. Received: " + tag);
            case 252:
                if ("layout/act_select_super_panel_music_0".equals(tag)) {
                    return new ActSelectSuperPanelMusicBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_super_panel_music is invalid. Received: " + tag);
            case 253:
                if ("layout/act_select_temperature_weather_0".equals(tag)) {
                    return new ActSelectTemperatureWeatherBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_temperature_weather is invalid. Received: " + tag);
            case 254:
                if ("layout/act_select_theme_mode_0".equals(tag)) {
                    return new ActSelectThemeModeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_theme_mode is invalid. Received: " + tag);
            case 255:
                if ("layout/act_select_time_0".equals(tag)) {
                    return new ActSelectTimeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_time is invalid. Received: " + tag);
            case 256:
                if ("layout/act_select_voice_speak_0".equals(tag)) {
                    return new ActSelectVoiceSpeakBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_voice_speak is invalid. Received: " + tag);
            case 257:
                if ("layout/act_select_weather_0".equals(tag)) {
                    return new ActSelectWeatherBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_select_weather is invalid. Received: " + tag);
            case 258:
                if ("layout/act_sense_setting_0".equals(tag)) {
                    return new ActSenseSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_sense_setting is invalid. Received: " + tag);
            case 259:
                if ("layout/act_sensor_noboby_test_0".equals(tag)) {
                    return new ActSensorNobobyTestBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_sensor_noboby_test is invalid. Received: " + tag);
            case 260:
                if ("layout/act_sensor_setting_0".equals(tag)) {
                    return new ActSensorSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_sensor_setting is invalid. Received: " + tag);
            case 261:
                if ("layout/act_serial_setting_0".equals(tag)) {
                    return new ActSerialSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_serial_setting is invalid. Received: " + tag);
            case 262:
                if ("layout/act_set_light_channel_0".equals(tag)) {
                    return new ActSetLightChannelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_set_light_channel is invalid. Received: " + tag);
            case 263:
                if ("layout/act_set_screen_display_0".equals(tag)) {
                    return new ActSetScreenDisplayBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_set_screen_display is invalid. Received: " + tag);
            case 264:
                if ("layout/act_share_duv_list_0".equals(tag)) {
                    return new ActShareDuvListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_share_duv_list is invalid. Received: " + tag);
            case 265:
                if ("layout/act_smart_panel_0".equals(tag)) {
                    return new ActSmartPanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_smart_panel is invalid. Received: " + tag);
            case 266:
                if ("layout/act_smart_panel_child_setting_0".equals(tag)) {
                    return new ActSmartPanelChildSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_smart_panel_child_setting is invalid. Received: " + tag);
            case 267:
                if ("layout/act_smart_panel_group_child_setting_0".equals(tag)) {
                    return new ActSmartPanelGroupChildSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_smart_panel_group_child_setting is invalid. Received: " + tag);
            case 268:
                if ("layout/act_smart_panel_key_set_0".equals(tag)) {
                    return new ActSmartPanelKeySetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_smart_panel_key_set is invalid. Received: " + tag);
            case 269:
                if ("layout/act_smart_panel_setting_0".equals(tag)) {
                    return new ActSmartPanelSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_smart_panel_setting is invalid. Received: " + tag);
            case 270:
                if ("layout/act_smart_panel_switch_setting_0".equals(tag)) {
                    return new ActSmartPanelSwitchSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_smart_panel_switch_setting is invalid. Received: " + tag);
            case LAYOUT_ACTSMARTPANELTHEME /* 271 */:
                if ("layout/act_smart_panel_theme_0".equals(tag)) {
                    return new ActSmartPanelThemeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_smart_panel_theme is invalid. Received: " + tag);
            case 272:
                if ("layout/act_smart_speaker_0".equals(tag)) {
                    return new ActSmartSpeakerBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_smart_speaker is invalid. Received: " + tag);
            case 273:
                if ("layout/act_smart_speaker_detail_0".equals(tag)) {
                    return new ActSmartSpeakerDetailBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_smart_speaker_detail is invalid. Received: " + tag);
            case 274:
                if ("layout/act_songs_0".equals(tag)) {
                    return new ActSongsBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_songs is invalid. Received: " + tag);
            case 275:
                if ("layout/act_sonos_music_detail_0".equals(tag)) {
                    return new ActSonosMusicDetailBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_sonos_music_detail is invalid. Received: " + tag);
            case 276:
                if ("layout/act_sonos_setting_default_0".equals(tag)) {
                    return new ActSonosSettingDefaultBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_sonos_setting_default is invalid. Received: " + tag);
            case 277:
                if ("layout/act_sonos_web_view_0".equals(tag)) {
                    return new ActSonosWebViewBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_sonos_web_view is invalid. Received: " + tag);
            case 278:
                if ("layout/act_sort_0".equals(tag)) {
                    return new ActSortBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_sort is invalid. Received: " + tag);
            case 279:
                if ("layout/act_sort_local_music_0".equals(tag)) {
                    return new ActSortLocalMusicBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_sort_local_music is invalid. Received: " + tag);
            case 280:
                if ("layout/act_sp485_list_0".equals(tag)) {
                    return new ActSp485ListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_sp485_list is invalid. Received: " + tag);
            case 281:
                if ("layout/act_spi_controller_0".equals(tag)) {
                    return new ActSpiControllerBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_spi_controller is invalid. Received: " + tag);
            case 282:
                if ("layout/act_spi_controller_setting_0".equals(tag)) {
                    return new ActSpiControllerSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_spi_controller_setting is invalid. Received: " + tag);
            case 283:
                if ("layout/act_spi_edit_play_list_0".equals(tag)) {
                    return new ActSpiEditPlayListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_spi_edit_play_list is invalid. Received: " + tag);
            case 284:
                if ("layout/act_spi_light_setting_0".equals(tag)) {
                    return new ActSpiLightSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_spi_light_setting is invalid. Received: " + tag);
            case 285:
                if ("layout/act_splash_0".equals(tag)) {
                    return new ActSplashBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_splash is invalid. Received: " + tag);
            case LAYOUT_ACTSTEPSINTRODUCTION /* 286 */:
                if ("layout/act_steps_introduction_0".equals(tag)) {
                    return new ActStepsIntroductionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_steps_introduction is invalid. Received: " + tag);
            case LAYOUT_ACTSUBDEVICESETTINGDEFAULT /* 287 */:
                if ("layout/act_sub_device_setting_default_0".equals(tag)) {
                    return new ActSubDeviceSettingDefaultBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_sub_device_setting_default is invalid. Received: " + tag);
            case 288:
                if ("layout/act_super_panel_0".equals(tag)) {
                    return new ActSuperPanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_super_panel is invalid. Received: " + tag);
            case 289:
                if ("layout/act_super_panel_album_0".equals(tag)) {
                    return new ActSuperPanelAlbumBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_super_panel_album is invalid. Received: " + tag);
            case 290:
                if ("layout/act_super_panel_clip_photo_0".equals(tag)) {
                    return new ActSuperPanelClipPhotoBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_super_panel_clip_photo is invalid. Received: " + tag);
            case LAYOUT_ACTSUPERPANELCONTINOUSTALK /* 291 */:
                if ("layout/act_super_panel_continous_talk_0".equals(tag)) {
                    return new ActSuperPanelContinousTalkBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_super_panel_continous_talk is invalid. Received: " + tag);
            case LAYOUT_ACTSUPERPANELIRREMOTECONTROL /* 292 */:
                if ("layout/act_super_panel_ir_remote_control_0".equals(tag)) {
                    return new ActSuperPanelIrRemoteControlBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_super_panel_ir_remote_control is invalid. Received: " + tag);
            case 293:
                if ("layout/act_super_panel_key_set_0".equals(tag)) {
                    return new ActSuperPanelKeySetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_super_panel_key_set is invalid. Received: " + tag);
            case 294:
                if ("layout/act_super_panel_key_set_6s_0".equals(tag)) {
                    return new ActSuperPanelKeySet6sBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_super_panel_key_set_6s is invalid. Received: " + tag);
            case LAYOUT_ACTSUPERPANELPREVIEWPHOTO /* 295 */:
                if ("layout/act_super_panel_preview_photo_0".equals(tag)) {
                    return new ActSuperPanelPreviewPhotoBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_super_panel_preview_photo is invalid. Received: " + tag);
            case LAYOUT_ACTSUPERPANELSELECTPHOTO /* 296 */:
                if ("layout/act_super_panel_select_photo_0".equals(tag)) {
                    return new ActSuperPanelSelectPhotoBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_super_panel_select_photo is invalid. Received: " + tag);
            case LAYOUT_ACTSUPERPANELSETTING /* 297 */:
                if ("layout/act_super_panel_setting_0".equals(tag)) {
                    return new ActSuperPanelSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_super_panel_setting is invalid. Received: " + tag);
            case 298:
                if ("layout/act_super_panel_voice_control_range_0".equals(tag)) {
                    return new ActSuperPanelVoiceControlRangeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_super_panel_voice_control_range is invalid. Received: " + tag);
            case 299:
                if ("layout/act_super_panel_voice_control_range_role_0".equals(tag)) {
                    return new ActSuperPanelVoiceControlRangeRoleBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_super_panel_voice_control_range_role is invalid. Received: " + tag);
            case 300:
                if ("layout/act_super_panel_voice_talk_0".equals(tag)) {
                    return new ActSuperPanelVoiceTalkBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_super_panel_voice_talk is invalid. Received: " + tag);
            default:
                return null;
        }
    }

    private final ViewDataBinding internalGetViewDataBinding6(DataBindingComponent component, View view, int internalId, Object tag) {
        switch (internalId) {
            case 301:
                if ("layout/act_te_panel_0".equals(tag)) {
                    return new ActTePanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_te_panel is invalid. Received: " + tag);
            case 302:
                if ("layout/act_test_mode_main_0".equals(tag)) {
                    return new ActTestModeMainBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_test_mode_main is invalid. Received: " + tag);
            case 303:
                if ("layout/act_test_prepare_0".equals(tag)) {
                    return new ActTestPrepareBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_test_prepare is invalid. Received: " + tag);
            case 304:
                if ("layout/act_test_step_0".equals(tag)) {
                    return new ActTestStepBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_test_step is invalid. Received: " + tag);
            case 305:
                if ("layout/act_theme_download_0".equals(tag)) {
                    return new ActThemeDownloadBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_theme_download is invalid. Received: " + tag);
            case 306:
                if ("layout/act_transfer_music_0".equals(tag)) {
                    return new ActTransferMusicBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_transfer_music is invalid. Received: " + tag);
            case 307:
                if ("layout/act_trig_0".equals(tag)) {
                    return new ActTrigBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_trig is invalid. Received: " + tag);
            case 308:
                if ("layout/act_trig_curtain_0".equals(tag)) {
                    return new ActTrigCurtainBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_trig_curtain is invalid. Received: " + tag);
            case 309:
                if ("layout/act_trig_curtain_channel_set_0".equals(tag)) {
                    return new ActTrigCurtainChannelSetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_trig_curtain_channel_set is invalid. Received: " + tag);
            case 310:
                if ("layout/act_trig_curtain_group_setting_0".equals(tag)) {
                    return new ActTrigCurtainGroupSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_trig_curtain_group_setting is invalid. Received: " + tag);
            case LAYOUT_ACTTRIGCURTAINOPENDIRSET /* 311 */:
                if ("layout/act_trig_curtain_open_dir_set_0".equals(tag)) {
                    return new ActTrigCurtainOpenDirSetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_trig_curtain_open_dir_set is invalid. Received: " + tag);
            case LAYOUT_ACTTRIGTOBLE /* 312 */:
                if ("layout/act_trig_to_ble_0".equals(tag)) {
                    return new ActTrigToBleBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_trig_to_ble is invalid. Received: " + tag);
            case LAYOUT_ACTTV /* 313 */:
                if ("layout/act_tv_0".equals(tag)) {
                    return new ActTvBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_tv is invalid. Received: " + tag);
            case LAYOUT_ACTUPGRADE /* 314 */:
                if ("layout/act_upgrade_0".equals(tag)) {
                    return new ActUpgradeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_upgrade is invalid. Received: " + tag);
            case LAYOUT_ACTUSERINFO /* 315 */:
                if ("layout/act_user_info_0".equals(tag)) {
                    return new ActUserInfoBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_user_info is invalid. Received: " + tag);
            case LAYOUT_ACTVOICECALLBIND /* 316 */:
                if ("layout/act_voice_call_bind_0".equals(tag)) {
                    return new ActVoiceCallBindBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_voice_call_bind is invalid. Received: " + tag);
            case 317:
                if ("layout/act_voice_call_grop_list_0".equals(tag)) {
                    return new ActVoiceCallGropListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_voice_call_grop_list is invalid. Received: " + tag);
            case 318:
                if ("layout/act_voice_call_group_add_0".equals(tag)) {
                    return new ActVoiceCallGroupAddBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_voice_call_group_add is invalid. Received: " + tag);
            case 319:
                if ("layout/act_voice_call_setting_0".equals(tag)) {
                    return new ActVoiceCallSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_voice_call_setting is invalid. Received: " + tag);
            case 320:
                if ("layout/act_voice_call_whitelist_add_0".equals(tag)) {
                    return new ActVoiceCallWhitelistAddBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_voice_call_whitelist_add is invalid. Received: " + tag);
            case 321:
                if ("layout/act_wave_sensor_0".equals(tag)) {
                    return new ActWaveSensorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_wave_sensor is invalid. Received: " + tag);
            case 322:
                if ("layout/act_wave_sensor_effect_period_0".equals(tag)) {
                    return new ActWaveSensorEffectPeriodBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_wave_sensor_effect_period is invalid. Received: " + tag);
            case 323:
                if ("layout/act_wave_sensor_group_setting_0".equals(tag)) {
                    return new ActWaveSensorGroupSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_wave_sensor_group_setting is invalid. Received: " + tag);
            case 324:
                if ("layout/act_wave_sensor_pro_0".equals(tag)) {
                    return new ActWaveSensorProBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_wave_sensor_pro is invalid. Received: " + tag);
            case 325:
                if ("layout/act_wave_sensor_setting_0".equals(tag)) {
                    return new ActWaveSensorSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_wave_sensor_setting is invalid. Received: " + tag);
            case 326:
                if ("layout/act_web_view_0".equals(tag)) {
                    return new ActWebViewBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_web_view is invalid. Received: " + tag);
            case 327:
                if ("layout/act_welcome_0".equals(tag)) {
                    return new ActWelcomeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_welcome is invalid. Received: " + tag);
            case 328:
                if ("layout/act_xy_select_0".equals(tag)) {
                    return new ActXySelectBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for act_xy_select is invalid. Received: " + tag);
            case 329:
                if ("layout/activity_act_mesh_add_all_device_0".equals(tag)) {
                    return new ActivityActMeshAddAllDeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_act_mesh_add_all_device is invalid. Received: " + tag);
            case LAYOUT_DIALOG512CHANNELSETTING /* 330 */:
                if ("layout/dialog_512_channel_setting_0".equals(tag)) {
                    return new Dialog512ChannelSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_512_channel_setting is invalid. Received: " + tag);
            case LAYOUT_DIALOGACQUICK /* 331 */:
                if ("layout/dialog_ac_quick_0".equals(tag)) {
                    return new DialogAcQuickBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_ac_quick is invalid. Received: " + tag);
            case LAYOUT_DIALOGADDCG485CATEGORY /* 332 */:
                if ("layout/dialog_add_cg485_category_0".equals(tag)) {
                    return new DialogAddCg485CategoryBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_add_cg485_category is invalid. Received: " + tag);
            case LAYOUT_DIALOGADDCG485DEVICE /* 333 */:
                if ("layout/dialog_add_cg485_device_0".equals(tag)) {
                    return new DialogAddCg485DeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_add_cg485_device is invalid. Received: " + tag);
            case LAYOUT_DIALOGADDCOLORPOINT /* 334 */:
                if ("layout/dialog_add_color_point_0".equals(tag)) {
                    return new DialogAddColorPointBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_add_color_point is invalid. Received: " + tag);
            case LAYOUT_DIALOGADDGROUP /* 335 */:
                if ("layout/dialog_add_group_0".equals(tag)) {
                    return new DialogAddGroupBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_add_group is invalid. Received: " + tag);
            case LAYOUT_DIALOGADDSCENE /* 336 */:
                if ("layout/dialog_add_scene_0".equals(tag)) {
                    return new DialogAddSceneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_add_scene is invalid. Received: " + tag);
            case LAYOUT_DIALOGBUTTONTIP /* 337 */:
                if ("layout/dialog_button_tip_0".equals(tag)) {
                    return new DialogButtonTipBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_button_tip is invalid. Received: " + tag);
            case LAYOUT_DIALOGCALENDAR /* 338 */:
                if ("layout/dialog_calendar_0".equals(tag)) {
                    return new DialogCalendarBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_calendar is invalid. Received: " + tag);
            case LAYOUT_DIALOGCALLINVITE /* 339 */:
                if ("layout/dialog_call_invite_0".equals(tag)) {
                    return new DialogCallInviteBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_call_invite is invalid. Received: " + tag);
            case LAYOUT_DIALOGCENTERDALIMODIFYPARAM /* 340 */:
                if ("layout/dialog_center_dali_modify_param_0".equals(tag)) {
                    return new DialogCenterDaliModifyParamBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_center_dali_modify_param is invalid. Received: " + tag);
            case LAYOUT_DIALOGCENTERSELECTLIST /* 341 */:
                if ("layout/dialog_center_select_list_0".equals(tag)) {
                    return new DialogCenterSelectListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_center_select_list is invalid. Received: " + tag);
            case LAYOUT_DIALOGCENTERTIP /* 342 */:
                if ("layout/dialog_center_tip_0".equals(tag)) {
                    return new DialogCenterTipBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_center_tip is invalid. Received: " + tag);
            case LAYOUT_DIALOGCITYPICKER /* 343 */:
                if ("layout/dialog_city_picker_0".equals(tag)) {
                    return new DialogCityPickerBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_city_picker is invalid. Received: " + tag);
            case LAYOUT_DIALOGCOLORBRTCONTROL /* 344 */:
                if ("layout/dialog_color_brt_control_0".equals(tag)) {
                    return new DialogColorBrtControlBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_color_brt_control is invalid. Received: " + tag);
            case LAYOUT_DIALOGDALIDETECT /* 345 */:
                if ("layout/dialog_dali_detect_0".equals(tag)) {
                    return new DialogDaliDetectBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_dali_detect is invalid. Received: " + tag);
            case LAYOUT_DIALOGDALIDETECTTIP /* 346 */:
                if ("layout/dialog_dali_detect_tip_0".equals(tag)) {
                    return new DialogDaliDetectTipBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_dali_detect_tip is invalid. Received: " + tag);
            case LAYOUT_DIALOGDALILOADDATA /* 347 */:
                if ("layout/dialog_dali_load_data_0".equals(tag)) {
                    return new DialogDaliLoadDataBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_dali_load_data is invalid. Received: " + tag);
            case LAYOUT_DIALOGDELFAIL /* 348 */:
                if ("layout/dialog_del_fail_0".equals(tag)) {
                    return new DialogDelFailBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_del_fail is invalid. Received: " + tag);
            case LAYOUT_DIALOGDEVICEICONSELECTOR /* 349 */:
                if ("layout/dialog_device_icon_selector_0".equals(tag)) {
                    return new DialogDeviceIconSelectorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_device_icon_selector is invalid. Received: " + tag);
            case 350:
                if ("layout/dialog_dim_depth_selector_0".equals(tag)) {
                    return new DialogDimDepthSelectorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_dim_depth_selector is invalid. Received: " + tag);
            default:
                return null;
        }
    }

    private final ViewDataBinding internalGetViewDataBinding7(DataBindingComponent component, View view, int internalId, Object tag) {
        switch (internalId) {
            case 351:
                if ("layout/dialog_e6_tip_0".equals(tag)) {
                    return new DialogE6TipBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_e6_tip is invalid. Received: " + tag);
            case 352:
                if ("layout/dialog_edit_0".equals(tag)) {
                    return new DialogEditBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_edit is invalid. Received: " + tag);
            case 353:
                if ("layout/dialog_edit_copy_0".equals(tag)) {
                    return new DialogEditCopyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_edit_copy is invalid. Received: " + tag);
            case LAYOUT_DIALOGEDITDEVICE /* 354 */:
                if ("layout/dialog_edit_device_0".equals(tag)) {
                    return new DialogEditDeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_edit_device is invalid. Received: " + tag);
            case LAYOUT_DIALOGEURFUNCTION /* 355 */:
                if ("layout/dialog_eur_function_0".equals(tag)) {
                    return new DialogEurFunctionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_eur_function is invalid. Received: " + tag);
            case LAYOUT_DIALOGEURFUNCTIONANDRGB /* 356 */:
                if ("layout/dialog_eur_function_and_rgb_0".equals(tag)) {
                    return new DialogEurFunctionAndRgbBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_eur_function_and_rgb is invalid. Received: " + tag);
            case LAYOUT_DIALOGEXECUTEDELAYTIMESELECTOR /* 357 */:
                if ("layout/dialog_execute_delay_time_selector_0".equals(tag)) {
                    return new DialogExecuteDelayTimeSelectorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_execute_delay_time_selector is invalid. Received: " + tag);
            case LAYOUT_DIALOGEXECUTETIMESELECTOR /* 358 */:
                if ("layout/dialog_execute_time_selector_0".equals(tag)) {
                    return new DialogExecuteTimeSelectorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_execute_time_selector is invalid. Received: " + tag);
            case LAYOUT_DIALOGFLOORHEATQUICK /* 359 */:
                if ("layout/dialog_floor_heat_quick_0".equals(tag)) {
                    return new DialogFloorHeatQuickBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_floor_heat_quick is invalid. Received: " + tag);
            case 360:
                if ("layout/dialog_frequency_interval_0".equals(tag)) {
                    return new DialogFrequencyIntervalBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_frequency_interval is invalid. Received: " + tag);
            case LAYOUT_DIALOGFRESHAIRQUICK /* 361 */:
                if ("layout/dialog_fresh_air_quick_0".equals(tag)) {
                    return new DialogFreshAirQuickBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_fresh_air_quick is invalid. Received: " + tag);
            case LAYOUT_DIALOGGRADUALTIME /* 362 */:
                if ("layout/dialog_gradual_time_0".equals(tag)) {
                    return new DialogGradualTimeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_gradual_time is invalid. Received: " + tag);
            case LAYOUT_DIALOGIMAGETIP /* 363 */:
                if ("layout/dialog_image_tip_0".equals(tag)) {
                    return new DialogImageTipBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_image_tip is invalid. Received: " + tag);
            case LAYOUT_DIALOGINTERCOMTIMEPICKER /* 364 */:
                if ("layout/dialog_intercom_time_picker_0".equals(tag)) {
                    return new DialogIntercomTimePickerBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_intercom_time_picker is invalid. Received: " + tag);
            case LAYOUT_DIALOGIRFUN /* 365 */:
                if ("layout/dialog_ir_fun_0".equals(tag)) {
                    return new DialogIrFunBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_ir_fun is invalid. Received: " + tag);
            case LAYOUT_DIALOGIRLEARN /* 366 */:
                if ("layout/dialog_ir_learn_0".equals(tag)) {
                    return new DialogIrLearnBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_ir_learn is invalid. Received: " + tag);
            case LAYOUT_DIALOGIRQUICK /* 367 */:
                if ("layout/dialog_ir_quick_0".equals(tag)) {
                    return new DialogIrQuickBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_ir_quick is invalid. Received: " + tag);
            case LAYOUT_DIALOGLEARNINSTRUCT /* 368 */:
                if ("layout/dialog_learn_instruct_0".equals(tag)) {
                    return new DialogLearnInstructBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_learn_instruct is invalid. Received: " + tag);
            case LAYOUT_DIALOGLEFTTITLESELECTLIST /* 369 */:
                if ("layout/dialog_left_title_select_list_0".equals(tag)) {
                    return new DialogLeftTitleSelectListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_left_title_select_list is invalid. Received: " + tag);
            case LAYOUT_DIALOGLIGHTQUICK /* 370 */:
                if ("layout/dialog_light_quick_0".equals(tag)) {
                    return new DialogLightQuickBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_light_quick is invalid. Received: " + tag);
            case 371:
                if ("layout/dialog_list_0".equals(tag)) {
                    return new DialogListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_list is invalid. Received: " + tag);
            case LAYOUT_DIALOGLOADING /* 372 */:
                if ("layout/dialog_loading_0".equals(tag)) {
                    return new DialogLoadingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_loading is invalid. Received: " + tag);
            case LAYOUT_DIALOGLOADINGPROGRESS /* 373 */:
                if ("layout/dialog_loading_progress_0".equals(tag)) {
                    return new DialogLoadingProgressBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_loading_progress is invalid. Received: " + tag);
            case LAYOUT_DIALOGLOADINGSUCCESS /* 374 */:
                if ("layout/dialog_loading_success_0".equals(tag)) {
                    return new DialogLoadingSuccessBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_loading_success is invalid. Received: " + tag);
            case LAYOUT_DIALOGLOGCALENDAR /* 375 */:
                if ("layout/dialog_log_calendar_0".equals(tag)) {
                    return new DialogLogCalendarBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_log_calendar is invalid. Received: " + tag);
            case LAYOUT_DIALOGMATTERQRCODE /* 376 */:
                if ("layout/dialog_matter_qrcode_0".equals(tag)) {
                    return new DialogMatterQrcodeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_matter_qrcode is invalid. Received: " + tag);
            case LAYOUT_DIALOGMSTIMESELECTOR /* 377 */:
                if ("layout/dialog_ms_time_selector_0".equals(tag)) {
                    return new DialogMsTimeSelectorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_ms_time_selector is invalid. Received: " + tag);
            case LAYOUT_DIALOGMUSICLIST /* 378 */:
                if ("layout/dialog_music_list_0".equals(tag)) {
                    return new DialogMusicListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_music_list is invalid. Received: " + tag);
            case LAYOUT_DIALOGMUSICTIME /* 379 */:
                if ("layout/dialog_music_time_0".equals(tag)) {
                    return new DialogMusicTimeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_music_time is invalid. Received: " + tag);
            case LAYOUT_DIALOGMUSICVIPTIP /* 380 */:
                if ("layout/dialog_music_vip_tip_0".equals(tag)) {
                    return new DialogMusicVipTipBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_music_vip_tip is invalid. Received: " + tag);
            case LAYOUT_DIALOGMUSICVOLUME /* 381 */:
                if ("layout/dialog_music_volume_0".equals(tag)) {
                    return new DialogMusicVolumeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_music_volume is invalid. Received: " + tag);
            case LAYOUT_DIALOGNUMBEREDIT /* 382 */:
                if ("layout/dialog_number_edit_0".equals(tag)) {
                    return new DialogNumberEditBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_number_edit is invalid. Received: " + tag);
            case LAYOUT_DIALOGPAIR /* 383 */:
                if ("layout/dialog_pair_0".equals(tag)) {
                    return new DialogPairBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_pair is invalid. Received: " + tag);
            case 384:
                if ("layout/dialog_panel_brt_0".equals(tag)) {
                    return new DialogPanelBrtBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_panel_brt is invalid. Received: " + tag);
            case LAYOUT_DIALOGPOWERSTATEBATCH /* 385 */:
                if ("layout/dialog_power_state_batch_0".equals(tag)) {
                    return new DialogPowerStateBatchBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_power_state_batch is invalid. Received: " + tag);
            case LAYOUT_DIALOGPOWERSTATESELECTOR /* 386 */:
                if ("layout/dialog_power_state_selector_0".equals(tag)) {
                    return new DialogPowerStateSelectorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_power_state_selector is invalid. Received: " + tag);
            case LAYOUT_DIALOGPROGRESS /* 387 */:
                if ("layout/dialog_progress_0".equals(tag)) {
                    return new DialogProgressBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_progress is invalid. Received: " + tag);
            case LAYOUT_DIALOGRC4STIP /* 388 */:
                if ("layout/dialog_rc4s_tip_0".equals(tag)) {
                    return new DialogRc4sTipBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_rc4s_tip is invalid. Received: " + tag);
            case LAYOUT_DIALOGREGMODE /* 389 */:
                if ("layout/dialog_reg_mode_0".equals(tag)) {
                    return new DialogRegModeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_reg_mode is invalid. Received: " + tag);
            case LAYOUT_DIALOGRESULT /* 390 */:
                if ("layout/dialog_result_0".equals(tag)) {
                    return new DialogResultBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_result is invalid. Received: " + tag);
            case LAYOUT_DIALOGRGBFUNCTIONTIP /* 391 */:
                if ("layout/dialog_rgb_function_tip_0".equals(tag)) {
                    return new DialogRgbFunctionTipBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_rgb_function_tip is invalid. Received: " + tag);
            case LAYOUT_DIALOGROOMPICKER /* 392 */:
                if ("layout/dialog_room_picker_0".equals(tag)) {
                    return new DialogRoomPickerBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_room_picker is invalid. Received: " + tag);
            case LAYOUT_DIALOGROOMSELECTOR /* 393 */:
                if ("layout/dialog_room_selector_0".equals(tag)) {
                    return new DialogRoomSelectorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_room_selector is invalid. Received: " + tag);
            case LAYOUT_DIALOGSCANNFC /* 394 */:
                if ("layout/dialog_scan_nfc_0".equals(tag)) {
                    return new DialogScanNfcBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_scan_nfc is invalid. Received: " + tag);
            case LAYOUT_DIALOGSELECTBRT /* 395 */:
                if ("layout/dialog_select_brt_0".equals(tag)) {
                    return new DialogSelectBrtBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_select_brt is invalid. Received: " + tag);
            case LAYOUT_DIALOGSELECTCGDACTION /* 396 */:
                if ("layout/dialog_select_cgd_action_0".equals(tag)) {
                    return new DialogSelectCgdActionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_select_cgd_action is invalid. Received: " + tag);
            case LAYOUT_DIALOGSELECTCOLOR /* 397 */:
                if ("layout/dialog_select_color_0".equals(tag)) {
                    return new DialogSelectColorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_select_color is invalid. Received: " + tag);
            case LAYOUT_DIALOGSELECTCT /* 398 */:
                if ("layout/dialog_select_ct_0".equals(tag)) {
                    return new DialogSelectCtBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_select_ct is invalid. Received: " + tag);
            case LAYOUT_DIALOGSELECTDIMCURVE /* 399 */:
                if ("layout/dialog_select_dim_curve_0".equals(tag)) {
                    return new DialogSelectDimCurveBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_select_dim_curve is invalid. Received: " + tag);
            case 400:
                if ("layout/dialog_select_dim_fade_time_0".equals(tag)) {
                    return new DialogSelectDimFadeTimeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_select_dim_fade_time is invalid. Received: " + tag);
            default:
                return null;
        }
    }

    private final ViewDataBinding internalGetViewDataBinding8(DataBindingComponent component, View view, int internalId, Object tag) {
        switch (internalId) {
            case 401:
                if ("layout/dialog_select_dim_range_0".equals(tag)) {
                    return new DialogSelectDimRangeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_select_dim_range is invalid. Received: " + tag);
            case 402:
                if ("layout/dialog_select_gq_theme_0".equals(tag)) {
                    return new DialogSelectGqThemeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_select_gq_theme is invalid. Received: " + tag);
            case 403:
                if ("layout/dialog_select_item_0".equals(tag)) {
                    return new DialogSelectItemBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_select_item is invalid. Received: " + tag);
            case 404:
                if ("layout/dialog_select_k_duv_0".equals(tag)) {
                    return new DialogSelectKDuvBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_select_k_duv is invalid. Received: " + tag);
            case LAYOUT_DIALOGSELECTLIST /* 405 */:
                if ("layout/dialog_select_list_0".equals(tag)) {
                    return new DialogSelectListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_select_list is invalid. Received: " + tag);
            case 406:
                if ("layout/dialog_select_list_and_pic_0".equals(tag)) {
                    return new DialogSelectListAndPicBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_select_list_and_pic is invalid. Received: " + tag);
            case 407:
                if ("layout/dialog_select_lux_0".equals(tag)) {
                    return new DialogSelectLuxBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_select_lux is invalid. Received: " + tag);
            case LAYOUT_DIALOGSELECTSCENE /* 408 */:
                if ("layout/dialog_select_scene_0".equals(tag)) {
                    return new DialogSelectSceneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_select_scene is invalid. Received: " + tag);
            case LAYOUT_DIALOGSELECTVOLUME /* 409 */:
                if ("layout/dialog_select_volume_0".equals(tag)) {
                    return new DialogSelectVolumeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_select_volume is invalid. Received: " + tag);
            case 410:
                if ("layout/dialog_sensing_distance_setting_0".equals(tag)) {
                    return new DialogSensingDistanceSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_sensing_distance_setting is invalid. Received: " + tag);
            case 411:
                if ("layout/dialog_set_ble_type_0".equals(tag)) {
                    return new DialogSetBleTypeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_set_ble_type is invalid. Received: " + tag);
            case 412:
                if ("layout/dialog_set_screen_display_0".equals(tag)) {
                    return new DialogSetScreenDisplayBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_set_screen_display is invalid. Received: " + tag);
            case 413:
                if ("layout/dialog_single_picker_0".equals(tag)) {
                    return new DialogSinglePickerBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_single_picker is invalid. Received: " + tag);
            case 414:
                if ("layout/dialog_sp_quick_0".equals(tag)) {
                    return new DialogSpQuickBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_sp_quick is invalid. Received: " + tag);
            case 415:
                if ("layout/dialog_switch_pattern_selector_0".equals(tag)) {
                    return new DialogSwitchPatternSelectorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_switch_pattern_selector is invalid. Received: " + tag);
            case 416:
                if ("layout/dialog_te_panel_0".equals(tag)) {
                    return new DialogTePanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_te_panel is invalid. Received: " + tag);
            case LAYOUT_DIALOGTIMEINTERVALSELECTOR /* 417 */:
                if ("layout/dialog_time_interval_selector_0".equals(tag)) {
                    return new DialogTimeIntervalSelectorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_time_interval_selector is invalid. Received: " + tag);
            case LAYOUT_DIALOGTIMEPICKER /* 418 */:
                if ("layout/dialog_time_picker_0".equals(tag)) {
                    return new DialogTimePickerBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_time_picker is invalid. Received: " + tag);
            case LAYOUT_DIALOGTIMESELECTOR /* 419 */:
                if ("layout/dialog_time_selector_0".equals(tag)) {
                    return new DialogTimeSelectorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_time_selector is invalid. Received: " + tag);
            case 420:
                if ("layout/dialog_time_selector_with_limit_0".equals(tag)) {
                    return new DialogTimeSelectorWithLimitBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_time_selector_with_limit is invalid. Received: " + tag);
            case 421:
                if ("layout/dialog_time_selector_with_ms_0".equals(tag)) {
                    return new DialogTimeSelectorWithMsBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_time_selector_with_ms is invalid. Received: " + tag);
            case 422:
                if ("layout/dialog_timing_set_0".equals(tag)) {
                    return new DialogTimingSetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_timing_set is invalid. Received: " + tag);
            case 423:
                if ("layout/dialog_tip_0".equals(tag)) {
                    return new DialogTipBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_tip is invalid. Received: " + tag);
            case 424:
                if ("layout/dialog_wheel_select_double_list_0".equals(tag)) {
                    return new DialogWheelSelectDoubleListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_wheel_select_double_list is invalid. Received: " + tag);
            case 425:
                if ("layout/dialog_wheel_select_list_0".equals(tag)) {
                    return new DialogWheelSelectListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_wheel_select_list is invalid. Received: " + tag);
            case 426:
                if ("layout/dialog_white_balance_0".equals(tag)) {
                    return new DialogWhiteBalanceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_white_balance is invalid. Received: " + tag);
            case LAYOUT_DIALOGWY /* 427 */:
                if ("layout/dialog_wy_0".equals(tag)) {
                    return new DialogWyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_wy is invalid. Received: " + tag);
            case LAYOUT_FOOTERADD /* 428 */:
                if ("layout/footer_add_0".equals(tag)) {
                    return new FooterAddBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for footer_add is invalid. Received: " + tag);
            case LAYOUT_FOOTERSUPERPANELKEYSET /* 429 */:
                if ("layout/footer_super_panel_key_set_0".equals(tag)) {
                    return new FooterSuperPanelKeySetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for footer_super_panel_key_set is invalid. Received: " + tag);
            case LAYOUT_FTAC /* 430 */:
                if ("layout/ft_ac_0".equals(tag)) {
                    return new FtAcBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_ac is invalid. Received: " + tag);
            case 431:
                if ("layout/ft_ac_dialog_0".equals(tag)) {
                    return new FtAcDialogBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_ac_dialog is invalid. Received: " + tag);
            case 432:
                if ("layout/ft_access_control_0".equals(tag)) {
                    return new FtAccessControlBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_access_control is invalid. Received: " + tag);
            case 433:
                if ("layout/ft_advanced_mode_0".equals(tag)) {
                    return new FtAdvancedModeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_advanced_mode is invalid. Received: " + tag);
            case 434:
                if ("layout/ft_air_0".equals(tag)) {
                    return new FtAirBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_air is invalid. Received: " + tag);
            case 435:
                if ("layout/ft_air_dialog_0".equals(tag)) {
                    return new FtAirDialogBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_air_dialog is invalid. Received: " + tag);
            case 436:
                if ("layout/ft_automation_0".equals(tag)) {
                    return new FtAutomationBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_automation is invalid. Received: " + tag);
            case 437:
                if ("layout/ft_bind_mail_0".equals(tag)) {
                    return new FtBindMailBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_bind_mail is invalid. Received: " + tag);
            case LAYOUT_FTBINDPHONE /* 438 */:
                if ("layout/ft_bind_phone_0".equals(tag)) {
                    return new FtBindPhoneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_bind_phone is invalid. Received: " + tag);
            case LAYOUT_FTCAMERAPTZ /* 439 */:
                if ("layout/ft_camera_ptz_0".equals(tag)) {
                    return new FtCameraPtzBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_camera_ptz is invalid. Received: " + tag);
            case LAYOUT_FTCAMERARECORD /* 440 */:
                if ("layout/ft_camera_record_0".equals(tag)) {
                    return new FtCameraRecordBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_camera_record is invalid. Received: " + tag);
            case 441:
                if ("layout/ft_camera_talk_0".equals(tag)) {
                    return new FtCameraTalkBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_camera_talk is invalid. Received: " + tag);
            case 442:
                if ("layout/ft_clip_photo_0".equals(tag)) {
                    return new FtClipPhotoBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_clip_photo is invalid. Received: " + tag);
            case 443:
                if ("layout/ft_cloud_scene_0".equals(tag)) {
                    return new FtCloudSceneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_cloud_scene is invalid. Received: " + tag);
            case 444:
                if ("layout/ft_color_cct_0".equals(tag)) {
                    return new FtColorCctBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_color_cct is invalid. Received: " + tag);
            case 445:
                if ("layout/ft_color_circle_0".equals(tag)) {
                    return new FtColorCircleBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_color_circle is invalid. Received: " + tag);
            case 446:
                if ("layout/ft_color_hsl_0".equals(tag)) {
                    return new FtColorHslBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_color_hsl is invalid. Received: " + tag);
            case LAYOUT_FTCOLORPUSHROD /* 447 */:
                if ("layout/ft_color_pushrod_0".equals(tag)) {
                    return new FtColorPushrodBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_color_pushrod is invalid. Received: " + tag);
            case LAYOUT_FTCOLORXYY /* 448 */:
                if ("layout/ft_color_xyy_0".equals(tag)) {
                    return new FtColorXyyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_color_xyy is invalid. Received: " + tag);
            case LAYOUT_FTCTLIGHT /* 449 */:
                if ("layout/ft_ct_light_0".equals(tag)) {
                    return new FtCtLightBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_ct_light is invalid. Received: " + tag);
            case LAYOUT_FTDALIADD /* 450 */:
                if ("layout/ft_dali_add_0".equals(tag)) {
                    return new FtDaliAddBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_dali_add is invalid. Received: " + tag);
            default:
                return null;
        }
    }

    private final ViewDataBinding internalGetViewDataBinding9(DataBindingComponent component, View view, int internalId, Object tag) {
        switch (internalId) {
            case LAYOUT_FTDALIPUSHROD /* 451 */:
                if ("layout/ft_dali_pushrod_0".equals(tag)) {
                    return new FtDaliPushrodBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_dali_pushrod is invalid. Received: " + tag);
            case LAYOUT_FTDEVICE2 /* 452 */:
                if ("layout/ft_device2_0".equals(tag)) {
                    return new FtDevice2BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_device2 is invalid. Received: " + tag);
            case LAYOUT_FTDEVICEANDGROUP /* 453 */:
                if ("layout/ft_device_and_group_0".equals(tag)) {
                    return new FtDeviceAndGroupBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_device_and_group is invalid. Received: " + tag);
            case LAYOUT_FTDIMLIGHT /* 454 */:
                if ("layout/ft_dim_light_0".equals(tag)) {
                    return new FtDimLightBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_dim_light is invalid. Received: " + tag);
            case LAYOUT_FTFINDMAILPWD /* 455 */:
                if ("layout/ft_find_mail_pwd_0".equals(tag)) {
                    return new FtFindMailPwdBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_find_mail_pwd is invalid. Received: " + tag);
            case LAYOUT_FTFINDPHONEPWD /* 456 */:
                if ("layout/ft_find_phone_pwd_0".equals(tag)) {
                    return new FtFindPhonePwdBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_find_phone_pwd is invalid. Received: " + tag);
            case LAYOUT_FTGENERALMODE /* 457 */:
                if ("layout/ft_general_mode_0".equals(tag)) {
                    return new FtGeneralModeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_general_mode is invalid. Received: " + tag);
            case LAYOUT_FTGQXACTION /* 458 */:
                if ("layout/ft_gqx_action_0".equals(tag)) {
                    return new FtGqxActionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_gqx_action is invalid. Received: " + tag);
            case LAYOUT_FTINTELLIGENCE /* 459 */:
                if ("layout/ft_intelligence_0".equals(tag)) {
                    return new FtIntelligenceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_intelligence is invalid. Received: " + tag);
            case LAYOUT_FTLIST /* 460 */:
                if ("layout/ft_list_0".equals(tag)) {
                    return new FtListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_list is invalid. Received: " + tag);
            case LAYOUT_FTLOCALSCENE /* 461 */:
                if ("layout/ft_local_scene_0".equals(tag)) {
                    return new FtLocalSceneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_local_scene is invalid. Received: " + tag);
            case LAYOUT_FTLOG /* 462 */:
                if ("layout/ft_log_0".equals(tag)) {
                    return new FtLogBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_log is invalid. Received: " + tag);
            case LAYOUT_FTMAILREG /* 463 */:
                if ("layout/ft_mail_reg_0".equals(tag)) {
                    return new FtMailRegBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_mail_reg is invalid. Received: " + tag);
            case LAYOUT_FTMESSAGEHOME /* 464 */:
                if ("layout/ft_message_home_0".equals(tag)) {
                    return new FtMessageHomeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_message_home is invalid. Received: " + tag);
            case LAYOUT_FTMESSAGENOTICE /* 465 */:
                if ("layout/ft_message_notice_0".equals(tag)) {
                    return new FtMessageNoticeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_message_notice is invalid. Received: " + tag);
            case LAYOUT_FTMIC /* 466 */:
                if ("layout/ft_mic_0".equals(tag)) {
                    return new FtMicBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_mic is invalid. Received: " + tag);
            case LAYOUT_FTMUSIC /* 467 */:
                if ("layout/ft_music_0".equals(tag)) {
                    return new FtMusicBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_music is invalid. Received: " + tag);
            case LAYOUT_FTMY /* 468 */:
                if ("layout/ft_my_0".equals(tag)) {
                    return new FtMyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_my is invalid. Received: " + tag);
            case LAYOUT_FTPAGESCREENPANELDETAIL /* 469 */:
                if ("layout/ft_page_screen_panel_detail_0".equals(tag)) {
                    return new FtPageScreenPanelDetailBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_page_screen_panel_detail is invalid. Received: " + tag);
            case LAYOUT_FTPAGESCREENPANELSWITCH /* 470 */:
                if ("layout/ft_page_screen_panel_switch_0".equals(tag)) {
                    return new FtPageScreenPanelSwitchBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_page_screen_panel_switch is invalid. Received: " + tag);
            case LAYOUT_FTPHONEREG /* 471 */:
                if ("layout/ft_phone_reg_0".equals(tag)) {
                    return new FtPhoneRegBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_phone_reg is invalid. Received: " + tag);
            case LAYOUT_FTRGBLIGHT /* 472 */:
                if ("layout/ft_rgb_light_0".equals(tag)) {
                    return new FtRgbLightBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_rgb_light is invalid. Received: " + tag);
            case LAYOUT_FTROOM /* 473 */:
                if ("layout/ft_room_0".equals(tag)) {
                    return new FtRoomBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_room is invalid. Received: " + tag);
            case LAYOUT_FTROOMDALIADD /* 474 */:
                if ("layout/ft_room_dali_add_0".equals(tag)) {
                    return new FtRoomDaliAddBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_room_dali_add is invalid. Received: " + tag);
            case LAYOUT_FTSCENE /* 475 */:
                if ("layout/ft_scene_0".equals(tag)) {
                    return new FtSceneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_scene is invalid. Received: " + tag);
            case LAYOUT_FTSELECTICONS /* 476 */:
                if ("layout/ft_select_icons_0".equals(tag)) {
                    return new FtSelectIconsBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_select_icons is invalid. Received: " + tag);
            case LAYOUT_FTSELECTPRODUCT /* 477 */:
                if ("layout/ft_select_product_0".equals(tag)) {
                    return new FtSelectProductBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_select_product is invalid. Received: " + tag);
            case LAYOUT_FTSELECTTHEME /* 478 */:
                if ("layout/ft_select_theme_0".equals(tag)) {
                    return new FtSelectThemeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_select_theme is invalid. Received: " + tag);
            case LAYOUT_FTSENSORTESTSTEP /* 479 */:
                if ("layout/ft_sensor_test_step_0".equals(tag)) {
                    return new FtSensorTestStepBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_sensor_test_step is invalid. Received: " + tag);
            case 480:
                if ("layout/ft_song_playlist_0".equals(tag)) {
                    return new FtSongPlaylistBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_song_playlist is invalid. Received: " + tag);
            case LAYOUT_FTSONGS /* 481 */:
                if ("layout/ft_songs_0".equals(tag)) {
                    return new FtSongsBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_songs is invalid. Received: " + tag);
            case LAYOUT_FTSPICOLOR /* 482 */:
                if ("layout/ft_spi_color_0".equals(tag)) {
                    return new FtSpiColorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_spi_color is invalid. Received: " + tag);
            case LAYOUT_FTSPIMODE /* 483 */:
                if ("layout/ft_spi_mode_0".equals(tag)) {
                    return new FtSpiModeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_spi_mode is invalid. Received: " + tag);
            case LAYOUT_FTSPIPLAYLIST /* 484 */:
                if ("layout/ft_spi_play_list_0".equals(tag)) {
                    return new FtSpiPlayListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_spi_play_list is invalid. Received: " + tag);
            case LAYOUT_FTSTEPSINTRODUCTION /* 485 */:
                if ("layout/ft_steps_introduction_0".equals(tag)) {
                    return new FtStepsIntroductionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_steps_introduction is invalid. Received: " + tag);
            case LAYOUT_FTSUBDEVICE /* 486 */:
                if ("layout/ft_sub_device_0".equals(tag)) {
                    return new FtSubDeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_sub_device is invalid. Received: " + tag);
            case 487:
                if ("layout/ft_tv_ext_fun_0".equals(tag)) {
                    return new FtTvExtFunBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_tv_ext_fun is invalid. Received: " + tag);
            case 488:
                if ("layout/ft_tv_fun_0".equals(tag)) {
                    return new FtTvFunBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_tv_fun is invalid. Received: " + tag);
            case LAYOUT_FTTVNUM /* 489 */:
                if ("layout/ft_tv_num_0".equals(tag)) {
                    return new FtTvNumBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for ft_tv_num is invalid. Received: " + tag);
            case LAYOUT_HEADG4MAXKEYSET /* 490 */:
                if ("layout/head_g4_max_key_set_0".equals(tag)) {
                    return new HeadG4MaxKeySetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for head_g4_max_key_set is invalid. Received: " + tag);
            case LAYOUT_HEADSUPERPANELKEYSET /* 491 */:
                if ("layout/head_super_panel_key_set_0".equals(tag)) {
                    return new HeadSuperPanelKeySetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for head_super_panel_key_set is invalid. Received: " + tag);
            case LAYOUT_ITEM485TOBLE /* 492 */:
                if ("layout/item_485_to_ble_0".equals(tag)) {
                    return new Item485ToBleBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_485_to_ble is invalid. Received: " + tag);
            case LAYOUT_ITEMADDGROUP /* 493 */:
                if ("layout/item_add_group_0".equals(tag)) {
                    return new ItemAddGroupBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_add_group is invalid. Received: " + tag);
            case LAYOUT_ITEMADDINSTRUCTCONTENT /* 494 */:
                if ("layout/item_add_instruct_content_0".equals(tag)) {
                    return new ItemAddInstructContentBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_add_instruct_content is invalid. Received: " + tag);
            case LAYOUT_ITEMADDINSTRUCTTITLE /* 495 */:
                if ("layout/item_add_instruct_title_0".equals(tag)) {
                    return new ItemAddInstructTitleBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_add_instruct_title is invalid. Received: " + tag);
            case LAYOUT_ITEMADVANCEDMODEADD /* 496 */:
                if ("layout/item_advanced_mode_add_0".equals(tag)) {
                    return new ItemAdvancedModeAddBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_advanced_mode_add is invalid. Received: " + tag);
            case LAYOUT_ITEMADVANCEDMODECOLORTIME /* 497 */:
                if ("layout/item_advanced_mode_color_time_0".equals(tag)) {
                    return new ItemAdvancedModeColorTimeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_advanced_mode_color_time is invalid. Received: " + tag);
            case LAYOUT_ITEMASPANELRELATEDINFO /* 498 */:
                if ("layout/item_as_panel_related_info_0".equals(tag)) {
                    return new ItemAsPanelRelatedInfoBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_as_panel_related_info is invalid. Received: " + tag);
            case LAYOUT_ITEMAUTOCONDITION /* 499 */:
                if ("layout/item_auto_condition_0".equals(tag)) {
                    return new ItemAutoConditionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_auto_condition is invalid. Received: " + tag);
            case 500:
                if ("layout/item_automation_0".equals(tag)) {
                    return new ItemAutomationBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_automation is invalid. Received: " + tag);
            default:
                return null;
        }
    }

    private final ViewDataBinding internalGetViewDataBinding10(DataBindingComponent component, View view, int internalId, Object tag) {
        switch (internalId) {
            case 501:
                if ("layout/item_batch_setting_0".equals(tag)) {
                    return new ItemBatchSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_batch_setting is invalid. Received: " + tag);
            case 502:
                if ("layout/item_big_content_left_0".equals(tag)) {
                    return new ItemBigContentLeftBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_big_content_left is invalid. Received: " + tag);
            case 503:
                if ("layout/item_big_content_right_0".equals(tag)) {
                    return new ItemBigContentRightBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_big_content_right is invalid. Received: " + tag);
            case 504:
                if ("layout/item_bind_eur_scene_0".equals(tag)) {
                    return new ItemBindEurSceneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_bind_eur_scene is invalid. Received: " + tag);
            case 505:
                if ("layout/item_bind_zone_0".equals(tag)) {
                    return new ItemBindZoneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_bind_zone is invalid. Received: " + tag);
            case 506:
                if ("layout/item_ble_to_485_0".equals(tag)) {
                    return new ItemBleTo485BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_ble_to_485 is invalid. Received: " + tag);
            case LAYOUT_ITEMBTOTA /* 507 */:
                if ("layout/item_bt_ota_0".equals(tag)) {
                    return new ItemBtOtaBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_bt_ota is invalid. Received: " + tag);
            case LAYOUT_ITEMCATEGORY /* 508 */:
                if ("layout/item_category_0".equals(tag)) {
                    return new ItemCategoryBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_category is invalid. Received: " + tag);
            case LAYOUT_ITEMCENTRALAIRSUBDEVICE /* 509 */:
                if ("layout/item_central_air_sub_device_0".equals(tag)) {
                    return new ItemCentralAirSubDeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_central_air_sub_device is invalid. Received: " + tag);
            case 510:
                if ("layout/item_cg485_color_0".equals(tag)) {
                    return new ItemCg485ColorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_cg485_color is invalid. Received: " + tag);
            case 511:
                if ("layout/item_cgd_0".equals(tag)) {
                    return new ItemCgdBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_cgd is invalid. Received: " + tag);
            case 512:
                if ("layout/item_cgd_action_0".equals(tag)) {
                    return new ItemCgdActionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_cgd_action is invalid. Received: " + tag);
            case 513:
                if ("layout/item_cloud_scene_0".equals(tag)) {
                    return new ItemCloudSceneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_cloud_scene is invalid. Received: " + tag);
            case 514:
                if ("layout/item_color_0".equals(tag)) {
                    return new ItemColorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_color is invalid. Received: " + tag);
            case 515:
                if ("layout/item_color_point_0".equals(tag)) {
                    return new ItemColorPointBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_color_point is invalid. Received: " + tag);
            case 516:
                if ("layout/item_command_0".equals(tag)) {
                    return new ItemCommandBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_command is invalid. Received: " + tag);
            case 517:
                if ("layout/item_content_normal_0".equals(tag)) {
                    return new ItemContentNormalBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_content_normal is invalid. Received: " + tag);
            case 518:
                if ("layout/item_content_small_0".equals(tag)) {
                    return new ItemContentSmallBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_content_small is invalid. Received: " + tag);
            case 519:
                if ("layout/item_ct_light_group_control_sub_device_0".equals(tag)) {
                    return new ItemCtLightGroupControlSubDeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_ct_light_group_control_sub_device is invalid. Received: " + tag);
            case 520:
                if ("layout/item_curtain_currainmotortype_select_0".equals(tag)) {
                    return new ItemCurtainCurrainmotortypeSelectBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_curtain_currainmotortype_select is invalid. Received: " + tag);
            case 521:
                if ("layout/item_curtain_open_dir_select_0".equals(tag)) {
                    return new ItemCurtainOpenDirSelectBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_curtain_open_dir_select is invalid. Received: " + tag);
            case 522:
                if ("layout/item_dali_batch_modify_param_0".equals(tag)) {
                    return new ItemDaliBatchModifyParamBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_dali_batch_modify_param is invalid. Received: " + tag);
            case LAYOUT_ITEMDALILIGHTMANAGE /* 523 */:
                if ("layout/item_dali_light_manage_0".equals(tag)) {
                    return new ItemDaliLightManageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_dali_light_manage is invalid. Received: " + tag);
            case LAYOUT_ITEMDALIMANAGE /* 524 */:
                if ("layout/item_dali_manage_0".equals(tag)) {
                    return new ItemDaliManageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_dali_manage is invalid. Received: " + tag);
            case LAYOUT_ITEMDEFAULT /* 525 */:
                if ("layout/item_default_0".equals(tag)) {
                    return new ItemDefaultBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_default is invalid. Received: " + tag);
            case LAYOUT_ITEMDEFAULTMODE /* 526 */:
                if ("layout/item_default_mode_0".equals(tag)) {
                    return new ItemDefaultModeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_default_mode is invalid. Received: " + tag);
            case LAYOUT_ITEMDEFAULTMODESELECT /* 527 */:
                if ("layout/item_default_mode_select_0".equals(tag)) {
                    return new ItemDefaultModeSelectBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_default_mode_select is invalid. Received: " + tag);
            case 528:
                if ("layout/item_device_as_panel_0".equals(tag)) {
                    return new ItemDeviceAsPanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_device_as_panel is invalid. Received: " + tag);
            case 529:
                if ("layout/item_device_ble_0".equals(tag)) {
                    return new ItemDeviceBleBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_device_ble is invalid. Received: " + tag);
            case 530:
                if ("layout/item_device_camera_0".equals(tag)) {
                    return new ItemDeviceCameraBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_device_camera is invalid. Received: " + tag);
            case 531:
                if ("layout/item_device_central_air_gate_0".equals(tag)) {
                    return new ItemDeviceCentralAirGateBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_device_central_air_gate is invalid. Received: " + tag);
            case 532:
                if ("layout/item_device_group_manage_0".equals(tag)) {
                    return new ItemDeviceGroupManageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_device_group_manage is invalid. Received: " + tag);
            case 533:
                if ("layout/item_device_ir_0".equals(tag)) {
                    return new ItemDeviceIrBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_device_ir is invalid. Received: " + tag);
            case 534:
                if ("layout/item_device_key_switch_0".equals(tag)) {
                    return new ItemDeviceKeySwitchBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_device_key_switch is invalid. Received: " + tag);
            case 535:
                if ("layout/item_device_light_0".equals(tag)) {
                    return new ItemDeviceLightBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_device_light is invalid. Received: " + tag);
            case 536:
                if ("layout/item_device_log_0".equals(tag)) {
                    return new ItemDeviceLogBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_device_log is invalid. Received: " + tag);
            case 537:
                if ("layout/item_device_manage_0".equals(tag)) {
                    return new ItemDeviceManageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_device_manage is invalid. Received: " + tag);
            case 538:
                if ("layout/item_device_manage_new_0".equals(tag)) {
                    return new ItemDeviceManageNewBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_device_manage_new is invalid. Received: " + tag);
            case 539:
                if ("layout/item_device_mesh_gateway_0".equals(tag)) {
                    return new ItemDeviceMeshGatewayBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_device_mesh_gateway is invalid. Received: " + tag);
            case 540:
                if ("layout/item_device_music_player_0".equals(tag)) {
                    return new ItemDeviceMusicPlayerBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_device_music_player is invalid. Received: " + tag);
            case 541:
                if ("layout/item_device_scene_0".equals(tag)) {
                    return new ItemDeviceSceneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_device_scene is invalid. Received: " + tag);
            case 542:
                if ("layout/item_device_select_0".equals(tag)) {
                    return new ItemDeviceSelectBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_device_select is invalid. Received: " + tag);
            case 543:
                if ("layout/item_device_sensor_0".equals(tag)) {
                    return new ItemDeviceSensorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_device_sensor is invalid. Received: " + tag);
            case 544:
                if ("layout/item_device_sort_0".equals(tag)) {
                    return new ItemDeviceSortBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_device_sort is invalid. Received: " + tag);
            case 545:
                if ("layout/item_device_super_panel_0".equals(tag)) {
                    return new ItemDeviceSuperPanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_device_super_panel is invalid. Received: " + tag);
            case 546:
                if ("layout/item_diy_mode_0".equals(tag)) {
                    return new ItemDiyModeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_diy_mode is invalid. Received: " + tag);
            case 547:
                if ("layout/item_double_text_0".equals(tag)) {
                    return new ItemDoubleTextBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_double_text is invalid. Received: " + tag);
            case 548:
                if ("layout/item_e6_panel_key_0".equals(tag)) {
                    return new ItemE6PanelKeyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_e6_panel_key is invalid. Received: " + tag);
            case 549:
                if ("layout/item_e6d_action_0".equals(tag)) {
                    return new ItemE6dActionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_e6d_action is invalid. Received: " + tag);
            case 550:
                if ("layout/item_e6d_address_0".equals(tag)) {
                    return new ItemE6dAddressBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_e6d_address is invalid. Received: " + tag);
            default:
                return null;
        }
    }

    private final ViewDataBinding internalGetViewDataBinding11(DataBindingComponent component, View view, int internalId, Object tag) {
        switch (internalId) {
            case 551:
                if ("layout/item_e6d_default_0".equals(tag)) {
                    return new ItemE6dDefaultBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_e6d_default is invalid. Received: " + tag);
            case 552:
                if ("layout/item_edit_diy_key_name_0".equals(tag)) {
                    return new ItemEditDiyKeyNameBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_edit_diy_key_name is invalid. Received: " + tag);
            case 553:
                if ("layout/item_empty_foot_0".equals(tag)) {
                    return new ItemEmptyFootBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_empty_foot is invalid. Received: " + tag);
            case 554:
                if ("layout/item_empty_foot_15_0".equals(tag)) {
                    return new ItemEmptyFoot15BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_empty_foot_15 is invalid. Received: " + tag);
            case 555:
                if ("layout/item_env_status_detail_condition_0".equals(tag)) {
                    return new ItemEnvStatusDetailConditionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_env_status_detail_condition is invalid. Received: " + tag);
            case 556:
                if ("layout/item_floor_manage_0".equals(tag)) {
                    return new ItemFloorManageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_floor_manage is invalid. Received: " + tag);
            case 557:
                if ("layout/item_fresh_air_info_0".equals(tag)) {
                    return new ItemFreshAirInfoBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_fresh_air_info is invalid. Received: " + tag);
            case 558:
                if ("layout/item_general_mode_0".equals(tag)) {
                    return new ItemGeneralModeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_general_mode is invalid. Received: " + tag);
            case 559:
                if ("layout/item_go_0".equals(tag)) {
                    return new ItemGoBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_go is invalid. Received: " + tag);
            case 560:
                if ("layout/item_go_1_0".equals(tag)) {
                    return new ItemGo1BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_go_1 is invalid. Received: " + tag);
            case 561:
                if ("layout/item_go_2_0".equals(tag)) {
                    return new ItemGo2BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_go_2 is invalid. Received: " + tag);
            case LAYOUT_ITEMGROUPDEVICESORT /* 562 */:
                if ("layout/item_group_device_sort_0".equals(tag)) {
                    return new ItemGroupDeviceSortBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_group_device_sort is invalid. Received: " + tag);
            case 563:
                if ("layout/item_group_light_0".equals(tag)) {
                    return new ItemGroupLightBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_group_light is invalid. Received: " + tag);
            case 564:
                if ("layout/item_group_manage_0".equals(tag)) {
                    return new ItemGroupManageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_group_manage is invalid. Received: " + tag);
            case 565:
                if ("layout/item_group_select_0".equals(tag)) {
                    return new ItemGroupSelectBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_group_select is invalid. Received: " + tag);
            case 566:
                if ("layout/item_group_smart_panel_0".equals(tag)) {
                    return new ItemGroupSmartPanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_group_smart_panel is invalid. Received: " + tag);
            case 567:
                if ("layout/item_group_switch_pattern_0".equals(tag)) {
                    return new ItemGroupSwitchPatternBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_group_switch_pattern is invalid. Received: " + tag);
            case 568:
                if ("layout/item_group_wave_sensor_0".equals(tag)) {
                    return new ItemGroupWaveSensorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_group_wave_sensor is invalid. Received: " + tag);
            case 569:
                if ("layout/item_home_manage_0".equals(tag)) {
                    return new ItemHomeManageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_home_manage is invalid. Received: " + tag);
            case 570:
                if ("layout/item_img_0".equals(tag)) {
                    return new ItemImgBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_img is invalid. Received: " + tag);
            case 571:
                if ("layout/item_img_preview_0".equals(tag)) {
                    return new ItemImgPreviewBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_img_preview is invalid. Received: " + tag);
            case 572:
                if ("layout/item_intercom_door_0".equals(tag)) {
                    return new ItemIntercomDoorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_intercom_door is invalid. Received: " + tag);
            case 573:
                if ("layout/item_intercom_log_call_0".equals(tag)) {
                    return new ItemIntercomLogCallBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_intercom_log_call is invalid. Received: " + tag);
            case 574:
                if ("layout/item_intercom_log_open_door_0".equals(tag)) {
                    return new ItemIntercomLogOpenDoorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_intercom_log_open_door is invalid. Received: " + tag);
            case 575:
                if ("layout/item_intercom_log_title_0".equals(tag)) {
                    return new ItemIntercomLogTitleBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_intercom_log_title is invalid. Received: " + tag);
            case 576:
                if ("layout/item_ir_diy_fun_0".equals(tag)) {
                    return new ItemIrDiyFunBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_ir_diy_fun is invalid. Received: " + tag);
            case 577:
                if ("layout/item_ir_diy_key_0".equals(tag)) {
                    return new ItemIrDiyKeyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_ir_diy_key is invalid. Received: " + tag);
            case 578:
                if ("layout/item_ir_fun_0".equals(tag)) {
                    return new ItemIrFunBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_ir_fun is invalid. Received: " + tag);
            case 579:
                if ("layout/item_ir_key_0".equals(tag)) {
                    return new ItemIrKeyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_ir_key is invalid. Received: " + tag);
            case 580:
                if ("layout/item_ir_key_te_0".equals(tag)) {
                    return new ItemIrKeyTeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_ir_key_te is invalid. Received: " + tag);
            case 581:
                if ("layout/item_k_and_duv_0".equals(tag)) {
                    return new ItemKAndDuvBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_k_and_duv is invalid. Received: " + tag);
            case 582:
                if ("layout/item_light_action_0".equals(tag)) {
                    return new ItemLightActionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_light_action is invalid. Received: " + tag);
            case 583:
                if ("layout/item_light_group_control_0".equals(tag)) {
                    return new ItemLightGroupControlBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_light_group_control is invalid. Received: " + tag);
            case 584:
                if ("layout/item_light_group_control_sub_device_0".equals(tag)) {
                    return new ItemLightGroupControlSubDeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_light_group_control_sub_device is invalid. Received: " + tag);
            case 585:
                if ("layout/item_light_order_0".equals(tag)) {
                    return new ItemLightOrderBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_light_order is invalid. Received: " + tag);
            case 586:
                if ("layout/item_mesh_scan_0".equals(tag)) {
                    return new ItemMeshScanBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_mesh_scan is invalid. Received: " + tag);
            case 587:
                if ("layout/item_mesh_scan_all_device_0".equals(tag)) {
                    return new ItemMeshScanAllDeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_mesh_scan_all_device is invalid. Received: " + tag);
            case 588:
                if ("layout/item_mesh_scan_replace_0".equals(tag)) {
                    return new ItemMeshScanReplaceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_mesh_scan_replace is invalid. Received: " + tag);
            case 589:
                if ("layout/item_mesh_search_scan_0".equals(tag)) {
                    return new ItemMeshSearchScanBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_mesh_search_scan is invalid. Received: " + tag);
            case 590:
                if ("layout/item_message_data_footer_0".equals(tag)) {
                    return new ItemMessageDataFooterBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_message_data_footer is invalid. Received: " + tag);
            case 591:
                if ("layout/item_message_notice_0".equals(tag)) {
                    return new ItemMessageNoticeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_message_notice is invalid. Received: " + tag);
            case 592:
                if ("layout/item_message_place_0".equals(tag)) {
                    return new ItemMessagePlaceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_message_place is invalid. Received: " + tag);
            case 593:
                if ("layout/item_mode_0".equals(tag)) {
                    return new ItemModeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_mode is invalid. Received: " + tag);
            case 594:
                if ("layout/item_mr_param_0".equals(tag)) {
                    return new ItemMrParamBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_mr_param is invalid. Received: " + tag);
            case 595:
                if ("layout/item_music_0".equals(tag)) {
                    return new ItemMusicBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_music is invalid. Received: " + tag);
            case 596:
                if ("layout/item_music_dialog_list_0".equals(tag)) {
                    return new ItemMusicDialogListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_music_dialog_list is invalid. Received: " + tag);
            case 597:
                if ("layout/item_music_list_0".equals(tag)) {
                    return new ItemMusicListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_music_list is invalid. Received: " + tag);
            case 598:
                if ("layout/item_music_search_0".equals(tag)) {
                    return new ItemMusicSearchBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_music_search is invalid. Received: " + tag);
            case 599:
                if ("layout/item_name_value_0".equals(tag)) {
                    return new ItemNameValueBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_name_value is invalid. Received: " + tag);
            case 600:
                if ("layout/item_num_panel_switch_position_set_0".equals(tag)) {
                    return new ItemNumPanelSwitchPositionSetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_num_panel_switch_position_set is invalid. Received: " + tag);
            default:
                return null;
        }
    }

    private final ViewDataBinding internalGetViewDataBinding12(DataBindingComponent component, View view, int internalId, Object tag) {
        switch (internalId) {
            case 601:
                if ("layout/item_pad_content_0".equals(tag)) {
                    return new ItemPadContentBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_pad_content is invalid. Received: " + tag);
            case 602:
                if ("layout/item_page_screen_panel_0".equals(tag)) {
                    return new ItemPageScreenPanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_page_screen_panel is invalid. Received: " + tag);
            case 603:
                if ("layout/item_page_smart_panel_key_set_0".equals(tag)) {
                    return new ItemPageSmartPanelKeySetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_page_smart_panel_key_set is invalid. Received: " + tag);
            case 604:
                if ("layout/item_panel_bind_0".equals(tag)) {
                    return new ItemPanelBindBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_panel_bind is invalid. Received: " + tag);
            case 605:
                if ("layout/item_panel_switch_position_set_0".equals(tag)) {
                    return new ItemPanelSwitchPositionSetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_panel_switch_position_set is invalid. Received: " + tag);
            case 606:
                if ("layout/item_pic_0".equals(tag)) {
                    return new ItemPicBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_pic is invalid. Received: " + tag);
            case 607:
                if ("layout/item_place_user_0".equals(tag)) {
                    return new ItemPlaceUserBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_place_user is invalid. Received: " + tag);
            case 608:
                if ("layout/item_place_user_transform_place_0".equals(tag)) {
                    return new ItemPlaceUserTransformPlaceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_place_user_transform_place is invalid. Received: " + tag);
            case 609:
                if ("layout/item_playlist_manager_0".equals(tag)) {
                    return new ItemPlaylistManagerBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_playlist_manager is invalid. Received: " + tag);
            case 610:
                if ("layout/item_relate_info_0".equals(tag)) {
                    return new ItemRelateInfoBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_relate_info is invalid. Received: " + tag);
            case 611:
                if ("layout/item_rgbcw_duv_0".equals(tag)) {
                    return new ItemRgbcwDuvBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_rgbcw_duv is invalid. Received: " + tag);
            case 612:
                if ("layout/item_room_manage_0".equals(tag)) {
                    return new ItemRoomManageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_room_manage is invalid. Received: " + tag);
            case 613:
                if ("layout/item_room_name_0".equals(tag)) {
                    return new ItemRoomNameBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_room_name is invalid. Received: " + tag);
            case 614:
                if ("layout/item_rs8_key_0".equals(tag)) {
                    return new ItemRs8KeyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_rs8_key is invalid. Received: " + tag);
            case 615:
                if ("layout/item_rs8_sub_device_0".equals(tag)) {
                    return new ItemRs8SubDeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_rs8_sub_device is invalid. Received: " + tag);
            case 616:
                if ("layout/item_scene_action_0".equals(tag)) {
                    return new ItemSceneActionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_scene_action is invalid. Received: " + tag);
            case 617:
                if ("layout/item_scene_eur_0".equals(tag)) {
                    return new ItemSceneEurBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_scene_eur is invalid. Received: " + tag);
            case 618:
                if ("layout/item_scene_new_0".equals(tag)) {
                    return new ItemSceneNewBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_scene_new is invalid. Received: " + tag);
            case 619:
                if ("layout/item_scene_panel_key_0".equals(tag)) {
                    return new ItemScenePanelKeyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_scene_panel_key is invalid. Received: " + tag);
            case 620:
                if ("layout/item_screen_display_0".equals(tag)) {
                    return new ItemScreenDisplayBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_screen_display is invalid. Received: " + tag);
            case 621:
                if ("layout/item_screen_info_input_0".equals(tag)) {
                    return new ItemScreenInfoInputBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_screen_info_input is invalid. Received: " + tag);
            case 622:
                if ("layout/item_screen_panel_0".equals(tag)) {
                    return new ItemScreenPanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_screen_panel is invalid. Received: " + tag);
            case 623:
                if ("layout/item_screen_panel_mult_line_0".equals(tag)) {
                    return new ItemScreenPanelMultLineBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_screen_panel_mult_line is invalid. Received: " + tag);
            case 624:
                if ("layout/item_screen_sp_btn_key_0".equals(tag)) {
                    return new ItemScreenSpBtnKeyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_screen_sp_btn_key is invalid. Received: " + tag);
            case 625:
                if ("layout/item_search_bar_0".equals(tag)) {
                    return new ItemSearchBarBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_search_bar is invalid. Received: " + tag);
            case 626:
                if ("layout/item_search_bar_no_edit_0".equals(tag)) {
                    return new ItemSearchBarNoEditBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_search_bar_no_edit is invalid. Received: " + tag);
            case 627:
                if ("layout/item_search_bar_no_edit_music_0".equals(tag)) {
                    return new ItemSearchBarNoEditMusicBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_search_bar_no_edit_music is invalid. Received: " + tag);
            case 628:
                if ("layout/item_select_0".equals(tag)) {
                    return new ItemSelectBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select is invalid. Received: " + tag);
            case 629:
                if ("layout/item_select2_0".equals(tag)) {
                    return new ItemSelect2BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select2 is invalid. Received: " + tag);
            case 630:
                if ("layout/item_select_automation_0".equals(tag)) {
                    return new ItemSelectAutomationBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_automation is invalid. Received: " + tag);
            case 631:
                if ("layout/item_select_cloud_scene_0".equals(tag)) {
                    return new ItemSelectCloudSceneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_cloud_scene is invalid. Received: " + tag);
            case 632:
                if ("layout/item_select_cover_0".equals(tag)) {
                    return new ItemSelectCoverBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_cover is invalid. Received: " + tag);
            case 633:
                if ("layout/item_select_dali_with_place_0".equals(tag)) {
                    return new ItemSelectDaliWithPlaceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_dali_with_place is invalid. Received: " + tag);
            case 634:
                if ("layout/item_select_device_icon_0".equals(tag)) {
                    return new ItemSelectDeviceIconBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_device_icon is invalid. Received: " + tag);
            case 635:
                if ("layout/item_select_device_icon_horizontal_0".equals(tag)) {
                    return new ItemSelectDeviceIconHorizontalBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_device_icon_horizontal is invalid. Received: " + tag);
            case 636:
                if ("layout/item_select_device_import_mode_0".equals(tag)) {
                    return new ItemSelectDeviceImportModeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_device_import_mode is invalid. Received: " + tag);
            case 637:
                if ("layout/item_select_device_in_group_0".equals(tag)) {
                    return new ItemSelectDeviceInGroupBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_device_in_group is invalid. Received: " + tag);
            case 638:
                if ("layout/item_select_device_manage_0".equals(tag)) {
                    return new ItemSelectDeviceManageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_device_manage is invalid. Received: " + tag);
            case 639:
                if ("layout/item_select_device_with_place_0".equals(tag)) {
                    return new ItemSelectDeviceWithPlaceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_device_with_place is invalid. Received: " + tag);
            case 640:
                if ("layout/item_select_device_with_sync_0".equals(tag)) {
                    return new ItemSelectDeviceWithSyncBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_device_with_sync is invalid. Received: " + tag);
            case 641:
                if ("layout/item_select_diy_0".equals(tag)) {
                    return new ItemSelectDiyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_diy is invalid. Received: " + tag);
            case 642:
                if ("layout/item_select_gateway_0".equals(tag)) {
                    return new ItemSelectGatewayBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_gateway is invalid. Received: " + tag);
            case 643:
                if ("layout/item_select_home_member_0".equals(tag)) {
                    return new ItemSelectHomeMemberBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_home_member is invalid. Received: " + tag);
            case 644:
                if ("layout/item_select_icon_0".equals(tag)) {
                    return new ItemSelectIconBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_icon is invalid. Received: " + tag);
            case 645:
                if ("layout/item_select_k_value_0".equals(tag)) {
                    return new ItemSelectKValueBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_k_value is invalid. Received: " + tag);
            case 646:
                if ("layout/item_select_list_0".equals(tag)) {
                    return new ItemSelectListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_list is invalid. Received: " + tag);
            case 647:
                if ("layout/item_select_list_white_0".equals(tag)) {
                    return new ItemSelectListWhiteBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_list_white is invalid. Received: " + tag);
            case 648:
                if ("layout/item_select_list_with_icon_0".equals(tag)) {
                    return new ItemSelectListWithIconBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_list_with_icon is invalid. Received: " + tag);
            case 649:
                if ("layout/item_select_loop_value_0".equals(tag)) {
                    return new ItemSelectLoopValueBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_loop_value is invalid. Received: " + tag);
            case 650:
                if ("layout/item_select_lux_ct_0".equals(tag)) {
                    return new ItemSelectLuxCtBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_lux_ct is invalid. Received: " + tag);
            default:
                return null;
        }
    }

    private final ViewDataBinding internalGetViewDataBinding13(DataBindingComponent component, View view, int internalId, Object tag) {
        switch (internalId) {
            case 651:
                if ("layout/item_select_mode_cover_0".equals(tag)) {
                    return new ItemSelectModeCoverBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_mode_cover is invalid. Received: " + tag);
            case 652:
                if ("layout/item_select_music_player_volume_0".equals(tag)) {
                    return new ItemSelectMusicPlayerVolumeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_music_player_volume is invalid. Received: " + tag);
            case 653:
                if ("layout/item_select_on_off_time_diy_0".equals(tag)) {
                    return new ItemSelectOnOffTimeDiyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_on_off_time_diy is invalid. Received: " + tag);
            case 654:
                if ("layout/item_select_on_off_time_normal_0".equals(tag)) {
                    return new ItemSelectOnOffTimeNormalBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_on_off_time_normal is invalid. Received: " + tag);
            case 655:
                if ("layout/item_select_place_0".equals(tag)) {
                    return new ItemSelectPlaceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_place is invalid. Received: " + tag);
            case 656:
                if ("layout/item_select_product_0".equals(tag)) {
                    return new ItemSelectProductBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_product is invalid. Received: " + tag);
            case 657:
                if ("layout/item_select_scene_0".equals(tag)) {
                    return new ItemSelectSceneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_scene is invalid. Received: " + tag);
            case LAYOUT_ITEMSELECTSUB /* 658 */:
                if ("layout/item_select_sub_0".equals(tag)) {
                    return new ItemSelectSubBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_sub is invalid. Received: " + tag);
            case LAYOUT_ITEMSELECTSUBTEXTVER /* 659 */:
                if ("layout/item_select_subtext_ver_0".equals(tag)) {
                    return new ItemSelectSubtextVerBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_subtext_ver is invalid. Received: " + tag);
            case LAYOUT_ITEMSELECTSUBTEXTVERONOFFDIY /* 660 */:
                if ("layout/item_select_subtext_ver_onoff_diy_0".equals(tag)) {
                    return new ItemSelectSubtextVerOnoffDiyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_subtext_ver_onoff_diy is invalid. Received: " + tag);
            case LAYOUT_ITEMSELECTSUBTEXTVERONOFFDIYALL /* 661 */:
                if ("layout/item_select_subtext_ver_onoff_diy_all_0".equals(tag)) {
                    return new ItemSelectSubtextVerOnoffDiyAllBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_subtext_ver_onoff_diy_all is invalid. Received: " + tag);
            case LAYOUT_ITEMSELECTSUBTEXTVERONOFFDIYDALI /* 662 */:
                if ("layout/item_select_subtext_ver_onoff_diy_dali_0".equals(tag)) {
                    return new ItemSelectSubtextVerOnoffDiyDaliBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_subtext_ver_onoff_diy_dali is invalid. Received: " + tag);
            case LAYOUT_ITEMSELECTWITHICON /* 663 */:
                if ("layout/item_select_with_icon_0".equals(tag)) {
                    return new ItemSelectWithIconBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_with_icon is invalid. Received: " + tag);
            case LAYOUT_ITEMSELECTWITHPLACECG485 /* 664 */:
                if ("layout/item_select_with_place_cg485_0".equals(tag)) {
                    return new ItemSelectWithPlaceCg485BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_with_place_cg485 is invalid. Received: " + tag);
            case 665:
                if ("layout/item_select_with_sub_0".equals(tag)) {
                    return new ItemSelectWithSubBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_select_with_sub is invalid. Received: " + tag);
            case LAYOUT_ITEMSENSORSUBDEVICE /* 666 */:
                if ("layout/item_sensor_sub_device_0".equals(tag)) {
                    return new ItemSensorSubDeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_sensor_sub_device is invalid. Received: " + tag);
            case LAYOUT_ITEMSETTING /* 667 */:
                if ("layout/item_setting_0".equals(tag)) {
                    return new ItemSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_setting is invalid. Received: " + tag);
            case 668:
                if ("layout/item_share_data_0".equals(tag)) {
                    return new ItemShareDataBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_share_data is invalid. Received: " + tag);
            case 669:
                if ("layout/item_simple_select_0".equals(tag)) {
                    return new ItemSimpleSelectBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_simple_select is invalid. Received: " + tag);
            case 670:
                if ("layout/item_smart_panel_0".equals(tag)) {
                    return new ItemSmartPanelBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_smart_panel is invalid. Received: " + tag);
            case 671:
                if ("layout/item_smart_panel_key_0".equals(tag)) {
                    return new ItemSmartPanelKeyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_smart_panel_key is invalid. Received: " + tag);
            case 672:
                if ("layout/item_smart_panel_key_eb6_0".equals(tag)) {
                    return new ItemSmartPanelKeyEb6BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_smart_panel_key_eb6 is invalid. Received: " + tag);
            case 673:
                if ("layout/item_smart_panel_key_set_0".equals(tag)) {
                    return new ItemSmartPanelKeySetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_smart_panel_key_set is invalid. Received: " + tag);
            case 674:
                if ("layout/item_smart_speaker_0".equals(tag)) {
                    return new ItemSmartSpeakerBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_smart_speaker is invalid. Received: " + tag);
            case 675:
                if ("layout/item_song_info_0".equals(tag)) {
                    return new ItemSongInfoBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_song_info is invalid. Received: " + tag);
            case 676:
                if ("layout/item_song_playlist_0".equals(tag)) {
                    return new ItemSongPlaylistBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_song_playlist is invalid. Received: " + tag);
            case 677:
                if ("layout/item_song_playlist_foot_0".equals(tag)) {
                    return new ItemSongPlaylistFootBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_song_playlist_foot is invalid. Received: " + tag);
            case 678:
                if ("layout/item_sort_0".equals(tag)) {
                    return new ItemSortBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_sort is invalid. Received: " + tag);
            case 679:
                if ("layout/item_sp_btn_key_0".equals(tag)) {
                    return new ItemSpBtnKeyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_sp_btn_key is invalid. Received: " + tag);
            case 680:
                if ("layout/item_spi_edit_play_list_0".equals(tag)) {
                    return new ItemSpiEditPlayListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_spi_edit_play_list is invalid. Received: " + tag);
            case 681:
                if ("layout/item_spi_mode_0".equals(tag)) {
                    return new ItemSpiModeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_spi_mode is invalid. Received: " + tag);
            case 682:
                if ("layout/item_spi_play_list_0".equals(tag)) {
                    return new ItemSpiPlayListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_spi_play_list is invalid. Received: " + tag);
            case 683:
                if ("layout/item_sq_panel_action_0".equals(tag)) {
                    return new ItemSqPanelActionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_sq_panel_action is invalid. Received: " + tag);
            case 684:
                if ("layout/item_super_panel_fun_0".equals(tag)) {
                    return new ItemSuperPanelFunBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_super_panel_fun is invalid. Received: " + tag);
            case 685:
                if ("layout/item_super_panel_key_set_0".equals(tag)) {
                    return new ItemSuperPanelKeySetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_super_panel_key_set is invalid. Received: " + tag);
            case 686:
                if ("layout/item_super_panel_switch_0".equals(tag)) {
                    return new ItemSuperPanelSwitchBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_super_panel_switch is invalid. Received: " + tag);
            case 687:
                if ("layout/item_switch_0".equals(tag)) {
                    return new ItemSwitchBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_switch is invalid. Received: " + tag);
            case 688:
                if ("layout/item_switch_button_0".equals(tag)) {
                    return new ItemSwitchButtonBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_switch_button is invalid. Received: " + tag);
            case 689:
                if ("layout/item_tail_0".equals(tag)) {
                    return new ItemTailBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_tail is invalid. Received: " + tag);
            case 690:
                if ("layout/item_temp_0".equals(tag)) {
                    return new ItemTempBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_temp is invalid. Received: " + tag);
            case 691:
                if ("layout/item_text_0".equals(tag)) {
                    return new ItemTextBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_text is invalid. Received: " + tag);
            case 692:
                if ("layout/item_text_36_0".equals(tag)) {
                    return new ItemText36BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_text_36 is invalid. Received: " + tag);
            case 693:
                if ("layout/item_text_add_0".equals(tag)) {
                    return new ItemTextAddBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_text_add is invalid. Received: " + tag);
            case 694:
                if ("layout/item_text_head_0".equals(tag)) {
                    return new ItemTextHeadBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_text_head is invalid. Received: " + tag);
            case 695:
                if ("layout/item_text_middle_0".equals(tag)) {
                    return new ItemTextMiddleBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_text_middle is invalid. Received: " + tag);
            case 696:
                if ("layout/item_text_wrap_0".equals(tag)) {
                    return new ItemTextWrapBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_text_wrap is invalid. Received: " + tag);
            case 697:
                if ("layout/item_title_0".equals(tag)) {
                    return new ItemTitleBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_title is invalid. Received: " + tag);
            case 698:
                if ("layout/item_trig_key_0".equals(tag)) {
                    return new ItemTrigKeyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_trig_key is invalid. Received: " + tag);
            case 699:
                if ("layout/item_trig_scene_0".equals(tag)) {
                    return new ItemTrigSceneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_trig_scene is invalid. Received: " + tag);
            case 700:
                if ("layout/layout_empty_0".equals(tag)) {
                    return new LayoutEmptyBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for layout_empty is invalid. Received: " + tag);
            default:
                return null;
        }
    }

    private final ViewDataBinding internalGetViewDataBinding14(DataBindingComponent component, View view, int internalId, Object tag) {
        switch (internalId) {
            case 701:
                if ("layout/layout_error_0".equals(tag)) {
                    return new LayoutErrorBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for layout_error is invalid. Received: " + tag);
            case 702:
                if ("layout/layout_error_2_0".equals(tag)) {
                    return new LayoutError2BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for layout_error_2 is invalid. Received: " + tag);
            case 703:
                if ("layout/layout_ext_0".equals(tag)) {
                    return new LayoutExtBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for layout_ext is invalid. Received: " + tag);
            case 704:
                if ("layout/layout_loading_0".equals(tag)) {
                    return new LayoutLoadingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for layout_loading is invalid. Received: " + tag);
            case 705:
                if ("layout/layout_protocol_default_0".equals(tag)) {
                    return new LayoutProtocolDefaultBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for layout_protocol_default is invalid. Received: " + tag);
            case 706:
                if ("layout/layout_radio_image_text_view_0".equals(tag)) {
                    return new LayoutRadioImageTextViewBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for layout_radio_image_text_view is invalid. Received: " + tag);
            case 707:
                if ("layout/layout_tab_view_0".equals(tag)) {
                    return new LayoutTabViewBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for layout_tab_view is invalid. Received: " + tag);
            case 708:
                if ("layout/layout_title_default_0".equals(tag)) {
                    return new LayoutTitleDefaultBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for layout_title_default is invalid. Received: " + tag);
            case 709:
                if ("layout/layout_title_ir_0".equals(tag)) {
                    return new LayoutTitleIrBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for layout_title_ir is invalid. Received: " + tag);
            case 710:
                if ("layout/layout_title_tran_0".equals(tag)) {
                    return new LayoutTitleTranBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for layout_title_tran is invalid. Received: " + tag);
            case 711:
                if ("layout/layout_title_transparent_0".equals(tag)) {
                    return new LayoutTitleTransparentBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for layout_title_transparent is invalid. Received: " + tag);
            case 712:
                if ("layout/tab_text_0".equals(tag)) {
                    return new TabTextBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for tab_text is invalid. Received: " + tag);
            case 713:
                if ("layout/tab_text2_0".equals(tag)) {
                    return new TabText2BindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for tab_text2 is invalid. Received: " + tag);
            case 714:
                if ("layout/view_brt_setting_0".equals(tag)) {
                    return new ViewBrtSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_brt_setting is invalid. Received: " + tag);
            case 715:
                if ("layout/view_cgd_action_0".equals(tag)) {
                    return new ViewCgdActionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_cgd_action is invalid. Received: " + tag);
            case 716:
                if ("layout/view_cgd_light_title_dali_0".equals(tag)) {
                    return new ViewCgdLightTitleDaliBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_cgd_light_title_dali is invalid. Received: " + tag);
            case 717:
                if ("layout/view_cgd_light_title_manage_0".equals(tag)) {
                    return new ViewCgdLightTitleManageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_cgd_light_title_manage is invalid. Received: " + tag);
            case 718:
                if ("layout/view_cgd_light_title_select_0".equals(tag)) {
                    return new ViewCgdLightTitleSelectBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_cgd_light_title_select is invalid. Received: " + tag);
            case 719:
                if ("layout/view_cgd_light_title_select_group_0".equals(tag)) {
                    return new ViewCgdLightTitleSelectGroupBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_cgd_light_title_select_group is invalid. Received: " + tag);
            case 720:
                if ("layout/view_dali_manage_bottom_0".equals(tag)) {
                    return new ViewDaliManageBottomBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_dali_manage_bottom is invalid. Received: " + tag);
            case 721:
                if ("layout/view_dali_text_seekbar_0".equals(tag)) {
                    return new ViewDaliTextSeekbarBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_dali_text_seekbar is invalid. Received: " + tag);
            case 722:
                if ("layout/view_dali_text_seekbar_new_0".equals(tag)) {
                    return new ViewDaliTextSeekbarNewBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_dali_text_seekbar_new is invalid. Received: " + tag);
            case 723:
                if ("layout/view_device_manage_bottom_0".equals(tag)) {
                    return new ViewDeviceManageBottomBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_device_manage_bottom is invalid. Received: " + tag);
            case 724:
                if ("layout/view_edit_text_seekbar_0".equals(tag)) {
                    return new ViewEditTextSeekbarBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_edit_text_seekbar is invalid. Received: " + tag);
            case 725:
                if ("layout/view_eur_tip_function_0".equals(tag)) {
                    return new ViewEurTipFunctionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_eur_tip_function is invalid. Received: " + tag);
            case 726:
                if ("layout/view_eur_tip_scene_0".equals(tag)) {
                    return new ViewEurTipSceneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_eur_tip_scene is invalid. Received: " + tag);
            case 727:
                if ("layout/view_eur_tip_zone_0".equals(tag)) {
                    return new ViewEurTipZoneBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_eur_tip_zone is invalid. Received: " + tag);
            case 728:
                if ("layout/view_five_current_item_0".equals(tag)) {
                    return new ViewFiveCurrentItemBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_five_current_item is invalid. Received: " + tag);
            case 729:
                if ("layout/view_image_text_0".equals(tag)) {
                    return new ViewImageTextBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_image_text is invalid. Received: " + tag);
            case 730:
                if ("layout/view_location_tip_0".equals(tag)) {
                    return new ViewLocationTipBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_location_tip is invalid. Received: " + tag);
            case 731:
                if ("layout/view_number_setting_0".equals(tag)) {
                    return new ViewNumberSettingBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_number_setting is invalid. Received: " + tag);
            case 732:
                if ("layout/view_power_state_ble_all_0".equals(tag)) {
                    return new ViewPowerStateBleAllBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_power_state_ble_all is invalid. Received: " + tag);
            case 733:
                if ("layout/view_remote_tip_0".equals(tag)) {
                    return new ViewRemoteTipBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_remote_tip is invalid. Received: " + tag);
            case 734:
                if ("layout/view_s1_pro_guide_0".equals(tag)) {
                    return new ViewS1ProGuideBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_s1_pro_guide is invalid. Received: " + tag);
            case 735:
                if ("layout/view_s1_pro_guide_en_0".equals(tag)) {
                    return new ViewS1ProGuideEnBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_s1_pro_guide_en is invalid. Received: " + tag);
            case 736:
                if ("layout/view_s2_pro_guide_0".equals(tag)) {
                    return new ViewS2ProGuideBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_s2_pro_guide is invalid. Received: " + tag);
            case 737:
                if ("layout/view_s2_pro_guide_en_0".equals(tag)) {
                    return new ViewS2ProGuideEnBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_s2_pro_guide_en is invalid. Received: " + tag);
            case 738:
                if ("layout/view_s3_pro_guide_0".equals(tag)) {
                    return new ViewS3ProGuideBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_s3_pro_guide is invalid. Received: " + tag);
            case 739:
                if ("layout/view_s3_pro_guide_en_0".equals(tag)) {
                    return new ViewS3ProGuideEnBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_s3_pro_guide_en is invalid. Received: " + tag);
            case 740:
                if ("layout/view_sq_pro_guide_0".equals(tag)) {
                    return new ViewSqProGuideBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_sq_pro_guide is invalid. Received: " + tag);
            case 741:
                if ("layout/view_sq_pro_guide_en_0".equals(tag)) {
                    return new ViewSqProGuideEnBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_sq_pro_guide_en is invalid. Received: " + tag);
            case 742:
                if ("layout/view_switch_seekbar_0".equals(tag)) {
                    return new ViewSwitchSeekbarBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_switch_seekbar is invalid. Received: " + tag);
            case 743:
                if ("layout/view_switch_two_seekbar_0".equals(tag)) {
                    return new ViewSwitchTwoSeekbarBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_switch_two_seekbar is invalid. Received: " + tag);
            case 744:
                if ("layout/view_text_seekbar_0".equals(tag)) {
                    return new ViewTextSeekbarBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_text_seekbar is invalid. Received: " + tag);
            case 745:
                if ("layout/view_text_with_button_0".equals(tag)) {
                    return new ViewTextWithButtonBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_text_with_button is invalid. Received: " + tag);
            default:
                return null;
        }
    }

    @Override // androidx.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
        int i = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
        if (i <= 0) {
            return null;
        }
        Object tag = view.getTag();
        if (tag == null) {
            throw new RuntimeException("view must have a tag");
        }
        switch ((i - 1) / 50) {
            case 0:
                return internalGetViewDataBinding0(component, view, i, tag);
            case 1:
                return internalGetViewDataBinding1(component, view, i, tag);
            case 2:
                return internalGetViewDataBinding2(component, view, i, tag);
            case 3:
                return internalGetViewDataBinding3(component, view, i, tag);
            case 4:
                return internalGetViewDataBinding4(component, view, i, tag);
            case 5:
                return internalGetViewDataBinding5(component, view, i, tag);
            case 6:
                return internalGetViewDataBinding6(component, view, i, tag);
            case 7:
                return internalGetViewDataBinding7(component, view, i, tag);
            case 8:
                return internalGetViewDataBinding8(component, view, i, tag);
            case 9:
                return internalGetViewDataBinding9(component, view, i, tag);
            case 10:
                return internalGetViewDataBinding10(component, view, i, tag);
            case 11:
                return internalGetViewDataBinding11(component, view, i, tag);
            case 12:
                return internalGetViewDataBinding12(component, view, i, tag);
            case 13:
                return internalGetViewDataBinding13(component, view, i, tag);
            case 14:
                return internalGetViewDataBinding14(component, view, i, tag);
            default:
                return null;
        }
    }

    @Override // androidx.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
        if (views == null || views.length == 0 || INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId) <= 0 || views[0].getTag() != null) {
            return null;
        }
        throw new RuntimeException("view must have a tag");
    }

    @Override // androidx.databinding.DataBinderMapper
    public int getLayoutId(String tag) {
        Integer num;
        if (tag == null || (num = InnerLayoutIdLookup.sKeys.get(tag)) == null) {
            return 0;
        }
        return num.intValue();
    }

    @Override // androidx.databinding.DataBinderMapper
    public String convertBrIdToString(int localId) {
        return InnerBrLookup.sKeys.get(localId);
    }

    @Override // androidx.databinding.DataBinderMapper
    public List<DataBinderMapper> collectDependencies() {
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
        arrayList.add(new me.tatarka.bindingcollectionadapter2.DataBinderMapperImpl());
        arrayList.add(new me.tatarka.bindingcollectionadapter2.recyclerview.DataBinderMapperImpl());
        return arrayList;
    }

    private static void internalPopulateLayoutIdLookup0() {
        SparseIntArray sparseIntArray = INTERNAL_LAYOUT_ID_LOOKUP;
        sparseIntArray.put(R.layout.act_about, 1);
        sparseIntArray.put(R.layout.act_ac, 2);
        sparseIntArray.put(R.layout.act_ac_bg, 3);
        sparseIntArray.put(R.layout.act_ac_central, 4);
        sparseIntArray.put(R.layout.act_account_and_security, 5);
        sparseIntArray.put(R.layout.act_add_automation, 6);
        sparseIntArray.put(R.layout.act_add_cloud_scene, 7);
        sparseIntArray.put(R.layout.act_add_device, 8);
        sparseIntArray.put(R.layout.act_add_duv, 9);
        sparseIntArray.put(R.layout.act_add_floor, 10);
        sparseIntArray.put(R.layout.act_add_instruct, 11);
        sparseIntArray.put(R.layout.act_add_ir_device, 12);
        sparseIntArray.put(R.layout.act_add_ir_key, 13);
        sparseIntArray.put(R.layout.act_add_local_scene, 14);
        sparseIntArray.put(R.layout.act_add_mode_color, 15);
        sparseIntArray.put(R.layout.act_add_mode_ct_dim, 16);
        sparseIntArray.put(R.layout.act_add_music, 17);
        sparseIntArray.put(R.layout.act_add_place_user, 18);
        sparseIntArray.put(R.layout.act_add_virtual_device, 19);
        sparseIntArray.put(R.layout.act_as_panel, 20);
        sparseIntArray.put(R.layout.act_as_panel_setting, 21);
        sparseIntArray.put(R.layout.act_auto_net_time_setting, 22);
        sparseIntArray.put(R.layout.act_battery_guide, 23);
        sparseIntArray.put(R.layout.act_ble_curtain_motor, 24);
        sparseIntArray.put(R.layout.act_ble_curtain_motor_more_setting, 25);
        sparseIntArray.put(R.layout.act_ble_curtain_motor_type_setting, 26);
        sparseIntArray.put(R.layout.act_ble_ham_setting_default, 27);
        sparseIntArray.put(R.layout.act_ble_motor_mode_set, 28);
        sparseIntArray.put(R.layout.act_ble_motor_setting, 29);
        sparseIntArray.put(R.layout.act_ble_music_player, 30);
        sparseIntArray.put(R.layout.act_ble_music_player_setting, 31);
        sparseIntArray.put(R.layout.act_ble_trig_curtain_setting, 32);
        sparseIntArray.put(R.layout.act_ble_trig_scene_setting, 33);
        sparseIntArray.put(R.layout.act_brt_button_setting, 34);
        sparseIntArray.put(R.layout.act_bt_ota, 35);
        sparseIntArray.put(R.layout.act_bt_ota_low_power, 36);
        sparseIntArray.put(R.layout.act_bt_ota_single, 37);
        sparseIntArray.put(R.layout.act_camera_info, 38);
        sparseIntArray.put(R.layout.act_camera_play, 39);
        sparseIntArray.put(R.layout.act_camera_play_back, 40);
        sparseIntArray.put(R.layout.act_camera_setting, 41);
        sparseIntArray.put(R.layout.act_central_air_gateway, 42);
        sparseIntArray.put(R.layout.act_central_air_mesh_gateway_setting, 43);
        sparseIntArray.put(R.layout.act_central_air_pro_gateway, 44);
        sparseIntArray.put(R.layout.act_cg_485, 45);
        sparseIntArray.put(R.layout.act_cg_485_device, 46);
        sparseIntArray.put(R.layout.act_cg_485_old, 47);
        sparseIntArray.put(R.layout.act_cg_485_setting, 48);
        sparseIntArray.put(R.layout.act_cgd_pro_light, 49);
        sparseIntArray.put(R.layout.act_cgd_pro_light_setting, 50);
        sparseIntArray.put(R.layout.act_change_email, 51);
        sparseIntArray.put(R.layout.act_change_phone, 52);
        sparseIntArray.put(R.layout.act_change_pwd, 53);
        sparseIntArray.put(R.layout.act_channel_512, 54);
        sparseIntArray.put(R.layout.act_child_mcu_upgrade, 55);
        sparseIntArray.put(R.layout.act_choice_light_type, 56);
        sparseIntArray.put(R.layout.act_color_light, 57);
        sparseIntArray.put(R.layout.act_color_light_cc, 58);
        sparseIntArray.put(R.layout.act_command_category, 59);
        sparseIntArray.put(R.layout.act_config_success, 60);
        sparseIntArray.put(R.layout.act_create_home, 61);
        sparseIntArray.put(R.layout.act_ct_edit, 62);
        sparseIntArray.put(R.layout.act_ct_light, 63);
        sparseIntArray.put(R.layout.act_ct_select_color, 64);
        sparseIntArray.put(R.layout.act_current_set, 65);
        sparseIntArray.put(R.layout.act_current_set_five, 66);
        sparseIntArray.put(R.layout.act_dali_batch_modify_param, 67);
        sparseIntArray.put(R.layout.act_dali_light, 68);
        sparseIntArray.put(R.layout.act_dali_light_group_setting, 69);
        sparseIntArray.put(R.layout.act_dali_light_setting, 70);
        sparseIntArray.put(R.layout.act_dali_scene_setting, 71);
        sparseIntArray.put(R.layout.act_dali_select, 72);
        sparseIntArray.put(R.layout.act_dca_music_detail, 73);
        sparseIntArray.put(R.layout.act_dca_music_home, 74);
        sparseIntArray.put(R.layout.act_dca_music_list, 75);
        sparseIntArray.put(R.layout.act_dca_web_view, 76);
        sparseIntArray.put(R.layout.act_device_config_fail, 77);
        sparseIntArray.put(R.layout.act_device_connect, 78);
        sparseIntArray.put(R.layout.act_device_connect_android10, 79);
        sparseIntArray.put(R.layout.act_device_group_manage, 80);
        sparseIntArray.put(R.layout.act_device_log, 81);
        sparseIntArray.put(R.layout.act_device_manage, 82);
        sparseIntArray.put(R.layout.act_device_replace, 83);
        sparseIntArray.put(R.layout.act_device_setting_battery, 84);
        sparseIntArray.put(R.layout.act_device_setting_default, 85);
        sparseIntArray.put(R.layout.act_dim_light, 86);
        sparseIntArray.put(R.layout.act_dim_select_color, 87);
        sparseIntArray.put(R.layout.act_diy_ir_setting, 88);
        sparseIntArray.put(R.layout.act_diy_light_name, 89);
        sparseIntArray.put(R.layout.act_diy_room, 90);
        sparseIntArray.put(R.layout.act_dmx_512_setting, 91);
        sparseIntArray.put(R.layout.act_dmx_channel_select, 92);
        sparseIntArray.put(R.layout.act_door_sensor, 93);
        sparseIntArray.put(R.layout.act_duv_list, 94);
        sparseIntArray.put(R.layout.act_e6_panel, 95);
        sparseIntArray.put(R.layout.act_e6_panel_setting, 96);
        sparseIntArray.put(R.layout.act_edit_advanced_mode, 97);
        sparseIntArray.put(R.layout.act_edit_color_diy_mode, 98);
        sparseIntArray.put(R.layout.act_edit_general_mode, 99);
        sparseIntArray.put(R.layout.act_edit_instruct_cmd, 100);
        sparseIntArray.put(R.layout.act_edit_key_name, 101);
        sparseIntArray.put(R.layout.act_edit_name, 102);
        sparseIntArray.put(R.layout.act_edit_number, 103);
        sparseIntArray.put(R.layout.act_engineering_mode, 104);
        sparseIntArray.put(R.layout.act_engineering_mode_on_off, 105);
        sparseIntArray.put(R.layout.act_environment_log, 106);
        sparseIntArray.put(R.layout.act_eur_panel, 107);
        sparseIntArray.put(R.layout.act_eur_panel_eb6, 108);
        sparseIntArray.put(R.layout.act_eur_panel_group_setting, 109);
        sparseIntArray.put(R.layout.act_eur_panel_setting, 110);
        sparseIntArray.put(R.layout.act_fan, 111);
        sparseIntArray.put(R.layout.act_feed_back, 112);
        sparseIntArray.put(R.layout.act_floor_heat, 113);
        sparseIntArray.put(R.layout.act_floor_manage, 114);
        sparseIntArray.put(R.layout.act_fresh_air, 115);
        sparseIntArray.put(R.layout.act_g4_max_key_set, 116);
        sparseIntArray.put(R.layout.act_get_permission, 117);
        sparseIntArray.put(R.layout.act_gq_pro, 118);
        sparseIntArray.put(R.layout.act_gq_pro_theme, 119);
        sparseIntArray.put(R.layout.act_gqx, 120);
        sparseIntArray.put(R.layout.act_gqx_setting, 121);
        sparseIntArray.put(R.layout.act_group_curtain_setting, 122);
        sparseIntArray.put(R.layout.act_group_manage, 123);
        sparseIntArray.put(R.layout.act_group_setting, 124);
        sparseIntArray.put(R.layout.act_home, 125);
        sparseIntArray.put(R.layout.act_home_kit, 126);
        sparseIntArray.put(R.layout.act_home_kit_old, 127);
        sparseIntArray.put(R.layout.act_home_kit_setting, 128);
        sparseIntArray.put(R.layout.act_home_manage, 129);
        sparseIntArray.put(R.layout.act_home_position, 130);
        sparseIntArray.put(R.layout.act_home_qr_code, 131);
        sparseIntArray.put(R.layout.act_home_setting, 132);
        sparseIntArray.put(R.layout.act_home_transfer_setting, 133);
        sparseIntArray.put(R.layout.act_hsd_sensor, 134);
        sparseIntArray.put(R.layout.act_hsd_sensor_setting, 135);
        sparseIntArray.put(R.layout.act_import_scene_all, 136);
        sparseIntArray.put(R.layout.act_instruct_setting, 137);
        sparseIntArray.put(R.layout.act_intelligence, 138);
        sparseIntArray.put(R.layout.act_intercom, 139);
        sparseIntArray.put(R.layout.act_intercom_login, 140);
        sparseIntArray.put(R.layout.act_intercom_record, 141);
        sparseIntArray.put(R.layout.act_intercom_select_community, 142);
        sparseIntArray.put(R.layout.act_intercom_set_face, 143);
        sparseIntArray.put(R.layout.act_intercom_set_open_key, 144);
        sparseIntArray.put(R.layout.act_intercom_setting, 145);
        sparseIntArray.put(R.layout.act_intercom_temp_key, 146);
        sparseIntArray.put(R.layout.act_intercom_tips, 147);
        sparseIntArray.put(R.layout.act_ir_diy, 148);
        sparseIntArray.put(R.layout.act_ir_setting, 149);
        sparseIntArray.put(R.layout.act_kbs, 150);
        sparseIntArray.put(R.layout.act_kbs_group_setting, 151);
        sparseIntArray.put(R.layout.act_kbs_setting, 152);
        sparseIntArray.put(R.layout.act_key_switch, 153);
        sparseIntArray.put(R.layout.act_knob_panel, 154);
        sparseIntArray.put(R.layout.act_knob_panel_setting, 155);
        sparseIntArray.put(R.layout.act_knob_screen_panel, 156);
        sparseIntArray.put(R.layout.act_light_group_sub_item_control, 157);
        sparseIntArray.put(R.layout.act_light_on_off_time, 158);
        sparseIntArray.put(R.layout.act_light_plan_batch_set, 159);
        sparseIntArray.put(R.layout.act_light_plan_detail, 160);
        sparseIntArray.put(R.layout.act_light_plan_list, 161);
        sparseIntArray.put(R.layout.act_light_setting, 162);
        sparseIntArray.put(R.layout.act_light_setting_new, 163);
        sparseIntArray.put(R.layout.act_local_device_log, 164);
        sparseIntArray.put(R.layout.act_location_permission_description, 165);
        sparseIntArray.put(R.layout.act_login, 166);
        sparseIntArray.put(R.layout.act_map, 167);
        sparseIntArray.put(R.layout.act_matter_list, 168);
        sparseIntArray.put(R.layout.act_matter_platform_list, 169);
        sparseIntArray.put(R.layout.act_matter_sub_list, 170);
        sparseIntArray.put(R.layout.act_mesh_gateway, 171);
        sparseIntArray.put(R.layout.act_mesh_gateway_light_setting, 172);
        sparseIntArray.put(R.layout.act_mesh_gateway_setting, 173);
        sparseIntArray.put(R.layout.act_mesh_near_device, 174);
        sparseIntArray.put(R.layout.act_mesh_scan, 175);
        sparseIntArray.put(R.layout.act_mesh_scan2, 176);
        sparseIntArray.put(R.layout.act_mesh_scan_1, 177);
        sparseIntArray.put(R.layout.act_mesh_scan_proxy, 178);
        sparseIntArray.put(R.layout.act_message_center, 179);
        sparseIntArray.put(R.layout.act_mode, 180);
        sparseIntArray.put(R.layout.act_module_switch, 181);
        sparseIntArray.put(R.layout.act_monitor, 182);
        sparseIntArray.put(R.layout.act_motor, 183);
        sparseIntArray.put(R.layout.act_motor_pair, 184);
        sparseIntArray.put(R.layout.act_music, 185);
        sparseIntArray.put(R.layout.act_music_list, 186);
        sparseIntArray.put(R.layout.act_net_config, 187);
        sparseIntArray.put(R.layout.act_net_connect, 188);
        sparseIntArray.put(R.layout.act_new_as_panel, 189);
        sparseIntArray.put(R.layout.act_new_screen_panel, 190);
        sparseIntArray.put(R.layout.act_new_smart_panel, 191);
        sparseIntArray.put(R.layout.act_nfc_restore, 192);
        sparseIntArray.put(R.layout.act_open_door_log_detail, 193);
        sparseIntArray.put(R.layout.act_page_screen_panel, 194);
        sparseIntArray.put(R.layout.act_panel_color_set, 195);
        sparseIntArray.put(R.layout.act_panel_group_setting, 196);
        sparseIntArray.put(R.layout.act_panel_night_getup, 197);
        sparseIntArray.put(R.layout.act_panel_switch_position_set, 198);
        sparseIntArray.put(R.layout.act_place_user_setting, 199);
        sparseIntArray.put(R.layout.act_playlist_manage, 200);
        sparseIntArray.put(R.layout.act_product_introduction, 201);
        sparseIntArray.put(R.layout.act_product_introduction_1, 202);
        sparseIntArray.put(R.layout.act_pub_list, 203);
        sparseIntArray.put(R.layout.act_qr_code_scan, 204);
        sparseIntArray.put(R.layout.act_qr_code_scan_result, 205);
        sparseIntArray.put(R.layout.act_r8_setting, 206);
        sparseIntArray.put(R.layout.act_rc4s, 207);
        sparseIntArray.put(R.layout.act_record_list, 208);
        sparseIntArray.put(R.layout.act_register, 209);
        sparseIntArray.put(R.layout.act_remote_battery, 210);
        sparseIntArray.put(R.layout.act_replace, 211);
        sparseIntArray.put(R.layout.act_rgbwaf_select, 212);
        sparseIntArray.put(R.layout.act_room_manage, 213);
        sparseIntArray.put(R.layout.act_rs8, 214);
        sparseIntArray.put(R.layout.act_rs8_add_sub_device, 215);
        sparseIntArray.put(R.layout.act_rs8_address_write, 216);
        sparseIntArray.put(R.layout.act_rs8_curtain, 217);
        sparseIntArray.put(R.layout.act_rs8_sub_device_setting, 218);
        sparseIntArray.put(R.layout.act_save_qr_code, 219);
        sparseIntArray.put(R.layout.act_scene_panel, 220);
        sparseIntArray.put(R.layout.act_screen_panel, 221);
        sparseIntArray.put(R.layout.act_screen_panel_elderly_mode, 222);
        sparseIntArray.put(R.layout.act_search_automation, 223);
        sparseIntArray.put(R.layout.act_search_device, 224);
        sparseIntArray.put(R.layout.act_search_google_position, 225);
        sparseIntArray.put(R.layout.act_search_music, 226);
        sparseIntArray.put(R.layout.act_search_position, 227);
        sparseIntArray.put(R.layout.act_search_scene, LAYOUT_ACTSEARCHSCENE);
        sparseIntArray.put(R.layout.act_select, 229);
        sparseIntArray.put(R.layout.act_select2, 230);
        sparseIntArray.put(R.layout.act_select3, 231);
        sparseIntArray.put(R.layout.act_select4, 232);
        sparseIntArray.put(R.layout.act_select_ble_curtain_action, 233);
        sparseIntArray.put(R.layout.act_select_brand, 234);
        sparseIntArray.put(R.layout.act_select_color, LAYOUT_ACTSELECTCOLOR);
        sparseIntArray.put(R.layout.act_select_color_cc, LAYOUT_ACTSELECTCOLORCC);
        sparseIntArray.put(R.layout.act_select_condition_device, LAYOUT_ACTSELECTCONDITIONDEVICE);
        sparseIntArray.put(R.layout.act_select_country, LAYOUT_ACTSELECTCOUNTRY);
        sparseIntArray.put(R.layout.act_select_dali_add, LAYOUT_ACTSELECTDALIADD);
        sparseIntArray.put(R.layout.act_select_dali_color, 240);
        sparseIntArray.put(R.layout.act_select_divide, LAYOUT_ACTSELECTDIVIDE);
        sparseIntArray.put(R.layout.act_select_effect_period, LAYOUT_ACTSELECTEFFECTPERIOD);
        sparseIntArray.put(R.layout.act_select_home_member, LAYOUT_ACTSELECTHOMEMEMBER);
        sparseIntArray.put(R.layout.act_select_light, LAYOUT_ACTSELECTLIGHT);
        sparseIntArray.put(R.layout.act_select_list, LAYOUT_ACTSELECTLIST);
        sparseIntArray.put(R.layout.act_select_multi_type, LAYOUT_ACTSELECTMULTITYPE);
        sparseIntArray.put(R.layout.act_select_operators, LAYOUT_ACTSELECTOPERATORS);
        sparseIntArray.put(R.layout.act_select_scene, 248);
        sparseIntArray.put(R.layout.act_select_scene_all, LAYOUT_ACTSELECTSCENEALL);
        sparseIntArray.put(R.layout.act_select_sonos_action, 250);
        sparseIntArray.put(R.layout.act_select_spi_for_action, 251);
        sparseIntArray.put(R.layout.act_select_super_panel_music, 252);
        sparseIntArray.put(R.layout.act_select_temperature_weather, 253);
        sparseIntArray.put(R.layout.act_select_theme_mode, 254);
        sparseIntArray.put(R.layout.act_select_time, 255);
        sparseIntArray.put(R.layout.act_select_voice_speak, 256);
        sparseIntArray.put(R.layout.act_select_weather, 257);
        sparseIntArray.put(R.layout.act_sense_setting, 258);
        sparseIntArray.put(R.layout.act_sensor_noboby_test, 259);
        sparseIntArray.put(R.layout.act_sensor_setting, 260);
        sparseIntArray.put(R.layout.act_serial_setting, 261);
        sparseIntArray.put(R.layout.act_set_light_channel, 262);
        sparseIntArray.put(R.layout.act_set_screen_display, 263);
        sparseIntArray.put(R.layout.act_share_duv_list, 264);
        sparseIntArray.put(R.layout.act_smart_panel, 265);
        sparseIntArray.put(R.layout.act_smart_panel_child_setting, 266);
        sparseIntArray.put(R.layout.act_smart_panel_group_child_setting, 267);
        sparseIntArray.put(R.layout.act_smart_panel_key_set, 268);
        sparseIntArray.put(R.layout.act_smart_panel_setting, 269);
        sparseIntArray.put(R.layout.act_smart_panel_switch_setting, 270);
        sparseIntArray.put(R.layout.act_smart_panel_theme, LAYOUT_ACTSMARTPANELTHEME);
        sparseIntArray.put(R.layout.act_smart_speaker, 272);
        sparseIntArray.put(R.layout.act_smart_speaker_detail, 273);
        sparseIntArray.put(R.layout.act_songs, 274);
        sparseIntArray.put(R.layout.act_sonos_music_detail, 275);
        sparseIntArray.put(R.layout.act_sonos_setting_default, 276);
        sparseIntArray.put(R.layout.act_sonos_web_view, 277);
        sparseIntArray.put(R.layout.act_sort, 278);
        sparseIntArray.put(R.layout.act_sort_local_music, 279);
        sparseIntArray.put(R.layout.act_sp485_list, 280);
        sparseIntArray.put(R.layout.act_spi_controller, 281);
        sparseIntArray.put(R.layout.act_spi_controller_setting, 282);
        sparseIntArray.put(R.layout.act_spi_edit_play_list, 283);
        sparseIntArray.put(R.layout.act_spi_light_setting, 284);
        sparseIntArray.put(R.layout.act_splash, 285);
        sparseIntArray.put(R.layout.act_steps_introduction, LAYOUT_ACTSTEPSINTRODUCTION);
        sparseIntArray.put(R.layout.act_sub_device_setting_default, LAYOUT_ACTSUBDEVICESETTINGDEFAULT);
        sparseIntArray.put(R.layout.act_super_panel, 288);
        sparseIntArray.put(R.layout.act_super_panel_album, 289);
        sparseIntArray.put(R.layout.act_super_panel_clip_photo, 290);
        sparseIntArray.put(R.layout.act_super_panel_continous_talk, LAYOUT_ACTSUPERPANELCONTINOUSTALK);
        sparseIntArray.put(R.layout.act_super_panel_ir_remote_control, LAYOUT_ACTSUPERPANELIRREMOTECONTROL);
        sparseIntArray.put(R.layout.act_super_panel_key_set, 293);
        sparseIntArray.put(R.layout.act_super_panel_key_set_6s, 294);
        sparseIntArray.put(R.layout.act_super_panel_preview_photo, LAYOUT_ACTSUPERPANELPREVIEWPHOTO);
        sparseIntArray.put(R.layout.act_super_panel_select_photo, LAYOUT_ACTSUPERPANELSELECTPHOTO);
        sparseIntArray.put(R.layout.act_super_panel_setting, LAYOUT_ACTSUPERPANELSETTING);
        sparseIntArray.put(R.layout.act_super_panel_voice_control_range, 298);
        sparseIntArray.put(R.layout.act_super_panel_voice_control_range_role, 299);
        sparseIntArray.put(R.layout.act_super_panel_voice_talk, 300);
        sparseIntArray.put(R.layout.act_te_panel, 301);
        sparseIntArray.put(R.layout.act_test_mode_main, 302);
        sparseIntArray.put(R.layout.act_test_prepare, 303);
        sparseIntArray.put(R.layout.act_test_step, 304);
        sparseIntArray.put(R.layout.act_theme_download, 305);
        sparseIntArray.put(R.layout.act_transfer_music, 306);
        sparseIntArray.put(R.layout.act_trig, 307);
        sparseIntArray.put(R.layout.act_trig_curtain, 308);
        sparseIntArray.put(R.layout.act_trig_curtain_channel_set, 309);
        sparseIntArray.put(R.layout.act_trig_curtain_group_setting, 310);
        sparseIntArray.put(R.layout.act_trig_curtain_open_dir_set, LAYOUT_ACTTRIGCURTAINOPENDIRSET);
        sparseIntArray.put(R.layout.act_trig_to_ble, LAYOUT_ACTTRIGTOBLE);
        sparseIntArray.put(R.layout.act_tv, LAYOUT_ACTTV);
        sparseIntArray.put(R.layout.act_upgrade, LAYOUT_ACTUPGRADE);
        sparseIntArray.put(R.layout.act_user_info, LAYOUT_ACTUSERINFO);
        sparseIntArray.put(R.layout.act_voice_call_bind, LAYOUT_ACTVOICECALLBIND);
        sparseIntArray.put(R.layout.act_voice_call_grop_list, 317);
        sparseIntArray.put(R.layout.act_voice_call_group_add, 318);
        sparseIntArray.put(R.layout.act_voice_call_setting, 319);
        sparseIntArray.put(R.layout.act_voice_call_whitelist_add, 320);
        sparseIntArray.put(R.layout.act_wave_sensor, 321);
        sparseIntArray.put(R.layout.act_wave_sensor_effect_period, 322);
        sparseIntArray.put(R.layout.act_wave_sensor_group_setting, 323);
        sparseIntArray.put(R.layout.act_wave_sensor_pro, 324);
        sparseIntArray.put(R.layout.act_wave_sensor_setting, 325);
        sparseIntArray.put(R.layout.act_web_view, 326);
        sparseIntArray.put(R.layout.act_welcome, 327);
        sparseIntArray.put(R.layout.act_xy_select, 328);
        sparseIntArray.put(R.layout.activity_act_mesh_add_all_device, 329);
        sparseIntArray.put(R.layout.dialog_512_channel_setting, LAYOUT_DIALOG512CHANNELSETTING);
        sparseIntArray.put(R.layout.dialog_ac_quick, LAYOUT_DIALOGACQUICK);
        sparseIntArray.put(R.layout.dialog_add_cg485_category, LAYOUT_DIALOGADDCG485CATEGORY);
        sparseIntArray.put(R.layout.dialog_add_cg485_device, LAYOUT_DIALOGADDCG485DEVICE);
        sparseIntArray.put(R.layout.dialog_add_color_point, LAYOUT_DIALOGADDCOLORPOINT);
        sparseIntArray.put(R.layout.dialog_add_group, LAYOUT_DIALOGADDGROUP);
        sparseIntArray.put(R.layout.dialog_add_scene, LAYOUT_DIALOGADDSCENE);
        sparseIntArray.put(R.layout.dialog_button_tip, LAYOUT_DIALOGBUTTONTIP);
        sparseIntArray.put(R.layout.dialog_calendar, LAYOUT_DIALOGCALENDAR);
        sparseIntArray.put(R.layout.dialog_call_invite, LAYOUT_DIALOGCALLINVITE);
        sparseIntArray.put(R.layout.dialog_center_dali_modify_param, LAYOUT_DIALOGCENTERDALIMODIFYPARAM);
        sparseIntArray.put(R.layout.dialog_center_select_list, LAYOUT_DIALOGCENTERSELECTLIST);
        sparseIntArray.put(R.layout.dialog_center_tip, LAYOUT_DIALOGCENTERTIP);
        sparseIntArray.put(R.layout.dialog_city_picker, LAYOUT_DIALOGCITYPICKER);
        sparseIntArray.put(R.layout.dialog_color_brt_control, LAYOUT_DIALOGCOLORBRTCONTROL);
        sparseIntArray.put(R.layout.dialog_dali_detect, LAYOUT_DIALOGDALIDETECT);
        sparseIntArray.put(R.layout.dialog_dali_detect_tip, LAYOUT_DIALOGDALIDETECTTIP);
        sparseIntArray.put(R.layout.dialog_dali_load_data, LAYOUT_DIALOGDALILOADDATA);
        sparseIntArray.put(R.layout.dialog_del_fail, LAYOUT_DIALOGDELFAIL);
        sparseIntArray.put(R.layout.dialog_device_icon_selector, LAYOUT_DIALOGDEVICEICONSELECTOR);
        sparseIntArray.put(R.layout.dialog_dim_depth_selector, 350);
        sparseIntArray.put(R.layout.dialog_e6_tip, 351);
        sparseIntArray.put(R.layout.dialog_edit, 352);
        sparseIntArray.put(R.layout.dialog_edit_copy, 353);
        sparseIntArray.put(R.layout.dialog_edit_device, LAYOUT_DIALOGEDITDEVICE);
        sparseIntArray.put(R.layout.dialog_eur_function, LAYOUT_DIALOGEURFUNCTION);
        sparseIntArray.put(R.layout.dialog_eur_function_and_rgb, LAYOUT_DIALOGEURFUNCTIONANDRGB);
        sparseIntArray.put(R.layout.dialog_execute_delay_time_selector, LAYOUT_DIALOGEXECUTEDELAYTIMESELECTOR);
        sparseIntArray.put(R.layout.dialog_execute_time_selector, LAYOUT_DIALOGEXECUTETIMESELECTOR);
        sparseIntArray.put(R.layout.dialog_floor_heat_quick, LAYOUT_DIALOGFLOORHEATQUICK);
        sparseIntArray.put(R.layout.dialog_frequency_interval, 360);
        sparseIntArray.put(R.layout.dialog_fresh_air_quick, LAYOUT_DIALOGFRESHAIRQUICK);
        sparseIntArray.put(R.layout.dialog_gradual_time, LAYOUT_DIALOGGRADUALTIME);
        sparseIntArray.put(R.layout.dialog_image_tip, LAYOUT_DIALOGIMAGETIP);
        sparseIntArray.put(R.layout.dialog_intercom_time_picker, LAYOUT_DIALOGINTERCOMTIMEPICKER);
        sparseIntArray.put(R.layout.dialog_ir_fun, LAYOUT_DIALOGIRFUN);
        sparseIntArray.put(R.layout.dialog_ir_learn, LAYOUT_DIALOGIRLEARN);
        sparseIntArray.put(R.layout.dialog_ir_quick, LAYOUT_DIALOGIRQUICK);
        sparseIntArray.put(R.layout.dialog_learn_instruct, LAYOUT_DIALOGLEARNINSTRUCT);
        sparseIntArray.put(R.layout.dialog_left_title_select_list, LAYOUT_DIALOGLEFTTITLESELECTLIST);
        sparseIntArray.put(R.layout.dialog_light_quick, LAYOUT_DIALOGLIGHTQUICK);
        sparseIntArray.put(R.layout.dialog_list, 371);
        sparseIntArray.put(R.layout.dialog_loading, LAYOUT_DIALOGLOADING);
        sparseIntArray.put(R.layout.dialog_loading_progress, LAYOUT_DIALOGLOADINGPROGRESS);
        sparseIntArray.put(R.layout.dialog_loading_success, LAYOUT_DIALOGLOADINGSUCCESS);
        sparseIntArray.put(R.layout.dialog_log_calendar, LAYOUT_DIALOGLOGCALENDAR);
        sparseIntArray.put(R.layout.dialog_matter_qrcode, LAYOUT_DIALOGMATTERQRCODE);
        sparseIntArray.put(R.layout.dialog_ms_time_selector, LAYOUT_DIALOGMSTIMESELECTOR);
        sparseIntArray.put(R.layout.dialog_music_list, LAYOUT_DIALOGMUSICLIST);
        sparseIntArray.put(R.layout.dialog_music_time, LAYOUT_DIALOGMUSICTIME);
        sparseIntArray.put(R.layout.dialog_music_vip_tip, LAYOUT_DIALOGMUSICVIPTIP);
        sparseIntArray.put(R.layout.dialog_music_volume, LAYOUT_DIALOGMUSICVOLUME);
        sparseIntArray.put(R.layout.dialog_number_edit, LAYOUT_DIALOGNUMBEREDIT);
        sparseIntArray.put(R.layout.dialog_pair, LAYOUT_DIALOGPAIR);
        sparseIntArray.put(R.layout.dialog_panel_brt, 384);
        sparseIntArray.put(R.layout.dialog_power_state_batch, LAYOUT_DIALOGPOWERSTATEBATCH);
        sparseIntArray.put(R.layout.dialog_power_state_selector, LAYOUT_DIALOGPOWERSTATESELECTOR);
        sparseIntArray.put(R.layout.dialog_progress, LAYOUT_DIALOGPROGRESS);
        sparseIntArray.put(R.layout.dialog_rc4s_tip, LAYOUT_DIALOGRC4STIP);
        sparseIntArray.put(R.layout.dialog_reg_mode, LAYOUT_DIALOGREGMODE);
        sparseIntArray.put(R.layout.dialog_result, LAYOUT_DIALOGRESULT);
        sparseIntArray.put(R.layout.dialog_rgb_function_tip, LAYOUT_DIALOGRGBFUNCTIONTIP);
        sparseIntArray.put(R.layout.dialog_room_picker, LAYOUT_DIALOGROOMPICKER);
        sparseIntArray.put(R.layout.dialog_room_selector, LAYOUT_DIALOGROOMSELECTOR);
        sparseIntArray.put(R.layout.dialog_scan_nfc, LAYOUT_DIALOGSCANNFC);
        sparseIntArray.put(R.layout.dialog_select_brt, LAYOUT_DIALOGSELECTBRT);
        sparseIntArray.put(R.layout.dialog_select_cgd_action, LAYOUT_DIALOGSELECTCGDACTION);
        sparseIntArray.put(R.layout.dialog_select_color, LAYOUT_DIALOGSELECTCOLOR);
        sparseIntArray.put(R.layout.dialog_select_ct, LAYOUT_DIALOGSELECTCT);
        sparseIntArray.put(R.layout.dialog_select_dim_curve, LAYOUT_DIALOGSELECTDIMCURVE);
        sparseIntArray.put(R.layout.dialog_select_dim_fade_time, 400);
        sparseIntArray.put(R.layout.dialog_select_dim_range, 401);
        sparseIntArray.put(R.layout.dialog_select_gq_theme, 402);
        sparseIntArray.put(R.layout.dialog_select_item, 403);
        sparseIntArray.put(R.layout.dialog_select_k_duv, 404);
        sparseIntArray.put(R.layout.dialog_select_list, LAYOUT_DIALOGSELECTLIST);
        sparseIntArray.put(R.layout.dialog_select_list_and_pic, 406);
        sparseIntArray.put(R.layout.dialog_select_lux, 407);
        sparseIntArray.put(R.layout.dialog_select_scene, LAYOUT_DIALOGSELECTSCENE);
        sparseIntArray.put(R.layout.dialog_select_volume, LAYOUT_DIALOGSELECTVOLUME);
        sparseIntArray.put(R.layout.dialog_sensing_distance_setting, 410);
        sparseIntArray.put(R.layout.dialog_set_ble_type, 411);
        sparseIntArray.put(R.layout.dialog_set_screen_display, 412);
        sparseIntArray.put(R.layout.dialog_single_picker, 413);
        sparseIntArray.put(R.layout.dialog_sp_quick, 414);
        sparseIntArray.put(R.layout.dialog_switch_pattern_selector, 415);
        sparseIntArray.put(R.layout.dialog_te_panel, 416);
        sparseIntArray.put(R.layout.dialog_time_interval_selector, LAYOUT_DIALOGTIMEINTERVALSELECTOR);
        sparseIntArray.put(R.layout.dialog_time_picker, LAYOUT_DIALOGTIMEPICKER);
        sparseIntArray.put(R.layout.dialog_time_selector, LAYOUT_DIALOGTIMESELECTOR);
        sparseIntArray.put(R.layout.dialog_time_selector_with_limit, 420);
        sparseIntArray.put(R.layout.dialog_time_selector_with_ms, 421);
        sparseIntArray.put(R.layout.dialog_timing_set, 422);
        sparseIntArray.put(R.layout.dialog_tip, 423);
        sparseIntArray.put(R.layout.dialog_wheel_select_double_list, 424);
        sparseIntArray.put(R.layout.dialog_wheel_select_list, 425);
        sparseIntArray.put(R.layout.dialog_white_balance, 426);
        sparseIntArray.put(R.layout.dialog_wy, LAYOUT_DIALOGWY);
        sparseIntArray.put(R.layout.footer_add, LAYOUT_FOOTERADD);
        sparseIntArray.put(R.layout.footer_super_panel_key_set, LAYOUT_FOOTERSUPERPANELKEYSET);
        sparseIntArray.put(R.layout.ft_ac, LAYOUT_FTAC);
        sparseIntArray.put(R.layout.ft_ac_dialog, 431);
        sparseIntArray.put(R.layout.ft_access_control, 432);
        sparseIntArray.put(R.layout.ft_advanced_mode, 433);
        sparseIntArray.put(R.layout.ft_air, 434);
        sparseIntArray.put(R.layout.ft_air_dialog, 435);
        sparseIntArray.put(R.layout.ft_automation, 436);
        sparseIntArray.put(R.layout.ft_bind_mail, 437);
        sparseIntArray.put(R.layout.ft_bind_phone, LAYOUT_FTBINDPHONE);
        sparseIntArray.put(R.layout.ft_camera_ptz, LAYOUT_FTCAMERAPTZ);
        sparseIntArray.put(R.layout.ft_camera_record, LAYOUT_FTCAMERARECORD);
        sparseIntArray.put(R.layout.ft_camera_talk, 441);
        sparseIntArray.put(R.layout.ft_clip_photo, 442);
        sparseIntArray.put(R.layout.ft_cloud_scene, 443);
        sparseIntArray.put(R.layout.ft_color_cct, 444);
        sparseIntArray.put(R.layout.ft_color_circle, 445);
        sparseIntArray.put(R.layout.ft_color_hsl, 446);
        sparseIntArray.put(R.layout.ft_color_pushrod, LAYOUT_FTCOLORPUSHROD);
        sparseIntArray.put(R.layout.ft_color_xyy, LAYOUT_FTCOLORXYY);
        sparseIntArray.put(R.layout.ft_ct_light, LAYOUT_FTCTLIGHT);
        sparseIntArray.put(R.layout.ft_dali_add, LAYOUT_FTDALIADD);
        sparseIntArray.put(R.layout.ft_dali_pushrod, LAYOUT_FTDALIPUSHROD);
        sparseIntArray.put(R.layout.ft_device2, LAYOUT_FTDEVICE2);
        sparseIntArray.put(R.layout.ft_device_and_group, LAYOUT_FTDEVICEANDGROUP);
        sparseIntArray.put(R.layout.ft_dim_light, LAYOUT_FTDIMLIGHT);
        sparseIntArray.put(R.layout.ft_find_mail_pwd, LAYOUT_FTFINDMAILPWD);
        sparseIntArray.put(R.layout.ft_find_phone_pwd, LAYOUT_FTFINDPHONEPWD);
        sparseIntArray.put(R.layout.ft_general_mode, LAYOUT_FTGENERALMODE);
        sparseIntArray.put(R.layout.ft_gqx_action, LAYOUT_FTGQXACTION);
        sparseIntArray.put(R.layout.ft_intelligence, LAYOUT_FTINTELLIGENCE);
        sparseIntArray.put(R.layout.ft_list, LAYOUT_FTLIST);
        sparseIntArray.put(R.layout.ft_local_scene, LAYOUT_FTLOCALSCENE);
        sparseIntArray.put(R.layout.ft_log, LAYOUT_FTLOG);
        sparseIntArray.put(R.layout.ft_mail_reg, LAYOUT_FTMAILREG);
        sparseIntArray.put(R.layout.ft_message_home, LAYOUT_FTMESSAGEHOME);
        sparseIntArray.put(R.layout.ft_message_notice, LAYOUT_FTMESSAGENOTICE);
        sparseIntArray.put(R.layout.ft_mic, LAYOUT_FTMIC);
        sparseIntArray.put(R.layout.ft_music, LAYOUT_FTMUSIC);
        sparseIntArray.put(R.layout.ft_my, LAYOUT_FTMY);
        sparseIntArray.put(R.layout.ft_page_screen_panel_detail, LAYOUT_FTPAGESCREENPANELDETAIL);
        sparseIntArray.put(R.layout.ft_page_screen_panel_switch, LAYOUT_FTPAGESCREENPANELSWITCH);
        sparseIntArray.put(R.layout.ft_phone_reg, LAYOUT_FTPHONEREG);
        sparseIntArray.put(R.layout.ft_rgb_light, LAYOUT_FTRGBLIGHT);
        sparseIntArray.put(R.layout.ft_room, LAYOUT_FTROOM);
        sparseIntArray.put(R.layout.ft_room_dali_add, LAYOUT_FTROOMDALIADD);
        sparseIntArray.put(R.layout.ft_scene, LAYOUT_FTSCENE);
        sparseIntArray.put(R.layout.ft_select_icons, LAYOUT_FTSELECTICONS);
        sparseIntArray.put(R.layout.ft_select_product, LAYOUT_FTSELECTPRODUCT);
        sparseIntArray.put(R.layout.ft_select_theme, LAYOUT_FTSELECTTHEME);
        sparseIntArray.put(R.layout.ft_sensor_test_step, LAYOUT_FTSENSORTESTSTEP);
        sparseIntArray.put(R.layout.ft_song_playlist, 480);
        sparseIntArray.put(R.layout.ft_songs, LAYOUT_FTSONGS);
        sparseIntArray.put(R.layout.ft_spi_color, LAYOUT_FTSPICOLOR);
        sparseIntArray.put(R.layout.ft_spi_mode, LAYOUT_FTSPIMODE);
        sparseIntArray.put(R.layout.ft_spi_play_list, LAYOUT_FTSPIPLAYLIST);
        sparseIntArray.put(R.layout.ft_steps_introduction, LAYOUT_FTSTEPSINTRODUCTION);
        sparseIntArray.put(R.layout.ft_sub_device, LAYOUT_FTSUBDEVICE);
        sparseIntArray.put(R.layout.ft_tv_ext_fun, 487);
        sparseIntArray.put(R.layout.ft_tv_fun, 488);
        sparseIntArray.put(R.layout.ft_tv_num, LAYOUT_FTTVNUM);
        sparseIntArray.put(R.layout.head_g4_max_key_set, LAYOUT_HEADG4MAXKEYSET);
        sparseIntArray.put(R.layout.head_super_panel_key_set, LAYOUT_HEADSUPERPANELKEYSET);
        sparseIntArray.put(R.layout.item_485_to_ble, LAYOUT_ITEM485TOBLE);
        sparseIntArray.put(R.layout.item_add_group, LAYOUT_ITEMADDGROUP);
        sparseIntArray.put(R.layout.item_add_instruct_content, LAYOUT_ITEMADDINSTRUCTCONTENT);
        sparseIntArray.put(R.layout.item_add_instruct_title, LAYOUT_ITEMADDINSTRUCTTITLE);
        sparseIntArray.put(R.layout.item_advanced_mode_add, LAYOUT_ITEMADVANCEDMODEADD);
        sparseIntArray.put(R.layout.item_advanced_mode_color_time, LAYOUT_ITEMADVANCEDMODECOLORTIME);
        sparseIntArray.put(R.layout.item_as_panel_related_info, LAYOUT_ITEMASPANELRELATEDINFO);
        sparseIntArray.put(R.layout.item_auto_condition, LAYOUT_ITEMAUTOCONDITION);
        sparseIntArray.put(R.layout.item_automation, 500);
    }

    private static void internalPopulateLayoutIdLookup1() {
        SparseIntArray sparseIntArray = INTERNAL_LAYOUT_ID_LOOKUP;
        sparseIntArray.put(R.layout.item_batch_setting, 501);
        sparseIntArray.put(R.layout.item_big_content_left, 502);
        sparseIntArray.put(R.layout.item_big_content_right, 503);
        sparseIntArray.put(R.layout.item_bind_eur_scene, 504);
        sparseIntArray.put(R.layout.item_bind_zone, 505);
        sparseIntArray.put(R.layout.item_ble_to_485, 506);
        sparseIntArray.put(R.layout.item_bt_ota, LAYOUT_ITEMBTOTA);
        sparseIntArray.put(R.layout.item_category, LAYOUT_ITEMCATEGORY);
        sparseIntArray.put(R.layout.item_central_air_sub_device, LAYOUT_ITEMCENTRALAIRSUBDEVICE);
        sparseIntArray.put(R.layout.item_cg485_color, 510);
        sparseIntArray.put(R.layout.item_cgd, 511);
        sparseIntArray.put(R.layout.item_cgd_action, 512);
        sparseIntArray.put(R.layout.item_cloud_scene, 513);
        sparseIntArray.put(R.layout.item_color, 514);
        sparseIntArray.put(R.layout.item_color_point, 515);
        sparseIntArray.put(R.layout.item_command, 516);
        sparseIntArray.put(R.layout.item_content_normal, 517);
        sparseIntArray.put(R.layout.item_content_small, 518);
        sparseIntArray.put(R.layout.item_ct_light_group_control_sub_device, 519);
        sparseIntArray.put(R.layout.item_curtain_currainmotortype_select, 520);
        sparseIntArray.put(R.layout.item_curtain_open_dir_select, 521);
        sparseIntArray.put(R.layout.item_dali_batch_modify_param, 522);
        sparseIntArray.put(R.layout.item_dali_light_manage, LAYOUT_ITEMDALILIGHTMANAGE);
        sparseIntArray.put(R.layout.item_dali_manage, LAYOUT_ITEMDALIMANAGE);
        sparseIntArray.put(R.layout.item_default, LAYOUT_ITEMDEFAULT);
        sparseIntArray.put(R.layout.item_default_mode, LAYOUT_ITEMDEFAULTMODE);
        sparseIntArray.put(R.layout.item_default_mode_select, LAYOUT_ITEMDEFAULTMODESELECT);
        sparseIntArray.put(R.layout.item_device_as_panel, 528);
        sparseIntArray.put(R.layout.item_device_ble, 529);
        sparseIntArray.put(R.layout.item_device_camera, 530);
        sparseIntArray.put(R.layout.item_device_central_air_gate, 531);
        sparseIntArray.put(R.layout.item_device_group_manage, 532);
        sparseIntArray.put(R.layout.item_device_ir, 533);
        sparseIntArray.put(R.layout.item_device_key_switch, 534);
        sparseIntArray.put(R.layout.item_device_light, 535);
        sparseIntArray.put(R.layout.item_device_log, 536);
        sparseIntArray.put(R.layout.item_device_manage, 537);
        sparseIntArray.put(R.layout.item_device_manage_new, 538);
        sparseIntArray.put(R.layout.item_device_mesh_gateway, 539);
        sparseIntArray.put(R.layout.item_device_music_player, 540);
        sparseIntArray.put(R.layout.item_device_scene, 541);
        sparseIntArray.put(R.layout.item_device_select, 542);
        sparseIntArray.put(R.layout.item_device_sensor, 543);
        sparseIntArray.put(R.layout.item_device_sort, 544);
        sparseIntArray.put(R.layout.item_device_super_panel, 545);
        sparseIntArray.put(R.layout.item_diy_mode, 546);
        sparseIntArray.put(R.layout.item_double_text, 547);
        sparseIntArray.put(R.layout.item_e6_panel_key, 548);
        sparseIntArray.put(R.layout.item_e6d_action, 549);
        sparseIntArray.put(R.layout.item_e6d_address, 550);
        sparseIntArray.put(R.layout.item_e6d_default, 551);
        sparseIntArray.put(R.layout.item_edit_diy_key_name, 552);
        sparseIntArray.put(R.layout.item_empty_foot, 553);
        sparseIntArray.put(R.layout.item_empty_foot_15, 554);
        sparseIntArray.put(R.layout.item_env_status_detail_condition, 555);
        sparseIntArray.put(R.layout.item_floor_manage, 556);
        sparseIntArray.put(R.layout.item_fresh_air_info, 557);
        sparseIntArray.put(R.layout.item_general_mode, 558);
        sparseIntArray.put(R.layout.item_go, 559);
        sparseIntArray.put(R.layout.item_go_1, 560);
        sparseIntArray.put(R.layout.item_go_2, 561);
        sparseIntArray.put(R.layout.item_group_device_sort, LAYOUT_ITEMGROUPDEVICESORT);
        sparseIntArray.put(R.layout.item_group_light, 563);
        sparseIntArray.put(R.layout.item_group_manage, 564);
        sparseIntArray.put(R.layout.item_group_select, 565);
        sparseIntArray.put(R.layout.item_group_smart_panel, 566);
        sparseIntArray.put(R.layout.item_group_switch_pattern, 567);
        sparseIntArray.put(R.layout.item_group_wave_sensor, 568);
        sparseIntArray.put(R.layout.item_home_manage, 569);
        sparseIntArray.put(R.layout.item_img, 570);
        sparseIntArray.put(R.layout.item_img_preview, 571);
        sparseIntArray.put(R.layout.item_intercom_door, 572);
        sparseIntArray.put(R.layout.item_intercom_log_call, 573);
        sparseIntArray.put(R.layout.item_intercom_log_open_door, 574);
        sparseIntArray.put(R.layout.item_intercom_log_title, 575);
        sparseIntArray.put(R.layout.item_ir_diy_fun, 576);
        sparseIntArray.put(R.layout.item_ir_diy_key, 577);
        sparseIntArray.put(R.layout.item_ir_fun, 578);
        sparseIntArray.put(R.layout.item_ir_key, 579);
        sparseIntArray.put(R.layout.item_ir_key_te, 580);
        sparseIntArray.put(R.layout.item_k_and_duv, 581);
        sparseIntArray.put(R.layout.item_light_action, 582);
        sparseIntArray.put(R.layout.item_light_group_control, 583);
        sparseIntArray.put(R.layout.item_light_group_control_sub_device, 584);
        sparseIntArray.put(R.layout.item_light_order, 585);
        sparseIntArray.put(R.layout.item_mesh_scan, 586);
        sparseIntArray.put(R.layout.item_mesh_scan_all_device, 587);
        sparseIntArray.put(R.layout.item_mesh_scan_replace, 588);
        sparseIntArray.put(R.layout.item_mesh_search_scan, 589);
        sparseIntArray.put(R.layout.item_message_data_footer, 590);
        sparseIntArray.put(R.layout.item_message_notice, 591);
        sparseIntArray.put(R.layout.item_message_place, 592);
        sparseIntArray.put(R.layout.item_mode, 593);
        sparseIntArray.put(R.layout.item_mr_param, 594);
        sparseIntArray.put(R.layout.item_music, 595);
        sparseIntArray.put(R.layout.item_music_dialog_list, 596);
        sparseIntArray.put(R.layout.item_music_list, 597);
        sparseIntArray.put(R.layout.item_music_search, 598);
        sparseIntArray.put(R.layout.item_name_value, 599);
        sparseIntArray.put(R.layout.item_num_panel_switch_position_set, 600);
        sparseIntArray.put(R.layout.item_pad_content, 601);
        sparseIntArray.put(R.layout.item_page_screen_panel, 602);
        sparseIntArray.put(R.layout.item_page_smart_panel_key_set, 603);
        sparseIntArray.put(R.layout.item_panel_bind, 604);
        sparseIntArray.put(R.layout.item_panel_switch_position_set, 605);
        sparseIntArray.put(R.layout.item_pic, 606);
        sparseIntArray.put(R.layout.item_place_user, 607);
        sparseIntArray.put(R.layout.item_place_user_transform_place, 608);
        sparseIntArray.put(R.layout.item_playlist_manager, 609);
        sparseIntArray.put(R.layout.item_relate_info, 610);
        sparseIntArray.put(R.layout.item_rgbcw_duv, 611);
        sparseIntArray.put(R.layout.item_room_manage, 612);
        sparseIntArray.put(R.layout.item_room_name, 613);
        sparseIntArray.put(R.layout.item_rs8_key, 614);
        sparseIntArray.put(R.layout.item_rs8_sub_device, 615);
        sparseIntArray.put(R.layout.item_scene_action, 616);
        sparseIntArray.put(R.layout.item_scene_eur, 617);
        sparseIntArray.put(R.layout.item_scene_new, 618);
        sparseIntArray.put(R.layout.item_scene_panel_key, 619);
        sparseIntArray.put(R.layout.item_screen_display, 620);
        sparseIntArray.put(R.layout.item_screen_info_input, 621);
        sparseIntArray.put(R.layout.item_screen_panel, 622);
        sparseIntArray.put(R.layout.item_screen_panel_mult_line, 623);
        sparseIntArray.put(R.layout.item_screen_sp_btn_key, 624);
        sparseIntArray.put(R.layout.item_search_bar, 625);
        sparseIntArray.put(R.layout.item_search_bar_no_edit, 626);
        sparseIntArray.put(R.layout.item_search_bar_no_edit_music, 627);
        sparseIntArray.put(R.layout.item_select, 628);
        sparseIntArray.put(R.layout.item_select2, 629);
        sparseIntArray.put(R.layout.item_select_automation, 630);
        sparseIntArray.put(R.layout.item_select_cloud_scene, 631);
        sparseIntArray.put(R.layout.item_select_cover, 632);
        sparseIntArray.put(R.layout.item_select_dali_with_place, 633);
        sparseIntArray.put(R.layout.item_select_device_icon, 634);
        sparseIntArray.put(R.layout.item_select_device_icon_horizontal, 635);
        sparseIntArray.put(R.layout.item_select_device_import_mode, 636);
        sparseIntArray.put(R.layout.item_select_device_in_group, 637);
        sparseIntArray.put(R.layout.item_select_device_manage, 638);
        sparseIntArray.put(R.layout.item_select_device_with_place, 639);
        sparseIntArray.put(R.layout.item_select_device_with_sync, 640);
        sparseIntArray.put(R.layout.item_select_diy, 641);
        sparseIntArray.put(R.layout.item_select_gateway, 642);
        sparseIntArray.put(R.layout.item_select_home_member, 643);
        sparseIntArray.put(R.layout.item_select_icon, 644);
        sparseIntArray.put(R.layout.item_select_k_value, 645);
        sparseIntArray.put(R.layout.item_select_list, 646);
        sparseIntArray.put(R.layout.item_select_list_white, 647);
        sparseIntArray.put(R.layout.item_select_list_with_icon, 648);
        sparseIntArray.put(R.layout.item_select_loop_value, 649);
        sparseIntArray.put(R.layout.item_select_lux_ct, 650);
        sparseIntArray.put(R.layout.item_select_mode_cover, 651);
        sparseIntArray.put(R.layout.item_select_music_player_volume, 652);
        sparseIntArray.put(R.layout.item_select_on_off_time_diy, 653);
        sparseIntArray.put(R.layout.item_select_on_off_time_normal, 654);
        sparseIntArray.put(R.layout.item_select_place, 655);
        sparseIntArray.put(R.layout.item_select_product, 656);
        sparseIntArray.put(R.layout.item_select_scene, 657);
        sparseIntArray.put(R.layout.item_select_sub, LAYOUT_ITEMSELECTSUB);
        sparseIntArray.put(R.layout.item_select_subtext_ver, LAYOUT_ITEMSELECTSUBTEXTVER);
        sparseIntArray.put(R.layout.item_select_subtext_ver_onoff_diy, LAYOUT_ITEMSELECTSUBTEXTVERONOFFDIY);
        sparseIntArray.put(R.layout.item_select_subtext_ver_onoff_diy_all, LAYOUT_ITEMSELECTSUBTEXTVERONOFFDIYALL);
        sparseIntArray.put(R.layout.item_select_subtext_ver_onoff_diy_dali, LAYOUT_ITEMSELECTSUBTEXTVERONOFFDIYDALI);
        sparseIntArray.put(R.layout.item_select_with_icon, LAYOUT_ITEMSELECTWITHICON);
        sparseIntArray.put(R.layout.item_select_with_place_cg485, LAYOUT_ITEMSELECTWITHPLACECG485);
        sparseIntArray.put(R.layout.item_select_with_sub, 665);
        sparseIntArray.put(R.layout.item_sensor_sub_device, LAYOUT_ITEMSENSORSUBDEVICE);
        sparseIntArray.put(R.layout.item_setting, LAYOUT_ITEMSETTING);
        sparseIntArray.put(R.layout.item_share_data, 668);
        sparseIntArray.put(R.layout.item_simple_select, 669);
        sparseIntArray.put(R.layout.item_smart_panel, 670);
        sparseIntArray.put(R.layout.item_smart_panel_key, 671);
        sparseIntArray.put(R.layout.item_smart_panel_key_eb6, 672);
        sparseIntArray.put(R.layout.item_smart_panel_key_set, 673);
        sparseIntArray.put(R.layout.item_smart_speaker, 674);
        sparseIntArray.put(R.layout.item_song_info, 675);
        sparseIntArray.put(R.layout.item_song_playlist, 676);
        sparseIntArray.put(R.layout.item_song_playlist_foot, 677);
        sparseIntArray.put(R.layout.item_sort, 678);
        sparseIntArray.put(R.layout.item_sp_btn_key, 679);
        sparseIntArray.put(R.layout.item_spi_edit_play_list, 680);
        sparseIntArray.put(R.layout.item_spi_mode, 681);
        sparseIntArray.put(R.layout.item_spi_play_list, 682);
        sparseIntArray.put(R.layout.item_sq_panel_action, 683);
        sparseIntArray.put(R.layout.item_super_panel_fun, 684);
        sparseIntArray.put(R.layout.item_super_panel_key_set, 685);
        sparseIntArray.put(R.layout.item_super_panel_switch, 686);
        sparseIntArray.put(R.layout.item_switch, 687);
        sparseIntArray.put(R.layout.item_switch_button, 688);
        sparseIntArray.put(R.layout.item_tail, 689);
        sparseIntArray.put(R.layout.item_temp, 690);
        sparseIntArray.put(R.layout.item_text, 691);
        sparseIntArray.put(R.layout.item_text_36, 692);
        sparseIntArray.put(R.layout.item_text_add, 693);
        sparseIntArray.put(R.layout.item_text_head, 694);
        sparseIntArray.put(R.layout.item_text_middle, 695);
        sparseIntArray.put(R.layout.item_text_wrap, 696);
        sparseIntArray.put(R.layout.item_title, 697);
        sparseIntArray.put(R.layout.item_trig_key, 698);
        sparseIntArray.put(R.layout.item_trig_scene, 699);
        sparseIntArray.put(R.layout.layout_empty, 700);
        sparseIntArray.put(R.layout.layout_error, 701);
        sparseIntArray.put(R.layout.layout_error_2, 702);
        sparseIntArray.put(R.layout.layout_ext, 703);
        sparseIntArray.put(R.layout.layout_loading, 704);
        sparseIntArray.put(R.layout.layout_protocol_default, 705);
        sparseIntArray.put(R.layout.layout_radio_image_text_view, 706);
        sparseIntArray.put(R.layout.layout_tab_view, 707);
        sparseIntArray.put(R.layout.layout_title_default, 708);
        sparseIntArray.put(R.layout.layout_title_ir, 709);
        sparseIntArray.put(R.layout.layout_title_tran, 710);
        sparseIntArray.put(R.layout.layout_title_transparent, 711);
        sparseIntArray.put(R.layout.tab_text, 712);
        sparseIntArray.put(R.layout.tab_text2, 713);
        sparseIntArray.put(R.layout.view_brt_setting, 714);
        sparseIntArray.put(R.layout.view_cgd_action, 715);
        sparseIntArray.put(R.layout.view_cgd_light_title_dali, 716);
        sparseIntArray.put(R.layout.view_cgd_light_title_manage, 717);
        sparseIntArray.put(R.layout.view_cgd_light_title_select, 718);
        sparseIntArray.put(R.layout.view_cgd_light_title_select_group, 719);
        sparseIntArray.put(R.layout.view_dali_manage_bottom, 720);
        sparseIntArray.put(R.layout.view_dali_text_seekbar, 721);
        sparseIntArray.put(R.layout.view_dali_text_seekbar_new, 722);
        sparseIntArray.put(R.layout.view_device_manage_bottom, 723);
        sparseIntArray.put(R.layout.view_edit_text_seekbar, 724);
        sparseIntArray.put(R.layout.view_eur_tip_function, 725);
        sparseIntArray.put(R.layout.view_eur_tip_scene, 726);
        sparseIntArray.put(R.layout.view_eur_tip_zone, 727);
        sparseIntArray.put(R.layout.view_five_current_item, 728);
        sparseIntArray.put(R.layout.view_image_text, 729);
        sparseIntArray.put(R.layout.view_location_tip, 730);
        sparseIntArray.put(R.layout.view_number_setting, 731);
        sparseIntArray.put(R.layout.view_power_state_ble_all, 732);
        sparseIntArray.put(R.layout.view_remote_tip, 733);
        sparseIntArray.put(R.layout.view_s1_pro_guide, 734);
        sparseIntArray.put(R.layout.view_s1_pro_guide_en, 735);
        sparseIntArray.put(R.layout.view_s2_pro_guide, 736);
        sparseIntArray.put(R.layout.view_s2_pro_guide_en, 737);
        sparseIntArray.put(R.layout.view_s3_pro_guide, 738);
        sparseIntArray.put(R.layout.view_s3_pro_guide_en, 739);
        sparseIntArray.put(R.layout.view_sq_pro_guide, 740);
        sparseIntArray.put(R.layout.view_sq_pro_guide_en, 741);
        sparseIntArray.put(R.layout.view_switch_seekbar, 742);
        sparseIntArray.put(R.layout.view_switch_two_seekbar, 743);
        sparseIntArray.put(R.layout.view_text_seekbar, 744);
        sparseIntArray.put(R.layout.view_text_with_button, 745);
    }

    private static class InnerBrLookup {
        static final SparseArray<String> sKeys;

        private InnerBrLookup() {
        }

        static {
            SparseArray<String> sparseArray = new SparseArray<>(97);
            sKeys = sparseArray;
            sparseArray.put(1, "Content");
            sparseArray.put(0, "_all");
            sparseArray.put(2, GetSmsCodeResetReq.ACCOUNT);
            sparseArray.put(3, "appNotice");
            sparseArray.put(4, "bgRes");
            sparseArray.put(5, "bottomText");
            sparseArray.put(6, "bottomTip");
            sparseArray.put(7, "btnName");
            sparseArray.put(8, "checkedChangeListener");
            sparseArray.put(9, "circle");
            sparseArray.put(10, "clickCommand");
            sparseArray.put(11, "clicklocation");
            sparseArray.put(12, "colorControl");
            sparseArray.put(13, "content");
            sparseArray.put(14, "content2");
            sparseArray.put(15, MtcConf2Constants.MtcConfCreateTimeKey);
            sparseArray.put(16, "ctGone");
            sparseArray.put(17, Constants.CURRENT);
            sparseArray.put(18, "currentVersion");
            sparseArray.put(19, "cwGone");
            sparseArray.put(20, "date");
            sparseArray.put(21, "defaultRes");
            sparseArray.put(22, "deleteTip");
            sparseArray.put(23, "device");
            sparseArray.put(24, "deviceName");
            sparseArray.put(25, "deviceVisible");
            sparseArray.put(26, "diyVolume");
            sparseArray.put(27, "endTime");
            sparseArray.put(28, "errorRes");
            sparseArray.put(29, Constants.EXPAND);
            sparseArray.put(30, "footer");
            sparseArray.put(31, "gravity");
            sparseArray.put(32, "group");
            sparseArray.put(33, "hasMeshNetworkFun");
            sparseArray.put(34, "hideMore");
            sparseArray.put(35, "hideSwitch");
            sparseArray.put(36, "hourUnit");
            sparseArray.put(37, "iconRes");
            sparseArray.put(38, "illuminance");
            sparseArray.put(39, "imageRes");
            sparseArray.put(40, "item");
            sparseArray.put(41, "itemBinding");
            sparseArray.put(42, MtcConfConstants.MtcConfRecordListKey);
            sparseArray.put(43, "managerOrOwner");
            sparseArray.put(44, "minUnit");
            sparseArray.put(45, "msUnit");
            sparseArray.put(46, "name");
            sparseArray.put(47, "nameText");
            sparseArray.put(48, "nameTip");
            sparseArray.put(49, "newVersion");
            sparseArray.put(50, "noBackground");
            sparseArray.put(51, "offline");
            sparseArray.put(52, "onlineString");
            sparseArray.put(53, "onlineTextBgRes");
            sparseArray.put(54, "onlineTextColor");
            sparseArray.put(55, "ownerOrManager");
            sparseArray.put(56, "password");
            sparseArray.put(57, "place");
            sparseArray.put(58, "placeHolderRes");
            sparseArray.put(59, "placeInfo");
            sparseArray.put(60, "placeMessage");
            sparseArray.put(61, "productId");
            sparseArray.put(62, "protocol");
            sparseArray.put(63, "rgbGone");
            sparseArray.put(64, MtcConf2Constants.MtcConfRoleExKey);
            sparseArray.put(65, "sceneEdit");
            sparseArray.put(66, "secUnit");
            sparseArray.put(67, "select");
            sparseArray.put(68, "selectRes");
            sparseArray.put(69, Constants.SENSITIVITY);
            sparseArray.put(70, "serialNum");
            sparseArray.put(71, "showInput");
            sparseArray.put(72, "showTitle");
            sparseArray.put(73, "signal");
            sparseArray.put(74, Constants.SSID);
            sparseArray.put(75, "startTime");
            sparseArray.put(76, "stateOn");
            sparseArray.put(77, "strProgress");
            sparseArray.put(78, AlcsConstant.EVENT_SUB);
            sparseArray.put(79, "switchPositionSetting");
            sparseArray.put(80, "switchSettingShow");
            sparseArray.put(81, "textColor");
            sparseArray.put(82, "tipText");
            sparseArray.put(83, "title");
            sparseArray.put(84, "titleGone");
            sparseArray.put(85, "type");
            sparseArray.put(86, "typeBg");
            sparseArray.put(87, "upgradeName");
            sparseArray.put(88, "upgradeTip");
            sparseArray.put(89, "user");
            sparseArray.put(90, "verifyCode");
            sparseArray.put(91, "viewModel");
            sparseArray.put(92, "viewmodel");
            sparseArray.put(93, "voiceContent");
            sparseArray.put(94, "voiceVisible");
            sparseArray.put(95, "wGone");
            sparseArray.put(96, "withUnit");
        }
    }

    private static class InnerLayoutIdLookup {
        static final HashMap<String, Integer> sKeys = new HashMap<>(745);

        private InnerLayoutIdLookup() {
        }

        static {
            internalPopulateLayoutIdLookup0();
            internalPopulateLayoutIdLookup1();
        }

        private static void internalPopulateLayoutIdLookup0() {
            HashMap<String, Integer> hashMap = sKeys;
            hashMap.put("layout/act_about_0", Integer.valueOf(R.layout.act_about));
            hashMap.put("layout/act_ac_0", Integer.valueOf(R.layout.act_ac));
            hashMap.put("layout/act_ac_bg_0", Integer.valueOf(R.layout.act_ac_bg));
            hashMap.put("layout/act_ac_central_0", Integer.valueOf(R.layout.act_ac_central));
            hashMap.put("layout/act_account_and_security_0", Integer.valueOf(R.layout.act_account_and_security));
            hashMap.put("layout/act_add_automation_0", Integer.valueOf(R.layout.act_add_automation));
            hashMap.put("layout/act_add_cloud_scene_0", Integer.valueOf(R.layout.act_add_cloud_scene));
            hashMap.put("layout/act_add_device_0", Integer.valueOf(R.layout.act_add_device));
            hashMap.put("layout/act_add_duv_0", Integer.valueOf(R.layout.act_add_duv));
            hashMap.put("layout/act_add_floor_0", Integer.valueOf(R.layout.act_add_floor));
            hashMap.put("layout/act_add_instruct_0", Integer.valueOf(R.layout.act_add_instruct));
            hashMap.put("layout/act_add_ir_device_0", Integer.valueOf(R.layout.act_add_ir_device));
            hashMap.put("layout/act_add_ir_key_0", Integer.valueOf(R.layout.act_add_ir_key));
            hashMap.put("layout/act_add_local_scene_0", Integer.valueOf(R.layout.act_add_local_scene));
            hashMap.put("layout/act_add_mode_color_0", Integer.valueOf(R.layout.act_add_mode_color));
            hashMap.put("layout/act_add_mode_ct_dim_0", Integer.valueOf(R.layout.act_add_mode_ct_dim));
            hashMap.put("layout/act_add_music_0", Integer.valueOf(R.layout.act_add_music));
            hashMap.put("layout/act_add_place_user_0", Integer.valueOf(R.layout.act_add_place_user));
            hashMap.put("layout/act_add_virtual_device_0", Integer.valueOf(R.layout.act_add_virtual_device));
            hashMap.put("layout/act_as_panel_0", Integer.valueOf(R.layout.act_as_panel));
            hashMap.put("layout/act_as_panel_setting_0", Integer.valueOf(R.layout.act_as_panel_setting));
            hashMap.put("layout/act_auto_net_time_setting_0", Integer.valueOf(R.layout.act_auto_net_time_setting));
            hashMap.put("layout/act_battery_guide_0", Integer.valueOf(R.layout.act_battery_guide));
            hashMap.put("layout/act_ble_curtain_motor_0", Integer.valueOf(R.layout.act_ble_curtain_motor));
            hashMap.put("layout/act_ble_curtain_motor_more_setting_0", Integer.valueOf(R.layout.act_ble_curtain_motor_more_setting));
            hashMap.put("layout/act_ble_curtain_motor_type_setting_0", Integer.valueOf(R.layout.act_ble_curtain_motor_type_setting));
            hashMap.put("layout/act_ble_ham_setting_default_0", Integer.valueOf(R.layout.act_ble_ham_setting_default));
            hashMap.put("layout/act_ble_motor_mode_set_0", Integer.valueOf(R.layout.act_ble_motor_mode_set));
            hashMap.put("layout/act_ble_motor_setting_0", Integer.valueOf(R.layout.act_ble_motor_setting));
            hashMap.put("layout/act_ble_music_player_0", Integer.valueOf(R.layout.act_ble_music_player));
            hashMap.put("layout/act_ble_music_player_setting_0", Integer.valueOf(R.layout.act_ble_music_player_setting));
            hashMap.put("layout/act_ble_trig_curtain_setting_0", Integer.valueOf(R.layout.act_ble_trig_curtain_setting));
            hashMap.put("layout/act_ble_trig_scene_setting_0", Integer.valueOf(R.layout.act_ble_trig_scene_setting));
            hashMap.put("layout/act_brt_button_setting_0", Integer.valueOf(R.layout.act_brt_button_setting));
            hashMap.put("layout/act_bt_ota_0", Integer.valueOf(R.layout.act_bt_ota));
            hashMap.put("layout/act_bt_ota_low_power_0", Integer.valueOf(R.layout.act_bt_ota_low_power));
            hashMap.put("layout/act_bt_ota_single_0", Integer.valueOf(R.layout.act_bt_ota_single));
            hashMap.put("layout/act_camera_info_0", Integer.valueOf(R.layout.act_camera_info));
            hashMap.put("layout/act_camera_play_0", Integer.valueOf(R.layout.act_camera_play));
            hashMap.put("layout/act_camera_play_back_0", Integer.valueOf(R.layout.act_camera_play_back));
            hashMap.put("layout/act_camera_setting_0", Integer.valueOf(R.layout.act_camera_setting));
            hashMap.put("layout/act_central_air_gateway_0", Integer.valueOf(R.layout.act_central_air_gateway));
            hashMap.put("layout/act_central_air_mesh_gateway_setting_0", Integer.valueOf(R.layout.act_central_air_mesh_gateway_setting));
            hashMap.put("layout/act_central_air_pro_gateway_0", Integer.valueOf(R.layout.act_central_air_pro_gateway));
            hashMap.put("layout/act_cg_485_0", Integer.valueOf(R.layout.act_cg_485));
            hashMap.put("layout/act_cg_485_device_0", Integer.valueOf(R.layout.act_cg_485_device));
            hashMap.put("layout/act_cg_485_old_0", Integer.valueOf(R.layout.act_cg_485_old));
            hashMap.put("layout/act_cg_485_setting_0", Integer.valueOf(R.layout.act_cg_485_setting));
            hashMap.put("layout/act_cgd_pro_light_0", Integer.valueOf(R.layout.act_cgd_pro_light));
            hashMap.put("layout/act_cgd_pro_light_setting_0", Integer.valueOf(R.layout.act_cgd_pro_light_setting));
            hashMap.put("layout/act_change_email_0", Integer.valueOf(R.layout.act_change_email));
            hashMap.put("layout/act_change_phone_0", Integer.valueOf(R.layout.act_change_phone));
            hashMap.put("layout/act_change_pwd_0", Integer.valueOf(R.layout.act_change_pwd));
            hashMap.put("layout/act_channel_512_0", Integer.valueOf(R.layout.act_channel_512));
            hashMap.put("layout/act_child_mcu_upgrade_0", Integer.valueOf(R.layout.act_child_mcu_upgrade));
            hashMap.put("layout/act_choice_light_type_0", Integer.valueOf(R.layout.act_choice_light_type));
            hashMap.put("layout/act_color_light_0", Integer.valueOf(R.layout.act_color_light));
            hashMap.put("layout/act_color_light_cc_0", Integer.valueOf(R.layout.act_color_light_cc));
            hashMap.put("layout/act_command_category_0", Integer.valueOf(R.layout.act_command_category));
            hashMap.put("layout/act_config_success_0", Integer.valueOf(R.layout.act_config_success));
            hashMap.put("layout/act_create_home_0", Integer.valueOf(R.layout.act_create_home));
            hashMap.put("layout/act_ct_edit_0", Integer.valueOf(R.layout.act_ct_edit));
            hashMap.put("layout/act_ct_light_0", Integer.valueOf(R.layout.act_ct_light));
            hashMap.put("layout/act_ct_select_color_0", Integer.valueOf(R.layout.act_ct_select_color));
            hashMap.put("layout/act_current_set_0", Integer.valueOf(R.layout.act_current_set));
            hashMap.put("layout/act_current_set_five_0", Integer.valueOf(R.layout.act_current_set_five));
            hashMap.put("layout/act_dali_batch_modify_param_0", Integer.valueOf(R.layout.act_dali_batch_modify_param));
            hashMap.put("layout/act_dali_light_0", Integer.valueOf(R.layout.act_dali_light));
            hashMap.put("layout/act_dali_light_group_setting_0", Integer.valueOf(R.layout.act_dali_light_group_setting));
            hashMap.put("layout/act_dali_light_setting_0", Integer.valueOf(R.layout.act_dali_light_setting));
            hashMap.put("layout/act_dali_scene_setting_0", Integer.valueOf(R.layout.act_dali_scene_setting));
            hashMap.put("layout/act_dali_select_0", Integer.valueOf(R.layout.act_dali_select));
            hashMap.put("layout/act_dca_music_detail_0", Integer.valueOf(R.layout.act_dca_music_detail));
            hashMap.put("layout/act_dca_music_home_0", Integer.valueOf(R.layout.act_dca_music_home));
            hashMap.put("layout/act_dca_music_list_0", Integer.valueOf(R.layout.act_dca_music_list));
            hashMap.put("layout/act_dca_web_view_0", Integer.valueOf(R.layout.act_dca_web_view));
            hashMap.put("layout/act_device_config_fail_0", Integer.valueOf(R.layout.act_device_config_fail));
            hashMap.put("layout/act_device_connect_0", Integer.valueOf(R.layout.act_device_connect));
            hashMap.put("layout/act_device_connect_android10_0", Integer.valueOf(R.layout.act_device_connect_android10));
            hashMap.put("layout/act_device_group_manage_0", Integer.valueOf(R.layout.act_device_group_manage));
            hashMap.put("layout/act_device_log_0", Integer.valueOf(R.layout.act_device_log));
            hashMap.put("layout/act_device_manage_0", Integer.valueOf(R.layout.act_device_manage));
            hashMap.put("layout/act_device_replace_0", Integer.valueOf(R.layout.act_device_replace));
            hashMap.put("layout/act_device_setting_battery_0", Integer.valueOf(R.layout.act_device_setting_battery));
            hashMap.put("layout/act_device_setting_default_0", Integer.valueOf(R.layout.act_device_setting_default));
            hashMap.put("layout/act_dim_light_0", Integer.valueOf(R.layout.act_dim_light));
            hashMap.put("layout/act_dim_select_color_0", Integer.valueOf(R.layout.act_dim_select_color));
            hashMap.put("layout/act_diy_ir_setting_0", Integer.valueOf(R.layout.act_diy_ir_setting));
            hashMap.put("layout/act_diy_light_name_0", Integer.valueOf(R.layout.act_diy_light_name));
            hashMap.put("layout/act_diy_room_0", Integer.valueOf(R.layout.act_diy_room));
            hashMap.put("layout/act_dmx_512_setting_0", Integer.valueOf(R.layout.act_dmx_512_setting));
            hashMap.put("layout/act_dmx_channel_select_0", Integer.valueOf(R.layout.act_dmx_channel_select));
            hashMap.put("layout/act_door_sensor_0", Integer.valueOf(R.layout.act_door_sensor));
            hashMap.put("layout/act_duv_list_0", Integer.valueOf(R.layout.act_duv_list));
            hashMap.put("layout/act_e6_panel_0", Integer.valueOf(R.layout.act_e6_panel));
            hashMap.put("layout/act_e6_panel_setting_0", Integer.valueOf(R.layout.act_e6_panel_setting));
            hashMap.put("layout/act_edit_advanced_mode_0", Integer.valueOf(R.layout.act_edit_advanced_mode));
            hashMap.put("layout/act_edit_color_diy_mode_0", Integer.valueOf(R.layout.act_edit_color_diy_mode));
            hashMap.put("layout/act_edit_general_mode_0", Integer.valueOf(R.layout.act_edit_general_mode));
            hashMap.put("layout/act_edit_instruct_cmd_0", Integer.valueOf(R.layout.act_edit_instruct_cmd));
            hashMap.put("layout/act_edit_key_name_0", Integer.valueOf(R.layout.act_edit_key_name));
            hashMap.put("layout/act_edit_name_0", Integer.valueOf(R.layout.act_edit_name));
            hashMap.put("layout/act_edit_number_0", Integer.valueOf(R.layout.act_edit_number));
            hashMap.put("layout/act_engineering_mode_0", Integer.valueOf(R.layout.act_engineering_mode));
            hashMap.put("layout/act_engineering_mode_on_off_0", Integer.valueOf(R.layout.act_engineering_mode_on_off));
            hashMap.put("layout/act_environment_log_0", Integer.valueOf(R.layout.act_environment_log));
            hashMap.put("layout/act_eur_panel_0", Integer.valueOf(R.layout.act_eur_panel));
            hashMap.put("layout/act_eur_panel_eb6_0", Integer.valueOf(R.layout.act_eur_panel_eb6));
            hashMap.put("layout/act_eur_panel_group_setting_0", Integer.valueOf(R.layout.act_eur_panel_group_setting));
            hashMap.put("layout/act_eur_panel_setting_0", Integer.valueOf(R.layout.act_eur_panel_setting));
            hashMap.put("layout/act_fan_0", Integer.valueOf(R.layout.act_fan));
            hashMap.put("layout/act_feed_back_0", Integer.valueOf(R.layout.act_feed_back));
            hashMap.put("layout/act_floor_heat_0", Integer.valueOf(R.layout.act_floor_heat));
            hashMap.put("layout/act_floor_manage_0", Integer.valueOf(R.layout.act_floor_manage));
            hashMap.put("layout/act_fresh_air_0", Integer.valueOf(R.layout.act_fresh_air));
            hashMap.put("layout/act_g4_max_key_set_0", Integer.valueOf(R.layout.act_g4_max_key_set));
            hashMap.put("layout/act_get_permission_0", Integer.valueOf(R.layout.act_get_permission));
            hashMap.put("layout/act_gq_pro_0", Integer.valueOf(R.layout.act_gq_pro));
            hashMap.put("layout/act_gq_pro_theme_0", Integer.valueOf(R.layout.act_gq_pro_theme));
            hashMap.put("layout/act_gqx_0", Integer.valueOf(R.layout.act_gqx));
            hashMap.put("layout/act_gqx_setting_0", Integer.valueOf(R.layout.act_gqx_setting));
            hashMap.put("layout/act_group_curtain_setting_0", Integer.valueOf(R.layout.act_group_curtain_setting));
            hashMap.put("layout/act_group_manage_0", Integer.valueOf(R.layout.act_group_manage));
            hashMap.put("layout/act_group_setting_0", Integer.valueOf(R.layout.act_group_setting));
            hashMap.put("layout/act_home_0", Integer.valueOf(R.layout.act_home));
            hashMap.put("layout/act_home_kit_0", Integer.valueOf(R.layout.act_home_kit));
            hashMap.put("layout/act_home_kit_old_0", Integer.valueOf(R.layout.act_home_kit_old));
            hashMap.put("layout/act_home_kit_setting_0", Integer.valueOf(R.layout.act_home_kit_setting));
            hashMap.put("layout/act_home_manage_0", Integer.valueOf(R.layout.act_home_manage));
            hashMap.put("layout/act_home_position_0", Integer.valueOf(R.layout.act_home_position));
            hashMap.put("layout/act_home_qr_code_0", Integer.valueOf(R.layout.act_home_qr_code));
            hashMap.put("layout/act_home_setting_0", Integer.valueOf(R.layout.act_home_setting));
            hashMap.put("layout/act_home_transfer_setting_0", Integer.valueOf(R.layout.act_home_transfer_setting));
            hashMap.put("layout/act_hsd_sensor_0", Integer.valueOf(R.layout.act_hsd_sensor));
            hashMap.put("layout/act_hsd_sensor_setting_0", Integer.valueOf(R.layout.act_hsd_sensor_setting));
            hashMap.put("layout/act_import_scene_all_0", Integer.valueOf(R.layout.act_import_scene_all));
            hashMap.put("layout/act_instruct_setting_0", Integer.valueOf(R.layout.act_instruct_setting));
            hashMap.put("layout/act_intelligence_0", Integer.valueOf(R.layout.act_intelligence));
            hashMap.put("layout/act_intercom_0", Integer.valueOf(R.layout.act_intercom));
            hashMap.put("layout/act_intercom_login_0", Integer.valueOf(R.layout.act_intercom_login));
            hashMap.put("layout/act_intercom_record_0", Integer.valueOf(R.layout.act_intercom_record));
            hashMap.put("layout/act_intercom_select_community_0", Integer.valueOf(R.layout.act_intercom_select_community));
            hashMap.put("layout/act_intercom_set_face_0", Integer.valueOf(R.layout.act_intercom_set_face));
            hashMap.put("layout/act_intercom_set_open_key_0", Integer.valueOf(R.layout.act_intercom_set_open_key));
            hashMap.put("layout/act_intercom_setting_0", Integer.valueOf(R.layout.act_intercom_setting));
            hashMap.put("layout/act_intercom_temp_key_0", Integer.valueOf(R.layout.act_intercom_temp_key));
            hashMap.put("layout/act_intercom_tips_0", Integer.valueOf(R.layout.act_intercom_tips));
            hashMap.put("layout/act_ir_diy_0", Integer.valueOf(R.layout.act_ir_diy));
            hashMap.put("layout/act_ir_setting_0", Integer.valueOf(R.layout.act_ir_setting));
            hashMap.put("layout/act_kbs_0", Integer.valueOf(R.layout.act_kbs));
            hashMap.put("layout/act_kbs_group_setting_0", Integer.valueOf(R.layout.act_kbs_group_setting));
            hashMap.put("layout/act_kbs_setting_0", Integer.valueOf(R.layout.act_kbs_setting));
            hashMap.put("layout/act_key_switch_0", Integer.valueOf(R.layout.act_key_switch));
            hashMap.put("layout/act_knob_panel_0", Integer.valueOf(R.layout.act_knob_panel));
            hashMap.put("layout/act_knob_panel_setting_0", Integer.valueOf(R.layout.act_knob_panel_setting));
            hashMap.put("layout/act_knob_screen_panel_0", Integer.valueOf(R.layout.act_knob_screen_panel));
            hashMap.put("layout/act_light_group_sub_item_control_0", Integer.valueOf(R.layout.act_light_group_sub_item_control));
            hashMap.put("layout/act_light_on_off_time_0", Integer.valueOf(R.layout.act_light_on_off_time));
            hashMap.put("layout/act_light_plan_batch_set_0", Integer.valueOf(R.layout.act_light_plan_batch_set));
            hashMap.put("layout/act_light_plan_detail_0", Integer.valueOf(R.layout.act_light_plan_detail));
            hashMap.put("layout/act_light_plan_list_0", Integer.valueOf(R.layout.act_light_plan_list));
            hashMap.put("layout/act_light_setting_0", Integer.valueOf(R.layout.act_light_setting));
            hashMap.put("layout/act_light_setting_new_0", Integer.valueOf(R.layout.act_light_setting_new));
            hashMap.put("layout/act_local_device_log_0", Integer.valueOf(R.layout.act_local_device_log));
            hashMap.put("layout/act_location_permission_description_0", Integer.valueOf(R.layout.act_location_permission_description));
            hashMap.put("layout/act_login_0", Integer.valueOf(R.layout.act_login));
            hashMap.put("layout/act_map_0", Integer.valueOf(R.layout.act_map));
            hashMap.put("layout/act_matter_list_0", Integer.valueOf(R.layout.act_matter_list));
            hashMap.put("layout/act_matter_platform_list_0", Integer.valueOf(R.layout.act_matter_platform_list));
            hashMap.put("layout/act_matter_sub_list_0", Integer.valueOf(R.layout.act_matter_sub_list));
            hashMap.put("layout/act_mesh_gateway_0", Integer.valueOf(R.layout.act_mesh_gateway));
            hashMap.put("layout/act_mesh_gateway_light_setting_0", Integer.valueOf(R.layout.act_mesh_gateway_light_setting));
            hashMap.put("layout/act_mesh_gateway_setting_0", Integer.valueOf(R.layout.act_mesh_gateway_setting));
            hashMap.put("layout/act_mesh_near_device_0", Integer.valueOf(R.layout.act_mesh_near_device));
            hashMap.put("layout/act_mesh_scan_0", Integer.valueOf(R.layout.act_mesh_scan));
            hashMap.put("layout/act_mesh_scan2_0", Integer.valueOf(R.layout.act_mesh_scan2));
            hashMap.put("layout/act_mesh_scan_1_0", Integer.valueOf(R.layout.act_mesh_scan_1));
            hashMap.put("layout/act_mesh_scan_proxy_0", Integer.valueOf(R.layout.act_mesh_scan_proxy));
            hashMap.put("layout/act_message_center_0", Integer.valueOf(R.layout.act_message_center));
            hashMap.put("layout/act_mode_0", Integer.valueOf(R.layout.act_mode));
            hashMap.put("layout/act_module_switch_0", Integer.valueOf(R.layout.act_module_switch));
            hashMap.put("layout/act_monitor_0", Integer.valueOf(R.layout.act_monitor));
            hashMap.put("layout/act_motor_0", Integer.valueOf(R.layout.act_motor));
            hashMap.put("layout/act_motor_pair_0", Integer.valueOf(R.layout.act_motor_pair));
            hashMap.put("layout/act_music_0", Integer.valueOf(R.layout.act_music));
            hashMap.put("layout/act_music_list_0", Integer.valueOf(R.layout.act_music_list));
            hashMap.put("layout/act_net_config_0", Integer.valueOf(R.layout.act_net_config));
            hashMap.put("layout/act_net_connect_0", Integer.valueOf(R.layout.act_net_connect));
            hashMap.put("layout/act_new_as_panel_0", Integer.valueOf(R.layout.act_new_as_panel));
            hashMap.put("layout/act_new_screen_panel_0", Integer.valueOf(R.layout.act_new_screen_panel));
            hashMap.put("layout/act_new_smart_panel_0", Integer.valueOf(R.layout.act_new_smart_panel));
            hashMap.put("layout/act_nfc_restore_0", Integer.valueOf(R.layout.act_nfc_restore));
            hashMap.put("layout/act_open_door_log_detail_0", Integer.valueOf(R.layout.act_open_door_log_detail));
            hashMap.put("layout/act_page_screen_panel_0", Integer.valueOf(R.layout.act_page_screen_panel));
            hashMap.put("layout/act_panel_color_set_0", Integer.valueOf(R.layout.act_panel_color_set));
            hashMap.put("layout/act_panel_group_setting_0", Integer.valueOf(R.layout.act_panel_group_setting));
            hashMap.put("layout/act_panel_night_getup_0", Integer.valueOf(R.layout.act_panel_night_getup));
            hashMap.put("layout/act_panel_switch_position_set_0", Integer.valueOf(R.layout.act_panel_switch_position_set));
            hashMap.put("layout/act_place_user_setting_0", Integer.valueOf(R.layout.act_place_user_setting));
            hashMap.put("layout/act_playlist_manage_0", Integer.valueOf(R.layout.act_playlist_manage));
            hashMap.put("layout/act_product_introduction_0", Integer.valueOf(R.layout.act_product_introduction));
            hashMap.put("layout/act_product_introduction_1_0", Integer.valueOf(R.layout.act_product_introduction_1));
            hashMap.put("layout/act_pub_list_0", Integer.valueOf(R.layout.act_pub_list));
            hashMap.put("layout/act_qr_code_scan_0", Integer.valueOf(R.layout.act_qr_code_scan));
            hashMap.put("layout/act_qr_code_scan_result_0", Integer.valueOf(R.layout.act_qr_code_scan_result));
            hashMap.put("layout/act_r8_setting_0", Integer.valueOf(R.layout.act_r8_setting));
            hashMap.put("layout/act_rc4s_0", Integer.valueOf(R.layout.act_rc4s));
            hashMap.put("layout/act_record_list_0", Integer.valueOf(R.layout.act_record_list));
            hashMap.put("layout/act_register_0", Integer.valueOf(R.layout.act_register));
            hashMap.put("layout/act_remote_battery_0", Integer.valueOf(R.layout.act_remote_battery));
            hashMap.put("layout/act_replace_0", Integer.valueOf(R.layout.act_replace));
            hashMap.put("layout/act_rgbwaf_select_0", Integer.valueOf(R.layout.act_rgbwaf_select));
            hashMap.put("layout/act_room_manage_0", Integer.valueOf(R.layout.act_room_manage));
            hashMap.put("layout/act_rs8_0", Integer.valueOf(R.layout.act_rs8));
            hashMap.put("layout/act_rs8_add_sub_device_0", Integer.valueOf(R.layout.act_rs8_add_sub_device));
            hashMap.put("layout/act_rs8_address_write_0", Integer.valueOf(R.layout.act_rs8_address_write));
            hashMap.put("layout/act_rs8_curtain_0", Integer.valueOf(R.layout.act_rs8_curtain));
            hashMap.put("layout/act_rs8_sub_device_setting_0", Integer.valueOf(R.layout.act_rs8_sub_device_setting));
            hashMap.put("layout/act_save_qr_code_0", Integer.valueOf(R.layout.act_save_qr_code));
            hashMap.put("layout/act_scene_panel_0", Integer.valueOf(R.layout.act_scene_panel));
            hashMap.put("layout/act_screen_panel_0", Integer.valueOf(R.layout.act_screen_panel));
            hashMap.put("layout/act_screen_panel_elderly_mode_0", Integer.valueOf(R.layout.act_screen_panel_elderly_mode));
            hashMap.put("layout/act_search_automation_0", Integer.valueOf(R.layout.act_search_automation));
            hashMap.put("layout/act_search_device_0", Integer.valueOf(R.layout.act_search_device));
            hashMap.put("layout/act_search_google_position_0", Integer.valueOf(R.layout.act_search_google_position));
            hashMap.put("layout/act_search_music_0", Integer.valueOf(R.layout.act_search_music));
            hashMap.put("layout/act_search_position_0", Integer.valueOf(R.layout.act_search_position));
            hashMap.put("layout/act_search_scene_0", Integer.valueOf(R.layout.act_search_scene));
            hashMap.put("layout/act_select_0", Integer.valueOf(R.layout.act_select));
            hashMap.put("layout/act_select2_0", Integer.valueOf(R.layout.act_select2));
            hashMap.put("layout/act_select3_0", Integer.valueOf(R.layout.act_select3));
            hashMap.put("layout/act_select4_0", Integer.valueOf(R.layout.act_select4));
            hashMap.put("layout/act_select_ble_curtain_action_0", Integer.valueOf(R.layout.act_select_ble_curtain_action));
            hashMap.put("layout/act_select_brand_0", Integer.valueOf(R.layout.act_select_brand));
            hashMap.put("layout/act_select_color_0", Integer.valueOf(R.layout.act_select_color));
            hashMap.put("layout/act_select_color_cc_0", Integer.valueOf(R.layout.act_select_color_cc));
            hashMap.put("layout/act_select_condition_device_0", Integer.valueOf(R.layout.act_select_condition_device));
            hashMap.put("layout/act_select_country_0", Integer.valueOf(R.layout.act_select_country));
            hashMap.put("layout/act_select_dali_add_0", Integer.valueOf(R.layout.act_select_dali_add));
            hashMap.put("layout/act_select_dali_color_0", Integer.valueOf(R.layout.act_select_dali_color));
            hashMap.put("layout/act_select_divide_0", Integer.valueOf(R.layout.act_select_divide));
            hashMap.put("layout/act_select_effect_period_0", Integer.valueOf(R.layout.act_select_effect_period));
            hashMap.put("layout/act_select_home_member_0", Integer.valueOf(R.layout.act_select_home_member));
            hashMap.put("layout/act_select_light_0", Integer.valueOf(R.layout.act_select_light));
            hashMap.put("layout/act_select_list_0", Integer.valueOf(R.layout.act_select_list));
            hashMap.put("layout/act_select_multi_type_0", Integer.valueOf(R.layout.act_select_multi_type));
            hashMap.put("layout/act_select_operators_0", Integer.valueOf(R.layout.act_select_operators));
            hashMap.put("layout/act_select_scene_0", Integer.valueOf(R.layout.act_select_scene));
            hashMap.put("layout/act_select_scene_all_0", Integer.valueOf(R.layout.act_select_scene_all));
            hashMap.put("layout/act_select_sonos_action_0", Integer.valueOf(R.layout.act_select_sonos_action));
            hashMap.put("layout/act_select_spi_for_action_0", Integer.valueOf(R.layout.act_select_spi_for_action));
            hashMap.put("layout/act_select_super_panel_music_0", Integer.valueOf(R.layout.act_select_super_panel_music));
            hashMap.put("layout/act_select_temperature_weather_0", Integer.valueOf(R.layout.act_select_temperature_weather));
            hashMap.put("layout/act_select_theme_mode_0", Integer.valueOf(R.layout.act_select_theme_mode));
            hashMap.put("layout/act_select_time_0", Integer.valueOf(R.layout.act_select_time));
            hashMap.put("layout/act_select_voice_speak_0", Integer.valueOf(R.layout.act_select_voice_speak));
            hashMap.put("layout/act_select_weather_0", Integer.valueOf(R.layout.act_select_weather));
            hashMap.put("layout/act_sense_setting_0", Integer.valueOf(R.layout.act_sense_setting));
            hashMap.put("layout/act_sensor_noboby_test_0", Integer.valueOf(R.layout.act_sensor_noboby_test));
            hashMap.put("layout/act_sensor_setting_0", Integer.valueOf(R.layout.act_sensor_setting));
            hashMap.put("layout/act_serial_setting_0", Integer.valueOf(R.layout.act_serial_setting));
            hashMap.put("layout/act_set_light_channel_0", Integer.valueOf(R.layout.act_set_light_channel));
            hashMap.put("layout/act_set_screen_display_0", Integer.valueOf(R.layout.act_set_screen_display));
            hashMap.put("layout/act_share_duv_list_0", Integer.valueOf(R.layout.act_share_duv_list));
            hashMap.put("layout/act_smart_panel_0", Integer.valueOf(R.layout.act_smart_panel));
            hashMap.put("layout/act_smart_panel_child_setting_0", Integer.valueOf(R.layout.act_smart_panel_child_setting));
            hashMap.put("layout/act_smart_panel_group_child_setting_0", Integer.valueOf(R.layout.act_smart_panel_group_child_setting));
            hashMap.put("layout/act_smart_panel_key_set_0", Integer.valueOf(R.layout.act_smart_panel_key_set));
            hashMap.put("layout/act_smart_panel_setting_0", Integer.valueOf(R.layout.act_smart_panel_setting));
            hashMap.put("layout/act_smart_panel_switch_setting_0", Integer.valueOf(R.layout.act_smart_panel_switch_setting));
            hashMap.put("layout/act_smart_panel_theme_0", Integer.valueOf(R.layout.act_smart_panel_theme));
            hashMap.put("layout/act_smart_speaker_0", Integer.valueOf(R.layout.act_smart_speaker));
            hashMap.put("layout/act_smart_speaker_detail_0", Integer.valueOf(R.layout.act_smart_speaker_detail));
            hashMap.put("layout/act_songs_0", Integer.valueOf(R.layout.act_songs));
            hashMap.put("layout/act_sonos_music_detail_0", Integer.valueOf(R.layout.act_sonos_music_detail));
            hashMap.put("layout/act_sonos_setting_default_0", Integer.valueOf(R.layout.act_sonos_setting_default));
            hashMap.put("layout/act_sonos_web_view_0", Integer.valueOf(R.layout.act_sonos_web_view));
            hashMap.put("layout/act_sort_0", Integer.valueOf(R.layout.act_sort));
            hashMap.put("layout/act_sort_local_music_0", Integer.valueOf(R.layout.act_sort_local_music));
            hashMap.put("layout/act_sp485_list_0", Integer.valueOf(R.layout.act_sp485_list));
            hashMap.put("layout/act_spi_controller_0", Integer.valueOf(R.layout.act_spi_controller));
            hashMap.put("layout/act_spi_controller_setting_0", Integer.valueOf(R.layout.act_spi_controller_setting));
            hashMap.put("layout/act_spi_edit_play_list_0", Integer.valueOf(R.layout.act_spi_edit_play_list));
            hashMap.put("layout/act_spi_light_setting_0", Integer.valueOf(R.layout.act_spi_light_setting));
            hashMap.put("layout/act_splash_0", Integer.valueOf(R.layout.act_splash));
            hashMap.put("layout/act_steps_introduction_0", Integer.valueOf(R.layout.act_steps_introduction));
            hashMap.put("layout/act_sub_device_setting_default_0", Integer.valueOf(R.layout.act_sub_device_setting_default));
            hashMap.put("layout/act_super_panel_0", Integer.valueOf(R.layout.act_super_panel));
            hashMap.put("layout/act_super_panel_album_0", Integer.valueOf(R.layout.act_super_panel_album));
            hashMap.put("layout/act_super_panel_clip_photo_0", Integer.valueOf(R.layout.act_super_panel_clip_photo));
            hashMap.put("layout/act_super_panel_continous_talk_0", Integer.valueOf(R.layout.act_super_panel_continous_talk));
            hashMap.put("layout/act_super_panel_ir_remote_control_0", Integer.valueOf(R.layout.act_super_panel_ir_remote_control));
            hashMap.put("layout/act_super_panel_key_set_0", Integer.valueOf(R.layout.act_super_panel_key_set));
            hashMap.put("layout/act_super_panel_key_set_6s_0", Integer.valueOf(R.layout.act_super_panel_key_set_6s));
            hashMap.put("layout/act_super_panel_preview_photo_0", Integer.valueOf(R.layout.act_super_panel_preview_photo));
            hashMap.put("layout/act_super_panel_select_photo_0", Integer.valueOf(R.layout.act_super_panel_select_photo));
            hashMap.put("layout/act_super_panel_setting_0", Integer.valueOf(R.layout.act_super_panel_setting));
            hashMap.put("layout/act_super_panel_voice_control_range_0", Integer.valueOf(R.layout.act_super_panel_voice_control_range));
            hashMap.put("layout/act_super_panel_voice_control_range_role_0", Integer.valueOf(R.layout.act_super_panel_voice_control_range_role));
            hashMap.put("layout/act_super_panel_voice_talk_0", Integer.valueOf(R.layout.act_super_panel_voice_talk));
            hashMap.put("layout/act_te_panel_0", Integer.valueOf(R.layout.act_te_panel));
            hashMap.put("layout/act_test_mode_main_0", Integer.valueOf(R.layout.act_test_mode_main));
            hashMap.put("layout/act_test_prepare_0", Integer.valueOf(R.layout.act_test_prepare));
            hashMap.put("layout/act_test_step_0", Integer.valueOf(R.layout.act_test_step));
            hashMap.put("layout/act_theme_download_0", Integer.valueOf(R.layout.act_theme_download));
            hashMap.put("layout/act_transfer_music_0", Integer.valueOf(R.layout.act_transfer_music));
            hashMap.put("layout/act_trig_0", Integer.valueOf(R.layout.act_trig));
            hashMap.put("layout/act_trig_curtain_0", Integer.valueOf(R.layout.act_trig_curtain));
            hashMap.put("layout/act_trig_curtain_channel_set_0", Integer.valueOf(R.layout.act_trig_curtain_channel_set));
            hashMap.put("layout/act_trig_curtain_group_setting_0", Integer.valueOf(R.layout.act_trig_curtain_group_setting));
            hashMap.put("layout/act_trig_curtain_open_dir_set_0", Integer.valueOf(R.layout.act_trig_curtain_open_dir_set));
            hashMap.put("layout/act_trig_to_ble_0", Integer.valueOf(R.layout.act_trig_to_ble));
            hashMap.put("layout/act_tv_0", Integer.valueOf(R.layout.act_tv));
            hashMap.put("layout/act_upgrade_0", Integer.valueOf(R.layout.act_upgrade));
            hashMap.put("layout/act_user_info_0", Integer.valueOf(R.layout.act_user_info));
            hashMap.put("layout/act_voice_call_bind_0", Integer.valueOf(R.layout.act_voice_call_bind));
            hashMap.put("layout/act_voice_call_grop_list_0", Integer.valueOf(R.layout.act_voice_call_grop_list));
            hashMap.put("layout/act_voice_call_group_add_0", Integer.valueOf(R.layout.act_voice_call_group_add));
            hashMap.put("layout/act_voice_call_setting_0", Integer.valueOf(R.layout.act_voice_call_setting));
            hashMap.put("layout/act_voice_call_whitelist_add_0", Integer.valueOf(R.layout.act_voice_call_whitelist_add));
            hashMap.put("layout/act_wave_sensor_0", Integer.valueOf(R.layout.act_wave_sensor));
            hashMap.put("layout/act_wave_sensor_effect_period_0", Integer.valueOf(R.layout.act_wave_sensor_effect_period));
            hashMap.put("layout/act_wave_sensor_group_setting_0", Integer.valueOf(R.layout.act_wave_sensor_group_setting));
            hashMap.put("layout/act_wave_sensor_pro_0", Integer.valueOf(R.layout.act_wave_sensor_pro));
            hashMap.put("layout/act_wave_sensor_setting_0", Integer.valueOf(R.layout.act_wave_sensor_setting));
            hashMap.put("layout/act_web_view_0", Integer.valueOf(R.layout.act_web_view));
            hashMap.put("layout/act_welcome_0", Integer.valueOf(R.layout.act_welcome));
            hashMap.put("layout/act_xy_select_0", Integer.valueOf(R.layout.act_xy_select));
            hashMap.put("layout/activity_act_mesh_add_all_device_0", Integer.valueOf(R.layout.activity_act_mesh_add_all_device));
            hashMap.put("layout/dialog_512_channel_setting_0", Integer.valueOf(R.layout.dialog_512_channel_setting));
            hashMap.put("layout/dialog_ac_quick_0", Integer.valueOf(R.layout.dialog_ac_quick));
            hashMap.put("layout/dialog_add_cg485_category_0", Integer.valueOf(R.layout.dialog_add_cg485_category));
            hashMap.put("layout/dialog_add_cg485_device_0", Integer.valueOf(R.layout.dialog_add_cg485_device));
            hashMap.put("layout/dialog_add_color_point_0", Integer.valueOf(R.layout.dialog_add_color_point));
            hashMap.put("layout/dialog_add_group_0", Integer.valueOf(R.layout.dialog_add_group));
            hashMap.put("layout/dialog_add_scene_0", Integer.valueOf(R.layout.dialog_add_scene));
            hashMap.put("layout/dialog_button_tip_0", Integer.valueOf(R.layout.dialog_button_tip));
            hashMap.put("layout/dialog_calendar_0", Integer.valueOf(R.layout.dialog_calendar));
            hashMap.put("layout/dialog_call_invite_0", Integer.valueOf(R.layout.dialog_call_invite));
            hashMap.put("layout/dialog_center_dali_modify_param_0", Integer.valueOf(R.layout.dialog_center_dali_modify_param));
            hashMap.put("layout/dialog_center_select_list_0", Integer.valueOf(R.layout.dialog_center_select_list));
            hashMap.put("layout/dialog_center_tip_0", Integer.valueOf(R.layout.dialog_center_tip));
            hashMap.put("layout/dialog_city_picker_0", Integer.valueOf(R.layout.dialog_city_picker));
            hashMap.put("layout/dialog_color_brt_control_0", Integer.valueOf(R.layout.dialog_color_brt_control));
            hashMap.put("layout/dialog_dali_detect_0", Integer.valueOf(R.layout.dialog_dali_detect));
            hashMap.put("layout/dialog_dali_detect_tip_0", Integer.valueOf(R.layout.dialog_dali_detect_tip));
            hashMap.put("layout/dialog_dali_load_data_0", Integer.valueOf(R.layout.dialog_dali_load_data));
            hashMap.put("layout/dialog_del_fail_0", Integer.valueOf(R.layout.dialog_del_fail));
            hashMap.put("layout/dialog_device_icon_selector_0", Integer.valueOf(R.layout.dialog_device_icon_selector));
            hashMap.put("layout/dialog_dim_depth_selector_0", Integer.valueOf(R.layout.dialog_dim_depth_selector));
            hashMap.put("layout/dialog_e6_tip_0", Integer.valueOf(R.layout.dialog_e6_tip));
            hashMap.put("layout/dialog_edit_0", Integer.valueOf(R.layout.dialog_edit));
            hashMap.put("layout/dialog_edit_copy_0", Integer.valueOf(R.layout.dialog_edit_copy));
            hashMap.put("layout/dialog_edit_device_0", Integer.valueOf(R.layout.dialog_edit_device));
            hashMap.put("layout/dialog_eur_function_0", Integer.valueOf(R.layout.dialog_eur_function));
            hashMap.put("layout/dialog_eur_function_and_rgb_0", Integer.valueOf(R.layout.dialog_eur_function_and_rgb));
            hashMap.put("layout/dialog_execute_delay_time_selector_0", Integer.valueOf(R.layout.dialog_execute_delay_time_selector));
            hashMap.put("layout/dialog_execute_time_selector_0", Integer.valueOf(R.layout.dialog_execute_time_selector));
            hashMap.put("layout/dialog_floor_heat_quick_0", Integer.valueOf(R.layout.dialog_floor_heat_quick));
            hashMap.put("layout/dialog_frequency_interval_0", Integer.valueOf(R.layout.dialog_frequency_interval));
            hashMap.put("layout/dialog_fresh_air_quick_0", Integer.valueOf(R.layout.dialog_fresh_air_quick));
            hashMap.put("layout/dialog_gradual_time_0", Integer.valueOf(R.layout.dialog_gradual_time));
            hashMap.put("layout/dialog_image_tip_0", Integer.valueOf(R.layout.dialog_image_tip));
            hashMap.put("layout/dialog_intercom_time_picker_0", Integer.valueOf(R.layout.dialog_intercom_time_picker));
            hashMap.put("layout/dialog_ir_fun_0", Integer.valueOf(R.layout.dialog_ir_fun));
            hashMap.put("layout/dialog_ir_learn_0", Integer.valueOf(R.layout.dialog_ir_learn));
            hashMap.put("layout/dialog_ir_quick_0", Integer.valueOf(R.layout.dialog_ir_quick));
            hashMap.put("layout/dialog_learn_instruct_0", Integer.valueOf(R.layout.dialog_learn_instruct));
            hashMap.put("layout/dialog_left_title_select_list_0", Integer.valueOf(R.layout.dialog_left_title_select_list));
            hashMap.put("layout/dialog_light_quick_0", Integer.valueOf(R.layout.dialog_light_quick));
            hashMap.put("layout/dialog_list_0", Integer.valueOf(R.layout.dialog_list));
            hashMap.put("layout/dialog_loading_0", Integer.valueOf(R.layout.dialog_loading));
            hashMap.put("layout/dialog_loading_progress_0", Integer.valueOf(R.layout.dialog_loading_progress));
            hashMap.put("layout/dialog_loading_success_0", Integer.valueOf(R.layout.dialog_loading_success));
            hashMap.put("layout/dialog_log_calendar_0", Integer.valueOf(R.layout.dialog_log_calendar));
            hashMap.put("layout/dialog_matter_qrcode_0", Integer.valueOf(R.layout.dialog_matter_qrcode));
            hashMap.put("layout/dialog_ms_time_selector_0", Integer.valueOf(R.layout.dialog_ms_time_selector));
            hashMap.put("layout/dialog_music_list_0", Integer.valueOf(R.layout.dialog_music_list));
            hashMap.put("layout/dialog_music_time_0", Integer.valueOf(R.layout.dialog_music_time));
            hashMap.put("layout/dialog_music_vip_tip_0", Integer.valueOf(R.layout.dialog_music_vip_tip));
            hashMap.put("layout/dialog_music_volume_0", Integer.valueOf(R.layout.dialog_music_volume));
            hashMap.put("layout/dialog_number_edit_0", Integer.valueOf(R.layout.dialog_number_edit));
            hashMap.put("layout/dialog_pair_0", Integer.valueOf(R.layout.dialog_pair));
            hashMap.put("layout/dialog_panel_brt_0", Integer.valueOf(R.layout.dialog_panel_brt));
            hashMap.put("layout/dialog_power_state_batch_0", Integer.valueOf(R.layout.dialog_power_state_batch));
            hashMap.put("layout/dialog_power_state_selector_0", Integer.valueOf(R.layout.dialog_power_state_selector));
            hashMap.put("layout/dialog_progress_0", Integer.valueOf(R.layout.dialog_progress));
            hashMap.put("layout/dialog_rc4s_tip_0", Integer.valueOf(R.layout.dialog_rc4s_tip));
            hashMap.put("layout/dialog_reg_mode_0", Integer.valueOf(R.layout.dialog_reg_mode));
            hashMap.put("layout/dialog_result_0", Integer.valueOf(R.layout.dialog_result));
            hashMap.put("layout/dialog_rgb_function_tip_0", Integer.valueOf(R.layout.dialog_rgb_function_tip));
            hashMap.put("layout/dialog_room_picker_0", Integer.valueOf(R.layout.dialog_room_picker));
            hashMap.put("layout/dialog_room_selector_0", Integer.valueOf(R.layout.dialog_room_selector));
            hashMap.put("layout/dialog_scan_nfc_0", Integer.valueOf(R.layout.dialog_scan_nfc));
            hashMap.put("layout/dialog_select_brt_0", Integer.valueOf(R.layout.dialog_select_brt));
            hashMap.put("layout/dialog_select_cgd_action_0", Integer.valueOf(R.layout.dialog_select_cgd_action));
            hashMap.put("layout/dialog_select_color_0", Integer.valueOf(R.layout.dialog_select_color));
            hashMap.put("layout/dialog_select_ct_0", Integer.valueOf(R.layout.dialog_select_ct));
            hashMap.put("layout/dialog_select_dim_curve_0", Integer.valueOf(R.layout.dialog_select_dim_curve));
            hashMap.put("layout/dialog_select_dim_fade_time_0", Integer.valueOf(R.layout.dialog_select_dim_fade_time));
            hashMap.put("layout/dialog_select_dim_range_0", Integer.valueOf(R.layout.dialog_select_dim_range));
            hashMap.put("layout/dialog_select_gq_theme_0", Integer.valueOf(R.layout.dialog_select_gq_theme));
            hashMap.put("layout/dialog_select_item_0", Integer.valueOf(R.layout.dialog_select_item));
            hashMap.put("layout/dialog_select_k_duv_0", Integer.valueOf(R.layout.dialog_select_k_duv));
            hashMap.put("layout/dialog_select_list_0", Integer.valueOf(R.layout.dialog_select_list));
            hashMap.put("layout/dialog_select_list_and_pic_0", Integer.valueOf(R.layout.dialog_select_list_and_pic));
            hashMap.put("layout/dialog_select_lux_0", Integer.valueOf(R.layout.dialog_select_lux));
            hashMap.put("layout/dialog_select_scene_0", Integer.valueOf(R.layout.dialog_select_scene));
            hashMap.put("layout/dialog_select_volume_0", Integer.valueOf(R.layout.dialog_select_volume));
            hashMap.put("layout/dialog_sensing_distance_setting_0", Integer.valueOf(R.layout.dialog_sensing_distance_setting));
            hashMap.put("layout/dialog_set_ble_type_0", Integer.valueOf(R.layout.dialog_set_ble_type));
            hashMap.put("layout/dialog_set_screen_display_0", Integer.valueOf(R.layout.dialog_set_screen_display));
            hashMap.put("layout/dialog_single_picker_0", Integer.valueOf(R.layout.dialog_single_picker));
            hashMap.put("layout/dialog_sp_quick_0", Integer.valueOf(R.layout.dialog_sp_quick));
            hashMap.put("layout/dialog_switch_pattern_selector_0", Integer.valueOf(R.layout.dialog_switch_pattern_selector));
            hashMap.put("layout/dialog_te_panel_0", Integer.valueOf(R.layout.dialog_te_panel));
            hashMap.put("layout/dialog_time_interval_selector_0", Integer.valueOf(R.layout.dialog_time_interval_selector));
            hashMap.put("layout/dialog_time_picker_0", Integer.valueOf(R.layout.dialog_time_picker));
            hashMap.put("layout/dialog_time_selector_0", Integer.valueOf(R.layout.dialog_time_selector));
            hashMap.put("layout/dialog_time_selector_with_limit_0", Integer.valueOf(R.layout.dialog_time_selector_with_limit));
            hashMap.put("layout/dialog_time_selector_with_ms_0", Integer.valueOf(R.layout.dialog_time_selector_with_ms));
            hashMap.put("layout/dialog_timing_set_0", Integer.valueOf(R.layout.dialog_timing_set));
            hashMap.put("layout/dialog_tip_0", Integer.valueOf(R.layout.dialog_tip));
            hashMap.put("layout/dialog_wheel_select_double_list_0", Integer.valueOf(R.layout.dialog_wheel_select_double_list));
            hashMap.put("layout/dialog_wheel_select_list_0", Integer.valueOf(R.layout.dialog_wheel_select_list));
            hashMap.put("layout/dialog_white_balance_0", Integer.valueOf(R.layout.dialog_white_balance));
            hashMap.put("layout/dialog_wy_0", Integer.valueOf(R.layout.dialog_wy));
            hashMap.put("layout/footer_add_0", Integer.valueOf(R.layout.footer_add));
            hashMap.put("layout/footer_super_panel_key_set_0", Integer.valueOf(R.layout.footer_super_panel_key_set));
            hashMap.put("layout/ft_ac_0", Integer.valueOf(R.layout.ft_ac));
            hashMap.put("layout/ft_ac_dialog_0", Integer.valueOf(R.layout.ft_ac_dialog));
            hashMap.put("layout/ft_access_control_0", Integer.valueOf(R.layout.ft_access_control));
            hashMap.put("layout/ft_advanced_mode_0", Integer.valueOf(R.layout.ft_advanced_mode));
            hashMap.put("layout/ft_air_0", Integer.valueOf(R.layout.ft_air));
            hashMap.put("layout/ft_air_dialog_0", Integer.valueOf(R.layout.ft_air_dialog));
            hashMap.put("layout/ft_automation_0", Integer.valueOf(R.layout.ft_automation));
            hashMap.put("layout/ft_bind_mail_0", Integer.valueOf(R.layout.ft_bind_mail));
            hashMap.put("layout/ft_bind_phone_0", Integer.valueOf(R.layout.ft_bind_phone));
            hashMap.put("layout/ft_camera_ptz_0", Integer.valueOf(R.layout.ft_camera_ptz));
            hashMap.put("layout/ft_camera_record_0", Integer.valueOf(R.layout.ft_camera_record));
            hashMap.put("layout/ft_camera_talk_0", Integer.valueOf(R.layout.ft_camera_talk));
            hashMap.put("layout/ft_clip_photo_0", Integer.valueOf(R.layout.ft_clip_photo));
            hashMap.put("layout/ft_cloud_scene_0", Integer.valueOf(R.layout.ft_cloud_scene));
            hashMap.put("layout/ft_color_cct_0", Integer.valueOf(R.layout.ft_color_cct));
            hashMap.put("layout/ft_color_circle_0", Integer.valueOf(R.layout.ft_color_circle));
            hashMap.put("layout/ft_color_hsl_0", Integer.valueOf(R.layout.ft_color_hsl));
            hashMap.put("layout/ft_color_pushrod_0", Integer.valueOf(R.layout.ft_color_pushrod));
            hashMap.put("layout/ft_color_xyy_0", Integer.valueOf(R.layout.ft_color_xyy));
            hashMap.put("layout/ft_ct_light_0", Integer.valueOf(R.layout.ft_ct_light));
            hashMap.put("layout/ft_dali_add_0", Integer.valueOf(R.layout.ft_dali_add));
            hashMap.put("layout/ft_dali_pushrod_0", Integer.valueOf(R.layout.ft_dali_pushrod));
            hashMap.put("layout/ft_device2_0", Integer.valueOf(R.layout.ft_device2));
            hashMap.put("layout/ft_device_and_group_0", Integer.valueOf(R.layout.ft_device_and_group));
            hashMap.put("layout/ft_dim_light_0", Integer.valueOf(R.layout.ft_dim_light));
            hashMap.put("layout/ft_find_mail_pwd_0", Integer.valueOf(R.layout.ft_find_mail_pwd));
            hashMap.put("layout/ft_find_phone_pwd_0", Integer.valueOf(R.layout.ft_find_phone_pwd));
            hashMap.put("layout/ft_general_mode_0", Integer.valueOf(R.layout.ft_general_mode));
            hashMap.put("layout/ft_gqx_action_0", Integer.valueOf(R.layout.ft_gqx_action));
            hashMap.put("layout/ft_intelligence_0", Integer.valueOf(R.layout.ft_intelligence));
            hashMap.put("layout/ft_list_0", Integer.valueOf(R.layout.ft_list));
            hashMap.put("layout/ft_local_scene_0", Integer.valueOf(R.layout.ft_local_scene));
            hashMap.put("layout/ft_log_0", Integer.valueOf(R.layout.ft_log));
            hashMap.put("layout/ft_mail_reg_0", Integer.valueOf(R.layout.ft_mail_reg));
            hashMap.put("layout/ft_message_home_0", Integer.valueOf(R.layout.ft_message_home));
            hashMap.put("layout/ft_message_notice_0", Integer.valueOf(R.layout.ft_message_notice));
            hashMap.put("layout/ft_mic_0", Integer.valueOf(R.layout.ft_mic));
            hashMap.put("layout/ft_music_0", Integer.valueOf(R.layout.ft_music));
            hashMap.put("layout/ft_my_0", Integer.valueOf(R.layout.ft_my));
            hashMap.put("layout/ft_page_screen_panel_detail_0", Integer.valueOf(R.layout.ft_page_screen_panel_detail));
            hashMap.put("layout/ft_page_screen_panel_switch_0", Integer.valueOf(R.layout.ft_page_screen_panel_switch));
            hashMap.put("layout/ft_phone_reg_0", Integer.valueOf(R.layout.ft_phone_reg));
            hashMap.put("layout/ft_rgb_light_0", Integer.valueOf(R.layout.ft_rgb_light));
            hashMap.put("layout/ft_room_0", Integer.valueOf(R.layout.ft_room));
            hashMap.put("layout/ft_room_dali_add_0", Integer.valueOf(R.layout.ft_room_dali_add));
            hashMap.put("layout/ft_scene_0", Integer.valueOf(R.layout.ft_scene));
            hashMap.put("layout/ft_select_icons_0", Integer.valueOf(R.layout.ft_select_icons));
            hashMap.put("layout/ft_select_product_0", Integer.valueOf(R.layout.ft_select_product));
            hashMap.put("layout/ft_select_theme_0", Integer.valueOf(R.layout.ft_select_theme));
            hashMap.put("layout/ft_sensor_test_step_0", Integer.valueOf(R.layout.ft_sensor_test_step));
            hashMap.put("layout/ft_song_playlist_0", Integer.valueOf(R.layout.ft_song_playlist));
            hashMap.put("layout/ft_songs_0", Integer.valueOf(R.layout.ft_songs));
            hashMap.put("layout/ft_spi_color_0", Integer.valueOf(R.layout.ft_spi_color));
            hashMap.put("layout/ft_spi_mode_0", Integer.valueOf(R.layout.ft_spi_mode));
            hashMap.put("layout/ft_spi_play_list_0", Integer.valueOf(R.layout.ft_spi_play_list));
            hashMap.put("layout/ft_steps_introduction_0", Integer.valueOf(R.layout.ft_steps_introduction));
            hashMap.put("layout/ft_sub_device_0", Integer.valueOf(R.layout.ft_sub_device));
            hashMap.put("layout/ft_tv_ext_fun_0", Integer.valueOf(R.layout.ft_tv_ext_fun));
            hashMap.put("layout/ft_tv_fun_0", Integer.valueOf(R.layout.ft_tv_fun));
            hashMap.put("layout/ft_tv_num_0", Integer.valueOf(R.layout.ft_tv_num));
            hashMap.put("layout/head_g4_max_key_set_0", Integer.valueOf(R.layout.head_g4_max_key_set));
            hashMap.put("layout/head_super_panel_key_set_0", Integer.valueOf(R.layout.head_super_panel_key_set));
            hashMap.put("layout/item_485_to_ble_0", Integer.valueOf(R.layout.item_485_to_ble));
            hashMap.put("layout/item_add_group_0", Integer.valueOf(R.layout.item_add_group));
            hashMap.put("layout/item_add_instruct_content_0", Integer.valueOf(R.layout.item_add_instruct_content));
            hashMap.put("layout/item_add_instruct_title_0", Integer.valueOf(R.layout.item_add_instruct_title));
            hashMap.put("layout/item_advanced_mode_add_0", Integer.valueOf(R.layout.item_advanced_mode_add));
            hashMap.put("layout/item_advanced_mode_color_time_0", Integer.valueOf(R.layout.item_advanced_mode_color_time));
            hashMap.put("layout/item_as_panel_related_info_0", Integer.valueOf(R.layout.item_as_panel_related_info));
            hashMap.put("layout/item_auto_condition_0", Integer.valueOf(R.layout.item_auto_condition));
            hashMap.put("layout/item_automation_0", Integer.valueOf(R.layout.item_automation));
        }

        private static void internalPopulateLayoutIdLookup1() {
            HashMap<String, Integer> hashMap = sKeys;
            hashMap.put("layout/item_batch_setting_0", Integer.valueOf(R.layout.item_batch_setting));
            hashMap.put("layout/item_big_content_left_0", Integer.valueOf(R.layout.item_big_content_left));
            hashMap.put("layout/item_big_content_right_0", Integer.valueOf(R.layout.item_big_content_right));
            hashMap.put("layout/item_bind_eur_scene_0", Integer.valueOf(R.layout.item_bind_eur_scene));
            hashMap.put("layout/item_bind_zone_0", Integer.valueOf(R.layout.item_bind_zone));
            hashMap.put("layout/item_ble_to_485_0", Integer.valueOf(R.layout.item_ble_to_485));
            hashMap.put("layout/item_bt_ota_0", Integer.valueOf(R.layout.item_bt_ota));
            hashMap.put("layout/item_category_0", Integer.valueOf(R.layout.item_category));
            hashMap.put("layout/item_central_air_sub_device_0", Integer.valueOf(R.layout.item_central_air_sub_device));
            hashMap.put("layout/item_cg485_color_0", Integer.valueOf(R.layout.item_cg485_color));
            hashMap.put("layout/item_cgd_0", Integer.valueOf(R.layout.item_cgd));
            hashMap.put("layout/item_cgd_action_0", Integer.valueOf(R.layout.item_cgd_action));
            hashMap.put("layout/item_cloud_scene_0", Integer.valueOf(R.layout.item_cloud_scene));
            hashMap.put("layout/item_color_0", Integer.valueOf(R.layout.item_color));
            hashMap.put("layout/item_color_point_0", Integer.valueOf(R.layout.item_color_point));
            hashMap.put("layout/item_command_0", Integer.valueOf(R.layout.item_command));
            hashMap.put("layout/item_content_normal_0", Integer.valueOf(R.layout.item_content_normal));
            hashMap.put("layout/item_content_small_0", Integer.valueOf(R.layout.item_content_small));
            hashMap.put("layout/item_ct_light_group_control_sub_device_0", Integer.valueOf(R.layout.item_ct_light_group_control_sub_device));
            hashMap.put("layout/item_curtain_currainmotortype_select_0", Integer.valueOf(R.layout.item_curtain_currainmotortype_select));
            hashMap.put("layout/item_curtain_open_dir_select_0", Integer.valueOf(R.layout.item_curtain_open_dir_select));
            hashMap.put("layout/item_dali_batch_modify_param_0", Integer.valueOf(R.layout.item_dali_batch_modify_param));
            hashMap.put("layout/item_dali_light_manage_0", Integer.valueOf(R.layout.item_dali_light_manage));
            hashMap.put("layout/item_dali_manage_0", Integer.valueOf(R.layout.item_dali_manage));
            hashMap.put("layout/item_default_0", Integer.valueOf(R.layout.item_default));
            hashMap.put("layout/item_default_mode_0", Integer.valueOf(R.layout.item_default_mode));
            hashMap.put("layout/item_default_mode_select_0", Integer.valueOf(R.layout.item_default_mode_select));
            hashMap.put("layout/item_device_as_panel_0", Integer.valueOf(R.layout.item_device_as_panel));
            hashMap.put("layout/item_device_ble_0", Integer.valueOf(R.layout.item_device_ble));
            hashMap.put("layout/item_device_camera_0", Integer.valueOf(R.layout.item_device_camera));
            hashMap.put("layout/item_device_central_air_gate_0", Integer.valueOf(R.layout.item_device_central_air_gate));
            hashMap.put("layout/item_device_group_manage_0", Integer.valueOf(R.layout.item_device_group_manage));
            hashMap.put("layout/item_device_ir_0", Integer.valueOf(R.layout.item_device_ir));
            hashMap.put("layout/item_device_key_switch_0", Integer.valueOf(R.layout.item_device_key_switch));
            hashMap.put("layout/item_device_light_0", Integer.valueOf(R.layout.item_device_light));
            hashMap.put("layout/item_device_log_0", Integer.valueOf(R.layout.item_device_log));
            hashMap.put("layout/item_device_manage_0", Integer.valueOf(R.layout.item_device_manage));
            hashMap.put("layout/item_device_manage_new_0", Integer.valueOf(R.layout.item_device_manage_new));
            hashMap.put("layout/item_device_mesh_gateway_0", Integer.valueOf(R.layout.item_device_mesh_gateway));
            hashMap.put("layout/item_device_music_player_0", Integer.valueOf(R.layout.item_device_music_player));
            hashMap.put("layout/item_device_scene_0", Integer.valueOf(R.layout.item_device_scene));
            hashMap.put("layout/item_device_select_0", Integer.valueOf(R.layout.item_device_select));
            hashMap.put("layout/item_device_sensor_0", Integer.valueOf(R.layout.item_device_sensor));
            hashMap.put("layout/item_device_sort_0", Integer.valueOf(R.layout.item_device_sort));
            hashMap.put("layout/item_device_super_panel_0", Integer.valueOf(R.layout.item_device_super_panel));
            hashMap.put("layout/item_diy_mode_0", Integer.valueOf(R.layout.item_diy_mode));
            hashMap.put("layout/item_double_text_0", Integer.valueOf(R.layout.item_double_text));
            hashMap.put("layout/item_e6_panel_key_0", Integer.valueOf(R.layout.item_e6_panel_key));
            hashMap.put("layout/item_e6d_action_0", Integer.valueOf(R.layout.item_e6d_action));
            hashMap.put("layout/item_e6d_address_0", Integer.valueOf(R.layout.item_e6d_address));
            hashMap.put("layout/item_e6d_default_0", Integer.valueOf(R.layout.item_e6d_default));
            hashMap.put("layout/item_edit_diy_key_name_0", Integer.valueOf(R.layout.item_edit_diy_key_name));
            hashMap.put("layout/item_empty_foot_0", Integer.valueOf(R.layout.item_empty_foot));
            hashMap.put("layout/item_empty_foot_15_0", Integer.valueOf(R.layout.item_empty_foot_15));
            hashMap.put("layout/item_env_status_detail_condition_0", Integer.valueOf(R.layout.item_env_status_detail_condition));
            hashMap.put("layout/item_floor_manage_0", Integer.valueOf(R.layout.item_floor_manage));
            hashMap.put("layout/item_fresh_air_info_0", Integer.valueOf(R.layout.item_fresh_air_info));
            hashMap.put("layout/item_general_mode_0", Integer.valueOf(R.layout.item_general_mode));
            hashMap.put("layout/item_go_0", Integer.valueOf(R.layout.item_go));
            hashMap.put("layout/item_go_1_0", Integer.valueOf(R.layout.item_go_1));
            hashMap.put("layout/item_go_2_0", Integer.valueOf(R.layout.item_go_2));
            hashMap.put("layout/item_group_device_sort_0", Integer.valueOf(R.layout.item_group_device_sort));
            hashMap.put("layout/item_group_light_0", Integer.valueOf(R.layout.item_group_light));
            hashMap.put("layout/item_group_manage_0", Integer.valueOf(R.layout.item_group_manage));
            hashMap.put("layout/item_group_select_0", Integer.valueOf(R.layout.item_group_select));
            hashMap.put("layout/item_group_smart_panel_0", Integer.valueOf(R.layout.item_group_smart_panel));
            hashMap.put("layout/item_group_switch_pattern_0", Integer.valueOf(R.layout.item_group_switch_pattern));
            hashMap.put("layout/item_group_wave_sensor_0", Integer.valueOf(R.layout.item_group_wave_sensor));
            hashMap.put("layout/item_home_manage_0", Integer.valueOf(R.layout.item_home_manage));
            hashMap.put("layout/item_img_0", Integer.valueOf(R.layout.item_img));
            hashMap.put("layout/item_img_preview_0", Integer.valueOf(R.layout.item_img_preview));
            hashMap.put("layout/item_intercom_door_0", Integer.valueOf(R.layout.item_intercom_door));
            hashMap.put("layout/item_intercom_log_call_0", Integer.valueOf(R.layout.item_intercom_log_call));
            hashMap.put("layout/item_intercom_log_open_door_0", Integer.valueOf(R.layout.item_intercom_log_open_door));
            hashMap.put("layout/item_intercom_log_title_0", Integer.valueOf(R.layout.item_intercom_log_title));
            hashMap.put("layout/item_ir_diy_fun_0", Integer.valueOf(R.layout.item_ir_diy_fun));
            hashMap.put("layout/item_ir_diy_key_0", Integer.valueOf(R.layout.item_ir_diy_key));
            hashMap.put("layout/item_ir_fun_0", Integer.valueOf(R.layout.item_ir_fun));
            hashMap.put("layout/item_ir_key_0", Integer.valueOf(R.layout.item_ir_key));
            hashMap.put("layout/item_ir_key_te_0", Integer.valueOf(R.layout.item_ir_key_te));
            hashMap.put("layout/item_k_and_duv_0", Integer.valueOf(R.layout.item_k_and_duv));
            hashMap.put("layout/item_light_action_0", Integer.valueOf(R.layout.item_light_action));
            hashMap.put("layout/item_light_group_control_0", Integer.valueOf(R.layout.item_light_group_control));
            hashMap.put("layout/item_light_group_control_sub_device_0", Integer.valueOf(R.layout.item_light_group_control_sub_device));
            hashMap.put("layout/item_light_order_0", Integer.valueOf(R.layout.item_light_order));
            hashMap.put("layout/item_mesh_scan_0", Integer.valueOf(R.layout.item_mesh_scan));
            hashMap.put("layout/item_mesh_scan_all_device_0", Integer.valueOf(R.layout.item_mesh_scan_all_device));
            hashMap.put("layout/item_mesh_scan_replace_0", Integer.valueOf(R.layout.item_mesh_scan_replace));
            hashMap.put("layout/item_mesh_search_scan_0", Integer.valueOf(R.layout.item_mesh_search_scan));
            hashMap.put("layout/item_message_data_footer_0", Integer.valueOf(R.layout.item_message_data_footer));
            hashMap.put("layout/item_message_notice_0", Integer.valueOf(R.layout.item_message_notice));
            hashMap.put("layout/item_message_place_0", Integer.valueOf(R.layout.item_message_place));
            hashMap.put("layout/item_mode_0", Integer.valueOf(R.layout.item_mode));
            hashMap.put("layout/item_mr_param_0", Integer.valueOf(R.layout.item_mr_param));
            hashMap.put("layout/item_music_0", Integer.valueOf(R.layout.item_music));
            hashMap.put("layout/item_music_dialog_list_0", Integer.valueOf(R.layout.item_music_dialog_list));
            hashMap.put("layout/item_music_list_0", Integer.valueOf(R.layout.item_music_list));
            hashMap.put("layout/item_music_search_0", Integer.valueOf(R.layout.item_music_search));
            hashMap.put("layout/item_name_value_0", Integer.valueOf(R.layout.item_name_value));
            hashMap.put("layout/item_num_panel_switch_position_set_0", Integer.valueOf(R.layout.item_num_panel_switch_position_set));
            hashMap.put("layout/item_pad_content_0", Integer.valueOf(R.layout.item_pad_content));
            hashMap.put("layout/item_page_screen_panel_0", Integer.valueOf(R.layout.item_page_screen_panel));
            hashMap.put("layout/item_page_smart_panel_key_set_0", Integer.valueOf(R.layout.item_page_smart_panel_key_set));
            hashMap.put("layout/item_panel_bind_0", Integer.valueOf(R.layout.item_panel_bind));
            hashMap.put("layout/item_panel_switch_position_set_0", Integer.valueOf(R.layout.item_panel_switch_position_set));
            hashMap.put("layout/item_pic_0", Integer.valueOf(R.layout.item_pic));
            hashMap.put("layout/item_place_user_0", Integer.valueOf(R.layout.item_place_user));
            hashMap.put("layout/item_place_user_transform_place_0", Integer.valueOf(R.layout.item_place_user_transform_place));
            hashMap.put("layout/item_playlist_manager_0", Integer.valueOf(R.layout.item_playlist_manager));
            hashMap.put("layout/item_relate_info_0", Integer.valueOf(R.layout.item_relate_info));
            hashMap.put("layout/item_rgbcw_duv_0", Integer.valueOf(R.layout.item_rgbcw_duv));
            hashMap.put("layout/item_room_manage_0", Integer.valueOf(R.layout.item_room_manage));
            hashMap.put("layout/item_room_name_0", Integer.valueOf(R.layout.item_room_name));
            hashMap.put("layout/item_rs8_key_0", Integer.valueOf(R.layout.item_rs8_key));
            hashMap.put("layout/item_rs8_sub_device_0", Integer.valueOf(R.layout.item_rs8_sub_device));
            hashMap.put("layout/item_scene_action_0", Integer.valueOf(R.layout.item_scene_action));
            hashMap.put("layout/item_scene_eur_0", Integer.valueOf(R.layout.item_scene_eur));
            hashMap.put("layout/item_scene_new_0", Integer.valueOf(R.layout.item_scene_new));
            hashMap.put("layout/item_scene_panel_key_0", Integer.valueOf(R.layout.item_scene_panel_key));
            hashMap.put("layout/item_screen_display_0", Integer.valueOf(R.layout.item_screen_display));
            hashMap.put("layout/item_screen_info_input_0", Integer.valueOf(R.layout.item_screen_info_input));
            hashMap.put("layout/item_screen_panel_0", Integer.valueOf(R.layout.item_screen_panel));
            hashMap.put("layout/item_screen_panel_mult_line_0", Integer.valueOf(R.layout.item_screen_panel_mult_line));
            hashMap.put("layout/item_screen_sp_btn_key_0", Integer.valueOf(R.layout.item_screen_sp_btn_key));
            hashMap.put("layout/item_search_bar_0", Integer.valueOf(R.layout.item_search_bar));
            hashMap.put("layout/item_search_bar_no_edit_0", Integer.valueOf(R.layout.item_search_bar_no_edit));
            hashMap.put("layout/item_search_bar_no_edit_music_0", Integer.valueOf(R.layout.item_search_bar_no_edit_music));
            hashMap.put("layout/item_select_0", Integer.valueOf(R.layout.item_select));
            hashMap.put("layout/item_select2_0", Integer.valueOf(R.layout.item_select2));
            hashMap.put("layout/item_select_automation_0", Integer.valueOf(R.layout.item_select_automation));
            hashMap.put("layout/item_select_cloud_scene_0", Integer.valueOf(R.layout.item_select_cloud_scene));
            hashMap.put("layout/item_select_cover_0", Integer.valueOf(R.layout.item_select_cover));
            hashMap.put("layout/item_select_dali_with_place_0", Integer.valueOf(R.layout.item_select_dali_with_place));
            hashMap.put("layout/item_select_device_icon_0", Integer.valueOf(R.layout.item_select_device_icon));
            hashMap.put("layout/item_select_device_icon_horizontal_0", Integer.valueOf(R.layout.item_select_device_icon_horizontal));
            hashMap.put("layout/item_select_device_import_mode_0", Integer.valueOf(R.layout.item_select_device_import_mode));
            hashMap.put("layout/item_select_device_in_group_0", Integer.valueOf(R.layout.item_select_device_in_group));
            hashMap.put("layout/item_select_device_manage_0", Integer.valueOf(R.layout.item_select_device_manage));
            hashMap.put("layout/item_select_device_with_place_0", Integer.valueOf(R.layout.item_select_device_with_place));
            hashMap.put("layout/item_select_device_with_sync_0", Integer.valueOf(R.layout.item_select_device_with_sync));
            hashMap.put("layout/item_select_diy_0", Integer.valueOf(R.layout.item_select_diy));
            hashMap.put("layout/item_select_gateway_0", Integer.valueOf(R.layout.item_select_gateway));
            hashMap.put("layout/item_select_home_member_0", Integer.valueOf(R.layout.item_select_home_member));
            hashMap.put("layout/item_select_icon_0", Integer.valueOf(R.layout.item_select_icon));
            hashMap.put("layout/item_select_k_value_0", Integer.valueOf(R.layout.item_select_k_value));
            hashMap.put("layout/item_select_list_0", Integer.valueOf(R.layout.item_select_list));
            hashMap.put("layout/item_select_list_white_0", Integer.valueOf(R.layout.item_select_list_white));
            hashMap.put("layout/item_select_list_with_icon_0", Integer.valueOf(R.layout.item_select_list_with_icon));
            hashMap.put("layout/item_select_loop_value_0", Integer.valueOf(R.layout.item_select_loop_value));
            hashMap.put("layout/item_select_lux_ct_0", Integer.valueOf(R.layout.item_select_lux_ct));
            hashMap.put("layout/item_select_mode_cover_0", Integer.valueOf(R.layout.item_select_mode_cover));
            hashMap.put("layout/item_select_music_player_volume_0", Integer.valueOf(R.layout.item_select_music_player_volume));
            hashMap.put("layout/item_select_on_off_time_diy_0", Integer.valueOf(R.layout.item_select_on_off_time_diy));
            hashMap.put("layout/item_select_on_off_time_normal_0", Integer.valueOf(R.layout.item_select_on_off_time_normal));
            hashMap.put("layout/item_select_place_0", Integer.valueOf(R.layout.item_select_place));
            hashMap.put("layout/item_select_product_0", Integer.valueOf(R.layout.item_select_product));
            hashMap.put("layout/item_select_scene_0", Integer.valueOf(R.layout.item_select_scene));
            hashMap.put("layout/item_select_sub_0", Integer.valueOf(R.layout.item_select_sub));
            hashMap.put("layout/item_select_subtext_ver_0", Integer.valueOf(R.layout.item_select_subtext_ver));
            hashMap.put("layout/item_select_subtext_ver_onoff_diy_0", Integer.valueOf(R.layout.item_select_subtext_ver_onoff_diy));
            hashMap.put("layout/item_select_subtext_ver_onoff_diy_all_0", Integer.valueOf(R.layout.item_select_subtext_ver_onoff_diy_all));
            hashMap.put("layout/item_select_subtext_ver_onoff_diy_dali_0", Integer.valueOf(R.layout.item_select_subtext_ver_onoff_diy_dali));
            hashMap.put("layout/item_select_with_icon_0", Integer.valueOf(R.layout.item_select_with_icon));
            hashMap.put("layout/item_select_with_place_cg485_0", Integer.valueOf(R.layout.item_select_with_place_cg485));
            hashMap.put("layout/item_select_with_sub_0", Integer.valueOf(R.layout.item_select_with_sub));
            hashMap.put("layout/item_sensor_sub_device_0", Integer.valueOf(R.layout.item_sensor_sub_device));
            hashMap.put("layout/item_setting_0", Integer.valueOf(R.layout.item_setting));
            hashMap.put("layout/item_share_data_0", Integer.valueOf(R.layout.item_share_data));
            hashMap.put("layout/item_simple_select_0", Integer.valueOf(R.layout.item_simple_select));
            hashMap.put("layout/item_smart_panel_0", Integer.valueOf(R.layout.item_smart_panel));
            hashMap.put("layout/item_smart_panel_key_0", Integer.valueOf(R.layout.item_smart_panel_key));
            hashMap.put("layout/item_smart_panel_key_eb6_0", Integer.valueOf(R.layout.item_smart_panel_key_eb6));
            hashMap.put("layout/item_smart_panel_key_set_0", Integer.valueOf(R.layout.item_smart_panel_key_set));
            hashMap.put("layout/item_smart_speaker_0", Integer.valueOf(R.layout.item_smart_speaker));
            hashMap.put("layout/item_song_info_0", Integer.valueOf(R.layout.item_song_info));
            hashMap.put("layout/item_song_playlist_0", Integer.valueOf(R.layout.item_song_playlist));
            hashMap.put("layout/item_song_playlist_foot_0", Integer.valueOf(R.layout.item_song_playlist_foot));
            hashMap.put("layout/item_sort_0", Integer.valueOf(R.layout.item_sort));
            hashMap.put("layout/item_sp_btn_key_0", Integer.valueOf(R.layout.item_sp_btn_key));
            hashMap.put("layout/item_spi_edit_play_list_0", Integer.valueOf(R.layout.item_spi_edit_play_list));
            hashMap.put("layout/item_spi_mode_0", Integer.valueOf(R.layout.item_spi_mode));
            hashMap.put("layout/item_spi_play_list_0", Integer.valueOf(R.layout.item_spi_play_list));
            hashMap.put("layout/item_sq_panel_action_0", Integer.valueOf(R.layout.item_sq_panel_action));
            hashMap.put("layout/item_super_panel_fun_0", Integer.valueOf(R.layout.item_super_panel_fun));
            hashMap.put("layout/item_super_panel_key_set_0", Integer.valueOf(R.layout.item_super_panel_key_set));
            hashMap.put("layout/item_super_panel_switch_0", Integer.valueOf(R.layout.item_super_panel_switch));
            hashMap.put("layout/item_switch_0", Integer.valueOf(R.layout.item_switch));
            hashMap.put("layout/item_switch_button_0", Integer.valueOf(R.layout.item_switch_button));
            hashMap.put("layout/item_tail_0", Integer.valueOf(R.layout.item_tail));
            hashMap.put("layout/item_temp_0", Integer.valueOf(R.layout.item_temp));
            hashMap.put("layout/item_text_0", Integer.valueOf(R.layout.item_text));
            hashMap.put("layout/item_text_36_0", Integer.valueOf(R.layout.item_text_36));
            hashMap.put("layout/item_text_add_0", Integer.valueOf(R.layout.item_text_add));
            hashMap.put("layout/item_text_head_0", Integer.valueOf(R.layout.item_text_head));
            hashMap.put("layout/item_text_middle_0", Integer.valueOf(R.layout.item_text_middle));
            hashMap.put("layout/item_text_wrap_0", Integer.valueOf(R.layout.item_text_wrap));
            hashMap.put("layout/item_title_0", Integer.valueOf(R.layout.item_title));
            hashMap.put("layout/item_trig_key_0", Integer.valueOf(R.layout.item_trig_key));
            hashMap.put("layout/item_trig_scene_0", Integer.valueOf(R.layout.item_trig_scene));
            hashMap.put("layout/layout_empty_0", Integer.valueOf(R.layout.layout_empty));
            hashMap.put("layout/layout_error_0", Integer.valueOf(R.layout.layout_error));
            hashMap.put("layout/layout_error_2_0", Integer.valueOf(R.layout.layout_error_2));
            hashMap.put("layout/layout_ext_0", Integer.valueOf(R.layout.layout_ext));
            hashMap.put("layout/layout_loading_0", Integer.valueOf(R.layout.layout_loading));
            hashMap.put("layout/layout_protocol_default_0", Integer.valueOf(R.layout.layout_protocol_default));
            hashMap.put("layout/layout_radio_image_text_view_0", Integer.valueOf(R.layout.layout_radio_image_text_view));
            hashMap.put("layout/layout_tab_view_0", Integer.valueOf(R.layout.layout_tab_view));
            hashMap.put("layout/layout_title_default_0", Integer.valueOf(R.layout.layout_title_default));
            hashMap.put("layout/layout_title_ir_0", Integer.valueOf(R.layout.layout_title_ir));
            hashMap.put("layout/layout_title_tran_0", Integer.valueOf(R.layout.layout_title_tran));
            hashMap.put("layout/layout_title_transparent_0", Integer.valueOf(R.layout.layout_title_transparent));
            hashMap.put("layout/tab_text_0", Integer.valueOf(R.layout.tab_text));
            hashMap.put("layout/tab_text2_0", Integer.valueOf(R.layout.tab_text2));
            hashMap.put("layout/view_brt_setting_0", Integer.valueOf(R.layout.view_brt_setting));
            hashMap.put("layout/view_cgd_action_0", Integer.valueOf(R.layout.view_cgd_action));
            hashMap.put("layout/view_cgd_light_title_dali_0", Integer.valueOf(R.layout.view_cgd_light_title_dali));
            hashMap.put("layout/view_cgd_light_title_manage_0", Integer.valueOf(R.layout.view_cgd_light_title_manage));
            hashMap.put("layout/view_cgd_light_title_select_0", Integer.valueOf(R.layout.view_cgd_light_title_select));
            hashMap.put("layout/view_cgd_light_title_select_group_0", Integer.valueOf(R.layout.view_cgd_light_title_select_group));
            hashMap.put("layout/view_dali_manage_bottom_0", Integer.valueOf(R.layout.view_dali_manage_bottom));
            hashMap.put("layout/view_dali_text_seekbar_0", Integer.valueOf(R.layout.view_dali_text_seekbar));
            hashMap.put("layout/view_dali_text_seekbar_new_0", Integer.valueOf(R.layout.view_dali_text_seekbar_new));
            hashMap.put("layout/view_device_manage_bottom_0", Integer.valueOf(R.layout.view_device_manage_bottom));
            hashMap.put("layout/view_edit_text_seekbar_0", Integer.valueOf(R.layout.view_edit_text_seekbar));
            hashMap.put("layout/view_eur_tip_function_0", Integer.valueOf(R.layout.view_eur_tip_function));
            hashMap.put("layout/view_eur_tip_scene_0", Integer.valueOf(R.layout.view_eur_tip_scene));
            hashMap.put("layout/view_eur_tip_zone_0", Integer.valueOf(R.layout.view_eur_tip_zone));
            hashMap.put("layout/view_five_current_item_0", Integer.valueOf(R.layout.view_five_current_item));
            hashMap.put("layout/view_image_text_0", Integer.valueOf(R.layout.view_image_text));
            hashMap.put("layout/view_location_tip_0", Integer.valueOf(R.layout.view_location_tip));
            hashMap.put("layout/view_number_setting_0", Integer.valueOf(R.layout.view_number_setting));
            hashMap.put("layout/view_power_state_ble_all_0", Integer.valueOf(R.layout.view_power_state_ble_all));
            hashMap.put("layout/view_remote_tip_0", Integer.valueOf(R.layout.view_remote_tip));
            hashMap.put("layout/view_s1_pro_guide_0", Integer.valueOf(R.layout.view_s1_pro_guide));
            hashMap.put("layout/view_s1_pro_guide_en_0", Integer.valueOf(R.layout.view_s1_pro_guide_en));
            hashMap.put("layout/view_s2_pro_guide_0", Integer.valueOf(R.layout.view_s2_pro_guide));
            hashMap.put("layout/view_s2_pro_guide_en_0", Integer.valueOf(R.layout.view_s2_pro_guide_en));
            hashMap.put("layout/view_s3_pro_guide_0", Integer.valueOf(R.layout.view_s3_pro_guide));
            hashMap.put("layout/view_s3_pro_guide_en_0", Integer.valueOf(R.layout.view_s3_pro_guide_en));
            hashMap.put("layout/view_sq_pro_guide_0", Integer.valueOf(R.layout.view_sq_pro_guide));
            hashMap.put("layout/view_sq_pro_guide_en_0", Integer.valueOf(R.layout.view_sq_pro_guide_en));
            hashMap.put("layout/view_switch_seekbar_0", Integer.valueOf(R.layout.view_switch_seekbar));
            hashMap.put("layout/view_switch_two_seekbar_0", Integer.valueOf(R.layout.view_switch_two_seekbar));
            hashMap.put("layout/view_text_seekbar_0", Integer.valueOf(R.layout.view_text_seekbar));
            hashMap.put("layout/view_text_with_button_0", Integer.valueOf(R.layout.view_text_with_button));
        }
    }
}