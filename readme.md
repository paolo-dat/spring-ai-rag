# Spring AI RAG Demo

A Retrieval-Augmented Generation (RAG) application built with Spring AI that allows users to upload PDF documents and chat with an AI that uses the document content as context for generating responses.

## Overview

This application demonstrates how to implement a RAG system using Spring AI and pgvector. It provides:

- PDF document upload and processing
- Vector storage of document content using PostgreSQL with pgvector extension
- AI-powered chat interface that retrieves relevant information from uploaded documents

## Technologies Used

- Spring Boot 3.5.3
- Spring AI 1.0.0
- OpenAI API for LLM capabilities
- PostgreSQL with pgvector extension for vector storage
- Docker for containerization

## Prerequisites

- Java 21
- Docker and Docker Compose
- OpenAI API key

## Setup and Running

1. **Set environment variables**:
   ```
   export OPEN_AI_TOKEN=your_openai_api_key
   export OPEN_AI_MODEL=gpt-3.5-turbo  # or your preferred model
   ```

2. **Start the PostgreSQL database with pgvector**:
   ```
   docker-compose up -d
   ```

3. **Build and run the application**:
   ```
   ./mvnw spring-boot:run
   ```

## Usage

### Upload a PDF Document

Send a POST request to `/upload` with a PDF file:

```
curl -X POST -F "file=@/path/to/your/document.pdf" http://localhost:8080/upload
```

The document will be processed, split into chunks, and stored in the vector database.

### Chat with AI

Send a GET request to `/chat` with your question:

```
curl "http://localhost:8080/chat?message=What%20information%20is%20in%20the%20document?"
```

The AI will respond with information retrieved from the uploaded documents, providing context-aware answers.

## How It Works

1. When a PDF is uploaded, the `DocumentController` processes it using `PagePdfDocumentReader`
2. The document is split into manageable chunks using `TokenTextSplitter`
3. These chunks are stored in the pgvector database
4. When a user sends a chat message, the `ChatController` uses `QuestionAnswerAdvisor` to:
    - Find relevant document chunks in the vector store
    - Include these chunks as context for the AI model
    - Generate a response that's informed by the document content

This approach allows the AI to provide more accurate and relevant responses based on the specific information in your documents.