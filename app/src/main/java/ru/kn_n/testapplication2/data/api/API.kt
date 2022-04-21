package ru.kn_n.testapplication2.data.api

import ru.kn_n.testapplication2.domain.containers.Repositories
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("search/repositories")
    fun getSearchRepositories( @Query("q") query: String):Observable<Repositories>
}