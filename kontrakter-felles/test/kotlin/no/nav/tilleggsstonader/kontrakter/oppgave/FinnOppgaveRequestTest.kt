package no.nav.tilleggsstonader.kontrakter.oppgave

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.tilleggsstonader.kontrakter.felles.ObjectMapperProvider.objectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FinnOppgaveRequestTest {
    @Test
    fun `Kan konvertere fra json til FinnOppgaveRequest`() {
        val finnOppgaveRequestString =
            """
            {
              "tema": ["TSO","TSR"],
              "behandlingstema": "ab0300",
              "behandlingstype": "NASJONAL",
              "erUtenMappe": false,
              "oppgavetype": "BehandleSak",
              "enhet": "1234",
              "saksbehandler": "John Smith",
              "aktørId": "12345678",
              "journalpostId": "J123456",
              "saksreferanse": "S123456",
              "tilordnetRessurs": "Jane Smith",
              "tildeltRessurs": true,
              "opprettetFomTidspunkt": "2023-01-12T08:21:16.290013",
              "opprettetTomTidspunkt": "2023-01-12T08:21:16.290013",
              "fristFomDato": "2000-10-31",
              "fristTomDato": "2000-10-31",
              "aktivFomDato": "2000-10-31",
              "aktivTomDato": "2000-10-31",
              "mappeId": 12345,
              "limit" : 10,
              "offset" : 0,
              "sorteringsrekkefolge" : "ASC",
              "sorteringsfelt" : "FRIST"
            }
            """.trimIndent()

        val request = objectMapper.readValue<FinnOppgaveRequest>(finnOppgaveRequestString)
        val requestJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request)

        assertThat(requestJson).isEqualToIgnoringWhitespace(finnOppgaveRequestString)
    }
}
