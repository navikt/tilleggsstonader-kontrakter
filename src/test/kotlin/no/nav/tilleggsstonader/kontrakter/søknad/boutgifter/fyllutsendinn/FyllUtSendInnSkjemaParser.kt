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

    /**
     * Printer alle conditionals for betinget visning
     */
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

    /**
     * Printer alle custom conditionals for avansert betinget visning
     */
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

    /**
     * Henter og lagrer skjema fra skjemabyggeren (bygget på https://form.io/)
     * Skriver kun definierte felt i [FyllUtSendInnSkjema] til fil.
     * Skriver ut hela skjemat i console i tilfelle det er noen andre felt som kan være interessante
     *
     * Filtrer vekk personopplysninger då den delen ikke er så interessant då den er ifylt automatisk
     * Personopplysninger ignoreres i [SkjemaBoutgifter] då man ignorerer "dineOpplysninger" som grupperer personopplysninger
     */
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
                val ignorerteKeys = setOf("personopplysninger", "veiledning")
                val parsedJson = objectMapper.readValue<FyllUtSendInnSkjema>(response.body())
                    .let { it.copy(components = it.components.filterNot { it.key in ignorerteKeys }) }

                FileUtil.skrivTilFil("søknad/boutgifter/skjema.json", om.writeValueAsString(parsedJson))
                // Printer hela skjemat i console
                println(om.writeValueAsString(objectMapper.readTree(response.body())))
            } catch (e: Exception) {
                println(response.body())
                throw e
            }
        } else {
            error("Feilet henting av skjema response=$response")
        }
    }
}

/**
 * Definisjon av skjema
 */
private data class FyllUtSendInnSkjema(
    val name: String,
    val revision: String,
    val skjemanummer: String,
    val components: List<SkjemaKomponent>,
) {

    /**
     * Når man skal skrive ut eksempel og dataklasser trenger man ikke å ha med vedlegg då de ikke er med i json-skjema
     * Vedleggen har dog betingede visninger som kan være relevante å kontrollere
     */
    fun relevanteKomponenter(): List<SkjemaKomponent> = components.filterNot { it.key in ignorerteKeys }

    companion object {
        val ignorerteKeys = setOf("vedlegg")
    }
}

/**
 * @param nøkkel på en komponent
 * @param components er komponenter under denne komponenten
 * @param values er liste med alternativer i tilfelle det er en checkbox-group
 *
 * @param conditional er betinget visning, eks hvis denne delen kun skal vises hvis man svart ja på en annen komponent
 * @param customConditional er avansert betinget visning, lik [conditional] men skrives som "show = data.hovedytelse.tiltakspenger === true;"
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
private data class SkjemaKomponent(
    val key: String,
    val type: String?,
    val tree: Boolean?,
    val components: List<SkjemaKomponent>?,
    val values: List<Value>?,
    val inputType: Any?,
    val multiple: Boolean,
    val conditional: Conditional,
    val customConditional: String?,
) {
    fun skalIgnoreres() = type == "alertstripe" || type == "htmlelement"

    fun erPåkrevd(): Boolean = conditional.isRequired() && customConditional.isNullOrBlank()

    /**
     * Brukes som alternativ i tilfelle det er en checkbox-group
     * @param label visningstekst, eks "Arbeidsavklaringspenger (AAP)"
     * @param value verdi på valg, eks "aap"
     */
    data class Value(
        val label: String,
        val value: String, // key
    )

    /**
     * Betinger visning, brukes hvis en komponent skal vises basert på et annet valg
     * @param eq verdi på annet spørsmål, eks "jaPaHjemstedet"
     * @param whenStr peker til annet spørsmål, eks "boligEllerOvernatting.fasteUtgifter.delerDuUtgifterTilBoligMedAndre"
     */
    data class Conditional(
        val eq: String,
        @JsonProperty("when")
        val whenStr: String?,
    ) {
        fun isRequired() = this.eq == ""
    }
}

/**
 * Genererer eksempel på json for skjema
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
                mapOf("aktivitet" to jsonStrukturAktivitet())
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
            inputType == "numeric" -> 100
            type == "navDatepicker" -> "2025-01-01"
            type == "textfield" -> "EksempelSvar"
            else -> error("Har ikke mapping for $this")
        }

    private fun jsonStrukturAktivitet(): Map<String, Any> {
        val periode = mapOf("fom" to "2025-01-01", "tom" to "2025-01-01")
        return mapOf(
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
    }
}

/**
 * Mapper skjema til Kotlin data class for å kunne oppdatere klasser under [SkjemaBoutgifter]
 */
private class KotlinDataClassMapper(
    private val components: List<SkjemaKomponent>,
) {
    private val klassedefinisjoner = mutableListOf<Klassedefinisjon>()
    private val enumdefinisjoner = mutableListOf<Enumdefinisjon>()

    fun printKotlinDataClasses() {
        generer()
        print()
    }

    private fun generer() {
        klassedefinisjoner.add(
            Klassedefinisjon(
                navn = "SkjemaBoutgifter",
                felter = components.flatMap { it.genererDataClasses() })
        )
    }

    private fun print() {
        klassedefinisjoner.reversed().distinct().forEach {
            println("data class ${it.navn}(")
            it.felter.forEach {
                println("  val ${it.felt}: ${it.type.storFørsteBokstav()},")
            }
            println(")")
            println("")
        }
        enumdefinisjoner.distinct().forEach {
            println("enum class ${it.navn}{")
            it.verdier.forEach { verdi ->
                println("  $verdi,")
            }
            println("}")
            println("")
        }
    }

    private fun SkjemaKomponent.genererDataClasses(): List<Felt> {
        require(components != null) {
            "Feiler mapComponents for $this"
        }
        return components
            .filterNot { it.skalIgnoreres() }
            .flatMap { component ->
                if (component.type == "navSkjemagruppe") {
                    component.genererDataClasses()
                } else {
                    listOf(Felt(felt = component.key, type = component.felttype(component.mapKlassNavnOgFelter())))
                }
            }
    }

    private fun SkjemaKomponent.mapKlassNavnOgFelter(): String =
        when {
            key == "aktiviteterOgMaalgruppe" -> {
                leggTilKlassedefinisjonerForAktiviteterOgMålgruppe()
                "AktiviteterOgMålgruppe"
            }
            // Radiopanel har 1 svar, {key: svar}
            type == "radiopanel" -> {
                if (values!!.all { it.value == "ja" || it.value == "nei" }) {
                    enumdefinisjoner.add(Enumdefinisjon(navn = "JaNei", verdier = values.map { it.value }))
                    "JaNeiType"
                } else {
                    val type = "${key}Type"
                    leggTilEnumDefinisjon(type)
                    type
                }
            }
            // Selectboxes har flere svar som har et svar for hvert valg {key: {svar1: boolean, svar2: boolean}}
            type == "selectboxes" -> {
                val type = "${key}Type"
                leggTilEnumDefinisjon(type)
                "Map<${type.klassenavn()}, Boolean>"
            }

            inputType == "numeric" -> "Int"
            type == "textfield" -> "String"
            type == "navDatepicker" -> "LocalDate"
            type == "landvelger" -> {
                klassedefinisjoner.add(
                    Klassedefinisjon(
                        navn = "Landvelger",
                        felter = listOf(Felt(felt = "value", type = "String"), Felt(felt = "label", type = "String"))
                    )
                )
                "Landvelger"
            }
            type == "datagrid" || type == "container" || type == "navSkjemagruppe" -> {
                this.leggTilDataClassMapping(key)
                key
            }
            else -> error("Har ikke mapping for $this")
        }

    /**
     * Legger til AktiviteterOgMålgruppe med egne definisjoner av Skjemagruppe då denne ikke er definiert i skjema
     */
    private fun leggTilKlassedefinisjonerForAktiviteterOgMålgruppe() {
        val felterAktivitet =
            listOf(
                Felt(felt = "aktivitetId", type = "String"),
                Felt(felt = "text", type = "String"),
                Felt(felt = "periode", type = "Periode?"),
                Felt(felt = "maalgruppe", type = "Målgruppe?"),
            )
        val felterMålgruppe =
            listOf(
                Felt(felt = "maalgruppetype", type = "String"),
                Felt(felt = "gyldighetsperiode", type = "Periode"),
                Felt(felt = "maalgruppenavn", type = "String"),
            )
        klassedefinisjoner.add(
            Klassedefinisjon(
                navn = "Periode",
                listOf(Felt(felt = "fom", type = "LocalDate"), Felt(felt = "tom", type = "LocalDate"))
            )
        )
        klassedefinisjoner.add(Klassedefinisjon(navn = "Målgruppe", felter = felterMålgruppe))
        klassedefinisjoner.add(Klassedefinisjon(navn = "Aktivitet", felter = felterAktivitet))
        klassedefinisjoner.add(
            Klassedefinisjon(
                navn = "AktiviteterOgMålgruppe",
                felter = listOf(Felt(felt = "aktivitet", type = "Aktivitet"))
            )
        )
    }

    private fun SkjemaKomponent.leggTilEnumDefinisjon(type: String) {
        enumdefinisjoner.add(Enumdefinisjon(navn = type.klassenavn(), verdier =  values!!.map { it.value }))
    }

    private fun SkjemaKomponent.leggTilDataClassMapping(klassenavn: String) {
        klassedefinisjoner.add(Klassedefinisjon(navn = klassenavn.klassenavn(), felter = genererDataClasses()))
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

    private fun String.storFørsteBokstav() =
        replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    private data class Klassedefinisjon(
        val navn: String,
        val felter: List<Felt>
    )

    private data class Felt(
        val felt: String,
        val type: String,
    )

    private data class Enumdefinisjon(
        val navn: String,
        val verdier: List<String>
    )
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
