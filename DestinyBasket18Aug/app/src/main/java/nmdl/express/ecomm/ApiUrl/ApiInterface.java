package nmdl.express.ecomm.ApiUrl;

import java.util.List;

import nmdl.express.ecomm.Checksum;
import nmdl.express.ecomm.response.AdressResponse;
import nmdl.express.ecomm.response.CartAddResponse;
import nmdl.express.ecomm.response.CartNumResponse;
import nmdl.express.ecomm.response.CartResponse;
import nmdl.express.ecomm.response.CartSumResponse;
import nmdl.express.ecomm.response.CatSliderResponse;
import nmdl.express.ecomm.response.CategoryResponse;
import nmdl.express.ecomm.response.CityResponse;
import nmdl.express.ecomm.response.LoginResponse;
import nmdl.express.ecomm.response.MyOrderResponse;
import nmdl.express.ecomm.response.MyPaymentResponse;
import nmdl.express.ecomm.response.OrderResponse;
import nmdl.express.ecomm.response.ProductResponse;
import nmdl.express.ecomm.response.RegisterResponse;
import nmdl.express.ecomm.response.ShopCategoryResponse;
import nmdl.express.ecomm.response.ShopResponse;
import nmdl.express.ecomm.response.WalletResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("userRegister")
    Call<RegisterResponse> RegisterResponse(
            @Field("name") String name,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("password") String password,
            @Field("macAddress") String macAddress

    );


    @FormUrlEncoded
    @POST("userLogin")
    Call<LoginResponse> LoginResponse(
            @Field("email") String name,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("forgotPwd")
    Call<LoginResponse> forgotPwd(
            @Field("email") String email
    );

//    @FormUrlEncoded
//    @POST("notification")
//    Call<Noti> salary(
//            @Field("Token") String Token
//    );

    @GET("category")
    Call<List<CategoryResponse>> category();

    @GET("city")
    Call<List<CityResponse>> city();

    @FormUrlEncoded
    @POST("shop")
    Call<List<ShopResponse>> shop(
            @Field("city") String city,
            @Field("cat_id") String cat_id
    );
    @FormUrlEncoded
    @POST("shopcat")
    Call<List<ShopCategoryResponse>> shopCategoryResponse(
            @Field("shop_id") String shop_id
    );

    @GET("categoryslider")
    Call<List<CatSliderResponse>> slider();

    @FormUrlEncoded
    @POST("product")
    Call<List<ProductResponse>> product(
            @Field("shop_id") String shop_id,
            @Field("sub_cid") String shop_cid
    );

    @FormUrlEncoded
    @POST("productDetail")
    Call<ProductResponse> productDetail(
            @Field("p_id") String p_id
    );

    @FormUrlEncoded
    @POST("cartadd")
    Call<CartAddResponse> cartadd(
            @Field("p_id") String p_id,
            @Field("shop_id") String shop_id,
            @Field("u_id") String u_id,
            @Field("quantity") String quantity,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST("cart")
    Call<List<CartResponse>> cart(
            @Field("u_id") String u_id
    );
    @FormUrlEncoded
    @POST("cartad")
    Call<CartAddResponse> cartad(
            @Field("cart_id") String cart_id,
            @Field("quantity") String quantity
    );

    @FormUrlEncoded
    @POST("order")
    Call<OrderResponse> order(
            @Field("u_id") String u_id,
            @Field("mode") String mode,
            @Field("place") String place,
            @Field("txn") String txn,
            @Field("total") String total,
            @Field("sub") String sub
    );
    @FormUrlEncoded
    @POST("generateChecksum.php")
    Call<Checksum> getChecksum(
            @Field("MID") String mId,
            @Field("ORDER_ID") String orderId,
            @Field("CUST_ID") String custId,
            @Field("CHANNEL_ID") String channelId,
            @Field("TXN_AMOUNT") String txnAmount,
            @Field("WEBSITE") String website,
            @Field("CALLBACK_URL") String callbackUrl,
            @Field("INDUSTRY_TYPE_ID") String industryTypeId );

    @FormUrlEncoded
    @POST("wallet")
    Call<WalletResponse> wallet(
            @Field("u_id") String u_id
    );
    @FormUrlEncoded
    @POST("mypayment")
    Call<List<MyPaymentResponse>> mypayment(
            @Field("u_id") String u_id
    );

    @FormUrlEncoded
    @POST("myorder")
    Call<List<MyOrderResponse>> myorder(
            @Field("u_id") String u_id
    );
    @FormUrlEncoded
    @POST("adress")
    Call<AdressResponse> adress(
            @Field("u_id") String u_id,
            @Field("adress") String adress
    );

    @FormUrlEncoded
    @POST("cartsum")
    Call<CartSumResponse> cartsum(
            @Field("u_id") String u_id,
            @Field("shop_id") String shop_id
    );
    @FormUrlEncoded
    @POST("cartnum")
    Call<CartNumResponse> cartnum(
            @Field("u_id") String u_id
    );

}
