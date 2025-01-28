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
}

fun Stønadstype.dokumentTypeInterntVedtak(): Dokumenttype =
    when (this) {
        Stønadstype.BARNETILSYN -> Dokumenttype.BARNETILSYN_INTERNT_VEDTAK
        Stønadstype.LÆREMIDLER -> Dokumenttype.LÆREMIDLER_INTERNT_VEDTAK
    }
