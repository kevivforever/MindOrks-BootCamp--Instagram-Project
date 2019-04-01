package com.example.viveknaik.instagram.data.repository

import com.example.viveknaik.instagram.data.model.Dummy
import com.example.viveknaik.instagram.data.remote.NetworkService
import com.example.viveknaik.instagram.data.remote.request.DummyRequest
import com.mindorks.bootcamp.instagram.data.local.db.DatabaseService
import io.reactivex.Single
import javax.inject.Inject

class DummyRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun fetchDummy(id: String): Single<List<Dummy>> =
        networkService.doDummyCall(DummyRequest(id)).map { it.data }

}