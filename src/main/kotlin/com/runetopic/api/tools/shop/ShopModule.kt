package com.runetopic.api.tools.shop

import org.koin.dsl.module

/**
 * @author Jordan Abraham
 */
fun shopModule() = module {
    single { ShopService(ShopStorage("api")) }
}
