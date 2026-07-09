package com.ltech.smarthome.ui.device.sonos;

import java.util.List;

/* loaded from: classes4.dex */
public class SonosResponse {
    private String access_token;
    private String expires_in;
    private List<Groups> groups;
    private List<Households> households;
    private String refresh_token;
    private String scope;
    private String token_type;

    public List<Groups> getGroups() {
        return this.groups;
    }

    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }

    public List<Households> getHouseholds() {
        return this.households;
    }

    public void setHouseholds(List<Households> households) {
        this.households = households;
    }

    public String getAccess_token() {
        return this.access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return this.refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getToken_type() {
        return this.token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getExpires_in() {
        return this.expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public static class Groups {
        private String _objectType;
        private String coordinatorId;
        private String id;
        private String name;
        private String playbackState;
        private List<String> playerIds;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlaybackState() {
            return this.playbackState;
        }

        public void setPlaybackState(String playbackState) {
            this.playbackState = playbackState;
        }

        public String getCoordinatorId() {
            return this.coordinatorId;
        }

        public void setCoordinatorId(String coordinatorId) {
            this.coordinatorId = coordinatorId;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String get_objectType() {
            return this._objectType;
        }

        public void set_objectType(String _objectType) {
            this._objectType = _objectType;
        }

        public List<String> getPlayerIds() {
            return this.playerIds;
        }

        public void setPlayerIds(List<String> playerIds) {
            this.playerIds = playerIds;
        }
    }

    public static class Households {
        private String id;
        private String name;
        private String ownerLuid;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOwnerLuid() {
            return this.ownerLuid;
        }

        public void setOwnerLuid(String ownerLuid) {
            this.ownerLuid = ownerLuid;
        }

        public String toString() {
            return "Households{name='" + this.name + "', id='" + this.id + "', ownerLuid='" + this.ownerLuid + "'}";
        }
    }

    public static class PlayStatus {
        private String _objectType;
        private AvailablePlaybackActionsBean availablePlaybackActions;
        private boolean isDucking;
        private String itemId;
        private PlayModesBean playModes;
        private String playbackState;
        private int positionMillis;
        private String previousItemId;
        private int previousPositionMillis;

        public String getItemId() {
            return this.itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getPreviousItemId() {
            return this.previousItemId;
        }

        public void setPreviousItemId(String previousItemId) {
            this.previousItemId = previousItemId;
        }

        public boolean isIsDucking() {
            return this.isDucking;
        }

        public void setIsDucking(boolean isDucking) {
            this.isDucking = isDucking;
        }

        public AvailablePlaybackActionsBean getAvailablePlaybackActions() {
            return this.availablePlaybackActions;
        }

        public void setAvailablePlaybackActions(AvailablePlaybackActionsBean availablePlaybackActions) {
            this.availablePlaybackActions = availablePlaybackActions;
        }

        public int getPositionMillis() {
            return this.positionMillis;
        }

        public void setPositionMillis(int positionMillis) {
            this.positionMillis = positionMillis;
        }

        public String getPlaybackState() {
            return this.playbackState;
        }

        public void setPlaybackState(String playbackState) {
            this.playbackState = playbackState;
        }

        public PlayModesBean getPlayModes() {
            return this.playModes;
        }

        public void setPlayModes(PlayModesBean playModes) {
            this.playModes = playModes;
        }

        public String get_objectType() {
            return this._objectType;
        }

        public void set_objectType(String _objectType) {
            this._objectType = _objectType;
        }

        public int getPreviousPositionMillis() {
            return this.previousPositionMillis;
        }

        public void setPreviousPositionMillis(int previousPositionMillis) {
            this.previousPositionMillis = previousPositionMillis;
        }

        public static class AvailablePlaybackActionsBean {
            private String _objectType;
            private boolean canCrossfade;
            private boolean canPause;
            private boolean canPlay;
            private boolean canRepeat;
            private boolean canRepeatOne;
            private boolean canSeek;
            private boolean canShuffle;
            private boolean canSkip;
            private boolean canSkipBack;
            private boolean canSkipToPrevious;
            private boolean canStop;

            public boolean isCanRepeatOne() {
                return this.canRepeatOne;
            }

            public void setCanRepeatOne(boolean canRepeatOne) {
                this.canRepeatOne = canRepeatOne;
            }

            public boolean isCanCrossfade() {
                return this.canCrossfade;
            }

            public void setCanCrossfade(boolean canCrossfade) {
                this.canCrossfade = canCrossfade;
            }

            public boolean isCanStop() {
                return this.canStop;
            }

            public void setCanStop(boolean canStop) {
                this.canStop = canStop;
            }

            public boolean isCanPlay() {
                return this.canPlay;
            }

            public void setCanPlay(boolean canPlay) {
                this.canPlay = canPlay;
            }

            public boolean isCanSkipBack() {
                return this.canSkipBack;
            }

            public void setCanSkipBack(boolean canSkipBack) {
                this.canSkipBack = canSkipBack;
            }

            public boolean isCanSkipToPrevious() {
                return this.canSkipToPrevious;
            }

            public void setCanSkipToPrevious(boolean canSkipToPrevious) {
                this.canSkipToPrevious = canSkipToPrevious;
            }

            public boolean isCanShuffle() {
                return this.canShuffle;
            }

            public void setCanShuffle(boolean canShuffle) {
                this.canShuffle = canShuffle;
            }

            public boolean isCanSkip() {
                return this.canSkip;
            }

            public void setCanSkip(boolean canSkip) {
                this.canSkip = canSkip;
            }

            public boolean isCanSeek() {
                return this.canSeek;
            }

            public void setCanSeek(boolean canSeek) {
                this.canSeek = canSeek;
            }

            public boolean isCanRepeat() {
                return this.canRepeat;
            }

            public void setCanRepeat(boolean canRepeat) {
                this.canRepeat = canRepeat;
            }

            public String get_objectType() {
                return this._objectType;
            }

            public void set_objectType(String _objectType) {
                this._objectType = _objectType;
            }

            public boolean isCanPause() {
                return this.canPause;
            }

            public void setCanPause(boolean canPause) {
                this.canPause = canPause;
            }
        }

        public static class PlayModesBean {
            private String _objectType;
            private boolean crossfade;
            private boolean repeat;
            private boolean repeatOne;
            private boolean shuffle;

            public boolean isRepeat() {
                return this.repeat;
            }

            public void setRepeat(boolean repeat) {
                this.repeat = repeat;
            }

            public boolean isRepeatOne() {
                return this.repeatOne;
            }

            public void setRepeatOne(boolean repeatOne) {
                this.repeatOne = repeatOne;
            }

            public boolean isShuffle() {
                return this.shuffle;
            }

            public void setShuffle(boolean shuffle) {
                this.shuffle = shuffle;
            }

            public String get_objectType() {
                return this._objectType;
            }

            public void set_objectType(String _objectType) {
                this._objectType = _objectType;
            }

            public boolean isCrossfade() {
                return this.crossfade;
            }

            public void setCrossfade(boolean crossfade) {
                this.crossfade = crossfade;
            }
        }
    }

    public static class Volume {
        private String _objectType;
        private boolean fixed;
        private boolean muted;
        private int volume;

        public int getVolume() {
            return this.volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

        public boolean isFixed() {
            return this.fixed;
        }

        public void setFixed(boolean fixed) {
            this.fixed = fixed;
        }

        public String get_objectType() {
            return this._objectType;
        }

        public void set_objectType(String _objectType) {
            this._objectType = _objectType;
        }

        public boolean isMuted() {
            return this.muted;
        }

        public void setMuted(boolean muted) {
            this.muted = muted;
        }
    }

    public static class Metadata {
        private String _objectType;
        private ContainerBean container;
        private CurrentItemBean currentItem;
        private CurrentItemBean nextItem;

        public ContainerBean getContainer() {
            return this.container;
        }

        public void setContainer(ContainerBean container) {
            this.container = container;
        }

        public CurrentItemBean getNextItem() {
            return this.nextItem;
        }

        public void setNextItem(CurrentItemBean nextItem) {
            this.nextItem = nextItem;
        }

        public String get_objectType() {
            return this._objectType;
        }

        public void set_objectType(String _objectType) {
            this._objectType = _objectType;
        }

        public CurrentItemBean getCurrentItem() {
            return this.currentItem;
        }

        public void setCurrentItem(CurrentItemBean currentItem) {
            this.currentItem = currentItem;
        }

        public static class ContainerBean {
            private String _objectType;
            private List<?> images;

            public String get_objectType() {
                return this._objectType;
            }

            public void set_objectType(String _objectType) {
                this._objectType = _objectType;
            }

            public List<?> getImages() {
                return this.images;
            }

            public void setImages(List<?> images) {
                this.images = images;
            }
        }

        public static class CurrentItemBean {
            private String _objectType;
            private PoliciesBean policies;
            private TrackBean track;

            public PoliciesBean getPolicies() {
                return this.policies;
            }

            public void setPolicies(PoliciesBean policies) {
                this.policies = policies;
            }

            public TrackBean getTrack() {
                return this.track;
            }

            public void setTrack(TrackBean track) {
                this.track = track;
            }

            public String get_objectType() {
                return this._objectType;
            }

            public void set_objectType(String _objectType) {
                this._objectType = _objectType;
            }

            public static class PoliciesBean {
                private String _objectType;

                public String get_objectType() {
                    return this._objectType;
                }

                public void set_objectType(String _objectType) {
                    this._objectType = _objectType;
                }
            }

            public static class TrackBean {
                private String _objectType;
                private AlbumBean album;
                private ArtistBean artist;
                private int durationMillis;
                private IdBean id;
                private String imageUrl;
                private List<ImagesBean> images;
                private String name;
                private QualityBean quality;
                private ServiceBean service;
                private String type;

                public ArtistBean getArtist() {
                    return this.artist;
                }

                public void setArtist(ArtistBean artist) {
                    this.artist = artist;
                }

                public AlbumBean getAlbum() {
                    return this.album;
                }

                public void setAlbum(AlbumBean album) {
                    this.album = album;
                }

                public ServiceBean getService() {
                    return this.service;
                }

                public void setService(ServiceBean service) {
                    this.service = service;
                }

                public String getImageUrl() {
                    return this.imageUrl;
                }

                public void setImageUrl(String imageUrl) {
                    this.imageUrl = imageUrl;
                }

                public String getName() {
                    return this.name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getDurationMillis() {
                    return this.durationMillis;
                }

                public void setDurationMillis(int durationMillis) {
                    this.durationMillis = durationMillis;
                }

                public IdBean getId() {
                    return this.id;
                }

                public void setId(IdBean id) {
                    this.id = id;
                }

                public String getType() {
                    return this.type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String get_objectType() {
                    return this._objectType;
                }

                public void set_objectType(String _objectType) {
                    this._objectType = _objectType;
                }

                public QualityBean getQuality() {
                    return this.quality;
                }

                public void setQuality(QualityBean quality) {
                    this.quality = quality;
                }

                public List<ImagesBean> getImages() {
                    return this.images;
                }

                public void setImages(List<ImagesBean> images) {
                    this.images = images;
                }

                public static class ArtistBean {
                    private String _objectType;
                    private String name;

                    public String getName() {
                        return this.name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String get_objectType() {
                        return this._objectType;
                    }

                    public void set_objectType(String _objectType) {
                        this._objectType = _objectType;
                    }
                }

                public static class AlbumBean {
                    private String _objectType;
                    private String name;

                    public String getName() {
                        return this.name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String get_objectType() {
                        return this._objectType;
                    }

                    public void set_objectType(String _objectType) {
                        this._objectType = _objectType;
                    }
                }

                public static class ServiceBean {
                    private String _objectType;
                    private String id;
                    private List<?> images;
                    private String name;

                    public String getName() {
                        return this.name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getId() {
                        return this.id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String get_objectType() {
                        return this._objectType;
                    }

                    public void set_objectType(String _objectType) {
                        this._objectType = _objectType;
                    }

                    public List<?> getImages() {
                        return this.images;
                    }

                    public void setImages(List<?> images) {
                        this.images = images;
                    }
                }

                public static class IdBean {
                    private String _objectType;
                    private String accountId;
                    private String objectId;
                    private String serviceId;

                    public String getAccountId() {
                        return this.accountId;
                    }

                    public void setAccountId(String accountId) {
                        this.accountId = accountId;
                    }

                    public String getServiceId() {
                        return this.serviceId;
                    }

                    public void setServiceId(String serviceId) {
                        this.serviceId = serviceId;
                    }

                    public String get_objectType() {
                        return this._objectType;
                    }

                    public void set_objectType(String _objectType) {
                        this._objectType = _objectType;
                    }

                    public String getObjectId() {
                        return this.objectId;
                    }

                    public void setObjectId(String objectId) {
                        this.objectId = objectId;
                    }
                }

                public static class QualityBean {
                    private String _objectType;

                    public String get_objectType() {
                        return this._objectType;
                    }

                    public void set_objectType(String _objectType) {
                        this._objectType = _objectType;
                    }
                }

                public static class ImagesBean {
                    private String _objectType;
                    private String url;

                    public String get_objectType() {
                        return this._objectType;
                    }

                    public void set_objectType(String _objectType) {
                        this._objectType = _objectType;
                    }

                    public String getUrl() {
                        return this.url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }
                }
            }
        }
    }

    public static class Favorites {
        private String description;
        private long id;
        private String imageUrl;
        private String name;
        private String type;

        public String getImageUrl() {
            return this.imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public long getId() {
            return this.id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public String toString() {
        return "SonosResponse{access_token='" + this.access_token + "', refresh_token='" + this.refresh_token + "', scope='" + this.scope + "', token_type='" + this.token_type + "', expires_in='" + this.expires_in + "', households=" + this.households + ", groups=" + this.groups + '}';
    }
}