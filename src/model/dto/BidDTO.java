package model.dto;



import model.Bid;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BidDTO {

    private long id;
    private double price;
    private long auction_id;
    private long user_id;
    private Date dateTime;

    public BidDTO(double price, long auction_id, long user_id) {
        this.price = price;
        this.auction_id = auction_id;
        this.user_id = user_id;
    }

    public BidDTO(double price, Date dateTime, long auction_id, long user_id) {
        this.price = price;
        this.auction_id = auction_id;
        this.user_id = user_id;
        this.dateTime = dateTime;
    }

    public BidDTO(Bid bid) {
        this.price = bid.getPrice();
        this.auction_id = bid.getAuction().getId();
        this.user_id = bid.getUser().getId();
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

    public long getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(long auction_id) {
        this.auction_id = auction_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }


    @Override
    public String toString() {
        return "price=" + price +
                ", auction_id=" + auction_id +
                ", user_id=" + user_id +
                ", dateTime=" + dateTime;
    }
}
