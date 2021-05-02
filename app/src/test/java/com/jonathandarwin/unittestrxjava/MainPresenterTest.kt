package com.jonathandarwin.unittestrxjava

import com.jonathandarwin.unittestrxjava.model.User
import com.jonathandarwin.unittestrxjava.model.UserDTO
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import java.lang.Exception

/**
 * Created By : Jonathan Darwin on May 02, 2021
 */ 
class MainPresenterTest {

    private lateinit var presenter: MainContract.Presenter

    @MockK
    lateinit var view: MainContract.View
    
    @MockK
    lateinit var mainRepository: MainRepository

    private val userList = arrayListOf<UserDTO>()

    private val users = arrayListOf<User>()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        presenter = MainPresenter(mainRepository, view)

        // AndroidSchedulers.mainThread() using android framework dependencies, so we should override
        // and change the implementation of AndroidSchedulers.mainThread() to another thread
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            _ -> Schedulers.trampoline()
        }
    }

    @Test
    fun `test get users success`() {
        userList.clear()
        userList.add(UserDTO("jonathandarwin", "Jonathan", "Darwin", "1999-02-17"))
        userList.add(UserDTO("andy", "Andy", "", "2000-02-17"))
        userList.add(UserDTO("siapayak", "Siapa", "Yak", "2022-02-17"))

        users.clear()
        users.add(User("jonathandarwin", "Jonathan Darwin", "1999-02-17"))
        users.add(User("andy", "Andy", "2000-02-17"))
        users.add(User("siapayak", "Siapa Yak", "2022-02-17"))

        every { mainRepository.getUsers() }.returns(Observable.just(userList))
        every { view.onSuccess(any()) }.returns(Unit)

        presenter.getUsers()

        verify { view.onSuccess(users) }
    }

    @Test
    fun `test get users error`() {
        every { mainRepository.getUsers() }.returns(Observable.error(Exception("Network Error")))
        every { view.onError(any()) }.returns(Unit)

        presenter.getUsers()

        verify { view.onError("Network Error") }
    }

    @Test
    fun `test get users with full name success`() {
        userList.clear()
        userList.add(UserDTO("jonathandarwin", "Jonathan", "Darwin", "1999-02-17"))
        userList.add(UserDTO("siapayak", "Siapa", "Yak", "2022-02-17"))

        users.clear()
        users.add(User("jonathandarwin", "Jonathan Darwin", "1999-02-17"))
        users.add(User("siapayak", "Siapa Yak", "2022-02-17"))

        every { mainRepository.getUsersWithFullName() }.returns(Observable.just(userList))
        every { view.onSuccess(users) }.returns(Unit)

        presenter.getUsersWithFullName()

        verify { view.onSuccess(users) }
    }

    @Test
    fun `test get users with full name error`() {
        every { mainRepository.getUsersWithFullName() }.returns(Observable.error(Exception("Error. Please try again")))
        every { view.onError(any()) }.returns(Unit)

        presenter.getUsersWithFullName()

        verify { view.onError("Error. Please try again") }
    }
}