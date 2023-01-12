package com.example.neosoftassignment.network

data class CallState<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        // In case of Success,set status as
        // Success and data as the response
        fun <T> success(data: T?): CallState<T> {
            return CallState(Status.SUCCESS, data, null)
        }

        // In case of failure ,set state to Error ,
        // add the error message,set data to null
        fun <T> error(msg: String): CallState<T> {
            return CallState(Status.ERROR, null, msg)
        }

        // In case of failure ,set state to Error ,
        // add the error message,we can pass data also if required
        fun <T> error(msg: String, data: T?): CallState<T> {
            return CallState(Status.ERROR, data, msg)
        }

        // When the call is loading set the state
        // as Loading and rest as null
        fun <T> loading(): CallState<T> {
            return CallState(Status.LOADING, null, null)
        }
    }
}


// An enum to store the
// current state of api call
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}
