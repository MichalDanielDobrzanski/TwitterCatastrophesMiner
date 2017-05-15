# Distrbuted Agent system for analyzing Twitter content in case of finding car crashes and fire outbreaks

- This project was made at Warsaw Univerity of Technology for the purpose of Agent Systems course.

- Ths system finds car crashes and fire outbreaks related messages from Twitts

- **Classification** whether particulat Tweet contains dangerous content (car crash / fire outbreak)

- Implemeted **Agent architecture** for distribute system with **JADE framework**:
  - Main agent container
  - Defined custom agnt behaviour

- Used **Twitter4J** in order to get http requessts from Twiter official API:
  - needed obtaining API key
  - some agents are crawlers and gather Tweets

- Used **openNLP** for *natual language processing part*:
  - some agents feed the model with pre-proessed Tweet content in order to train it

Contributors:
- breiker
- mjkrajsman
  
## Folders:

- agent - whole agent system implemented with JADE
- openNLP - learning and testing NLP document categorizer model
- Twitter4J - handling Twitter API


### Resources:

- JADE: [main page](jade.tilab.com/)

- openNLP: [Sentiment analysis using OpenNLP document categorizer](http://rimmythepaperclip.blogspot.com/2015/01/using-apache-opennlp-document.html)

- openNLP document categorizer: [Using Apache OpenNLP Document Categorizer](http://technobium.com/sentiment-analysis-using-opennlp-document-categorizer/)

 - Twiter4J: [A Java library for TwitterAPI](http://twitter4j.org/en/index.html)

