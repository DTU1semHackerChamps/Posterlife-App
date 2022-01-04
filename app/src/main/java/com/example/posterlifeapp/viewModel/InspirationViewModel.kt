package com.example.posterlifeapp.viewModel

import android.content.res.AssetManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.posterlifeapp.Repositories.InspirationRepository
import com.example.posterlifeapp.model.Poster

class InspirationViewModel(JSONassets: AssetManager) : ViewModel() {
    //var inspirationRepository: InspirationRepository = InspirationRepository(JSONassets)

    private var _mutableInspirationList = MutableLiveData<List<Poster>>()

    val inspirationList: LiveData<List<Poster>>
    get() = _mutableInspirationList

    init {
        //_mutableInspirationList.value = inspirationRepository.loadJSONPosterData()
    }
}