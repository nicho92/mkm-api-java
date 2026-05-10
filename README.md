# mkm-api-java

[![Maven Central](https://img.shields.io/maven-central/v/com.github.nicho92/mkm-api-java.svg)](https://central.sonatype.com/artifact/com.github.nicho92/mkm-api-java)
[![Java](https://img.shields.io/badge/Java-21%2B-blue.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](http://www.apache.org/licenses/LICENSE-2.0)

Java client library for the Cardmarket (formerly MagicCardMarket / MKM) API v2.0.
It wraps authentication, XML parsing, and the most common marketplace workflows so
Java applications can search products, manage stock, handle want lists, inspect
orders, and work with shopping carts through a small service-oriented API.

> **Note**
> This project is an unofficial Java implementation for the Cardmarket API. You
> need valid Cardmarket API credentials before you can call authenticated
> endpoints.

## Features

- OAuth 1.0 request signing for Cardmarket API calls
- Product and metaproduct search helpers
- Article lookup by seller or product
- Shopping cart management
- Stock import, export, add, update, and delete operations
- Want list create, rename, delete, item add/update/delete, and item loading
- Order listing, order lookup, and tracking number updates
- User lookup, vacation mode, messaging, and authenticated account lookup
- Game, expansion, and category lookup
- XML response mapping to Java model classes

## Requirements

- Java 21 or newer
- Maven 3.x for building from source
- Cardmarket API application token/secret and access token/secret

## Installation

### Maven

```xml
<dependency>
  <groupId>com.github.nicho92</groupId>
  <artifactId>mkm-api-java</artifactId>
  <version>0.26</version>
</dependency>
```

### Gradle

```gradle
implementation 'com.github.nicho92:mkm-api-java:0.26'
```

## Quick start

Initialize the singleton API configuration once at application startup, then use
service classes for each API domain.

```java
import java.util.List;

import org.api.mkm.modele.Game;
import org.api.mkm.modele.User;
import org.api.mkm.services.GameService;
import org.api.mkm.tools.MkmAPIConfig;

public class Example {
  public static void main(String[] args) throws Exception {
    MkmAPIConfig.getInstance().init(
        System.getenv("MKM_ACCESS_TOKEN_SECRET"),
        System.getenv("MKM_ACCESS_TOKEN"),
        System.getenv("MKM_APP_SECRET"),
        System.getenv("MKM_APP_TOKEN")
    );

    User me = MkmAPIConfig.getInstance()
        .getAuthenticator()
        .getAuthenticatedUser();

    List<Game> games = new GameService().listGames();

    System.out.println("Authenticated as: " + me.getUsername());
    System.out.println("Available games: " + games.size());
  }
}
```

## Configuration

You can initialize credentials directly:

```java
MkmAPIConfig.getInstance().init(
    accessTokenSecret,
    accessToken,
    appSecret,
    appToken
);
```

Or load them from a Java `Properties` object or file containing these keys:

```properties
APP_ACCESS_TOKEN_SECRET=your-access-token-secret
APP_ACCESS_TOKEN=your-access-token
APP_SECRET=your-application-secret
APP_TOKEN=your-application-token
```

```java
MkmAPIConfig.getInstance().init(new File("cardmarket.properties"));
```

Keep credentials out of source control. Prefer environment variables, secret
managers, or ignored local property files.

## Available services

| Service | Purpose |
| --- | --- |
| `AuthenticationServices` | Authenticate requests and fetch the authenticated account. |
| `ArticleService` | Find seller articles or articles for a product. |
| `CartServices` | Add, remove, empty, and read shopping cart contents. |
| `GameService` | List games, expansions, and product categories. |
| `OrderService` | List orders, load an order by ID, and update tracking numbers. |
| `ProductServices` | Search products/metaproducts, fetch product data, and export product/price-guide files. |
| `StockService` | Read, export, add, update, remove, and inspect stock articles. |
| `UserService` | Search users, set vacation mode, send messages, and read message threads. |
| `WantsService` | Manage want lists and want-list items. |
| `DevelopperServices` | Execute lower-level custom API requests. |

## Usage examples

### Search products

```java
ProductServices products = new ProductServices();
List<Product> results = products.findProduct("Lightning Bolt", null);
```

### Read a want list

```java
WantsService wants = new WantsService();
List<Wantslist> lists = wants.getWantList();

for (Wantslist list : lists) {
  wants.loadItems(list);
  System.out.println(list.getName() + ": " + list.getItem().size() + " items");
}
```

### Add an article to the shopping cart

```java
CartServices cart = new CartServices();
cart.addArticle(article);
Basket basket = cart.getBasket();
```

### Check API request-limit counters

`MkmAPIConfig` tracks the latest `X-Request-Limit-Max` and
`X-Request-Limit-Count` response headers observed by the library:

```java
int used = MkmAPIConfig.getInstance().getCountCall();
int max = MkmAPIConfig.getInstance().getMaxCall();
```

## Building from source

Clone the repository and build with Maven:

```bash
git clone https://github.com/nicho92/mkm-api-java.git
cd mkm-api-java
mvn clean test
```

Create the package locally:

```bash
mvn clean package
```

## Project structure

```text
src/main/java/org/api/mkm/services   Service classes for Cardmarket endpoints
src/main/java/org/api/mkm/modele     API model classes
src/main/java/org/api/mkm/tools      Configuration, constants, XML, and utility helpers
src/main/java/org/api/mkm/exceptions Custom exception types
src/main/java/org/mkm/gui            Optional Swing GUI helpers
src/test/java                        Tests
```

## Error handling

Most service methods throw `IOException` for network and parsing failures.
Authentication setup can throw `MkmException` when required credential fields are
missing. Non-successful HTTP responses are surfaced through `MkmNetworkException`
where implemented.

## Contributing

Contributions are welcome. A typical workflow is:

1. Fork the repository.
2. Create a feature branch.
3. Make your changes with tests when possible.
4. Run `mvn clean test`.
5. Open a pull request describing the change and any compatibility impact.

## License

This project is distributed under the Apache License, Version 2.0, as declared in
`pom.xml`.
