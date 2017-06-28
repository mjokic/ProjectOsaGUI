package model.dto;


public class ItemDTO {

    private long id;
    private String name;
    private String description;
    private String picture;
    private boolean sold;
    private long userId;

    public ItemDTO(){}

    public ItemDTO(String name, String description, String picture, boolean sold, long userId) {
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.sold = sold;
        this.userId = userId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
                ", sold=" + sold +
                '}';
    }
}
