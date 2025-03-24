package no.nav.tilleggsstonader.kontrakter.søknad.boutgifter.fyllutsendinn

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.tilleggsstonader.kontrakter.FileUtil
import no.nav.tilleggsstonader.kontrakter.felles.ObjectMapperProvider.objectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.Locale

/**
 * Hjelpemetoder for å hente skjema-struktur og generere eksempel-json og kotlin data-klasser
 * for å enkelt kunne oppdatere kontrakter
 *
 * skjema.json inneholder FyllUt-skjemabygger-skjemaet fra
 * https://skjemadelingslenke.ekstern.dev.nav.no/fyllut/api/forms/nav111219
 *
 * Kommenter ut [Disabled] og kjør testene.
 * * Sett [FileUtil.SKAL_SKRIVE_TIL_FIL] til true
 * * Hent skjema [hentSkjema]
 * * Print eksempel-json [printEksempelJson]
 * * Print data classes [printDataClasses] og kopier til [SkjemaBoutgifter]
 */
//@Disabled
class FyllUtSendInnSkjemaParser {
    private val skjema =
        objectMapper
            .readValue<FyllUtSendInnSkjema>(FileUtil.readFile("søknad/boutgifter/skjema.json"))
    private val om = objectMapper.writerWithDefaultPrettyPrinter()

    /**
     * Printer eksempel på hvordan json kan se ut med alle felter
     */
    @Test
    fun printEksempelJson() {
        val jsonStruktur = JsonStrukturGenerator(skjema.relevanteKomponenter()).genererJsonStruktur()
        val json = om.writeValueAsString(jsonStruktur)
        assertThat(FileUtil.SKAL_SKRIVE_TIL_FIL).isTrue()
        FileUtil.skrivTilFil("søknad/boutgifter/skjema-eksempel.json", json)
    }

    /**
     * Printer data classes for å erstatte alt under [SkjemaBoutgifter]
     */
    @Test
    fun printDataClasses() {
        KotlinDataClassMapper(skjema.relevanteKomponenter()).printKotlinDataClasses()
    }

    /**
     * Prøver å finne ugyldige conditionals i tilfelle man har oppdatert et eller annet felt men ikke oppdatert en conditional
     * Virker ikke helt 100% men kanskje halvveis
     */
    @Test
    fun `printer ugyldige conditionals`() {
        ConditionalsValidering.printUgyldigeConditionals(skjema.relevanteKomponenter())
    }

    @Test
    fun `print alle customConditional`() {
        fun SkjemaKomponent.print() {
            if (!customConditional.isNullOrBlank()) {
                println("key=$key customConditional=$customConditional")
            }
            components?.forEach { it.print() }
        }
        skjema.components.forEach { it.print() }
    }

    @Test
    fun `print alle conditionals`() {
        fun SkjemaKomponent.print() {
            conditional.whenStr?.takeIf { it.isNotBlank() }?.let {
                println("key=$key conditional=$it eq=${conditional.eq}")
            }
            components?.forEach { it.print() }
        }
        skjema.components.forEach { it.print() }
    }

    @Test
    fun hentSkjema() {
        assertThat(FileUtil.SKAL_SKRIVE_TIL_FIL).isTrue()
        val request =
            HttpRequest
                .newBuilder()
                .uri(URI("https://skjemadelingslenke.ekstern.dev.nav.no/fyllut/api/forms/nav111219"))
                .timeout(Duration.ofSeconds(3))
                .GET()
                .build()
        val response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() == 200) {
            println("Skriver response til skjema.json")
            try {
                val parsedJson = objectMapper.readTree(response.body())
                FileUtil.skrivTilFil("søknad/boutgifter/skjema.json", om.writeValueAsString(parsedJson))
            } catch (e: Exception) {
                println(response.body())
                throw e
            }
        } else {
            error("Feilet henting av skjema response=$response")
        }
    }
}

private data class FyllUtSendInnSkjema(
    val components: List<SkjemaKomponent>,
) {
    fun relevanteKomponenter(): List<SkjemaKomponent> = components.filterNot { ignorerteKeys.contains(it.key) }

    companion object {
        val ignorerteKeys =
            setOf(
                "vedlegg",
                "personopplysninger",
                "veiledning",
            )
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
private data class SkjemaKomponent(
    val key: String,
    val type: String?,
    val components: List<SkjemaKomponent>?,
    val values: List<Value>?,
    val inputType: Any?,
    val conditional: Conditional,
    val multiple: Boolean,
    val customConditional: String?,
) {
    fun skalIgnoreres() = type == "alertstripe" || type == "htmlelement"

    fun erPåkrevd(): Boolean = conditional.isRequired() && customConditional.isNullOrBlank()

    data class Value(
        val label: String,
        val value: String, // key
    )

    data class Conditional(
        val eq: String,
        @JsonProperty("when")
        val whenStr: String?,
    ) {
        fun isRequired() = this.eq == ""
    }
}

/**
 * Genererer jsonstruktur for fyllUt-schema
 */
private class JsonStrukturGenerator(
    private val components: List<SkjemaKomponent>,
) {
    fun genererJsonStruktur(): Map<String, Any> =
        components
            .flatMap { it.genererJsonStruktur() }
            .toMap()

    private fun SkjemaKomponent.genererJsonStruktur(): List<Pair<String, Any>> {
        require(components != null) {
            "Feiler mapComponentsToExampleSkjema for $this"
        }
        return components
            .filterNot { it.skalIgnoreres() }
            .flatMap { component ->
                if (component.type == "navSkjemagruppe") {
                    component.genererJsonStruktur()
                } else {
                    listOf(component.key to component.jsonStruktur())
                }
            }
    }

    private fun SkjemaKomponent.jsonStruktur(): Any =
        when {
            // spesialhåndtering for egen komponent som ikke vises tydelig i skjema.json
            key == "aktiviteterOgMaalgruppe" -> {
                val periode = mapOf("fom" to "2025-01-01", "tom" to "2025-01-01")
                val aktivitet =
                    mapOf(
                        "aktivitetId" to "123",
                        "periode" to periode,
                        "maalgruppe" to
                            mapOf(
                                "maalgruppetype" to "NEDSARBEVN",
                                "gyldighetsperiode" to periode,
                                "maalgruppenavn" to "maalgruppenavn",
                            ),
                        "text" to "Jeg får ikke opp noen aktiviteter her som stemmer med det jeg vil søke om",
                    )
                mapOf("aktivitet" to aktivitet)
            }
            // spesialhåndtering for egen komponent som ikke vises tydelig i skjema.json
            type == "landvelger" -> mapOf("value" to "AF", "label" to "Afghanistan")
            // Datagrid har en liste med svar {key: [{key: ...}]}
            type == "datagrid" -> listOf(genererJsonStruktur().toMap())
            // container wrapper andre deler
            type == "container" -> genererJsonStruktur().toMap()
            // navSkjemagruppe wrapper andre deler
            type == "navSkjemagruppe" -> components!!.filterNot { it.skalIgnoreres() }.map { it.jsonStruktur() }
            // Radiopanel har 1 svar, {key: svar}
            type == "radiopanel" -> values!!.first().value
            // Selectboxes har flere svar som har et svar for hvert valg {key: {svar1: boolean, svar2: boolean}}
            type == "selectboxes" -> values!!.associate { it.value to true }
            type == "currency" -> 100
            type == "navDatepicker" -> "2025-01-01"
            type == "textfield" -> "EksempelSvar"
            else -> error("Har ikke mapping for $this")
        }
}

/**
 * Mapper skjema til Kotlin data class for å kunne oppdatere klasser under [SkjemaBoutgifter]
 */
private class KotlinDataClassMapper(
    private val components: List<SkjemaKomponent>,
) {
    val klassedefinisjoner = mutableListOf<Pair<String, Map<String, String>>>()
    val enumdefinisjoner = mutableListOf<Pair<String, Set<String>>>()

    fun printKotlinDataClasses() {
        generer()
        print()
    }

    private fun generer() {
        klassedefinisjoner.add("SkjemaBoutgifter" to components.flatMap { it.genererDataClasses() }.toMap())
    }

    private fun print() {
        klassedefinisjoner.reversed().distinct().forEach {
            println("data class ${it.first}(")
            it.second.forEach { (key, value) ->
                println("  val $key: ${value.storFørsteBokstav()},")
            }
            println(")")
            println("")
        }
        enumdefinisjoner.distinct().forEach {
            println("enum class ${it.first}{")
            it.second.forEach { value ->
                println("  $value,")
            }
            println("}")
            println("")
        }
    }

    private fun SkjemaKomponent.genererDataClasses(): List<Pair<String, String>> {
        require(components != null) {
            "Feiler mapComponents for $this"
        }
        return components
            .filterNot { it.skalIgnoreres() }
            .flatMap { component ->
                if (component.type == "navSkjemagruppe") {
                    component.genererDataClasses()
                } else {
                    listOf(component.key to component.felttype(component.mapKlassNavnOgFelter()))
                }
            }
    }

    private fun SkjemaKomponent.mapKlassNavnOgFelter(): String =
        when {
            // spesialhåndtering for egen komponent som ikke vises tydelig i skjema.json
            key == "aktiviteterOgMaalgruppe" -> {
                val aktivitet =
                    mapOf(
                        "aktivitetId" to "String",
                        "text" to "String",
                        "periode" to "Periode?",
                        "maalgruppe" to "Målgruppe?",
                    )
                val målgruppe =
                    mapOf(
                        "maalgruppetype" to "String",
                        "gyldighetsperiode" to "Periode",
                        "maalgruppenavn" to "String",
                    )
                klassedefinisjoner.add("Periode" to mapOf("fom" to "LocalDate", "tom" to "LocalDate"))
                klassedefinisjoner.add("Målgruppe" to målgruppe)
                klassedefinisjoner.add("Aktivitet" to aktivitet)
                klassedefinisjoner.add("AktiviteterOgMålgruppe" to mapOf("aktivitet" to "Aktivitet"))
                "AktiviteterOgMålgruppe"
            }
            // Radiopanel har 1 svar, {key: svar}
            type == "radiopanel" -> "String"
            // Selectboxes har flere svar som har et svar for hvert valg {key: {svar1: boolean, svar2: boolean}}
            type == "selectboxes" -> {
                val type = "${key}Type"
                enumdefinisjoner.add(type.klassenavn() to values!!.map { it.value }.toSet())
                "Map<${type.klassenavn()}, Boolean>"
            }

            type == "currency" -> "Int"
            type == "textfield" -> "String"
            type == "navDatepicker" -> "LocalDate"
            type == "landvelger" -> {
                klassedefinisjoner.add("Landvelger" to mapOf("value" to "String", "label" to "String"))
                "Landvelger"
            }

            type == "datagrid" || type == "container" || type == "navSkjemagruppe" -> {
                this.leggTilDataClassMapping(key)
                key
            }

            else -> error("Har ikke mapping for $this")
        }

    private fun SkjemaKomponent.leggTilDataClassMapping(klassenavn: String) {
        klassedefinisjoner.add(klassenavn.klassenavn() to genererDataClasses().toMap())
    }

    private fun String.klassenavn(): String =
        when (this.lowercase()) {
            "OppholdUtenforNorgeSiste12mnd".lowercase(),
            "OppholdUtenforNorgeNeste12mnd".lowercase(),
            -> "OppholdUtenforNorge"
            else -> this
        }.storFørsteBokstav()

    private fun SkjemaKomponent.felttype(klassenavn: String): String {
        var type = ""
        if (multiple || this.type == "datagrid") {
            type += "List<${klassenavn.klassenavn()}>"
        } else {
            type += klassenavn.klassenavn()
        }
        if (!erPåkrevd()) {
            type += "?"
        }
        return type
    }

    private fun String.storFørsteBokstav() = replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}

/**
 * Printer alle ugyldige conditionals
 * Uklart hvor godt den virker
 */
private object ConditionalsValidering {
    data class Parent(
        val component: SkjemaKomponent,
        val parent: Parent?,
    ) {
        fun inneholderKey(key: String): Boolean =
            parent?.component?.components?.any { it.key == key } == true ||
                parent?.inneholderKey(key) == true
    }

    fun printUgyldigeConditionals(components: List<SkjemaKomponent>) {
        val alleNøkler = components.flatMap { it.alleNøkler("data") }.toSet()
        // alleNøkler.forEach { println(it) }
        // println("####")
        components.forEach { it.validerConditionals(alleNøkler, Parent(it, null)) }
    }

    private fun SkjemaKomponent.alleNøkler(prefix: String): Set<String> {
        // panel og navSkjemagruppe skal ikke ha med key som nøkkel då de kun er for å gruppere komponenter
        val key = if (this.type == "panel" || this.type == "navSkjemagruppe") prefix else "$prefix.$key"
        val nøklerComponents = components?.flatMap { it.alleNøkler(key) }?.toSet() ?: emptySet()
        val nøklerValues = values?.map { "$key.${it.value}" }?.toSet() ?: emptySet()
        // activities custom komponent som ikke har med delene i skjema
        val spesialNøkler: Set<String> = if (type == "activities") setOf("$key.aktivitetId") else emptySet()
        return setOf(key) + nøklerComponents + nøklerValues + spesialNøkler
    }

    private fun SkjemaKomponent.validerConditionals(
        alleNøkler: Set<String>,
        parent: Parent,
    ) {
        val whenStr = conditional.whenStr
        if (!whenStr.isNullOrBlank()) {
            if (whenStr.contains(".")) {
                // whenStr inneholder ikke prefix "data." som customConditional
                val gyldig = alleNøkler.contains("data.$whenStr")
                if (!gyldig) {
                    println("$key conditional nøkkelSomMangler=$whenStr")
                }
            } else {
                // hvis whenStr ikke inneholder . må man sjekke om parent inneholder key
                val gyldig = parent.inneholderKey(key)
                if (!gyldig) {
                    println("$key conditional nøkkelSomMangler=$whenStr")
                }
            }
        }
        customConditional?.takeIf { it.isNotBlank() }?.let { customConditional ->
            // regex finner alle ord inkl punktum og ". " er interessant då vi ønsker å filtrere vekk de.
            val matchResults =
                "([\"\\w.]+)"
                    .toRegex()
                    .findAll(customConditional)
                    .flatMap { it.groupValues }
                    .filterNot { it == "show" || it == "true" || it == "false" }
                    .filterNot { it.contains("\"") }
                    .toSet()
            matchResults.forEach {
                if (it.contains(".")) {
                    val gyldig = alleNøkler.contains(it)
                    if (!gyldig) {
                        println("$key customConditional nøkkelSomMangler=$it")
                    }
                } else {
                    println("customConditional $it")
                }
            }
        }
        components?.forEach { it.validerConditionals(alleNøkler, Parent(it, parent)) }
    }
}
