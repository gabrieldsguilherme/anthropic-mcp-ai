
# 🧠 Name Analyzer with AI

This project is a **Proof of Concept (POC)** for implementing the **Model Context Protocol (MCP)** to track the full lifecycle of an API request, enrich it with structured data, and use **AI (GPT-4o / GPT-3.5)** to generate an **automatic technical summary**.

---

## 🚀 Features

- 🧾 **Request lifecycle trace** using the `MCPContext` abstraction
- 🌐 **External API integrations**:
    - [`agify.io`](https://agify.io) → estimate age
    - [`genderize.io`](https://genderize.io) → estimate gender
- 🧠 **AI-powered analysis** using OpenAI's GPT models
- 📄 OpenAPI 3.0 documentation served via Swagger UI
- 📦 Clean architecture using Spring Boot 3 + Java 17

---

## 📐 Architecture

```
Client
  |
  v
/analyze [POST] <----> AnalyzeController
                         |
                         v
                  AnalyzerService
                         |
            +------------+------------+
            |                         |
        AgifyClient             GenderizeClient
            |                         |
            +------------+------------+
                         |
                      MCPContext  ← records full trace
                         |
                         v
                     IAService  → OpenAI call
                         |
                         v
                  AnalyzeResponse (enriched)
```

---

## 🔧 Tech Stack

- Java 17 ☕️
- Spring Boot 3 ⚙️
- Spring WebFlux (WebClient) 🌐
- Lombok 🧬
- OpenAI API (Chat Completions – GPT-4o / GPT-3.5) 🤖

---

## 📥 How to Run

```bash
# Clone the repository
git clone https://github.com/your-username/anthropic-mcp-ai.git
cd anthropic-mcp-ai

# Make sure you have Java 17 installed
./mvnw spring-boot:run
```

---

## 🔐 Environment Configuration

Set these variables in your environment or in a `.env` file:

```env
OPENAI_API_KEY=sk-XXXXXXXXXXXXXXXXXXXXXXXXXXX
```

These values will be loaded via the `AIProperties` class using `@ConfigurationProperties`.

---

## 🧪 Try It

Once the server is running, access:

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- Try a POST to `/analyze` with:

```json
{
  "name": "gabriel"
}
```

You will receive a structured response with the AI-generated explanation in the `technicalExplanation` field.

---

# Enjoy! 😉
