package dev.carv.bank.loan.service

import dev.carv.bank.commons.exception.ResourceAlreadyExistsException
import dev.carv.bank.commons.exception.ResourceNotFoundException
import dev.carv.bank.loan.constant.LoanType
import dev.carv.bank.loan.dto.LoanDto
import dev.carv.bank.loan.entity.LoanEntity
import dev.carv.bank.loan.mapper.LoanMapper
import dev.carv.bank.loan.repository.LoanRepository
import dev.carv.bank.loan.service.impl.LoanServiceImpl
import spock.lang.Specification

class LoanServiceImplSpec extends Specification {

    LoanMapper mapper = Mock()
    LoanRepository repository = Mock()
    LoanServiceImpl service = new LoanServiceImpl(mapper, repository)

    static final String MOBILE_NUMBER = "525512345678"
    static final String LOAN_NUMBER = "75255123456758"

    def "createLoan should save a new loan when mobile number does not exist"() {
        given:
        repository.existsByMobileNumber(MOBILE_NUMBER) >> false
        LoanEntity capturedLoan = null

        when:
        service.createLoan(MOBILE_NUMBER)

        then:
        1 * repository.save(_) >> { LoanEntity it -> capturedLoan = it }
        capturedLoan != null
        capturedLoan.getMobileNumber() == MOBILE_NUMBER
        capturedLoan.getLoanNumber() != null
        capturedLoan.getType() != null
        capturedLoan.getTotalLoan() > BigDecimal.ZERO
        capturedLoan.getPaidLoan().compareTo(BigDecimal.ZERO) == 0
        capturedLoan.getOutstandingLoan() == capturedLoan.getTotalLoan()
    }

    def "createLoan should throw ResourceAlreadyExistsException when mobile number already exists"() {
        given:
        repository.existsByMobileNumber(MOBILE_NUMBER) >> true

        when:
        service.createLoan(MOBILE_NUMBER)

        then:
        def ex = thrown(ResourceAlreadyExistsException)
        ex.message.contains("Loan")
        ex.message.contains("mobileNumber")
        ex.message.contains(MOBILE_NUMBER)
        0 * repository.save(_)
    }

    def "fetchLoan should return LoanDto when loan is found"() {
        given:
        def entity = createLoanEntity()
        def expectedDto = createLoanDto()
        repository.findByMobileNumber(MOBILE_NUMBER) >> Optional.of(entity)
        mapper.toDto(entity) >> expectedDto

        when:
        def actualDto = service.fetchLoan(MOBILE_NUMBER)

        then:
        actualDto == expectedDto
    }

    def "fetchLoan should throw ResourceNotFoundException when loan does not exist"() {
        given:
        repository.findByMobileNumber(MOBILE_NUMBER) >> Optional.empty()

        when:
        service.fetchLoan(MOBILE_NUMBER)

        then:
        def ex = thrown(ResourceNotFoundException)
        ex.message.contains("Loan")
        ex.message.contains("mobileNumber")
        ex.message.contains(MOBILE_NUMBER)
        0 * mapper._
    }

    def "updateLoan should update entity fields and return true when loan exists"() {
        given:
        def dto = createLoanDto()
        def entity = createLoanEntity()
        repository.findByLoanNumber(dto.loanNumber()) >> Optional.of(entity)

        when:
        def updated = service.updateLoan(dto)

        then:
        updated == true
        entity.getMobileNumber() == dto.mobileNumber()
        entity.getLoanNumber() == dto.loanNumber()
        entity.getType() == LoanType.valueOf(dto.type())
        entity.getTotalLoan() == dto.totalLoan()
        entity.getPaidLoan() == dto.paidLoan()
        entity.getOutstandingLoan() == dto.outstandingLoan()
    }

    def "updateLoan should throw ResourceNotFoundException when loan does not exist"() {
        given:
        def dto = createLoanDto()
        repository.findByLoanNumber(dto.loanNumber()) >> Optional.empty()

        when:
        service.updateLoan(dto)

        then:
        def ex = thrown(ResourceNotFoundException)
        ex.message.contains("Loan")
        ex.message.contains("loanNumber")
        ex.message.contains(dto.loanNumber())
    }

    def "deleteLoan should delete loan by id and return true when loan exists"() {
        given:
        def entity = createLoanEntity()
        repository.findByMobileNumber(MOBILE_NUMBER) >> Optional.of(entity)

        when:
        def deleted = service.deleteLoan(MOBILE_NUMBER)

        then:
        deleted == true
        1 * repository.deleteById(entity.getId())
    }

    def "deleteLoan should throw ResourceNotFoundException when loan does not exist"() {
        given:
        repository.findByMobileNumber(MOBILE_NUMBER) >> Optional.empty()

        when:
        service.deleteLoan(MOBILE_NUMBER)

        then:
        def ex = thrown(ResourceNotFoundException)
        ex.message.contains("Loan")
        ex.message.contains("mobileNumber")
        ex.message.contains(MOBILE_NUMBER)
        0 * repository.deleteById(_)
    }

    private LoanEntity createLoanEntity() {
        def entity = new LoanEntity()
        entity.setId(UUID.randomUUID())
        entity.setMobileNumber(MOBILE_NUMBER)
        entity.setLoanNumber(LOAN_NUMBER)
        entity.setType(LoanType.HOME)
        entity.setTotalLoan(new BigDecimal("100000.00"))
        entity.setPaidLoan(new BigDecimal("10000.00"))
        entity.setOutstandingLoan(new BigDecimal("90000.00"))
        entity
    }

    private LoanDto createLoanDto() {
        new LoanDto(
            MOBILE_NUMBER,
            LOAN_NUMBER,
            LoanType.HOME.name(),
            new BigDecimal("100000.00"),
            new BigDecimal("10000.00"),
            new BigDecimal("90000.00")
        )
    }
}
