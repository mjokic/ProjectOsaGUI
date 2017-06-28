package services;


import model.Item;
import model.dto.ItemDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ItemAPIService {

    @GET("items/")
    Call<List<ItemDTO>> getAllItems(@Header("Authorization") String token);

    @GET("items/{id}")
    Call<Item> getItemById(@Path("id") long id, @Header("Authorization") String token);

    @POST("items/")
    Call<ItemDTO> addItem(@Body ItemDTO itemDTO, @Header("Authorization") String token);

    @PUT("items/{id}")
    Call<ItemDTO> editItem(@Body ItemDTO itemDTO, @Path("id") long id, @Header("Authorization") String token);

    @DELETE("items/{id}")
    Call<Void> deleteItem(@Path("id") long id, @Header("Authorization") String token);

}
