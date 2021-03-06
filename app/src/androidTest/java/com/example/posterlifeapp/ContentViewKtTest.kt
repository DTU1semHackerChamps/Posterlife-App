package com.example.posterlifeapp

import androidx.activity.viewModels
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.core.app.ApplicationProvider
import io.paperdb.Paper
import io.paperdb.Paper.book
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.junit.Assert.assertNotEquals
import org.junit.Test

class ContentViewKtTest : TestCase() {

    @Test
    fun testSyncCart() {
        Paper.init(ApplicationProvider.getApplicationContext())
        Paper.book().destroy()
        val contentViewModel = ContentViewModel()

        GlobalScope.launch(Dispatchers.Main) {
            async {
                SyncCart(
                    "Levende · Søren Ulrik Thomsen (1981)",
                    249,
                    "https://posterlife.dk/wp-content/uploads/2019/05/Levende_soeren_ulrik_thomsen.jpg",
                    contentViewModel
                )

                assertEquals("Levende · Søren Ulrik Thomsen (1981)",book().read<List<String>>("Titles")!![0])
                assertEquals(249,book().read<List<Int>>("Prices")!![0])
                assertEquals(1,book().read<List<Int>>("Quantity")!![0])
                assertEquals("https://posterlife.dk/wp-content/uploads/2019/05/Levende_soeren_ulrik_thomsen.jpg",book().read<List<String>>("imageuris")!![0])

                SyncCart(
                    "Levende · Søren Ulrik Thomsen (1981)",
                    249,
                    "https://posterlife.dk/wp-content/uploads/2019/05/Levende_soeren_ulrik_thomsen.jpg",
                    contentViewModel
                )

                assertEquals(2,book().read<List<Int>>("Quantity")!![0])
                assertEquals(mutableStateOf(2).value,contentViewModel.cartAmount.value)


                assertNotEquals("hej",book().read<List<String>>("Titles")!![0])

            }
        }
    }
}

