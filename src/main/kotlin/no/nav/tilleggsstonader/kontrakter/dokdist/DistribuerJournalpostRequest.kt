@file:Suppress("ktlint:standard:enum-entry-name-case")

package no.nav.tilleggsstonader.kontrakter.dokdist

import no.nav.tilleggsstonader.kontrakter.felles.Fagsystem

data class DistribuerJournalpostRequest(
    val journalpostId: String,
    val bestillendeFagsystem: Fagsystem,
    val dokumentProdApp: String,
    val distribusjonstype: Distribusjonstype?,
    val distribusjonstidspunkt: Distribusjonstidspunkt = Distribusjonstidspunkt.KJERNETID,
    val adresse: ManuellAdresse? = null,
) {
    init {
        require(journalpostId.isNotBlank()) { "Mangler journalpostId" }
        require(dokumentProdApp.isNotBlank()) { "Mangler dokumentProdApp" }
    }
}

enum class Distribusjonstype {
    VEDTAK,
    VIKTIG,
    ANNET,
}

enum class Distribusjonstidspunkt {
    KJERNETID,
    UMIDDELBART,
}

enum class AdresseType {
    norskPostadresse,
    utenlandskPostadresse,
}

data class ManuellAdresse(
    val adresseType: AdresseType,
    val adresselinje1: String?,
    val adresselinje2: String? = null,
    val adresselinje3: String? = null,
    val postnummer: String?,
    val poststed: String?,
    val land: String = "NO",
) {
    init {
        check(land.length == 2) { "Ugyldig landkode" }
        if (land == "NO") {
            check(adresseType == AdresseType.norskPostadresse) { "Feil adresse type" }
        }
        if (adresseType == AdresseType.norskPostadresse) {
            checkNotNull(adresselinje1) { "AdresseLinje1 er påkrevd for norsk postadresse" }
            checkNotNull(postnummer) { "Postnummer er påkrevd for norsk postadresse" }
            checkNotNull(poststed) { "Poststed er påkrevd for norsk postadresse" }
        }
    }
}
