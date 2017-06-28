package model;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Bid implements Serializable {

    private long id;
    private double price;
    private Date dateTime;
    private Auction auction;
    private User user;

    public Bid(long id, double price, Auction auction, User user) {
        this.id = id;
        this.price = price;
        this.dateTime = new Date();
        this.auction = auction;
        this.user = user;
    }

    public Bid(double price, Auction auction, User user) {
        this.price = price;
        this.dateTime = new Date();
        this.auction = auction;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public long getAuctionId(){
        return this.auction.getId();
    }

    public long getUserId(){
        return this.user.getId();
    }

    @Override
    public String toString() {
        return  "Date:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateTime) +
                "\nPrice:" + price +
//                "Auction=" + auction +
                "\nUser=" + user.getEmail()
                ;
    }
}
