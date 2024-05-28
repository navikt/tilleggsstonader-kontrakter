package no.nav.tilleggsstonader.kontrakter.ytelse

import java.time.LocalDate

/**
 * TODO slett default verdier når den er tatt i bruk av consumer
 * @param typer definierer hvilke typer som skal hentes
 */
data class YtelsePerioderRequest(
    val ident: String,
    val fom: LocalDate = LocalDate.now(),
    val tom: LocalDate = LocalDate.now(),
    val typer: List<TypeYtelsePeriode> = TypeYtelsePeriode.entries,
)

/**
 * TODO slett [arbeidsavklaringspenger]
 * TODO slett [ensligForsørger]
 * TODO slett default emptylist
 * Når perioder og hentetInformasjon er tatt i bruk
 */
data class YtelsePerioderDto(
    val arbeidsavklaringspenger: PerioderArbeidsavklaringspenger,
    val ensligForsørger: PerioderEnsligForsørger,
    val perioder: List<YtelsePeriode> = emptyList(),
    val hentetInformasjon: List<HentetInformasjon> = emptyList(),
)

data class YtelsePeriode(
    val type: TypeYtelsePeriode,
    val fom: LocalDate,
    val tom: LocalDate,
)

data class HentetInformasjon(
    val type: TypeYtelsePeriode,
    val status: StatusHentetInformasjon,
)

enum class StatusHentetInformasjon {
    OK,
    FEILET,
}

enum class TypeYtelsePeriode {
    AAP,
    ENSLIG_FORSØRGER,
}
