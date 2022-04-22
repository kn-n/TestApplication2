package ru.kn_n.testapplication2.presentation.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import ru.kn_n.testapplication2.domain.containers.Repositories
import ru.kn_n.testapplication2.domain.containers.Repository
import com.github.terrakok.cicerone.Router
import io.reactivex.disposables.CompositeDisposable
import kotlinx.serialization.json.Json
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.kn_n.testapplication2.data.db.SQLiteHelper
import ru.kn_n.testapplication2.domain.interactor.InteractorForResponse
import ru.kn_n.testapplication2.presentation.view.main.repositories.RepositoriesView
import ru.kn_n.testapplication2.presentation.navigation.Screens
import ru.kn_n.testapplication2.presentation.view.repository.REPOSITORY
import ru.kn_n.testapplication2.presentation.view.repository.RepositoryFragment
import ru.kn_n.testapplication2.presentation.view.repository.UID
import javax.inject.Inject

@InjectViewState
class RepositoriesPresenter @Inject constructor(private val router: Router, private val interactor: InteractorForResponse) : MvpPresenter<RepositoriesView>()  {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    fun search(query: String){
        compositeDisposable.add(
            interactor.getRepositories(query).subscribe({response -> onResponse(response)}, {t -> onFailure(t) }))

    }

    private fun onResponse(list: Repositories){
        Log.d("API", "I did that")
        viewState.saveResult(ArrayList(list.items))
        showResult(ArrayList(list.items))
    }

    private fun onFailure(t: Throwable) {
//        viewState.showError(t)
        Log.d("API", t.toString())
        val emptyList: ArrayList<Repository> = ArrayList()
        showResult(ArrayList(emptyList))
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    fun searchInDB(query: String, id: String, context: Context){
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

    private fun showResult(list: ArrayList<Repository>){
        viewState.showResult(list)
    }

    fun deleteAll(id: String, context: Context){
        val helper = SQLiteHelper(context)
        helper.deleteAllUsersSavedRepository(id)
    }

    fun deleteRepoFromDB(id: String, repository: Repository, context: Context){
        val helper = SQLiteHelper(context)
        helper.deleteUsersSavedRepository(id, repository.full_name)
    }

    fun goToRepository(repository: Repository, id: String){
        val jsonRepo = Json.encodeToString(Repository.serializer(), repository)
        val fragment = RepositoryFragment()
        fragment.arguments = Bundle().apply  { putString(REPOSITORY, jsonRepo)
                                                putString(UID, id)}
        router.navigateTo(Screens.Repository(fragment))
    }

    fun checkRepoInDB(id: String, repository: Repository, context: Context):Boolean{
        val helper = SQLiteHelper(context)
        return helper.checkRepository(id, repository.full_name)
    }
}