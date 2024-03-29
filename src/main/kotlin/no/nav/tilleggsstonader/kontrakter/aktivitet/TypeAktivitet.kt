package no.nav.tilleggsstonader.kontrakter.aktivitet

/**
 * Disse typene kommer fra arena, og er ikke mappet til nye verdier då der er så mange
 * select aktivitetkode
 * || '("'||aktivitettypenavn||'",'
 * || case when status_tilleggsstonader = 'J' then 'true' else 'false' end || '),'
 * from ram_aktivitettype;
 */
enum class TypeAktivitet(val beskrivelse: String, val rettTilStønad: Boolean) {
    AMBF1("AMB Avklaring (fase 1)", false),
    ABOPPF("Arbeid med bistand A oppfølging", false),
    ABUOPPF("Arbeid med bistand A utvidet oppfølging", false),
    ABIST("Arbeid med Bistand (AB)", true),
    ABTBOPPF("Arbeid med bistand B", false),
    LONNTILAAP("Arbeidsavklaringspenger som lønnstilskudd", false),
    ARBFORB("Arbeidsforberedende trening (AFT)", true),
    AMOY("Arbeidsmarkedskurs (AMO) yrkeshemmede", false),
    AMO("Arbeidsmarkedsopplæring (AMO)", true),
    AMOE("Arbeidsmarkedsopplæring (AMO) enkeltplass", true),
    AMOB("Arbeidsmarkedsopplæring (AMO) i bedrift", true),
    ARBPVURD("Arbeidsplassvudering", false),
    PRAKSORD("Arbeidspraksis i ordinær virksomhet", true),
    PRAKSKJERM("Arbeidspraksis i skjermet virksomhet", true),
    ARBTILARBT("Arbeidsrettede tiltak - Arbeidstrening", false),
    ARBTILTAV("Arbeidsrettede tiltak - Avklaring", false),
    ARBTILTBE("Arbeidsrettede tiltak - Behandling/rehabilitering", false),
    ARBTILKVAL("Arbeidsrettede tiltak - Kvalifisering", false),
    ARBTILFORM("Arbeidsrettede tiltak - Lønntilskudd", false),
    ARBTILOPPF("Arbeidsrettede tiltak - Oppfølgingstiltak", false),
    ARBTILTILR("Arbeidsrettede tiltak - Tilrettelagt arbeid", false),
    ARBRRHBAG("Arbeidsrettet rehabilitering", false),
    ARBRRHBSM("Arbeidsrettet rehabilitering - sykmeldt arbeidstaker", false),
    ARBRRHDAG("Arbeidsrettet rehabilitering (dag)", true),
    ARBRDAGSM("Arbeidsrettet rehabilitering (dag) - sykmeldt arbeidstaker", true),
    ARBRRDOGN("Arbeidsrettet rehabilitering (døgn)", true),
    ARBDOGNSM("Arbeidsrettet rehabilitering (døgn) - sykmeldt arbeidstaker", true),
    ASV("Arbeidssamvirke (ASV)", false),
    ARBTREN("Arbeidstrening", true),
    ATG("Arbeidstreningsgrupper", false),
    ARBUTPRO("Arbeidsutprøving", false),
    AVKLARAG("Avklaring", true),
    AVKLARUS("Avklaring", false),
    AVKLARSP("Avklaring - sykmeldt arbeidstaker", true),
    AVKLARKV("Avklaring av kortere varighet", false),
    AVKLARSV("Avklaring i skjermet virksomhet", true),
    BIA("Bedriftsintern attføring", false),
    BIO("Bedriftsintern opplæring (BIO)", false),
    BEHUTRPS("Behandling for lettere psykiske lidelser", false),
    DIGIOPPARB("Digitalt oppfølgingstiltak for arbeidsledige (jobbklubb)", true),
    DIVTILT("Diverse tiltak", false),
    ETAB("Egenetablering", true),
    EKSPEBIST("Ekspertbistand", false),
    ENKELAMO("Enkeltplass AMO", true),
    ENKFAGYRKE("Enkeltplass Fag- og yrkesopplæring VGS og høyere yrkesfaglig utdanning", true),
    FLEKSJOBB("Fleksibel jobb - lønnstilskudd av lengre varighet", false),
    TILRTILSK("Forebyggings- og tilretteleggingstilskudd IA virksomheter og BHT-honorar", false),
    KAT("Formidlingstjenester", false),
    VALS("Formidlingstjenester - Ventelønn", false),
    FORSAMOENK("Forsøk AMO enkeltplass", true),
    FORSAMOGRU("Forsøk AMO gruppe", false),
    FORSFAGENK("Forsøk fag- og yrkesopplæring enkeltplass", true),
    FORSFAGGRU("Forsøk fag- og yrkesopplæring gruppe", false),
    FORSHOYUTD("Forsøk høyere utdanning", true),
    FORSOPPLEV("Forsøk opplæringstiltak av lengre varighet", true),
    FUNKSJASS("Funksjonsassistanse", false),
    GRUPPEAMO("Gruppe AMO", true),
    GRUFAGYRKE("Gruppe Fag- og yrkesopplæring VGS og høyere yrkesfaglig utdanning", true),
    HOYEREUTD("Høyere utdanning", true),
    IAPLASS("IA-plass", false),
    INDJOBSTOT("Individuell jobbstøtte (IPS)", true),
    IPSUNG("Individuell karrierestøtte (IPS Ung)", true),
    INDOPPFOLG("Individuelt oppfølgingstiltak", false),
    INKLUTILS("Inkluderingstilskudd", false),
    ITGRTILS("Integreringstilskudd", false),
    JOBBKLUBB("Intern jobbklubb", false),
    JOBBFOKUS("Jobbfokus/Utvidet formidlingsbistand", false),
    JOBBK("Jobbklubb", true),
    JOBBBONUS("Jobbklubb med bonusordning", false),
    JOBBKBONUS("Jobbklubb med bonusordning", false),
    JOBBSKAP("Jobbskapingsprosjekter", false),
    KARTL("Kartlegging av norskkunnskaper", false),
    KOMAVTILT("Kommunale avklaringstiltak", false),
    AMBF2("Kvalifisering i arbeidsmarkedsbedrift", true),
    STATLAERL("Lærlinger i statlige etater", false),
    LONNTILS("Lønnstilskudd", false),
    REAKTUFOR("Lønnstilskudd - reaktivisering av uførepensjonister", false),
    LONNTILL("Lønnstilskudd av lengre varighet", false),
    MENTOR("Mentor", false),
    MIDLONTIL("Midlertidig lønnstilskudd", false),
    NETTAMO("Nettbasert arbeidsmarkedsopplæring (AMO)", true),
    INST_S("Nye plasser institusjonelle tiltak", false),
    INDOPPFAG("Oppfølging", true),
    INDOPPFSP("Oppfølging - sykmeldt arbeidstaker", true),
    OUTDEF("Ordinær utdanning for enslige forsørgere mv", true),
    PV("Produksjonsverksted (PV)", false),
    INDOPPRF("Resultatbasert finansiering av formidlingsbistand", false),
    REFINO("Resultatbasert finansiering av oppfølging", true),
    SPA("Spa prosjekter", false),
    SUPPEMP("Supported Employment", true),
    SYSSLANG("Sysselsettingstiltak for langtidsledige", false),
    YHEMMOFF("Sysselsettingstiltak for yrkeshemmede", false),
    SYSSOFF("Sysselsettingstiltak i offentlig sektor for yrkeshemmede", false),
    TEKNTILR("Teknisk tilrettelegging", false),
    LONNTIL("Tidsbegrenset lønnstilskudd", false),
    TIDSUBLONN("Tidsubestemt lønnstilskudd", false),
    AMBF3("Tilrettelagt arbeid i arbeidsmarkedsbedrift", false),
    TILRETTEL("Tilrettelegging for arbeidstaker", false),
    TILPERBED("Tilretteleggingstilskudd for arbeidssøker", false),
    TILSJOBB("Tilskudd til sommerjobb", false),
    UFØREPENLØ("Uførepensjon som lønnstilskudd", false),
    UTDYRK("Utdanning", true),
    UTDPERMVIK("Utdanningspermisjoner", false),
    VIKARBLED("Utdanningsvikariater", false),
    UTBHLETTPS("Utredning/behandling lettere psykiske lidelser", false),
    UTBHPSLD("Utredning/behandling lettere psykiske og sammensatte lidelser", false),
    UTBHSAMLI("Utredning/behandling sammensatte lidelser", false),
    UTVAOONAV("Utvidet oppfølging i NAV", true),
    UTVOPPFOPL("Utvidet oppfølging i opplæring", true),
    VARLONTIL("Varig lønnstilskudd", false),
    VATIAROR("Varig tilrettelagt arbeid i ordinær virksomhet", false),
    VASV("Varig tilrettelagt arbeid i skjermet virksomhet", false),
    VV("Varig vernet arbeid (VVA)", false),
    OPPLT2AAR("2-årig opplæringstiltak", true),
}
