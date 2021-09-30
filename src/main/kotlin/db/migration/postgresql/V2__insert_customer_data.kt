package db.migration.postgresql

import com.vomoxada.rsocketmicroservicecustomer.domain.enums.Gender
import com.vomoxada.rsocketmicroservicecustomer.domain.enums.Segment
import org.apache.commons.csv.CSVFormat
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.flywaydb.core.internal.jdbc.JdbcTemplate
import org.slf4j.LoggerFactory
import java.io.FileReader
import java.time.LocalDate
import java.util.UUID

class V2__insert_customer_data : BaseJavaMigration() {
    private val logger = LoggerFactory.getLogger(V2__insert_customer_data::class.java)
    private val csvParser = CSVFormat.Builder
        .create(CSVFormat.EXCEL)
        .setHeader("id", "email", "first_name", "last_name", "birthdate", "gender", "profession_id")
        .setSkipHeaderRecord(true)
        .build()

    override fun migrate(context: Context) {
        val jdbcTemplate = JdbcTemplate(context.connection)

        val dataFile = FileReader(javaClass.classLoader.getResource("customer-table-data.csv").path)

        val csvRecords = csvParser.parse(dataFile).records
        logger.info("Started to migrate ${csvRecords.size} rows of customer data.")

        csvRecords.forEach {
            val id = UUID.fromString(it.get("id"))
            val email = it.get("email")
            val firstName = it.get("first_name")
            val lastName = it.get("last_name")
            val birthdate = LocalDate.parse(it.get("birthdate"))
            val gender = Gender.valueOf(it.get("gender"))
            val professionId = UUID.fromString(it.get("profession_id"))
            val segment = Segment.values().random()

            jdbcTemplate.executeStatement(
                "insert into customer (id, email,first_name,last_name,birthdate,gender,profession_id,segment) values(" +
                        "'${id}'," +
                        "'${email.escapeSingleQuote()}'," +
                        "'${firstName.escapeSingleQuote()}'," +
                        "'${lastName.escapeSingleQuote()}'," +
                        "'${birthdate}'," +
                        "'${gender}'," +
                        "'${professionId}'," +
                        "'${segment}')"
            )
        }
        logger.info("Finished migration of customer data.")
    }
}

private fun String.escapeSingleQuote(): String {
    return this.replace("'", "''")
}
