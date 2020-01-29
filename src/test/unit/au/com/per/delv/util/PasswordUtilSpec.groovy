package au.com.per.delv.util

import spock.lang.Shared
import spock.lang.Specification

import static au.com.per.delv.util.PasswordUtil.*

class PasswordUtilSpec extends Specification{

	def "password match is correct"() {
        given:
        def password = "password"

        when:
        def salt = generateSalt()
		def passwordEntrypted = encryptPassword(password, salt)

        then:
        assert comparePassword(password, passwordEntrypted, salt)    
    }

	def "password match is incorrect"() {
        given:
        def password = "password"
		def passwordIncorrect = "password!"

        when:
        def salt = generateSalt()
		def passwordEntrypted = encryptPassword(password, salt)

        then:
        assert !comparePassword(passwordIncorrect, passwordEntrypted, salt)        
    }

}
