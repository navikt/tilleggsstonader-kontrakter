package no.nav.tilleggsstonader.kontrakter.felles

import tools.jackson.databind.DeserializationFeature
import tools.jackson.databind.json.JsonMapper
import tools.jackson.module.kotlin.KotlinFeature
import tools.jackson.module.kotlin.jacksonMapperBuilder

object ObjectMapperProvider {
    // Uten KotlinPropertyNameAsImplicitName så vil ikke properties som starter med non-ascii bokstaver blir fjernet
    private fun lagJsonMapper() =
        jacksonMapperBuilder { configure(KotlinFeature.KotlinPropertyNameAsImplicitName, true) }
            .enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)

    val jsonMapper: JsonMapper =
        lagJsonMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .build()

    val jsonMapperFailOnUnknownProperties: JsonMapper =
        lagJsonMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
            .build()
}
