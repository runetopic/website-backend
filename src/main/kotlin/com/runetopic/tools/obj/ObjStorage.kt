package com.runetopic.tools.obj

import java.util.*

/**
 * @author Jordan Abraham
 */
class ObjStorage {
    val storage = Collections.synchronizedSet(mutableSetOf<Obj>())
}
