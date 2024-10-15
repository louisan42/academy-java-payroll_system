package com.bptn.services;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class AuthenticatorService implements Authenticator{

    /*
     * Argon2passwordEncoder take parameters
     * - saltLength : - set to 12
     * - hashLength : - set to 32 byte (256 bit) hash
     * - parallelism (number of threads): - 2
     * - memory : - set to 4Mib
     * - iteration : - 2
     * see https://snyk.io/blog/password-hashing-java-applications/
     */
    private static final Argon2PasswordEncoder encoder =
            new Argon2PasswordEncoder(12, 32, 2, 4 * 1024, 2);

    @Override
    /**
     * Returns a hash form of the string passed as a parameter
     * using Argon2id crypto framework
     * @param password
     * @return a hash String of the password
     */
    public String hashPassword(String password) {

        return encoder.encode(password);
    }

    @Override
    public boolean verifyPassword(String password,String encodedPassword){

        return encoder.matches(password, encodedPassword);
    }
}
