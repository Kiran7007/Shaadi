package com.peopleinteractive.shaadi.ui.people

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.peopleinteractive.shaadi.data.db.entity.People
import com.peopleinteractive.shaadi.data.repository.PeopleRepository
import com.peopleinteractive.shaadi.util.TestCoroutineRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PeopleViewModelTest : TestCase() {

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: PeopleRepository

    @Mock
    private lateinit var observer: Observer<List<People>>

    @Before
    fun setup() {
    }

    @Test
    fun testRequestPeoples_whenFetchDataFromRemoteORLocalDatabase_thenCheckPeopleList() {
        testCoroutineRule.runBlockingTest {
            doReturn(flowOf { emptyList<People>() })
                .`when`(repository)
                .fetchDataFromDB()

            val viewModel = PeopleViewModel(repository)
            viewModel.peoples.observeForever(observer)
            viewModel.peopleIntent.send(PeopleIntent.FetchLocalPeople)
            verify(repository).fetchRemotePeoples()
            viewModel.peoples.removeObserver(observer)
        }
    }
}