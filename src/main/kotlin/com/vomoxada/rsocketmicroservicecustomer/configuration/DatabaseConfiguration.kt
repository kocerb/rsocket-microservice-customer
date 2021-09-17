package com.vomoxada.rsocketmicroservicecustomer.configuration

import com.vomoxada.rsocketmicroservicecustomer.configuration.properties.DatabaseProperties
import com.vomoxada.rsocketmicroservicecustomer.domain.enums.Gender
import com.vomoxada.rsocketmicroservicecustomer.domain.enums.Segment
import com.vomoxada.rsocketmicroservicecustomer.domain.enums.Status
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.postgresql.codec.EnumCodec
import io.r2dbc.postgresql.extension.CodecRegistrar
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.convert.EnumWriteSupport
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import reactor.core.publisher.Mono

@Configuration
@EnableR2dbcRepositories
@EnableR2dbcAuditing
class DatabaseConfiguration(
    private val databaseProperties: DatabaseProperties
) : AbstractR2dbcConfiguration() {

    @Bean
    override fun connectionFactory(): PostgresqlConnectionFactory {
        return PostgresqlConnectionFactory(
            with(databaseProperties) {
                PostgresqlConnectionConfiguration
                    .builder()
                    .host(host)
                    .port(port.toInt())
                    .database(name)
                    .username(username)
                    .password(password)
                    .codecRegistrar(getCodecRegistrar())
                    .build()
            }
        )
    }

    fun getCodecRegistrar(): CodecRegistrar {
        return EnumCodec.builder()
            .withEnum("status", Status::class.java)
            .withEnum("gender", Gender::class.java)
            .withEnum("segment", Segment::class.java)
            .build()
    }

    override fun getCustomConverters(): List<EnumWriteSupport<out Enum<*>>> {
        return listOf(
            enumConverterOf<Status>(),
            enumConverterOf<Gender>(),
            enumConverterOf<Segment>()
        )
    }

    @Bean
    fun auditorAware(): ReactiveAuditorAware<String>? {
        return ReactiveAuditorAware { Mono.empty() }
    }
}

inline fun <reified T : Enum<T>?> enumConverterOf(): EnumWriteSupport<T> {
    return object : EnumWriteSupport<T>() {}
}

