package com.knowledge.myfinapp.data.remote.api

import com.knowledge.myfinapp.data.remote.dto.ExpenseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ExpenseApi {

    @GET("expenses")
    suspend fun getExpenses(
        @Query("updatedAfter") updatedAfter: Long? = null
    ): List<ExpenseDto>

    @POST("expenses")
    suspend fun sendExpenses(@Body expenses: List<ExpenseDto>)

    @GET("expenses/{id}")
    suspend fun getExpenseById(@Path("id") id: String): ExpenseDto?
}