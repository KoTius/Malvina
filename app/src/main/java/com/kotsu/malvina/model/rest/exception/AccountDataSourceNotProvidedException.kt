package com.kotsu.malvina.model.rest.exception

import java.lang.RuntimeException


@Deprecated("Will never be thrown")
class AccountDataSourceNotProvidedException(message: String) : RuntimeException(message) {
}