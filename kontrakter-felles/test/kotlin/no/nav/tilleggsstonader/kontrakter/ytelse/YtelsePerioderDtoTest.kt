package no.nav.tilleggsstonader.kontrakter.ytelse

import no.nav.tilleggsstonader.kontrakter.felles.JsonMapperProvider.jsonMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tools.jackson.module.kotlin.readValue
import java.time.LocalDate

class YtelsePerioderDtoTest {

    @Test
    fun `skal serialisere og deserialisere alle YtelsePeriode-subtyper`() {
        val perioder =
            listOf(
                YtelsePeriode.AAP(
                    fom = LocalDate.of(2026, 1, 1),
                    tom = LocalDate.of(2026, 1, 31),
                    aapErFerdigAvklart = false,
                ),
                YtelsePeriode.Dagpenger(
                    fom = LocalDate.of(2026, 2, 1),
                    tom = null,
                    gjenståendeDagerFraTelleverk =
                        GjenståendeDagerFraTelleverk(
                            dato = LocalDate.of(2026, 2, 15),
                            antallDager = 42,
                        ),
                ),
                YtelsePeriode.EnsligForsørger(
                    fom = LocalDate.of(2026, 3, 1),
                    tom = LocalDate.of(2026, 3, 31),
                    ensligForsørgerStønadstype = EnsligForsørgerStønadstype.BARNETILSYN,
                    erNyttRegelverk2026 = true,
                ),
                YtelsePeriode.Omstillingsstønad(
                    fom = LocalDate.of(2026, 4, 1),
                    tom = LocalDate.of(2026, 4, 30),
                ),
                YtelsePeriode.TiltakspengerTPSak(
                    fom = LocalDate.of(2026, 5, 1),
                    tom = null,
                ),
                YtelsePeriode.TiltakspengerArena(
                    fom = LocalDate.of(2026, 6, 1),
                    tom = LocalDate.of(2026, 6, 30),
                ),
            )

        perioder.forEach { periode ->
            val json = jsonMapper.writeValueAsString(periode)
            assertThat(json).contains(""""type":"${periode.type.name}"""")

            val periodeFraJson = jsonMapper.readValue<YtelsePeriode>(json)
            assertThat(periodeFraJson).isEqualTo(periode)
        }
    }

    @Test
    fun `skal deserialisere blandet liste i YtelsePerioderDto til riktige subtyper`() {
        val json =
            """
            {
              "perioder": [
                { "type": "AAP", "fom": "2026-01-01", "tom": "2026-01-31", "aapErFerdigAvklart": true },
                { "type": "DAGPENGER", "fom": "2026-02-01", "tom": null, "gjenståendeDagerFraTelleverk": { "dato": "2026-02-10", "antallDager": 11 } },
                { "type": "ENSLIG_FORSØRGER", "fom": "2026-03-01", "tom": "2026-03-31", "ensligForsørgerStønadstype": "SKOLEPENGER", "erNyttRegelverk2026": false },
                { "type": "OMSTILLINGSSTØNAD", "fom": "2026-04-01", "tom": "2026-04-30" },
                { "type": "TILTAKSPENGER_TPSAK", "fom": "2026-05-01", "tom": null },
                { "type": "TILTAKSPENGER_ARENA", "fom": "2026-06-01", "tom": "2026-06-30" }
              ],
              "perioderHentetFom": "2026-01-01",
              "perioderHentetTom": "2026-12-31"
            }
            """.trimIndent()

        val dto = jsonMapper.readValue<YtelsePerioderDto>(json)

        assertThat(dto.perioder[0]).isInstanceOf(YtelsePeriode.AAP::class.java)
        assertThat(dto.perioder[1]).isInstanceOf(YtelsePeriode.Dagpenger::class.java)
        assertThat(dto.perioder[2]).isInstanceOf(YtelsePeriode.EnsligForsørger::class.java)
        assertThat(dto.perioder[3]).isInstanceOf(YtelsePeriode.Omstillingsstønad::class.java)
        assertThat(dto.perioder[4]).isInstanceOf(YtelsePeriode.TiltakspengerTPSak::class.java)
        assertThat(dto.perioder[5]).isInstanceOf(YtelsePeriode.TiltakspengerArena::class.java)
    }
}
