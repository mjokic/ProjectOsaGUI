package model.converters;

import javafx.util.StringConverter;
import model.Item;
import model.dto.ItemDTO;

import java.util.HashMap;
import java.util.Map;

public class ItemConverter extends StringConverter {

    private Map<String, ItemDTO> mapItems = new HashMap<>();

    @Override
    public String toString(Object object) {
        ItemDTO item = (ItemDTO) object;
        String string = item.getName() + " (" + item.getId() + ")";
        mapItems.put(string, item);
        return string;
    }

    @Override
    public Object fromString(String string) {
        return mapItems.get(string);
    }
}
