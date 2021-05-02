package com.jonathandarwin.unittestrxjava

import com.jonathandarwin.unittestrxjava.model.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Created By : Jonathan Darwin on May 02, 2021
 */ 
class MainPresenter(
    private val mainRepository: MainRepository,
    private val view: MainContract.View
) : MainContract.Presenter {

    private val disposable = CompositeDisposable()

    override fun getUsers() {
        disposable.add(
            mainRepository.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    it.map {userDTO ->
                        val name = if(userDTO.lastName == "") userDTO.firstName else userDTO.firstName + " " + userDTO.lastName
                        User(userDTO.username, name, userDTO.dob)
                    }
                }
                .subscribe({
                    view.onSuccess(it)
                }, {
                    view.onError(it.message ?: "Error. Please try again")
                })
        )
    }

    override fun getUsersWithFullName() {
        disposable.add(
            mainRepository.getUsersWithFullName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    it.map {userDTO ->
                        User(userDTO.username, userDTO.firstName + " " + userDTO.lastName, userDTO.dob)
                    }
                }
                .subscribe({
                    view.onSuccess(it)
                }, {
                    view.onError(it.message ?: "Error. Please try again")
                })
        )
    }

    override fun unbind() {
        disposable.clear()
    }
}