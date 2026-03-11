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
 * @param kildeResultat populeres med alle typer som man har sendt med i requesten
 */
data class YtelsePerioderDto(
    val perioder: List<YtelsePeriode>,
    val kildeResultat: List<KildeResultatYtelse> = emptyList(),
    val perioderHentetFom: LocalDate,
    val perioderHentetTom: LocalDate,
) {
    data class KildeResultatYtelse(
        val type: TypeYtelsePeriode,
        val resultat: ResultatKilde,
    )
}

enum class ResultatKilde {
    OK,
    FEILET,
}

/**
 * @param aapErFerdigAvklart hvis aktivitetsfasen == 'Ferdig avklart', man har då ikke rett på tilsyn barn
 * @param gjenståendeDagerFraTelleverk for dagpenger er ikke alltid sluttdatoen for vedtaket kjent. Ettersom våre saksbehandlere må sette
 *   en tom-dato på målgruppe, er det nyttig for dem å vite hvor mange gjenstående dager bruker har rett på ytelsen.
 */
data class YtelsePeriode(
    val type: TypeYtelsePeriode,
    val fom: LocalDate,
    val tom: LocalDate?,
    val aapErFerdigAvklart: Boolean? = null,
    val ensligForsørgerStønadstype: EnsligForsørgerStønadstype? = null,
    val gjenståendeDagerFraTelleverk: `GjenståendeDagerFraTelleverk`? = null,
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

data class GjenståendeDagerFraTelleverk(
    val dato: LocalDate,
    val antallDager: Int,
)

enum class TypeYtelsePeriode {
    AAP,
    DAGPENGER,
    ENSLIG_FORSØRGER,
    OMSTILLINGSSTØNAD,
    TILTAKSPENGER_TPSAK,
    TILTAKSPENGER_ARENA,
}

enum class EnsligForsørgerStønadstype {
    OVERGANGSSTØNAD,
    SKOLEPENGER,
    BARNETILSYN,
}
