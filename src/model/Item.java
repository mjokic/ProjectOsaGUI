package model;


import java.io.Serializable;
import java.util.Set;

public class Item implements Serializable {

    private long id;
    private String name;
    private String description;
    private String picture;
    private boolean sold;
//    public Auction auction;
    private Set<Auction> auctions;

    public Item(String name, String description, String picture, boolean sold){
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.sold = sold;
    }

    public Item(long id, String name, String description, String picture, boolean sold) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.sold = sold;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public Set<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(Set<Auction> auctions) {
        this.auctions = auctions;
    }

    //    public Auction getAuction() {
//        return auction;
//    }
//
//    public void setAuction(Auction auction) {
//        this.auction = auction;
//    }



    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", picture=" + picture +
                ", sold=" + sold +
                '}';
    }
}
