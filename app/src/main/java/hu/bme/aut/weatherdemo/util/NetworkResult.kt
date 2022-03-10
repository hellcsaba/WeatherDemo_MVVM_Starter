package hu.bme.aut.weatherdemo.util

import java.lang.Exception

sealed class NetworkResponse<out T: Any>

class NetworkResult<K: Any>(val result: K): NetworkResponse<K>()
class NetworkErrorResult(val errorMsg: Exception): NetworkResponse<Nothing>()
