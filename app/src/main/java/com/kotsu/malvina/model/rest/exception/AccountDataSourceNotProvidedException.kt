package com.kotsu.malvina.model.rest.exception

import java.lang.RuntimeException


class AccountDataSourceNotProvidedException(message: String) : RuntimeException(message) {
}