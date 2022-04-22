package ru.kn_n.testapplication2.presentation.presenter

import android.content.Context
import android.os.Bundle
import com.github.terrakok.cicerone.Router
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.kn_n.testapplication2.data.db.SQLiteHelper
import ru.kn_n.testapplication2.domain.containers.Repository
import ru.kn_n.testapplication2.domain.model.SavedRepository
import ru.kn_n.testapplication2.presentation.navigation.Screens.Main
import ru.kn_n.testapplication2.presentation.view.main.MainFragment
import ru.kn_n.testapplication2.presentation.view.main.USER_ID
import ru.kn_n.testapplication2.presentation.view.repository.RepositoryView
import javax.inject.Inject

@InjectViewState
class RepositoryPresenter @Inject constructor(private val router: Router) :  MvpPresenter<RepositoryView>(){
    fun backToMain(id: String){
        val fragment = MainFragment()
        fragment.arguments = Bundle().apply { putString(USER_ID, id) }
        router.backTo(Main(fragment))
    }

    fun addRepositoryToSaved(id: String, repository: Repository, context: Context) {
        val helper = SQLiteHelper(context)
        var description = ""
        if (!repository.description.isNullOrEmpty()) description = repository.description!!

        val savedRepository = SavedRepository(
            repository.full_name,
            id,
            repository.full_name,
            description,
            repository.forks,
            repository.watchers,
            repository.created_at,
            repository.owner.login,
            repository.owner.avatar_url
        )
        helper.insertSavedRepository(savedRepository)
    }

    fun deleteRepositoryFromSaved(id: String, name:String, context: Context){
        val helper = SQLiteHelper(context)
        helper.deleteUsersSavedRepository(id, name)
    }

    fun checkRepoInDB(id: String, repository: Repository, context: Context):Boolean{
        val helper = SQLiteHelper(context)
        return helper.checkRepository(id, repository.full_name)
    }
}