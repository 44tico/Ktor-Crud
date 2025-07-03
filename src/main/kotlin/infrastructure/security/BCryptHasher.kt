package com.joao.infrastructure.security

import com.joao.domain.services.PasswordHasher
import org.mindrot.jbcrypt.BCrypt

class BCryptHasher : PasswordHasher {
    override fun hash(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

}