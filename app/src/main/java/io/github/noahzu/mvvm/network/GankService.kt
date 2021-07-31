package io.github.noahzu.mvvm.network

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @Author:         zu.jinhao
 * @CreateDate:     2021/7/31 6:22 下午
 * @Description:    todo
 */
interface GankService {

    @GET("random/category/GanHuo/type/{type}/count/{count}")
    suspend fun fetchRandomArtical(@Path("type") type : String,@Path("count") count : String)
}