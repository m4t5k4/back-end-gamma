package com.example.postgresservice.model;


import javax.persistence.*;

@Entity

@Table(name="prices")
public class Prices {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long id;

    @Column(name = "appId")
    private int appId;

    @Column(name="euro")
    private double euro;

    @Column(name="dollar")
    private double dollar;

    @Column(name="pound")
    private double pound;

    @Column(name="peso")
    private double peso;

    public Prices(){

    }

    public Prices(int appId, double euro, double dollar, double pound, double peso) {
        this.appId = appId;
        this.euro = euro;
        this.dollar = dollar;
        this.pound = pound;
        this.peso = peso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public double getEuro() {
        return euro;
    }

    public void setEuro(double euro) {
        this.euro = euro;
    }

    public double getDollar() {
        return dollar;
    }

    public void setDollar(double dollar) {
        this.dollar = dollar;
    }

    public double getPound() {
        return pound;
    }

    public void setPound(double pound) {
        this.pound = pound;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}
