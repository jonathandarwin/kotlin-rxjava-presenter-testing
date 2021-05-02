package com.jonathandarwin.unittestrxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jonathandarwin.unittestrxjava.model.User

class MainActivity : AppCompatActivity(), MainContract.View {

    private val mainRepository = MainRepository()
    private lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(mainRepository, this)
        presenter.getUsers()
    }

    override fun onSuccess(users: List<User>) {

    }

    override fun onError(message: String) {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }
}