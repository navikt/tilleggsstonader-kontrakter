## Søknadskontrakter og dataflyt

- `kontrakter-felles/main/kotlin/no/nav/tilleggsstonader/kontrakter/søknad/` definerer de strukturerte søknadene som deles av søknad-api og sak. Felles felttyper ligger i søknadspakken, og felles avsnitt og domenetyper ligger under `søknad/felles/`.
- Søknad-api mapper innsendte DTO-er til kontraktene og lagrer JSON. `EnumFelt` inneholder både enumverdien og spørsmål-/svartekstene som brukes i PDF-en. Behold enumverdiene når typer trekkes ut til felles bruk.
- JSON og PDF arkiveres via integrasjoner etter `LagPdfTask` og `ArkiverSøknadTask`. Sak mottar en journalposthendelse og henter originaldokumentet via integrasjoner, ikke direkte fra søknad-api.
- Et nytt søknadsfelt krever tilsvarende endringer i søknadsfrontend, søknad-api, sakens skjema og fakta-mapping, og sak-frontend. En kontraktsendring alene får ikke feltet frem i oppsummeringsfanen.
- Ved lokal utvikling kan `includeBuild("../tilleggsstonader-kontrakter")` avkommenteres i `settings.gradle.kts` i søknad-api og sak. Det erstatter den publiserte kontraktsavhengigheten lokalt; det endrer ikke versjonen i `build.gradle.kts`.
- Nye obligatoriske felt uten standardverdi kan gjøre lagret JSON uleselig og stoppe utestående tasks. Avklar kompatibilitetskravet før slike endringer. Ikke logg søknadsinnhold.
