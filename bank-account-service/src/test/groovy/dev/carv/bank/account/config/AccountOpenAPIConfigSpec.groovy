package dev.carv.bank.account.config

import io.swagger.v3.oas.models.OpenAPI
import spock.lang.Specification

class AccountOpenAPIConfigSpec extends Specification {

    def "accountOpenAPI bean should configure OpenAPI specification correctly"() {
        given:
        def config = new AccountOpenAPIConfig()

        when:
        OpenAPI openAPI = config.accountOpenAPI()

        then:
        openAPI != null
        openAPI.info != null
        openAPI.info.title == "Accounts REST API Documentation"
        openAPI.info.version == "v1"
        openAPI.info.contact != null
        openAPI.info.contact.name == "Carlos Rosas"
        openAPI.info.contact.email == "carloschars@gmail.com"
        openAPI.info.license != null
        openAPI.info.license.name == "Apache 2.0"
        openAPI.externalDocs != null
        openAPI.externalDocs.url == "https://bank-carv.com/account-service/swagger-ui.html"
    }
}
