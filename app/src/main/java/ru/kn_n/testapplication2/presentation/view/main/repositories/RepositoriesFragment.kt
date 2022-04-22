package ru.kn_n.testapplication2.presentation.view.main.repositories

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import ru.kn_n.testapplication2.domain.containers.Repository
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.kn_n.testapplication2.databinding.FragmentRepBinding
import ru.kn_n.testapplication2.di.Scopes
import ru.kn_n.testapplication2.presentation.presenter.RepositoriesPresenter
import toothpick.Toothpick

const val FRAGMENT_STATE_ID = "stateId"
const val U_ID = "id"

class RepositoriesFragment : MvpAppCompatFragment(), RepositoriesView {

    private var _binding: FragmentRepBinding? = null

    @InjectPresenter
    lateinit var repositoriesPresenter: RepositoriesPresenter

    @ProvidePresenter
    fun provideRepositoriesPresenter() = Toothpick.openScope(Scopes.APP_SCOPE).getInstance(
        RepositoriesPresenter::class.java)

    private val binding get() = _binding!!

    private var listRepo: ArrayList<Repository> = ArrayList()

    private var userId = ""

    private var fragmentState = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRepBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.listOfRepositories.layoutManager = LinearLayoutManager(context)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(FRAGMENT_STATE_ID) }?.apply {
            userId = getString(U_ID)!!
            fragmentState = getInt(FRAGMENT_STATE_ID)
            if (getInt(FRAGMENT_STATE_ID)==0){
                binding.deleteAllSaved.visibility = View.GONE
                binding.searchBtn.setOnClickListener {
                    it.hideKeyboard()
                    search(binding.searchText.text.toString())
                }
            } else {
                searchInBD("", getString(U_ID)!!)
                binding.deleteAllSaved.visibility = View.VISIBLE
                binding.deleteAllSaved.setOnClickListener {
                    deleteAllSaved(getString(U_ID)!!)
                }
                binding.searchBtn.setOnClickListener {
                    it.hideKeyboard()
                    searchInBD(binding.searchText.text.toString(), getString(U_ID)!!)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (fragmentState==1) searchInBD("", userId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun search(query: String) {
        startLoading()
        repositoriesPresenter.search(query)
    }

    override fun showResult(listRepository: ArrayList<Repository>) {
        stopLoading()
        binding.listOfRepositories.adapter = RepositoriesAdapter(listRepository, { repo -> onListItemClick(repo) },{ repo -> checkRepositoryInDB(repo) },{ repo -> deleteRepFromDB(repo) })
    }

    override fun saveResult(listRepository: ArrayList<Repository>){
        listRepo = listRepository
    }

    private fun onListItemClick(repository: Repository) {
        repositoriesPresenter.goToRepository(repository, userId)
    }

    private fun checkRepositoryInDB(repository: Repository):Boolean {
        return repositoriesPresenter.checkRepoInDB(userId, repository, requireContext())
    }

    private fun deleteRepFromDB(repository: Repository) {
        repositoriesPresenter.deleteRepoFromDB(userId, repository, requireContext())
        searchInBD("", userId)
    }

    override fun searchInBD(query: String, id:String) {
        repositoriesPresenter.searchInDB(query, id, requireContext())
    }

    override fun showError(t: Throwable) {
        TODO("Not yet implemented")
    }

    override fun deleteAllSaved(id: String) {
        val list : ArrayList<Repository> = ArrayList()
        showResult(list)
        repositoriesPresenter.deleteAll(id, requireContext())
    }

    private fun stopLoading() {
        binding.loading.stopAnimation()
        binding.loading.visibility = View.GONE
    }

    private fun startLoading() {
        binding.loading.startAnimation()
        binding.loading.visibility = View.VISIBLE
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}