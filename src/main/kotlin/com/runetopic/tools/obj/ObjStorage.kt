package com.runetopic.tools.obj

import java.util.*

/**
 * @author Jordan Abraham
 */
object ObjStorage {
    val storage = Collections.synchronizedSet(mutableSetOf<Obj>())
}
