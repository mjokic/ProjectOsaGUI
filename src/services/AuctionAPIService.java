package services;


import model.Auction;
import model.Bid;
import model.dto.AuctionDTO;
import model.dto.BidDTO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface AuctionAPIService {

    @GET("auctions/")
    Call<List<Auction>> getAllAuctions(@Header("Authorization") String token);

    @GET("auctions/{id}")
    Call<Auction> getAuction(@Path("id") long id, @Header("Authorization") String token);

    @GET("auctions/{id}/bids")
    Call<List<Bid>> getBids(@Path("id") long id, @Header("Authorization") String token);

    @GET("auctions/{id}/bids")
    Call<List<BidDTO>> getBidsDTO(@Path("id") long id, @Header("Authorization") String token);

    @POST("auctions/")
    Call<Auction> createAuction(@Body AuctionDTO auctionDTO, @Header("Authorization") String token);

    @PUT("auctions/{id}")
    Call<Auction> editAuction(@Body AuctionDTO auctionDTO, @Path("id") long id, @Header("Authorization") String token);


}
