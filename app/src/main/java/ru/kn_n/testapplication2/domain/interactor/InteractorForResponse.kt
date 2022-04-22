package ru.kn_n.testapplication2.domain.interactor

import io.reactivex.Observable
import ru.kn_n.testapplication2.data.repositories.GitHubRepository
import ru.kn_n.testapplication2.domain.containers.Repositories
import javax.inject.Inject

class InteractorForResponse @Inject constructor(private val gitHubRepository: GitHubRepository) {
    fun getRepositories(query:String) : Observable<Repositories> {
        return gitHubRepository.getRepositories(query)
    }
}