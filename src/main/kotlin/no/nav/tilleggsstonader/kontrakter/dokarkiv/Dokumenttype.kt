package no.nav.tilleggsstonader.kontrakter.dokarkiv

import no.nav.tilleggsstonader.kontrakter.felles.Stønadstype

enum class Dokumenttype {
    BARNETILSYN_SØKNAD,
    BARNETILSYN_SØKNAD_VEDLEGG,
    BARNETILSYN_VEDTAKSBREV,
    BARNETILSYN_FRITTSTÅENDE_BREV,
    BARNETILSYN_INTERNT_VEDTAK,
}

fun Stønadstype.dokumentTypeInterntVedtak(): Dokumenttype = when (this) {
    Stønadstype.BARNETILSYN -> Dokumenttype.BARNETILSYN_INTERNT_VEDTAK
}
