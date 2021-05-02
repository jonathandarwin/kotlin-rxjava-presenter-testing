package com.jonathandarwin.unittestrxjava

import com.jonathandarwin.unittestrxjava.model.User

/**
 * Created By : Jonathan Darwin on May 02, 2021
 */ 
interface MainContract {
    interface View {
        fun onSuccess(users: List<User>)

        fun onError(message: String)
    }

    interface Presenter {
        fun getUsers()

        fun getUsersWithFullName()

        fun unbind()
    }
}