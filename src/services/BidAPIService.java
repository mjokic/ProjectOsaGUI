package services;


import model.Bid;
import model.dto.BidDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

import java.util.List;

public interface BidAPIService {

    @GET("/bids")
    Call<List<BidDTO>> getAllBids(@Header("Authorization") String token);

    @POST("/bids/")
    Call<Bid> createBid(@Body BidDTO bid, @Header("Authorization") String token);

}
