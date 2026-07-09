package com.ltech.smarthome.model.bean;

import com.aliyun.alink.h2.api.Constraint;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.ams.emas.push.notification.b;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.justalk.cloud.lemon.MtcConf2Constants;
import com.justalk.cloud.lemon.MtcConfConstants;
import com.ltech.smarthome.push.PushContentParamKey;
import com.ltech.smarthome.utils.Constants;
import io.objectbox.BoxStoreBuilder;
import io.objectbox.ModelBuilder;
import no.nordicsemi.android.log.LogContract;

/* loaded from: classes4.dex */
public class MyObjectBox {
    public static BoxStoreBuilder builder() {
        BoxStoreBuilder boxStoreBuilder = new BoxStoreBuilder(getModel());
        boxStoreBuilder.entity(Automation_.__INSTANCE);
        boxStoreBuilder.entity(Device_.__INSTANCE);
        boxStoreBuilder.entity(Floor_.__INSTANCE);
        boxStoreBuilder.entity(Group_.__INSTANCE);
        boxStoreBuilder.entity(McuVersion_.__INSTANCE);
        boxStoreBuilder.entity(ModeContent_.__INSTANCE);
        boxStoreBuilder.entity(Place_.__INSTANCE);
        boxStoreBuilder.entity(PlaceUser_.__INSTANCE);
        boxStoreBuilder.entity(PlayListInfo_.__INSTANCE);
        boxStoreBuilder.entity(Room_.__INSTANCE);
        boxStoreBuilder.entity(Scene_.__INSTANCE);
        boxStoreBuilder.entity(SongInfo_.__INSTANCE);
        boxStoreBuilder.entity(SuperPanelInfo_.__INSTANCE);
        boxStoreBuilder.entity(User_.__INSTANCE);
        return boxStoreBuilder;
    }

    private static byte[] getModel() {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.lastEntityId(14, 8639960229318138820L);
        modelBuilder.lastIndexId(0, 0L);
        modelBuilder.lastRelationId(0, 0L);
        buildEntityAutomation(modelBuilder);
        buildEntityDevice(modelBuilder);
        buildEntityFloor(modelBuilder);
        buildEntityGroup(modelBuilder);
        buildEntityMcuVersion(modelBuilder);
        buildEntityModeContent(modelBuilder);
        buildEntityPlace(modelBuilder);
        buildEntityPlaceUser(modelBuilder);
        buildEntityPlayListInfo(modelBuilder);
        buildEntityRoom(modelBuilder);
        buildEntityScene(modelBuilder);
        buildEntitySongInfo(modelBuilder);
        buildEntitySuperPanelInfo(modelBuilder);
        buildEntityUser(modelBuilder);
        return modelBuilder.build();
    }

    private static void buildEntityAutomation(ModelBuilder modelBuilder) {
        ModelBuilder.EntityBuilder entity = modelBuilder.entity("Automation");
        entity.id(1, 6705886512468269148L).lastPropertyId(20, 8211908299790351898L);
        entity.flags(1);
        entity.property("id", 6).id(1, 7803950021434748119L).flags(1);
        entity.property("automationId", 6).id(2, 2629021807326769330L);
        entity.property("currentUserId", 6).id(16, 2267838066444146697L);
        entity.property(MtcConf2Constants.MtcConfThirdUserIdKey, 6).id(3, 8758161409241328597L);
        entity.property("placeId", 6).id(4, 4574273447055974806L);
        entity.property("name", 9).id(5, 20665753849163589L);
        entity.property("startTime", 9).id(6, 6139515939888313083L);
        entity.property("endTime", 9).id(7, 4063622421250505475L);
        entity.property(Constants.WEEKS, 9).id(8, 5706713636910899390L);
        entity.property("timeZone", 9).id(9, 1020816320074677314L);
        entity.property("picIndex", 5).id(10, 4514620571267794147L);
        entity.property("enable", 1).id(11, 4787091227658619484L);
        entity.property("conditionType", 5).id(12, 5053204669159692890L);
        entity.property("index", 5).id(13, 4079483107743614095L);
        entity.property("automationType", 5).id(17, 2877859150215307394L);
        entity.property("gatewayDeviceId", 6).id(18, 212691650473928889L);
        entity.property("cycleindex", 5).id(19, 7365837451284444905L);
        entity.property("intervaltime", 5).id(20, 8211908299790351898L);
        entity.property("conditions", 9).id(14, 943858077752680614L).flags(2);
        entity.property("actions", 9).id(15, 3924584936505096638L).flags(2);
        entity.entityDone();
    }

    private static void buildEntityDevice(ModelBuilder modelBuilder) {
        ModelBuilder.EntityBuilder entity = modelBuilder.entity("Device");
        entity.id(2, 7527875015594622375L).lastPropertyId(46, 7852569917876502686L);
        entity.flags(1);
        entity.property("id", 6).id(1, 6267061293553585711L).flags(1);
        entity.property("deviceId", 6).id(2, 2409449627053507051L);
        entity.property("devicesn", 9).id(21, 4226618889515198766L);
        entity.property(MtcConf2Constants.MtcConfThirdUserIdKey, 6).id(3, 5140969779121303476L);
        entity.property("floorId", 6).id(4, 8443611812279291261L);
        entity.property("roomId", 6).id(5, 9036056691288237091L);
        entity.property("placeId", 6).id(6, 3061067255790767950L);
        entity.property("wifiMac", 9).id(7, 4196034902157968517L);
        entity.property("productId", 9).id(8, 7528114759963329987L);
        entity.property("hardwareId", 9).id(9, 8095607239007421026L);
        entity.property("onlineFlag", 5).id(10, 6312734054951781237L);
        entity.property("deviceName", 9).id(11, 3694173322014563185L);
        entity.property("macfalg", 5).id(12, 2174230202758036350L);
        entity.property("macdeviceid", 6).id(13, 6914911758163630008L);
        entity.property("deviceState", 9).id(14, 6952761121467340603L).flags(2);
        entity.property("platFormDeviceId", 9).id(15, 3354520184083339228L);
        entity.property("iotDeviceName", 9).id(16, 2636845695865438969L);
        entity.property("iotProductKey", 9).id(17, 4182495614039488238L);
        entity.property("imageIndex", 5).id(18, 8284988546074645077L);
        entity.property(RemoteMessageConst.MessageBody.PARAM, 9).id(19, 742176658754129466L);
        entity.property("extParam", 9).id(20, 449351845680389183L);
        entity.property("roomName", 9).id(22, 4624772662241704193L);
        entity.property("floorName", 9).id(23, 6494082271559455778L);
        entity.property("createtime", 9).id(26, 5705115089161531488L);
        entity.property("unicastAddress", 5).id(27, 7779187019993899365L);
        entity.property("checkTime", 6).id(28, 8666179528417700343L);
        entity.property("firmwareversion", 9).id(29, 8822821962900743009L);
        entity.property("mcuversion", 9).id(30, 6754365255368679668L);
        entity.property("bleversion", 9).id(31, 7839410708345740340L);
        entity.property("minkelvin", 5).id(32, 3469112925055518739L);
        entity.property("maxkelvin", 5).id(33, 4626537205365669809L);
        entity.property("maccode", 9).id(34, 4508595495897632218L);
        entity.property(Constants.LATITUDE, 8).id(35, 5605093127483585463L);
        entity.property(Constants.LONGITUDE, 8).id(36, 7139096807497776040L);
        entity.property("oauthtype", 5).id(37, 7985780848052837840L);
        entity.property("mainProductId", 9).id(38, 6990198415149705913L);
        entity.property("subhide", 5).id(39, 5314762360834917553L);
        entity.property("btmodule", 9).id(40, 5204931781725201217L);
        entity.property("panelColor", 9).id(41, 8587426248877441276L);
        entity.property(PushContentParamKey.REPORT_INSTRUCT, 9).id(42, 8578470902391495240L);
        entity.property("status", 5).id(43, 6148749401595060848L);
        entity.property("virtual", 5).id(44, 4686692303060253837L);
        entity.property("writable", 5).id(45, 7834104574472421516L);
        entity.property("presetKValues", 9).id(46, 7852569917876502686L).flags(2);
        entity.property("index", 5).id(25, 3685243002916165942L);
        entity.entityDone();
    }

    private static void buildEntityFloor(ModelBuilder modelBuilder) {
        ModelBuilder.EntityBuilder entity = modelBuilder.entity("Floor");
        entity.id(3, 4255230419656823726L).lastPropertyId(6, 4315989290242794570L);
        entity.flags(1);
        entity.property("id", 6).id(1, 2357103594575102329L).flags(1);
        entity.property("floorId", 6).id(2, 2285995388615553554L);
        entity.property(MtcConf2Constants.MtcConfThirdUserIdKey, 6).id(3, 97987036348617592L);
        entity.property("floorName", 9).id(4, 4753705801993350815L);
        entity.property("placeId", 6).id(5, 3382714927899287892L);
        entity.property("index", 5).id(6, 4315989290242794570L);
        entity.entityDone();
    }

    private static void buildEntityGroup(ModelBuilder modelBuilder) {
        ModelBuilder.EntityBuilder entity = modelBuilder.entity("Group");
        entity.id(4, 8193941154670794176L).lastPropertyId(41, 1057123067651234436L);
        entity.flags(1);
        entity.property("id", 6).id(1, 5908675346110195486L).flags(1);
        entity.property("groupId", 6).id(2, 554687703611521903L);
        entity.property(MtcConf2Constants.MtcConfThirdUserIdKey, 6).id(3, 2878074092015284537L);
        entity.property("placeId", 6).id(4, 1263248194583475619L);
        entity.property("groupName", 9).id(5, 5762139467027140842L);
        entity.property("moduleType", 9).id(6, 597330456680339993L);
        entity.property("controlType", 9).id(7, 6657148832389036846L);
        entity.property("groupAddress", 5).id(8, 3761081975103281013L);
        entity.property("groupIndex", 5).id(11, 914318614657171037L);
        entity.property(RemoteMessageConst.MessageBody.PARAM, 9).id(23, 7568463802108209143L);
        entity.property("extParam", 9).id(12, 5085639731973569381L);
        entity.property("floorId", 6).id(13, 4612517335112241988L);
        entity.property("roomId", 6).id(14, 7315175622351620829L);
        entity.property("createtime", 9).id(15, 917854200598082241L);
        entity.property("deviceIds", 9).id(9, 4819470310964965881L).flags(2);
        entity.property("groupState", 9).id(10, 546650102414812383L).flags(2);
        entity.property("floorName", 9).id(16, 4124472815626219876L);
        entity.property("roomName", 9).id(17, 308879638403123317L);
        entity.property("memorizePowerOff", 5).id(18, 2255413332445999594L);
        entity.property("setting", 9).id(19, 8917001966843965045L);
        entity.property("firstDevUniAddr", 5).id(21, 8394381792628002478L);
        entity.property("imgindex", 5).id(22, 6903237394891986108L);
        entity.property("minkelvin", 5).id(24, 1751359767361412814L);
        entity.property("maxkelvin", 5).id(25, 2132628576200494307L);
        entity.property("lightPlanData", 9).id(26, 3973990009841665669L);
        entity.property("macdeviceid", 6).id(27, 2268485460114671003L);
        entity.property("subkey", 5).id(28, 7943378524187730214L);
        entity.property("maingroupid", 6).id(29, 4471468505393131109L);
        entity.property("mainModuleType", 9).id(30, 6074192698186717396L);
        entity.property("mainControlType", 9).id(31, 26057123013632999L);
        entity.property("subhide", 5).id(32, 7445037356722895959L);
        entity.property("screenSetting", 9).id(33, 2287715132159683262L);
        entity.property("panelSettingState", 9).id(34, 4171954970708981777L);
        entity.property("editTime", 6).id(35, 6523156106169102046L);
        entity.property("colorPaletteUrl", 9).id(37, 6775221774787885514L);
        entity.property("checkTime", 6).id(39, 8276665706916457097L);
        entity.property(PushContentParamKey.REPORT_INSTRUCT, 9).id(40, 406043217191587241L);
        entity.property("leaderSup", 5).id(41, 1057123067651234436L);
        entity.property("presetKValues", 9).id(36, 7311879919790460099L).flags(2);
        entity.entityDone();
    }

    private static void buildEntityMcuVersion(ModelBuilder modelBuilder) {
        ModelBuilder.EntityBuilder entity = modelBuilder.entity("McuVersion");
        entity.id(13, 1860072180293201713L).lastPropertyId(26, 9083531832812855862L);
        entity.flags(1);
        entity.property("id", 6).id(1, 5473662017742496786L).flags(1);
        entity.property("deviceversionid", 6).id(2, 3195957888385626054L);
        entity.property("productid", 6).id(3, 6842181099120587936L);
        entity.property("versionname", 9).id(4, 8808843850607440893L);
        entity.property("versionnumber", 6).id(5, 6942007554557131987L);
        entity.property("filedir", 9).id(6, 7024072848026769822L);
        entity.property("filename", 9).id(7, 3441510852850768538L);
        entity.property("url", 9).id(8, 4894923634476612234L);
        entity.property("enable", 5).id(9, 1774741304093464929L);
        entity.property("createtime", 9).id(10, 4727732175901374188L);
        entity.property("begincreatetime", 9).id(11, 5203494511864646134L);
        entity.property("endcreatetime", 9).id(12, 8391190758933835383L);
        entity.property("type", 5).id(13, 4364017516819068294L);
        entity.property("pagesize", 5).id(14, 8510982419397009006L);
        entity.property("pagenum", 5).id(15, 8145076652266819782L);
        entity.property("productname", 9).id(16, 3363124842600364383L);
        entity.property("remark", 9).id(17, 4793740930264768743L);
        entity.property("firmwaretypecode", 9).id(18, 1338805161722196210L);
        entity.property("starttime", 9).id(19, 9082369870159747772L);
        entity.property("endtime", 9).id(20, 4687316491778685769L);
        entity.property("timetype", 5).id(21, 8016943875610220223L);
        entity.property("starthour", 9).id(22, 2257022436655909423L);
        entity.property("endhour", 9).id(23, 3606056158017714097L);
        entity.property("status", 5).id(24, 1276537809911760301L);
        entity.property("firmwareData", 23).id(25, 6533242670273586748L);
        entity.property("meshUpdate", 1).id(26, 9083531832812855862L);
        entity.entityDone();
    }

    private static void buildEntityModeContent(ModelBuilder modelBuilder) {
        ModelBuilder.EntityBuilder entity = modelBuilder.entity("ModeContent");
        entity.id(14, 8639960229318138820L).lastPropertyId(12, 1771905573882765811L);
        entity.flags(1);
        entity.property("lightModeId", 6).id(1, 3030781922718805661L).flags(1);
        entity.property("placeId", 6).id(2, 5623480551940785297L);
        entity.property(MtcConf2Constants.MtcConfThirdUserIdKey, 6).id(3, 3709022188682872222L);
        entity.property("modeName", 9).id(4, 724361076303826006L);
        entity.property("Content", 9).id(5, 9147076044656484212L);
        entity.property("modeType", 5).id(6, 9044178427797633096L);
        entity.property("modeIndex", 5).id(7, 6190049390261819958L);
        entity.property("deviceType", 5).id(8, 7591831730680575302L);
        entity.property("moduleType", 9).id(9, 8309125274675187565L);
        entity.property("controlType", 9).id(10, 2850852234771278982L);
        entity.property("picIndex", 5).id(11, 1422213402800237963L);
        entity.property("playTimes", 5).id(12, 1771905573882765811L);
        entity.entityDone();
    }

    private static void buildEntityPlace(ModelBuilder modelBuilder) {
        ModelBuilder.EntityBuilder entity = modelBuilder.entity("Place");
        entity.id(5, 773292192498944265L).lastPropertyId(22, 5112839736612586186L);
        entity.flags(1);
        entity.property("id", 6).id(1, 3133114058119526674L).flags(1);
        entity.property("placeId", 6).id(2, 5444552156872928533L);
        entity.property(MtcConf2Constants.MtcConfThirdUserIdKey, 6).id(3, 2782102937227190459L);
        entity.property("currentUserId", 6).id(18, 8984927271233710122L);
        entity.property("placeName", 9).id(4, 4473734264359389514L);
        entity.property("roleType", 5).id(5, 4209858209319519881L);
        entity.property(Constants.LATITUDE, 8).id(6, 2023973721321898890L);
        entity.property(Constants.LONGITUDE, 8).id(7, 8951347812560857416L);
        entity.property("qrCode", 9).id(8, 2285299795120166352L);
        entity.property("location", 9).id(9, 8691597820071995062L);
        entity.property("imageUrl", 9).id(10, 5144065144215554609L);
        entity.property("netKey", 9).id(11, 8083081468764688603L);
        entity.property("appKey", 9).id(12, 6057144331904377245L);
        entity.property("meshUUID", 9).id(13, 4151069535239060207L);
        entity.property("floortotal", 5).id(20, 5375775914969545662L);
        entity.property("roomtotal", 5).id(21, 4131533750255554269L);
        entity.property("devicetotal", 5).id(22, 5112839736612586186L);
        entity.property("provisionerAddress", 9).id(17, 138744890717151279L);
        entity.property("expiresIn", 5).id(14, 7042684100801208427L);
        entity.property("createdAt", 5).id(15, 1635961467032492328L);
        entity.property("appToken", 9).id(16, 8099606799202666137L);
        entity.property("ivindex", 5).id(19, 8178152300970862161L);
        entity.entityDone();
    }

    private static void buildEntityPlaceUser(ModelBuilder modelBuilder) {
        ModelBuilder.EntityBuilder entity = modelBuilder.entity("PlaceUser");
        entity.id(6, 4996182455117103161L).lastPropertyId(10, 3798923336480790396L);
        entity.flags(1);
        entity.property("placeUserId", 6).id(1, 8080829842291264278L).flags(129);
        entity.property("placeId", 6).id(2, 4220797459442127701L);
        entity.property("roleType", 5).id(3, 4169836061252997875L);
        entity.property("userName", 9).id(4, 876731724010292329L);
        entity.property(MtcConf2Constants.MtcConfThirdUserIdKey, 6).id(5, 1895820101171450722L);
        entity.property("headUrl", 9).id(6, 597816505576678719L);
        entity.property("currentUserId", 6).id(7, 547674969401327170L);
        entity.property("remark", 9).id(8, 5519017488672010033L);
        entity.property("mobilephone", 9).id(9, 3289331134202766772L);
        entity.property("email", 9).id(10, 3798923336480790396L);
        entity.entityDone();
    }

    private static void buildEntityPlayListInfo(ModelBuilder modelBuilder) {
        ModelBuilder.EntityBuilder entity = modelBuilder.entity("PlayListInfo");
        entity.id(11, 6935612517967392078L).lastPropertyId(7, 8051243104616005291L);
        entity.flags(1);
        entity.property("id", 6).id(1, 739034632912965741L).flags(129);
        entity.property("num", 5).id(2, 6210512744690701438L);
        entity.property("songCount", 5).id(3, 5020237798853755487L);
        entity.property("name", 9).id(4, 8395668651998794397L);
        entity.property("totalCount", 5).id(5, 6176306836289825984L);
        entity.property(MtcConfConstants.MtcConfRecordListKey, 9).id(6, 7391825615279179239L).flags(2);
        entity.property("deviceId", 6).id(7, 8051243104616005291L);
        entity.entityDone();
    }

    private static void buildEntityRoom(ModelBuilder modelBuilder) {
        ModelBuilder.EntityBuilder entity = modelBuilder.entity("Room");
        entity.id(7, 4194528765014197955L).lastPropertyId(7, 1371007011288221542L);
        entity.flags(1);
        entity.property("id", 6).id(1, 2758943041197014872L).flags(1);
        entity.property("roomId", 6).id(2, 6845595932204343143L);
        entity.property(MtcConf2Constants.MtcConfThirdUserIdKey, 6).id(3, 8583785616607475224L);
        entity.property("roomName", 9).id(4, 6553035784890916907L);
        entity.property("floorId", 6).id(5, 2472041964086548060L);
        entity.property("placeId", 6).id(6, 7225280961798720848L);
        entity.property("index", 5).id(7, 1371007011288221542L);
        entity.entityDone();
    }

    private static void buildEntityScene(ModelBuilder modelBuilder) {
        ModelBuilder.EntityBuilder entity = modelBuilder.entity("Scene");
        entity.id(8, 6257794413475613093L).lastPropertyId(20, 8838031380815706159L);
        entity.flags(1);
        entity.property("id", 6).id(1, 8968935232637887775L).flags(1);
        entity.property("sceneId", 6).id(2, 3473359205415401304L);
        entity.property(MtcConf2Constants.MtcConfThirdUserIdKey, 6).id(3, 6029041216155525418L);
        entity.property("placeId", 6).id(4, 1361149156928703317L);
        entity.property("sceneName", 9).id(5, 3332943564368279378L);
        entity.property("sceneIndex", 5).id(6, 4100343414047234864L);
        entity.property("iconPos", 5).id(7, 7877913399254226296L);
        entity.property(MtcConf2Constants.MtcConfCreateTimeKey, 9).id(11, 6776506450356784032L);
        entity.property("floorId", 6).id(12, 4342175100577386862L);
        entity.property("roomId", 6).id(13, 1984278602486872979L);
        entity.property("common", 1).id(17, 1925129804086320632L);
        entity.property("dynamic", 1).id(18, 7517272889886056374L);
        entity.property("sceneContents", 9).id(8, 4062300178645355044L).flags(2);
        entity.property("sceneNum", 5).id(9, 4854726156694933231L);
        entity.property("sceneType", 5).id(10, 5017854045143104460L);
        entity.property(RemoteMessageConst.MessageBody.PARAM, 9).id(14, 8934073556831949595L);
        entity.property("macdeviceid", 6).id(15, 2882321279005394260L);
        entity.property("extParam", 9).id(16, 1846290750401907434L);
        entity.property("floorName", 9).id(19, 5436302219465738936L);
        entity.property("roomName", 9).id(20, 8838031380815706159L);
        entity.entityDone();
    }

    private static void buildEntitySongInfo(ModelBuilder modelBuilder) {
        ModelBuilder.EntityBuilder entity = modelBuilder.entity("SongInfo");
        entity.id(12, 4145840906477473075L).lastPropertyId(7, 4982736628914763323L);
        entity.flags(1);
        entity.property("id", 6).id(1, 759623327050416462L).flags(129);
        entity.property("name", 9).id(2, 4894647344693728230L);
        entity.property("author", 9).id(3, 6266078283562276900L);
        entity.property("num", 5).id(4, 1364955755996351339L);
        entity.property("totalCount", 5).id(5, 7108212837495528730L);
        entity.property("deviceId", 6).id(6, 5106035106047086254L);
        entity.property("state", 5).id(7, 4982736628914763323L);
        entity.entityDone();
    }

    private static void buildEntitySuperPanelInfo(ModelBuilder modelBuilder) {
        ModelBuilder.EntityBuilder entity = modelBuilder.entity("SuperPanelInfo");
        entity.id(9, 8267041185198295928L).lastPropertyId(14, 7462219505003419005L);
        entity.flags(1);
        entity.property("id", 6).id(1, 8230202243150138028L).flags(1);
        entity.property("deviceId", 6).id(2, 8585796085209739872L);
        entity.property(MtcConf2Constants.MtcConfThirdUserIdKey, 6).id(3, 226390604811589666L);
        entity.property("deviceIds", 9).id(4, 5995466387309798230L).flags(2);
        entity.property("groupIds", 9).id(5, 3535562238398834698L).flags(2);
        entity.property("sceneIds", 9).id(6, 7662961315969186008L).flags(2);
        entity.property("panelKeyInfo", 9).id(7, 1495224530075939618L).flags(2);
        entity.property("sortRoleList", 9).id(8, 2830489802157761985L).flags(2);
        entity.property("sortSceneList", 9).id(9, 2227095475495828902L).flags(2);
        entity.property("devices", 9).id(10, 2989974857577647259L).flags(2);
        entity.property("scenes", 9).id(11, 4267987212035579909L).flags(2);
        entity.property("groups", 9).id(12, 7496605232982426448L).flags(2);
        entity.property("lastfirmwareversion", 9).id(13, 6057666196757526069L);
        entity.property("lastmcuversion", 9).id(14, 7462219505003419005L);
        entity.entityDone();
    }

    private static void buildEntityUser(ModelBuilder modelBuilder) {
        ModelBuilder.EntityBuilder entity = modelBuilder.entity("User");
        entity.id(10, 3879795626785119564L).lastPropertyId(14, 2711045064399245865L);
        entity.flags(1);
        entity.property(MtcConf2Constants.MtcConfThirdUserIdKey, 6).id(1, 8953776873695858151L).flags(129);
        entity.property(b.APP_ID, 6).id(2, 2735466916638292300L);
        entity.property("userName", 9).id(3, 3423926600244102168L);
        entity.property(LogContract.Session.SESSION_CONTENT_DIRECTORY, 9).id(4, 1090525311075963196L);
        entity.property("secretKey", 9).id(5, 2426003938649260757L);
        entity.property("productKey", 9).id(6, 4379522302917370280L);
        entity.property(Constraint.PARAM_DEVICE_SECRET, 9).id(7, 662419698424636022L);
        entity.property(TmpConstant.DEVICE_IOTID, 9).id(8, 5735954857398294129L);
        entity.property("deviceName", 9).id(9, 4781469743077557329L);
        entity.property("email", 9).id(10, 9786799954368171L);
        entity.property("mobilePhone", 9).id(11, 232080378040141236L);
        entity.property("headUrl", 9).id(12, 6714873851833205106L);
        entity.property("placetotal", 5).id(13, 4135883284986109456L);
        entity.property("devicetotal", 5).id(14, 2711045064399245865L);
        entity.entityDone();
    }
}