package no.nav.tilleggsstonader.kontrakter.ytelse

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
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

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = YtelsePeriode.AAP::class, name = "AAP"),
    JsonSubTypes.Type(value = YtelsePeriode.Dagpenger::class, name = "DAGPENGER"),
    JsonSubTypes.Type(value = YtelsePeriode.EnsligForsørger::class, name = "ENSLIG_FORSØRGER"),
    JsonSubTypes.Type(value = YtelsePeriode.Omstillingsstønad::class, name = "OMSTILLINGSSTØNAD"),
    JsonSubTypes.Type(value = YtelsePeriode.TiltakspengerTPSak::class, name = "TILTAKSPENGER_TPSAK"),
    JsonSubTypes.Type(value = YtelsePeriode.TiltakspengerArena::class, name = "TILTAKSPENGER_ARENA"),
)
sealed interface YtelsePeriode {
    val fom: LocalDate
    val tom: LocalDate?
    val type: TypeYtelsePeriode
        get() =
            when (this) {
                is AAP -> TypeYtelsePeriode.AAP
                is Dagpenger -> TypeYtelsePeriode.DAGPENGER
                is EnsligForsørger -> TypeYtelsePeriode.ENSLIG_FORSØRGER
                is Omstillingsstønad -> TypeYtelsePeriode.OMSTILLINGSSTØNAD
                is TiltakspengerArena -> TypeYtelsePeriode.TILTAKSPENGER_ARENA
                is TiltakspengerTPSak -> TypeYtelsePeriode.TILTAKSPENGER_TPSAK
            }

    /**
     * @param aapErFerdigAvklart hvis aktivitetsfasen == 'Ferdig avklart', man har då ikke rett på tilsyn barn
     **/
    data class AAP(
        override val fom: LocalDate,
        override val tom: LocalDate?,
        val aapErFerdigAvklart: Boolean,
    ) : YtelsePeriode

    /**
     * @param gjenståendeDagerFraTelleverk for dagpenger er ikke alltid sluttdatoen for vedtaket kjent. Ettersom våre saksbehandlere må sette
     *   en tom-dato på målgruppe, er det nyttig for dem å vite hvor mange gjenstående dager bruker har rett på ytelsen.
     **/
    data class Dagpenger(
        override val fom: LocalDate,
        override val tom: LocalDate?,
        val gjenståendeDagerFraTelleverk: GjenståendeDagerFraTelleverk?,
    ) : YtelsePeriode

    data class EnsligForsørger(
        override val fom: LocalDate,
        override val tom: LocalDate?,
        val ensligForsørgerStønadstype: EnsligForsørgerStønadstype,
        val erNyttRegelverk2026: Boolean?,
    ) : YtelsePeriode

    data class Omstillingsstønad(
        override val fom: LocalDate,
        override val tom: LocalDate?,
    ) : YtelsePeriode

    data class TiltakspengerTPSak(
        override val fom: LocalDate,
        override val tom: LocalDate?,
    ) : YtelsePeriode

    data class TiltakspengerArena(
        override val fom: LocalDate,
        override val tom: LocalDate?,
    ) : YtelsePeriode
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
