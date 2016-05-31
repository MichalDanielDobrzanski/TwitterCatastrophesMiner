Import do InteliJ IDEA:
    File->Open->Wskazać na plik pom.xml

Wymagania:
    W katalogu gdzie uruchamiamy aplikację musi być plik 
    twitter4j.properties.
    
    Więcej informacji i inne opcje:
        http://twitter4j.org/en/configuration.html#fileconfiguration
    
Uruchamianie:
    Główny kontener:
        mvn -Pjade-main exec:java
    Zbieracz tweetów (crawler):
        mvn -Pjade-crawler exec:java
    Przetwarzanie (categorizer, store):
        mvn -Pjade-process exec:java

Parametry uruchomieniowe:
    Znajdują się w plikach src/main/resources/jade-*-container.properties

Agenci:
    CrawlerAgent
        CrawlTwitterBehaviour - pobiera tweety za pomocą streaming api z twitter4j.
            Po pobraniu tworzy SendTwittsBehaviour
            
        SendTwittsBehaviour, wysyła tweety dalej do CategorizerAgent

    CategorizerAgent
        CategorizeBehaviour

    StoreAgent
        StoreBehaviour

Pakiety:
    SendTwittsBehaviour -> (CrawledTweet) -> CategorizeBehaviour
    CategorizeBehaviour -> (CategorizedTweet) -> StoreBehaviour

TODO:
    - Podpiąć model z projektu openNLP do CategorizerAgent - Dobi: już jest w projekcie
    - Na razie CrawlerAgent pobiera tylko sample nie filter. - Dobi: już pobiera tweety na podstawie słów kluczowych
    - StoreAgent - zapisywanie tweetow z kategoriami
    - Update modelu online:
            StoreAgent - nowy behaviour UpdateCategorizerModelBehaviour
            CategorizerAgent - nowy behaviour UpdateToNewModelBehaviour
    - Opakowe wysylania wiadomosci w szukanie w whitepage i dopiero wysylanie.


Extra:

plik out.txt - zebranie logów dla konkretnego rodzaju agentów

