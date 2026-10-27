package no.nav.tilleggsstonader.kontrakter.søknad

import no.nav.tilleggsstonader.kontrakter.felles.Hovedytelse
import no.nav.tilleggsstonader.kontrakter.felles.JsonMapperProvider.jsonMapper
import no.nav.tilleggsstonader.kontrakter.felles.JsonMapperProvider.jsonMapperFailOnUnknownProperties
import no.nav.tilleggsstonader.kontrakter.felles.Språkkode
import no.nav.tilleggsstonader.kontrakter.søknad.felles.HovedytelseAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.felles.ReisemåteAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.reisetilsamling.OffentligTransportInfo
import no.nav.tilleggsstonader.kontrakter.søknad.reisetilsamling.Transportmiddel
import no.nav.tilleggsstonader.kontrakter.søknad.roah.AdresseMidlertidigBostedAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.roah.AdresseOriginaltBostedAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.roah.AdresseOriginaltBostedDetaljerAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.roah.PeriodeMidlertidigBostedAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.roah.ROAHAdresseAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.roah.ROAHAktivitetAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.roah.ROAHBarnOgSærligeBehovAvsnitt
import no.nav.tilleggsstonader.kontrakter.søknad.roah.ROAHReiseInfo
import no.nav.tilleggsstonader.kontrakter.søknad.roah.ROAHTypeReise
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tools.jackson.databind.node.ArrayNode
import tools.jackson.databind.node.ObjectNode
import tools.jackson.module.kotlin.readValue
import java.time.LocalDate
import java.time.LocalDateTime

class SøknadsskjemaROAHTest {
    @Test
    fun `skal serialisere og deserialisere roah-skjema`() {
        val innsendtSkjema = lagInnsendtSkjema()

        val json = jsonMapper.writeValueAsString(innsendtSkjema)
        val parsed =
            jsonMapperFailOnUnknownProperties.readValue<
                InnsendtSkjema<SøknadsskjemaROAH>,
            >(json)

        assertThat(
            parsed.skjema.barnOgSærligeBehov.harSærligeBehovForFlereHjemreiser
                ?.verdi,
        ).isEqualTo(JaNei.NEI)
        assertThat(parsed.skjema.reiser).hasSize(2)
        assertThat(
            parsed.skjema.reiser
                .first()
                .typeReise
                ?.verdi,
        ).isEqualTo(ROAHTypeReise.OPPSTART)
    }

    @Test
    fun `skal kunne deserialisere reise uten tom-dato`() {
        val innsendtSkjema = lagInnsendtSkjema()
        val tree = jsonMapper.readTree(jsonMapper.writeValueAsString(innsendtSkjema)) as ObjectNode
        val reiser = tree.path("skjema").path("reiser") as ArrayNode
        (reiser[0] as ObjectNode).remove("tom")

        val parsed =
            jsonMapperFailOnUnknownProperties.readValue<InnsendtSkjema<SøknadsskjemaROAH>>(
                jsonMapper.writeValueAsString(tree),
            )

        assertThat(
            parsed.skjema.reiser
                .first()
                .tom,
        ).isNull()
    }

    private fun lagInnsendtSkjema(): InnsendtSkjema<SøknadsskjemaROAH> =
        InnsendtSkjema(
            ident = "12345678910",
            mottattTidspunkt = LocalDateTime.of(2026, 1, 1, 12, 0),
            språk = Språkkode.NB,
            skjema =
                SøknadsskjemaROAH(
                    hovedytelse =
                        HovedytelseAvsnitt(
                            hovedytelse =
                                EnumFlereValgFelt(
                                    label = "Mottar du eller har du nylig søkt om noe av dette?",
                                    verdier = listOf(VerdiFelt(Hovedytelse.AAP, "Arbeidsavklaringspenger (AAP)")),
                                    alternativer = listOf("Arbeidsavklaringspenger (AAP)"),
                                ),
                            arbeidOgOpphold = null,
                        ),
                    aktivitet =
                        ROAHAktivitetAvsnitt(
                            aktiviteter = null,
                            annenAktivitet = null,
                            lønnetAktivitet = null,
                        ),
                    adresser =
                        ROAHAdresseAvsnitt(
                            måBoBorteHjemmefra =
                                EnumFelt(
                                    "Må du bo borte hjemmefra?",
                                    JaNei.JA,
                                    "Ja",
                                    listOf("Ja", "Nei"),
                                ),
                            periodeMidlertidigBosted =
                                PeriodeMidlertidigBostedAvsnitt(
                                    fomFlyttedato = DatoFelt("Fra dato", LocalDate.of(2026, 1, 10)),
                                    tomFlyttedato = DatoFelt("Til dato", LocalDate.of(2026, 3, 31)),
                                ),
                            adresseOriginaltBosted =
                                AdresseOriginaltBostedAvsnitt(
                                    skalReiseFraFolkeregistrertAdresse =
                                        EnumFelt(
                                            "Skal du reise fra folkeregistrert adresse?",
                                            JaNei.NEI,
                                            "Nei",
                                            listOf("Ja", "Nei"),
                                        ),
                                    adresseOriginaltBosted = lagAdresseOriginaltBosted("Originalgata 1", "0123", "Oslo"),
                                ),
                            adresseMidlertidigBosted = lagAdresseMidlertidigBosted("Midlertidig vei 2", "5003", "Bergen"),
                        ),
                    barnOgSærligeBehov =
                        ROAHBarnOgSærligeBehovAvsnitt(
                            harBarnUnder18SomHarFlyttetMed =
                                EnumFelt(
                                    "Har du barn under 18 som har flyttet med?",
                                    JaNei.NEI,
                                    "Nei",
                                    listOf("Ja", "Nei"),
                                ),
                            hvilkeBarnFlytterMed = null,
                            harBarnHjemmeUnder4Klasse =
                                EnumFelt(
                                    "Har du barn hjemme under 4. klasse?",
                                    JaNei.JA,
                                    "Ja",
                                    listOf("Ja", "Nei"),
                                ),
                            harSærligeBehovForFlereHjemreiser =
                                EnumFelt(
                                    "Har du særlige behov for flere hjemreiser?",
                                    JaNei.NEI,
                                    "Nei",
                                    listOf("Ja", "Nei"),
                                ),
                        ),
                    reiser =
                        listOf(
                            ROAHReiseInfo(
                                typeReise =
                                    EnumFelt(
                                        "Hvilken type reise søker du om?",
                                        ROAHTypeReise.OPPSTART,
                                        "Oppstart",
                                        listOf("Oppstart", "Avslutning", "Hjemreise"),
                                    ),
                                fom = DatoFelt("Fra og med", LocalDate.of(2026, 1, 10)),
                                tom = null,
                                reisemåte = lagReisemåte(),
                            ),
                            ROAHReiseInfo(
                                typeReise =
                                    EnumFelt(
                                        "Hvilken type reise søker du om?",
                                        ROAHTypeReise.HJEMREISE,
                                        "Hjemreise",
                                        listOf("Oppstart", "Avslutning", "Hjemreise"),
                                    ),
                                fom = DatoFelt("Fra og med", LocalDate.of(2026, 2, 1)),
                                tom = DatoFelt("Til og med", LocalDate.of(2026, 2, 15)),
                                reisemåte = lagReisemåte(),
                            ),
                        ),
                    dokumentasjon = emptyList(),
                ),
        )

    private fun lagAdresseOriginaltBosted(
        gateadresse: String,
        postnummer: String,
        poststed: String,
    ): AdresseOriginaltBostedDetaljerAvsnitt =
        AdresseOriginaltBostedDetaljerAvsnitt(
            land = SelectFelt(label = "Velg land", verdi = "NOR", svarTekst = "Norge"),
            gateadresse = VerdiFelt(verdi = gateadresse, label = "Gateadresse"),
            postnummer = VerdiFelt(verdi = postnummer, label = "Postnummer"),
            poststed = VerdiFelt(verdi = poststed, label = "Poststed"),
        )

    private fun lagAdresseMidlertidigBosted(
        gateadresse: String,
        postnummer: String,
        poststed: String,
    ): AdresseMidlertidigBostedAvsnitt =
        AdresseMidlertidigBostedAvsnitt(
            land = SelectFelt(label = "Velg land", verdi = "NOR", svarTekst = "Norge"),
            gateadresse = VerdiFelt(verdi = gateadresse, label = "Gateadresse"),
            postnummer = VerdiFelt(verdi = postnummer, label = "Postnummer"),
            poststed = VerdiFelt(verdi = poststed, label = "Poststed"),
        )

    private fun lagReisemåte() =
        ReisemåteAvsnitt(
            hvilkeTransportmidlerBleBenyttet =
                EnumFlereValgFelt(
                    label = "Hvilke transportmidler ble benyttet?",
                    verdier = listOf(VerdiFelt(Transportmiddel.OFFENTLIG_TRANSPORT, "Offentlig transport")),
                    alternativer = listOf("Offentlig transport", "Privat bil", "Drosje"),
                ),
            unntakFraOffentligTransport = null,
            unntakFraPrivatBil = null,
            offentligTransport =
                OffentligTransportInfo(
                    totalUtgifterOffentligTransport = VerdiFelt("1200", "Total utgift"),
                ),
            privatBil = null,
            drosje = null,
        )
}
