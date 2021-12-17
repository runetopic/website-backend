package com.runetopic.api.tools.obj

import java.util.*

/**
 * @author Jordan Abraham
 */
object ObjStorage {
    val storage = Collections.synchronizedList(mutableListOf<Obj>())
}
