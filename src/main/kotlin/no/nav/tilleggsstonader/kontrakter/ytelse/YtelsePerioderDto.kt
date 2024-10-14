package no.nav.tilleggsstonader.kontrakter.ytelse

import java.time.LocalDate

/**
 * @param typer definerer hvilke typer som skal hentes
 */
data class YtelsePerioderRequest(
    val ident: String,
    val fom: LocalDate,
    val tom: LocalDate,
    val typer: List<TypeYtelsePeriode>,
)

/**
 * @param hentetInformasjon populeres med alle typer som man har sendt med i requesten
 */
data class YtelsePerioderDto(
    val perioder: List<YtelsePeriode>,
    val hentetInformasjon: List<HentetInformasjon>,
)

/**
 * @param aapErFerdigAvklart hvis aktivitetsfasen == 'Ferdig avklart', man har då ikke rett på tilsyn barn
 */
data class YtelsePeriode(
    val type: TypeYtelsePeriode,
    val fom: LocalDate,
    val tom: LocalDate?,
    val aapErFerdigAvklart: Boolean? = null,
    val ensligForsørgerStønadstype: EnsligForsørgerStønadstype? = null,
) {
    init {
        if (type != TypeYtelsePeriode.AAP && aapErFerdigAvklart != null) {
            error("Kan ikke sette 'aapAktivitetsfase' for $type")
        }
        if (type != TypeYtelsePeriode.ENSLIG_FORSØRGER && ensligForsørgerStønadstype != null) {
            error("Kan ikke sette 'ensligForsørgerStønadstype' for $type")
        }
    }
}

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
    OMSTILLINGSSTØNAD,
}

enum class EnsligForsørgerStønadstype {
    OVERGANGSSTØNAD,
    SKOLEPENGER,
    BARNETILSYN,
}
