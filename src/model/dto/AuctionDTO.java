package model.dto;

import model.Auction;

import java.util.Date;


public class AuctionDTO {

    private long id;
    private double startPrice;
    private Date startDate;
    private Date endDate;
    private long user_id;
    private long item_id;
    private boolean over;

    public AuctionDTO(){}

    public AuctionDTO(long id, double startPrice, Date startDate, Date endDate, long user_id, long item_id, boolean over) {
        this.id = id;
        this.startPrice = startPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user_id = user_id;
        this.item_id = item_id;
        this.over = over;
    }

    public AuctionDTO(Auction auction){
        this.id = auction.getId();
        this.startPrice = auction.getStartPrice();
        this.startDate = auction.getStartDate();
        this.endDate = auction.getEndDate();
        this.user_id = auction.getUser().getId();
        this.item_id = auction.getItem().getId();
        this.over = auction.isOver();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getItem_id() {
        return item_id;
    }

    public void setItem_id(long item_id) {
        this.item_id = item_id;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    @Override
    public String toString() {
        return "AuctionDTO{" +
                "startPrice=" + startPrice +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", user_id=" + user_id +
                ", item_id=" + item_id +
                '}';
    }
}
