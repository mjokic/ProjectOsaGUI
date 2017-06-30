package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.AuctionDTO;
import model.dto.BidDTO;
import model.dto.ItemDTO;
import model.dto.UserDTO;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import services.AuctionAPIService;
import services.BidAPIService;
import services.ItemAPIService;
import services.UserAPIService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Manager {

    public static ObservableList<Auction> auctions = FXCollections.observableArrayList();
    public static ObservableList<ItemDTO> items = FXCollections.observableArrayList();
    public static ObservableList<BidDTO> bids = FXCollections.observableArrayList();
    public static ObservableList<UserDTO> users = FXCollections.observableArrayList();


    public static final String url
            = "http://104.223.21.47";
//    public static final String url
//        = "http://192.168.0.15:8080";

    public static String email;
    public static String token;

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(logging);


    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setLenient()
            .create();

    private static Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(url)
            .client(httpClient.build())
            .build();

    public static AuctionAPIService auctionApiService = retrofit.create(AuctionAPIService.class);
    public static ItemAPIService itemApiService = retrofit.create(ItemAPIService.class);
    public static BidAPIService bidApiService = retrofit.create(BidAPIService.class);
    public static UserAPIService userApiService = retrofit.create(UserAPIService.class);
//    public static MeApiServiceInterface meApiServiceInterface = retrofit.create(MeApiServiceInterface.class);

}
