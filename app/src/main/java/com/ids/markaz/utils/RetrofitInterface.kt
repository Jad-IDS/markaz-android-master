package com.ids.markaz.utils

import com.ids.markaz.model.*
import retrofit2.Call
import retrofit2.http.*


interface RetrofitInterface {


    @FormUrlEncoded
    @POST("connect/token")
    fun login(
        @Field(ApiParameters.USERNAME) model: String,
        @Field(ApiParameters.PASSWORD) osVersion: String,
        @Field(ApiParameters.CLIENT_ID) deviceToken: String,
        @Field(ApiParameters.GRANT_TYPE) deviceTypeId: String,
        @Field(ApiParameters.SCOPE) imei: String
    ): Call<ResponseLogin>


    @GET("identity/User/GetUserClients")
    fun getUserClients(@Query(ApiParameters.VESTIO_USER_ID) vestio_user_id: Int): Call<ArrayList<ResponseUser>>


    @GET("identity/User/GetUserTypes")
    fun getUserTypes(@Query(ApiParameters.VESTIO_USER_ID) vestio_user_id: Int): Call<ArrayList<ResponseUser>>


    @GET("client/Dashboard/GetInvestmentInformation")
    fun getInvestmentInfo(
        @Query(ApiParameters.CLIENTID) clientid: Int,
        @Query(ApiParameters.CURRENCYID) currencyid: Int,
        @Query(ApiParameters.DATE) date: String
    ): Call<ResponseInvestmentInfo>



    @GET("client/Currency/GetCurrencies")
    fun getCurrencies(): Call<ArrayList<ResponseCurrency>>


    @GET("client/Dashboard/GetInvestmentsByAssetClass")
    fun getInvestmentByAsset(
        @Query(ApiParameters.CLIENTID) clientid: Int,
        @Query(ApiParameters.CURRENCYID) currencyid: Int,
        @Query(ApiParameters.DATE) date: String
    ): Call<ArrayList<ResponseAllInvestment>>

    @GET("client/Dashboard/GetInvestmentsByCountry")
    fun getInvestmentByCountry(
        @Query(ApiParameters.CLIENTID) clientid: Int,
        @Query(ApiParameters.CURRENCYID) currencyid: Int,
        @Query(ApiParameters.DATE) date: String
    ): Call<ArrayList<ResponseAllInvestment>>

    @GET("client/Dashboard/GetInvestmentsByCurrency")
    fun getInvestmentByCurrency(
        @Query(ApiParameters.CLIENTID) clientid: Int,
        @Query(ApiParameters.CURRENCYID) currencyid: Int,
        @Query(ApiParameters.DATE) date: String
    ): Call<ArrayList<ResponseAllInvestment>>

    @GET("client/Dashboard/GetInvestmentsBySector")
    fun getInvestmentsBySector(
        @Query(ApiParameters.CLIENTID) clientid: Int,
        @Query(ApiParameters.CURRENCYID) currencyid: Int,
        @Query(ApiParameters.DATE) date: String
    ): Call<ArrayList<ResponseAllInvestment>>



    @GET("portfolio/Dashboard/GetPortfolioAccounts")
    fun getPortfolioAccount(
        @Query(ApiParameters.VESTIO_USER_ID) vestio_user_id: Int
    ): Call<ArrayList<ResponseAccountSummary>>

    @GET("portfolio/Dashboard/GetPortfolioCashDetails")
    fun getCashDetails(
        @Query(ApiParameters.PORTFOLIO_ID) portfolio_id: Int,
        @Query(ApiParameters.CURRENCYID) currency_id: Int,
        @Query(ApiParameters.VESTIO_USER_ID) vestio_id: Int
    ): Call<ResponsePortfolioCash>

    @GET("portfolio/Dashboard/GetPortfolioHoldingPosition")
    fun getHoldingPosition(
        @Query(ApiParameters.PORTFOLIO_ID) portfolio_id: Int,
        @Query(ApiParameters.CURRENCYID) currency_id: Int,
        @Query(ApiParameters.DATE) date: String,
        @Query(ApiParameters.PRICE_BASIS) price_basis: String
    ): Call<ArrayList<ResponseHoldingPosition>>



    @GET("portfolio/Transaction/GetTransactions")
    fun getTransactions(
        @Query(ApiParameters.PORTFOLIO_ID) portfolio_id: Int,
        @Query(ApiParameters.CURRENCYID) currency_id: Int,
        @Query(ApiParameters.DATE) date: String,
        @Query(ApiParameters.PRICE_BASIS) price_basis: String
    ): Call<ArrayList<ResponseTransaction>>


    @GET("identity/user/GetUserPortfolios")
    fun getUserPortfolios(@Query(ApiParameters.VESTIO_USER_ID) vestio_user_id: Int): Call<ArrayList<ResponseUserPortfolio>>


    @GET("portfolio/Dashboard/GetPortfolioPerformanceDetails")
    fun getPortfolioPerformanceDetails(
        @Query(ApiParameters.VESTIO_USER_ID) vestio_user_id: Int,
        @Query(ApiParameters.DATE) date: String

    ): Call<ArrayList<ResponsePerformance>>




    @GET("portfolio/Holding/GetBottomHoldingsByReturn")
    fun getBottomHoldingByRerun(
        @Query(ApiParameters.PORTFOLIO_ID) portfolio_id: Int,
        @Query(ApiParameters.CURRENCYID) currency_id: Int,
        @Query(ApiParameters.DATE) date: String,
        @Query(ApiParameters.PRICE_BASIS) price_basis: String

    ): Call<ArrayList<ResponsePerformanceInside>>


    @GET("portfolio/Holding/GetTopHoldingsByWeight")
    fun getTopHoldingByWeight(
        @Query(ApiParameters.PORTFOLIO_ID) portfolio_id: Int,
        @Query(ApiParameters.CURRENCYID) currency_id: Int,
        @Query(ApiParameters.DATE) date: String,
        @Query(ApiParameters.PRICE_BASIS) price_basis: String

    ): Call<ArrayList<ResponsePerformanceInside>>


    @GET("portfolio/Holding/GetTopHoldingsByReturn")
    fun getTopHoldingByRerun(
        @Query(ApiParameters.PORTFOLIO_ID) portfolio_id: Int,
        @Query(ApiParameters.CURRENCYID) currency_id: Int,
        @Query(ApiParameters.DATE) date: String,
        @Query(ApiParameters.PRICE_BASIS) price_basis: String

    ): Call<ArrayList<ResponsePerformanceInside>>



    @GET("client/Report/GetPortfolioReports")
    fun getPortfolioReport(): Call<ArrayList<ResponsePortfolioReport>>



}
