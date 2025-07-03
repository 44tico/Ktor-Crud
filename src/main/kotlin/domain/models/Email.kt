package com.joao.domain.models

class Email(private val value: String) {
    init {
        require(value.contains("@") && value.contains(".")) {
            "Email inválido."
        }
    }

    fun getValue(): String = value
}