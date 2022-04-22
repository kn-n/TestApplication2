package ru.kn_n.testapplication2.data.repositories

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.kn_n.testapplication2.data.api.RetrofitClient
import ru.kn_n.testapplication2.domain.containers.Repositories
import toothpick.InjectConstructor

@InjectConstructor
class GitHubRepository{
    fun getRepositories(query: String): Observable<Repositories> {
        return RetrofitClient.buildService()
            .getSearchRepositories(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}