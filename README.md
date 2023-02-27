# Trask homework: Case study
# Spring Boot Application

## Overview

Spring Boot Application fulfilling the tasks and functionality described below.

## Technologies

- Java 19
- Spring Boot 3.0.2
- PostgreSQL 15.2

## Installation

To run this application, you'll need to have Java 19 and PostgreSQL 15.2 installed on your machine. Once you have those installed, follow these steps:

1. Clone the repository: `git clone https://github.com/PetangTheBeast/trask_CaseStudy.git`
2. Configure the database: create a new database in PostgreSQL and update the `application.properties` file with your database credentials
3. Run the application: `./mvnw spring-boot:run`

## Usage

To use the application, navigate to `http://localhost:8080` in your web browser. 


## Zadání

Vytvořte SpringBoot aplikaci s použitím Javy nebo Kotlinu na téma jednoduché databáze uchazečů. Aplikace musí obsahovat API a databázi do které se budou ukládat údaje o uchazeči. V databázi budou uloženy i technologie, které se vážou k uchazeči. Každá vazba mezi uchazečem a technologií bude obsahovat úroveň (1-10) a poznámku.


## Funkcionalita

Správa uchazečů:
- seznam 
- detail se seznamem technologií (včetně poznámky a úrovně znalostí) 
- přidání/odebrání/úprava uchazeče
- přidání/odebraní technologie pro uchazeče

Správa technologií:
- seznam
- detail
- přidání/odebrání/úprava

