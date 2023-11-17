package com.example.tp5http

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
        @GET("/offre")
        suspend fun getOffres(): Response<MutableList<Offre>>
        @GET("/offre/{id}")
        suspend fun getOffreById(@Path("id") id: Int):Response<Offre>
        @POST("/offre")
        suspend fun AddOffre(@Body offre: Offre):Response<Offre>
        @PUT("/offre/{id}")
        suspend fun UpdateOffre(@Path("id") id:Int,@Body offre:Offre):Response<Offre>
        @DELETE("/offre/{id}")
        suspend fun DeleteOffre(@Path("id") id:Int): Response<Boolean>
}
