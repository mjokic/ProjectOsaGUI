package services;


import model.Bid;
import model.dto.BidDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface BidAPIService {

    @GET("/bids")
    Call<List<BidDTO>> getAllBids(@Header("Authorization") String token);

    @POST("/bids/")
    Call<Bid> createBid(@Body BidDTO bid, @Header("Authorization") String token);

    @PUT("/bids/{id}")
    Call<Void> editBid(@Body BidDTO bid, @Path("id") long id, @Header("Authorization") String token);

    @DELETE("/bids/{id}")
    Call<Void> deleteBid(@Path("id") long id, @Header("Authorization") String token);

}
