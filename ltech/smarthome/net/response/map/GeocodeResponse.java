package com.ltech.smarthome.net.response.map;

/* loaded from: classes4.dex */
public class GeocodeResponse {
    private String info;
    private String infocode;
    private Regeocode regeocode;
    private String status;

    public String getInfocode() {
        return this.infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Regeocode getRegeocode() {
        return this.regeocode;
    }

    public void setRegeocode(Regeocode regeocode) {
        this.regeocode = regeocode;
    }

    public static class Regeocode {
        private AddressComponent addressComponent;
        private String formatted_address;

        public String getFormatted_address() {
            return this.formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public AddressComponent getAddressComponent() {
            return this.addressComponent;
        }

        public void setAddressComponent(AddressComponent addressComponent) {
            this.addressComponent = addressComponent;
        }

        public static class AddressComponent {
            private String city;
            private String citycode;
            private String country;
            private String province;

            public String getCountry() {
                return this.country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getProvince() {
                String str = this.province;
                return str == null ? "" : str;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                String str = this.city;
                return str == null ? this.province : str;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCitycode() {
                return this.citycode;
            }

            public void setCitycode(String citycode) {
                this.citycode = citycode;
            }
        }
    }
}