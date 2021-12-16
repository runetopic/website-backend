package com.runetopic.user

import org.koin.dsl.module

/**
 * @author Jordan Abraham
 */
fun userModule() = module {
    single { UserStorage }
    single { UserService }
}
