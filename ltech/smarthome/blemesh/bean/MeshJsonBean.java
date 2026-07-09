package com.ltech.smarthome.blemesh.bean;

import java.util.List;

/* loaded from: classes3.dex */
public class MeshJsonBean {
    private String $schema;
    private List<AppKeysBean> appKeys;
    private List<GroupBean> groups;
    private String id;
    private String meshName;
    private String meshUUID;
    private List<NetKeysBean> netKeys;
    private List<NodesBean> nodes;
    private List<ProvisionersBean> provisioners;
    private List<Object> scenes;
    private String timestamp;
    private String version;

    public String get$schema() {
        return this.$schema;
    }

    public void set$schema(String $schema) {
        this.$schema = $schema;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMeshUUID() {
        return this.meshUUID;
    }

    public void setMeshUUID(String meshUUID) {
        this.meshUUID = meshUUID;
    }

    public String getMeshName() {
        return this.meshName;
    }

    public void setMeshName(String meshName) {
        this.meshName = meshName;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<NetKeysBean> getNetKeys() {
        return this.netKeys;
    }

    public void setNetKeys(List<NetKeysBean> netKeys) {
        this.netKeys = netKeys;
    }

    public List<AppKeysBean> getAppKeys() {
        return this.appKeys;
    }

    public void setAppKeys(List<AppKeysBean> appKeys) {
        this.appKeys = appKeys;
    }

    public List<ProvisionersBean> getProvisioners() {
        return this.provisioners;
    }

    public void setProvisioners(List<ProvisionersBean> provisioners) {
        this.provisioners = provisioners;
    }

    public List<NodesBean> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<NodesBean> nodes) {
        this.nodes = nodes;
    }

    public List<GroupBean> getGroups() {
        return this.groups;
    }

    public void setGroups(List<GroupBean> groups) {
        this.groups = groups;
    }

    public List<Object> getScenes() {
        return this.scenes;
    }

    public void setScenes(List<Object> scenes) {
        this.scenes = scenes;
    }

    public static class NetKeysBean {
        private int index;
        private String key;
        private String minSecurity;
        private String name;
        private int phase;
        private String timestamp;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getPhase() {
            return this.phase;
        }

        public void setPhase(int phase) {
            this.phase = phase;
        }

        public String getMinSecurity() {
            return this.minSecurity;
        }

        public void setMinSecurity(String minSecurity) {
            this.minSecurity = minSecurity;
        }

        public String getTimestamp() {
            return this.timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }

    public static class AppKeysBean {
        private int boundNetKey;
        private int index;
        private String key;
        private String name;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getBoundNetKey() {
            return this.boundNetKey;
        }

        public void setBoundNetKey(int boundNetKey) {
            this.boundNetKey = boundNetKey;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    public static class ProvisionersBean {
        private String UUID;
        private List<AllocatedGroupRangeBean> allocatedGroupRange;
        private List<AllocatedSceneRangeBean> allocatedSceneRange;
        private List<AllocatedUnicastRangeBean> allocatedUnicastRange;
        private String provisionerName;

        public String getProvisionerName() {
            return this.provisionerName;
        }

        public void setProvisionerName(String provisionerName) {
            this.provisionerName = provisionerName;
        }

        public String getUUID() {
            return this.UUID;
        }

        public void setUUID(String UUID) {
            this.UUID = UUID;
        }

        public List<AllocatedUnicastRangeBean> getAllocatedUnicastRange() {
            return this.allocatedUnicastRange;
        }

        public void setAllocatedUnicastRange(List<AllocatedUnicastRangeBean> allocatedUnicastRange) {
            this.allocatedUnicastRange = allocatedUnicastRange;
        }

        public List<AllocatedGroupRangeBean> getAllocatedGroupRange() {
            return this.allocatedGroupRange;
        }

        public void setAllocatedGroupRange(List<AllocatedGroupRangeBean> allocatedGroupRange) {
            this.allocatedGroupRange = allocatedGroupRange;
        }

        public List<AllocatedSceneRangeBean> getAllocatedSceneRange() {
            return this.allocatedSceneRange;
        }

        public void setAllocatedSceneRange(List<AllocatedSceneRangeBean> allocatedSceneRange) {
            this.allocatedSceneRange = allocatedSceneRange;
        }

        public static class AllocatedUnicastRangeBean {
            private String highAddress;
            private String lowAddress;

            public AllocatedUnicastRangeBean(String lowAddress, String highAddress) {
                this.lowAddress = lowAddress;
                this.highAddress = highAddress;
            }

            public String getLowAddress() {
                return this.lowAddress;
            }

            public void setLowAddress(String lowAddress) {
                this.lowAddress = lowAddress;
            }

            public String getHighAddress() {
                return this.highAddress;
            }

            public void setHighAddress(String highAddress) {
                this.highAddress = highAddress;
            }
        }

        public static class AllocatedGroupRangeBean {
            private String highAddress;
            private String lowAddress;

            public AllocatedGroupRangeBean(String lowAddress, String highAddress) {
                this.lowAddress = lowAddress;
                this.highAddress = highAddress;
            }

            public String getLowAddress() {
                return this.lowAddress;
            }

            public void setLowAddress(String lowAddress) {
                this.lowAddress = lowAddress;
            }

            public String getHighAddress() {
                return this.highAddress;
            }

            public void setHighAddress(String highAddress) {
                this.highAddress = highAddress;
            }
        }

        public static class AllocatedSceneRangeBean {
            private String firstScene;
            private String lastScene;

            public AllocatedSceneRangeBean(String firstScene, String lastScene) {
                this.firstScene = firstScene;
                this.lastScene = lastScene;
            }

            public String getFirstScene() {
                return this.firstScene;
            }

            public void setFirstScene(String firstScene) {
                this.firstScene = firstScene;
            }

            public String getLastScene() {
                return this.lastScene;
            }

            public void setLastScene(String lastScene) {
                this.lastScene = lastScene;
            }
        }
    }

    public static class NodesBean {
        private String UUID;
        private List<AppKeysBeanX> appKeys;
        private boolean blacklisted;
        private boolean configComplete;
        private int defaultTTL;
        private String deviceKey;
        private List<ElementsBean> elements;
        private FeaturesBean features;
        private String name;
        private List<NetKeysBeanX> netKeys;
        private String security;
        private String unicastAddress;

        public String getUUID() {
            return this.UUID;
        }

        public void setUUID(String UUID) {
            this.UUID = UUID;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDeviceKey() {
            return this.deviceKey;
        }

        public void setDeviceKey(String deviceKey) {
            this.deviceKey = deviceKey;
        }

        public String getUnicastAddress() {
            return this.unicastAddress;
        }

        public void setUnicastAddress(String unicastAddress) {
            this.unicastAddress = unicastAddress;
        }

        public String getSecurity() {
            return this.security;
        }

        public void setSecurity(String security) {
            this.security = security;
        }

        public boolean isConfigComplete() {
            return this.configComplete;
        }

        public void setConfigComplete(boolean configComplete) {
            this.configComplete = configComplete;
        }

        public FeaturesBean getFeatures() {
            return this.features;
        }

        public void setFeatures(FeaturesBean features) {
            this.features = features;
        }

        public int getDefaultTTL() {
            return this.defaultTTL;
        }

        public void setDefaultTTL(int defaultTTL) {
            this.defaultTTL = defaultTTL;
        }

        public boolean isBlacklisted() {
            return this.blacklisted;
        }

        public void setBlacklisted(boolean blacklisted) {
            this.blacklisted = blacklisted;
        }

        public List<NetKeysBeanX> getNetKeys() {
            return this.netKeys;
        }

        public void setNetKeys(List<NetKeysBeanX> netKeys) {
            this.netKeys = netKeys;
        }

        public List<AppKeysBeanX> getAppKeys() {
            return this.appKeys;
        }

        public void setAppKeys(List<AppKeysBeanX> appKeys) {
            this.appKeys = appKeys;
        }

        public List<ElementsBean> getElements() {
            return this.elements;
        }

        public void setElements(List<ElementsBean> elements) {
            this.elements = elements;
        }

        public static class FeaturesBean {
            private int friend;
            private int lowPower;
            private int proxy;
            private int relay;

            public FeaturesBean(int friend, int lowPower, int proxy, int relay) {
                this.friend = friend;
                this.lowPower = lowPower;
                this.proxy = proxy;
                this.relay = relay;
            }

            public int getFriend() {
                return this.friend;
            }

            public void setFriend(int friend) {
                this.friend = friend;
            }

            public int getLowPower() {
                return this.lowPower;
            }

            public void setLowPower(int lowPower) {
                this.lowPower = lowPower;
            }

            public int getProxy() {
                return this.proxy;
            }

            public void setProxy(int proxy) {
                this.proxy = proxy;
            }

            public int getRelay() {
                return this.relay;
            }

            public void setRelay(int relay) {
                this.relay = relay;
            }
        }

        public static class NetKeysBeanX {
            private int index;
            private boolean updated;

            public int getIndex() {
                return this.index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public boolean isUpdated() {
                return this.updated;
            }

            public void setUpdated(boolean updated) {
                this.updated = updated;
            }
        }

        public static class AppKeysBeanX {
            private int index;
            private boolean updated;

            public int getIndex() {
                return this.index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public boolean isUpdated() {
                return this.updated;
            }

            public void setUpdated(boolean updated) {
                this.updated = updated;
            }
        }

        public static class ElementsBean {
            private int index;
            private String location;
            private List<ModelsBean> models;
            private String name;

            public String getName() {
                return this.name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getIndex() {
                return this.index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public String getLocation() {
                return this.location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public List<ModelsBean> getModels() {
                return this.models;
            }

            public void setModels(List<ModelsBean> models) {
                this.models = models;
            }

            public static class ModelsBean {
                private List<?> bind;
                private String modelId;
                private List<String> subscribe;

                public String getModelId() {
                    return this.modelId;
                }

                public void setModelId(String modelId) {
                    this.modelId = modelId;
                }

                public List<?> getBind() {
                    return this.bind;
                }

                public void setBind(List<?> bind) {
                    this.bind = bind;
                }

                public List<String> getSubscribe() {
                    return this.subscribe;
                }

                public void setSubscribe(List<String> subscribe) {
                    this.subscribe = subscribe;
                }
            }
        }

        public static class NetworkTransmitBean {
            private int count;
            private int interval;

            public NetworkTransmitBean(int count, int interval) {
                this.count = count;
                this.interval = interval;
            }

            public int getCount() {
                return this.count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getInterval() {
                return this.interval;
            }

            public void setInterval(int interval) {
                this.interval = interval;
            }
        }
    }

    public static class GroupBean {
        private String address;
        private String name;
        private String parentAddress;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return this.address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getParentAddress() {
            return this.parentAddress;
        }

        public void setParentAddress(String parentAddress) {
            this.parentAddress = parentAddress;
        }
    }
}