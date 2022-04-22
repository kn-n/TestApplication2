package ru.kn_n.testapplication2.presentation.view.repository

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
import ru.kn_n.testapplication2.presentation.presenter.RepositoryPresenter
import ru.kn_n.testapplication2.utils.makeDate
import toothpick.Toothpick

const val REPOSITORY = "repo"
const val UID = "id"

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
            val id = getString(UID)!!
            Picasso.get().load(repo.owner.avatar_url).into(binding.userImg)
            binding.dateOfCreation.text = makeDate(repo.created_at)
            binding.back.setOnClickListener { back(id) }
            if (repositoryPresenter.checkRepoInDB(id, repo, requireContext())) {
                binding.btnSaveDelete.isSelected = true
                binding.btnSaveDelete.setOnClickListener { deleteFromSaved(id, repo) }
            } else {
                binding.btnSaveDelete.isSelected = false
                binding.btnSaveDelete.setOnClickListener { addToSaved(id, repo) }
            }
        }
    }

    override fun addToSaved(id: String, repo:Repository) {
        binding.btnSaveDelete.isSelected = true
        repositoryPresenter.addRepositoryToSaved(id, repo, requireContext())
    }

    override fun deleteFromSaved(id: String, repo:Repository) {
        binding.btnSaveDelete.isSelected = false
        repositoryPresenter.deleteRepositoryFromSaved(id, repo.full_name, requireContext())
    }

    override fun back(id: String) {
        repositoryPresenter.backToMain(id)
    }
}