package com.example.HateConscription.dateData

import android.content.ContentValues.TAG
import android.util.Log
import androidx.datastore.core.DataStore
import com.example.HateConscription.calendar.DateNDaysDataState
import kotlinx.coroutines.flow.catch
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStateRepository @Inject constructor (
    private val dataStateStore: DataStore<DateNDaysDataState>
): DataRepository {

    override suspend fun getData() = dataStateStore.data
            .catch { exception ->
                if (exception is IOException) {
                    Log.e(TAG, "Error reading data state.", exception)
                    emit(DateNDaysDataState())
                } else {
                    throw exception
                }
            }



    override suspend fun setData(
        id: Int,
        birthdaySelect: String,
        enlistmentDaySelect: String,
        dDay: String,
        day2Leave: String,
        show: Boolean,
    ) {
        dataStateStore.updateData { value ->
            value.copy(
                id = id,
                birthdaySelect = birthdaySelect,
                enlistmentDaySelect = enlistmentDaySelect,
                dDay = dDay,
                day2Leave = day2Leave,
                show = show,
            )
        }
    }
}