package model.converters;

import javafx.util.StringConverter;
import model.Auction;
import model.dto.AuctionDTO;

import java.util.HashMap;
import java.util.Map;

public class AuctionConverter extends StringConverter {

    Map<String, Auction> mapAuctions = new HashMap<>();

    @Override
    public String toString(Object object) {
        Auction auction = (Auction) object;
        String string = auction.getId() + " (Start Price: " + auction.getStartPrice() + ")";
        mapAuctions.put(string, auction);
        return string;
    }

    @Override
    public Object fromString(String string) {
        return mapAuctions.get(string);
    }
}
