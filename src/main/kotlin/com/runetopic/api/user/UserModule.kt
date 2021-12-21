package com.runetopic.api.user

import org.koin.dsl.module

/**
 * @author Jordan Abraham
 */
fun userModule() = module {
    single { UserService(UserStorage("api")) }
}
