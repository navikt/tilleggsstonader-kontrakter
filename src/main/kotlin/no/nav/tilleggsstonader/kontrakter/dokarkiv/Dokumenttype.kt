package no.nav.tilleggsstonader.kontrakter.dokarkiv

import no.nav.tilleggsstonader.kontrakter.felles.Stønadstype

enum class Dokumenttype {
    BARNETILSYN_SØKNAD,
    BARNETILSYN_SØKNAD_VEDLEGG,
    BARNETILSYN_VEDTAKSBREV,
    BARNETILSYN_FRITTSTÅENDE_BREV,
    BARNETILSYN_INTERNT_VEDTAK,
    BARNETILSYN_KLAGE_VEDTAKSBREV,
    BARNETILSYN_KLAGE_INTERNT_VEDTAK,

    LÆREMIDLER_SØKNAD,
    LÆREMIDLER_SØKNAD_VEDLEGG,
    LÆREMIDLER_VEDTAKSBREV,
    LÆREMIDLER_FRITTSTÅENDE_BREV,
    LÆREMIDLER_INTERNT_VEDTAK,
    LÆREMIDLER_KLAGE_VEDTAKSBREV,
    LÆREMIDLER_KLAGE_INTERNT_VEDTAK,

    // BOUTGIFTER_SØKNAD, // foreløoig ingen
    // BOUTGIFTER_SØKNAD_VEDLEGG,
    BOUTGIFTER_VEDTAKSBREV,
    BOUTGIFTER_FRITTSTÅENDE_BREV,
    BOUTGIFTER_INTERNT_VEDTAK,
    BOUTGIFTER_KLAGE_VEDTAKSBREV,
    BOUTGIFTER_KLAGE_INTERNT_VEDTAK,
}

data class Dokumentyper(
    val søknad: Dokumenttype?,
    val søknadVedlegg: Dokumenttype?,
    val vedtaksbrev: Dokumenttype,
    val frittståendeBrev: Dokumenttype,
    val interntVedtak: Dokumenttype,
    val klageVedtaksbrev: Dokumenttype,
    val klageInterntVedtak: Dokumenttype,
)

val Stønadstype.dokumenttyper: Dokumentyper
    get() =
        when (this) {
            Stønadstype.BARNETILSYN ->
                Dokumentyper(
                    søknad = Dokumenttype.BARNETILSYN_SØKNAD,
                    søknadVedlegg = Dokumenttype.BARNETILSYN_SØKNAD_VEDLEGG,
                    vedtaksbrev = Dokumenttype.BARNETILSYN_VEDTAKSBREV,
                    frittståendeBrev = Dokumenttype.BARNETILSYN_FRITTSTÅENDE_BREV,
                    interntVedtak = Dokumenttype.BARNETILSYN_INTERNT_VEDTAK,
                    klageVedtaksbrev = Dokumenttype.BARNETILSYN_KLAGE_VEDTAKSBREV,
                    klageInterntVedtak = Dokumenttype.BARNETILSYN_KLAGE_INTERNT_VEDTAK,
                )
            Stønadstype.LÆREMIDLER ->
                Dokumentyper(
                    søknad = Dokumenttype.LÆREMIDLER_SØKNAD,
                    søknadVedlegg = Dokumenttype.LÆREMIDLER_SØKNAD_VEDLEGG,
                    vedtaksbrev = Dokumenttype.LÆREMIDLER_VEDTAKSBREV,
                    frittståendeBrev = Dokumenttype.LÆREMIDLER_FRITTSTÅENDE_BREV,
                    interntVedtak = Dokumenttype.LÆREMIDLER_INTERNT_VEDTAK,
                    klageVedtaksbrev = Dokumenttype.LÆREMIDLER_KLAGE_VEDTAKSBREV,
                    klageInterntVedtak = Dokumenttype.LÆREMIDLER_KLAGE_INTERNT_VEDTAK,
                )
            Stønadstype.BOUTGIFTER ->
                Dokumentyper(
                    søknad = null,
                    søknadVedlegg = null,
                    vedtaksbrev = Dokumenttype.BOUTGIFTER_VEDTAKSBREV,
                    frittståendeBrev = Dokumenttype.BOUTGIFTER_FRITTSTÅENDE_BREV,
                    interntVedtak = Dokumenttype.BOUTGIFTER_INTERNT_VEDTAK,
                    klageVedtaksbrev = Dokumenttype.BOUTGIFTER_KLAGE_VEDTAKSBREV,
                    klageInterntVedtak = Dokumenttype.BOUTGIFTER_KLAGE_INTERNT_VEDTAK,
                )
        }

fun Stønadstype.dokumentTypeInterntVedtak(): Dokumenttype =
    when (this) {
        Stønadstype.BARNETILSYN -> Dokumenttype.BARNETILSYN_INTERNT_VEDTAK
        Stønadstype.LÆREMIDLER -> Dokumenttype.LÆREMIDLER_INTERNT_VEDTAK
        Stønadstype.BOUTGIFTER -> Dokumenttype.BOUTGIFTER_INTERNT_VEDTAK
    }
