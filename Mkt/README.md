# Marketing Service - Java 8 Port

The service provides integration with a marketing microservice for managing products, orders, customers, email subscriptions, and SMS messaging.

## Features

- **Product Management**: Import products and product collections
- **Order Management**: Import orders and order collections
- **Customer Management**: Import customers and customer collections
- **Email Subscriptions**: Subscribe email contacts, manage suppression lists, update waitlists
- **SMS Subscriptions**: Subscribe SMS contacts, send transactional SMS messages
- **OAuth2 Authentication**: Automatic token management for all clients
- **Singleton Pattern**: Thread-safe singleton implementation for MarketingService

## Requirements

- Java 8 or higher
- Maven 3.x
- Marketing microservice API access

## Dependencies

- OkHttp 3.14.9 (HTTP client)
- Gson 2.8.9 (JSON serialization)

## Environment Variables

The service requires the following environment variables:

### Data API
- `MKT_DATA_API_CLIENT_ID`: Client ID for data API
- `MKT_DATA_API_CLIENT_SECRET`: Client secret for data API

### Email API
- `MKT_EMAIL_API_CLIENT_ID`: Client ID for email API
- `MKT_EMAIL_API_CLIENT_SECRET`: Client secret for email API
- `MKT_TRANSACTIONAL_EMAIL_LIST_ID`: Transactional email list ID
- `MKT_SUPPRESSION_LIST_ID`: Suppression list ID (optional)
- `MKT_ALERTS_BACK_IN_STOCK_LIST_ID`: Back in stock alerts list ID (optional)

### SMS API
- `MKT_SMS_API_CLIENT_ID`: Client ID for SMS API
- `MKT_SMS_API_CLIENT_SECRET`: Client secret for SMS API
- `MKT_TRANSACTIONAL_SMS_LIST_ID`: Transactional SMS list ID
- `MKT_TRANSACTIONAL_SMS_SHORTCODE_ID`: SMS shortcode ID

## Usage

### Basic Usage

```java
import app.mkt.MarketingService;
import app.mkt.dtos.ProductDTO;
import java.util.HashMap;
import java.util.Map;

// Get singleton instance
MarketingService service = MarketingService.instance();

// Import a product
Map<String, Object> productData = new HashMap<>();
productData.put(ProductDTO.SKU, "PROD-001");
productData.put(ProductDTO.TITLE, "Example Product");
productData.put(ProductDTO.PRICE, 29.99);
ProductDTO product = new ProductDTO(productData);
service.importProduct(product);
```

### Email Subscriptions

```java
import app.mkt.dtos.emails.EmailContactDTO;
import java.util.HashMap;
import java.util.Map;

Map<String, Object> contactData = new HashMap<>();
contactData.put(EmailContactDTO.EMAIL_ADDRESS, "user@example.com");
contactData.put(EmailContactDTO.SUBSCRIPTION_STATE, true);
contactData.put(EmailContactDTO.EXTERNAL_CONTACT_ID, "12345");
contactData.put(EmailContactDTO.SEGMENTATION_FIELD_VALUES, new ArrayList<>());

EmailContactDTO contact = new EmailContactDTO(contactData);
service.subscribeEmail(contact);
```

### SMS Subscriptions

```java
import app.mkt.dtos.sms.SMSContactDTO;
import java.util.HashMap;
import java.util.Map;

Map<String, Object> smsData = new HashMap<>();
smsData.put(SMSContactDTO.PHONE_NUMBER, "+1234567890");
smsData.put(SMSContactDTO.EMAIL_ADDRESS, "user@example.com");
smsData.put(SMSContactDTO.FIRST_NAME, "John");
smsData.put(SMSContactDTO.LAST_NAME, "Doe");
smsData.put(SMSContactDTO.OPTED_OUT, false);

SMSContactDTO smsContact = new SMSContactDTO(smsData);
service.subscribeSMS(smsContact);
```

## Package Structure

- `app.mkt`: Main service class
  - `MarketingService.java`: Main service implementation
- `app.mkt.client`: HTTP client classes
  - `AbstractHttpClient.java`: Base HTTP client
  - `MktDataClient.java`: Data API client
  - `MktEmailClient.java`: Email API client
  - `MktSMSClient.java`: SMS API client
- `app.mkt.dtos`: Data Transfer Objects
  - `ProductDTO.java`: Product data structure
  - `OrderDTO.java`: Order data structure
  - `CustomerDTO.java`: Customer data structure
  - `emails/`: Email-related DTOs
    - `EmailContactDTO.java`: Email contact DTO
    - `WaitingListSubscriptionDTO.java`: Waitlist subscription DTO
  - `sms/`: SMS-related DTOs
    - `SMSContactDTO.java`: SMS contact DTO
    - `MessageDTO.java`: SMS message DTO
- `app.mkt.interfaces`: Interface definitions
  - `ProductInterface.java`: Product field constants
  - `OrderInterface.java`: Order field constants
  - `EmailContactDTOInterface.java`: Email contact interface
  - `MessageDTOInterface.java`: Message interface

## Building

```bash
mvn clean compile
```

- All clients automatically authenticate on construction
- Authentication tokens are managed internally
- The service uses singleton pattern for instance management
- All DTOs support JSON serialization via `toJSON()` method
- Error handling uses standard Java exceptions and logging

