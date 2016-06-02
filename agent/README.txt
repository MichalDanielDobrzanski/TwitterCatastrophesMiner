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
    - StoreAgent - zapisywanie tweetow z kategoriami - Dobi: Przejrzyjcie i oceńcie.
    - Update modelu online:
            StoreAgent - nowy behaviour UpdateCategorizerModelBehaviour
            CategorizerAgent - nowy behaviour UpdateToNewModelBehaviour
    - Opakowe wysylania wiadomosci w szukanie w whitepage i dopiero wysylanie.


Twitter4J:

	Aby zablokować nadmierne duże logi generowane przez Twitter4J, do własnego pliku twitter4j.properties należy dodać linijkę:
	loggerFactory=twitter4j.NullLoggerFactory
	Referencja: http://twitter4j.org/en/configuration.html

Nowy model:

	Podmieniony model. Nauczony za pomocą około 3200 Tweetów i szczegółowych keywordów.
	Sprawozdanie w pliku model_information.txt
	

