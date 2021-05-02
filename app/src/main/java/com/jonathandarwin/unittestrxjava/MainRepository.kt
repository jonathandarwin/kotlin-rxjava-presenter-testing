package com.jonathandarwin.unittestrxjava

import com.jonathandarwin.unittestrxjava.model.UserDTO
import io.reactivex.rxjava3.core.Observable

/**
 * Created By : Jonathan Darwin on May 02, 2021
 */ 
class MainRepository {

    private val userList = arrayListOf<UserDTO>()

    init {
        userList.add(UserDTO("jonathandarwin", "Jonathan", "Darwin", "1999-02-17"))
        userList.add(UserDTO("andy", "Andy", "", "2000-02-17"))
        userList.add(UserDTO("siapayak", "Siapa", "Yak", "2022-02-17"))
    }

    fun getUsers(): Observable<List<UserDTO>> {
        return Observable.create { emitter ->
            emitter.onNext(userList)
        }
    }

    fun getUsersWithFullName(): Observable<List<UserDTO>> {
        return Observable.create<List<UserDTO>> { emitter ->
            emitter.onNext(userList)
        }.flatMap {
            Observable.fromIterable(it)
        }.filter {
            it.lastName != ""
        }.toList().toObservable()
    }
}