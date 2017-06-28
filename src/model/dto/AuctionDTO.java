package model.dto;

import java.util.Date;


public class AuctionDTO {

    private double startPrice;
    private Date startDate;
    private Date endDate;
    private long user_id;
    private long item_id;
    private boolean over;

    public AuctionDTO(){}

    public AuctionDTO(double startPrice, Date startDate, Date endDate, long user_id, long item_id, boolean over) {
        this.startPrice = startPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user_id = user_id;
        this.item_id = item_id;
        this.over = over;
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
