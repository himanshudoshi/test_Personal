package ca.bell.nmf.ui

import java.lang.RuntimeException

class InvalidFontProfileException(override val message: String?, severity: Int): RuntimeException(message) {

    //TODO for tracking Severity should be used for tracking

    constructor(message: String?): this(message, NORMAL)

    companion object {
        val SEVERE = 5
        val HIGH = 4
        val NORMAL = 3
        val LOW = 2
        val LOWEST = 1
    }

}