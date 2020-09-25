package com.anningtex.commonbasedata.data.api;


import com.anningtex.commonbasedata.data.base.BaseResponse;
import com.anningtex.commonbasedata.entity.RecentBean;
import com.anningtex.commonbasedata.entity.LoginBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @author Song
 */
public interface AppApi {
    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("login_in")
//    Observable<BaseResponse<LoginBean>> getLogin(@FieldMap Map<String, String> map);

    Observable<BaseResponse<LoginBean>> getLogin(@Field("username") String userName,
                                                 @Field("password") String password,
                                                 @Field("device_id") String deviceId);

    /**
     * OneFragment
     */
    @FormUrlEncoded
    @POST("api/v1/getWarehouseDelivernoWithOrderList2")
    Observable<BaseResponse<List<RecentBean>>> getOneFrag(@Field("supplier_delivery_no") String date);

    /**
     * 已废弃
     */
    @GET("global_api.php")
    Observable<BaseResponse<List<LoginBean>>> getCat(@QueryMap() Map<String, String> map);

    /**
     * 已废弃
     */
    @FormUrlEncoded
    @POST("article_api.php")
    Observable<String> getNews(@FieldMap Map<String, String> map);
}
