package com.knowledge.myfinapp.data.remote.api

import com.knowledge.myfinapp.data.remote.dto.TransactionDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionApi {

    @GET("transactions")
    suspend fun getTransactions(
        @Query("updatedAfter") updatedAfter: Long? = null
    ): List<TransactionDto>

    @POST("transactions")
    suspend fun sendTransactions(@Body transactions: List<TransactionDto>)

    @GET("transactions/{id}")
    suspend fun getTransactionById(@Path("id") id: String): TransactionDto?
}