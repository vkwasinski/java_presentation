# Java Code Examples - Demo Repository

This is a demonstration repository containing Java code examples I have written for educational and portfolio purposes. The examples showcase various Java programming concepts, design patterns, and practical implementations.

## Overview

This repository contains Java 8+ code examples demonstrating:

- **Service Architecture Patterns**: Singleton, Factory, Adapter patterns
- **RESTful API Integration**: HTTP client implementations with OAuth2 authentication
- **Data Transfer Objects (DTOs)**: Structured data modeling
- **Caching Strategies**: Redis integration with compression and serialization
- **Logging Systems**: Strategy and Stream patterns for flexible logging
- **Error Handling**: Custom exception hierarchies
- **SOLID Principles**: Clean, maintainable code structure

## Projects

### CacheService

A Redis cache service implementation demonstrating:
- Singleton pattern with thread-safe initialization
- Key-value and hash operations
- GZIP compression and Java serialization
- Environment-based key prefixing
- TTL (time-to-live) management
- Cache-or-compute pattern

**Location**: `CacheService/`

**Technologies**: Java 8, Jedis (Redis client), Maven

### Marketing Service (Mkt)

A marketing microservice integration demonstrating:
- HTTP client abstraction with OkHttp
- OAuth2 authentication flow
- RESTful API integration patterns
- Data synchronization (products, orders, customers)
- Email and SMS subscription management
- DTO pattern implementation

**Location**: `Mkt/`

**Technologies**: Java 8, OkHttp, Gson, Maven

### Log Service

A flexible logging service implementation demonstrating:
- Strategy pattern for different logger types (Web, CLI, Audit)
- Stream pattern for different output destinations (File, AuditFile, NewRelic)
- Singleton pattern with thread-safe initialization
- Debug mode support via environment variable
- Daily log file rotation
- SLF4J/Logback integration

**Location**: `Logs/`

**Technologies**: Java 8, SLF4J, Logback, Maven

## Purpose

This repository serves as:

- **Portfolio Demonstration**: Showcasing Java development skills and best practices
- **Learning Resource**: Examples of common patterns and implementations
- **Reference Material**: Code samples for similar projects

## Requirements

- **Java**: 8 or higher
- **Maven**: 3.x or higher
- **Build Tool**: Maven for dependency management

## Getting Started

Each project contains its own `pom.xml` and can be built independently:

```bash
# Build CacheService
cd CacheService
mvn clean compile

# Build Marketing Service
cd Mkt
mvn clean compile

# Build Log Service
cd Logs
mvn clean compile
```

## Code Quality

All code examples follow:

- **SOLID Principles**: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion
- **Design Patterns**: Where appropriate (Singleton, Factory, Adapter, Strategy, Stream)
- **Java Best Practices**: Proper exception handling, documentation, and type safety
- **Clean Code**: Readable, maintainable, and well-documented

## Structure

```
Java_examples/
├── CacheService/          # Redis cache service implementation
│   ├── Exceptions/        # Custom exception classes
│   ├── Interfaces/        # Service interfaces
│   └── RedisService.java # Main service class
├── Mkt/                   # Marketing service integration
│   ├── Client/           # HTTP client implementations
│   ├── DTOs/             # Data Transfer Objects
│   ├── Interfaces/       # Interface definitions
│   └── MarketingService.java # Main service class
├── Logs/                  # Logging service implementation
│   ├── Strategies/       # Logger strategy implementations
│   ├── Streams/          # Stream implementations
│   ├── Interfaces/       # Interface definitions
│   └── Log.java          # Main logging service class
└── README.md             # This file
```

## Notes

- All code is written for **Java 8** compatibility
- Examples use **Maven** for dependency management
- Code follows **app** namespace convention (removed proprietary namespaces)
- Services are designed to be **framework-agnostic** and can be integrated into various projects

## License

This repository contains demonstration code examples for portfolio and educational purposes.

## Author

Code examples written for demonstration and learning purposes.

