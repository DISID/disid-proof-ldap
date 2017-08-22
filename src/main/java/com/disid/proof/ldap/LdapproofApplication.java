package com.disid.proof.ldap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * = LdapproofApplication
 *
 * TODO Auto-generated class documentation
 *
 */
@SpringBootApplication
@EnableScheduling
public class LdapproofApplication {

    /**
     * TODO Auto-generated method documentation
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(LdapproofApplication.class, args);
    }
}