package es.iessaladillo.pedrojoya.pr081.modelos;

import java.util.List;

// Class generated by Clever Models 

public class Clima {
    private Integer cod;
    private Float message;
    private City city;
    private Integer cnt;
    private List<Lista> list;

    public Integer getCod() {
        return cod;
    }

    public Float getMessage() {
        return message;
    }

    public City getCity() {
        return city;
    }

    public Integer getCnt() {
        return cnt;
    }

    public List<Lista> getList() {
        return list;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public void setMessage(Float message) {
        this.message = message;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public void setList(List<Lista> list) {
        this.list = list;
    }

}