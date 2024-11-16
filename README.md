# Activité Pratique N°3: Event Driven Architecture with Apache Kafka 

**Presente par**: Oussama Elhachimi
**Encadre par**: Pr. Mohamed YOUSSFI    
**Filière**: Big Data & Cloud Computing

---

## 📝 Table des Matières

- [Objectif](#objectif)
- [Prérequis](#prérequis)
- [Partie 1 : Configuration de Kafka en Local](#partie-1-configuration-de-kafka-en-local)
- [Partie 2 : Configuration de Kafka avec Docker](#partie-2-configuration-de-kafka-avec-docker)
- [Partie 3 : Application avec Spring Cloud Streams](#partie-3-application-avec-spring-cloud-streams)
- [Références](#références)
- [Évaluation](#évaluation)

## Objectif

Cette activité pratique vise à vous familiariser avec l'**Architecture Pilotée par les Événements** en utilisant Apache Kafka. Vous allez :

1. Installer et configurer Kafka localement et avec Docker.
2. Développer des services Kafka (Producer, Consumer, Supplier) avec **Spring Cloud Streams**.
3. Implémenter une analyse des données en temps réel avec **Kafka Streams**.
4. Créer une application web pour visualiser les résultats de l'analyse en temps réel.

## Prérequis

- **Java 17+**
- **Apache Kafka**
- **Docker** et **Docker Compose**
- **Spring Boot** et **Spring Cloud Streams**

---

## Partie 1 : Configuration de Kafka en Local (Windows)

1. **Télécharger Apache Kafka** :
   - Rendez-vous sur le site officiel : [Apache Kafka](https://kafka.apache.org/downloads).
   - Téléchargez et extrayez l'archive.
   - Il recommende de mettre le dossier extrait dans un chemin court pour eviter des erreurs dans le lancements par exemple :(C:/kafka)
   - Deplacer au chemin de dossier kafka et ouvrir un terminal pour executer les commandes .
    
2. **Démarrer Zookeeper** :
   ```bash
   start bin\windows\zookeeper-server-start.bat config/zookeeper.properties
   ```
   
    ![1 1](https://github.com/user-attachments/assets/df0b1b63-79f8-4ae0-a534-c063388046b7)

3. **Démarrer le Serveur Kafka** :
```bash
start bin\windows\kafka-server-start.bat config/server.properties
```
    ![2 1](https://github.com/user-attachments/assets/807c67c4-aa9e-438c-ac87-414f821d1123).

4. **Tester avec Kafka Console Producer et Consumer** :
4.1 **Créer un topic** :
   Pour crée un topic nommé **R1** , vous pouvez executez  la commande suivante :
```bash
start bin\windows\kafka-topics.bat  --bootstrap-server localhost:9092 --create --topic R1 --partitions 1 --replication-factor 1
```
4.2 **Lancer un Producer** :
   La commande suivant lance un producer qui permet d'envoyer des messages au topic **R1** :
```bash
start bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic R1
```
  **Resultat**
  ![3](https://github.com/user-attachments/assets/02a57847-cb57-4a45-9d40-41714122582b)

4.3 **Lancer un Consumer** :
   La commande suivant lance un consumer qui lit les messages du topic **R1** :
```bash
start bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic R1
```
  **Remarque** : vous pouvez ajouter l'option:  
    ```--from-beginning``` : Indique de lire tous les messages depuis le début du topic (et non seulement les nouveaux messages).
  
  **Resultat**
  ![4](https://github.com/user-attachments/assets/f11d32af-03b0-440e-822e-59bd8d556ec2)



## Partie 2 : Configuration de Kafka avec Docker

1. **Créer le fichier `docker-compose.yml`** :
Ce fichier définit une configuration pour déployer Zookeeper et Kafka en utilisant des images Docker Bitnami.
```yaml
version: "3"
networks:
  myNetwork:
services:
  zookeeper:
    image: 'bitnami/zookeeper:latest'
    ports:
      - '2181:2181'
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes
    networks:
      - myNetwork
  kafka:
    image: 'bitnami/kafka:latest'
    user: root
    ports:
      - '9092:9092'
    environment:
      - KAFKA_BROKER_ID=1
      - KAFKA_LISTENERS=PLAINTEXT://:9092
      - KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://127.0.0.1:9092
      - KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
    volumes:
      - ./Kafka:/bitnami/kafka
    networks:
      - myNetwork
    depends_on:
      - zookeeper
```
2. **Démarrer les conteneurs Docker** :
Executer la commande suivant pour lancer le cluster :
  ```bash
  docker-compose up -d
  ```
  ****Resultat : Docker Desktop****
  ![image](https://github.com/user-attachments/assets/e491b652-e91d-4583-8118-bcabd3178e4e)

3. **Connecter a l'interieur du conteneur** :
Executer la commande suivant pour connecté au conteneur, où vous pourrez exécuter des commandes comme si vous étiez dans un terminal Linux classique :
  ```bash
  docker exec -it <kafka-container-id> bin/bash
  ```
4. **Tester avec Kafka Console Producer et Consumer** :
4.1 **Lancer un Producer**
   ![producer](https://github.com/user-attachments/assets/4649cff1-e9f1-4a6d-b5ff-bcf0d8c478da)
4.1 **Lancer un Consumer**
   ![consumer](https://github.com/user-attachments/assets/70d15ea6-56d0-46e6-95eb-2eefee481124)