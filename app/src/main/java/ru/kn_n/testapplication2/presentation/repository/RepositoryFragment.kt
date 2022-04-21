package ru.kn_n.testapplication2.presentation.repository

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import ru.kn_n.testapplication2.domain.containers.Repository
import kotlinx.serialization.json.Json
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.kn_n.testapplication2.databinding.FragmentRepositoryBinding
import ru.kn_n.testapplication2.di.Scopes
import ru.kn_n.testapplication2.utils.makeDate
import toothpick.Toothpick

const val REPOSITORY = "repo"

class RepositoryFragment: MvpAppCompatFragment(), RepositoryView  {

    @InjectPresenter
    lateinit var repositoryPresenter: RepositoryPresenter

    @ProvidePresenter
    fun provideRepositoryPresenter() =
        Toothpick.openScope(Scopes.APP_SCOPE).getInstance(RepositoryPresenter::class.java)

    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(REPOSITORY) }?.apply {
            val repo = Json.decodeFromString(Repository.serializer(), getString(REPOSITORY)!!)
            binding.repository = repo
            Picasso.get().load(repo.owner.avatar_url).into(binding.userImg)
            binding.dateOfCreation.text = makeDate(repo.created_at)
        }
    }

    override fun showInformation() {
        TODO("Not yet implemented")
    }

    override fun addToSaved() {
        TODO("Not yet implemented")
    }

    override fun deleteFromSaved() {
        TODO("Not yet implemented")
    }

    override fun back() {
        TODO("Not yet implemented")
    }
}