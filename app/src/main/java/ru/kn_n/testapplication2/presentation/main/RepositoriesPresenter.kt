package ru.kn_n.testapplication2.presentation.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import ru.kn_n.testapplication2.data.api.RetrofitClient
import ru.kn_n.testapplication2.domain.containers.Repositories
import ru.kn_n.testapplication2.domain.containers.Repository
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.json.Json
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.kn_n.testapplication2.data.db.SQLiteHelper
import ru.kn_n.testapplication2.presentation.navigation.Screens
import ru.kn_n.testapplication2.presentation.repository.REPOSITORY
import ru.kn_n.testapplication2.presentation.repository.RepositoryFragment
import ru.kn_n.testapplication2.utils.makeClient
import javax.inject.Inject

@InjectViewState
class RepositoriesPresenter @Inject constructor(private val router: Router) : MvpPresenter<RepositoriesView>()  {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun search(query: String){
        compositeDisposable.add(
            RetrofitClient.buildService()
            .getSearchRepositories(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({response -> onResponse(response)}, {t -> onFailure(t) }))
    }

    private fun onResponse(list: Repositories){
        Log.d("API", "I did that")
        showResult(ArrayList(list.items))
    }

    private fun onFailure(t: Throwable) {
//        viewState.showError(t)
        Log.d("API", t.toString())
        val emptyList: ArrayList<Repository> = ArrayList()
        showResult(ArrayList(emptyList))
    }

    private fun searchInDB(query: String, id: String, context: Context){
        val helper = SQLiteHelper(context)
        val allRep = helper.getUsersSavedRepositories(id)
        if (query == "") {
            showResult(allRep)
        } else {
            val searchResult: ArrayList<Repository> = ArrayList()
            for (rep in allRep) {
                if (rep.full_name.contains(query) || rep.description!!.contains(query)){
                    searchResult.add(rep)
                }
            }
            showResult(searchResult)
        }

    }

    fun signOut(context: Context){
        makeClient(context).signOut()
//        router.navigateTo(SignIn())
    }

    fun signOut(){
//        router.navigateTo(SignIn())
    }

    private fun showResult(list: ArrayList<Repository>){
        viewState.showResult(list)
    }

    fun deleteAll(id: String, context: Context){
        val helper = SQLiteHelper(context)
        helper.deleteAllUsersSavedRepository(id)
    }

    fun goToRepository(repository: Repository){
        val jsonRepo = Json.encodeToString(Repository.serializer(), repository)
        val fragment = RepositoryFragment()
        fragment.arguments = Bundle().apply  { putString(REPOSITORY, jsonRepo) }
        router.navigateTo(Screens.Repository(fragment))
    }
}