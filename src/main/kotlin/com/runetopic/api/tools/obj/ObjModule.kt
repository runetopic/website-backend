package com.runetopic.api.tools.obj

import org.koin.dsl.module

/**
 * @author Jordan Abraham
 */
fun objModule() = module {
    single { ObjService(ObjStorage("api")) }
}
