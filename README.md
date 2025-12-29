# Blog do Agi - Test Automation Framework

[![Java](https://img.shields.io/badge/Java-11%2B-orange)](https://adoptium.net/)
[![Selenium](https://img.shields.io/badge/Selenium-4.16.0-green)](https://www.selenium.dev/)

---

## Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Execução dos Testes](#execução-dos-testes)
- [Relatórios](#relatórios)
- [CI/CD](#cicd)

---

## Sobre o Projeto

Este projeto implementa testes automatizados end-to-end para a funcionalidade de **pesquisa de artigos** do Blog do Agi, utilizando Selenium WebDriver.

### Objetivos

- Validar funcionalidade de busca do blog
- Detectar regressões rapidamente
- Documentar comportamento esperado
- Facilitar manutenção com Page Objects

---

---

##  Tecnologias Utilizadas


Java 11+ 
Selenium WebDriver - 4.16.0 
JUnit - 5.10.1 
Gradle - 8.5 
Allure - 2.25.0 
AssertJ - 3.24.2 

---


## Pré-requisitos

- **Java JDK 11 ou superior**
  - Download: [Adoptium](https://adoptium.net/)
  - Verificação: `java -version`

- **Google Chrome** (ou outro navegador)
  - Chrome é usado por padrão

- **Gradle** (incluído via Gradle Wrapper)

- **IDE** (IntelliJ IDEA, Eclipse, VS Code)

---

## 🚀 Instalação

### 1. Clonar o Repositório

```bash
git clone 
cd blog-agi-tests
```

### 2. Verificar Java

```bash
java -version
# Deve retornar versão 11 ou superior
```

### 3. Instalar Dependências

```bash
# Linux/Mac
./gradlew build --refresh-dependencies

# Windows
gradlew.bat build --refresh-dependencies
```

O **WebDriverManager** baixará automaticamente o driver do navegador.

---

## ▶️ Execução dos Testes

### Executar Todos os Testes

```bash
# Linux/Mac
./gradlew clean test

# Windows
gradlew.bat clean test
```

### Executar Testes Específicos

```bash
# Apenas testes de pesquisa positiva
./gradlew test --tests SearchTest

# Apenas testes negativos
./gradlew test --tests SearchNegativeTest

# Teste específico
./gradlew test --tests SearchTest.shouldReturnResultsForValidTerm
```

---

## 📊 Relatórios

### 1. Relatório HTML do Gradle

Gerado automaticamente após execução:

```bash
# Localização
build/reports/tests/test/index.html

