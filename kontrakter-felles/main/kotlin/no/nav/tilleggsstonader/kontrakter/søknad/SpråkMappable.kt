package no.nav.tilleggsstonader.kontrakter.søknad

import no.nav.tilleggsstonader.kontrakter.felles.Språkkode

interface SpråkMappable {
    fun språkMapper(): Map<Språkkode, String>
}
