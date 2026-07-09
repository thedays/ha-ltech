package com.ltech.smarthome.model.extra;

import java.util.List;

/* loaded from: classes4.dex */
public class CityList {
    private List<CitylistBean> citylist;

    public List<CitylistBean> getCitylist() {
        return this.citylist;
    }

    public void setCitylist(List<CitylistBean> citylist) {
        this.citylist = citylist;
    }

    public static class CitylistBean {

        /* renamed from: c, reason: collision with root package name */
        private List<CBean> f6268c;
        private String p;

        public String getP() {
            return this.p;
        }

        public void setP(String p) {
            this.p = p;
        }

        public List<CBean> getC() {
            return this.f6268c;
        }

        public void setC(List<CBean> c2) {
            this.f6268c = c2;
        }

        public static class CBean {

            /* renamed from: a, reason: collision with root package name */
            private List<ABean> f6269a;
            private String n;

            public String getN() {
                return this.n;
            }

            public void setN(String n) {
                this.n = n;
            }

            public List<ABean> getA() {
                return this.f6269a;
            }

            public void setA(List<ABean> a2) {
                this.f6269a = a2;
            }

            public static class ABean {
                private String s;

                public String getS() {
                    return this.s;
                }

                public void setS(String s) {
                    this.s = s;
                }
            }
        }
    }
}