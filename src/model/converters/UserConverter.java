package model.converters;


import javafx.util.StringConverter;
import model.dto.UserDTO;

import java.util.HashMap;
import java.util.Map;

public class UserConverter extends StringConverter{

    private Map<String, UserDTO> mapUserDTO = new HashMap<>();

    @Override
    public String toString(Object object) {
        UserDTO user = (UserDTO) object;
        String string = user.getEmail() + " (" + user.getId() + ")";
        mapUserDTO.put(string, user);
        return string;
    }

    @Override
    public Object fromString(String string) {
        return mapUserDTO.get(string);
    }
}
