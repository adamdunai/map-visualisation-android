package com.example.mapvisualisation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Collects flow and returns collected values as a list. Also returns the job so it can be cancelled.
 * First cancel the job and then you can assert the list.
 */
fun <T> Flow<T>.test(scope: CoroutineScope): Pair<List<T>, Job> {
    val results = mutableListOf<T>()
    val job = scope.launch {
        this@test.collect { value ->
            results.add(value)
        }
    }
    return results to job
}
