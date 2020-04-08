/**
  * Copyright 2020 bejson.com 
  */
package cn.acyou.framework.utils.amap.entity;
import java.util.List;

/**
 * Auto-generated: 2020-04-08 20:8:26
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class AddressComponent {

    private String city;
    private String province;
    private String adcode;
    private String district;
    private String towncode;
    private StreetNumber streetNumber;
    private String country;
    private String township;
    private List<BusinessAreas> businessAreas;
    private Building building;
    private Neighborhood neighborhood;
    private String citycode;
    public void setCity(String city) {
         this.city = city;
     }
     public String getCity() {
         return city;
     }

    public void setProvince(String province) {
         this.province = province;
     }
     public String getProvince() {
         return province;
     }

    public void setAdcode(String adcode) {
         this.adcode = adcode;
     }
     public String getAdcode() {
         return adcode;
     }

    public void setDistrict(String district) {
         this.district = district;
     }
     public String getDistrict() {
         return district;
     }

    public void setTowncode(String towncode) {
         this.towncode = towncode;
     }
     public String getTowncode() {
         return towncode;
     }

    public void setStreetNumber(StreetNumber streetNumber) {
         this.streetNumber = streetNumber;
     }
     public StreetNumber getStreetNumber() {
         return streetNumber;
     }

    public void setCountry(String country) {
         this.country = country;
     }
     public String getCountry() {
         return country;
     }

    public void setTownship(String township) {
         this.township = township;
     }
     public String getTownship() {
         return township;
     }

    public void setBusinessAreas(List<BusinessAreas> businessAreas) {
         this.businessAreas = businessAreas;
     }
     public List<BusinessAreas> getBusinessAreas() {
         return businessAreas;
     }

    public void setBuilding(Building building) {
         this.building = building;
     }
     public Building getBuilding() {
         return building;
     }

    public void setNeighborhood(Neighborhood neighborhood) {
         this.neighborhood = neighborhood;
     }
     public Neighborhood getNeighborhood() {
         return neighborhood;
     }

    public void setCitycode(String citycode) {
         this.citycode = citycode;
     }
     public String getCitycode() {
         return citycode;
     }

}