package com.jonathandarwin.unittestrxjava

import com.jonathandarwin.unittestrxjava.model.UserDTO
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.Before
import org.junit.Test

/**
 * Created By : Jonathan Darwin on May 02, 2021
 */ 
class MainRepositoryTest {

    lateinit var mainRepository: MainRepository

    private val userList = arrayListOf<UserDTO>()
    @Before
    fun setup() {
        mainRepository = MainRepository()
    }

    @Test
    fun `get users success`() {
        val ts = TestObserver.create<List<UserDTO>>()
        mainRepository.getUsers().subscribe(ts)

        userList.clear()
        userList.add(UserDTO("jonathandarwin", "Jonathan", "Darwin", "1999-02-17"))
        userList.add(UserDTO("andy", "Andy", "", "2000-02-17"))
        userList.add(UserDTO("siapayak", "Siapa", "Yak", "2022-02-17"))

        ts.assertNoErrors()
        ts.assertValue(userList)
    }

    @Test
    fun `get users with full name success`() {
        val ts = TestObserver.create<List<UserDTO>>()
        mainRepository.getUsersWithFullName().subscribe(ts)

        userList.clear()
        userList.add(UserDTO("jonathandarwin", "Jonathan", "Darwin", "1999-02-17"))
        userList.add(UserDTO("siapayak", "Siapa", "Yak", "2022-02-17"))

        ts.assertValue(userList)
    }
}