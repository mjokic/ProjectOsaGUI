package services;

import model.LoginCredentials;
import model.User;
import model.dto.UserDTO;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;


public interface UserAPIService {


    @GET("/users")
    Call<List<UserDTO>> getAllUsers(@Header("Authorization") String token);


    @POST("/login")
    Call<Void> login(@Body LoginCredentials credentials);

    @POST("/users")
    Call<Void> addUser(@Body UserDTO userDTO, @Header("Authorization") String token);

    @PUT("/users/{id}")
    Call<UserDTO> editUser(@Body UserDTO userDTO, @Path("id") long id, @Header("Authorization") String token);

    @DELETE("/users/{id}")
    Call<Void> deleteUser(@Path("id") long id, @Header("Authorization") String token);

    @Multipart
    @POST("/upload")
    Call<String> uploadUserAvatar(@Part MultipartBody.Part file, @Header("Authorization") String token);

    @Multipart
    @POST("/upload1")
    Call<String> uploadItemPicture(@Part MultipartBody.Part file, @Header("Authorization") String token);
}
