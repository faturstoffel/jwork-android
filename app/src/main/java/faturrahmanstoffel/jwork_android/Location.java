package faturrahmanstoffel.jwork_android;

/**
 * Class Location, yang akan membuat objek lokasi
 * @author Fatur Rahman Stoffel
 * @version 21-06-2021
 */

public class Location {
    private String province;
    private String description;
    private String city;

    /**
     * Konstruktor dari objek class location
     * @param province
     * @param description
     * @param city
     */
    public Location(String province, String description, String city){
        this.province = province;
        this.city = city;
        this.description = description;
    }

    /**
     * Untuk mendapatkan provinsi lokasi job
     * @return province
     */
    public String getProvince(){
        return province;
    }

    /**
     * Untuk medapatkan deskripsi
     * @return description
     */
    public String getDescription(){
        return description;
    }

    /**
     * Untuk mendapatkan kota dari lokasi job
     * @return city
     */
    public String getCity(){
        return city;
    }

    /**
     * Untuk mengatur provinsi
     * @param province
     */
    public void setProvince(String province){
        this.province = province;
    }

    /**
     * Untuk mengatur deskripsi
     * @param description
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Untuk mengatur kota
     * @param city
     */
    public void setCity(String city){
        this.city = city;
    }

    /**
     * To String
     */
    @Override
    public String toString()
    {
        return "= Location ===============================" +
                "\nProvince      : " + province +
                "\nCity          : " + city +
                "\nDescription   : " + description +
                "\n==========================================";
    }


}
