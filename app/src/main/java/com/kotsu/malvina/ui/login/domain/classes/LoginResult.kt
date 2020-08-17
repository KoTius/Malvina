package com.kotsu.malvina.ui.login.domain.classes


abstract class LoginResult

class LoginSuccess(
    val authToken: String
) : LoginResult()

class LoginFailure(
    val errors: List<LoginError>
) : LoginResult()

class LoginNetworkError : LoginResult()